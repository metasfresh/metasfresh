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
    v_costingmethod           char(1);
BEGIN
    RAISE DEBUG 'm_costdetail_delete_from_date: p_C_AcctSchema_ID=%, p_M_CostElement_ID=%, p_M_Product_ID=%, p_AD_Org_ID=%, p_StartDateAcct=%, p_DryRun=%',
        p_C_AcctSchema_ID, p_M_CostElement_ID, p_M_Product_ID, p_AD_Org_ID, p_StartDateAcct, p_DryRun;

    -- The cost element's costing method decides HOW a changing-costs inbound moves the current cost
    -- price, so case 2.5 below can only reconstruct that price for the methods that average (see there).
    SELECT ce.costingmethod
    INTO v_costingmethod
    FROM m_costelement ce
    WHERE ce.m_costelement_id = p_M_CostElement_ID;

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
            ELSIF (v_firstCostDetail.qty > 0 AND v_firstCostDetail.ischangingcosts = 'Y'
                AND v_costingmethod IN ('A', 'I', 'M', 'S')) THEN
                -- case 2.5: inbound trx (qty > 0) that changed the cost price but is NOT an inventory line
                -- (M_MatchPO, M_InOutLine non-material costs, PP_Cost_Collector receipts, ...).
                -- Unlike case 2.4 (inventory lines post at the unchanged current price), these inbounds
                -- recompute the current cost price via weighted average, mirroring
                -- de.metas.costing.CurrentCost.addWeightedAverage:
                --     newPrice = (prevPrice*prevQty + amt) / (prevQty + qty)   rounded to the currency costing precision.
                -- M_CostDetail stores no resulting price, so it must be recomputed here; copying case 2.4
                -- verbatim would leave M_Cost.CurrentCostPrice stale and mis-value the repost.
                -- NOTE: this reconstructs the price from the stored prev_* / amt / qty of the cost detail, exactly
                -- like the surrounding cases.
                --
                -- The costing-method gate is REQUIRED: only the averaging methods move the price this way.
                --   'A' AveragePO / 'I' AverageInvoice / 'M' MovingAverageInvoice
                --        -> their handlers call CurrentCost.addWeightedAverage, so the formula matches.
                --   'S' StandardCosting
                --        -> a changing-costs inbound records amt = prevPrice*qty
                --           (StandardCostingMethodHandler.createCostForMaterialReceipt), so the weighted
                --           average provably reproduces prevPrice: it is a no-op, not an approximation.
                --   'p' LastPOPrice / 'i' LastInvoice are DELIBERATELY EXCLUDED: on the M_MatchPO /
                --        M_MatchInv path their handlers REPLACE the price with amt/qty
                --        (LastPOCostingMethodHandler.createCostForMatchPO,
                --        LastInvoiceCostingMethodHandler.createCostForMatchInvoice_MaterialCosts), so averaging
                --        would silently write a WRONG CurrentCostPrice. Example: prevPrice=20, prevQty=10,
                --        inbound qty=10 amt=400 -> replace gives 40, averaging gives 600/20 = 30.
                --        CAVEAT - 'p' is NOT uniform: it has at least three reachable shapes, because the
                --        manufacturing handler (ManufacturingLastPOCostingMethodHandler) overrides the base one.
                --          (1) M_MatchPO                       -> REPLACE with amt/qty (base handler, above).
                --          (2) PP_Cost_Collector main/co-product receipt (createMainProductOrCoProductReceipt)
                --              -> addWeightedAverage, but with amt = currentCostPrice*qty, so a no-op.
                --          (3) PP_Cost_Collector component-issue REVERSAL (createComponentIssue, isReversal)
                --              -> addWeightedAverage on the reversal's own amt/qty. An issue is outbound, so its
                --                 reversal is qty>0 with ischangingcosts='Y' and no inventory line - it reaches
                --                 this branch shape - and amt/qty is the price at the ORIGINAL issue, which need
                --                 NOT equal the current price. That one is a GENUINE average, not a no-op.
                --        So do NOT read the (2) no-op as licence to add 'p' to the gate. Excluding 'p' wholesale
                --        is safe for a different and stronger reason: the ONLY fallback is the loud RAISE below,
                --        never a silently wrong CurrentCostPrice - which holds for every shape, including (3).
                --        Known residual gap: a 'p' element whose last pre-range detail is any PP_Cost_Collector
                --        row therefore still RAISEs instead of being reconstructed. Closing it needs a
                --        (method, document) key, not a method-only one - deliberately out of scope here.
                --   'L' Lifo / 'F' Fifo / 'U' UserDefined / 'x' ExternalProcessing are likewise excluded.
                -- Everything excluded falls through to the RAISE below - i.e. exactly the behaviour those
                -- methods already have today - so this change can never turn a loud abort into a silent
                -- mis-valuation.
                -- A NULL costingmethod (unresolvable cost element) makes the IN test NULL, not TRUE, so it
                -- also falls through to the RAISE rather than averaging blindly.
                v_next_costs_found := TRUE;
                v_next_costs_debugInfo := 'case 2.5: based on prev costs of last cost details (inbound trx, weighted average), with changing costs';
                v_next_currentqty   := v_firstCostDetail.prev_currentqty   + v_firstCostDetail.qty;
                v_next_cumulatedamt := v_firstCostDetail.prev_cumulatedamt + v_firstCostDetail.amt;
                v_next_cumulatedqty := v_firstCostDetail.prev_cumulatedqty + v_firstCostDetail.qty;
                IF (v_next_currentqty <> 0) THEN
                    v_next_currentcostprice := round(
                            (v_firstCostDetail.prev_currentcostprice * v_firstCostDetail.prev_currentqty
                                + v_firstCostDetail.amt) / v_next_currentqty,
                            (SELECT cur.costingprecision::int
                             FROM c_currency cur
                             WHERE cur.c_currency_id = v_firstCostDetail.c_currency_id));
                ELSE
                    v_next_currentcostprice := v_firstCostDetail.prev_currentcostprice;
                END IF;
                v_next_currentcostpricell := v_firstCostDetail.prev_currentcostpricell; -- LL untouched by addWeightedAverage
            ELSE -- (v_firstCostDetail.qty > 0), or a costing method case 2.5 must not average for
                RAISE EXCEPTION 'Extracting current costs from an inbound transaction is not implemented (costing method %) (%)', v_costingmethod, v_firstCostDetail;
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

