-- VAT-ID online check: register the async queue that runs the save-triggered check.
--
-- WHY. The save-triggered check used to run on the saving thread, immediately after commit. Its everyday
-- latency is a fraction of a second, but a VIES that is merely slow rather than down would be felt
-- directly by the user saving a Business Partner, and on a bulk import it would occupy one thread per
-- record for as long as the service takes to answer. Moving it to a work package puts that wait on the
-- async processor, which brings retry, restart-survival and monitor visibility with it.
--
-- The process (C_BPartner_VATaxID_Check) is deliberately NOT routed through this queue: it already runs
-- asynchronously with its own per-target transaction isolation and its own run report, and its caller
-- waits for a summary rather than for a save to return.
--
-- POOL SIZE 1, deliberately. The queue's whole purpose is to keep a slow third party off the user's
-- thread, not to hit that third party harder: VIES is a shared public service and the requester identity
-- we send is attributable, so a wide pool would turn a bulk import into a burst of concurrent calls from
-- one requester. One worker drains the queue steadily instead. De-duplication in the check service means
-- repeated saves of the same value collapse to a single call anyway, so depth is rarely the constraint.
-- Raise it only with evidence that the queue is falling behind.
--
-- IDs from idserver.metas.de: AD_MigrationScript 5819330, C_Queue_PackageProcessor 540115,
-- C_Queue_Processor 540085, C_Queue_Processor_Assign 540129.

INSERT INTO C_Queue_PackageProcessor (C_Queue_PackageProcessor_ID, AD_Client_ID, AD_Org_ID, IsActive,
                                      Created, CreatedBy, Updated, UpdatedBy,
                                      Classname, InternalName, Description, EntityType)
VALUES (540115 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 17:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'de.metas.vatid.async.VATaxIDCheckWorkpackageProcessor',
        'VATaxIDCheckWorkpackageProcessor',
        'Runs the VAT-ID online check that a Business Partner or address save triggered, off the saving thread.',
        'D');

INSERT INTO C_Queue_Processor (C_Queue_Processor_ID, AD_Client_ID, AD_Org_ID, IsActive,
                              Created, CreatedBy, Updated, UpdatedBy,
                              Name, PoolSize, KeepAliveTimeMillis)
VALUES (540085 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 17:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 17:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'VATaxIDCheckWorkpackageProcessor', 1, 1000);

INSERT INTO C_Queue_Processor_Assign (C_Queue_Processor_Assign_ID, AD_Client_ID, AD_Org_ID, IsActive,
                                      Created, CreatedBy, Updated, UpdatedBy,
                                      C_Queue_Processor_ID, C_Queue_PackageProcessor_ID)
VALUES (540129 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-15 17:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-15 17:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        540085, 540115);
