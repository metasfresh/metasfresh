create index if not exists idx_m_matchinv_m_inout_id
on
m_matchinv(m_inout_id)
;

create index if not exists idx_m_matchinv_c_invoice_id 
on
m_matchinv(c_invoice_id)
;

