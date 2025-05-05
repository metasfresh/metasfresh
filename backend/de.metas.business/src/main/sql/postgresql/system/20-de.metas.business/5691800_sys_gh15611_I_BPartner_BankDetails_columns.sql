-- 2023-06-15T07:43:03.132667500Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582440,0,'BankDetails',TO_TIMESTAMP('2023-06-15 10:43:02.983','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Bank Details','Bank Details',TO_TIMESTAMP('2023-06-15 10:43:02.983','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-06-15T07:43:03.135670900Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582440 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: BankDetails
-- 2023-06-15T07:43:10.559467700Z
UPDATE AD_Element_Trl SET Name='Bankverbindung', PrintName='Bankverbindung',Updated=TO_TIMESTAMP('2023-06-15 10:43:10.558','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582440 AND AD_Language='de_CH'
;

-- 2023-06-15T07:43:10.560463600Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582440,'de_CH') 
;

-- Element: BankDetails
-- 2023-06-15T07:43:13.357232700Z
UPDATE AD_Element_Trl SET Name='Bankverbindung', PrintName='Bankverbindung',Updated=TO_TIMESTAMP('2023-06-15 10:43:13.357','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582440 AND AD_Language='de_DE'
;

-- 2023-06-15T07:43:13.358237400Z
UPDATE AD_Element SET Name='Bankverbindung', PrintName='Bankverbindung' WHERE AD_Element_ID=582440
;

-- 2023-06-15T07:43:13.754852600Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582440,'de_DE') 
;

-- 2023-06-15T07:43:13.755852800Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582440,'de_DE') 
;

-- Element: BankDetails
-- 2023-06-15T07:43:16.733220800Z
UPDATE AD_Element_Trl SET Name='Bankverbindung', PrintName='Bankverbindung',Updated=TO_TIMESTAMP('2023-06-15 10:43:16.733','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=582440 AND AD_Language='fr_CH'
;

-- 2023-06-15T07:43:16.735223Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582440,'fr_CH') 
;



-- Column: I_BPartner.BankDetails
-- 2023-06-15T07:43:40.391987400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586817,582440,0,10,533,'BankDetails',TO_TIMESTAMP('2023-06-15 10:43:40.251','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Bankverbindung',0,0,TO_TIMESTAMP('2023-06-15 10:43:40.251','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-15T07:43:40.394985Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586817 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-15T07:43:40.874566200Z
/* DDL */  select update_Column_Translation_From_AD_Element(582440) 
;

-- 2023-06-15T07:43:51.018910300Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN BankDetails VARCHAR(255)')
;

-- Column: I_BPartner.QR_IBAN
-- 2023-06-15T07:44:59.826889Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586818,577768,0,10,533,'QR_IBAN',TO_TIMESTAMP('2023-06-15 10:44:59.685','YYYY-MM-DD HH24:MI:SS.US'),100,'N','International Bank Account Number','D',0,34,'For payments with a structured QR reference, the QR-IBAN must be used to indicate the account to be credited. The formal structure of the QR-IBAN corresponds to the rules stipulated in ISO 13616 standard for IBAN.
IBAN/QR-IBAN from the Swiss QR Code. Printed in blocks of 4 characters (5x4-character groups, the last character separate).','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'QR IBAN',0,0,TO_TIMESTAMP('2023-06-15 10:44:59.685','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-06-15T07:44:59.828886500Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586818 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-06-15T07:45:00.424672800Z
/* DDL */  select update_Column_Translation_From_AD_Element(577768) 
;

-- 2023-06-15T07:45:02.713180Z
/* DDL */ SELECT public.db_alter_table('I_BPartner','ALTER TABLE public.I_BPartner ADD COLUMN QR_IBAN VARCHAR(34)')
;

