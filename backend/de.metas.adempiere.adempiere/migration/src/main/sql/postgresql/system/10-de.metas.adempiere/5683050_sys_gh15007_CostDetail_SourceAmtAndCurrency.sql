-- 2023-03-28T15:50:51.867Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582177,0,'SourceAmt',TO_TIMESTAMP('2023-03-28 18:50:51','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Source Amount','Source Amount',TO_TIMESTAMP('2023-03-28 18:50:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-28T15:50:52.361Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582177 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: SourceAmt
-- 2023-03-28T15:50:57.320Z
UPDATE AD_Element_Trl SET Name='Quellbetrag', PrintName='Quellbetrag',Updated=TO_TIMESTAMP('2023-03-28 18:50:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='de_CH'
;

-- 2023-03-28T15:50:57.349Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'de_CH') 
;

-- Element: SourceAmt
-- 2023-03-28T15:50:59.778Z
UPDATE AD_Element_Trl SET Name='Quellbetrag', PrintName='Quellbetrag',Updated=TO_TIMESTAMP('2023-03-28 18:50:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='de_DE'
;

-- 2023-03-28T15:50:59.780Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'de_DE') 
;

-- Element: SourceAmt
-- 2023-03-28T15:51:02.627Z
UPDATE AD_Element_Trl SET Name='Quellbetrag', PrintName='Quellbetrag',Updated=TO_TIMESTAMP('2023-03-28 18:51:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='fr_CH'
;

-- 2023-03-28T15:51:02.630Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'fr_CH') 
;

-- Element: SourceAmt
-- 2023-03-28T15:51:04.753Z
UPDATE AD_Element_Trl SET Name='Quellbetrag', PrintName='Quellbetrag',Updated=TO_TIMESTAMP('2023-03-28 18:51:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582177 AND AD_Language='nl_NL'
;

-- 2023-03-28T15:51:04.755Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582177,'nl_NL') 
;

-- Column: M_CostDetail.SourceAmt
-- 2023-03-28T15:51:57.104Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586369,582177,0,12,808,'SourceAmt',TO_TIMESTAMP('2023-03-28 18:51:56','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,22,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Source Amount',0,0,TO_TIMESTAMP('2023-03-28 18:51:56','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-28T15:51:57.110Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586369 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-28T15:51:57.712Z
/* DDL */  select update_Column_Translation_From_AD_Element(582177) 
;

-- 2023-03-28T15:52:14.737Z
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN SourceAmt NUMERIC')
;

-- Column: M_CostDetail.SourceAmt
-- 2023-03-28T15:53:07.043Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-03-28 18:53:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586369
;

-- Column: M_CostDetail.Source_Currency_ID
-- 2023-03-28T15:56:07.783Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586370,578756,0,30,112,808,'Source_Currency_ID',TO_TIMESTAMP('2023-03-28 18:56:07','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Source Currency',0,0,TO_TIMESTAMP('2023-03-28 18:56:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-28T15:56:07.787Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586370 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-28T15:56:08.375Z
/* DDL */  select update_Column_Translation_From_AD_Element(578756) 
;

-- 2023-03-28T15:56:10.407Z
/* DDL */ SELECT public.db_alter_table('M_CostDetail','ALTER TABLE public.M_CostDetail ADD COLUMN Source_Currency_ID NUMERIC(10)')
;

-- 2023-03-28T15:56:10.545Z
ALTER TABLE M_CostDetail ADD CONSTRAINT SourceCurrency_MCostDetail FOREIGN KEY (Source_Currency_ID) REFERENCES public.C_Currency DEFERRABLE INITIALLY DEFERRED
;

-- 2023-03-28T15:56:37.972Z
INSERT INTO t_alter_column values('m_costdetail','Source_Currency_ID','NUMERIC(10)',null,null)
;

-- Column: M_CostDetail.Source_Currency_ID
-- 2023-03-28T16:02:23.661Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-03-28 19:02:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586370
;

-- 2023-03-28T16:02:26.417Z
INSERT INTO t_alter_column values('m_costdetail','Source_Currency_ID','NUMERIC(10)',null,null)
;

-- 2023-03-28T16:02:26.422Z
INSERT INTO t_alter_column values('m_costdetail','Source_Currency_ID',null,'NULL',null)
;

-- Column: M_CostDetail.SourceAmt
-- 2023-03-28T16:02:36.031Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2023-03-28 19:02:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586369
;

-- 2023-03-28T16:02:39.091Z
INSERT INTO t_alter_column values('m_costdetail','SourceAmt','NUMERIC',null,null)
;

-- 2023-03-28T16:02:39.097Z
INSERT INTO t_alter_column values('m_costdetail','SourceAmt',null,'NULL',null)
;

