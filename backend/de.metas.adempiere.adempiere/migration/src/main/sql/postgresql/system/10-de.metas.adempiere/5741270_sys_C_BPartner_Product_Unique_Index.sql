
-- backup

CREATE TABLE backup.c_bpartner_product_06122024 AS SELECT * FROM C_BPartner_Product;


-- fix table



CREATE TABLE fix.C_BPartner_Product_Duplicates_06122024 AS
SELECT C_BPartner_Product_ID,
       c_bpartner_id,
       m_product_id,
       usedforcustomer,
       usedforvendor

FROM c_bpartner_product bpp
WHERE isactive = 'Y'
  AND EXISTS(SELECT 1
             FROM c_bpartner_product bpp_duplicate
             WHERE
                 bpp_duplicate.isactive = 'Y'
               AND bpp.c_bpartner_id = bpp_duplicate.c_bpartner_id
               AND bpp.m_product_id = bpp_duplicate.m_product_id
               AND bpp.usedforcustomer = bpp_duplicate.usedforcustomer
               AND bpp.usedforvendor = bpp_duplicate.usedforvendor
               AND bpp_duplicate.c_bpartner_product_id<bpp.c_bpartner_product_id

                 );


-- select * from fix.C_BPartner_Product_Duplicates_06122024


-- deactivate duplicates 


UPDATE c_bpartner_product SET IsActive = 'N' WHERE c_bpartner_product_id in (select c_bpartner_product_id from fix.C_BPartner_Product_Duplicates_06122024);

-- index
CREATE UNIQUE INDEX idx_unique_cbpartner_product_composite
ON c_bpartner_product (
    COALESCE(c_bpartner_id, 0::numeric),
    COALESCE(m_product_id, 0::numeric),
    COALESCE(usedforcustomer, 'N'::char),
    COALESCE(usedforvendor, 'N'::char)
)
WHERE isactive = 'Y';

