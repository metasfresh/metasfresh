-- Add the Verpackungsmaterial (packaging material) child tab to the Product window
-- for the M_Product_PackagingMaterial table created in migration 5809760.
-- IDs allocated from idserver.metas.de on 2026-06-26:
--   AD_Tab             549326  (Verpackungsmaterial tab on Product window 140)
--   AD_Field           781254  (PackagingMaterialType field)
--   AD_Field           781255  (WeightInGram field)
--   AD_UI_Section      547832  (main section)
--   AD_UI_Column       549571  (single column)
--   AD_UI_ElementGroup 555469  (main group)
--   AD_UI_Element      652374  (PackagingMaterialType UI element)
--   AD_UI_Element      652375  (WeightInGram UI element)
-- Pre-existing (migration 5809760):
--   AD_Table 542621, AD_Element 585052 (table label),
--   M_Product_ID FK column 592892 (IsParent='Y'),
--   PackagingMaterialType column 592893 (element 585053),
--   WeightInGram column 592894 (element 585054)

-- Run mode: SWING_CLIENT

-- Point the child table at the Product window (AD_Window_ID=140)
-- 2026-06-26 16:00:00
UPDATE AD_Table SET AD_Window_ID=140,Updated=TO_TIMESTAMP('2026-06-26 16:00:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542621
;

-- AD_Tab: Verpackungsmaterial (child of Product window 140, TabLevel 1)
-- Child->parent link via AD_Column_ID = M_Product_PackagingMaterial.M_Product_ID (592892, the FK in the child table).
-- AD_Column_ID is the primary link-column mechanism (GridTabVO.buildLinkColumnNames: if AD_Column_ID is set
-- it is used directly as the child link column). Verified working on 3todev; the Parent_Column_ID-only variant
-- did not bind the tab there.
-- 2026-06-26 16:00:10
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,AD_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,585052,0,549326 /*From ID Server*/,542621,140,'Y',TO_TIMESTAMP('2026-06-26 16:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Verpackungsmaterialien des Produkts.','D','N','Verpackungsmaterialien des Produkts (Materialtyp und Gewicht).','N','A','M_Product_PackagingMaterial','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Verpackungsmaterial',592892,'N',183,1,TO_TIMESTAMP('2026-06-26 16:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 16:00:11
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549326
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-06-26 16:00:12
/* DDL */ select update_tab_translation_from_ad_element(585052)
;

-- 2026-06-26 16:00:13
/* DDL */ select AD_Element_Link_Create_Missing_Tab(549326)
;

-- AD_Field: PackagingMaterialType (label/translation propagated from column element 585053)
-- 2026-06-26 16:01:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592893,781254 /*From ID Server*/,0,549326,TO_TIMESTAMP('2026-06-26 16:01:00','YYYY-MM-DD HH24:MI:SS'),100,'Verpackungsmaterial-Typ',10,'D','Y','N','N','N','N','N','N','N','Verpackungsmaterial-Typ',TO_TIMESTAMP('2026-06-26 16:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 16:01:01
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781254
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-26 16:01:02
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585053)
;

-- 2026-06-26 16:01:03
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781254
;

-- 2026-06-26 16:01:04
/* DDL */ select AD_Element_Link_Create_Missing_Field(781254)
;

-- AD_Field: WeightInGram (label/translation propagated from column element 585054)
-- 2026-06-26 16:01:10
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592894,781255 /*From ID Server*/,0,549326,TO_TIMESTAMP('2026-06-26 16:01:10','YYYY-MM-DD HH24:MI:SS'),100,'Gewicht (g)',10,'D','Y','N','N','N','N','N','N','N','Gewicht (g)',TO_TIMESTAMP('2026-06-26 16:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2026-06-26 16:01:11
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781255
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-26 16:01:12
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585054)
;

-- 2026-06-26 16:01:13
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781255
;

-- 2026-06-26 16:01:14
/* DDL */ select AD_Element_Link_Create_Missing_Field(781255)
;

-- AD_UI_Section: main
-- 2026-06-26 16:02:00
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549326,547832 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 16:02:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2026-06-26 16:02:00','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2026-06-26 16:02:01
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547832
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- AD_UI_Column
-- 2026-06-26 16:02:10
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549571 /*From ID Server*/,547832,TO_TIMESTAMP('2026-06-26 16:02:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-06-26 16:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_UI_ElementGroup
-- 2026-06-26 16:02:20
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549571,555469 /*From ID Server*/,TO_TIMESTAMP('2026-06-26 16:02:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2026-06-26 16:02:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_UI_Element: PackagingMaterialType (shown in single-row and grid)
-- 2026-06-26 16:03:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781254,0,549326,555469,652374 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-26 16:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Verpackungsmaterial-Typ','Y','N','N','Y','Y','N','N',0,'Verpackungsmaterial-Typ',10,10,0,TO_TIMESTAMP('2026-06-26 16:03:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- AD_UI_Element: WeightInGram (shown in single-row and grid)
-- 2026-06-26 16:03:10
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,781255,0,549326,555469,652375 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-26 16:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Gewicht (g)','Y','N','N','Y','Y','N','N',0,'Gewicht (g)',20,20,0,TO_TIMESTAMP('2026-06-26 16:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;





-- Run mode: SWING_CLIENT

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Verpackungsmaterial
-- Column: M_Product_PackagingMaterial.M_Product_PackagingMaterial_ID
-- 2026-06-29T10:42:52.172Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592884,781308,0,549326,TO_TIMESTAMP('2026-06-29 10:42:51.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Verpackungsmaterial',TO_TIMESTAMP('2026-06-29 10:42:51.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:52.212Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781308 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:52.275Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585052)
;

-- 2026-06-29T10:42:52.361Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781308
;

-- 2026-06-29T10:42:52.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781308)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Mandant
-- Column: M_Product_PackagingMaterial.AD_Client_ID
-- 2026-06-29T10:42:52.855Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592885,781309,0,549326,TO_TIMESTAMP('2026-06-29 10:42:52.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-06-29 10:42:52.489000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:52.895Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781309 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:52.935Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-06-29T10:42:53.067Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781309
;

-- 2026-06-29T10:42:53.106Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781309)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Sektion
-- Column: M_Product_PackagingMaterial.AD_Org_ID
-- 2026-06-29T10:42:53.541Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592886,781310,0,549326,TO_TIMESTAMP('2026-06-29 10:42:53.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-06-29 10:42:53.186000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:53.582Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781310 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:53.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-06-29T10:42:53.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781310
;

-- 2026-06-29T10:42:53.797Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781310)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Aktiv
-- Column: M_Product_PackagingMaterial.IsActive
-- 2026-06-29T10:42:54.237Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592887,781311,0,549326,TO_TIMESTAMP('2026-06-29 10:42:53.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-06-29 10:42:53.878000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:54.278Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781311 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:54.317Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-06-29T10:42:54.445Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781311
;

-- 2026-06-29T10:42:54.484Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781311)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Erstellt
-- Column: M_Product_PackagingMaterial.Created
-- 2026-06-29T10:42:54.919Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592888,781312,0,549326,TO_TIMESTAMP('2026-06-29 10:42:54.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',29,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','Y','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2026-06-29 10:42:54.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:54.960Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781312 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:55.001Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2026-06-29T10:42:55.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781312
;

-- 2026-06-29T10:42:55.148Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781312)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Erstellt durch
-- Column: M_Product_PackagingMaterial.CreatedBy
-- 2026-06-29T10:42:55.596Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592889,781313,0,549326,TO_TIMESTAMP('2026-06-29 10:42:55.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','Y','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2026-06-29 10:42:55.228000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:55.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781313 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:55.678Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2026-06-29T10:42:55.764Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781313
;

-- 2026-06-29T10:42:55.802Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781313)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Aktualisiert
-- Column: M_Product_PackagingMaterial.Updated
-- 2026-06-29T10:42:56.247Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592890,781314,0,549326,TO_TIMESTAMP('2026-06-29 10:42:55.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',29,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','Y','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2026-06-29 10:42:55.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:56.287Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781314 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:56.327Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2026-06-29T10:42:56.388Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781314
;

-- 2026-06-29T10:42:56.426Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781314)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Aktualisiert durch
-- Column: M_Product_PackagingMaterial.UpdatedBy
-- 2026-06-29T10:42:56.873Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592891,781315,0,549326,TO_TIMESTAMP('2026-06-29 10:42:56.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','Y','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2026-06-29 10:42:56.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:56.914Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781315 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:56.955Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2026-06-29T10:42:57.043Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781315
;

-- 2026-06-29T10:42:57.082Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781315)
;

-- Field: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> Produkt
-- Column: M_Product_PackagingMaterial.M_Product_ID
-- 2026-06-29T10:42:57.523Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592892,781316,0,549326,TO_TIMESTAMP('2026-06-29 10:42:57.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','Produkt',TO_TIMESTAMP('2026-06-29 10:42:57.162000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-06-29T10:42:57.563Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781316 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-06-29T10:42:57.604Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-06-29T10:42:57.696Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781316
;

-- 2026-06-29T10:42:57.735Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781316)
;

-- UI Section: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main
-- UI Column: 20
-- 2026-06-29T10:43:18.227Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549578,547832,TO_TIMESTAMP('2026-06-29 10:43:17.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-06-29 10:43:17.934000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20
-- UI Element Group: flag
-- 2026-06-29T10:43:29.503Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549578,555476,TO_TIMESTAMP('2026-06-29 10:43:29.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flag',10,TO_TIMESTAMP('2026-06-29 10:43:29.189000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20
-- UI Element Group: org
-- 2026-06-29T10:43:36.269Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549578,555477,TO_TIMESTAMP('2026-06-29 10:43:36.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-06-29 10:43:36.013000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20 -> flag.Aktiv
-- Column: M_Product_PackagingMaterial.IsActive
-- 2026-06-29T10:44:24.895Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781311,0,549326,555476,652421,'F',TO_TIMESTAMP('2026-06-29 10:44:24.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',10,0,0,TO_TIMESTAMP('2026-06-29 10:44:24.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20 -> org.Sektion
-- Column: M_Product_PackagingMaterial.AD_Org_ID
-- 2026-06-29T10:44:51.940Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781310,0,549326,555477,652422,'F',TO_TIMESTAMP('2026-06-29 10:44:51.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',10,0,0,TO_TIMESTAMP('2026-06-29 10:44:51.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20 -> org.Mandant
-- Column: M_Product_PackagingMaterial.AD_Client_ID
-- 2026-06-29T10:45:21.139Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781309,0,549326,555477,652423,'F',TO_TIMESTAMP('2026-06-29 10:45:20.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',20,0,0,TO_TIMESTAMP('2026-06-29 10:45:20.591000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20 -> flag.Aktiv
-- Column: M_Product_PackagingMaterial.IsActive
-- 2026-06-29T10:45:36.641Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-06-29 10:45:36.640000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652421
;

-- UI Element: Produkt(140,D) -> Verpackungsmaterial(549326,D) -> main -> 20 -> org.Sektion
-- Column: M_Product_PackagingMaterial.AD_Org_ID
-- 2026-06-29T10:45:36.880Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-06-29 10:45:36.879000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652422
;

