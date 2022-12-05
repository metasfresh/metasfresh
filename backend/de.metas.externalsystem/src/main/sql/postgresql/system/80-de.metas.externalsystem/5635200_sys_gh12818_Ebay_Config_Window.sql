-- 2022-04-14T08:43:01.301Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,580781,0,TO_TIMESTAMP('2022-04-14 11:43:01','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','External system config eBay','External system config eBay',TO_TIMESTAMP('2022-04-14 11:43:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T08:43:01.303Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=580781 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;




-- Window: External system config eBay, InternalName=null
-- 2022-04-14T09:31:20.026Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,580781,0,541468,TO_TIMESTAMP('2022-04-14 12:31:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Y','N','N','N','N','N','N','Y','External system config eBay','N',TO_TIMESTAMP('2022-04-14 12:31:19','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2022-04-14T09:31:20.029Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541468 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2022-04-14T09:31:20.033Z
/* DDL */  select update_window_translation_from_ad_element(580781) 
;

-- 2022-04-14T09:31:20.045Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541468
;

-- 2022-04-14T09:31:20.046Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541468)
;

-- Tab: External system config eBay -> eBay
-- Table: ExternalSystem_Config_Ebay
-- 2022-04-14T09:32:40.703Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,579444,0,546094,541741,541468,'Y',TO_TIMESTAMP('2022-04-14 12:32:40','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_Ebay','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'eBay','N',10,0,TO_TIMESTAMP('2022-04-14 12:32:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:40.706Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546094 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-04-14T09:32:40.708Z
/* DDL */  select update_tab_translation_from_ad_element(579444) 
;

-- 2022-04-14T09:32:40.711Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546094)
;

-- Field: External system config eBay -> eBay -> Mandant
-- Column: ExternalSystem_Config_Ebay.AD_Client_ID
-- 2022-04-14T09:32:42.419Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574921,691679,0,546094,TO_TIMESTAMP('2022-04-14 12:32:42','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-04-14 12:32:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:42.421Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:42.422Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-04-14T09:32:42.901Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691679
;

-- 2022-04-14T09:32:42.902Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691679)
;

-- Field: External system config eBay -> eBay -> Sektion
-- Column: ExternalSystem_Config_Ebay.AD_Org_ID
-- 2022-04-14T09:32:43.047Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574922,691680,0,546094,TO_TIMESTAMP('2022-04-14 12:32:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-04-14 12:32:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:43.048Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691680 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:43.049Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-04-14T09:32:43.145Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691680
;

-- 2022-04-14T09:32:43.147Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691680)
;

-- Field: External system config eBay -> eBay -> Aktiv
-- Column: ExternalSystem_Config_Ebay.IsActive
-- 2022-04-14T09:32:43.308Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574925,691681,0,546094,TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:43.309Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691681 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:43.311Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-04-14T09:32:43.435Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691681
;

-- 2022-04-14T09:32:43.436Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691681)
;

-- Field: External system config eBay -> eBay -> eBay
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_Ebay_ID
-- 2022-04-14T09:32:43.582Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574928,691682,0,546094,TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','eBay',TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:43.584Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691682 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:43.585Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579444) 
;

-- 2022-04-14T09:32:43.587Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691682
;

-- 2022-04-14T09:32:43.588Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691682)
;

-- Field: External system config eBay -> eBay -> App ID
-- Column: ExternalSystem_Config_Ebay.AppId
-- 2022-04-14T09:32:43.724Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574929,691683,0,546094,TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100,'Client ID',255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','App ID',TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:43.726Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691683 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:43.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579445) 
;

-- 2022-04-14T09:32:43.729Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691683
;

-- 2022-04-14T09:32:43.730Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691683)
;

-- Field: External system config eBay -> eBay -> Cert ID
-- Column: ExternalSystem_Config_Ebay.CertId
-- 2022-04-14T09:32:43.862Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574930,691684,0,546094,TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100,'Client Secret',255,'de.metas.externalsystem','','Y','N','N','N','N','N','N','N','Cert ID',TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:43.864Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691684 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:43.865Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579446) 
;

-- 2022-04-14T09:32:43.867Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691684
;

-- 2022-04-14T09:32:43.868Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691684)
;

-- Field: External system config eBay -> eBay -> Dev ID
-- Column: ExternalSystem_Config_Ebay.DevId
-- 2022-04-14T09:32:44.002Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574931,691685,0,546094,TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Dev ID',TO_TIMESTAMP('2022-04-14 12:32:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:44.003Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691685 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:44.004Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579447) 
;

-- 2022-04-14T09:32:44.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691685
;

-- 2022-04-14T09:32:44.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691685)
;

-- Field: External system config eBay -> eBay -> API Mode
-- Column: ExternalSystem_Config_Ebay.API_Mode
-- 2022-04-14T09:32:44.156Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574943,691686,0,546094,TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','API Mode',TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:44.157Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691686 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:44.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579451) 
;

-- 2022-04-14T09:32:44.160Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691686
;

-- 2022-04-14T09:32:44.160Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691686)
;

-- Field: External system config eBay -> eBay -> External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T09:32:44.309Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574947,691687,0,546094,TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:44.311Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691687 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:44.312Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-04-14T09:32:44.314Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691687
;

-- 2022-04-14T09:32:44.315Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691687)
;

-- Field: External system config eBay -> eBay -> Suchschlüssel
-- Column: ExternalSystem_Config_Ebay.ExternalSystemValue
-- 2022-04-14T09:32:44.479Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,574960,691688,0,546094,TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Suchschlüssel',TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:44.480Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691688 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:44.482Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578788) 
;

-- 2022-04-14T09:32:44.483Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691688
;

-- 2022-04-14T09:32:44.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691688)
;

-- Field: External system config eBay -> eBay -> Refresh Token
-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-04-14T09:32:44.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582767,691689,0,546094,TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Refresh Token',TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:44.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691689 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:44.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580773) 
;

-- 2022-04-14T09:32:44.630Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691689
;

-- 2022-04-14T09:32:44.631Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691689)
;

-- Field: External system config eBay -> eBay -> Preisliste
-- Column: ExternalSystem_Config_Ebay.M_PriceList_ID
-- 2022-04-14T09:32:44.765Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582768,691690,0,546094,TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste',10,'de.metas.externalsystem','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','N','N','N','N','N','N','Preisliste',TO_TIMESTAMP('2022-04-14 12:32:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:32:44.767Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691690 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:32:44.768Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(449) 
;

-- 2022-04-14T09:32:44.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691690
;

-- 2022-04-14T09:32:44.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691690)
;

-- Tab: External system config eBay -> ExternalSystem_Config_Ebay_Mapping
-- Table: ExternalSystem_Config_Ebay_Mapping
-- 2022-04-14T09:33:42.530Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582778,580774,0,546095,542124,541468,'Y',TO_TIMESTAMP('2022-04-14 12:33:42','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_Ebay_Mapping','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_Ebay_Mapping','N',20,1,TO_TIMESTAMP('2022-04-14 12:33:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:42.532Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546095 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-04-14T09:33:42.534Z
/* DDL */  select update_tab_translation_from_ad_element(580774) 
;

-- 2022-04-14T09:33:42.537Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546095)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Reihenfolge
-- Column: ExternalSystem_Config_Ebay_Mapping.SeqNo
-- 2022-04-14T09:33:44.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582769,691691,0,546095,TO_TIMESTAMP('2022-04-14 12:33:44','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',2,'de.metas.externalsystem','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2022-04-14 12:33:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:45.001Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691691 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:45.003Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566) 
;

-- 2022-04-14T09:33:45.010Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691691
;

-- 2022-04-14T09:33:45.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691691)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Mandant
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Client_ID
-- 2022-04-14T09:33:45.168Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582770,691692,0,546095,TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:45.169Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691692 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:45.171Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-04-14T09:33:45.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691692
;

-- 2022-04-14T09:33:45.264Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691692)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Sektion
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Org_ID
-- 2022-04-14T09:33:45.415Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582771,691693,0,546095,TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:45.416Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691693 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:45.418Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-04-14T09:33:45.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691693
;

-- 2022-04-14T09:33:45.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691693)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Aktiv
-- Column: ExternalSystem_Config_Ebay_Mapping.IsActive
-- 2022-04-14T09:33:45.620Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582774,691694,0,546095,TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:45.622Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691694 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:45.623Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-04-14T09:33:45.732Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691694
;

-- 2022-04-14T09:33:45.734Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691694)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> ExternalSystem_Config_Ebay_Mapping
-- Column: ExternalSystem_Config_Ebay_Mapping.ExternalSystem_Config_Ebay_Mapping_ID
-- 2022-04-14T09:33:45.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582777,691695,0,546095,TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_Ebay_Mapping',TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:45.868Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691695 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:45.869Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580774) 
;

-- 2022-04-14T09:33:45.871Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691695
;

-- 2022-04-14T09:33:45.872Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691695)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> eBay
-- Column: ExternalSystem_Config_Ebay_Mapping.ExternalSystem_Config_Ebay_ID
-- 2022-04-14T09:33:45.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582778,691696,0,546095,TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','eBay',TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:45.971Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691696 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:45.973Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579444) 
;

-- 2022-04-14T09:33:45.975Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691696
;

-- 2022-04-14T09:33:45.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691696)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Auftrags-Belegart
-- Column: ExternalSystem_Config_Ebay_Mapping.C_DocTypeOrder_ID
-- 2022-04-14T09:33:46.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582779,691697,0,546095,TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for the orders generated from this order candidate',10,'de.metas.externalsystem','The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','N','N','N','N','N','N','N','Auftrags-Belegart',TO_TIMESTAMP('2022-04-14 12:33:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691697 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577366) 
;

-- 2022-04-14T09:33:46.157Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691697
;

-- 2022-04-14T09:33:46.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691697)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Zahlungsweise
-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-14T09:33:46.293Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582780,691698,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird',500,'de.metas.externalsystem','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','N','N','N','N','N','N','Zahlungsweise',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.294Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691698 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.295Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1143) 
;

-- 2022-04-14T09:33:46.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691698
;

-- 2022-04-14T09:33:46.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691698)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Gesch.-Partner ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-14T09:33:46.446Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582781,691699,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.',500,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Gesch.-Partner ex.',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.447Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691699 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.449Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579145) 
;

-- 2022-04-14T09:33:46.450Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691699
;

-- 2022-04-14T09:33:46.450Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691699)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Gesch.-Partner nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-14T09:33:46.571Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582782,691700,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.',500,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Gesch.-Partner nicht ex.',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691700 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579146) 
;

-- 2022-04-14T09:33:46.575Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691700
;

-- 2022-04-14T09:33:46.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691700)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Adresse ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-14T09:33:46.708Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582783,691701,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.',500,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Adresse ex.',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.709Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691701 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.711Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579147) 
;

-- 2022-04-14T09:33:46.712Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691701
;

-- 2022-04-14T09:33:46.713Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691701)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Adr. nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-14T09:33:46.847Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582784,691702,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.',500,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Wenn Adr. nicht ex.',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.848Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691702 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.849Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579148) 
;

-- 2022-04-14T09:33:46.850Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691702
;

-- 2022-04-14T09:33:46.851Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691702)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Rechnung per eMail
-- Column: ExternalSystem_Config_Ebay_Mapping.IsInvoiceEmailEnabled
-- 2022-04-14T09:33:46.970Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582785,691703,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Rechnung per eMail',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:46.971Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691703 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:46.972Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543189) 
;

-- 2022-04-14T09:33:46.974Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691703
;

-- 2022-04-14T09:33:46.975Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691703)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Zahlungsbedingung
-- Column: ExternalSystem_Config_Ebay_Mapping.C_PaymentTerm_ID
-- 2022-04-14T09:33:47.160Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582786,691704,0,546095,TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs',10,'de.metas.externalsystem','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','N','N','N','N','N','N','Zahlungsbedingung',TO_TIMESTAMP('2022-04-14 12:33:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:47.161Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691704 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:47.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(204) 
;

-- 2022-04-14T09:33:47.169Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691704
;

-- 2022-04-14T09:33:47.170Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691704)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> eBay Customer Group
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayCustomerGroup
-- 2022-04-14T09:33:47.318Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582787,691705,0,546095,TO_TIMESTAMP('2022-04-14 12:33:47','YYYY-MM-DD HH24:MI:SS'),100,500,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','eBay Customer Group',TO_TIMESTAMP('2022-04-14 12:33:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:47.319Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691705 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:47.321Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580779) 
;

-- 2022-04-14T09:33:47.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691705
;

-- 2022-04-14T09:33:47.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691705)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> eBay Payment Method
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayPaymentMethod
-- 2022-04-14T09:33:47.455Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582788,691706,0,546095,TO_TIMESTAMP('2022-04-14 12:33:47','YYYY-MM-DD HH24:MI:SS'),100,500,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','eBay Payment Method',TO_TIMESTAMP('2022-04-14 12:33:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:47.457Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691706 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:47.458Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580780) 
;

-- 2022-04-14T09:33:47.459Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691706
;

-- 2022-04-14T09:33:47.460Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691706)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Beschreibung
-- Column: ExternalSystem_Config_Ebay_Mapping.Description
-- 2022-04-14T09:33:47.610Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582789,691707,0,546095,TO_TIMESTAMP('2022-04-14 12:33:47','YYYY-MM-DD HH24:MI:SS'),100,1000,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2022-04-14 12:33:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:33:47.611Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691707 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T09:33:47.612Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2022-04-14T09:33:47.728Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691707
;

-- 2022-04-14T09:33:47.729Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691707)
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Reihenfolge
-- Column: ExternalSystem_Config_Ebay_Mapping.SeqNo
-- 2022-04-14T09:34:01.224Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691691
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Mandant
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Client_ID
-- 2022-04-14T09:34:02.522Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691692
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Sektion
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Org_ID
-- 2022-04-14T09:34:05.441Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691693
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Aktiv
-- Column: ExternalSystem_Config_Ebay_Mapping.IsActive
-- 2022-04-14T09:34:12.644Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691694
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Auftrags-Belegart
-- Column: ExternalSystem_Config_Ebay_Mapping.C_DocTypeOrder_ID
-- 2022-04-14T09:34:13.565Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691697
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Zahlungsweise
-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-14T09:34:14.147Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691698
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Gesch.-Partner ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-14T09:34:14.790Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691699
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Gesch.-Partner nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-14T09:34:15.364Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691700
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Adresse ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-14T09:34:15.851Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691701
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Wenn Adr. nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-14T09:34:16.447Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691702
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Rechnung per eMail
-- Column: ExternalSystem_Config_Ebay_Mapping.IsInvoiceEmailEnabled
-- 2022-04-14T09:34:17.356Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691703
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Zahlungsbedingung
-- Column: ExternalSystem_Config_Ebay_Mapping.C_PaymentTerm_ID
-- 2022-04-14T09:34:17.935Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691704
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> eBay Customer Group
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayCustomerGroup
-- 2022-04-14T09:34:19.847Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691705
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> eBay Payment Method
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayPaymentMethod
-- 2022-04-14T09:34:20.471Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691706
;

-- Field: External system config eBay -> ExternalSystem_Config_Ebay_Mapping -> Beschreibung
-- Column: ExternalSystem_Config_Ebay_Mapping.Description
-- 2022-04-14T09:34:22.078Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691707
;

-- Field: External system config eBay -> eBay -> Mandant
-- Column: ExternalSystem_Config_Ebay.AD_Client_ID
-- 2022-04-14T09:34:33.097Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691679
;

-- Field: External system config eBay -> eBay -> Sektion
-- Column: ExternalSystem_Config_Ebay.AD_Org_ID
-- 2022-04-14T09:34:34.297Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691680
;

-- Field: External system config eBay -> eBay -> Aktiv
-- Column: ExternalSystem_Config_Ebay.IsActive
-- 2022-04-14T09:34:35.773Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691681
;

-- Field: External system config eBay -> eBay -> App ID
-- Column: ExternalSystem_Config_Ebay.AppId
-- 2022-04-14T09:34:36.631Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691683
;

-- Field: External system config eBay -> eBay -> Dev ID
-- Column: ExternalSystem_Config_Ebay.DevId
-- 2022-04-14T09:34:39.064Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691685
;

-- Field: External system config eBay -> eBay -> Cert ID
-- Column: ExternalSystem_Config_Ebay.CertId
-- 2022-04-14T09:34:41.146Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691684
;

-- Field: External system config eBay -> eBay -> API Mode
-- Column: ExternalSystem_Config_Ebay.API_Mode
-- 2022-04-14T09:34:41.761Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691686
;

-- Field: External system config eBay -> eBay -> External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T09:34:42.380Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691687
;

-- Field: External system config eBay -> eBay -> Suchschlüssel
-- Column: ExternalSystem_Config_Ebay.ExternalSystemValue
-- 2022-04-14T09:34:43.665Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691688
;

-- Field: External system config eBay -> eBay -> Refresh Token
-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-04-14T09:34:45.106Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691689
;

-- Field: External system config eBay -> eBay -> Preisliste
-- Column: ExternalSystem_Config_Ebay.M_PriceList_ID
-- 2022-04-14T09:34:47.956Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2022-04-14 12:34:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691690
;

-- 2022-04-14T09:34:52.136Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546094,544740,TO_TIMESTAMP('2022-04-14 12:34:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-14 12:34:51','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-04-14T09:34:52.137Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544740 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-04-14T09:34:52.275Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545737,544740,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:34:52.415Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545738,544740,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:34:52.584Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545737,548796,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Mandant
-- Column: ExternalSystem_Config_Ebay.AD_Client_ID
-- 2022-04-14T09:34:52.842Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691679,0,546094,548796,605329,'F',TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Sektion
-- Column: ExternalSystem_Config_Ebay.AD_Org_ID
-- 2022-04-14T09:34:52.961Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691680,0,546094,548796,605330,'F',TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',20,0,0,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Aktiv
-- Column: ExternalSystem_Config_Ebay.IsActive
-- 2022-04-14T09:34:53.115Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691681,0,546094,548796,605331,'F',TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',30,0,0,TO_TIMESTAMP('2022-04-14 12:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.App ID
-- Column: ExternalSystem_Config_Ebay.AppId
-- 2022-04-14T09:34:53.241Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691683,0,546094,548796,605332,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Client ID','Y','N','Y','N','N','App ID',40,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Cert ID
-- Column: ExternalSystem_Config_Ebay.CertId
-- 2022-04-14T09:34:53.392Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691684,0,546094,548796,605333,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Client Secret','','Y','N','Y','N','N','Cert ID',50,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Dev ID
-- Column: ExternalSystem_Config_Ebay.DevId
-- 2022-04-14T09:34:53.485Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691685,0,546094,548796,605334,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Dev ID',60,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.API Mode
-- Column: ExternalSystem_Config_Ebay.API_Mode
-- 2022-04-14T09:34:53.611Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691686,0,546094,548796,605335,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','API Mode',70,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T09:34:53.810Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691687,0,546094,548796,605336,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','External System Config',80,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Suchschlüssel
-- Column: ExternalSystem_Config_Ebay.ExternalSystemValue
-- 2022-04-14T09:34:53.949Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691688,0,546094,548796,605337,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Suchschlüssel',90,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Refresh Token
-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-04-14T09:34:54.056Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691689,0,546094,548796,605338,'F',TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Refresh Token',100,0,0,TO_TIMESTAMP('2022-04-14 12:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Preisliste
-- Column: ExternalSystem_Config_Ebay.M_PriceList_ID
-- 2022-04-14T09:34:54.149Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691690,0,546094,548796,605339,'F',TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnung der Preisliste','Preislisten werden verwendet, um Preis, Spanne und Kosten einer ge- oder verkauften Ware zu bestimmen.','Y','N','Y','N','N','Preisliste',110,0,0,TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:34:54.257Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546095,544741,TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-04-14T09:34:54.259Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544741 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-04-14T09:34:54.364Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545739,544741,TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:34:54.487Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545739,548797,TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-04-14 12:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:35:31.105Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545738,548798,TO_TIMESTAMP('2022-04-14 12:35:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','flag',10,TO_TIMESTAMP('2022-04-14 12:35:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:35:35.077Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545738,548799,TO_TIMESTAMP('2022-04-14 12:35:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-04-14 12:35:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.Aktiv
-- Column: ExternalSystem_Config_Ebay.IsActive
-- 2022-04-14T09:42:00.959Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548798, SeqNo=10,Updated=TO_TIMESTAMP('2022-04-14 12:42:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605331
;

-- UI Element: External system config eBay -> eBay.Sektion
-- Column: ExternalSystem_Config_Ebay.AD_Org_ID
-- 2022-04-14T09:42:20.176Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548799, SeqNo=10,Updated=TO_TIMESTAMP('2022-04-14 12:42:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605330
;

-- UI Element: External system config eBay -> eBay.Mandant
-- Column: ExternalSystem_Config_Ebay.AD_Client_ID
-- 2022-04-14T09:42:27.278Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548799, SeqNo=20,Updated=TO_TIMESTAMP('2022-04-14 12:42:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605329
;

-- 2022-04-14T09:43:35.543Z
UPDATE AD_UI_ElementGroup SET SeqNo=30,Updated=TO_TIMESTAMP('2022-04-14 12:43:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=548799
;

-- 2022-04-14T09:43:45.080Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545738,548800,TO_TIMESTAMP('2022-04-14 12:43:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','details',20,TO_TIMESTAMP('2022-04-14 12:43:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T09:44:08.638Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548800, SeqNo=10,Updated=TO_TIMESTAMP('2022-04-14 12:44:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605336
;

-- UI Element: External system config eBay -> eBay.Refresh Token
-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-04-14T09:44:22.568Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548800, SeqNo=20,Updated=TO_TIMESTAMP('2022-04-14 12:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605338
;

-- UI Element: External system config eBay -> eBay.Preisliste
-- Column: ExternalSystem_Config_Ebay.M_PriceList_ID
-- 2022-04-14T09:44:30.609Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548800, SeqNo=30,Updated=TO_TIMESTAMP('2022-04-14 12:44:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605339
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Reihenfolge
-- Column: ExternalSystem_Config_Ebay_Mapping.SeqNo
-- 2022-04-14T09:46:11.090Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691691,0,546095,548797,605340,'F',TO_TIMESTAMP('2022-04-14 12:46:10','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Reihenfolge',10,0,0,TO_TIMESTAMP('2022-04-14 12:46:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Reihenfolge
-- Column: ExternalSystem_Config_Ebay_Mapping.SeqNo
-- 2022-04-14T09:46:52.693Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=605340
;

-- 2022-04-14T09:46:52.700Z
DELETE FROM AD_UI_ElementGroup WHERE AD_UI_ElementGroup_ID=548797
;

-- 2022-04-14T09:46:52.707Z
DELETE FROM AD_UI_Column WHERE AD_UI_Column_ID=545739
;

-- 2022-04-14T09:46:52.709Z
DELETE FROM  AD_UI_Section_Trl WHERE AD_UI_Section_ID=544741
;

-- 2022-04-14T09:46:52.715Z
DELETE FROM AD_UI_Section WHERE AD_UI_Section_ID=544741
;

-- 2022-04-14T09:47:03.177Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546095,544742,TO_TIMESTAMP('2022-04-14 12:47:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-14 12:47:02','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-04-14T09:47:03.178Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=544742 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2022-04-14T09:47:03.284Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,545740,544742,TO_TIMESTAMP('2022-04-14 12:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-04-14 12:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:47:03.392Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,545740,548801,TO_TIMESTAMP('2022-04-14 12:47:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2022-04-14 12:47:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Reihenfolge
-- Column: ExternalSystem_Config_Ebay_Mapping.SeqNo
-- 2022-04-14T09:47:31.099Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691691,0,546095,548801,605341,'F',TO_TIMESTAMP('2022-04-14 12:47:30','YYYY-MM-DD HH24:MI:SS'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2022-04-14 12:47:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Mandant
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Client_ID
-- 2022-04-14T09:47:35.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691692,0,546095,548801,605342,'F',TO_TIMESTAMP('2022-04-14 12:47:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2022-04-14 12:47:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Sektion
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Org_ID
-- 2022-04-14T09:47:41.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691693,0,546095,548801,605343,'F',TO_TIMESTAMP('2022-04-14 12:47:41','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',30,0,0,TO_TIMESTAMP('2022-04-14 12:47:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Aktiv
-- Column: ExternalSystem_Config_Ebay_Mapping.IsActive
-- 2022-04-14T09:47:47.594Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691694,0,546095,548801,605344,'F',TO_TIMESTAMP('2022-04-14 12:47:47','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',40,0,0,TO_TIMESTAMP('2022-04-14 12:47:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Auftrags-Belegart
-- Column: ExternalSystem_Config_Ebay_Mapping.C_DocTypeOrder_ID
-- 2022-04-14T09:47:55.183Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691697,0,546095,548801,605345,'F',TO_TIMESTAMP('2022-04-14 12:47:54','YYYY-MM-DD HH24:MI:SS'),100,'Document type used for the orders generated from this order candidate','The Document Type for Order indicates the document type that will be used when an order is generated from this order candidate.','Y','N','N','Y','N','N','N',0,'Auftrags-Belegart',50,0,0,TO_TIMESTAMP('2022-04-14 12:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Zahlungsweise
-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-14T09:48:00.081Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691698,0,546095,548801,605346,'F',TO_TIMESTAMP('2022-04-14 12:47:59','YYYY-MM-DD HH24:MI:SS'),100,'Wie die Rechnung bezahlt wird','Die Zahlungsweise zeigt die Art der Bezahlung der Rechnung an.','Y','N','N','Y','N','N','N',0,'Zahlungsweise',60,0,0,TO_TIMESTAMP('2022-04-14 12:47:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Gesch.-Partner ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-14T09:48:06.134Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691699,0,546095,548801,605347,'F',TO_TIMESTAMP('2022-04-14 12:48:05','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde schon als Geschäftspartner in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Gesch.-Partner ex.',70,0,0,TO_TIMESTAMP('2022-04-14 12:48:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Gesch.-Partner nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-14T09:48:11.842Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691700,0,546095,548801,605348,'F',TO_TIMESTAMP('2022-04-14 12:48:11','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn ein Shopware-Kunde noch nicht als Geschäftspartner in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Gesch.-Partner nicht ex.',80,0,0,TO_TIMESTAMP('2022-04-14 12:48:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Adresse ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-14T09:48:18.119Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691701,0,546095,548801,605349,'F',TO_TIMESTAMP('2022-04-14 12:48:17','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden schon in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Adresse ex.',90,0,0,TO_TIMESTAMP('2022-04-14 12:48:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Adr. nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-14T09:48:22.817Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691702,0,546095,548801,605350,'F',TO_TIMESTAMP('2022-04-14 12:48:22','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, was passieren soll, wenn die Adresse eines Shopware-Kunden noch nicht in metasfresh angelegt ist.','Y','N','N','Y','N','N','N',0,'Wenn Adr. nicht ex.',100,0,0,TO_TIMESTAMP('2022-04-14 12:48:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Rechnung per eMail
-- Column: ExternalSystem_Config_Ebay_Mapping.IsInvoiceEmailEnabled
-- 2022-04-14T09:48:29.257Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691703,0,546095,548801,605351,'F',TO_TIMESTAMP('2022-04-14 12:48:29','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Rechnung per eMail',110,0,0,TO_TIMESTAMP('2022-04-14 12:48:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Zahlungsbedingung
-- Column: ExternalSystem_Config_Ebay_Mapping.C_PaymentTerm_ID
-- 2022-04-14T09:48:35.662Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691704,0,546095,548801,605352,'F',TO_TIMESTAMP('2022-04-14 12:48:35','YYYY-MM-DD HH24:MI:SS'),100,'Die Bedingungen für die Bezahlung dieses Vorgangs','Die Zahlungskondition bestimmt Fristen und Bedingungen für Zahlungen zu diesem Vorgang.','Y','N','N','Y','N','N','N',0,'Zahlungsbedingung',120,0,0,TO_TIMESTAMP('2022-04-14 12:48:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.eBay Customer Group
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayCustomerGroup
-- 2022-04-14T09:48:40.527Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691705,0,546095,548801,605353,'F',TO_TIMESTAMP('2022-04-14 12:48:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eBay Customer Group',130,0,0,TO_TIMESTAMP('2022-04-14 12:48:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.eBay Payment Method
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayPaymentMethod
-- 2022-04-14T09:48:44.915Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691706,0,546095,548801,605354,'F',TO_TIMESTAMP('2022-04-14 12:48:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'eBay Payment Method',140,0,0,TO_TIMESTAMP('2022-04-14 12:48:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Beschreibung
-- Column: ExternalSystem_Config_Ebay_Mapping.Description
-- 2022-04-14T09:48:51.658Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691707,0,546095,548801,605355,'F',TO_TIMESTAMP('2022-04-14 12:48:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',150,0,0,TO_TIMESTAMP('2022-04-14 12:48:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Aktiv
-- Column: ExternalSystem_Config_Ebay_Mapping.IsActive
-- 2022-04-14T09:49:22.329Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-04-14 12:49:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605344
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Zahlungsweise
-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-14T09:49:34.489Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2022-04-14 12:49:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605346
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Zahlungsbedingung
-- Column: ExternalSystem_Config_Ebay_Mapping.C_PaymentTerm_ID
-- 2022-04-14T09:49:45.096Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2022-04-14 12:49:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605352
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Rechnung per eMail
-- Column: ExternalSystem_Config_Ebay_Mapping.IsInvoiceEmailEnabled
-- 2022-04-14T09:50:13.633Z
UPDATE AD_UI_Element SET SeqNo=25,Updated=TO_TIMESTAMP('2022-04-14 12:50:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605351
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.eBay Customer Group
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayCustomerGroup
-- 2022-04-14T09:50:23.182Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2022-04-14 12:50:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605353
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.eBay Payment Method
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayPaymentMethod
-- 2022-04-14T09:50:27.450Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2022-04-14 12:50:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605354
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Gesch.-Partner ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfExists
-- 2022-04-14T09:50:39.994Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2022-04-14 12:50:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605347
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Gesch.-Partner nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartner_IfNotExists
-- 2022-04-14T09:50:42.644Z
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2022-04-14 12:50:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605348
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Adresse ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfExists
-- 2022-04-14T09:50:45.953Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2022-04-14 12:50:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605349
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Wenn Adr. nicht ex.
-- Column: ExternalSystem_Config_Ebay_Mapping.BPartnerLocation_IfNotExists
-- 2022-04-14T09:50:49.142Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2022-04-14 12:50:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605350
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Beschreibung
-- Column: ExternalSystem_Config_Ebay_Mapping.Description
-- 2022-04-14T09:51:03.782Z
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2022-04-14 12:51:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605355
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Sektion
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Org_ID
-- 2022-04-14T09:51:08.006Z
UPDATE AD_UI_Element SET SeqNo=130,Updated=TO_TIMESTAMP('2022-04-14 12:51:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605343
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Mandant
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Client_ID
-- 2022-04-14T09:51:11.549Z
UPDATE AD_UI_Element SET SeqNo=140,Updated=TO_TIMESTAMP('2022-04-14 12:51:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605342
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Reihenfolge
-- Column: ExternalSystem_Config_Ebay_Mapping.SeqNo
-- 2022-04-14T09:53:04.598Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605341
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Aktiv
-- Column: ExternalSystem_Config_Ebay_Mapping.IsActive
-- 2022-04-14T09:53:04.606Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605344
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Rechnung per eMail
-- Column: ExternalSystem_Config_Ebay_Mapping.IsInvoiceEmailEnabled
-- 2022-04-14T09:53:04.613Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605351
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Zahlungsweise
-- Column: ExternalSystem_Config_Ebay_Mapping.PaymentRule
-- 2022-04-14T09:53:04.619Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605346
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Zahlungsbedingung
-- Column: ExternalSystem_Config_Ebay_Mapping.C_PaymentTerm_ID
-- 2022-04-14T09:53:04.625Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605352
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Auftrags-Belegart
-- Column: ExternalSystem_Config_Ebay_Mapping.C_DocTypeOrder_ID
-- 2022-04-14T09:53:04.631Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605345
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.eBay Customer Group
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayCustomerGroup
-- 2022-04-14T09:53:04.637Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605353
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.eBay Payment Method
-- Column: ExternalSystem_Config_Ebay_Mapping.EBayPaymentMethod
-- 2022-04-14T09:53:04.643Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605354
;

-- UI Element: External system config eBay -> ExternalSystem_Config_Ebay_Mapping.Sektion
-- Column: ExternalSystem_Config_Ebay_Mapping.AD_Org_ID
-- 2022-04-14T09:53:04.649Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-04-14 12:53:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605343
;

-- UI Element: External system config eBay -> eBay.Suchschlüssel
-- Column: ExternalSystem_Config_Ebay.ExternalSystemValue
-- 2022-04-14T09:54:01.011Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605337
;

-- UI Element: External system config eBay -> eBay.Aktiv
-- Column: ExternalSystem_Config_Ebay.IsActive
-- 2022-04-14T09:54:01.018Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605331
;

-- UI Element: External system config eBay -> eBay.App ID
-- Column: ExternalSystem_Config_Ebay.AppId
-- 2022-04-14T09:54:01.024Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605332
;

-- UI Element: External system config eBay -> eBay.Cert ID
-- Column: ExternalSystem_Config_Ebay.CertId
-- 2022-04-14T09:54:01.030Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605333
;

-- UI Element: External system config eBay -> eBay.Dev ID
-- Column: ExternalSystem_Config_Ebay.DevId
-- 2022-04-14T09:54:01.035Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605334
;

-- UI Element: External system config eBay -> eBay.API Mode
-- Column: ExternalSystem_Config_Ebay.API_Mode
-- 2022-04-14T09:54:01.041Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605335
;

-- UI Element: External system config eBay -> eBay.Refresh Token
-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-04-14T09:54:01.047Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605338
;

-- UI Element: External system config eBay -> eBay.Preisliste
-- Column: ExternalSystem_Config_Ebay.M_PriceList_ID
-- 2022-04-14T09:54:01.053Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-04-14 12:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605339
;

-- 2022-04-14T09:58:38.267Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,580781,541925,0,541468,TO_TIMESTAMP('2022-04-14 12:58:38','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','Externalsystem_Config_EBay','Y','N','N','N','N','External system config eBay',TO_TIMESTAMP('2022-04-14 12:58:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T09:58:38.269Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=541925 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2022-04-14T09:58:38.271Z
INSERT  INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 541925, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=541925)
;

-- 2022-04-14T09:58:38.282Z
/* DDL */  select update_menu_translation_from_ad_element(580781) 
;

-- Reordering children of `System`
-- Node name: `API Audit`
-- 2022-04-14T09:58:38.879Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2022-04-14T09:58:38.881Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2022-04-14T09:58:38.882Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2022-04-14T09:58:38.883Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2022-04-14T09:58:38.883Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2022-04-14T09:58:38.884Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2022-04-14T09:58:38.885Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2022-04-14T09:58:38.886Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2022-04-14T09:58:38.887Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2022-04-14T09:58:38.888Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2022-04-14T09:58:38.889Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2022-04-14T09:58:38.890Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2022-04-14T09:58:38.891Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2022-04-14T09:58:38.892Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2022-04-14T09:58:38.893Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2022-04-14T09:58:38.894Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2022-04-14T09:58:38.895Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2022-04-14T09:58:38.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2022-04-14T09:58:38.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2022-04-14T09:58:38.897Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2022-04-14T09:58:38.898Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2022-04-14T09:58:38.899Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2022-04-14T09:58:38.900Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2022-04-14T09:58:38.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2022-04-14T09:58:38.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2022-04-14T09:58:38.902Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2022-04-14T09:58:38.903Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2022-04-14T09:58:38.904Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2022-04-14T09:58:38.905Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2022-04-14T09:58:38.906Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Config (AD_Zebra_Config)`
-- 2022-04-14T09:58:38.907Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2022-04-14T09:58:38.908Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2022-04-14T09:58:38.909Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2022-04-14T09:58:38.909Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2022-04-14T09:58:38.910Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2022-04-14T09:58:38.911Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2022-04-14T09:58:38.912Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2022-04-14T09:58:38.913Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2022-04-14T09:58:38.914Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV_Missing_Counter_Documents (RV_Missing_Counter_Documents)`
-- 2022-04-14T09:58:38.915Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2022-04-14T09:58:38.915Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2022-04-14T09:58:38.916Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2022-04-14T09:58:38.917Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2022-04-14T09:58:38.918Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2022-04-14T09:58:38.919Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2022-04-14T09:58:38.920Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2022-04-14T09:58:38.921Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2022-04-14T09:58:38.922Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2022-04-14T09:58:38.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2022-04-14T09:58:38.923Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2022-04-14T09:58:38.924Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Kontenrahmen (I_ElementValue)`
-- 2022-04-14T09:58:38.925Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2022-04-14T09:58:38.926Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2022-04-14T09:58:38.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2022-04-14T09:58:38.927Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2022-04-14T09:58:38.928Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2022-04-14T09:58:38.929Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2022-04-14T09:58:38.930Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2022-04-14T09:58:38.931Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2022-04-14T09:58:38.932Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2022-04-14T09:58:38.932Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2022-04-14T09:58:38.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2022-04-14T09:58:38.934Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-04-14T09:58:38.935Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-04-14T09:58:38.936Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-04-14T09:58:38.937Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2022-04-14T09:58:38.938Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2022-04-14T09:58:38.939Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2022-04-14T09:58:38.940Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Rollen-Zugriff aktualisieren (org.compiere.process.RoleAccessUpdate)`
-- 2022-04-14T09:58:38.941Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2022-04-14T09:58:38.941Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2022-04-14T09:58:38.942Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Config (GeocodingConfig)`
-- 2022-04-14T09:58:38.943Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2022-04-14T09:58:38.944Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2022-04-14T09:58:38.945Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2022-04-14T09:58:38.946Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2022-04-14T09:58:38.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;

-- Node name: `External system config eBay`
-- 2022-04-14T09:58:38.947Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541925 AND AD_Tree_ID=10
;

-- Reordering children of `System`
-- Node name: `API Audit`
-- 2022-04-14T09:58:48.806Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541725 AND AD_Tree_ID=10
;

-- Node name: `External system config Shopware 6 (ExternalSystem_Config_Shopware6)`
-- 2022-04-14T09:58:48.807Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541702 AND AD_Tree_ID=10
;

-- Node name: `External system config eBay`
-- 2022-04-14T09:58:48.808Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541925 AND AD_Tree_ID=10
;

-- Node name: `External System (ExternalSystem_Config)`
-- 2022-04-14T09:58:48.809Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541585 AND AD_Tree_ID=10
;

-- Node name: `External system log (ExternalSystem_Config_PInstance_Log_v)`
-- 2022-04-14T09:58:48.810Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541600 AND AD_Tree_ID=10
;

-- Node name: `PostgREST Configs (S_PostgREST_Config)`
-- 2022-04-14T09:58:48.811Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541481 AND AD_Tree_ID=10
;

-- Node name: `External reference (S_ExternalReference)`
-- 2022-04-14T09:58:48.812Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541456 AND AD_Tree_ID=10
;

-- Node name: `EMail`
-- 2022-04-14T09:58:48.813Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541134 AND AD_Tree_ID=10
;

-- Node name: `Test`
-- 2022-04-14T09:58:48.814Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541474 AND AD_Tree_ID=10
;

-- Node name: `Full Text Search`
-- 2022-04-14T09:58:48.815Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541111 AND AD_Tree_ID=10
;

-- Node name: `Asynchronous processing`
-- 2022-04-14T09:58:48.815Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541416 AND AD_Tree_ID=10
;

-- Node name: `Scan Barcode (de.metas.ui.web.globalaction.process.GlobalActionReadProcess)`
-- 2022-04-14T09:58:48.816Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541142 AND AD_Tree_ID=10
;

-- Node name: `Async workpackage queue (C_Queue_WorkPackage)`
-- 2022-04-14T09:58:48.817Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540892 AND AD_Tree_ID=10
;

-- Node name: `Scheduler (AD_Scheduler)`
-- 2022-04-14T09:58:48.818Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540969 AND AD_Tree_ID=10
;

-- Node name: `Batch (C_Async_Batch)`
-- 2022-04-14T09:58:48.819Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540914 AND AD_Tree_ID=10
;

-- Node name: `Role (AD_Role)`
-- 2022-04-14T09:58:48.820Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=150 AND AD_Tree_ID=10
;

-- Node name: `Batch Type (C_Async_Batch_Type)`
-- 2022-04-14T09:58:48.821Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540915 AND AD_Tree_ID=10
;

-- Node name: `User (AD_User)`
-- 2022-04-14T09:58:48.822Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=147 AND AD_Tree_ID=10
;

-- Node name: `Counter Document (C_DocTypeCounter)`
-- 2022-04-14T09:58:48.823Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541539 AND AD_Tree_ID=10
;

-- Node name: `Users Group (AD_UserGroup)`
-- 2022-04-14T09:58:48.824Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541216 AND AD_Tree_ID=10
;

-- Node name: `User Record Access (AD_User_Record_Access)`
-- 2022-04-14T09:58:48.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541263 AND AD_Tree_ID=10
;

-- Node name: `Language (AD_Language)`
-- 2022-04-14T09:58:48.825Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=145 AND AD_Tree_ID=10
;

-- Node name: `Menu (AD_Menu)`
-- 2022-04-14T09:58:48.826Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=144 AND AD_Tree_ID=10
;

-- Node name: `User Dashboard (WEBUI_Dashboard)`
-- 2022-04-14T09:58:48.827Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540743 AND AD_Tree_ID=10
;

-- Node name: `KPI (WEBUI_KPI)`
-- 2022-04-14T09:58:48.828Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540784 AND AD_Tree_ID=10
;

-- Node name: `Document Type (C_DocType)`
-- 2022-04-14T09:58:48.829Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540826 AND AD_Tree_ID=10
;

-- Node name: `Boiler Plate (AD_BoilerPlate)`
-- 2022-04-14T09:58:48.830Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540898 AND AD_Tree_ID=10
;

-- Node name: `Boilerplate Translation (AD_BoilerPlate_Trl)`
-- 2022-04-14T09:58:48.831Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541476 AND AD_Tree_ID=10
;

-- Node name: `Document Type Printing Options (C_DocType_PrintOptions)`
-- 2022-04-14T09:58:48.832Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541563 AND AD_Tree_ID=10
;

-- Node name: `Text Variable (AD_BoilerPlate_Var)`
-- 2022-04-14T09:58:48.833Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540895 AND AD_Tree_ID=10
;

-- Node name: `Print Format (AD_PrintFormat)`
-- 2022-04-14T09:58:48.834Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540827 AND AD_Tree_ID=10
;

-- Node name: `Zebra Config (AD_Zebra_Config)`
-- 2022-04-14T09:58:48.835Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541599 AND AD_Tree_ID=10
;

-- Node name: `Document Sequence (AD_Sequence)`
-- 2022-04-14T09:58:48.836Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540828 AND AD_Tree_ID=10
;

-- Node name: `My Profile (AD_User)`
-- 2022-04-14T09:58:48.837Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540849 AND AD_Tree_ID=10
;

-- Node name: `Printing Queue (C_Printing_Queue)`
-- 2022-04-14T09:58:48.838Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=34, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540911 AND AD_Tree_ID=10
;

-- Node name: `Druck-Jobs (C_Print_Job)`
-- 2022-04-14T09:58:48.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=35, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540427 AND AD_Tree_ID=10
;

-- Node name: `Druckpaket (C_Print_Package)`
-- 2022-04-14T09:58:48.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=36, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540438 AND AD_Tree_ID=10
;

-- Node name: `Printer (AD_PrinterHW)`
-- 2022-04-14T09:58:48.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=37, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540912 AND AD_Tree_ID=10
;

-- Node name: `Printer Mapping (AD_Printer_Config)`
-- 2022-04-14T09:58:48.841Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=38, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540913 AND AD_Tree_ID=10
;

-- Node name: `Printer-Tray-Mapping (AD_Printer_Matching)`
-- 2022-04-14T09:58:48.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=39, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541478 AND AD_Tree_ID=10
;

-- Node name: `RV_Missing_Counter_Documents (RV_Missing_Counter_Documents)`
-- 2022-04-14T09:58:48.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=40, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541540 AND AD_Tree_ID=10
;

-- Node name: `System Configuration (AD_SysConfig)`
-- 2022-04-14T09:58:48.844Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=41, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540894 AND AD_Tree_ID=10
;

-- Node name: `Prozess Revision (AD_PInstance)`
-- 2022-04-14T09:58:48.845Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=42, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540917 AND AD_Tree_ID=10
;

-- Node name: `Session Audit (AD_Session)`
-- 2022-04-14T09:58:48.846Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=43, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540982 AND AD_Tree_ID=10
;

-- Node name: `Logischer Drucker (AD_Printer)`
-- 2022-04-14T09:58:48.847Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=44, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541414 AND AD_Tree_ID=10
;

-- Node name: `Change Log (AD_ChangeLog)`
-- 2022-04-14T09:58:48.848Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=45, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540919 AND AD_Tree_ID=10
;

-- Node name: `Import Business Partner (I_BPartner)`
-- 2022-04-14T09:58:48.849Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=46, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540983 AND AD_Tree_ID=10
;

-- Node name: `Export Processor (EXP_Processor)`
-- 2022-04-14T09:58:48.850Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=47, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53101 AND AD_Tree_ID=10
;

-- Node name: `Import Product (I_Product)`
-- 2022-04-14T09:58:48.850Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=48, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541080 AND AD_Tree_ID=10
;

-- Node name: `Import Replenishment (I_Replenish)`
-- 2022-04-14T09:58:48.851Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=49, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541273 AND AD_Tree_ID=10
;

-- Node name: `Import Inventory (I_Inventory)`
-- 2022-04-14T09:58:48.852Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=50, Updated=now(), UpdatedBy=100 WHERE  Node_ID=363 AND AD_Tree_ID=10
;

-- Node name: `Import Discount Schema (I_DiscountSchema)`
-- 2022-04-14T09:58:48.853Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=51, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541079 AND AD_Tree_ID=10
;

-- Node name: `Import Kontenrahmen (I_ElementValue)`
-- 2022-04-14T09:58:48.854Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=52, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541108 AND AD_Tree_ID=10
;

-- Node name: `Import Format (AD_ImpFormat)`
-- 2022-04-14T09:58:48.855Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=53, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541053 AND AD_Tree_ID=10
;

-- Node name: `Data import (C_DataImport)`
-- 2022-04-14T09:58:48.856Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=54, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541052 AND AD_Tree_ID=10
;

-- Node name: `Data Import Run (C_DataImport_Run)`
-- 2022-04-14T09:58:48.857Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=55, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541513 AND AD_Tree_ID=10
;

-- Node name: `Import Postal Data (I_Postal)`
-- 2022-04-14T09:58:48.858Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=56, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541233 AND AD_Tree_ID=10
;

-- Node name: `Import Processor (IMP_Processor)`
-- 2022-04-14T09:58:48.859Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=57, Updated=now(), UpdatedBy=100 WHERE  Node_ID=53103 AND AD_Tree_ID=10
;

-- Node name: `Import Processor Log (IMP_ProcessorLog)`
-- 2022-04-14T09:58:48.860Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=58, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541389 AND AD_Tree_ID=10
;

-- Node name: `Eingabequelle (AD_InputDataSource)`
-- 2022-04-14T09:58:48.860Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=59, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540243 AND AD_Tree_ID=10
;

-- Node name: `Accounting Export Format (DATEV_ExportFormat)`
-- 2022-04-14T09:58:48.861Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=60, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541041 AND AD_Tree_ID=10
;

-- Node name: `Message (AD_Message)`
-- 2022-04-14T09:58:48.863Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=61, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541104 AND AD_Tree_ID=10
;

-- Node name: `Event Log (AD_EventLog)`
-- 2022-04-14T09:58:48.864Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=62, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541109 AND AD_Tree_ID=10
;

-- Node name: `Anhang (AD_AttachmentEntry)`
-- 2022-04-14T09:58:48.865Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=63, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541162 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2022-04-14T09:58:48.866Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=64, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000099 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2022-04-14T09:58:48.866Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=65, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000100 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2022-04-14T09:58:48.867Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=66, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000101 AND AD_Tree_ID=10
;

-- Node name: `Extended Windows`
-- 2022-04-14T09:58:48.868Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=67, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540901 AND AD_Tree_ID=10
;

-- Node name: `Attachment changelog (AD_Attachment_Log)`
-- 2022-04-14T09:58:48.869Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=68, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541282 AND AD_Tree_ID=10
;

-- Node name: `Fix Native Sequences (de.metas.process.ExecuteUpdateSQL)`
-- 2022-04-14T09:58:48.870Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=69, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541339 AND AD_Tree_ID=10
;

-- Node name: `Rollen-Zugriff aktualisieren (org.compiere.process.RoleAccessUpdate)`
-- 2022-04-14T09:58:48.871Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=70, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541326 AND AD_Tree_ID=10
;

-- Node name: `User Record Access Update from BPartner Hierachy (de.metas.security.permissions.bpartner_hierarchy.process.AD_User_Record_Access_UpdateFrom_BPartnerHierarchy)`
-- 2022-04-14T09:58:48.872Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=71, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541417 AND AD_Tree_ID=10
;

-- Node name: `Belege verarbeiten (org.adempiere.ad.process.ProcessDocuments)`
-- 2022-04-14T09:58:48.873Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=72, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540631 AND AD_Tree_ID=10
;

-- Node name: `Geocoding Config (GeocodingConfig)`
-- 2022-04-14T09:58:48.874Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=73, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541374 AND AD_Tree_ID=10
;

-- Node name: `Exported Data Audit (Data_Export_Audit)`
-- 2022-04-14T09:58:48.875Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=74, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541752 AND AD_Tree_ID=10
;

-- Node name: `External System Service (ExternalSystem_Service)`
-- 2022-04-14T09:58:48.876Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=75, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541861 AND AD_Tree_ID=10
;

-- Node name: `External System Service Instance (ExternalSystem_Service_Instance)`
-- 2022-04-14T09:58:48.877Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=76, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541862 AND AD_Tree_ID=10
;

-- Node name: `Print Scale Devices QR Codes (de.metas.devices.webui.process.PrintDeviceQRCodes)`
-- 2022-04-14T09:58:48.878Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000098, SeqNo=77, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541906 AND AD_Tree_ID=10
;




-- 2022-04-14T10:06:21.489Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,545737,548802,TO_TIMESTAMP('2022-04-14 13:06:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','IDs',20,TO_TIMESTAMP('2022-04-14 13:06:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config eBay -> eBay.App ID
-- Column: ExternalSystem_Config_Ebay.AppId
-- 2022-04-14T10:06:30.742Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548802, SeqNo=10,Updated=TO_TIMESTAMP('2022-04-14 13:06:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605332
;

-- UI Element: External system config eBay -> eBay.Cert ID
-- Column: ExternalSystem_Config_Ebay.CertId
-- 2022-04-14T10:06:38.209Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548802, SeqNo=20,Updated=TO_TIMESTAMP('2022-04-14 13:06:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605333
;

-- UI Element: External system config eBay -> eBay.Dev ID
-- Column: ExternalSystem_Config_Ebay.DevId
-- 2022-04-14T10:06:46.765Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548802, SeqNo=30,Updated=TO_TIMESTAMP('2022-04-14 13:06:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605334
;

-- UI Element: External system config eBay -> eBay.External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T10:07:58.388Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=548796, SeqNo=100,Updated=TO_TIMESTAMP('2022-04-14 13:07:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605336
;

-- UI Element: External system config eBay -> eBay.External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T10:08:13.875Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2022-04-14 13:08:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605336
;

-- UI Element: External system config eBay -> eBay.Suchschlüssel
-- Column: ExternalSystem_Config_Ebay.ExternalSystemValue
-- 2022-04-14T10:08:17.143Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2022-04-14 13:08:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605337
;

-- UI Element: External system config eBay -> eBay.API Mode
-- Column: ExternalSystem_Config_Ebay.API_Mode
-- 2022-04-14T10:08:20.415Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2022-04-14 13:08:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605335
;

-- UI Element: External system config eBay -> eBay.External System Config
-- Column: ExternalSystem_Config_Ebay.ExternalSystem_Config_ID
-- 2022-04-14T10:09:02.210Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605336
;

-- UI Element: External system config eBay -> eBay.Suchschlüssel
-- Column: ExternalSystem_Config_Ebay.ExternalSystemValue
-- 2022-04-14T10:09:02.217Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605337
;

-- UI Element: External system config eBay -> eBay.API Mode
-- Column: ExternalSystem_Config_Ebay.API_Mode
-- 2022-04-14T10:09:02.224Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605335
;

-- UI Element: External system config eBay -> eBay.Aktiv
-- Column: ExternalSystem_Config_Ebay.IsActive
-- 2022-04-14T10:09:02.230Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605331
;

-- UI Element: External system config eBay -> eBay.App ID
-- Column: ExternalSystem_Config_Ebay.AppId
-- 2022-04-14T10:09:02.237Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605332
;

-- UI Element: External system config eBay -> eBay.Cert ID
-- Column: ExternalSystem_Config_Ebay.CertId
-- 2022-04-14T10:09:02.243Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605333
;

-- UI Element: External system config eBay -> eBay.Dev ID
-- Column: ExternalSystem_Config_Ebay.DevId
-- 2022-04-14T10:09:02.249Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605334
;

-- UI Element: External system config eBay -> eBay.Refresh Token
-- Column: ExternalSystem_Config_Ebay.RefreshToken
-- 2022-04-14T10:09:02.256Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605338
;

-- UI Element: External system config eBay -> eBay.Preisliste
-- Column: ExternalSystem_Config_Ebay.M_PriceList_ID
-- 2022-04-14T10:09:02.263Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-04-14 13:09:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605339
;

-- UI Element: External system config eBay -> eBay.Sektion
-- Column: ExternalSystem_Config_Ebay.AD_Org_ID
-- 2022-04-14T10:09:10.890Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-04-14 13:09:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605330
;





-- Table: ExternalSystem_Config_Ebay_Mapping
-- 2022-04-14T16:11:14.041806400Z
UPDATE AD_Table SET AD_Window_ID=541468,Updated=TO_TIMESTAMP('2022-04-14 19:11:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542124
;

-- Table: ExternalSystem_Config_Ebay
-- 2022-04-14T16:11:40.975205600Z
UPDATE AD_Table SET AD_Window_ID=541468,Updated=TO_TIMESTAMP('2022-04-14 19:11:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=541741
;
