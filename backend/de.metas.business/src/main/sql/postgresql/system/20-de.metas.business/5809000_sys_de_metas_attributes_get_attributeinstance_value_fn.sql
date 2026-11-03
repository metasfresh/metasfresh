-- Source DDL: backend/de.metas.business/src/main/sql/postgresql/ddl/de_metas_attributes/functions/get_attributeinstance_value.sql
-- Companion reader for de_metas_attributes.upsert_attributeinstance.
-- Returns one attribute's value off an ASI as text, read from the typed column dictated by
-- M_Attribute.AttributeValueType (S -> Value, N -> ValueNumber, D -> ValueDate, L -> M_AttributeValue.Value).
-- Returns NULL when the attribute does not exist or the ASI has no instance for it.
CREATE SCHEMA IF NOT EXISTS de_metas_attributes;

CREATE OR REPLACE FUNCTION de_metas_attributes.get_attributeinstance_value(
    p_M_AttributeSetInstance_ID numeric,
    p_M_Attribute_ID            numeric
) RETURNS text AS $$
DECLARE
    v_type char(1);
    v_out  text;
BEGIN
    SELECT a.AttributeValueType INTO v_type
      FROM M_Attribute a
     WHERE a.M_Attribute_ID = p_M_Attribute_ID;
    IF NOT FOUND THEN
        RETURN NULL;
    END IF;

    SELECT CASE v_type
             WHEN 'S' THEN ai.Value
             WHEN 'L' THEN av.Value
             WHEN 'N' THEN ai.ValueNumber::text
             WHEN 'D' THEN to_char(ai.ValueDate, 'YYYY-MM-DD')
           END
      INTO v_out
      FROM M_AttributeInstance ai
      LEFT JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
     WHERE ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
       AND ai.M_Attribute_ID = p_M_Attribute_ID;

    RETURN v_out;
END;
$$ LANGUAGE plpgsql;
