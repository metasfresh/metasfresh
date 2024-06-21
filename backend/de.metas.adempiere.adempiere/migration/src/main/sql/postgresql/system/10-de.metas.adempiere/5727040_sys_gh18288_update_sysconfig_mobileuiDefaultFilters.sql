-- 2024-06-21T11:49:46.412Z
UPDATE AD_SysConfig SET ConfigurationLevel='S', Description='Describes which filters to be applied to available manufacturing jobs for logged user in mobile UI.
Following values are available:
* UserPlant -show only those manufacturing order which are for a plant which is associated with logged user
 TodayDateStartSchedule - show only those which have the DateStartSchedule as today.',Updated=TO_TIMESTAMP('2024-06-21 14:49:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_SysConfig_ID=541519
;

UPDATE ad_sysconfig
SET value = REPLACE(value, 'TodayDatePromised', 'TodayDateStartSchedule'), Updated=TO_TIMESTAMP('2024-06-21 14:49:46', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=99
WHERE name = 'mobileui.manufacturing.defaultFilters'
  AND value LIKE '%TodayDatePromised%'
;
