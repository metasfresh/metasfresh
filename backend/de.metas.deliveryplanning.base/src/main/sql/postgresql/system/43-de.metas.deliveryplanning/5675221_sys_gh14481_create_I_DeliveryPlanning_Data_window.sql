-- 2023-02-02T17:28:37.251Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582003,0,TO_TIMESTAMP('2023-02-02 19:28:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Planning Import','Delivery Planning Import',TO_TIMESTAMP('2023-02-02 19:28:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:28:37.252Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582003 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-02-02T17:28:52.437Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Import', PrintName='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 19:28:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582003 AND AD_Language='de_CH'
;

-- 2023-02-02T17:28:52.439Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582003,'de_CH') 
;

-- Element: null
-- 2023-02-02T17:28:55.591Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Import', PrintName='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 19:28:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582003 AND AD_Language='de_DE'
;

-- 2023-02-02T17:28:55.591Z
UPDATE AD_Element SET Name='Lieferplanung Import', PrintName='Lieferplanung Import' WHERE AD_Element_ID=582003
;

-- 2023-02-02T17:28:55.872Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582003,'de_DE') 
;

-- 2023-02-02T17:28:55.873Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582003,'de_DE') 
;

-- Element: null
-- 2023-02-02T17:28:59.517Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Import', PrintName='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 19:28:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582003 AND AD_Language='nl_NL'
;

-- 2023-02-02T17:28:59.519Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582003,'nl_NL') 
;

-- Element: null
-- 2023-02-02T17:29:28.676Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Import', PrintName='Lieferplanung Import',Updated=TO_TIMESTAMP('2023-02-02 19:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582003 AND AD_Language='fr_CH'
;

-- 2023-02-02T17:29:28.678Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582003,'fr_CH') 
;

-- Window: Lieferplanung Import, InternalName=null
-- 2023-02-02T17:30:17.797Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,581998,0,541671,TO_TIMESTAMP('2023-02-02 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','N','N','Y','N','N','N','Y','Lieferplanung Import','N',TO_TIMESTAMP('2023-02-02 19:30:17','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-02T17:30:17.799Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541671 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-02T17:30:17.802Z
/* DDL */  select update_window_translation_from_ad_element(581998) 
;

-- 2023-02-02T17:30:17.814Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541671
;

-- 2023-02-02T17:30:17.815Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541671)
;

-- Tab: Lieferplanung Import(541671,D) -> Lieferplanung Import
-- Table: I_DeliveryPlanning
-- 2023-02-02T17:31:12.142Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581998,0,546796,542290,541671,'Y',TO_TIMESTAMP('2023-02-02 19:31:11','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','I_DeliveryPlanning','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Lieferplanung Import','N',10,0,TO_TIMESTAMP('2023-02-02 19:31:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:12.143Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546796 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-02T17:31:12.145Z
/* DDL */  select update_tab_translation_from_ad_element(581998) 
;

-- 2023-02-02T17:31:12.148Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546796)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Mandant
-- Column: I_DeliveryPlanning.AD_Client_ID
-- 2023-02-02T17:31:39.997Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585742,712041,0,546796,TO_TIMESTAMP('2023-02-02 19:31:39','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-02-02 19:31:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:39.999Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:40Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-02T17:31:41.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712041
;

-- 2023-02-02T17:31:41.016Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712041)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Sektion
-- Column: I_DeliveryPlanning.AD_Org_ID
-- 2023-02-02T17:31:41.124Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585743,712042,0,546796,TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:41.125Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:41.126Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-02T17:31:41.339Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712042
;

-- 2023-02-02T17:31:41.340Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712042)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Lieferplanung Import
-- Column: I_DeliveryPlanning.I_DeliveryPlanning_ID
-- 2023-02-02T17:31:41.444Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585744,712043,0,546796,TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Lieferplanung Import',TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:41.445Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:41.446Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581998) 
;

-- 2023-02-02T17:31:41.449Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712043
;

-- 2023-02-02T17:31:41.449Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712043)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Dateiname
-- Column: I_DeliveryPlanning.FileName
-- 2023-02-02T17:31:41.545Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585747,712044,0,546796,TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL',1024,'D','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','N','N','N','N','N','Dateiname',TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:41.546Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:41.547Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2023-02-02T17:31:41.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712044
;

-- 2023-02-02T17:31:41.554Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712044)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Import-Zeitpunkt
-- Column: I_DeliveryPlanning.Imported
-- 2023-02-02T17:31:41.647Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585748,712045,0,546796,TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Import-Zeitpunkt',TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:41.647Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:41.648Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581560) 
;

-- 2023-02-02T17:31:41.651Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712045
;

-- 2023-02-02T17:31:41.651Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712045)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Aktiv
-- Column: I_DeliveryPlanning.IsActive
-- 2023-02-02T17:31:41.735Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585749,712046,0,546796,TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:41.736Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:41.737Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-02T17:31:41.943Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712046
;

-- 2023-02-02T17:31:41.944Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712046)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Verarbeitet
-- Column: I_DeliveryPlanning.Processed
-- 2023-02-02T17:31:42.050Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585751,712047,0,546796,TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-02-02 19:31:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:42.051Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:42.052Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-02-02T17:31:42.064Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712047
;

-- 2023-02-02T17:31:42.065Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712047)
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Sektionskennung
-- Column: I_DeliveryPlanning.M_SectionCode_ID
-- 2023-02-02T17:31:42.148Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585754,712048,0,546796,TO_TIMESTAMP('2023-02-02 19:31:42','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-02-02 19:31:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:31:42.149Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T17:31:42.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-02-02T17:31:42.156Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712048
;

-- 2023-02-02T17:31:42.156Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712048)
;

-- Tab: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D)
-- UI Section: main
-- 2023-02-02T17:32:02.641Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,UIStyle,Updated,UpdatedBy,Value) VALUES (0,0,546796,545426,TO_TIMESTAMP('2023-02-02 19:32:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'main',TO_TIMESTAMP('2023-02-02 19:32:02','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-02T17:32:02.642Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545426 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main
-- UI Column: 10
-- 2023-02-02T17:32:07.609Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546617,545426,TO_TIMESTAMP('2023-02-02 19:32:07','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-02 19:32:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-02T17:35:46.223Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546617,550332,TO_TIMESTAMP('2023-02-02 19:35:46','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-02-02 19:35:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 10 -> main.Dateiname
-- Column: I_DeliveryPlanning.FileName
-- 2023-02-02T17:37:10.971Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712044,0,546796,550332,615514,'F',TO_TIMESTAMP('2023-02-02 19:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','Y','N','N','N',0,'Dateiname',10,0,0,TO_TIMESTAMP('2023-02-02 19:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 10 -> main.Import-Zeitpunkt
-- Column: I_DeliveryPlanning.Imported
-- 2023-02-02T17:37:55.674Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712045,0,546796,550332,615515,'F',TO_TIMESTAMP('2023-02-02 19:37:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import-Zeitpunkt',20,0,0,TO_TIMESTAMP('2023-02-02 19:37:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 10 -> main.Verarbeitet
-- Column: I_DeliveryPlanning.Processed
-- 2023-02-02T17:38:26.523Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712047,0,546796,550332,615516,'F',TO_TIMESTAMP('2023-02-02 19:38:26','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',30,0,0,TO_TIMESTAMP('2023-02-02 19:38:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main
-- UI Column: 20
-- 2023-02-02T17:38:34.988Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546618,545426,TO_TIMESTAMP('2023-02-02 19:38:34','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-02 19:38:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 20
-- UI Element Group: main
-- 2023-02-02T17:38:45.480Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546618,550333,TO_TIMESTAMP('2023-02-02 19:38:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-02-02 19:38:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 20 -> main.Sektion
-- Column: I_DeliveryPlanning.AD_Org_ID
-- 2023-02-02T17:39:25.503Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712042,0,546796,550333,615517,'F',TO_TIMESTAMP('2023-02-02 19:39:25','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2023-02-02 19:39:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 20 -> main.Verarbeitet
-- Column: I_DeliveryPlanning.Processed
-- 2023-02-02T17:39:39.694Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712047,0,546796,550333,615518,'F',TO_TIMESTAMP('2023-02-02 19:39:39','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',10,0,0,TO_TIMESTAMP('2023-02-02 19:39:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 20 -> main.Mandant
-- Column: I_DeliveryPlanning.AD_Client_ID
-- 2023-02-02T17:39:50.732Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712041,0,546796,550333,615519,'F',TO_TIMESTAMP('2023-02-02 19:39:50','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',30,0,0,TO_TIMESTAMP('2023-02-02 19:39:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 10 -> main.Verarbeitet
-- 2023-02-02T17:40:16.783Z
UPDATE AD_UI_Element SET AD_Field_ID=NULL,Updated=TO_TIMESTAMP('2023-02-02 19:40:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615516
;

-- UI Element: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> main -> 10 -> main.Sektionskennung
-- Column: I_DeliveryPlanning.M_SectionCode_ID
-- 2023-02-02T17:40:53.882Z
UPDATE AD_UI_Element SET AD_Field_ID=712048, Description=NULL, Help=NULL, Name='Sektionskennung',Updated=TO_TIMESTAMP('2023-02-02 19:40:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615516
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Import-Zeitpunkt
-- Column: I_DeliveryPlanning.Imported
-- 2023-02-02T17:41:05.428Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 19:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712045
;

-- Field: Lieferplanung Import(541671,D) -> Lieferplanung Import(546796,D) -> Dateiname
-- Column: I_DeliveryPlanning.FileName
-- 2023-02-02T17:41:26.343Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 19:41:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712044
;

-- Name: Lieferplanung Import
-- Action Type: W
-- Window: Lieferplanung Import(541671,D)
-- 2023-02-02T17:43:26.492Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,581998,542046,0,541671,TO_TIMESTAMP('2023-02-02 19:43:26','YYYY-MM-DD HH24:MI:SS'),100,'D','I_DeliveryPlanning','Y','N','N','N','N','Lieferplanung Import',TO_TIMESTAMP('2023-02-02 19:43:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T17:43:26.493Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542046 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-02T17:43:26.495Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542046, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542046)
;

-- 2023-02-02T17:43:26.505Z
/* DDL */  select update_menu_translation_from_ad_element(581998) 
;

-- Reordering children of `Logistics`
-- Node name: `Tour (M_Tour)`
-- 2023-02-02T17:43:34.691Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540796 AND AD_Tree_ID=10
;

-- Node name: `Tourversion (M_TourVersion)`
-- 2023-02-02T17:43:34.692Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540798 AND AD_Tree_ID=10
;

-- Node name: `Delivery Days (M_DeliveryDay)`
-- 2023-02-02T17:43:34.692Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540797 AND AD_Tree_ID=10
;

-- Node name: `Distribution Order (DD_Order)`
-- 2023-02-02T17:43:34.693Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540829 AND AD_Tree_ID=10
;

-- Node name: `Distributions Editor (DD_OrderLine)`
-- 2023-02-02T17:43:34.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540973 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction (M_HU_PI)`
-- 2023-02-02T17:43:34.694Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540830 AND AD_Tree_ID=10
;

-- Node name: `Packing Instruction Version (M_HU_PI_Version)`
-- 2023-02-02T17:43:34.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540831 AND AD_Tree_ID=10
;

-- Node name: `CU-TU Allocation (M_HU_PI_Item_Product)`
-- 2023-02-02T17:43:34.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541375 AND AD_Tree_ID=10
;

-- Node name: `Packing Material (M_HU_PackingMaterial)`
-- 2023-02-02T17:43:34.696Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540844 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (M_HU)`
-- 2023-02-02T17:43:34.697Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540846 AND AD_Tree_ID=10
;

-- Node name: `Packaging code (M_HU_PackagingCode)`
-- 2023-02-02T17:43:34.698Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541384 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit Transaction (M_HU_Trx_Line)`
-- 2023-02-02T17:43:34.699Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540977 AND AD_Tree_ID=10
;

-- Node name: `Handling Unit (HU) Tracing (M_HU_Trace)`
-- 2023-02-02T17:43:34.699Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540900 AND AD_Tree_ID=10
;

-- Node name: `Delivery Planning (M_Delivery_Planning)`
-- 2023-02-02T17:43:34.700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542021 AND AD_Tree_ID=10
;

-- Node name: `Delivery Instruction (M_ShipperTransportation)`
-- 2023-02-02T17:43:34.700Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542032 AND AD_Tree_ID=10
;

-- Node name: `Transport Disposition (M_Tour_Instance)`
-- 2023-02-02T17:43:34.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540856 AND AD_Tree_ID=10
;

-- Node name: `Transport Delivery (M_DeliveryDay_Alloc)`
-- 2023-02-02T17:43:34.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540857 AND AD_Tree_ID=10
;

-- Node name: `Material Transactions (M_Transaction)`
-- 2023-02-02T17:43:34.702Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540860 AND AD_Tree_ID=10
;

-- Node name: `Transportation Order (M_ShipperTransportation)`
-- 2023-02-02T17:43:34.703Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540866 AND AD_Tree_ID=10
;

-- Node name: `Package (M_Package)`
-- 2023-02-02T17:43:34.703Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541057 AND AD_Tree_ID=10
;

-- Node name: `Internal Use (M_Inventory)`
-- 2023-02-02T17:43:34.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540918 AND AD_Tree_ID=10
;

-- Node name: `GO! Delivery Orders (GO_DeliveryOrder)`
-- 2023-02-02T17:43:34.704Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=21, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541011 AND AD_Tree_ID=10
;

-- Node name: `Der Kurier Delivery Orders (DerKurier_DeliveryOrder)`
-- 2023-02-02T17:43:34.705Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=22, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541083 AND AD_Tree_ID=10
;

-- Node name: `DHL Delivery Order (DHL_ShipmentOrder)`
-- 2023-02-02T17:43:34.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=23, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541388 AND AD_Tree_ID=10
;

-- Node name: `DPD Delivery Order (DPD_StoreOrder)`
-- 2023-02-02T17:43:34.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=24, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541394 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-02-02T17:43:34.707Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=25, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000057 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-02-02T17:43:34.708Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=26, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000065 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-02-02T17:43:34.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=27, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000075 AND AD_Tree_ID=10
;

-- Node name: `HU Reservierung (M_HU_Reservation)`
-- 2023-02-02T17:43:34.709Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=28, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541117 AND AD_Tree_ID=10
;

-- Node name: `Service Handling Units (M_HU)`
-- 2023-02-02T17:43:34.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=29, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541572 AND AD_Tree_ID=10
;

-- Node name: `HU QR Code (M_HU_QRCode)`
-- 2023-02-02T17:43:34.710Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=30, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541905 AND AD_Tree_ID=10
;

-- Node name: `Means of Transportation (M_MeansOfTransportation)`
-- 2023-02-02T17:43:34.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=31, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542024 AND AD_Tree_ID=10
;

-- Node name: `Department (M_Department)`
-- 2023-02-02T17:43:34.711Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=32, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542041 AND AD_Tree_ID=10
;

-- Node name: `Lieferplanung Import`
-- 2023-02-02T17:43:34.712Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000016, SeqNo=33, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542046 AND AD_Tree_ID=10
;

-- Element: I_DeliveryPlanning_ID
-- 2023-02-02T17:44:23.827Z
UPDATE AD_Element_Trl SET Name='Delivery Planning Import', PrintName='Delivery Planning Import',Updated=TO_TIMESTAMP('2023-02-02 19:44:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998 AND AD_Language='en_US'
;

-- 2023-02-02T17:44:23.829Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'en_US') 
;

-- Column: I_DeliveryPlanning.M_SectionCode_ID
-- 2023-02-02T17:45:37.915Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-02-02 19:45:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585754
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Datenimport(546796,D) -> Verarbeitet
-- Column: I_DeliveryPlanning_Data.Processed
-- 2023-02-02T21:44:22.416Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 23:44:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712047
;



-- Rename I_DeliveryPlanning to I_DeliveryPlanning_Data

-- Element: I_DeliveryPlanning_Data_ID
-- 2023-02-02T21:42:48.110Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Datenimport', PrintName='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 23:42:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998 AND AD_Language='de_CH'
;

-- 2023-02-02T21:42:48.112Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'de_CH') 
;

-- Element: I_DeliveryPlanning_Data_ID
-- 2023-02-02T21:42:50.158Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Datenimport', PrintName='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 23:42:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998 AND AD_Language='de_DE'
;

-- 2023-02-02T21:42:50.159Z
UPDATE AD_Element SET Name='Lieferplanung Datenimport', PrintName='Lieferplanung Datenimport' WHERE AD_Element_ID=581998
;

-- 2023-02-02T21:42:50.428Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(581998,'de_DE') 
;

-- 2023-02-02T21:42:50.429Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'de_DE') 
;

-- Element: I_DeliveryPlanning_Data_ID
-- 2023-02-02T21:42:52.851Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Datenimport', PrintName='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 23:42:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998 AND AD_Language='fr_CH'
;

-- 2023-02-02T21:42:52.852Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'fr_CH') 
;

-- Element: I_DeliveryPlanning_Data_ID
-- 2023-02-02T21:43:00.203Z
UPDATE AD_Element_Trl SET Name='Lieferplanung Datenimport', PrintName='Lieferplanung Datenimport',Updated=TO_TIMESTAMP('2023-02-02 23:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998 AND AD_Language='nl_NL'
;

-- 2023-02-02T21:43:00.205Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'nl_NL') 
;

-- Element: I_DeliveryPlanning_Data_ID
-- 2023-02-02T21:43:03.078Z
UPDATE AD_Element_Trl SET Name='Delivery Planning Data Import', PrintName='Delivery Planning Data Import',Updated=TO_TIMESTAMP('2023-02-02 23:43:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581998 AND AD_Language='en_US'
;

-- 2023-02-02T21:43:03.079Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581998,'en_US') 
;

-- Tab: Lieferplanung Datenimport(541671,D) -> Lieferplanung Datenimport
-- Table: I_DeliveryPlanning_Data
-- 2023-02-02T21:49:30.764Z
UPDATE AD_Tab SET InternalName='I_DeliveryPlanning_Data',Updated=TO_TIMESTAMP('2023-02-02 23:49:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546796
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Datenimport(546796,D) -> Bereit zur Verarbeitung
-- Column: I_DeliveryPlanning_Data.IsReadyForProcessing
-- 2023-02-03T13:48:59.366Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585842,712128,0,546796,0,TO_TIMESTAMP('2023-02-03 15:48:59','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Bereit zur Verarbeitung',0,10,0,1,1,TO_TIMESTAMP('2023-02-03 15:48:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-03T13:48:59.367Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712128 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-03T13:48:59.369Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541909) 
;

-- 2023-02-03T13:48:59.372Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712128
;

-- 2023-02-03T13:48:59.372Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712128)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Datenimport(546796,D) -> Bereit zur Verarbeitung
-- Column: I_DeliveryPlanning_Data.IsReadyForProcessing
-- 2023-02-03T13:49:08.552Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-03 15:49:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712128
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Datenimport(546796,D) -> main -> 20 -> main.Bereit zur Verarbeitung
-- Column: I_DeliveryPlanning_Data.IsReadyForProcessing
-- 2023-02-03T13:49:50.350Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712128,0,546796,550333,615561,'F',TO_TIMESTAMP('2023-02-03 15:49:50','YYYY-MM-DD HH24:MI:SS'),100,'Zeigt an, ob das Zusammentstellen eines Arbeitspakets abgeschlossen ist.','Y','N','N','Y','N','N','N',0,'Bereit zur Verarbeitung',5,0,0,TO_TIMESTAMP('2023-02-03 15:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;