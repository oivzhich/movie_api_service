package config;

import org.aeonbits.owner.Config;

public interface PropertiesConfiguration extends Config {
    @DefaultValue("18.208.18.36:8081")
    String env();

    @DefaultValue("olga")
    String username();

    @DefaultValue("oi1234")
    String password();
}
