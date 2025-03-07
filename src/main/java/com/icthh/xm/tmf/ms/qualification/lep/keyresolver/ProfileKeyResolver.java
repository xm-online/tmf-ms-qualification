package com.icthh.xm.tmf.ms.qualification.lep.keyresolver;

import com.icthh.xm.lep.api.LepKeyResolver;
import com.icthh.xm.lep.api.LepMethod;
import com.icthh.xm.tmf.ms.qualification.web.rest.util.HeaderRequestExtractor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileKeyResolver implements LepKeyResolver {

    private final HeaderRequestExtractor headerRequestExtractor;

    @Override
    public List<String> segments(LepMethod method) {
        return List.of(
            headerRequestExtractor.get("profile")
        );
    }
}
