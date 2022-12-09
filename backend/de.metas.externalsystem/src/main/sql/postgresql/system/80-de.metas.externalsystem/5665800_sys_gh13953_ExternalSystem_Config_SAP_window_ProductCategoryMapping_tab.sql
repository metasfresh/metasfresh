-- Tab: External system config SAP(541631,de.metas.externalsystem) -> External reference
-- Table: S_ExternalReference
-- 2022-11-23T13:46:24.065Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583308,577647,0,546682,541486,541631,'Y',TO_TIMESTAMP('2022-11-23 15:46:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalreference','N','N','A','S_ExternalReference_ProductCategory','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'External reference','N',50,0,TO_TIMESTAMP('2022-11-23 15:46:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:46:24.067Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546682 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2022-11-23T13:46:24.070Z
/* DDL */  select update_tab_translation_from_ad_element(577647) 
;

-- 2022-11-23T13:46:24.074Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546682)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Mandant
-- Column: S_ExternalReference.AD_Client_ID
-- 2022-11-23T13:46:41.817Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570573,708174,0,546682,TO_TIMESTAMP('2022-11-23 15:46:41','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalreference','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2022-11-23 15:46:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:46:41.819Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708174 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:46:41.821Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2022-11-23T13:46:42.166Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708174
;

-- 2022-11-23T13:46:42.167Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708174)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Sektion
-- Column: S_ExternalReference.AD_Org_ID
-- 2022-11-23T13:46:51.004Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570574,708175,0,546682,TO_TIMESTAMP('2022-11-23 15:46:50','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalreference','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2022-11-23 15:46:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:46:51.006Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708175 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:46:51.008Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2022-11-23T13:46:51.224Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708175
;

-- 2022-11-23T13:46:51.225Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708175)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T13:47:00.180Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570588,708176,0,546682,TO_TIMESTAMP('2022-11-23 15:47:00','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalreference','Y','N','N','N','N','N','N','N','External reference',TO_TIMESTAMP('2022-11-23 15:47:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:47:00.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708176 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:47:00.185Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577602) 
;

-- 2022-11-23T13:47:00.187Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708176
;

-- 2022-11-23T13:47:00.188Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708176)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> External System Config
-- Column: S_ExternalReference.ExternalSystem_Config_ID
-- 2022-11-23T13:47:10.704Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583308,708177,0,546682,TO_TIMESTAMP('2022-11-23 15:47:10','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalreference','Y','N','N','N','N','N','N','N','External System Config',TO_TIMESTAMP('2022-11-23 15:47:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:47:10.706Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708177 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:47:10.709Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578728) 
;

-- 2022-11-23T13:47:10.713Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708177
;

-- 2022-11-23T13:47:10.713Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708177)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-23T13:47:19.761Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570585,708178,0,546682,TO_TIMESTAMP('2022-11-23 15:47:19','YYYY-MM-DD HH24:MI:SS'),100,'Name of an external system (e.g. Github )',255,'de.metas.externalreference','Name of an external system (e.g. Github )','Y','N','N','N','N','N','N','N','External system',TO_TIMESTAMP('2022-11-23 15:47:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:47:19.763Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708178 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:47:19.764Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577608) 
;

-- 2022-11-23T13:47:19.767Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708178
;

-- 2022-11-23T13:47:19.769Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708178)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Aktiv
-- Column: S_ExternalReference.IsActive
-- 2022-11-23T13:47:31.927Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570578,708179,0,546682,TO_TIMESTAMP('2022-11-23 15:47:31','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalreference','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2022-11-23 15:47:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:47:31.928Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708179 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:47:31.929Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2022-11-23T13:47:32.270Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708179
;

-- 2022-11-23T13:47:32.270Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708179)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T13:47:47.606Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570590,708180,0,546682,TO_TIMESTAMP('2022-11-23 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID',10,'de.metas.externalreference','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','N','N','N','N','N','Datensatz-ID',TO_TIMESTAMP('2022-11-23 15:47:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:47:47.608Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708180 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:47:47.614Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2022-11-23T13:47:47.629Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708180
;

-- 2022-11-23T13:47:47.633Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708180)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Referenced table ID
-- Column: S_ExternalReference.Referenced_AD_Table_ID
-- 2022-11-23T13:47:54.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570617,708181,0,546682,TO_TIMESTAMP('2022-11-23 15:47:54','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalreference','Y','N','N','N','N','N','N','N','Referenced table ID',TO_TIMESTAMP('2022-11-23 15:47:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:47:54.973Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708181 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:47:54.975Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577669) 
;

-- 2022-11-23T13:47:54.981Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708181
;

-- 2022-11-23T13:47:54.981Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708181)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Referenced record ID
-- Column: S_ExternalReference.Referenced_Record_ID
-- 2022-11-23T13:48:02.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570620,708182,0,546682,TO_TIMESTAMP('2022-11-23 15:48:01','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalreference','Y','N','N','N','N','N','N','N','Referenced record ID',TO_TIMESTAMP('2022-11-23 15:48:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:48:02.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708182 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:48:02.107Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577670) 
;

-- 2022-11-23T13:48:02.109Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708182
;

-- 2022-11-23T13:48:02.109Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708182)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Art
-- Column: S_ExternalReference.Type
-- 2022-11-23T13:48:16.744Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570581,708183,0,546682,TO_TIMESTAMP('2022-11-23 15:48:16','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.externalreference','','Y','N','N','N','N','N','N','N','Art',TO_TIMESTAMP('2022-11-23 15:48:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:48:16.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708183 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:48:16.748Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2022-11-23T13:48:16.755Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708183
;

-- 2022-11-23T13:48:16.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708183)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> Art
-- Column: S_ExternalReference.Type
-- 2022-11-23T13:48:35.665Z
UPDATE AD_Field SET DefaultValue='ProductCategory',Updated=TO_TIMESTAMP('2022-11-23 15:48:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708183
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference)
-- UI Section: main
-- 2022-11-23T13:48:51.709Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546682,545305,TO_TIMESTAMP('2022-11-23 15:48:51','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-23 15:48:51','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2022-11-23T13:48:51.711Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545305 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> main
-- UI Column: 10
-- 2022-11-23T13:48:54.964Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546463,545305,TO_TIMESTAMP('2022-11-23 15:48:54','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2022-11-23 15:48:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> main -> 10
-- UI Element Group: externalRef
-- 2022-11-23T13:49:05.368Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546463,550048,TO_TIMESTAMP('2022-11-23 15:49:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','externalRef',10,'primary',TO_TIMESTAMP('2022-11-23 15:49:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:49:50.955Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708176
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T13:49:50.956Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=708176
;

-- 2022-11-23T13:49:50.966Z
DELETE FROM AD_Field WHERE AD_Field_ID=708176
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> External reference(546682,de.metas.externalreference) -> External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T13:50:08.740Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570588,708184,0,546682,TO_TIMESTAMP('2022-11-23 15:50:08','YYYY-MM-DD HH24:MI:SS'),100,255,'de.metas.externalreference','Y','N','N','N','N','N','N','N','External reference',TO_TIMESTAMP('2022-11-23 15:50:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:50:08.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=708184 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-23T13:50:08.749Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577602) 
;

-- 2022-11-23T13:50:08.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=708184
;

-- 2022-11-23T13:50:08.751Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(708184)
;

-- 2022-11-23T13:52:49.238Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581723,0,TO_TIMESTAMP('2022-11-23 15:52:49','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','ProductCategory Mappings','ProductCategory Mappings',TO_TIMESTAMP('2022-11-23 15:52:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-23T13:52:49.245Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581723 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings
-- Table: S_ExternalReference
-- 2022-11-23T13:53:01.725Z
UPDATE AD_Tab SET AD_Element_ID=581723, CommitWarning=NULL, Description=NULL, Help=NULL, Name='ProductCategory Mappings',Updated=TO_TIMESTAMP('2022-11-23 15:53:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546682
;

-- 2022-11-23T13:53:01.746Z
/* DDL */  select update_tab_translation_from_ad_element(581723) 
;

-- 2022-11-23T13:53:01.755Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546682)
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings
-- Table: S_ExternalReference
-- 2022-11-23T13:53:11.367Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2022-11-23 15:53:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546682
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings
-- Table: S_ExternalReference
-- 2022-11-23T13:53:15.617Z
UPDATE AD_Tab SET Parent_Column_ID=584643,Updated=TO_TIMESTAMP('2022-11-23 15:53:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546682
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 10 -> externalRef.External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-23T13:53:32.588Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708178,0,546682,613554,550048,'F',TO_TIMESTAMP('2022-11-23 15:53:32','YYYY-MM-DD HH24:MI:SS'),100,'Name of an external system (e.g. Github )','Name of an external system (e.g. Github )','Y','N','N','Y','N','N','N',0,'External system',10,0,0,TO_TIMESTAMP('2022-11-23 15:53:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 10 -> externalRef.External reference
-- Column: S_ExternalReference.ExternalReference
-- 2022-11-23T13:53:44.853Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708184,0,546682,613555,550048,'F',TO_TIMESTAMP('2022-11-23 15:53:44','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'External reference',20,0,0,TO_TIMESTAMP('2022-11-23 15:53:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 10 -> externalRef.Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T13:53:54.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708180,0,546682,613556,550048,'F',TO_TIMESTAMP('2022-11-23 15:53:53','YYYY-MM-DD HH24:MI:SS'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',30,0,0,TO_TIMESTAMP('2022-11-23 15:53:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Section: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main
-- UI Column: 20
-- 2022-11-23T13:53:57.868Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546464,545305,TO_TIMESTAMP('2022-11-23 15:53:57','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2022-11-23 15:53:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 20
-- UI Element Group: flags
-- 2022-11-23T13:54:05.972Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546464,550049,TO_TIMESTAMP('2022-11-23 15:54:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,TO_TIMESTAMP('2022-11-23 15:54:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 20 -> flags.Aktiv
-- Column: S_ExternalReference.IsActive
-- 2022-11-23T13:54:16.209Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708179,0,546682,613557,550049,'F',TO_TIMESTAMP('2022-11-23 15:54:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2022-11-23 15:54:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 20
-- UI Element Group: org
-- 2022-11-23T13:54:21.365Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546464,550050,TO_TIMESTAMP('2022-11-23 15:54:21','YYYY-MM-DD HH24:MI:SS'),100,'Y','org',20,TO_TIMESTAMP('2022-11-23 15:54:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 20 -> org.Mandant
-- Column: S_ExternalReference.AD_Client_ID
-- 2022-11-23T13:54:35.422Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708174,0,546682,613558,550050,'F',TO_TIMESTAMP('2022-11-23 15:54:35','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',10,0,0,TO_TIMESTAMP('2022-11-23 15:54:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> main -> 20 -> org.Sektion
-- Column: S_ExternalReference.AD_Org_ID
-- 2022-11-23T13:54:42.610Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,708175,0,546682,613559,550050,'F',TO_TIMESTAMP('2022-11-23 15:54:42','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',20,0,0,TO_TIMESTAMP('2022-11-23 15:54:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> External system
-- Column: S_ExternalReference.ExternalSystem
-- 2022-11-23T13:54:57.786Z
UPDATE AD_Field SET DefaultValue='SAP', IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-23 15:54:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708178
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings
-- Table: S_ExternalReference
-- 2022-11-23T13:59:32.951Z
UPDATE AD_Tab SET WhereClause='S_ExternalReference.Type=''ProductCategory''',Updated=TO_TIMESTAMP('2022-11-23 15:59:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546682
;

-- Tab: External system config SAP(541631,de.metas.externalsystem) -> UOM Zuordnung
-- Table: S_ExternalReference
-- 2022-11-23T13:59:57.635Z
UPDATE AD_Tab SET WhereClause='S_ExternalReference.Type=''UOM''',Updated=TO_TIMESTAMP('2022-11-23 15:59:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546681
;

-- Field: External system config SAP(541631,de.metas.externalsystem) -> ProductCategory Mappings(546682,de.metas.externalreference) -> Datensatz-ID
-- Column: S_ExternalReference.Record_ID
-- 2022-11-23T14:00:47.998Z
UPDATE AD_Field SET AD_Reference_ID=30, AD_Reference_Value_ID=163,Updated=TO_TIMESTAMP('2022-11-23 16:00:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=708180
;

