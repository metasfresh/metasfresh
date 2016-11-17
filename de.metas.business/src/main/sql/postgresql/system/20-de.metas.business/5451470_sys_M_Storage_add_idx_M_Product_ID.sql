--
-- i stumbled over long-running SQLs on M_Storage and found that there is no idx on M_Product_ID..
-- this should generally help
-- note that the index m_storage_uniqe already takes car of the perofrmance of per-locator selects.
--
DROP INDEX IF EXISTS m_storage_m_product_id;
CREATE INDEX m_storage_m_product_id
  ON m_storage
  USING btree
  (m_product_id);
