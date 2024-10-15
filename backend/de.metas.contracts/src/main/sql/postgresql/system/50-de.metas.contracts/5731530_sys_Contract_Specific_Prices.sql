

-- Tab: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise
-- Table: ModCntr_Specific_Price
-- 2024-08-20T17:09:13.457Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,
                    InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,
                    IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,583070,0,547570,542405,540359,'Y',TO_TIMESTAMP('2024-08-20 17:09:13.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.contracts','N','N','A',
        'ModCntr_Specific_Price','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Vertragsspezifische Preise','N',70,0,
        TO_TIMESTAMP('2024-08-20 17:09:13.304000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:09:13.462Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption,
                        IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,
                                                                                                                 t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,
                                                                                                                 t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547570
                                                                                                                                                                AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-08-20T17:09:13.495Z
/* DDL */  select update_tab_translation_from_ad_element(583070)
;

-- 2024-08-20T17:09:13.500Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547570)
;



-- Tab: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise
-- Table: ModCntr_Specific_Price
-- 2024-08-20T17:12:15.247Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2024-08-20 17:12:15.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547570
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Mandant
-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-08-20T17:12:49.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588089,729813,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-08-20 17:12:49.037000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:49.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:49.174Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-08-20T17:12:49.257Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729813
;

-- 2024-08-20T17:12:49.258Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729813)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Organisation
-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-08-20T17:12:49.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588090,729814,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-08-20 17:12:49.277000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:49.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:49.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-08-20T17:12:49.439Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729814
;

-- 2024-08-20T17:12:49.440Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729814)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Aktiv
-- Column: ModCntr_Specific_Price.IsActive
-- 2024-08-20T17:12:49.537Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588093,729815,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-08-20 17:12:49.443000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:49.538Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:49.539Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-08-20T17:12:49.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729815
;

-- 2024-08-20T17:12:49.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729815)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Vertragsspezifische Preise
-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-08-20T17:12:49.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588096,729816,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Vertragsspezifische Preise',TO_TIMESTAMP('2024-08-20 17:12:49.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:49.761Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:49.763Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583070)
;

-- 2024-08-20T17:12:49.766Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729816
;

-- 2024-08-20T17:12:49.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729816)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Pauschale - Vertragsperiode
-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-08-20T17:12:49.853Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588097,729817,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Pauschale - Vertragsperiode',TO_TIMESTAMP('2024-08-20 17:12:49.769000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:49.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:49.856Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541447)
;

-- 2024-08-20T17:12:49.860Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729817
;

-- 2024-08-20T17:12:49.861Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729817)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-08-20T17:12:49.957Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588098,729818,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'de.metas.contracts','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2024-08-20 17:12:49.863000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:49.958Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:49.960Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2024-08-20T17:12:49.968Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729818
;

-- 2024-08-20T17:12:49.969Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729818)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-08-20T17:12:50.069Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588099,729819,0,547570,TO_TIMESTAMP('2024-08-20 17:12:49.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Bausteine',TO_TIMESTAMP('2024-08-20 17:12:49.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.070Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.072Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582426)
;

-- 2024-08-20T17:12:50.074Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729819
;

-- 2024-08-20T17:12:50.075Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729819)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-08-20T17:12:50.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588100,729820,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'de.metas.contracts','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2024-08-20 17:12:50.077000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.175Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2024-08-20T17:12:50.197Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729820
;

-- 2024-08-20T17:12:50.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729820)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-08-20T17:12:50.294Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588101,729821,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Preis',10,'de.metas.contracts','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','N','N','N','N','N','N','Preis',TO_TIMESTAMP('2024-08-20 17:12:50.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.295Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.296Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1416)
;

-- 2024-08-20T17:12:50.299Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729821
;

-- 2024-08-20T17:12:50.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729821)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-08-20T17:12:50.389Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588103,729822,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag',10,'de.metas.contracts','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2024-08-20 17:12:50.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.391Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.392Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193)
;

-- 2024-08-20T17:12:50.401Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729822
;

-- 2024-08-20T17:12:50.402Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729822)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-08-20T17:12:50.498Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588104,729823,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerkategorie',10,'de.metas.contracts','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','N','N','N','N','N','N','Steuerkategorie',TO_TIMESTAMP('2024-08-20 17:12:50.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.499Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(211)
;

-- 2024-08-20T17:12:50.504Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729823
;

-- 2024-08-20T17:12:50.505Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729823)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-08-20T17:12:50.635Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588112,729824,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'de.metas.contracts','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2024-08-20 17:12:50.507000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2024-08-20T17:12:50.649Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729824
;

-- 2024-08-20T17:12:50.650Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729824)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Staffelpreis
-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-08-20T17:12:50.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588320,729825,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.contracts','Y','N','N','N','N','N','N','N','Staffelpreis',TO_TIMESTAMP('2024-08-20 17:12:50.653000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.761Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.762Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580373)
;

-- 2024-08-20T17:12:50.765Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729825
;

-- 2024-08-20T17:12:50.766Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729825)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Mindestwert
-- Column: ModCntr_Specific_Price.MinValue
-- 2024-08-20T17:12:50.854Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588321,729826,0,547570,TO_TIMESTAMP('2024-08-20 17:12:50.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Mindestwert',TO_TIMESTAMP('2024-08-20 17:12:50.768000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:12:50.855Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:12:50.857Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53400)
;

-- 2024-08-20T17:12:50.859Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729826
;

-- 2024-08-20T17:12:50.860Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729826)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Erstellt
-- Column: ModCntr_Specific_Price.Created
-- 2024-08-20T17:13:01.145Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588091,729827,0,547570,TO_TIMESTAMP('2024-08-20 17:13:01.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'de.metas.contracts','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2024-08-20 17:13:01.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:13:01.146Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:13:01.148Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2024-08-20T17:13:01.171Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729827
;

-- 2024-08-20T17:13:01.172Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729827)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Erstellt durch
-- Column: ModCntr_Specific_Price.CreatedBy
-- 2024-08-20T17:13:01.267Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588092,729828,0,547570,TO_TIMESTAMP('2024-08-20 17:13:01.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'de.metas.contracts','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2024-08-20 17:13:01.174000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:13:01.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:13:01.269Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2024-08-20T17:13:01.291Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729828
;

-- 2024-08-20T17:13:01.292Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729828)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Aktualisiert
-- Column: ModCntr_Specific_Price.Updated
-- 2024-08-20T17:13:01.380Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588094,729829,0,547570,TO_TIMESTAMP('2024-08-20 17:13:01.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'de.metas.contracts','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2024-08-20 17:13:01.295000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:13:01.381Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:13:01.383Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2024-08-20T17:13:01.404Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729829
;

-- 2024-08-20T17:13:01.406Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729829)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Aktualisiert durch
-- Column: ModCntr_Specific_Price.UpdatedBy
-- 2024-08-20T17:13:01.492Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588095,729830,0,547570,TO_TIMESTAMP('2024-08-20 17:13:01.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'de.metas.contracts','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2024-08-20 17:13:01.408000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-08-20T17:13:01.494Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-08-20T17:13:01.495Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2024-08-20T17:13:01.516Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729830
;

-- 2024-08-20T17:13:01.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729830)
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Pauschale - Vertragsperiode
-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-08-20T17:13:11.159Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:11.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729817
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-08-20T17:13:12.517Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:12.517000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729818
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-08-20T17:13:13.015Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:13.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729819
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-08-20T17:13:13.397Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:13.397000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729820
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-08-20T17:13:13.830Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:13.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729821
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Mandant
-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-08-20T17:13:14.254Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:14.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729813
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-08-20T17:13:14.702Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:14.702000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729823
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-08-20T17:13:15.109Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:15.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729824
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Staffelpreis
-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-08-20T17:13:15.486Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:15.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729825
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Mindestwert
-- Column: ModCntr_Specific_Price.MinValue
-- 2024-08-20T17:13:15.854Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:15.854000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729826
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-08-20T17:13:16.268Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:16.268000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729822
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Organisation
-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-08-20T17:13:16.617Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:16.616000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729814
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Aktiv
-- Column: ModCntr_Specific_Price.IsActive
-- 2024-08-20T17:13:17.217Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:17.216000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729815
;

-- Field: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> Vertragsspezifische Preise
-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-08-20T17:13:28.952Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2024-08-20 17:13:28.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=729816
;






-- Tab: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise
-- Table: ModCntr_Specific_Price
-- 2024-08-20T17:15:36.819Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2024-08-20 17:15:36.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547570
;



-- Tab: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts)
-- UI Section: main
-- 2024-08-20T17:16:02.682Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547570,546154,TO_TIMESTAMP('2024-08-20 17:16:02.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-08-20 17:16:02.558000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2024-08-20T17:16:02.683Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546154 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main
-- UI Column: 10
-- 2024-08-20T17:16:02.777Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547520,546154,TO_TIMESTAMP('2024-08-20 17:16:02.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2024-08-20 17:16:02.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2024-08-20T17:16:02.860Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547520,551895,TO_TIMESTAMP('2024-08-20 17:16:02.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2024-08-20 17:16:02.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Erstellt
-- Column: ModCntr_Specific_Price.Created
-- 2024-08-20T17:16:02.968Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729827,0,547570,551895,625271,'F',TO_TIMESTAMP('2024-08-20 17:16:02.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,10,0,TO_TIMESTAMP('2024-08-20 17:16:02.868000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Erstellt durch
-- Column: ModCntr_Specific_Price.CreatedBy
-- 2024-08-20T17:16:03.073Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729828,0,547570,551895,625272,'F',TO_TIMESTAMP('2024-08-20 17:16:02.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,20,0,TO_TIMESTAMP('2024-08-20 17:16:02.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Aktualisiert
-- Column: ModCntr_Specific_Price.Updated
-- 2024-08-20T17:16:03.192Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729829,0,547570,551895,625273,'F',TO_TIMESTAMP('2024-08-20 17:16:03.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','Y','N','Aktualisiert',0,30,0,TO_TIMESTAMP('2024-08-20 17:16:03.076000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Aktualisiert durch
-- Column: ModCntr_Specific_Price.UpdatedBy
-- 2024-08-20T17:16:03.284Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729830,0,547570,551895,625274,'F',TO_TIMESTAMP('2024-08-20 17:16:03.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','Y','N','Aktualisiert durch',0,40,0,TO_TIMESTAMP('2024-08-20 17:16:03.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Mandant
-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-08-20T17:17:22.735Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729813,0,547570,551895,625275,'F',TO_TIMESTAMP('2024-08-20 17:17:22.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2024-08-20 17:17:22.597000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Organisation
-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-08-20T17:17:31.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729814,0,547570,551895,625276,'F',TO_TIMESTAMP('2024-08-20 17:17:31.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',20,0,0,TO_TIMESTAMP('2024-08-20 17:17:31.574000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Aktiv
-- Column: ModCntr_Specific_Price.IsActive
-- 2024-08-20T17:17:37.167Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729815,0,547570,551895,625277,'F',TO_TIMESTAMP('2024-08-20 17:17:36.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',30,0,0,TO_TIMESTAMP('2024-08-20 17:17:36.898000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Vertragsspezifische Preise
-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-08-20T17:17:43.877Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729816,0,547570,551895,625278,'F',TO_TIMESTAMP('2024-08-20 17:17:43.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Vertragsspezifische Preise',40,0,0,TO_TIMESTAMP('2024-08-20 17:17:43.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Pauschale - Vertragsperiode
-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-08-20T17:17:49.136Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729817,0,547570,551895,625279,'F',TO_TIMESTAMP('2024-08-20 17:17:48.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Pauschale - Vertragsperiode',50,0,0,TO_TIMESTAMP('2024-08-20 17:17:48.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-08-20T17:17:57.911Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729818,0,547570,551895,625280,'F',TO_TIMESTAMP('2024-08-20 17:17:57.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','Y','N','N','Reihenfolge',60,0,0,TO_TIMESTAMP('2024-08-20 17:17:57.792000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-08-20T17:18:02.320Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729819,0,547570,551895,625281,'F',TO_TIMESTAMP('2024-08-20 17:18:02.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Bausteine',70,0,0,TO_TIMESTAMP('2024-08-20 17:18:02.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-08-20T17:18:08.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729820,0,547570,551895,625282,'F',TO_TIMESTAMP('2024-08-20 17:18:08.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',80,0,0,TO_TIMESTAMP('2024-08-20 17:18:08.202000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-08-20T17:18:14.417Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729821,0,547570,551895,625283,'F',TO_TIMESTAMP('2024-08-20 17:18:14.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Preis','Bezeichnet den Preis für ein Produkt oder eine Dienstleistung.','Y','N','Y','N','N','Preis',90,0,0,TO_TIMESTAMP('2024-08-20 17:18:14.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-08-20T17:18:18.596Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551895, IsDisplayed='Y', SeqNo=100,Updated=TO_TIMESTAMP('2024-08-20 17:18:18.596000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625283
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-08-20T17:18:23.098Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729822,0,547570,551895,625284,'F',TO_TIMESTAMP('2024-08-20 17:18:22.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','Y','N','N','Währung',110,0,0,TO_TIMESTAMP('2024-08-20 17:18:22.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-08-20T17:18:28.751Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729823,0,547570,551895,625285,'F',TO_TIMESTAMP('2024-08-20 17:18:28.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerkategorie','Die Steuerkategorie hilft, ähnliche Steuern zu gruppieren. Z.B. Verkaufssteuer oder Mehrwertsteuer.
','Y','N','Y','N','N','Steuerkategorie',120,0,0,TO_TIMESTAMP('2024-08-20 17:18:28.578000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-08-20T17:18:35.678Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729824,0,547570,551895,625286,'F',TO_TIMESTAMP('2024-08-20 17:18:35.497000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','Maßeinheit',130,0,0,TO_TIMESTAMP('2024-08-20 17:18:35.497000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Staffelpreis
-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-08-20T17:18:41.155Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729825,0,547570,551895,625287,'F',TO_TIMESTAMP('2024-08-20 17:18:41.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Staffelpreis',140,0,0,TO_TIMESTAMP('2024-08-20 17:18:41.015000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Mindestwert
-- Column: ModCntr_Specific_Price.MinValue
-- 2024-08-20T17:18:50.198Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729826,0,547570,551895,625288,'F',TO_TIMESTAMP('2024-08-20 17:18:48.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Mindestwert',150,0,0,TO_TIMESTAMP('2024-08-20 17:18:48.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Mandant
-- Column: ModCntr_Specific_Price.AD_Client_ID
-- 2024-08-20T17:21:19.511Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-08-20 17:21:19.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625275
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Organisation
-- Column: ModCntr_Specific_Price.AD_Org_ID
-- 2024-08-20T17:21:24.903Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-08-20 17:21:24.903000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625276
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Aktiv
-- Column: ModCntr_Specific_Price.IsActive
-- 2024-08-20T17:21:28.263Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-08-20 17:21:28.263000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625277
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Vertragsspezifische Preise
-- Column: ModCntr_Specific_Price.ModCntr_Specific_Price_ID
-- 2024-08-20T17:21:34.555Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-08-20 17:21:34.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625278
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Pauschale - Vertragsperiode
-- Column: ModCntr_Specific_Price.C_Flatrate_Term_ID
-- 2024-08-20T17:21:37.026Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2024-08-20 17:21:37.026000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625279
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-08-20T17:21:43.500Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2024-08-20 17:21:43.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625280
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-08-20T17:21:49.915Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2024-08-20 17:21:49.915000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625281
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-08-20T17:21:53.451Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2024-08-20 17:21:53.451000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625282
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-08-20T17:22:03.247Z
UPDATE AD_UI_Element SET SeqNo=40,Updated=TO_TIMESTAMP('2024-08-20 17:22:03.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625283
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-08-20T17:22:06.071Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-08-20 17:22:06.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625286
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-08-20T17:22:16.071Z
UPDATE AD_UI_Element SET SeqNo=60,Updated=TO_TIMESTAMP('2024-08-20 17:22:16.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625284
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-08-20T17:22:35.221Z
UPDATE AD_UI_Element SET SeqNo=70,Updated=TO_TIMESTAMP('2024-08-20 17:22:35.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625285
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Staffelpreis
-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-08-20T17:22:38.880Z
UPDATE AD_UI_Element SET SeqNo=80,Updated=TO_TIMESTAMP('2024-08-20 17:22:38.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625287
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Mindestwert
-- Column: ModCntr_Specific_Price.MinValue
-- 2024-08-20T17:22:43.644Z
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2024-08-20 17:22:43.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625288
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Erstellt
-- Column: ModCntr_Specific_Price.Created
-- 2024-08-20T17:23:36.932Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.932000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625271
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Erstellt durch
-- Column: ModCntr_Specific_Price.CreatedBy
-- 2024-08-20T17:23:36.936Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625272
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Aktualisiert
-- Column: ModCntr_Specific_Price.Updated
-- 2024-08-20T17:23:36.941Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.941000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625273
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Aktualisiert durch
-- Column: ModCntr_Specific_Price.UpdatedBy
-- 2024-08-20T17:23:36.945Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.945000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625274
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: ModCntr_Specific_Price.SeqNo
-- 2024-08-20T17:23:36.950Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625280
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Bausteine
-- Column: ModCntr_Specific_Price.ModCntr_Module_ID
-- 2024-08-20T17:23:36.955Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.955000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625281
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: ModCntr_Specific_Price.M_Product_ID
-- 2024-08-20T17:23:36.960Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.960000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625282
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Preis
-- Column: ModCntr_Specific_Price.Price
-- 2024-08-20T17:23:36.964Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625283
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Maßeinheit
-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-08-20T17:23:36.969Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625286
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Währung
-- Column: ModCntr_Specific_Price.C_Currency_ID
-- 2024-08-20T17:23:36.973Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625284
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Steuerkategorie
-- Column: ModCntr_Specific_Price.C_TaxCategory_ID
-- 2024-08-20T17:23:36.978Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2024-08-20 17:23:36.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625285
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Staffelpreis
-- Column: ModCntr_Specific_Price.IsScalePrice
-- 2024-08-20T17:23:37.001Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-08-20 17:23:37.001000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625287
;

-- UI Element: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise(547570,de.metas.contracts) -> main -> 10 -> default.Mindestwert
-- Column: ModCntr_Specific_Price.MinValue
-- 2024-08-20T17:23:37.006Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-08-20 17:23:37.006000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=625288
;


-- Tab: Verträge(540359,de.metas.contracts) -> Vertragsspezifische Preise
-- Table: ModCntr_Specific_Price
-- 2024-08-20T17:28:51.187Z
UPDATE AD_Tab SET Parent_Column_ID=545802,Updated=TO_TIMESTAMP('2024-08-20 17:28:51.187000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=547570
;






-- Process: ModCntr_Specific_Price_Update(de.metas.contracts.modular.process.ModCntr_Specific_Price_Update)
-- Table: ModCntr_Specific_Price
-- EntityType: de.metas.contracts
-- 2024-08-21T08:30:37.990Z
INSERT INTO AD_Table_Process (AD_Client_ID,AD_Org_ID,AD_Process_ID,AD_Table_ID,AD_Table_Process_ID,Created,CreatedBy,EntityType,IsActive,Updated,UpdatedBy,WEBUI_DocumentAction,WEBUI_IncludedTabTopAction,WEBUI_ViewAction,WEBUI_ViewQuickAction,WEBUI_ViewQuickAction_Default) VALUES (0,0,585382,542405,541517,TO_TIMESTAMP('2024-08-21 08:30:37.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.contracts','Y',TO_TIMESTAMP('2024-08-21 08:30:37.988000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N')
;


