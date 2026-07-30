-- Run mode: SWING_CLIENT

-- 2026-02-16T08:35:26.076Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584533,0,'IsTriggerOnComplete',TO_TIMESTAMP('2026-02-16 08:35:25.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Führt den definierten Prozess automatisch aus, nachdem das Dokument fertiggestellt wurde, sofern die konfigurierten Bedingungen erfüllt sind.','de.metas.externalsystem','Y','Autom. nach Fertigstellung','Autom. nach Fertigstellung',TO_TIMESTAMP('2026-02-16 08:35:25.906000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-16T08:35:26.089Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584533 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: IsTriggerOnComplete
-- 2026-02-16T08:36:00.693Z
UPDATE AD_Element_Trl SET Description='Runs the process after the document is completed, if conditions are met.', IsTranslated='Y', Name='Auto on Doc Complete', PrintName='Auto on Doc Complete',Updated=TO_TIMESTAMP('2026-02-16 08:36:00.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584533 AND AD_Language='en_US'
;

-- 2026-02-16T08:36:00.695Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-16T08:36:00.927Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584533,'en_US')
;

-- Element: IsTriggerOnComplete
-- 2026-02-16T08:36:06.205Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-16 08:36:06.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584533 AND AD_Language='de_CH'
;

-- 2026-02-16T08:36:06.210Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584533,'de_CH')
;

-- Element: IsTriggerOnComplete
-- 2026-02-16T08:36:07.573Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-16 08:36:07.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584533 AND AD_Language='de_DE'
;

-- 2026-02-16T08:36:07.577Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584533,'de_DE')
;

-- 2026-02-16T08:36:07.580Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584533,'de_DE')
;

-- Column: ExternalSystem_Config_ScriptedExportConversion.IsTriggerOnComplete
-- 2026-02-16T11:33:33.320Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592008,584533,0,20,542541,'XX','IsTriggerOnComplete',TO_TIMESTAMP('2026-02-16 11:33:33.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','N','Führt den definierten Prozess automatisch aus, nachdem das Dokument fertiggestellt wurde, sofern die konfigurierten Bedingungen erfüllt sind.','de.metas.externalsystem',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Autom. nach Fertigstellung',0,0,TO_TIMESTAMP('2026-02-16 11:33:33.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-16T11:33:33.327Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592008 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-16T11:33:33.348Z
/* DDL */  select update_Column_Translation_From_AD_Element(584533)
;

-- 2026-02-16T11:33:35.104Z
/* DDL */ SELECT public.db_alter_table('ExternalSystem_Config_ScriptedExportConversion','ALTER TABLE public.ExternalSystem_Config_ScriptedExportConversion ADD COLUMN IsTriggerOnComplete CHAR(1) DEFAULT ''N'' CHECK (IsTriggerOnComplete IN (''Y'',''N'')) NOT NULL')
;

-- Field: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> Autom. nach Fertigstellung
-- Column: ExternalSystem_Config_ScriptedExportConversion.IsTriggerOnComplete
-- 2026-02-16T11:34:31.467Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592008,773919,0,548464,TO_TIMESTAMP('2026-02-16 11:34:31.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Führt den definierten Prozess automatisch aus, nachdem das Dokument fertiggestellt wurde, sofern die konfigurierten Bedingungen erfüllt sind.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Autom. nach Fertigstellung',TO_TIMESTAMP('2026-02-16 11:34:31.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-16T11:34:31.471Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-16T11:34:31.475Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584533)
;

-- 2026-02-16T11:34:31.487Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773919
;

-- 2026-02-16T11:34:31.499Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773919)
;

-- UI Element: Externes System Konfiguration(541024,de.metas.externalsystem) -> Skriptbasierte Exportkonvertierung(548464,de.metas.externalsystem) -> main -> 20 -> flags.Autom. nach Fertigstellung
-- Column: ExternalSystem_Config_ScriptedExportConversion.IsTriggerOnComplete
-- 2026-02-16T11:35:25.700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773919,0,548464,553621,647947,'F',TO_TIMESTAMP('2026-02-16 11:35:25.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Führt den definierten Prozess automatisch aus, nachdem das Dokument fertiggestellt wurde, sofern die konfigurierten Bedingungen erfüllt sind.','Y','N','N','Y','N','N','N',0,'Autom. nach Fertigstellung',20,0,0,TO_TIMESTAMP('2026-02-16 11:35:25.571000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> Autom. nach Fertigstellung
-- Column: ExternalSystem_Config_ScriptedExportConversion.IsTriggerOnComplete
-- 2026-02-16T11:54:16.047Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592008,773920,0,548471,TO_TIMESTAMP('2026-02-16 11:54:15.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Führt den definierten Prozess automatisch aus, nachdem das Dokument fertiggestellt wurde, sofern die konfigurierten Bedingungen erfüllt sind.',1,'de.metas.externalsystem','Y','N','N','N','N','N','N','N','Autom. nach Fertigstellung',TO_TIMESTAMP('2026-02-16 11:54:15.935000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-16T11:54:16.053Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=773920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-16T11:54:16.060Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584533)
;

-- 2026-02-16T11:54:16.072Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=773920
;

-- 2026-02-16T11:54:16.081Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(773920)
;

-- UI Element: Skriptbasierte Exportkonvertierung(541961,de.metas.externalsystem) -> ExternalSystem_Config_ScriptedExportConversion(548471,de.metas.externalsystem) -> main -> 20 -> flags.Autom. nach Fertigstellung
-- Column: ExternalSystem_Config_ScriptedExportConversion.IsTriggerOnComplete
-- 2026-02-16T11:54:46.719Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,773920,0,548471,553637,647948,'F',TO_TIMESTAMP('2026-02-16 11:54:46.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Führt den definierten Prozess automatisch aus, nachdem das Dokument fertiggestellt wurde, sofern die konfigurierten Bedingungen erfüllt sind.','Y','N','N','Y','N','N','N',0,'Autom. nach Fertigstellung',20,0,0,TO_TIMESTAMP('2026-02-16 11:54:46.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

