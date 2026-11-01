-- Invoice candidates of a closed disposition with nothing delivered are now processed by a computed rule.
-- Before, closing such a disposition set Processed_Override = 'Y' on its candidates, and reopening the disposition cleared it again.
-- That reopen is gone, so those explicit closes would stay closed forever: reset them, so that the computed rule decides again.
--
-- Affected: explicit closes on a closed shipment / receipt schedule whose line was never delivered or invoiced,
-- written after migration 5821320_sys_Event_InvoicingError_message.sql was applied (the explicit close came with it).
-- Without that migration's AD_MigrationScript record, nothing is reset.
-- Not affected: candidates covered by a non-simulation flatrate term (closed when they were created; same term lookup as the invoice candidate's flatrate check).
-- A close by hand on such a line after that time cannot be told apart from the disposition's close; it is reset too.
--
-- The rows are queued for recompute first (same predicate, before Processed_Override is reset),
-- then one update workpackage is enqueued per client that has queued rows.

SELECT backup_table('C_Invoice_Candidate', '_reset_ProcOverride');

INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)
SELECT ic.C_Invoice_Candidate_ID
FROM C_Invoice_Candidate ic
WHERE ic.Processed_Override = 'Y'
  AND ic.IsDeliveryClosed = 'Y'
  AND ic.QtyDelivered = 0
  AND ic.QtyInvoiced = 0
  AND ic.Updated >= (SELECT MIN(ms.Created) FROM AD_MigrationScript ms WHERE ms.Name LIKE '%5821320_sys_Event_InvoicingError_message.sql')
  AND EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_OrderLine_ID = ic.C_OrderLine_ID AND ol.QtyDelivered = 0)
  AND (EXISTS (SELECT 1 FROM M_ShipmentSchedule ss WHERE ss.C_OrderLine_ID = ic.C_OrderLine_ID AND ss.IsClosed = 'Y')
    OR EXISTS (SELECT 1 FROM M_ReceiptSchedule rs WHERE rs.C_OrderLine_ID = ic.C_OrderLine_ID AND rs.IsClosed = 'Y'))
  AND NOT EXISTS (SELECT 1
                  FROM C_Flatrate_Term ft
                           JOIN C_Flatrate_Conditions fc ON fc.C_Flatrate_Conditions_ID = ft.C_Flatrate_Conditions_ID
                  WHERE ft.IsActive = 'Y'
                    AND ft.IsSimulation = 'N'
                    AND ft.DocStatus = 'CO'
                    AND ft.AD_Org_ID = ic.AD_Org_ID
                    AND ft.Bill_BPartner_ID = ic.Bill_BPartner_ID
                    AND ft.StartDate <= ic.DateOrdered
                    AND ft.EndDate >= ic.DateOrdered
                    AND fc.IsActive = 'Y'
                    AND fc.DocStatus = 'CO'
                    AND fc.Type_Conditions NOT IN ('Subscr', 'HoldingFee')
                    AND fc.M_Product_Flatrate_ID IS DISTINCT FROM ic.M_Product_ID
                    AND fc.M_Product_Correction_ID IS DISTINCT FROM ic.M_Product_ID
                    AND fc.M_Product_Actual_ID IS DISTINCT FROM ic.M_Product_ID
                    AND EXISTS (SELECT 1
                                FROM C_Flatrate_Matching m
                                         JOIN M_Product p ON p.M_Product_ID = ic.M_Product_ID
                                WHERE m.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
                                  AND m.IsActive = 'Y'
                                  AND (m.M_Product_Category_Matching_ID IS NULL OR m.M_Product_Category_Matching_ID = p.M_Product_Category_ID)
                                  AND (m.M_Product_ID IS NULL OR m.M_Product_ID = ic.M_Product_ID)
                                  AND (m.C_Charge_ID IS NULL OR COALESCE(ic.C_Charge_ID, 0) <= 0 OR m.C_Charge_ID = ic.C_Charge_ID)))
;

UPDATE C_Invoice_Candidate ic
SET Processed_Override = NULL,
    Updated            = TO_TIMESTAMP('2026-11-01 15:00:00', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy          = 99
WHERE ic.Processed_Override = 'Y'
  AND ic.IsDeliveryClosed = 'Y'
  AND ic.QtyDelivered = 0
  AND ic.QtyInvoiced = 0
  AND ic.Updated >= (SELECT MIN(ms.Created) FROM AD_MigrationScript ms WHERE ms.Name LIKE '%5821320_sys_Event_InvoicingError_message.sql')
  AND EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_OrderLine_ID = ic.C_OrderLine_ID AND ol.QtyDelivered = 0)
  AND (EXISTS (SELECT 1 FROM M_ShipmentSchedule ss WHERE ss.C_OrderLine_ID = ic.C_OrderLine_ID AND ss.IsClosed = 'Y')
    OR EXISTS (SELECT 1 FROM M_ReceiptSchedule rs WHERE rs.C_OrderLine_ID = ic.C_OrderLine_ID AND rs.IsClosed = 'Y'))
  AND NOT EXISTS (SELECT 1
                  FROM C_Flatrate_Term ft
                           JOIN C_Flatrate_Conditions fc ON fc.C_Flatrate_Conditions_ID = ft.C_Flatrate_Conditions_ID
                  WHERE ft.IsActive = 'Y'
                    AND ft.IsSimulation = 'N'
                    AND ft.DocStatus = 'CO'
                    AND ft.AD_Org_ID = ic.AD_Org_ID
                    AND ft.Bill_BPartner_ID = ic.Bill_BPartner_ID
                    AND ft.StartDate <= ic.DateOrdered
                    AND ft.EndDate >= ic.DateOrdered
                    AND fc.IsActive = 'Y'
                    AND fc.DocStatus = 'CO'
                    AND fc.Type_Conditions NOT IN ('Subscr', 'HoldingFee')
                    AND fc.M_Product_Flatrate_ID IS DISTINCT FROM ic.M_Product_ID
                    AND fc.M_Product_Correction_ID IS DISTINCT FROM ic.M_Product_ID
                    AND fc.M_Product_Actual_ID IS DISTINCT FROM ic.M_Product_ID
                    AND EXISTS (SELECT 1
                                FROM C_Flatrate_Matching m
                                         JOIN M_Product p ON p.M_Product_ID = ic.M_Product_ID
                                WHERE m.C_Flatrate_Conditions_ID = fc.C_Flatrate_Conditions_ID
                                  AND m.IsActive = 'Y'
                                  AND (m.M_Product_Category_Matching_ID IS NULL OR m.M_Product_Category_Matching_ID = p.M_Product_Category_ID)
                                  AND (m.M_Product_ID IS NULL OR m.M_Product_ID = ic.M_Product_ID)
                                  AND (m.C_Charge_ID IS NULL OR COALESCE(ic.C_Charge_ID, 0) <= 0 OR m.C_Charge_ID = ic.C_Charge_ID)))
;

-- The recompute queue has no client column, and an update run only takes its own client's queued rows,
-- so each client with queued rows gets its own update workpackage (client 0 is skipped: the update run refuses it).
-- The workpackage runs as a user of that client in one of that client's roles: the lowest-ID active, non-manual role
-- with client-level access to all organisations that is assigned to an active user (user 100 preferred, else the lowest-ID user).
-- If the client has no such role, the role stays empty and user 100 is used; the async processor then looks up the user's role itself.
-- nextval and now() are needed here: the workpackage is runtime queue data, created when this migration runs.
DO
$$
    DECLARE
        v_client  record;
        v_role_id numeric;
        v_user_id numeric;
    BEGIN
        FOR v_client IN SELECT ic.AD_Client_ID, COUNT(*) AS queued
                        FROM C_Invoice_Candidate_Recompute r
                                 JOIN C_Invoice_Candidate ic ON ic.C_Invoice_Candidate_ID = r.C_Invoice_Candidate_ID
                        WHERE ic.AD_Client_ID > 0
                        GROUP BY ic.AD_Client_ID
                        ORDER BY ic.AD_Client_ID
            LOOP
                v_role_id := NULL;
                v_user_id := NULL;
                SELECT ur.AD_Role_ID, ur.AD_User_ID
                INTO v_role_id, v_user_id
                FROM AD_User_Roles ur
                         JOIN AD_Role r ON r.AD_Role_ID = ur.AD_Role_ID
                         JOIN AD_User u ON u.AD_User_ID = ur.AD_User_ID
                WHERE r.AD_Client_ID = v_client.AD_Client_ID
                  AND r.IsActive = 'Y'
                  AND r.IsManual = 'N'
                  AND r.IsAccessAllOrgs = 'Y'
                  AND r.UserLevel LIKE '%C%'
                  AND ur.IsActive = 'Y'
                  AND u.IsActive = 'Y'
                ORDER BY (ur.AD_User_ID = 100) DESC, r.AD_Role_ID, ur.AD_User_ID
                LIMIT 1;

                INSERT INTO C_Queue_WorkPackage (C_Queue_WorkPackage_ID, C_Queue_PackageProcessor_ID,
                                                 AD_Client_ID, AD_Org_ID, AD_Role_ID, AD_User_ID,
                                                 IsReadyForProcessing, IsActive, Created, CreatedBy, Updated, UpdatedBy)
                VALUES (NEXTVAL('c_queue_workpackage_seq'),
                        (SELECT p.C_Queue_PackageProcessor_ID
                         FROM C_Queue_PackageProcessor p
                         WHERE p.Classname = 'de.metas.invoicecandidate.async.spi.impl.UpdateInvalidInvoiceCandidatesWorkpackageProcessor'),
                        v_client.AD_Client_ID, 0, v_role_id, COALESCE(v_user_id, 100),
                        'Y', 'Y', NOW(), 99, NOW(), 99);

                RAISE NOTICE 'AD_Client_ID=%: % queued invoice candidates; update workpackage enqueued with AD_Role_ID=%, AD_User_ID=%',
                    v_client.AD_Client_ID, v_client.queued, v_role_id, COALESCE(v_user_id, 100);
            END LOOP;
    END
$$
;
