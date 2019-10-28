package com.icthh.xm.tmf.ms.qualification.lep.keyresolver;

import com.icthh.xm.commons.lep.AppendLepKeyResolver;
import com.icthh.xm.lep.api.LepManagerService;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.lep.api.commons.SeparatorSegmentedLepKey;
import org.springframework.stereotype.Component;

@Component
public class ProfileChannelKeyResolver extends AppendLepKeyResolver {

    @Override
    protected String[] getAppendSegments(SeparatorSegmentedLepKey baseKey,
                                         LepMethod method,
                                         LepManagerService managerService) {
        String profile = getRequiredParam(method, "profile", String.class);
        String translatedProfile = translateToLepConvention(profile);
        String channel = getRequiredParam(method, "channelId", String.class);
        String translatedChannel = translateToLepConvention(channel);
        return new String[]{translatedProfile, translatedChannel};
    }
}
