-- 2022-04-06T10:59:22.097Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541048,543154,TO_TIMESTAMP('2022-04-06 12:59:22','YYYY-MM-DD HH24:MI:SS'),100,'Prevent Baseversion rollout','U','Y','Prevent Baseversion rollout',TO_TIMESTAMP('2022-04-06 12:59:22','YYYY-MM-DD HH24:MI:SS'),100,'bv','Prevent Baseversion rollout')
;

-- 2022-04-06T10:59:22.099Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543154 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-04-06T11:08:06.710Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Kein Baseversion Rollout', Name='Kein Baseversion Rollout',Updated=TO_TIMESTAMP('2022-04-06 13:08:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543154
;

-- 2022-04-06T11:08:16.928Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Kein Baseversion Rollout', Name='Kein Baseversion Rollout',Updated=TO_TIMESTAMP('2022-04-06 13:08:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543154
;

-- 2022-04-06T11:08:54.245Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Kein Baseversion Rollout', Name='Kein Baseversion Rollout',Updated=TO_TIMESTAMP('2022-04-06 13:08:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543154
;

-- 2022-04-06T11:03:28.146Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ReadOnlyLogic='@Cloud_Instance_Rule_Type@=''bv''',Updated=TO_TIMESTAMP('2022-04-06 13:03:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541582
;
