
-- 2021-10-26T09:18:24.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Async_Batch_Type (AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,NotificationType,SkipTimeoutMillis,Updated,UpdatedBy) VALUES (1000000,1000000,540019,TO_TIMESTAMP('2021-10-26 12:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Order_Processing','Y','ABP',0,TO_TIMESTAMP('2021-10-26 12:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-26T10:02:31.337Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542914,541374,TO_TIMESTAMP('2021-10-26 13:02:31','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','Order creation',TO_TIMESTAMP('2021-10-26 13:02:31','YYYY-MM-DD HH24:MI:SS'),100,'Order creation','Order creation')
;

-- 2021-10-26T10:02:31.350Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542914 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-10-26T10:28:19.357Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_List SET Value='OrderCreation',Updated=TO_TIMESTAMP('2021-10-26 13:28:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=542914
;
