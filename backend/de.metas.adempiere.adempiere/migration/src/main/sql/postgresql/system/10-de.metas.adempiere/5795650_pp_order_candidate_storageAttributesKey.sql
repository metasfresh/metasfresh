-- Run mode: SWING_CLIENT

-- Column: PP_Order_Candidate.StorageAttributesKey
-- 2026-03-25T16:11:31.400Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,TechnicalNote,Updated,UpdatedBy,Version) VALUES (0,592312,543463,0,10,541913,'XX','StorageAttributesKey',TO_TIMESTAMP('2026-03-25 16:11:31.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','EE01',0,1024,'Note: in java we have the following constants
* none: "-1002" (makes sense where this is used for stock-keeping)
* other: "-1001" (makes sense where this is used information)
* all: "-1000" (makes sense where this is used for matching)','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'StorageAttributesKey (technical)','1=2',0,0,'Always computed based on M_AttributeSetInstance_ID',TO_TIMESTAMP('2026-03-25 16:11:31.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-25T16:11:31.408Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592312 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-25T16:11:31.434Z
/* DDL */  select update_Column_Translation_From_AD_Element(543463)
;

-- 2026-03-25T16:11:35.561Z
/* DDL */ SELECT public.db_alter_table('PP_Order_Candidate','ALTER TABLE public.PP_Order_Candidate ADD COLUMN StorageAttributesKey VARCHAR(1024)')
;

-- Column: PP_Order_Candidate.StorageAttributesKey
-- 2026-03-25T17:08:25.613Z
UPDATE AD_Column SET IsUpdateable='N', ReadOnlyLogic='',Updated=TO_TIMESTAMP('2026-03-25 17:08:25.613000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=592312
;

