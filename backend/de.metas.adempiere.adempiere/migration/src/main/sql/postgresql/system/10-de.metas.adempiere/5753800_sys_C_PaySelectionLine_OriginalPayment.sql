-- 2025-05-07T08:21:32.720Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583612,0,'Original_Payment_ID',TO_TIMESTAMP('2025-05-07 11:21:32.466','YYYY-MM-DD HH24:MI:SS.US'),100,'D','Y','Original Payment','Original Payment',TO_TIMESTAMP('2025-05-07 11:21:32.466','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-07T08:21:32.724Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583612 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: Original_Payment_ID
-- 2025-05-07T08:21:42.390Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ursprungszahlung', PrintName='Ursprungszahlung',Updated=TO_TIMESTAMP('2025-05-07 11:21:42.39','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583612 AND AD_Language='de_DE'
;

-- 2025-05-07T08:21:42.391Z
UPDATE AD_Element SET Name='Ursprungszahlung', PrintName='Ursprungszahlung' WHERE AD_Element_ID=583612
;

-- 2025-05-07T08:21:42.911Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583612,'de_DE')
;

-- 2025-05-07T08:21:42.913Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583612,'de_DE')
;

-- Element: Original_Payment_ID
-- 2025-05-07T08:21:48.084Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Ursprungszahlung', PrintName='Ursprungszahlung',Updated=TO_TIMESTAMP('2025-05-07 11:21:48.084','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583612 AND AD_Language='de_CH'
;

-- 2025-05-07T08:21:48.087Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583612,'de_CH')
;

-- Column: C_PaySelectionLine.Original_Payment_ID
-- 2025-05-07T08:22:20.865Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589954,583612,0,30,343,427,'Original_Payment_ID',TO_TIMESTAMP('2025-05-07 11:22:20.743','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Ursprungszahlung',0,0,TO_TIMESTAMP('2025-05-07 11:22:20.743','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2025-05-07T08:22:20.867Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=589954 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2025-05-07T08:22:20.870Z
/* DDL */  select update_Column_Translation_From_AD_Element(583612)
;

-- 2025-05-07T08:22:21.823Z
/* DDL */ SELECT public.db_alter_table('C_PaySelectionLine','ALTER TABLE public.C_PaySelectionLine ADD COLUMN Original_Payment_ID NUMERIC(10)')
;

-- 2025-05-07T08:22:21.931Z
ALTER TABLE C_PaySelectionLine ADD CONSTRAINT OriginalPayment_CPaySelectionLine FOREIGN KEY (Original_Payment_ID) REFERENCES public.C_Payment DEFERRABLE INITIALLY DEFERRED
;

-- Field: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> Ursprungszahlung
-- Column: C_PaySelectionLine.Original_Payment_ID
-- 2025-05-07T08:30:20.624Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589954,742004,0,353,TO_TIMESTAMP('2025-05-07 11:30:20.504','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','N','N','N','N','N','N','N','Ursprungszahlung',TO_TIMESTAMP('2025-05-07 11:30:20.504','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2025-05-07T08:30:20.625Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=742004 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-05-07T08:30:20.627Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583612)
;

-- 2025-05-07T08:30:20.631Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=742004
;

-- 2025-05-07T08:30:20.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(742004)
;

-- UI Element: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> main -> 10 -> default.Ursprungszahlung
-- Column: C_PaySelectionLine.Original_Payment_ID
-- 2025-05-07T08:31:51.957Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,742004,0,353,541024,631417,'F',TO_TIMESTAMP('2025-05-07 11:31:51.725','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Ursprungszahlung',200,0,0,TO_TIMESTAMP('2025-05-07 11:31:51.725','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> main -> 10 -> default.Ursprungszahlung
-- Column: C_PaySelectionLine.Original_Payment_ID
-- 2025-05-07T08:32:27.449Z
UPDATE AD_UI_Element SET SeqNo=171,Updated=TO_TIMESTAMP('2025-05-07 11:32:27.449','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631417
;

-- UI Element: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> main -> 10 -> default.Ursprungszahlung
-- Column: C_PaySelectionLine.Original_Payment_ID
-- 2025-05-07T08:32:34.516Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2025-05-07 11:32:34.516','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=631417
;

-- UI Element: Zahlung anweisen(206,D) -> Rechnung auswählen(353,D) -> main -> 10 -> default.Sektion
-- Column: C_PaySelectionLine.AD_Org_ID
-- 2025-05-07T08:32:34.524Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2025-05-07 11:32:34.524','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=547752
;

