-- Reference: GithubConfigNameList
-- Value: importTimeout
-- ValueName: Import Timeout
-- 2022-08-29T15:41:43.650Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543294,541633,TO_TIMESTAMP('2022-08-29 18:41:43','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Import Timeout',TO_TIMESTAMP('2022-08-29 18:41:43','YYYY-MM-DD HH24:MI:SS'),100,'importTimeout','Import Timeout')
;

-- 2022-08-29T15:41:43.665Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543294 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;


-- Reference: GithubConfigNameList
-- Value: importTimeout
-- ValueName: Import Timeout
-- 2022-08-30T09:13:07.013Z
UPDATE AD_Ref_List SET Name='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543294
;

-- Reference: GithubConfigNameList
-- Value: importTimeout
-- ValueName: Import timeout minutes
-- 2022-08-30T09:13:14.568Z
UPDATE AD_Ref_List SET ValueName='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543294
;

-- Reference: GithubConfigNameList
-- Value: importTimeoutMinutes
-- ValueName: Import timeout minutes
-- 2022-08-30T09:13:22.776Z
UPDATE AD_Ref_List SET Value='importTimeoutMinutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543294
;

-- Reference Item: GithubConfigNameList -> importTimeoutMinutes_Import timeout minutes
-- 2022-08-30T09:13:34.342Z
UPDATE AD_Ref_List_Trl SET Name='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543294
;

-- Reference Item: GithubConfigNameList -> importTimeoutMinutes_Import timeout minutes
-- 2022-08-30T09:13:37.045Z
UPDATE AD_Ref_List_Trl SET Name='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543294
;

-- Reference Item: GithubConfigNameList -> importTimeoutMinutes_Import timeout minutes
-- 2022-08-30T09:13:39.443Z
UPDATE AD_Ref_List_Trl SET Name='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543294
;

-- Reference Item: GithubConfigNameList -> importTimeoutMinutes_Import timeout minutes
-- 2022-08-30T09:13:42.112Z
UPDATE AD_Ref_List_Trl SET Name='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543294
;

-- Reference Item: GithubConfigNameList -> importTimeoutMinutes_Import timeout minutes
-- 2022-08-30T09:13:49.816Z
UPDATE AD_Ref_List_Trl SET Name='Import timeout minutes',Updated=TO_TIMESTAMP('2022-08-30 12:13:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Language='nl_NL' AND AD_Ref_List_ID=543294
;
