-- 2024-07-31T12:12:28.979Z
UPDATE AD_Element_Trl SET Name='SurchargeAmt', PrintName='SurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:12:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583195 AND AD_Language='de_DE'
;

-- 2024-07-31T12:12:28.985Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583195,'de_DE') 
;

-- 2024-07-31T12:12:28.987Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583195,'de_DE') 
;

-- 2024-07-31T12:12:31.374Z
UPDATE AD_Element_Trl SET Name='SurchargeAmt', PrintName='SurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:12:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583195 AND AD_Language='en_US'
;

-- 2024-07-31T12:12:31.379Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583195,'en_US') 
;

-- 2024-07-31T12:12:34.262Z
UPDATE AD_Element_Trl SET Name='SurchargeAmt', PrintName='SurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:12:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583195 AND AD_Language='fr_CH'
;

-- 2024-07-31T12:12:34.266Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583195,'fr_CH') 
;

-- 2024-07-31T12:12:37.351Z
UPDATE AD_Element_Trl SET Name='SurchargeAmt', PrintName='SurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:12:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583195 AND AD_Language='nl_NL'
;

-- 2024-07-31T12:12:37.357Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583195,'nl_NL') 
;

-- 2024-07-31T12:13:44.002Z
UPDATE AD_Element SET ColumnName='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:13:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583196
;

-- 2024-07-31T12:13:44.004Z
UPDATE AD_Column SET ColumnName='TotalLinesWithSurchargeAmt' WHERE AD_Element_ID=583196
;

-- 2024-07-31T12:13:44.007Z
UPDATE AD_Process_Para SET ColumnName='TotalLinesWithSurchargeAmt' WHERE AD_Element_ID=583196
;

-- 2024-07-31T12:13:44.013Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583196,'de_DE') 
;

-- 2024-07-31T12:13:47.688Z
UPDATE AD_Element_Trl SET Name='TotalLinesWithSurchargeAmt', PrintName='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:13:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583196 AND AD_Language='de_CH'
;

-- 2024-07-31T12:13:47.692Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583196,'de_CH') 
;

-- 2024-07-31T12:13:50.538Z
UPDATE AD_Element_Trl SET Name='TotalLinesWithSurchargeAmt', PrintName='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:13:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583196 AND AD_Language='de_DE'
;

-- 2024-07-31T12:13:50.543Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583196,'de_DE') 
;

-- 2024-07-31T12:13:50.545Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583196,'de_DE') 
;

-- 2024-07-31T12:13:53.312Z
UPDATE AD_Element_Trl SET Name='TotalLinesWithSurchargeAmt', PrintName='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:13:53','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583196 AND AD_Language='en_US'
;

-- 2024-07-31T12:13:53.317Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583196,'en_US') 
;

-- 2024-07-31T12:13:56.293Z
UPDATE AD_Element_Trl SET Name='TotalLinesWithSurchargeAmt', PrintName='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:13:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583196 AND AD_Language='fr_CH'
;

-- 2024-07-31T12:13:56.297Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583196,'fr_CH') 
;

-- 2024-07-31T12:14:11.111Z
UPDATE AD_Element_Trl SET Name='TotalLinesWithSurchargeAmt', PrintName='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:14:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583196 AND AD_Language='nl_NL'
;

-- 2024-07-31T12:14:11.116Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583196,'nl_NL') 
;

-- 2024-07-31T12:14:57.763Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583204,0,'TotalVatWithSurchargeAmt',TO_TIMESTAMP('2024-07-31 14:14:57','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','TotalVatWithSurchargeAmt','TotalVatWithSurchargeAmt',TO_TIMESTAMP('2024-07-31 14:14:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-31T12:14:57.766Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583204 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: EDI_cctop_invoic_v.TotalVatWithSurchargeAmt
-- 2024-07-31T12:15:17.545Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588891,583204,0,12,540462,'TotalVatWithSurchargeAmt',TO_TIMESTAMP('2024-07-31 14:15:17','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'TotalVatWithSurchargeAmt',0,0,TO_TIMESTAMP('2024-07-31 14:15:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-31T12:15:17.548Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588891 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-31T12:15:17.554Z
/* DDL */  select update_Column_Translation_From_AD_Element(583204) 
;

-- 2024-07-31T12:15:35.551Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583205,0,'GrandTotalWithSurchargeAmt',TO_TIMESTAMP('2024-07-31 14:15:35','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','GrandTotalWithSurchargeAmt','GrandTotalWithSurchargeAmt',TO_TIMESTAMP('2024-07-31 14:15:35','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-07-31T12:15:35.554Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583205 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

---
--- fix the names of existing Fields
---

-- Column: EDI_cctop_invoic_v.GrandTotalWithSurchargeAmt
-- 2024-07-31T12:16:19.162Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588892,583205,0,12,540462,'GrandTotalWithSurchargeAmt',TO_TIMESTAMP('2024-07-31 14:16:18','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'GrandTotalWithSurchargeAmt',0,0,TO_TIMESTAMP('2024-07-31 14:16:18','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-07-31T12:16:19.165Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588892 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-31T12:16:19.170Z
/* DDL */  select update_Column_Translation_From_AD_Element(583205) 
;

-- 2024-07-31T12:16:30.013Z
UPDATE AD_Element SET ColumnName='ReceiverGLN',Updated=TO_TIMESTAMP('2024-07-31 14:16:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541983
;

-- 2024-07-31T12:16:30.016Z
UPDATE AD_Column SET ColumnName='ReceiverGLN' WHERE AD_Element_ID=541983
;

-- 2024-07-31T12:16:30.019Z
UPDATE AD_Process_Para SET ColumnName='ReceiverGLN' WHERE AD_Element_ID=541983
;

-- 2024-07-31T12:16:30.027Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541983,'de_DE') 
;

-- 2024-07-31T12:16:50.200Z
UPDATE AD_Element SET ColumnName='SenderGLN',Updated=TO_TIMESTAMP('2024-07-31 14:16:50','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541984
;

-- 2024-07-31T12:16:50.204Z
UPDATE AD_Column SET ColumnName='SenderGLN' WHERE AD_Element_ID=541984
;

-- 2024-07-31T12:16:50.207Z
UPDATE AD_Process_Para SET ColumnName='SenderGLN' WHERE AD_Element_ID=541984
;

-- 2024-07-31T12:16:50.213Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541984,'de_DE') 
;

-- 2024-07-31T12:17:22.664Z
UPDATE AD_Element SET ColumnName='Shipment_DocumentNo',Updated=TO_TIMESTAMP('2024-07-31 14:17:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541980
;

-- 2024-07-31T12:17:22.667Z
UPDATE AD_Column SET ColumnName='Shipment_DocumentNo' WHERE AD_Element_ID=541980
;

-- 2024-07-31T12:17:22.669Z
UPDATE AD_Process_Para SET ColumnName='Shipment_DocumentNo' WHERE AD_Element_ID=541980
;

-- 2024-07-31T12:17:22.675Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541980,'de_DE') 
;

-- 2024-07-31T12:17:47.471Z
UPDATE AD_Element SET ColumnName='TotalTaxBaseAmt',Updated=TO_TIMESTAMP('2024-07-31 14:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541982
;

-- 2024-07-31T12:17:47.473Z
UPDATE AD_Column SET ColumnName='TotalTaxBaseAmt' WHERE AD_Element_ID=541982
;

-- 2024-07-31T12:17:47.475Z
UPDATE AD_Process_Para SET ColumnName='TotalTaxBaseAmt' WHERE AD_Element_ID=541982
;

-- 2024-07-31T12:17:47.483Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541982,'de_DE') 
;

-- 2024-07-31T12:18:03.355Z
UPDATE AD_Element SET ColumnName='TotalVat',Updated=TO_TIMESTAMP('2024-07-31 14:18:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=541981
;

-- 2024-07-31T12:18:03.358Z
UPDATE AD_Column SET ColumnName='TotalVat' WHERE AD_Element_ID=541981
;

-- 2024-07-31T12:18:03.360Z
UPDATE AD_Process_Para SET ColumnName='TotalVat' WHERE AD_Element_ID=541981
;

-- 2024-07-31T12:18:03.365Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(541981,'de_DE') 
;

--
-- add new export format lines
--
-- 2024-07-31T12:19:12.460Z
UPDATE EXP_FormatLine SET Name='TotalTaxBaseAmt', Value='TotalTaxBaseAmt',Updated=TO_TIMESTAMP('2024-07-31 14:19:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545114
;

-- 2024-07-31T12:19:22.745Z
UPDATE EXP_FormatLine SET Name='TotalVat', Value='TotalVat',Updated=TO_TIMESTAMP('2024-07-31 14:19:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545115
;

-- 2024-07-31T12:19:54.357Z
UPDATE EXP_FormatLine SET Name='TotalLinesWithSurchargeAmt', Value='TotalLinesWithSurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:19:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550770
;

-- 2024-07-31T12:20:05.955Z
UPDATE EXP_FormatLine SET Name='SurchargeAmt', Value='SurchargeAmt',Updated=TO_TIMESTAMP('2024-07-31 14:20:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550769
;

-- 2024-07-31T12:20:34.222Z
UPDATE EXP_FormatLine SET Name='ReceiverGLN', Value='ReceiverGLN',Updated=TO_TIMESTAMP('2024-07-31 14:20:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545109
;

-- 2024-07-31T12:20:43.791Z
UPDATE EXP_FormatLine SET Name='SenderGLN', Value='SenderGLN',Updated=TO_TIMESTAMP('2024-07-31 14:20:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545110
;

-- 2024-07-31T12:21:55.248Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588891,0,TO_TIMESTAMP('2024-07-31 14:21:55','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540220,550772,'Y','Y','N','TotalVatWithSurchargeAmt',510,'E',TO_TIMESTAMP('2024-07-31 14:21:55','YYYY-MM-DD HH24:MI:SS'),100,'TotalVatWithSurchargeAmt')
;

-- 2024-07-31T12:22:17.077Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588892,0,TO_TIMESTAMP('2024-07-31 14:22:16','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540220,550773,'Y','Y','N','GrandTotalWithSurchargeAmt',520,'E',TO_TIMESTAMP('2024-07-31 14:22:16','YYYY-MM-DD HH24:MI:SS'),100,'GrandTotalWithSurchargeAmt')
;

