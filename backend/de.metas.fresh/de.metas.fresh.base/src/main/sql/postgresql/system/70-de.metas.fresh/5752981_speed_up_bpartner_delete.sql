CREATE INDEX IF NOT EXISTS fact_acct_c_bpartner2_id
    ON fact_acct (c_bpartner2_id)
;

CREATE INDEX IF NOT EXISTS fact_acct_c_bpartner_id
    ON fact_acct (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS m_inoutline_c_bpartner2_id
    ON m_inoutline (c_bpartner2_id)
;

CREATE INDEX IF NOT EXISTS c_orderline_c_bpartner2_id
    ON c_orderline (c_bpartner2_id)
;

CREATE INDEX IF NOT EXISTS c_invoiceline_c_bpartner2_id
    ON c_invoiceline (c_bpartner2_id)
;

CREATE INDEX IF NOT EXISTS m_hu_snapshot_c_bpartner_id
    ON m_hu_snapshot (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS c_orderline_c_bpartner_vendor_id
    ON c_orderline (c_bpartner_vendor_id)
;

CREATE INDEX IF NOT EXISTS m_inoutline_subproducer_bpartner_id
    ON m_inoutline (subproducer_bpartner_id)
;

CREATE INDEX IF NOT EXISTS m_shipmentschedule_c_bpartner_id
    ON m_shipmentschedule (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_c_bpartner_salesrep_internal_id
    ON c_olcand (c_bpartner_salesrep_internal_id)
;

CREATE INDEX IF NOT EXISTS c_printing_queue_bill_bpartner_id
    ON c_printing_queue (bill_bpartner_id)
;

CREATE INDEX IF NOT EXISTS ad_archive_c_bpartner_id
    ON ad_archive (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_dropship_bpartner_override_id
    ON c_olcand (dropship_bpartner_override_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_c_bpartner_salesrep_id
    ON c_olcand (c_bpartner_salesrep_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_handover_partner_override_id
    ON c_olcand (handover_partner_override_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_c_bpartner_override_id
    ON c_olcand (c_bpartner_override_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_dropship_bpartner_id
    ON c_olcand (dropship_bpartner_id)
;

CREATE INDEX IF NOT EXISTS m_shipmentschedule_c_bpartner_override_id
    ON m_shipmentschedule (c_bpartner_override_id)
;

CREATE INDEX IF NOT EXISTS c_printing_queue_c_bpartner_id
    ON c_printing_queue (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS c_doc_outbound_log_line_c_bpartner_id
    ON c_doc_outbound_log_line (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_handover_partner_id
    ON c_olcand (handover_partner_id)
;

CREATE INDEX IF NOT EXISTS m_movement_c_bpartner_id
    ON m_movement (c_bpartner_id)
;

CREATE INDEX IF NOT EXISTS m_shipmentschedule_c_bpartner_vendor_id
    ON m_shipmentschedule (c_bpartner_vendor_id)
;

CREATE INDEX IF NOT EXISTS c_olcand_c_bpartner_id
    ON c_olcand (c_bpartner_id)
;
