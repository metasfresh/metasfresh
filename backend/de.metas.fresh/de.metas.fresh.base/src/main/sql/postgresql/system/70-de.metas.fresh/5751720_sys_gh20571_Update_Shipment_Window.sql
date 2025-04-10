-- Field: Lieferung_OLD -> Lieferposition -> Auftragsposition
-- Column: M_InOutLine.C_OrderLine_ID
-- Field: Lieferung_OLD(169,D) -> Lieferposition(258,D) -> Auftragsposition
-- Column: M_InOutLine.C_OrderLine_ID
-- 2025-04-10T08:34:19.793Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-04-10 08:34:19.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=2947
;

-- Column: M_InOutLine.C_OrderLine_ID
-- Column: M_InOutLine.C_OrderLine_ID
-- 2025-04-10T09:39:34.294Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2025-04-10 09:39:34.294000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=3811
;
