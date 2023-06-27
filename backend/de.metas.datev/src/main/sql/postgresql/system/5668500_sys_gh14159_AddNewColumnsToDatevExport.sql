-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2022-12-15T08:21:39.151Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,585324,1133,0,12,540936,'TaxAmt',TO_TIMESTAMP('2022-12-15 10:21:38.93','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Steuerbetrag für diesen Beleg','de.metas.datev',10,'Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Steuerbetrag',0,TO_TIMESTAMP('2022-12-15 10:21:38.93','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-12-15T08:21:39.155Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585324 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T08:21:39.194Z
/* DDL */  select update_Column_Translation_From_AD_Element(1133) 
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2022-12-15T08:22:13.431Z
UPDATE AD_Column SET FieldLength=131089,Updated=TO_TIMESTAMP('2022-12-15 10:22:13.43','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585324
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- 2022-12-15T08:22:46.777Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,Updated,UpdatedBy,Version) VALUES (0,585325,316,0,12,540936,'GrandTotal',TO_TIMESTAMP('2022-12-15 10:22:46.665','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Summe über Alles zu diesem Beleg','de.metas.datev',131089,'Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N',0,'Summe Gesamt',0,TO_TIMESTAMP('2022-12-15 10:22:46.665','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-12-15T08:22:46.778Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585325 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T08:22:46.781Z
/* DDL */  select update_Column_Translation_From_AD_Element(316) 
;

-- Table: RV_DATEV_Export_Fact_Acct_Invoice
-- Table: RV_DATEV_Export_Fact_Acct_Invoice
-- 2022-12-15T08:29:41.772Z
UPDATE AD_Table SET AD_Window_ID=540413,Updated=TO_TIMESTAMP('2022-12-15 10:29:41.77','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=540936
;

-- Table: RV_DATEV_Export_Fact_Acct_Invoice
-- Table: RV_DATEV_Export_Fact_Acct_Invoice
-- 2022-12-15T08:30:03.898Z
UPDATE AD_Table SET AD_Window_ID=NULL,Updated=TO_TIMESTAMP('2022-12-15 10:30:03.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_ID=540936
;

-- Column: DATEV_ExportLine.TaxAmt
-- Column: DATEV_ExportLine.TaxAmt
-- 2022-12-15T08:31:37.890Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585326,1133,0,12,540935,'TaxAmt',TO_TIMESTAMP('2022-12-15 10:31:37.737','YYYY-MM-DD HH24:MI:SS.US'),100,'N','0','Steuerbetrag für diesen Beleg','de.metas.datev',0,10,'Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Steuerbetrag',0,0,TO_TIMESTAMP('2022-12-15 10:31:37.737','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-12-15T08:31:37.891Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T08:31:37.894Z
/* DDL */  select update_Column_Translation_From_AD_Element(1133) 
;

-- 2022-12-15T08:31:43.954Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN TaxAmt NUMERIC DEFAULT 0 NOT NULL')
;

-- Column: DATEV_ExportLine.GrandTotal
-- Column: DATEV_ExportLine.GrandTotal
-- 2022-12-15T08:32:00.161Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585327,316,0,12,540935,'GrandTotal',TO_TIMESTAMP('2022-12-15 10:32:00.032','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Summe über Alles zu diesem Beleg','de.metas.datev',0,22,'Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Summe Gesamt',0,0,TO_TIMESTAMP('2022-12-15 10:32:00.032','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2022-12-15T08:32:00.163Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=585327 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-12-15T08:32:00.165Z
/* DDL */  select update_Column_Translation_From_AD_Element(316) 
;

-- 2022-12-15T08:32:11.808Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN GrandTotal NUMERIC')
;

-- Column: DATEV_ExportLine.TaxAmt
-- Column: DATEV_ExportLine.TaxAmt
-- 2022-12-15T08:32:19.165Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2022-12-15 10:32:19.165','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585326
;

-- 2022-12-15T08:32:22.196Z
INSERT INTO t_alter_column values('datev_exportline','TaxAmt','NUMERIC',null,'0')
;

-- 2022-12-15T08:32:22.200Z
INSERT INTO t_alter_column values('datev_exportline','TaxAmt',null,'NULL',null)
;

-- Field: Buchungen Export -> Lines -> Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- Field: Buchungen Export(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- 2022-12-15T08:33:31.179Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585326,709966,0,541037,TO_TIMESTAMP('2022-12-15 10:33:30.998','YYYY-MM-DD HH24:MI:SS.US'),100,'Steuerbetrag für diesen Beleg',10,'de.metas.datev','Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','N','N','N','N','N','N','Steuerbetrag',TO_TIMESTAMP('2022-12-15 10:33:30.998','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-15T08:33:31.181Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=709966 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T08:33:31.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1133) 
;

-- 2022-12-15T08:33:31.203Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709966
;

-- 2022-12-15T08:33:31.207Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709966)
;

-- Field: Buchungen Export -> Lines -> Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- Field: Buchungen Export(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- 2022-12-15T08:33:31.313Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585327,709967,0,541037,TO_TIMESTAMP('2022-12-15 10:33:31.213','YYYY-MM-DD HH24:MI:SS.US'),100,'Summe über Alles zu diesem Beleg',22,'de.metas.datev','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','N','N','N','N','N','Summe Gesamt',TO_TIMESTAMP('2022-12-15 10:33:31.213','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2022-12-15T08:33:31.314Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=709967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-12-15T08:33:31.316Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(316) 
;

-- 2022-12-15T08:33:31.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=709967
;

-- 2022-12-15T08:33:31.343Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(709967)
;

-- UI Element: Buchungen Export -> Lines.Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- UI Element: Buchungen Export(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.Steuerbetrag
-- Column: DATEV_ExportLine.TaxAmt
-- 2022-12-15T08:34:03.022Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709966,0,541037,541479,614517,'F',TO_TIMESTAMP('2022-12-15 10:34:02.871','YYYY-MM-DD HH24:MI:SS.US'),100,'Steuerbetrag für diesen Beleg','Der Steuerbetrag zeigt den Gesamtbetrag der Steuern für einen Beleg an.','Y','N','N','Y','Y','N','N','Steuerbetrag',31,40,0,TO_TIMESTAMP('2022-12-15 10:34:02.871','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchungen Export -> Lines.Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- UI Element: Buchungen Export(540413,de.metas.datev) -> Lines(541037,de.metas.datev) -> main -> 10 -> default.Summe Gesamt
-- Column: DATEV_ExportLine.GrandTotal
-- 2022-12-15T08:34:17.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,709967,0,541037,541479,614518,'F',TO_TIMESTAMP('2022-12-15 10:34:17.309','YYYY-MM-DD HH24:MI:SS.US'),100,'Summe über Alles zu diesem Beleg','Die Summe Gesamt zeigt die Summe über Alles inklusive Steuern und Fracht in Belegwährung an.','Y','N','N','Y','Y','N','N','Summe Gesamt',32,40,0,TO_TIMESTAMP('2022-12-15 10:34:17.309','YYYY-MM-DD HH24:MI:SS.US'),100)
;



-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.TaxAmt
-- 2022-12-15T09:21:29.555Z
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2022-12-15 11:21:29.555','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585324
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- Column: RV_DATEV_Export_Fact_Acct_Invoice.GrandTotal
-- 2022-12-15T09:21:41.698Z
UPDATE AD_Column SET DefaultValue='', IsMandatory='N',Updated=TO_TIMESTAMP('2022-12-15 11:21:41.698','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=585325
;


-- sys config

-- 2022-12-15T11:27:25.090Z
INSERT INTO AD_SysConfig (AD_Client_ID,AD_Org_ID,AD_SysConfig_ID,ConfigurationLevel,Created,CreatedBy,EntityType,IsActive,Name,Updated,UpdatedBy,Value) VALUES (0,0,541583,'S',TO_TIMESTAMP('2022-12-15 13:27:24.88','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.datev','Y','DATEVExportLines_OneLinePerInvoiceTax',TO_TIMESTAMP('2022-12-15 13:27:24.88','YYYY-MM-DD HH24:MI:SS.US'),100,'N')
;

