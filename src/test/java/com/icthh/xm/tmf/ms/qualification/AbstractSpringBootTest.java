package com.icthh.xm.tmf.ms.qualification;

import com.icthh.xm.tmf.ms.qualification.config.SecurityBeanOverrideConfiguration;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Abstract test for extension for any SpringBoot test.
 *
 * This class prevents Spring test context refreshing between test runs as in case when each Test defines own
 * @SpringBootTest configuration. Marks test with junit5 @Tag
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {
    SecurityBeanOverrideConfiguration.class,
    QualificationApp.class
})
@Tag("com.icthh.xm.tmf.ms.qualification.AbstractSpringBootTest")
public abstract class AbstractSpringBootTest {

}
