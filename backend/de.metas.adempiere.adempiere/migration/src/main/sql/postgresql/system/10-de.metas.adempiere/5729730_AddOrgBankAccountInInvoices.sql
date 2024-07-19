-- Run mode: SWING_CLIENT

-- 2024-07-19T08:27:11.532Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583191,0,'Org_BP_Account',TO_TIMESTAMP('2024-07-19 11:27:11.325','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Organization Bank account','Organization Bank account',TO_TIMESTAMP('2024-07-19 11:27:11.325','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-19T08:27:11.546Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583191 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Org_BP_Account
-- 2024-07-19T08:27:41.810Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sektion Bankkonto', PrintName='Sektion Bankkonto',Updated=TO_TIMESTAMP('2024-07-19 11:27:41.81','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583191 AND AD_Language='de_CH'
;

-- 2024-07-19T08:27:41.840Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583191,'de_CH')
;

-- Element: Org_BP_Account
-- 2024-07-19T08:28:56.534Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Sektion Bankkonto', PrintName='Sektion Bankkonto',Updated=TO_TIMESTAMP('2024-07-19 11:28:56.533','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583191 AND AD_Language='de_DE'
;

-- 2024-07-19T08:28:56.539Z
UPDATE AD_Element SET Name='Sektion Bankkonto', PrintName='Sektion Bankkonto' WHERE AD_Element_ID=583191
;

-- 2024-07-19T08:28:56.815Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583191,'de_DE')
;

-- 2024-07-19T08:28:56.817Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583191,'de_DE')
;

-- 2024-07-19T08:29:03.932Z
UPDATE AD_Element SET ColumnName='Org_BP_Account_ID',Updated=TO_TIMESTAMP('2024-07-19 11:29:03.932','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583191
;

-- 2024-07-19T08:29:03.934Z
UPDATE AD_Column SET ColumnName='Org_BP_Account_ID' WHERE AD_Element_ID=583191
;

-- 2024-07-19T08:29:03.934Z
UPDATE AD_Process_Para SET ColumnName='Org_BP_Account_ID' WHERE AD_Element_ID=583191
;

-- 2024-07-19T08:29:03.937Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583191,'de_DE')
;

-- Column: C_Invoice_Candidate.Org_BP_Account_ID
-- 2024-07-19T08:29:55.812Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588862,583191,0,30,53283,540270,'Org_BP_Account_ID',TO_TIMESTAMP('2024-07-19 11:29:55.653','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sektion Bankkonto',0,0,TO_TIMESTAMP('2024-07-19 11:29:55.653','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-19T08:29:55.815Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588862 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-19T08:29:55.818Z
/* DDL */  select update_Column_Translation_From_AD_Element(583191)
;

-- 2024-07-19T08:30:15.609Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN Org_BP_Account_ID NUMERIC(10)')
;

-- 2024-07-19T08:30:16.095Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT OrgBPAccount_CInvoiceCandidate FOREIGN KEY (Org_BP_Account_ID) REFERENCES public.C_BankAccount DEFERRABLE INITIALLY DEFERRED
;

-- Name: C_BankAccount of AD_Org
-- 2024-07-19T08:37:07.046Z
INSERT INTO AD_Val_Rule (AD_Client_ID,AD_Org_ID,AD_Val_Rule_ID,Code,Created,CreatedBy,EntityType,IsActive,Name,Type,Updated,UpdatedBy) VALUES (0,0,540685,'C_BankAccount.AD_Org_ID IN (@AD_Org_ID@, 0)',TO_TIMESTAMP('2024-07-19 11:37:06.927','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','C_BankAccount of AD_Org','S',TO_TIMESTAMP('2024-07-19 11:37:06.927','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_Invoice_Candidate.Org_BP_Account_ID
-- 2024-07-19T08:37:15.846Z
UPDATE AD_Column SET AD_Val_Rule_ID=540685,Updated=TO_TIMESTAMP('2024-07-19 11:37:15.846','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588862
;

-- Column: C_Invoice.Org_BP_Account_ID
-- 2024-07-19T08:47:46.114Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588863,583191,0,30,53283,318,'Org_BP_Account_ID',TO_TIMESTAMP('2024-07-19 11:47:45.913','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Sektion Bankkonto',0,0,TO_TIMESTAMP('2024-07-19 11:47:45.913','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-07-19T08:47:46.118Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-07-19T08:47:46.125Z
/* DDL */  select update_Column_Translation_From_AD_Element(583191)
;

-- Column: C_Invoice.Org_BP_Account_ID
-- 2024-07-19T08:50:29.512Z
UPDATE AD_Column SET AD_Val_Rule_ID=540685,Updated=TO_TIMESTAMP('2024-07-19 11:50:29.512','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588863
;

-- 2024-07-19T08:50:37.221Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN Org_BP_Account_ID NUMERIC(10)')
;

-- 2024-07-19T08:50:38.176Z
ALTER TABLE C_Invoice ADD CONSTRAINT OrgBPAccount_CInvoice FOREIGN KEY (Org_BP_Account_ID) REFERENCES public.C_BankAccount DEFERRABLE INITIALLY DEFERRED
;





------------ windows  -----


-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Sektion Bankkonto
-- Column: C_Invoice_Candidate.Org_BP_Account_ID
-- 2024-07-19T12:06:26.110Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588862,729135,0,540279,0,TO_TIMESTAMP('2024-07-19 15:06:25.959','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Sektion Bankkonto',0,590,0,1,1,TO_TIMESTAMP('2024-07-19 15:06:25.959','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-19T12:06:26.112Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-19T12:06:26.114Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583191)
;

-- 2024-07-19T12:06:26.117Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729135
;

-- 2024-07-19T12:06:26.118Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729135)
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Sektion Bankkonto
-- Column: C_Invoice_Candidate.Org_BP_Account_ID
-- 2024-07-19T12:06:50.378Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729135,0,540279,540056,625024,'F',TO_TIMESTAMP('2024-07-19 15:06:50.229','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Sektion Bankkonto',1070,0,0,TO_TIMESTAMP('2024-07-19 15:06:50.229','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Field: Rechnung_OLD(167,D) -> Rechnung(263,D) -> Sektion Bankkonto
-- Column: C_Invoice.Org_BP_Account_ID
-- 2024-07-19T12:07:27.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588863,729136,0,263,0,TO_TIMESTAMP('2024-07-19 15:07:27.381','YYYY-MM-DD HH24:MI:SS.US'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Sektion Bankkonto',0,550,0,1,1,TO_TIMESTAMP('2024-07-19 15:07:27.381','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-07-19T12:07:27.512Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729136 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-07-19T12:07:27.514Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583191)
;

-- 2024-07-19T12:07:27.516Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729136
;

-- 2024-07-19T12:07:27.517Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729136)
;

-- UI Element: Rechnung_OLD(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Sektion Bankkonto
-- Column: C_Invoice.Org_BP_Account_ID
-- 2024-07-19T12:07:46.042Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729136,0,263,541214,625025,'F',TO_TIMESTAMP('2024-07-19 15:07:45.931','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','Y','N','Y','N','N','N',0,'Sektion Bankkonto',120,0,0,TO_TIMESTAMP('2024-07-19 15:07:45.931','YYYY-MM-DD HH24:MI:SS.US'),100)
;

