-- Source DDL: backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/ddl/de_metas_inoutcandidate/m_shipmentschedule_recompute_dedup.sql

DROP FUNCTION IF EXISTS m_shipmentschedule_recompute_dedup();

CREATE OR REPLACE FUNCTION m_shipmentschedule_recompute_dedup()
RETURNS bigint
LANGUAGE plpgsql
AS $func$
DECLARE
    v_deleted bigint;
BEGIN
    -- Fail fast rather than hang/deadlock behind a running recompute batch's bulk UPDATE.
    SET LOCAL lock_timeout = '5s';

    -- Only UNCLAIMED rows (AD_PInstance_ID IS NULL): never removes a row an in-flight
    -- batch has claimed and is processing. Keep the lowest-ctid row per schedule.
    WITH ranked AS (
        SELECT ctid,
               row_number() OVER (PARTITION BY M_ShipmentSchedule_ID ORDER BY ctid) AS rn
        FROM M_ShipmentSchedule_Recompute
        WHERE AD_PInstance_ID IS NULL
    )
    DELETE FROM M_ShipmentSchedule_Recompute t
    USING ranked r
    WHERE t.ctid = r.ctid
      AND r.rn > 1;

    GET DIAGNOSTICS v_deleted = ROW_COUNT;
    RAISE NOTICE 'm_shipmentschedule_recompute_dedup: deleted % duplicate flag(s)', v_deleted;
    RETURN v_deleted;
END;
$func$;

COMMENT ON FUNCTION m_shipmentschedule_recompute_dedup() IS
'Deduplicates enqueued M_ShipmentSchedule_Recompute rows: keeps one per M_ShipmentSchedule_ID (unclaimed rows only). Returns number of duplicate flags deleted. Stopgap for over-enqueuing; run on demand or periodically.';
