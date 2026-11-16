-- Column: M_InOutLine.M_Delivery_Planning_ID
--
-- A delivery planning is received into a receipt LINE and shipped out of a shipment LINE: a line corresponds to
-- a receipt/shipment schedule and therefore to exactly one planning, while the HEADER aggregates lines by the
-- standard criteria and can legitimately carry several plannings. M_InOut.M_Delivery_Planning_ID (AD_Column
-- 585626, added by 5673610) sits at the wrong grain and is dropped by a later script in this same change set;
-- this column replaces it for both directions.
--
-- AD_Element 581677 ('M_Delivery_Planning_ID') is REUSED, not re-created: ad_element_columnname_uc makes
-- AD_Element unique per ColumnName, so the eight columns of this name across the dictionary already share it.
--
-- IDs allocated from idserver.metas.de on 2026-09-09:
--   AD_Column 593535 (M_InOutLine.M_Delivery_Planning_ID)
--
-- Nullable / IsMandatory='N' by construction: a line of an unplanned receive has no planning, and every
-- pre-existing line predates this column.

-- 2026-09-09T00:00:00.000Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,593535 /*From ID Server*/,581677,0,30,320,'M_Delivery_Planning_ID',TO_TIMESTAMP('2026-09-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Delivery Planning',0,0,TO_TIMESTAMP('2026-09-09 00:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2026-09-09T00:00:00.000Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593535 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-09-09T00:00:00.000Z
/* DDL */  select update_Column_Translation_From_AD_Element(581677)
;

-- 2026-09-09T00:00:00.000Z
/* DDL */ SELECT public.db_alter_table('M_InOutLine','ALTER TABLE public.M_InOutLine ADD COLUMN M_Delivery_Planning_ID NUMERIC(10)')
;

-- 2026-09-09T00:00:00.000Z
ALTER TABLE M_InOutLine ADD CONSTRAINT MDeliveryPlanning_MInOutLine FOREIGN KEY (M_Delivery_Planning_ID) REFERENCES public.M_Delivery_Planning DEFERRABLE INITIALLY DEFERRED
;
