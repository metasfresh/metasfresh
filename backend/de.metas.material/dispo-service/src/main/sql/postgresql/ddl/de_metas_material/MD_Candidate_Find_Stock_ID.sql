-- Helper functions for MD_Candidate ATP calculations
-- These are shared between MD_Candidate_Remove_From_ATP and MD_Candidate_Recalculate_ATP_From_Date

-- Function: de_metas_material.MD_Candidate_Find_Stock_ID
-- Purpose: Find the STOCK candidate ID associated with a non-STOCK candidate

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Find_Stock_ID(numeric,
                                                                     numeric,
                                                                     varchar)
;

CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Find_Stock_ID(
    p_candidate_id   numeric,
    p_parent_id      numeric,
    p_candidate_type varchar
)
    RETURNS numeric
AS
$BODY$
DECLARE
    v_stock_candidate_id numeric;
BEGIN
    -- SUPPLY candidates have STOCK as parent, DEMAND candidates have STOCK as child
    IF p_candidate_type IN ('SUPPLY', 'INVENTORY_UP', 'UNEXPECTED_INCREASE') THEN
        -- Stock is the parent
        RETURN p_parent_id;
    ELSE
        -- Stock is the child - find it
        SELECT MD_Candidate_ID
        INTO v_stock_candidate_id
        FROM MD_Candidate
        WHERE MD_Candidate_Parent_ID = p_candidate_id
          AND MD_Candidate_Type = 'STOCK'
          AND IsActive = 'Y'
        LIMIT 1;

        RETURN v_stock_candidate_id;
    END IF;
END;
$BODY$
    LANGUAGE plpgsql STABLE
;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Find_Stock_ID(numeric, numeric, varchar) IS
    'Finds the STOCK candidate ID associated with a non-STOCK candidate.
    - For SUPPLY/INVENTORY_UP/UNEXPECTED_INCREASE: STOCK is the parent
    - For DEMAND and others: STOCK is the child'
;

-- Function: de_metas_material.MD_Candidate_Update_QtyDetails
-- Purpose: Create or update MD_Candidate_QtyDetails for a candidate

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Update_QtyDetails(numeric,
                                                                         numeric,
                                                                         numeric,
                                                                         numeric,
                                                                         numeric)
;

CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Update_QtyDetails(
    p_candidate_id                numeric,
    p_stock_candidate_id          numeric,
    p_previous_stock_candidate_id numeric,
    p_previous_stock_qty          numeric,
    p_current_candidate_qty       numeric
)
    RETURNS void
AS
$BODY$
DECLARE
    v_has_qtydetails boolean;
BEGIN
    -- Check if QtyDetails exist for this candidate
    SELECT EXISTS(SELECT 1
                  FROM MD_Candidate_QtyDetails
                  WHERE MD_Candidate_ID = p_candidate_id
                    AND IsActive = 'Y')
    INTO v_has_qtydetails;

    IF v_has_qtydetails THEN
        -- Update existing QtyDetails that reference the previous STOCK
        UPDATE MD_Candidate_QtyDetails
        SET Qty       = p_previous_stock_qty,
            Updated   = NOW(),
            UpdatedBy = 99
        WHERE MD_Candidate_ID = p_candidate_id
          AND Detail_MD_Candidate_ID = p_previous_stock_candidate_id
          AND IsActive = 'Y';
    ELSE
        -- Create 2 QtyDetails records for this candidate:
        -- 1. Reference to previous STOCK (if it exists)
        IF p_previous_stock_candidate_id IS NOT NULL THEN
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
                   p_candidate_id,
                   p_stock_candidate_id,
                   p_previous_stock_candidate_id,
                   p_previous_stock_qty,
                   NOW(),
                   99
            FROM MD_Candidate c
            WHERE c.MD_Candidate_ID = p_candidate_id;
        END IF;

        -- 2. Reference to current candidate's own record
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
               p_candidate_id,
               p_stock_candidate_id,
               p_candidate_id,
               p_current_candidate_qty,
               NOW(),
               99
        FROM MD_Candidate c
        WHERE c.MD_Candidate_ID = p_candidate_id;
    END IF;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Update_QtyDetails(numeric, numeric, numeric, numeric, numeric) IS
    'Creates or updates MD_Candidate_QtyDetails for a candidate.
    Creates two records:
    1. Reference to previous STOCK candidate (with its qty)
    2. Reference to current candidate itself (with its qty)'
;
