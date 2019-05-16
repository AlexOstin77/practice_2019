package ru.bellintegrator.practice;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ru.bellintegrator.practice.service.impl.OrganizationServiceImpl;
import ru.bellintegrator.practice.view.OrganizationFilterView;
import ru.bellintegrator.practice.view.OrganizationView;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@WebAppConfiguration(value = "src/main/resources")
public class OrganizationServiceImplTest {

    private OrganizationView organizationViewGetById1 = new OrganizationView();
    private OrganizationView organizationViewAddNew = new OrganizationView();
    private OrganizationView organizationViewAddNewOther = new OrganizationView();
    private OrganizationView organizationViewUpdate = new OrganizationView();
    private OrganizationFilterView organizationFilterViewGetById1 = new OrganizationFilterView();
    private OrganizationFilterView organizationFilterViewGetById2 = new OrganizationFilterView();
    private OrganizationFilterView organizationFilterViewGetById3 = new OrganizationFilterView();
    private List organizationFilterListContainsOrganizationId1Id2 = new ArrayList<OrganizationFilterView>();
    private List organizationFilterListContainsOrganizationId1 = new ArrayList<OrganizationFilterView>();

    @Autowired
    public OrganizationServiceImpl service;

    @Before
    public void setUp() throws Exception {
        organizationViewGetById1.setId("1");
        organizationViewGetById1.setName("ООО СТАРТ");
        organizationViewGetById1.setFullName("ОБЩЕСТВО С ОГРАНИЧЕННОЙ ОТВЕСТВЕННОСТЬЮ СТАРТ");
        organizationViewGetById1.setInn("5820001122");
        organizationViewGetById1.setKpp("582001001");
        organizationViewGetById1.setAddress("ПЕНЗА УЛ.КРАСНАЯ Д. 1");
        organizationViewGetById1.setPhone("541234");
        organizationViewGetById1.setActive(true);

        organizationViewAddNew.setName("ЗАРЯ");
        organizationViewAddNew.setFullName("МУНИЦИПАЛЬНОЕ ПРЕДПРИЯТИЕ ЗАРЯ");
        organizationViewAddNew.setInn("5820007711");
        organizationViewAddNew.setKpp("582001001");
        organizationViewAddNew.setAddress("ПЕНЗА УЛ.ПРОФСОЮЗНАЯ Д. 77");
        organizationViewAddNew.setPhone("557711");
        organizationViewAddNew.setActive(true);

        organizationViewAddNewOther.setName("ДРУГАЯ ЗАРЯ");
        organizationViewAddNewOther.setFullName("МУНИЦИПАЛЬНОЕ ПРЕДПРИЯТИЕ ЗАРЯ");
        organizationViewAddNewOther.setInn("5820007700");
        organizationViewAddNewOther.setKpp("582001001");
        organizationViewAddNewOther.setAddress("ПЕНЗА УЛ.ПРОФСОЮЗНАЯ Д. 7");
        organizationViewAddNewOther.setPhone("557700");
        organizationViewAddNew.setActive(false);

        organizationViewUpdate.setId("3");
        organizationViewUpdate.setName("НПП РУБИН");
        organizationViewUpdate.setFullName("НАУЧНО – ПРОИЗВОДСТВЕННОЕ ОБЪЕДИНЕНИЕ РУБИН");
        organizationViewUpdate.setInn("5820008822");
        organizationViewUpdate.setKpp("582001001");
        organizationViewUpdate.setAddress("ПЕНЗА УЛ.ЗАВОДСКАЯ Д. 88");
        organizationViewUpdate.setPhone("558822");
        organizationViewUpdate.setActive(false);

        organizationFilterViewGetById1.setId("1");
        organizationFilterViewGetById1.setName("ООО СТАРТ");
        organizationFilterViewGetById1.setActive(true);

        organizationFilterViewGetById2.setId("2");
        organizationFilterViewGetById2.setName("ЗАО МИР");
        organizationFilterViewGetById2.setActive(true);

        organizationFilterViewGetById3.setId("3");
        organizationFilterViewGetById3.setName("КБ ИНТЕГРАЛ");
        organizationFilterViewGetById3.setActive(false);

        organizationFilterListContainsOrganizationId1.add(organizationFilterViewGetById1);

        organizationFilterListContainsOrganizationId1Id2.add(organizationFilterViewGetById1);
        organizationFilterListContainsOrganizationId1Id2.add(organizationFilterViewGetById2);
    }


    @Test
    public void filterOrganizationListNameWithLetterO() {
        OrganizationFilterView organizationFilterViewNameWithO = new OrganizationFilterView();
        organizationFilterViewNameWithO.setName("О");
        List organizationFilterView = service.filterOrganizationList(organizationFilterViewNameWithO);
        assertEquals(organizationFilterView, organizationFilterListContainsOrganizationId1Id2);
    }

    @Test
    public void filterOrganizationListNameWithLettersOO() {
        OrganizationFilterView organizationFilterViewNameWithOO = new OrganizationFilterView();
        organizationFilterViewNameWithOO.setName("ОО");
        organizationFilterViewNameWithOO.setInn("1122");
        organizationFilterViewNameWithOO.setActive(true);
        List organizationFilterView = service.filterOrganizationList(organizationFilterViewNameWithOO);
        assertEquals(organizationFilterView, organizationFilterListContainsOrganizationId1);
    }

    @Test
    public void filterOrganizationListNameWithLettersOONegative() {
        OrganizationFilterView organizationFilterViewNameWithOO = new OrganizationFilterView();
        organizationFilterViewNameWithOO.setName("ОО");
        List organizationFilterView = service.filterOrganizationList(organizationFilterViewNameWithOO);
        assertNotEquals(organizationFilterView, organizationFilterListContainsOrganizationId1Id2);
    }

    @Test
    public void getOrganizationById() {
        OrganizationView organization1 = service.getOrganizationById("1");
        assertEquals(organizationViewGetById1, organization1);
    }

    @Test
    public void getOrganizationByIdNegative() {
        OrganizationView organization2 = service.getOrganizationById("2");
        assertNotEquals(organizationViewGetById1, organization2);
    }

    @Test
    public void updateOrganization() {
        OrganizationView organizationNew = new OrganizationView();
        organizationNew.setId(organizationViewUpdate.getId());
        organizationNew.setName(organizationViewUpdate.getName());
        organizationNew.setFullName(organizationViewUpdate.getFullName());
        organizationNew.setInn(organizationViewUpdate.getInn());
        organizationNew.setKpp(organizationViewUpdate.getKpp());
        organizationNew.setAddress(organizationViewUpdate.getAddress());
        organizationNew.setActive(organizationViewUpdate.getActive());
        service.updateOrganization(organizationNew);
        OrganizationView organizationOneAfterUpdate = service.getOrganizationById("3");
        assertEquals(organizationNew, organizationOneAfterUpdate);
    }

    @Test
    public void updateOrganizationNameNegative() {
        OrganizationView organization3 = service.getOrganizationById("3");
        organization3.setName("НПП РУБИН");
        service.updateOrganization(organization3);
        OrganizationView organizationOneAfterUpdate = service.getOrganizationById("3");
        assertNotEquals("КБ ИНТЕГРАЛ", organizationOneAfterUpdate.getName());
    }

    @Test
    public void addOrganization() {
        service.add(organizationViewAddNew);
        OrganizationFilterView organizationFilterViewNew = new OrganizationFilterView();
        organizationFilterViewNew.setName(organizationViewAddNew.getName());
        organizationFilterViewNew.setInn(organizationViewAddNew.getInn());
        List<OrganizationFilterView> organizationFilterListNew = service.filterOrganizationList(organizationFilterViewNew);
        assertEquals(organizationViewAddNew.getName(),organizationFilterListNew.get(0).getName());

    }

    @Test
    public void addOrganizationOtherNegative() {
        service.add(organizationViewAddNewOther);
        OrganizationFilterView organizationFilterViewNewOther = new OrganizationFilterView();
        organizationFilterViewNewOther.setName(organizationViewAddNewOther.getName());
        organizationFilterViewNewOther.setInn(organizationViewAddNewOther.getInn());
        List<OrganizationFilterView> organizationFilterListNewOther = service.filterOrganizationList(organizationFilterViewNewOther);
        assertFalse(  organizationFilterListNewOther.size() == 0);
    }
}