-- Run mode: SWING_CLIENT

-- 2026-02-09T14:40:35.162Z
UPDATE AD_Element SET EntityType='D',Updated=TO_TIMESTAMP('2026-02-09 14:40:35.160000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543884
;

-- 2026-02-09T14:40:35.217Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543884,'de_DE')
;

-- Element: CSVFieldQuote
-- 2026-02-09T14:41:00.486Z
UPDATE AD_Element_Trl SET Description='Zeichen zur Umschließung von Feldwerten im CSV-Export (z.B. Anführungszeichen). Leer lassen, um Werte ohne Textbegrenzung zu exportieren.', Name='CSV-Textbegrenzungszeichen', PrintName='CSV-Textbegrenzungszeichen',Updated=TO_TIMESTAMP('2026-02-09 14:41:00.486000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543884 AND AD_Language='de_CH'
;

-- 2026-02-09T14:41:00.491Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_CH' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-09T14:41:01.078Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543884,'de_CH')
;

-- Element: CSVFieldQuote
-- 2026-02-09T14:41:02.595Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2026-02-09 14:41:02.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543884 AND AD_Language='de_CH'
;

-- 2026-02-09T14:41:02.601Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543884,'de_CH')
;

-- Element: CSVFieldQuote
-- 2026-02-09T14:41:25.564Z
UPDATE AD_Element_Trl SET Description='Character used to enclose field values in CSV export (e.g. double quote). Leave empty to export values without text qualification.', IsTranslated='Y', Name='CSV Text Qualifier', PrintName='CSV Text Qualifier',Updated=TO_TIMESTAMP('2026-02-09 14:41:25.564000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543884 AND AD_Language='en_US'
;

-- 2026-02-09T14:41:25.566Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-09T14:41:25.917Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543884,'en_US')
;

-- Element: CSVFieldQuote
-- 2026-02-09T14:41:45.936Z
UPDATE AD_Element_Trl SET Description='Zeichen zur Umschließung von Feldwerten im CSV-Export (z.B. Anführungszeichen). Leer lassen, um Werte ohne Textbegrenzung zu exportieren.', IsTranslated='Y', Name='CSV-Textbegrenzungszeichen', PrintName='CSV-Textbegrenzungszeichen',Updated=TO_TIMESTAMP('2026-02-09 14:41:45.936000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=543884 AND AD_Language='de_DE'
;

-- 2026-02-09T14:41:45.939Z
UPDATE AD_Element base SET Description=trl.Description, Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='de_DE' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-09T14:41:47.329Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(543884,'de_DE')
;

-- 2026-02-09T14:41:47.331Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(543884,'de_DE')
;

-- Column: AD_Process.CSVFieldQuote
-- 2026-02-09T14:42:04.265Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,DefaultValue,Description,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,591964,543884,0,10,284,'XX','CSVFieldQuote',TO_TIMESTAMP('2026-02-09 14:42:04.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','"','Zeichen zur Umschließung von Feldwerten im CSV-Export (z.B. Anführungszeichen). Leer lassen, um Werte ohne Textbegrenzung zu exportieren.','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,'CSV-Textbegrenzungszeichen',0,0,TO_TIMESTAMP('2026-02-09 14:42:04.082000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-02-09T14:42:04.270Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=591964 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-02-09T14:42:04.274Z
/* DDL */  select update_Column_Translation_From_AD_Element(543884)
;

-- Field: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> CSV-Textbegrenzungszeichen
-- Column: AD_Process.CSVFieldQuote
-- 2026-02-09T14:43:15.264Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591964,772009,0,245,0,TO_TIMESTAMP('2026-02-09 14:43:15.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeichen zur Umschließung von Feldwerten im CSV-Export (z.B. Anführungszeichen). Leer lassen, um Werte ohne Textbegrenzung zu exportieren.',0,'@SpreadsheetFormat@=csv','D',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'CSV-Textbegrenzungszeichen',0,314,350,0,1,1,TO_TIMESTAMP('2026-02-09 14:43:15.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-09T14:43:15.268Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772009 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-09T14:43:15.272Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543884)
;

-- 2026-02-09T14:43:15.299Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772009
;

-- 2026-02-09T14:43:15.308Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772009)
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.CSV Field Delimiter
-- Column: AD_Process.CSVFieldDelimiter
-- 2026-02-09T14:44:20.161Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,650311,0,245,541397,646916,'F',TO_TIMESTAMP('2026-02-09 14:44:19.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'To insert [Tab] character you need to copy tab-whitespace from NotePad or other text editor ','Y','N','N','Y','N','N','N',0,'CSV Field Delimiter',32,0,0,TO_TIMESTAMP('2026-02-09 14:44:19.859000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.CSVFieldQuote
-- Column: AD_Process.CSVFieldQuote
-- 2026-02-09T14:44:40.359Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772009,0,245,541397,646917,'F',TO_TIMESTAMP('2026-02-09 14:44:40.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zeichen zur Umschließung von Feldwerten im CSV-Export (z.B. Anführungszeichen). Leer lassen, um Werte ohne Textbegrenzung zu exportieren.','Y','N','N','Y','N','N','N',0,'CSVFieldQuote',80,0,0,TO_TIMESTAMP('2026-02-09 14:44:40.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Bericht & Prozess(165,D) -> Bericht & Prozess(245,D) -> main -> 10 -> description.CSVFieldQuote
-- Column: AD_Process.CSVFieldQuote
-- 2026-02-09T14:44:46.404Z
UPDATE AD_UI_Element SET SeqNo=34,Updated=TO_TIMESTAMP('2026-02-09 14:44:46.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646917
;


/* DDL */
alter table ad_process add CSVFieldQuote varchar(25) default '"';

