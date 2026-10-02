package com.deliverytech.delivery_api.config.monitoring.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component("externalService")
public class ExternalServiceHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    public ExternalServiceHealthIndicator() {
        // Timeout de 2 segundos: se a internet cair, o check não fica travado
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(2000);
        this.restTemplate = new RestTemplate(factory);
    }

    @Override
    public Health health() {
        try {
            // Simula uma chamada para um serviço externo (gateway de pagamento)
            String url = "https://httpbin.org/status/200";
            restTemplate.getForObject(url, String.class);

            return Health.up()
                .withDetail("service", "Payment Gateway")
                .withDetail("url", url)
                .withDetail("status", "Disponível")
                .build();

        } catch (Exception e) {
            return Health.down()
                .withDetail("service", "Payment Gateway")
                .withDetail("error", e.getMessage())
                .withDetail("status", "Indisponível")
                .build();
        }
    }
}