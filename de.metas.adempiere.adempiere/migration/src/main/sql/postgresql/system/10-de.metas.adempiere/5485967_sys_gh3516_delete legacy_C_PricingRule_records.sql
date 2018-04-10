delete from C_PricingRule
where C_PricingRule_ID IN (
	50000, -- org.adempiere.pricing.spi.impl.rules.PriceListVersionVB
	50002, -- org.adempiere.pricing.spi.impl.rules.PriceListVB
	50003, -- org.adempiere.pricing.spi.impl.rules.PriceList
	50004, -- org.adempiere.pricing.spi.impl.rules.BasePriceListVB
	50005 -- org.adempiere.pricing.spi.impl.rules.BasePriceList
);

