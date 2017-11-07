
-- 2017-11-03T15:45:50.267
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557786,540823,540397,0,TO_TIMESTAMP('2017-11-03 15:45:50','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',50,TO_TIMESTAMP('2017-11-03 15:45:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-03T15:46:03.213
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET SeqNo=60,Updated=TO_TIMESTAMP('2017-11-03 15:46:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540794
;


-- 2017-11-03T15:46:33.562
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-03 15:46:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540823
;

-- 2017-11-03T15:46:37.876
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Column SET SeqNo=40,Updated=TO_TIMESTAMP('2017-11-03 15:46:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Column_ID=540822
;

-- 2017-11-03T15:46:57.772
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556476,540824,540397,0,TO_TIMESTAMP('2017-11-03 15:46:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',70,TO_TIMESTAMP('2017-11-03 15:46:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-11-03T15:47:04.934
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Index_Table SET Help='This index has two purposes:
* ensuring uniqueness on stock records
* supporting de_metas_material_dispo.MD_Candidate_Latest_v in finding the latest DateProjected for  a given product-id, warehouse-id and StorageAttributesKey-(like-)expression
Note: the Qty column is in so that hopefully MD_Candidate_Latest_v can do an index-inly-scan',Updated=TO_TIMESTAMP('2017-11-03 15:47:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540397
;

-- 2017-11-03T15:47:35.500
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS md_candidate_uc_stock
;

-- 2017-11-03T15:47:35.501
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_UC_Stock ON MD_Candidate (M_Product_ID,StorageAttributesKey,M_Warehouse_ID,DateProjected,Qty) WHERE IsActive='Y' AND MD_Candidate_Type='Stock'
;

COMMENT ON INDEX public.md_candidate_uc_stock
  IS 'This index has two purposes:
* ensuring uniqueness on stock records
* supporting de_metas_material_dispo.MD_Candidate_Latest_v in finding the latest DateProjected for 
  a given product-id, warehouse-id and StorageAttributesKey-(like-)expression

Note: the Qty column is in so that hopefully MD_Candidate_Latest_v can do an index-inly-scan'
;


CREATE SCHEMA IF NOT EXISTS de_metas_material_dispo;

DROP VIEW IF EXISTS de_metas_material_dispo.MD_Candidate_Latest_v;
CREATE VIEW de_metas_material_dispo.MD_Candidate_Latest_v AS 
SELECT DISTINCT ON (M_Product_ID, StorageattributesKey, M_Warehouse_ID)
	M_Product_ID,
	StorageattributesKey,
	M_Warehouse_ID,
	DateProjected,
	Qty
FROM MD_Candidate
ORDER BY     
	M_Product_ID,
	StorageattributesKey,
    M_Warehouse_ID,
    Dateprojected DESC
;

