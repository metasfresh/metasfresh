-- Column: C_POS.C_Workplace_ID
-- Column: C_POS.C_Workplace_ID
-- 2024-10-08T20:31:55.280Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,589281,582772,0,30,748,'XX','C_Workplace_ID',TO_TIMESTAMP('2024-10-08 23:31:54','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Arbeitsplatz',0,0,TO_TIMESTAMP('2024-10-08 23:31:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2024-10-08T20:31:55.292Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=589281 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-10-08T20:31:55.342Z
/* DDL */  select update_Column_Translation_From_AD_Element(582772) 
;

-- 2024-10-08T20:31:56.827Z
/* DDL */ SELECT public.db_alter_table('C_POS','ALTER TABLE public.C_POS ADD COLUMN C_Workplace_ID NUMERIC(10)')
;

-- 2024-10-08T20:31:56.844Z
ALTER TABLE C_POS ADD CONSTRAINT CWorkplace_CPOS FOREIGN KEY (C_Workplace_ID) REFERENCES public.C_Workplace DEFERRABLE INITIALLY DEFERRED
;

-- Field: POS-Terminal -> POS-Terminal -> Arbeitsplatz
-- Column: C_POS.C_Workplace_ID
-- Field: POS-Terminal(338,D) -> POS-Terminal(676,D) -> Arbeitsplatz
-- Column: C_POS.C_Workplace_ID
-- 2024-10-08T20:32:21.598Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,589281,731879,0,676,TO_TIMESTAMP('2024-10-08 23:32:21','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Arbeitsplatz',TO_TIMESTAMP('2024-10-08 23:32:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2024-10-08T20:32:21.602Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=731879 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-10-08T20:32:21.606Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582772) 
;

-- 2024-10-08T20:32:21.628Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=731879
;

-- 2024-10-08T20:32:21.632Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(731879)
;

-- UI Element: POS-Terminal -> POS-Terminal.Arbeitsplatz
-- Column: C_POS.C_Workplace_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Arbeitsplatz
-- Column: C_POS.C_Workplace_ID
-- 2024-10-08T20:35:15.353Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,731879,0,676,551965,626161,'F',TO_TIMESTAMP('2024-10-08 23:35:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Arbeitsplatz',150,0,0,TO_TIMESTAMP('2024-10-08 23:35:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: POS-Terminal -> POS-Terminal.Arbeitsplatz
-- Column: C_POS.C_Workplace_ID
-- UI Element: POS-Terminal(338,D) -> POS-Terminal(676,D) -> main -> 10 -> default.Arbeitsplatz
-- Column: C_POS.C_Workplace_ID
-- 2024-10-08T20:35:56.978Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2024-10-08 23:35:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=626161
;

