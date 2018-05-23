package mis.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(mis.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(mis.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(mis.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(mis.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Country.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.State.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.City.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Profile.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.House.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Floor.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Flat.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Rent.class.getName(), jcacheConfiguration);
            cm.createCache(mis.domain.Billing.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
