ALTER TABLE m_productprice
  DROP CONSTRAINT IF EXISTS m_productprice_pricelistver_product; -- UNIQUE(m_pricelist_version_id, m_product_id);

drop index if exists m_productprice_pricelistver_product;
create index m_productprice_pricelistver_product on M_ProductPrice (M_PriceList_Version_ID, M_Product_ID, MatchSeqNo);

