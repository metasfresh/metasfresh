-- Run mode: SWING_CLIENT

-- 2026-09-29T07:16:19.045Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585497,0,'IsShowCustomsTariffNumber',TO_TIMESTAMP('2026-09-29 07:16:18.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Zolltarifnummer anzeigen','Zolltarifnummer anzeigen',TO_TIMESTAMP('2026-09-29 07:16:18.505000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-29T07:16:19.133Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585497 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsShowCustomsTariffNumber
-- 2026-09-29T07:17:37.556Z
UPDATE AD_Element_Trl SET Description='Show Customs Tariff Number in Sales invoice report', Help='Show Customs Tariff Number in Sales invoice report', IsTranslated='Y', Name='Show Customs Tariff Number', PrintName='Show Customs Tariff Number',Updated=TO_TIMESTAMP('2026-09-29 07:17:37.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585497 AND AD_Language='en_US'
;

-- 2026-09-29T07:17:37.627Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-09-29T07:17:43.232Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585497,'en_US')
;

-- Element: IsShowCustomsTariffNumber
-- 2026-09-29T07:17:52.621Z
UPDATE AD_Element_Trl SET Description='Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen', Help='Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-09-29 07:17:52.621000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585497 AND AD_Language='de_DE'
;

-- 2026-09-29T07:17:52.692Z
UPDATE AD_Element base SET Description=trl.Description, Help=trl.Help, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-09-29T07:17:59.687Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585497,'de_DE')
;

-- 2026-09-29T07:17:59.746Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585497,'de_DE')
;

-- Column: C_BPartner.IsShowCustomsTariffNumber
-- 2026-09-29T07:18:53.732Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593672,585497,0,20,291,'XX','IsShowCustomsTariffNumber',TO_TIMESTAMP('2026-09-29 07:18:53.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen','D',0,1,'Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Zolltarifnummer anzeigen',0,0,TO_TIMESTAMP('2026-09-29 07:18:53.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-09-29T07:18:53.794Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593672 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-29T07:18:53.921Z
/* DDL */  select update_Column_Translation_From_AD_Element(585497)
;

-- 2026-09-29T07:19:10.055Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN IsShowCustomsTariffNumber CHAR(1) DEFAULT ''N'' CHECK (IsShowCustomsTariffNumber IN (''Y'',''N'')) NOT NULL')
;

-- Field: Geschäftspartner_old(123,D) -> Geschäftspartner(220,D) -> Zolltarifnummer anzeigen
-- Column: C_BPartner.IsShowCustomsTariffNumber
-- 2026-09-29T07:25:00.876Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,593672,785592,0,220,0,TO_TIMESTAMP('2026-09-29 07:24:59.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen',0,'D',0,'Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Zolltarifnummer anzeigen',0,0,460,0,1,1,TO_TIMESTAMP('2026-09-29 07:24:59.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-09-29T07:25:00.951Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=785592 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-09-29T07:25:01.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585497)
;

-- 2026-09-29T07:25:01.085Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=785592
;

-- 2026-09-29T07:25:01.148Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(785592)
;

-- UI Element: Geschäftspartner_old(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.Zolltarifnummer anzeigen
-- Column: C_BPartner.IsShowCustomsTariffNumber
-- 2026-09-29T07:26:06.184Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,785592,0,220,1000011,654920,'F',TO_TIMESTAMP('2026-09-29 07:26:05.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen','Zolltarifnummer im Bericht zur Verkaufsrechnung anzeigen','Y','N','N','Y','N','N','N',0,'Zolltarifnummer anzeigen',125,0,0,TO_TIMESTAMP('2026-09-29 07:26:05.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

