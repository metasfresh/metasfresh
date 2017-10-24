-- 2017-10-24T11:37:06.426
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540411,0,540815,TO_TIMESTAMP('2017-10-24 11:37:06','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Y','MD_Candidate_Demand_Detail_UC','N',TO_TIMESTAMP('2017-10-24 11:37:06','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2017-10-24T11:37:06.440
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540411 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-10-24T11:37:23.166
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556581,540819,540411,0,TO_TIMESTAMP('2017-10-24 11:37:23','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',10,TO_TIMESTAMP('2017-10-24 11:37:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T11:37:37.020
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Demand_Detail_UC ON MD_Candidate_Demand_Detail (MD_Candidate_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:38:35.611
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540412,0,540821,TO_TIMESTAMP('2017-10-24 11:38:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Y','MD_Candidate_Dist_Detail_UC','N',TO_TIMESTAMP('2017-10-24 11:38:35','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2017-10-24T11:38:35.614
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540412 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-10-24T11:38:59.321
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556799,540820,540412,0,TO_TIMESTAMP('2017-10-24 11:38:59','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',10,TO_TIMESTAMP('2017-10-24 11:38:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T11:39:09.013
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Dist_Detail_UC ON MD_Candidate_Dist_Detail (MD_Candidate_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:40:12.598
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,557526,540821,540410,0,TO_TIMESTAMP('2017-10-24 11:40:12','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',30,TO_TIMESTAMP('2017-10-24 11:40:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T11:40:22.989
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Index_Column WHERE AD_Index_Column_ID=540821
;

-- 2017-10-24T11:40:27.499
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS md_candidate_transaction_detail_uc
;

-- 2017-10-24T11:40:27.502
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Transaction_Detail_UC ON MD_Candidate_Transaction_Detail (MD_Candidate_ID,M_Transaction_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:40:49.094
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS md_candidate_transaction_detail_uc
;

-- 2017-10-24T11:40:49.095
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Transaction_Detail_UC ON MD_Candidate_Transaction_Detail (MD_Candidate_ID,M_Transaction_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:40:55.361
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Prod_Detail_UC ON MD_Candidate_Prod_Detail (MD_Candidate_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:41:02.058
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS md_candidate_dist_detail_uc
;

-- 2017-10-24T11:41:02.059
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Dist_Detail_UC ON MD_Candidate_Dist_Detail (MD_Candidate_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:41:11.014
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS md_candidate_demand_detail_uc
;

-- 2017-10-24T11:41:11.015
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Demand_Detail_UC ON MD_Candidate_Demand_Detail (MD_Candidate_ID) WHERE IsActive='Y'
;

-- 2017-10-24T11:41:51.977
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556482,540822,540397,0,TO_TIMESTAMP('2017-10-24 11:41:51','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',40,TO_TIMESTAMP('2017-10-24 11:41:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2017-10-24T11:41:58.023
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DROP INDEX IF EXISTS md_candidate_uc_stock
;

-- 2017-10-24T11:41:58.025
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_UC_Stock ON MD_Candidate (DateProjected,M_Product_ID,M_Warehouse_ID) WHERE IsActive='Y' AND MD_Candidate_Type='Stock'
;

