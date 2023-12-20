-- Tab: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import
-- Table: I_DeliveryPlanning
-- 2023-02-02T21:49:50.216Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,585783,582010,0,546801,542292,541671,'Y',TO_TIMESTAMP('2023-02-02 23:49:50','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','I_DeliveryPlanning','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Lieferplanung Import','N',20,1,TO_TIMESTAMP('2023-02-02 23:49:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:49:50.217Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546801 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-02T21:49:50.219Z
/* DDL */  select update_tab_translation_from_ad_element(582010) 
;

-- 2023-02-02T21:49:50.232Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546801)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Mandant
-- Column: I_DeliveryPlanning.AD_Client_ID
-- 2023-02-02T21:50:22.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585767,712059,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:22.192Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:22.193Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-02T21:50:22.325Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712059
;

-- 2023-02-02T21:50:22.326Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712059)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Probleme
-- Column: I_DeliveryPlanning.AD_Issue_ID
-- 2023-02-02T21:50:22.436Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585768,712060,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:22.437Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:22.438Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887) 
;

-- 2023-02-02T21:50:22.444Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712060
;

-- 2023-02-02T21:50:22.444Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712060)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Sektion
-- Column: I_DeliveryPlanning.AD_Org_ID
-- 2023-02-02T21:50:22.546Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585769,712061,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:22.547Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:22.548Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-02T21:50:22.653Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712061
;

-- 2023-02-02T21:50:22.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712061)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Daten Import
-- Column: I_DeliveryPlanning.C_DataImport_ID
-- 2023-02-02T21:50:22.758Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585770,712062,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:22.759Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:22.760Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913) 
;

-- 2023-02-02T21:50:22.763Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712062
;

-- 2023-02-02T21:50:22.764Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712062)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Data Import Run
-- Column: I_DeliveryPlanning.C_DataImport_Run_ID
-- 2023-02-02T21:50:22.861Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585771,712063,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Data Import Run',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:22.862Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:22.863Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114) 
;

-- 2023-02-02T21:50:22.865Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712063
;

-- 2023-02-02T21:50:22.866Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712063)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Import-Fehlermeldung
-- Column: I_DeliveryPlanning.I_ErrorMsg
-- 2023-02-02T21:50:22.953Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585774,712064,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden',2000,'D','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','N','N','N','N','N','Import-Fehlermeldung',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:22.953Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:22.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(912) 
;

-- 2023-02-02T21:50:22.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712064
;

-- 2023-02-02T21:50:22.962Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712064)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Importiert
-- Column: I_DeliveryPlanning.I_IsImported
-- 2023-02-02T21:50:23.059Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585775,712065,0,546801,TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?',1,'D','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','N','N','N','N','N','Importiert',TO_TIMESTAMP('2023-02-02 23:50:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.062Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(913) 
;

-- 2023-02-02T21:50:23.066Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712065
;

-- 2023-02-02T21:50:23.066Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712065)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Import Line Content
-- Column: I_DeliveryPlanning.I_LineContent
-- 2023-02-02T21:50:23.157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585776,712066,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.158Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115) 
;

-- 2023-02-02T21:50:23.161Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712066
;

-- 2023-02-02T21:50:23.161Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712066)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Import Line No
-- Column: I_DeliveryPlanning.I_LineNo
-- 2023-02-02T21:50:23.259Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585777,712067,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.260Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.261Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116) 
;

-- 2023-02-02T21:50:23.263Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712067
;

-- 2023-02-02T21:50:23.264Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712067)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Aktiv
-- Column: I_DeliveryPlanning.IsActive
-- 2023-02-02T21:50:23.349Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585778,712068,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.350Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.351Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-02T21:50:23.466Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712068
;

-- 2023-02-02T21:50:23.466Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712068)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Verarbeitet
-- Column: I_DeliveryPlanning.Processed
-- 2023-02-02T21:50:23.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585779,712069,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.574Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.575Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-02-02T21:50:23.580Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712069
;

-- 2023-02-02T21:50:23.581Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712069)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferplanung Import
-- Column: I_DeliveryPlanning.I_DeliveryPlanning_ID
-- 2023-02-02T21:50:23.676Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585782,712070,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Lieferplanung Import',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.677Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.677Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582010) 
;

-- 2023-02-02T21:50:23.680Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712070
;

-- 2023-02-02T21:50:23.680Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712070)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferplanung Datenimport
-- Column: I_DeliveryPlanning.I_DeliveryPlanning_Data_ID
-- 2023-02-02T21:50:23.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585783,712071,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Lieferplanung Datenimport',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.777Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.777Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581998) 
;

-- 2023-02-02T21:50:23.779Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712071
;

-- 2023-02-02T21:50:23.780Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712071)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Nr.
-- Column: I_DeliveryPlanning.DocumentNo
-- 2023-02-02T21:50:23.867Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585784,712072,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',40,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.867Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.868Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-02-02T21:50:23.872Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712072
;

-- 2023-02-02T21:50:23.873Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712072)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferplanung
-- Column: I_DeliveryPlanning.M_Delivery_Planning_ID
-- 2023-02-02T21:50:23.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585785,712073,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Lieferplanung',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:23.992Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:23.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581677) 
;

-- 2023-02-02T21:50:23.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712073
;

-- 2023-02-02T21:50:23.995Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712073)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferadresse
-- Column: I_DeliveryPlanning.ShipToLocation_Name
-- 2023-02-02T21:50:24.080Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585786,712074,0,546801,TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100,1024,'D','Y','N','N','N','N','N','N','N','Lieferadresse',TO_TIMESTAMP('2023-02-02 23:50:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.081Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.082Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581681) 
;

-- 2023-02-02T21:50:24.084Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712074
;

-- 2023-02-02T21:50:24.085Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712074)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Produktname
-- Column: I_DeliveryPlanning.ProductName
-- 2023-02-02T21:50:24.183Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585787,712075,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes',600,'D','Y','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.184Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2659) 
;

-- 2023-02-02T21:50:24.188Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712075
;

-- 2023-02-02T21:50:24.189Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712075)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lager
-- Column: I_DeliveryPlanning.WarehouseName
-- 2023-02-02T21:50:24.279Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585789,712076,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,'Lagerbezeichnung',60,'D','Y','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.280Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712076 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2280) 
;

-- 2023-02-02T21:50:24.283Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712076
;

-- 2023-02-02T21:50:24.284Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712076)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Ursprungsland
-- Column: I_DeliveryPlanning.OriginCountry
-- 2023-02-02T21:50:24.381Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585790,712077,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,60,'D','Y','N','N','N','N','N','N','N','Ursprungsland',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.382Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712077 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.383Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581694) 
;

-- 2023-02-02T21:50:24.385Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712077
;

-- 2023-02-02T21:50:24.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712077)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Stapel Nr.
-- Column: I_DeliveryPlanning.Batch
-- 2023-02-02T21:50:24.470Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585792,712078,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,250,'D','Y','N','N','N','N','N','N','N','Stapel Nr.',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.472Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712078 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.472Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581692) 
;

-- 2023-02-02T21:50:24.474Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712078
;

-- 2023-02-02T21:50:24.475Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712078)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Ausgabenummer
-- Column: I_DeliveryPlanning.ReleaseNo
-- 2023-02-02T21:50:24.572Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585793,712079,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,'Internal Release Number',250,'D','Y','N','N','N','N','N','N','N','Ausgabenummer',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.573Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712079 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.573Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2122) 
;

-- 2023-02-02T21:50:24.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712079
;

-- 2023-02-02T21:50:24.577Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712079)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Voraussichtliches Verladedatum
-- Column: I_DeliveryPlanning.PlannedLoadingDate
-- 2023-02-02T21:50:24.666Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585794,712080,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Voraussichtliches Verladedatum',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.667Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712080 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.667Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581688) 
;

-- 2023-02-02T21:50:24.669Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712080
;

-- 2023-02-02T21:50:24.670Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712080)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Verladedatum
-- Column: I_DeliveryPlanning.ActualLoadingDate
-- 2023-02-02T21:50:24.769Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585795,712081,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Verladedatum',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.770Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712081 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.770Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581689) 
;

-- 2023-02-02T21:50:24.772Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712081
;

-- 2023-02-02T21:50:24.773Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712081)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Geplante Verlademenge
-- Column: I_DeliveryPlanning.PlannedLoadedQuantity
-- 2023-02-02T21:50:24.860Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585796,712082,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Geplante Verlademenge',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.861Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712082 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.861Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581794) 
;

-- 2023-02-02T21:50:24.864Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712082
;

-- 2023-02-02T21:50:24.864Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712082)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Tatsächlich verladene Menge
-- Column: I_DeliveryPlanning.ActualLoadQty
-- 2023-02-02T21:50:24.954Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585797,712083,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Tatsächlich verladene Menge',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:24.955Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712083 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:24.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581690) 
;

-- 2023-02-02T21:50:24.958Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712083
;

-- 2023-02-02T21:50:24.958Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712083)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Voraussichtliches Lieferdatum
-- Column: I_DeliveryPlanning.PlannedDeliveryDate
-- 2023-02-02T21:50:25.046Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585798,712084,0,546801,TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Voraussichtliches Lieferdatum',TO_TIMESTAMP('2023-02-02 23:50:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:25.047Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712084 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:25.047Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581686) 
;

-- 2023-02-02T21:50:25.050Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712084
;

-- 2023-02-02T21:50:25.050Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712084)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferdatum
-- Column: I_DeliveryPlanning.ActualDeliveryDate
-- 2023-02-02T21:50:25.138Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585799,712085,0,546801,TO_TIMESTAMP('2023-02-02 23:50:25','YYYY-MM-DD HH24:MI:SS'),100,7,'D','Y','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2023-02-02 23:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:25.139Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712085 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:25.140Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581687) 
;

-- 2023-02-02T21:50:25.141Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712085
;

-- 2023-02-02T21:50:25.142Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712085)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Geplante Entlademenge
-- Column: I_DeliveryPlanning.PlannedDischargeQuantity
-- 2023-02-02T21:50:25.224Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585800,712086,0,546801,TO_TIMESTAMP('2023-02-02 23:50:25','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Geplante Entlademenge',TO_TIMESTAMP('2023-02-02 23:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:25.225Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712086 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:25.226Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581795) 
;

-- 2023-02-02T21:50:25.228Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712086
;

-- 2023-02-02T21:50:25.229Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712086)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Tatsächlich abgeladene Menge
-- Column: I_DeliveryPlanning.ActualDischargeQuantity
-- 2023-02-02T21:50:25.319Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585801,712087,0,546801,TO_TIMESTAMP('2023-02-02 23:50:25','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Tatsächlich abgeladene Menge',TO_TIMESTAMP('2023-02-02 23:50:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T21:50:25.319Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712087 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T21:50:25.320Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581796) 
;

-- 2023-02-02T21:50:25.322Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712087
;

-- 2023-02-02T21:50:25.323Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712087)
;

-- Tab: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D)
-- UI Section: main
-- 2023-02-02T21:50:57.155Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,UIStyle,Updated,UpdatedBy,Value) VALUES (0,0,546801,545428,TO_TIMESTAMP('2023-02-02 23:50:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'primary',TO_TIMESTAMP('2023-02-02 23:50:57','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-02T21:50:57.156Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545428 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main
-- UI Column: 10
-- 2023-02-02T21:51:02.350Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546620,545428,TO_TIMESTAMP('2023-02-02 23:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-02 23:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10
-- UI Element Group: main
-- 2023-02-02T21:51:13.473Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546620,550335,TO_TIMESTAMP('2023-02-02 23:51:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2023-02-02 23:51:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Importiert
-- Column: I_DeliveryPlanning.I_IsImported
-- 2023-02-02T22:23:17.503Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712065,0,546801,550335,615528,'F',TO_TIMESTAMP('2023-02-03 00:23:17','YYYY-MM-DD HH24:MI:SS'),100,'Ist dieser Import verarbeitet worden?','DasSelektionsfeld "Importiert" zeigt an, ob dieser Import verarbeitet worden ist.','Y','N','N','Y','N','N','N',0,'Importiert',10,0,0,TO_TIMESTAMP('2023-02-03 00:23:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Import Line No
-- Column: I_DeliveryPlanning.I_LineNo
-- 2023-02-02T22:23:34.435Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712067,0,546801,550335,615529,'F',TO_TIMESTAMP('2023-02-03 00:23:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line No',20,0,0,TO_TIMESTAMP('2023-02-03 00:23:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Import-Fehlermeldung
-- Column: I_DeliveryPlanning.I_ErrorMsg
-- 2023-02-02T22:24:01.486Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712064,0,546801,550335,615530,'F',TO_TIMESTAMP('2023-02-03 00:24:01','YYYY-MM-DD HH24:MI:SS'),100,'Meldungen, die durch den Importprozess generiert wurden','"Import-Fehlermeldung" zeigt alle Fehlermeldungen an, die durch den Importprozess generiert wurden.','Y','N','N','Y','N','N','N',0,'Import-Fehlermeldung',30,0,0,TO_TIMESTAMP('2023-02-03 00:24:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Nr.
-- Column: I_DeliveryPlanning.DocumentNo
-- 2023-02-02T22:24:20.938Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712072,0,546801,550335,615531,'F',TO_TIMESTAMP('2023-02-03 00:24:20','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',40,0,0,TO_TIMESTAMP('2023-02-03 00:24:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Lieferadresse
-- Column: I_DeliveryPlanning.ShipToLocation_Name
-- 2023-02-02T22:25:13.936Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712074,0,546801,550335,615532,'F',TO_TIMESTAMP('2023-02-03 00:25:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lieferadresse',50,0,0,TO_TIMESTAMP('2023-02-03 00:25:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Produktname
-- Column: I_DeliveryPlanning.ProductName
-- 2023-02-02T22:25:26.019Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712075,0,546801,550335,615533,'F',TO_TIMESTAMP('2023-02-03 00:25:25','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes','Y','N','N','Y','N','N','N',0,'Produktname',60,0,0,TO_TIMESTAMP('2023-02-03 00:25:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Produktcode
-- Column: I_DeliveryPlanning.ProductCode
-- 2023-02-02T22:26:55.726Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585788,712088,0,546801,0,TO_TIMESTAMP('2023-02-03 00:26:55','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Produktcode',0,10,0,1,1,TO_TIMESTAMP('2023-02-03 00:26:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T22:26:55.727Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712088 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T22:26:55.728Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576649) 
;

-- 2023-02-02T22:26:55.731Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712088
;

-- 2023-02-02T22:26:55.732Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712088)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferdatum
-- Column: I_DeliveryPlanning.DeliveryDate
-- 2023-02-02T22:27:05.357Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585791,712089,0,546801,0,TO_TIMESTAMP('2023-02-03 00:27:05','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Lieferdatum',0,20,0,1,1,TO_TIMESTAMP('2023-02-03 00:27:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-02T22:27:05.358Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712089 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-02T22:27:05.359Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376) 
;

-- 2023-02-02T22:27:05.363Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712089
;

-- 2023-02-02T22:27:05.363Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712089)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Produktcode
-- Column: I_DeliveryPlanning.ProductCode
-- 2023-02-02T22:27:38.669Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712088,0,546801,550335,615534,'F',TO_TIMESTAMP('2023-02-03 00:27:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Produktcode',70,0,0,TO_TIMESTAMP('2023-02-03 00:27:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Lager
-- Column: I_DeliveryPlanning.WarehouseName
-- 2023-02-02T22:27:52.470Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712076,0,546801,550335,615535,'F',TO_TIMESTAMP('2023-02-03 00:27:52','YYYY-MM-DD HH24:MI:SS'),100,'Lagerbezeichnung','Y','N','N','Y','N','N','N',0,'Lager',80,0,0,TO_TIMESTAMP('2023-02-03 00:27:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Ursprungsland
-- Column: I_DeliveryPlanning.OriginCountry
-- 2023-02-02T22:28:06.622Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712077,0,546801,550335,615536,'F',TO_TIMESTAMP('2023-02-03 00:28:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Ursprungsland',90,0,0,TO_TIMESTAMP('2023-02-03 00:28:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Stapel Nr.
-- Column: I_DeliveryPlanning.Batch
-- 2023-02-02T22:28:22.812Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712078,0,546801,550335,615537,'F',TO_TIMESTAMP('2023-02-03 00:28:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Stapel Nr.',100,0,0,TO_TIMESTAMP('2023-02-03 00:28:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Ausgabenummer
-- Column: I_DeliveryPlanning.ReleaseNo
-- 2023-02-02T22:28:35.243Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712079,0,546801,550335,615538,'F',TO_TIMESTAMP('2023-02-03 00:28:35','YYYY-MM-DD HH24:MI:SS'),100,'Internal Release Number','Y','N','N','Y','N','N','N',0,'Ausgabenummer',110,0,0,TO_TIMESTAMP('2023-02-03 00:28:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Voraussichtliches Verladedatum
-- Column: I_DeliveryPlanning.PlannedLoadingDate
-- 2023-02-02T22:29:23.121Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712080,0,546801,550335,615539,'F',TO_TIMESTAMP('2023-02-03 00:29:22','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Voraussichtliches Verladedatum',120,0,0,TO_TIMESTAMP('2023-02-03 00:29:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Verladedatum
-- Column: I_DeliveryPlanning.ActualLoadingDate
-- 2023-02-02T22:29:42.071Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712081,0,546801,550335,615540,'F',TO_TIMESTAMP('2023-02-03 00:29:41','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Verladedatum',130,0,0,TO_TIMESTAMP('2023-02-03 00:29:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Geplante Verlademenge
-- Column: I_DeliveryPlanning.PlannedLoadedQuantity
-- 2023-02-02T22:29:59.186Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712082,0,546801,550335,615541,'F',TO_TIMESTAMP('2023-02-03 00:29:59','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geplante Verlademenge',140,0,0,TO_TIMESTAMP('2023-02-03 00:29:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Tatsächlich verladene Menge
-- Column: I_DeliveryPlanning.ActualLoadQty
-- 2023-02-02T22:30:12.946Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712083,0,546801,550335,615542,'F',TO_TIMESTAMP('2023-02-03 00:30:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tatsächlich verladene Menge',150,0,0,TO_TIMESTAMP('2023-02-03 00:30:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Voraussichtliches Lieferdatum
-- Column: I_DeliveryPlanning.PlannedDeliveryDate
-- 2023-02-02T22:30:39.876Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712084,0,546801,550335,615543,'F',TO_TIMESTAMP('2023-02-03 00:30:39','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Voraussichtliches Lieferdatum',160,0,0,TO_TIMESTAMP('2023-02-03 00:30:39','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Lieferdatum
-- Column: I_DeliveryPlanning.ActualDeliveryDate
-- 2023-02-02T22:32:36.005Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712085,0,546801,550335,615544,'F',TO_TIMESTAMP('2023-02-03 00:32:35','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Lieferdatum',170,0,0,TO_TIMESTAMP('2023-02-03 00:32:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Geplante Entlademenge
-- Column: I_DeliveryPlanning.PlannedDischargeQuantity
-- 2023-02-02T22:32:51.615Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712086,0,546801,550335,615545,'F',TO_TIMESTAMP('2023-02-03 00:32:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Geplante Entlademenge',180,0,0,TO_TIMESTAMP('2023-02-03 00:32:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Tatsächlich abgeladene Menge
-- Column: I_DeliveryPlanning.ActualDischargeQuantity
-- 2023-02-02T22:33:13.005Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712087,0,546801,550335,615546,'F',TO_TIMESTAMP('2023-02-03 00:33:12','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tatsächlich abgeladene Menge',190,0,0,TO_TIMESTAMP('2023-02-03 00:33:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Mandant
-- Column: I_DeliveryPlanning.AD_Client_ID
-- 2023-02-02T22:33:38.814Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712059
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Probleme
-- Column: I_DeliveryPlanning.AD_Issue_ID
-- 2023-02-02T22:33:38.817Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712060
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Sektion
-- Column: I_DeliveryPlanning.AD_Org_ID
-- 2023-02-02T22:33:38.821Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712061
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Daten Import
-- Column: I_DeliveryPlanning.C_DataImport_ID
-- 2023-02-02T22:33:38.825Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712062
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Data Import Run
-- Column: I_DeliveryPlanning.C_DataImport_Run_ID
-- 2023-02-02T22:33:38.827Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712063
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Import-Fehlermeldung
-- Column: I_DeliveryPlanning.I_ErrorMsg
-- 2023-02-02T22:33:38.831Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712064
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Importiert
-- Column: I_DeliveryPlanning.I_IsImported
-- 2023-02-02T22:33:38.834Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712065
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Import Line Content
-- Column: I_DeliveryPlanning.I_LineContent
-- 2023-02-02T22:33:38.837Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712066
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Import Line No
-- Column: I_DeliveryPlanning.I_LineNo
-- 2023-02-02T22:33:38.840Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712067
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Aktiv
-- Column: I_DeliveryPlanning.IsActive
-- 2023-02-02T22:33:38.843Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712068
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Verarbeitet
-- Column: I_DeliveryPlanning.Processed
-- 2023-02-02T22:33:38.846Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712069
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferplanung Import
-- Column: I_DeliveryPlanning.I_DeliveryPlanning_ID
-- 2023-02-02T22:33:38.848Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712070
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferplanung Datenimport
-- Column: I_DeliveryPlanning.I_DeliveryPlanning_Data_ID
-- 2023-02-02T22:33:38.852Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712071
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Nr.
-- Column: I_DeliveryPlanning.DocumentNo
-- 2023-02-02T22:33:38.854Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712072
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferplanung
-- Column: I_DeliveryPlanning.M_Delivery_Planning_ID
-- 2023-02-02T22:33:38.857Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712073
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferadresse
-- Column: I_DeliveryPlanning.ShipToLocation_Name
-- 2023-02-02T22:33:38.860Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712074
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Produktname
-- Column: I_DeliveryPlanning.ProductName
-- 2023-02-02T22:33:38.863Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712075
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lager
-- Column: I_DeliveryPlanning.WarehouseName
-- 2023-02-02T22:33:38.865Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712076
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Ursprungsland
-- Column: I_DeliveryPlanning.OriginCountry
-- 2023-02-02T22:33:38.868Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712077
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Stapel Nr.
-- Column: I_DeliveryPlanning.Batch
-- 2023-02-02T22:33:38.871Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712078
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Ausgabenummer
-- Column: I_DeliveryPlanning.ReleaseNo
-- 2023-02-02T22:33:38.874Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712079
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Voraussichtliches Verladedatum
-- Column: I_DeliveryPlanning.PlannedLoadingDate
-- 2023-02-02T22:33:38.876Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712080
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Verladedatum
-- Column: I_DeliveryPlanning.ActualLoadingDate
-- 2023-02-02T22:33:38.879Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712081
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Geplante Verlademenge
-- Column: I_DeliveryPlanning.PlannedLoadedQuantity
-- 2023-02-02T22:33:38.882Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712082
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Tatsächlich verladene Menge
-- Column: I_DeliveryPlanning.ActualLoadQty
-- 2023-02-02T22:33:38.884Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712083
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Voraussichtliches Lieferdatum
-- Column: I_DeliveryPlanning.PlannedDeliveryDate
-- 2023-02-02T22:33:38.887Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712084
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferdatum
-- Column: I_DeliveryPlanning.ActualDeliveryDate
-- 2023-02-02T22:33:38.889Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712085
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Geplante Entlademenge
-- Column: I_DeliveryPlanning.PlannedDischargeQuantity
-- 2023-02-02T22:33:38.892Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712086
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Tatsächlich abgeladene Menge
-- Column: I_DeliveryPlanning.ActualDischargeQuantity
-- 2023-02-02T22:33:38.895Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712087
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Produktcode
-- Column: I_DeliveryPlanning.ProductCode
-- 2023-02-02T22:33:38.897Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712088
;

-- Field: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> Lieferdatum
-- Column: I_DeliveryPlanning.DeliveryDate
-- 2023-02-02T22:33:38.901Z
UPDATE AD_Field SET IsDisplayedGrid='Y', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-02-03 00:33:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712089
;

-- UI Element: Lieferplanung Datenimport(541671,D) -> Lieferplanung Import(546801,D) -> main -> 10 -> main.Import Line Content
-- Column: I_DeliveryPlanning.I_LineContent
-- 2023-02-02T22:34:13.262Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712066,0,546801,550335,615547,'F',TO_TIMESTAMP('2023-02-03 00:34:13','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Import Line Content',200,0,0,TO_TIMESTAMP('2023-02-03 00:34:13','YYYY-MM-DD HH24:MI:SS'),100)
;

