-- Partial covering index for the picking source-HU search: makes the per-candidate-HU
-- M_HU_Attribute EXISTS (M_HU_ID, M_Attribute_ID, ValueNumber, IsActive='Y') index-only.

CREATE INDEX IF NOT EXISTS m_hu_attribute_hu_attr_valnum
    ON M_HU_Attribute (M_HU_ID, M_Attribute_ID, ValueNumber)
    WHERE IsActive = 'Y';
