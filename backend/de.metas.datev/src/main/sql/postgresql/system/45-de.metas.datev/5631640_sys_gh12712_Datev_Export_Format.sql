-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DebitOrCreditIndicator
-- 2022-03-23T13:44:55.955Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,582635,578557,0,14,540936,'DebitOrCreditIndicator',TO_TIMESTAMP('2022-03-23 14:44:54','YYYY-MM-DD HH24:MI:SS'),100,'D',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','DebitOrCreditIndicator',TO_TIMESTAMP('2022-03-23 14:44:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-23T13:44:56.030Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582635 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-23T13:44:56.182Z
/* DDL */  select update_Column_Translation_From_AD_Element(578557) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DebitOrCreditIndicator
-- 2022-03-23T13:50:42.209Z
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', FieldLength=1,Updated=TO_TIMESTAMP('2022-03-23 14:50:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582635
;

-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2022-03-23T13:51:32.696Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582636,578557,0,20,540935,'DebitOrCreditIndicator',TO_TIMESTAMP('2022-03-23 14:51:31','YYYY-MM-DD HH24:MI:SS'),100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'DebitOrCreditIndicator',0,0,TO_TIMESTAMP('2022-03-23 14:51:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-23T13:51:32.768Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582636 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-23T13:51:32.951Z
/* DDL */  select update_Column_Translation_From_AD_Element(578557) 
;

-- 2022-03-23T13:52:13.776Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN DebitOrCreditIndicator CHAR(1) DEFAULT ''N'' CHECK (DebitOrCreditIndicator IN (''Y'',''N''))')
;

-- Field: Buchungen Export -> Lines -> DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2022-03-23T13:53:21.839Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582636,691474,0,541037,0,TO_TIMESTAMP('2022-03-23 14:53:20','YYYY-MM-DD HH24:MI:SS'),100,0,'U',0,'Y','Y','Y','N','N','N','N','N','DebitOrCreditIndicator',0,110,0,1,1,TO_TIMESTAMP('2022-03-23 14:53:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-23T13:53:21.932Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691474 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-23T13:53:21.999Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578557) 
;

-- 2022-03-23T13:53:22.131Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691474
;

-- 2022-03-23T13:53:22.196Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691474)
;

-- UI Element: Buchungen Export -> Lines.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2022-03-23T13:54:35.552Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691474,0,541037,541479,605266,'F',TO_TIMESTAMP('2022-03-23 14:54:34','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'DebitOrCreditIndicator',130,0,0,TO_TIMESTAMP('2022-03-23 14:54:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-23T13:55:33.529Z
UPDATE AD_Element_Trl SET Name='Soll-/Haben-Kennzeichen', PrintName='Soll-/Haben-Kennzeichen',Updated=TO_TIMESTAMP('2022-03-23 14:55:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578557 AND AD_Language='de_DE'
;

-- 2022-03-23T13:55:33.595Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578557,'de_DE') 
;

-- 2022-03-23T13:55:33.746Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(578557,'de_DE') 
;

-- 2022-03-23T13:55:33.817Z
UPDATE AD_Column SET ColumnName='DebitOrCreditIndicator', Name='Soll-/Haben-Kennzeichen', Description=NULL, Help=NULL WHERE AD_Element_ID=578557
;

-- 2022-03-23T13:55:33.878Z
UPDATE AD_Process_Para SET ColumnName='DebitOrCreditIndicator', Name='Soll-/Haben-Kennzeichen', Description=NULL, Help=NULL, AD_Element_ID=578557 WHERE UPPER(ColumnName)='DEBITORCREDITINDICATOR' AND IsCentrallyMaintained='Y' AND AD_Element_ID IS NULL
;

-- 2022-03-23T13:55:33.949Z
UPDATE AD_Process_Para SET ColumnName='DebitOrCreditIndicator', Name='Soll-/Haben-Kennzeichen', Description=NULL, Help=NULL WHERE AD_Element_ID=578557 AND IsCentrallyMaintained='Y'
;

-- 2022-03-23T13:55:34.010Z
UPDATE AD_Field SET Name='Soll-/Haben-Kennzeichen', Description=NULL, Help=NULL WHERE (AD_Column_ID IN (SELECT AD_Column_ID FROM AD_Column WHERE AD_Element_ID=578557) AND AD_Name_ID IS NULL ) OR (AD_Name_ID = 578557)
;

-- 2022-03-23T13:55:34.145Z
UPDATE AD_PrintFormatItem pi SET PrintName='Soll-/Haben-Kennzeichen', Name='Soll-/Haben-Kennzeichen' WHERE IsCentrallyMaintained='Y' AND EXISTS (SELECT * FROM AD_Column c  WHERE c.AD_Column_ID=pi.AD_Column_ID AND c.AD_Element_ID=578557)
;

-- 2022-03-23T13:55:34.206Z
UPDATE AD_Tab SET Name='Soll-/Haben-Kennzeichen', Description=NULL, Help=NULL, CommitWarning = NULL WHERE AD_Element_ID = 578557
;

-- 2022-03-23T13:55:34.269Z
UPDATE AD_WINDOW SET Name='Soll-/Haben-Kennzeichen', Description=NULL, Help=NULL WHERE AD_Element_ID = 578557
;

-- 2022-03-23T13:55:34.328Z
UPDATE AD_Menu SET   Name = 'Soll-/Haben-Kennzeichen', Description = NULL, WEBUI_NameBrowse = NULL, WEBUI_NameNew = NULL, WEBUI_NameNewBreadcrumb = NULL WHERE AD_Element_ID = 578557
;

-- 2022-03-23T13:55:54.778Z
UPDATE AD_Element_Trl SET Name='Debit/Credit Indicator', PrintName='Debit/Credit Indicator',Updated=TO_TIMESTAMP('2022-03-23 14:55:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=578557 AND AD_Language='en_US'
;

-- 2022-03-23T13:55:54.847Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(578557,'en_US') 
;

-- UI Element: Buchungen Export -> Lines.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2022-03-23T13:56:51.338Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-03-23 14:56:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605266
;

-- UI Element: Buchungen Export -> Lines.DebitOrCreditIndicator
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2022-03-23T13:56:58.255Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-03-23 14:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605266
;

-- UI Element: Buchungen Export -> Lines.Debit Account
-- Column: DATEV_ExportLine.DR_Account
-- 2022-03-23T13:56:58.682Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-03-23 14:56:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551039
;

-- UI Element: Buchungen Export -> Lines.Credit Account
-- Column: DATEV_ExportLine.CR_Account
-- 2022-03-23T13:56:59.063Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-03-23 14:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551040
;

-- UI Element: Buchungen Export -> Lines.Betrag
-- Column: DATEV_ExportLine.Amt
-- 2022-03-23T13:56:59.604Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-03-23 14:56:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551041
;

-- UI Element: Buchungen Export -> Lines.Geschäftspartner
-- Column: DATEV_ExportLine.C_BPartner_ID
-- 2022-03-23T13:57:00.090Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-03-23 14:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551042
;

-- UI Element: Buchungen Export -> Lines.Rechnung
-- Column: DATEV_ExportLine.C_Invoice_ID
-- 2022-03-23T13:57:00.482Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-03-23 14:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551043
;

-- UI Element: Buchungen Export -> Lines.Activity Name
-- Column: DATEV_ExportLine.ActivityName
-- 2022-03-23T13:57:00.855Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-03-23 14:57:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551044
;

-- UI Element: Buchungen Export -> Lines.Buchungsdatum
-- Column: DATEV_ExportLine.DateAcct
-- 2022-03-23T13:57:01.227Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-03-23 14:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551045
;

-- UI Element: Buchungen Export -> Lines.Datum Fälligkeit
-- Column: DATEV_ExportLine.DueDate
-- 2022-03-23T13:57:01.617Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-03-23 14:57:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551046
;

-- UI Element: Buchungen Export -> Lines.Beschreibung
-- Column: DATEV_ExportLine.Description
-- 2022-03-23T13:57:02.068Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-03-23 14:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551047
;

-- UI Element: Buchungen Export -> Lines.Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2022-03-23T13:57:02.448Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-03-23 14:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551048
;

-- UI Element: Buchungen Export -> Lines.Sektion
-- Column: DATEV_ExportLine.AD_Org_ID
-- 2022-03-23T13:57:02.841Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-03-23 14:57:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551051
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Currency
-- 2022-03-23T14:16:16.301Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,582637,575620,0,17,540936,'Currency',TO_TIMESTAMP('2022-03-23 15:16:15','YYYY-MM-DD HH24:MI:SS'),100,'Währungen verwalten','D',3,'Y','Y','N','N','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2022-03-23 15:16:15','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-23T14:16:16.374Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-23T14:16:16.513Z
/* DDL */  select update_Column_Translation_From_AD_Element(575620) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Currency
-- 2022-03-23T14:17:31.548Z
UPDATE AD_Column SET AD_Reference_ID=10,Updated=TO_TIMESTAMP('2022-03-23 15:17:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582637
;

-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:19:18.576Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,582638,575620,0,10,540935,'Currency',TO_TIMESTAMP('2022-03-23 15:19:17','YYYY-MM-DD HH24:MI:SS'),100,'N','Währungen verwalten','de.metas.datev',0,3,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Währung',0,0,TO_TIMESTAMP('2022-03-23 15:19:17','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-03-23T14:19:18.667Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=582638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-03-23T14:19:18.977Z
/* DDL */  select update_Column_Translation_From_AD_Element(575620) 
;

-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:19:40.638Z
UPDATE AD_Column SET EntityType='D',Updated=TO_TIMESTAMP('2022-03-23 15:19:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=582638
;

-- 2022-03-23T14:20:09.784Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN Currency VARCHAR(3)')
;

-- Field: Buchungen Export -> Lines -> Soll-/Haben-Kennzeichen
-- Column: DATEV_ExportLine.DebitOrCreditIndicator
-- 2022-03-23T14:21:02.322Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-03-23 15:21:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691474
;

-- Field: Buchungen Export -> Lines -> Währung
-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:21:24.272Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,582638,691475,0,541037,0,TO_TIMESTAMP('2022-03-23 15:21:23','YYYY-MM-DD HH24:MI:SS'),100,'Währungen verwalten',0,'U',0,'Y','Y','Y','N','N','N','N','N','Währung',0,120,0,1,1,TO_TIMESTAMP('2022-03-23 15:21:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-03-23T14:21:24.338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=691475 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-03-23T14:21:24.406Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575620) 
;

-- 2022-03-23T14:21:24.477Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=691475
;

-- 2022-03-23T14:21:24.541Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(691475)
;

-- Field: Buchungen Export -> Lines -> Währung
-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:21:35.839Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2022-03-23 15:21:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=691475
;

-- UI Element: Buchungen Export -> Lines.Währung
-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:22:13.637Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,691475,0,541037,541479,605267,'F',TO_TIMESTAMP('2022-03-23 15:22:12','YYYY-MM-DD HH24:MI:SS'),100,'Währungen verwalten','Y','N','N','Y','N','N','N',0,'Währung',140,0,0,TO_TIMESTAMP('2022-03-23 15:22:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Buchungen Export -> Lines.Währung
-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:22:33.061Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-03-23 15:22:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605267
;

-- UI Element: Buchungen Export -> Lines.Währung
-- Column: DATEV_ExportLine.Currency
-- 2022-03-23T14:22:39.281Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-03-23 15:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=605267
;

-- UI Element: Buchungen Export -> Lines.Geschäftspartner
-- Column: DATEV_ExportLine.C_BPartner_ID
-- 2022-03-23T14:22:39.682Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-03-23 15:22:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551042
;

-- UI Element: Buchungen Export -> Lines.Rechnung
-- Column: DATEV_ExportLine.C_Invoice_ID
-- 2022-03-23T14:22:40.087Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-03-23 15:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551043
;

-- UI Element: Buchungen Export -> Lines.Activity Name
-- Column: DATEV_ExportLine.ActivityName
-- 2022-03-23T14:22:40.499Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-03-23 15:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551044
;

-- UI Element: Buchungen Export -> Lines.Buchungsdatum
-- Column: DATEV_ExportLine.DateAcct
-- 2022-03-23T14:22:40.878Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-03-23 15:22:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551045
;

-- UI Element: Buchungen Export -> Lines.Datum Fälligkeit
-- Column: DATEV_ExportLine.DueDate
-- 2022-03-23T14:22:41.288Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-03-23 15:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551046
;

-- UI Element: Buchungen Export -> Lines.Beschreibung
-- Column: DATEV_ExportLine.Description
-- 2022-03-23T14:22:41.680Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-03-23 15:22:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551047
;

-- UI Element: Buchungen Export -> Lines.Accounting Fact
-- Column: DATEV_ExportLine.Fact_Acct_ID
-- 2022-03-23T14:22:42.122Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-03-23 15:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551048
;

-- UI Element: Buchungen Export -> Lines.Sektion
-- Column: DATEV_ExportLine.AD_Org_ID
-- 2022-03-23T14:22:42.496Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-03-23 15:22:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=551051
;

-- 2022-03-23T14:27:43.026Z
INSERT INTO t_alter_column values('datev_exportline','DebitOrCreditIndicator','CHAR(1)',null,'N')
;

-- 2022-03-23T14:28:04.868Z
INSERT INTO t_alter_column values('datev_exportline','Currency','VARCHAR(3)',null,null)
;

ALTER TABLE datev_exportline
    DROP CONSTRAINT IF EXISTS "datev_exportline_debitorcreditindicator_check"
;

