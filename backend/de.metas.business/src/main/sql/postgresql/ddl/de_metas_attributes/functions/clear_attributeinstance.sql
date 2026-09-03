-- Companion to de_metas_attributes.upsert_attributeinstance: sets an attribute's value to NULL on an ASI.
-- Nulls every typed column of the (ASI, attribute) M_AttributeInstance (Value / ValueNumber / ValueDate /
-- M_AttributeValue_ID) — attribute-agnostic, so it also clears List attributes (which upsert cannot, since a NULL
-- list code has no M_AttributeValue to resolve). The instance row is kept (value becomes null); Description is refreshed.
-- No-op when the ASI is null/0 or has no instance for the attribute. Returns the ASI id.
CREATE SCHEMA IF NOT EXISTS de_metas_attributes;

CREATE OR REPLACE FUNCTION de_metas_attributes.clear_attributeinstance(
    p_M_AttributeSetInstance_ID numeric,
    p_M_Attribute_ID            numeric
) RETURNS numeric AS $$
BEGIN
    IF p_M_AttributeSetInstance_ID IS NULL OR p_M_AttributeSetInstance_ID = 0 THEN
        RETURN p_M_AttributeSetInstance_ID;
    END IF;

    UPDATE M_AttributeInstance
       SET Value = NULL, ValueNumber = NULL, ValueDate = NULL, M_AttributeValue_ID = NULL,
           Updated = now(), UpdatedBy = 0
     WHERE M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
       AND M_Attribute_ID = p_M_Attribute_ID;

    UPDATE M_AttributeSetInstance asi
       SET Description = COALESCE((
              SELECT string_agg(
                       COALESCE(av.Name, ai.Value, ai.ValueNumber::text, to_char(ai.ValueDate, 'DD.MM.YYYY')), '_'
                       ORDER BY ai.M_AttributeInstance_ID)
                FROM M_AttributeInstance ai
                LEFT JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
               WHERE ai.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
                 AND ai.IsActive = 'Y'), ''),
           Updated = now(), UpdatedBy = 0
     WHERE asi.M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID;

    RETURN p_M_AttributeSetInstance_ID;
END;
$$ LANGUAGE plpgsql;
