DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID  numeric,
    p_M_CostElement_ID numeric,
    p_M_Product_ID     numeric,
    p_M_Product_IDs    numeric[],
    p_ReorderDocs      char(1),
    p_StartDateAcct    timestamp WITH TIME ZONE)
;

DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric,
    p_M_Product_IDs              numeric[],
    p_ReorderDocs                char(1),
    p_ReorderDocs_DateAcct_Trunc varchar,
    p_StartDateAcct              timestamp WITH TIME ZONE)
;

DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric,
    p_M_Product_IDs              numeric[],
    p_m_product_selection_id     numeric,
    p_ReorderDocs                char(1),
    p_ReorderDocs_DateAcct_Trunc varchar,
    p_StartDateAcct              timestamp WITH TIME ZONE)
;


DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric,
    p_M_Product_IDs              numeric[],
    p_ReorderDocs                char(1),
    p_ReorderDocs_DateAcct_Trunc varchar,
    p_StartDateAcct              timestamp WITH TIME ZONE,
    p_DryRun           char(1))
;

DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric,
    p_M_Product_IDs              numeric[],
    p_m_product_selection_id     numeric,
    p_ReorderDocs                char(1),
    p_ReorderDocs_DateAcct_Trunc varchar,
    p_StartDateAcct              timestamp WITH TIME ZONE)
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


DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID  numeric,
    p_M_CostElement_ID numeric,
    p_M_Product_ID     numeric,
    p_M_Product_IDs    numeric[],
    p_ReorderDocs      char(1),
    p_StartDateAcct    timestamp WITH TIME ZONE)
;

DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric,
    p_M_Product_IDs              numeric[],
    p_ReorderDocs                char(1),
    p_ReorderDocs_DateAcct_Trunc varchar,
    p_StartDateAcct              timestamp WITH TIME ZONE)
;

DROP FUNCTION IF EXISTS "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric,
    p_M_Product_IDs              numeric[],
    p_m_product_selection_id     numeric,
    p_ReorderDocs                char(1),
    p_ReorderDocs_DateAcct_Trunc varchar,
    p_StartDateAcct              timestamp WITH TIME ZONE)
;


CREATE OR REPLACE FUNCTION "de_metas_acct".product_costs_recreate_from_date(
    p_C_AcctSchema_ID            numeric,
    p_M_CostElement_ID           numeric,
    p_M_Product_ID               numeric = NULL,
    p_M_Product_IDs              numeric[] = NULL,
    p_m_product_selection_id     numeric = NULL,
    p_ReorderDocs                char(1) = 'Y',
    p_ReorderDocs_DateAcct_Trunc varchar = 'DD',
    p_StartDateAcct              timestamp WITH TIME ZONE = '1970-01-01')
    RETURNS text
AS
$BODY$
DECLARE
    v_productIds      numeric[];
    v_costingLevel    char(1);
    v_orgIds          numeric[];
    --
    rowcount          integer := 0;
    v_result          text    := '';
    v_ok              text    := '';
    --
    v_currentProduct  record;
    v_currentOrgId    numeric;
    v_firstCostDetail m_costdetail_v%rowtype;
    v_record          record;
BEGIN

    --
    -- Validate parameter: Product
    --
    IF (p_m_product_selection_id IS NOT NULL AND p_m_product_selection_id > 0) THEN
        SELECT ARRAY_AGG(sel.T_Selection_ID ORDER BY sel.T_Selection_ID)
        INTO v_productIds
        FROM T_Selection sel
        WHERE sel.ad_pinstance_id = p_m_product_selection_id;
        RAISE NOTICE 'v_productIds: %', v_productIds;
    ELSEIF (p_M_Product_ID IS NOT NULL AND p_M_Product_ID > 0) THEN
        v_productIds := ARRAY [p_M_Product_ID];
        -- RAISE EXCEPTION 'Product shall be > 0 but it was %', p_M_Product_ID;
    ELSE
        v_productIds := p_M_Product_IDs;
    END IF;
    --
    IF (v_productIds IS NULL OR ARRAY_LENGTH(v_productIds, 1) <= 0) THEN
        RAISE EXCEPTION
            'No products provided. p_M_Product_ID(=%) or p_M_Product_IDs(=%) shall be set',
            p_M_Product_ID, p_M_Product_IDs;
    END IF;
    --
    v_result := v_result || ARRAY_LENGTH(v_productIds, 1) || ' products; ';
    RAISE NOTICE 'Products: %', v_productIds;

    --
    -- Validate parameter: Accounting Schema
    -- => extract eligible Orgs
    --
    SELECT cas.costinglevel
    INTO v_costingLevel
    FROM c_acctschema cas
    WHERE cas.c_acctschema_id = p_C_AcctSchema_ID;
    IF (v_costingLevel = 'C') THEN
        v_orgIds := ARRAY [ 0 ];
    ELSIF (v_costingLevel = 'O') THEN
        SELECT ARRAY_AGG(org.ad_org_id ORDER BY org.ad_org_id)
        INTO v_orgIds
        FROM ad_org org
        WHERE org.ad_client_id = 1000000
          AND org.ad_org_id != 0;
    ELSE
        RAISE EXCEPTION 'Costing level `%` not supported for C_AcctSchema_ID=%', v_costingLevel, p_C_AcctSchema_ID;
    END IF;
    RAISE NOTICE 'p_C_AcctSchema_ID=%: CostingLevel=%', p_C_AcctSchema_ID, v_costingLevel;
    RAISE NOTICE 'Orgs: %', v_orgIds;

    RAISE NOTICE 'p_M_CostElement_ID=%', p_M_CostElement_ID;
    RAISE NOTICE 'p_ReorderDocs=%', p_ReorderDocs;
    RAISE NOTICE 'p_StartDateAcct=%', p_StartDateAcct;


    --
    -- Build up a list with those documents which we have to un-post
    --
    DROP TABLE IF EXISTS TMP_documents_to_unpost;
    CREATE TEMPORARY TABLE TMP_documents_to_unpost AS
    SELECT DISTINCT tablename,
                    record_id,
                    reversal_id,
                    m_product_id,
                    dateacct,
                    tablename_prio,
                    docbasetype,
                    ad_client_id,
                    ad_org_id
    FROM "de_metas_acct".accountable_docs_and_lines_v
    WHERE m_product_id = ANY (v_productIds)
      AND dateacct >= p_StartDateAcct
      AND ad_client_id = 1000000;
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Selected % documents to be reposted', rowcount;

    --
    -- Make sure the accounting periods are open
    --
    FOR v_record IN (SELECT DISTINCT t.dateacct, t.docbasetype, t.ad_client_id, t.ad_org_id
                     FROM TMP_documents_to_unpost t
                     ORDER BY t.dateacct, t.docbasetype, t.ad_client_id, t.ad_org_id)
        LOOP
            PERFORM "de_metas_acct".assert_period_open(
                    p_DateAcct := v_record.dateacct,
                    p_DocBaseType := v_record.docbasetype,
                    p_AD_Client_ID := v_record.ad_client_id,
                    p_AD_Org_ID := v_record.ad_org_id);
        END LOOP;


    --
    -- Iterate each product and try to revert to cost/cost details before our start date.
    --
    FOR v_currentProduct IN (SELECT p.m_product_id,
                                    p.value || '_' || p.name || ' (ID=' || p.m_product_id || ')' AS productInfo
                             FROM m_product p
                             WHERE p.m_product_id = ANY (v_productIds)
                             ORDER BY p.m_product_id)
        LOOP
        --
        -- Iterate each costing org
        --
            FOREACH v_currentOrgId IN ARRAY v_orgIds
                LOOP
                    PERFORM de_metas_acct.m_costdetail_delete_from_date(
                            p_C_AcctSchema_ID := p_C_AcctSchema_ID,
                            p_M_CostElement_ID := p_M_CostElement_Id,
                            p_M_Product_ID := v_currentProduct.m_product_id,
                            p_AD_Org_ID := v_currentOrgId,
                            p_StartDateAcct := p_StartDateAcct,
                            p_DryRun := 'N'
                        );
                END LOOP;
        END LOOP;


    --
    -- Delete PP_Order_Cost records
    --
    DELETE
    FROM pp_order_cost poc
    WHERE EXISTS(SELECT 1
                 FROM pp_order o
                 WHERE o.pp_order_id = poc.pp_order_id
                   AND o.m_product_id = ANY (v_productIds)
                   AND o.dateordered >= p_StartDateAcct);
    GET DIAGNOSTICS rowcount = ROW_COUNT;
    RAISE NOTICE 'Deleted % PP_Order_Cost records', rowcount;
    v_result := v_result || rowcount || ' PP_Order_Cost(s) deleted; ';


    --
    -- Un-post documents and enqueue them to posting queue.
    --
    SELECT COUNT(1)
    INTO rowcount
    FROM (SELECT "de_metas_acct".fact_acct_unpost(
                         p_tablename := t.tablename,
                         p_record_id := t.record_id,
                         p_force := 'Y',
                         p_checkPeriodOpen := 'N' -- don't check it because we checked it above
                     )
          FROM TMP_documents_to_unpost t
          ORDER BY m_product_id,
                   dateacct,
                   tablename_prio,
                   LEAST(t.record_id, t.reversal_id),
                   t.record_id) t;
    RAISE NOTICE 'Un-posted % documents', rowcount;
    v_result := v_result || rowcount || ' document(s) un-posted; ';

    --
    -- Reorder the documents in the posting queue
    -- This step it's very important in order to get correct costs.
    --
    IF (p_ReorderDocs = 'Y') THEN
        PERFORM "de_metas_acct".accounting_docs_to_repost_reorder(
                p_DateAcct_Trunc := p_ReorderDocs_DateAcct_Trunc
            );
        v_result := v_result || 'reordered enqueued docs';
    END IF;


    --
    -- Return the summary text message
    --
    RAISE NOTICE 'DONE: %', v_result;
    RETURN v_result;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;
