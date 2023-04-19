-- Column: C_BPartner_BlockStatus.IsCurrent
-- 2023-02-24T12:08:37.405Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586217,542147,0,20,542315,'IsCurrent',TO_TIMESTAMP('2023-02-24 14:08:37','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Current Valid Version',0,0,TO_TIMESTAMP('2023-02-24 14:08:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-24T12:08:37.409Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586217 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-24T12:08:37.472Z
/* DDL */  select update_Column_Translation_From_AD_Element(542147) 
;

-- 2023-02-24T12:08:42.408Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_BlockStatus','ALTER TABLE public.C_BPartner_BlockStatus ADD COLUMN IsCurrent CHAR(1) DEFAULT ''N'' CHECK (IsCurrent IN (''Y'',''N'')) NOT NULL')
;

-- 2023-02-24T12:09:11.685Z
INSERT INTO AD_Index_Table (AD_Client_ID,AD_Index_Table_ID,AD_Org_ID,AD_Table_ID,Created,CreatedBy,EntityType,IsActive,IsUnique,Name,Processing,Updated,UpdatedBy) VALUES (0,540723,0,542315,TO_TIMESTAMP('2023-02-24 14:09:11','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Y','IDX_Unique_IsCurrent_C_BPartner_ID','N',TO_TIMESTAMP('2023-02-24 14:09:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-24T12:09:11.687Z
INSERT INTO AD_Index_Table_Trl (AD_Language,AD_Index_Table_ID, ErrorMsg, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Index_Table_ID, t.ErrorMsg, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Index_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Index_Table_ID=540723 AND NOT EXISTS (SELECT 1 FROM AD_Index_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Index_Table_ID=t.AD_Index_Table_ID)
;

-- 2023-02-24T12:10:07.984Z
UPDATE AD_Index_Table SET WhereClause='IsActive=''Y'' AND IsCurrent=''Y''',Updated=TO_TIMESTAMP('2023-02-24 14:10:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Index_Table_ID=540723
;

-- 2023-02-24T12:10:22.760Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586202,541306,540723,0,TO_TIMESTAMP('2023-02-24 14:10:22','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',10,TO_TIMESTAMP('2023-02-24 14:10:22','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-24T12:10:58.301Z
INSERT INTO AD_Index_Column (AD_Client_ID,AD_Column_ID,AD_Index_Column_ID,AD_Index_Table_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,586217,541307,540723,0,TO_TIMESTAMP('2023-02-24 14:10:58','YYYY-MM-DD HH24:MI:SS'),100,'D','Y',20,TO_TIMESTAMP('2023-02-24 14:10:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-24T12:11:03.990Z
CREATE UNIQUE INDEX IDX_Unique_IsCurrent_C_BPartner_ID ON C_BPartner_BlockStatus (C_BPartner_ID,IsCurrent) WHERE IsActive='Y' AND IsCurrent='Y'
;

-- 2023-02-24T12:11:09.606Z
CREATE INDEX IDX_Partner_CreatedAt ON C_BPartner_BlockStatus (C_BPartner_ID,Created)
;

