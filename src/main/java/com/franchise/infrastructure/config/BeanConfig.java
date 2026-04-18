package com.franchise.infrastructure.config;

import com.franchise.domain.api.IBranchServicePort;
import com.franchise.domain.api.IFranchiseServicePort;
import com.franchise.domain.api.IProductServicePort;
import com.franchise.domain.spi.IBranchPersistencePort;
import com.franchise.domain.spi.IFranchisePersistencePort;
import com.franchise.domain.spi.IProductPersistencePort;
import com.franchise.domain.usecase.BranchUseCase;
import com.franchise.domain.usecase.FranchiseUseCase;
import com.franchise.domain.usecase.ProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public IFranchiseServicePort franchiseServicePort(IFranchisePersistencePort franchisePersistencePort) {
        return new FranchiseUseCase(franchisePersistencePort);
    }

    @Bean
    public IBranchServicePort  branchServicePort(IBranchPersistencePort branchPersistencePort) {
        return new BranchUseCase(branchPersistencePort);
    }

    @Bean
    public IProductServicePort productServicePort(IProductPersistencePort productPersistencePort) {
        return new ProductUseCase(productPersistencePort);
    }
}
