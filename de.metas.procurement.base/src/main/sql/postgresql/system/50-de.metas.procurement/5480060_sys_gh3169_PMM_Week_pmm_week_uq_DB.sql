DROP INDEX IF EXISTS public.pmm_week_uq;



-- 2017-12-08T17:22:55.751
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX pmm_week_uq ON PMM_Week (C_BPartner_ID,M_Product_ID,WeekDate,M_AttributeSetInstance_ID) WHERE IsActive = 'Y'
;

