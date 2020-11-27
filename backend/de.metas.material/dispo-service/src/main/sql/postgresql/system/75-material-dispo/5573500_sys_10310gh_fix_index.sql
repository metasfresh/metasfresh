
-- we won't need or want the uniqueness anymore; also, as we had the Qty in that index, it anyways didn't make a lot of sense

-- 2020-11-27T08:49:36.023Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Help='This index has two purposes:
* ensuring uniqueness on stock records
* supporting de_metas_material_dispo.MD_Candidate_Latest_v in finding the latest DateProjected for  a given product-id, warehouse-id and StorageAttributesKey-(like-)expression
Note: the Qty column is in so that hopefully MD_Candidate_Latest_v can do an index-only-scan',Updated=TO_TIMESTAMP('2020-11-27 09:49:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540397
;

-- 2020-11-27T08:50:29.744Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Description='Records with type "Stock" need to be unique in terms of product, attributes, warehouse and projected date',Updated=TO_TIMESTAMP('2020-11-27 09:50:29','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540397
;

-- 2020-11-27T08:51:24.718Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Name='MD_Candidate_Stock_Perf', Description='', Help='This index has the purpose of supporting de_metas_material_dispo.MD_Candidate_Latest_v in finding the latest DateProjected for  a given product-id, warehouse-id and StorageAttributesKey-(like-)expression
Note: the Qty column is in so that hopefully MD_Candidate_Latest_v can do an index-only-scan', IsUnique='N',Updated=TO_TIMESTAMP('2020-11-27 09:51:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540397
;

DROP INDEX IF EXISTS MD_Candidate_UC_Stock;

-- 2020-11-27T08:51:34.050Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE INDEX MD_Candidate_Stock_Perf ON MD_Candidate (M_Product_ID,StorageAttributesKey,M_Warehouse_ID,DateProjected,Qty) WHERE IsActive='Y' AND MD_Candidate_Type='Stock'
;

