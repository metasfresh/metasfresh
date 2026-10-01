-- Invoice candidate project: read-only in both invoice candidate windows.
-- Every recompute of an unprocessed order line candidate copies the project from the order line,
-- so a manual edit on the candidate would be overwritten without notice.

-- Field: Rechnungsdisposition(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
UPDATE AD_Field SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2026-10-01 23:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=758717
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Projekt
-- Column: C_Invoice_Candidate.C_Project_ID
UPDATE AD_Field SET IsReadOnly='Y', Updated=TO_TIMESTAMP('2026-10-01 23:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=758718
;
