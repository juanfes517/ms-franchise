package com.franchise.infrastructure.config;

import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.spi.IFranchisePersistencePort;
import com.franchise.domain.usecase.FranchiseUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public IFranchiseServicePort franchiseServicePort(IFranchisePersistencePort franchisePersistencePort) {
        return new FranchiseUseCase(franchisePersistencePort);
    }
}
