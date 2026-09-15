-- Run mode: SWING_CLIENT

-- Field: Verträge(540359,de.metas.contracts) -> Liefervereinbarung(540862,de.metas.procurement) -> Geplante Menge
-- Column: C_Flatrate_DataEntry.Qty_Planned
-- 2026-03-02T14:36:55.319Z
UPDATE AD_Field SET AD_Name_ID=1568, Description='', Help='', Name='Geplante Menge',Updated=TO_TIMESTAMP('2026-03-02 14:36:55.319000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=559723
;

-- 2026-03-02T14:36:55.502Z
UPDATE AD_Field_Trl trl SET Description='',Help='',Name='Geplante Menge' WHERE AD_Field_ID=559723 AND AD_Language='de_CH'
;

-- 2026-03-02T14:36:55.582Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1568)
;

-- 2026-03-02T14:36:55.647Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=559723
;

-- 2026-03-02T14:36:55.704Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(559723)
;

