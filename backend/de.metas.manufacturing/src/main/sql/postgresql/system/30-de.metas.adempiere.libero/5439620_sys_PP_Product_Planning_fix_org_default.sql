--
-- give the old default (0), no user without client-access could create/edit records
--

-- 12.02.2016 14:16
-- URL zum Konzept
UPDATE AD_Column SET DefaultValue='@AD_Org_ID/0@',Updated=TO_TIMESTAMP('2016-02-12 14:16:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=53376
;

