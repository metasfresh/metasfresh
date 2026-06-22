-- Copy-on-write helper for the immutable M_AttributeSetInstance: clones an ASI into a brand-new one.
-- Creates a new M_AttributeSetInstance copying the header (client/org/attribute-set/description) and duplicates every
-- M_AttributeInstance row, then returns the new id. Use this before changing an attribute on a host whose ASI may be
-- shared: clone → apply the change to the copy → reassign the host's M_AttributeSetInstance_ID, leaving the original ASI
-- (which other records may reference) untouched. No-op passthrough when the input is null/0 or the source is not found.
CREATE SCHEMA IF NOT EXISTS de_metas_attributes;

CREATE OR REPLACE FUNCTION de_metas_attributes.cloneASI(
    p_M_AttributeSetInstance_ID numeric
) RETURNS numeric AS $$
DECLARE
    v_new numeric;
BEGIN
    IF p_M_AttributeSetInstance_ID IS NULL OR p_M_AttributeSetInstance_ID = 0 THEN
        RETURN p_M_AttributeSetInstance_ID;
    END IF;

    INSERT INTO M_AttributeSetInstance
        (M_AttributeSetInstance_ID, AD_Client_ID, AD_Org_ID, M_AttributeSet_ID, Description,
         IsActive, Created, CreatedBy, Updated, UpdatedBy)
    SELECT nextval('m_attributesetinstance_seq'), AD_Client_ID, AD_Org_ID, M_AttributeSet_ID, Description,
           'Y', now(), 0, now(), 0
      FROM M_AttributeSetInstance
     WHERE M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID
    RETURNING M_AttributeSetInstance_ID INTO v_new;

    IF v_new IS NULL THEN
        RETURN p_M_AttributeSetInstance_ID;   -- source not found → nothing cloned
    END IF;

    INSERT INTO M_AttributeInstance
        (M_AttributeSetInstance_ID, M_Attribute_ID, AD_Client_ID, AD_Org_ID,
         IsActive, Created, CreatedBy, Updated, UpdatedBy,
         Value, ValueNumber, ValueDate, M_AttributeValue_ID)
    SELECT v_new, M_Attribute_ID, AD_Client_ID, AD_Org_ID,
           IsActive, now(), 0, now(), 0,
           Value, ValueNumber, ValueDate, M_AttributeValue_ID
      FROM M_AttributeInstance
     WHERE M_AttributeSetInstance_ID = p_M_AttributeSetInstance_ID;

    RETURN v_new;
END;
$$ LANGUAGE plpgsql;
