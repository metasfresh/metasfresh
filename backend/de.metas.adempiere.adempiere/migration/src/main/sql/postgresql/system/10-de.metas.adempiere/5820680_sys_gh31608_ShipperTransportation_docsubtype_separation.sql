-- gh31608 -- the transport-order role and the delivery-instruction role share
-- M_ShipperTransportation, distinguished only by C_DocType.DocSubType ('DI' = delivery instruction,
-- else = transport order). Two of the AD_Val_Rule-driven "add to transport order" pickers still
-- offer a delivery instruction as a valid target. This script closes that leak by excluding
-- DocSubType='DI' rows from both rules.
--
-- Usage audit performed before touching either rule (AD_Val_Rule modification requires an audit -
-- metasfresh-application-dictionary skill):
--   540248 M_ShipperTransportation_Open            -> 8 AD_Process_Para rows, all M_ShipperTransportation_ID,
--          all on processes that ADD something (a shipment, HUs, a purchase order/line, a receipt
--          schedule, a tour instance) TO a transport order - never legitimately a delivery instruction.
--   540468 M_ShipperTransportation_Open_ ForShipper -> 1 AD_Process_Para row (ProductsToPick_4EyesReview_ProcessAll),
--          same shape, plus a per-shipper filter.
-- Every one of the 9 callsites is a transport-order-only "add to" action, so excluding DocSubType='DI'
-- is correct for the rule's FULL consumer set, not just this iteration's path. Both rules are
-- therefore corrected in place rather than forked into a new AD_Val_Rule: forking here would leave
-- the same defect on the other processes and create two near-duplicate rules, which is exactly the
-- outcome the fork-instead-of-broaden guidance exists to avoid when a rule's scope genuinely IS
-- meant to change for every consumer.
--
-- NOT included here (deliberately out of scope for this script): a "direction correlation" clause
-- that would additionally require: an empty transport stays open; a non-empty one must match
-- M_ShipperTransportation.TransportDirection. That clause needs a NEW hidden direction parameter,
-- populated via getParameterDefaultValue, on each of the 9 processes above - a separate, larger
-- change than this script's 5 process classes across 3 modules. It is also independent of
-- document-TYPE separation: excluding DocSubType='DI' alone is sufficient so a delivery instruction
-- is never offered as an add-to target. Left for the task that owns TransportDirection consumer
-- wiring.
--
-- Cosmetic, in-diff: 540468's Name carries a stray space ("M_ShipperTransportation_Open_ ForShipper")
-- - fixed to "M_ShipperTransportation_Open_ForShipper" while the row is touched anyway. Verified no
-- active AD_Val_Rule already holds that name.

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

-- BillLadingReport (540011, classname JasperReportStarter - a class shared by every Jasper report,
-- so it cannot carry a per-process precondition without affecting unrelated reports) is
-- window-scoped to the transport-order window instead of guarded in Java: the process prints a
-- handling-unit loading list (de/metas/docs/sales/billoflading/report.jrxml), and the
-- delivery-planning flow never links a handling unit to its M_Package, so on a delivery instruction
-- the report has nothing to print. No customer repo checked customises a window or AD_Table_Process
-- over M_ShipperTransportation (AD_Table_ID 540030), so the window-scope is not defeated by an
-- existing clone. PrintAllShipmentDocuments (541228, AD_Table_Process 540765+541325) is untouched -
-- it is deliberately offered on both windows already.
UPDATE AD_Table_Process
SET AD_Window_ID = 540020 /* Transport Auftrag */,
    Updated = TO_TIMESTAMP('2026-08-27 09:00:02', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy = 100
WHERE AD_Table_Process_ID = 540789 /* BillLadingReport on M_ShipperTransportation, previously unscoped (both windows) */;
