-- Run mode: SWING_CLIENT

-- Reference Item: C_Order InvoiceRule -> M_Manual
-- 2026-05-14T09:57:27.849Z
UPDATE AD_Ref_List_Trl SET Name='Manuell',Updated=TO_TIMESTAMP('2026-05-14 09:57:27.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=544228
;

-- 2026-05-14T09:57:27.854Z
UPDATE AD_Ref_List base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Ref_List_Trl trl  WHERE trl.AD_Ref_List_ID=base.AD_Ref_List_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- Reference: C_Order InvoiceRule
-- Value: M
-- ValueName: Manual
-- 2026-05-14T09:57:34.234Z
UPDATE AD_Ref_List SET Name='Manuell',Updated=TO_TIMESTAMP('2026-05-14 09:57:34.234000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Ref_List_ID=544228
;

-- 2026-05-14T09:57:34.236Z
UPDATE AD_Ref_List_Trl trl SET Name='Manuell' WHERE AD_Ref_List_ID=544228 AND AD_Language='de_DE'
;

