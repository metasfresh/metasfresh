
create index if not exists m_packageline_m_package_id
    on m_packageline (m_package_id);

create index if not exists m_package_hu_m_package_id
    on m_package_hu (m_package_id);

create index if not exists m_shippingpackage_m_shipperttransportation_id
    on m_shippingpackage (m_shippertransportation_id);

create index if not exists m_shippingpackage_m_shippertransportation_id
    on m_shippingpackage (m_shippertransportation_id);


DROP INDEX IF EXISTS m_product_c_uom_id;
CREATE INDEX m_product_m_product_id_c_uom_id
    ON m_product (m_product_id, c_uom_id)
;

COMMENT ON INDEX m_product_m_product_id_c_uom_id IS 'The purpose of this index is to support cases where products are left-joined just because their C_UOM_ID is needed.
Concrete example: MD_Stock_From_HUs_V'
;

