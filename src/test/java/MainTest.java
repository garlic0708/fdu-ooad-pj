import application.SpringBootApplication;
import application.repository.DeviceRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringBootApplication.class})
public class MainTest {

    @Autowired
    DeviceRepository deviceRepository;

    @Test
    public void testXXX() {
        deviceRepository.findAll();
    }
}
