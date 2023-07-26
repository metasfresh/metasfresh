DROP FUNCTION IF EXISTS product_costs_recreate_allProducts_reorder ()
;
DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_allProducts_reorder ()
;


CREATE FUNCTION "de_metas_acct".product_costs_recreate_allProducts_reorder() RETURNS text


    LANGUAGE plpgsql
AS
$$
DECLARE
    rowcount integer;
    v_result text := '';
BEGIN
    DELETE
    FROM m_cost c
    WHERE TRUE
    -- AND NOT exists(SELECT 1 FROM m_costelement ce WHERE ce.m_costelement_id = c.m_costelement_id AND ce.costingmethod = 'S') -- exclude standard costing
    ;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_Cost(s) deleted; ';
    RAISE NOTICE '% M_Cost(s) deleted', rowcount;


    DELETE
    FROM m_costdetail
    WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_CostDetail(s) deleted; ';
    RAISE NOTICE '% M_CostDetail(s) deleted; ', rowcount;

    DELETE
    FROM pp_order_cost poc
    WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' PP_Order_Cost(s) deleted; ';
    RAISE NOTICE '% PP_Order_Cost(s) deleted; ', rowcount;


    SELECT count(1)
    INTO rowcount
    FROM (
             SELECT "de_metas_acct".fact_acct_unpost(t.tablename, t.record_id, 'Y')
             FROM (
                      SELECT DISTINCT tablename,
                                      record_id,
                                      dateacct,
                                      tablename_prio,
                                      least(record_id, reversal_id)
                      FROM "de_metas_acct".accountable_docs_and_lines_v
                      WHERE M_Product_ID IS NOT NULL
                      ORDER BY dateacct,
                               tablename_prio,
                               least(record_id, reversal_id),
                               record_id
                  ) t
         ) t;
    v_result := v_result || rowcount || ' document(s) unposted; ';
    RAISE NOTICE '% document(s) unposted; ', rowcount;

    RETURN v_result;
END;
$$
;
