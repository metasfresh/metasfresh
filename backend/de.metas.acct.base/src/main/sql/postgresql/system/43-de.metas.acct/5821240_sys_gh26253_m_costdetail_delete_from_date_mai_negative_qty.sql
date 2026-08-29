-- Source DDL: backend/de.metas.acct.base/src/main/sql/postgresql/ddl/functions/m_costdetail_delete_from_date.sql
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
    --
    v_anchor_m_product_id     numeric;
    v_anchor_dateacct         date;
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
                -- SQL FOLLOWS JAVA. This rewind must reproduce what the posting layer would have left in
                -- M_Cost: the recompute deletes the cost details from the target date on, and the costing
                -- handlers then replay forward starting from exactly this value.
                --   MovingAverageInvoice ('M') is the ONE method whose handlers carry a negative CurrentQty
                --     through an over-issue (de.metas.costing.CurrentCost.addToCurrentQtyAndCumulateAllowingNegative,
                --     used only by the MovingAverageInvoice handlers), so the clamp is skipped for it.
                --   Every other method still clamps at zero on an over-issue
                --     (de.metas.costing.CurrentCost.addToCurrentQtyAndCumulate), so the clamp stays for them.
                -- CONCRETE FAILURE SCENARIO THIS PREVENTS: clamping for 'M' rewinds M_Cost to a floored
                -- starting quantity while the forward replay is unfloored, so the replay treats quantity that
                -- no document ever created as real on-hand. That phantom quantity is then the WEIGHT in the
                -- weighted average (CurrentCost.addWeightedAverage derives the running amount from
                -- price * CurrentQty), which makes the cost price sticky and drives CurrentCostPrice*CurrentQty
                -- away from what is booked on the product-asset account.
                -- A NULL costingmethod (unresolvable cost element) makes the comparison NULL, and
                -- IS DISTINCT FROM yields TRUE there, so it keeps the historical clamping behaviour.
                IF (v_next_currentqty < 0 AND v_costingmethod IS DISTINCT FROM 'M') THEN
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
                --        -> INVARIANT: every Standard-costing handler posts amt = currentCostPrice*qty by
                --           construction - it multiplies the CURRENT cost price of the very same cost
                --           segment+element by the quantity and never derives a price from the document:
                --             StandardCostingMethodHandler.createCostForMaterialReceipt /
                --                 .createOutboundCostDefaultImpl / .createCostForMatchInvoice_MaterialCosts
                --             ManufacturingStandardCostingMethodHandler.createIssueOrReceipt /
                --                 .createActivityControl / .createUsageVariance
                --           Substituting amt = prevPrice*qty into the weighted average gives
                --           (prevPrice*prevQty + prevPrice*qty) / (prevQty + qty) = prevPrice, so the
                --           reconstruction provably reproduces prevPrice for EVERY such inbound: it is a
                --           no-op, not an approximation.
                --   'p' LastPOPrice / 'i' LastInvoice are DELIBERATELY EXCLUDED: on the M_MatchPO /
                --        M_MatchInv path their handlers REPLACE the price with amt/qty
                --        (LastPOCostingMethodHandler.createCostForMatchPO,
                --        LastInvoiceCostingMethodHandler.createCostForMatchInvoice_MaterialCosts), so averaging
                --        would silently write a WRONG CurrentCostPrice. Example: prevPrice=20, prevQty=10,
                --        inbound qty=10 amt=400 -> replace gives 40, averaging gives 600/20 = 30.
                --        CAVEAT - for 'p' and 'i' the price movement depends on the DOCUMENT, not on the method
                --        alone, so a method-only rule cannot model them. Observed variants, all reachable with
                --        qty>0 + ischangingcosts='Y' + no inventory line (i.e. all matching this branch's shape):
                --          - REPLACE with amt/qty                     (base handler, M_MatchPO / M_MatchInv)
                --          - weighted average that IS a no-op         (ManufacturingLastPOCostingMethodHandler
                --                                                      .createMainProductOrCoProductReceipt:
                --                                                      amt = currentCostPrice*qty)
                --          - weighted average that is NOT a no-op     (same class, .createComponentIssue reversal:
                --                                                      amt/qty is the price at the ORIGINAL issue)
                --          - price left UNTOUCHED, qty only           (createOutboundCostDefaultImpl reversal ->
                --                                                      CurrentCost.addToCurrentQtyAndCumulate)
                --        Treat that list as EXAMPLES, never as exhaustive - it has already been wrong twice.
                --        So do NOT read any single no-op variant as licence to add 'p' or 'i' to the gate.
                --        Excluding them is safe for a reason that does NOT depend on enumerating the variants:
                --        the ONLY fallback is the loud RAISE below, never a silently wrong CurrentCostPrice.
                --        Known residual gap: a 'p' / 'i' element whose last pre-range detail has this shape
                --        therefore still RAISEs instead of being reconstructed. Closing it needs a
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
    -- REFUSE when the delete range contains a COST-REVALUATION OPENING ANCHOR
    --
    --
    -- M_CostDetail.M_CostRevaluationLine_ID is a nullable FK set ONLY on a cost detail written by a
    -- completed M_CostRevaluation line (de.metas.costing.impl.CostDetailRepository#updateRecordFromDocumentRef);
    -- it is NULL on every other detail. For the CopyFromCostElement switch that detail is the single
    -- zero-delta OPENING ANCHOR dated AT the cut-off
    -- (de.metas.costing.impl.CostingService#seedCurrentCostFromOpening).
    --
    -- CONCRETE FAILURE SCENARIO THIS PREVENTS: December 2025 is re-opened for year-end adjustments and
    -- someone recomputes a product from a December document, i.e. from a date at or before the 31.12.2025
    -- cut-off. The anchor then lies inside the DELETE below and is destroyed - while EVERYTHING STILL LOOKS
    -- CORRECT, which is exactly what makes it dangerous: the anchor is ischangingcosts='Y' and its prev_*
    -- ARE the seeded opening (seedCurrentCostFromOpening writes the same opening into M_Cost and into the
    -- anchor's prev_*), so Approach 1 above picks the anchor as v_firstCostDetail, v_next_costs_found stays
    -- TRUE and M_Cost is UPDATEd back to the very same numbers. M_Cost therefore SURVIVES unchanged at the
    -- correct price - nothing visibly breaks - and the real damage is the silent loss of the anchor ROW: the
    -- M_CostRevaluationLine survives with NO corresponding M_CostDetail, which destroys
    --   (a) the reversal basis - CostingService#reverseSeededCurrentCost undoes the switch by deleting that
    --       very anchor, so reversing the switch afterwards has nothing left to reverse;
    --   (b) the audit / reconstruction record of WHAT the opening balance was and WHERE it came from -
    --       no other row carries it; and
    --   (c) the double-seed guard - CostRevaluationService#createDetails skips an already-seeded product by
    --       exactly this "a detail with M_CostRevaluationLine_ID IS NOT NULL exists" signal
    --       (CostDetailRepository#retrieveProductIdsWithCostRevaluationSeed), so once the anchor is gone a
    --       re-run of the switch no longer skips the product and re-seeds an already-in-use cost.
    -- Refusing costs nothing: the recompute is re-runnable with a start date strictly after the cut-off,
    -- which is the only correct range anyway - the opening is a given, not something a recompute may replay.
    --
    -- Same range predicate as the DELETE below, so the guard fires exactly when the DELETE would hit an anchor.
    SELECT cd.m_product_id, cd.dateacct::date
    INTO v_anchor_m_product_id, v_anchor_dateacct
    FROM m_costdetail_v cd
    WHERE cd.c_acctschema_id = p_C_AcctSchema_ID
      AND cd.m_costelement_id = p_M_CostElement_ID
      AND cd.M_Product_ID = p_M_Product_ID
      AND cd.ad_client_id = 1000000
      AND (p_AD_Org_ID IS NULL OR p_AD_Org_ID <= 0 OR cd.ad_org_id = p_AD_Org_ID)
      AND cd.dateacct >= p_StartDateAcct
      AND cd.m_costrevaluationline_id IS NOT NULL
    ORDER BY cd.dateacct, cd.m_costdetail_id
    LIMIT 1;
    --
    IF (v_anchor_dateacct IS NOT NULL) THEN
        RAISE EXCEPTION 'Cost recompute refused for M_Product_ID=%: the range starting at % contains the cost-revaluation opening anchor M_CostDetail dated %. Deleting that anchor would destroy the only record of the opening balance and the basis for reversing the cost revaluation. Restart the recompute with a start date strictly after %.',
            v_anchor_m_product_id, p_StartDateAcct::date, v_anchor_dateacct, v_anchor_dateacct;
    END IF;

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

