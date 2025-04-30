--
-- Script: /tmp/webui_migration_scripts_2025-04-29_322526634968644583/5753170_migration_2025-04-30_postgresql.sql
-- User: metasfresh
-- OS user: root
--


-- Run mode: WEBUI

-- Column: C_BP_BankAccount.A_Country
-- 2025-04-30T10:07:40.061Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,Updated,UpdatedBy,Version) VALUES (0,589920,1988,0,10,298,'XX','A_Country_ID',TO_TIMESTAMP('2025-04-30 10:07:40.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Land','D',40,'Name des Landes','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Land',TO_TIMESTAMP('2025-04-30 10:07:40.055000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-30T10:07:56.005Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583601,0,'A_Country_ID',TO_TIMESTAMP('2025-04-30 10:07:56.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'U','Y','Land','Land',TO_TIMESTAMP('2025-04-30 10:07:56.003000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-04-30T10:07:56.014Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583601 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2025-04-30T10:08:03.336Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2025-04-30 10:08:03.336000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583601
;

-- 2025-04-30T10:08:03.410Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583601,'de_DE')
;

-- Element: A_Country_ID
-- 2025-04-30T10:08:18.849Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-30 10:08:18.849000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583601 AND AD_Language='de_CH'
;

-- 2025-04-30T10:08:18.850Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583601,'de_CH')
;

-- Element: A_Country_ID
-- 2025-04-30T10:08:21.785Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-30 10:08:21.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583601 AND AD_Language='de_DE'
;

-- 2025-04-30T10:08:21.786Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583601,'de_DE')
;

-- 2025-04-30T10:08:21.786Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583601,'de_DE')
;

-- Element: A_Country_ID
-- 2025-04-30T10:08:28.149Z
UPDATE AD_Element_Trl SET PrintName='Country',Updated=TO_TIMESTAMP('2025-04-30 10:08:28.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583601 AND AD_Language='en_US'
;

-- 2025-04-30T10:08:28.150Z
UPDATE AD_Element base SET PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-30T10:08:28.306Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583601,'en_US')
;

-- Element: A_Country_ID
-- 2025-04-30T10:08:29.271Z
UPDATE AD_Element_Trl SET Name='Country',Updated=TO_TIMESTAMP('2025-04-30 10:08:29.271000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583601 AND AD_Language='en_US'
;

-- 2025-04-30T10:08:29.272Z
UPDATE AD_Element base SET Name=trl.Name, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-04-30T10:08:29.427Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583601,'en_US')
;

-- Element: A_Country_ID
-- 2025-04-30T10:08:29.434Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-04-30 10:08:29.434000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583601 AND AD_Language='en_US'
;

-- 2025-04-30T10:08:29.436Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583601,'en_US')
;

-- Column: C_BP_BankAccount.A_Country_ID
-- 2025-04-30T10:08:38.336Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,Updated,UpdatedBy,Version) VALUES (0,589920,583601,0,19,298,'XX','A_Country_ID',TO_TIMESTAMP('2025-04-30 10:08:38.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Land',TO_TIMESTAMP('2025-04-30 10:08:38.328000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-04-30T10:08:38.344Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589920 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-04-30T10:08:38.616Z
/* DDL */  select update_Column_Translation_From_AD_Element(583601)
;

-- Column: C_BP_BankAccount.A_Country_ID
-- 2025-04-30T10:08:48.362Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-04-30 10:08:48.362000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589920
;

-- Column: C_BP_BankAccount.A_Country_ID
-- 2025-04-30T10:09:40.376Z
UPDATE AD_Column SET AD_Reference_Value_ID=156, IsExcludeFromZoomTargets='Y',Updated=TO_TIMESTAMP('2025-04-30 10:09:40.376000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589920
;

-- 2025-04-30T10:10:10.371Z
/* DDL */ SELECT public.db_alter_table('C_BP_BankAccount','ALTER TABLE public.C_BP_BankAccount ADD COLUMN A_Country_ID NUMERIC(10)')
;

-- 2025-04-30T10:10:10.491Z
ALTER TABLE C_BP_BankAccount ADD CONSTRAINT ACountry_CBPBankAccount FOREIGN KEY (A_Country_ID) REFERENCES public.C_Country DEFERRABLE INITIALLY DEFERRED
;
