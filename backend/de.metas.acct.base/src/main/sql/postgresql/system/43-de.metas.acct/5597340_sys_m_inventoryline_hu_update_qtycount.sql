DROP FUNCTION IF EXISTS de_metas_acct.m_inventoryline_hu_update_qtycount(
    p_M_Inventory_ID  numeric,
    p_DryRun          char(1),
    p_UpdateHUQtyBook char(1)
)
;

CREATE OR REPLACE FUNCTION de_metas_acct.m_inventoryline_hu_update_qtycount(
    p_M_Inventory_ID  numeric,
    p_DryRun          char(1) = 'N',
    p_UpdateHUQtyBook char(1) = 'N'
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
                END LOOP;

            RAISE NOTICE '     remaining Qty to allocate=% % %',
                v_qtyToAllocateRemaining, v_currentInventoryLine.uomsymbol,
                (CASE WHEN v_qtyToAllocateRemaining != 0 THEN '!!!' ELSE '' END);
        END LOOP;
END;
$BODY$
    LANGUAGE plpgsql
    VOLATILE
    COST 100
;


