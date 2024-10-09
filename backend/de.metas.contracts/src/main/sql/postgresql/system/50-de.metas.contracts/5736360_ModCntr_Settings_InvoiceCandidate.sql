-- Run mode: SWING_CLIENT

-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T10:34:30.879Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589284,582425,0,30,540270,'ModCntr_Settings_ID',TO_TIMESTAMP('2024-10-09 13:34:30.617','YYYY-MM-DD HH24:MI:SS.US'),100,'N','de.metas.invoicecandidate',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Einstellungen für modulare Verträge',0,0,TO_TIMESTAMP('2024-10-09 13:34:30.617','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-10-09T10:34:30.890Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589284 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-09T10:34:30.922Z
/* DDL */  select update_Column_Translation_From_AD_Element(582425)
;

-- 2024-10-09T10:34:32.190Z
/* DDL */ SELECT public.db_alter_table('C_Invoice_Candidate','ALTER TABLE public.C_Invoice_Candidate ADD COLUMN ModCntr_Settings_ID NUMERIC(10)')
;

-- 2024-10-09T10:34:32.649Z
ALTER TABLE C_Invoice_Candidate ADD CONSTRAINT ModCntrSettings_CInvoiceCandidate FOREIGN KEY (ModCntr_Settings_ID) REFERENCES public.ModCntr_Settings DEFERRABLE INITIALLY DEFERRED
;








	-- Field: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> Einstellungen für modulare Verträge
	-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
	-- 2024-10-09T10:46:36.970Z
	INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589284,731884,0,540279,TO_TIMESTAMP('2024-10-09 13:46:36.82','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Einstellungen für modulare Verträge',TO_TIMESTAMP('2024-10-09 13:46:36.82','YYYY-MM-DD HH24:MI:SS.US'),100)
	;

	-- 2024-10-09T10:46:36.971Z
	INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731884 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
	;

	-- 2024-10-09T10:46:36.973Z
	/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425)
	;

	-- 2024-10-09T10:46:36.975Z
	DELETE FROM AD_Element_Link WHERE AD_Field_ID=731884
	;

	-- 2024-10-09T10:46:36.976Z
	/* DDL */ select AD_Element_Link_Create_Missing_Field(731884)
	;




-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T11:05:03.911Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731884,0,540279,540056,626170,'F',TO_TIMESTAMP('2024-10-09 14:05:03.784','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Einstellungen für modulare Verträge',1080,0,0,TO_TIMESTAMP('2024-10-09 14:05:03.784','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsdisposition_OLD(540092,de.metas.invoicecandidate) -> Rechnungskandidaten(540279,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T11:05:29.454Z
UPDATE AD_UI_Element SET IsAdvancedField='Y', SeqNo=1075,Updated=TO_TIMESTAMP('2024-10-09 14:05:29.454','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626170
;





-- Field: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> Einstellungen für modulare Verträge
-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T11:05:52.046Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589284,731889,0,543052,TO_TIMESTAMP('2024-10-09 14:05:51.923','YYYY-MM-DD HH24:MI:SS.US'),100,10,'de.metas.invoicecandidate','Y','N','N','N','N','N','N','N','Einstellungen für modulare Verträge',TO_TIMESTAMP('2024-10-09 14:05:51.923','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-10-09T11:05:52.047Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=731889 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-09T11:05:52.048Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582425)
;

-- 2024-10-09T11:05:52.051Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731889
;

-- 2024-10-09T11:05:52.052Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731889)
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T11:07:21.140Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731889,0,543052,544370,626171,'F',TO_TIMESTAMP('2024-10-09 14:07:21.025','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Einstellungen für modulare Verträge',1050,0,0,TO_TIMESTAMP('2024-10-09 14:07:21.025','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Rechnungsdisposition Einkauf_OLD(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> advanced edit -> 10 -> advanced edit.Einstellungen für modulare Verträge
-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T11:07:37.473Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2024-10-09 14:07:37.473','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=626171
;


-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T14:49:35.212Z
UPDATE AD_Column SET ReadOnlyLogic='1=1',Updated=TO_TIMESTAMP('2024-10-09 17:49:35.211','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589284
;

-- Column: C_Invoice_Candidate.ModCntr_Settings_ID
-- 2024-10-09T15:10:18.409Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2024-10-09 18:10:18.409','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=589284
;

