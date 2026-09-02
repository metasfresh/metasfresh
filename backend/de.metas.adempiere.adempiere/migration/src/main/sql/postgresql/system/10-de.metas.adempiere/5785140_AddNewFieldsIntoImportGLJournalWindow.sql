-- Run mode: SWING_CLIENT

-- Column: I_GLJournal.CR_Tax_Acct_ID
-- 2026-01-22T15:10:46.423Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591888,542735,0,25,599,'XX','CR_Tax_Acct_ID',TO_TIMESTAMP('2026-01-22 15:10:46.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax account (credit)',0,0,TO_TIMESTAMP('2026-01-22 15:10:46.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-22T15:10:46.425Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-22T15:10:46.447Z
/* DDL */  select update_Column_Translation_From_AD_Element(542735)
;

-- Column: I_GLJournal.DR_Tax_Acct_ID
-- 2026-01-22T15:11:03.065Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591889,542734,0,25,599,'XX','DR_Tax_Acct_ID',TO_TIMESTAMP('2026-01-22 15:11:02.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'Tax account (debit)',0,0,TO_TIMESTAMP('2026-01-22 15:11:02.891000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-01-22T15:11:03.066Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591889 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-01-22T15:11:03.137Z
/* DDL */  select update_Column_Translation_From_AD_Element(542734)
;

-- 2026-01-22T15:11:16.717Z
/* DDL */ SELECT public.db_alter_table('I_GLJournal','ALTER TABLE public.I_GLJournal ADD COLUMN DR_Tax_Acct_ID NUMERIC(10)')
;

-- 2026-01-22T15:11:24.675Z
/* DDL */ SELECT public.db_alter_table('I_GLJournal','ALTER TABLE public.I_GLJournal ADD COLUMN CR_Tax_Acct_ID NUMERIC(10)')
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Probleme
-- Column: I_GLJournal.AD_Issue_ID
-- 2026-01-22T15:12:36.784Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568901,768058,0,508,TO_TIMESTAMP('2026-01-22 15:12:36.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',10,'D','','Y','N','N','N','N','N','N','N','Probleme',TO_TIMESTAMP('2026-01-22 15:12:36.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:36.789Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768058 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:36.794Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2887)
;

-- 2026-01-22T15:12:36.811Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768058
;

-- 2026-01-22T15:12:36.816Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768058)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Import Line No
-- Column: I_GLJournal.I_LineNo
-- 2026-01-22T15:12:36.934Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568902,768059,0,508,TO_TIMESTAMP('2026-01-22 15:12:36.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Import Line No',TO_TIMESTAMP('2026-01-22 15:12:36.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:36.935Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768059 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:36.937Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577116)
;

-- 2026-01-22T15:12:36.940Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768059
;

-- 2026-01-22T15:12:36.941Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768059)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Import Line Content
-- Column: I_GLJournal.I_LineContent
-- 2026-01-22T15:12:37.050Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568903,768060,0,508,TO_TIMESTAMP('2026-01-22 15:12:36.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,4000,'D','Y','N','N','N','N','N','N','N','Import Line Content',TO_TIMESTAMP('2026-01-22 15:12:36.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.052Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768060 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.053Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577115)
;

-- 2026-01-22T15:12:37.055Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768060
;

-- 2026-01-22T15:12:37.056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768060)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Daten Import Verlauf
-- Column: I_GLJournal.C_DataImport_Run_ID
-- 2026-01-22T15:12:37.148Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568904,768061,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Daten Import Verlauf',TO_TIMESTAMP('2026-01-22 15:12:37.058000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.150Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768061 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.151Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577114)
;

-- 2026-01-22T15:12:37.154Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768061
;

-- 2026-01-22T15:12:37.155Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768061)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Daten Import
-- Column: I_GLJournal.C_DataImport_ID
-- 2026-01-22T15:12:37.269Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568905,768062,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Daten Import',TO_TIMESTAMP('2026-01-22 15:12:37.157000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.270Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768062 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.271Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543913)
;

-- 2026-01-22T15:12:37.275Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768062
;

-- 2026-01-22T15:12:37.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768062)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> ActivityValue
-- Column: I_GLJournal.ActivityValue
-- 2026-01-22T15:12:37.392Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591872,768063,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,40,'D','Y','N','N','N','N','N','N','N','ActivityValue',TO_TIMESTAMP('2026-01-22 15:12:37.279000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.394Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768063 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.398Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(53222)
;

-- 2026-01-22T15:12:37.402Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768063
;

-- 2026-01-22T15:12:37.404Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768063)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Summe (Soll)
-- Column: I_GLJournal.DR_TaxTotalAmt
-- 2026-01-22T15:12:37.525Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591873,768064,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Summe (Soll)',TO_TIMESTAMP('2026-01-22 15:12:37.407000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.527Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768064 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.529Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542742)
;

-- 2026-01-22T15:12:37.531Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768064
;

-- 2026-01-22T15:12:37.533Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768064)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Summe (Haben)
-- Column: I_GLJournal.CR_TaxTotalAmt
-- 2026-01-22T15:12:37.635Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591875,768065,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Summe (Haben)',TO_TIMESTAMP('2026-01-22 15:12:37.536000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.637Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768065 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542743)
;

-- 2026-01-22T15:12:37.642Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768065
;

-- 2026-01-22T15:12:37.643Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768065)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Valid Combination Tax From
-- Column: I_GLJournal.C_ValidCombinationTaxFrom_ID
-- 2026-01-22T15:12:37.748Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591878,768066,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Valid Combination Tax From',TO_TIMESTAMP('2026-01-22 15:12:37.644000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.750Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768066 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.751Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584450)
;

-- 2026-01-22T15:12:37.753Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768066
;

-- 2026-01-22T15:12:37.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768066)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Valid Combination Tax To
-- Column: I_GLJournal.C_ValidCombinationTaxTo_ID
-- 2026-01-22T15:12:37.878Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591879,768067,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Valid Combination Tax To',TO_TIMESTAMP('2026-01-22 15:12:37.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.880Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768067 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584451)
;

-- 2026-01-22T15:12:37.883Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768067
;

-- 2026-01-22T15:12:37.884Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768067)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Steuer (Haben)
-- Column: I_GLJournal.CR_Tax_ID
-- 2026-01-22T15:12:37.990Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591882,768068,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerart',10,'D','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','N','N','N','N','N','N','Steuer (Haben)',TO_TIMESTAMP('2026-01-22 15:12:37.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:37.992Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768068 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:37.993Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542741)
;

-- 2026-01-22T15:12:37.996Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768068
;

-- 2026-01-22T15:12:37.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768068)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Steuer (Soll)
-- Column: I_GLJournal.DR_Tax_ID
-- 2026-01-22T15:12:38.102Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591883,768069,0,508,TO_TIMESTAMP('2026-01-22 15:12:37.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Steuerart',10,'D','Steuer bezeichnet die in dieser Dokumentenzeile verwendete Steuerart.','Y','N','N','N','N','N','N','N','Steuer (Soll)',TO_TIMESTAMP('2026-01-22 15:12:37.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768069 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.106Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542740)
;

-- 2026-01-22T15:12:38.108Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768069
;

-- 2026-01-22T15:12:38.109Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768069)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> DR Tax Name
-- Column: I_GLJournal.DR_TaxName
-- 2026-01-22T15:12:38.210Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591884,768070,0,508,TO_TIMESTAMP('2026-01-22 15:12:38.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,200,'D','Y','N','N','N','N','N','N','N','DR Tax Name',TO_TIMESTAMP('2026-01-22 15:12:38.111000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.211Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768070 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.213Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584456)
;

-- 2026-01-22T15:12:38.215Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768070
;

-- 2026-01-22T15:12:38.216Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768070)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> CR Tax Name
-- Column: I_GLJournal.CR_TaxName
-- 2026-01-22T15:12:38.324Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591885,768071,0,508,TO_TIMESTAMP('2026-01-22 15:12:38.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,200,'D','Y','N','N','N','N','N','N','N','CR Tax Name',TO_TIMESTAMP('2026-01-22 15:12:38.218000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.326Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768071 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.328Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584457)
;

-- 2026-01-22T15:12:38.330Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768071
;

-- 2026-01-22T15:12:38.331Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768071)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Steuer Kontoschlüssel von
-- Column: I_GLJournal.TaxAccountValueFrom
-- 2026-01-22T15:12:38.441Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591886,768072,0,508,TO_TIMESTAMP('2026-01-22 15:12:38.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Steuer Kontoschlüssel von',TO_TIMESTAMP('2026-01-22 15:12:38.334000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.443Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768072 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.445Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584458)
;

-- 2026-01-22T15:12:38.446Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768072
;

-- 2026-01-22T15:12:38.448Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768072)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Steuer Kontoschlüssel bis
-- Column: I_GLJournal.TaxAccountValueTo
-- 2026-01-22T15:12:38.555Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591887,768073,0,508,TO_TIMESTAMP('2026-01-22 15:12:38.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Steuer Kontoschlüssel bis',TO_TIMESTAMP('2026-01-22 15:12:38.450000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.557Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768073 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.558Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584459)
;

-- 2026-01-22T15:12:38.561Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768073
;

-- 2026-01-22T15:12:38.561Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768073)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Tax account (credit)
-- Column: I_GLJournal.CR_Tax_Acct_ID
-- 2026-01-22T15:12:38.660Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591888,768074,0,508,TO_TIMESTAMP('2026-01-22 15:12:38.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Tax account (credit)',TO_TIMESTAMP('2026-01-22 15:12:38.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.661Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768074 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.662Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542735)
;

-- 2026-01-22T15:12:38.665Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768074
;

-- 2026-01-22T15:12:38.666Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768074)
;

-- Field: Import - Hauptbuchjournal(278,D) -> Hauptbuch(508,D) -> Tax account (debit)
-- Column: I_GLJournal.DR_Tax_Acct_ID
-- 2026-01-22T15:12:38.775Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591889,768075,0,508,TO_TIMESTAMP('2026-01-22 15:12:38.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Tax account (debit)',TO_TIMESTAMP('2026-01-22 15:12:38.669000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-01-22T15:12:38.776Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=768075 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-01-22T15:12:38.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542734)
;

-- 2026-01-22T15:12:38.781Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=768075
;

-- 2026-01-22T15:12:38.782Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(768075)
;



-- Column: I_GLJournal.CR_TaxTotalAmt
-- 2026-01-23T16:38:27.984Z
UPDATE AD_Column SET IsMandatory='N',Updated=TO_TIMESTAMP('2026-01-23 16:38:27.984000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591875
;

-- 2026-01-23T16:38:36.978Z
INSERT INTO t_alter_column values('i_gljournal','CR_TaxTotalAmt','NUMERIC',null,null)
;

-- 2026-01-23T16:38:36.981Z
INSERT INTO t_alter_column values('i_gljournal','CR_TaxTotalAmt',null,'NULL',null)
;

-- Column: I_GLJournal.DR_Tax_Acct_ID
-- 2026-01-23T16:58:33.850Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=362, AD_Val_Rule_ID=252,Updated=TO_TIMESTAMP('2026-01-23 16:58:33.850000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591889
;

-- 2026-01-23T16:58:36.352Z
INSERT INTO t_alter_column values('i_gljournal','DR_Tax_Acct_ID','NUMERIC(10)',null,null)
;

-- Column: I_GLJournal.CR_Tax_Acct_ID
-- 2026-01-23T16:59:07.156Z
UPDATE AD_Column SET AD_Reference_ID=30, AD_Reference_Value_ID=362,Updated=TO_TIMESTAMP('2026-01-23 16:59:07.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591888
;

-- 2026-01-23T16:59:08.624Z
INSERT INTO t_alter_column values('i_gljournal','CR_Tax_Acct_ID','NUMERIC(10)',null,null)
;

-- Column: I_GLJournal.DR_Tax_Acct_ID
-- 2026-01-23T16:59:28.636Z
UPDATE AD_Column SET AD_Val_Rule_ID=NULL,Updated=TO_TIMESTAMP('2026-01-23 16:59:28.636000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591889
;

-- Column: I_GLJournal.C_ValidCombinationTaxTo_ID
-- 2026-01-23T17:23:00.713Z
UPDATE AD_Column SET AD_Reference_ID=25, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2026-01-23 17:23:00.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591879
;

-- Column: I_GLJournal.C_ValidCombinationTaxFrom_ID
-- 2026-01-23T17:23:19.487Z
UPDATE AD_Column SET AD_Reference_ID=25, AD_Reference_Value_ID=NULL,Updated=TO_TIMESTAMP('2026-01-23 17:23:19.487000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591878
;



------------



-- Process: Import_GLJournal(org.compiere.process.ImportGLJournal)
-- ParameterName: AD_Org_ID
-- 2026-01-23T18:22:46.523Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=329
;

-- 2026-01-23T18:22:46.528Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=329
;
;

-- Process: Import_GLJournal(org.compiere.process.ImportGLJournal)
-- ParameterName: AD_Client_ID
-- 2026-01-23T18:22:57.992Z
DELETE FROM  AD_Process_Para_Trl WHERE AD_Process_Para_ID=328
;

-- 2026-01-23T18:22:57.998Z
DELETE FROM AD_Process_Para WHERE AD_Process_Para_ID=328
;