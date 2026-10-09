-- Run mode: SWING_CLIENT

-- Tab: Rechnung(167,D) -> Invoice detailed informations
-- Table: C_Invoice_Detail
-- 2025-03-27T15:52:26.747Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,551192,542551,0,547937,540614,167,'Y',TO_TIMESTAMP('2025-03-27 17:52:26.459','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.swat','N','N','A','C_Invoice_Detail','Y','N','Y','Y','N','N','N','Y','Y','Y','N','N','N','Y','Y','N','N','N',0,'Invoice detailed informations','N',35,0,TO_TIMESTAMP('2025-03-27 17:52:26.459','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:52:26.762Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547937 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-03-27T15:52:26.805Z
/* DDL */  select update_tab_translation_from_ad_element(542551)
;

-- 2025-03-27T15:52:26.820Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547937)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Mandant
-- Column: C_Invoice_Detail.AD_Client_ID
-- 2025-03-27T15:53:04.160Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551184,741528,0,547937,TO_TIMESTAMP('2025-03-27 17:53:03.973','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',10,'de.metas.swat','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-03-27 17:53:03.973','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:04.164Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:04.169Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-03-27T15:53:04.299Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741528
;

-- 2025-03-27T15:53:04.300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741528)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Organisation
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2025-03-27T15:53:04.410Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551185,741529,0,547937,TO_TIMESTAMP('2025-03-27 17:53:04.316','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',10,'de.metas.swat','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Organisation',TO_TIMESTAMP('2025-03-27 17:53:04.316','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:04.412Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741529 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:04.414Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-03-27T15:53:04.519Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741529
;

-- 2025-03-27T15:53:04.520Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741529)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Aktiv
-- Column: C_Invoice_Detail.IsActive
-- 2025-03-27T15:53:04.604Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551188,741530,0,547937,TO_TIMESTAMP('2025-03-27 17:53:04.522','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'de.metas.swat','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-03-27 17:53:04.522','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:04.605Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:04.608Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-03-27T15:53:04.714Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741530
;

-- 2025-03-27T15:53:04.715Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741530)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Invoice detailed informations
-- Column: C_Invoice_Detail.C_Invoice_Detail_ID
-- 2025-03-27T15:53:04.808Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551191,741531,0,547937,TO_TIMESTAMP('2025-03-27 17:53:04.717','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.swat','Y','N','N','N','N','N','N','N','Invoice detailed informations',TO_TIMESTAMP('2025-03-27 17:53:04.717','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:04.809Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:04.810Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542551)
;

-- 2025-03-27T15:53:04.814Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741531
;

-- 2025-03-27T15:53:04.815Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741531)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Rechnung
-- Column: C_Invoice_Detail.C_Invoice_ID
-- 2025-03-27T15:53:04.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551192,741532,0,547937,TO_TIMESTAMP('2025-03-27 17:53:04.817','YYYY-MM-DD HH24:MI:SS.US'),100,'Invoice Identifier',10,'de.metas.swat','The Invoice Document.','Y','N','N','N','N','N','N','N','Rechnung',TO_TIMESTAMP('2025-03-27 17:53:04.817','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:04.912Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:04.914Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1008)
;

-- 2025-03-27T15:53:04.929Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741532
;

-- 2025-03-27T15:53:04.930Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741532)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Rechnungsposition
-- Column: C_Invoice_Detail.C_InvoiceLine_ID
-- 2025-03-27T15:53:05.017Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551193,741533,0,547937,TO_TIMESTAMP('2025-03-27 17:53:04.933','YYYY-MM-DD HH24:MI:SS.US'),100,'Rechnungszeile',10,'de.metas.swat','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','N','N','N','N','N','Rechnungsposition',TO_TIMESTAMP('2025-03-27 17:53:04.933','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1076)
;

-- 2025-03-27T15:53:05.039Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741533
;

-- 2025-03-27T15:53:05.040Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741533)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Reihenfolge
-- Column: C_Invoice_Detail.SeqNo
-- 2025-03-27T15:53:05.132Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551194,741534,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.044','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst',10,'de.metas.swat','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','N','N','N','N','N','Reihenfolge',TO_TIMESTAMP('2025-03-27 17:53:05.044','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.134Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741534 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.136Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(566)
;

-- 2025-03-27T15:53:05.150Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741534
;

-- 2025-03-27T15:53:05.151Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741534)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> davor andrucken
-- Column: C_Invoice_Detail.IsPrintBefore
-- 2025-03-27T15:53:05.237Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551195,741535,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.155','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.swat','If set, this line will be printed before the line to which it links.','Y','N','N','N','N','N','N','N','davor andrucken',TO_TIMESTAMP('2025-03-27 17:53:05.155','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.238Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741535 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.241Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542552)
;

-- 2025-03-27T15:53:05.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741535
;

-- 2025-03-27T15:53:05.248Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741535)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Produkt
-- Column: C_Invoice_Detail.M_Product_ID
-- 2025-03-27T15:53:05.341Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551196,741536,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.252','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel',10,'de.metas.swat','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2025-03-27 17:53:05.252','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.342Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741536 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.344Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2025-03-27T15:53:05.385Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741536
;

-- 2025-03-27T15:53:05.385Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741536)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Beschreibung
-- Column: C_Invoice_Detail.Description
-- 2025-03-27T15:53:05.465Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551197,741537,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.387','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'de.metas.swat','Y','N','N','N','N','N','N','N','Beschreibung',TO_TIMESTAMP('2025-03-27 17:53:05.387','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.467Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741537 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.469Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2025-03-27T15:53:05.529Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741537
;

-- 2025-03-27T15:53:05.530Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741537)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Notiz
-- Column: C_Invoice_Detail.Note
-- 2025-03-27T15:53:05.622Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551198,741538,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.532','YYYY-MM-DD HH24:MI:SS.US'),100,'Optional weitere Information',2000,'de.metas.swat','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','N','N','N','N','N','Notiz',TO_TIMESTAMP('2025-03-27 17:53:05.532','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.624Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741538 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.626Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1115)
;

-- 2025-03-27T15:53:05.636Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741538
;

-- 2025-03-27T15:53:05.637Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741538)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Merkmale
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2025-03-27T15:53:05.728Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551199,741539,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.641','YYYY-MM-DD HH24:MI:SS.US'),100,'Merkmals Ausprägungen zum Produkt',10,'de.metas.swat','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','N','N','N','N','N','Merkmale',TO_TIMESTAMP('2025-03-27 17:53:05.641','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.730Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741539 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.733Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2025-03-27T15:53:05.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741539
;

-- 2025-03-27T15:53:05.743Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741539)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Menge
-- Column: C_Invoice_Detail.Qty
-- 2025-03-27T15:53:05.837Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551200,741540,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.745','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge',10,'de.metas.swat','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2025-03-27 17:53:05.745','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.839Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741540 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.841Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2025-03-27T15:53:05.858Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741540
;

-- 2025-03-27T15:53:05.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741540)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Maßeinheit
-- Column: C_Invoice_Detail.C_UOM_ID
-- 2025-03-27T15:53:05.946Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551201,741541,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.862','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit',10,'de.metas.swat','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2025-03-27 17:53:05.862','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:05.948Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741541 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:05.951Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2025-03-27T15:53:05.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741541
;

-- 2025-03-27T15:53:05.969Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741541)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Bestellte Menge in Preiseinheit
-- Column: C_Invoice_Detail.QtyEnteredInPriceUOM
-- 2025-03-27T15:53:06.055Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551202,741542,0,547937,TO_TIMESTAMP('2025-03-27 17:53:05.972','YYYY-MM-DD HH24:MI:SS.US'),100,'Bestellte Menge in Preiseinheit',10,'de.metas.swat','Die "Bestellte Menge in Preiseinheit" bezeichnet die Menge einer Ware, die bestellt wurde, umgerechnet in die Mengeneinheit auf die sich der Preis bezieht.','Y','N','N','N','N','N','N','N','Bestellte Menge in Preiseinheit',TO_TIMESTAMP('2025-03-27 17:53:05.972','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.057Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741542 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.059Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542528)
;

-- 2025-03-27T15:53:06.066Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741542
;

-- 2025-03-27T15:53:06.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741542)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Preiseinheit
-- Column: C_Invoice_Detail.Price_UOM_ID
-- 2025-03-27T15:53:06.160Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551203,741543,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.072','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.swat','Y','N','N','N','N','N','N','N','Preiseinheit',TO_TIMESTAMP('2025-03-27 17:53:06.072','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.161Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741543 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.162Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542464)
;

-- 2025-03-27T15:53:06.166Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741543
;

-- 2025-03-27T15:53:06.166Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741543)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Preis
-- Column: C_Invoice_Detail.PriceEntered
-- 2025-03-27T15:53:06.254Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551204,741544,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.168','YYYY-MM-DD HH24:MI:SS.US'),100,'Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit',10,'de.metas.swat','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet','Y','N','N','N','N','N','N','N','Preis',TO_TIMESTAMP('2025-03-27 17:53:06.168','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.256Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741544 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.258Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2588)
;

-- 2025-03-27T15:53:06.265Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741544
;

-- 2025-03-27T15:53:06.266Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741544)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Einzelpreis
-- Column: C_Invoice_Detail.PriceActual
-- 2025-03-27T15:53:06.354Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551205,741545,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.27','YYYY-MM-DD HH24:MI:SS.US'),100,'Effektiver Preis',10,'de.metas.swat','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','N','N','N','N','N','Einzelpreis',TO_TIMESTAMP('2025-03-27 17:53:06.27','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.356Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741545 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.358Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(519)
;

-- 2025-03-27T15:53:06.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741545
;

-- 2025-03-27T15:53:06.368Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741545)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Rabatt %
-- Column: C_Invoice_Detail.Discount
-- 2025-03-27T15:53:06.472Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551206,741546,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.371','YYYY-MM-DD HH24:MI:SS.US'),100,'Abschlag in Prozent',10,'de.metas.swat','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','N','N','N','N','N','N','Rabatt %',TO_TIMESTAMP('2025-03-27 17:53:06.371','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.474Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741546 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.476Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(280)
;

-- 2025-03-27T15:53:06.485Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741546
;

-- 2025-03-27T15:53:06.486Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741546)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Packvorschrift
-- Column: C_Invoice_Detail.M_HU_PI_Item_Product_ID
-- 2025-03-27T15:53:06.573Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551207,741547,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.49','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.swat','Y','N','N','N','N','N','N','N','Packvorschrift',TO_TIMESTAMP('2025-03-27 17:53:06.49','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.575Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741547 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.577Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2025-03-27T15:53:06.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741547
;

-- 2025-03-27T15:53:06.585Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741547)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> TU Anzahl
-- Column: C_Invoice_Detail.QtyTU
-- 2025-03-27T15:53:06.677Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551278,741548,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.588','YYYY-MM-DD HH24:MI:SS.US'),100,14,'de.metas.swat','Y','N','N','N','N','N','N','N','TU Anzahl',TO_TIMESTAMP('2025-03-27 17:53:06.588','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.679Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741548 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.681Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542490)
;

-- 2025-03-27T15:53:06.688Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741548
;

-- 2025-03-27T15:53:06.689Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741548)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Rechnungskandidat
-- Column: C_Invoice_Detail.C_Invoice_Candidate_ID
-- 2025-03-27T15:53:06.780Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551279,741549,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.693','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.swat','Y','N','N','N','N','N','N','N','Rechnungskandidat',TO_TIMESTAMP('2025-03-27 17:53:06.693','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.782Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741549 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.783Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541266)
;

-- 2025-03-27T15:53:06.788Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741549
;

-- 2025-03-27T15:53:06.789Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741549)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> andrucken
-- Column: C_Invoice_Detail.IsPrinted
-- 2025-03-27T15:53:06.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551280,741550,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.79','YYYY-MM-DD HH24:MI:SS.US'),100,'Indicates if this document / line is printed',1,'de.metas.swat','The Printed checkbox indicates if this document or line will included when printing.','Y','N','N','N','N','N','N','N','andrucken',TO_TIMESTAMP('2025-03-27 17:53:06.79','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.876Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741550 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(399)
;

-- 2025-03-27T15:53:06.887Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741550
;

-- 2025-03-27T15:53:06.887Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741550)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Anteil
-- Column: C_Invoice_Detail.Percentage
-- 2025-03-27T15:53:06.972Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551282,741551,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.891','YYYY-MM-DD HH24:MI:SS.US'),100,'',10,'de.metas.swat','','Y','N','N','N','N','N','N','N','Anteil',TO_TIMESTAMP('2025-03-27 17:53:06.891','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:06.973Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741551 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:06.974Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2004)
;

-- 2025-03-27T15:53:06.977Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741551
;

-- 2025-03-27T15:53:06.978Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741551)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Packvorschrift (TU)
-- Column: C_Invoice_Detail.M_TU_HU_PI_ID
-- 2025-03-27T15:53:07.063Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551288,741552,0,547937,TO_TIMESTAMP('2025-03-27 17:53:06.98','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.swat','Y','N','N','N','N','N','N','N','Packvorschrift (TU)',TO_TIMESTAMP('2025-03-27 17:53:06.98','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:07.065Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741552 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:07.066Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542489)
;

-- 2025-03-27T15:53:07.069Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741552
;

-- 2025-03-27T15:53:07.069Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741552)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Detail-Info statt Rechnungszeile andrucken
-- Column: C_Invoice_Detail.IsDetailOverridesLine
-- 2025-03-27T15:53:07.166Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,551291,741553,0,547937,TO_TIMESTAMP('2025-03-27 17:53:07.071','YYYY-MM-DD HH24:MI:SS.US'),100,1,'de.metas.swat','Y','N','N','N','N','N','N','N','Detail-Info statt Rechnungszeile andrucken',TO_TIMESTAMP('2025-03-27 17:53:07.071','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:07.168Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741553 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:07.170Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542573)
;

-- 2025-03-27T15:53:07.176Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741553
;

-- 2025-03-27T15:53:07.177Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741553)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Produktionsauftrag
-- Column: C_Invoice_Detail.PP_Order_ID
-- 2025-03-27T15:53:07.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,552846,741554,0,547937,TO_TIMESTAMP('2025-03-27 17:53:07.181','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.swat','Y','N','N','N','N','N','N','N','Produktionsauftrag',TO_TIMESTAMP('2025-03-27 17:53:07.181','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:07.271Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741554 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:07.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53276)
;

-- 2025-03-27T15:53:07.281Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741554
;

-- 2025-03-27T15:53:07.282Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741554)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Label
-- Column: C_Invoice_Detail.Label
-- 2025-03-27T15:53:07.375Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569733,741555,0,547937,TO_TIMESTAMP('2025-03-27 17:53:07.285','YYYY-MM-DD HH24:MI:SS.US'),100,256,'de.metas.swat','Y','N','N','N','N','N','N','N','Label',TO_TIMESTAMP('2025-03-27 17:53:07.285','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:07.376Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741555 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:07.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576005)
;

-- 2025-03-27T15:53:07.385Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741555
;

-- 2025-03-27T15:53:07.386Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741555)
;

-- Field: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> Datum
-- Column: C_Invoice_Detail.Date
-- 2025-03-27T15:53:07.479Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,570889,741556,0,547937,TO_TIMESTAMP('2025-03-27 17:53:07.39','YYYY-MM-DD HH24:MI:SS.US'),100,7,'de.metas.swat','Y','N','N','N','N','N','N','N','Datum',TO_TIMESTAMP('2025-03-27 17:53:07.39','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-03-27T15:53:07.481Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=741556 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-03-27T15:53:07.483Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577762)
;

-- 2025-03-27T15:53:07.488Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=741556
;

-- 2025-03-27T15:53:07.488Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(741556)
;

-- Tab: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat)
-- UI Section: main
-- 2025-03-27T15:53:40.044Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547937,546519,TO_TIMESTAMP('2025-03-27 17:53:39.931','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-03-27 17:53:39.931','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2025-03-27T15:53:40.046Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546519 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main
-- UI Column: 10
-- 2025-03-27T15:53:51.513Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547959,546519,TO_TIMESTAMP('2025-03-27 17:53:51.383','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2025-03-27 17:53:51.383','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10
-- UI Element Group: default
-- 2025-03-27T15:54:01.173Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547959,552701,TO_TIMESTAMP('2025-03-27 17:54:01.045','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2025-03-27 17:54:01.045','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Invoice detailed informations
-- Column: C_Invoice_Detail.C_Invoice_Detail_ID
-- 2025-03-27T15:55:00.974Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741531,0,547937,631154,552701,'F',TO_TIMESTAMP('2025-03-27 17:55:00.806','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','N','N','N','N',0,'Invoice detailed informations',0,0,0,TO_TIMESTAMP('2025-03-27 17:55:00.806','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Rechnung
-- Column: C_Invoice_Detail.C_Invoice_ID
-- 2025-03-27T15:55:54.395Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741532,0,547937,631156,552701,'F',TO_TIMESTAMP('2025-03-27 17:55:54.254','YYYY-MM-DD HH24:MI:SS.US'),100,'Invoice Identifier','The Invoice Document.','Y','N','N','Y','N','N','N',0,'Rechnung',10,0,0,TO_TIMESTAMP('2025-03-27 17:55:54.254','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Rechnungsposition
-- Column: C_Invoice_Detail.C_InvoiceLine_ID
-- 2025-03-27T15:56:14.798Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741533,0,547937,631157,552701,'F',TO_TIMESTAMP('2025-03-27 17:56:14.67','YYYY-MM-DD HH24:MI:SS.US'),100,'Rechnungszeile','Eine "Rechnungszeile" bezeichnet eine einzelne Position auf der Rechnung.','Y','N','N','Y','N','N','N',0,'Rechnungsposition',20,0,0,TO_TIMESTAMP('2025-03-27 17:56:14.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Aktiv
-- Column: C_Invoice_Detail.IsActive
-- 2025-03-27T15:56:31.464Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741530,0,547937,631158,552701,'F',TO_TIMESTAMP('2025-03-27 17:56:31.329','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',30,0,0,TO_TIMESTAMP('2025-03-27 17:56:31.329','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.davor andrucken
-- Column: C_Invoice_Detail.IsPrintBefore
-- 2025-03-27T15:56:52.376Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741535,0,547937,631159,552701,'F',TO_TIMESTAMP('2025-03-27 17:56:52.239','YYYY-MM-DD HH24:MI:SS.US'),100,'If set, this line will be printed before the line to which it links.','Y','N','N','Y','N','N','N',0,'davor andrucken',40,0,0,TO_TIMESTAMP('2025-03-27 17:56:52.239','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Reihenfolge
-- Column: C_Invoice_Detail.SeqNo
-- 2025-03-27T15:57:17.542Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741534,0,547937,631160,552701,'F',TO_TIMESTAMP('2025-03-27 17:57:17.415','YYYY-MM-DD HH24:MI:SS.US'),100,'Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst','"Reihenfolge" bestimmt die Reihenfolge der Einträge','Y','N','N','Y','N','N','N',0,'Reihenfolge',50,0,0,TO_TIMESTAMP('2025-03-27 17:57:17.415','YYYY-MM-DD HH24:MI:SS.US'),100,'S')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: C_Invoice_Detail.M_Product_ID
-- 2025-03-27T15:58:17.403Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741536,0,547937,631161,552701,'F',TO_TIMESTAMP('2025-03-27 17:58:17.288','YYYY-MM-DD HH24:MI:SS.US'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',60,0,0,TO_TIMESTAMP('2025-03-27 17:58:17.288','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: C_Invoice_Detail.Description
-- 2025-03-27T15:58:35.193Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741537,0,547937,631162,552701,'F',TO_TIMESTAMP('2025-03-27 17:58:35.063','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Beschreibung',70,0,0,TO_TIMESTAMP('2025-03-27 17:58:35.063','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Notiz
-- Column: C_Invoice_Detail.Note
-- 2025-03-27T15:58:52.172Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741538,0,547937,631163,552701,'F',TO_TIMESTAMP('2025-03-27 17:58:52.044','YYYY-MM-DD HH24:MI:SS.US'),100,'Optional weitere Information','Das Notiz-Feld erlaubt es, weitere Informationen zu diesem Eintrag anzugeben','Y','N','N','Y','N','N','N',0,'Notiz',80,0,0,TO_TIMESTAMP('2025-03-27 17:58:52.044','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Datum
-- Column: C_Invoice_Detail.Date
-- 2025-03-27T15:59:10.898Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741556,0,547937,631164,552701,'F',TO_TIMESTAMP('2025-03-27 17:59:10.776','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Datum',85,0,0,TO_TIMESTAMP('2025-03-27 17:59:10.776','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2025-03-27T15:59:36.458Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741539,0,547937,631165,552701,'F',TO_TIMESTAMP('2025-03-27 17:59:36.324','YYYY-MM-DD HH24:MI:SS.US'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','N','N','N',0,'Merkmale',90,0,0,TO_TIMESTAMP('2025-03-27 17:59:36.324','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Menge
-- Column: C_Invoice_Detail.Qty
-- 2025-03-27T16:00:14.353Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741540,0,547937,631166,552701,'F',TO_TIMESTAMP('2025-03-27 18:00:11.166','YYYY-MM-DD HH24:MI:SS.US'),100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',100,0,0,TO_TIMESTAMP('2025-03-27 18:00:11.166','YYYY-MM-DD HH24:MI:SS.US'),100,'S')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Maßeinheit
-- Column: C_Invoice_Detail.C_UOM_ID
-- 2025-03-27T16:00:37.028Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741541,0,547937,631167,552701,'F',TO_TIMESTAMP('2025-03-27 18:00:36.896','YYYY-MM-DD HH24:MI:SS.US'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',110,0,0,TO_TIMESTAMP('2025-03-27 18:00:36.896','YYYY-MM-DD HH24:MI:SS.US'),100,'S')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Preis
-- Column: C_Invoice_Detail.PriceEntered
-- 2025-03-27T16:00:57.590Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741544,0,547937,631168,552701,'F',TO_TIMESTAMP('2025-03-27 18:00:57.439','YYYY-MM-DD HH24:MI:SS.US'),100,'Eingegebener Preis - der Preis basierend auf der gewählten Mengeneinheit','Der eingegebene Preis wird basierend auf der Mengenumrechnung in den Effektivpreis umgerechnet','Y','N','N','Y','N','N','N',0,'Preis',120,0,0,TO_TIMESTAMP('2025-03-27 18:00:57.439','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Preiseinheit
-- Column: C_Invoice_Detail.Price_UOM_ID
-- 2025-03-27T16:01:19.077Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741543,0,547937,631169,552701,'F',TO_TIMESTAMP('2025-03-27 18:01:18.93','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',130,0,0,TO_TIMESTAMP('2025-03-27 18:01:18.93','YYYY-MM-DD HH24:MI:SS.US'),100,'S')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Rabatt %
-- Column: C_Invoice_Detail.Discount
-- 2025-03-27T16:01:34.451Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741546,0,547937,631170,552701,'F',TO_TIMESTAMP('2025-03-27 18:01:34.286','YYYY-MM-DD HH24:MI:SS.US'),100,'Abschlag in Prozent','"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','N','Y','N','N','N',0,'Rabatt %',140,0,0,TO_TIMESTAMP('2025-03-27 18:01:34.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Einzelpreis
-- Column: C_Invoice_Detail.PriceActual
-- 2025-03-27T16:01:53.078Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741545,0,547937,631171,552701,'F',TO_TIMESTAMP('2025-03-27 18:01:52.956','YYYY-MM-DD HH24:MI:SS.US'),100,'Effektiver Preis','Der Einzelpreis oder Effektive Preis bezeichnet den Preis für das Produkt in Ausgangswährung.','Y','N','N','Y','N','N','N',0,'Einzelpreis',150,0,0,TO_TIMESTAMP('2025-03-27 18:01:52.956','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Bestellte Menge in Preiseinheit
-- Column: C_Invoice_Detail.QtyEnteredInPriceUOM
-- 2025-03-27T16:02:31.583Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741542,0,547937,631172,552701,'F',TO_TIMESTAMP('2025-03-27 18:02:31.385','YYYY-MM-DD HH24:MI:SS.US'),100,'Bestellte Menge in Preiseinheit','Die "Bestellte Menge in Preiseinheit" bezeichnet die Menge einer Ware, die bestellt wurde, umgerechnet in die Mengeneinheit auf die sich der Preis bezieht.','Y','N','N','Y','N','N','N',0,'Bestellte Menge in Preiseinheit',160,0,0,TO_TIMESTAMP('2025-03-27 18:02:31.385','YYYY-MM-DD HH24:MI:SS.US'),100,'S')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Organisation
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2025-03-27T16:03:19.953Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741529,0,547937,631173,552701,'F',TO_TIMESTAMP('2025-03-27 18:03:19.789','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Organisation',170,0,0,TO_TIMESTAMP('2025-03-27 18:03:19.789','YYYY-MM-DD HH24:MI:SS.US'),100,'M')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Mandant
-- Column: C_Invoice_Detail.AD_Client_ID
-- 2025-03-27T16:03:36.095Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy,WidgetSize) VALUES (0,741528,0,547937,631174,552701,'F',TO_TIMESTAMP('2025-03-27 18:03:35.985','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',180,0,0,TO_TIMESTAMP('2025-03-27 18:03:35.985','YYYY-MM-DD HH24:MI:SS.US'),100,'M')
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Rechnung
-- Column: C_Invoice_Detail.C_Invoice_ID
-- 2025-03-27T16:04:48.216Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.216','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631156
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Rechnungsposition
-- Column: C_Invoice_Detail.C_InvoiceLine_ID
-- 2025-03-27T16:04:48.218Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.218','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631157
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Aktiv
-- Column: C_Invoice_Detail.IsActive
-- 2025-03-27T16:04:48.222Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631158
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.davor andrucken
-- Column: C_Invoice_Detail.IsPrintBefore
-- 2025-03-27T16:04:48.224Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.224','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631159
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Reihenfolge
-- Column: C_Invoice_Detail.SeqNo
-- 2025-03-27T16:04:48.227Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.227','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631160
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: C_Invoice_Detail.M_Product_ID
-- 2025-03-27T16:04:48.230Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.23','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631161
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: C_Invoice_Detail.Description
-- 2025-03-27T16:04:48.233Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.233','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631162
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Datum
-- Column: C_Invoice_Detail.Date
-- 2025-03-27T16:04:48.235Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.235','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631164
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Notiz
-- Column: C_Invoice_Detail.Note
-- 2025-03-27T16:04:48.238Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.238','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631163
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: C_Invoice_Detail.M_AttributeSetInstance_ID
-- 2025-03-27T16:04:48.240Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.24','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631165
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Menge
-- Column: C_Invoice_Detail.Qty
-- 2025-03-27T16:04:48.243Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.243','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631166
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Maßeinheit
-- Column: C_Invoice_Detail.C_UOM_ID
-- 2025-03-27T16:04:48.245Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.245','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631167
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Preis
-- Column: C_Invoice_Detail.PriceEntered
-- 2025-03-27T16:04:48.248Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.248','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631168
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Preiseinheit
-- Column: C_Invoice_Detail.Price_UOM_ID
-- 2025-03-27T16:04:48.250Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.25','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631169
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Rabatt %
-- Column: C_Invoice_Detail.Discount
-- 2025-03-27T16:04:48.252Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.252','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631170
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Einzelpreis
-- Column: C_Invoice_Detail.PriceActual
-- 2025-03-27T16:04:48.255Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.255','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631171
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Bestellte Menge in Preiseinheit
-- Column: C_Invoice_Detail.QtyEnteredInPriceUOM
-- 2025-03-27T16:04:48.258Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.258','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631172
;

-- UI Element: Rechnung(167,D) -> Invoice detailed informations(547937,de.metas.swat) -> main -> 10 -> default.Organisation
-- Column: C_Invoice_Detail.AD_Org_ID
-- 2025-03-27T16:04:48.260Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2025-03-27 18:04:48.26','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631173
;

-- Run mode: SWING_CLIENT

-- Tab: Rechnung(167,D) -> Invoice detailed informations
-- Table: C_Invoice_Detail
-- 2025-03-27T16:15:42.204Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2025-03-27 18:15:42.204','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547937
;

-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Detail.PP_Order_ID
-- 2025-03-27T18:15:24.637Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2025-03-27 20:15:24.636','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=552846
;

-- Run mode: SWING_CLIENT

-- Tab: Rechnung_OLD(167,D) -> Detailzeilen
-- Table: C_Invoice_Detail
-- 2025-03-27T20:12:19.546Z
UPDATE AD_Tab SET AD_Element_ID=573726, CommitWarning=NULL, Description=NULL, Help=NULL, Name='Detailzeilen',Updated=TO_TIMESTAMP('2025-03-27 22:12:19.546','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547937
;

-- 2025-03-27T20:12:19.557Z
UPDATE AD_Tab_Trl trl SET Name='Detailzeilen' WHERE AD_Tab_ID=547937 AND AD_Language='de_DE'
;

-- 2025-03-27T20:12:19.591Z
/* DDL */  select update_tab_translation_from_ad_element(573726)
;

-- 2025-03-27T20:12:19.604Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547937)
;

-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Detail.C_Invoice_Candidate_ID
-- 2025-03-28T09:45:00.345Z
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2025-03-28 11:45:00.344','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=551279
;

-- Run mode: SWING_CLIENT

-- UI Element: Eingangsrechnung(183,D) -> Detailzeilen(540621,de.metas.swat) -> main -> 10 -> default.Rechnungskandidat
-- Column: C_Invoice_Detail.C_Invoice_Candidate_ID
-- 2025-04-01T08:59:56.977Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,614934,0,540621,631293,540221,'F',TO_TIMESTAMP('2025-04-01 11:59:56.836','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungskandidat',165,0,0,TO_TIMESTAMP('2025-04-01 11:59:56.836','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_Invoice_Detail.PP_Order_ID
-- 2025-04-01T09:03:07.042Z
UPDATE AD_Column SET IsCalculated='Y',Updated=TO_TIMESTAMP('2025-04-01 12:03:07.042','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=552846
;

-- Run mode: SWING_CLIENT

-- UI Element: Rechnung_OLD(167,D) -> Detailzeilen(547937,de.metas.swat) -> main -> 10 -> default.Rechnungskandidat
-- Column: C_Invoice_Detail.C_Invoice_Candidate_ID
-- 2025-04-01T09:05:52.946Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,741549,0,547937,631294,552701,'F',TO_TIMESTAMP('2025-04-01 12:05:52.78','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N',0,'Rechnungskandidat',165,0,0,TO_TIMESTAMP('2025-04-01 12:05:52.78','YYYY-MM-DD HH24:MI:SS.US'),100)
;
