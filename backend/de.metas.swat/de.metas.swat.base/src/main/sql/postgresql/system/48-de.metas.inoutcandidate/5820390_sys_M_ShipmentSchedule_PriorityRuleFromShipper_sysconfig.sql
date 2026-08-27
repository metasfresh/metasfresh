-- IDs allocated from idserver.metas.de on 2026-08-27:
--   AD_SysConfig 541850 (M_ShipmentSchedule_PriorityRuleFromShipper switch)

INSERT INTO ad_sysconfig
(ad_sysconfig_id, ad_client_id, ad_org_id, created, updated, createdby, updatedby, isactive,
 name, value, description, entitytype, configurationlevel)
VALUES
(541850, 0, 0, now(), now(), 100, 100, 'Y',
 'M_ShipmentSchedule_PriorityRuleFromShipper', 'N',
 'By setting this configuration you can switch on and off the derivation of shipment schedule priority from its shipper. If this behaviour is needed, set the value to "Y" otherwise to "N".',
 'de.metas.inoutcandidate', 'O');
