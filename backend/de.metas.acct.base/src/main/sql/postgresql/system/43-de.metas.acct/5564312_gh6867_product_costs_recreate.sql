DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate(p_M_Product_ID numeric);
DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate(p_M_Product_ID numeric, p_ReorderDocs char(1));

CREATE OR REPLACE FUNCTION "de_metas_acct".product_costs_recreate(p_M_Product_ID numeric,
                                                                  p_ReorderDocs  char(1) = 'Y')
    RETURNS text
AS
$BODY$
DECLARE
    rowcount integer;
    v_result text := '';
BEGIN
    IF (p_M_Product_ID IS NULL OR p_M_Product_ID <= 0) THEN
        RAISE EXCEPTION 'Product shall be > 0 but it was %', p_M_Product_ID;
    END IF;

    UPDATE m_cost
    SET currentcostprice=0, currentqty=0, cumulatedamt=0, cumulatedqty=0
    WHERE m_product_id = p_M_Product_ID;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_Cost(s) set to ZERO; ';


    DELETE
    FROM m_costdetail
    WHERE m_product_id = p_M_Product_ID;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_CostDetail(s) deleted; ';


    SELECT count(1)
    INTO rowcount
    FROM (
             SELECT "de_metas_acct".fact_acct_unpost(t.tablename, t.record_id, 'Y')
             FROM (
                      SELECT DISTINCT tablename,
                                      record_id,
                                      m_product_id,
                                      dateacct,
                                      tablename_prio,
                                      least(record_id, reversal_id)
                      FROM "de_metas_acct".accountable_docs_and_lines_v
                      WHERE m_product_id = p_M_Product_ID
                      ORDER BY m_product_id,
                               dateacct,
                               tablename_prio,
                               least(record_id, reversal_id),
                               record_id
                  ) t
         ) t;
    v_result := v_result || rowcount || ' document(s) unposted; ';

    IF (p_ReorderDocs = 'Y') THEN
        PERFORM "de_metas_acct".accounting_docs_to_repost_reorder();
        v_result := v_result || 'reordered enqueued docs';
    END IF;

    RETURN v_result;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;
