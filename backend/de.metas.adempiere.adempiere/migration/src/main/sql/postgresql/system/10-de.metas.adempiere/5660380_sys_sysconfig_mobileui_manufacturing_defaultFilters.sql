-- 2022-10-14T19:27:51.081Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541519,'S',TO_TIMESTAMP('2022-10-14 22:27:50','YYYY-MM-DD HH24:MI:SS'),100,'Describes which filters to be applied to available manufacturing jobs for logged user in mobile UI.
Following values are available:
* UserPlant -show only those manufacturing order which are for a plant which is associated with logged user
 TodayDatePromised - show only those which have the DatePromised as today.','D','Y','mobileui.manufacturing.defaultFilters',TO_TIMESTAMP('2022-10-14 22:27:50','YYYY-MM-DD HH24:MI:SS'),100,'UserPlant, TodayDatePromised')
;

-- 2022-10-14T19:28:36.567Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='-',Updated=TO_TIMESTAMP('2022-10-14 22:28:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541519
;

-- 2022-10-14T19:28:41.385Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
--UPDATE AD_SysConfig SET ConfigurationLevel='S', Value='UserPlant, TodayDatePromised',Updated=TO_TIMESTAMP('2022-10-14 22:28:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541519
-- ;

