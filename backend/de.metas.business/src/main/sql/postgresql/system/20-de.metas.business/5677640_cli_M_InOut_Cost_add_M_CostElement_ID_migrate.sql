UPDATE C_Order_Cost t
SET M_CostElement_ID=(SELECT ct.m_costelement_id FROM c_cost_type ct WHERE ct.c_cost_type_id = t.c_cost_type_id)
WHERE t.M_CostElement_ID IS NULL
;

UPDATE M_InOut_Cost t
SET M_CostElement_ID=(SELECT ct.m_costelement_id FROM c_cost_type ct WHERE ct.c_cost_type_id = t.c_cost_type_id)
WHERE t.M_CostElement_ID IS NULL
;

