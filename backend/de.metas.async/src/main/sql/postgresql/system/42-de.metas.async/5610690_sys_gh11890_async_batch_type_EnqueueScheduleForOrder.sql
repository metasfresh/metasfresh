
-- 2021-10-26T09:18:24.467Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO C_Async_Batch_Type (AD_Client_ID,AD_Org_ID,C_Async_Batch_Type_ID,Created,CreatedBy,InternalName,IsActive,NotificationType,SkipTimeoutMillis,Updated,UpdatedBy) VALUES (1000000,1000000,540019,TO_TIMESTAMP('2021-10-26 12:18:24','YYYY-MM-DD HH24:MI:SS'),100,'Order_Processing','Y','ABP',0,TO_TIMESTAMP('2021-10-26 12:18:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-10-28T11:26:23.320Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,542917,541374,TO_TIMESTAMP('2021-10-28 14:26:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.async','Y','Enqueue schedule for order',TO_TIMESTAMP('2021-10-28 14:26:23','YYYY-MM-DD HH24:MI:SS'),100,'EnqueueScheduleForOrder','Enqueue schedule for order')
;

-- 2021-10-28T11:26:23.323Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=542917 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2021-10-28T11:53:07.069Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE C_Async_Batch_Type SET InternalName='EnqueueScheduleForOrder',Updated=TO_TIMESTAMP('2021-10-28 14:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE C_Async_Batch_Type_ID=540019
;
