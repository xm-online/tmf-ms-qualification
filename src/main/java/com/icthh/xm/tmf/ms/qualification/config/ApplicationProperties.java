package com.icthh.xm.tmf.ms.qualification.config;

import com.icthh.xm.commons.lep.TenantScriptStorage;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Qualification.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private boolean kafkaEnabled;
    private String kafkaSystemTopic;
    private String kafkaSystemQueue;
    private final Lep lep = new Lep();
    private List<String> tenantIgnoredPathList = Collections.emptyList();
    private boolean timelinesEnabled;
    private final Retry retry = new Retry();
    private RestTemplateProperties loadBalancedRestTemplate;
    private String configPrivateKey;
    private final ApiDocs apiDocs = new ApiDocs();
    private final Prometheus prometheus = new Prometheus();
    private final Management management = new Management();
    private final TenantCache tenantCache = new TenantCache();
    private final TenantMemoryCache tenantMemoryCache = new TenantMemoryCache();
    private final XmAutoConfigurationFilter xmAutoConfigurationFilter = new XmAutoConfigurationFilter();

    @Getter
    @Setter
    public static class Lep {
        private TenantScriptStorage tenantScriptStorage;
        private String lepResourcePathPattern;
    }

    @Data
    private static class Retry {
        private int maxAttempts;
        private long delay;
        private int multiplier;
    }

    @Getter
    @Setter
    public static class RestTemplateProperties {
        private Integer connectionRequestTimeout;
        private Integer connectTimeout;
        private Integer readTimeout;
    }

    @Data
    public static class ApiDocs {
        private boolean privilegeCheck;
    }

    public static class Prometheus {
        private boolean publicAccess;

        public boolean isPublic() {
            return publicAccess;
        }

        public void setPublic(boolean publicAccess) {
            this.publicAccess = publicAccess;
        }
    }

    @Data
    public static class TenantCache {
        private String enabled;
    }

    @Data
    public static class TenantMemoryCache {
        private String enabled;
    }

    @Data
    public static class XmAutoConfigurationFilter {
        private boolean disabled;
    }

    @Data
    public static class Management {
        private final Access access = new Access();
        private final Info info = new Info();
        private final Logs logs = new Logs();

        @Data
        public static class Access {
            private String role;
        }

        public static class Info {
            private boolean publicAccess;

            public boolean isPublic() {
                return publicAccess;
            }

            public void setPublic(boolean publicAccess) {
                this.publicAccess = publicAccess;
            }
        }

        @Data
        public static class Logs {
            private boolean systemAdminOnly;
        }
    }
}
