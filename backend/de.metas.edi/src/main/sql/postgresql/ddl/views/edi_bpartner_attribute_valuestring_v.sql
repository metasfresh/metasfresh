DROP VIEW IF EXISTS edi_bpartner_attribute_valuestring_v
;

CREATE OR REPLACE VIEW edi_bpartner_attribute_valuestring_v AS
SELECT partner.value AS bpartner_value,
       partner.ad_org_id,
       partner.ad_client_id,
       attr.value    AS attr_value,
       rattr.valuestring
FROM c_bpartner partner
         INNER JOIN
     record_attribute rattr
     ON rattr.record_id = partner.c_bpartner_id and ad_table_id = get_table_id('C_BPartner')
         INNER JOIN m_attribute attr ON rattr.m_attribute_id = attr.m_attribute_id
         INNER JOIN M_AttributeSet_IncludedTab inclTab ON rattr.m_attributeset_includedtab_id = inclTab.m_attributeset_includedtab_id
WHERE inclTab.m_attributeset_id = 540019
;
