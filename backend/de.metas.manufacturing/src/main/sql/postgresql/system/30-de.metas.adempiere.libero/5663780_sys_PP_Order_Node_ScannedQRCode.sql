-- select migrationscript_ignore('30-de.metas.adempiere.libero/5663780_sys_PP_Order_Node_ScannedQRCode.sql');

-- 2022-11-09T10:45:59.901Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581653,0,'ScannedQRCode',TO_TIMESTAMP('2022-11-09 12:45:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Scanned QR Code','Scanned QR Code',TO_TIMESTAMP('2022-11-09 12:45:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T10:45:59.908Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581653 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: PP_Order_Node.ScannedQRCode
-- 2022-11-09T10:47:57.174Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,
                       IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,584881,581653,0,10,53022,'ScannedQRCode',TO_TIMESTAMP('2022-11-09 12:47:57','YYYY-MM-DD HH24:MI:SS'),100,'N','EE01',0,2000,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N',
        'N','N','N','N','N','N','Y','N',0,'Scanned QR Code',0,0,TO_TIMESTAMP('2022-11-09 12:47:57','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-11-09T10:47:57.179Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-11-09T10:47:57.212Z
/* DDL */  select update_Column_Translation_From_AD_Element(581653) 
;

-- 2022-11-09T10:48:00.306Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Node','ALTER TABLE public.PP_Order_Node ADD COLUMN ScannedQRCode VARCHAR(2000)')
;

-- Field: Produktionsauftrag_OLD -> Knoten Arbeitsablauf -> Scanned QR Code
-- Column: PP_Order_Node.ScannedQRCode
-- 2022-11-09T10:48:41.028Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584881,707988,0,53036,TO_TIMESTAMP('2022-11-09 12:48:40','YYYY-MM-DD HH24:MI:SS'),100,2000,'EE01','Y','N','N','N','N','N','N','N','Scanned QR Code',TO_TIMESTAMP('2022-11-09 12:48:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-11-09T10:48:41.031Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=707988 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-11-09T10:48:41.034Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581653) 
;

-- 2022-11-09T10:48:41.049Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=707988
;

-- 2022-11-09T10:48:41.057Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(707988)
;

-- Field: Produktionsauftrag_OLD -> Knoten Arbeitsablauf -> Scanned QR Code
-- Column: PP_Order_Node.ScannedQRCode
-- 2022-11-09T10:48:57.865Z
UPDATE AD_Field SET IsDisplayed='Y', SeqNo=300,Updated=TO_TIMESTAMP('2022-11-09 12:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707988
;

-- Field: Produktionsauftrag_OLD -> Knoten Arbeitsablauf -> Scanned QR Code
-- Column: PP_Order_Node.ScannedQRCode
-- 2022-11-09T10:49:14.326Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2022-11-09 12:49:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=707988
;

-- Column: PP_Order_Node.ScannedQRCode
-- 2022-11-09T10:49:32.136Z
UPDATE AD_Column SET AD_Reference_ID=36,Updated=TO_TIMESTAMP('2022-11-09 12:49:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=584881
;

-- 2022-11-09T10:49:56.855Z
INSERT INTO t_alter_column values('pp_order_node','ScannedQRCode','TEXT',null,null)
;

