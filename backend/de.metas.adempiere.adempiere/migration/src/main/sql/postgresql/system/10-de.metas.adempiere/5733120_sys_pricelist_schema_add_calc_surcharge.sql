-- Column: M_DiscountSchema.M_DiscountSchema_Calculated_Surcharge_ID
-- Column: M_DiscountSchema.M_DiscountSchema_Calculated_Surcharge_ID
-- 2024-09-12T08:12:39.375Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588978,583250,0,19,475,'M_DiscountSchema_Calculated_Surcharge_ID',TO_TIMESTAMP('2024-09-12 08:12:39.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Price Schema - Calculated Surcharge',0,0,TO_TIMESTAMP('2024-09-12 08:12:39.238000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2024-09-12T08:12:39.378Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=588978 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-12T08:12:39.382Z
/* DDL */  select update_Column_Translation_From_AD_Element(583250)
;

-- 2024-09-12T08:12:42.974Z
/* DDL */ SELECT public.db_alter_table('M_DiscountSchema','ALTER TABLE public.M_DiscountSchema ADD COLUMN M_DiscountSchema_Calculated_Surcharge_ID NUMERIC(10)')
;

-- 2024-09-12T08:12:43.028Z
ALTER TABLE M_DiscountSchema ADD CONSTRAINT MDiscountSchemaCalculatedSurcharge_MDiscountSchema FOREIGN KEY (M_DiscountSchema_Calculated_Surcharge_ID) REFERENCES public.M_DiscountSchema_Calculated_Surcharge DEFERRABLE INITIALLY DEFERRED
;

-- Field: Preislisten-Schema -> Preislisten-Schema -> Price Schema - Calculated Surcharge
-- Column: M_DiscountSchema.M_DiscountSchema_Calculated_Surcharge_ID
-- Field: Preislisten-Schema(337,D) -> Preislisten-Schema(675,D) -> Price Schema - Calculated Surcharge
-- Column: M_DiscountSchema.M_DiscountSchema_Calculated_Surcharge_ID
-- 2024-09-12T08:13:06.099Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588978,729881,0,675,TO_TIMESTAMP('2024-09-12 08:13:05.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Price Schema - Calculated Surcharge',TO_TIMESTAMP('2024-09-12 08:13:05.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2024-09-12T08:13:06.101Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=729881 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-12T08:13:06.103Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583250)
;

-- 2024-09-12T08:13:06.107Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729881
;

-- 2024-09-12T08:13:06.107Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729881)
;

-- UI Element: Preislisten-Schema -> Preislisten-Schema.Price Schema - Calculated Surcharge
-- Column: M_DiscountSchema.M_DiscountSchema_Calculated_Surcharge_ID
-- UI Element: Preislisten-Schema(337,D) -> Preislisten-Schema(675,D) -> main -> 10 -> description.Price Schema - Calculated Surcharge
-- Column: M_DiscountSchema.M_DiscountSchema_Calculated_Surcharge_ID
-- 2024-09-12T08:18:23.355Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729881,0,675,540845,625335,'F',TO_TIMESTAMP('2024-09-12 08:18:23.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Price Schema - Calculated Surcharge',20,0,0,TO_TIMESTAMP('2024-09-12 08:18:23.168000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

