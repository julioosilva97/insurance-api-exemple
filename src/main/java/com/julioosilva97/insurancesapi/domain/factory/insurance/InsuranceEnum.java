package com.julioosilva97.insurancesapi.domain.factory.insurance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.logging.log4j.util.Supplier;
@Getter
@AllArgsConstructor
public enum InsuranceEnum {

    BASIC(InsuranceBasicFactory::new),
    PARTIAL(InsurancePartialFactory::new),
    TOTAL(InsuranceTotalFactory::new);

    private final Supplier<InsuranceFactory> factory;

    public InsuranceFactory getFactory(){ return this.factory.get();};
}
