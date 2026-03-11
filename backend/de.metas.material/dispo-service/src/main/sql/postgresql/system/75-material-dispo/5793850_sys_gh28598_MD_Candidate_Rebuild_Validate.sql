-- Function: de_metas_material.MD_Candidate_Rebuild_Validate
-- Purpose: Compare MD_Candidate state after rebuild against source documents
-- Returns discrepancies: qty mismatches, missing candidates, orphan candidates, ATP drift
-- See: https://github.com/metasfresh/me03/issues/28598

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Rebuild_Validate();
CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Rebuild_Validate()
RETURNS TABLE (
    check_name text,
    severity text,  -- 'ERROR', 'WARNING', 'INFO'
    business_case text,
    detail text,
    expected_value text,
    actual_value text
) AS
$BODY$
BEGIN
    -- ============================================================
    -- CHECK 1: SHIPMENT qty mismatch
    -- Expected: MD_Candidate.Qty = ShipmentSchedule.QtyOrdered - QtyDelivered
    -- ============================================================
    RETURN QUERY
    SELECT
        'SHIPMENT_QTY_MISMATCH'::text,
        'ERROR'::text,
        'SHIPMENT'::text,
        format('MD_Candidate_ID=%s, M_ShipmentSchedule_ID=%s', c.md_candidate_id, dd.m_shipmentschedule_id),
        (ss.qtyordered - ss.qtydelivered)::text,
        c.qty::text
    FROM md_candidate c
    JOIN md_candidate_demand_detail dd ON dd.md_candidate_id = c.md_candidate_id AND dd.isactive = 'Y'
    JOIN m_shipmentschedule ss ON ss.m_shipmentschedule_id = dd.m_shipmentschedule_id
    WHERE c.md_candidate_businesscase = 'SHIPMENT'
      AND c.md_candidate_type = 'DEMAND'
      AND c.isactive = 'Y'
      AND c.qty != (ss.qtyordered - ss.qtydelivered);

    -- ============================================================
    -- CHECK 2: PURCHASE qty mismatch
    -- ============================================================
    RETURN QUERY
    SELECT
        'PURCHASE_QTY_MISMATCH'::text,
        'ERROR'::text,
        'PURCHASE'::text,
        format('MD_Candidate_ID=%s, M_ReceiptSchedule_ID=%s', c.md_candidate_id, pd.m_receiptschedule_id),
        (rs.qtyordered - COALESCE(rs.qtymoved, 0))::text,
        c.qty::text
    FROM md_candidate c
    JOIN md_candidate_purchase_detail pd ON pd.md_candidate_id = c.md_candidate_id
    JOIN m_receiptschedule rs ON rs.m_receiptschedule_id = pd.m_receiptschedule_id
    WHERE c.md_candidate_businesscase = 'PURCHASE'
      AND c.md_candidate_type = 'SUPPLY'
      AND c.isactive = 'Y'
      AND c.qty != (rs.qtyordered - COALESCE(rs.qtymoved, 0));

    -- ============================================================
    -- CHECK 3: PRODUCTION header qty mismatch
    -- ============================================================
    RETURN QUERY
    SELECT
        'PRODUCTION_HEADER_QTY_MISMATCH'::text,
        'ERROR'::text,
        'PRODUCTION'::text,
        format('MD_Candidate_ID=%s, PP_Order_ID=%s', c.md_candidate_id, ppd.pp_order_id),
        (ppo.qtyordered - ppo.qtydelivered)::text,
        c.qty::text
    FROM md_candidate c
    JOIN md_candidate_prod_detail ppd ON ppd.md_candidate_id = c.md_candidate_id
    JOIN pp_order ppo ON ppo.pp_order_id = ppd.pp_order_id
    WHERE c.md_candidate_businesscase = 'PRODUCTION'
      AND c.md_candidate_type = 'SUPPLY'
      AND ppd.pp_order_bomline_id IS NULL
      AND c.isactive = 'Y'
      AND c.qty != (ppo.qtyordered - ppo.qtydelivered);

    -- ============================================================
    -- CHECK 4: Missing SHIPMENT candidates (open schedule, no candidate)
    -- ============================================================
    RETURN QUERY
    SELECT
        'MISSING_SHIPMENT_CANDIDATE'::text,
        'ERROR'::text,
        'SHIPMENT'::text,
        format('M_ShipmentSchedule_ID=%s, Product=%s', ss.m_shipmentschedule_id, ss.m_product_id),
        'candidate should exist'::text,
        'no candidate found'::text
    FROM m_shipmentschedule ss
    WHERE ss.processed = 'N' AND ss.isactive = 'Y'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_demand_detail dd
          JOIN md_candidate c ON c.md_candidate_id = dd.md_candidate_id
          WHERE dd.m_shipmentschedule_id = ss.m_shipmentschedule_id
            AND dd.isactive = 'Y'
            AND c.md_candidate_businesscase = 'SHIPMENT'
      );

    -- ============================================================
    -- CHECK 5: Missing PURCHASE candidates
    -- ============================================================
    RETURN QUERY
    SELECT
        'MISSING_PURCHASE_CANDIDATE'::text,
        'ERROR'::text,
        'PURCHASE'::text,
        format('M_ReceiptSchedule_ID=%s, Product=%s', rs.m_receiptschedule_id, rs.m_product_id),
        'candidate should exist'::text,
        'no candidate found'::text
    FROM m_receiptschedule rs
    WHERE rs.processed = 'N' AND rs.isactive = 'Y'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_purchase_detail pd
          JOIN md_candidate c ON c.md_candidate_id = pd.md_candidate_id
          WHERE pd.m_receiptschedule_id = rs.m_receiptschedule_id
            AND c.md_candidate_businesscase = 'PURCHASE'
      );

    -- ============================================================
    -- CHECK 6: Missing PRODUCTION candidates (open PP_Order, no header candidate)
    -- ============================================================
    RETURN QUERY
    SELECT
        'MISSING_PRODUCTION_CANDIDATE'::text,
        'ERROR'::text,
        'PRODUCTION'::text,
        format('PP_Order_ID=%s, Product=%s', ppo.pp_order_id, ppo.m_product_id),
        'candidate should exist'::text,
        'no candidate found'::text
    FROM pp_order ppo
    WHERE ppo.docstatus IN ('DR', 'IP', 'CO') AND ppo.isactive = 'Y'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_prod_detail ppd
          JOIN md_candidate c ON c.md_candidate_id = ppd.md_candidate_id
          WHERE ppd.pp_order_id = ppo.pp_order_id
            AND ppd.pp_order_bomline_id IS NULL
            AND c.md_candidate_businesscase = 'PRODUCTION'
      );

    -- ============================================================
    -- CHECK 7: Orphan candidates (detail FK points to closed/deleted source doc)
    -- ============================================================
    RETURN QUERY
    SELECT
        'ORPHAN_SHIPMENT_CANDIDATE'::text,
        'WARNING'::text,
        'SHIPMENT'::text,
        format('MD_Candidate_ID=%s, M_ShipmentSchedule_ID=%s', c.md_candidate_id, dd.m_shipmentschedule_id),
        'should be deleted'::text,
        'still exists'::text
    FROM md_candidate c
    JOIN md_candidate_demand_detail dd ON dd.md_candidate_id = c.md_candidate_id AND dd.isactive = 'Y'
    WHERE c.md_candidate_businesscase = 'SHIPMENT' AND c.md_candidate_type = 'DEMAND'
      AND dd.m_shipmentschedule_id IS NOT NULL
      AND NOT EXISTS (
          SELECT 1 FROM m_shipmentschedule ss
          WHERE ss.m_shipmentschedule_id = dd.m_shipmentschedule_id
            AND ss.processed = 'N' AND ss.isactive = 'Y'
      );

    -- ============================================================
    -- CHECK 8: STOCK timeline consistency
    -- For each DEMAND/SUPPLY candidate, there should be a matching STOCK candidate
    -- ============================================================
    RETURN QUERY
    SELECT
        'MISSING_STOCK_CANDIDATE'::text,
        'ERROR'::text,
        COALESCE(c.md_candidate_businesscase, c.md_candidate_type)::text,
        format('MD_Candidate_ID=%s, Type=%s, Product=%s', c.md_candidate_id, c.md_candidate_type, c.m_product_id),
        'should have paired STOCK'::text,
        'no STOCK candidate found'::text
    FROM md_candidate c
    WHERE c.isactive = 'Y'
      AND c.md_candidate_type IN ('DEMAND', 'SUPPLY')
      -- Check: there should be a STOCK candidate with matching product/warehouse/attributes/date/seqno
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate s
          WHERE s.md_candidate_type = 'STOCK'
            AND s.isactive = 'Y'
            AND s.m_product_id = c.m_product_id
            AND s.m_warehouse_id = c.m_warehouse_id
            AND s.storageattributeskey = c.storageattributeskey
            AND s.dateprojected = c.dateprojected
            AND s.seqno = c.seqno
      );

    -- ============================================================
    -- CHECK 9: ATP sanity check per product/warehouse
    -- Compare ATP from STOCK timeline vs computed (MD_Stock.QtyOnHand + sum of open supply - demand)
    -- ============================================================
    RETURN QUERY
    WITH
    -- Latest STOCK candidate per product/warehouse/attributes
    latest_stock AS (
        SELECT DISTINCT ON (m_product_id, m_warehouse_id, storageattributeskey)
            m_product_id, m_warehouse_id, storageattributeskey, qty
        FROM md_candidate
        WHERE isactive = 'Y' AND md_candidate_type = 'STOCK'
        ORDER BY m_product_id, m_warehouse_id, storageattributeskey, dateprojected DESC, seqno DESC
    ),
    -- Computed ATP from source docs
    computed_atp AS (
        SELECT
            c.m_product_id, c.m_warehouse_id, c.storageattributeskey,
            COALESCE(ms.qtyonhand, 0)
                + COALESCE(SUM(CASE WHEN c.md_candidate_type = 'SUPPLY' THEN c.qty ELSE 0 END), 0)
                - COALESCE(SUM(CASE WHEN c.md_candidate_type = 'DEMAND' THEN c.qty ELSE 0 END), 0)
            AS expected_atp
        FROM md_candidate c
        LEFT JOIN md_stock ms ON ms.m_product_id = c.m_product_id
            AND ms.m_warehouse_id = c.m_warehouse_id
            AND ms.attributeskey = c.storageattributeskey
        WHERE c.isactive = 'Y' AND c.md_candidate_type IN ('DEMAND', 'SUPPLY')
        GROUP BY c.m_product_id, c.m_warehouse_id, c.storageattributeskey, ms.qtyonhand
    )
    SELECT
        'ATP_MISMATCH'::text,
        'ERROR'::text,
        'STOCK'::text,
        format('Product=%s, Warehouse=%s', ca.m_product_id, ca.m_warehouse_id),
        ca.expected_atp::text,
        COALESCE(ls.qty, 0)::text
    FROM computed_atp ca
    LEFT JOIN latest_stock ls ON ls.m_product_id = ca.m_product_id
        AND ls.m_warehouse_id = ca.m_warehouse_id
        AND ls.storageattributeskey = ca.storageattributeskey
    WHERE COALESCE(ls.qty, 0) != ca.expected_atp;

    -- ============================================================
    -- CHECK 10: TransactionDetail integrity
    -- Transaction details should only reference existing candidates
    -- ============================================================
    RETURN QUERY
    SELECT
        'ORPHAN_TRANSACTION_DETAIL'::text,
        'ERROR'::text,
        '-'::text,
        format('MD_Candidate_Transaction_Detail_ID=%s, references MD_Candidate_ID=%s', td.md_candidate_transaction_detail_id, td.md_candidate_id),
        'should reference existing candidate'::text,
        'candidate not found'::text
    FROM md_candidate_transaction_detail td
    WHERE td.isactive = 'Y'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate c WHERE c.md_candidate_id = td.md_candidate_id
      );

    -- ============================================================
    -- Summary: overall counts
    -- ============================================================
    RETURN QUERY
    SELECT
        'SUMMARY'::text,
        'INFO'::text,
        c.md_candidate_type || '/' || COALESCE(c.md_candidate_businesscase, '-'),
        'Total count'::text,
        ''::text,
        count(*)::text
    FROM md_candidate c
    WHERE c.isactive = 'Y'
    GROUP BY c.md_candidate_type, c.md_candidate_businesscase
    ORDER BY c.md_candidate_type, c.md_candidate_businesscase;
END;
$BODY$
LANGUAGE plpgsql STABLE;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Rebuild_Validate() IS
'Validates MD_Candidate data after a rebuild.
Checks: qty mismatches, missing candidates, orphans, STOCK timeline consistency, ATP sanity.
Returns all discrepancies as rows. Empty result = all clean.
See: https://github.com/metasfresh/me03/issues/28598';
