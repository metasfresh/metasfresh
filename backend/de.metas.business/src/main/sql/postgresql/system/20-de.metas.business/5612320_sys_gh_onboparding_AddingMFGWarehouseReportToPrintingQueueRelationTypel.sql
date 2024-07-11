-- 2021-11-05T13:00:10.274Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540590,540589,540321,TO_TIMESTAMP('2021-11-05 14:00:09','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_MFGWarehouse_Report ->C_Printing_Queue ',TO_TIMESTAMP('2021-11-05 14:00:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T14:19:22.752Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541476,TO_TIMESTAMP('2021-11-05 15:19:20','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','C_Order_MFGWarehouse_Report',TO_TIMESTAMP('2021-11-05 15:19:20','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T14:19:22.757Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541476 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T14:21:28.537Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,552733,0,541476,540683,540274,TO_TIMESTAMP('2021-11-05 15:21:28','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 15:21:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T14:21:55.247Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541476,Updated=TO_TIMESTAMP('2021-11-05 15:21:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540321
;

-- 2021-11-05T14:31:05.117Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=540878,Updated=TO_TIMESTAMP('2021-11-05 15:31:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540132
;


