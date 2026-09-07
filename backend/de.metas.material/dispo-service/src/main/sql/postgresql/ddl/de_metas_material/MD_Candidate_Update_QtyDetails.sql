-- Helper functions for MD_Candidate ATP calculations
-- These are shared between MD_Candidate_Remove_From_ATP and MD_Candidate_Recalculate_ATP_From_Date

-- Function: de_metas_material.MD_Candidate_Get_Stock_Impact
-- Purpose: Calculate the stock impact of a candidate based on its type and quantity
-- This implements the logic from Candidate.getStockImpactPlannedQuantity()

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Get_Stock_Impact(varchar,
                                                                        numeric)
;

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Get_Stock_Impact(varchar,
                                                                        varchar,
                                                                        numeric,
                                                                        numeric)
;

CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Get_Stock_Impact(
    p_candidate_type varchar,
    p_business_case  varchar,
    p_qty            numeric,
    p_qty_fulfilled  numeric
)
    RETURNS numeric
AS
$BODY$
BEGIN
    -- Calculate stock impact based on candidate type and business case
    -- Uses qty for DEMAND/STOCK_UP/SUPPLY and specific INVENTORY cases
    -- Uses qtyFulfilled for UNEXPECTED and other INVENTORY cases
    RETURN CASE
               WHEN p_candidate_type IN ('DEMAND', 'STOCK_UP') OR
                    (p_candidate_type = 'INVENTORY_DOWN' AND p_business_case = 'STOCK_CHANGE')
                   THEN -p_qty

               WHEN p_candidate_type = 'SUPPLY' OR
                    (p_candidate_type = 'INVENTORY_UP' AND p_business_case = 'STOCK_CHANGE')
                   THEN p_qty

               WHEN p_candidate_type IN ('UNEXPECTED_DECREASE', 'INVENTORY_DOWN', 'ATTRIBUTES_CHANGED_FROM')
                   THEN -p_qty_fulfilled

               WHEN p_candidate_type IN ('UNEXPECTED_INCREASE', 'INVENTORY_UP', 'ATTRIBUTES_CHANGED_TO')
                   THEN p_qty_fulfilled

                   ELSE 0
           END;
END;
$BODY$
    LANGUAGE plpgsql IMMUTABLE
;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Get_Stock_Impact(varchar, varchar, numeric, numeric) IS
    'Calculates the stock impact of an MD_Candidate based on its type, business case, qty, and qtyFulfilled.
    Formula:
    - DEMAND, STOCK_UP, INVENTORY_DOWN (STOCK_CHANGE): -qty
    - SUPPLY, INVENTORY_UP (STOCK_CHANGE): qty
    - UNEXPECTED_DECREASE, INVENTORY_DOWN, ATTRIBUTES_CHANGED_FROM: -qtyFulfilled
    - UNEXPECTED_INCREASE, INVENTORY_UP, ATTRIBUTES_CHANGED_TO: qtyFulfilled'
;


-- Function: de_metas_material.MD_Candidate_Get_Effective_Qty
-- Purpose: Get the effective quantity value used for ATP calculations (either qty or qtyFulfilled)

DROP FUNCTION IF EXISTS de_metas_material.MD_Candidate_Get_Effective_Qty(varchar,
                                                                         varchar,
                                                                         numeric,
                                                                         numeric)
;

CREATE OR REPLACE FUNCTION de_metas_material.MD_Candidate_Get_Effective_Qty(
    p_candidate_type varchar,
    p_business_case  varchar,
    p_qty            numeric,
    p_qty_fulfilled  numeric
)
    RETURNS numeric
AS
$BODY$
BEGIN
    -- Return the quantity field that is actually used for ATP calculations
    RETURN CASE
               WHEN p_candidate_type IN ('DEMAND', 'STOCK_UP') OR
                    (p_candidate_type = 'INVENTORY_DOWN' AND p_business_case = 'STOCK_CHANGE') OR
                    p_candidate_type = 'SUPPLY' OR
                    (p_candidate_type = 'INVENTORY_UP' AND p_business_case = 'STOCK_CHANGE')
                   THEN p_qty

               WHEN p_candidate_type IN ('UNEXPECTED_DECREASE', 'INVENTORY_DOWN', 'ATTRIBUTES_CHANGED_FROM',
                                         'UNEXPECTED_INCREASE', 'INVENTORY_UP', 'ATTRIBUTES_CHANGED_TO')
                   THEN p_qty_fulfilled

                   ELSE p_qty
           END;
END;
$BODY$
    LANGUAGE plpgsql IMMUTABLE
;

COMMENT ON FUNCTION de_metas_material.MD_Candidate_Get_Effective_Qty(varchar, varchar, numeric, numeric) IS
    'Returns the effective quantity value used for ATP calculations.
    Returns qty for DEMAND/STOCK_UP/SUPPLY and INVENTORY with STOCK_CHANGE.
    Returns qtyFulfilled for UNEXPECTED and other INVENTORY cases.'
;


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


/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2026 metas GmbH
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
