-- Run mode: SWING_CLIENT

-- UI Element: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> 1000028 -> 10 -> main.Preiseinheit
-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-04T11:00:19.163Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=624016
;

-- 2024-04-04T11:00:19.173Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=727305
;

-- Field: Vertrag(540359,de.metas.contracts) -> Contract Specific Prices(547499,D) -> Preiseinheit
-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-04T11:00:19.176Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=727305
;

-- 2024-04-04T11:00:19.179Z
DELETE FROM AD_Field WHERE AD_Field_ID=727305
;

-- 2024-04-04T11:00:19.210Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE ModCntr_Specific_Price DROP COLUMN IF EXISTS PriceUOM')
;

-- Column: ModCntr_Specific_Price.PriceUOM
-- 2024-04-04T11:00:19.231Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=588102
;

-- 2024-04-04T11:00:19.235Z
DELETE FROM AD_Column WHERE AD_Column_ID=588102
;

-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-04T11:02:12.223Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588112,215,0,19,542405,'C_UOM_ID',TO_TIMESTAMP('2024-04-04 14:02:12.095','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Maßeinheit','de.metas.contracts',0,10,'Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Maßeinheit',0,0,TO_TIMESTAMP('2024-04-04 14:02:12.095','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-04-04T11:02:12.229Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588112 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-04-04T11:02:12.231Z
/* DDL */  select update_Column_Translation_From_AD_Element(215)
;

-- 2024-04-04T11:02:14.005Z
/* DDL */ SELECT public.db_alter_table('ModCntr_Specific_Price','ALTER TABLE public.ModCntr_Specific_Price ADD COLUMN C_UOM_ID NUMERIC(10)')
;

-- 2024-04-04T11:02:14.010Z
ALTER TABLE ModCntr_Specific_Price ADD CONSTRAINT CUOM_ModCntrSpecificPrice FOREIGN KEY (C_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: ModCntr_Specific_Price.C_UOM_ID
-- 2024-04-04T11:04:18.056Z
UPDATE AD_Column SET IsMandatory='Y',Updated=TO_TIMESTAMP('2024-04-04 14:04:18.056','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=588112
;

-- 2024-04-04T11:04:18.634Z
INSERT INTO t_alter_column values('modcntr_specific_price','C_UOM_ID','NUMERIC(10)',null,null)
;

-- 2024-04-04T11:04:18.636Z
INSERT INTO t_alter_column values('modcntr_specific_price','C_UOM_ID',null,'NOT NULL',null)
;

-- UI Element: Vertragsbaustein Typ(541710,de.metas.contracts) -> Vertragsbaustein Typ(547011,de.metas.contracts) -> main -> 10 -> infos.Beschreibung
-- Column: ModCntr_Type.Description
-- 2024-04-04T11:07:54.839Z
UPDATE AD_UI_Element SET WidgetSize='XXL',Updated=TO_TIMESTAMP('2024-04-04 14:07:54.839','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=617950
;

-- Column: ModCntr_Type.Description
-- 2024-04-04T11:08:49.010Z
UPDATE AD_Column SET AD_Reference_ID=14,Updated=TO_TIMESTAMP('2024-04-04 14:08:49.01','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=586750
;

-- 2024-04-04T11:08:54.955Z
INSERT INTO t_alter_column values('modcntr_type','Description','VARCHAR(2000)',null,null)
;

