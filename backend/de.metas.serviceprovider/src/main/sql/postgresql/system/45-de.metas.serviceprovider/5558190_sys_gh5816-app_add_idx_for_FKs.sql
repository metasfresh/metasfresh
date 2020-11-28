-- 2020-04-29T07:15:42.195Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540531,0,541467,TO_TIMESTAMP('2020-04-29 10:15:41','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','IDX_S_ExternalIssueDetail_S_Issue_ID','N',TO_TIMESTAMP('2020-04-29 10:15:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:15:42.200Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540531 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-29T07:15:55.835Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570213,541007,540531,0,TO_TIMESTAMP('2020-04-29 10:15:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-29 10:15:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:15:59.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_S_ExternalIssueDetail_S_Issue_ID ON S_ExternalIssueDetail (S_Issue_ID)
;

-- 2020-04-29T07:19:51.418Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540532,0,541443,TO_TIMESTAMP('2020-04-29 10:19:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','IDX_S_TimeBooking_S_Issue_ID','N',TO_TIMESTAMP('2020-04-29 10:19:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:19:51.419Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540532 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-29T07:20:37.209Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,570592,541008,540532,0,TO_TIMESTAMP('2020-04-29 10:20:37','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-29 10:20:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:20:40.569Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_S_TimeBooking_S_Issue_ID ON S_TimeBooking (S_Issue_ID)
;

-- 2020-04-29T07:21:12.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540533,0,541443,TO_TIMESTAMP('2020-04-29 10:21:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','IDX_S_TimeBooking_AD_Performing_User_ID','N',TO_TIMESTAMP('2020-04-29 10:21:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:21:12.560Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Index_Table_ID=540533 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2020-04-29T07:21:30.885Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,569672,541009,540533,0,TO_TIMESTAMP('2020-04-29 10:21:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y',10,TO_TIMESTAMP('2020-04-29 10:21:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-04-29T07:21:33.279Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX IDX_S_TimeBooking_AD_Performing_User_ID ON S_TimeBooking (AD_User_Performing_ID)
;

