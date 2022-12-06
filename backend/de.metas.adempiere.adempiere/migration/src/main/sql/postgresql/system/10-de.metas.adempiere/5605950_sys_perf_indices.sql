
DROP INDEX if exists public.cm_chat_record_id;
CREATE INDEX cm_chat_record_id ON cm_chat (record_id);
DROP INDEX if exists c_invoice_m_inout_id;
CREATE INDEX c_invoice_m_inout_id ON c_invoice (m_inout_id);
DROP INDEX if exists m_inout_ref_inout_id;
CREATE INDEX m_inout_ref_inout_id ON m_inout (ref_inout_id);
DROP INDEX if exists m_package_m_inout_id;
CREATE INDEX m_package_m_inout_id ON m_package (m_inout_id);
DROP INDEX if exists m_shippingpackage_m_inout_id;
CREATE INDEX m_shippingpackage_m_inout_id ON m_shippingpackage (m_inout_id);
DROP INDEX if exists r_request_m_inout_id;
CREATE INDEX r_request_m_inout_id ON r_request (m_inout_id);
DROP INDEX if exists m_material_balance_detail_m_inout_id;
CREATE INDEX m_material_balance_detail_m_inout_id ON m_material_balance_detail (m_inout_id);
