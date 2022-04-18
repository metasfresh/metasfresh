-- Column: C_Payment.IBAN
-- 2022-04-14T08:24:41.243Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582792,2664,0,10,335,'IBAN',TO_TIMESTAMP('2022-04-14 09:24:39','YYYY-MM-DD HH24:MI:SS'),100,'N','International Bank Account Number','D',0,40,'If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'IBAN',0,0,TO_TIMESTAMP('2022-04-14 09:24:39','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-04-14T08:24:41.325Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582792 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-04-14T08:24:41.500Z
/* DDL */  select update_Column_Translation_From_AD_Element(2664) 
;

-- 2022-04-14T08:24:57.904Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN IBAN VARCHAR(40)')
;

-- Field: Zahlung -> Zahlung -> IBAN
-- Column: C_Payment.IBAN
-- 2022-04-14T08:26:56.014Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582792,691678,0,330,0,TO_TIMESTAMP('2022-04-14 09:26:54','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number',0,'U','If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.',0,'Y','Y','Y','N','N','N','N','N','IBAN',0,680,0,1,1,TO_TIMESTAMP('2022-04-14 09:26:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-04-14T08:26:56.085Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-04-14T08:26:56.159Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2664) 
;

-- 2022-04-14T08:26:56.247Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691678
;

-- 2022-04-14T08:26:56.311Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691678)
;

-- Field: Zahlung -> Zahlung -> IBAN
-- Column: C_Payment.IBAN
-- 2022-04-14T08:27:07.883Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-04-14 09:27:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691678
;

-- UI Element: Zahlung -> Zahlung.IBAN
-- Column: C_Payment.IBAN
-- 2022-04-14T08:28:28.630Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691678,0,330,540060,605328,'F',TO_TIMESTAMP('2022-04-14 09:28:27','YYYY-MM-DD HH24:MI:SS'),100,'International Bank Account Number','If your bank provides an International Bank Account Number, enter it here
Details ISO 13616 and http://www.ecbs.org. The account number has the maximum length of 22 characters (without spaces). The IBAN is often printed with a apace after 4 characters. Do not enter the spaces in Adempiere.','Y','N','N','Y','N','N','N',0,'IBAN',275,0,0,TO_TIMESTAMP('2022-04-14 09:28:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Zahlung -> Zahlung.IBAN
-- Column: C_Payment.IBAN
-- 2022-04-14T08:29:01.293Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2022-04-14 09:29:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605328
;

