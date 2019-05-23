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
import ru.bellintegrator.practice.view.OfficeFilterView;
import ru.bellintegrator.practice.view.OfficeView;

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

public class MockMvcOfficeTest {
    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper om = new ObjectMapper();

    @Test
    public void testPostOfficeFilterNameIsOk() throws Exception {
        OfficeFilterView officeFilterViewOrgId1 = new OfficeFilterView();
        officeFilterViewOrgId1.setOrgId("1");
        officeFilterViewOrgId1.setActive(true);
        mockMvc.perform(
                post("/api/office/list")
                        .content(om.writeValueAsString(officeFilterViewOrgId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void testPostOfficeFilterEmptyOrgIdIsError() throws Exception {
        OfficeFilterView officeFilterViewOrgId1 = new OfficeFilterView();
        officeFilterViewOrgId1.setOrgId("");
        MvcResult mvcResult = mockMvc.perform(
                post("/api/office/list")
                        .content(om.writeValueAsString(officeFilterViewOrgId1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле OrgId* офиса\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetOfficeGetByIdIsOk() throws Exception {
        mockMvc.perform(get("/api/office/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("1")))
                .andExpect(jsonPath("$.name", is("АДМИНИСТРАЦИЯ")))
                .andExpect(jsonPath("$.address", is("Г.ПЕНЗА УЛ. ЛЕНИНА Д.777")))
                .andExpect(jsonPath("$.phone", is("84125565")))
                .andExpect(jsonPath("$.isActive", is(true)));
    }

    @Test
    public void testGetOfficeByEmptyIdIsError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/office/{id}", "")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле Id* офиса\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostOfficeUpdateIsOk() throws Exception {
        OfficeView officeView = new OfficeView();
        String id = "2";
        officeView.setId(id);
        officeView.setName("ОБНОВЛЕННЫЙ ОФИС");
        officeView.setAddress("ГОРОД-2");
        officeView.setPhone("111");
        officeView.setActive(false);
        mockMvc.perform(
                post("/api/office/update")
                        .content(om.writeValueAsString(officeView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        perfomGetOfficeById(officeView);
    }

    @Test
    public void testPostOfficeUpdateIsError() throws Exception {
        OfficeView officeViewUpdate = new OfficeView();
        MvcResult mvcResult = mockMvc.perform(
                post("/api/office/update")
                        .content(om.writeValueAsString(officeViewUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнены все обязательные поля* офиса\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostOfficeAddIsOk() throws Exception {
        OfficeView officeView = new OfficeView();
        officeView.setOrgId("1");
        officeView.setName("НОВЫЙ ОФИС");
        officeView.setAddress("ГОРОД");
        officeView.setPhone("111");
        officeView.setActive(true);
        mockMvc.perform(
                post("/api/office/save")
                        .content(om.writeValueAsString(officeView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String id = getIdFromListFilterOffice(officeView);
        officeView.setId(id);
        perfomGetOfficeById(officeView);
    }

    @Test
    public void testPostOfficeAddEmptyNameIsError() throws Exception {
        OfficeView officeViewAdd = new OfficeView();
        officeViewAdd.setOrgId("");
        MvcResult mvcResult = mockMvc.perform(
                post("/api/office/save")
                        .content(om.writeValueAsString(officeViewAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле orgId* офиса\"}",
                mvcResult.getResponse().getContentAsString());
    }

    public String getIdFromListFilterOffice(OfficeView officeView) throws Exception {
        OfficeFilterView officeFilterView = new OfficeFilterView();
        officeFilterView.setOrgId(officeView.getOrgId());
        officeFilterView.setName(officeView.getName());
        officeFilterView.setPhone(officeView.getPhone());
        officeFilterView.setActive(officeView.getActive());
        mockMvc.perform(
                post("/api/office/list")
                        .content(om.writeValueAsString(officeFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("6")))
                .andExpect(jsonPath("$.length()", is(1)));
        return "6";
    }

    public void perfomGetOfficeById(OfficeView officeView) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/office/{id}", officeView.getId())
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(officeView.getName())))
                .andExpect(jsonPath("$.address", is(officeView.getAddress())))
                .andExpect(jsonPath("$.phone", is(officeView.getPhone())))
                .andExpect(jsonPath("$.isActive", is(officeView.getActive())));
    }
}
