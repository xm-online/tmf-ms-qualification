package com.icthh.xm.tmf.ms.qualification.lep.keyresolver;

import com.icthh.xm.commons.lep.XmLepConstants;
import com.icthh.xm.commons.lep.spring.LepServiceHandler;
import com.icthh.xm.lep.api.*;
import com.icthh.xm.lep.core.CoreLepManager;
import com.icthh.xm.tmf.ms.qualification.web.rest.PromotionQualificationDelegate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProfileChannelKeyResolverTest {

    @InjectMocks
    private LepServiceHandler lepServiceHandler;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private CoreLepManager lepManager;

    @Captor
    private ArgumentCaptor<LepKey> baseLepKey;

    @Captor
    private ArgumentCaptor<LepKeyResolver> keyResolver;

    @Captor
    private ArgumentCaptor<LepMethod> lepMethod;

    @Captor
    private ArgumentCaptor<Version> version;

    @Test
    public void testResolveLepKeyByProfileAndChannelId() throws Throwable {

        Method method = PromotionQualificationDelegate.class.getMethod("promotionQualificationFind", String.class,
            String.class, String.class);

        when(applicationContext.getBean(LepManager.class)).thenReturn(lepManager);

        ProfileChannelKeyResolver resolver = new ProfileChannelKeyResolver();
        when(applicationContext.getBean(ProfileChannelKeyResolver.class)).thenReturn(resolver);

        lepServiceHandler.onMethodInvoke(PromotionQualificationDelegate.class,
            new PromotionQualificationDelegate(), method, new Object[]{"RTM", "relatedPartyId_12345", "CRM"});

        verify(lepManager)
            .processLep(baseLepKey.capture(), version.capture(), keyResolver.capture(), lepMethod.capture());

        LepKey resolvedKey = resolver.resolve(baseLepKey.getValue(), lepMethod.getValue(), null);

        assertEquals(
            String.join(XmLepConstants.EXTENSION_KEY_SEPARATOR,
                "service", "PromotionQualificationFind", "RTM", "CRM"), resolvedKey.getId());
    }
}
