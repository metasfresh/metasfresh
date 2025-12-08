DROP INDEX IF EXISTS Record_Attribute_Parent
;

CREATE INDEX Record_Attribute_Parent ON Record_Attribute (ad_table_id, record_id, m_attributeset_includedtab_id)
;

DROP INDEX IF EXISTS Record_Attribute_attribute
;

CREATE UNIQUE INDEX Record_Attribute_attribute ON Record_Attribute (ad_table_id, record_id, m_attributeset_includedtab_id, m_attribute_id)
;

