-- Overview tab (549338): replace the two text fields ProductValue (781407) and ProductName (781408)
-- with a single M_Product_ID lookup field so the grid shows the product via its standard lookup.
--
-- IDs allocated from idserver.metas.de on 2026-07-02:
--   AD_Field       781321  (M_Product_ID in Overview tab)
--   AD_UI_Element  652430  (M_Product_ID in Overview tab)

-- ============================================================
-- 1. Remove ProductValue and ProductName fields from Overview tab
-- ============================================================
-- Two rollout paths converge here:
--   (a) fresh install — 5811430 already created rows with the fresh ID-server
--       allocations (AD_UI_Element 652523/652524, AD_Field 781407/781408).
--   (b) already-applied instance — the same rows still carry the earlier
--       hand-picked IDs (AD_UI_Element 581186/581187, AD_Field 581172/581173)
--       until the companion renumber migration (5813770) has run on that
--       instance, which happens AFTER this script by filename prefix.
-- So the IN-list carries BOTH id sets and each DELETE hits the correct rows
-- in whichever path we're on. Unused values are harmless no-ops.
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID IN (652523/*ProductValue new*/, 652524/*ProductName new*/, 581186/*ProductValue old*/, 581187/*ProductName old*/);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (781407/*ProductValue new*/, 781408/*ProductName new*/, 581172/*ProductValue old*/, 581173/*ProductName old*/);
DELETE FROM AD_Field_Trl     WHERE AD_Field_ID IN (781407/*ProductValue new*/, 781408/*ProductName new*/, 581172/*ProductValue old*/, 581173/*ProductName old*/);
DELETE FROM AD_Field          WHERE AD_Field_ID IN (781407/*ProductValue new*/, 781408/*ProductName new*/, 581172/*ProductValue old*/, 581173/*ProductName old*/);

-- ============================================================
-- 2. Add M_Product_ID lookup field (replaces both removed fields, SeqNo=10)
-- ============================================================
INSERT INTO AD_Field
    (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
     Name, AD_Tab_ID, AD_Column_ID, IsDisplayed, SeqNo, IsDisplayedGrid, SeqNoGrid,
     IsSameLine, IsHeading, IsFieldOnly, IsEncrypted, EntityType)
VALUES (781321/*From ID Server*/, 0, 0, 'Y',
    TO_TIMESTAMP('2026-07-02 23:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    TO_TIMESTAMP('2026-07-02 23:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Artikel', 549338, 592941/*M_Product_ID in M_Picking_OrderBoard_Overview_v*/, 'Y', 10, 'Y', 10,
    'N', 'N', 'N', 'N', 'D')
;

INSERT INTO AD_Field_Trl
    (AD_Language, AD_Field_ID, IsTranslated, Name, Description, Help,
     AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 781321, 'N', f.Name, f.Description, f.Help,
       0, 0, 'Y',
       TO_TIMESTAMP('2026-07-02 23:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
       TO_TIMESTAMP('2026-07-02 23:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100
FROM   AD_Language l, AD_Field f
WHERE  l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND f.AD_Field_ID = 781321
  AND  NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = 781321)
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(454/*M_Product_ID*/);
DELETE FROM AD_Element_Link WHERE AD_Field_ID = 781321;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(781321);

-- ============================================================
-- 3. Add AD_UI_Element for M_Product_ID
-- ============================================================
-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Produkt
-- Column: M_Picking_OrderBoard_Overview_v.M_Product_ID
-- 2026-07-02T20:46:37.525Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781321,0,549338,555492,652430,'F',TO_TIMESTAMP('2026-07-02 20:46:37.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2026-07-02 20:46:37.021000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Produkt
-- Column: M_Picking_OrderBoard_Overview_v.M_Product_ID
-- 2026-07-02T20:47:38.820Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-07-02 20:47:38.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652430
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Land
-- Column: M_Picking_OrderBoard_Overview_v.C_Country_ID
-- 2026-07-02T20:47:39.664Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-07-02 20:47:39.664000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652528
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Lieferdatum
-- Column: M_Picking_OrderBoard_Overview_v.DeliveryDate
-- 2026-07-02T20:47:40.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-07-02 20:47:40.158000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652526
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Menge wartend
-- Column: M_Picking_OrderBoard_Overview_v.QtyWaiting
-- 2026-07-02T20:47:40.659Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-07-02 20:47:40.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652529
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Menge in Kommissionierung
-- Column: M_Picking_OrderBoard_Overview_v.QtyPicking
-- 2026-07-02T20:47:41.160Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-07-02 20:47:41.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652530
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Menge packen
-- Column: M_Picking_OrderBoard_Overview_v.QtyPacking
-- 2026-07-02T20:47:41.657Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-07-02 20:47:41.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652531
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Menge gesamt
-- Column: M_Picking_OrderBoard_Overview_v.QtyTotal
-- 2026-07-02T20:47:42.150Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-07-02 20:47:42.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652532
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Maßeinheit
-- Column: M_Picking_OrderBoard_Overview_v.C_UOM_ID
-- 2026-07-02T20:47:42.651Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-07-02 20:47:42.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652525
;

-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Auftragszeilen
-- Column: M_Picking_OrderBoard_Overview_v.OrderLineCount
-- 2026-07-02T20:47:43.150Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-07-02 20:47:43.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652533
;

-- Tab: Auftrags-Board(542168,D) -> Wartend
-- Table: M_Picking_OrderBoard_v
-- 2026-07-03T05:16:39.145Z
UPDATE AD_Tab SET AD_Column_ID=592960,Updated=TO_TIMESTAMP('2026-07-03 05:16:39.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=549335
;

-- Tab: Auftrags-Board(542168,D) -> In Kommissionierung
-- Table: M_Picking_OrderBoard_v
-- 2026-07-03T05:17:14.136Z
UPDATE AD_Tab SET AD_Column_ID=592960,Updated=TO_TIMESTAMP('2026-07-03 05:17:14.136000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=549336
;

-- Tab: Auftrags-Board(542168,D) -> Packen
-- Table: M_Picking_OrderBoard_v
-- 2026-07-03T05:17:23.418Z
UPDATE AD_Tab SET AD_Column_ID=592960,Updated=TO_TIMESTAMP('2026-07-03 05:17:23.418000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=549337
;


-- UI Element: Auftrags-Board(542168,D) -> Übersicht(549338,D) -> main -> 10 -> default.Landname
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-07-03 10:14:35.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=652527
;

