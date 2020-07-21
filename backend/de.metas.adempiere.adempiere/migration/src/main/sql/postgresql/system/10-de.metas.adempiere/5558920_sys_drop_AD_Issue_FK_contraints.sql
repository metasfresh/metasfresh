
-- 2020-05-08T15:05:39.610Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2020-05-08 17:05:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=559010
;
ALTER TABLE public.c_purchasecandidate_alloc DROP CONSTRAINT IF EXISTS adissue_cpurchasecandidatealloc;

-- 2020-05-08T15:06:35.595Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2020-05-08 17:06:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=542081
;
ALTER TABLE public.c_advcommissionfactcand DROP CONSTRAINT IF EXISTS adissue_cadvcommissionfactcand;

-- 2020-05-08T15:07:12.381Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2020-05-08 17:07:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558853
;
ALTER TABLE public.msv3_bestellung_transaction DROP CONSTRAINT IF EXISTS adissue_msv3bestellungtransaction;

-- 2020-05-08T15:08:47.774Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2020-05-08 17:08:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=569574
;
ALTER TABLE public.dpd_storeorder_log DROP CONSTRAINT IF EXISTS adissue_dpdstoreorderlog;

-- 2020-05-08T15:10:17.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET DDL_NoForeignKey='Y',Updated=TO_TIMESTAMP('2020-05-08 17:10:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558939
;
ALTER TABLE public.msv3_verfuegbarkeit_transaction DROP CONSTRAINT IF EXISTS adissue_msv3verfuegbarkeittransaction; -- was already set in AD_Column
