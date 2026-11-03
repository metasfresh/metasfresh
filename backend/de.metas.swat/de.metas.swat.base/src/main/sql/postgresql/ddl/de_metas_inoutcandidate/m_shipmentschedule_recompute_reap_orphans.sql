-- Purpose: delete ORPHANED enqueued recompute flags -- unclaimed rows whose M_ShipmentSchedule no longer exists.
-- Context: M_ShipmentSchedule_Recompute has no FK to M_ShipmentSchedule, so deleting a schedule leaves its flag
-- behind. M_ShipmentSchedule_TagToRecompute can never tag such a flag (both of its branches require the schedule
-- row), so it stays enqueued forever: it bloats this high-churn queue table and keeps every "is there untagged
-- work left?" probe answering yes.

DROP FUNCTION IF EXISTS m_shipmentschedule_recompute_reap_orphans();

CREATE OR REPLACE FUNCTION m_shipmentschedule_recompute_reap_orphans()
RETURNS bigint
LANGUAGE plpgsql
AS $func$
DECLARE
    v_deleted bigint;
BEGIN
    -- Fail fast rather than hang/deadlock behind a running recompute batch's bulk UPDATE.
    SET LOCAL lock_timeout = '5s';

    -- Only UNCLAIMED rows (AD_PInstance_ID IS NULL): never removes a row an in-flight
    -- batch has claimed and is processing.
    DELETE FROM M_ShipmentSchedule_Recompute sr
     WHERE sr.AD_PInstance_ID IS NULL
       AND NOT EXISTS (SELECT 1
                         FROM M_ShipmentSchedule s
                        WHERE s.M_ShipmentSchedule_ID = sr.M_ShipmentSchedule_ID);

    GET DIAGNOSTICS v_deleted = ROW_COUNT;
    RAISE NOTICE 'm_shipmentschedule_recompute_reap_orphans: deleted % orphaned flag(s)', v_deleted;
    RETURN v_deleted;
END;
$func$;

COMMENT ON FUNCTION m_shipmentschedule_recompute_reap_orphans() IS
'Deletes orphaned M_ShipmentSchedule_Recompute rows: unclaimed rows (AD_PInstance_ID IS NULL) whose M_ShipmentSchedule no longer exists and which therefore can never be tagged. Returns the number of orphaned flags deleted. Idempotent; run on demand or periodically.';
