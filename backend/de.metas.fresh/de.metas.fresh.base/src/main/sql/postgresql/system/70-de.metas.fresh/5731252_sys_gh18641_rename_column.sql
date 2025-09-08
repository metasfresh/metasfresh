ALTER TABLE m_product
    RENAME COLUMN netweight TO grossweight
;

ALTER TABLE m_product
    RENAME COLUMN NetWeight_UOM_ID TO GrossWeight_UOM_ID
;