-- Column: GPLR_Report.SAP_ProductHierarchy
-- 2023-06-28T06:46:41.323Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587011,581917,0,10,542341,'XX','SAP_ProductHierarchy',TO_TIMESTAMP('2023-06-28 09:46:40','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,40,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'SAP Product Hierarchy',0,0,TO_TIMESTAMP('2023-06-28 09:46:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-06-28T06:46:41.326Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587011 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-28T06:46:41.871Z
/* DDL */  select update_Column_Translation_From_AD_Element(581917) 
;

-- 2023-06-28T06:46:44.474Z
/* DDL */ SELECT public.db_alter_table('GPLR_Report','ALTER TABLE public.GPLR_Report ADD COLUMN SAP_ProductHierarchy VARCHAR(40)')
;

-- Column: GPLR_Report.InvoiceDocumentNo
-- 2023-06-28T06:47:49.846Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 09:47:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586869
;

-- 2023-06-28T06:47:51.552Z
INSERT INTO t_alter_column values('gplr_report','InvoiceDocumentNo','VARCHAR(255)',null,null)
;

-- 2023-06-28T06:47:51.556Z
INSERT INTO t_alter_column values('gplr_report','InvoiceDocumentNo',null,'NOT NULL',null)
;

-- 2023-06-28T06:48:04.122Z
INSERT INTO t_alter_column values('gplr_report','DocTypeName','VARCHAR(255)',null,null)
;

-- Column: GPLR_Report.DocTypeName
-- 2023-06-28T06:48:08.861Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 09:48:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586870
;

-- 2023-06-28T06:48:09.623Z
INSERT INTO t_alter_column values('gplr_report','DocTypeName','VARCHAR(255)',null,null)
;

-- 2023-06-28T06:48:09.625Z
INSERT INTO t_alter_column values('gplr_report','DocTypeName',null,'NOT NULL',null)
;

-- Column: GPLR_Report.CreatedByName
-- 2023-06-28T06:48:21.589Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2023-06-28 09:48:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586871
;

-- 2023-06-28T06:48:22.362Z
INSERT INTO t_alter_column values('gplr_report','CreatedByName','VARCHAR(255)',null,null)
;

-- 2023-06-28T06:48:22.364Z
INSERT INTO t_alter_column values('gplr_report','CreatedByName',null,'NOT NULL',null)
;

