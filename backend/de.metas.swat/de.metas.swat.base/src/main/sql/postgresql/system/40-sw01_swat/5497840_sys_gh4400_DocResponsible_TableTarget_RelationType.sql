-- 2018-07-24T14:12:04.631
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsDirected,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540205,TO_TIMESTAMP('2018-07-24 14:12:04','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','Y','C_Doc_Responsible_TableRecordIdTarget',TO_TIMESTAMP('2018-07-24 14:12:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-24T14:12:25.982
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,540875,TO_TIMESTAMP('2018-07-24 14:12:25','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Doc_Responsible',TO_TIMESTAMP('2018-07-24 14:12:25','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2018-07-24T14:12:25.986
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Reference_ID=540875 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2018-07-24T14:13:37.342
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,Updated,UpdatedBy) VALUES (0,548509,0,540875,540470,540177,TO_TIMESTAMP('2018-07-24 14:13:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N',TO_TIMESTAMP('2018-07-24 14:13:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2018-07-24T14:13:59.480
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Target_ID=540875,Updated=TO_TIMESTAMP('2018-07-24 14:13:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540205
;

