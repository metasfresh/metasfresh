DROP FUNCTION IF EXISTS Report.fresh_attributes(IN p_M_AttributeSetInstance_ID numeric);

CREATE OR REPLACE FUNCTION Report.fresh_attributes(IN p_M_AttributeSetInstance_ID numeric)
    RETURNS TABLE (
                      ai_value character varying,
                      m_attributesetinstance_id numeric,
                      at_value character varying,
                      at_name character varying,
                      at_isattrdocumentrelevant char(1),
                      isprintedindocument char(1))
AS
$$
SELECT *
FROM Report.fresh_attributes(p_M_AttributeSetInstance_ID,'de_DE')
$$
    LANGUAGE sql
    STABLE;
