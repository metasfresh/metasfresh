-- Column: S_TimeBooking.C_Activity_ID
-- 2022-08-29T06:46:46.430Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,ReadOnlyLogic,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584231,1005,0,19,541443,'C_Activity_ID','(SELECT s.C_Activity_ID from S_Issue s where s.S_Issue_ID=S_TimeBooking.S_Issue_ID)',TO_TIMESTAMP('2022-08-29 09:46:46','YYYY-MM-DD HH24:MI:SS'),100,'N','Kostenstelle','de.metas.serviceprovider',0,10,'Erfassung der zugehörigen Kostenstelle','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Kostenstelle','1=1',0,0,TO_TIMESTAMP('2022-08-29 09:46:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-29T06:46:46.438Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584231 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-29T06:46:46.488Z
/* DDL */  select update_Column_Translation_From_AD_Element(1005) 
;

-- Field: Time Booking(540907,de.metas.serviceprovider) -> S_TimeBooking(542445,de.metas.serviceprovider) -> Kostenstelle
-- Column: S_TimeBooking.C_Activity_ID
-- 2022-08-29T06:47:49.447Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584231,705610,0,542445,TO_TIMESTAMP('2022-08-29 09:47:49','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle',10,'de.metas.serviceprovider','Erfassung der zugehörigen Kostenstelle','Y','N','N','N','N','N','N','N','Kostenstelle',TO_TIMESTAMP('2022-08-29 09:47:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-29T06:47:49.453Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705610 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-29T06:47:49.458Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1005) 
;

-- 2022-08-29T06:47:49.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705610
;

-- 2022-08-29T06:47:49.567Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705610)
;

-- UI Element: Time Booking(540907,de.metas.serviceprovider) -> S_TimeBooking(542445,de.metas.serviceprovider) -> main -> 10 -> default.Kostenstelle
-- Column: S_TimeBooking.C_Activity_ID
-- 2022-08-29T06:48:18.334Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Element_ID,AD_UI_ElementGroup_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayed_SideList,IsDisplayedGrid,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNo_SideList,SeqNoGrid,Updated,UpdatedBy) VALUES (0,705610,0,542445,612275,543654,'F',TO_TIMESTAMP('2022-08-29 09:48:18','YYYY-MM-DD HH24:MI:SS'),100,'Kostenstelle','Erfassung der zugehörigen Kostenstelle','Y','N','N','Y','N','N','N',0,'Kostenstelle',70,0,0,TO_TIMESTAMP('2022-08-29 09:48:18','YYYY-MM-DD HH24:MI:SS'),100)
;

