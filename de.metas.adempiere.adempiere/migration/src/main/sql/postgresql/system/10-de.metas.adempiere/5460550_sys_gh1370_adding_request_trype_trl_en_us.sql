-- 2017-04-23T13:47:44
-- URL zum Konzept
-- INSERT INTO R_RequestType_Trl (CreatedBy,UpdatedBy,Updated,Created,AD_Client_ID,AD_Language,AD_Org_ID,IsActive,IsTranslated,Name,R_RequestType_ID) VALUES ( 100,100,TO_TIMESTAMP('2017-04-23 13:47:43','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-04-23 13:47:43','YYYY-MM-DD HH24:MI:SS'),0,'en_US',0,'Y','Y','1',540007)
-- ;

-- 2017-04-23T13:47:55.250
-- URL zum Konzept
-- INSERT INTO R_RequestType_Trl (CreatedBy,UpdatedBy,Updated,Created,AD_Client_ID,AD_Language,AD_Org_ID,IsActive,IsTranslated,Name,R_RequestType_ID) VALUES ( 100,100,TO_TIMESTAMP('2017-04-23 13:47:55','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-04-23 13:47:55','YYYY-MM-DD HH24:MI:SS'),0,'en_US',0,'Y','Y','2',540005)
-- ;

-- 2017-04-23T13:51:57.406
-- URL zum Konzept
-- INSERT INTO R_RequestType_Trl (CreatedBy,UpdatedBy,Updated,Created,AD_Client_ID,AD_Language,AD_Org_ID,IsActive,IsTranslated,Name,R_RequestType_ID) VALUES ( 100,100,TO_TIMESTAMP('2017-04-23 13:51:57','YYYY-MM-DD HH24:MI:SS'),TO_TIMESTAMP('2017-04-23 13:51:57','YYYY-MM-DD HH24:MI:SS'),0,'en_US',0,'Y','Y','2',540005)
-- ;

-- 2017-04-23T14:00:07.523
-- URL zum Konzept
UPDATE AD_Column SET IsTranslated='Y',Updated=TO_TIMESTAMP('2017-04-23 14:00:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556499
;

-- 2017-04-23T20:35:41.202
-- URL zum Konzept
UPDATE R_RequestType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-23 20:35:41','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='1' WHERE AD_Language='en_US' AND R_RequestType_ID=540007
;

-- 2017-04-23T20:37:38.362
-- URL zum Konzept
UPDATE R_RequestType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-23 20:37:38','YYYY-MM-DD HH24:MI:SS'),Name='Consumer Complaint' WHERE AD_Language='en_US' AND R_RequestType_ID=540007
;

-- 2017-04-23T20:37:59.921
-- URL zum Konzept
UPDATE R_RequestType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-23 20:37:59','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Customer Complaint' WHERE AD_Language='en_US' AND R_RequestType_ID=540005
;

-- 2017-04-23T20:38:10.244
-- URL zum Konzept
UPDATE R_RequestType_Trl SET UpdatedBy=100,Updated=TO_TIMESTAMP('2017-04-23 20:38:10','YYYY-MM-DD HH24:MI:SS'),IsTranslated='Y',Name='Vendor Complaint' WHERE AD_Language='en_US' AND R_RequestType_ID=540006
;

