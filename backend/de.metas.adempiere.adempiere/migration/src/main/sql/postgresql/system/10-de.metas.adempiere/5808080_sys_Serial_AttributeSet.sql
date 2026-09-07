-- Serial No Picking — a ready-to-use "Serial" attribute set containing the standard SerialNo attribute.
-- Serial-no products (M_Product.IsSerialNoPicked='Y') must use an attribute set that supports SerialNo so the
-- picked HU carries the SerialNo attribute; this ships a default one customers (and the E2E tests) can assign.
-- AD_Client_ID=0 (system) on purpose: a reusable building block visible to products on ANY client — the
-- SerialNo M_Attribute it references is itself system-level, and other system-level attribute sets already exist.

INSERT INTO M_AttributeSet (M_AttributeSet_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,Name,IsInstanceAttribute,MandatoryType)
VALUES (540022 /*From ID Server*/,0,0,'Y',TO_TIMESTAMP('2026-06-16 03:00:00','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-16 03:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Serial','Y','N')
;

-- link the standard SerialNo attribute (M_Attribute_ID=540034, value 'SerialNo') as an instance attribute
INSERT INTO M_AttributeUse (M_AttributeUse_ID,M_AttributeSet_ID,M_Attribute_ID,AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,SeqNo)
VALUES (540101 /*From ID Server*/,540022,540034,0,0,'Y',TO_TIMESTAMP('2026-06-16 03:00:10','YYYY-MM-DD HH24:MI:SS'),100,TO_TIMESTAMP('2026-06-16 03:00:10','YYYY-MM-DD HH24:MI:SS'),100,10)
;
