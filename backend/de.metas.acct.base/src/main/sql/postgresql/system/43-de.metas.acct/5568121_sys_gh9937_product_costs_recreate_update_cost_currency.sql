DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate(p_M_Product_ID numeric);

CREATE OR REPLACE FUNCTION "de_metas_acct".product_costs_recreate(p_M_Product_ID numeric)
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

    UPDATE m_cost c
    SET currentcostprice=0, currentqty=0, cumulatedamt=0, cumulatedqty=0,
        c_currency_id=ac.c_currency_id /*make sure to also get the *current* currency of our ac*/
    FROM c_acctschema ac
    WHERE m_product_id = p_M_Product_ID AND ac.c_acctschema_id=c.c_acctschema_id /*m_cost.c_acctschema_id is mandatory*/;
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

    RETURN v_result;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
                     COST 100;
