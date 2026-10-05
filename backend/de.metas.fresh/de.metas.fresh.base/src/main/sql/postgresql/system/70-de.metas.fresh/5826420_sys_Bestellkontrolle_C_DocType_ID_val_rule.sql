-- Bestellkontrolle -- restrict the C_DocType_ID lookup to the two order-checkup document base types.
-- C_Order_MFGWarehouse_Report.C_DocType_ID is read-only in the window, but it is now also a filter, and the
-- filter's lookup offered every document type of the system. The WebUI filter takes its validation rule from
-- the field's AD_Val_Rule_ID, i.e. COALESCE(AD_Field, AD_Column), so the rule goes on the column.
-- The rule has no context variables on purpose: the filter lookup drops rules that depend on any.

-- Name: C_DocType_ID_OrderCheckup
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,Description,EntityType,IsActive,Name,Type,Updated,UpdatedBy)
VALUES (0,0,540802 /*From ID Server*/,'C_DocType.DocBaseType IN (''BKP'', ''BKB'')',TO_TIMESTAMP('2026-09-25 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegarten der Bestellkontrolle (Produktion und Büro)','de.metas.fresh','Y','C_DocType_ID_OrderCheckup','S',TO_TIMESTAMP('2026-09-25 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order_MFGWarehouse_Report.C_DocType_ID
UPDATE AD_Column SET AD_Val_Rule_ID=540802, Updated=TO_TIMESTAMP('2026-09-25 11:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Column_ID=593637
;
