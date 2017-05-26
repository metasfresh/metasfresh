

-- 2017-04-28T16:56:01.307
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET AD_Reference_Value_ID=53320,Updated=TO_TIMESTAMP('2017-04-28 16:56:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556526
;

-- 2017-04-28T17:00:12.492
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556530,275,0,10,540810,'N','Description',TO_TIMESTAMP('2017-04-28 17:00:12','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',2000,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','Beschreibung',0,TO_TIMESTAMP('2017-04-28 17:00:12','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-28T17:00:12.497
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556530 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-04-28T17:00:55.780
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556528
;

-- 2017-04-28T17:00:55.816
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=556528
;

-- 2017-04-28T17:00:59.072
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=556527
;

-- 2017-04-28T17:00:59.076
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Column WHERE AD_Column_ID=556527
;

-- 2017-04-28T17:08:06.248
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,AllowZoomTo,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,SeqNo,Updated,UpdatedBy,Version) VALUES (0,556531,543308,0,30,540810,'N','MD_Candidate_ID',TO_TIMESTAMP('2017-04-28 17:08:06','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.material.dispo',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Dispositionskandidat',0,TO_TIMESTAMP('2017-04-28 17:08:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2017-04-28T17:08:06.250
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language,t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N' AND t.AD_Column_ID=556531 AND NOT EXISTS (SELECT * FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2017-04-28T17:01:07.009
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ CREATE TABLE public.MD_Candidate_Prod_Details (AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, Description VARCHAR(2000), IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, MD_Candidate_Prod_Details_ID NUMERIC(10) NOT NULL, PP_Plant_ID NUMERIC(10), PP_Product_BOMLine_ID NUMERIC(10), PP_Product_Planning_ID NUMERIC(10), Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT MD_Candidate_Prod_Details_Key PRIMARY KEY (MD_Candidate_Prod_Details_ID), CONSTRAINT PPPlant_MDCandidateProdDetails FOREIGN KEY (PP_Plant_ID) REFERENCES public.S_Resource DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PPProductBOMLine_MDCandidatePr FOREIGN KEY (PP_Product_BOMLine_ID) REFERENCES public.PP_Product_BOMLine DEFERRABLE INITIALLY DEFERRED, CONSTRAINT PPProductPlanning_MDCandidateP FOREIGN KEY (PP_Product_Planning_ID) REFERENCES public.PP_Product_Planning DEFERRABLE INITIALLY DEFERRED)
;

-- 2017-04-28T17:08:08.188
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
/* DDL */ SELECT public.db_alter_table('md_candidate_prod_details','ALTER TABLE public.MD_Candidate_Prod_Details ADD COLUMN MD_Candidate_ID NUMERIC(10)')
;

-- 2017-04-28T17:08:08.227
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
ALTER TABLE MD_Candidate_Prod_Details ADD CONSTRAINT MDCandidate_MDCandidateProdDet FOREIGN KEY (MD_Candidate_ID) REFERENCES public.MD_Candidate DEFERRABLE INITIALLY DEFERRED
;

