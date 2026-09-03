-- Generic, attribute-agnostic UPSERT of a single M_AttributeInstance.
-- Writes p_value into the typed column dictated by M_Attribute.AttributeValueType
--   S -> Value, N -> ValueNumber, D -> ValueDate, L -> M_AttributeValue_ID (+ Value mirror).
-- Creates the M_AttributeSetInstance on demand when none is passed (null/0) and returns its id.
-- Refreshes M_AttributeSetInstance.Description as a best-effort SQL concat ('_'-joined);
-- this diverges from the Java buildDescription for attributes using a DescriptionPattern or translations.
-- Idempotent on (M_AttributeSetInstance_ID, M_Attribute_ID). No hardcoded attribute / column / host names.
CREATE SCHEMA IF NOT EXISTS de_metas_attributes;

CREATE OR REPLACE FUNCTION de_metas_attributes.upsert_attributeinstance(
    p_M_AttributeSetInstance_ID numeric,
    p_M_Attribute_ID            numeric,
    p_value                     text,
    p_M_AttributeSet_ID         numeric DEFAULT 0,
    p_AD_Client_ID              numeric DEFAULT NULL,
    p_AD_Org_ID                 numeric DEFAULT NULL
) RETURNS numeric AS $$
DECLARE
    v_asi_id       numeric := p_M_AttributeSetInstance_ID;
    v_type         char(1);
    v_client_id    numeric;
    v_org_id       numeric;
    v_attrvalue_id numeric;
BEGIN
    SELECT a.AttributeValueType, a.AD_Client_ID, a.AD_Org_ID
      INTO v_type, v_client_id, v_org_id
      FROM M_Attribute a
     WHERE a.M_Attribute_ID = p_M_Attribute_ID;
    IF NOT FOUND THEN
        RAISE EXCEPTION 'M_Attribute % not found', p_M_Attribute_ID;
    END IF;
    IF v_type NOT IN ('S', 'N', 'D', 'L') THEN
        RAISE EXCEPTION 'Unsupported AttributeValueType=% for M_Attribute % (expected S/N/D/L)', v_type, p_M_Attribute_ID;
    END IF;

    v_client_id := COALESCE(p_AD_Client_ID, v_client_id);
    v_org_id    := COALESCE(p_AD_Org_ID, v_org_id);

    -- create the ASI on demand (M_AttributeSetInstance_ID has no sequence default)
    IF v_asi_id IS NULL OR v_asi_id = 0 THEN
        INSERT INTO M_AttributeSetInstance
            (M_AttributeSetInstance_ID, AD_Client_ID, AD_Org_ID, M_AttributeSet_ID,
             IsActive, Created, CreatedBy, Updated, UpdatedBy)
        VALUES (nextval('m_attributesetinstance_seq'), v_client_id, v_org_id, COALESCE(p_M_AttributeSet_ID, 0),
             'Y', now(), 0, now(), 0)
        RETURNING M_AttributeSetInstance_ID INTO v_asi_id;
    END IF;

    -- list type: resolve the M_AttributeValue from the supplied value (code)
    IF v_type = 'L' THEN
        IF p_value IS NULL THEN
            RAISE EXCEPTION 'NULL value not allowed for list attribute % — use clear_attributeinstance to null a list value', p_M_Attribute_ID;
        END IF;
        SELECT av.M_AttributeValue_ID INTO v_attrvalue_id
          FROM M_AttributeValue av
         WHERE av.M_Attribute_ID = p_M_Attribute_ID AND av.Value = p_value;
        IF NOT FOUND THEN
            RAISE EXCEPTION 'M_AttributeValue with Value=% not found for M_Attribute %', p_value, p_M_Attribute_ID;
        END IF;
    END IF;

    UPDATE M_AttributeInstance ai
       SET Value               = CASE WHEN v_type IN ('S','L') THEN p_value           ELSE ai.Value END,
           ValueNumber         = CASE WHEN v_type = 'N'        THEN p_value::numeric   ELSE ai.ValueNumber END,
           ValueDate           = CASE WHEN v_type = 'D'        THEN p_value::timestamp ELSE ai.ValueDate END,
           M_AttributeValue_ID = CASE WHEN v_type = 'L'        THEN v_attrvalue_id     ELSE ai.M_AttributeValue_ID END,
           Updated = now(), UpdatedBy = 0
     WHERE ai.M_AttributeSetInstance_ID = v_asi_id
       AND ai.M_Attribute_ID = p_M_Attribute_ID;

    IF NOT FOUND THEN
        INSERT INTO M_AttributeInstance
            (M_AttributeSetInstance_ID, M_Attribute_ID, AD_Client_ID, AD_Org_ID,
             IsActive, Created, CreatedBy, Updated, UpdatedBy,
             Value, ValueNumber, ValueDate, M_AttributeValue_ID)
        VALUES (v_asi_id, p_M_Attribute_ID, v_client_id, v_org_id,
             'Y', now(), 0, now(), 0,
             CASE WHEN v_type IN ('S','L') THEN p_value END,
             CASE WHEN v_type = 'N'        THEN p_value::numeric END,
             CASE WHEN v_type = 'D'        THEN p_value::timestamp END,
             CASE WHEN v_type = 'L'        THEN v_attrvalue_id END);
    END IF;

    -- best-effort Description refresh (see header caveat)
    UPDATE M_AttributeSetInstance asi
       SET Description = COALESCE((
              SELECT string_agg(
                       COALESCE(av.Name, ai.Value, ai.ValueNumber::text, to_char(ai.ValueDate, 'DD.MM.YYYY')), '_'
                       ORDER BY ai.M_AttributeInstance_ID)
                FROM M_AttributeInstance ai
                LEFT JOIN M_AttributeValue av ON av.M_AttributeValue_ID = ai.M_AttributeValue_ID
               WHERE ai.M_AttributeSetInstance_ID = v_asi_id
                 AND ai.IsActive = 'Y'), ''),
           Updated = now(), UpdatedBy = 0
     WHERE asi.M_AttributeSetInstance_ID = v_asi_id;

    RETURN v_asi_id;
END;
$$ LANGUAGE plpgsql;
