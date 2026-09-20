-- Registers the async workpackage processor that performs the real (writing) ATP reconciliation.
--
-- Why a work package at all: MD_Candidate_Reconcile_ATP runs in the webapi JVM, which never activates the
-- material-dispo spring profile, so it cannot call AtpReconciliationCommand directly (IsServerProcess doesn't
-- help - it only affects Swing class loading). A real run is enqueued and drained here by the app server
-- instead; a dry run needs none of this, previewed synchronously off the unguarded AtpTargetCalculator.
--
-- All three rows are required: a C_Queue_Processor only drains packages assigned to it via
-- C_Queue_Processor_Assign, so a registered classname with no assignment row just accumulates forever.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   C_Queue_PackageProcessor 540116
--   C_Queue_Processor        540086
--   C_Queue_Processor_Assign 540130

INSERT INTO C_Queue_PackageProcessor (AD_Client_ID,AD_Org_ID,Classname,C_Queue_PackageProcessor_ID,Created,CreatedBy,EntityType,InternalName,IsActive,Updated,UpdatedBy)
VALUES (0,0,'de.metas.material.dispo.reconcile.async.AtpReconciliationWorkpackageProcessor',540116 /*From ID Server*/,TO_TIMESTAMP('2026-09-09 22:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','AtpReconciliationWorkpackageProcessor','Y',TO_TIMESTAMP('2026-09-09 22:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Own queue processor (not an assignment to an existing one) with PoolSize 1: this must not compete for
-- threads with the high-volume queues (invoice candidates, printing), and two concurrent reconciliations of
-- overlapping selections would each compute a target from a chain the other is rewriting.
--
-- PoolSize 1 only serialises WITHIN one app server - deliberately no cross-replica lock, because a second
-- app-server replica activating @Profile(material-dispo) would already be double-processing every material
-- event, long before two reconciliations could overlap. That topology can't exist without the engine already
-- being broken, so guarding against it here would be redundant.
INSERT INTO C_Queue_Processor (AD_Client_ID,AD_Org_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,KeepAliveTimeMillis,Name,PoolSize,Updated,UpdatedBy)
VALUES (0,0,540086 /*From ID Server*/,TO_TIMESTAMP('2026-09-09 22:00:01','YYYY-MM-DD HH24:MI:SS'),100,'Y',1000,'AtpReconciliationWorkpackageProcessor',1,TO_TIMESTAMP('2026-09-09 22:00:01','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO C_Queue_Processor_Assign (AD_Client_ID,AD_Org_ID,C_Queue_PackageProcessor_ID,C_Queue_Processor_Assign_ID,C_Queue_Processor_ID,Created,CreatedBy,IsActive,Updated,UpdatedBy)
VALUES (0,0,540116,540130 /*From ID Server*/,540086,TO_TIMESTAMP('2026-09-09 22:00:02','YYYY-MM-DD HH24:MI:SS'),100,'Y',TO_TIMESTAMP('2026-09-09 22:00:02','YYYY-MM-DD HH24:MI:SS'),100)
;
