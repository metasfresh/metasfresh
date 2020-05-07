DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.get_attributes_value(IN M_AttributeSetInstance_ID numeric);
CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.get_attributes_value(IN M_AttributeSetInstance_ID numeric)
RETURNS TABLE ( 
attributes_value text
)
AS
$$
	SELECT	String_agg ( ai_value, ', ' ORDER BY Length(ai_value), ai_value ) AS Attributes
	FROM Report.fresh_Attributes
	WHERE M_AttributeSetInstance_ID = $1
	GROUP BY M_AttributeSetInstance_ID
$$

LANGUAGE sql STABLE;	