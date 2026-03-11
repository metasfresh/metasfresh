-- Function: de_metas_material.MD_Candidate_Rebuild
-- Purpose: Rebuild all MD_Candidate records from source documents (open orders, schedules, etc.)
-- Approach: Three-pass per business case (UPDATE existing / INSERT missing / DELETE orphans) + rebuild STOCK timeline
-- See: https://github.com/metasfresh/me03/issues/28598

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Rebuild(numeric, character);
CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Rebuild(
    p_AD_PInstance_ID numeric,
    p_IsBackupBeforeDelete character DEFAULT 'Y'
)
RETURNS TABLE (
    action text,
    business_case text,
    record_count bigint
) AS
$BODY$
DECLARE
    v_now timestamp with time zone := now();
    v_suffix text := '_pinstance_' || p_AD_PInstance_ID::text;
    v_count bigint;
BEGIN
    -- ============================================================
    -- STEP 1: Backup (if enabled)
    -- ============================================================
    IF p_IsBackupBeforeDelete = 'Y' THEN
        PERFORM backup_table('md_candidate', v_suffix);
        PERFORM backup_table('md_candidate_demand_detail', v_suffix);
        PERFORM backup_table('md_candidate_purchase_detail', v_suffix);
        PERFORM backup_table('md_candidate_prod_detail', v_suffix);
        PERFORM backup_table('md_candidate_dist_detail', v_suffix);
        PERFORM backup_table('md_candidate_stockchange_detail', v_suffix);
        PERFORM backup_table('md_candidate_transaction_detail', v_suffix);
    END IF;

    -- ============================================================
    -- STEP 2: SHIPMENT business case (DEMAND candidates)
    -- Match key: MD_Candidate_Demand_Detail.M_ShipmentSchedule_ID
    -- Source: M_ShipmentSchedule (open, active)
    -- ============================================================

    -- Pass A: UPDATE existing SHIPMENT candidates from open shipment schedules
    UPDATE md_candidate c
    SET qty             = ss.qtyordered - ss.qtydelivered,
        dateprojected   = COALESCE(ss.deliverydate, ss.preparationdate, o.dateordered, v_now),
        m_warehouse_id  = ss.m_warehouse_id,
        storageattributeskey = COALESCE(GenerateASIStorageAttributesKey(ss.m_attributesetinstance_id), ''),
        m_attributesetinstance_id = COALESCE(ss.m_attributesetinstance_id, 0),
        m_shipmentschedule_id = ss.m_shipmentschedule_id,
        c_orderso_id    = o.c_order_id,
        updated         = v_now,
        updatedby       = 0
    FROM md_candidate_demand_detail dd
    JOIN m_shipmentschedule ss ON ss.m_shipmentschedule_id = dd.m_shipmentschedule_id
    JOIN c_orderline ol ON ol.c_orderline_id = ss.c_orderline_id
    JOIN c_order o ON o.c_order_id = ol.c_order_id
    WHERE dd.md_candidate_id = c.md_candidate_id
      AND dd.isactive = 'Y'
      AND c.md_candidate_businesscase = 'SHIPMENT'
      AND c.md_candidate_type = 'DEMAND'
      AND ss.processed = 'N'
      AND ss.isactive = 'Y';

    -- Also update the demand detail PlannedQty
    UPDATE md_candidate_demand_detail dd
    SET plannedqty      = ss.qtyordered,
        actualqty       = ss.qtydelivered,
        c_orderline_id  = ss.c_orderline_id,
        updated         = v_now,
        updatedby       = 0
    FROM m_shipmentschedule ss
    WHERE dd.m_shipmentschedule_id = ss.m_shipmentschedule_id
      AND dd.isactive = 'Y'
      AND ss.processed = 'N'
      AND ss.isactive = 'Y'
      AND EXISTS (SELECT 1 FROM md_candidate c
                  WHERE c.md_candidate_id = dd.md_candidate_id
                    AND c.md_candidate_businesscase = 'SHIPMENT'
                    AND c.md_candidate_type = 'DEMAND');

    -- Pass B: INSERT missing SHIPMENT candidates (open schedules with no MD_Candidate)
    WITH new_candidates AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            m_shipmentschedule_id, c_orderso_id,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            ss.ad_client_id, ss.ad_org_id, v_now, 0, v_now, 0, 'Y',
            'DEMAND', 'SHIPMENT', 'doc_created',
            ss.m_product_id, ss.m_warehouse_id,
            COALESCE(ss.deliverydate, ss.preparationdate, o.dateordered, v_now),
            ss.qtyordered - ss.qtydelivered,
            COALESCE(GenerateASIStorageAttributesKey(ss.m_attributesetinstance_id), ''),
            COALESCE(ss.m_attributesetinstance_id, 0),
            0, -- seqno
            ss.m_shipmentschedule_id, o.c_order_id,
            0, 0, 'N'
        FROM m_shipmentschedule ss
        JOIN c_orderline ol ON ol.c_orderline_id = ss.c_orderline_id
        JOIN c_order o ON o.c_order_id = ol.c_order_id
        WHERE ss.processed = 'N'
          AND ss.isactive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM md_candidate_demand_detail dd
              JOIN md_candidate c2 ON c2.md_candidate_id = dd.md_candidate_id
              WHERE dd.m_shipmentschedule_id = ss.m_shipmentschedule_id
                AND dd.isactive = 'Y'
                AND c2.md_candidate_businesscase = 'SHIPMENT'
          )
        RETURNING md_candidate_id, ad_client_id, ad_org_id, m_shipmentschedule_id
    )
    INSERT INTO md_candidate_demand_detail (
        md_candidate_demand_detail_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
        md_candidate_id, m_shipmentschedule_id, c_orderline_id, plannedqty, actualqty
    )
    SELECT
        nextval('md_candidate_demand_detail_seq'),
        nc.ad_client_id, nc.ad_org_id, v_now, 0, v_now, 0, 'Y',
        nc.md_candidate_id, nc.m_shipmentschedule_id,
        ss.c_orderline_id, ss.qtyordered, ss.qtydelivered
    FROM new_candidates nc
    JOIN m_shipmentschedule ss ON ss.m_shipmentschedule_id = nc.m_shipmentschedule_id;

    -- Pass C: DELETE orphan SHIPMENT candidates (shipment schedule closed/processed/deleted)
    -- First delete detail records, then parent
    DELETE FROM md_candidate_demand_detail dd
    WHERE dd.isactive = 'Y'
      AND dd.m_shipmentschedule_id IS NOT NULL
      AND EXISTS (
          SELECT 1 FROM md_candidate c
          WHERE c.md_candidate_id = dd.md_candidate_id
            AND c.md_candidate_businesscase = 'SHIPMENT'
            AND c.md_candidate_type = 'DEMAND'
      )
      AND NOT EXISTS (
          SELECT 1 FROM m_shipmentschedule ss
          WHERE ss.m_shipmentschedule_id = dd.m_shipmentschedule_id
            AND ss.processed = 'N'
            AND ss.isactive = 'Y'
      );

    -- Delete orphan SHIPMENT candidates that have no demand detail (detail was deleted above)
    DELETE FROM md_candidate c
    WHERE c.md_candidate_businesscase = 'SHIPMENT'
      AND c.md_candidate_type = 'DEMAND'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_demand_detail dd
          WHERE dd.md_candidate_id = c.md_candidate_id AND dd.isactive = 'Y'
      );

    -- ============================================================
    -- STEP 3: PURCHASE business case (SUPPLY candidates)
    -- Match key: MD_Candidate_Purchase_Detail.M_ReceiptSchedule_ID
    -- Source: M_ReceiptSchedule (open, active)
    -- ============================================================

    -- Pass A: UPDATE existing PURCHASE candidates from open receipt schedules
    UPDATE md_candidate c
    SET qty             = rs.qtyordered - COALESCE(rs.qtymoved, 0),
        dateprojected   = COALESCE(o.datepromised, o.dateordered, v_now),
        m_warehouse_id  = rs.m_warehouse_id,
        storageattributeskey = COALESCE(GenerateASIStorageAttributesKey(rs.m_attributesetinstance_id), ''),
        m_attributesetinstance_id = COALESCE(rs.m_attributesetinstance_id, 0),
        updated         = v_now,
        updatedby       = 0
    FROM md_candidate_purchase_detail pd
    JOIN m_receiptschedule rs ON rs.m_receiptschedule_id = pd.m_receiptschedule_id
    JOIN c_orderline ol ON ol.c_orderline_id = rs.c_orderline_id
    JOIN c_order o ON o.c_order_id = ol.c_order_id
    WHERE pd.md_candidate_id = c.md_candidate_id
      AND c.md_candidate_businesscase = 'PURCHASE'
      AND c.md_candidate_type = 'SUPPLY'
      AND rs.processed = 'N'
      AND rs.isactive = 'Y';

    -- Also update the purchase detail
    UPDATE md_candidate_purchase_detail pd
    SET plannedqty          = rs.qtyordered,
        qtyordered          = rs.qtyordered,
        c_orderlinepo_id    = rs.c_orderline_id,
        c_bpartner_vendor_id = ol.c_bpartner_id,
        updated             = v_now,
        updatedby           = 0
    FROM m_receiptschedule rs
    JOIN c_orderline ol ON ol.c_orderline_id = rs.c_orderline_id
    WHERE pd.m_receiptschedule_id = rs.m_receiptschedule_id
      AND rs.processed = 'N'
      AND rs.isactive = 'Y'
      AND EXISTS (SELECT 1 FROM md_candidate c
                  WHERE c.md_candidate_id = pd.md_candidate_id
                    AND c.md_candidate_businesscase = 'PURCHASE'
                    AND c.md_candidate_type = 'SUPPLY');

    -- Pass B: INSERT missing PURCHASE candidates
    WITH new_candidates AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            rs.ad_client_id, rs.ad_org_id, v_now, 0, v_now, 0, 'Y',
            'SUPPLY', 'PURCHASE', 'doc_created',
            rs.m_product_id, rs.m_warehouse_id,
            COALESCE(o.datepromised, o.dateordered, v_now),
            rs.qtyordered - COALESCE(rs.qtymoved, 0),
            COALESCE(GenerateASIStorageAttributesKey(rs.m_attributesetinstance_id), ''),
            COALESCE(rs.m_attributesetinstance_id, 0),
            0, -- seqno
            0, 0, 'N'
        FROM m_receiptschedule rs
        JOIN c_orderline ol ON ol.c_orderline_id = rs.c_orderline_id
        JOIN c_order o ON o.c_order_id = ol.c_order_id
        WHERE rs.processed = 'N'
          AND rs.isactive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM md_candidate_purchase_detail pd
              JOIN md_candidate c2 ON c2.md_candidate_id = pd.md_candidate_id
              WHERE pd.m_receiptschedule_id = rs.m_receiptschedule_id
                AND c2.md_candidate_businesscase = 'PURCHASE'
          )
        RETURNING md_candidate_id, ad_client_id, ad_org_id,
                  (SELECT rs2.m_receiptschedule_id FROM m_receiptschedule rs2
                   JOIN c_orderline ol2 ON ol2.c_orderline_id = rs2.c_orderline_id
                   WHERE rs2.m_product_id = md_candidate.m_product_id
                     AND rs2.m_warehouse_id = md_candidate.m_warehouse_id
                     AND rs2.processed = 'N' AND rs2.isactive = 'Y'
                   LIMIT 1) AS m_receiptschedule_id
    )
    INSERT INTO md_candidate_purchase_detail (
        md_candidate_purchase_detail_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
        md_candidate_id, m_receiptschedule_id, c_orderlinepo_id, plannedqty, c_bpartner_vendor_id, isadvised
    )
    SELECT
        nextval('md_candidate_purchase_detail_seq'),
        nc.ad_client_id, nc.ad_org_id, v_now, 0, v_now, 0, 'Y',
        nc.md_candidate_id, nc.m_receiptschedule_id,
        rs.c_orderline_id, rs.qtyordered, ol.c_bpartner_id, 'N'
    FROM new_candidates nc
    JOIN m_receiptschedule rs ON rs.m_receiptschedule_id = nc.m_receiptschedule_id
    JOIN c_orderline ol ON ol.c_orderline_id = rs.c_orderline_id;

    -- Pass C: DELETE orphan PURCHASE candidates
    DELETE FROM md_candidate_purchase_detail pd
    WHERE pd.m_receiptschedule_id IS NOT NULL
      AND EXISTS (
          SELECT 1 FROM md_candidate c
          WHERE c.md_candidate_id = pd.md_candidate_id
            AND c.md_candidate_businesscase = 'PURCHASE'
            AND c.md_candidate_type = 'SUPPLY'
      )
      AND NOT EXISTS (
          SELECT 1 FROM m_receiptschedule rs
          WHERE rs.m_receiptschedule_id = pd.m_receiptschedule_id
            AND rs.processed = 'N'
            AND rs.isactive = 'Y'
      );

    DELETE FROM md_candidate c
    WHERE c.md_candidate_businesscase = 'PURCHASE'
      AND c.md_candidate_type = 'SUPPLY'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_purchase_detail pd
          WHERE pd.md_candidate_id = c.md_candidate_id
      );

    -- ============================================================
    -- STEP 4: PRODUCTION business case
    -- Header: PP_Order → SUPPLY candidate (finished product)
    -- BOM Lines: PP_Order_BOMLine → DEMAND candidates (components)
    -- Match key: MD_Candidate_Prod_Detail.PP_Order_ID + PP_Order_BOMLine_ID
    -- ============================================================

    -- Pass A: UPDATE existing PRODUCTION header candidates (SUPPLY, finished product)
    UPDATE md_candidate c
    SET qty             = ppo.qtyordered - ppo.qtydelivered,
        dateprojected   = COALESCE(ppo.datepromised, ppo.dateordered, v_now),
        m_warehouse_id  = ppo.m_warehouse_id,
        storageattributeskey = COALESCE(GenerateASIStorageAttributesKey(ppo.m_attributesetinstance_id), ''),
        m_attributesetinstance_id = COALESCE(ppo.m_attributesetinstance_id, 0),
        updated         = v_now,
        updatedby       = 0
    FROM md_candidate_prod_detail ppd
    JOIN pp_order ppo ON ppo.pp_order_id = ppd.pp_order_id
    WHERE ppd.md_candidate_id = c.md_candidate_id
      AND ppd.pp_order_bomline_id IS NULL  -- header (no BOM line)
      AND c.md_candidate_businesscase = 'PRODUCTION'
      AND c.md_candidate_type = 'SUPPLY'
      AND ppo.docstatus IN ('DR', 'IP', 'CO')
      AND ppo.isactive = 'Y';

    -- Update prod detail for headers
    UPDATE md_candidate_prod_detail ppd
    SET pp_order_docstatus = ppo.docstatus,
        plannedqty         = ppo.qtyordered,
        actualqty          = ppo.qtydelivered,
        pp_plant_id        = ppo.s_resource_id,
        updated            = v_now,
        updatedby          = 0
    FROM pp_order ppo
    WHERE ppd.pp_order_id = ppo.pp_order_id
      AND ppd.pp_order_bomline_id IS NULL
      AND ppo.docstatus IN ('DR', 'IP', 'CO')
      AND ppo.isactive = 'Y'
      AND EXISTS (SELECT 1 FROM md_candidate c
                  WHERE c.md_candidate_id = ppd.md_candidate_id
                    AND c.md_candidate_businesscase = 'PRODUCTION'
                    AND c.md_candidate_type = 'SUPPLY');

    -- Pass A: UPDATE existing PRODUCTION BOM line candidates (DEMAND, components)
    UPDATE md_candidate c
    SET qty             = CASE
                              WHEN bl.componenttype IN ('CO', 'PK') THEN bl.qtyrequiered - COALESCE(bl.qtydelivered, 0)
                              ELSE bl.qtyrequiered - COALESCE(bl.qtydelivered, 0)
                          END,
        dateprojected   = CASE
                              WHEN bl.componenttype IN ('BY', 'CP') THEN COALESCE(ppo.datepromised, ppo.dateordered, v_now)
                              ELSE COALESCE(ppo.datestartschedule, ppo.dateordered, v_now)
                          END,
        m_warehouse_id  = COALESCE(bl.m_warehouse_id, ppo.m_warehouse_id),
        storageattributeskey = COALESCE(GenerateASIStorageAttributesKey(bl.m_attributesetinstance_id), ''),
        m_attributesetinstance_id = COALESCE(bl.m_attributesetinstance_id, 0),
        updated         = v_now,
        updatedby       = 0
    FROM md_candidate_prod_detail ppd
    JOIN pp_order_bomline bl ON bl.pp_order_bomline_id = ppd.pp_order_bomline_id
    JOIN pp_order ppo ON ppo.pp_order_id = bl.pp_order_id
    WHERE ppd.md_candidate_id = c.md_candidate_id
      AND ppd.pp_order_bomline_id IS NOT NULL  -- BOM line
      AND c.md_candidate_businesscase = 'PRODUCTION'
      AND ppo.docstatus IN ('DR', 'IP', 'CO')
      AND ppo.isactive = 'Y';

    -- Update prod detail for BOM lines
    UPDATE md_candidate_prod_detail ppd
    SET pp_order_docstatus = ppo.docstatus,
        plannedqty         = bl.qtyrequiered,
        actualqty          = COALESCE(bl.qtydelivered, 0),
        pp_plant_id        = ppo.s_resource_id,
        updated            = v_now,
        updatedby          = 0
    FROM pp_order_bomline bl
    JOIN pp_order ppo ON ppo.pp_order_id = bl.pp_order_id
    WHERE ppd.pp_order_bomline_id = bl.pp_order_bomline_id
      AND ppd.pp_order_bomline_id IS NOT NULL
      AND ppo.docstatus IN ('DR', 'IP', 'CO')
      AND ppo.isactive = 'Y'
      AND EXISTS (SELECT 1 FROM md_candidate c
                  WHERE c.md_candidate_id = ppd.md_candidate_id
                    AND c.md_candidate_businesscase = 'PRODUCTION');

    -- Pass B: INSERT missing PRODUCTION header candidates (SUPPLY for finished product)
    -- Use CTE with RETURNING to capture the new MD_Candidate_ID for GroupId assignment
    WITH new_headers AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            ppo.ad_client_id, ppo.ad_org_id, v_now, 0, v_now, 0, 'Y',
            'SUPPLY', 'PRODUCTION', 'doc_created',
            ppo.m_product_id, ppo.m_warehouse_id,
            COALESCE(ppo.datepromised, ppo.dateordered, v_now),
            ppo.qtyordered - ppo.qtydelivered,
            COALESCE(GenerateASIStorageAttributesKey(ppo.m_attributesetinstance_id), ''),
            COALESCE(ppo.m_attributesetinstance_id, 0),
            0, 0, 0, 'N'
        FROM pp_order ppo
        WHERE ppo.docstatus IN ('DR', 'IP', 'CO')
          AND ppo.isactive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM md_candidate_prod_detail ppd
              JOIN md_candidate c2 ON c2.md_candidate_id = ppd.md_candidate_id
              WHERE ppd.pp_order_id = ppo.pp_order_id
                AND ppd.pp_order_bomline_id IS NULL
                AND c2.md_candidate_businesscase = 'PRODUCTION'
          )
        RETURNING md_candidate_id, ad_client_id, ad_org_id, m_product_id
    ),
    -- Set GroupId to the header candidate's own ID
    group_update AS (
        UPDATE md_candidate c
        SET md_candidate_groupid = c.md_candidate_id
        FROM new_headers nh
        WHERE c.md_candidate_id = nh.md_candidate_id
        RETURNING c.md_candidate_id, c.md_candidate_groupid
    )
    -- Insert prod detail for new headers
    INSERT INTO md_candidate_prod_detail (
        md_candidate_prod_detail_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
        md_candidate_id, pp_order_id, pp_order_bomline_id, pp_product_bomline_id,
        pp_plant_id, pp_order_docstatus, plannedqty, actualqty, isadvised, ispickdirectlyiffeasible
    )
    SELECT
        nextval('md_candidate_prod_detail_seq'),
        nh.ad_client_id, nh.ad_org_id, v_now, 0, v_now, 0, 'Y',
        nh.md_candidate_id, ppo.pp_order_id, NULL, 0, -- NULL bomline = header, 0 = header indicator
        ppo.s_resource_id, ppo.docstatus, ppo.qtyordered, ppo.qtydelivered, 'N', 'N'
    FROM new_headers nh
    JOIN pp_order ppo ON ppo.m_product_id = nh.m_product_id
      AND ppo.docstatus IN ('DR', 'IP', 'CO')
      AND ppo.isactive = 'Y'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_prod_detail ppd2
          WHERE ppd2.pp_order_id = ppo.pp_order_id AND ppd2.pp_order_bomline_id IS NULL
      );

    -- Pass B: INSERT missing PRODUCTION BOM line candidates (DEMAND for components)
    WITH new_bomlines AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            md_candidate_groupid,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            bl.ad_client_id, bl.ad_org_id, v_now, 0, v_now, 0, 'Y',
            -- ComponentType BY/CP = co-product/by-product → SUPPLY; otherwise CO/PK = component → DEMAND
            CASE WHEN bl.componenttype IN ('BY', 'CP') THEN 'SUPPLY' ELSE 'DEMAND' END,
            'PRODUCTION', 'doc_created',
            bl.m_product_id, COALESCE(bl.m_warehouse_id, ppo.m_warehouse_id),
            CASE
                WHEN bl.componenttype IN ('BY', 'CP') THEN COALESCE(ppo.datepromised, ppo.dateordered, v_now)
                ELSE COALESCE(ppo.datestartschedule, ppo.dateordered, v_now)
            END,
            bl.qtyrequiered - COALESCE(bl.qtydelivered, 0),
            COALESCE(GenerateASIStorageAttributesKey(bl.m_attributesetinstance_id), ''),
            COALESCE(bl.m_attributesetinstance_id, 0),
            0,
            -- GroupId: find the header candidate's ID for this PP_Order
            (SELECT c_hdr.md_candidate_id FROM md_candidate c_hdr
             JOIN md_candidate_prod_detail ppd_hdr ON ppd_hdr.md_candidate_id = c_hdr.md_candidate_id
             WHERE ppd_hdr.pp_order_id = ppo.pp_order_id
               AND ppd_hdr.pp_order_bomline_id IS NULL
               AND c_hdr.md_candidate_businesscase = 'PRODUCTION'
             LIMIT 1),
            0, 0, 'N'
        FROM pp_order_bomline bl
        JOIN pp_order ppo ON ppo.pp_order_id = bl.pp_order_id
        WHERE ppo.docstatus IN ('DR', 'IP', 'CO')
          AND ppo.isactive = 'Y'
          AND bl.isactive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM md_candidate_prod_detail ppd
              JOIN md_candidate c2 ON c2.md_candidate_id = ppd.md_candidate_id
              WHERE ppd.pp_order_bomline_id = bl.pp_order_bomline_id
                AND c2.md_candidate_businesscase = 'PRODUCTION'
          )
        RETURNING md_candidate_id, ad_client_id, ad_org_id, m_product_id, m_warehouse_id
    )
    INSERT INTO md_candidate_prod_detail (
        md_candidate_prod_detail_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
        md_candidate_id, pp_order_id, pp_order_bomline_id, pp_product_bomline_id,
        pp_plant_id, pp_order_docstatus, plannedqty, actualqty, isadvised, ispickdirectlyiffeasible
    )
    SELECT
        nextval('md_candidate_prod_detail_seq'),
        nb.ad_client_id, nb.ad_org_id, v_now, 0, v_now, 0, 'Y',
        nb.md_candidate_id, bl.pp_order_id, bl.pp_order_bomline_id, bl.pp_product_bomline_id,
        ppo.s_resource_id, ppo.docstatus, bl.qtyrequiered, COALESCE(bl.qtydelivered, 0), 'N', 'N'
    FROM new_bomlines nb
    JOIN pp_order_bomline bl ON bl.m_product_id = nb.m_product_id
    JOIN pp_order ppo ON ppo.pp_order_id = bl.pp_order_id
      AND ppo.docstatus IN ('DR', 'IP', 'CO')
      AND ppo.isactive = 'Y'
      AND bl.isactive = 'Y'
      AND COALESCE(bl.m_warehouse_id, ppo.m_warehouse_id) = nb.m_warehouse_id
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_prod_detail ppd2
          WHERE ppd2.pp_order_bomline_id = bl.pp_order_bomline_id
      );

    -- Pass C: DELETE orphan PRODUCTION candidates (PP_Order closed/voided)
    DELETE FROM md_candidate_prod_detail ppd
    WHERE ppd.pp_order_id IS NOT NULL
      AND EXISTS (
          SELECT 1 FROM md_candidate c
          WHERE c.md_candidate_id = ppd.md_candidate_id
            AND c.md_candidate_businesscase = 'PRODUCTION'
      )
      AND NOT EXISTS (
          SELECT 1 FROM pp_order ppo
          WHERE ppo.pp_order_id = ppd.pp_order_id
            AND ppo.docstatus IN ('DR', 'IP', 'CO')
            AND ppo.isactive = 'Y'
      );

    -- Delete orphan BOM line prod details where the BOM line no longer exists
    DELETE FROM md_candidate_prod_detail ppd
    WHERE ppd.pp_order_bomline_id IS NOT NULL
      AND NOT EXISTS (
          SELECT 1 FROM pp_order_bomline bl
          WHERE bl.pp_order_bomline_id = ppd.pp_order_bomline_id
            AND bl.isactive = 'Y'
      );

    -- Delete PRODUCTION candidates that have no prod detail
    DELETE FROM md_candidate c
    WHERE c.md_candidate_businesscase = 'PRODUCTION'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_prod_detail ppd
          WHERE ppd.md_candidate_id = c.md_candidate_id
      );

    -- ============================================================
    -- STEP 5: DISTRIBUTION business case
    -- Each DD_OrderLine creates 2 candidates:
    --   DEMAND at source warehouse (M_Warehouse_From_ID)
    --   SUPPLY at destination warehouse (M_Warehouse_To_ID)
    -- Match key: MD_Candidate_Dist_Detail.DD_OrderLine_ID + warehouse
    -- ============================================================

    -- Pass A: UPDATE existing DISTRIBUTION DEMAND candidates (source warehouse)
    UPDATE md_candidate c
    SET qty             = ddl.qtyordered - COALESCE(ddl.qtydelivered, 0),
        dateprojected   = COALESCE(ddl.datepromised, ddo.datepromised, v_now)
                          - COALESCE(
                              (SELECT make_interval(days => ndl.transferttime::int)
                               FROM dd_networkdistributionline ndl
                               WHERE ndl.dd_networkdistributionline_id = dist.dd_networkdistributionline_id),
                              interval '0 days'),
        m_warehouse_id  = ddo.m_warehouse_from_id,
        storageattributeskey = COALESCE(GenerateASIStorageAttributesKey(ddl.m_attributesetinstance_id), ''),
        m_attributesetinstance_id = COALESCE(ddl.m_attributesetinstance_id, 0),
        updated         = v_now,
        updatedby       = 0
    FROM md_candidate_dist_detail dist
    JOIN dd_orderline ddl ON ddl.dd_orderline_id = dist.dd_orderline_id
    JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
    WHERE dist.md_candidate_id = c.md_candidate_id
      AND c.md_candidate_businesscase = 'DISTRIBUTION'
      AND c.md_candidate_type = 'DEMAND'
      AND c.m_warehouse_id = ddo.m_warehouse_from_id  -- identify DEMAND by warehouse
      AND ddo.docstatus IN ('DR', 'IP', 'CO')
      AND ddo.isactive = 'Y';

    -- Pass A: UPDATE existing DISTRIBUTION SUPPLY candidates (destination warehouse)
    UPDATE md_candidate c
    SET qty             = ddl.qtyordered - COALESCE(ddl.qtydelivered, 0),
        dateprojected   = COALESCE(ddl.datepromised, ddo.datepromised, v_now),
        m_warehouse_id  = ddo.m_warehouse_to_id,
        storageattributeskey = COALESCE(GenerateASIStorageAttributesKey(ddl.m_attributesetinstance_id), ''),
        m_attributesetinstance_id = COALESCE(ddl.m_attributesetinstance_id, 0),
        updated         = v_now,
        updatedby       = 0
    FROM md_candidate_dist_detail dist
    JOIN dd_orderline ddl ON ddl.dd_orderline_id = dist.dd_orderline_id
    JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
    WHERE dist.md_candidate_id = c.md_candidate_id
      AND c.md_candidate_businesscase = 'DISTRIBUTION'
      AND c.md_candidate_type = 'SUPPLY'
      AND c.m_warehouse_id = ddo.m_warehouse_to_id  -- identify SUPPLY by warehouse
      AND ddo.docstatus IN ('DR', 'IP', 'CO')
      AND ddo.isactive = 'Y';

    -- Update dist detail records
    UPDATE md_candidate_dist_detail dist
    SET dd_order_docstatus = ddo.docstatus,
        plannedqty         = ddl.qtyordered,
        actualqty          = COALESCE(ddl.qtydelivered, 0),
        updated            = v_now,
        updatedby          = 0
    FROM dd_orderline ddl
    JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
    WHERE dist.dd_orderline_id = ddl.dd_orderline_id
      AND ddo.docstatus IN ('DR', 'IP', 'CO')
      AND ddo.isactive = 'Y'
      AND EXISTS (SELECT 1 FROM md_candidate c
                  WHERE c.md_candidate_id = dist.md_candidate_id
                    AND c.md_candidate_businesscase = 'DISTRIBUTION');

    -- Pass B: INSERT missing DISTRIBUTION candidates
    -- For each open DD_OrderLine missing a DEMAND candidate, create DEMAND + SUPPLY pair
    WITH new_demand AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            ddl.ad_client_id, ddl.ad_org_id, v_now, 0, v_now, 0, 'Y',
            'DEMAND', 'DISTRIBUTION', 'doc_created',
            ddl.m_product_id, ddo.m_warehouse_from_id,
            COALESCE(ddl.datepromised, ddo.datepromised, v_now)
                - COALESCE(
                    (SELECT make_interval(days => ndl.transferttime::int)
                     FROM dd_networkdistributionline ndl
                     WHERE ndl.dd_networkdistributionline_id = (
                         SELECT dd2.dd_networkdistributionline_id FROM md_candidate_dist_detail dd2
                         WHERE dd2.dd_orderline_id = ddl.dd_orderline_id LIMIT 1)),
                    interval '0 days'),
            ddl.qtyordered - COALESCE(ddl.qtydelivered, 0),
            COALESCE(GenerateASIStorageAttributesKey(ddl.m_attributesetinstance_id), ''),
            COALESCE(ddl.m_attributesetinstance_id, 0),
            0, 0, 0, 'N'
        FROM dd_orderline ddl
        JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
        WHERE ddo.docstatus IN ('DR', 'IP', 'CO')
          AND ddo.isactive = 'Y'
          AND ddl.isactive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM md_candidate_dist_detail dist
              JOIN md_candidate c2 ON c2.md_candidate_id = dist.md_candidate_id
              WHERE dist.dd_orderline_id = ddl.dd_orderline_id
                AND c2.md_candidate_businesscase = 'DISTRIBUTION'
                AND c2.md_candidate_type = 'DEMAND'
          )
        RETURNING md_candidate_id, ad_client_id, ad_org_id, m_product_id
    ),
    new_demand_detail AS (
        INSERT INTO md_candidate_dist_detail (
            md_candidate_dist_detail_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_id, dd_order_id, dd_orderline_id, dd_order_docstatus,
            plannedqty, actualqty, isadvised, ispickdirectlyiffeasible
        )
        SELECT
            nextval('md_candidate_dist_detail_seq'),
            nd.ad_client_id, nd.ad_org_id, v_now, 0, v_now, 0, 'Y',
            nd.md_candidate_id, ddo.dd_order_id, ddl.dd_orderline_id, ddo.docstatus,
            ddl.qtyordered, COALESCE(ddl.qtydelivered, 0), 'N', 'N'
        FROM new_demand nd
        JOIN dd_orderline ddl ON ddl.m_product_id = nd.m_product_id
        JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
          AND ddo.docstatus IN ('DR', 'IP', 'CO')
          AND ddo.isactive = 'Y'
          AND ddl.isactive = 'Y'
        -- Only for lines that were just inserted (no existing detail)
        WHERE NOT EXISTS (
            SELECT 1 FROM md_candidate_dist_detail dist2
            WHERE dist2.dd_orderline_id = ddl.dd_orderline_id
              AND dist2.md_candidate_id != nd.md_candidate_id
              AND EXISTS (SELECT 1 FROM md_candidate c3
                          WHERE c3.md_candidate_id = dist2.md_candidate_id
                            AND c3.md_candidate_type = 'DEMAND')
        )
        RETURNING md_candidate_id
    ),
    -- Now insert SUPPLY candidates for the same DD_OrderLines (destination warehouse)
    new_supply AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            ddl.ad_client_id, ddl.ad_org_id, v_now, 0, v_now, 0, 'Y',
            'SUPPLY', 'DISTRIBUTION', 'doc_created',
            ddl.m_product_id, ddo.m_warehouse_to_id,
            COALESCE(ddl.datepromised, ddo.datepromised, v_now),
            ddl.qtyordered - COALESCE(ddl.qtydelivered, 0),
            COALESCE(GenerateASIStorageAttributesKey(ddl.m_attributesetinstance_id), ''),
            COALESCE(ddl.m_attributesetinstance_id, 0),
            0, 0, 0, 'N'
        FROM dd_orderline ddl
        JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
        WHERE ddo.docstatus IN ('DR', 'IP', 'CO')
          AND ddo.isactive = 'Y'
          AND ddl.isactive = 'Y'
          AND NOT EXISTS (
              SELECT 1 FROM md_candidate_dist_detail dist
              JOIN md_candidate c2 ON c2.md_candidate_id = dist.md_candidate_id
              WHERE dist.dd_orderline_id = ddl.dd_orderline_id
                AND c2.md_candidate_businesscase = 'DISTRIBUTION'
                AND c2.md_candidate_type = 'SUPPLY'
          )
        RETURNING md_candidate_id, ad_client_id, ad_org_id, m_product_id
    )
    INSERT INTO md_candidate_dist_detail (
        md_candidate_dist_detail_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
        md_candidate_id, dd_order_id, dd_orderline_id, dd_order_docstatus,
        plannedqty, actualqty, isadvised, ispickdirectlyiffeasible
    )
    SELECT
        nextval('md_candidate_dist_detail_seq'),
        ns.ad_client_id, ns.ad_org_id, v_now, 0, v_now, 0, 'Y',
        ns.md_candidate_id, ddo.dd_order_id, ddl.dd_orderline_id, ddo.docstatus,
        ddl.qtyordered, COALESCE(ddl.qtydelivered, 0), 'N', 'N'
    FROM new_supply ns
    JOIN dd_orderline ddl ON ddl.m_product_id = ns.m_product_id
    JOIN dd_order ddo ON ddo.dd_order_id = ddl.dd_order_id
      AND ddo.docstatus IN ('DR', 'IP', 'CO')
      AND ddo.isactive = 'Y'
      AND ddl.isactive = 'Y'
    WHERE NOT EXISTS (
        SELECT 1 FROM md_candidate_dist_detail dist2
        WHERE dist2.dd_orderline_id = ddl.dd_orderline_id
          AND dist2.md_candidate_id != ns.md_candidate_id
          AND EXISTS (SELECT 1 FROM md_candidate c3
                      WHERE c3.md_candidate_id = dist2.md_candidate_id
                        AND c3.md_candidate_type = 'SUPPLY')
    );

    -- Pass C: DELETE orphan DISTRIBUTION candidates
    DELETE FROM md_candidate_dist_detail dist
    WHERE dist.dd_order_id IS NOT NULL
      AND EXISTS (
          SELECT 1 FROM md_candidate c
          WHERE c.md_candidate_id = dist.md_candidate_id
            AND c.md_candidate_businesscase = 'DISTRIBUTION'
      )
      AND NOT EXISTS (
          SELECT 1 FROM dd_order ddo
          WHERE ddo.dd_order_id = dist.dd_order_id
            AND ddo.docstatus IN ('DR', 'IP', 'CO')
            AND ddo.isactive = 'Y'
      );

    DELETE FROM md_candidate c
    WHERE c.md_candidate_businesscase = 'DISTRIBUTION'
      AND NOT EXISTS (
          SELECT 1 FROM md_candidate_dist_detail dist
          WHERE dist.md_candidate_id = c.md_candidate_id
      );

    -- ============================================================
    -- STEP 6: Delete non-business-case candidates
    -- These represent historical adjustments already captured in MD_Stock
    -- ============================================================

    -- Delete StockChange details first
    DELETE FROM md_candidate_stockchange_detail scd
    WHERE EXISTS (
        SELECT 1 FROM md_candidate c
        WHERE c.md_candidate_id = scd.md_candidate_id
          AND c.md_candidate_type IN (
              'STOCK_UP', 'UNEXPECTED_INCREASE', 'UNEXPECTED_DECREASE',
              'INVENTORY_UP', 'INVENTORY_DOWN',
              'ATTRIBUTES_CHANGED_FROM', 'ATTRIBUTES_CHANGED_TO'
          )
    );

    -- Delete forecast demand details
    DELETE FROM md_candidate_demand_detail dd
    WHERE dd.m_forecastline_id IS NOT NULL;

    -- Delete the non-business-case candidates themselves
    DELETE FROM md_candidate c
    WHERE c.md_candidate_type IN (
        'STOCK_UP', 'UNEXPECTED_INCREASE', 'UNEXPECTED_DECREASE',
        'INVENTORY_UP', 'INVENTORY_DOWN',
        'ATTRIBUTES_CHANGED_FROM', 'ATTRIBUTES_CHANGED_TO'
    );

    -- Delete FORECAST candidates (md_candidate_businesscase is null but linked via m_forecast_id or demand_detail.m_forecastline_id)
    DELETE FROM md_candidate_demand_detail dd
    WHERE EXISTS (
        SELECT 1 FROM md_candidate c
        WHERE c.md_candidate_id = dd.md_candidate_id
          AND c.m_forecast_id IS NOT NULL
          AND c.md_candidate_businesscase IS NULL
    );

    DELETE FROM md_candidate c
    WHERE c.m_forecast_id IS NOT NULL
      AND c.md_candidate_businesscase IS NULL
      AND c.md_candidate_type != 'STOCK';

    -- ============================================================
    -- STEP 7: Delete orphan transaction details for candidates that were deleted
    -- (FK is deferred, so we can clean up now)
    -- ============================================================
    DELETE FROM md_candidate_transaction_detail td
    WHERE NOT EXISTS (
        SELECT 1 FROM md_candidate c WHERE c.md_candidate_id = td.md_candidate_id
    );

    -- ============================================================
    -- STEP 8: Rebuild STOCK timeline
    -- Delete ALL existing STOCK candidates, then create new ones based on
    -- MD_Stock.QtyOnHand (baseline) + running sum of DEMAND/SUPPLY deltas
    -- ============================================================

    -- First, clear parent_id references FROM stock candidates
    UPDATE md_candidate SET md_candidate_parent_id = NULL
    WHERE md_candidate_type = 'STOCK';

    -- Clear parent_id references TO stock candidates (SUPPLY candidates point to STOCK as parent)
    UPDATE md_candidate c SET md_candidate_parent_id = NULL
    WHERE md_candidate_parent_id IS NOT NULL
      AND EXISTS (
          SELECT 1 FROM md_candidate s
          WHERE s.md_candidate_id = c.md_candidate_parent_id
            AND s.md_candidate_type = 'STOCK'
      );

    -- Delete all STOCK candidates
    DELETE FROM md_candidate WHERE md_candidate_type = 'STOCK';

    -- Rebuild STOCK candidates: one per DEMAND/SUPPLY candidate, showing cumulative ATP
    -- Plus one baseline per (product, warehouse, attributesKey) from MD_Stock
    WITH
    -- Collect all demand/supply events with their stock delta
    events AS (
        SELECT
            c.md_candidate_id,
            c.ad_client_id,
            c.ad_org_id,
            c.m_product_id,
            c.m_warehouse_id,
            c.storageattributeskey,
            c.dateprojected,
            c.seqno,
            c.md_candidate_type,
            CASE
                WHEN c.md_candidate_type = 'DEMAND' THEN -c.qty
                WHEN c.md_candidate_type = 'SUPPLY' THEN c.qty
                ELSE 0
            END AS stock_delta
        FROM md_candidate c
        WHERE c.isactive = 'Y'
          AND c.md_candidate_type IN ('DEMAND', 'SUPPLY')
    ),
    -- Get baseline stock per product/warehouse/attributes
    baselines AS (
        SELECT
            ms.m_product_id,
            ms.m_warehouse_id,
            ms.attributeskey AS storageattributeskey,
            COALESCE(ms.qtyonhand, 0) AS qtyonhand
        FROM md_stock ms
        WHERE ms.isactive = 'Y'
    ),
    -- Compute running sum for each event
    stock_timeline AS (
        SELECT
            e.md_candidate_id,
            e.ad_client_id,
            e.ad_org_id,
            e.m_product_id,
            e.m_warehouse_id,
            e.storageattributeskey,
            e.dateprojected,
            e.seqno,
            e.md_candidate_type,
            COALESCE(b.qtyonhand, 0) + SUM(e.stock_delta) OVER (
                PARTITION BY e.m_product_id, e.m_warehouse_id, e.storageattributeskey
                ORDER BY e.dateprojected, e.seqno
                ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
            ) AS cumulative_qty
        FROM events e
        LEFT JOIN baselines b ON b.m_product_id = e.m_product_id
            AND b.m_warehouse_id = e.m_warehouse_id
            AND b.storageattributeskey = e.storageattributeskey
    ),
    -- Insert STOCK candidates
    new_stock AS (
        INSERT INTO md_candidate (
            md_candidate_id, ad_client_id, ad_org_id, created, createdby, updated, updatedby, isactive,
            md_candidate_type, md_candidate_businesscase, md_candidate_status,
            m_product_id, m_warehouse_id, dateprojected, qty,
            storageattributeskey, m_attributesetinstance_id, seqno,
            replenish_minqty, replenish_maxqty, isreservedforcustomer
        )
        SELECT
            nextval('md_candidate_seq'),
            st.ad_client_id, st.ad_org_id, v_now, 0, v_now, 0, 'Y',
            'STOCK', NULL, NULL,
            st.m_product_id, st.m_warehouse_id, st.dateprojected, st.cumulative_qty,
            st.storageattributeskey, 0, st.seqno,
            0, 0, 'N'
        FROM stock_timeline st
        RETURNING md_candidate_id, m_product_id, m_warehouse_id, storageattributeskey, dateprojected, seqno
    )
    -- Consume the CTE result (PL/pgSQL requires a destination for SELECT)
    SELECT count(*) INTO v_count FROM new_stock;

    -- Link STOCK → DEMAND: set STOCK.MD_Candidate_Parent_ID = DEMAND candidate
    UPDATE md_candidate stock
    SET md_candidate_parent_id = demand.md_candidate_id
    FROM md_candidate demand
    WHERE demand.md_candidate_type IN ('DEMAND')
      AND demand.isactive = 'Y'
      AND stock.md_candidate_type = 'STOCK'
      AND stock.m_product_id = demand.m_product_id
      AND stock.m_warehouse_id = demand.m_warehouse_id
      AND stock.storageattributeskey = demand.storageattributeskey
      AND stock.dateprojected = demand.dateprojected
      AND stock.seqno = demand.seqno;

    -- Link SUPPLY → STOCK: set SUPPLY.MD_Candidate_Parent_ID = STOCK candidate
    UPDATE md_candidate supply
    SET md_candidate_parent_id = stock.md_candidate_id
    FROM md_candidate stock
    WHERE stock.md_candidate_type = 'STOCK'
      AND stock.isactive = 'Y'
      AND supply.md_candidate_type = 'SUPPLY'
      AND supply.isactive = 'Y'
      AND stock.m_product_id = supply.m_product_id
      AND stock.m_warehouse_id = supply.m_warehouse_id
      AND stock.storageattributeskey = supply.storageattributeskey
      AND stock.dateprojected = supply.dateprojected
      AND stock.seqno = supply.seqno;

    -- ============================================================
    -- STEP 9: Return summary statistics
    -- ============================================================
    RETURN QUERY
    SELECT 'RESULT'::text AS action,
           c.md_candidate_type || '/' || COALESCE(c.md_candidate_businesscase, '-') AS business_case,
           count(*)::bigint AS record_count
    FROM md_candidate c
    WHERE c.isactive = 'Y'
    GROUP BY c.md_candidate_type, c.md_candidate_businesscase
    ORDER BY c.md_candidate_type, c.md_candidate_businesscase;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Rebuild(numeric, character) IS
'Rebuilds all MD_Candidate records from source documents.
Three-pass approach per business case: UPDATE existing, INSERT missing, DELETE orphans.
Then rebuilds STOCK timeline from MD_Stock.QtyOnHand + running sum of demand/supply deltas.
Preserves MD_Candidate_Transaction_Detail on updated candidates.
See: https://github.com/metasfresh/me03/issues/28598';
