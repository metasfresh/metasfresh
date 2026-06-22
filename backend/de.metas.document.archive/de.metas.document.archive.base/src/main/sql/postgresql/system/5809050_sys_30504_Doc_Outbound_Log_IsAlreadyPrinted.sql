-- 30504: Add a "Gedruckt"/"Printed" Yes/No filter+grid column to window "Ausgehende Belege".
--
-- New virtual ColumnSQL Yes/No column C_Doc_Outbound_Log.IsAlreadyPrinted:
--   'Y' when the document has >=1 print line, 'N' otherwise. Same definition as the existing
--   virtual PrintCount column (col 548164) — IsAlreadyPrinted = (PrintCount > 0) expressed as Yes/No.
-- Surfaced as a grid column right of "Anz. gedruckt" (SeqNoGrid=105, between PrintCount@100 and
--   DateLastPrint@110) and as a default-filter selection column (IsSelectionColumn='Y').
-- Standard core window 540170 / tab 540474 / table 540453 (de.metas.document.archive) — verified
--   against a customer-faithful dt204 DB: no override window for 540170, so a single core script.
-- Virtual column => no physical DDL. No AD_SQLColumn_SourceTableColumn row, matching the identical
--   sibling PrintCount (cacheinvalidateparent='Y' handles parent cache).
-- A NEW AD_Element is used (not the standard IsPrinted/element 399 "andrucken", which is intended in
--   report/Jasper windows 217/218/249 + AD_PrintFormatItem).

-- Element: IsAlreadyPrinted
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585032 /*From ID Server*/,0,'IsAlreadyPrinted',TO_TIMESTAMP('2026-06-19 12:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Gedruckt','Gedruckt',TO_TIMESTAMP('2026-06-19 12:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585032
AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- de_DE / de_CH = "Gedruckt"
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Gedruckt', PrintName='Gedruckt',Updated=TO_TIMESTAMP('2026-06-19 12:00:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585032 AND AD_Language IN ('de_DE','de_CH')
;

-- en_US = "Printed"
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Printed', PrintName='Printed',Updated=TO_TIMESTAMP('2026-06-19 12:00:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=585032 AND AD_Language='en_US'
;

-- Column: C_Doc_Outbound_Log.IsAlreadyPrinted  (virtual Yes/No, selection/filter column)
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsAutocomplete,IsCalculated,IsEncrypted,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version)
VALUES (0,592859 /*From ID Server*/,585032,0,20,540453,'IsAlreadyPrinted','(CASE WHEN (select COUNT(*) from C_Doc_Outbound_Log_Line where C_Doc_Outbound_Log_Line.Action = ''print'' AND C_Doc_Outbound_Log_Line.C_Doc_Outbound_Log_ID = C_Doc_Outbound_Log.C_Doc_Outbound_Log_ID) > 0 THEN ''Y'' ELSE ''N'' END)',TO_TIMESTAMP('2026-06-19 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,'N','de.metas.document.archive',1,'Y','Y','N','N','N','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','Gedruckt','NP',10,0,TO_TIMESTAMP('2026-06-19 12:01:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=592859
AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

/* DDL */  select update_Column_Translation_From_AD_Element(585032)
;

-- Field: Ausgehende Belege -> Gedruckt  (grid + filter only, not single-record form)
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy)
VALUES (0,592859,781231 /*From ID Server*/,0,540474,0,TO_TIMESTAMP('2026-06-19 12:02:00','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.document.archive',0,'Y','N','N','Y','N','N','N','Y','N','Gedruckt',55,105,1,1,TO_TIMESTAMP('2026-06-19 12:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=781231
AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

/* DDL */  select update_FieldTranslation_From_AD_Name_Element(585032)
;

DELETE FROM AD_Element_Link WHERE AD_Field_ID=781231
;

/* DDL */ select AD_Element_Link_Create_Missing_Field(781231)
;

-- UI Element: place in element group 540352 on tab 540474, grid right of "Anz. gedruckt"
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize)
VALUES (0,781231,0,540474,540352,652343 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-19 12:02:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','Y','N','N','Gedruckt',15,105,0,TO_TIMESTAMP('2026-06-19 12:02:30','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- Propagate all element translations (incl. en_US "Printed") to the column/field _Trl rows.
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(585032)
;
