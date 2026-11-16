-- AD_OrgInfo.ReportBackground_Image_ID: optional full-page image per organisation, printed behind the content of every page
-- of the document reports (e.g. as a watermark). Same mechanism as Logo_ID and ReportBottom_Logo_ID:
-- the report service serves every image column of AD_OrgInfo to Jasper as "de/metas/org/images/<ColumnName>.png".
-- Field placement: window Organisation (110), tab Org Info (170), group "default" (540463), right after "Report Bottom Logo".
-- IDs from the central ID server (2026-11-02): AD_Element 585573; AD_Column 593766; AD_Field 786276; AD_UI_Element 655460.

-- ============================================================================
-- AD_Element ReportBackground_Image_ID: 585573
-- ============================================================================

-- 2026-11-02 11:00:00
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,Help,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585573 /*From ID Server*/,0,'ReportBackground_Image_ID',TO_TIMESTAMP('2026-11-02 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Ganzseitiges Bild, das auf jeder Seite der Belege hinter dem Inhalt gedruckt wird, z.B. als Wasserzeichen.','D','Das Bild wird in Originalgröße auf die ganze Seite gelegt. Empfohlen: A4-Format im RGB-Farbmodus (CMYK-Bilder können nicht gedruckt werden), ca. 150 dpi und eine kleine Dateigröße, damit die PDF-Dateien für den E-Mail-Versand klein bleiben. Ist kein Bild hinterlegt, wird kein Hintergrund gedruckt. Der Hintergrund wird nur in den Berichten gedruckt, die dieses Bild verwenden.','Y','Berichtshintergrund','Berichtshintergrund',TO_TIMESTAMP('2026-11-02 11:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-11-02 11:00:00 (seed _Trl rows for all system/base languages)
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585573 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2026-11-02 11:00:12 (en_US)
UPDATE AD_Element_Trl SET Name='Report Background Image', PrintName='Report Background Image', Description='Full-page image that is printed behind the content on every page of the documents, e.g. as a watermark.', Help='The image is placed over the whole page. Recommended: A4 format in RGB colour mode (CMYK images cannot be printed), about 150 dpi and a small file size, so that the PDF files stay small for sending by e-mail. If no image is set, no background is printed. The background is only printed in the reports that use this image.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-02 11:00:12.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585573 AND AD_Language='en_US'
;

-- 2026-11-02 11:00:12
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585573,'en_US')
;

-- 2026-11-02 11:00:13 (de_DE: same text as the base, flip IsTranslated)
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-02 11:00:13.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585573 AND AD_Language='de_DE'
;

-- 2026-11-02 11:00:14 (de_CH: same text as the base, but without ß)
UPDATE AD_Element_Trl SET Help='Das Bild wird in Originalgrösse auf die ganze Seite gelegt. Empfohlen: A4-Format im RGB-Farbmodus (CMYK-Bilder können nicht gedruckt werden), ca. 150 dpi und eine kleine Dateigrösse, damit die PDF-Dateien für den E-Mail-Versand klein bleiben. Ist kein Bild hinterlegt, wird kein Hintergrund gedruckt. Der Hintergrund wird nur in den Berichten gedruckt, die dieses Bild verwenden.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-11-02 11:00:14.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585573 AND AD_Language='de_CH'
;

-- 2026-11-02 11:00:14
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585573,'de_CH')
;

-- ============================================================================
-- AD_Column AD_OrgInfo.ReportBackground_Image_ID (593766), Image (32), nullable
-- ============================================================================

-- 2026-11-02 11:01:00
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,PersonalDataCategory,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) SELECT 0,593766 /*From ID Server*/,585573,0,32,t.AD_Table_ID,'XX','ReportBackground_Image_ID',TO_TIMESTAMP('2026-11-02 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N',e.Description,'D',0,10,e.Help,'Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N',0,e.Name,'NP',0,0,TO_TIMESTAMP('2026-11-02 11:01:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0 FROM AD_Table t, AD_Element e WHERE t.TableName='AD_OrgInfo' AND e.AD_Element_ID=585573
;

-- 2026-11-02 11:01:00 (seed _Trl)
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593766 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-11-02 11:01:30
/* DDL */ select update_Column_Translation_From_AD_Element(585573)
;

-- ============================================================================
-- Physical DB column + foreign key to AD_Image
-- ============================================================================

-- 2026-11-02 11:02:00
/* DDL */ SELECT public.db_alter_table('AD_OrgInfo','ALTER TABLE public.AD_OrgInfo ADD COLUMN ReportBackground_Image_ID NUMERIC(10)')
;

-- 2026-11-02 11:02:00
ALTER TABLE AD_OrgInfo ADD CONSTRAINT ReportBackgroundImage_ADOrgInfo FOREIGN KEY (ReportBackground_Image_ID) REFERENCES public.AD_Image DEFERRABLE INITIALLY DEFERRED
;

-- ============================================================================
-- AD_Field on Organisation (110) -> Org Info (170)
-- ============================================================================

-- 2026-11-02 11:03:00
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) SELECT 0,593766,786276 /*From ID Server*/,0,170,TO_TIMESTAMP('2026-11-02 11:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,e.Description,10,'D',e.Help,'Y','Y','N','N','N','N','N','N',e.Name,175,0,TO_TIMESTAMP('2026-11-02 11:03:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100 FROM AD_Element e WHERE e.AD_Element_ID=585573
;

-- 2026-11-02 11:03:00 (seed _Trl)
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=786276 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-11-02 11:04:00
/* DDL */ select update_FieldTranslation_From_AD_Name_Element(585573)
;

-- 2026-11-02 11:04:01
DELETE FROM AD_Element_Link WHERE AD_Field_ID=786276
;

-- 2026-11-02 11:04:02
/* DDL */ select AD_Element_Link_Create_Missing_Field(786276)
;

-- ============================================================================
-- AD_UI_Element: group "default" (540463). SeqNo 0 like its siblings; ordered by id it lands right after
-- "Report Bottom Logo" (585258). Grid: between Report Bottom Logo (200) and Portal domain name (210).
-- ============================================================================

-- 2026-11-02 11:05:00
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,786276,0,170,540463,655460 /*From ID Server*/,'F',TO_TIMESTAMP('2026-11-02 11:05:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','Berichtshintergrund',0,205,0,TO_TIMESTAMP('2026-11-02 11:05:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
