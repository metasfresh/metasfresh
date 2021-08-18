-- 2021-08-17T08:14:51.334520100Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541384,'O',TO_TIMESTAMP('2021-08-17 11:14:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.session.UserSession.IsAlwaysDisplayNewBPartner',TO_TIMESTAMP('2021-08-17 11:14:51','YYYY-MM-DD HH24:MI:SS'),100,'N')
;

-- 2021-08-18T07:34:09.303918600Z
-- URL zum Konzept
UPDATE AD_SysConfig SET Description='When this configuration has the value ''Y'', the "New Partner"  window is available in the the new Order as soon as one starts searching for the Business Partner, whether there are rusults of the search or not.
When on ''N'', the  "New Partner" is only available if no partners were found for the search criteria.',Updated=TO_TIMESTAMP('2021-08-18 10:34:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541384
;

