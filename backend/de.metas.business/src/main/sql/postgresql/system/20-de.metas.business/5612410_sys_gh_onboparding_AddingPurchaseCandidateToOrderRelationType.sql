-- 2021-11-05T16:35:56.168Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_RelationType (AD_Client_ID,AD_Org_ID,AD_Reference_Source_ID,AD_Reference_Target_ID,AD_RelationType_ID,Created,CreatedBy,EntityType,IsActive,IsTableRecordIdTarget,Name,Updated,UpdatedBy) VALUES (0,0,540250,540653,540325,TO_TIMESTAMP('2021-11-05 17:35:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Y','N','C_Order  -> PMM_PurchaseCandidate ',TO_TIMESTAMP('2021-11-05 17:35:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T16:38:17.162Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541482,TO_TIMESTAMP('2021-11-05 17:38:16','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','PMM_PurchaseCandidate',TO_TIMESTAMP('2021-11-05 17:38:16','YYYY-MM-DD HH24:MI:SS'),100,'T')
;

-- 2021-11-05T16:38:17.165Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541482 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- 2021-11-05T16:39:32.111Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_Table (AD_Client_ID,AD_Key,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsValueDisplayed,ShowInactiveValues,Updated,UpdatedBy) VALUES (0,554109,0,541482,540745,540285,TO_TIMESTAMP('2021-11-05 17:39:32','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N',TO_TIMESTAMP('2021-11-05 17:39:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-11-05T16:39:57.391Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET AD_Reference_Source_ID=541482,Updated=TO_TIMESTAMP('2021-11-05 17:39:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540152
;

-- 2021-11-05T16:40:14.784Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET EntityType='D',Updated=TO_TIMESTAMP('2021-11-05 17:40:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540325
;

