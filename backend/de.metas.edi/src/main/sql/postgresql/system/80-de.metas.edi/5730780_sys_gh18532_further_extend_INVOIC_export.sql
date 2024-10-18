-- 2024-08-05T08:37:43.945Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583212,0,'SingleSurchargePercent',TO_TIMESTAMP('2024-08-05 10:37:43','YYYY-MM-DD HH24:MI:SS'),100,'Logically, this is set if there is just one surcharge (+ or -) on the document level. In practice, we currently always have max one surcharge.','de.metas.esb.edi','Y','SingleSurchargePercent','SingleSurchargePercent',TO_TIMESTAMP('2024-08-05 10:37:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-05T08:37:43.959Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583212 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: EDI_cctop_901_991_v.SingleSurchargePercent
-- 2024-08-05T08:38:14.763Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588903,583212,0,22,540469,'SingleSurchargePercent',TO_TIMESTAMP('2024-08-05 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,'N','Logically, this is set if there is just one surcharge (+ or -) on the document level. In practice, we currently always have max one surcharge.','de.metas.esb.edi',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'SingleSurchargePercent',0,0,TO_TIMESTAMP('2024-08-05 10:38:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T08:38:14.766Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588903 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T08:38:14.807Z
/* DDL */  select update_Column_Translation_From_AD_Element(583212) 
;

-- 2024-08-05T08:41:05.228Z
UPDATE EXP_FormatLine SET Position=120,Updated=TO_TIMESTAMP('2024-08-05 10:41:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550776
;

-- 2024-08-05T08:41:08.003Z
UPDATE EXP_FormatLine SET Position=110,Updated=TO_TIMESTAMP('2024-08-05 10:41:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550775
;

-- 2024-08-05T08:41:14.066Z
UPDATE EXP_FormatLine SET Position=100,Updated=TO_TIMESTAMP('2024-08-05 10:41:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550774
;

-- 2024-08-05T08:41:36.351Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588903,0,TO_TIMESTAMP('2024-08-05 10:41:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540227,550777,'Y','N','N','SingleSurchargePercent',90,'E',TO_TIMESTAMP('2024-08-05 10:41:36','YYYY-MM-DD HH24:MI:SS'),100,'SingleSurchargePercent')
;

-- 2024-08-05T08:41:47.008Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-05 10:41:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545173
;

-- 2024-08-05T08:41:49.858Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-05 10:41:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545174
;

-- 2024-08-05T08:41:52.184Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-05 10:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545175
;

-- 2024-08-05T08:41:52.766Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-05 10:41:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545176
;

-- 2024-08-05T08:41:56.204Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-05 10:41:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545177
;

-- 2024-08-05T08:42:03.325Z
UPDATE EXP_FormatLine SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-08-05 10:42:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545178
;

-- Column: EDI_cctop_901_991_v.IsTaxExempt
-- 2024-08-05T08:46:22.297Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588904,930,0,20,540469,'IsTaxExempt',TO_TIMESTAMP('2024-08-05 10:46:22','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Steuersatz steuerbefreit','de.metas.esb.edi',0,1,'If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Steuerbefreit',0,0,TO_TIMESTAMP('2024-08-05 10:46:22','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T08:46:22.300Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588904 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T08:46:22.305Z
/* DDL */  select update_Column_Translation_From_AD_Element(930) 
;

-- 2024-08-05T08:46:39.484Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588904,0,TO_TIMESTAMP('2024-08-05 10:46:39','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540227,550778,'Y','Y','N','IsTaxExempt',130,'E',TO_TIMESTAMP('2024-08-05 10:46:39','YYYY-MM-DD HH24:MI:SS'),100,'IsTaxExempt')
;

-- Column: EDI_cctop_invoic_500_v.Discount
-- 2024-08-05T08:55:13.618Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588905,280,0,22,540463,'Discount',TO_TIMESTAMP('2024-08-05 10:55:13','YYYY-MM-DD HH24:MI:SS'),100,'N','Abschlag in Prozent','de.metas.esb.edi',0,14,'"Rabatt %" bezeichnet den angewendeten Abschlag in Prozent.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Rabatt %',0,0,TO_TIMESTAMP('2024-08-05 10:55:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T08:55:13.622Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588905 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T08:55:13.629Z
/* DDL */  select update_Column_Translation_From_AD_Element(280) 
;

-- 2024-08-05T08:56:37.003Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588905,0,TO_TIMESTAMP('2024-08-05 10:56:36','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550779,'Y','N','N','Discount',390,'E',TO_TIMESTAMP('2024-08-05 10:56:36','YYYY-MM-DD HH24:MI:SS'),100,'Discount')
;

-- Column: EDI_cctop_invoic_500_v.IsTaxExempt
-- 2024-08-05T08:57:30.736Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588906,930,0,20,540463,'IsTaxExempt',TO_TIMESTAMP('2024-08-05 10:57:30','YYYY-MM-DD HH24:MI:SS'),100,'N','N','Steuersatz steuerbefreit','de.metas.esb.edi',0,1,'If a business partner is exempt from tax on sales, the exempt tax rate is used. For this, you need to set up a tax rate with a 0% rate and indicate that this is your tax exempt rate.  This is required for tax reporting, so that you can track tax exempt transactions.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Steuerbefreit',0,0,TO_TIMESTAMP('2024-08-05 10:57:30','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T08:57:30.739Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588906 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T08:57:30.744Z
/* DDL */  select update_Column_Translation_From_AD_Element(930) 
;

-- 2024-08-05T08:57:46.271Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588906,0,TO_TIMESTAMP('2024-08-05 10:57:46','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540221,550780,'Y','Y','N','IsTaxExempt',400,'E',TO_TIMESTAMP('2024-08-05 10:57:46','YYYY-MM-DD HH24:MI:SS'),100,'IsTaxExempt')
;

-- 2024-08-05T08:58:21.413Z
UPDATE EXP_FormatLine SET Position=65,Updated=TO_TIMESTAMP('2024-08-05 10:58:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550779
;

-- 2024-08-05T08:59:27.730Z
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-08-05 10:59:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550780
;

-- 2024-08-05T09:00:02.209Z
UPDATE EXP_FormatLine SET Position=135,Updated=TO_TIMESTAMP('2024-08-05 11:00:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550780
;

-- 2024-08-05T09:00:13.737Z
UPDATE EXP_FormatLine SET Position=140,Updated=TO_TIMESTAMP('2024-08-05 11:00:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550780
;

-- 2024-08-05T09:02:24.847Z
UPDATE EXP_FormatLine SET Description='If true, then this C_Tax is flagged as tex-exempt',Updated=TO_TIMESTAMP('2024-08-05 11:02:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550780
;

-- 2024-08-05T09:02:34.479Z
UPDATE EXP_FormatLine SET Description='If true, then the tax rate is zero',Updated=TO_TIMESTAMP('2024-08-05 11:02:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=545128
;

-- 2024-08-05T09:07:21.271Z
UPDATE AD_Element_Trl SET Description='Achtung, Steuersatz kann auch nur 0% (steuerfrei) sein.', Help='Wenn ein Geschäftspartner von der Umsatzsteuer befreit ist, wird der befreite Steuersatz verwendet. Dazu müssen Sie einen Steuersatz mit einem Satz von 0 % einrichten und angeben, dass dies Ihr steuerbefreiter Satz ist.  Dies ist für die Steuerberichterstattung erforderlich, damit Sie steuerbefreite Transaktionen verfolgen können.',Updated=TO_TIMESTAMP('2024-08-05 11:07:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=930 AND AD_Language='de_DE'
;

-- 2024-08-05T09:07:21.279Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(930,'de_DE') 
;

-- 2024-08-05T09:07:21.286Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(930,'de_DE') 
;

-- 2024-08-05T09:14:30.411Z
DELETE FROM EXP_FormatLine WHERE EXP_FormatLine_ID=550777
;

-- Column: EDI_cctop_901_991_v.SingleSurchargePercent
-- 2024-08-05T09:14:49.803Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588903
;

-- 2024-08-05T09:14:49.815Z
DELETE FROM AD_Column WHERE AD_Column_ID=588903
;

-- 2024-08-05T09:14:55.015Z
DELETE FROM  AD_Element_Trl WHERE AD_Element_ID=583212
;

-- 2024-08-05T09:14:55.024Z
DELETE FROM AD_Element WHERE AD_Element_ID=583212
;

-- Column: EDI_cctop_140_v.Name
-- 2024-08-05T09:27:15.468Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588907,469,0,10,540468,'Name',TO_TIMESTAMP('2024-08-05 11:27:15','YYYY-MM-DD HH24:MI:SS'),100,'N','','de.metas.esb.edi',0,70,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Name',0,1,TO_TIMESTAMP('2024-08-05 11:27:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T09:27:15.472Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588907 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T09:27:15.478Z
/* DDL */  select update_Column_Translation_From_AD_Element(469) 
;

-- 2024-08-05T09:28:54.825Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583213,0,'DiscountBase',TO_TIMESTAMP('2024-08-05 11:28:54','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','DiscountBase','DiscountBase',TO_TIMESTAMP('2024-08-05 11:28:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-05T09:28:54.829Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583213 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-08-05T09:29:10.554Z
UPDATE AD_Element SET ColumnName='DiscountBaseAmt',Updated=TO_TIMESTAMP('2024-08-05 11:29:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583213
;

-- 2024-08-05T09:29:10.557Z
UPDATE AD_Column SET ColumnName='DiscountBaseAmt' WHERE AD_Element_ID=583213
;

-- 2024-08-05T09:29:10.558Z
UPDATE AD_Process_Para SET ColumnName='DiscountBaseAmt' WHERE AD_Element_ID=583213
;

-- 2024-08-05T09:29:10.566Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583213,'de_DE') 
;

-- 2024-08-05T09:29:22.726Z
UPDATE AD_Element_Trl SET Name='DiscountBaseAmt', PrintName='DiscountBaseAmt',Updated=TO_TIMESTAMP('2024-08-05 11:29:22','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583213 AND AD_Language='de_CH'
;

-- 2024-08-05T09:29:22.732Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583213,'de_CH') 
;

-- 2024-08-05T09:29:25.517Z
UPDATE AD_Element_Trl SET Name='DiscountBaseAmt', PrintName='DiscountBaseAmt',Updated=TO_TIMESTAMP('2024-08-05 11:29:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583213 AND AD_Language='de_DE'
;

-- 2024-08-05T09:29:25.522Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583213,'de_DE') 
;

-- 2024-08-05T09:29:25.524Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583213,'de_DE') 
;

-- 2024-08-05T09:29:28.279Z
UPDATE AD_Element_Trl SET Name='DiscountBaseAmt', PrintName='DiscountBaseAmt',Updated=TO_TIMESTAMP('2024-08-05 11:29:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583213 AND AD_Language='en_US'
;

-- 2024-08-05T09:29:28.284Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583213,'en_US') 
;

-- 2024-08-05T09:29:30.966Z
UPDATE AD_Element_Trl SET Name='DiscountBaseAmt', PrintName='DiscountBaseAmt',Updated=TO_TIMESTAMP('2024-08-05 11:29:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=583213 AND AD_Language='fr_CH'
;

-- 2024-08-05T09:29:30.971Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583213,'fr_CH') 
;

-- Column: EDI_cctop_140_v.DiscountBaseAmt
-- 2024-08-05T09:30:05.499Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588908,583213,0,22,540468,'DiscountBaseAmt',TO_TIMESTAMP('2024-08-05 11:30:05','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.esb.edi',0,14,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'DiscountBaseAmt',0,0,TO_TIMESTAMP('2024-08-05 11:30:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T09:30:05.502Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588908 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T09:30:05.507Z
/* DDL */  select update_Column_Translation_From_AD_Element(583213) 
;

-- Column: EDI_cctop_140_v.DiscountAmt
-- 2024-08-05T09:30:38.539Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588909,1395,0,12,540468,'DiscountAmt',TO_TIMESTAMP('2024-08-05 11:30:38','YYYY-MM-DD HH24:MI:SS'),100,'N','Calculated amount of discount','de.metas.esb.edi',0,10,'The Discount Amount indicates the discount amount for a document or line.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Skonto',0,0,TO_TIMESTAMP('2024-08-05 11:30:38','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-05T09:30:38.542Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588909 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-05T09:30:38.546Z
/* DDL */  select update_Column_Translation_From_AD_Element(1395) 
;

-- Column: EDI_cctop_140_v.DiscountBaseAmt
-- 2024-08-05T09:30:48.471Z
UPDATE AD_Column SET AD_Reference_ID=12,Updated=TO_TIMESTAMP('2024-08-05 11:30:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588908
;

-- 2024-08-05T09:32:19.651Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588907,0,TO_TIMESTAMP('2024-08-05 11:32:19','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540226,550781,'Y','N','N','Name',70,'E',TO_TIMESTAMP('2024-08-05 11:32:19','YYYY-MM-DD HH24:MI:SS'),100,'Name')
;

-- 2024-08-05T09:32:49.042Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588909,0,TO_TIMESTAMP('2024-08-05 11:32:48','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540226,550782,'Y','Y','N','DiscountAmt',80,'E',TO_TIMESTAMP('2024-08-05 11:32:48','YYYY-MM-DD HH24:MI:SS'),100,'DiscountAmt')
;

-- 2024-08-05T09:33:11.764Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588908,0,TO_TIMESTAMP('2024-08-05 11:33:11','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540226,550783,'Y','N','N','DiscountBaseAmt',90,'E',TO_TIMESTAMP('2024-08-05 11:33:11','YYYY-MM-DD HH24:MI:SS'),100,'DiscountBaseAmt')
;

-- 2024-08-05T09:33:21.037Z
UPDATE EXP_FormatLine SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-08-05 11:33:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550782
;

-- Column: EDI_cctop_140_v.DiscountAmt
-- 2024-08-05T09:33:31.631Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2024-08-05 11:33:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=588909
;

-- 2024-08-06T07:49:48.164Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583215,0,'IsMainVAT',TO_TIMESTAMP('2024-08-06 09:49:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Hauptmehrwertsteuersatz','Hauptmehrwertsteuersatz',TO_TIMESTAMP('2024-08-06 09:49:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-08-06T07:49:48.173Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583215 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: EDI_cctop_901_991_v.IsMainVAT
-- 2024-08-06T07:50:15.262Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588910,583215,0,20,540469,'IsMainVAT',TO_TIMESTAMP('2024-08-06 09:50:15','YYYY-MM-DD HH24:MI:SS'),100,'N','N','de.metas.esb.edi',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Hauptmehrwertsteuersatz',0,0,TO_TIMESTAMP('2024-08-06 09:50:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-08-06T07:50:15.268Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588910 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-08-06T07:50:15.281Z
/* DDL */  select update_Column_Translation_From_AD_Element(583215) 
;

-- 2024-08-06T07:50:58.419Z
INSERT INTO EXP_FormatLine (AD_Client_ID,AD_Column_ID,AD_Org_ID,Created,CreatedBy,EntityType,EXP_Format_ID,EXP_FormatLine_ID,IsActive,IsMandatory,IsPartUniqueIndex,Name,Position,Type,Updated,UpdatedBy,Value) VALUES (0,588910,0,TO_TIMESTAMP('2024-08-06 09:50:58','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi',540227,550784,'Y','Y','N','IsMainVAT',140,'E',TO_TIMESTAMP('2024-08-06 09:50:58','YYYY-MM-DD HH24:MI:SS'),100,'IsMainVAT')
;

-- 2024-08-06T07:51:21.427Z
UPDATE EXP_FormatLine SET Position=43,Updated=TO_TIMESTAMP('2024-08-06 09:51:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE EXP_FormatLine_ID=550778
;

