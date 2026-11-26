-- Invoice candidates of a closed disposition with nothing delivered or invoiced are now processed by a computed rule.
-- Older candidates in that state still have Processed = 'N': the rule only applies when something recomputes them.
-- Queue them once for recompute, so that the rule decides each of them now.
--
-- Selected (a preselection only; the recompute's rule decides each row): active, delivery closed, not processed, no Processed_Override,
-- no error, nothing delivered (candidate and order line; no order line counts as nothing delivered), nothing invoiced,
-- invoice rule (override first) "after order delivered" for any product, or "after delivery" / "order completely delivered" /
-- "customer schedule after delivery" / "manual" for stock items only (the rules whose quantity to invoice can only come from a delivery).
-- Candidates already queued for recompute are skipped, so running this again adds no row twice.
-- No candidate is changed here: the recompute (with all its interceptors) changes them, so no backup is taken.

INSERT INTO C_Invoice_Candidate_Recompute (C_Invoice_Candidate_ID)
SELECT ic.C_Invoice_Candidate_ID
FROM C_Invoice_Candidate ic
WHERE ic.IsActive = 'Y'
  AND ic.IsDeliveryClosed = 'Y'
  AND ic.Processed = 'N'
  AND ic.Processed_Override IS NULL
  AND ic.IsError = 'N'
  AND (COALESCE(ic.InvoiceRule_Override, ic.InvoiceRule) = 'O'
    OR (COALESCE(ic.InvoiceRule_Override, ic.InvoiceRule) IN ('D', 'C', 'S', 'M')
        AND EXISTS (SELECT 1 FROM M_Product p WHERE p.M_Product_ID = ic.M_Product_ID AND p.ProductType = 'I')))
  AND ic.QtyDelivered = 0
  AND ic.QtyInvoiced = 0
  AND NOT EXISTS (SELECT 1 FROM C_OrderLine ol WHERE ol.C_OrderLine_ID = ic.C_OrderLine_ID AND ol.QtyDelivered <> 0)
  AND NOT EXISTS (SELECT 1 FROM C_Invoice_Candidate_Recompute r WHERE r.C_Invoice_Candidate_ID = ic.C_Invoice_Candidate_ID)
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

                RAISE NOTICE 'AD_Client_ID=%: % queued invoice candidates, update workpackage enqueued with AD_Role_ID=%, AD_User_ID=%',
                    v_client.AD_Client_ID, v_client.queued, v_role_id, COALESCE(v_user_id, 100);
            END LOOP;
    END
$$
;
