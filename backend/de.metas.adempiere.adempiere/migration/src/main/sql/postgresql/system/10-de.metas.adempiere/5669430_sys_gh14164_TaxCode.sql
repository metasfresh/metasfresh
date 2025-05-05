-- Column: C_Tax.Value
-- 2022-12-20T11:09:32.408Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585418,620,0,10,261,'Value',TO_TIMESTAMP('2022-12-20 13:09:31','YYYY-MM-DD HH24:MI:SS'),100,'N','Search key for the record in the format required - must be unique','D',0,25,'A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','Y',0,'Search Key',0,0,TO_TIMESTAMP('2022-12-20 13:09:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-12-20T11:09:32.499Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585418 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-20T11:09:32.623Z
/* DDL */  select update_Column_Translation_From_AD_Element(620) 
;

-- 2022-12-20T11:09:39.739Z
/* DDL */ SELECT public.db_alter_table('C_Tax','ALTER TABLE public.C_Tax ADD COLUMN Value VARCHAR(25)')
;

-- Field: Tax Rate(137,D) -> Tax(174,D) -> Search Key
-- Column: C_Tax.Value
-- 2022-12-20T11:10:37.469Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585418,710049,0,174,TO_TIMESTAMP('2022-12-20 13:10:36','YYYY-MM-DD HH24:MI:SS'),100,'Search key for the record in the format required - must be unique',25,'D','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','Search Key',TO_TIMESTAMP('2022-12-20 13:10:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T11:10:37.508Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710049 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-20T11:10:37.547Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620) 
;

-- 2022-12-20T11:10:37.784Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710049
;

-- 2022-12-20T11:10:37.824Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710049)
;

-- 2022-12-20T11:13:42.285Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581888,0,TO_TIMESTAMP('2022-12-20 13:13:41','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Tax Code','Tax Code',TO_TIMESTAMP('2022-12-20 13:13:41','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-12-20T11:13:42.325Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581888 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Field: Tax Rate(137,D) -> Tax(174,D) -> Tax Code
-- Column: C_Tax.Value
-- 2022-12-20T11:14:44.146Z
UPDATE AD_Field SET AD_Name_ID=581888, Description=NULL, Help=NULL, Name='Tax Code',Updated=TO_TIMESTAMP('2022-12-20 13:14:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710049
;

-- 2022-12-20T11:14:44.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581888) 
;

-- 2022-12-20T11:14:44.226Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710049
;

-- 2022-12-20T11:14:44.264Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710049)
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Tax Code
-- Column: C_Tax.Value
-- 2022-12-20T11:15:49.714Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,710049,0,174,540519,614567,'F',TO_TIMESTAMP('2022-12-20 13:15:49','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Tax Code',5,0,0,TO_TIMESTAMP('2022-12-20 13:15:49','YYYY-MM-DD HH24:MI:SS'),100,'M')
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Tax Code
-- Column: C_Tax.Value
-- 2022-12-20T11:16:13.376Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2022-12-20 13:16:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614567
;

-- Column: C_Tax.Value
-- 2022-12-20T11:17:13.421Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-20 13:17:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585418
;

-- Column: C_Tax.Value
-- 2022-12-20T11:17:24.574Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2022-12-20 13:17:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585418
;

-- Column: C_Tax.Name
-- 2022-12-20T11:18:14.900Z
UPDATE AD_Column SET SeqNo=2,Updated=TO_TIMESTAMP('2022-12-20 13:18:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=2246
;

-- Column: C_Tax.Value
-- 2022-12-20T11:18:38.592Z
UPDATE AD_Column SET IsIdentifier='Y', SeqNo=1,Updated=TO_TIMESTAMP('2022-12-20 13:18:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585418
;




-- Column: C_Tax.Value
-- 2022-12-20T11:24:30.335Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2022-12-20 13:24:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585418
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Tax Code
-- Column: C_Tax.Value
-- 2022-12-20T11:25:14.517Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2022-12-20 13:25:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614567
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Name
-- Column: C_Tax.Name
-- 2022-12-20T11:25:14.743Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2022-12-20 13:25:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544916
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Gültig Ab
-- Column: C_Tax.ValidFrom
-- 2022-12-20T11:25:15.032Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2022-12-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544917
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Steuer Kategorie
-- Column: C_Tax.C_TaxCategory_ID
-- 2022-12-20T11:25:15.259Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2022-12-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544918
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> property.Satz
-- Column: C_Tax.Rate
-- 2022-12-20T11:25:15.493Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2022-12-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584674
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> property.SeqNo
-- Column: C_Tax.SeqNo
-- 2022-12-20T11:25:15.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2022-12-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=586801
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Ursprungsland
-- Column: C_Tax.C_Country_ID
-- 2022-12-20T11:25:15.996Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2022-12-20 13:25:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584680
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Bestimmungsland
-- Column: C_Tax.To_Country_ID
-- 2022-12-20T11:25:16.224Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2022-12-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584681
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Typ Bestimmungsland
-- Column: C_Tax.TypeOfDestCountry
-- 2022-12-20T11:25:16.449Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2022-12-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585278
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.VK/ EK Typ
-- Column: C_Tax.SOPOType
-- 2022-12-20T11:25:16.675Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2022-12-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584679
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Partner hat eine Ust.-ID
-- Column: C_Tax.RequiresTaxCertificate
-- 2022-12-20T11:25:16.899Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2022-12-20 13:25:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584676
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> property.Steuer Ausschließlich
-- Column: C_Tax.IsWholeTax
-- 2022-12-20T11:25:17.173Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2022-12-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584460
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Steuerbefreit
-- Column: C_Tax.IsTaxExempt
-- 2022-12-20T11:25:17.396Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2022-12-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=584675
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Fiskalvertretung
-- Column: C_Tax.IsFiscalRepresentation
-- 2022-12-20T11:25:17.626Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2022-12-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585274
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 10 -> default.Kleinunernehmen
-- Column: C_Tax.IsSmallbusiness
-- 2022-12-20T11:25:17.849Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2022-12-20 13:25:17','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=585275
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> flags.Aktiv
-- Column: C_Tax.IsActive
-- 2022-12-20T11:25:18.113Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2022-12-20 13:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544924
;

-- UI Element: Tax Rate(137,D) -> Tax(174,D) -> main -> 20 -> org.Sektion
-- Column: C_Tax.AD_Org_ID
-- 2022-12-20T11:25:18.336Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2022-12-20 13:25:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=544930
;

-- Column: C_Tax.Value
-- 2022-12-20T11:58:07.963Z
UPDATE AD_Column SET IsUseDocSequence='N',Updated=TO_TIMESTAMP('2022-12-20 13:58:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585418
;

-- 2022-12-20T12:43:25.782Z
UPDATE AD_Element SET ColumnName='TaxCode',Updated=TO_TIMESTAMP('2022-12-20 14:43:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=581888
;

-- 2022-12-20T12:43:25.820Z
UPDATE AD_Column SET ColumnName='TaxCode' WHERE AD_Element_ID=581888
;

-- 2022-12-20T12:43:25.859Z
UPDATE AD_Process_Para SET ColumnName='TaxCode' WHERE AD_Element_ID=581888
;

-- 2022-12-20T12:43:26.008Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(581888,'en_US') 
;

-- Column: C_Tax.TaxCode
-- 2022-12-20T12:43:57.447Z
UPDATE AD_Column SET AD_Element_ID=581888, ColumnName='TaxCode', Description=NULL, Help=NULL, IsCalculated='N', Name='Tax Code',Updated=TO_TIMESTAMP('2022-12-20 14:43:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585418
;

-- 2022-12-20T12:43:57.485Z
UPDATE AD_Field SET Name='Tax Code', Description=NULL, Help=NULL WHERE AD_Column_ID=585418
;

-- 2022-12-20T12:43:57.523Z
/* DDL */  select update_Column_Translation_From_AD_Element(581888) 
;

-- Field: Tax Rate(137,D) -> Tax(174,D) -> Tax Code
-- Column: C_Tax.TaxCode
-- 2022-12-20T12:45:35.176Z
UPDATE AD_Field SET AD_Name_ID=NULL, Description=NULL, Help=NULL, Name='Tax Code',Updated=TO_TIMESTAMP('2022-12-20 14:45:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710049
;

-- 2022-12-20T12:45:35.214Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581888) 
;

-- 2022-12-20T12:45:35.258Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710049
;

-- 2022-12-20T12:45:35.296Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710049)
;


ALTER TABLE C_Tax RENAME COLUMN Value TO TaxCode;


