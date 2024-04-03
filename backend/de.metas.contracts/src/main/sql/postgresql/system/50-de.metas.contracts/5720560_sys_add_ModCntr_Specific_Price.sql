-- Run mode: SWING_CLIENT

-- Tab: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices
-- Table: ModCntr_Specific_Price
-- 2024-04-02T08:57:09.736Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,DisplayLogic,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,588097,583070,0,547499,542405,540359,'Y',TO_TIMESTAMP('2024-04-02 11:57:09.379','YYYY-MM-DD HH24:MI:SS.US'),100,'@Type_Conditions/''''@=''InterimInvoice'' | @Type_Conditions/''''@=''ModularContract''','D','N','N','A','ModCntr_Specific_Price','Y','N','Y','Y','N','N','N','N','Y','Y','Y','N','N','Y','Y','N','N','N',0,'Contract Specific Prices',545802,'N',70,1,TO_TIMESTAMP('2024-04-02 11:57:09.379','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:09.741Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547499 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-04-02T08:57:09.771Z
/* DDL */  select update_tab_translation_from_ad_element(583070)
;

-- 2024-04-02T08:57:09.783Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547499)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Mandant
-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-04-02T08:57:34.050Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588089,727295,0,547499,TO_TIMESTAMP('2024-04-02 11:57:33.891','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-04-02 11:57:33.891','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:34.051Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727295 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:34.053Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-04-02T08:57:34.893Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727295
;

-- 2024-04-02T08:57:34.894Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727295)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Organisation
-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-04-02T08:57:35.021Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588090,727296,0,547499,TO_TIMESTAMP('2024-04-02 11:57:34.912','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-04-02 11:57:34.912','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:35.022Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727296 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:35.023Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-04-02T08:57:35.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727296
;

-- 2024-04-02T08:57:35.380Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727296)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Aktiv
-- Column: ModCntr_Specific_Price.IsActive
-- 2024-04-02T08:57:35.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588093,727297,0,547499,TO_TIMESTAMP('2024-04-02 11:57:35.382','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-04-02 11:57:35.382','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:35.493Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727297 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:35.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-04-02T08:57:35.583Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727297
;

-- 2024-04-02T08:57:35.583Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727297)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Contract Specific Prices
-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-04-02T08:57:35.680Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588096,727298,0,547499,TO_TIMESTAMP('2024-04-02 11:57:35.585','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Contract Specific Prices',TO_TIMESTAMP('2024-04-02 11:57:35.585','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:35.681Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727298 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:35.682Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583070)
;

-- 2024-04-02T08:57:35.684Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727298
;

-- 2024-04-02T08:57:35.685Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727298)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-02T08:57:35.773Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588098,727299,0,547499,TO_TIMESTAMP('2024-04-02 11:57:35.687','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'D','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','Y','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2024-04-02 11:57:35.687','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:35.773Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727299 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:35.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2024-04-02T08:57:35.792Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727299
;

-- 2024-04-02T08:57:35.792Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727299)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-04-02T08:57:35.879Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588100,727300,0,547499,TO_TIMESTAMP('2024-04-02 11:57:35.794','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2024-04-02 11:57:35.794','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:35.880Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727300 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:35.881Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2024-04-02T08:57:36.003Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727300
;

-- 2024-04-02T08:57:36.004Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727300)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-04-02T08:57:36.103Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588101,727301,0,547499,TO_TIMESTAMP('2024-04-02 11:57:36.006','YYYY-MM-DD HH24:MI:SS.US'),100,'Preis',10,'D','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','Y','N','N','N','N','N','Preis',TO_TIMESTAMP('2024-04-02 11:57:36.006','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:36.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727301 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:36.105Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1416)
;

-- 2024-04-02T08:57:36.110Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727301
;

-- 2024-04-02T08:57:36.111Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727301)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-02T08:57:36.207Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588103,727302,0,547499,TO_TIMESTAMP('2024-04-02 11:57:36.113','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','Y','N','N','N','N','N','Währung',TO_TIMESTAMP('2024-04-02 11:57:36.113','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:36.207Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727302 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:36.208Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2024-04-02T08:57:36.295Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727302
;

-- 2024-04-02T08:57:36.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727302)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-02T08:57:36.398Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588104,727303,0,547499,TO_TIMESTAMP('2024-04-02 11:57:36.298','YYYY-MM-DD HH24:MI:SS.US'),100,'Steuerkategorie',10,'D','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','Y','N','N','N','N','N','Steuerkategorie',TO_TIMESTAMP('2024-04-02 11:57:36.298','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T08:57:36.399Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727303 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T08:57:36.399Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(211)
;

-- 2024-04-02T08:57:36.413Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727303
;

-- 2024-04-02T08:57:36.414Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727303)
;

-- Tab: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D)
-- UI Section:
-- 2024-04-02T08:58:07.938Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547499,546080,TO_TIMESTAMP('2024-04-02 11:58:07.782','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-04-02 11:58:07.782','YYYY-MM-DD HH24:MI:SS.US'),100) RETURNING Value
;

-- 2024-04-02T08:58:07.939Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546080 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028
-- UI Column: 10
-- 2024-04-02T08:58:22.216Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547430,546080,TO_TIMESTAMP('2024-04-02 11:58:22.079','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-04-02 11:58:22.079','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10
-- UI Element Group: main
-- 2024-04-02T08:59:56.678Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,547430,551739,TO_TIMESTAMP('2024-04-02 11:59:56.51','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','main',10,TO_TIMESTAMP('2024-04-02 11:59:56.51','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-04-02T09:00:38.001Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727299,0,547499,551739,624012,'F',TO_TIMESTAMP('2024-04-02 12:00:37.825','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2024-04-02 12:00:37.825','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-02T09:01:33.380Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588099,727304,0,547499,0,TO_TIMESTAMP('2024-04-02 12:01:33.237','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Bausteine',0,10,0,1,1,TO_TIMESTAMP('2024-04-02 12:01:33.237','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T09:01:33.382Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727304 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T09:01:33.383Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582426)
;

-- 2024-04-02T09:01:33.386Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727304
;

-- 2024-04-02T09:01:33.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727304)
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Preiseinheit
-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-02T09:01:54.878Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588102,727305,0,547499,0,TO_TIMESTAMP('2024-04-02 12:01:54.722','YYYY-MM-DD HH24:MI:SS.US'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','N','Preiseinheit',0,20,0,1,1,TO_TIMESTAMP('2024-04-02 12:01:54.722','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-04-02T09:01:54.879Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=727305 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-04-02T09:01:54.880Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582754)
;

-- 2024-04-02T09:01:54.882Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727305
;

-- 2024-04-02T09:01:54.882Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(727305)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-04-02T09:03:11.592Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727304,0,547499,551739,624013,'F',TO_TIMESTAMP('2024-04-02 12:03:11.455','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Bausteine',20,0,0,TO_TIMESTAMP('2024-04-02 12:03:11.455','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-04-02T09:03:42.496Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727300,0,547499,551739,624014,'F',TO_TIMESTAMP('2024-04-02 12:03:42.345','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',30,0,0,TO_TIMESTAMP('2024-04-02 12:03:42.345','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-04-02T09:04:09.714Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727301,0,547499,551739,624015,'F',TO_TIMESTAMP('2024-04-02 12:04:09.542','YYYY-MM-DD HH24:MI:SS.US'),100,'Preis','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','N','Y','N','N','N',0,'Preis',40,0,0,TO_TIMESTAMP('2024-04-02 12:04:09.542','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Preiseinheit
-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-02T09:04:28.495Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727305,0,547499,551739,624016,'F',TO_TIMESTAMP('2024-04-02 12:04:28.33','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',50,0,0,TO_TIMESTAMP('2024-04-02 12:04:28.33','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-04-02T09:04:39.783Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727302,0,547499,551739,624017,'F',TO_TIMESTAMP('2024-04-02 12:04:39.643','YYYY-MM-DD HH24:MI:SS.US'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',60,0,0,TO_TIMESTAMP('2024-04-02 12:04:39.643','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-04-02T09:04:51.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,727303,0,547499,551739,624018,'F',TO_TIMESTAMP('2024-04-02 12:04:50.824','YYYY-MM-DD HH24:MI:SS.US'),100,'Steuerkategorie','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','N','Y','N','N','N',0,'Steuerkategorie',70,0,0,TO_TIMESTAMP('2024-04-02 12:04:50.824','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Table: ModCntr_Specific_Price
-- 2024-04-02T10:07:42.093Z
UPDATE AD_Table SET EntityType='de.metas.contracts',Updated=TO_TIMESTAMP('2024-04-02 13:07:42.091','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=542405
;

