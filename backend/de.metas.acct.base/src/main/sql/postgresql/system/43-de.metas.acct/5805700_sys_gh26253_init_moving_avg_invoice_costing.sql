-- Run mode: SWING_CLIENT

-- gh26253: PL/pgSQL function to initialize Moving Average Invoice costing.
--
-- Seeds M_Cost records for the MAI cost element (costingmethod='M') from the
-- existing material cost records of the schema's current costing method,
-- then switches the accounting schema's costing method to 'M'.
--
-- cumulatedamt is seeded as currentcostprice * currentqty so that the first
-- incoming receipt/invoice computes a correct weighted average from the existing stock.
--
-- Usage: SELECT C_AcctSchema_InitMovingAvgInvoice(<C_AcctSchema_ID>);
CREATE OR REPLACE FUNCTION public.C_AcctSchema_InitMovingAvgInvoice(
    p_C_AcctSchema_ID numeric
)
    RETURNS text
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_CurrentCostingMethod char;
    v_AD_Client_ID         numeric;
    v_CurrentCostElementId numeric;
    v_MAICostElementId     numeric;
    v_RowsInserted         int;
BEGIN
    -- Fetch current costing method and client
    SELECT costingmethod, ad_client_id
    INTO v_CurrentCostingMethod, v_AD_Client_ID
    FROM c_acctschema
    WHERE c_acctschema_id = p_C_AcctSchema_ID;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'C_AcctSchema_ID % not found', p_C_AcctSchema_ID;
    END IF;

    IF v_CurrentCostingMethod = 'M' THEN
        RAISE NOTICE 'C_AcctSchema_ID % is already on MovingAverageInvoice, nothing to do', p_C_AcctSchema_ID;
        RETURN 'C_AcctSchema_ID ' || p_C_AcctSchema_ID || ' is already on MovingAverageInvoice, nothing to do';
    END IF;

    -- Find the material cost element for the current costing method
    SELECT m_costelement_id
    INTO v_CurrentCostElementId
    FROM m_costelement
    WHERE ad_client_id     = v_AD_Client_ID
      AND costelementtype  = 'M'
      AND costingmethod    = v_CurrentCostingMethod
      AND isactive         = 'Y'
    ORDER BY m_costelement_id
    LIMIT 1;

    IF v_CurrentCostElementId IS NULL THEN
        RAISE EXCEPTION 'No active material cost element found for method=% client=%',
            v_CurrentCostingMethod, v_AD_Client_ID;
    END IF;

    -- Find the MovingAverageInvoice cost element for this client
    SELECT m_costelement_id
    INTO v_MAICostElementId
    FROM m_costelement
    WHERE ad_client_id    = v_AD_Client_ID
      AND costelementtype = 'M'
      AND costingmethod   = 'M'
      AND isactive        = 'Y'
    ORDER BY m_costelement_id
    LIMIT 1;

    IF v_MAICostElementId IS NULL THEN
        RAISE EXCEPTION 'No active MovingAverageInvoice cost element (costingmethod=M) found for client=%',
            v_AD_Client_ID;
    END IF;

    -- Backup affected tables before making changes
    PERFORM backup_table('m_cost',       '_pre_mai');
    PERFORM backup_table('c_acctschema', '_pre_mai');

    -- Seed M_Cost records for the MAI cost element.
    -- Skip products that already have a record for the target cost element + cost type.
    INSERT INTO m_cost (
        ad_client_id, ad_org_id,
        m_product_id, m_costtype_id, c_acctschema_id,
        m_costelement_id, m_attributesetinstance_id,
        currentcostprice, currentqty,
        cumulatedamt, cumulatedqty,
        currentcostpricell,
        c_currency_id, c_uom_id,
        percent, isactive,
        created, createdby, updated, updatedby
    )
    SELECT
        src.ad_client_id,
        src.ad_org_id,
        src.m_product_id,
        src.m_costtype_id,
        src.c_acctschema_id,
        v_MAICostElementId,
        src.m_attributesetinstance_id,
        src.currentcostprice,
        src.currentqty,
        src.currentcostprice * src.currentqty,  -- seeds cumulatedamt for weighted-average formula
        src.currentqty,                         -- seeds cumulatedqty
        src.currentcostpricell,
        src.c_currency_id,
        src.c_uom_id,
        0,
        'Y',
        now(), 99, now(), 99
    FROM m_cost src
    WHERE src.c_acctschema_id  = p_C_AcctSchema_ID
      AND src.m_costelement_id = v_CurrentCostElementId
      AND NOT EXISTS (
          SELECT 1
          FROM m_cost tgt
          WHERE tgt.c_acctschema_id           = p_C_AcctSchema_ID
            AND tgt.m_product_id              = src.m_product_id
            AND tgt.m_costelement_id          = v_MAICostElementId
            AND tgt.m_attributesetinstance_id = src.m_attributesetinstance_id
            AND tgt.m_costtype_id             = src.m_costtype_id
      );

    GET DIAGNOSTICS v_RowsInserted = ROW_COUNT;
    RAISE NOTICE 'C_AcctSchema_InitMovingAvgInvoice: seeded % M_Cost records for C_AcctSchema_ID=%',
        v_RowsInserted, p_C_AcctSchema_ID;

    -- Switch the accounting schema costing method to MovingAverageInvoice
    UPDATE c_acctschema
    SET  costingmethod = 'M',
         updated       = now(),
         updatedby     = 99
    WHERE c_acctschema_id = p_C_AcctSchema_ID;

    RAISE NOTICE 'C_AcctSchema_InitMovingAvgInvoice: C_AcctSchema_ID=% switched to costingmethod=M',
        p_C_AcctSchema_ID;

    RETURN 'Seeded ' || v_RowsInserted || ' M_Cost records for C_AcctSchema_ID=' || p_C_AcctSchema_ID || ', switched to costingmethod=M';
END;
$$;
