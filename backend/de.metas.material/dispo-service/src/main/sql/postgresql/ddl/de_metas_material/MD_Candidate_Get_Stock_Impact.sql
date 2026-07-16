-- Helper functions for MD_Candidate ATP calculations
-- These are shared between MD_Candidate_Remove_From_ATP and MD_Candidate_Recalculate_ATP_From_Date

-- Function: de_metas_material.MD_Candidate_Get_Stock_Impact
-- Purpose: Calculate the stock impact of a candidate based on its type and quantity
-- This implements the logic from Candidate.getStockImpactPlannedQuantity()

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
