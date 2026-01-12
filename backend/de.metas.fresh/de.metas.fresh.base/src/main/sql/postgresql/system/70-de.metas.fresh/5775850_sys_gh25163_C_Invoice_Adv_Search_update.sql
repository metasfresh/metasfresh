-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Adv_Search.DateInvoiced
-- 2025-11-06T15:04:30.084Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=1000063
;

-- 2025-11-06T15:04:30.500Z
DELETE FROM AD_Column WHERE AD_Column_ID=1000063
;

-- Column: C_Invoice_Adv_Search.DateInvoiced
-- 2025-11-06T15:05:14.842Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591494,267,0,15,542435,'DateInvoiced',TO_TIMESTAMP('2025-11-06 16:05:13.969','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Datum auf der Rechnung','D',0,7,'"Rechnungsdatum" bezeichnet das auf der Rechnung verwendete Datum.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Rechnungsdatum',0,0,TO_TIMESTAMP('2025-11-06 16:05:13.969','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-11-06T15:05:14.921Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591494 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-11-06T15:05:15.088Z
/* DDL */  select update_Column_Translation_From_AD_Element(267)
;

-- 2025-11-06T15:05:55.533Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Adv_Search','ALTER TABLE public.C_Invoice_Adv_Search ADD COLUMN DateInvoiced TIMESTAMP WITHOUT TIME ZONE')
;

