-- Adds M_Product.BestBeforeDays (total shelf life in days) and its I_Product import twin.
-- Idempotent on purpose: some instances already carry M_Product.BestBeforeDays from a customer-side migration,
-- with their own AD_Column/AD_Element ids. AD rows are therefore detected by (table, column name) / column name
-- and created only if missing; the physical column is added only if missing. Pre-existing M_Product / I_Product AD_Columns are
-- reconciled to core (EntityType D, Integer reference); their physical column types are NOT touched. An existing
-- unbounded NUMERIC column is left as is (compatible as long as the values are whole days).

-- Element (German base text, en_US override below); created only if no element named BestBeforeDays exists
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy)
SELECT 0,585545 /*From ID Server*/,0,'BestBeforeDays',TO_TIMESTAMP('2026-10-19 14:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Gesamthaltbarkeit des Produkts in Tagen.','D','Anzahl der Tage, die das Produkt ab Produktion insgesamt haltbar ist (Mindesthaltbarkeitsdatum minus Produktionsdatum).','Y','Gesamthaltbarkeit','Gesamthaltbarkeit',TO_TIMESTAMP('2026-10-19 14:00:00','YYYY-MM-DD HH24:MI:SS'),100
WHERE NOT EXISTS (SELECT 1 FROM AD_Element e WHERE e.ColumnName='BestBeforeDays')
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Description,Help,Name,PrintName,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Element_ID,t.Description,t.Help,t.Name,t.PrintName,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Element_ID=585545
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET IsTranslated='Y',Name='Total shelf life',PrintName='Total shelf life',Description='Total shelf life of the product in days.',Help='Number of days the product stays good in total, counted from its production date (best-before date minus production date).',Updated=TO_TIMESTAMP('2026-10-19 14:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585545 AND AD_Language='en_US'
;

-- M_Product.BestBeforeDays
ALTER TABLE M_Product ADD COLUMN IF NOT EXISTS BestBeforeDays NUMERIC(10)
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
SELECT 0,593750 /*From ID Server*/,(SELECT AD_Element_ID FROM AD_Element WHERE ColumnName='BestBeforeDays' ORDER BY AD_Element_ID LIMIT 1),0,11,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product'),'XX','BestBeforeDays',TO_TIMESTAMP('2026-10-19 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Gesamthaltbarkeit des Produkts in Tagen.','D',0,10,'Anzahl der Tage, die das Produkt ab Produktion insgesamt haltbar ist (Mindesthaltbarkeitsdatum minus Produktionsdatum).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N',0,'Gesamthaltbarkeit','NP',0,0,TO_TIMESTAMP('2026-10-19 14:01:00','YYYY-MM-DD HH24:MI:SS'),100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='M_Product'))
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593750
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- I_Product.BestBeforeDays (import table twin)
ALTER TABLE I_Product ADD COLUMN IF NOT EXISTS BestBeforeDays NUMERIC(10)
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
SELECT 0,593751 /*From ID Server*/,(SELECT AD_Element_ID FROM AD_Element WHERE ColumnName='BestBeforeDays' ORDER BY AD_Element_ID LIMIT 1),0,11,(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product'),'XX','BestBeforeDays',TO_TIMESTAMP('2026-10-19 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,'N','Gesamthaltbarkeit des Produkts in Tagen.','D',0,10,'Anzahl der Tage, die das Produkt ab Produktion insgesamt haltbar ist (Mindesthaltbarkeitsdatum minus Produktionsdatum).','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','Y','N',0,'Gesamthaltbarkeit','NP',0,0,TO_TIMESTAMP('2026-10-19 14:02:00','YYYY-MM-DD HH24:MI:SS'),100,0
WHERE NOT EXISTS (SELECT 1 FROM AD_Column c WHERE c.ColumnName='BestBeforeDays' AND c.AD_Table_ID=(SELECT AD_Table_ID FROM AD_Table WHERE TableName='I_Product'))
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Column_ID,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND t.AD_Column_ID=593751
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Reconcile pre-existing M_Product / I_Product BestBeforeDays AD_Columns to core (no-op for the columns created above).
-- Only the AD reference changes; the physical column type is untouched.
UPDATE AD_Column SET EntityType='D',AD_Reference_ID=11,Updated=TO_TIMESTAMP('2026-10-19 14:02:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100
WHERE ColumnName='BestBeforeDays' AND AD_Table_ID IN (SELECT AD_Table_ID FROM AD_Table WHERE TableName IN ('M_Product','I_Product')) AND (EntityType<>'D' OR AD_Reference_ID<>11)
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(AD_Element_ID) FROM AD_Element WHERE ColumnName='BestBeforeDays'
;
