-- Run mode: SWING_CLIENT

-- 2026-04-29T16:45:40.751Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584805,0,'Bp_account_place',TO_TIMESTAMP('2026-04-29 16:45:39.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev','Y','bp_account_place','bp_account_place',TO_TIMESTAMP('2026-04-29 16:45:39.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-04-29T16:45:40.761Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584805 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.Bp_account_place
-- 2026-04-29T16:45:41.229Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,592446,584805,0,14,540936,'Bp_account_place',TO_TIMESTAMP('2026-04-29 16:45:39.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.datev',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','bp_account_place',TO_TIMESTAMP('2026-04-29 16:45:39.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-04-29T16:45:41.234Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592446 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-29T16:45:41.259Z
/* DDL */  select update_Column_Translation_From_AD_Element(584805)
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.DueDate
-- 2026-04-29T16:45:41.603Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,592447,2000,0,16,540936,'DueDate',TO_TIMESTAMP('2026-04-29 16:45:41.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, zu dem Zahlung fällig wird','de.metas.datev',35,'Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','Y','N','N','N','N','N','N','N','N','N','Datum Fälligkeit',TO_TIMESTAMP('2026-04-29 16:45:41.500000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-04-29T16:45:41.607Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592447 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-29T16:45:41.682Z
/* DDL */  select update_Column_Translation_From_AD_Element(2000)
;

-- 2026-04-29T16:46:25.178Z
UPDATE AD_Element SET ColumnName='BP_Account_Place', Name='Soll/Haben-Kennzeichen', PrintName='Soll/Haben-Kennzeichen',Updated=TO_TIMESTAMP('2026-04-29 16:46:25.177000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584805
;

-- 2026-04-29T16:46:25.180Z
UPDATE AD_Element_Trl trl SET Name='Soll/Haben-Kennzeichen',PrintName='Soll/Haben-Kennzeichen' WHERE AD_Element_ID=584805 AND AD_Language='de_DE'
;

-- 2026-04-29T16:46:25.182Z
UPDATE AD_Column SET ColumnName='BP_Account_Place' WHERE AD_Element_ID=584805
;

-- 2026-04-29T16:46:25.184Z
UPDATE AD_Process_Para SET ColumnName='BP_Account_Place' WHERE AD_Element_ID=584805
;

-- 2026-04-29T16:46:25.188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584805,'de_DE')
;

-- Element: BP_Account_Place
-- 2026-04-29T16:46:31.299Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Soll/Haben-Kennzeichen', PrintName='Soll/Haben-Kennzeichen',Updated=TO_TIMESTAMP('2026-04-29 16:46:31.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584805 AND AD_Language in ('de_CH', 'de_DE')
;

-- 2026-04-29T16:46:31.300Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language in ('de_CH', 'de_DE') AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-29T16:46:31.726Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584805,'de_CH')
;

/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584805,'de_DE')
;


-- Element: BP_Account_Place
-- 2026-04-29T16:46:56.861Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Debit/Credit indicator', PrintName='Debit/Credit indicator',Updated=TO_TIMESTAMP('2026-04-29 16:46:56.861000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584805 AND AD_Language='en_US'
;

-- 2026-04-29T16:46:56.862Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-29T16:46:57.053Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584805,'en_US')
;

-- Element: BP_Account_Place
-- 2026-04-29T16:47:02.195Z
UPDATE AD_Element_Trl SET Name='Debit/Credit indicator', PrintName='Debit/Credit indicator',Updated=TO_TIMESTAMP('2026-04-29 16:47:02.195000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584805 AND AD_Language='fr_CH'
;

-- 2026-04-29T16:47:02.196Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='fr_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-04-29T16:47:02.412Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584805,'fr_CH')
;

-- Column: DATEV_ExportLine.BP_Account_Place
-- 2026-04-29T16:57:06.247Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592448,584805,0,10,540935,'XX','BP_Account_Place',TO_TIMESTAMP('2026-04-29 16:57:06.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.datev',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Soll/Haben-Kennzeichen',0,0,TO_TIMESTAMP('2026-04-29 16:57:06.128000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-04-29T16:57:06.251Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592448 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-29T16:57:06.326Z
/* DDL */  select update_Column_Translation_From_AD_Element(584805)
;

-- 2026-04-29T16:57:11.461Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN BP_Account_Place VARCHAR(1)')
;

-- Column: RV_DATEV_Export_Fact_Acct_Invoice.BP_Account_Place
-- 2026-04-29T16:57:50.320Z
UPDATE AD_Column SET AD_Reference_ID=10, FieldLength=1,Updated=TO_TIMESTAMP('2026-04-29 16:57:50.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592446
;

-- Column: DATEV_ExportLine.DueDate
-- 2026-04-29T17:00:55.667Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592449,2000,0,15,540935,'XX','DueDate',TO_TIMESTAMP('2026-04-29 17:00:55.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Datum, zu dem Zahlung fällig wird','de.metas.datev',0,7,'Datum, zu dem Zahlung ohne Abzüge oder Rabattierung fällig wird.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Datum Fälligkeit',0,0,TO_TIMESTAMP('2026-04-29 17:00:55.511000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-04-29T17:00:55.672Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592449 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-04-29T17:00:55.744Z
/* DDL */  select update_Column_Translation_From_AD_Element(2000)
;

-- 2026-04-29T17:01:19.415Z
/* DDL */ SELECT public.db_alter_table('DATEV_ExportLine','ALTER TABLE public.DATEV_ExportLine ADD COLUMN DueDate TIMESTAMP WITHOUT TIME ZONE')
;


-- Value: DATEV_CreateExportLines
-- Classname: de.metas.datev.process.DATEV_CreateExportLines
-- 2026-05-01T05:50:17.816Z
UPDATE AD_Process SET RefreshAllAfterExecution='Y',Updated=TO_TIMESTAMP('2026-05-01 05:50:17.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Process_ID=540923
;

