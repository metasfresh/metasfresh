
-- 2021-11-25T17:39:30.068Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,Description) VALUES (0,0,541429,'S',TO_TIMESTAMP('2021-11-25 19:39:29','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.address.AddressDescriptorFactory.Street.IsDisplay',TO_TIMESTAMP('2021-11-25 19:39:29','YYYY-MM-DD HH24:MI:SS'),100,'N',
'Decides if the Street field shall be shown in the address dialog')
;

-- 2021-11-25T17:40:10.601Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,Description) VALUES (0,0,541430,'S',TO_TIMESTAMP('2021-11-25 19:40:10','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.address.AddressDescriptorFactory.DHL_PostId.IsDisplay',TO_TIMESTAMP('2021-11-25 19:40:10','YYYY-MM-DD HH24:MI:SS'),100,'N',
'Decides if the DHL_PostId field shall be shown in the address dialog')
;

-- 2021-11-25T17:40:40.109Z
-- URL zum Konzept
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,Description) VALUES (0,0,541431,'S',TO_TIMESTAMP('2021-11-25 19:40:39','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','de.metas.ui.web.address.AddressDescriptorFactory.HouseNumber.IsDisplay',TO_TIMESTAMP('2021-11-25 19:40:39','YYYY-MM-DD HH24:MI:SS'),100,'N',
'Decides if the HouseNumber field shall be shown in the address dialog')
;




-- 2021-11-25T17:44:36.901Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='C',Updated=TO_TIMESTAMP('2021-11-25 19:44:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541430
;

-- 2021-11-25T17:44:44.438Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='C',Updated=TO_TIMESTAMP('2021-11-25 19:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541431
;

-- 2021-11-25T17:45:00.174Z
-- URL zum Konzept
UPDATE AD_SysConfig SET ConfigurationLevel='C',Updated=TO_TIMESTAMP('2021-11-25 19:45:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541429
;

-- 2021-11-26T15:28:11.855Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541432,'C',TO_TIMESTAMP('2021-11-26 16:28:11','YYYY-MM-DD HH24:MI:SS'),100,'If set, then the given expression is used for the address model''s default country','D','N','de.metas.ui.web.address.AddressDescriptorFactory.C_Country.DefaultLogic',TO_TIMESTAMP('2021-11-26 16:28:11','YYYY-MM-DD HH24:MI:SS'),100,'@SQL=(select C_Country_ID from C_Country where CountryCode=''DE'')')
;
