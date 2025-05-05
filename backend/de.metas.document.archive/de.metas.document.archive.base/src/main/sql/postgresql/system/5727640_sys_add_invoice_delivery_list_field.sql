-- Run mode: SWING_CLIENT

-- Name: Invoice Delivery
-- 2024-07-02T07:23:09.717Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541874,TO_TIMESTAMP('2024-07-02 09:23:09.526','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','Y','N','Invoice Delivery',TO_TIMESTAMP('2024-07-02 09:23:09.526','YYYY-MM-DD HH24:MI:SS.US'),100,'L')
;

-- 2024-07-02T07:23:09.722Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541874 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Invoice Delivery
-- Value: P
-- ValueName: PostFinance
-- 2024-07-02T07:24:05.841Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541874,543699,TO_TIMESTAMP('2024-07-02 09:24:05.704','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','Y','PostFinance',TO_TIMESTAMP('2024-07-02 09:24:05.704','YYYY-MM-DD HH24:MI:SS.US'),100,'P','PostFinance')
;

-- 2024-07-02T07:24:05.843Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543699 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Invoice Delivery -> P_PostFinance
-- 2024-07-02T07:24:12.521Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:24:12.521','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543699
;

-- Reference Item: Invoice Delivery -> P_PostFinance
-- 2024-07-02T07:24:14.114Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:24:14.114','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543699
;

-- Reference Item: Invoice Delivery -> P_PostFinance
-- 2024-07-02T07:24:14.738Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:24:14.738','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='it_IT' AND AD_Ref_List_ID=543699
;

-- Reference Item: Invoice Delivery -> P_PostFinance
-- 2024-07-02T07:24:15.858Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:24:15.858','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543699
;

-- Reference Item: Invoice Delivery -> P_PostFinance
-- 2024-07-02T07:24:21.023Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:24:21.023','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='fr_CH' AND AD_Ref_List_ID=543699
;

-- Reference: Invoice Delivery
-- Value: O
-- ValueName: Off
-- 2024-07-02T07:24:59.551Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541874,543700,TO_TIMESTAMP('2024-07-02 09:24:59.416','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','Y','Aus',TO_TIMESTAMP('2024-07-02 09:24:59.416','YYYY-MM-DD HH24:MI:SS.US'),100,'O','Off')
;

-- 2024-07-02T07:24:59.553Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543700 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference Item: Invoice Delivery -> O_Off
-- 2024-07-02T07:25:03.752Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:25:03.752','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_CH' AND AD_Ref_List_ID=543700
;

-- Reference Item: Invoice Delivery -> O_Off
-- 2024-07-02T07:25:05.105Z
UPDATE AD_Ref_List_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:25:05.105','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='de_DE' AND AD_Ref_List_ID=543700
;

-- Reference Item: Invoice Delivery -> O_Off
-- 2024-07-02T07:25:13.256Z
UPDATE AD_Ref_List_Trl SET Name='Off',Updated=TO_TIMESTAMP('2024-07-02 09:25:13.256','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Language='en_US' AND AD_Ref_List_ID=543700
;

-- 2024-07-02T07:28:07.144Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583161,0,'InvoiceDelivery',TO_TIMESTAMP('2024-07-02 09:28:07.013','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.document.archive','Y','Rechnungszustellung','Rechnungszustellung',TO_TIMESTAMP('2024-07-02 09:28:07.013','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-02T07:28:07.147Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583161 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InvoiceDelivery
-- 2024-07-02T07:28:13.705Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:28:13.705','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583161 AND AD_Language='de_CH'
;

-- 2024-07-02T07:28:13.722Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583161,'de_CH')
;

-- Element: InvoiceDelivery
-- 2024-07-02T07:28:14.633Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2024-07-02 09:28:14.633','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583161 AND AD_Language='de_DE'
;

-- 2024-07-02T07:28:14.636Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583161,'de_DE')
;

-- 2024-07-02T07:28:14.637Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583161,'de_DE')
;

-- Element: InvoiceDelivery
-- 2024-07-02T07:28:52.658Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Invoice Delivery', PrintName='Invoice Delivery',Updated=TO_TIMESTAMP('2024-07-02 09:28:52.658','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583161 AND AD_Language='en_US'
;

-- 2024-07-02T07:28:52.661Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583161,'en_US')
;

-- Element: InvoiceDelivery
-- 2024-07-03T12:49:20.093Z
UPDATE AD_Element_Trl SET Description='Partner Group value is used, if Partner value isn''t set',Updated=TO_TIMESTAMP('2024-07-03 14:49:20.093','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583161 AND AD_Language='en_US'
;

-- 2024-07-03T12:49:20.097Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583161,'en_US')
;

-- Element: InvoiceDelivery
-- 2024-07-03T12:50:16.338Z
UPDATE AD_Element_Trl SET Description='Der Wert der Geschäftspartnergruppe wird verwendet, wenn der Geschäftspartner Wert nicht gesetzt ist',Updated=TO_TIMESTAMP('2024-07-03 14:50:16.338','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583161 AND AD_Language='de_CH'
;

-- 2024-07-03T12:50:16.341Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583161,'de_CH')
;

-- Element: InvoiceDelivery
-- 2024-07-03T12:50:27.830Z
UPDATE AD_Element_Trl SET Description='Der Wert der Geschäftspartnergruppe wird verwendet, wenn der Geschäftspartner Wert nicht gesetzt ist',Updated=TO_TIMESTAMP('2024-07-03 14:50:27.83','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583161 AND AD_Language='de_DE'
;

-- 2024-07-03T12:50:27.833Z
UPDATE AD_Element SET Description='Der Wert der Geschäftspartnergruppe wird verwendet, wenn der Geschäftspartner Wert nicht gesetzt ist' WHERE AD_Element_ID=583161
;

-- 2024-07-03T12:50:28.064Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583161,'de_DE')
;

-- 2024-07-03T12:50:28.067Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583161,'de_DE')
;

-- Column: C_BPartner.InvoiceDelivery
-- 2024-07-02T07:32:09.751Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588660,583161,0,17,541874,291,'InvoiceDelivery',TO_TIMESTAMP('2024-07-02 09:32:09.622','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungszustellung',0,0,TO_TIMESTAMP('2024-07-02 09:32:09.622','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-02T07:32:09.754Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588660 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-02T07:32:09.759Z
/* DDL */  select update_Column_Translation_From_AD_Element(583161)
;

-- 2024-07-02T07:32:12.229Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN InvoiceDelivery CHAR(1)')
;

-- Column: C_BP_Group.InvoiceDelivery
-- 2024-07-02T07:34:11.625Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588661,583161,0,17,541874,394,'InvoiceDelivery',TO_TIMESTAMP('2024-07-02 09:34:11.49','YYYY-MM-DD HH24:MI:SS.US'),100,'N','O','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungszustellung',0,0,TO_TIMESTAMP('2024-07-02 09:34:11.49','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-02T07:34:11.628Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588661 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-02T07:34:11.632Z
/* DDL */  select update_Column_Translation_From_AD_Element(583161)
;

-- 2024-07-02T07:34:13.762Z
/* DDL */ SELECT public.db_alter_table('C_BP_Group','ALTER TABLE public.C_BP_Group ADD COLUMN InvoiceDelivery CHAR(1) DEFAULT ''O'' NOT NULL')
;
