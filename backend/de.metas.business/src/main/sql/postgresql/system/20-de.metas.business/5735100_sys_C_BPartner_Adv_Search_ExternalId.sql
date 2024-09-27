-- Run mode: SWING_CLIENT




-- Extra precaution


create table backup.C_Bpartner_Adv_Search_Columns_27092024 AS SELECT * FROM AD_COLUMN WHERE ad_table_ID = 541761;


-- DROP  clumn in case it exists on a private repo


DELETE FROM AD_Column WHERE ad_table_id = 541761 AND Columnname ilike 'ExternalId';

-- 2024-09-27T07:58:24.661Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Adv_Search','ALTER TABLE public.C_BPartner_Adv_Search DROP COLUMN IF EXISTS ExternalId')
;




-- insert column again

-- Column: C_BPartner_Adv_Search.ExternalId
-- 2024-09-27T07:54:18.227Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589170,543939,0,10,541761,'ExternalId',TO_TIMESTAMP('2024-09-27 10:54:17.958','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,255,'Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Externe ID',0,0,TO_TIMESTAMP('2024-09-27 10:54:17.958','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-27T07:54:18.233Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589170 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-27T07:54:18.272Z
/* DDL */  select update_Column_Translation_From_AD_Element(543939)
;

-- 2024-09-27T07:58:24.661Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Adv_Search','ALTER TABLE public.C_BPartner_Adv_Search ADD COLUMN ExternalId VARCHAR(255)')
;

