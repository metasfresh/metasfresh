-- 2022-09-08T08:24:34.617Z
UPDATE AD_ImpFormat_Row SET DataType='S',ConstantValue='',Updated=TO_TIMESTAMP('2022-09-08 08:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541796
;

-- Column: I_Invoice_Candidate.OrgCode
-- 2022-09-08T08:24:34.617Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-09-08 11:24:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584230
;

-- 2022-09-08T08:25:47.684Z
INSERT INTO t_alter_column values('i_invoice_candidate','OrgCode','VARCHAR(255)',null,null)
;

-- 2022-09-08T08:25:47.690Z
INSERT INTO t_alter_column values('i_invoice_candidate','OrgCode',null,'NULL',null)
;

-- 2022-09-08T11:08:19.469Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581431,0,'Default_OrgCode',TO_TIMESTAMP('2022-09-08 14:08:18','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Default Org Suchschlüssel','Default Org Suchschlüssel',TO_TIMESTAMP('2022-09-08 14:08:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-08T11:08:19.475Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581431 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Default_OrgCode
-- 2022-09-08T11:09:08.959Z
UPDATE AD_Element_Trl SET Name='Default Org Code', PrintName='Default Org Code',Updated=TO_TIMESTAMP('2022-09-08 14:09:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581431 AND AD_Language='en_US'
;

-- 2022-09-08T11:09:08.985Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581431,'en_US') 
;

-- Column: I_Invoice_Candidate.Default_OrgCode
-- 2022-09-08T11:09:52.049Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584273,581431,0,10,542207,'Default_OrgCode',TO_TIMESTAMP('2022-09-08 14:09:51','YYYY-MM-DD HH24:MI:SS'),100,'N','001','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N',0,'Default Org Suchschlüssel',0,0,TO_TIMESTAMP('2022-09-08 14:09:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-09-08T11:09:52.054Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584273 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-09-08T11:09:52.061Z
/* DDL */  select update_Column_Translation_From_AD_Element(581431) 
;

-- 2022-09-08T11:09:52.588Z
/* DDL */ SELECT public.db_alter_table('I_Invoice_Candidate','ALTER TABLE public.I_Invoice_Candidate ADD COLUMN Default_OrgCode VARCHAR(255) DEFAULT ''001'' NOT NULL')
;

-- 2022-09-08T11:11:53.013Z
INSERT INTO AD_ImpFormat_Row (AD_Client_ID,AD_Column_ID,AD_ImpFormat_ID,AD_ImpFormat_Row_ID,AD_Org_ID,Created,CreatedBy,DataType,DecimalPoint,DivideBy100,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584273,540077,541797,0,TO_TIMESTAMP('2022-09-08 14:11:53','YYYY-MM-DD HH24:MI:SS'),100,'C','.','N','Y','Default Org Suchschlüssel',170,TO_TIMESTAMP('2022-09-08 14:11:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-09-08T11:11:56.280Z
UPDATE AD_ImpFormat_Row SET ConstantValue='001',Updated=TO_TIMESTAMP('2022-09-08 14:11:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541797
;

-- 2022-09-08T11:12:08.988Z
UPDATE AD_ImpFormat_Row SET StartNo=17,Updated=TO_TIMESTAMP('2022-09-08 14:12:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_ImpFormat_Row_ID=541797
;

-- Column: I_Invoice_Candidate.Default_OrgCode
-- 2022-09-08T13:26:23.341Z
UPDATE AD_Column SET DefaultValue='',Updated=TO_TIMESTAMP('2022-09-08 16:26:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584273
;

