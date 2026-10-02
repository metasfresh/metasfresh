-- Invoice candidate project: read-only in both invoice candidate windows.
-- Every recompute of an unprocessed order line candidate copies the project from the order line,
-- so a manual edit on the candidate would be overwritten without notice.

-- Field 758717: window 540092 (sales invoice candidates) -> tab 540279 -> project field
-- Column: C_Invoice_Candidate.C_Project_ID
UPDATE AD_Field SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2026-10-01 23:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=758717
;

-- Field 758718: window 540983 (purchase invoice candidates) -> tab 543052 -> project field
-- Column: C_Invoice_Candidate.C_Project_ID
UPDATE AD_Field SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2026-10-01 23:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=758718
;
