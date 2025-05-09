-- Column: C_Payment.Original_Payment_ID
-- 2025-05-07T08:44:06.260Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589955,583612,0,30,343,335,'Original_Payment_ID',TO_TIMESTAMP('2025-05-07 11:44:06.129','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ursprungszahlung',0,0,TO_TIMESTAMP('2025-05-07 11:44:06.129','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-07T08:44:06.262Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589955 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-07T08:44:06.265Z
/* DDL */  select update_Column_Translation_From_AD_Element(583612)
;

-- 2025-05-07T08:44:07.226Z
/* DDL */ SELECT public.db_alter_table('C_Payment','ALTER TABLE public.C_Payment ADD COLUMN Original_Payment_ID NUMERIC(10)')
;

-- 2025-05-07T08:44:07.432Z
ALTER TABLE C_Payment ADD CONSTRAINT OriginalPayment_CPayment FOREIGN KEY (Original_Payment_ID) REFERENCES public.C_Payment DEFERRABLE INITIALLY DEFERRED
;

-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Ursprungszahlung
-- Column: C_Payment.Original_Payment_ID
-- 2025-05-07T08:44:32.208Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589955,742005,0,330,TO_TIMESTAMP('2025-05-07 11:44:32.083','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Ursprungszahlung',TO_TIMESTAMP('2025-05-07 11:44:32.083','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-07T08:44:32.209Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742005 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T08:44:32.211Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583612)
;

-- 2025-05-07T08:44:32.214Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742005
;

-- 2025-05-07T08:44:32.215Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742005)
;

-- Field: Zahlung(195,D) -> Zahlung(330,D) -> Ursprungszahlung
-- Column: C_Payment.Original_Payment_ID
-- 2025-05-07T08:44:48.976Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2025-05-07 11:44:48.976','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Field_ID=742005
;

-- UI Element: Zahlung(195,D) -> Zahlung(330,D) -> main -> 10 -> order invoice.Ursprungszahlung
-- Column: C_Payment.Original_Payment_ID
-- 2025-05-07T08:45:45.600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742005,0,330,540955,631418,'F',TO_TIMESTAMP('2025-05-07 11:45:45.37','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Ursprungszahlung',60,0,0,TO_TIMESTAMP('2025-05-07 11:45:45.37','YYYY-MM-DD HH24:MI:SS.US'),100)
;

