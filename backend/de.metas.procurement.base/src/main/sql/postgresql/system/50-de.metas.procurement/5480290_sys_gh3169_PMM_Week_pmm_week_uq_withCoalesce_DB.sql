
-- 2017-12-11T13:12:02.312
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS pmm_week_uq
;

-- 2017-12-11T13:12:02.336
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX pmm_week_uq ON PMM_Week (C_BPartner_ID,M_Product_ID,WeekDate,COALESCE(M_AttributeSetInstance_ID, 0)) WHERE IsActive = 'Y'
;

