-- Run mode: SWING_CLIENT

-- Column: C_CostClassification.Name
-- 2026-07-20T10:32:42.018Z
UPDATE AD_Column SET SeqNo=2,Updated=TO_TIMESTAMP('2026-07-20 10:32:42.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591795
;

-- Column: C_CostClassification.Value
-- 2026-07-20T10:33:32.005Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2026-07-20 10:33:32.005000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591794
;

-- Column: C_CostClassification.Description
-- 2026-07-20T10:35:21.281Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=3,Updated=TO_TIMESTAMP('2026-07-20 10:35:21.281000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591796
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 10 -> default.Suchschlüssel
-- Column: C_CostClassification.Value
-- 2026-07-20T10:39:40.629Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-07-20 10:39:40.628000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641279
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 10 -> default.Kostenartengruppe
-- Column: C_CostClassification.C_CostClassification_Category_ID
-- 2026-07-20T10:40:07.922Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-07-20 10:40:07.922000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641281
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 20 -> org.Sektion
-- Column: C_CostClassification.AD_Org_ID
-- 2026-07-20T10:40:48.445Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-07-20 10:40:48.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641284
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 10 -> description.Beschreibung
-- Column: C_CostClassification.Description
-- 2026-07-20T10:41:08.314Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-07-20 10:41:08.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641282
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 20 -> flags.Aktiv
-- Column: C_CostClassification.IsActive
-- 2026-07-20T10:41:08.751Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-07-20 10:41:08.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641283
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 10 -> default.Kostenartengruppe
-- Column: C_CostClassification.C_CostClassification_Category_ID
-- 2026-07-20T10:41:09.194Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-07-20 10:41:09.194000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641281
;

-- UI Element: Kostenklassifizierung(542000,D) -> Kostenklassifizierung(548693,D) -> main -> 20 -> org.Sektion
-- Column: C_CostClassification.AD_Org_ID
-- 2026-07-20T10:41:09.626Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-07-20 10:41:09.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=641284
;

