-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Signed version
-- Column: ExternalSystem_Config_SAP.SignedVersion
-- 2023-03-01T04:58:18.481Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586266,712723,0,546671,TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Part of Azure service shared access signature. The storage service version to use to authorize and handle requests that you make with this shared access signature.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Signed version',TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T04:58:18.486Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712723 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T04:58:18.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582111) 
;

-- 2023-03-01T04:58:18.513Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712723
;

-- 2023-03-01T04:58:18.522Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712723)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Signed permissions
-- Column: ExternalSystem_Config_SAP.SignedPermissions
-- 2023-03-01T04:58:18.614Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586267,712724,0,546671,TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Part of Azure service shared access signature. The permissions that are associated with the shared access signature. The user is restricted to operations that are allowed by the permissions. You must omit this field if it has been specified in an associated stored access policy.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Signed permissions',TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T04:58:18.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712724 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T04:58:18.617Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582110) 
;

-- 2023-03-01T04:58:18.621Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712724
;

-- 2023-03-01T04:58:18.622Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712724)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Signature
-- Column: ExternalSystem_Config_SAP.SignatureSAS
-- 2023-03-01T04:58:18.711Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586268,712725,0,546671,TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100,'Azure service shared access signature.',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Signature',TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T04:58:18.712Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712725 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T04:58:18.714Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582112) 
;

-- 2023-03-01T04:58:18.717Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712725
;

-- 2023-03-01T04:58:18.718Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712725)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Base-URL
-- Column: ExternalSystem_Config_SAP.BaseURL
-- 2023-03-01T04:58:18.806Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586269,712726,0,546671,TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Base-URL',TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T04:58:18.807Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712726 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T04:58:18.808Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578731) 
;

-- 2023-03-01T04:58:18.820Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712726
;

-- 2023-03-01T04:58:18.821Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712726)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documnets_Path
-- 2023-03-01T04:58:18.915Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586270,712727,0,546671,TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Post accounting documents path',TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T04:58:18.916Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712727 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T04:58:18.918Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582113) 
;

-- 2023-03-01T04:58:18.920Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712727
;

-- 2023-03-01T04:58:18.921Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712727)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> API-Version
-- Column: ExternalSystem_Config_SAP.ApiVersion
-- 2023-03-01T04:58:19.010Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586271,712728,0,546671,TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','API-Version',TO_TIMESTAMP('2023-03-01 06:58:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T04:58:19.010Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712728 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T04:58:19.012Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582114) 
;

-- 2023-03-01T04:58:19.021Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712728
;

-- 2023-03-01T04:58:19.023Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712728)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10
-- UI Element Group: credentials
-- 2023-03-01T05:00:22.199Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546446,550413,TO_TIMESTAMP('2023-03-01 07:00:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','credentials',20,'',TO_TIMESTAMP('2023-03-01 07:00:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Base-URL
-- Column: ExternalSystem_Config_SAP.BaseURL
-- 2023-03-01T05:00:47.531Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712726,0,546671,615924,550413,'F',TO_TIMESTAMP('2023-03-01 07:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Base-URL',10,0,0,TO_TIMESTAMP('2023-03-01 07:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Post accounting documents path
-- Column: ExternalSystem_Config_SAP.Post_Acct_Documnets_Path
-- 2023-03-01T05:01:00.870Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712727,0,546671,615925,550413,'F',TO_TIMESTAMP('2023-03-01 07:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Post accounting documents path',20,0,0,TO_TIMESTAMP('2023-03-01 07:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.API-Version
-- Column: ExternalSystem_Config_SAP.ApiVersion
-- 2023-03-01T05:01:13.767Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712728,0,546671,615926,550413,'F',TO_TIMESTAMP('2023-03-01 07:01:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'API-Version',30,0,0,TO_TIMESTAMP('2023-03-01 07:01:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Signed permissions
-- Column: ExternalSystem_Config_SAP.SignedPermissions
-- 2023-03-01T05:01:25.930Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712724,0,546671,615927,550413,'F',TO_TIMESTAMP('2023-03-01 07:01:25','YYYY-MM-DD HH24:MI:SS'),100,'Part of Azure service shared access signature. The permissions that are associated with the shared access signature. The user is restricted to operations that are allowed by the permissions. You must omit this field if it has been specified in an associated stored access policy.','Y','N','N','Y','N','N','N',0,'Signed permissions',40,0,0,TO_TIMESTAMP('2023-03-01 07:01:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Signed version
-- Column: ExternalSystem_Config_SAP.SignedVersion
-- 2023-03-01T05:01:33.273Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712723,0,546671,615928,550413,'F',TO_TIMESTAMP('2023-03-01 07:01:33','YYYY-MM-DD HH24:MI:SS'),100,'Part of Azure service shared access signature. The storage service version to use to authorize and handle requests that you make with this shared access signature.','Y','N','N','Y','N','N','N',0,'Signed version',50,0,0,TO_TIMESTAMP('2023-03-01 07:01:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ExternalSystem_Config_SAP(546671,de.metas.externalsystem) -> main -> 10 -> credentials.Signature
-- Column: ExternalSystem_Config_SAP.SignatureSAS
-- 2023-03-01T05:01:41.822Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712725,0,546671,615929,550413,'F',TO_TIMESTAMP('2023-03-01 07:01:41','YYYY-MM-DD HH24:MI:SS'),100,'Azure service shared access signature.','Y','N','N','Y','N','N','N',0,'Signature',60,0,0,TO_TIMESTAMP('2023-03-01 07:01:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export
-- Table: ExternalSystem_Config_SAP_Acct_Export
-- 2023-03-01T05:03:30.617Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582101,0,546842,542316,541631,'N',TO_TIMESTAMP('2023-03-01 07:03:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_SAP_Acct_Export','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'SAP Config - accounting Export',584652,'N',70,1,TO_TIMESTAMP('2023-03-01 07:03:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:30.625Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546842 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-01T05:03:30.635Z
/* DDL */  select update_tab_translation_from_ad_element(582101) 
;

-- 2023-03-01T05:03:30.644Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546842)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export
-- Table: ExternalSystem_Config_SAP_Acct_Export
-- 2023-03-01T05:03:40.207Z
UPDATE AD_Tab SET AD_Column_ID=586227,Updated=TO_TIMESTAMP('2023-03-01 07:03:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546842
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> Client
-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Client_ID
-- 2023-03-01T05:03:54.193Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586219,712729,0,546842,TO_TIMESTAMP('2023-03-01 07:03:54','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'de.metas.externalsystem','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-03-01 07:03:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:54.199Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712729 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:54.212Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-03-01T05:03:55.512Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712729
;

-- 2023-03-01T05:03:55.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712729)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> Organisation
-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Org_ID
-- 2023-03-01T05:03:55.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586220,712730,0,546842,TO_TIMESTAMP('2023-03-01 07:03:55','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'de.metas.externalsystem','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-03-01 07:03:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:55.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712730 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:55.607Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-03-01T05:03:56.184Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712730
;

-- 2023-03-01T05:03:56.185Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712730)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> Active
-- Column: ExternalSystem_Config_SAP_Acct_Export.IsActive
-- 2023-03-01T05:03:56.276Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586223,712731,0,546842,TO_TIMESTAMP('2023-03-01 07:03:56','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'de.metas.externalsystem','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-03-01 07:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:56.278Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712731 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:56.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-03-01T05:03:56.881Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712731
;

-- 2023-03-01T05:03:56.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712731)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> SAP Config - accounting Export
-- Column: ExternalSystem_Config_SAP_Acct_Export.ExternalSystem_Config_SAP_Acct_Export_ID
-- 2023-03-01T05:03:56.977Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586226,712732,0,546842,TO_TIMESTAMP('2023-03-01 07:03:56','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','SAP Config - accounting Export',TO_TIMESTAMP('2023-03-01 07:03:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:56.980Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712732 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:56.984Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582101) 
;

-- 2023-03-01T05:03:56.997Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712732
;

-- 2023-03-01T05:03:56.998Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712732)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> ExternalSystem_Config_SAP
-- Column: ExternalSystem_Config_SAP_Acct_Export.ExternalSystem_Config_SAP_ID
-- 2023-03-01T05:03:57.091Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586227,712733,0,546842,TO_TIMESTAMP('2023-03-01 07:03:57','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_SAP',TO_TIMESTAMP('2023-03-01 07:03:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:57.094Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712733 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:57.096Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581532) 
;

-- 2023-03-01T05:03:57.101Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712733
;

-- 2023-03-01T05:03:57.101Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712733)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> Process
-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Process_ID
-- 2023-03-01T05:03:57.186Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586228,712734,0,546842,TO_TIMESTAMP('2023-03-01 07:03:57','YYYY-MM-DD HH24:MI:SS'),100,'Process or Report',10,'de.metas.externalsystem','The Process field identifies a unique Process or Report in the system.','Y','N','N','N','N','N','N','N','Process',TO_TIMESTAMP('2023-03-01 07:03:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:57.187Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712734 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:57.189Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(117) 
;

-- 2023-03-01T05:03:57.238Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712734
;

-- 2023-03-01T05:03:57.239Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712734)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> Document Type
-- Column: ExternalSystem_Config_SAP_Acct_Export.C_DocType_ID
-- 2023-03-01T05:03:57.329Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586229,712735,0,546842,TO_TIMESTAMP('2023-03-01 07:03:57','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules',10,'de.metas.externalsystem','The Document Type determines document sequence and processing rules','Y','N','N','N','N','N','N','N','Document Type',TO_TIMESTAMP('2023-03-01 07:03:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-01T05:03:57.331Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712735 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-01T05:03:57.335Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196) 
;

-- 2023-03-01T05:03:57.433Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712735
;

-- 2023-03-01T05:03:57.433Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712735)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem)
-- UI Section: main
-- 2023-03-01T05:05:20.670Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546842,545462,TO_TIMESTAMP('2023-03-01 07:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-01 07:05:20','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-03-01T05:05:20.676Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545462 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2023-03-01T05:05:20.862Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546671,545462,TO_TIMESTAMP('2023-03-01 07:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-01 07:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2023 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2023-03-01T05:05:20.963Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546671,550414,TO_TIMESTAMP('2023-03-01 07:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-03-01 07:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 10 -> default.SAP Config - accounting Export
-- Column: ExternalSystem_Config_SAP_Acct_Export.ExternalSystem_Config_SAP_Acct_Export_ID
-- 2023-03-01T05:06:06.594Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712732,0,546842,615930,550414,'F',TO_TIMESTAMP('2023-03-01 07:06:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'SAP Config - accounting Export',10,0,0,TO_TIMESTAMP('2023-03-01 07:06:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 10 -> default.Document Type
-- Column: ExternalSystem_Config_SAP_Acct_Export.C_DocType_ID
-- 2023-03-01T05:06:12.982Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712735,0,546842,615931,550414,'F',TO_TIMESTAMP('2023-03-01 07:06:12','YYYY-MM-DD HH24:MI:SS'),100,'Document type or rules','The Document Type determines document sequence and processing rules','Y','N','N','Y','N','N','N',0,'Document Type',20,0,0,TO_TIMESTAMP('2023-03-01 07:06:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 10 -> default.Process
-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Process_ID
-- 2023-03-01T05:06:24.068Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712734,0,546842,615932,550414,'F',TO_TIMESTAMP('2023-03-01 07:06:23','YYYY-MM-DD HH24:MI:SS'),100,'Process or Report','The Process field identifies a unique Process or Report in the system.','Y','N','N','Y','N','N','N',0,'Process',30,0,0,TO_TIMESTAMP('2023-03-01 07:06:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main
-- UI Column: 20
-- 2023-03-01T05:06:36.994Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546672,545462,TO_TIMESTAMP('2023-03-01 07:06:36','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-03-01 07:06:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 20
-- UI Element Group: flags
-- 2023-03-01T05:06:43.705Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546672,550415,TO_TIMESTAMP('2023-03-01 07:06:43','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-03-01 07:06:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 20
-- UI Element Group: org
-- 2023-03-01T05:06:50.957Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546672,550416,TO_TIMESTAMP('2023-03-01 07:06:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2023-03-01 07:06:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 20 -> flags.Active
-- Column: ExternalSystem_Config_SAP_Acct_Export.IsActive
-- 2023-03-01T05:07:10.314Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712731,0,546842,615933,550415,'F',TO_TIMESTAMP('2023-03-01 07:07:10','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2023-03-01 07:07:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 20 -> org.Organisation
-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Org_ID
-- 2023-03-01T05:07:29.090Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712730,0,546842,615934,550416,'F',TO_TIMESTAMP('2023-03-01 07:07:28','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-03-01 07:07:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> SAP Config - accounting Export(546842,de.metas.externalsystem) -> main -> 20 -> org.Client
-- Column: ExternalSystem_Config_SAP_Acct_Export.AD_Client_ID
-- 2023-03-01T05:07:35.284Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712729,0,546842,615935,550416,'F',TO_TIMESTAMP('2023-03-01 07:07:35','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2023-03-01 07:07:35','YYYY-MM-DD HH24:MI:SS'),100)
;

