-- 2023-05-25T09:20:08.761Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582372,0,'IsReverseCharge',TO_TIMESTAMP('2023-05-25 12:20:08','YYYY-MM-DD HH24:MI:SS'),100,'D','When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','Reverse Charge','Reverse Charge',TO_TIMESTAMP('2023-05-25 12:20:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T09:20:08.768Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582372 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_Tax.IsReverseCharge
-- 2023-05-25T09:20:23.242Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586704,582372,0,20,261,'IsReverseCharge',TO_TIMESTAMP('2023-05-25 12:20:23','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reverse Charge',0,0,TO_TIMESTAMP('2023-05-25 12:20:23','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-25T09:20:23.244Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586704 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-25T09:20:23.790Z
/* DDL */  select update_Column_Translation_From_AD_Element(582372) 
;

-- 2023-05-25T09:20:24.900Z
/* DDL */ SELECT public.db_alter_table('C_Tax','ALTER TABLE public.C_Tax ADD COLUMN IsReverseCharge CHAR(1) DEFAULT ''N'' CHECK (IsReverseCharge IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_OrderTax.IsReverseCharge
-- 2023-05-25T09:20:58.875Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586705,582372,0,20,314,'IsReverseCharge',TO_TIMESTAMP('2023-05-25 12:20:58','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reverse Charge',0,0,TO_TIMESTAMP('2023-05-25 12:20:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-25T09:20:58.877Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586705 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-25T09:20:59.413Z
/* DDL */  select update_Column_Translation_From_AD_Element(582372) 
;

-- 2023-05-25T09:21:01.558Z
/* DDL */ SELECT public.db_alter_table('C_OrderTax','ALTER TABLE public.C_OrderTax ADD COLUMN IsReverseCharge CHAR(1) DEFAULT ''N'' CHECK (IsReverseCharge IN (''Y'',''N'')) NOT NULL')
;

-- 2023-05-25T09:22:16.374Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582373,0,'ReverseChargeTaxAmt',TO_TIMESTAMP('2023-05-25 12:22:16','YYYY-MM-DD HH24:MI:SS'),100,'','D','','Y','Reverse Charge Tax Amount','Reverse Charge Tax',TO_TIMESTAMP('2023-05-25 12:22:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T09:22:16.376Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582373 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: C_OrderTax.ReverseChargeTaxAmt
-- 2023-05-25T09:22:36.416Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586706,582373,0,12,314,'ReverseChargeTaxAmt',TO_TIMESTAMP('2023-05-25 12:22:36','YYYY-MM-DD HH24:MI:SS'),100,'N','0','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reverse Charge Tax Amount',0,0,TO_TIMESTAMP('2023-05-25 12:22:36','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-25T09:22:36.417Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586706 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-25T09:22:36.902Z
/* DDL */  select update_Column_Translation_From_AD_Element(582373) 
;

-- 2023-05-25T09:22:37.678Z
/* DDL */ SELECT public.db_alter_table('C_OrderTax','ALTER TABLE public.C_OrderTax ADD COLUMN ReverseChargeTaxAmt NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: C_InvoiceTax.IsReverseCharge
-- 2023-05-25T09:23:24.826Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586707,582372,0,20,334,'IsReverseCharge',TO_TIMESTAMP('2023-05-25 12:23:24','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reverse Charge',0,0,TO_TIMESTAMP('2023-05-25 12:23:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-25T09:23:24.829Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586707 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-25T09:23:25.410Z
/* DDL */  select update_Column_Translation_From_AD_Element(582372) 
;

-- 2023-05-25T09:23:27.044Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceTax','ALTER TABLE public.C_InvoiceTax ADD COLUMN IsReverseCharge CHAR(1) DEFAULT ''N'' CHECK (IsReverseCharge IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_InvoiceTax.ReverseChargeTaxAmt
-- 2023-05-25T09:23:40.542Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586708,582373,0,12,334,'ReverseChargeTaxAmt',TO_TIMESTAMP('2023-05-25 12:23:39','YYYY-MM-DD HH24:MI:SS'),100,'N','0','','D',0,10,'','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Reverse Charge Tax Amount',0,0,TO_TIMESTAMP('2023-05-25 12:23:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-05-25T09:23:40.544Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586708 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-05-25T09:23:41.082Z
/* DDL */  select update_Column_Translation_From_AD_Element(582373) 
;

-- 2023-05-25T09:23:41.865Z
/* DDL */ SELECT public.db_alter_table('C_InvoiceTax','ALTER TABLE public.C_InvoiceTax ADD COLUMN ReverseChargeTaxAmt NUMERIC DEFAULT 0 NOT NULL')
;

-- Field: Tax Rate(137,D) -> Tax(174,D) -> Reverse Charge
-- Column: C_Tax.IsReverseCharge
-- 2023-05-25T12:33:27.578Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586704,715578,0,174,TO_TIMESTAMP('2023-05-25 15:33:27','YYYY-MM-DD HH24:MI:SS'),100,1,'D','When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','N','N','N','N','N','N','Reverse Charge',TO_TIMESTAMP('2023-05-25 15:33:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T12:33:27.580Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715578 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-25T12:33:27.583Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582372) 
;

-- 2023-05-25T12:33:27.596Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715578
;

-- 2023-05-25T12:33:27.598Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715578)
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> property.Reverse Charge
-- Column: C_Tax.IsReverseCharge
-- 2023-05-25T12:34:18.841Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715578,0,174,545777,617553,'F',TO_TIMESTAMP('2023-05-25 15:34:18','YYYY-MM-DD HH24:MI:SS'),100,'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','Y','N','N','Reverse Charge',50,0,0,TO_TIMESTAMP('2023-05-25 15:34:18','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> property.Reverse Charge
-- Column: C_Tax.IsReverseCharge
-- 2023-05-25T12:34:27.670Z
UPDATE AD_UI_Element SET SeqNo=15,Updated=TO_TIMESTAMP('2023-05-25 15:34:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617553
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> Reverse Charge
-- Column: C_InvoiceTax.IsReverseCharge
-- 2023-05-25T12:37:49.633Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586707,715974,0,292,TO_TIMESTAMP('2023-05-25 15:37:49','YYYY-MM-DD HH24:MI:SS'),100,1,'D','When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','N','N','N','N','N','N','Reverse Charge',TO_TIMESTAMP('2023-05-25 15:37:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T12:37:49.635Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715974 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-25T12:37:49.637Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582372) 
;

-- 2023-05-25T12:37:49.642Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715974
;

-- 2023-05-25T12:37:49.643Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715974)
;

-- Field: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> Reverse Charge Tax Amount
-- Column: C_InvoiceTax.ReverseChargeTaxAmt
-- 2023-05-25T12:37:49.771Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586708,715975,0,292,TO_TIMESTAMP('2023-05-25 15:37:49','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Reverse Charge Tax Amount',TO_TIMESTAMP('2023-05-25 15:37:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T12:37:49.772Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715975 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-25T12:37:49.774Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582373) 
;

-- 2023-05-25T12:37:49.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715975
;

-- 2023-05-25T12:37:49.779Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715975)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Reverse Charge
-- Column: C_InvoiceTax.IsReverseCharge
-- 2023-05-25T12:38:29.412Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715974,0,292,540220,617753,'F',TO_TIMESTAMP('2023-05-25 15:38:29','YYYY-MM-DD HH24:MI:SS'),100,'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','Y','N','N','Reverse Charge',20,0,0,TO_TIMESTAMP('2023-05-25 15:38:29','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Reverse Charge Tax Amount
-- Column: C_InvoiceTax.ReverseChargeTaxAmt
-- 2023-05-25T12:38:36.717Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715975,0,292,540220,617770,'F',TO_TIMESTAMP('2023-05-25 15:38:36','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Reverse Charge Tax Amount',30,0,0,TO_TIMESTAMP('2023-05-25 15:38:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Reverse Charge Tax Amount
-- Column: C_InvoiceTax.ReverseChargeTaxAmt
-- 2023-05-25T12:39:35.166Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617770
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Bezugswert
-- Column: C_InvoiceTax.TaxBaseAmt
-- 2023-05-25T12:39:35.173Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542676
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Preis inklusive Steuern
-- Column: C_InvoiceTax.IsTaxIncluded
-- 2023-05-25T12:39:35.180Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542677
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Whole Tax
-- Column: C_InvoiceTax.IsWholeTax
-- 2023-05-25T12:39:35.186Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542678
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Reverse Charge
-- Column: C_InvoiceTax.IsReverseCharge
-- 2023-05-25T12:39:35.193Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617753
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Packaging Tax
-- Column: C_InvoiceTax.IsPackagingTax
-- 2023-05-25T12:39:35.199Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542679
;

-- UI Element: Invoice (Vendor)_OLD(183,D) -> Invoice Tax(292,D) -> main -> 10 -> default.Sektion
-- Column: C_InvoiceTax.AD_Org_ID
-- 2023-05-25T12:39:35.205Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-05-25 15:39:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=542672
;

-- Field: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> Reverse Charge
-- Column: C_OrderTax.IsReverseCharge
-- 2023-05-25T14:05:16.017Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586705,715976,0,295,TO_TIMESTAMP('2023-05-25 17:05:15','YYYY-MM-DD HH24:MI:SS'),100,1,'D','When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','N','N','N','N','N','N','Reverse Charge',TO_TIMESTAMP('2023-05-25 17:05:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T14:05:16.019Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715976 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-25T14:05:16.021Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582372) 
;

-- 2023-05-25T14:05:16.027Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715976
;

-- 2023-05-25T14:05:16.028Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715976)
;

-- Field: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> Reverse Charge Tax Amount
-- Column: C_OrderTax.ReverseChargeTaxAmt
-- 2023-05-25T14:05:16.152Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586706,715977,0,295,TO_TIMESTAMP('2023-05-25 17:05:16','YYYY-MM-DD HH24:MI:SS'),100,'',10,'D','','Y','N','N','N','N','N','N','N','Reverse Charge Tax Amount',TO_TIMESTAMP('2023-05-25 17:05:16','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-05-25T14:05:16.154Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=715977 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-05-25T14:05:16.155Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582373) 
;

-- 2023-05-25T14:05:16.158Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=715977
;

-- 2023-05-25T14:05:16.159Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(715977)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Reverse Charge
-- Column: C_OrderTax.IsReverseCharge
-- 2023-05-25T14:09:47.494Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715976,0,295,540071,617874,'F',TO_TIMESTAMP('2023-05-25 17:09:47','YYYY-MM-DD HH24:MI:SS'),100,'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service.','Y','N','Y','N','N','Reverse Charge',100,0,0,TO_TIMESTAMP('2023-05-25 17:09:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Reverse Charge Tax Amount
-- Column: C_OrderTax.ReverseChargeTaxAmt
-- 2023-05-25T14:09:55.003Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,715977,0,295,540071,617875,'F',TO_TIMESTAMP('2023-05-25 17:09:54','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','Y','N','N','Reverse Charge Tax Amount',110,0,0,TO_TIMESTAMP('2023-05-25 17:09:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Reverse Charge Tax Amount
-- Column: C_OrderTax.ReverseChargeTaxAmt
-- 2023-05-25T14:10:46.136Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617875
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Bezugswert
-- Column: C_OrderTax.TaxBaseAmt
-- 2023-05-25T14:10:46.143Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541275
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Preis inklusive Steuern
-- Column: C_OrderTax.IsTaxIncluded
-- 2023-05-25T14:10:46.151Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541276
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Whole Tax
-- Column: C_OrderTax.IsWholeTax
-- 2023-05-25T14:10:46.158Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541277
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Reverse Charge
-- Column: C_OrderTax.IsReverseCharge
-- 2023-05-25T14:10:46.164Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=617874
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Packaging Tax
-- Column: C_OrderTax.IsPackagingTax
-- 2023-05-25T14:10:46.170Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541278
;

-- UI Element: Purchase Order_OLD(181,D) -> Order Tax(295,D) -> main -> 10 -> default.Sektion
-- Column: C_OrderTax.AD_Org_ID
-- 2023-05-25T14:10:46.176Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-05-25 17:10:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=541271
;

