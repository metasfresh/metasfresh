-- 2019-02-21T13:56:08.651
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='(@PriceBase/P@ = P & @PricingSystemSurchargeAmt/0@ ! 0 & @Base_PricingSystem_ID/0@ ! 0) | @PriceBase/P@ = F',Updated=TO_TIMESTAMP('2019-02-21 13:56:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=560678
;

-- 2019-02-21T13:56:43.402
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@PriceBase/F@ = P',Updated=TO_TIMESTAMP('2019-02-21 13:56:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559655
;