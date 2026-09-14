-- IDs allocated from idserver.metas.de on 2026-07-27:
--   AD_Element    585136 (new IsBudgetForecast label for M_Forecast)
--   AD_Column     593037 (M_Forecast.IsBudgetForecast, default 'N', NOT NULL, mandatory)
--   AD_Field      781847 (Forecast window 328 / header tab 653)
--   AD_UI_Element 652771 (header, group 540277 "flags", SeqNo 40)
--
-- A budget forecast is used only for budgeting/planning and generates NO material-disposition
-- demand (no purchase/production candidates are created from it). The Description/Help below
-- state that meaning; Name/PrintName remain "Budgetprognose" / "Budget Forecast".
-- IsTranslated convention for EntityType 'D' elements: de_DE/de_CH='N' (base-language text
-- already carries German), en_US='Y'. Both flags are set correctly from the start here.

-- Element: IsBudgetForecast
-- 2026-07-27T14:00:00.000Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585136 /*From ID Server*/,0,'IsBudgetForecast',TO_TIMESTAMP('2026-07-27 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Budgetprognose','Budgetprognose',TO_TIMESTAMP('2026-07-27 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-27T14:00:00.100Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585136 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsBudgetForecast (de_DE, base language) -- set Description/Help; IsTranslated='N' per convention
-- 2026-07-27T14:00:01.000Z
UPDATE AD_Element_Trl SET IsTranslated='N',Description='Budget-/Planungsprognose: erzeugt keinen Material-Dispo-Bedarf',Help='Wenn aktiv, dient diese Prognose nur der Budgetierung bzw. Planung und erzeugt keinen Bedarf in der Materialdisposition (es werden keine Bestell- oder Fertigungskandidaten daraus erzeugt).',Updated=TO_TIMESTAMP('2026-07-27 14:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585136 AND AD_Language='de_DE'
;

-- Element: IsBudgetForecast (de_CH) -- mirrors de_DE (no ß / Swiss-specific wording needed); IsTranslated='N' per convention
-- 2026-07-27T14:00:02.000Z
UPDATE AD_Element_Trl SET IsTranslated='N',Description='Budget-/Planungsprognose: erzeugt keinen Material-Dispo-Bedarf',Help='Wenn aktiv, dient diese Prognose nur der Budgetierung bzw. Planung und erzeugt keinen Bedarf in der Materialdisposition (es werden keine Bestell- oder Fertigungskandidaten daraus erzeugt).',Updated=TO_TIMESTAMP('2026-07-27 14:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585136 AND AD_Language='de_CH'
;

-- Element: IsBudgetForecast (en_US) -- English Name/Description/Help; IsTranslated='Y'
-- 2026-07-27T14:00:03.000Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Budget Forecast', PrintName='Budget Forecast',Description='Budget/planning forecast: does not generate material-disposition demand',Help='If active, this forecast is used only for budgeting/planning and does not generate any material-disposition demand (no purchase or production candidates are created from it).',Updated=TO_TIMESTAMP('2026-07-27 14:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=585136 AND AD_Language='en_US'
;

-- Propagate base-language (de_DE) Description/Help back onto the base AD_Element record
-- 2026-07-27T14:00:03.100Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(585136,'de_DE')
;

-- Column: M_Forecast.IsBudgetForecast (AD_Table_ID=720)
-- 2026-07-27T14:00:04.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,PersonalDataCategory,Version) VALUES (0,593037 /*From ID Server*/,585136,0,20,720,'XX','IsBudgetForecast',TO_TIMESTAMP('2026-07-27 14:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'Budgetprognose',0,0,TO_TIMESTAMP('2026-07-27 14:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'NP',0)
;

-- 2026-07-27T14:00:04.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593037 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-07-27T14:00:04.200Z
/* DDL */  select update_Column_Translation_From_AD_Element(585136)
;

-- 2026-07-27T14:00:05.000Z
/* DDL */ SELECT public.db_alter_table('M_Forecast','ALTER TABLE public.M_Forecast ADD COLUMN IsBudgetForecast CHAR(1) DEFAULT ''N'' CHECK (IsBudgetForecast IN (''Y'',''N'')) NOT NULL')
;

-- Field: Prognose(328,D) -> Prognose(653,D) -> Budgetprognose
-- Column: M_Forecast.IsBudgetForecast
-- 2026-07-27T14:00:06.000Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,593037,781847 /*From ID Server*/,0,653,TO_TIMESTAMP('2026-07-27 14:00:06.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','Y','N','N','N','N','N','N','Budgetprognose',TO_TIMESTAMP('2026-07-27 14:00:06.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-07-27T14:00:06.100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=781847 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Propagate element Name/Description/Help/IsTranslated to AD_Field_Trl (all languages) and to the
-- AD_Field base Name/Description. NOTE: this function does NOT sync AD_Field.Help (base column) -
-- that is set explicitly below (known pitfall; see metasfresh-application-dictionary skill).
-- 2026-07-27T14:00:06.200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585136)
;

-- 2026-07-27T14:00:06.300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=781847
;

-- 2026-07-27T14:00:06.400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(781847)
;

-- AD_Field.Help (base column) is not carried by update_FieldTranslation_From_AD_Name_Element - set it directly
-- 2026-07-27T14:00:06.500Z
UPDATE AD_Field SET Help='Wenn aktiv, dient diese Prognose nur der Budgetierung bzw. Planung und erzeugt keinen Bedarf in der Materialdisposition (es werden keine Bestell- oder Fertigungskandidaten daraus erzeugt).',Updated=TO_TIMESTAMP('2026-07-27 14:00:06.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=781847
;

-- UI Element: Prognose(328,D) -> Prognose(653,D) -> main -> flags.Budgetprognose
-- Column: M_Forecast.IsBudgetForecast
-- 2026-07-27T14:00:07.000Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,781847,0,653,540277,652771 /*From ID Server*/,'F',TO_TIMESTAMP('2026-07-27 14:00:07.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','N','N','Budgetprognose',40,0,0,TO_TIMESTAMP('2026-07-27 14:00:07.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
