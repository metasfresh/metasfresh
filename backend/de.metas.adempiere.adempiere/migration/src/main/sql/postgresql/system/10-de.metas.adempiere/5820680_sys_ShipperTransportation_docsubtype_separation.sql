-- M_ShipperTransportation carries both the transport-order and the delivery-instruction role,
-- distinguished only by C_DocType.DocSubType ('DI' = delivery instruction, else = transport order).
-- Two AD_Val_Rule-driven "add to transport order" pickers still offer a delivery instruction as a
-- valid target; this script excludes DocSubType='DI' rows from both rules.
--
-- Both rules are corrected in place rather than forked: every consumer of either rule is a
-- transport-order-only "add to" action, so the narrower scope is right for the rule's full consumer
-- set. Forking would leave the same defect on the other consumers and create two near-duplicate
-- rules.

UPDATE AD_Val_Rule
SET Code = 'M_ShipperTransportation.Processed=''N''' || E'\n'
        || 'AND EXISTS (SELECT 1 FROM C_DocType dt' || E'\n'
        || '             WHERE dt.C_DocType_ID = M_ShipperTransportation.C_DocType_ID' || E'\n'
        || '               AND (dt.DocSubType IS NULL OR dt.DocSubType <> ''DI''))',
    Updated = TO_TIMESTAMP('2026-08-27 09:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540248 /* M_ShipperTransportation_Open */;

UPDATE AD_Val_Rule
SET Name = 'M_ShipperTransportation_Open_ForShipper',
    Code = 'M_ShipperTransportation.Processed=''N'' AND M_ShipperTransportation.M_Shipper_ID = @M_Shipper_ID/-1@' || E'\n'
        || 'AND EXISTS (SELECT 1 FROM C_DocType dt' || E'\n'
        || '             WHERE dt.C_DocType_ID = M_ShipperTransportation.C_DocType_ID' || E'\n'
        || '               AND (dt.DocSubType IS NULL OR dt.DocSubType <> ''DI''))',
    Updated = TO_TIMESTAMP('2026-08-27 09:00:01', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Val_Rule_ID = 540468 /* M_ShipperTransportation_Open_ ForShipper */;

-- BillLadingReport prints a handling-unit loading list, and the delivery-planning flow never links a
-- handling unit to its M_Package, so on a delivery instruction it has nothing to print. It is scoped
-- to the transport-order window rather than guarded in Java because its process class
-- (JasperReportStarter) is shared by every Jasper report. Narrowing a table-global binding also drops
-- it from any override window over 540020; companion scripts in those repositories restore it there.
UPDATE AD_Table_Process
SET AD_Window_ID = 540020 /* Transport Auftrag */,
    Updated = TO_TIMESTAMP('2026-08-27 09:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_Process_ID = 540789 /* BillLadingReport on M_ShipperTransportation */;
