-- 2023-12-17T18:09:54.112Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582870,0,'IsCompletelyDeliveredORInvoiced',TO_TIMESTAMP('2023-12-17 19:09:53.401','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Vollständig geliefert oder fakturiert','Vollständig geliefert oder fakturiert',TO_TIMESTAMP('2023-12-17 19:09:53.401','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-17T18:09:54.179Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582870 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-12-17T18:14:35.469Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Fully delivered or invoiced', PrintName='Fully delivered or invoiced',Updated=TO_TIMESTAMP('2023-12-17 19:14:35.307','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582870 AND AD_Language='en_US'
;

-- 2023-12-17T18:14:35.558Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582870,'en_US') 
;

-- Column: C_Order.IsCompletelyDeliveredORInvoiced
-- Column SQL (old): null
-- 2023-12-17T18:21:37.988Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587759,582870,0,20,259,'IsCompletelyDeliveredORInvoiced','(CASE             WHEN (C_Order.QtyOrdered <= C_Order.QtyMoved AND C_Order.QtyOrdered > 0)                 THEN ''Y''             WHEN C_Order.QtyOrdered <= C_Order.QtyInvoiced AND C_Order.QtyOrdered > 0                 THEN ''Y''                 ELSE ''N'' END)',TO_TIMESTAMP('2023-12-17 19:21:37.247','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Vollständig geliefert oder fakturiert',0,0,TO_TIMESTAMP('2023-12-17 19:21:37.247','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-17T18:21:38.043Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=587759 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-17T18:21:38.153Z
/* DDL */  select update_Column_Translation_From_AD_Element(582870) 
;

-- Column: C_Order.IsCompletelyDeliveredORInvoiced
-- 2023-12-17T18:22:32.955Z
UPDATE AD_Column SET FilterDefaultValue='N', FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-12-17 19:22:32.793','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587759
;

