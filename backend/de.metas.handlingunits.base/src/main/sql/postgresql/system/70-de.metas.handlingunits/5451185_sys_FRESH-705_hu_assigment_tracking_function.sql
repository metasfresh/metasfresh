/*
This function finds another M_HU_Assignment for inout which has the same TU as your M_HU_Assignment's TU
OR other TUs which are split from your M_HU_Assignment's TU
*/

DROP FUNCTION IF EXISTS "de.metas.handlingunits".hu_assigment_tracking(in m_hu_assignment_id numeric);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".hu_assigment_tracking(in m_hu_assignment_id numeric)
RETURNS TABLE
	(m_hu_assignment_id numeric,
	record_id numeric)
AS
$$ 

SELECT  c_huas.m_hu_assignment_id as m_hu_assignment_id, 
	c_huas.record_id as record_id

FROM M_HU_Assignment c_huas

INNER JOIN M_HU_Assignment huas ON huas.m_hu_assignment_id = $1

WHERE c_huas.isActive = 'Y' and c_huas.m_hu_assignment_id != $1 AND c_huas.m_tu_hu_id = huas.m_tu_hu_id 
	AND c_huas.ad_table_id = get_table_id('M_InOutLine')
	AND c_huas.M_TU_HU_ID is not null 
UNION ALL

(

SELECT  c_huas.m_hu_assignment_id as m_hu_assignment_id, 
	c_huas.record_id as record_id

FROM M_HU_Assignment c_huas
INNER JOIN M_HU_Assignment huas ON huas.m_hu_assignment_id = $1
INNER JOIN M_HU_Trx_Line trx ON huas.M_TU_HU_ID = trx.M_HU_ID AND trx.isActive = 'Y'

WHERE c_huas.isActive = 'Y' and c_huas.m_hu_assignment_id != $1 
	AND c_huas.m_tu_hu_id = ANY  (ARRAY( SELECT distinct hu_id from "de.metas.handlingunits".recursive_hu_trace(trx.M_HU_Trx_Line_ID::integer)))
	AND c_huas.ad_table_id = get_table_id('M_InOutLine')
	AND c_huas.M_TU_HU_ID is not null 
)
$$
LANGUAGE sql STABLE;
