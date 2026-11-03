-- Add missing index on c_invoiceline.m_inoutline_id
-- Needed for any query joining c_invoiceline to m_inoutline via m_inoutline_id (e.g. receipt-based price lookups).
-- Without this, LEFT JOINs on m_inoutline_id trigger sequential scans on c_invoiceline.
CREATE INDEX IF NOT EXISTS c_invoiceline_m_inoutline_id ON c_invoiceline (m_inoutline_id);
