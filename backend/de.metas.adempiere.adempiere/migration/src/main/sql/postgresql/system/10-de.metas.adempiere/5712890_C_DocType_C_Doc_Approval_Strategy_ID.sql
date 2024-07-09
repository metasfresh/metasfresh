-- Column: C_DocType.C_Doc_Approval_Strategy_ID
-- 2023-12-07T10:13:43.494797900Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587714,582841,0,30,217,'C_Doc_Approval_Strategy_ID',TO_TIMESTAMP('2023-12-07 12:13:43.096','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Document Approval Strategy',0,0,TO_TIMESTAMP('2023-12-07 12:13:43.096','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2023-12-07T10:13:43.497468700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587714 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-12-07T10:13:44.065431700Z
/* DDL */  select update_Column_Translation_From_AD_Element(582841) 
;

-- 2023-12-07T10:13:46.654270300Z
/* DDL */ SELECT public.db_alter_table('C_DocType','ALTER TABLE public.C_DocType ADD COLUMN C_Doc_Approval_Strategy_ID NUMERIC(10)')
;

-- 2023-12-07T10:13:47.501087100Z
ALTER TABLE C_DocType ADD CONSTRAINT CDocApprovalStrategy_CDocType FOREIGN KEY (C_Doc_Approval_Strategy_ID) REFERENCES public.C_Doc_Approval_Strategy DEFERRABLE INITIALLY DEFERRED
;

-- Field: Belegart(135,D) -> Belegart(167,D) -> Document Approval Strategy
-- Column: C_DocType.C_Doc_Approval_Strategy_ID
-- 2023-12-07T10:14:12.322194200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587714,723120,0,167,TO_TIMESTAMP('2023-12-07 12:14:12.182','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Document Approval Strategy',TO_TIMESTAMP('2023-12-07 12:14:12.182','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-12-07T10:14:12.324801900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=723120 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-12-07T10:14:12.326878600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582841) 
;

-- 2023-12-07T10:14:12.330498600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=723120
;

-- 2023-12-07T10:14:12.331540300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(723120)
;

-- UI Column: Belegart(135,D) -> Belegart(167,D) -> main -> 20
-- UI Element Group: approval
-- 2023-12-07T10:15:31.467973500Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,540253,551397,TO_TIMESTAMP('2023-12-07 12:15:31.324','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','approval',15,TO_TIMESTAMP('2023-12-07 12:15:31.324','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Belegart(135,D) -> Belegart(167,D) -> main -> 20 -> approval.Document Approval Strategy
-- Column: C_DocType.C_Doc_Approval_Strategy_ID
-- 2023-12-07T10:15:50.428699100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,723120,0,167,551397,621969,'F',TO_TIMESTAMP('2023-12-07 12:15:50.272','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Document Approval Strategy',10,0,0,TO_TIMESTAMP('2023-12-07 12:15:50.272','YYYY-MM-DD HH24:MI:SS.US'),100)
;

