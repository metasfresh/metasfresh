CREATE OR REPLACE VIEW report.fresh_Pricelist_Version_Val_Rule AS
SELECT plv.m_pricelist_version_id,
       bp.c_bpartner_id,
       plv.validfrom,
       plv.name,
       pl.issopricelist AS issotrx
FROM c_bpartner bp
         LEFT JOIN c_bp_group bpg ON bp.c_bp_group_id = bpg.c_bp_group_id
         LEFT JOIN m_pricelist pl ON bp.m_pricingsystem_id = pl.m_pricingsystem_id
         LEFT JOIN m_pricelist_version plv ON pl.m_pricelist_id = plv.m_pricelist_id
WHERE plv.IsActive = 'Y'::bpchar
;


COMMENT ON VIEW report.fresh_Pricelist_Version_Val_Rule IS 'Returns all currently valid C_PriceList_Version_IDs for all C_BPartner_ID along with the valid-from-date and price list version name'
;

