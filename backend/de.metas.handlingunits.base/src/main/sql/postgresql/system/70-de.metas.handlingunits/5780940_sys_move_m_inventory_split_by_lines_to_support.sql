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
-- Move m_inventory_split_by_lines function to support schema

-- Ensure support schema exists
CREATE SCHEMA IF NOT EXISTS support;

-- Drop old function from public schema
DROP FUNCTION IF EXISTS public.m_inventory_split_by_lines(numeric, integer);
DROP FUNCTION IF EXISTS m_inventory_split_by_lines(numeric, integer);

-- Drop if exists in support schema (for idempotency)
DROP FUNCTION IF EXISTS support.m_inventory_split_by_lines(numeric, integer);

CREATE OR REPLACE FUNCTION support.m_inventory_split_by_lines(
    p_m_inventory_id numeric,
    p_lines_max_count integer
)
RETURNS TABLE (
    original_inventory_id numeric,
    new_inventory_ids numeric[],
    total_lines_processed integer,
    message text
)
LANGUAGE plpgsql
AS $$
DECLARE
    v_total_lines integer;
    v_num_new_docs integer;
    v_inventory_rec record;
    v_new_inventory_id numeric;
    v_new_documentno varchar;
    v_doc_counter integer;
    v_lines_to_move integer;
    v_offset integer;
    v_new_inventory_ids numeric[] := ARRAY[]::numeric[];
BEGIN
    -- Validate parameters
    IF p_lines_max_count <= 0 THEN
        RAISE EXCEPTION 'Parameter p_lines_max_count must be positive, got: %', p_lines_max_count;
    END IF;

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

    -- If lines are within limit, nothing to do
    IF v_total_lines <= p_lines_max_count THEN
        RETURN QUERY SELECT
            p_m_inventory_id,
            ARRAY[]::numeric[],
            v_total_lines,
            'No split needed - inventory has ' || v_total_lines || ' lines (limit: ' || p_lines_max_count || ')';
        RETURN;
    END IF;

    -- Calculate how many additional documents we need
    -- Original keeps p_lines_max_count, rest are distributed to new docs
    v_num_new_docs := CEIL((v_total_lines - p_lines_max_count)::numeric / p_lines_max_count::numeric)::integer;

    -- Create temporary table to hold line distribution
    -- Assign each line to a document number (0 = original, 1 = _02, 2 = _03, etc.)
    CREATE TEMP TABLE IF NOT EXISTS temp_line_distribution ON COMMIT DROP AS
    WITH numbered_lines AS (
        SELECT
            m_inventoryline_id,
            ROW_NUMBER() OVER (ORDER BY m_inventoryline_id) - 1 AS line_seq
        FROM m_inventoryline
        WHERE m_inventory_id = p_m_inventory_id
    )
    SELECT
        m_inventoryline_id,
        FLOOR(line_seq / p_lines_max_count)::integer AS doc_index
    FROM numbered_lines;

    -- Create new inventory documents and redistribute lines
    v_doc_counter := 2;  -- Start with _02

    FOR i IN 1..v_num_new_docs LOOP
        -- Get new inventory ID from sequence
        v_new_inventory_id := nextval('m_inventory_seq');

        -- Generate new document number (append _02, _03, etc.)
        v_new_documentno := v_inventory_rec.documentno || '_' || LPAD(v_doc_counter::text, 2, '0');

        -- Insert new inventory record (copy all fields except id and documentno)
        -- created/updated timestamps will be set automatically by column defaults
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

        -- Move lines assigned to this document index (i = doc_index since we start at 1)
        UPDATE m_inventoryline
        SET m_inventory_id = v_new_inventory_id,
            updated = now(),
            updatedby = v_inventory_rec.updatedby
        WHERE m_inventoryline_id IN (
            SELECT m_inventoryline_id
            FROM temp_line_distribution
            WHERE doc_index = i
        );

        -- Track new inventory IDs
        v_new_inventory_ids := array_append(v_new_inventory_ids, v_new_inventory_id);

        -- Prepare for next iteration
        v_doc_counter := v_doc_counter + 1;
    END LOOP;

    -- Clean up temp table
    DROP TABLE IF EXISTS temp_line_distribution;

    -- Return summary
    RETURN QUERY SELECT
        p_m_inventory_id,
        v_new_inventory_ids,
        v_total_lines,
        'Split ' || v_total_lines || ' lines into ' || (v_num_new_docs + 1) || ' inventory documents';
END;
$$;

COMMENT ON FUNCTION support.m_inventory_split_by_lines(numeric, integer) IS
'Splits an M_Inventory document with too many lines into multiple documents.
Parameters:
- p_m_inventory_id: The inventory document ID to split
- p_lines_max_count: Maximum number of lines per inventory document
Returns:
- original_inventory_id: The original inventory ID
- new_inventory_ids: Array of newly created inventory IDs
- total_lines_processed: Total number of lines that were distributed
- message: Status message
The original inventory keeps the first p_lines_max_count lines.
Excess lines are distributed to new inventory documents with DocumentNo suffixes (_02, _03, etc.)
The inventory must not be processed yet (processed = ''N'').';
