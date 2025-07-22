-- Run mode: SWING_CLIENT

-- Column: C_BPartner_Location.M_Shipper_ID
-- 2025-07-22T07:03:43.924Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,590571,455,0,19,293,'XX','M_Shipper_ID',TO_TIMESTAMP('2025-07-22 07:03:43.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Methode oder Art der Warenlieferung','D',0,10,'"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Lieferweg',0,0,TO_TIMESTAMP('2025-07-22 07:03:43.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2025-07-22T07:03:43.930Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=590571 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-07-22T07:03:43.962Z
/* DDL */  select update_Column_Translation_From_AD_Element(455)
;

-- 2025-07-22T07:03:52.855Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE public.C_BPartner_Location ADD COLUMN M_Shipper_ID NUMERIC(10)')
;

-- 2025-07-22T07:03:53.860Z
ALTER TABLE C_BPartner_Location ADD CONSTRAINT MShipper_CBPartnerLocation FOREIGN KEY (M_Shipper_ID) REFERENCES public.M_Shipper DEFERRABLE INITIALLY DEFERRED
;

-- Field: Geschäftspartner(123,D) -> Adresse(222,D) -> Lieferweg
-- Column: C_BPartner_Location.M_Shipper_ID
-- 2025-07-22T07:05:22.497Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,590571,750390,0,222,0,TO_TIMESTAMP('2025-07-22 07:05:22.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',0,'D',0,'"Lieferweg" bezeichnet die Art der Warenlieferung.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Lieferweg',0,0,200,0,1,1,TO_TIMESTAMP('2025-07-22 07:05:22.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-07-22T07:05:22.502Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=750390 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-07-22T07:05:22.506Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2025-07-22T07:05:22.555Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=750390
;

-- 2025-07-22T07:05:22.561Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(750390)
;

-- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Lieferweg
-- Column: C_BPartner_Location.M_Shipper_ID
-- 2025-07-22T07:07:36.578Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,750390,0,222,1000034,635301,'F',TO_TIMESTAMP('2025-07-22 07:07:36.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','Y','N','N','N',0,'Lieferweg',190,0,0,TO_TIMESTAMP('2025-07-22 07:07:36.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Adresse(222,D) -> main -> 10 -> default.Lieferweg
-- Column: C_BPartner_Location.M_Shipper_ID
-- 2025-07-22T07:08:17.695Z
UPDATE AD_UI_Element SET SeqNo=125,Updated=TO_TIMESTAMP('2025-07-22 07:08:17.695000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=635301
;


-------------------------------------------
-------------------------------------------
-------------------------------------------
-------------------------------------------




