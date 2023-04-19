-- 2023-02-27T14:28:34.390Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582103,0,TO_TIMESTAMP('2023-02-27 16:28:34','YYYY-MM-DD HH24:MI:SS'),100,'The `Change Business Partner Block` window allows changing the blocking status for multiple partners by attaching a file in TSV format which contains all the information needed and then trigger `Process file` process. A template for this file can be downloaded via `Download TSV File process`.','D','Y','Change Business Partner Block','Change Business Partner Block',TO_TIMESTAMP('2023-02-27 16:28:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:28:34.393Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582103 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Change Business Partner Block, InternalName=null
-- 2023-02-27T14:29:48.058Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,582103,0,541682,TO_TIMESTAMP('2023-02-27 16:29:47','YYYY-MM-DD HH24:MI:SS'),100,'The `Change Business Partner Block` window allows changing the blocking status for multiple partners by attaching a file in TSV format which contains all the information needed and then trigger `Process file` process. A template for this file can be downloaded via `Download TSV File process`.','D','Y','N','N','N','N','N','N','Y','Change Business Partner Block','N',TO_TIMESTAMP('2023-02-27 16:29:47','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-02-27T14:29:48.062Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=541682 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-02-27T14:29:48.065Z
/* DDL */  select update_window_translation_from_ad_element(582103) 
;

-- 2023-02-27T14:29:48.077Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541682
;

-- 2023-02-27T14:29:48.085Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541682)
;

-- 2023-02-27T14:34:37.811Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582104,0,TO_TIMESTAMP('2023-02-27 16:34:37','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Block file','Block file',TO_TIMESTAMP('2023-02-27 16:34:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:34:37.813Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582104 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-02-27T14:34:45.676Z
UPDATE AD_Element_Trl SET Name='Block Datei', PrintName='Block Datei',Updated=TO_TIMESTAMP('2023-02-27 16:34:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582104 AND AD_Language='de_DE'
;

-- 2023-02-27T14:34:45.677Z
UPDATE AD_Element SET Name='Block Datei', PrintName='Block Datei' WHERE AD_Element_ID=582104
;

-- 2023-02-27T14:34:46.164Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582104,'de_DE') 
;

-- 2023-02-27T14:34:46.168Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582104,'de_DE') 
;

-- Element: null
-- 2023-02-27T14:35:03.072Z
UPDATE AD_Element_Trl SET Name='Block Datei', PrintName='Block Datei',Updated=TO_TIMESTAMP('2023-02-27 16:35:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582104 AND AD_Language='de_CH'
;

-- 2023-02-27T14:35:03.074Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582104,'de_CH') 
;

-- Tab: Change Business Partner Block(541682,D) -> Block file
-- Table: C_BPartner_Block_File
-- 2023-02-27T14:37:01.860Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,582102,0,546841,542317,541682,'Y',TO_TIMESTAMP('2023-02-27 16:37:01','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_BPartner_Block_File','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Block file','N',10,0,TO_TIMESTAMP('2023-02-27 16:37:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:01.869Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546841 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-27T14:37:01.872Z
/* DDL */  select update_tab_translation_from_ad_element(582102) 
;

-- 2023-02-27T14:37:01.876Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546841)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Mandant
-- Column: C_BPartner_Block_File.AD_Client_ID
-- 2023-02-27T14:37:40.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586230,712709,0,546841,TO_TIMESTAMP('2023-02-27 16:37:39','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-02-27 16:37:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:40.033Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712709 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:40.039Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-27T14:37:40.523Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712709
;

-- 2023-02-27T14:37:40.523Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712709)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Sektion
-- Column: C_BPartner_Block_File.AD_Org_ID
-- 2023-02-27T14:37:40.633Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586231,712710,0,546841,TO_TIMESTAMP('2023-02-27 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-27 16:37:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:40.635Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712710 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:40.636Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-27T14:37:40.934Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712710
;

-- 2023-02-27T14:37:40.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712710)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Erstellt
-- Column: C_BPartner_Block_File.Created
-- 2023-02-27T14:37:41.023Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586232,712711,0,546841,TO_TIMESTAMP('2023-02-27 16:37:40','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-02-27 16:37:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:41.025Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712711 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:41.027Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-02-27T14:37:41.111Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712711
;

-- 2023-02-27T14:37:41.112Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712711)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Erstellt durch
-- Column: C_BPartner_Block_File.CreatedBy
-- 2023-02-27T14:37:41.201Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586233,712712,0,546841,TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:41.203Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712712 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:41.205Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-02-27T14:37:41.262Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712712
;

-- 2023-02-27T14:37:41.262Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712712)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Aktiv
-- Column: C_BPartner_Block_File.IsActive
-- 2023-02-27T14:37:41.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586234,712713,0,546841,TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:41.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712713 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:41.360Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-27T14:37:41.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712713
;

-- 2023-02-27T14:37:41.530Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712713)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Aktualisiert
-- Column: C_BPartner_Block_File.Updated
-- 2023-02-27T14:37:41.627Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586235,712714,0,546841,TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:41.628Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712714 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:41.629Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607) 
;

-- 2023-02-27T14:37:41.682Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712714
;

-- 2023-02-27T14:37:41.682Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712714)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Aktualisiert durch
-- Column: C_BPartner_Block_File.UpdatedBy
-- 2023-02-27T14:37:41.764Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586236,712715,0,546841,TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:41.765Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712715 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:41.768Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608) 
;

-- 2023-02-27T14:37:41.813Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712715
;

-- 2023-02-27T14:37:41.814Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712715)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Block file
-- Column: C_BPartner_Block_File.C_BPartner_Block_File_ID
-- 2023-02-27T14:37:41.905Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586237,712716,0,546841,TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Block file',TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:41.907Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712716 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:41.908Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582102) 
;

-- 2023-02-27T14:37:41.912Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712716
;

-- 2023-02-27T14:37:41.913Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712716)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Dateiname
-- Column: C_BPartner_Block_File.FileName
-- 2023-02-27T14:37:41.999Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586238,712717,0,546841,TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL',255,'D','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','N','N','N','N','N','Dateiname',TO_TIMESTAMP('2023-02-27 16:37:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:42Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712717 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:42.002Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2023-02-27T14:37:42.014Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712717
;

-- 2023-02-27T14:37:42.015Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712717)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Verarbeitet
-- Column: C_BPartner_Block_File.Processed
-- 2023-02-27T14:37:42.108Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586239,712718,0,546841,TO_TIMESTAMP('2023-02-27 16:37:42','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-02-27 16:37:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-27T14:37:42.110Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712718 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-27T14:37:42.112Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-02-27T14:37:42.141Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712718
;

-- 2023-02-27T14:37:42.142Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712718)
;

-- Tab: Change Business Partner Block(541682,D) -> Block file(546841,D)
-- UI Section: main
-- 2023-02-27T14:38:08.358Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy,Value) VALUES (0,0,546841,545461,TO_TIMESTAMP('2023-02-27 16:38:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-02-27 16:38:08','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-27T14:38:08.360Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545461 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main
-- UI Column: 10
-- 2023-02-27T14:38:13.109Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546669,545461,TO_TIMESTAMP('2023-02-27 16:38:13','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-27 16:38:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main
-- UI Column: 20
-- 2023-02-27T14:38:15.425Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546670,545461,TO_TIMESTAMP('2023-02-27 16:38:15','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2023-02-27 16:38:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-27T14:38:23.221Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546669,550410,TO_TIMESTAMP('2023-02-27 16:38:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-02-27 16:38:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 10 -> main.Dateiname
-- Column: C_BPartner_Block_File.FileName
-- 2023-02-27T14:38:36.428Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712717,0,546841,615914,550410,'F',TO_TIMESTAMP('2023-02-27 16:38:36','YYYY-MM-DD HH24:MI:SS'),100,'Name of the local file or URL','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','Y','N','N','N',0,'Dateiname',10,0,0,TO_TIMESTAMP('2023-02-27 16:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20
-- UI Element Group: flags
-- 2023-02-27T14:39:04.796Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546670,550411,TO_TIMESTAMP('2023-02-27 16:39:04','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2023-02-27 16:39:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20 -> flags.Aktiv
-- Column: C_BPartner_Block_File.IsActive
-- 2023-02-27T14:39:18.841Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712713,0,546841,615915,550411,'F',TO_TIMESTAMP('2023-02-27 16:39:18','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2023-02-27 16:39:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20 -> flags.Verarbeitet
-- Column: C_BPartner_Block_File.Processed
-- 2023-02-27T14:39:27.935Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712718,0,546841,615916,550411,'F',TO_TIMESTAMP('2023-02-27 16:39:27','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',20,0,0,TO_TIMESTAMP('2023-02-27 16:39:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20
-- UI Element Group: org
-- 2023-02-27T14:39:34.108Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546670,550412,TO_TIMESTAMP('2023-02-27 16:39:33','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2023-02-27 16:39:33','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20 -> org.Mandant
-- Column: C_BPartner_Block_File.AD_Client_ID
-- 2023-02-27T14:39:43.146Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712709,0,546841,615917,550412,'F',TO_TIMESTAMP('2023-02-27 16:39:43','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2023-02-27 16:39:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20 -> org.Sektion
-- Column: C_BPartner_Block_File.AD_Org_ID
-- 2023-02-27T14:39:54.251Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712710,0,546841,615918,550412,'F',TO_TIMESTAMP('2023-02-27 16:39:54','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2023-02-27 16:39:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Name: Change Business Partner Block
-- Action Type: W
-- 2023-02-28T05:49:08.207Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582103,542057,0,TO_TIMESTAMP('2023-02-28 07:49:07','YYYY-MM-DD HH24:MI:SS'),100,'The `Change Business Partner Block` window allows changing the blocking status for multiple partners by attaching a file in TSV format which contains all the information needed and then trigger `Process file` process. A template for this file can be downloaded via `Download TSV File process`.','D','Change Business Partner Block','Y','N','N','N','N','Change Business Partner Block',TO_TIMESTAMP('2023-02-28 07:49:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T05:49:08.211Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542057 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-28T05:49:08.217Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542057, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542057)
;

-- 2023-02-28T05:49:08.240Z
/* DDL */  select update_menu_translation_from_ad_element(582103) 
;

-- Reordering children of `Business Partner Rules`
-- Node name: `Business Partner Setup`
-- 2023-02-28T05:49:08.312Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=266 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group (C_BP_Group)`
-- 2023-02-28T05:49:08.312Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=232 AND AD_Tree_ID=10
;

-- Node name: `Greeting (C_Greeting)`
-- 2023-02-28T05:49:08.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=190 AND AD_Tree_ID=10
;

-- Node name: `Payment Term (C_PaymentTerm)`
-- 2023-02-28T05:49:08.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=127 AND AD_Tree_ID=10
;

-- Node name: `Invoice Schedule (C_InvoiceSchedule)`
-- 2023-02-28T05:49:08.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=133 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_Dunning)`
-- 2023-02-28T05:49:08.313Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=172 AND AD_Tree_ID=10
;

-- Node name: `Withholding (1099) (C_Withholding)`
-- 2023-02-28T05:49:08.314Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=173 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2023-02-28T05:49:08.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=110 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Info (C_BPartner)`
-- 2023-02-28T05:49:08.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=394 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Open`
-- 2023-02-28T05:49:08.315Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=512 AND AD_Tree_ID=10
;

-- Node name: `Validate Business Partner (org.compiere.process.BPartnerValidate)`
-- 2023-02-28T05:49:08.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=506 AND AD_Tree_ID=10
;

-- Node name: `Adressdaten-PLZ verifizieren (de.metas.location.process.C_Location_Postal_Validate)`
-- 2023-02-28T05:49:08.316Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540286 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Organization (org.compiere.process.OrgOwnership)`
-- 2023-02-28T05:49:08.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=420 AND AD_Tree_ID=10
;

-- Node name: `Partner Relation (C_BP_Relation)`
-- 2023-02-28T05:49:08.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=451 AND AD_Tree_ID=10
;

-- Node name: `UnLink Business Partner Org (org.compiere.process.BPartnerOrgUnLink)`
-- 2023-02-28T05:49:08.317Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=473 AND AD_Tree_ID=10
;

-- Node name: `Revenue Recognition (C_RevenueRecognition)`
-- 2023-02-28T05:49:08.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=186 AND AD_Tree_ID=10
;

-- Node name: `Dunning`
-- 2023-02-28T05:49:08.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540384 AND AD_Tree_ID=10
;

-- Node name: `Change Business Partner Block`
-- 2023-02-28T05:49:08.318Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542057 AND AD_Tree_ID=10
;

-- Name: Change Business Partner Block
-- Action Type: W
-- Window: Change Business Partner Block(541682,D)
-- 2023-02-28T05:56:37.618Z
UPDATE AD_Menu SET AD_Window_ID=541682,Updated=TO_TIMESTAMP('2023-02-28 07:56:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542057
;

-- Name: Change Business Partner Block
-- Action Type: W
-- Window: Change Business Partner Block(541682,D)
-- 2023-02-28T05:58:08.852Z
UPDATE AD_Menu SET WEBUI_NameBrowse='Change Business Partner Block',Updated=TO_TIMESTAMP('2023-02-28 07:58:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542057
;

-- 2023-02-28T05:58:08.853Z
UPDATE AD_Menu_Trl trl SET WEBUI_NameBrowse='Change Business Partner Block' WHERE AD_Menu_ID=542057 AND AD_Language='de_DE'
;

-- Window: Change Business Partner Block, InternalName=Change Business Partner Block
-- 2023-02-28T06:07:38.925Z
UPDATE AD_Window SET InternalName='Change Business Partner Block',Updated=TO_TIMESTAMP('2023-02-28 08:07:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541682
;

-- Window: Change Business Partner Block, InternalName=Change Business Partner Block
-- 2023-02-28T06:07:49.187Z
UPDATE AD_Window SET IsEnableRemoteCacheInvalidation='Y',Updated=TO_TIMESTAMP('2023-02-28 08:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541682
;

-- UI Column: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-28T06:08:38.393Z
UPDATE AD_UI_ElementGroup SET UIStyle='primary',Updated=TO_TIMESTAMP('2023-02-28 08:08:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=550410
;


-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Daten Import
-- Column: C_BPartner_Block_File.C_DataImport_ID
-- 2023-02-28T06:10:12.761Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586243,712721,0,546841,0,TO_TIMESTAMP('2023-02-28 08:10:12','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Daten Import',0,10,0,1,1,TO_TIMESTAMP('2023-02-28 08:10:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T06:10:12.766Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712721 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-28T06:10:12.773Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2023-02-28T06:10:12.789Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712721
;

-- 2023-02-28T06:10:12.795Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712721)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 10 -> main.Daten Import
-- Column: C_BPartner_Block_File.C_DataImport_ID
-- 2023-02-28T06:10:44.766Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712721,0,546841,615921,550410,'F',TO_TIMESTAMP('2023-02-28 08:10:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Daten Import',20,0,0,TO_TIMESTAMP('2023-02-28 08:10:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Fehler
-- Column: C_BPartner_Block_File.IsError
-- 2023-02-28T08:20:35.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586264,712722,0,546841,0,TO_TIMESTAMP('2023-02-28 10:20:35','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten',0,'D',0,'Y','Y','Y','N','N','N','N','N','Fehler',0,20,0,1,1,TO_TIMESTAMP('2023-02-28 10:20:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T08:20:35.642Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712722 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-28T08:20:35.656Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2395) 
;

-- 2023-02-28T08:20:35.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712722
;

-- 2023-02-28T08:20:35.675Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712722)
;

-- UI Element: Change Business Partner Block(541682,D) -> Block file(546841,D) -> main -> 20 -> flags.Fehler
-- Column: C_BPartner_Block_File.IsError
-- 2023-02-28T08:20:58.075Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,712722,0,546841,615922,550411,'F',TO_TIMESTAMP('2023-02-28 10:20:57','YYYY-MM-DD HH24:MI:SS'),100,'Ein Fehler ist bei der Durchführung aufgetreten','Y','N','N','Y','N','N','N',0,'Fehler',30,0,0,TO_TIMESTAMP('2023-02-28 10:20:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Daten Import
-- Column: C_BPartner_Block_File.C_DataImport_ID
-- 2023-02-28T08:22:54.616Z
UPDATE AD_Field SET DefaultValue='@SQL= SELECT C_DataImport_ID from C_DataImport WHERE InternalName ilike ''Block Status''', DisplayLogic='1=0',Updated=TO_TIMESTAMP('2023-02-28 10:22:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712721
;

-- Name: Change Business Partner Block
-- Action Type: W
-- Window: Change Business Partner Block(541682,D)
-- 2023-02-28T08:43:20.429Z
DELETE FROM  AD_Menu_Trl WHERE AD_Menu_ID=542057
;

-- 2023-02-28T08:43:20.469Z
DELETE FROM AD_Menu WHERE AD_Menu_ID=542057
;

-- 2023-02-28T08:43:20.478Z
DELETE FROM AD_TreeNodeMM n WHERE Node_ID=542057 AND EXISTS (SELECT * FROM AD_Tree t WHERE t.AD_Tree_ID=n.AD_Tree_ID AND t.AD_Table_ID=116)
;

-- Name: Change Business Partner Block
-- Action Type: W
-- Window: Change Business Partner Block(541682,D)
-- 2023-02-28T08:45:54.272Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582103,542062,0,541682,TO_TIMESTAMP('2023-02-28 10:45:54','YYYY-MM-DD HH24:MI:SS'),100,'The `Change Business Partner Block` window allows changing the blocking status for multiple partners by attaching a file in TSV format which contains all the information needed and then trigger `Process file` process. A template for this file can be downloaded via `Download TSV File process`.','D','Change Business Partner Block','Y','N','N','N','N','Change Business Partner Block',TO_TIMESTAMP('2023-02-28 10:45:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-28T08:45:54.275Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542062 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-02-28T08:45:54.277Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542062, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542062)
;

-- 2023-02-28T08:45:54.281Z
/* DDL */  select update_menu_translation_from_ad_element(582103) 
;

-- Reordering children of `Business Partner Rules`
-- Node name: `Business Partner Setup`
-- 2023-02-28T08:45:54.836Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=266 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Group (C_BP_Group)`
-- 2023-02-28T08:45:54.836Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=232 AND AD_Tree_ID=10
;

-- Node name: `Greeting (C_Greeting)`
-- 2023-02-28T08:45:54.837Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=190 AND AD_Tree_ID=10
;

-- Node name: `Payment Term (C_PaymentTerm)`
-- 2023-02-28T08:45:54.838Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=127 AND AD_Tree_ID=10
;

-- Node name: `Invoice Schedule (C_InvoiceSchedule)`
-- 2023-02-28T08:45:54.838Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=133 AND AD_Tree_ID=10
;

-- Node name: `Dunning (C_Dunning)`
-- 2023-02-28T08:45:54.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=172 AND AD_Tree_ID=10
;

-- Node name: `Withholding (1099) (C_Withholding)`
-- 2023-02-28T08:45:54.839Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=173 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2023-02-28T08:45:54.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=110 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Info (C_BPartner)`
-- 2023-02-28T08:45:54.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=394 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Open`
-- 2023-02-28T08:45:54.840Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=512 AND AD_Tree_ID=10
;

-- Node name: `Validate Business Partner (org.compiere.process.BPartnerValidate)`
-- 2023-02-28T08:45:54.841Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=506 AND AD_Tree_ID=10
;

-- Node name: `Adressdaten-PLZ verifizieren (de.metas.location.process.C_Location_Postal_Validate)`
-- 2023-02-28T08:45:54.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540286 AND AD_Tree_ID=10
;

-- Node name: `Business Partner Organization (org.compiere.process.OrgOwnership)`
-- 2023-02-28T08:45:54.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=420 AND AD_Tree_ID=10
;

-- Node name: `Partner Relation (C_BP_Relation)`
-- 2023-02-28T08:45:54.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=451 AND AD_Tree_ID=10
;

-- Node name: `UnLink Business Partner Org (org.compiere.process.BPartnerOrgUnLink)`
-- 2023-02-28T08:45:54.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=473 AND AD_Tree_ID=10
;

-- Node name: `Revenue Recognition (C_RevenueRecognition)`
-- 2023-02-28T08:45:54.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=186 AND AD_Tree_ID=10
;

-- Node name: `Dunning`
-- 2023-02-28T08:45:54.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540384 AND AD_Tree_ID=10
;

-- Node name: `Change Business Partner Block`
-- 2023-02-28T08:45:54.844Z
UPDATE AD_TreeNodeMM SET Parent_ID=165, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542062 AND AD_Tree_ID=10
;

-- Reordering children of `CRM`
-- Node name: `Change Business Partner Block`
-- 2023-02-28T08:46:05.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542062 AND AD_Tree_ID=10
;

-- Node name: `Request (R_Request)`
-- 2023-02-28T08:46:05.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=237 AND AD_Tree_ID=10
;

-- Node name: `Request (all) (R_Request)`
-- 2023-02-28T08:46:05.656Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=308 AND AD_Tree_ID=10
;

-- Node name: `Company Phone Book (AD_User)`
-- 2023-02-28T08:46:05.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541234 AND AD_Tree_ID=10
;

-- Node name: `Business Partner (C_BPartner)`
-- 2023-02-28T08:46:05.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000021 AND AD_Tree_ID=10
;

-- Node name: `Partner Export (C_BPartner_Export)`
-- 2023-02-28T08:46:05.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541728 AND AD_Tree_ID=10
;

-- Node name: `Outbound Documents (C_Doc_Outbound_Log)`
-- 2023-02-28T08:46:05.657Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540815 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-02-28T08:46:05.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000042 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-02-28T08:46:05.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000024 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-02-28T08:46:05.658Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000025 AND AD_Tree_ID=10
;

-- Node name: `BPartne Global ID (I_BPartner_GlobalID)`
-- 2023-02-28T08:46:05.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541200 AND AD_Tree_ID=10
;

-- Node name: `Import User (I_User)`
-- 2023-02-28T08:46:05.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541210 AND AD_Tree_ID=10
;

-- Node name: `Phone call (R_Request)`
-- 2023-02-28T08:46:05.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541896 AND AD_Tree_ID=10
;

/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
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

-- Node name: `Phonecall Schema (C_Phonecall_Schema)`
-- 2023-02-28T08:46:05.659Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541217 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schema Version (C_Phonecall_Schema_Version)`
-- 2023-02-28T08:46:05.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541218 AND AD_Tree_ID=10
;

-- Node name: `Phonecall Schedule (C_Phonecall_Schedule)`
-- 2023-02-28T08:46:05.660Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000008, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541219 AND AD_Tree_ID=10
;

-- Name: Change Business Partner Block
-- Action Type: W
-- Window: Change Business Partner Block(541682,D)
-- 2023-02-28T08:46:29.750Z
UPDATE AD_Menu SET IsCreateNew='Y',Updated=TO_TIMESTAMP('2023-02-28 10:46:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=542062
;

-- Column: C_BPartner_Block_File.C_DataImport_ID
-- 2023-02-28T08:52:34.869Z
UPDATE AD_Column SET DefaultValue='@SQL= SELECT C_DataImport_ID from C_DataImport WHERE InternalName ilike ''BPartner Block Status''',Updated=TO_TIMESTAMP('2023-02-28 10:52:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586243
;

-- Field: Change Business Partner Block(541682,D) -> Block file(546841,D) -> Daten Import
-- Column: C_BPartner_Block_File.C_DataImport_ID
-- 2023-02-28T08:52:49.065Z
UPDATE AD_Field SET DefaultValue='@SQL= SELECT C_DataImport_ID from C_DataImport WHERE InternalName ilike ''BPartner Block Status''',Updated=TO_TIMESTAMP('2023-02-28 10:52:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712721
;

