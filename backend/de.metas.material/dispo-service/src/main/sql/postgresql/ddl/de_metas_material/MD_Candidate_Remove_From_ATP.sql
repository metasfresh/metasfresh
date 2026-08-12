-- Function: de_metas_material.MD_Candidate_Remove_From_ATP
-- Purpose: Remove an MD_Candidate record from ATP (Available to Promise) calculations
--
-- This function:
-- 1. Calculates the candidate's stock impact based on type, business case, qty, and qtyFulfilled
-- 2. Negates this impact to remove it from ATP
-- 3. Sets the candidate's Qty to 0
-- 4. Updates the associated STOCK candidate's ATP value
-- 5. Propagates changes through the entire MD_Candidate_QtyDetails chain chronologically
-- 6. Creates QtyDetails if they don't exist (looking up previous STOCK record)
--
-- Stock Impact Formula (uses helper function MD_Candidate_Get_Stock_Impact):
-- - DEMAND, STOCK_UP, INVENTORY_DOWN (STOCK_CHANGE): -qty
-- - SUPPLY, INVENTORY_UP (STOCK_CHANGE): qty
-- - UNEXPECTED_DECREASE, INVENTORY_DOWN, ATTRIBUTES_CHANGED_FROM: -qtyFulfilled
-- - UNEXPECTED_INCREASE, INVENTORY_UP, ATTRIBUTES_CHANGED_TO: qtyFulfilled

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Remove_From_ATP(numeric)
;

CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Remove_From_ATP(
    p_MD_Candidate_ID numeric
)
    RETURNS TABLE
            (
                removed_candidate_id         numeric,
                stock_candidate_id           numeric,
                current_ATP_qty              numeric,
                qty_adjustment               numeric,
                impacted_md_candidates_count integer,
                message                      text
            )
AS
$BODY$
DECLARE
    v_candidate_record               RECORD;
    v_stock_candidate_id             numeric;
    v_stock_record                   RECORD;
    v_previous_stock_candidate_id    numeric;
    v_previous_stock_qty             numeric;
    v_qty_impact                     numeric;
    v_new_stock_qty                  numeric;
    v_qtydetails_exist               boolean;
    v_next_candidate_record          RECORD;
    v_next_stock_candidate_id        numeric;
    v_has_qtydetails                 boolean;
    v_current_candidate_stock_impact numeric;
    v_updated_stock_qty              numeric;
    v_update_chain_counter           integer := 0;
BEGIN
    -- 1. Validate input and get candidate record
    SELECT MD_Candidate_ID,
           MD_Candidate_Type,
           MD_Candidate_BusinessCase,
           MD_Candidate_Parent_ID,
           Qty,
           QtyFulfilled,
           M_Product_ID,
           M_Warehouse_ID,
           StorageAttributesKey,
           DateProjected,
           SeqNo
    INTO v_candidate_record
    FROM MD_Candidate
    WHERE MD_Candidate_ID = p_MD_Candidate_ID
      AND IsActive = 'Y';

    IF NOT FOUND THEN
        RETURN QUERY SELECT p_MD_Candidate_ID,
                            NULL::numeric,
                            NULL::numeric,
                            'ERROR: MD_Candidate not found or not active: ' || p_MD_Candidate_ID;
        RETURN;
    END IF;

    -- Check if already a STOCK type
    IF v_candidate_record.MD_Candidate_Type = 'STOCK' THEN
        RETURN QUERY SELECT p_MD_Candidate_ID,
                            NULL::numeric,
                            NULL::numeric,
                            NULL::numeric,
                            NULL::integer,
                            'ERROR: Cannot remove STOCK candidates directly. Only DEMAND/SUPPLY/etc types are supported.';
        RETURN;
    END IF;

    -- 2. Find the associated STOCK candidate using helper function
    v_stock_candidate_id := de_metas_material.MD_Candidate_Find_Stock_ID(
            p_MD_Candidate_ID,
            v_candidate_record.MD_Candidate_Parent_ID,
            v_candidate_record.MD_Candidate_Type
                            );

    -- STOCK candidate must exist - if not, it's a data integrity error
    IF v_stock_candidate_id IS NULL THEN
        RETURN QUERY SELECT p_MD_Candidate_ID,
                            NULL::numeric,
                            NULL::numeric,
                            'ERROR: No STOCK candidate found for this record. Data integrity issue - STOCK candidate should always exist.';
        RETURN;
    END IF;

    -- Get current stock record
    SELECT Qty
    INTO v_stock_record
    FROM MD_Candidate
    WHERE MD_Candidate_ID = v_stock_candidate_id;

    -- 3. Calculate the ATP adjustment (negate the impact)
    -- To REMOVE from ATP, we need to do the OPPOSITE of the normal stock impact
    v_qty_impact := -de_metas_material.MD_Candidate_Get_Stock_Impact(
            v_candidate_record.MD_Candidate_Type,
            v_candidate_record.MD_Candidate_BusinessCase,
            v_candidate_record.Qty,
            v_candidate_record.QtyFulfilled
                     );

    -- 4. Update the STOCK candidate quantity
    v_new_stock_qty := v_stock_record.Qty + v_qty_impact;

    UPDATE MD_Candidate
    SET Qty       = v_new_stock_qty,
        Updated   = NOW(),
        UpdatedBy = 99
    WHERE MD_Candidate_ID = v_stock_candidate_id;

    -- 5. Set the source candidate's Qty to 0
    UPDATE MD_Candidate
    SET Qty          = 0,
        QtyFulfilled = 0,
        Updated      = NOW(),
        UpdatedBy    = 99
    WHERE MD_Candidate_ID = p_MD_Candidate_ID;

    -- 6. Check if MD_Candidate_QtyDetails exist for this candidate
    SELECT EXISTS(SELECT 1
                  FROM MD_Candidate_QtyDetails
                  WHERE MD_Candidate_ID = p_MD_Candidate_ID
                    AND IsActive = 'Y')
    INTO v_qtydetails_exist;

    IF NOT v_qtydetails_exist THEN
        -- 7. Create MD_Candidate_QtyDetails if they don't exist
        -- Find previous STOCK candidate
        SELECT MD_Candidate_ID, Qty
        INTO v_previous_stock_candidate_id, v_previous_stock_qty
        FROM MD_Candidate
        WHERE MD_Candidate_Type = 'STOCK'
          AND M_Product_ID = v_candidate_record.M_Product_ID
          AND M_Warehouse_ID = v_candidate_record.M_Warehouse_ID
          AND StorageAttributesKey = v_candidate_record.StorageAttributesKey
          AND (DateProjected < v_candidate_record.DateProjected
            OR (DateProjected = v_candidate_record.DateProjected AND SeqNo < v_candidate_record.SeqNo))
          AND IsActive = 'Y'
        ORDER BY DateProjected DESC, SeqNo DESC
        LIMIT 1;

        -- Create QtyDetails for previous stock (if found)
        IF v_previous_stock_candidate_id IS NOT NULL THEN
            INSERT INTO MD_Candidate_QtyDetails (MD_Candidate_QtyDetails_ID,
                                                 AD_Client_ID,
                                                 AD_Org_ID,
                                                 Created,
                                                 CreatedBy,
                                                 IsActive,
                                                 MD_Candidate_ID,
                                                 Stock_MD_Candidate_ID,
                                                 Detail_MD_Candidate_ID,
                                                 Qty,
                                                 Updated,
                                                 UpdatedBy)
            SELECT NEXTVAL('MD_Candidate_QtyDetails_seq'),
                   c.AD_Client_ID,
                   c.AD_Org_ID,
                   NOW(),
                   99,
                   'Y',
                   p_MD_Candidate_ID,
                   v_stock_candidate_id,
                   v_previous_stock_candidate_id,
                   COALESCE(v_previous_stock_qty, 0),
                   NOW(),
                   99
            FROM MD_Candidate c
            WHERE c.MD_Candidate_ID = p_MD_Candidate_ID;
        END IF;

        -- Create QtyDetails for current candidate (with qty 0 since we're removing it)
        INSERT INTO MD_Candidate_QtyDetails (MD_Candidate_QtyDetails_ID,
                                             AD_Client_ID,
                                             AD_Org_ID,
                                             Created,
                                             CreatedBy,
                                             IsActive,
                                             MD_Candidate_ID,
                                             Stock_MD_Candidate_ID,
                                             Detail_MD_Candidate_ID,
                                             Qty,
                                             Updated,
                                             UpdatedBy)
        SELECT NEXTVAL('MD_Candidate_QtyDetails_seq'),
               c.AD_Client_ID,
               c.AD_Org_ID,
               NOW(),
               99,
               'Y',
               p_MD_Candidate_ID,
               v_stock_candidate_id,
               p_MD_Candidate_ID,
               0, -- Set to 0 since we're removing from ATP
               NOW(),
               99
        FROM MD_Candidate c
        WHERE c.MD_Candidate_ID = p_MD_Candidate_ID;
    ELSE
        -- 8. Update existing QtyDetails to reflect qty = 0
        UPDATE MD_Candidate_QtyDetails
        SET Qty       = 0,
            Updated   = NOW(),
            UpdatedBy = 99
        WHERE MD_Candidate_ID = p_MD_Candidate_ID
          AND Detail_MD_Candidate_ID = p_MD_Candidate_ID
          AND IsActive = 'Y';
    END IF;

    -- 9. Propagate changes through the entire chain
    -- Process all subsequent MD_Candidates in chronological order
    FOR v_next_candidate_record IN
        SELECT MD_Candidate_ID,
               MD_Candidate_Type,
               MD_Candidate_BusinessCase,
               MD_Candidate_Parent_ID,
               Qty,
               QtyFulfilled,
               DateProjected,
               SeqNo
        FROM MD_Candidate
        WHERE MD_Candidate_Type != 'STOCK'
          AND M_Product_ID = v_candidate_record.M_Product_ID
          AND M_Warehouse_ID = v_candidate_record.M_Warehouse_ID
          AND StorageAttributesKey = v_candidate_record.StorageAttributesKey
          AND (DateProjected > v_candidate_record.DateProjected
            OR (DateProjected = v_candidate_record.DateProjected AND SeqNo > v_candidate_record.SeqNo))
          AND IsActive = 'Y'
        ORDER BY DateProjected ASC, SeqNo ASC
        LOOP

            v_update_chain_counter = v_update_chain_counter + 1;

            -- Find this candidate's STOCK candidate
            v_next_stock_candidate_id := de_metas_material.MD_Candidate_Find_Stock_ID(
                    v_next_candidate_record.MD_Candidate_ID,
                    v_next_candidate_record.MD_Candidate_Parent_ID,
                    v_next_candidate_record.MD_Candidate_Type
                                         );

            -- Check if QtyDetails exist for this candidate
            SELECT EXISTS(SELECT 1
                          FROM MD_Candidate_QtyDetails
                          WHERE MD_Candidate_ID = v_next_candidate_record.MD_Candidate_ID
                            AND IsActive = 'Y')
            INTO v_has_qtydetails;

            IF v_has_qtydetails THEN
                -- Update existing QtyDetails that reference the current STOCK
                UPDATE MD_Candidate_QtyDetails
                SET Qty       = v_new_stock_qty,
                    Detail_MD_Candidate_ID = v_stock_candidate_id,
                    Updated   = NOW(),
                    UpdatedBy = 99
                WHERE MD_Candidate_ID = v_next_candidate_record.MD_Candidate_ID
                  AND Detail_MD_Candidate_ID != v_next_stock_candidate_id
                  AND IsActive = 'Y';
            ELSE
                -- Create 2 QtyDetails records for this candidate:
                -- 1. Reference to previous STOCK (with qty from that previous STOCK record)
                INSERT INTO MD_Candidate_QtyDetails (MD_Candidate_QtyDetails_ID,
                                                     AD_Client_ID,
                                                     AD_Org_ID,
                                                     Created,
                                                     CreatedBy,
                                                     IsActive,
                                                     MD_Candidate_ID,
                                                     Stock_MD_Candidate_ID,
                                                     Detail_MD_Candidate_ID,
                                                     Qty,
                                                     Updated,
                                                     UpdatedBy)
                SELECT NEXTVAL('MD_Candidate_QtyDetails_seq'),
                       c.AD_Client_ID,
                       c.AD_Org_ID,
                       NOW(),
                       99,
                       'Y',
                       v_next_candidate_record.MD_Candidate_ID,
                       v_next_stock_candidate_id,
                       v_stock_candidate_id,
                       prev_stock.Qty, -- Use qty from the previous STOCK record itself
                       NOW(),
                       99
                FROM MD_Candidate c
                         CROSS JOIN MD_Candidate prev_stock
                WHERE c.MD_Candidate_ID = v_next_candidate_record.MD_Candidate_ID
                  AND prev_stock.MD_Candidate_ID = v_stock_candidate_id;

                -- 2. Reference to this candidate's own record (not STOCK, but the candidate itself)
                INSERT INTO MD_Candidate_QtyDetails (MD_Candidate_QtyDetails_ID,
                                                     AD_Client_ID,
                                                     AD_Org_ID,
                                                     Created,
                                                     CreatedBy,
                                                     IsActive,
                                                     MD_Candidate_ID,
                                                     Stock_MD_Candidate_ID,
                                                     Detail_MD_Candidate_ID,
                                                     Qty,
                                                     Updated,
                                                     UpdatedBy)
                SELECT NEXTVAL('MD_Candidate_QtyDetails_seq'),
                       c.AD_Client_ID,
                       c.AD_Org_ID,
                       NOW(),
                       99,
                       'Y',
                       v_next_candidate_record.MD_Candidate_ID,
                       v_next_stock_candidate_id,
                       v_next_candidate_record.MD_Candidate_ID,
                       c.Qty, -- Use qty from the current candidate record
                       NOW(),
                       99
                FROM MD_Candidate c
                WHERE c.MD_Candidate_ID = v_next_candidate_record.MD_Candidate_ID;
            END IF;

            -- Calculate and update this candidate's STOCK Qty
            -- The STOCK qty = previous STOCK qty + current candidate's stock impact

            -- Calculate this candidate's stock impact using helper function
            v_current_candidate_stock_impact := de_metas_material.MD_Candidate_Get_Stock_Impact(
                    v_next_candidate_record.MD_Candidate_Type,
                    v_next_candidate_record.MD_Candidate_BusinessCase,
                    v_next_candidate_record.Qty,
                    v_next_candidate_record.QtyFulfilled
                                                );

            -- Calculate new STOCK qty: previous STOCK qty + this candidate's impact
            v_updated_stock_qty := v_new_stock_qty + v_current_candidate_stock_impact;

            -- Update the STOCK candidate's Qty
            UPDATE MD_Candidate
            SET Qty       = v_updated_stock_qty,
                Updated   = NOW(),
                UpdatedBy = 99
            WHERE MD_Candidate_ID = v_next_stock_candidate_id;

            -- Update variables for next iteration
            v_stock_candidate_id := v_next_stock_candidate_id;
            v_new_stock_qty := v_updated_stock_qty;
        END LOOP;

    -- Return result
    RETURN QUERY SELECT p_MD_Candidate_ID      AS MD_Candidate_ID,
                        v_stock_candidate_id   AS stock_md_candidate_id,
                        v_new_stock_qty        AS current_ATP_qty,
                        v_qty_impact           AS original_md_candidate_qty,
                        v_update_chain_counter AS impacted_md_candidates_count,
                        'SUCCESS: Removed candidate  from ATP. Stock qty adjusted from ' || v_stock_record.Qty || '.';
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Remove_From_ATP(numeric) IS
    'Removes an MD_Candidate from ATP calculations by:
    1. Calculating stock impact based on candidate type, business case, qty, and qtyFulfilled
    2. Negating this impact to remove it from ATP
    3. Setting candidate Qty to 0
    4. Updating the associated STOCK candidate ATP value
    5. Creating/updating MD_Candidate_QtyDetails for the entire chain
    6. Propagating changes chronologically through all subsequent candidates

    Stock Impact Formula:
    - DEMAND, STOCK_UP, INVENTORY_DOWN (STOCK_CHANGE): -qty
    - SUPPLY, INVENTORY_UP (STOCK_CHANGE): qty
    - UNEXPECTED_DECREASE, INVENTORY_DOWN, ATTRIBUTES_CHANGED_FROM: -qtyFulfilled
    - UNEXPECTED_INCREASE, INVENTORY_UP, ATTRIBUTES_CHANGED_TO: qtyFulfilled

    The function uses helper functions:
    - MD_Candidate_Get_Stock_Impact: Calculate stock impact from candidate
    - MD_Candidate_Find_Stock_ID: Find associated STOCK candidate
    - MD_Candidate_Update_QtyDetails: Create/update QtyDetails records (not used, inline logic)

    Parameters:
      p_MD_Candidate_ID - The MD_Candidate_ID to remove from ATP

    Returns:
      removed_candidate_id - The ID that was processed
      stock_candidate_id - The associated STOCK candidate ID
      current_ATP_qty - The new ATP quantity after removal
      qty_adjustment - The quantity adjustment made to ATP
      impacted_md_candidates_count - Number of candidates updated in the chain
      message - Success/error message

    Example usage:
      SELECT * FROM de_metas_material.MD_Candidate_Remove_From_ATP(1000000);
    '
;
