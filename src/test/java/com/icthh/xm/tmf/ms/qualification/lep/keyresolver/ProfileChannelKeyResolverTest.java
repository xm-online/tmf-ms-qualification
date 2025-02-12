package com.icthh.xm.tmf.ms.qualification.lep.keyresolver;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

import com.icthh.xm.lep.api.LepMethod;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ProfileChannelKeyResolverTest {


    @Spy
    ProfileChannelKeyResolver testedInstance;


    @Mock
    private LepMethod lepMethod;


    @Test
    public void testResolveLepKeyByProfileAndChannelId() throws Throwable {

        when(lepMethod.getParameter("profile", String.class)).thenReturn("PromotionQualificationFind");
        when(lepMethod.getParameter("channelId", String.class)).thenReturn("CRM");

        List<String> actualSegments = testedInstance.segments(lepMethod);

        assertThat(actualSegments).hasSize(2);
        assertThat(actualSegments).contains("PromotionQualificationFind","CRM");
    }
}
