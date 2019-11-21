/* 
* Here is a link with an sql that shows how to create product prices http://docs.metasfresh.org/sql_collection/m_productprice.html
*
* 
* Creates M_ProductPrice records with price 0, for the given M_PriceList_Version_ID and C_TaxCategory_ID
* The default values are for plv Testpreise Kunden (Deutschland) Deutschland EUR and tax category Normaler Steuersatz 19% (Deutschland)
*/
CREATE OR REPLACE FUNCTION createProductPricesRecords(p_M_PriceList_Version_ID  numeric default 2002141, p_C_TaxCategory_ID numeric default 1000009) RETURNS VOID AS $$
BEGIN

with rec as
(
    select
      p.M_Product_id,
      pp.m_pricelist_version_id,
      pp.pricelist,
      pp.pricestd,
      pp.pricelimit,
      pp.c_taxcategory_id,
      pp.c_uom_id
    from M_product p
      left join M_productPrice pp on p.m_product_id = pp.m_product_id and pp.m_pricelist_version_id = p_M_PriceList_Version_ID
    where pp.m_product_id is null
)

INSERT INTO M_ProductPrice (M_PriceList_Version_ID, C_UOM_ID, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive, PriceList, PriceStd, SeqNo, M_Product_ID, C_TaxCategory_ID, M_ProductPrice_ID)
  select
    p_M_PriceList_Version_ID,
    100,
    1000000,
    1000000,
    now(),
    99,
    now(),
    99,
    'Y',
    0                             as PriceList,
    0                             as pricestd,
    10                            as seqno,
    M_product_id,
    p_C_TaxCategory_ID                       as c_taxcategory_id,
    nextval('M_ProductPrice_seq') as M_ProductPrice_ID
  from rec;
END; $$
LANGUAGE PLPGSQL;

COMMENT ON FUNCTION createProductPricesRecords(numeric, numeric) IS 'Creates M_ProductPrice records with price 0, for the given M_PriceList_Version_ID and C_TaxCategory_ID.
The default values are for plv Testpreise Kunden (Deutschland) Deutschland EUR and tax category Normaler Steuersatz 19% (Deutschland)';