ALTER INDEX m_matchinv_ship RENAME TO m_matchinv_invoiceLine_and_inoutLine;

-- drop index if exists m_matchinv_inoutLine;
CREATE INDEX m_matchinv_inoutLine on m_matchinv(m_inoutline_id);

