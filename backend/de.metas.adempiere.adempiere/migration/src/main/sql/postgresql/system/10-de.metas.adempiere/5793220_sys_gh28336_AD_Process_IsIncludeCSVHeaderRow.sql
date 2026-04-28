-- gh28336: Add IsIncludeCSVHeaderRow flag to AD_Process
-- Controls whether CSV exports include a header row with column names.
-- Default 'Y' for backward compatibility. Set to 'N' for Intrastat export.

-- AD_Element for IsIncludeCSVHeaderRow
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Description,Help,Updated,UpdatedBy)
VALUES (0,584646,0,'IsIncludeCSVHeaderRow',TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100,'D','Y',
        'CSV-Kopfzeile einschließen','CSV-Kopfzeile einschließen',
        'Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',
        'Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',
        TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,Name,PrintName,Description,Help,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,584646,t.Name,t.PrintName,t.Description,t.Help,'N',0,0,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584646
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- de_DE (base language): German
UPDATE AD_Element_Trl SET
    Name='CSV-Kopfzeile einschließen',
    PrintName='CSV-Kopfzeile einschließen',
    Description='Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',
    Help='Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',
    IsTranslated='N',
    Updated=TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Element_ID=584646 AND AD_Language='de_DE'
;

/* DDL */ select update_ad_element_on_ad_element_trl_update(584646,'de_DE')
;
/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584646,'de_DE')
;

-- de_CH: same as de_DE
UPDATE AD_Element_Trl SET
    Name='CSV-Kopfzeile einschließen',
    PrintName='CSV-Kopfzeile einschließen',
    Description='Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',
    Help='Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',
    IsTranslated='N',
    Updated=TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Element_ID=584646 AND AD_Language='de_CH'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584646,'de_CH')
;

-- en_US: English
UPDATE AD_Element_Trl SET
    Name='Include CSV Header Row',
    PrintName='Include CSV Header Row',
    Description='If enabled, the first row of the CSV file will contain column headers.',
    Help='If enabled, the first row of the CSV file will contain column headers.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),UpdatedBy=100
WHERE AD_Element_ID=584646 AND AD_Language='en_US'
;

/* DDL */ select update_TRL_Tables_On_AD_Element_TRL_Update(584646,'en_US')
;


-- Column: AD_Process.IsIncludeCSVHeaderRow
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592207,584646,0,20,284,'XX','IsIncludeCSVHeaderRow',TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100,'N','Y','Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.','D',0,1,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','Y','N',0,'CSV-Kopfzeile einschließen','NP',0,0,TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592207
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */ select update_Column_Translation_From_AD_Element(584646)
;


-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> CSV-Kopfzeile einschließen
-- Column: AD_Process.IsIncludeCSVHeaderRow
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592207,774879,0,245,0,TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100,'Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.',0,'@SpreadsheetFormat@=csv','D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'CSV-Kopfzeile einschließen',0,316,352,0,1,1,TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=774879
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */ select update_FieldTranslation_From_AD_Name_Element(584646)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=774879
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(774879)
;


-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.IsIncludeCSVHeaderRow
-- Column: AD_Process.IsIncludeCSVHeaderRow
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,774879,0,245,541397,648527,'F',TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100,'Wenn aktiviert, enthält die erste Zeile der CSV-Datei die Spaltenüberschriften.','Y','N','N','Y','N','N','N',0,'IsIncludeCSVHeaderRow',36,0,0,TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'),100)
;


-- DDL: Add the physical column
ALTER TABLE ad_process ADD COLUMN IsIncludeCSVHeaderRow char(1) DEFAULT 'Y' CHECK (IsIncludeCSVHeaderRow IN ('Y','N')) NOT NULL;


-- Set Intrastat_Export process to suppress CSV header
UPDATE AD_Process SET IsIncludeCSVHeaderRow='N', Updated=TO_TIMESTAMP('2026-03-09 16:00','YYYY-MM-DD HH24:MI'), UpdatedBy=100 WHERE AD_Process_ID=585508;
