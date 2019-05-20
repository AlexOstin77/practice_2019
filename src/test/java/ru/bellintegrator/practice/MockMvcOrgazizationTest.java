package ru.bellintegrator.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bellintegrator.practice.service.impl.OrganizationServiceImpl;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class MockMvcOrgazizationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    OrganizationServiceImpl service;

    private static final ObjectMapper om = new ObjectMapper();

    @Test
    public void testGetOrganizationIsOk() throws Exception {
        OrganizationView organizationViewGetById1 = new OrganizationView();
        organizationViewGetById1.setId("1");
        organizationViewGetById1.setName("ОАО СТАРТ");
        organizationViewGetById1.setFullName("ОТКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО СТАРТ");
        organizationViewGetById1.setInn("5820001122");
        organizationViewGetById1.setKpp("582001001");
        organizationViewGetById1.setAddress("ПЕНЗА УЛ.КРАСНАЯ Д. 1");
        organizationViewGetById1.setPhone("541234");
        organizationViewGetById1.setActive(true);
        mockMvc.perform(get("/api/organization/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(organizationViewGetById1.getId())))
                .andExpect(jsonPath("$.name", is(organizationViewGetById1.getName())))
                .andExpect(jsonPath("$.fullName", is(organizationViewGetById1.getFullName())))
                .andExpect(jsonPath("$.inn", is(organizationViewGetById1.getInn())))
                .andExpect(jsonPath("$.kpp", is(organizationViewGetById1.getKpp())))
                .andExpect(jsonPath("$.address", is(organizationViewGetById1.getAddress())))
                .andExpect(jsonPath("$.phone", is(organizationViewGetById1.getPhone())))
                .andExpect(jsonPath("$.isActive", is(organizationViewGetById1.getActive())));
    }

    @Test
    public void testGetOrganizationByEmptyIdIsError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/organization/{id}", "")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле Id* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostOrganizationFilterNameIsOk() throws Exception {
        OrganizationFilterView organizationFilterViewNameWithO = new OrganizationFilterView();
        organizationFilterViewNameWithO.setName("АО");
        mockMvc.perform(
                post("/api/organization/list")
                        .content(om.writeValueAsString(organizationFilterViewNameWithO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testPostOrganizationFilterEmptyNameIsError() throws Exception {
        OrganizationFilterView organizationFilterViewNameWithO = new OrganizationFilterView();
        organizationFilterViewNameWithO.setName("");
        MvcResult mvcResult = mockMvc.perform(
                post("/api/organization/list")
                        .content(om.writeValueAsString(organizationFilterViewNameWithO))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле name* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostOrganizationUpdateIsOk() throws Exception {
        String id = "3";
        OrganizationView organizationViewUpdate = new OrganizationView();
        organizationViewUpdate.setId(id);
        organizationViewUpdate.setName("МУП ОБНОВЛЕНИЕ");
        organizationViewUpdate.setFullName("ТЕСТ ОБНОВЛЕНИЕ");
        organizationViewUpdate.setInn("5820009988");
        organizationViewUpdate.setKpp("582001009");
        organizationViewUpdate.setAddress("ГОРОД-2");
        organizationViewUpdate.setPhone("111");
        organizationViewUpdate.setActive(false);
        mockMvc.perform(
                post("/api/organization/update")
                        .content(om.writeValueAsString(organizationViewUpdate))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        assertEquals(service.getOrganizationById(id).getName(), organizationViewUpdate.getName());
        assertEquals(service.getOrganizationById(id).getFullName(), organizationViewUpdate.getFullName());
        assertEquals(service.getOrganizationById(id).getInn(), organizationViewUpdate.getInn());
        assertEquals(service.getOrganizationById(id).getKpp(), organizationViewUpdate.getKpp());
        assertEquals(service.getOrganizationById(id).getAddress(), organizationViewUpdate.getAddress());
        assertEquals(service.getOrganizationById(id).getPhone(), organizationViewUpdate.getPhone());
        assertEquals(service.getOrganizationById(id).getActive(), organizationViewUpdate.getActive());
    }

    @Test
    public void testPostOrganizationUpdateIsError() throws Exception {
        OrganizationView organizationViewUpdate = new OrganizationView();
        MvcResult mvcResult = mockMvc.perform(
                post("/api/organization/update")
                        .content(om.writeValueAsString(organizationViewUpdate))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнены все обязательные поля* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostOrganizationAddIsOk() throws Exception {
        OrganizationView organizationViewAdd = new OrganizationView();
        organizationViewAdd.setName("ЖК НОВАЯ");
        organizationViewAdd.setFullName("ДОБАВИТЬ ОРГАНИЗАЦИЮ НОВАЯ");
        organizationViewAdd.setInn("5820009999");
        organizationViewAdd.setKpp("582001001");
        organizationViewAdd.setAddress("город");
        organizationViewAdd.setActive(false);
        mockMvc.perform(
                post("/api/organization/save")
                        .content(om.writeValueAsString(organizationViewAdd))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String id = findIdFromListFilterOrganization(organizationViewAdd);
        assertEquals(service.getOrganizationById(id).getName(), organizationViewAdd.getName());
        assertEquals(service.getOrganizationById(id).getFullName(), organizationViewAdd.getFullName());
        assertEquals(service.getOrganizationById(id).getInn(), organizationViewAdd.getInn());
        assertEquals(service.getOrganizationById(id).getKpp(), organizationViewAdd.getKpp());
        assertEquals(service.getOrganizationById(id).getAddress(), organizationViewAdd.getAddress());
        assertEquals(service.getOrganizationById(id).getActive(), organizationViewAdd.getActive());
    }

    @Test
    public void testPostOrganizationAddEmptyNameIsError() throws Exception {
        OrganizationView organizationViewAdd = new OrganizationView();
        organizationViewAdd.setName("");
        organizationViewAdd.setFullName("ДОБАВИТЬ ОРГАНИЗАЦИЮ НОВАЯ");
        organizationViewAdd.setInn("5820009999");
        organizationViewAdd.setKpp("582001001");
        organizationViewAdd.setAddress("город");
        organizationViewAdd.setActive(false);
        MvcResult mvcResult = mockMvc.perform(
                post("/api/organization/save")
                        .content(om.writeValueAsString(organizationViewAdd))
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнены все обязательные поля* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    public String findIdFromListFilterOrganization(OrganizationView organizationViewAdd) {
        OrganizationFilterView organizationFilterView = new OrganizationFilterView();
        organizationFilterView.setName(organizationViewAdd.getName());
        organizationFilterView.setInn(organizationViewAdd.getInn());
        organizationFilterView.setActive(organizationFilterView.getActive());
        List<OrganizationFilterView> list = service.filterOrganizationList(organizationFilterView);
        return list.get(0).getId();
    }

}
