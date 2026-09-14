-- Source DDL: backend/de.metas.swat/de.metas.swat.base/src/main/sql/postgresql/ddl/de_metas_inoutcandidate/m_shipmentschedule_tagtorecompute.sql

DROP FUNCTION IF EXISTS m_shipmentschedule_tagtorecompute(numeric, integer);

CREATE OR REPLACE FUNCTION m_shipmentschedule_tagtorecompute(p_selection_id numeric, p_batchsize integer)
RETURNS integer
LANGUAGE plpgsql
AS $func$
DECLARE
    v_tagged integer;
BEGIN
    IF p_batchsize IS NULL OR p_batchsize <= 0 THEN
        -- Unbounded: tag every untagged marker (the manual, single-shot path). The EXISTS keeps the
        -- original Java NO_LIMIT branch's "only markers whose M_ShipmentSchedule still exists" filter.
        UPDATE M_ShipmentSchedule_Recompute sr
           SET AD_PInstance_ID = p_selection_id
         WHERE sr.AD_PInstance_ID IS NULL
           AND EXISTS (SELECT 1 FROM M_ShipmentSchedule s WHERE s.M_ShipmentSchedule_ID = sr.M_ShipmentSchedule_ID);
    ELSE
        -- Bounded to WHOLE PRODUCTS (stock-coherent unit): ShipmentScheduleUpdater loads one
        -- shared on-hand stock pool per recompute pass, so splitting a product's schedules across
        -- two passes would double-allocate stock. Candidate products are ordered ascending by
        -- M_Product_ID and accumulate (via a running total of their DISTINCT schedule counts)
        -- until the cumulative count would reach p_batchsize -- a product is never split, and the
        -- first product always qualifies (its running total so far is zero), so at least one
        -- whole product is tagged even if it alone exceeds p_batchsize. All of the qualifying
        -- products' recompute markers are tagged, including duplicates -- the outer WHERE matches
        -- on schedule id, not on recompute row id.
        UPDATE M_ShipmentSchedule_Recompute sr
           SET AD_PInstance_ID = p_selection_id
         WHERE sr.AD_PInstance_ID IS NULL
           AND sr.M_ShipmentSchedule_ID IN (
                 SELECT sr2.M_ShipmentSchedule_ID
                   FROM M_ShipmentSchedule_Recompute sr2
                   JOIN M_ShipmentSchedule s2 ON s2.M_ShipmentSchedule_ID = sr2.M_ShipmentSchedule_ID
                  WHERE sr2.AD_PInstance_ID IS NULL
                    AND s2.M_Product_ID IN (
                          SELECT p.M_Product_ID
                            FROM (
                                   SELECT s3.M_Product_ID,
                                          COUNT(DISTINCT sr3.M_ShipmentSchedule_ID) AS sched_count,
                                          SUM(COUNT(DISTINCT sr3.M_ShipmentSchedule_ID))
                                              OVER (ORDER BY s3.M_Product_ID ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) AS running_total
                                     FROM M_ShipmentSchedule_Recompute sr3
                                     JOIN M_ShipmentSchedule s3 ON s3.M_ShipmentSchedule_ID = sr3.M_ShipmentSchedule_ID
                                    WHERE sr3.AD_PInstance_ID IS NULL
                                    GROUP BY s3.M_Product_ID
                                 ) p
                           WHERE p.running_total - p.sched_count < p_batchsize
                        )
               );
    END IF;

    GET DIAGNOSTICS v_tagged = ROW_COUNT;
    RETURN v_tagged;
END;
$func$;

COMMENT ON FUNCTION m_shipmentschedule_tagtorecompute(numeric, integer) IS
'Tags untagged M_ShipmentSchedule_Recompute rows (AD_PInstance_ID IS NULL) with p_selection_id so a recompute pass can claim them. When p_batchsize is NULL or <= 0, tags everything (unbounded/manual path). Otherwise bounds the tagging to whole products (stock-coherent unit, never splits a product''s schedules across the boundary; always tags at least one whole product). Returns the number of M_ShipmentSchedule_Recompute rows tagged.';
