-- Column: C_BPartner.M_SectionCode_ID
-- 2022-08-19T17:27:44.172Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,584125,581238,0,30,291,'M_SectionCode_ID',TO_TIMESTAMP('2022-08-19 18:27:42','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Section Code',0,0,TO_TIMESTAMP('2022-08-19 18:27:42','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2022-08-19T17:27:44.275Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=584125 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2022-08-19T17:27:44.494Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- 2022-08-19T17:28:28.935Z
/* DDL */ SELECT public.db_alter_table('C_BPartner','ALTER TABLE public.C_BPartner ADD COLUMN M_SectionCode_ID NUMERIC(10)')
;

-- 2022-08-19T17:28:30.103Z
ALTER TABLE C_BPartner ADD CONSTRAINT MSectionCode_CBPartner FOREIGN KEY (M_SectionCode_ID) REFERENCES public.M_SectionCode DEFERRABLE INITIALLY DEFERRED
;

-- Field: Business Partner_OLD -> Business Partner -> Section Code
-- Column: C_BPartner.M_SectionCode_ID
-- 2022-08-19T17:35:18.574Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,584125,705348,0,220,TO_TIMESTAMP('2022-08-19 18:35:17','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Code',TO_TIMESTAMP('2022-08-19 18:35:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-19T17:35:18.670Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=705348 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2022-08-19T17:35:18.765Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2022-08-19T17:35:18.868Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=705348
;

-- 2022-08-19T17:35:18.960Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(705348)
;

-- 2022-08-19T17:37:11.409Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,1000003,549829,TO_TIMESTAMP('2022-08-19 18:37:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','Section',30,TO_TIMESTAMP('2022-08-19 18:37:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2022-08-19T17:37:24.233Z
UPDATE AD_UI_ElementGroup SET SeqNo=18,Updated=TO_TIMESTAMP('2022-08-19 18:37:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_ElementGroup_ID=549829
;

-- UI Element: Business Partner_OLD -> Business Partner.Section Code
-- Column: C_BPartner.M_SectionCode_ID
-- 2022-08-19T17:37:52.615Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,705348,0,220,549829,612137,'F',TO_TIMESTAMP('2022-08-19 18:37:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Section Code',10,0,0,TO_TIMESTAMP('2022-08-19 18:37:51','YYYY-MM-DD HH24:MI:SS'),100)
;

