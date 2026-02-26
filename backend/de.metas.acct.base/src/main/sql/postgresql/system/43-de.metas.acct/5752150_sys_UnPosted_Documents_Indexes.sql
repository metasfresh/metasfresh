
-- gl_journal
CREATE INDEX idx_gl_journal_unposted
    ON gl_journal (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- sap_gljournal
CREATE INDEX idx_sap_gljournal_unposted
    ON sap_gljournal (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- c_projectissue
CREATE INDEX idx_c_projectissue_unposted
    ON c_projectissue (posted, processed)
    WHERE posted <> 'Y' AND processed = 'Y';

-- c_invoice
CREATE INDEX idx_c_invoice_unposted
    ON c_invoice (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- m_inout
CREATE INDEX idx_m_inout_unposted
    ON m_inout (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- m_inventory
CREATE INDEX idx_m_inventory_unposted
    ON m_inventory (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- m_movement
CREATE INDEX idx_m_movement_unposted
    ON m_movement (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- c_cash
CREATE INDEX idx_c_cash_unposted
    ON c_cash (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- c_payment
CREATE INDEX idx_c_payment_unposted
    ON c_payment (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- c_allocationhdr
CREATE INDEX idx_c_allocationhdr_unposted
    ON c_allocationhdr (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- c_bankstatement
CREATE INDEX idx_c_bankstatement_unposted
    ON c_bankstatement (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- m_matchinv
CREATE INDEX idx_m_matchinv_unposted
    ON m_matchinv (posted, processed)
    WHERE posted <> 'Y' AND processed = 'Y';

-- m_matchpo
CREATE INDEX idx_m_matchpo_unposted
    ON m_matchpo (posted, processed)
    WHERE posted <> 'Y' AND processed = 'Y';

-- c_order
CREATE INDEX idx_c_order_unposted
    ON c_order (posted, processed)
    WHERE posted <> 'Y' AND processed = 'Y' AND docstatus NOT IN ('VO', 'WP');

-- m_requisition
CREATE INDEX idx_m_requisition_unposted
    ON m_requisition (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- pp_order
CREATE INDEX idx_pp_order_unposted
    ON pp_order (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- pp_cost_collector
CREATE INDEX idx_pp_cost_collector_unposted
    ON pp_cost_collector (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus <> 'VO' AND processed = 'Y';

-- m_costrevaluation
CREATE INDEX idx_m_costrevaluation_unposted
    ON m_costrevaluation (posted, docstatus, processed)
    WHERE posted <> 'Y' AND docstatus IN ('CO', 'CL', 'RE') AND processed = 'Y';