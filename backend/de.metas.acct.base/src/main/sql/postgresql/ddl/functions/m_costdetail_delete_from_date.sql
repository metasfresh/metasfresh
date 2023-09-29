DROP FUNCTION IF EXISTS "de_metas_acct".m_costdetail_delete_from_date(
    p_C_AcctSchema_ID  numeric,
    p_M_CostElement_ID numeric,
    p_M_Product_ID     numeric,
    p_AD_Org_ID        numeric,
    p_StartDateAcct    timestamp WITH TIME ZONE,
    p_DryRun           char(1)
)
;


CREATE OR REPLACE FUNCTION "de_metas_acct".m_costdetail_delete_from_date(
    p_C_AcctSchema_ID  numeric,
    p_M_CostElement_ID numeric,
    p_M_Product_ID     numeric = NULL,
    p_AD_Org_ID        numeric = 0,
    p_StartDateAcct    timestamp WITH TIME ZONE = '1970-01-01',
    p_DryRun           char(1) = 'Y'
)
    RETURNS text
    LANGUAGE plpgsql
AS
$BODY$
DECLARE
    rowcount                  integer := 0;
    --
    v_firstCostDetail         m_costdetail_v%rowtype;
    --
    v_next_costs_found        boolean := FALSE;
    v_next_costs_debugInfo    text;
    v_next_currentcostprice   numeric;
    v_next_currentcostpricell numeric;
    v_next_currentqty         numeric;
    v_next_cumulatedamt       numeric;
    v_next_cumulatedqty       numeric;
    v_m_cost_id               numeric;
BEGIN
    RAISE DEBUG 'm_costdetail_delete_from_date: p_C_AcctSchema_ID=%, p_M_CostElement_ID=%, p_M_Product_ID=%, p_AD_Org_ID=%, p_StartDateAcct=%, p_DryRun=%',
        p_C_AcctSchema_ID, p_M_CostElement_ID, p_M_Product_ID, p_AD_Org_ID, p_StartDateAcct, p_DryRun;

    --
    -- Compute current cost prices at target date
    --
    -- Approach 1: Find out first cost detail starting from our date.
    -- If found then the prev_* fields of that cost detail is exactly what we need
    SELECT cd.*
    INTO v_firstCostDetail
    FROM m_costdetail_v cd
    WHERE cd.ischangingcosts = 'Y'
      AND cd.c_acctschema_id = p_C_AcctSchema_ID
      AND cd.m_costelement_id = p_M_CostElement_ID
      AND cd.M_Product_ID = p_M_Product_ID
      AND cd.ad_client_id = 1000000
      AND (p_AD_Org_ID IS NULL OR p_AD_Org_ID <= 0 OR cd.ad_org_id = p_AD_Org_ID)
      AND cd.dateacct >= p_StartDateAcct
    ORDER BY cd.m_costdetail_id
    LIMIT 1;
    --
    IF v_firstCostDetail.m_costdetail_id IS NOT NULL THEN
        v_next_costs_found := TRUE;
        v_next_costs_debugInfo := 'case 1: based on prev costs of next cost detail';
        v_next_currentcostprice := v_firstCostDetail.prev_currentcostprice;
        v_next_currentcostpricell := v_firstCostDetail.prev_currentcostpricell;
        v_next_currentqty := v_firstCostDetail.prev_currentqty;
        v_next_cumulatedamt := v_firstCostDetail.prev_cumulatedamt;
        v_next_cumulatedqty := v_firstCostDetail.prev_cumulatedqty;
    END IF;


    --
    -- Compute current cost prices at target date
    --
    -- Approach 2: Find out first cost detail right before our target date.
    -- If found then we will use the prev_* fields of that cost detail to compute the current cost.
    IF (NOT v_next_costs_found) THEN
        SELECT cd.*
        INTO v_firstCostDetail
        FROM m_costdetail_v cd
        WHERE TRUE
          -- AND cd.ischangingcosts = 'Y' -- anything
          AND cd.c_acctschema_id = p_C_AcctSchema_ID
          AND cd.m_costelement_id = p_M_CostElement_Id
          AND cd.M_Product_ID = p_M_Product_ID
          AND cd.ad_client_id = 1000000
          AND (p_AD_Org_ID IS NULL OR p_AD_Org_ID <= 0 OR cd.ad_org_id = p_AD_Org_ID)
          AND cd.dateacct < p_StartDateAcct
        ORDER BY cd.m_costdetail_id DESC
        LIMIT 1;
        --
        IF v_firstCostDetail.m_costdetail_id IS NOT NULL THEN
            RAISE DEBUG 'v_firstCostDetail.m_costdetail_id is, % ', v_firstCostDetail.m_costdetail_id;

            IF (v_firstCostDetail.ischangingcosts = 'N') THEN
                v_next_costs_found := TRUE;
                v_next_costs_debugInfo := 'case 2.1: based on prev costs of last cost details (not changing costs)';
                v_next_currentcostprice := v_firstCostDetail.prev_currentcostprice;
                v_next_currentcostpricell := v_firstCostDetail.prev_currentcostpricell;
                v_next_currentqty := v_firstCostDetail.prev_currentqty;
                v_next_cumulatedamt := v_firstCostDetail.prev_cumulatedamt;
                v_next_cumulatedqty := v_firstCostDetail.prev_cumulatedqty;
            ELSIF (v_firstCostDetail.qty = 0) THEN
                v_next_costs_found := TRUE;
                v_next_costs_debugInfo := 'case 2.2: based on prev costs of last cost details (qty is zero)';
                v_next_currentcostprice := v_firstCostDetail.prev_currentcostprice;
                v_next_currentcostpricell := v_firstCostDetail.prev_currentcostpricell;
                v_next_currentqty := v_firstCostDetail.prev_currentqty;
                v_next_cumulatedamt := v_firstCostDetail.prev_cumulatedamt;
                v_next_cumulatedqty := v_firstCostDetail.prev_cumulatedqty;
            ELSIF (v_firstCostDetail.qty < 0) THEN
                v_next_costs_found := TRUE;
                v_next_costs_debugInfo := 'case 2.3: based on prev costs of last cost details (outbound trx)';
                v_next_currentcostprice := v_firstCostDetail.prev_currentcostprice;
                v_next_currentcostpricell := v_firstCostDetail.prev_currentcostpricell;
                v_next_currentqty := v_firstCostDetail.prev_currentqty + v_firstCostDetail.qty;
                IF (v_next_currentqty < 0) THEN
                    v_next_currentqty := 0;
                END IF;
                v_next_cumulatedamt := v_firstCostDetail.prev_cumulatedamt + v_firstCostDetail.amt;
                v_next_cumulatedqty := v_firstCostDetail.prev_cumulatedqty + v_firstCostDetail.qty;
            ELSIF (v_firstCostDetail.qty > 0 AND v_firstCostDetail.ischangingcosts = 'Y' AND v_firstCostDetail.m_inventoryline_id IS NOT NULL) THEN
                v_next_costs_found := TRUE;
                v_next_costs_debugInfo := 'case 2.4: based on prev costs of last cost details (inbound trx), with changing costs';
                v_next_currentcostprice := v_firstCostDetail.prev_currentcostprice;
                v_next_currentcostpricell := v_firstCostDetail.prev_currentcostpricell;
                v_next_currentqty := v_firstCostDetail.prev_currentqty + v_firstCostDetail.qty;
                v_next_cumulatedamt := v_firstCostDetail.prev_cumulatedamt + v_firstCostDetail.amt;
                v_next_cumulatedqty := v_firstCostDetail.prev_cumulatedqty + v_firstCostDetail.qty;
            ELSE -- (v_firstCostDetail.qty > 0) THEN
                RAISE EXCEPTION 'Extracting current costs from an inbound transaction is not implemented (%)', v_firstCostDetail;
            END IF;
        ELSE
            v_next_costs_found := FALSE;
            v_next_costs_debugInfo := 'no last cost detail before target date found';
        END IF;
    END IF;


    RAISE DEBUG 'v_next_costs_debugInfo , % ', v_next_costs_debugInfo;
    RAISE DEBUG 'v_next_currentcostprice , % ', v_next_currentcostprice;
    RAISE DEBUG 'v_next_currentcostpricell , % ', v_next_currentcostpricell;
    RAISE DEBUG 'v_next_currentqty , % ', v_next_currentqty;
    RAISE DEBUG 'v_next_cumulatedamt , % ', v_next_cumulatedamt;
    RAISE DEBUG 'v_next_cumulatedqty , % ', v_next_cumulatedqty;

    --
    --
    -- DELETE ALL COST details starting FROM our target DATE (inclusive)
    --
    --

    DELETE
    FROM m_costdetail
    WHERE m_costdetail_id IN (SELECT m_costdetail_id
                              FROM m_costdetail_v cd
                              WHERE cd.c_acctschema_id = p_C_AcctSchema_ID
                                AND cd.m_costelement_id = p_M_CostElement_ID
                                AND cd.M_Product_ID = p_M_Product_ID
                                AND cd.ad_client_id = 1000000
                                AND (p_AD_Org_ID IS NULL OR p_AD_Org_ID <= 0 OR cd.ad_org_id = p_AD_Org_ID)
                                AND cd.dateacct >= p_StartDateAcct);
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE DEBUG 'Deleted % M_CostDetail(s)', rowcount;

    --
    --
    -- UPDATE /DELETE CURRENT COSTS RECORD (M_Cost)
    --
    --

    IF (v_next_costs_found) THEN
        SELECT c.m_cost_id
        INTO v_m_cost_id
        FROM m_cost c
        WHERE c.c_acctschema_id = p_C_AcctSchema_ID
          AND c.m_costelement_id = p_M_CostElement_ID
          AND c.M_Product_ID = p_M_Product_ID
          AND c.ad_client_id = 1000000
          AND (p_AD_Org_ID IS NULL OR p_AD_Org_ID <= 0 OR c.ad_org_id = p_AD_Org_ID);
        --
        IF (v_m_cost_id IS NULL) THEN
            RAISE EXCEPTION 'No M_Cost record found';
        END IF;

        UPDATE m_cost c
        SET currentcostprice=v_next_currentcostprice,
            currentcostpricell=v_next_currentcostpricell,
            currentqty=v_next_currentqty,
            cumulatedamt=v_next_cumulatedamt,
            cumulatedqty=v_next_cumulatedqty
        WHERE c.m_cost_id = v_m_cost_id;
        RAISE DEBUG 'Updated M_Cost with M_Cost_ID=% as follows', v_m_cost_id;
        RAISE DEBUG 'CurrentCostPrice = %', v_next_currentcostprice;
        RAISE DEBUG 'CurrentCostPriceLL = %', v_next_currentcostpricell;
        RAISE DEBUG 'CurrentQty = %', v_next_currentqty;
        RAISE DEBUG 'CumulatedAmt = %', v_next_cumulatedamt;
        RAISE DEBUG 'CumulatedQty = %', v_next_cumulatedqty;
        RAISE DEBUG 'Details: %', v_next_costs_debugInfo;
    ELSE
        DELETE
        FROM m_cost c
        WHERE c.c_acctschema_id = p_C_AcctSchema_ID
          AND c.m_costelement_id = p_M_CostElement_Id
          AND c.M_Product_ID = p_M_Product_ID
          AND c.ad_client_id = 1000000
          AND (p_AD_Org_ID IS NULL OR p_AD_Org_ID <= 0 OR c.ad_org_id = p_AD_Org_ID);
        GET DIAGNOSTICS rowcount = ROW_COUNT;
        RAISE DEBUG 'Deleted % M_Cost(s) because: %', rowcount, v_next_costs_debugInfo;
    END IF;

    --
    --
    -- ROLLBACK if dry run
    --
    --

    IF (p_DryRun = 'Y') THEN
        RAISE EXCEPTION 'ROLLBACK because p_DryRun=Y';
    END IF;

    RETURN 'OK';
END;
$BODY$

