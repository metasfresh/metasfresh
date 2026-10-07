-- Run mode: SWING_CLIENT

-- 2026-03-26T10:51:06.048Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584701,0,'IsFinancial',TO_TIMESTAMP('2026-03-26 10:51:05.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gibt an, ob die Rechnung finanzielle Auswirkungen hat (z. B. buchungsrelevant ist).','U','Y','Financial Relevant','Financial Relevant',TO_TIMESTAMP('2026-03-26 10:51:05.908000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-26T10:51:06.061Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584701 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-03-26T10:51:40.039Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-03-26 10:51:40.035000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584701
;

-- 2026-03-26T10:51:40.061Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584701,'de_DE')
;

-- Element: IsFinancial
-- 2026-03-26T10:52:14.053Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Finanzrelevant', PrintName='Finanzrelevant',Updated=TO_TIMESTAMP('2026-03-26 10:52:14.053000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584701 AND AD_Language='de_CH'
;

-- 2026-03-26T10:52:14.055Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T10:52:14.219Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584701,'de_CH')
;

-- Element: IsFinancial
-- 2026-03-26T10:52:22.342Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Finanzrelevant', PrintName='Finanzrelevant',Updated=TO_TIMESTAMP('2026-03-26 10:52:22.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584701 AND AD_Language='de_DE'
;

-- 2026-03-26T10:52:22.343Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T10:52:24.734Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(584701,'de_DE')
;

-- 2026-03-26T10:52:24.735Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584701,'de_DE')
;

-- Element: IsFinancial
-- 2026-03-26T10:52:52.517Z
UPDATE AD_Element_Trl SET Description='Indicates whether the invoice has financial impact (e.g., posted to accounting).', IsTranslated='Y',Updated=TO_TIMESTAMP('2026-03-26 10:52:52.516000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584701 AND AD_Language='en_US'
;

-- 2026-03-26T10:52:52.518Z
UPDATE AD_Element base SET Description=trl.Description, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-03-26T10:52:52.740Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584701,'en_US')
;

-- Column: C_Invoice.IsFinancial
-- 2026-03-26T10:57:22.905Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592313,584701,0,20,318,'XX','IsFinancial',TO_TIMESTAMP('2026-03-26 10:57:22.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Y','Gibt an, ob die Rechnung finanzielle Auswirkungen hat (z. B. buchungsrelevant ist).','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','Y','N',0,'Finanzrelevant',0,0,TO_TIMESTAMP('2026-03-26 10:57:22.781000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-26T10:57:22.909Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592313 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-26T10:57:22.915Z
/* DDL */  select update_Column_Translation_From_AD_Element(584701)
;

-- 2026-03-26T10:57:28.495Z
/* DDL */ SELECT public.db_alter_table('C_Invoice','ALTER TABLE public.C_Invoice ADD COLUMN IsFinancial CHAR(1) DEFAULT ''Y'' CHECK (IsFinancial IN (''Y'',''N'')) NOT NULL')
;

