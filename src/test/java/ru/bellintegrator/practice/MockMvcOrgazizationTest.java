package ru.bellintegrator.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.bellintegrator.practice.controller.impl.OrganizationControllerImpl;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class MockMvcOrgazizationTest {
    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper om = new ObjectMapper();

    @MockBean
    private OrganizationControllerImpl mockOrganizationControllerImpl;

    @Before
    public void init() {
        OrganizationFilterView organizationFilterViewGetById1 = new OrganizationFilterView();
        OrganizationFilterView organizationFilterViewGetById2 = new OrganizationFilterView();
        OrganizationFilterView organizationFilterViewGetById3 = new OrganizationFilterView();
        List organizationFilterListContainsId1Id2 = new ArrayList<OrganizationFilterView>();
        List organizationFilterList1 = new ArrayList<OrganizationFilterView>();
        OrganizationView organizationViewGetById1 = new OrganizationView();
        organizationViewGetById1.setId("1");
        organizationViewGetById1.setName("ООО СТАРТ");
        organizationViewGetById1.setFullName("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕСТВЕННОСТЬЮ СТАРТ");
        organizationViewGetById1.setInn("5820001122");
        organizationViewGetById1.setKpp("582001001");
        organizationViewGetById1.setAddress("ПЕНЗА УЛ.КРАСНАЯ Д. 1");
        organizationViewGetById1.setPhone("541234");
        organizationViewGetById1.setActive(true);

        OrganizationView organizationViewGetById2 = new OrganizationView();
        organizationViewGetById2.setId("2");
        organizationFilterViewGetById1.setId("1");
        organizationFilterViewGetById1.setName("ООО СТАРТ");
        organizationFilterViewGetById1.setActive(true);

        organizationFilterViewGetById2.setId("2");
        organizationFilterViewGetById2.setName("ЗАО МИР");
        organizationFilterViewGetById2.setActive(true);

        organizationFilterViewGetById3.setId("3");
        organizationFilterViewGetById3.setName("КБ ИНТЕГРАЛ");
        organizationFilterViewGetById3.setActive(false);


        organizationFilterList1.add(organizationFilterViewGetById1);

        organizationFilterListContainsId1Id2.add(organizationFilterViewGetById1);
        organizationFilterListContainsId1Id2.add(organizationFilterViewGetById2);

        OrganizationFilterView organizationFilterViewNameWithO = new OrganizationFilterView();
        organizationFilterViewNameWithO.setName("О");

        when(mockOrganizationControllerImpl.getOrganizationById("1")).thenReturn(organizationViewGetById1);
        when(mockOrganizationControllerImpl.getOrganizationById("2")).thenReturn(organizationViewGetById2);
        when(mockOrganizationControllerImpl.filterOrganizations(organizationFilterViewNameWithO)).thenReturn(organizationFilterListContainsId1Id2);
    }

    @Test
    public void getOrganizationById1() throws Exception {
        mockMvc.perform(get("/api/organization/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("ООО СТАРТ")))
                .andExpect(jsonPath("$.fullName", is("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕСТВЕННОСТЬЮ СТАРТ")))
                .andExpect(jsonPath("$.inn", is("5820001122")))
                .andExpect(jsonPath("$.kpp", is("582001001")))
                .andExpect(jsonPath("$.address", is("ПЕНЗА УЛ.КРАСНАЯ Д. 1")))
                .andExpect(jsonPath("$.phone", is("541234")))
                .andExpect(jsonPath("$.isActive", is(true)));
        verify(mockOrganizationControllerImpl, times(1)).getOrganizationById("1");
    }

    @Test
    public void getOrganizationById2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/organization/{id}", 2)
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2));
    }

    @Test
    public void postOrganizationFilter() throws Exception {
        OrganizationFilterView organizationFilterViewNameWithO = new OrganizationFilterView();
        organizationFilterViewNameWithO.setName("О");

        mockMvc.perform(
                post("/api/organization/list")
                        .content(om.writeValueAsString(organizationFilterViewNameWithO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void postOrganizationAdd() throws Exception {
        OrganizationView organizationViewAdd = new OrganizationView();
        organizationViewAdd.setName("ООО ЗАРЯ");
        organizationViewAdd.setFullName("НОВАЯ ЗАРЯ");
        organizationViewAdd.setInn("5820009999");
        organizationViewAdd.setKpp("582001001");
        organizationViewAdd.setAddress("город");
        organizationViewAdd.setActive(false);
        mockMvc.perform(
                post("/api/organization/save")
                        .content(om.writeValueAsString(organizationViewAdd))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postOrganizationUpdate() throws Exception {
        OrganizationView organizationViewUpdate = new OrganizationView();
        organizationViewUpdate.setId("3");
        organizationViewUpdate.setName("МУП ОБНОВЛЕНИЕ");
        organizationViewUpdate.setFullName("ТЕСТ ОБНОВЛЕНИЕ");
        organizationViewUpdate.setInn("5820009988");
        organizationViewUpdate.setKpp("582001009");
        organizationViewUpdate.setAddress("ГОРОД-2");
        mockMvc.perform(
                post("/api/organization/update")
                        .content(om.writeValueAsString(organizationViewUpdate))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
