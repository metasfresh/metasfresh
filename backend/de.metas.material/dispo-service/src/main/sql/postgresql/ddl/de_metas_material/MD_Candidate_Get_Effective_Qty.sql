-- Helper functions for MD_Candidate ATP calculations
-- These are shared between MD_Candidate_Remove_From_ATP and MD_Candidate_Recalculate_ATP_From_Date

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
