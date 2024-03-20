-- 2023-12-18T17:00:21.778Z
INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, AD_SysConfig_ID, ConfigurationLevel, Created, CreatedBy, Description, EntityType, IsActive, Name, Updated, UpdatedBy, Value)
VALUES (0, 0, 541679, 'S', TO_TIMESTAMP('2023-12-18 19:00:21.516', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'Contains comma separated values from AD_Reference_ID = 540478. If set, the hus returned by ''de.metas.handlingunits.rest_api.HandlingUnitsRestController.getBySerialNo'' will be filtered against those statuses. (for example, for active and planning HUs the sys config must be set to: A,P)', 'de.metas.handlingunits', 'Y', 'de.metas.handlingunits.rest_api.bySerialNo.onlyHUStatuses', TO_TIMESTAMP('2023-12-18 19:00:21.516', 'YYYY-MM-DD HH24:MI:SS.US'), 100, 'A')
;

