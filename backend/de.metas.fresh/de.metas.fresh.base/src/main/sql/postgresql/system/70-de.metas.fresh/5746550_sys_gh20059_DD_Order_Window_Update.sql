-- Run mode: SWING_CLIENT

-- Column: DD_Order.M_Warehouse_From_ID
-- 2025-02-13T10:08:41.648Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-02-13 10:08:41.647000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570819
;

-- Column: DD_Order.M_Warehouse_To_ID
-- 2025-02-13T10:09:11.726Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2025-02-13 10:09:11.726000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570820
;

-- Column: DD_Order.M_Warehouse_From_ID
-- 2025-02-13T10:09:46.783Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2025-02-13 10:09:46.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570819
;

-- Column: DD_Order.M_Warehouse_To_ID
-- 2025-02-13T10:09:54.833Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2025-02-13 10:09:54.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=570820
;

-- Column: DD_Order.POReference
-- 2025-02-13T10:10:03.240Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2025-02-13 10:10:03.240000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53908
;

-- Column: DD_Order.AD_Org_ID
-- 2025-02-13T10:10:11.524Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=80,Updated=TO_TIMESTAMP('2025-02-13 10:10:11.524000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=53866
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 10 -> default.Transit Lager
-- Column: DD_Order.M_Warehouse_ID
-- 2025-02-13T10:17:01.079Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-02-13 10:17:01.079000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544292
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 10 -> default.Warehouse from
-- Column: DD_Order.M_Warehouse_From_ID
-- 2025-02-13T10:17:01.683Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-02-13 10:17:01.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=569848
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 10 -> default.Warehouse to
-- Column: DD_Order.M_Warehouse_To_ID
-- 2025-02-13T10:17:02.036Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-02-13 10:17:02.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=569849
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> docno.Zugesagter Termin
-- Column: DD_Order.DatePromised
-- 2025-02-13T10:17:02.383Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2025-02-13 10:17:02.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544300
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 10 -> dimensions.Lieferweg
-- Column: DD_Order.M_Shipper_ID
-- 2025-02-13T10:17:02.732Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2025-02-13 10:17:02.732000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544296
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> referenz.In Transit
-- Column: DD_Order.IsInTransit
-- 2025-02-13T10:17:03.084Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2025-02-13 10:17:03.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544302
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 10 -> default.Kostenstelle
-- Column: DD_Order.C_Activity_ID
-- 2025-02-13T10:17:03.441Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2025-02-13 10:17:03.441000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544293
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 10 -> dimensions.Streckengeschäft
-- Column: DD_Order.IsDropShip
-- 2025-02-13T10:17:03.795Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2025-02-13 10:17:03.795000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544301
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> referenz.Freigegeben
-- Column: DD_Order.IsApproved
-- 2025-02-13T10:17:04.146Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2025-02-13 10:17:04.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544303
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> referenz.Belegstatus
-- Column: DD_Order.DocStatus
-- 2025-02-13T10:17:04.498Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2025-02-13 10:17:04.498000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544262
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> posted.Posted
-- Column: DD_Order.Posted
-- 2025-02-13T10:17:04.847Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-02-13 10:17:04.847000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564740
;

-- UI Element: Distributionsauftrag_OLD(53012,EE01) -> Distributionsauftrag(53055,EE01) -> main -> 20 -> org.Sektion
-- Column: DD_Order.AD_Org_ID
-- 2025-02-13T10:17:05.195Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-02-13 10:17:05.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=544304
;

