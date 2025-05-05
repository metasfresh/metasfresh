-- Column: M_ReceiptSchedule.C_BPartner2_ID
-- 2023-03-10T10:11:54.499Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586300,582129,0,30,541252,540524,'C_BPartner2_ID',TO_TIMESTAMP('2023-03-10 12:11:54','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.inoutcandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Business Partner (2)',0,0,TO_TIMESTAMP('2023-03-10 12:11:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-10T10:11:54.501Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=586300 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-10T10:11:54.505Z
/* DDL */  select update_Column_Translation_From_AD_Element(582129) 
;

-- 2023-03-10T10:11:56.909Z
/* DDL */ SELECT public.db_alter_table('M_ReceiptSchedule','ALTER TABLE public.M_ReceiptSchedule ADD COLUMN C_BPartner2_ID NUMERIC(10)')
;

-- 2023-03-10T10:11:57.025Z
ALTER TABLE M_ReceiptSchedule ADD CONSTRAINT CBPartner2_MReceiptSchedule FOREIGN KEY (C_BPartner2_ID) REFERENCES public.C_BPartner DEFERRABLE INITIALLY DEFERRED
;

-- Field: Material Receipt Candidates(540196,de.metas.inoutcandidate) -> Material Receipt Candidates(540526,de.metas.inoutcandidate) -> Business Partner (2)
-- Column: M_ReceiptSchedule.C_BPartner2_ID
-- 2023-03-10T10:12:37.266Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,586300,712826,0,540526,TO_TIMESTAMP('2023-03-10 12:12:37','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.inoutcandidate','Y','N','N','N','N','N','N','N','Business Partner (2)',TO_TIMESTAMP('2023-03-10 12:12:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-10T10:12:37.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=712826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-10T10:12:37.269Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582129) 
;

-- 2023-03-10T10:12:37.272Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712826
;

-- 2023-03-10T10:12:37.274Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712826)
;

-- UI Element: Material Receipt Candidates(540196,de.metas.inoutcandidate) -> Material Receipt Candidates(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Business Partner (2)
-- Column: M_ReceiptSchedule.C_BPartner2_ID
-- 2023-03-10T10:13:24.653Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712826,0,540526,540129,616001,'F',TO_TIMESTAMP('2023-03-10 12:13:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Business Partner (2)',430,0,0,TO_TIMESTAMP('2023-03-10 12:13:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Material Receipt Candidates(540196,de.metas.inoutcandidate) -> Material Receipt Candidates(540526,de.metas.inoutcandidate) -> advanced edit -> 10 -> advanced edit.Business Partner (2)
-- Column: M_ReceiptSchedule.C_BPartner2_ID
-- 2023-03-10T10:13:36.072Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2023-03-10 12:13:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616001
;

-- Field: Material Receipt Candidates(540196,de.metas.inoutcandidate) -> Material Receipt Candidates(540526,de.metas.inoutcandidate) -> Business Partner (2)
-- Column: M_ReceiptSchedule.C_BPartner2_ID
-- 2023-03-10T10:14:01.318Z
UPDATE AD_Field SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-03-10 12:14:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712826
;

