
--
-- making LastExport and LastExportBy read-only, since they are set by the sepa-export-process
--
-- 07.12.2015 07:56
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2015-12-07 07:56:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=543271
;

-- 07.12.2015 07:56
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='Y', IsSameLine='Y',Updated=TO_TIMESTAMP('2015-12-07 07:56:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=543272
;

