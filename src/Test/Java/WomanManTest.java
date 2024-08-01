import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;


public class WomanManTest {
    private Man man;
    private Woman woman;

    @BeforeMethod
    public void setUp() {
        man = new Man("John", "Doe", 66);
        woman = new Woman("Jane", "Roe", 30);
    }

    @DataProvider(name = "isRetired")
    public Object[][] testDataRetired(){
        return new Object[][]{
                {"Johny", "Doe", 66}
        };
    }

    @DataProvider(name = "notRetired")
    public Object[][] testDataNotRetired(){
        return new Object[][]{
                {"Johny", "Doe", 19}
        };
    }

    @DataProvider(name = "manData")
    public Object[][] testData2() {
        return new Object[][]{
                {"Rives"}
        };
    }

    @DataProvider(name = "womanData")
    public Object[][] testData3() {
        return new Object[][]{
                {"Carrie-Anne"}
        };
    }

    @DataProvider(name = "csvData")
    public Object[][] csvTestData() throws IOException, CsvException {
        List<String[]> data;
        String csvFilePath = "src/test/resources/data/data.csv";
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            data = reader.readAll();
        }
        Object[][] result = new Object[data.size()][3];
        for (int i = 0; i < data.size(); i++) {
            String[] row = data.get(i);
            result[i][0] = row[0];
            result[i][1] = row[1];
            result[i][2] = Integer.parseInt(row[2]); // Преобразование возраста в int
        }
        return result;
    }

    @Test(dataProvider = "isRetired", groups = {"all"})
    public void testIsRetiredTrue(String firstName, String lastName, int age) {
        Man man = new Man(firstName, lastName, age);
        Assert.assertTrue(man.isRetired(), "Man should be retired when age is over 65");
    }

    @Test(dataProvider = "notRetired",groups = {"all"})
    public void testIsRetiredFalse(String firstName, String lastName, int age) {
        Man youngMan = new Man(firstName, lastName, age);
        Assert.assertFalse(youngMan.isRetired(), "Man should not be retired when age is 65 or less");
    }

    @Test(groups = {"all"})
    public void testRegisterPartnership() {
        man.registerPartnership(woman);
        Assert.assertEquals(woman.getLastName(), "Doe", "Woman should take the last name of the man after partnership registration");
        Assert.assertEquals(woman.getPartner(), man, "Woman's partner should be set to man after partnership registration");
    }

    @Test(groups = {"all"})
    public void testDeregisterPartnershipWithoutRevertingLastName() {
        man.registerPartnership(woman);
        man.deregisterPartnership(false);
        Assert.assertEquals(woman.getLastName(), "Doe", "Woman should keep the man's last name after partnership deregistration without revert");
        Assert.assertNull(woman.getPartner(), "Woman's partner should be null after partnership deregistration");
    }

    @Test(groups = {"all"})
    public void testDeregisterPartnershipWithRevertingLastName() {
        man.registerPartnership(woman);
        man.deregisterPartnership(true);
        Assert.assertEquals(woman.getLastName(), "Roe", "Woman should revert to her original last name after partnership deregistration with revert");
        Assert.assertNull(woman.getPartner(), "Woman's partner should be null after partnership deregistration");
    }

    @Test(groups = {"all", "gettersAndSetters"})
    public void testGetLastName() {
        Assert.assertEquals(woman.getLastName(), "Roe", "The last name should be 'Roe'");
    }

    @Test(dataProvider = "manData", groups = {"all", "gettersAndSetters"})
    public void testSetLastName(String newLastName) {
        man.setLastName(newLastName);
        Assert.assertEquals(man.getLastName(), newLastName, "The last name should be 'Rives'");
    }

    @Test(groups = {"all", "gettersAndSetters"})
    public void testGetFirstName() {
        Assert.assertEquals(man.getFirstName(), "John", "The first name should be 'John'");
    }

    @Test(dataProvider = "womanData", groups = {"all", "gettersAndSetters"})
    public void testSetFirstName(String newFirstName) {
        woman.setFirstName(newFirstName);
        Assert.assertEquals(woman.getFirstName(), newFirstName, "The first name should be 'Carrie-Anne'");
    }

    @Test(groups = {"all", "gettersAndSetters"})
    public void testGetAge() {
        Assert.assertEquals(man.getAge(), 66, "The age should be 66");
    }

    @Test(dataProvider = "csvData", groups = {"all", "gettersAndSetters"})
    public void testSetAge(String firstName, String lastName, int age) {
        Woman woman = new Woman(firstName, lastName, age);
        woman.setAge(age);
        Assert.assertEquals(woman.getAge(), age, "The age should be " + age);
    }

    @Test(groups = {"all", "gettersAndSetters"})
    public void testSetGetPartner() {
        woman.setPartner(man);
        Assert.assertEquals(woman.getPartner(), man, "The partner should be set and retrieved correctly for Woman");
    }
}