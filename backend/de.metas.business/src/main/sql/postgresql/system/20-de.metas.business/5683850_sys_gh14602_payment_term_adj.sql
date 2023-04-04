-- UI Element: Payment Term(141,D) -> Payment Term(184,D) -> main -> 20 -> flags.Nächster Geschäftstag
-- Column: C_PaymentTerm.IsNextBusinessDay
-- 2023-04-03T15:24:37.876Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545048
;

-- UI Element: Payment Term(141,D) -> Payment Term(184,D) -> main -> 20 -> flags.Nach Lieferung
-- Column: C_PaymentTerm.AfterDelivery
-- 2023-04-03T15:24:41.745Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=545049
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> After Delivery
-- Column: C_PaymentTerm.AfterDelivery
-- 2023-04-03T15:25:01.652Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-04-03 17:25:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1068
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> After Delivery
-- Column: C_PaymentTerm.AfterDelivery
-- 2023-04-03T15:25:28.588Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2023-04-03 17:25:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=1068
;

-- 2023-04-03T15:25:35.123Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=1068
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> After Delivery
-- Column: C_PaymentTerm.AfterDelivery
-- 2023-04-03T15:25:35.126Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=1068
;

-- 2023-04-03T15:25:35.129Z
DELETE FROM AD_Field WHERE AD_Field_ID=1068
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> Next Business Day
-- Column: C_PaymentTerm.IsNextBusinessDay
-- 2023-04-03T15:26:08.028Z
UPDATE AD_Field SET IsDisplayed='N', IsDisplayedGrid='N',Updated=TO_TIMESTAMP('2023-04-03 17:26:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=3081
;

-- 2023-04-03T15:26:10.807Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=3081
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> Next Business Day
-- Column: C_PaymentTerm.IsNextBusinessDay
-- 2023-04-03T15:26:10.810Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=3081
;

-- 2023-04-03T15:26:10.813Z
DELETE FROM AD_Field WHERE AD_Field_ID=3081
;

-- 2023-04-03T15:48:08.952Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582205,0,'BaseLineTypes',TO_TIMESTAMP('2023-04-03 17:48:08','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Base Line Types','Base Line Types',TO_TIMESTAMP('2023-04-03 17:48:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T15:48:08.954Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582205 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-04-03T15:48:34.157Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582206,0,'CalculationMethod',TO_TIMESTAMP('2023-04-03 17:48:34','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Calculation Method','Calculation Method',TO_TIMESTAMP('2023-04-03 17:48:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T15:48:34.159Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582206 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-04-03T15:48:48.741Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-04-03 17:48:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582206
;

-- 2023-04-03T15:48:48.763Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582206,'en_US') 
;

-- 2023-04-03T15:48:57.116Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2023-04-03 17:48:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582205
;

-- 2023-04-03T15:48:57.118Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582205,'en_US') 
;

-- Name: Base Line Types List
-- 2023-04-03T15:49:58.099Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541725,TO_TIMESTAMP('2023-04-03 17:49:57','YYYY-MM-DD HH24:MI:SS'),100,'Base Line Types List','D','Y','N','Base Line Types List',TO_TIMESTAMP('2023-04-03 17:49:57','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-04-03T15:49:58.100Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541725 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Base Line Types List
-- Value: AD
-- ValueName: After Delivery
-- 2023-04-03T15:50:29.933Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541725,543427,TO_TIMESTAMP('2023-04-03 17:50:29','YYYY-MM-DD HH24:MI:SS'),100,'After Delivery','D','Y','After Delivery',TO_TIMESTAMP('2023-04-03 17:50:29','YYYY-MM-DD HH24:MI:SS'),100,'AD','After Delivery')
;

-- 2023-04-03T15:50:29.934Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543427 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Base Line Types List
-- Value: ABL
-- ValueName: After Bill of Landing
-- 2023-04-03T15:50:56.478Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541725,543428,TO_TIMESTAMP('2023-04-03 17:50:56','YYYY-MM-DD HH24:MI:SS'),100,'After Bill of Landing','D','Y','After Bill of Landing',TO_TIMESTAMP('2023-04-03 17:50:56','YYYY-MM-DD HH24:MI:SS'),100,'ABL','After Bill of Landing')
;

-- 2023-04-03T15:50:56.479Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543428 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Base Line Types List
-- Value: ID
-- ValueName: Invoice Date
-- 2023-04-03T15:51:22.974Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541725,543429,TO_TIMESTAMP('2023-04-03 17:51:22','YYYY-MM-DD HH24:MI:SS'),100,'Invoice Date','D','Y','Invoice Date',TO_TIMESTAMP('2023-04-03 17:51:22','YYYY-MM-DD HH24:MI:SS'),100,'ID','Invoice Date')
;

-- 2023-04-03T15:51:22.975Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543429 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_PaymentTerm.BaseLineTypes
-- 2023-04-03T15:52:13.427Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586411,582205,0,17,541725,113,'BaseLineTypes',TO_TIMESTAMP('2023-04-03 17:52:13','YYYY-MM-DD HH24:MI:SS'),100,'N','ID','D',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Base Line Types',0,0,TO_TIMESTAMP('2023-04-03 17:52:13','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T15:52:13.428Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586411 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T15:52:13.806Z
/* DDL */  select update_Column_Translation_From_AD_Element(582205) 
;

-- 2023-04-03T15:52:47.371Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN BaseLineTypes VARCHAR(3) DEFAULT ''ID''')
;

-- Reference: Base Line Types List
-- Value: AD
-- ValueName: After Delivery
-- 2023-04-03T15:53:39.207Z
UPDATE AD_Ref_List SET Description='The goods issue date is used.',Updated=TO_TIMESTAMP('2023-04-03 17:53:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543427
;

-- 2023-04-03T15:53:39.208Z
UPDATE AD_Ref_List_Trl trl SET Description='The goods issue date is used.' WHERE AD_Ref_List_ID=543427 AND AD_Language='en_US'
;

-- Reference: Base Line Types List
-- Value: ABL
-- ValueName: After Bill of Landing
-- 2023-04-03T15:54:01.583Z
UPDATE AD_Ref_List SET Description='The actual landing date is taken from the delivery planning',Updated=TO_TIMESTAMP('2023-04-03 17:54:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Ref_List_ID=543428
;

-- 2023-04-03T15:54:01.584Z
UPDATE AD_Ref_List_Trl trl SET Description='The actual landing date is taken from the delivery planning' WHERE AD_Ref_List_ID=543428 AND AD_Language='en_US'
;

-- Name: Calculation Method List
-- 2023-04-03T15:55:31.495Z
INSERT INTO AD_Reference (AD_Client_ID,AD_Org_ID,AD_Reference_ID,Created,CreatedBy,Description,EntityType,IsActive,IsOrderByValue,Name,Updated,UpdatedBy,ValidationType) VALUES (0,0,541726,TO_TIMESTAMP('2023-04-03 17:55:31','YYYY-MM-DD HH24:MI:SS'),100,'Calculation Method List','D','Y','N','Calculation Method List',TO_TIMESTAMP('2023-04-03 17:55:31','YYYY-MM-DD HH24:MI:SS'),100,'L')
;

-- 2023-04-03T15:55:31.496Z
INSERT INTO AD_Reference_Trl (AD_Language,AD_Reference_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Reference_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Reference t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Reference_ID=541726 AND NOT EXISTS (SELECT 1 FROM AD_Reference_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Reference_ID=t.AD_Reference_ID)
;

-- Reference: Calculation Method List
-- Value: BLDX
-- ValueName: Base Line Date +X days
-- 2023-04-03T15:56:29.442Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541726,543430,TO_TIMESTAMP('2023-04-03 17:56:29','YYYY-MM-DD HH24:MI:SS'),100,'Base Line Date +X days','D','Y','Base Line Date +X days',TO_TIMESTAMP('2023-04-03 17:56:29','YYYY-MM-DD HH24:MI:SS'),100,'BLDX','Base Line Date +X days')
;

-- 2023-04-03T15:56:29.443Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543430 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Calculation Method List
-- Value: BLDXE
-- ValueName: Base Line Date +X days and then end of month
-- 2023-04-03T15:57:10.468Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541726,543431,TO_TIMESTAMP('2023-04-03 17:57:10','YYYY-MM-DD HH24:MI:SS'),100,'Base Line Date +X days and then end of month','D','Y','Base Line Date +X days and then end of month',TO_TIMESTAMP('2023-04-03 17:57:10','YYYY-MM-DD HH24:MI:SS'),100,'BLDXE','Base Line Date +X days and then end of month')
;

-- 2023-04-03T15:57:10.468Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543431 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Reference: Calculation Method List
-- Value: EBLDX
-- ValueName: End of the month of baseline date plus X days
-- 2023-04-03T15:57:40.930Z
INSERT INTO AD_Ref_List (AD_Client_ID,AD_Org_ID,AD_Reference_ID,AD_Ref_List_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,Updated,UpdatedBy,Value,ValueName) VALUES (0,0,541726,543432,TO_TIMESTAMP('2023-04-03 17:57:40','YYYY-MM-DD HH24:MI:SS'),100,'End of the month of baseline date plus X days','D','Y','End of the month of baseline date plus X days',TO_TIMESTAMP('2023-04-03 17:57:40','YYYY-MM-DD HH24:MI:SS'),100,'EBLDX','End of the month of baseline date plus X days')
;

-- 2023-04-03T15:57:40.931Z
INSERT INTO AD_Ref_List_Trl (AD_Language,AD_Ref_List_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Ref_List_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Ref_List t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Ref_List_ID=543432 AND NOT EXISTS (SELECT 1 FROM AD_Ref_List_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Ref_List_ID=t.AD_Ref_List_ID)
;

-- Column: C_PaymentTerm.CalculationMethod
-- 2023-04-03T15:58:31.938Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586413,582206,0,17,541726,113,'CalculationMethod',TO_TIMESTAMP('2023-04-03 17:58:31','YYYY-MM-DD HH24:MI:SS'),100,'N','BLDX','D',0,4,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Calculation Method',0,0,TO_TIMESTAMP('2023-04-03 17:58:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T15:58:31.940Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586413 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T15:58:32.329Z
/* DDL */  select update_Column_Translation_From_AD_Element(582206) 
;

-- 2023-04-03T15:58:42.923Z
/* DDL */ SELECT public.db_alter_table('C_PaymentTerm','ALTER TABLE public.C_PaymentTerm ADD COLUMN CalculationMethod VARCHAR(4) DEFAULT ''BLDX''')
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> Base Line Types
-- Column: C_PaymentTerm.BaseLineTypes
-- 2023-04-03T16:06:32.433Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586411,713599,0,184,0,TO_TIMESTAMP('2023-04-03 18:06:32','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Base Line Types',0,280,0,1,1,TO_TIMESTAMP('2023-04-03 18:06:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T16:06:32.434Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713599 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T16:06:32.435Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582205) 
;

-- 2023-04-03T16:06:32.440Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713599
;

-- 2023-04-03T16:06:32.441Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713599)
;

-- Field: Payment Term(141,D) -> Payment Term(184,D) -> Calculation Method
-- Column: C_PaymentTerm.CalculationMethod
-- 2023-04-03T16:07:00.426Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586413,713600,0,184,0,TO_TIMESTAMP('2023-04-03 18:07:00','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Calculation Method',0,290,0,1,1,TO_TIMESTAMP('2023-04-03 18:07:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T16:07:00.427Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713600 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T16:07:00.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582206) 
;

-- 2023-04-03T16:07:00.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713600
;

-- 2023-04-03T16:07:00.431Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713600)
;

-- UI Column: Payment Term(141,D) -> Payment Term(184,D) -> main -> 20
-- UI Element Group: extension
-- 2023-04-03T16:08:06.728Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540318,550518,TO_TIMESTAMP('2023-04-03 18:08:06','YYYY-MM-DD HH24:MI:SS'),100,'Y','extension',15,TO_TIMESTAMP('2023-04-03 18:08:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Payment Term(141,D) -> Payment Term(184,D) -> main -> 20 -> extension.Base Line Types
-- Column: C_PaymentTerm.BaseLineTypes
-- 2023-04-03T16:08:45.585Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713599,0,184,550518,616514,'F',TO_TIMESTAMP('2023-04-03 18:08:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Base Line Types',10,0,0,TO_TIMESTAMP('2023-04-03 18:08:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Payment Term(141,D) -> Payment Term(184,D) -> main -> 20 -> extension.Calculation Method
-- Column: C_PaymentTerm.CalculationMethod
-- 2023-04-03T16:08:55.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713600,0,184,550518,616515,'F',TO_TIMESTAMP('2023-04-03 18:08:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Calculation Method',20,0,0,TO_TIMESTAMP('2023-04-03 18:08:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: C_PaymentTerm.ab182_isAfterArrival
-- 2023-04-04T08:58:14.239Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584181
;

-- 2023-04-04T08:58:14.241Z
DELETE FROM AD_Column WHERE AD_Column_ID=584181
;

-- Column: C_PaymentTerm.ab182_isAfterBL
-- 2023-04-04T08:58:19.875Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=584182
;

-- 2023-04-04T08:58:19.876Z
DELETE FROM AD_Column WHERE AD_Column_ID=584182
;


ALTER TABLE c_paymentterm
    DROP COLUMN ab182_isafterarrival
;

ALTER TABLE c_paymentterm
    DROP COLUMN ab182_isafterbl
;
