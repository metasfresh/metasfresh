/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

-- 2025-12-15
-- Function to split M_Inventory documents by M_Locator_ID into multiple documents
-- Each locator gets its own inventory document

DROP FUNCTION IF EXISTS support.m_inventory_split_by_locator(numeric);

CREATE OR REPLACE FUNCTION support.m_inventory_split_by_locator(
    p_m_inventory_id numeric
)
RETURNS TABLE (
    original_inventory_id numeric,
    new_inventory_ids numeric[],
    total_lines_processed integer,
    locators_split integer,
    message text
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_total_lines integer;
    v_num_locators integer;
    v_primary_locator_id numeric;
    v_inventory_rec record;
    v_locator_rec record;
    v_new_inventory_id numeric;
    v_new_documentno varchar;
    v_doc_counter integer;
    v_new_inventory_ids numeric[] := ARRAY[]::numeric[];
    v_lines_moved integer;
BEGIN
    -- Check if inventory exists and is not processed
    SELECT * INTO v_inventory_rec
    FROM m_inventory
    WHERE m_inventory_id = p_m_inventory_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Inventory with ID % not found', p_m_inventory_id;
    END IF;

    IF v_inventory_rec.processed = 'Y' THEN
        RAISE EXCEPTION 'Inventory % (DocumentNo: %) is already processed and cannot be split',
            p_m_inventory_id, v_inventory_rec.documentno;
    END IF;

    -- Count total lines
    SELECT COUNT(*) INTO v_total_lines
    FROM m_inventoryline
    WHERE m_inventory_id = p_m_inventory_id;

    IF v_total_lines = 0 THEN
        RETURN QUERY SELECT
            p_m_inventory_id,
            ARRAY[]::numeric[],
            0,
            0,
            'No lines in inventory - nothing to split';
        RETURN;
    END IF;

    -- Count distinct locators
    SELECT COUNT(DISTINCT m_locator_id) INTO v_num_locators
    FROM m_inventoryline
    WHERE m_inventory_id = p_m_inventory_id;

    -- If only 1 locator, nothing to split
    IF v_num_locators <= 1 THEN
        RETURN QUERY SELECT
            p_m_inventory_id,
            ARRAY[]::numeric[],
            v_total_lines,
            v_num_locators,
            'No split needed - inventory has only ' || v_num_locators || ' locator(s)';
        RETURN;
    END IF;

    -- Find the primary locator (locator of the first inventory line by M_InventoryLine_ID)
    SELECT m_locator_id INTO v_primary_locator_id
    FROM m_inventoryline
    WHERE m_inventory_id = p_m_inventory_id
    ORDER BY m_inventoryline_id
    LIMIT 1;

    -- Create new inventory documents for each non-primary locator
    v_doc_counter := 2;  -- Start with _02

    FOR v_locator_rec IN
        SELECT DISTINCT m_locator_id
        FROM m_inventoryline
        WHERE m_inventory_id = p_m_inventory_id
          AND m_locator_id != v_primary_locator_id
        ORDER BY m_locator_id
    LOOP
        -- Get new inventory ID from sequence
        v_new_inventory_id := nextval('m_inventory_seq');

        -- Generate new document number (append _02, _03, etc.)
        v_new_documentno := v_inventory_rec.documentno || '_' || LPAD(v_doc_counter::text, 2, '0');

        -- Insert new inventory record (copy all fields except id and documentno)
        INSERT INTO m_inventory (
            m_inventory_id, ad_client_id, ad_org_id, isactive,
            createdby, updatedby, documentno, description,
            m_warehouse_id, movementdate, posted, processed, processing,
            updateqty, generatelist, m_perpetualinv_id, ad_orgtrx_id,
            c_project_id, c_campaign_id, c_activity_id, user1_id, user2_id,
            isapproved, docstatus, docaction, approvalamt, c_doctype_id,
            reversal_id, quickinput_product_id, quickinput_qtyinternalgain,
            poreference, snapshot_uuid, externalid, postingerror_issue_id,
            c_bpartner_location_id, c_bpartner_id, c_po_order_id,
            m_picking_job_id, ad_user_responsible_id
        )
        VALUES (
            v_new_inventory_id, v_inventory_rec.ad_client_id, v_inventory_rec.ad_org_id, v_inventory_rec.isactive,
            v_inventory_rec.createdby, v_inventory_rec.updatedby, v_new_documentno, v_inventory_rec.description,
            v_inventory_rec.m_warehouse_id, v_inventory_rec.movementdate, v_inventory_rec.posted,
            v_inventory_rec.processed, v_inventory_rec.processing,
            v_inventory_rec.updateqty, v_inventory_rec.generatelist, v_inventory_rec.m_perpetualinv_id,
            v_inventory_rec.ad_orgtrx_id,
            v_inventory_rec.c_project_id, v_inventory_rec.c_campaign_id, v_inventory_rec.c_activity_id,
            v_inventory_rec.user1_id, v_inventory_rec.user2_id,
            v_inventory_rec.isapproved, v_inventory_rec.docstatus, v_inventory_rec.docaction,
            v_inventory_rec.approvalamt, v_inventory_rec.c_doctype_id,
            v_inventory_rec.reversal_id, v_inventory_rec.quickinput_product_id,
            v_inventory_rec.quickinput_qtyinternalgain,
            v_inventory_rec.poreference, v_inventory_rec.snapshot_uuid, v_inventory_rec.externalid,
            v_inventory_rec.postingerror_issue_id,
            v_inventory_rec.c_bpartner_location_id, v_inventory_rec.c_bpartner_id, v_inventory_rec.c_po_order_id,
            v_inventory_rec.m_picking_job_id, v_inventory_rec.ad_user_responsible_id
        );

        -- Move all lines with this locator to the new inventory
        UPDATE m_inventoryline
        SET m_inventory_id = v_new_inventory_id,
            updated = now(),
            updatedby = v_inventory_rec.updatedby
        WHERE m_inventory_id = p_m_inventory_id
          AND m_locator_id = v_locator_rec.m_locator_id;

        GET DIAGNOSTICS v_lines_moved = ROW_COUNT;

        -- Track new inventory IDs
        v_new_inventory_ids := array_append(v_new_inventory_ids, v_new_inventory_id);

        -- Prepare for next iteration
        v_doc_counter := v_doc_counter + 1;
    END LOOP;

    -- Return summary
    RETURN QUERY SELECT
        p_m_inventory_id,
        v_new_inventory_ids,
        v_total_lines,
        v_num_locators,
        'Split ' || v_total_lines || ' lines across ' || v_num_locators || ' locators into ' || v_num_locators || ' inventory documents';
END;
$$;

COMMENT ON FUNCTION support.m_inventory_split_by_locator(numeric) IS
'Splits an M_Inventory document by M_Locator_ID into multiple documents.
Each distinct locator gets its own inventory document.
Parameters:
- p_m_inventory_id: The inventory document ID to split
Returns:
- original_inventory_id: The original inventory ID (keeps lines with the primary locator)
- new_inventory_ids: Array of newly created inventory IDs
- total_lines_processed: Total number of lines that were distributed
- locators_split: Number of distinct locators found
- message: Status message
The original inventory keeps lines with the same locator as the first inventory line (by M_InventoryLine_ID).
Lines with other locators are moved to new inventory documents with DocumentNo suffixes (_02, _03, etc.)
The inventory must not be processed yet (processed = ''N'').';
