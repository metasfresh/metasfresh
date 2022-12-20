UPDATE m_product_trl
SET technicaldescription = m_product.technicaldescription
FROM m_product
WHERE m_product_trl.m_product_id = m_product.m_product_id
  AND LENGTH(TRIM(m_product.technicaldescription)) > 0
;
