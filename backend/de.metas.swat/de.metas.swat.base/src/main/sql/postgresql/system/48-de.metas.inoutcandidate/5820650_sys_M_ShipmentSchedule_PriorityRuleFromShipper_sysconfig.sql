-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_SysConfig 541850 (M_ShipmentSchedule_PriorityRuleFromShipper switch)

-- 2026-08-27T00:00:00.000Z
INSERT INTO AD_SysConfig
(AD_SysConfig_ID, AD_Client_ID, AD_Org_ID, Created, Updated, CreatedBy, UpdatedBy, IsActive,
 Name, Value, Description, EntityType, ConfigurationLevel)
VALUES
(541850 /*From ID Server*/, 0, 0,
 TO_TIMESTAMP('2026-08-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),
 TO_TIMESTAMP('2026-08-27 00:00:00','YYYY-MM-DD HH24:MI:SS'),
 100, 100, 'Y',
 'M_ShipmentSchedule_PriorityRuleFromShipper', 'N',
 'By setting this configuration you can switch on and off the derivation of shipment schedule priority from its shipper. If this behaviour is needed, set the value to "Y" otherwise to "N".',
 'de.metas.inoutcandidate', 'O')
;
