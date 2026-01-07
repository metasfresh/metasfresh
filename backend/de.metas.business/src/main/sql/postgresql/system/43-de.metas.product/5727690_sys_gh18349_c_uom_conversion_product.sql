DROP INDEX IF EXISTS c_uom_conversion_product
;

CREATE UNIQUE INDEX c_uom_conversion_product ON c_uom_conversion (c_uom_id, c_uom_to_id, m_product_id) WHERE IsActive = 'Y'
;
