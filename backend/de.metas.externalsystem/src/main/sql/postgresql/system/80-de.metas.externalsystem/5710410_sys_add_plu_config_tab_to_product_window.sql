-- Tab: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Table: ExternalSystem_Config_LeichMehl_ProductMapping
-- Tab: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Table: ExternalSystem_Config_LeichMehl_ProductMapping
-- 2023-11-14T15:06:13.962Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581055,0,547280,542172,140,'Y',TO_TIMESTAMP('2023-11-14 16:06:13','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.externalsystem','N','N','A','ExternalSystem_Config_LeichMehl_ProductMapping','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'ExternalSystem_Config_LeichMehl_ProductMapping',578867,'N',310,1,TO_TIMESTAMP('2023-11-14 16:06:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:06:13.979Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=547280 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-11-14T15:06:13.996Z
/* DDL */  select update_tab_translation_from_ad_element(581055)
;

-- 2023-11-14T15:06:14.193Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547280)
;

-- Tab: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Table: ExternalSystem_Config_LeichMehl_ProductMapping
-- Tab: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Table: ExternalSystem_Config_LeichMehl_ProductMapping
-- 2023-11-15T15:14:36.713Z
UPDATE AD_Tab SET AD_Column_ID=583480,Updated=TO_TIMESTAMP('2023-11-15 16:14:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547280
;

-- Tab: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Table: ExternalSystem_Config_LeichMehl_ProductMapping
-- Tab: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Table: ExternalSystem_Config_LeichMehl_ProductMapping
-- 2023-11-15T15:19:53.651Z
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=150,Updated=TO_TIMESTAMP('2023-11-15 16:19:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=547280
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> Mandant
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.AD_Client_ID
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> Mandant
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.AD_Client_ID
-- 2023-11-14T15:21:09.725Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583469,721834,0,547280,TO_TIMESTAMP('2023-11-14 16:21:09','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'de.metas.externalsystem','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-11-14 16:21:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:09.747Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721834 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:09.780Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-11-14T15:21:15.077Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721834
;

-- 2023-11-14T15:21:15.084Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721834)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> Sektion
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.AD_Org_ID
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> Sektion
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.AD_Org_ID
-- 2023-11-14T15:21:15.219Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583470,721835,0,547280,TO_TIMESTAMP('2023-11-14 16:21:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.externalsystem','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-11-14 16:21:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:15.222Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721835 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:15.223Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-11-14T15:21:16.320Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721835
;

-- 2023-11-14T15:21:16.321Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721835)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- 2023-11-14T15:21:16.401Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583473,721836,0,547280,TO_TIMESTAMP('2023-11-14 16:21:16','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.externalsystem','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-11-14 16:21:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:16.403Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721836 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:16.405Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-11-14T15:21:17.005Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721836
;

-- 2023-11-14T15:21:17.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721836)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.ExternalSystem_Config_LeichMehl_ProductMapping_ID
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> ExternalSystem_Config_LeichMehl_ProductMapping
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.ExternalSystem_Config_LeichMehl_ProductMapping_ID
-- 2023-11-14T15:21:17.135Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583476,721837,0,547280,TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','ExternalSystem_Config_LeichMehl_ProductMapping',TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:17.137Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721837 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:17.138Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581055)
;

-- 2023-11-14T15:21:17.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721837
;

-- 2023-11-14T15:21:17.143Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721837)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> Produkt
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.M_Product_ID
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> Produkt
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.M_Product_ID
-- 2023-11-14T15:21:17.238Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583480,721838,0,547280,TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'de.metas.externalsystem','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:17.240Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721838 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:17.241Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2023-11-14T15:21:17.479Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721838
;

-- 2023-11-14T15:21:17.480Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721838)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> Geschäftspartner
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> Geschäftspartner
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- 2023-11-14T15:21:17.566Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583481,721839,0,547280,TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'de.metas.externalsystem','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:17.568Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721839 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:17.569Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2023-11-14T15:21:17.694Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721839
;

-- 2023-11-14T15:21:17.695Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721839)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> PLU_File
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> PLU_File
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- 2023-11-14T15:21:17.796Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,583482,721840,0,547280,TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,1024,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU_File',TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:17.798Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721840 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:17.800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581058)
;

-- 2023-11-14T15:21:17.803Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721840
;

-- 2023-11-14T15:21:17.803Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721840)
;

-- Field: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping -> PLU File Configuration
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- Field: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> PLU File Configuration
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T15:21:17.892Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587645,721841,0,547280,TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','PLU File Configuration',TO_TIMESTAMP('2023-11-14 16:21:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-11-14T15:21:17.893Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=721841 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T15:21:17.894Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582801)
;

-- 2023-11-14T15:21:17.898Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721841
;

-- 2023-11-14T15:21:17.899Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721841)
;

-- Tab: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem)
-- UI Section: main
-- 2023-11-14T15:29:14.947Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547280,545869,TO_TIMESTAMP('2023-11-14 16:29:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','',10,TO_TIMESTAMP('2023-11-14 16:29:14','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-11-14T15:29:14.953Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545869 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main
-- UI Column: 10
-- 2023-11-14T15:29:48.660Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547163,545869,TO_TIMESTAMP('2023-11-14 16:29:48','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-11-14 16:29:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10
-- UI Element Group: default
-- 2023-11-14T15:30:06.240Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547163,551301,TO_TIMESTAMP('2023-11-14 16:30:03','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,TO_TIMESTAMP('2023-11-14 16:30:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.Geschäftspartner
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.Geschäftspartner
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- 2023-11-14T15:31:20.770Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721839,0,547280,551301,621265,'F',TO_TIMESTAMP('2023-11-14 16:31:20','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',10,0,0,TO_TIMESTAMP('2023-11-14 16:31:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.PLU File Configuration
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.PLU File Configuration
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T15:31:38.199Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721841,0,547280,551301,621266,'F',TO_TIMESTAMP('2023-11-14 16:31:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PLU File Configuration',20,0,0,TO_TIMESTAMP('2023-11-14 16:31:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.PLU_File
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- 2023-11-14T15:32:27.170Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721840,0,547280,551301,621267,'F',TO_TIMESTAMP('2023-11-14 16:32:27','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'PLU_File',30,0,0,TO_TIMESTAMP('2023-11-14 16:32:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- 2023-11-14T15:32:45.874Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721836,0,547280,551301,621268,'F',TO_TIMESTAMP('2023-11-14 16:32:45','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',40,0,0,TO_TIMESTAMP('2023-11-14 16:32:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.Geschäftspartner
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.Geschäftspartner
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.C_BPartner_ID
-- 2023-11-14T15:33:03.684Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-11-14 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621265
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.PLU File Configuration
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.PLU File Configuration
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.LeichMehl_PluFile_ConfigGroup_ID
-- 2023-11-14T15:33:03.712Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-11-14 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621266
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.PLU_File
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.PLU_File
-- 2023-11-14T15:33:03.719Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-11-14 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621267
;

-- UI Element: Produkt -> ExternalSystem_Config_LeichMehl_ProductMapping.Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- UI Element: Produkt(140,D) -> ExternalSystem_Config_LeichMehl_ProductMapping(547280,de.metas.externalsystem) -> main -> 10 -> default.Aktiv
-- Column: ExternalSystem_Config_LeichMehl_ProductMapping.IsActive
-- 2023-11-14T15:33:03.726Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-11-14 16:33:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=621268
;

