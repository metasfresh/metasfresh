CREATE INDEX IF NOT EXISTS EDI_Desadv_Pack_M_HU_ID on edi_desadv_pack (m_hu_id)
;

CREATE INDEX IF NOT EXISTS M_Package_HU_HU_ID on M_Package_HU (m_hu_id)
;

CREATE INDEX IF NOT EXISTS m_hu_m_hu_pi_version_id ON m_hu(m_hu_pi_version_id)
;

CREATE INDEX IF NOT EXISTS c_order_edi_desadv_id ON c_order(edi_desadv_id)
;

CREATE INDEX IF NOT EXISTS edi_desadv_pack_item_edi_desadv_pack_id ON edi_desadv_pack_item(edi_desadv_pack_id)
;

-- old
-- CREATE INDEX m_hu_commonsearch
--     ON m_hu (ad_client_id, m_locator_id, c_bpartner_id, hustatus, m_hu_pi_version_id)
--     WHERE (isactive = 'Y'::bpchar)
-- ;
DROP INDEX IF EXISTS m_hu_commonsearch;
CREATE INDEX m_hu_commonsearch
    ON m_hu (m_hu_pi_version_id, hustatus, m_locator_id,  c_bpartner_id, ad_client_id )
    WHERE (isactive = 'Y'::bpchar)
;

CREATE INDEX IF NOT EXISTS edi_desadv_pack_item_edi_desadvline_id ON edi_desadv_pack_item(edi_desadvline_id)
;
