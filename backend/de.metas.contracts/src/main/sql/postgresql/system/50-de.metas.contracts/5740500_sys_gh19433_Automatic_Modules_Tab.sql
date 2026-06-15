-- Run mode: SWING_CLIENT

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-11-25T17:23:56.032Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,586804,582426,0,547697,542340,541712,'Y',TO_TIMESTAMP('2024-11-25 19:23:55.794','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','N','N','A','Automatic_ModCntr_Module','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Bausteine',586789,'N',30,1,TO_TIMESTAMP('2024-11-25 19:23:55.794','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:23:56.046Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547697 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2024-11-25T17:23:56.078Z
/* DDL */  select update_tab_translation_from_ad_element(582426)
;

-- 2024-11-25T17:23:56.094Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547697)
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-11-25T17:31:38.392Z
UPDATE AD_Tab SET AllowQuickInput='N', IsGenericZoomTarget='Y',Updated=TO_TIMESTAMP('2024-11-25 19:31:38.392','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547697
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Mandant
-- Column: ModCntr_Module.AD_Client_ID
-- 2024-11-25T17:31:50.177Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586795,734040,0,547697,TO_TIMESTAMP('2024-11-25 19:31:50.001','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.contracts','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2024-11-25 19:31:50.001','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:50.180Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734040 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:50.183Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2024-11-25T17:31:50.376Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734040
;

-- 2024-11-25T17:31:50.381Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734040)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Organisation
-- Column: ModCntr_Module.AD_Org_ID
-- 2024-11-25T17:31:50.490Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586796,734041,0,547697,TO_TIMESTAMP('2024-11-25 19:31:50.396','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.contracts','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2024-11-25 19:31:50.396','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:50.492Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734041 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:50.494Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2024-11-25T17:31:50.597Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734041
;

-- 2024-11-25T17:31:50.597Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734041)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Aktiv
-- Column: ModCntr_Module.IsActive
-- 2024-11-25T17:31:50.698Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586799,734042,0,547697,TO_TIMESTAMP('2024-11-25 19:31:50.599','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.contracts','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2024-11-25 19:31:50.599','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:50.700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734042 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:50.702Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2024-11-25T17:31:50.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734042
;

-- 2024-11-25T17:31:50.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734042)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Bausteine
-- Column: ModCntr_Module.ModCntr_Module_ID
-- 2024-11-25T17:31:50.898Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586802,734043,0,547697,TO_TIMESTAMP('2024-11-25 19:31:50.805','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Bausteine',TO_TIMESTAMP('2024-11-25 19:31:50.805','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:50.899Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734043 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:50.901Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582426)
;

-- 2024-11-25T17:31:50.905Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734043
;

-- 2024-11-25T17:31:50.906Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734043)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Reihenfolge
-- Column: ModCntr_Module.SeqNo
-- 2024-11-25T17:31:50.997Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586803,734044,0,547697,TO_TIMESTAMP('2024-11-25 19:31:50.908','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',22,'de.metas.contracts','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2024-11-25 19:31:50.908','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:50.999Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734044 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2024-11-25T17:31:51.015Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734044
;

-- 2024-11-25T17:31:51.016Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734044)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Einstellungen für modulare Verträge
-- Column: ModCntr_Module.ModCntr_Settings_ID
-- 2024-11-25T17:31:51.112Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586804,734045,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.02','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Einstellungen für modulare Verträge',TO_TIMESTAMP('2024-11-25 19:31:51.02','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.114Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734045 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425)
;

-- 2024-11-25T17:31:51.133Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734045
;

-- 2024-11-25T17:31:51.134Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734045)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Name
-- Column: ModCntr_Module.Name
-- 2024-11-25T17:31:51.236Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586805,734046,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.139','YYYY-MM-DD HH24:MI:SS.US'),100,'',255,'de.metas.contracts','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2024-11-25 19:31:51.139','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.238Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734046 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.240Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2024-11-25T17:31:51.304Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734046
;

-- 2024-11-25T17:31:51.304Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734046)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Produkt
-- Column: ModCntr_Module.M_Product_ID
-- 2024-11-25T17:31:51.405Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586806,734047,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.311','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'de.metas.contracts','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2024-11-25 19:31:51.311','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.407Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734047 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.409Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2024-11-25T17:31:51.460Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734047
;

-- 2024-11-25T17:31:51.460Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734047)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Abrechnungszeilengruppe
-- Column: ModCntr_Module.InvoicingGroup
-- 2024-11-25T17:31:51.553Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586807,734048,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.466','YYYY-MM-DD HH24:MI:SS.US'),100,255,'de.metas.contracts','Y','N','N','N','N','N','N','N','Abrechnungszeilengruppe',TO_TIMESTAMP('2024-11-25 19:31:51.466','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.554Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734048 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.555Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582427)
;

-- 2024-11-25T17:31:51.559Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734048
;

-- 2024-11-25T17:31:51.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734048)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Vertragsbaustein Typ
-- Column: ModCntr_Module.ModCntr_Type_ID
-- 2024-11-25T17:31:51.655Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586809,734049,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.562','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.contracts','Y','N','N','N','N','N','N','N','Vertragsbaustein Typ',TO_TIMESTAMP('2024-11-25 19:31:51.562','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.657Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.659Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582395)
;

-- 2024-11-25T17:31:51.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734049
;

-- 2024-11-25T17:31:51.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734049)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-11-25T17:31:51.764Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588229,734050,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.67','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'de.metas.contracts','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2024-11-25 19:31:51.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.766Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734050 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.768Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2024-11-25T17:31:51.819Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734050
;

-- 2024-11-25T17:31:51.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734050)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> Verarbeitet
-- Column: ModCntr_Module.Processed
-- 2024-11-25T17:31:51.904Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588257,734051,0,547697,TO_TIMESTAMP('2024-11-25 19:31:51.822','YYYY-MM-DD HH24:MI:SS.US'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'de.metas.contracts','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2024-11-25 19:31:51.822','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T17:31:51.905Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734051 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-11-25T17:31:51.906Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2024-11-25T17:31:51.933Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734051
;

-- 2024-11-25T17:31:51.934Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734051)
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts)
-- UI Section: main
-- 2024-11-25T17:32:04.901Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547697,546280,TO_TIMESTAMP('2024-11-25 19:32:04.784','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-11-25 19:32:04.784','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2024-11-25T17:32:04.905Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546280 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main
-- UI Column: 10
-- 2024-11-25T17:32:11.097Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547676,546280,TO_TIMESTAMP('2024-11-25 19:32:10.957','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2024-11-25 19:32:10.957','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10
-- UI Element Group: default
-- 2024-11-25T17:32:21.297Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547676,552184,TO_TIMESTAMP('2024-11-25 19:32:21.138','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2024-11-25 19:32:21.138','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: ModCntr_Module.SeqNo
-- 2024-11-25T17:32:44.134Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734044,0,547697,627364,552184,'F',TO_TIMESTAMP('2024-11-25 19:32:43.998','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',10,0,0,TO_TIMESTAMP('2024-11-25 19:32:43.998','YYYY-MM-DD HH24:MI:SS.US'),100)
;


-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: ModCntr_Module.Name
-- 2024-11-25T17:39:33.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734046,0,547697,627365,552184,'F',TO_TIMESTAMP('2024-11-25 19:39:33.288','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2024-11-25 19:39:33.288','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Abrechnungszeilengruppe
-- Column: ModCntr_Module.InvoicingGroup
-- 2024-11-25T17:39:47.839Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734048,0,547697,627366,552184,'F',TO_TIMESTAMP('2024-11-25 19:39:47.703','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Abrechnungszeilengruppe',30,0,0,TO_TIMESTAMP('2024-11-25 19:39:47.703','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: ModCntr_Module.M_Product_ID
-- 2024-11-25T17:39:54.657Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734047,0,547697,627367,552184,'F',TO_TIMESTAMP('2024-11-25 19:39:54.535','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',40,0,0,TO_TIMESTAMP('2024-11-25 19:39:54.535','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Vertragsbaustein Typ
-- Column: ModCntr_Module.ModCntr_Type_ID
-- 2024-11-25T17:40:44.204Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734049,0,547697,627368,552184,'F',TO_TIMESTAMP('2024-11-25 19:40:44.048','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Vertragsbaustein Typ',50,0,0,TO_TIMESTAMP('2024-11-25 19:40:44.048','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-11-25T17:40:58.798Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734050,0,547697,627369,552184,'F',TO_TIMESTAMP('2024-11-25 19:40:58.671','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',60,0,0,TO_TIMESTAMP('2024-11-25 19:40:58.671','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Reihenfolge
-- Column: ModCntr_Module.SeqNo
-- 2024-11-25T17:41:18.553Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2024-11-25 19:41:18.553','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627364
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Name
-- Column: ModCntr_Module.Name
-- 2024-11-25T17:41:18.557Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2024-11-25 19:41:18.557','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627365
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Abrechnungszeilengruppe
-- Column: ModCntr_Module.InvoicingGroup
-- 2024-11-25T17:41:18.560Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2024-11-25 19:41:18.56','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627366
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Produkt
-- Column: ModCntr_Module.M_Product_ID
-- 2024-11-25T17:41:18.565Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2024-11-25 19:41:18.565','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627367
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Vertragsbaustein Typ
-- Column: ModCntr_Module.ModCntr_Type_ID
-- 2024-11-25T17:41:18.569Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2024-11-25 19:41:18.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627368
;

-- UI Element: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine(547697,de.metas.contracts) -> main -> 10 -> default.Beschreibung
-- Column: ModCntr_Module.Description
-- 2024-11-25T17:41:18.572Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2024-11-25 19:41:18.572','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=627369
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-11-25T18:03:19.803Z
UPDATE AD_Tab SET WhereClause='Processed=''Y''',Updated=TO_TIMESTAMP('2024-11-25 20:03:19.803','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547697
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Bausteine
-- Table: ModCntr_Module
-- 2024-11-25T18:03:28.154Z
UPDATE AD_Tab SET WhereClause='Processed=''N''',Updated=TO_TIMESTAMP('2024-11-25 20:03:28.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547014
;
-- 2024-11-25T18:08:28.191Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583379,0,TO_TIMESTAMP('2024-11-25 20:08:28.043','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.contracts','Y','Automatische Bausteine','Automatische Bausteine',TO_TIMESTAMP('2024-11-25 20:08:28.043','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-11-25T18:08:28.196Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583379 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2024-11-25T18:08:37.032Z
UPDATE AD_Element_Trl SET Name='Automatic Modules', PrintName='Automatic Modules',Updated=TO_TIMESTAMP('2024-11-25 20:08:37.032','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583379 AND AD_Language='en_US'
;

-- 2024-11-25T18:08:37.035Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583379,'en_US')
;

-- Tab: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Automatische Bausteine
-- Table: ModCntr_Module
-- 2024-11-25T18:09:05.987Z
UPDATE AD_Tab SET AD_Element_ID=583379, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Automatische Bausteine',Updated=TO_TIMESTAMP('2024-11-25 20:09:05.987','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547697
;

-- 2024-11-25T18:09:05.992Z
UPDATE AD_Tab_Trl trl SET Name='Automatische Bausteine' WHERE AD_Tab_ID=547697 AND AD_Language='de_DE'
;

-- 2024-11-25T18:09:05.995Z
/* DDL */  select update_tab_translation_from_ad_element(583379)
;

-- 2024-11-25T18:09:06.006Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547697)
;

-- Field: Einstellungen für modulare Verträge(541712,de.metas.contracts) -> Automatische Bausteine(547697,de.metas.contracts) -> Sequenz
-- Column: ModCntr_Module.SeqNo
-- 2024-11-25T18:11:48.807Z
UPDATE AD_Field SET AD_Name_ID=582431, Description=NULL, Help=NULL, Name='Sequenz',Updated=TO_TIMESTAMP('2024-11-25 20:11:48.807','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=734044
;

-- 2024-11-25T18:11:48.811Z
UPDATE AD_Field_Trl trl SET Description=NULL,Help=NULL,Name='Sequenz' WHERE AD_Field_ID=734044 AND AD_Language='de_DE'
;

-- 2024-11-25T18:11:48.812Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582431)
;

-- 2024-11-25T18:11:48.826Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734044
;

-- 2024-11-25T18:11:48.832Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(734044)
;

