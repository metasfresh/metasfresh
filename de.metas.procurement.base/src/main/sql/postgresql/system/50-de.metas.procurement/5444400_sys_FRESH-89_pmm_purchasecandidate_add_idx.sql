
CREATE INDEX pmm_purchasecandidate_m_product_id
   ON pmm_purchasecandidate (m_product_id ASC NULLS LAST);

CREATE INDEX pmm_purchasecandidate_datepromised
   ON pmm_purchasecandidate (datepromised ASC NULLS LAST);
