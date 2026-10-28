
CREATE INDEX IF NOT EXISTS m_hu_attribute_externalbarcode_lookup
    ON m_hu_attribute (m_attribute_id, value)
    WHERE m_attribute_id = 540174;
