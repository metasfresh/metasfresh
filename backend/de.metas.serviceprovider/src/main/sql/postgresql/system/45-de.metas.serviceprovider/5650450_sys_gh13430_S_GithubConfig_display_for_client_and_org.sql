-- Table: S_GithubConfig
-- 2022-08-11T09:27:28.765Z
UPDATE AD_Table SET AccessLevel='3',Updated=TO_TIMESTAMP('2022-08-11 12:27:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541489
;

-- UI Element: Github config -> Github config.Organisation
-- Column: S_GithubConfig.AD_Org_ID
-- 2022-08-11T09:30:31.944Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,604491,0,542549,611514,543699,'F',TO_TIMESTAMP('2022-08-11 12:30:31','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',40,0,0,TO_TIMESTAMP('2022-08-11 12:30:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: GithubConfigNameList
-- 2022-08-11T09:33:09.692Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541633,TO_TIMESTAMP('2022-08-11 12:33:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','N','GithubConfigNameList',TO_TIMESTAMP('2022-08-11 12:33:09','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2022-08-11T09:33:09.700Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Reference_ID=541633 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: GithubConfigNameList
-- Value: accessToken
-- Name: Access token
-- 2022-08-11T09:33:32.493Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543277,541633,TO_TIMESTAMP('2022-08-11 12:33:32','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Access token',TO_TIMESTAMP('2022-08-11 12:33:32','YYYY-MM-DD HH24:MI:SS'),100,'accessToken','Access token')
;

-- 2022-08-11T09:33:32.509Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543277 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: GithubConfigNameList
-- Value: lookForParent
-- Name: Look for parent
-- 2022-08-11T09:34:06.878Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543278,541633,TO_TIMESTAMP('2022-08-11 12:34:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Look for parent',TO_TIMESTAMP('2022-08-11 12:34:06','YYYY-MM-DD HH24:MI:SS'),100,'lookForParent','Look for parent')
;

-- 2022-08-11T09:34:06.882Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543278 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: GithubConfigNameList
-- Value: githubSecret
-- Name: Github secret
-- 2022-08-11T09:34:25.639Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543279,541633,TO_TIMESTAMP('2022-08-11 12:34:25','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Github secret',TO_TIMESTAMP('2022-08-11 12:34:25','YYYY-MM-DD HH24:MI:SS'),100,'githubSecret','Github secret')
;

-- 2022-08-11T09:34:25.643Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543279 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: GithubConfigNameList
-- Value: githubUser
-- Name: Github user
-- 2022-08-11T09:34:42.304Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Ref_List_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,543280,541633,TO_TIMESTAMP('2022-08-11 12:34:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.serviceprovider','Y','Github user',TO_TIMESTAMP('2022-08-11 12:34:42','YYYY-MM-DD HH24:MI:SS'),100,'githubUser','Github user')
;

-- 2022-08-11T09:34:42.307Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Ref_List_ID=543280 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: S_GithubConfig.Name
-- 2022-08-11T09:35:11.046Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=541633, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2022-08-11 12:35:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=570649
;

