-- Transactional SQL test for view MD_Stock_PerWeek_V (me03 25618 / F19100).
-- Run:  docker exec -i deep_tundra_uat_db psql -U metasfresh metasfresh -f - < this_file
--   (or pipe the file's contents). The whole script is wrapped BEGIN; ... ROLLBACK;
--   so it leaves the shared deep_tundra_uat DB untouched.
--
-- Fixtures key on an existing-but-candidate-free product/warehouse pair
--   product   = 100      (active, no MD_Candidate rows in seed)
--   warehouse = 540005   (active, no MD_Candidate rows in seed)
-- so the view's `pw` CTE surfaces ONLY rows inserted here for that pair, and the
-- FKs MD_Candidate->M_Product / M_Warehouse are satisfied (no deferred-FK reliance).
--
-- Cases covered (per Task 2 brief (a)-(e)):
--   (a) shipments  : DEMAND/SHIPMENT Qty=-5 in week +2 => QtyExpectedShipments(week+2)=5
--   (b) receipts   : SUPPLY/PURCHASE Qty=8  in week +1 => QtyExpectedReceipts(week+1)=8
--   (c) ATP incl. other stream: STOCK running balance per week + a PRODUCTION supply,
--                    asserting QtyATP(week)=latest STOCK cumulative, and ATP delta for the
--                    production week != receipts-shipments (proves ATP is full-dispo number).
--   (d) overdue rolling: DEMAND dated BEFORE current week rolls into current-week (offset 0)
--                    QtyExpectedShipments.
--   (e) attribute aggregation: two STOCK candidates same product+WH+date, different
--                    StorageAttributesKey => their qtys SUM into QtyATP.

BEGIN;

DO $$
DECLARE
  v_prod   numeric := 100;
  v_wh     numeric := 540005;
  v_cid    numeric;  -- AD_Client_ID
  v_w0     date := date_trunc('week', now())::date;        -- current week start (Mon)
  v_w1     date := date_trunc('week', now())::date + 7;    -- week +1
  v_w2     date := date_trunc('week', now())::date + 14;   -- week +2
  v_w3     date := date_trunc('week', now())::date + 21;   -- week +3
  v_id     numeric;
  v_got    numeric;
BEGIN
  -- use AD_Client_ID of product 100 so client-scoped joins (if any) stay consistent
  SELECT ad_client_id INTO v_cid FROM m_product WHERE m_product_id = v_prod;

  -- helper: next candidate id base
  SELECT COALESCE(MAX(md_candidate_id),0) + 1 INTO v_id FROM md_candidate;

  -- ---- insert candidates -------------------------------------------------
  -- All rows: IsActive='Y', status 'planned' (NOT 'simulated' so they are visible).

  -- (a) shipment in week +2: DEMAND/SHIPMENT, Qty = -5 (demand stored negative)
  INSERT INTO md_candidate
    (md_candidate_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     dateprojected, m_product_id, m_warehouse_id, md_candidate_type, md_candidate_businesscase,
     md_candidate_status, qty, seqno, storageattributeskey)
  VALUES
    (v_id+1, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w2 + 2)::timestamptz, v_prod, v_wh, 'DEMAND', 'SHIPMENT', 'planned', -5, 10, '');

  -- (b) receipt in week +1: SUPPLY/PURCHASE, Qty = 8
  INSERT INTO md_candidate
    (md_candidate_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     dateprojected, m_product_id, m_warehouse_id, md_candidate_type, md_candidate_businesscase,
     md_candidate_status, qty, seqno, storageattributeskey)
  VALUES
    (v_id+2, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w1 + 2)::timestamptz, v_prod, v_wh, 'SUPPLY', 'PURCHASE', 'planned', 8, 10, '');

  -- (d) overdue shipment dated LAST week (before current week): DEMAND/SHIPMENT Qty=-3
  INSERT INTO md_candidate
    (md_candidate_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     dateprojected, m_product_id, m_warehouse_id, md_candidate_type, md_candidate_businesscase,
     md_candidate_status, qty, seqno, storageattributeskey)
  VALUES
    (v_id+3, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w0 - 3)::timestamptz, v_prod, v_wh, 'DEMAND', 'SHIPMENT', 'planned', -3, 10, '');

  -- (c) STOCK running balance candidates (cumulative projected stock per week-end).
  --     main subgroup ('') cumulative: week0->100, week1->108 (after +8 receipt),
  --     week2->103 (after -5 shipment), week3->123 (after +20 PRODUCTION supply).
  --     The 'attr2' subgroup (+5, week0) adds 5 to every week's ATP sum.
  INSERT INTO md_candidate
    (md_candidate_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     dateprojected, m_product_id, m_warehouse_id, md_candidate_type, md_candidate_businesscase,
     md_candidate_status, qty, seqno, storageattributeskey)
  VALUES
    (v_id+10, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w0 + 1)::timestamptz, v_prod, v_wh, 'STOCK', NULL, 'planned', 100, 100, ''),
    (v_id+11, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w1 + 2)::timestamptz, v_prod, v_wh, 'STOCK', NULL, 'planned', 108, 100, ''),
    (v_id+12, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w2 + 2)::timestamptz, v_prod, v_wh, 'STOCK', NULL, 'planned', 103, 100, ''),
    (v_id+13, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w3 + 2)::timestamptz, v_prod, v_wh, 'STOCK', NULL, 'planned', 123, 100, '');

  -- (c) PRODUCTION supply in week +3: SUPPLY/PRODUCTION Qty=20 (moves ATP but is neither
  --     QtyExpectedShipments nor QtyExpectedReceipts).
  INSERT INTO md_candidate
    (md_candidate_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     dateprojected, m_product_id, m_warehouse_id, md_candidate_type, md_candidate_businesscase,
     md_candidate_status, qty, seqno, storageattributeskey)
  VALUES
    (v_id+14, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w3 + 1)::timestamptz, v_prod, v_wh, 'SUPPLY', 'PRODUCTION', 'planned', 20, 10, '');

  -- (e) attribute aggregation: a SECOND STOCK candidate at week 0, same prod+WH+date,
  --     but a different StorageAttributesKey, Qty=5. Latest-per-subgroup then SUM => 100+5=105.
  INSERT INTO md_candidate
    (md_candidate_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     dateprojected, m_product_id, m_warehouse_id, md_candidate_type, md_candidate_businesscase,
     md_candidate_status, qty, seqno, storageattributeskey)
  VALUES
    (v_id+15, v_cid, 1000000, 'Y', now(), 100, now(), 100,
     (v_w0 + 1)::timestamptz, v_prod, v_wh, 'STOCK', NULL, 'planned', 5, 100, 'attr2');

  -- ====================== ASSERTIONS ======================================

  -- (a) shipments week +2 = 5
  SELECT qtyexpectedshipments INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w2;
  IF v_got IS DISTINCT FROM 5 THEN
    RAISE EXCEPTION '(a) QtyExpectedShipments week+2: expected 5, got %', v_got;
  END IF;

  -- (b) receipts week +1 = 8
  SELECT qtyexpectedreceipts INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w1;
  IF v_got IS DISTINCT FROM 8 THEN
    RAISE EXCEPTION '(b) QtyExpectedReceipts week+1: expected 8, got %', v_got;
  END IF;

  -- (d) overdue: the -3 shipment dated last week rolls into the CURRENT week's shipments.
  --     Current week has no other shipment, so QtyExpectedShipments(week0) = 3.
  SELECT qtyexpectedshipments INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w0;
  IF v_got IS DISTINCT FROM 3 THEN
    RAISE EXCEPTION '(d) overdue rolled QtyExpectedShipments week0: expected 3, got %', v_got;
  END IF;

  -- (e) ATP week 0 = 100 (subgroup '') + 5 (subgroup 'attr2') = 105
  SELECT qtyatp INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w0;
  IF v_got IS DISTINCT FROM 105 THEN
    RAISE EXCEPTION '(e) attr-aggregated QtyATP week0: expected 105, got %', v_got;
  END IF;

  -- (c) ATP cumulative per week-end = SUM over subgroups of each subgroup's latest STOCK.
  --     The 'attr2' subgroup (Qty=5, week0, never superseded) contributes +5 to EVERY week,
  --     so each week's ATP = main-subgroup cumulative + 5:
  --       week1 = 108 + 5 = 113 ; week2 = 103 + 5 = 108 ; week3 = 123 + 5 = 128.
  SELECT qtyatp INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w1;
  IF v_got IS DISTINCT FROM 113 THEN
    RAISE EXCEPTION '(c) QtyATP week+1: expected 113, got %', v_got;
  END IF;
  SELECT qtyatp INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w2;
  IF v_got IS DISTINCT FROM 108 THEN
    RAISE EXCEPTION '(c) QtyATP week+2: expected 108, got %', v_got;
  END IF;
  SELECT qtyatp INTO v_got
    FROM md_stock_perweek_v
   WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w3;
  IF v_got IS DISTINCT FROM 128 THEN
    RAISE EXCEPTION '(c) QtyATP week+3: expected 128, got %', v_got;
  END IF;

  -- (c) PROVE ATP is the full-dispo number for the production week:
  --     ATP delta week2->week3 = 123 - 103 = 20.
  --     receipts(week3) - shipments(week3) = 0 - 0 = 0  (PRODUCTION is neither stream).
  --     => ATP delta (20) != receipts-shipments (0).
  DECLARE
    v_atp2 numeric; v_atp3 numeric; v_rcp3 numeric; v_shp3 numeric;
  BEGIN
    SELECT qtyatp INTO v_atp2 FROM md_stock_perweek_v
      WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w2;
    SELECT qtyatp, qtyexpectedreceipts, qtyexpectedshipments
      INTO v_atp3, v_rcp3, v_shp3
      FROM md_stock_perweek_v
      WHERE m_product_id=v_prod AND m_warehouse_id=v_wh AND weekstartdate=v_w3;
    IF (v_atp3 - v_atp2) IS DISTINCT FROM 20 THEN
      RAISE EXCEPTION '(c) ATP delta week2->week3: expected 20, got %', (v_atp3 - v_atp2);
    END IF;
    IF (v_rcp3 - v_shp3) = (v_atp3 - v_atp2) THEN
      RAISE EXCEPTION '(c) ATP delta (%) must differ from receipts-shipments (%) for production week',
        (v_atp3 - v_atp2), (v_rcp3 - v_shp3);
    END IF;
  END;

  RAISE NOTICE 'OK — all of (a)-(e) passed';
END $$;

ROLLBACK;
