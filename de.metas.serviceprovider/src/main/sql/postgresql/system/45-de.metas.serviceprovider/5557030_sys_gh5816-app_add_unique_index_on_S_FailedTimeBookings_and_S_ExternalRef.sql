-- 2020-04-12T20:41:45.985Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540525,0,541486,TO_TIMESTAMP('2020-04-12 23:41:45','YYYY-MM-DD HH24:MI:SS'),100,'UNIQUE index on system,type and reference.','U','Y','Y','IDX_S_ExternalReference_ExternalSystem_Type_ExternalReferenc','N',TO_TIMESTAMP('2020-04-12 23:41:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:41:45.991Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540525 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-12T20:42:10.600Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570588,540995,540525,0,TO_TIMESTAMP('2020-04-12 23:42:10','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-12 23:42:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:42:31.666Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570585,540996,540525,0,TO_TIMESTAMP('2020-04-12 23:42:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',20,TO_TIMESTAMP('2020-04-12 23:42:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:43:06.309Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET EntityType='de.metas.serviceprovider',Updated=TO_TIMESTAMP('2020-04-12 23:43:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540525
;

-- 2020-04-12T20:44:10.473Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalReference_ExternalSystem_Type_ExternalReferenc ON S_ExternalReference (ExternalReference,ExternalSystem)
;

-- 2020-04-12T20:45:07.295Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570581,540997,540525,0,TO_TIMESTAMP('2020-04-12 23:45:07','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',30,TO_TIMESTAMP('2020-04-12 23:45:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:45:10.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS idx_s_externalreference_externalsystem_type_externalreferenc
;

-- 2020-04-12T20:45:10.170Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_ExternalReference_ExternalSystem_Type_ExternalReferenc ON S_ExternalReference (ExternalReference,ExternalSystem,Type)
;

-- 2020-04-12T20:46:40.474Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540526,0,541487,TO_TIMESTAMP('2020-04-12 23:46:40','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.serviceprovider','Y','Y','IDX_S_FailedTimeBooking_ExternalSystem_ExternalReference','N',TO_TIMESTAMP('2020-04-12 23:46:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:46:40.483Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540526 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-12T20:47:00.317Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570610,540998,540526,0,TO_TIMESTAMP('2020-04-12 23:47:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-12 23:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:47:16.742Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570608,540999,540526,0,TO_TIMESTAMP('2020-04-12 23:47:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',20,TO_TIMESTAMP('2020-04-12 23:47:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-12T20:47:26.289Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX IDX_S_FailedTimeBooking_ExternalSystem_ExternalReference ON S_FailedTimeBooking (ExternalSystem,ExternalId)
;

