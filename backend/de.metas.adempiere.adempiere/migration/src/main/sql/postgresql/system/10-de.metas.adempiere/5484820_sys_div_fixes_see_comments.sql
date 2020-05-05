--
-- make AD_Printer_Config.HostKey editable, because otherwise I can't set a customer one (e.g. for webui printing)
--
-- 2018-02-07T14:25:13.896
-- URL zum Konzept
UPDATE AD_Column SET IsUpdateable='Y',Updated=TO_TIMESTAMP('2018-02-07 14:25:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=551593
;

--
-- don't add "inserted" records to AD_ChangeLog; only "updated"
--
-- 2018-02-07T15:15:52.262
-- URL zum Konzept
UPDATE AD_SysConfig SET Value='N',Updated=TO_TIMESTAMP('2018-02-07 15:15:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=50015
;
-- cleanup
--
delete from AD_ChangeLog WHERE EventChangeLog='I';

--
-- don't log AD_EventLog.EventData, because the column is larger than the respective AD_changeLog column
--
-- 2018-02-07T15:17:30.547
-- URL zum Konzept
UPDATE AD_Column SET IsAllowLogging='N',Updated=TO_TIMESTAMP('2018-02-07 15:17:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=558410
;

--
-- AD_Issue.LoggerName
--
-- 2018-02-07T16:15:46.474
-- URL zum Konzept
UPDATE AD_Column SET FieldLength=512,Updated=TO_TIMESTAMP('2018-02-07 16:15:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=14862
;

-- 2018-02-07T16:15:50.585
-- URL zum Konzept
INSERT INTO t_alter_column values('ad_issue','LoggerName','VARCHAR(512)',null,null)
;

