-- Run mode: SWING_CLIENT

-- 2026-01-21T08:42:31.212Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584419,0,'PRINTER_OPTS_PrintPrice',TO_TIMESTAMP('2026-01-21 08:42:30.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Preis drucken','Preis drucken',TO_TIMESTAMP('2026-01-21 08:42:30.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T08:42:31.223Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584419 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: PRINTER_OPTS_PrintPrice
-- 2026-01-21T08:42:46.354Z
UPDATE AD_Element_Trl SET Name='Print Price', PrintName='Print Price',Updated=TO_TIMESTAMP('2026-01-21 08:42:46.354000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584419 AND AD_Language='en_US'
;

-- 2026-01-21T08:42:46.355Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T08:42:46.848Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584419,'en_US')
;

-- 2026-01-21T08:46:30.567Z
UPDATE AD_Element SET ColumnName='PRINTER_OPTS_IsPrintPrice',Updated=TO_TIMESTAMP('2026-01-21 08:46:30.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584419
;

-- 2026-01-21T08:46:30.568Z
UPDATE AD_Column SET ColumnName='PRINTER_OPTS_IsPrintPrice' WHERE AD_Element_ID=584419
;

-- 2026-01-21T08:46:30.569Z
UPDATE AD_Process_Para SET ColumnName='PRINTER_OPTS_IsPrintPrice' WHERE AD_Element_ID=584419
;

-- 2026-01-21T08:46:30.572Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584419,'de_DE')
;

-- Column: C_DocType_PrintOptions.PRINTER_OPTS_IsPrintPrice
-- 2026-01-21T08:47:05.111Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591863,584419,0,37,541551,'XX','PRINTER_OPTS_IsPrintPrice',TO_TIMESTAMP('2026-01-21 08:47:04.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Preis drucken',0,0,TO_TIMESTAMP('2026-01-21 08:47:04.949000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-21T08:47:05.113Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591863 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-21T08:47:05.116Z
/* DDL */  select update_Column_Translation_From_AD_Element(584419)
;

-- Column: C_DocType_PrintOptions.PRINTER_OPTS_IsPrintPrice
-- 2026-01-21T08:47:33.911Z
UPDATE AD_Column SET AD_Reference_ID=20, DefaultValue='N', FieldLength=1,Updated=TO_TIMESTAMP('2026-01-21 08:47:33.911000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591863
;

-- 2026-01-21T08:53:32.264Z
UPDATE AD_Element SET ColumnName='PRINTER_OPTS_IsPrintPrices',Updated=TO_TIMESTAMP('2026-01-21 08:53:32.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584419
;

-- 2026-01-21T08:53:32.265Z
UPDATE AD_Column SET ColumnName='PRINTER_OPTS_IsPrintPrices' WHERE AD_Element_ID=584419
;

-- 2026-01-21T08:53:32.266Z
UPDATE AD_Process_Para SET ColumnName='PRINTER_OPTS_IsPrintPrices' WHERE AD_Element_ID=584419
;

-- 2026-01-21T08:53:32.269Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584419,'de_DE')
;

-- 2026-01-21T08:53:43.610Z
/* DDL */ SELECT public.db_alter_table('C_DocType_PrintOptions','ALTER TABLE public.C_DocType_PrintOptions ADD COLUMN PRINTER_OPTS_IsPrintPrices CHAR(1) DEFAULT ''N'' CHECK (PRINTER_OPTS_IsPrintPrices IN (''Y'',''N'')) NOT NULL')
;

-- Element: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T08:55:05.682Z
UPDATE AD_Element_Trl SET Name='Print Prices', PrintName='Print Prices',Updated=TO_TIMESTAMP('2026-01-21 08:55:05.682000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584419 AND AD_Language='en_US'
;

-- 2026-01-21T08:55:05.683Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-01-21T08:55:05.960Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584419,'en_US')
;

-- Process: Auftrag (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T09:05:01.146Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584419,0,500007,543099,20,'PRINTER_OPTS_IsPrintPrices',TO_TIMESTAMP('2026-01-21 09:05:00.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U',0,'Y','N','Y','N','N','N','Preis drucken',30,TO_TIMESTAMP('2026-01-21 09:05:00.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T09:05:01.148Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543099 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Auftrag (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T09:07:09.243Z
UPDATE AD_Process_Para SET DefaultValue='Y',Updated=TO_TIMESTAMP('2026-01-21 09:07:09.243000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543099
;

-- Process: Auftrag (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T09:07:52.197Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 09:07:52.197000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543099
;

-- Element: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T09:08:00.157Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-01-21 09:08:00.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584419 AND AD_Language='en_US'
;

-- 2026-01-21T09:08:00.159Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584419,'en_US')
;

-- Process: Lieferschein (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T09:22:47.242Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584419,0,500008,543100,20,'PRINTER_OPTS_IsPrintPrices',TO_TIMESTAMP('2026-01-21 09:22:47.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','U',0,'Y','N','Y','N','N','N','Preis drucken',20,TO_TIMESTAMP('2026-01-21 09:22:47.054000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T09:22:47.244Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543100 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- Process: Lieferschein (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T10:00:42.646Z
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2026-01-21 10:00:42.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_Para_ID=543100
;

-- Field: Dokumenten Typ Druck Optionen(541004,D) -> Dokumenten Typ Druck Optionen(543206,D) -> Preis drucken
-- Column: C_DocType_PrintOptions.PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T10:10:06.897Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591863,765158,0,543206,0,TO_TIMESTAMP('2026-01-21 10:10:06.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Preis drucken',0,0,60,0,1,1,TO_TIMESTAMP('2026-01-21 10:10:06.691000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-21T10:10:06.900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=765158 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-21T10:10:06.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584419)
;

-- 2026-01-21T10:10:06.918Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=765158
;

-- 2026-01-21T10:10:06.920Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(765158)
;

-- UI Element: Dokumenten Typ Druck Optionen(541004,D) -> Dokumenten Typ Druck Optionen(543206,D) -> main -> 20 -> Flags.Preis drucken
-- Column: C_DocType_PrintOptions.PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T10:10:28.928Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,765158,0,543206,544605,643569,'F',TO_TIMESTAMP('2026-01-21 10:10:28.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Preis drucken',40,0,0,TO_TIMESTAMP('2026-01-21 10:10:28.752000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_DocType_PrintOptions.PRINTER_OPTS_IsPrintPrices
-- 2026-01-21T10:11:12.531Z
UPDATE AD_Column SET DefaultValue='Y',Updated=TO_TIMESTAMP('2026-01-21 10:11:12.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591863
;

-- UI Element: Dokumenten Typ Druck Optionen(541004,D) -> Dokumenten Typ Druck Optionen(543206,D) -> main -> 20 -> Flags.Preis drucken
-- Column: C_DocType_PrintOptions.PRINTER_OPTS_IsPrintPrices
-- 2026-01-22T07:31:28.713Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-01-22 07:31:28.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=643569
;

-- UI Element: Dokumenten Typ Druck Optionen(541004,D) -> Dokumenten Typ Druck Optionen(543206,D) -> main -> 20 -> Flags.Aktiv
-- Column: C_DocType_PrintOptions.IsActive
-- 2026-01-22T07:31:28.724Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-01-22 07:31:28.724000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=575097
;

-- UI Element: Dokumenten Typ Druck Optionen(541004,D) -> Dokumenten Typ Druck Optionen(543206,D) -> main -> 10 -> main.Beschreibung
-- Column: C_DocType_PrintOptions.Description
-- 2026-01-22T07:31:28.730Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-01-22 07:31:28.730000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=575096
;

-- Process: Bestellung (Jasper)(de.metas.report.jasper.client.process.JasperReportStarter)
-- ParameterName: PRINTER_OPTS_IsPrintPrices
-- 2026-01-22T07:47:13.398Z
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,584419,0,500010,543107,20,'PRINTER_OPTS_IsPrintPrices',TO_TIMESTAMP('2026-01-22 07:47:13.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','U',0,'Y','N','Y','N','N','N','Preis drucken',20,TO_TIMESTAMP('2026-01-22 07:47:13.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T07:47:13.405Z
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Process_Para_ID=543107 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;
