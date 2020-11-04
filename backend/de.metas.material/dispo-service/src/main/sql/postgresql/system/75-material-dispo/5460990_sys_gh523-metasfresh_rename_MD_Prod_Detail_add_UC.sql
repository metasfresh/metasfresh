-- 2017-04-28T18:20:23.988
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table SET Name='Dispo-Produktionsdetail', TableName='MD_Candidate_Prod_Detail',Updated=TO_TIMESTAMP('2017-04-28 18:20:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=540810
;

-- 2017-04-28T18:20:23.992
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Table_Trl SET IsTranslated='N' WHERE AD_Table_ID=540810
;

-- 2017-04-28T18:20:24.035
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Sequence SET Name='MD_Candidate_Prod_Detail',Updated=TO_TIMESTAMP('2017-04-28 18:20:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Sequence_ID=554394
;

-- 2017-04-28T18:20:41.658
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element SET ColumnName='MD_Candidate_Prod_Detail_ID', Name='Dispo-Produktionsdetail', PrintName='Dispo-Produktionsdetail',Updated=TO_TIMESTAMP('2017-04-28 18:20:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=543320
;

-- 2017-04-28T18:20:41.661
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Element_Trl SET IsTranslated='N' WHERE AD_Element_ID=543320
;

-- 2017-04-28T18:20:41.673
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnName='MD_Candidate_Prod_Detail_ID', Name='Dispo-Produktionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=543320
;

-- 2017-04-28T18:20:41.725
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MD_Candidate_Prod_Detail_ID', Name='Dispo-Produktionsdetail', Description=NULL, Help=NULL, AD_Element_ID=543320 WHERE UPPER(ColumnName)='MD_CANDIDATE_PROD_DETAIL_ID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2017-04-28T18:20:41.730
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET ColumnName='MD_Candidate_Prod_Detail_ID', Name='Dispo-Produktionsdetail', Description=NULL, Help=NULL WHERE AD_Element_ID=543320 AND IsCentrallyMaintained='Y'
;

-- 2017-04-28T18:20:41.731
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Field SET Name='Dispo-Produktionsdetail', Description=NULL, Help=NULL WHERE AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=543320) AND IsCentrallyMaintained='Y'
;

-- 2017-04-28T18:20:41.744
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_PrintFormatItem pi SET PrintName='Dispo-Produktionsdetail', Name='Dispo-Produktionsdetail' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=543320)
;

-- 2017-04-28T18:21:54.392
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy,WhereClause) VALUES (0,540398,0,540810,TO_TIMESTAMP('2017-04-28 18:21:54','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y','Y','MD_Candidate_Prod_Detail_UC','N',TO_TIMESTAMP('2017-04-28 18:21:54','YYYY-MM-DD HH24:MI:SS'),100,'IsActive=''Y''')
;

-- 2017-04-28T18:21:54.399
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Index_Table_ID=540398 AND NOT EXISTS (SELECT * FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2017-04-28T18:22:09.282
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,556531,540798,540398,0,TO_TIMESTAMP('2017-04-28 18:22:09','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.material.dispo','Y',10,TO_TIMESTAMP('2017-04-28 18:22:09','YYYY-MM-DD HH24:MI:SS'),100)
;


drop table MD_Candidate_Prod_Details;

-- 2017-04-28T18:21:19.664
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.MD_Candidate_Prod_Detail (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MD_Candidate_ID NUMERIC(10), MD_Candidate_Prod_Detail_ID NUMERIC(10) NOT NULL, PP_Plant_ID NUMERIC(10), PP_Product_BOMLine_ID NUMERIC(10), PP_Product_Planning_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MDCandidate_MDCandidateProdDet FOREIGN KEY (MD_Candidate_ID) REFERENCES public.MD_Candidate DEFERRABLE INITIALLY DEFERRED, CONSTRAINT MD_Candidate_Prod_Detail_Key PRIMARY KEY (MD_Candidate_Prod_Detail_ID), CONSTRAINT PPPlant_MDCandidateProdDetail FOREIGN KEY (PP_Plant_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PPProductBOMLine_MDCandidatePr FOREIGN KEY (PP_Product_BOMLine_ID) REFERENCES public.PP_Product_BOMLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PPProductPlanning_MDCandidateP FOREIGN KEY (PP_Product_Planning_ID) REFERENCES public.PP_Product_Planning DEFERRABLE INITIALLY DEFERRED)
;

-- 2017-04-28T18:22:12.047
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE UNIQUE INDEX MD_Candidate_Prod_Detail_UC ON MD_Candidate_Prod_Detail (MD_Candidate_ID) WHERE IsActive='Y'
;

