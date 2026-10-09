-- Run mode: SWING_CLIENT

-- Column: C_Dunning_Candidate.C_Dunning_ID
-- 2026-01-05T10:35:15.406Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591821,838,0,19,540396,'C_Dunning_ID','(SELECT C_Dunning_ID from C_DunningLevel where C_DunningLevel_ID = C_Dunning_Candidate.C_DunningLevel_ID)',TO_TIMESTAMP('2026-01-05 11:35:13.95','YYYY-MM-DD HH24:MI:SS.US'),100,'N','Dunning Rules for overdue invoices','de.metas.dunning',0,10,'E','The Dunning indicates the rules and method of dunning for past due payments.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N',0,'Mahnung',0,0,TO_TIMESTAMP('2026-01-05 11:35:13.95','YYYY-MM-DD HH24:MI:SS.US'),100,0)
;

-- 2026-01-05T10:35:15.487Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591821 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-05T10:35:15.633Z
/* DDL */  select update_Column_Translation_From_AD_Element(838)
;

-- Column: C_DunningLevel.C_DunningLevel_ID
-- Source Table: C_DunningLevel
-- 2026-01-05T10:37:05.065Z
INSERT INTO AD_SQLColumn_SourceTableColumn (AD_Client_ID,AD_Column_ID,AD_Org_ID,AD_SQLColumn_SourceTableColumn_ID,AD_Table_ID,Created,CreatedBy,FetchTargetRecordsMethod,IsActive,Link_Column_ID,Source_Column_ID,Source_Table_ID,Updated,UpdatedBy) VALUES (0,3701,0,540187,540396,TO_TIMESTAMP('2026-01-05 11:37:04.186','YYYY-MM-DD HH24:MI:SS.US'),100,'L','Y',547345,547345,331,TO_TIMESTAMP('2026-01-05 11:37:04.186','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- Column: C_Dunning_Candidate.C_Dunning_ID
-- 2026-01-05T10:37:50.670Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2026-01-05 11:37:50.67','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=591821
;

-- Field: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> Mahnung
-- Column: C_Dunning_Candidate.C_Dunning_ID
-- 2026-01-05T10:40:40.816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsForbidNewRecordCreation,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591821,760967,0,540424,0,TO_TIMESTAMP('2026-01-05 11:40:39.319','YYYY-MM-DD HH24:MI:SS.US'),100,'Dunning Rules for overdue invoices',0,'D','The Dunning indicates the rules and method of dunning for past due payments.',0,'Y','Y','Y','N','N','N','N','N','N','Mahnung',0,220,0,1,1,TO_TIMESTAMP('2026-01-05 11:40:39.319','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2026-01-05T10:40:40.883Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=760967 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-05T10:40:40.944Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(838)
;

-- 2026-01-05T10:40:41.018Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=760967
;

-- 2026-01-05T10:40:41.087Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(760967)
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Mahnung
-- Column: C_Dunning_Candidate.C_Dunning_ID
-- 2026-01-05T10:41:54.651Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,760967,0,540424,541192,641306,'F',TO_TIMESTAMP('2026-01-05 11:41:53.795','YYYY-MM-DD HH24:MI:SS.US'),100,'Dunning Rules for overdue invoices','The Dunning indicates the rules and method of dunning for past due payments.','Y','N','N','Y','N','N','N',0,'Mahnung',47,0,0,TO_TIMESTAMP('2026-01-05 11:41:53.795','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Mahnung
-- Column: C_Dunning_Candidate.C_Dunning_ID
-- 2026-01-05T10:42:44.321Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-01-05 11:42:44.321','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=641306
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> default.Mahnstufe
-- Column: C_Dunning_Candidate.C_DunningLevel_ID
-- 2026-01-05T10:42:44.698Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-01-05 11:42:44.698','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548994
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Offener Betrag
-- Column: C_Dunning_Candidate.OpenAmt
-- 2026-01-05T10:42:45.064Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-01-05 11:42:45.064','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548989
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Mahnzins
-- Column: C_Dunning_Candidate.DunningInterestAmt
-- 2026-01-05T10:42:45.433Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-01-05 11:42:45.433','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548990
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Gesamtbetrag
-- Column: C_Dunning_Candidate.TotalAmt
-- 2026-01-05T10:42:45.804Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-01-05 11:42:45.804','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548992
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Währung
-- Column: C_Dunning_Candidate.C_Currency_ID
-- 2026-01-05T10:42:46.175Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-01-05 11:42:46.175','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548993
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 10 -> dunning.Tage fällig
-- Column: C_Dunning_Candidate.DaysDue
-- 2026-01-05T10:42:46.548Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-01-05 11:42:46.547','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548974
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> flags.Verarbeitet
-- Column: C_Dunning_Candidate.Processed
-- 2026-01-05T10:42:46.915Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-01-05 11:42:46.915','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548984
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> flags.Mahnung erstellt
-- Column: C_Dunning_Candidate.IsDunningDocProcessed
-- 2026-01-05T10:42:47.282Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-01-05 11:42:47.282','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548985
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Dunning Date Effective
-- Column: C_Dunning_Candidate.DunningDateEffective
-- 2026-01-05T10:42:47.659Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-01-05 11:42:47.659','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548970
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Dunning Date
-- Column: C_Dunning_Candidate.DunningDate
-- 2026-01-05T10:42:48.033Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-01-05 11:42:48.033','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548969
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> dates.Mahnkarenz
-- Column: C_Dunning_Candidate.DunningGrace
-- 2026-01-05T10:42:48.399Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-01-05 11:42:48.399','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548991
;

-- UI Element: Mahndisposition(540154,de.metas.dunning) -> Mahndatensätze(540424,de.metas.dunning) -> main -> 20 -> org.Sektion
-- Column: C_Dunning_Candidate.AD_Org_ID
-- 2026-01-05T10:42:48.767Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=190,Updated=TO_TIMESTAMP('2026-01-05 11:42:48.767','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=548995
;

-- Column: C_Dunning_Candidate.C_Dunning_ID
-- 2026-01-05T10:44:20.490Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2026-01-05 11:44:20.49','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=591821
;

