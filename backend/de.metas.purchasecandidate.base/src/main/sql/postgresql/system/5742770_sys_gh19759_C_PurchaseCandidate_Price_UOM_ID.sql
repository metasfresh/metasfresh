
DO
$$
BEGIN

    IF not exists(select 1
                  from ad_column
                  where columnname = 'Price_UOM_ID' and ad_table_id = 540861) THEN

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2025-01-09T17:58:13.095Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,AD_Val_Rule_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589574,542464,0,18,114,540861,540472,'XX','Price_UOM_ID',TO_TIMESTAMP('2025-01-09 17:58:12.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','de.metas.purchasecandidate',0,10,'Y','N','Y','N','Y','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Preiseinheit',0,0,TO_TIMESTAMP('2025-01-09 17:58:12.811000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-01-09T17:58:13.100Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589574 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-01-09T17:58:13.160Z
/* DDL */  PERFORM update_Column_Translation_From_AD_Element(542464)
;

-- 2025-01-09T17:58:14.675Z
/* DDL */ PERFORM public.db_alter_table('C_PurchaseCandidate','ALTER TABLE public.C_PurchaseCandidate ADD COLUMN Price_UOM_ID NUMERIC(10)')
;

-- 2025-01-09T17:58:14.777Z
ALTER TABLE C_PurchaseCandidate ADD CONSTRAINT PriceUOM_CPurchaseCandidate FOREIGN KEY (Price_UOM_ID) REFERENCES public.C_UOM DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_PurchaseCandidate.Price_UOM_ID
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2025-01-09T18:16:10.974Z
UPDATE AD_Column SET ReadOnlyLogic='@IsManualPrice@=N',Updated=TO_TIMESTAMP('2025-01-09 18:16:10.974000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=589574
;

END IF;
END
$$;


DO
$$
    DECLARE
        column_id numeric;
BEGIN
        column_id := (SELECT ad_column_id from AD_Column where columnname = 'Price_UOM_ID' and ad_table_id = 540861);

-- Field: Bestelldisposition -> Bestelldisposition -> Preiseinheit
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- Field: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> Preiseinheit
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2025-01-09T17:58:49.528Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,column_id,734644,0,540894,TO_TIMESTAMP('2025-01-09 17:58:49.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.purchasecandidate','Y','N','N','N','N','N','N','N','Preiseinheit',TO_TIMESTAMP('2025-01-09 17:58:49.299000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-01-09T17:58:49.534Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=734644 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-01-09T17:58:49.538Z
/* DDL */  PERFORM update_FieldTranslation_From_AD_Name_Element(542464)
;

-- 2025-01-09T17:58:49.553Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=734644
;

-- 2025-01-09T17:58:49.558Z
/* DDL */ PERFORM AD_Element_Link_Create_Missing_Field(734644)
;

-- UI Element: Bestelldisposition -> Bestelldisposition.Preiseinheit
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- UI Element: Bestelldisposition(540375,de.metas.purchasecandidate) -> Bestelldisposition(540894,de.metas.purchasecandidate) -> main -> 10 -> price.Preiseinheit
-- Column: C_PurchaseCandidate.Price_UOM_ID
-- 2025-01-09T17:59:32.610Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,734644,0,540894,627788,545480,'F',TO_TIMESTAMP('2025-01-09 17:59:32.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Preiseinheit',60,0,0,TO_TIMESTAMP('2025-01-09 17:59:32.419000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


END
$$;

