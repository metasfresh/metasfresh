DROP FUNCTION IF EXISTS getc_bpartner_product_vendor(numeric);

create or replace function getc_bpartner_product_vendor(p_m_product_id numeric)
  returns TABLE(productno character varying, productname character varying, c_bpartner_product_id numeric)
stable
language sql
as $$
SELECT
  DISTINCT ON (iscurrentvendor)
  ProductNo,
  ProductName,
  C_BPartner_Product_ID
FROM C_BPartner_Product bpp
WHERE true
      AND bpp.M_Product_id = p_M_Product_id
      AND bpp.usedforvendor = 'Y'
ORDER BY bpp.iscurrentvendor desc, SeqNo, ProductNo, ProductName
LIMIT 1
$$;

comment on function getc_bpartner_product_vendor(p_m_product_id numeric)
is 'This function returns a VENDOR C_BPartner_Product record with a matching  given product-Id
If multiple C_BPartner_Product records match, the one with iscurrentvendor on Y and the lowest SeqNo is returned.
';
