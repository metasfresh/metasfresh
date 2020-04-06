-- 2020-02-25T16:18:22.509Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541108,TO_TIMESTAMP('2020-02-25 18:18:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','AD_User_Record_Access',TO_TIMESTAMP('2020-02-25 18:18:22','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2020-02-25T16:18:22.536Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Reference_ID=541108 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2020-02-25T16:19:08.817Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,564544,0,541108,541196,TO_TIMESTAMP('2020-02-25 18:19:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2020-02-25 18:19:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-25T16:20:46.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,541108,540239,TO_TIMESTAMP('2020-02-25 18:20:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','Y','AD_User_Record_Access_TableRecordIdTarget',TO_TIMESTAMP('2020-02-25 18:20:46','YYYY-MM-DD HH24:MI:SS'),100)
;

