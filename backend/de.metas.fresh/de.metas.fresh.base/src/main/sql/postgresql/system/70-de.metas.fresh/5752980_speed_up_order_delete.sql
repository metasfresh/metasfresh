CREATE INDEX IF NOT EXISTS fact_acct_c_orderso_id
    ON fact_acct (c_orderso_id)
;

CREATE INDEX IF NOT EXISTS c_orderline_ref_proposalline_id
    ON c_orderline (ref_proposalline_id)
;

CREATE INDEX IF NOT EXISTS c_orderline_c_orderso_id
    ON c_orderline (c_orderso_id)
;

CREATE INDEX IF NOT EXISTS m_inoutline_c_order_id
    ON m_inoutline (c_order_id)
;

CREATE INDEX IF NOT EXISTS m_inoutline_c_orderso_id
    ON m_inoutline (c_orderso_id)
;

CREATE INDEX IF NOT EXISTS c_invoiceline_c_orderso_id
    ON c_invoiceline (c_orderso_id)
;

CREATE INDEX IF NOT EXISTS c_invoiceline_c_order_id
    ON c_invoiceline (c_order_id)
;

CREATE INDEX IF NOT EXISTS c_order_c_frameagreement_order_id
    ON c_order (c_frameagreement_order_id)
;

CREATE INDEX IF NOT EXISTS c_order_ref_followuporder_id
    ON c_order (ref_followuporder_id)
;

CREATE INDEX IF NOT EXISTS c_order_link_order_id
    ON c_order (link_order_id)
;

CREATE INDEX IF NOT EXISTS c_order_ref_proposal_id
    ON c_order (ref_proposal_id)
;

CREATE INDEX IF NOT EXISTS c_order_ref_order_id
    ON c_order (ref_order_id)
;
