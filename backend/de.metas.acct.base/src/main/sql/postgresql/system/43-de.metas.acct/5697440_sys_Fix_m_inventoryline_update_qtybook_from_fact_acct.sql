DROP FUNCTION IF EXISTS de_metas_acct.m_inventoryline_update_qtybook_from_fact_acct(
    p_M_Inventory_ID      numeric,
    p_ProductAssetAccount varchar,
    p_RecreateLines       char(1),
    p_DryRun              char(1),
    p_DateAcctFrom        date,
    p_DateAcctTo          date
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.m_inventoryline_update_qtybook_from_fact_acct(
    p_M_Inventory_ID      numeric,
    p_ProductAssetAccount varchar,
    p_RecreateLines       char(1) = 'N',
    p_DryRun              char(1) = 'N',
    p_DateAcctFrom        date = NULL,
    p_DateAcctTo          date = NULL
)
    RETURNS VOID
AS
$BODY$
DECLARE
    v_inventoryInfo record;
    --
    v_rowcount      numeric;
    v_record        record;
BEGIN
    RAISE NOTICE 'Updating inventory line QtyBook from fact acct: p_M_Inventory_ID=%, p_ProductAssetAccount=%, p_RecreateLines=%, p_DryRun=%, p_DateAcctFrom=%, p_DateAcctTo=%',
        p_M_Inventory_ID,
        p_ProductAssetAccount,
        p_RecreateLines,
        p_DryRun,
        p_DateAcctFrom,
        p_DateAcctTo;

    SELECT inv.m_warehouse_id,
           wh.name                                        AS warehouse,
           p_DateAcctFrom                                 AS DateAcctFrom,
           COALESCE(p_DateAcctTo, inv.movementdate::date) AS DateAcctTo,
           inv.docstatus,
           inv.ad_client_id,
           inv.ad_org_id
    INTO v_inventoryInfo
    FROM m_inventory inv
             INNER JOIN m_warehouse wh USING (m_warehouse_id)
    WHERE inv.m_inventory_id = p_M_Inventory_ID;

    IF (v_inventoryInfo IS NULL) THEN
        RAISE EXCEPTION 'No inventory info found for M_Inventory_ID=%. Make sure that the Warehouse is also set', p_M_Inventory_ID;
    END IF;


    RAISE NOTICE 'Inventory DateAcct=%->%, Warehouse=% -- %',
        v_inventoryInfo.DateAcctFrom,
        v_inventoryInfo.DateAcctTo,
        v_inventoryInfo.warehouse,
        v_inventoryInfo;

    IF (v_inventoryInfo.docstatus != 'DR') THEN
        RAISE EXCEPTION 'Only Draft inventories are allowed to be updated';
    END IF;

    --
    -- Sum-up the qtys from Fact_Acct
    --
    DROP TABLE IF EXISTS tmp_fact_acct;
    CREATE TEMPORARY TABLE tmp_fact_acct AS
    SELECT loc.m_warehouse_id,
           fa.m_product_id,
           fa.c_uom_id,
           SUM(fa.amtacctdr - fa.amtacctcr) AS amt,
           SUM(fa.qty)                      AS qty
    FROM fact_acct fa
             INNER JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id
             LEFT OUTER JOIN m_locator loc ON loc.m_locator_id = fa.m_locator_id
    WHERE ev.value = p_ProductAssetAccount
      AND (v_inventoryInfo.DateAcctFrom IS NULL OR fa.dateacct >= v_inventoryInfo.DateAcctFrom::date)
      AND fa.dateacct::date <= v_inventoryInfo.DateAcctTo::date
      AND loc.m_warehouse_id = v_inventoryInfo.m_warehouse_id
      AND NOT (fa.ad_table_id = get_table_id('M_Inventory') AND fa.record_id IN (p_M_Inventory_ID))
    GROUP BY loc.m_warehouse_id, fa.m_product_id, fa.c_uom_id;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    CREATE UNIQUE INDEX ON tmp_fact_acct (m_product_id);
    RAISE NOTICE 'Prepared % products from Fact_Acct', v_rowcount;

    IF (p_RecreateLines = 'Y') THEN
        --
        -- CASE: Recreate Inventory Lines
        --

        --
        -- Backup existing lines
        DROP TABLE IF EXISTS tmp_prev_inventoryline;
        CREATE TEMPORARY TABLE tmp_prev_inventoryline AS
        SELECT line, m_product_id, c_uom_id, qtycount, qtybook, costprice, m_locator_id, isactive
        FROM m_inventoryline invl
        WHERE invl.m_inventory_id = p_M_Inventory_ID;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        CREATE UNIQUE INDEX ON tmp_prev_inventoryline (m_product_id); -- atm we support only one product per line
        RAISE NOTICE 'Backed up % inventory lines', v_rowcount;

        --
        -- Delete M_InventoryLine_HU records
        DELETE FROM m_inventoryline_hu invlhu WHERE invlhu.m_inventory_id = p_M_Inventory_ID;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Removed % M_InventoryLine_HU records', v_rowcount;

        --
        -- Delete M_InventoryLine records
        DELETE FROM m_inventoryline invl WHERE invl.m_inventory_id = p_M_Inventory_ID;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Removed % M_InventoryLine records', v_rowcount;

        --
        -- Create new M_InventoryLine records from tmp_fact_acct
        INSERT INTO m_inventoryline (m_inventoryline_id, ad_client_id, ad_org_id, createdby, updatedby, m_inventory_id, m_locator_id, m_product_id, line, description, c_charge_id, qtyinternaluse, reversalline_id, m_inoutline_id, c_uom_id, m_hu_pi_item_product_id, qtytu, qualitynote, m_hu_id, assignedto, externalid, costprice,
                                     qtybook,
                                     qtycount,
                                     iscounted,
                                     processed,
                                     huaggregationtype)
        SELECT NEXTVAL('m_inventoryline_seq'),
               v_inventoryInfo.ad_client_id                      AS ad_client_id,
               v_inventoryInfo.ad_org_id                         AS ad_org_id,
               99                                                AS createdby,
               99                                                AS updatedby,
               p_M_Inventory_ID                                  AS m_inventory_id,
               (SELECT loc.m_locator_id
                FROM m_locator loc
                WHERE loc.m_warehouse_id = v_inventoryInfo.m_warehouse_id
                ORDER BY loc.isdefault DESC, loc.m_locator_id
                LIMIT 1)                                         AS m_locator_id,
               fa.m_product_id,
               10 * DENSE_RANK() OVER (ORDER BY fa.m_product_id) AS line,
               NULL                                              AS description,
               NULL                                              AS c_charge_id,
               NULL                                              AS qtyinternaluse,
               NULL                                              AS reversalline_id,
               NULL                                              AS m_inoutline_id,
               fa.c_uom_id,
               NULL                                              AS m_hu_pi_item_product_id,
               NULL                                              AS qtytu,
               NULL                                              AS qualitynote,
               NULL                                              AS m_hu_id,
               NULL                                              AS assignedto,
               NULL                                              AS externalid,
               NULL                                              AS costprice,
               --
               fa.qty                                            AS qtybook,
               0                                                 AS qtycount,
               'Y'                                               AS iscounted,
               'Y'                                               AS processed,
               'M'                                               AS huaggregationtype
        FROM tmp_fact_acct fa
        WHERE fa.qty != 0
        ORDER BY fa.m_product_id;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Inserted % M_InventoryLine records (from Fact_Acct)', v_rowcount;

        --
        -- Update newly created inventory lines from previous inventory lines
        UPDATE M_InventoryLine invl
        SET qtycount=uomConvert(invl.m_product_id,
                                t.c_uom_id,
                                invl.c_uom_id,
                                t.qtycount),
            costprice=t.costprice
        FROM tmp_prev_inventoryline t
        WHERE invl.m_inventory_id = p_M_Inventory_ID
          AND invl.m_product_id = t.m_product_id;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Updated QtyCount for % M_InventoryLine records', v_rowcount;


        --
        -- Create new M_InventoryLine records from previous inventory lines
        INSERT INTO m_inventoryline (m_inventoryline_id, ad_client_id, ad_org_id, createdby, updatedby, m_inventory_id, m_locator_id, m_product_id, line, description, c_charge_id, qtyinternaluse, reversalline_id, m_inoutline_id, c_uom_id, m_hu_pi_item_product_id, qtytu, qualitynote, m_hu_id, assignedto, externalid, costprice,
                                     qtybook,
                                     qtycount,
                                     iscounted,
                                     processed,
                                     huaggregationtype,
                                     isactive)
        SELECT NEXTVAL('m_inventoryline_seq'),
               v_inventoryInfo.ad_client_id                                                           AS ad_client_id,
               v_inventoryInfo.ad_org_id                                                              AS ad_org_id,
               99                                                                                     AS createdby,
               99                                                                                     AS updatedby,
               p_M_Inventory_ID                                                                       AS m_inventory_id,
               prev_invl.m_locator_id                                                                 AS m_locator_id,
               prev_invl.m_product_id                                                                 AS m_product_id,
               (SELECT MAX(line) FROM m_inventoryline invl WHERE invl.m_inventory_id = p_M_Inventory_ID)
                   + 10 * DENSE_RANK() OVER (ORDER BY prev_invl.m_product_id, prev_invl.m_product_id) AS line,
               NULL                                                                                   AS description,
               NULL                                                                                   AS c_charge_id,
               NULL                                                                                   AS qtyinternaluse,
               NULL                                                                                   AS reversalline_id,
               NULL                                                                                   AS m_inoutline_id,
               prev_invl.c_uom_id                                                                     AS c_uom_id,
               NULL                                                                                   AS m_hu_pi_item_product_id,
               NULL                                                                                   AS qtytu,
               NULL                                                                                   AS qualitynote,
               NULL                                                                                   AS m_hu_id,
               NULL                                                                                   AS assignedto,
               NULL                                                                                   AS externalid,
               costprice                                                                                   AS costprice,
               --
               0                                                                                      AS qtybook,
               prev_invl.qtycount                                                                     AS qtycount,
               'Y'                                                                                    AS iscounted,
               'Y'                                                                                    AS processed,
               'M'                                                                                    AS huaggregationtype,
               prev_invl.isactive                                                                     AS isactive
        FROM tmp_prev_inventoryline prev_invl
        WHERE
            -- for products we don't already have
            NOT EXISTS(SELECT 1 FROM m_inventoryline invl WHERE invl.m_inventory_id = p_M_Inventory_ID AND invl.m_product_id = prev_invl.m_product_id)
        ORDER BY prev_invl.line, prev_invl.m_product_id;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Inserted % M_InventoryLine records (from previous lines)', v_rowcount;


        --
        -- Bring the HUs from that warehouse too
        --
        DROP TABLE IF EXISTS tmp_hu;
        CREATE TEMPORARY TABLE tmp_hu AS
        SELECT hu.m_hu_id, hus.m_product_id, hus.c_uom_id, hus.qty
        FROM m_hu hu
                 INNER JOIN m_locator loc ON loc.m_locator_id = hu.m_locator_id
                 INNER JOIN m_hu_storage hus ON hus.m_hu_id = hu.m_hu_id
        WHERE loc.m_warehouse_id = v_inventoryInfo.m_warehouse_id
          AND hu.hustatus IN ('A', 'S', 'I')
          AND hu.m_hu_item_parent_id IS NULL -- top level
        ;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        CREATE INDEX ON tmp_hu (m_hu_id);
        CREATE INDEX ON tmp_hu (m_product_id);
        RAISE NOTICE 'Selected % M_HU records', v_rowcount;

        -- IMPORTANT: we assume the Products from M_InventoryLine are distinct!!!
        RAISE NOTICE 'Making sure we don''t have multiple lines with same product...';
        FOR v_record IN (SELECT invl.m_product_id, p.Value, p.name, COUNT(1) AS duplicates
                         FROM m_inventoryline invl
                                  INNER JOIN m_product p ON p.m_product_id = invl.m_product_id
                         WHERE invl.m_inventory_id = p_M_Inventory_ID
                         GROUP BY invl.m_product_id, p.Value, p.name
                         HAVING COUNT(1) > 1)
            LOOP
                RAISE EXCEPTION 'Found duplicate  product: %', v_record;
            END LOOP;

        --
        -- Join HUs to existing inventory lines
        --
        INSERT INTO m_inventoryline_hu (ad_client_id, ad_org_id, created, createdby, c_uom_id, isactive, m_hu_id, m_inventoryline_hu_id, m_inventoryline_id, qtybook, qtycount, updated, updatedby, qtyinternaluse, m_inventory_id)
        SELECT invl.ad_client_id,
               invl.ad_org_id,
               NOW()                                AS created,
               99                                   AS createdby,
               COALESCE(hu.c_uom_id, invl.c_uom_id) AS c_uom_id,
               'Y'                                  AS isactive,
               hu.m_hu_id,
               NEXTVAL('m_inventoryline_hu_seq')    AS m_inventoryline_hu_id,
               invl.m_inventoryline_id,
               COALESCE(hu.qty, 0)                  AS qtybook,
               COALESCE(hu.qty, 0)                  AS qtycount,
               NOW()                                AS updated,
               99                                   AS updatedby,
               NULL                                 AS qtyinternaluse,
               invl.m_inventory_id
        FROM m_inventoryline invl
                 LEFT JOIN tmp_hu hu ON hu.m_product_id = invl.m_product_id
        WHERE invl.m_inventory_id = p_M_Inventory_ID
          AND invl.isactive = 'Y'
        ORDER BY invl.m_inventoryline_id, hu.m_hu_id;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Inserted % M_InventoryLine_HU records', v_rowcount;

        --
        -- Spread the M_InventoryLine.QtyCount/QtyBook over M_InventoryLine_HUs
        --
        PERFORM de_metas_acct.m_inventoryline_hu_update_qtycount(
                p_M_Inventory_ID := p_M_Inventory_ID,
                p_updatehuqtybook := 'N', -- was just calculated
                p_FailOnError := 'N',
                p_dryrun := 'N');
    ELSE
        --
        -- CASE: Just update existing Inventory Lines
        --

        --
        RAISE NOTICE 'Making sure we don''t have multiple lines with same product...';
        FOR v_record IN (SELECT invl.m_product_id, p.Value, p.name, COUNT(1) AS duplicates
                         FROM m_inventoryline invl
                                  INNER JOIN m_product p ON p.m_product_id = invl.m_product_id
                         WHERE invl.m_inventory_id = p_M_Inventory_ID
                         GROUP BY invl.m_product_id, p.Value, p.name
                         HAVING COUNT(1) > 1)
            LOOP
                RAISE EXCEPTION 'Found duplicate  product: %', v_record;
            END LOOP;

        --
        -- Update existing lines
        UPDATE m_inventoryline il
        SET qtybook=COALESCE((SELECT COALESCE(t.qty, 0) FROM tmp_fact_acct t WHERE t.m_product_id = il.m_product_id), 0),
            iscounted='Y'
        WHERE il.m_inventory_id = p_M_Inventory_ID;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Updated % M_InventoryLine records', v_rowcount;

        --
        -- Spread the M_InventoryLine.QtyCount/QtyBook over M_InventoryLine_HUs
        --
        PERFORM de_metas_acct.m_inventoryline_hu_update_qtycount(
                p_M_Inventory_ID := p_M_Inventory_ID,
                p_updatehuqtybook := 'Y',
                p_FailOnError := 'N',
                p_dryrun := 'N');
    END IF;

    IF (p_DryRun = 'Y') THEN
        RAISE EXCEPTION '!!! DryRun mode !!! rolling back transaction...';
    END IF;

    RAISE NOTICE 'DONE';
END ;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;


