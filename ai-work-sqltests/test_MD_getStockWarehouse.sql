-- Transactional SQL test for function MD_getStockWarehouse (me03 25618 / F19100).
-- Run:  docker exec -i deep_tundra_uat_db psql -U metasfresh metasfresh < this_file
-- The whole script is wrapped BEGIN; ... ROLLBACK; so it leaves the shared DB untouched.
--
-- Cases covered:
--   (1) Normal: packing WH with active network + active line => returns source WH
--   (2) No distribution network on WH => returns input unchanged
--   (3) IsAutoDistributionOrder='N' => returns input unchanged (not a picking/packing WH)
--   (4) Inactive warehouse (w.IsActive='N') => returns input unchanged
--   (5) Inactive distribution network header (nd.IsActive='N') => returns input unchanged
--   (6) Active network, but line IsActive='N' => returns input unchanged

BEGIN;

DO $$
DECLARE
  -- IDs: pick high values unlikely to collide with seed data
  v_nd_active   numeric := -9001;   -- DD_NetworkDistribution (active)
  v_nd_inactive numeric := -9002;   -- DD_NetworkDistribution (inactive header)
  v_wh_stock    numeric := -9010;   -- source / stocking warehouse
  v_wh_pack     numeric := -9011;   -- packing WH, active network (case 1)
  v_wh_nonet    numeric := -9012;   -- packing WH, no network (case 2)
  v_wh_noadr    numeric := -9013;   -- WH with network but IsAutoDistributionOrder='N' (case 3)
  v_wh_inactive numeric := -9014;   -- inactive WH (case 4)
  v_wh_indnet   numeric := -9015;   -- WH with inactive network header (case 5)
  v_wh_inact_ln numeric := -9016;   -- WH where the network line is inactive (case 6)
  v_client      numeric;
  v_bpartner    numeric;
  v_bploc       numeric;
  v_shipper     numeric;
  v_got         numeric;
BEGIN
  SELECT ad_client_id, c_bpartner_id, c_bpartner_location_id
    INTO v_client, v_bpartner, v_bploc
    FROM m_warehouse WHERE m_warehouse_id = 540005;

  SELECT m_shipper_id INTO v_shipper FROM m_shipper LIMIT 1;

  -- ---- fixtures: distribution networks ------------------------------------

  -- Active network header
  INSERT INTO dd_networkdistribution
    (dd_networkdistribution_id, ad_client_id, ad_org_id, isactive, created, createdby,
     updated, updatedby, value, name, ishudestroyed)
  VALUES
    (v_nd_active, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_NET_ACTIVE', 'Test Network Active', 'N');

  -- Inactive network header (case 5)
  INSERT INTO dd_networkdistribution
    (dd_networkdistribution_id, ad_client_id, ad_org_id, isactive, created, createdby,
     updated, updatedby, value, name, ishudestroyed)
  VALUES
    (v_nd_inactive, v_client, 1000000, 'N', now(), 100, now(), 100,
     'TEST_NET_INACTIVE', 'Test Network Inactive', 'N');

  -- ---- fixtures: warehouses -----------------------------------------------
  -- separator='*' and c_bpartner_id are mandatory NOT NULL columns.

  -- Source (stocking) warehouse
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder)
  VALUES
    (v_wh_stock, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_WH_STOCK', 'Test Stock WH', '*', v_bpartner, v_bploc, 'N');

  -- (1) Packing WH with active network, IsAutoDistributionOrder='Y'
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder, dd_networkdistribution_id)
  VALUES
    (v_wh_pack, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_WH_PACK', 'Test Packing WH', '*', v_bpartner, v_bploc, 'Y', v_nd_active);

  -- (2) WH without any distribution network
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder)
  VALUES
    (v_wh_nonet, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_WH_NONET', 'Test WH No Network', '*', v_bpartner, v_bploc, 'Y');

  -- (3) WH with active network but IsAutoDistributionOrder='N'
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder, dd_networkdistribution_id)
  VALUES
    (v_wh_noadr, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_WH_NOADR', 'Test WH No AutoDR', '*', v_bpartner, v_bploc, 'N', v_nd_active);

  -- (4) Inactive warehouse
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder, dd_networkdistribution_id)
  VALUES
    (v_wh_inactive, v_client, 1000000, 'N', now(), 100, now(), 100,
     'TEST_WH_INACTIVE', 'Test Inactive WH', '*', v_bpartner, v_bploc, 'Y', v_nd_active);

  -- (5) WH pointing to the inactive network header
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder, dd_networkdistribution_id)
  VALUES
    (v_wh_indnet, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_WH_INDNET', 'Test WH Inactive Net Hdr', '*', v_bpartner, v_bploc, 'Y', v_nd_inactive);

  -- (6) WH with active network but an inactive line
  INSERT INTO m_warehouse
    (m_warehouse_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     value, name, separator, c_bpartner_id, c_bpartner_location_id, isautoDistributionorder, dd_networkdistribution_id)
  VALUES
    (v_wh_inact_ln, v_client, 1000000, 'Y', now(), 100, now(), 100,
     'TEST_WH_INACTLN', 'Test WH Inactive Line', '*', v_bpartner, v_bploc, 'Y', v_nd_active);

  -- ---- fixtures: distribution network lines --------------------------------

  -- Active line: active network => v_wh_pack maps to v_wh_stock (case 1)
  INSERT INTO dd_networkdistributionline
    (dd_networkdistributionline_id, dd_networkdistribution_id,
     ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     m_warehouse_id, m_warehousesource_id, m_shipper_id)
  VALUES
    (-9001, v_nd_active,
     v_client, 1000000, 'Y', now(), 100, now(), 100,
     v_wh_pack, v_wh_stock,
     v_shipper);

  -- Also add line for v_wh_noadr targeting v_wh_stock (to confirm IsAutoDistributionOrder='N' still blocks)
  INSERT INTO dd_networkdistributionline
    (dd_networkdistributionline_id, dd_networkdistribution_id,
     ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     m_warehouse_id, m_warehousesource_id, m_shipper_id)
  VALUES
    (-9002, v_nd_active,
     v_client, 1000000, 'Y', now(), 100, now(), 100,
     v_wh_noadr, v_wh_stock,
     v_shipper);

  -- Line for inactive-WH case (case 4): active line, but WH itself is inactive
  INSERT INTO dd_networkdistributionline
    (dd_networkdistributionline_id, dd_networkdistribution_id,
     ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     m_warehouse_id, m_warehousesource_id, m_shipper_id)
  VALUES
    (-9003, v_nd_active,
     v_client, 1000000, 'Y', now(), 100, now(), 100,
     v_wh_inactive, v_wh_stock,
     v_shipper);

  -- Line for inactive-network-header case (case 5): active line in an inactive-header network
  INSERT INTO dd_networkdistributionline
    (dd_networkdistributionline_id, dd_networkdistribution_id,
     ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     m_warehouse_id, m_warehousesource_id, m_shipper_id)
  VALUES
    (-9004, v_nd_inactive,
     v_client, 1000000, 'Y', now(), 100, now(), 100,
     v_wh_indnet, v_wh_stock,
     v_shipper);

  -- INACTIVE line for case 6
  INSERT INTO dd_networkdistributionline
    (dd_networkdistributionline_id, dd_networkdistribution_id,
     ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     m_warehouse_id, m_warehousesource_id, m_shipper_id)
  VALUES
    (-9005, v_nd_active,
     v_client, 1000000, 'N', now(), 100, now(), 100,
     v_wh_inact_ln, v_wh_stock,
     v_shipper);

  -- ====================== ASSERTIONS ======================================

  -- (1) Normal resolution: packing WH with active network + active line => source WH
  SELECT MD_getStockWarehouse(v_wh_pack) INTO v_got;
  IF v_got IS DISTINCT FROM v_wh_stock THEN
    RAISE EXCEPTION '(1) active packing WH: expected source WH %, got %', v_wh_stock, v_got;
  END IF;

  -- (2) No network: function returns input unchanged
  SELECT MD_getStockWarehouse(v_wh_nonet) INTO v_got;
  IF v_got IS DISTINCT FROM v_wh_nonet THEN
    RAISE EXCEPTION '(2) no network: expected input % unchanged, got %', v_wh_nonet, v_got;
  END IF;

  -- (3) IsAutoDistributionOrder='N': returns input unchanged
  SELECT MD_getStockWarehouse(v_wh_noadr) INTO v_got;
  IF v_got IS DISTINCT FROM v_wh_noadr THEN
    RAISE EXCEPTION '(3) IsAutoDistributionOrder=N: expected input % unchanged, got %', v_wh_noadr, v_got;
  END IF;

  -- (4) Inactive WH: returns input unchanged (not a valid picking WH)
  SELECT MD_getStockWarehouse(v_wh_inactive) INTO v_got;
  IF v_got IS DISTINCT FROM v_wh_inactive THEN
    RAISE EXCEPTION '(4) inactive WH: expected input % unchanged, got %', v_wh_inactive, v_got;
  END IF;

  -- (5) Inactive distribution network header: returns input unchanged
  SELECT MD_getStockWarehouse(v_wh_indnet) INTO v_got;
  IF v_got IS DISTINCT FROM v_wh_indnet THEN
    RAISE EXCEPTION '(5) inactive network header: expected input % unchanged, got %', v_wh_indnet, v_got;
  END IF;

  -- (6) Active network but inactive line: returns input unchanged
  SELECT MD_getStockWarehouse(v_wh_inact_ln) INTO v_got;
  IF v_got IS DISTINCT FROM v_wh_inact_ln THEN
    RAISE EXCEPTION '(6) inactive line: expected input % unchanged, got %', v_wh_inact_ln, v_got;
  END IF;

  RAISE NOTICE 'OK — all of (1)-(6) passed';
END $$;

ROLLBACK;
