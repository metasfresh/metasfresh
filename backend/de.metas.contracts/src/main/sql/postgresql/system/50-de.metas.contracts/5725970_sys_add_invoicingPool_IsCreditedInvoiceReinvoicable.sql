-- Run mode: SWING_CLIENT

-- Column: C_DocType_Invoicing_Pool.IsCreditedInvoiceReinvoicable
-- 2024-06-10T15:43:25.256Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588336,542843,0,20,542277,'IsCreditedInvoiceReinvoicable',TO_TIMESTAMP('2024-06-10 18:43:24.976','YYYY-MM-DD HH24:MI:SS.US'),100,'N','N','Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehört, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.','de.metas.invoicecandidate',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Gutgeschriebener Betrag erneut abrechenbar',0,0,TO_TIMESTAMP('2024-06-10 18:43:24.976','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-06-10T15:43:25.264Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588336 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-06-10T15:43:25.299Z
/* DDL */  select update_Column_Translation_From_AD_Element(542843)
;

-- 2024-06-10T15:43:28.675Z
/* DDL */ SELECT public.db_alter_table('C_DocType_Invoicing_Pool','ALTER TABLE public.C_DocType_Invoicing_Pool ADD COLUMN IsCreditedInvoiceReinvoicable CHAR(1) DEFAULT ''N'' CHECK (IsCreditedInvoiceReinvoicable IN (''Y'',''N'')) NOT NULL')
;

-- Column: C_DocType_Invoicing_Pool.IsCreditedInvoiceReinvoicable
-- 2024-06-10T15:45:40.761Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2024-06-10 18:45:40.761','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588336
;

-- Field: Rechnungspool(541658,D) -> Rechnungspool(546734,D) -> Gutgeschriebener Betrag erneut abrechenbar
-- Column: C_DocType_Invoicing_Pool.IsCreditedInvoiceReinvoicable
-- 2024-06-10T15:47:29.072Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,588336,728997,0,546734,0,TO_TIMESTAMP('2024-06-10 18:47:28.888','YYYY-MM-DD HH24:MI:SS.US'),100,'Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehört, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.',0,'D',0,'Y','Y','Y','N','N','N','N','N','N','Gutgeschriebener Betrag erneut abrechenbar',0,10,0,1,1,TO_TIMESTAMP('2024-06-10 18:47:28.888','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-06-10T15:47:29.074Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=728997 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-06-10T15:47:29.076Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542843)
;

-- 2024-06-10T15:47:29.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=728997
;

-- 2024-06-10T15:47:29.093Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(728997)
;

-- Column: C_DocType_Invoicing_Pool.IsCreditedInvoiceReinvoicable
-- 2024-06-10T15:48:03.050Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2024-06-10 18:48:03.05','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588336
;

-- UI Element: Rechnungspool(541658,D) -> Rechnungspool(546734,D) -> main -> 20 -> main.Gutgeschriebener Betrag erneut abrechenbar
-- Column: C_DocType_Invoicing_Pool.IsCreditedInvoiceReinvoicable
-- 2024-06-10T15:48:59.382Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,728997,0,546734,550212,624941,'F',TO_TIMESTAMP('2024-06-10 18:48:59.191','YYYY-MM-DD HH24:MI:SS.US'),100,'Wenn dieser Wert "Ja" ist und diese Gutschrift zu einer Rechnung gehört, dann sind die betreffenden Rechnungskandidaten erneut abrechenbar, sofern "komplett abgerechnet abw." nicht auf "Ja" gesetzt wurde.','Y','N','N','Y','N','N','N',0,'Gutgeschriebener Betrag erneut abrechenbar',40,0,0,TO_TIMESTAMP('2024-06-10 18:48:59.191','YYYY-MM-DD HH24:MI:SS.US'),100)
;

