drop function if exists product_costs_recreate_allProducts_reorder ();

create function product_costs_recreate_allProducts_reorder() returns text


    language plpgsql
as
$$
DECLARE
    rowcount integer;
    v_result text := '';
BEGIN
    UPDATE m_cost
    SET currentcostprice=0,
        currentqty=0,
        cumulatedamt=0,
        cumulatedqty=0
        -- WHERE m_product_id = p_M_Product_ID
    WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_Cost(s) set to ZERO; ';
    raise notice '% M_Cost(s) set to ZERO', rowcount;


    DELETE
    FROM m_costdetail
    WHERE 1 = 1;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    v_result := v_result || rowcount || ' M_CostDetail(s) deleted; ';

    raise notice '% M_CostDetail(s) deleted; ', rowcount;

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
    raise notice '% document(s) unposted; ', rowcount;

    RETURN v_result;
END;
$$;

alter function product_costs_recreate_allProducts_reorder() owner to metasfresh;