-- C_Fiscal_Representation had no identifier column, so lookups (e.g. C_Invoice.C_Fiscal_Representation_ID) showed the raw id.
-- Identifier: registration country + VAT ID.

-- Column: C_Fiscal_Representation.To_Country_ID
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=10,Updated=TO_TIMESTAMP('2026-11-27 13:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=573812
;

-- Column: C_Fiscal_Representation.VATaxID
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=20,Updated=TO_TIMESTAMP('2026-11-27 13:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=573813
;
