-- 2021-01-21T16:24:03.138Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2021-01-21 18:24:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53628
;

-- 2021-01-21T16:26:51.863Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540528,'C_DocType.DocBaseType IN  (''MOP'',''MOF'',''MQO'', ''MRO'') ',TO_TIMESTAMP('2021-01-21 18:26:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','C_DocType MFG','S',TO_TIMESTAMP('2021-01-21 18:26:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-01-21T16:27:32.172Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=170, AD_Val_Rule_ID=540528,Updated=TO_TIMESTAMP('2021-01-21 18:27:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53628
;

-- 2021-01-21T16:27:45.210Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=170, AD_Val_Rule_ID=540528,Updated=TO_TIMESTAMP('2021-01-21 18:27:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53629
;

-- 2021-01-21T16:27:53.609Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=53233
;

-- 2021-01-21T16:27:53.642Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=53233
;

-- 2021-01-21T16:57:41.857Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='',Updated=TO_TIMESTAMP('2021-01-21 18:57:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=170
;

