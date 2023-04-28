-- Column: C_Dunning_Candidate.M_SectionCode_ID
-- 2022-09-23T09:41:26.650Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584433,581238,0,19,540396,'M_SectionCode_ID',TO_TIMESTAMP('2022-09-23 12:41:26','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.dunning',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-09-23 12:41:26','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-23T09:41:26.652Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584433 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-23T09:41:26.677Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-09-23T09:41:27.553Z
/* DDL */ SELECT public.db_alter_table('C_Dunning_Candidate','ALTER TABLE public.C_Dunning_Candidate ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-09-23T09:41:27.568Z
ALTER TABLE C_Dunning_Candidate ADD CONSTRAINT MSectionCode_CDunningCandidate FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- 2022-09-23T09:44:59.550Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,Description,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540709,0,542200,TO_TIMESTAMP('2022-09-23 12:44:59','YYYY-MM-DD HH24:MI:SS'),100,'Unique index section code value','D','Y','Y','IDX_M_SectionCode_unique_value','N',TO_TIMESTAMP('2022-09-23 12:44:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-23T09:44:59.553Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Index_Table_ID=540709 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2022-09-23T09:45:30.411Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,ColumnSQL,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583974,541273,540709,0,'',TO_TIMESTAMP('2022-09-23 12:45:30','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2022-09-23 12:45:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-23T10:02:05.514Z
CREATE UNIQUE INDEX IDX_M_SectionCode_unique_value ON M_SectionCode (Value)
;

-- 2022-09-23T10:04:19.396Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,583967,541274,540709,0,TO_TIMESTAMP('2022-09-23 13:04:19','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2022-09-23 13:04:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-23T10:04:21.984Z
DROP INDEX IF EXISTS idx_m_sectioncode_unique_value
;

-- 2022-09-23T10:04:21.986Z
CREATE UNIQUE INDEX IDX_M_SectionCode_unique_value ON M_SectionCode (Value,AD_Org_ID)
;

