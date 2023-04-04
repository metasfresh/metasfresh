-- 2023-04-03T15:51:02.402Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582207,0,'IsPrintLocalCurrencyInfo',TO_TIMESTAMP('2023-04-03 17:51:02','YYYY-MM-DD HH24:MI:SS'),100,'Determines if local currency VAT informations should be printed on sales invoices. Can be configured in tab tax reporting in window document type.','D','Y','Print local currency VAT','Print local currency VAT',TO_TIMESTAMP('2023-04-03 17:51:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T15:51:02.408Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582207 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsPrintLocalCurrencyInfo
-- 2023-04-03T15:51:26.283Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-04-03 17:51:26','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582207 AND AD_Language='en_US'
;

-- 2023-04-03T15:51:26.289Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582207,'en_US') 
;

-- Element: IsPrintLocalCurrencyInfo
-- 2023-04-03T15:53:18.041Z
UPDATE AD_Element_Trl SET Description='Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.', IsTranslated='Y', Name='Druck lokale Mehrwehrtsteuer', PrintName='Druck lokale Mehrwehrtsteuer',Updated=TO_TIMESTAMP('2023-04-03 17:53:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582207 AND AD_Language='de_CH'
;

-- 2023-04-03T15:53:18.043Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582207,'de_CH') 
;

-- Element: IsPrintLocalCurrencyInfo
-- 2023-04-03T15:54:03.470Z
UPDATE AD_Element_Trl SET Description='Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.', IsTranslated='Y', Name='Druck lokale Mehrwertsteuer', PrintName='Druck lokale Mehrwertsteuer',Updated=TO_TIMESTAMP('2023-04-03 17:54:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582207 AND AD_Language='de_DE'
;

-- 2023-04-03T15:54:03.471Z
UPDATE AD_Element SET Description='Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.', Name='Druck lokale Mehrwertsteuer', PrintName='Druck lokale Mehrwertsteuer' WHERE AD_Element_ID=582207
;

-- 2023-04-03T15:54:07.923Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(582207,'de_DE') 
;

-- 2023-04-03T15:54:07.925Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582207,'de_DE') 
;

-- Column: C_Invoice.IsPrintLocalCurrencyInfo
-- 2023-04-03T15:56:47.170Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586412,582207,0,20,318,'IsPrintLocalCurrencyInfo',TO_TIMESTAMP('2023-04-03 17:56:47','YYYY-MM-DD HH24:MI:SS'),100,'N','','Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Druck lokale Mehrwertsteuer',0,0,TO_TIMESTAMP('2023-04-03 17:56:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T15:56:47.173Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586412 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T15:56:47.670Z
/* DDL */  select update_Column_Translation_From_AD_Element(582207) 
;

-- 2023-04-03T15:58:31.671Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN IsPrintLocalCurrencyInfo CHAR(1) CHECK (IsPrintLocalCurrencyInfo IN (''Y'',''N''))')
;

-- Column: C_Invoice.IsPrintLocalCurrencyInfo
-- 2023-04-03T16:04:28.760Z
UPDATE AD_Column SET AD_Reference_ID=17, AD_Reference_Value_ID=540528,Updated=TO_TIMESTAMP('2023-04-03 18:04:28','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=586412
;

-- 2023-04-03T16:04:41.144Z
INSERT INTO t_alter_column values('c_invoice','IsPrintLocalCurrencyInfo','CHAR(1)',null,null)
;

-- Column: C_BPartner.IsPrintLocalCurrencyInfo
-- 2023-04-03T16:03:20.961Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586414,582207,0,17,540528,291,'IsPrintLocalCurrencyInfo',TO_TIMESTAMP('2023-04-03 18:03:20','YYYY-MM-DD HH24:MI:SS'),100,'N','','Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Druck lokale Mehrwertsteuer',0,0,TO_TIMESTAMP('2023-04-03 18:03:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-04-03T16:03:20.963Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586414 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-04-03T16:03:21.491Z
/* DDL */  select update_Column_Translation_From_AD_Element(582207) 
;

-- 2023-04-03T16:03:40.587Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsPrintLocalCurrencyInfo CHAR(1)')
;

-- Field: Rechnung(167,D) -> Rechnung(263,D) -> Druck lokale Mehrwertsteuer
-- Column: C_Invoice.IsPrintLocalCurrencyInfo
-- 2023-04-03T16:35:53.589Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586412,713601,0,263,0,TO_TIMESTAMP('2023-04-03 18:35:53','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.',60,'@IsSOTrx/''N''@=''Y''','D',0,'Y','Y','Y','N','N','N','N','N','Druck lokale Mehrwertsteuer',0,520,0,1,1,TO_TIMESTAMP('2023-04-03 18:35:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T16:35:53.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713601 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T16:35:53.595Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582207) 
;

-- 2023-04-03T16:35:53.599Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713601
;

-- 2023-04-03T16:35:53.603Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713601)
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Druck lokale Mehrwertsteuer
-- Column: C_Invoice.IsPrintLocalCurrencyInfo
-- 2023-04-03T16:37:22.062Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,713601,0,263,541214,616516,'F',TO_TIMESTAMP('2023-04-03 18:37:21','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.','Y','Y','N','N','N','N','N',0,'Druck lokale Mehrwertsteuer',90,0,0,TO_TIMESTAMP('2023-04-03 18:37:21','YYYY-MM-DD HH24:MI:SS'),100,'')
;

-- UI Element: Rechnung(167,D) -> Rechnung(263,D) -> advanced edit -> 10 -> advanced edit.Druck lokale Mehrwertsteuer
-- Column: C_Invoice.IsPrintLocalCurrencyInfo
-- 2023-04-03T16:37:33.706Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-04-03 18:37:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616516
;

-- Field: Geschäftspartner(123,D) -> Kunde(223,D) -> Druck lokale Mehrwertsteuer
-- Column: C_BPartner.IsPrintLocalCurrencyInfo
-- 2023-04-03T17:10:50.774Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586414,713603,0,223,60,TO_TIMESTAMP('2023-04-03 19:10:50','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.',0,'D',0,'Y','Y','Y','N','N','N','N','N','Druck lokale Mehrwertsteuer',0,350,0,1,1,TO_TIMESTAMP('2023-04-03 19:10:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-04-03T17:10:50.776Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=713603 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-04-03T17:10:50.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582207)
;

-- 2023-04-03T17:10:50.785Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=713603
;

-- 2023-04-03T17:10:50.786Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(713603)
;

-- UI Element: Geschäftspartner(123,D) -> Kunde(223,D) -> main -> 10 -> payment conditions.Druck lokale Mehrwertsteuer
-- Column: C_BPartner.IsPrintLocalCurrencyInfo
-- 2023-04-03T17:13:00.331Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,713603,0,223,540672,616518,'F',TO_TIMESTAMP('2023-04-03 19:13:00','YYYY-MM-DD HH24:MI:SS'),100,'Legt fest, ob Mehrwertsteuerinformationen in lokaler Währung auf Verkaufsrechnungen gedruckt werden sollen. Kann in der Registerkarte Steuerbericht im Fenster Belegart konfiguriert werden.','Y','N','N','Y','N','N','N',0,'Druck lokale Mehrwertsteuer',330,0,0,TO_TIMESTAMP('2023-04-03 19:13:00','YYYY-MM-DD HH24:MI:SS'),100)
;

