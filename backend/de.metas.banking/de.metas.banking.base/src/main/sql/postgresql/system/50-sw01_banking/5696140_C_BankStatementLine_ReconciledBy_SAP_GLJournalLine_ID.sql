-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T06:24:43.887Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587138,582564,0,30,541803,393,'XX','ReconciledBy_SAP_GLJournal_ID',TO_TIMESTAMP('2023-07-19 09:24:43','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reconciled by GL Journal',0,0,TO_TIMESTAMP('2023-07-19 09:24:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T06:24:43.897Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587138 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T06:24:44.861Z
/* DDL */  select update_Column_Translation_From_AD_Element(582564) 
;

-- 2023-07-19T06:24:47.303Z
/* DDL */ SELECT public.db_alter_table('C_BankStatementLine','ALTER TABLE public.C_BankStatementLine ADD COLUMN ReconciledBy_SAP_GLJournal_ID NUMERIC(10)')
;

-- 2023-07-19T06:24:47.674Z
ALTER TABLE C_BankStatementLine ADD CONSTRAINT ReconciledBySAPGLJournal_CBankStatementLine FOREIGN KEY (ReconciledBy_SAP_GLJournal_ID) REFERENCES public.SAP_GLJournal DEFERRABLE INITIALLY DEFERRED
;

-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:59:22.544Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,587139,582565,0,30,541700,393,'XX','ReconciledBy_SAP_GLJournalLine_ID',TO_TIMESTAMP('2023-07-19 09:59:20','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Reconciled by GL Journal Line',0,0,TO_TIMESTAMP('2023-07-19 09:59:20','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-07-19T06:59:22.546Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=587139 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-07-19T06:59:23.083Z
/* DDL */  select update_Column_Translation_From_AD_Element(582565) 
;

-- 2023-07-19T06:59:23.915Z
/* DDL */ SELECT public.db_alter_table('C_BankStatementLine','ALTER TABLE public.C_BankStatementLine ADD COLUMN ReconciledBy_SAP_GLJournalLine_ID NUMERIC(10)')
;

-- 2023-07-19T06:59:24.084Z
ALTER TABLE C_BankStatementLine ADD CONSTRAINT ReconciledBySAPGLJournalLine_CBankStatementLine FOREIGN KEY (ReconciledBy_SAP_GLJournalLine_ID) REFERENCES public.SAP_GLJournalLine DEFERRABLE INITIALLY DEFERRED
;

-- Field: Bank Statement(194,D) -> Statement Line(329,D) -> Reconciled by GL Journal
-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T06:59:47.973Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587138,716710,0,329,TO_TIMESTAMP('2023-07-19 09:59:45','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Reconciled by GL Journal',TO_TIMESTAMP('2023-07-19 09:59:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T06:59:47.975Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716710 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T06:59:47.979Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582564) 
;

-- 2023-07-19T06:59:47.983Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716710
;

-- 2023-07-19T06:59:47.984Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716710)
;

-- Field: Bank Statement(194,D) -> Statement Line(329,D) -> Reconciled by GL Journal Line
-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T06:59:50.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,587139,716711,0,329,TO_TIMESTAMP('2023-07-19 09:59:47','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Reconciled by GL Journal Line',TO_TIMESTAMP('2023-07-19 09:59:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-07-19T06:59:50.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=716711 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-07-19T06:59:50.377Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582565) 
;

-- 2023-07-19T06:59:50.381Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=716711
;

-- 2023-07-19T06:59:50.382Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(716711)
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Reconciled by GL Journal
-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T07:00:39.310Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716710,0,329,540272,618291,'F',TO_TIMESTAMP('2023-07-19 10:00:37','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Reconciled by GL Journal',610,0,0,TO_TIMESTAMP('2023-07-19 10:00:37','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Bank Statement(194,D) -> Statement Line(329,D) -> main -> 10 -> default.Reconciled by GL Journal Line
-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T07:00:47.973Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,716711,0,329,540272,618292,'F',TO_TIMESTAMP('2023-07-19 10:00:45','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Reconciled by GL Journal Line',620,0,0,TO_TIMESTAMP('2023-07-19 10:00:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Field: Bank Statement(194,D) -> Statement Line(329,D) -> Reconciled by GL Journal
-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournal_ID
-- 2023-07-19T07:01:27.720Z
UPDATE AD_Field SET DisplayLogic='@IsReconciled/N@=Y & @ReconciledBy_SAP_GLJournal_ID/0@>0', IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-07-19 10:01:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716710
;

-- Field: Bank Statement(194,D) -> Statement Line(329,D) -> Reconciled by GL Journal Line
-- Column: C_BankStatementLine.ReconciledBy_SAP_GLJournalLine_ID
-- 2023-07-19T07:01:36.132Z
UPDATE AD_Field SET DisplayLogic='@IsReconciled/N@=Y & @ReconciledBy_SAP_GLJournalLine_ID/0@>0',Updated=TO_TIMESTAMP('2023-07-19 10:01:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=716711
;

