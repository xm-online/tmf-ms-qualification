package com.icthh.xm.tmf.ms.qualification.lep.keyresolver;

import com.icthh.xm.lep.api.LepKeyResolver;
import com.icthh.xm.lep.api.LepMethod;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ProfileChannelKeyResolver implements LepKeyResolver {

    @Override
    public List<String> segments(LepMethod method) {
        return List.of(
            method.getParameter("profile", String.class),
            method.getParameter("channelId", String.class)
        );
    }
}
