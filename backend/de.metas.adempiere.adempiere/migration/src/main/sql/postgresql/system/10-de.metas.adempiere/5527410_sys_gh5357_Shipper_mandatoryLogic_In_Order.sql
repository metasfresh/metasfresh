-- 2019-07-16T15:09:09.390Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@DeliveryViaRule@<>',Updated=TO_TIMESTAMP('2019-07-16 18:09:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2197
;

-- 2019-07-16T15:10:54.811Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@DeliveryViaRule@! ''P'' ',Updated=TO_TIMESTAMP('2019-07-16 18:10:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2197
;

-- 2019-07-16T15:12:01.831Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET MandatoryLogic='@DeliveryViaRule@ ! ''P'' & @FreightCostRule@ ! ''I''',Updated=TO_TIMESTAMP('2019-07-16 18:12:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2197
;

