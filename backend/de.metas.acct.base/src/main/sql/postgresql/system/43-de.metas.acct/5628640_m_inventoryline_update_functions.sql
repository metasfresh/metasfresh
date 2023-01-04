/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP FUNCTION IF EXISTS de_metas_acct.m_inventoryline_hu_update_qtycount(
    p_M_Inventory_ID  numeric,
    p_DryRun          char(1),
    p_UpdateHUQtyBook char(1)
)
;

DROP FUNCTION IF EXISTS de_metas_acct.m_inventoryline_hu_update_qtycount(
    p_M_Inventory_ID  numeric,
    p_DryRun          char(1),
    p_UpdateHUQtyBook char(1),
    p_FailOnError     char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.m_inventoryline_hu_update_qtycount(
    p_M_Inventory_ID  numeric,
    p_DryRun          char(1) = 'N',
    p_UpdateHUQtyBook char(1) = 'N',
    p_FailOnError     char(1) = 'Y'
)
    RETURNS VOID
AS
$BODY$
DECLARE
    v_currentInventoryLine   record;
    v_currentInventoryLineHU record;
    v_qtyToAllocateRemaining numeric;
    v_huQtyBook              numeric;
    v_huQtyCount             numeric;
    v_huUomId                numeric;
    v_huUomSymbol            varchar;
    v_huInfo                 record;
BEGIN
    RAISE NOTICE 'Updating M_InventoryLine_HU.QtyCount for p_M_Inventory_ID=%, p_DryRun=%, p_UpdateHUQtyBook=%', p_M_Inventory_ID, p_DryRun, p_UpdateHUQtyBook;

    FOR v_currentInventoryLine IN (SELECT invl.*,
                                          p.value || '_' || p.name || ' (ID=' || p.m_product_id || ')' AS product,
                                          uom.uomsymbol
                                   FROM m_inventoryline invl
                                            INNER JOIN m_product p ON p.m_product_id = invl.m_product_id
                                            LEFT OUTER JOIN c_uom uom ON uom.c_uom_id = invl.c_uom_id
                                   WHERE invl.m_inventory_id = p_M_Inventory_ID
                                   ORDER BY invl.line, invl.m_inventoryline_id)
        LOOP
            v_qtyToAllocateRemaining := v_currentInventoryLine.qtybook - v_currentInventoryLine.qtycount;

            RAISE NOTICE 'Checking line %: %, QtyBook=% %, QtyCount=% % => Qty to allocate=% %',
                v_currentInventoryLine.line,
                v_currentInventoryLine.product,
                v_currentInventoryLine.qtybook, v_currentInventoryLine.uomsymbol,
                v_currentInventoryLine.qtycount, v_currentInventoryLine.uomsymbol,
                v_qtyToAllocateRemaining, v_currentInventoryLine.uomsymbol;

            FOR v_currentInventoryLineHU IN (SELECT invlhu.*,
                                                    uom.uomsymbol
                                             FROM m_inventoryline_hu invlhu
                                                      LEFT OUTER JOIN c_uom uom ON uom.c_uom_id = invlhu.c_uom_id
                                             WHERE invlhu.m_inventoryline_id = v_currentInventoryLine.m_inventoryline_id
                                             ORDER BY invlhu.m_inventoryline_hu_id)
                LOOP
                    IF (p_UpdateHUQtyBook = 'Y' AND v_currentInventoryLineHU.m_hu_id IS NOT NULL) THEN
                        SELECT hus.qty,
                               hus.c_uom_id,
                               uom.uomsymbol,
                               hu.hustatus
                        INTO v_huInfo
                        FROM m_hu_storage hus
                                 INNER JOIN m_hu hu ON hus.m_hu_id = hu.m_hu_id
                                 INNER JOIN c_uom uom ON uom.c_uom_id = hus.c_uom_id
                        WHERE hus.m_hu_id = v_currentInventoryLineHU.m_hu_id
                          AND hus.m_product_id = v_currentInventoryLine.m_product_id;

                        IF (v_huInfo IS NULL) THEN
                            RAISE EXCEPTION 'No HU info found for M_HU_ID=%, M_Product_ID=%',
                                v_currentInventoryLineHU.m_hu_id,
                                v_currentInventoryLine.m_product_id;
                        END IF;

                        --                         IF (v_huInfo.c_uom_id != v_currentInventoryLineHU.c_uom_id) THEN
                        --                             RAISE EXCEPTION 'Inventory line vs HU UOM mismatch: M_HU_ID=%, M_Product_ID=%, Inventory UOM=%, HU UOM=%',
                        --                                 v_currentInventoryLineHU.m_hu_id,
                        --                                 v_currentInventoryLine.product,
                        --                                 v_currentInventoryLineHU.uomsymbol,
                        --                                 v_huInfo.uomsymbol;
                        --                         END IF;

                        v_huUomId := v_huInfo.c_uom_id;
                        v_huUomSymbol := v_huInfo.uomsymbol;
                        v_huQtyBook := v_huInfo.qty;
                    ELSE
                        v_huUomId := v_currentInventoryLineHU.c_uom_id;
                        v_huUomSymbol := v_currentInventoryLineHU.uomsymbol;
                        v_huQtyBook := v_currentInventoryLineHU.qtybook;
                    END IF;

                    --
                    -- Convert HU's QtyBook
                    IF (v_huUomId != v_currentInventoryLine.c_uom_id) THEN
                        v_huQtyBook := uomConvert(
                                p_m_product_id := v_currentInventoryLine.m_product_id,
                                p_c_uom_from_id := v_huUomId,
                                p_c_uom_to_id := v_currentInventoryLine.c_uom_id,
                                p_qty := v_huQtyBook
                            );
                        IF (v_huQtyBook IS NULL) THEN
                            RAISE EXCEPTION 'Failed converting from % to % for %', v_huUomSymbol, v_currentInventoryLine.uomsymbol, v_currentInventoryLine.product;
                        END IF;

                        v_huUomId := v_currentInventoryLine.c_uom_id;
                        v_huUomSymbol := v_currentInventoryLine.uomsymbol;
                    END IF;

                    IF (v_huQtyBook != v_currentInventoryLineHU.qtybook OR v_huUomId != v_currentInventoryLineHU.c_uom_id) THEN
                        RAISE NOTICE 'Change QtyBook: % % (Old: % %)',
                            v_huQtyBook, v_huUomSymbol,
                            v_currentInventoryLineHU.qtybook, v_currentInventoryLineHU.uomsymbol;
                    END IF;


                    v_huQtyCount := NULL; -- to be calculated below

                    IF (v_qtyToAllocateRemaining = 0) THEN
                        v_huQtyCount := v_currentInventoryLineHU.qtybook;
                    ELSIF (v_qtyToAllocateRemaining > v_currentInventoryLineHU.qtybook) THEN
                        v_qtyToAllocateRemaining := v_qtyToAllocateRemaining - v_currentInventoryLineHU.qtybook;
                        v_huQtyCount := 0;
                    ELSE -- v_qtyToAllocateRemaining <= v_currentInventoryLineHU.qtybook
                        v_huQtyCount := v_currentInventoryLineHU.qtybook - v_qtyToAllocateRemaining;
                        v_qtyToAllocateRemaining := 0;
                    END IF;

                    IF (p_DryRun != 'Y') THEN
                        UPDATE m_inventoryline_hu invlhu
                        SET qtycount=v_huQtyCount,
                            qtybook=v_huQtyBook
                        WHERE invlhu.m_inventoryline_hu_id = v_currentInventoryLineHU.m_inventoryline_hu_id;
                    END IF;

                    RAISE NOTICE '     alloc: m_inventoryline_id=%, m_hu_id=%: QtyBook=% %, QtyCount=% % (Old: % %) -- remaining QtyBook to allocate=% %',
                        v_currentInventoryLineHU.m_inventoryline_id,
                        v_currentInventoryLineHU.m_inventoryline_hu_id,
                        v_huQtyBook, v_huUomSymbol,
                        v_huQtyCount, v_huUomSymbol,
                        v_currentInventoryLineHU.qtycount, v_currentInventoryLineHU.uomsymbol,
                        v_qtyToAllocateRemaining, v_currentInventoryLine.uomsymbol;
                END LOOP; -- v_currentInventoryLineHU

            IF (v_qtyToAllocateRemaining = 0) THEN
                RAISE NOTICE '     => OK, fully allocated';
            ELSE
                RAISE NOTICE '     => NOK, failed to allocate % % remaining', v_qtyToAllocateRemaining, v_currentInventoryLine.uomsymbol;
                IF (p_FailOnError = 'Y') THEN
                ELSE
                    RAISE EXCEPTION 'Failing on error!';
                END IF;
            END IF;
        END LOOP;
END ;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;































































DROP FUNCTION IF EXISTS de_metas_acct.m_inventoryline_update_qtycount_from_fact_acct(
    p_M_Inventory_ID      numeric,
    p_ProductAssetAccount varchar,
    p_RecreateLines       char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.m_inventoryline_update_qtycount_from_fact_acct(
    p_M_Inventory_ID      numeric,
    p_ProductAssetAccount varchar,
    p_RecreateLines       char(1) = 'N'
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
    RAISE NOTICE 'Updating inventory line QtyBook from fact acct: p_M_Inventory_ID=%, p_ProductAssetAccount=%, p_RecreateLines=%',
        p_M_Inventory_ID,
        p_ProductAssetAccount,
        p_RecreateLines;

    SELECT inv.m_warehouse_id,
           wh.name AS warehouse,
           inv.movementdate::date,
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


    RAISE NOTICE 'Inventory Date=%, Warehouse=% -- %',
        v_inventoryInfo.movementdate,
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
      AND fa.dateacct::date <= v_inventoryInfo.movementdate::date
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

        RAISE WARNING '!!! Newly (re)created lines will have QtyCount=0 !!!';

        DELETE FROM m_inventoryline_hu invlhu WHERE invlhu.m_inventory_id = p_M_Inventory_ID;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Removed % M_InventoryLine_HU records', v_rowcount;

        DELETE FROM m_inventoryline invl WHERE invl.m_inventory_id = p_M_Inventory_ID;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Removed % M_InventoryLine records', v_rowcount;

        INSERT INTO m_inventoryline (m_inventoryline_id, ad_client_id, ad_org_id, createdby, updatedby, m_inventory_id, m_locator_id, m_product_id, line, description, c_charge_id, qtyinternaluse, reversalline_id, m_inoutline_id, c_uom_id, m_hu_pi_item_product_id, qtytu, qualitynote, m_hu_id, assignedto, externalid, costprice,
                                     qtybook,
                                     qtycount,
                                     iscounted,
                                     processed,
                                     huaggregationtype)
        SELECT NEXTVAL('m_inventoryline_seq'),
               v_inventoryInfo.ad_client_id                                                                           AS ad_client_id,
               v_inventoryInfo.ad_org_id                                                                              AS ad_org_id,
               99                                                                                                     AS createdby,
               99                                                                                                     AS updatedby,
               p_M_Inventory_ID                                                                                       AS m_inventory_id,
               (SELECT loc.m_locator_id FROM m_locator loc WHERE loc.m_warehouse_id = v_inventoryInfo.m_warehouse_id) AS m_locator_id,
               fa.m_product_id,
               10 * DENSE_RANK() OVER (ORDER BY fa.m_product_id)                                                      AS line,
               NULL                                                                                                   AS description,
               NULL                                                                                                   AS c_charge_id,
               NULL                                                                                                   AS qtyinternaluse,
               NULL                                                                                                   AS reversalline_id,
               NULL                                                                                                   AS m_inoutline_id,
               fa.c_uom_id,
               NULL                                                                                                   AS m_hu_pi_item_product_id,
               NULL                                                                                                   AS qtytu,
               NULL                                                                                                   AS qualitynote,
               NULL                                                                                                   AS m_hu_id,
               NULL                                                                                                   AS assignedto,
               NULL                                                                                                   AS externalid,
               NULL                                                                                                   AS costprice,
               --
               fa.qty                                                                                                 AS qtybook,
               0                                                                                                      AS qtycount,
               'Y'                                                                                                    AS iscounted,
               'Y'                                                                                                    AS processed,
               'M'                                                                                                    AS huaggregationtype
        FROM tmp_fact_acct fa
        ORDER BY fa.m_product_id;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Inserted % M_InventoryLine records', v_rowcount;

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

        --
        -- Join HUs to existing inventory lines
        --
        INSERT INTO m_inventoryline_hu (ad_client_id, ad_org_id, created, createdby, c_uom_id, isactive, m_hu_id, m_inventoryline_hu_id, m_inventoryline_id, qtybook, qtycount, updated, updatedby, qtyinternaluse, m_inventory_id)
        SELECT invl.ad_client_id,
               invl.ad_org_id,
               NOW()                             AS created,
               99                                AS createdby,
               hu.c_uom_id,
               'Y'                               AS isactive,
               hu.m_hu_id,
               NEXTVAL('m_inventoryline_hu_seq') AS m_inventoryline_hu_id,
               invl.m_inventoryline_id,
               hu.qty                            AS qtybook,
               hu.qty                            AS qtycount,
               NOW()                             AS updated,
               99                                AS updatedby,
               NULL                              AS qtyinternaluse,
               invl.m_inventory_id
        FROM m_inventoryline invl
                 INNER JOIN tmp_hu hu ON hu.m_product_id = invl.m_product_id
        WHERE invl.m_inventory_id = p_M_Inventory_ID
        ORDER BY invl.m_inventoryline_id, hu.m_hu_id;
        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Inserted % M_InventoryLine_HU records', v_rowcount;

        --
        -- Spread the M_InventoryLine.QtyCount/QtyBook over M_InventoryLine_HUs
        --
        PERFORM de_metas_acct.m_inventoryline_hu_update_qtycount(
                p_M_Inventory_ID := p_M_Inventory_ID,
                p_updatehuqtybook := 'N', -- was just calculated
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
                p_dryrun := 'N');
    END IF;

    RAISE NOTICE 'DONE';
END ;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;


