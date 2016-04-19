import com.credit.card.Application;
import com.credit.card.controller.CreditCardController;
import com.credit.card.service.ProcessService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for CreditCardController rest
 *
 * @author Sergei Prusov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebIntegrationTest
@TestPropertySource(locations = "classpath:test.properties")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreditCardRestControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Autowired
    private ProcessService processService;

    @Before
    public void setup() throws Exception {
        CreditCardController creditCardController = new CreditCardController();
        ReflectionTestUtils.setField(creditCardController, "processService", processService);
        mockMvc = MockMvcBuilders.standaloneSetup(creditCardController).build();
    }

    @Test
    public void test1_creditCardServiceAddIsInvalid() throws Exception {
        mockMvc.perform(get("/api/v1/credit-card/add?name=Tom&number=1234567890123456&limit=$100")
                .contentType(contentType))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void test2_creditCardServicerAdd() throws Exception {
        mockMvc.perform(get("/api/v1/credit-card/add?name=Tom&number=4111111111111111&limit=$1000")
                .contentType(contentType))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(status().isCreated());
    }

    @Test
    public void test3_creditCardServiceCharge() throws Exception {
        mockMvc.perform(put("/api/v1/credit-card/charge?name=Tom&amount=$500")
                .contentType(contentType))
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.balance").value(500))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test4_creditCardServiceCharge() throws Exception {
        mockMvc.perform(put("/api/v1/credit-card/charge?name=Tom&amount=$800")
                .contentType(contentType))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void test5_creditCardServiceAdd() throws Exception {
        mockMvc.perform(get("/api/v1/credit-card/add?name=Lisa&number=5454545454545454&limit=$3000")
                .contentType(contentType))
                .andExpect(jsonPath("$.name").value("Lisa"))
                .andExpect(jsonPath("$.balance").value(0))
                .andExpect(status().isCreated());
    }

    @Test
    public void test6_creditCardServiceCharge() throws Exception {
        mockMvc.perform(put("/api/v1/credit-card/charge?name=Lisa&amount=$7")
                .contentType(contentType))
                .andExpect(jsonPath("$.name").value("Lisa"))
                .andExpect(jsonPath("$.balance").value(7))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test7_creditCardServiceCredit() throws Exception {
        mockMvc.perform(put("/api/v1/credit-card/credit?name=Lisa&amount=$100")
                .contentType(contentType))
                .andExpect(jsonPath("$.name").value("Lisa"))
                .andExpect(jsonPath("$.balance").value(-93))
                .andExpect(status().isAccepted());
    }

    @Test
    public void test8_creditCardServiceAdd() throws Exception {
        mockMvc.perform(get("/api/v1/credit-card/add?name=Quincy&number=1234567890123456&limit=$2000")
                .contentType(contentType))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void test9_creditCardServiceCredit() throws Exception {
        mockMvc.perform(put("/api/v1/credit-card/credit?name=Quincy&amount=$200")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }
}