-- 2020-10-01T15:34:12.766Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540554,0,500221,TO_TIMESTAMP('2020-10-01 18:34:12','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','IDX_M_ShipmentSchedule_PoReference','N',TO_TIMESTAMP('2020-10-01 18:34:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T15:34:12.772Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540554 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-10-01T15:34:25.689Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,552464,541043,540554,0,TO_TIMESTAMP('2020-10-01 18:34:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2020-10-01 18:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T15:34:28.546Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_M_ShipmentSchedule_PoReference ON M_ShipmentSchedule (POReference)
;

-- 2020-10-01T15:35:36.196Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540555,0,540244,TO_TIMESTAMP('2020-10-01 18:35:36','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','IDX_C_OLCand_PoReference','N',TO_TIMESTAMP('2020-10-01 18:35:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T15:35:36.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540555 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-10-01T15:35:49.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,549849,541044,540555,0,TO_TIMESTAMP('2020-10-01 18:35:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2020-10-01 18:35:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-10-01T15:35:52.688Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_C_OLCand_PoReference ON C_OLCand (POReference)
;

