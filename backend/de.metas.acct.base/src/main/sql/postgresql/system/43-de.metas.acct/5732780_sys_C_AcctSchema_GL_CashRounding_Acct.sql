
-- 2024-09-09T16:23:56.777Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583246,0,'CashRounding_Acct',TO_TIMESTAMP('2024-09-09 19:23:56.498','YYYY-MM-DD HH24:MI:SS.US'),100,'de.metas.acct','Y','Cash Rounding Account','Cash Rounding Account',TO_TIMESTAMP('2024-09-09 19:23:56.498','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-09T16:23:56.781Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583246 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2024-09-09T16:24:17.630Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2024-09-09 19:24:17.628','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Element_ID=583246
;

-- 2024-09-09T16:24:17.636Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583246,'de_DE')
;

-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-09T16:24:32.435Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,588947,583246,0,25,266,'CashRounding_Acct',TO_TIMESTAMP('2024-09-09 19:24:32.316','YYYY-MM-DD HH24:MI:SS.US'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Cash Rounding Account',0,0,TO_TIMESTAMP('2024-09-09 19:24:32.316','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2024-09-09T16:24:32.436Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=588947 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2024-09-09T16:24:32.439Z
/* DDL */  select update_Column_Translation_From_AD_Element(583246)
;

-- 2024-09-09T16:24:33.362Z
/* DDL */ SELECT public.db_alter_table('C_AcctSchema_GL','ALTER TABLE public.C_AcctSchema_GL ADD COLUMN CashRounding_Acct NUMERIC(10)')
;

-- 2024-09-09T16:24:33.370Z
ALTER TABLE C_AcctSchema_GL ADD CONSTRAINT CashRoundingA_CAcctSchemaGL FOREIGN KEY (CashRounding_Acct) REFERENCES public.C_ValidCombination DEFERRABLE INITIALLY DEFERRED
;






-- Field: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> Cash Rounding Account
-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-09T16:26:35.830Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,588947,729855,0,200,TO_TIMESTAMP('2024-09-09 19:26:35.704','YYYY-MM-DD HH24:MI:SS.US'),100,10,'D','Y','Y','N','N','N','N','N','Cash Rounding Account',TO_TIMESTAMP('2024-09-09 19:26:35.704','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2024-09-09T16:26:35.831Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=729855 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2024-09-09T16:26:35.832Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583246)
;

-- 2024-09-09T16:26:35.836Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=729855
;

-- 2024-09-09T16:26:35.837Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(729855)
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Cash Rounding Account
-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-09T16:27:32.901Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,729855,0,200,541333,625311,'F',TO_TIMESTAMP('2024-09-09 19:27:32.657','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Cash Rounding Account',170,0,0,TO_TIMESTAMP('2024-09-09 19:27:32.657','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Cash Rounding Account
-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-09T16:28:25.648Z
UPDATE AD_UI_Element SET SeqNo=115,Updated=TO_TIMESTAMP('2024-09-09 19:28:25.648','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625311
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Cash Rounding Account
-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-09T16:29:09.394Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-09-09 19:29:09.394','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625311
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Aktiv
-- Column: C_AcctSchema_GL.IsActive
-- 2024-09-09T16:29:09.402Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2024-09-09 19:29:09.402','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549949
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Sektion
-- Column: C_AcctSchema_GL.AD_Org_ID
-- 2024-09-09T16:29:09.409Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2024-09-09 19:29:09.409','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549947
;





-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Cash Rounding Account
-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-10T08:32:10.761Z
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2024-09-10 11:32:10.761','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625311
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Cash Rounding Account
-- Column: C_AcctSchema_GL.CashRounding_Acct
-- 2024-09-10T08:32:21.858Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.858','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=625311
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Doppelte Buchführung
-- Column: C_AcctSchema_GL.UseSuspenseBalancing
-- 2024-09-10T08:32:21.864Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.864','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549950
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.CpD-Konto
-- Column: C_AcctSchema_GL.SuspenseBalancing_Acct
-- 2024-09-10T08:32:21.871Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.871','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549951
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.CpD-Fehlerkonto verwenden
-- Column: C_AcctSchema_GL.UseSuspenseError
-- 2024-09-10T08:32:21.877Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.877','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549952
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.CpD-Fehlerkonto
-- Column: C_AcctSchema_GL.SuspenseError_Acct
-- 2024-09-10T08:32:21.884Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.884','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549953
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Währungsunterschiede verbuchen
-- Column: C_AcctSchema_GL.UseCurrencyBalancing
-- 2024-09-10T08:32:21.890Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.89','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549954
;

-- UI Element: Buchführungs-Schema(125,D) -> Hauptbuch(200,D) -> main -> 10 -> default.Konto für Währungsdifferenzen
-- Column: C_AcctSchema_GL.CurrencyBalancing_Acct
-- 2024-09-10T08:32:21.896Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2024-09-10 11:32:21.896','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=549955
;

