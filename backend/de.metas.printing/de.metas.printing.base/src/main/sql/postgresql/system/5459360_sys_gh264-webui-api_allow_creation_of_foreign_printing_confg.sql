
--
-- make AD_Printer_Config.HostKey editable because we need to be able to create a record for a webui user
--
-- 2017-03-31T22:20:25.594
-- URL zum Konzept
UPDATE AD_Field SET IsReadOnly='N',Updated=TO_TIMESTAMP('2017-03-31 22:20:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=555239
;

-- minor: also have a changelog for AD_Printer_Config
-- 2017-03-31T22:20:35.553
-- URL zum Konzept
UPDATE AD_Table SET IsChangeLog='Y',Updated=TO_TIMESTAMP('2017-03-31 22:20:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540637
;

