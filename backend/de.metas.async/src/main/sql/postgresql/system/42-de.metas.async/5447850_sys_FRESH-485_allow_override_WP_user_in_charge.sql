
--
-- actually, the whole fix is to set ConfigurationLevel='O'
--

-- Jul 5, 2016 2:44 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='O', Description='AD_User_ID of the user to notify if an error occurs with the asyncronous creation of a counter document. Empty value or missing AD_SysConfig record means "notify noone".
Setting Configuration Level to "Organization" to allow different orgs to have different users in charge
100=SuperUser',Updated=TO_TIMESTAMP('2016-07-05 14:44:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540930
;

-- Jul 5, 2016 2:45 PM
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='AD_User_ID of the user to notify if an error occurs with the asyncronous creation of a counter document. Empty value or missing AD_SysConfig record means "notify noone".
Setting Configuration Level to "Organization" to allow different orgs to have different users in charge.
100=SuperUser',Updated=TO_TIMESTAMP('2016-07-05 14:45:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=540930
;

