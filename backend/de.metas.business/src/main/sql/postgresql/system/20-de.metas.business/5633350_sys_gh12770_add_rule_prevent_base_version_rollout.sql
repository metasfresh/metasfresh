-- 2022-04-05T10:20:10.887Z
-- URL zum Konzept
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541048,543153,TO_TIMESTAMP('2022-04-05 12:20:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Prevent Baseversion rollout',TO_TIMESTAMP('2022-04-05 12:20:10','YYYY-MM-DD HH24:MI:SS'),100,'bv','Prevent Baseversion rollout')
;

-- 2022-04-05T10:20:10.892Z
-- URL zum Konzept
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543153 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- 2022-04-05T18:55:40.489Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Kein Baseversion Rollout', Name='Kein Baseversion Rollout',Updated=TO_TIMESTAMP('2022-04-05 20:55:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543153
;

-- 2022-04-05T18:55:54.557Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Kein Baseversion Rollout', Name='Kein Baseversion Rollout',Updated=TO_TIMESTAMP('2022-04-05 20:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543153
;

-- 2022-04-05T18:56:04.143Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Kein Baseversion Rollout', Name='Kein Baseversion Rollout',Updated=TO_TIMESTAMP('2022-04-05 20:56:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543153
;

-- 2022-04-05T18:56:15.844Z
-- URL zum Konzept
UPDATE AD_Ref_List_Trl SET Description='Prevent Baseversion rollout',Updated=TO_TIMESTAMP('2022-04-05 20:56:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543153
;

-- 2022-04-05T10:24:01.323Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ReadOnlyLogic='@Cloud_Instance_Rule_Type@=''bv''',Updated=TO_TIMESTAMP('2022-04-05 12:24:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541582
;
