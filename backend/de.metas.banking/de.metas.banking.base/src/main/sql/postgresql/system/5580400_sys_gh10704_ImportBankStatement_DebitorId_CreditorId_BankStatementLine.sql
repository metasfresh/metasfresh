


-- 2021-02-24T10:37:14.217Z
-- URL zum Konzept
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,578782,0,'DebtorOrCreditorId',TO_TIMESTAMP('2021-02-24 12:37:13','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Debitor No./Kreditor No.','Debitor No./Kreditor No.',TO_TIMESTAMP('2021-02-24 12:37:13','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2021-02-24T10:37:14.269Z
-- URL zum Konzept
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=578782 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2021-02-24T10:37:56.875Z
-- URL zum Konzept
UPDATE AD_Element SET ColumnName='DebitorOrCreditorId',Updated=TO_TIMESTAMP('2021-02-24 12:37:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578782
;

-- 2021-02-24T10:37:57.116Z
-- URL zum Konzept
UPDATE AD_Column SET ColumnName='DebitorOrCreditorId', Name='Debitor No./Kreditor No.', Description=NULL, Help=NULL WHERE AD_Element_ID=578782
;

-- 2021-02-24T10:37:57.169Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DebitorOrCreditorId', Name='Debitor No./Kreditor No.', Description=NULL, Help=NULL, AD_Element_ID=578782 WHERE UPPER(ColumnName)='DEBITORORCREDITORID' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2021-02-24T10:37:57.226Z
-- URL zum Konzept
UPDATE AD_Process_Para SET ColumnName='DebitorOrCreditorId', Name='Debitor No./Kreditor No.', Description=NULL, Help=NULL WHERE AD_Element_ID=578782 AND IsCentrallyMaintained='Y'
;

-- 2021-02-24T11:54:38.522Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573000,578782,0,11,393,'DebitorOrCreditorId',TO_TIMESTAMP('2021-02-24 13:54:37','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Debitor No./Kreditor No.',0,0,TO_TIMESTAMP('2021-02-24 13:54:37','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-24T11:54:38.574Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573000 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-24T11:54:38.686Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578782) 
;

-- 2021-02-24T11:54:45.664Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('C_BankStatementLine','ALTER TABLE public.C_BankStatementLine ADD COLUMN DebitorOrCreditorId NUMERIC(10) DEFAULT 0 NOT NULL')
;

-- 2021-02-24T12:49:47.198Z
-- URL zum Konzept
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,573001,578782,0,11,600,'DebitorOrCreditorId',TO_TIMESTAMP('2021-02-24 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,'N','0','D',0,14,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Debitor No./Kreditor No.',0,0,TO_TIMESTAMP('2021-02-24 14:49:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2021-02-24T12:49:47.249Z
-- URL zum Konzept
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=573001 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2021-02-24T12:49:47.303Z
-- URL zum Konzept
/* DDL */  select update_Column_Translation_From_AD_Element(578782) 
;

-- 2021-02-24T12:49:52.926Z
-- URL zum Konzept
/* DDL */ SELECT public.db_alter_table('I_BankStatement','ALTER TABLE public.I_BankStatement ADD COLUMN DebitorOrCreditorId NUMERIC(10) DEFAULT 0 NOT NULL')
;
