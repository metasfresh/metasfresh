-- 2023-07-27T08:32:51.561Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582575,0,TO_TIMESTAMP('2023-07-27 11:32:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','Import Bank Account','Import Bank Account',TO_TIMESTAMP('2023-07-27 11:32:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:32:51.569Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582575 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Import Bank Account, InternalName=null
-- 2023-07-27T08:35:14.234Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582575,0,541720,TO_TIMESTAMP('2023-07-27 11:35:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','Y','N','N','Y','N','N','N','N','Import Bank Account','N',TO_TIMESTAMP('2023-07-27 11:35:14','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-07-27T08:35:14.242Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541720 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-07-27T08:35:14.258Z
/* DDL */  select update_window_translation_from_ad_element(582575) 
;

-- 2023-07-27T08:35:14.293Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541720
;

-- 2023-07-27T08:35:14.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541720)
;

-- Tab: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account
-- Table: I_BP_BankAccount
-- 2023-07-27T08:47:30.991Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582573,0,547049,542355,541720,'Y',TO_TIMESTAMP('2023-07-27 11:47:30','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','N','N','A','I_BP_BankAccount','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Import BPartner Bank Account','N',10,0,TO_TIMESTAMP('2023-07-27 11:47:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:31.001Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547049 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-07-27T08:47:31.014Z
/* DDL */  select update_tab_translation_from_ad_element(582573) 
;

-- 2023-07-27T08:47:31.027Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547049)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Client
-- Column: I_BP_BankAccount.AD_Client_ID
-- 2023-07-27T08:47:36.763Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587174,717252,0,547049,TO_TIMESTAMP('2023-07-27 11:47:36','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.',10,'de.metas.banking','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','N','N','N','Y','N','Client',TO_TIMESTAMP('2023-07-27 11:47:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:36.775Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717252 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:36.792Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-07-27T08:47:38.762Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717252
;

-- 2023-07-27T08:47:38.763Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717252)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Issues
-- Column: I_BP_BankAccount.AD_Issue_ID
-- 2023-07-27T08:47:38.862Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587175,717253,0,547049,TO_TIMESTAMP('2023-07-27 11:47:38','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.banking','','Y','N','N','N','N','N','N','N','Issues',TO_TIMESTAMP('2023-07-27 11:47:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:38.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717253 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:38.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2023-07-27T08:47:38.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717253
;

-- 2023-07-27T08:47:38.938Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717253)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Organisation
-- Column: I_BP_BankAccount.AD_Org_ID
-- 2023-07-27T08:47:39.034Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587176,717254,0,547049,TO_TIMESTAMP('2023-07-27 11:47:38','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client',10,'de.metas.banking','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2023-07-27 11:47:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:39.037Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717254 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:39.043Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-07-27T08:47:39.839Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717254
;

-- 2023-07-27T08:47:39.839Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717254)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Data import
-- Column: I_BP_BankAccount.C_DataImport_ID
-- 2023-07-27T08:47:39.929Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587177,717255,0,547049,TO_TIMESTAMP('2023-07-27 11:47:39','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','Data import',TO_TIMESTAMP('2023-07-27 11:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:39.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717255 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:39.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2023-07-27T08:47:39.970Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717255
;

-- 2023-07-27T08:47:39.972Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717255)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Data Import Run
-- Column: I_BP_BankAccount.C_DataImport_Run_ID
-- 2023-07-27T08:47:40.072Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587178,717256,0,547049,TO_TIMESTAMP('2023-07-27 11:47:39','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2023-07-27 11:47:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:40.075Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717256 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:40.078Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114) 
;

-- 2023-07-27T08:47:40.105Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717256
;

-- 2023-07-27T08:47:40.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717256)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Import Error Message
-- Column: I_BP_BankAccount.I_ErrorMsg
-- 2023-07-27T08:47:40.204Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587181,717257,0,547049,TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100,'Messages generated from import process',2000,'de.metas.banking','The Import Error Message displays any error messages generated during the import process.','Y','N','N','N','N','N','N','N','Import Error Message',TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:40.207Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717257 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:40.211Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(912) 
;

-- 2023-07-27T08:47:40.289Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717257
;

-- 2023-07-27T08:47:40.290Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717257)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Imported
-- Column: I_BP_BankAccount.I_IsImported
-- 2023-07-27T08:47:40.383Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587182,717258,0,547049,TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100,'Has this import been processed',1,'de.metas.banking','The Imported check box indicates if this import has been processed.','Y','N','N','N','N','N','N','N','Imported',TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:40.386Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717258 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:40.389Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(913) 
;

-- 2023-07-27T08:47:40.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717258
;

-- 2023-07-27T08:47:40.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717258)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Import Line Content
-- Column: I_BP_BankAccount.I_LineContent
-- 2023-07-27T08:47:40.513Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587183,717259,0,547049,TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100,4000,'de.metas.banking','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:40.516Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717259 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:40.520Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115) 
;

-- 2023-07-27T08:47:40.542Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717259
;

-- 2023-07-27T08:47:40.544Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717259)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Import Line No
-- Column: I_BP_BankAccount.I_LineNo
-- 2023-07-27T08:47:40.661Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587184,717260,0,547049,TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:40.664Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717260 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:40.669Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116) 
;

-- 2023-07-27T08:47:40.689Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717260
;

-- 2023-07-27T08:47:40.690Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717260)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Active
-- Column: I_BP_BankAccount.IsActive
-- 2023-07-27T08:47:40.786Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587185,717261,0,547049,TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system',1,'de.metas.banking','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','N','N','N','N','N','Active',TO_TIMESTAMP('2023-07-27 11:47:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:40.789Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717261 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:40.795Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-07-27T08:47:41.915Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717261
;

-- 2023-07-27T08:47:41.915Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717261)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Processed
-- Column: I_BP_BankAccount.Processed
-- 2023-07-27T08:47:42.001Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587186,717262,0,547049,TO_TIMESTAMP('2023-07-27 11:47:41','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.banking','','Y','N','N','N','N','N','N','N','Processed',TO_TIMESTAMP('2023-07-27 11:47:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.002Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717262 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.003Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-07-27T08:47:42.053Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717262
;

-- 2023-07-27T08:47:42.053Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717262)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Import BPartner Bank Account
-- Column: I_BP_BankAccount.I_BP_BankAccount_ID
-- 2023-07-27T08:47:42.150Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587189,717263,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','Import BPartner Bank Account',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.153Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717263 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.157Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582573) 
;

-- 2023-07-27T08:47:42.173Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717263
;

-- 2023-07-27T08:47:42.174Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717263)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Partner No.
-- Column: I_BP_BankAccount.BPartnerValue
-- 2023-07-27T08:47:42.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587190,717264,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,'Key of the Business Partner',255,'de.metas.banking','Y','N','N','N','N','N','N','N','Partner No.',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.275Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717264 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.280Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2094) 
;

-- 2023-07-27T08:47:42.305Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717264
;

-- 2023-07-27T08:47:42.307Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717264)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Routing No
-- Column: I_BP_BankAccount.RoutingNo
-- 2023-07-27T08:47:42.406Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587191,717265,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,'Bank Routing Number',255,'de.metas.banking','The Bank Routing Number (ABA Number) identifies a legal Bank.  It is used in routing checks and electronic transactions.','Y','N','N','N','N','N','N','N','Routing No',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.409Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717265 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.413Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(964) 
;

-- 2023-07-27T08:47:42.451Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717265
;

-- 2023-07-27T08:47:42.453Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717265)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Account No
-- Column: I_BP_BankAccount.AccountNo
-- 2023-07-27T08:47:42.555Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587192,717266,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,'Account Number',255,'de.metas.banking','The Account Number indicates the Number assigned to this bank account. ','Y','N','N','N','N','N','N','N','Account No',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717266 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.558Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(840) 
;

-- 2023-07-27T08:47:42.587Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717266
;

-- 2023-07-27T08:47:42.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717266)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Account Name
-- Column: I_BP_BankAccount.A_Name
-- 2023-07-27T08:47:42.694Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587193,717267,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,'Name on Credit Card or Account holder',255,'de.metas.banking','The Name of the Credit Card or Account holder.','Y','N','N','N','N','N','N','N','Account Name',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.697Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717267 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1354) 
;

-- 2023-07-27T08:47:42.735Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717267
;

-- 2023-07-27T08:47:42.736Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717267)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Reference Details
-- Column: I_BP_BankAccount.Reference_Details
-- 2023-07-27T08:47:42.833Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587194,717268,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.banking','Y','N','N','N','N','N','N','N','Reference Details',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.837Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717268 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.841Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582574) 
;

-- 2023-07-27T08:47:42.856Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717268
;

-- 2023-07-27T08:47:42.857Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717268)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Business Partner
-- Column: I_BP_BankAccount.C_BPartner_ID
-- 2023-07-27T08:47:42.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587195,717269,0,547049,TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.banking','','Y','N','N','N','N','N','N','N','Business Partner',TO_TIMESTAMP('2023-07-27 11:47:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:42.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717269 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:42.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-07-27T08:47:43.049Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717269
;

-- 2023-07-27T08:47:43.051Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717269)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Currency
-- Column: I_BP_BankAccount.C_Currency_ID
-- 2023-07-27T08:47:43.140Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587196,717270,0,547049,TO_TIMESTAMP('2023-07-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record',10,'de.metas.banking','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','N','N','N','N','N','Currency',TO_TIMESTAMP('2023-07-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:43.142Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717270 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:43.144Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-07-27T08:47:43.333Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717270
;

-- 2023-07-27T08:47:43.334Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717270)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Currency Code
-- Column: I_BP_BankAccount.CurrencyCode
-- 2023-07-27T08:47:43.429Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587197,717271,0,547049,TO_TIMESTAMP('2023-07-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.banking','Y','N','N','N','N','N','N','N','Currency Code',TO_TIMESTAMP('2023-07-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:43.432Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717271 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:43.437Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577559) 
;

-- 2023-07-27T08:47:43.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717271
;

-- 2023-07-27T08:47:43.457Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717271)
;

-- Field: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> Process Now
-- Column: I_BP_BankAccount.Processing
-- 2023-07-27T08:47:43.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587198,717272,0,547049,TO_TIMESTAMP('2023-07-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.banking','Y','N','N','N','N','N','N','N','Process Now',TO_TIMESTAMP('2023-07-27 11:47:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T08:47:43.560Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=717272 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-27T08:47:43.565Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(524) 
;

-- 2023-07-27T08:47:43.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=717272
;

-- 2023-07-27T08:47:43.710Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(717272)
;

-- Tab: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking)
-- UI Section: main
-- 2023-07-27T08:47:52.353Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547049,545648,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-07-27T08:47:52.357Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545648 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main
-- UI Column: 10
-- 2023-07-27T08:47:52.567Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546900,545648,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main
-- UI Column: 20
-- 2023-07-27T08:47:52.666Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546901,545648,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10
-- UI Element Group: default
-- 2023-07-27T08:47:52.894Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546900,550853,TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-07-27 11:47:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> default.Partner No.
-- Column: I_BP_BankAccount.BPartnerValue
-- 2023-07-27T08:53:30.182Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717264,0,547049,618575,550853,'F',TO_TIMESTAMP('2023-07-27 11:53:29','YYYY-MM-DD HH24:MI:SS'),100,'Key of the Business Partner','Y','N','N','Y','N','N','N',0,'Partner No.',10,0,0,TO_TIMESTAMP('2023-07-27 11:53:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> default.Account No
-- Column: I_BP_BankAccount.AccountNo
-- 2023-07-27T08:55:57.653Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717266,0,547049,618576,550853,'F',TO_TIMESTAMP('2023-07-27 11:55:57','YYYY-MM-DD HH24:MI:SS'),100,'Account Number','The Account Number indicates the Number assigned to this bank account. ','Y','N','N','Y','N','N','N',0,'Account No',20,0,0,TO_TIMESTAMP('2023-07-27 11:55:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> default.Routing No
-- Column: I_BP_BankAccount.RoutingNo
-- 2023-07-27T08:56:10.970Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717265,0,547049,618577,550853,'F',TO_TIMESTAMP('2023-07-27 11:56:10','YYYY-MM-DD HH24:MI:SS'),100,'Bank Routing Number','The Bank Routing Number (ABA Number) identifies a legal Bank.  It is used in routing checks and electronic transactions.','Y','N','N','Y','N','N','N',0,'Routing No',30,0,0,TO_TIMESTAMP('2023-07-27 11:56:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> default.Account Name
-- Column: I_BP_BankAccount.A_Name
-- 2023-07-27T08:56:26.178Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717267,0,547049,618578,550853,'F',TO_TIMESTAMP('2023-07-27 11:56:26','YYYY-MM-DD HH24:MI:SS'),100,'Name on Credit Card or Account holder','The Name of the Credit Card or Account holder.','Y','N','N','Y','N','N','N',0,'Account Name',40,0,0,TO_TIMESTAMP('2023-07-27 11:56:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> default.Currency Code
-- Column: I_BP_BankAccount.CurrencyCode
-- 2023-07-27T08:57:16.873Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717271,0,547049,618579,550853,'F',TO_TIMESTAMP('2023-07-27 11:57:16','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Currency Code',50,0,0,TO_TIMESTAMP('2023-07-27 11:57:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> default.Reference Details
-- Column: I_BP_BankAccount.Reference_Details
-- 2023-07-27T08:58:22.405Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717268,0,547049,618580,550853,'F',TO_TIMESTAMP('2023-07-27 11:58:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Reference Details',60,0,0,TO_TIMESTAMP('2023-07-27 11:58:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10
-- UI Element Group: resolved
-- 2023-07-27T08:58:47.001Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546900,550854,TO_TIMESTAMP('2023-07-27 11:58:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','resolved',20,TO_TIMESTAMP('2023-07-27 11:58:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> resolved.Business Partner
-- Column: I_BP_BankAccount.C_BPartner_ID
-- 2023-07-27T08:59:38.220Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717269,0,547049,618581,550854,'F',TO_TIMESTAMP('2023-07-27 11:59:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Business Partner',10,0,0,TO_TIMESTAMP('2023-07-27 11:59:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 10 -> resolved.Currency
-- Column: I_BP_BankAccount.C_Currency_ID
-- 2023-07-27T08:59:53.186Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717270,0,547049,618582,550854,'F',TO_TIMESTAMP('2023-07-27 11:59:53','YYYY-MM-DD HH24:MI:SS'),100,'The Currency for this record','Indicates the Currency to be used when processing or reporting on this record','Y','N','N','Y','N','N','N',0,'Currency',20,0,0,TO_TIMESTAMP('2023-07-27 11:59:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20
-- UI Element Group: flags
-- 2023-07-27T09:00:05.238Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546901,550855,TO_TIMESTAMP('2023-07-27 12:00:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-07-27 12:00:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> flags.Active
-- Column: I_BP_BankAccount.IsActive
-- 2023-07-27T09:00:21.222Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717261,0,547049,618583,550855,'F',TO_TIMESTAMP('2023-07-27 12:00:21','YYYY-MM-DD HH24:MI:SS'),100,'The record is active in the system','There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
There are two reasons for de-activating and not deleting records:
(1) The system requires the record for audit purposes.
(2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.','Y','N','N','Y','N','N','N',0,'Active',10,0,0,TO_TIMESTAMP('2023-07-27 12:00:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> flags.Processed
-- Column: I_BP_BankAccount.Processed
-- 2023-07-27T09:00:34.149Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717262,0,547049,618584,550855,'F',TO_TIMESTAMP('2023-07-27 12:00:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Processed',20,0,0,TO_TIMESTAMP('2023-07-27 12:00:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20
-- UI Element Group: import
-- 2023-07-27T09:00:47.935Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546901,550856,TO_TIMESTAMP('2023-07-27 12:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','import',20,TO_TIMESTAMP('2023-07-27 12:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> import.Import Error Message
-- Column: I_BP_BankAccount.I_ErrorMsg
-- 2023-07-27T09:00:58.399Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717257,0,547049,618585,550856,'F',TO_TIMESTAMP('2023-07-27 12:00:58','YYYY-MM-DD HH24:MI:SS'),100,'Messages generated from import process','The Import Error Message displays any error messages generated during the import process.','Y','N','N','Y','N','N','N',0,'Import Error Message',10,0,0,TO_TIMESTAMP('2023-07-27 12:00:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> import.Import Line No
-- Column: I_BP_BankAccount.I_LineNo
-- 2023-07-27T09:01:06.547Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717260,0,547049,618586,550856,'F',TO_TIMESTAMP('2023-07-27 12:01:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line No',20,0,0,TO_TIMESTAMP('2023-07-27 12:01:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> import.Import Line Content
-- Column: I_BP_BankAccount.I_LineContent
-- 2023-07-27T09:01:17.265Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717259,0,547049,618587,550856,'F',TO_TIMESTAMP('2023-07-27 12:01:17','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line Content',30,0,0,TO_TIMESTAMP('2023-07-27 12:01:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> import.Imported
-- Column: I_BP_BankAccount.I_IsImported
-- 2023-07-27T09:01:24.497Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717258,0,547049,618588,550856,'F',TO_TIMESTAMP('2023-07-27 12:01:24','YYYY-MM-DD HH24:MI:SS'),100,'Has this import been processed','The Imported check box indicates if this import has been processed.','Y','N','N','Y','N','N','N',0,'Imported',40,0,0,TO_TIMESTAMP('2023-07-27 12:01:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> import.Imported
-- Column: I_BP_BankAccount.I_IsImported
-- 2023-07-27T09:01:38.459Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-07-27 12:01:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618588
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> import.Import Error Message
-- Column: I_BP_BankAccount.I_ErrorMsg
-- 2023-07-27T09:01:53.177Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-07-27 12:01:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=618585
;

-- UI Column: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20
-- UI Element Group: dataimp
-- 2023-07-27T09:02:10.503Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546901,550857,TO_TIMESTAMP('2023-07-27 12:02:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','dataimp',30,TO_TIMESTAMP('2023-07-27 12:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> dataimp.Data import
-- Column: I_BP_BankAccount.C_DataImport_ID
-- 2023-07-27T09:02:23.691Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717255,0,547049,618589,550857,'F',TO_TIMESTAMP('2023-07-27 12:02:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data import',10,0,0,TO_TIMESTAMP('2023-07-27 12:02:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> dataimp.Data Import Run
-- Column: I_BP_BankAccount.C_DataImport_Run_ID
-- 2023-07-27T09:02:33.715Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717256,0,547049,618590,550857,'F',TO_TIMESTAMP('2023-07-27 12:02:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Data Import Run',20,0,0,TO_TIMESTAMP('2023-07-27 12:02:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20
-- UI Element Group: client&org
-- 2023-07-27T09:04:21.202Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546901,550858,TO_TIMESTAMP('2023-07-27 12:04:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','client&org',40,TO_TIMESTAMP('2023-07-27 12:04:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> client&org.Organisation
-- Column: I_BP_BankAccount.AD_Org_ID
-- 2023-07-27T09:04:31.027Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717254,0,547049,618591,550858,'F',TO_TIMESTAMP('2023-07-27 12:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Organisational entity within client','An organisation is a unit of your client or legal entity - examples are store, department. You can share data between organisations.','Y','N','N','Y','N','N','N',0,'Organisation',10,0,0,TO_TIMESTAMP('2023-07-27 12:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Import Bank Account(541720,de.metas.banking) -> Import BPartner Bank Account(547049,de.metas.banking) -> main -> 20 -> client&org.Client
-- Column: I_BP_BankAccount.AD_Client_ID
-- 2023-07-27T09:04:49.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,717252,0,547049,618592,550858,'F',TO_TIMESTAMP('2023-07-27 12:04:48','YYYY-MM-DD HH24:MI:SS'),100,'Client/Tenant for this installation.','A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.','Y','N','N','Y','N','N','N',0,'Client',20,0,0,TO_TIMESTAMP('2023-07-27 12:04:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Import Bank Account
-- Action Type: W
-- Window: Import Bank Account(541720,de.metas.banking)
-- 2023-07-27T09:10:03.593Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582575,542095,0,541720,TO_TIMESTAMP('2023-07-27 12:10:03','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.banking','I_BP_BankAccount','Y','N','N','N','N','Import Bank Account',TO_TIMESTAMP('2023-07-27 12:10:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T09:10:03.601Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542095 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-07-27T09:10:03.616Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542095, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542095)
;

-- 2023-07-27T09:10:03.632Z
/* DDL */  select update_menu_translation_from_ad_element(582575) 
;

-- Reordering children of `CRM`
-- Node name: `Import Business Partner Block Status (I_BPartner_BlockStatus)`
-- 2023-07-27T09:10:04.234Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542064 AND AD_Tree_ID=10
;

-- Node name: `Change Business Partner Block (C_BPartner_Block_File)`
-- 2023-07-27T09:10:04.235Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542062 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2023-07-27T09:10:04.236Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2023-07-27T09:10:04.237Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book (AD_User)`
-- 2023-07-27T09:10:04.238Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2023-07-27T09:10:04.239Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export (C_BPartner_Export)`
-- 2023-07-27T09:10:04.240Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents (C_Doc_Outbound_Log)`
-- 2023-07-27T09:10:04.241Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-07-27T09:10:04.242Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-07-27T09:10:04.243Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-07-27T09:10:04.244Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `BPartne Global ID (I_BPartner_GlobalID)`
-- 2023-07-27T09:10:04.244Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User (I_User)`
-- 2023-07-27T09:10:04.245Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call (R_Request)`
-- 2023-07-27T09:10:04.246Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema (C_Phonecall_Schema)`
-- 2023-07-27T09:10:04.246Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema Version (C_Phonecall_Schema_Version)`
-- 2023-07-27T09:10:04.247Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule (C_Phonecall_Schedule)`
-- 2023-07-27T09:10:04.248Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- Node name: `Customer Communication (R_Request)`
-- 2023-07-27T09:10:04.248Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541981 AND AD_Tree_ID=10
;

-- Node name: `CRM Material Group (M_MaterialGroup)`
-- 2023-07-27T09:10:04.249Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541983 AND AD_Tree_ID=10
;

-- Node name: `CRM Material Group Assignment (M_MaterialGroup_Assignment_V)`
-- 2023-07-27T09:10:04.249Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541985 AND AD_Tree_ID=10
;

-- Node name: `CRM Material Data Download (M_Material_Data_Download_V)`
-- 2023-07-27T09:10:04.250Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542083 AND AD_Tree_ID=10
;

-- Node name: `Credit Insurance Report (de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess)`
-- 2023-07-27T09:10:04.250Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542085 AND AD_Tree_ID=10
;

-- Node name: `Import Bank Account`
-- 2023-07-27T09:10:04.251Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542095 AND AD_Tree_ID=10
;

-- Value: de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing.MoreThanOneBPSelected
-- 2023-07-27T09:49:15.512Z
INSERT INTO AD_Message (AD_Client_ID,AD_Message_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,MsgText,MsgType,Updated,UpdatedBy,Value) VALUES (0,545309,0,TO_TIMESTAMP('2023-07-27 12:49:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoice','Y','Cannot enqueue candidates from multiple business partners','E',TO_TIMESTAMP('2023-07-27 12:49:14','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.invoicecandidate.process.C_Invoice_Candidate_EnqueueSelectionForInvoicing.MoreThanOneBPSelected')
;

-- 2023-07-27T09:49:15.515Z
INSERT INTO AD_Message_Trl (AD_Language,AD_Message_ID, MsgText,MsgTip, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Message_ID, t.MsgText,t.MsgTip, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Message t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Message_ID=545309 AND NOT EXISTS (SELECT 1 FROM AD_Message_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Message_ID=t.AD_Message_ID)
;

-- 2023-07-27T09:52:27.831Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582576,0,'IsInvoicingSingleBPartner',TO_TIMESTAMP('2023-07-27 12:52:27','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Invoice Single Partner','Invoice Single Partner',TO_TIMESTAMP('2023-07-27 12:52:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-27T09:52:27.839Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582576 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;