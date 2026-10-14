-- C_BPartner_EDI_Setting — hide the "External System Config" field unless the
-- sending mode is External System ('E'). It is meaningless (and already
-- mandatory-logic'd off) in Replication mode ('R'), so it should not be shown.
--
-- Updates every AD_Field bound to the config column — across the standard tab
-- and any customer/custom copy of the EDI-Setting tab — by matching AD_Column_ID
-- rather than a single AD_Field_ID, so it applies for all metasfresh customers.
-- Reused: AD_Column 592683 (EdiDESADV_ExternalSystem_Config_ID),
--         592688 (EdiINVOIC_ExternalSystem_Config_ID).

UPDATE AD_Field
SET DisplayLogic = '@EdiDESADVSendingMode/R@=E',
    Updated = TO_TIMESTAMP('2026-08-28 10:10:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 592683
;

UPDATE AD_Field
SET DisplayLogic = '@EdiINVOICSendingMode/R@=E',
    Updated = TO_TIMESTAMP('2026-08-28 10:10:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Column_ID = 592688
;
