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
import ru.bellintegrator.practice.view.UserFilterView;
import ru.bellintegrator.practice.view.UserView;

import java.sql.Date;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class MockMvcUserTest {
    @Autowired
    private MockMvc mockMvc;
    private static final ObjectMapper om = new ObjectMapper();

    @Test
    public void testPostUserFilterIsOk() throws Exception {
        UserFilterView userFilterView = new UserFilterView();
        userFilterView.setOfficeId("1");
        userFilterView.setPossition("ДИРЕКТОР");
        mockMvc.perform(
                post("/api/user/list")
                        .content(om.writeValueAsString(userFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(4)));
    }

    @Test
    public void testPostUserFilterEmptyNameIsError() throws Exception {
        UserFilterView userFilterView = new UserFilterView();
        userFilterView.setOfficeId("");
        MvcResult mvcResult = mockMvc.perform(
                post("/api/user/list")
                        .content(om.writeValueAsString(userFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле officeId* сотрудника\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetUserByIdIsOk() throws Exception {
        UserView userView = new UserView();
        userView.setId("1");
        userView.setFirstName("ИВАН");
        userView.setMiddleName("ИВАНОВИЧ");
        userView.setSecondName("ИВАНОВ");
        userView.setPossition("ГЕНЕРАЛЬНЫЙ ДИРЕКТОР");
        userView.setPhone("94512345");
        userView.setDocName("Паспорт гражданина РФ");
        userView.setDocCode("21");
        userView.setDocNumber("565999");
        userView.setDocDate(Date.valueOf("2002-01-15"));
        userView.setCitizenshipName("Российская Федерация");
        userView.setCitizenshipCode("643");
        userView.setIdentified(true);
        perfomGetUserById(userView);
    }

    @Test
    public void testGetUserByEmptyIdIsError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/{id}", "")
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле Id* сотрудника\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostUserUpdateIsOk() throws Exception {
        UserView userView = new UserView();
        userView.setId("4");
        userView.setFirstName("НИКОЛАЙ");
        userView.setMiddleName("НИКОЛАЕВИЧ");
        userView.setSecondName("НИКОЛАЕВ");
        userView.setPossition("НАЧАЛЬНИК");
        userView.setPhone("94512345");
        userView.setDocName("Паспорт гражданина РФ");
        userView.setDocCode("21");
        userView.setDocNumber("561234");
        userView.setDocDate(Date.valueOf("2001-01-25"));
        userView.setCitizenshipName("Российская Федерация");
        userView.setCitizenshipCode("643");
        userView.setIdentified(true);
        mockMvc.perform(
                post("/api/user/update")
                        .content(om.writeValueAsString(userView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        perfomGetUserById(userView);
    }

    @Test
    public void testPostUserUpdateIsError() throws Exception {
        UserView userViewUpdate = new UserView();
        MvcResult mvcResult = mockMvc.perform(
                post("/api/user/update")
                        .content(om.writeValueAsString(userViewUpdate))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле Id* сотрудника\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostUserAddEmptyIsError() throws Exception {
        UserView userViewAdd = new UserView();
        MvcResult mvcResult = mockMvc.perform(
                post("/api/user/save")
                        .content(om.writeValueAsString(userViewAdd))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertEquals("{\"error\":\"Не заполнено обязательное поле officeId* сотрудника\"}",
                mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testPostUserAddIsOk() throws Exception {
        UserView userView = new UserView();
        userView.setOfficeId("2");
        userView.setFirstName("ЭДУАРД");
        userView.setSecondName("ЭВЕНТОВ");
        userView.setMiddleName("ЭДУАРДОВИЧ");
        userView.setPossition("ЭКОНОМИСТ");
        userView.setPhone("222");
        userView.setDocCode("21");
        userView.setDocName("Паспорт гражданина РФ");
        userView.setDocNumber("551234");
        userView.setDocDate(Date.valueOf("2007-02-15"));
        userView.setCitizenshipCode("643");
        userView.setCitizenshipName("Российская Федерация");
        userView.setIdentified(true);
        mockMvc.perform(
                post("/api/user/save")
                        .content(om.writeValueAsString(userView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        String id = getIdFromListFilterUser(userView);
        userView.setId(id);
        perfomGetUserById(userView);
    }

    public String getIdFromListFilterUser(UserView userView) throws Exception {
        UserFilterView userFilterView = new UserFilterView();
        userFilterView.setOfficeId(userView.getOfficeId());
        userFilterView.setFirstName(userView.getFirstName());
        userFilterView.setMiddleName(userView.getMiddleName());
        userFilterView.setSecondName(userView.getSecondName());
        userFilterView.setPossition(userView.getPossition());
        userFilterView.setDocCode(userView.getDocCode());
        userFilterView.setCitizenshipCode(userView.getCitizenshipCode());
        mockMvc.perform(
                post("/api/user/list")
                        .content(om.writeValueAsString(userFilterView))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is("13")))
                .andExpect(jsonPath("$.length()", is(1)));
        return "13";
    }

    public void perfomGetUserById(UserView userView) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/user/{id}", userView.getId())
                .accept(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userView.getId())))
                .andExpect(jsonPath("$.firstName", is(userView.getFirstName())))
                .andExpect(jsonPath("$.middleName", is(userView.getMiddleName())))
                .andExpect(jsonPath("$.secondName", is(userView.getSecondName())))
                .andExpect(jsonPath("$.possition", is(userView.getPossition())))
                .andExpect(jsonPath("$.phone", is(userView.getPhone())))
                .andExpect(jsonPath("$.docName", is(userView.getDocName())))
                .andExpect(jsonPath("$.docCode", is(userView.getDocCode())))
                .andExpect(jsonPath("$.docNumber", is(userView.getDocNumber())))
                .andExpect(jsonPath("$.docDate", is(userView.getDocDate().getTime())))
                .andExpect(jsonPath("$.citizenshipName", is(userView.getCitizenshipName())))
                .andExpect(jsonPath("$.citizenshipCode", is(userView.getCitizenshipCode())))
                .andExpect(jsonPath("$.isIdentified", is(userView.getIdentified())));
    }
}
