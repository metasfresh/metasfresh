
-- make it not null
ALTER TABLE dd_order
    ALTER COLUMN m_warehouse_from_id SET NOT NULL
;

-- drop default since it will be added as a Validation Rule in another migration script
ALTER TABLE dd_order
    ALTER COLUMN m_warehouse_from_id DROP DEFAULT
;
