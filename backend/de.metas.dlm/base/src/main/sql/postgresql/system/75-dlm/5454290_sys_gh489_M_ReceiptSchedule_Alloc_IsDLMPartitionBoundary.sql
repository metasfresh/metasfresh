--
-- HU related columns of M_ReceiptSchedule_Alloc.
-- Background: some HUs are first received and then sent, they are referenced by both SO and PO documents
--
-- 12.12.2016 11:24
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-12 11:24:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549838
;

-- 12.12.2016 11:24
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-12 11:24:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549837
;

-- 12.12.2016 11:25
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-12 11:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=549839
;

-- 12.12.2016 11:25
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-12 11:25:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=550841
;

-- 12.12.2016 11:25
-- URL zum Konzept
UPDATE AD_Column SET IsDLMPartitionBoundary='Y',Updated=TO_TIMESTAMP('2016-12-12 11:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551598
;

COMMIT;

select dlm.recreate_dlm_triggers('M_ReceiptSchedule_Alloc');

