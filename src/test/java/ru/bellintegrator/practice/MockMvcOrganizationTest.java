package ru.bellintegrator.practice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class MockMvcOrganizationTest {
    @Autowired
    private MockMvc mockMvc;

    private static final ObjectMapper om = new ObjectMapper();

    @Test
    public void testGetOrganizationByIdIsOk() throws Exception {
        OrganizationView organizationView = new OrganizationView();
        organizationView.setId("1");
        organizationView.setName("ОАО СТАРТ");
        organizationView.setFullName("ОТКРЫТОЕ АКЦИОНЕРНОЕ ОБЩЕСТВО СТАРТ");
        organizationView.setInn("5820001122");
        organizationView.setKpp("582001001");
        organizationView.setAddress("ПЕНЗА УЛ.КРАСНАЯ Д. 1");
        organizationView.setPhone("541234");
        organizationView.setActive(true);
        mockMvc.perform(get("/api/organization/1"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(organizationView.getId())))
                .andExpect(jsonPath("$.name", is(organizationView.getName())))
                .andExpect(jsonPath("$.fullName", is(organizationView.getFullName())))
                .andExpect(jsonPath("$.inn", is(organizationView.getInn())))
                .andExpect(jsonPath("$.kpp", is(organizationView.getKpp())))
                .andExpect(jsonPath("$.address", is(organizationView.getAddress())))
                .andExpect(jsonPath("$.phone", is(organizationView.getPhone())))
                .andExpect(jsonPath("$.isActive", is(organizationView.getActive())));
    }

    @Test
    public void testGetOrganizationByIdIsError() throws Exception {
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
    public void testPostFilterOrganizationByNameIsOk() throws Exception {
        OrganizationFilterView organizationFilterView = new OrganizationFilterView();
        organizationFilterView.setName("АО");
        mockMvc.perform(
                post("/api/organization/list")
                        .content(om.writeValueAsString(organizationFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testPostFilterOrganizationByNameIsError() throws Exception {
        OrganizationFilterView organizationFilterView = new OrganizationFilterView();
        organizationFilterView.setName("");
        MvcResult mvcResult = mockMvc.perform(
                post("/api/organization/list")
                        .content(om.writeValueAsString(organizationFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле name* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostUpdateOrganizationIsOk() throws Exception {
        String id = "3";
        OrganizationView organizationView = new OrganizationView();
        organizationView.setId(id);
        organizationView.setName("МУП ОБНОВЛЕНИЕ");
        organizationView.setFullName("ТЕСТ ОБНОВЛЕНИЕ");
        organizationView.setInn("5820009988");
        organizationView.setKpp("582001009");
        organizationView.setAddress("ГОРОД-2");
        organizationView.setPhone("111");
        organizationView.setActive(false);
        mockMvc.perform(
                post("/api/organization/update")
                        .content(om.writeValueAsString(organizationView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        perfomGetOrganizationById(organizationView);
    }

    @Test
    public void testPostUpdateOrganizationIsError() throws Exception {
        OrganizationView organizationViewUpdate = new OrganizationView();
        MvcResult mvcResult = mockMvc.perform(
                post("/api/organization/update")
                        .content(om.writeValueAsString(organizationViewUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнены все обязательные поля* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostAddOrganizationIsOk() throws Exception {
        OrganizationView organizationView = new OrganizationView();
        organizationView.setName("ЖК НОВАЯ");
        organizationView.setFullName("ДОБАВИТЬ ОРГАНИЗАЦИЮ НОВАЯ");
        organizationView.setInn("5820009999");
        organizationView.setKpp("582001001");
        organizationView.setAddress("город");
        organizationView.setPhone("111");
        organizationView.setActive(false);
        mockMvc.perform(
                post("/api/organization/save")
                        .content(om.writeValueAsString(organizationView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String id = getIdFromListFilterOrganization(organizationView);
        organizationView.setId(id);
        perfomGetOrganizationById(organizationView);
    }

    @Test
    public void testPostAddOrganizationEmptyNameIsError() throws Exception {
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
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнены все обязательные поля* организации\"}",
                mvcResult.getResponse().getContentAsString());
    }

    public String getIdFromListFilterOrganization(OrganizationView organizationView) throws Exception {
        OrganizationFilterView organizationFilterView = new OrganizationFilterView();
        organizationFilterView.setName(organizationView.getName());
        organizationFilterView.setInn(organizationView.getInn());
        organizationFilterView.setActive(organizationFilterView.getActive());
        mockMvc.perform(
                post("/api/organization/list")
                        .content(om.writeValueAsString(organizationFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("4")))
                .andExpect(jsonPath("$.length()", is(1)));
        return "4";
    }

    public void perfomGetOrganizationById(OrganizationView organizationView) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/organization/{id}", organizationView.getId())
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(organizationView.getId())))
                .andExpect(jsonPath("$.name", is(organizationView.getName())))
                .andExpect(jsonPath("$.fullName", is(organizationView.getFullName())))
                .andExpect(jsonPath("$.inn", is(organizationView.getInn())))
                .andExpect(jsonPath("$.kpp", is(organizationView.getKpp())))
                .andExpect(jsonPath("$.address", is(organizationView.getAddress())))
                .andExpect(jsonPath("$.phone", is(organizationView.getPhone())))
                .andExpect(jsonPath("$.isActive", is(organizationView.getActive())));
    }

}
