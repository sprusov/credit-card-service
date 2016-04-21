import com.credit.card.Application;
import com.credit.card.controller.CreditCardController;
import com.credit.card.domain.CreditCard;
import com.credit.card.service.ProcessService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private MockMvc mockMvc;

    @Autowired
    private ProcessService processService;

    @Before
    public void setup() throws Exception {
        CreditCardController creditCardController = new CreditCardController();
        ReflectionTestUtils.setField(creditCardController, "processService", processService);
        mockMvc = MockMvcBuilders.standaloneSetup(creditCardController).build();
    }

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Test
    public void test1_creditCardServiceAddIsInvalid() throws Exception {
        mockMvc.perform(post("/api/v1/credit-card/add")
                .content(this.json(new CreditCard("Tom", "1234567890123456", 100)))
                .contentType(contentType))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void test2_creditCardServicerAdd() throws Exception {
        mockMvc.perform(post("/api/v1/credit-card/add")
                .content(this.json(new CreditCard("Tom", "4111111111111111", 1000)))
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
        mockMvc.perform(post("/api/v1/credit-card/add")
                .content(this.json(new CreditCard("Lisa", "5454545454545454", 3000)))
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
        mockMvc.perform(post("/api/v1/credit-card/add")
                .content(this.json(new CreditCard("Quincy", "1234567890123456", 2000)))
                .contentType(contentType))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void test9_creditCardServiceCredit() throws Exception {
        mockMvc.perform(put("/api/v1/credit-card/credit?name=Quincy&amount=$200")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    /**
     *
     * @param o
     * @return String
     * @throws IOException
     */
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}