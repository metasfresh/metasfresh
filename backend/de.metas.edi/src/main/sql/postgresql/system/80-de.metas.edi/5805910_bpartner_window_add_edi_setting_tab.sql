-- 2026-06-03T08:00:00Z
-- Tab name element: "EDI-Einstellungen" / "EDI Settings"
-- AD_Element_ID=584936 (From ID Server)
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,584936 /*From ID Server*/,0,TO_TIMESTAMP('2026-06-03 08:00:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.esb.edi','Y','EDI-Einstellungen','EDI-Einstellungen',TO_TIMESTAMP('2026-06-03 08:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID,CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Element_ID=584936
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- en_US translation
UPDATE AD_Element_Trl
SET Name='EDI Settings', PrintName='EDI Settings', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 08:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584936 AND AD_Language='en_US'
;

-- de_CH: same wording as de_DE (no ß)
UPDATE AD_Element_Trl
SET Name='EDI-Einstellungen', PrintName='EDI-Einstellungen', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 08:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584936 AND AD_Language='de_CH'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584936,'en_US')
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584936,'de_CH')
;

-- Tab: Geschäftspartner(123,D) -> EDI-Einstellungen
-- Table: C_BPartner_EDI_Setting (AD_Table_ID=542610), TabLevel=1
-- AD_Column_ID=2893 = C_BPartner_ID on the parent C_BPartner header tab (tab 220)
-- SeqNo=300 (after existing max 290)
-- AD_Tab_ID=549287 (From ID Server)
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,
  AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,
  IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,
  IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,
  IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,
  IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,2893,584936,0,549287 /*From ID Server*/,542610,123,
  'Y',TO_TIMESTAMP('2026-06-03 08:00:20','YYYY-MM-DD HH24:MI:SS'),100,NULL,'de.metas.esb.edi','N',NULL,'N',
  'A','EdiSettingBPartner','Y','N','Y',
  'Y','N','N','N','Y','Y',
  'N','N','N','Y','Y',
  'Y','N','N',0,'EDI-Einstellungen','N',300,1,
  TO_TIMESTAMP('2026-06-03 08:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;

INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID,CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Tab t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Tab_ID=549287
  AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

/* DDL */ SELECT update_tab_translation_from_ad_element(584936)
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Tab(549287)
;

-- Set IsIdentifier='Y' on C_BPartner_ID column of C_BPartner_EDI_Setting (AD_Column_ID=592678)
-- Required: every table used as a lookup target must have at least one IsIdentifier='Y' column
UPDATE AD_Column
SET IsIdentifier='Y', SeqNo=10,
    Updated=TO_TIMESTAMP('2026-06-03 08:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Column_ID=592678
;

-- Field: C_BPartner_Location_ID (optional location filter)
-- AD_Field_ID=780666 (From ID Server), AD_Column_ID=592679, AD_Element_ID=189
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592679,780666 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:01:00','YYYY-MM-DD HH24:MI:SS'),100,'',10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2026-06-03 08:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780666
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(189)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780666
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780666)
;

-- Field: IsEdiDesadvRecipient
-- AD_Field_ID=780667 (From ID Server), AD_Column_ID=592680, AD_Element_ID=577426
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592680,780667 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:01:10','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Erhält EDI-DESADV',TO_TIMESTAMP('2026-06-03 08:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780667
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(577426)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780667
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780667)
;

-- Field: EdiDesadvRecipientGLN
-- AD_Field_ID=780668 (From ID Server), AD_Column_ID=592681, AD_Element_ID=542001
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592681,780668 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:01:20','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-ID des DESADV-Empfängers',TO_TIMESTAMP('2026-06-03 08:01:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780668
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(542001)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780668
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780668)
;

-- Field: EdiDESADVSendingMode
-- AD_Field_ID=780669 (From ID Server), AD_Column_ID=592682, AD_Element_ID=584485
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592682,780669 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-DESADV Sendemodus',TO_TIMESTAMP('2026-06-03 08:01:30','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780669
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584485)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780669
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780669)
;

-- Field: EdiDESADV_ExternalSystem_Config_ID
-- AD_Field_ID=780670 (From ID Server), AD_Column_ID=592683, AD_Element_ID=584488
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592683,780670 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:01:40','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-DESADV Externes System Config',TO_TIMESTAMP('2026-06-03 08:01:40','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780670
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584488)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780670
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780670)
;

-- Field: EdiDESADVDefaultItemCapacity
-- AD_Field_ID=780671 (From ID Server), AD_Column_ID=592684, AD_Element_ID=542978
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592684,780671 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:01:50','YYYY-MM-DD HH24:MI:SS'),100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',14,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','"CU pro TU" bei unbestimmter Verpackungskapazität',TO_TIMESTAMP('2026-06-03 08:01:50','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780671
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(542978)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780671
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780671)
;

-- Field: IsEdiInvoicRecipient
-- AD_Field_ID=780672 (From ID Server), AD_Column_ID=592685, AD_Element_ID=542000
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592685,780672 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:02:00','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Erhält EDI-INVOIC',TO_TIMESTAMP('2026-06-03 08:02:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780672
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(542000)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780672
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780672)
;

-- Field: EdiInvoicRecipientGLN
-- AD_Field_ID=780673 (From ID Server), AD_Column_ID=592686, AD_Element_ID=578054
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592686,780673 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:02:10','YYYY-MM-DD HH24:MI:SS'),100,'',255,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-ID des INVOIC-Empfängers',TO_TIMESTAMP('2026-06-03 08:02:10','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780673
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(578054)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780673
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780673)
;

-- Field: EdiINVOICSendingMode
-- AD_Field_ID=780674 (From ID Server), AD_Column_ID=592687, AD_Element_ID=584486
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592687,780674 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:02:20','YYYY-MM-DD HH24:MI:SS'),100,1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-INVOIC Sendemodus',TO_TIMESTAMP('2026-06-03 08:02:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780674
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584486)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780674
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780674)
;

-- Field: EdiINVOIC_ExternalSystem_Config_ID
-- AD_Field_ID=780675 (From ID Server), AD_Column_ID=592688, AD_Element_ID=584487
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592688,780675 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 08:02:30','YYYY-MM-DD HH24:MI:SS'),100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-INVOIC Externes System Config',TO_TIMESTAMP('2026-06-03 08:02:30','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780675
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(584487)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780675
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780675)
;

-- UI Section
-- AD_UI_Section_ID=547807 (From ID Server)
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549287,547807 /*From ID Server*/,TO_TIMESTAMP('2026-06-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-06-03 08:03:00','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID,Description,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_UI_Section t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_UI_Section_ID=547807
  AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Left column (SeqNo=10) — holds primary + DESADV + INVOIC groups
-- AD_UI_Column_ID=549535 (From ID Server)
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549535 /*From ID Server*/,547807,TO_TIMESTAMP('2026-06-03 08:03:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-06-03 08:03:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Right column (SeqNo=20) — holds flags group (design rules: first right-column group must be 'flags')
-- AD_UI_Column_ID=549536 (From ID Server)
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549536 /*From ID Server*/,547807,TO_TIMESTAMP('2026-06-03 08:03:20','YYYY-MM-DD HH24:MI:SS'),100,'Y',20,TO_TIMESTAMP('2026-06-03 08:03:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Left column: primary group (top-left, UIStyle='primary' — exactly one per tab)
-- Holds C_BPartner_Location_ID as the location context field
-- AD_UI_ElementGroup_ID=555417 (From ID Server)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549535,555417 /*From ID Server*/,TO_TIMESTAMP('2026-06-03 08:03:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2026-06-03 08:03:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Left column: DESADV element group (SeqNo=20)
-- AD_UI_ElementGroup_ID=555418 (From ID Server)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549535,555418 /*From ID Server*/,TO_TIMESTAMP('2026-06-03 08:03:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','DESADV',20,NULL,TO_TIMESTAMP('2026-06-03 08:03:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Left column: INVOIC element group (SeqNo=30)
-- AD_UI_ElementGroup_ID=555419 (From ID Server)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549535,555419 /*From ID Server*/,TO_TIMESTAMP('2026-06-03 08:03:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','INVOIC',30,NULL,TO_TIMESTAMP('2026-06-03 08:03:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Right column: flags group (first right-column group must be named 'flags' per design rules)
-- AD_UI_ElementGroup_ID=555420 (From ID Server)
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549536,555420 /*From ID Server*/,TO_TIMESTAMP('2026-06-03 08:04:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','flags',10,NULL,TO_TIMESTAMP('2026-06-03 08:04:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: primary group — C_BPartner_Location_ID (Standort)
-- AD_UI_Element_ID=651966 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780666,0,549287,555417,651966 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:04:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Standort',10,10,0,TO_TIMESTAMP('2026-06-03 08:04:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: DESADV group — IsEdiDesadvRecipient
-- AD_UI_Element_ID=651967 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780667,0,549287,555418,651967 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:04:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Erhält EDI-DESADV',10,20,0,TO_TIMESTAMP('2026-06-03 08:04:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: DESADV group — EdiDesadvRecipientGLN
-- AD_UI_Element_ID=651968 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780668,0,549287,555418,651968 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:04:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'EDI-ID des DESADV-Empfängers',20,30,0,TO_TIMESTAMP('2026-06-03 08:04:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: DESADV group — EdiDESADVSendingMode
-- AD_UI_Element_ID=651969 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780669,0,549287,555418,651969 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:04:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'EDI-DESADV Sendemodus',30,40,0,TO_TIMESTAMP('2026-06-03 08:04:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: DESADV group — EdiDESADV_ExternalSystem_Config_ID
-- AD_UI_Element_ID=651970 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780670,0,549287,555418,651970 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:04:50','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'EDI-DESADV Externes System Config',40,50,0,TO_TIMESTAMP('2026-06-03 08:04:50','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: DESADV group — EdiDESADVDefaultItemCapacity
-- AD_UI_Element_ID=651971 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780671,0,549287,555418,651971 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:05:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'"CU pro TU" bei unbestimmter Verpackungskapazität',50,60,0,TO_TIMESTAMP('2026-06-03 08:05:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: INVOIC group — IsEdiInvoicRecipient
-- AD_UI_Element_ID=651972 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780672,0,549287,555419,651972 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:05:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Erhält EDI-INVOIC',10,70,0,TO_TIMESTAMP('2026-06-03 08:05:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: INVOIC group — EdiInvoicRecipientGLN
-- AD_UI_Element_ID=651973 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780673,0,549287,555419,651973 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:05:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'EDI-ID des INVOIC-Empfängers',20,80,0,TO_TIMESTAMP('2026-06-03 08:05:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: INVOIC group — EdiINVOICSendingMode
-- AD_UI_Element_ID=651974 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780674,0,549287,555419,651974 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:05:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'EDI-INVOIC Sendemodus',30,90,0,TO_TIMESTAMP('2026-06-03 08:05:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: INVOIC group — EdiINVOIC_ExternalSystem_Config_ID
-- AD_UI_Element_ID=651975 (From ID Server)
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780675,0,549287,555419,651975 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 08:05:40','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'EDI-INVOIC Externes System Config',40,100,0,TO_TIMESTAMP('2026-06-03 08:05:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================
-- Fix 1: AD_Field + AD_UI_Element for IsActive in flags group (555420, SeqNo=10)
-- AD_Field_ID=780676, AD_Column_ID=592673 (IsActive on C_BPartner_EDI_Setting), AD_Element_ID=348
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592673,780676 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 09:00:00','YYYY-MM-DD HH24:MI:SS'),100,'',1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-06-03 09:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780676
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(348)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780676
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780676)
;

-- UI Element: flags group — IsActive (SeqNo=10)
-- AD_UI_Element_ID=651976
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780676,0,549287,555420,651976 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 09:00:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Aktiv',10,10,0,TO_TIMESTAMP('2026-06-03 09:00:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================
-- Fix 1 cont.: AD_Field + AD_UI_Element for AD_Org_ID in flags group (555420, SeqNo=20)
-- AD_Field_ID=780677, AD_Column_ID=592672 (AD_Org_ID on C_BPartner_EDI_Setting), AD_Element_ID=113
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592672,780677 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 09:00:20','YYYY-MM-DD HH24:MI:SS'),100,'',14,'de.metas.esb.edi','Y','N','N','N','N','N','Y','N','Sektion',TO_TIMESTAMP('2026-06-03 09:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780677
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(113)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780677
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780677)
;

-- UI Element: flags group — AD_Org_ID (SeqNo=20)
-- AD_UI_Element_ID=651977
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780677,0,549287,555420,651977 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 09:00:30','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Sektion',20,20,0,TO_TIMESTAMP('2026-06-03 09:00:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================
-- Fix 2: AD_Field + AD_UI_Element for C_BPartner_ID in primary group (555417, SeqNo=5)
-- AD_Field_ID=780678, AD_Column_ID=592678 (C_BPartner_ID on C_BPartner_EDI_Setting), AD_Element_ID=187
-- IsReadOnly='Y' — mandatory parent FK
-- ============================================================
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,592678,780678 /*From ID Server*/,0,549287,TO_TIMESTAMP('2026-06-03 09:01:00','YYYY-MM-DD HH24:MI:SS'),100,'',14,'de.metas.esb.edi','Y','N','N','N','N','N','Y','N','Geschäftspartner',TO_TIMESTAMP('2026-06-03 09:01:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID,Description,Help,Name,IsTranslated,AD_Client_ID,AD_Org_ID,Created,CreatedBy,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name,'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.CreatedBy,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y')
  AND t.AD_Field_ID=780678
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(187)
;
DELETE FROM AD_Element_Link WHERE AD_Field_ID=780678
;
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(780678)
;

-- UI Element: primary group — C_BPartner_ID (SeqNo=5, before Standort at SeqNo=10)
-- AD_UI_Element_ID=651978
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,780678,0,549287,555417,651978 /*From ID Server*/,'F',TO_TIMESTAMP('2026-06-03 09:01:10','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N',0,'Geschäftspartner',5,5,0,TO_TIMESTAMP('2026-06-03 09:01:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- ============================================================
-- Fix 3: Correct en_US translations for 4 AD_Elements with German/wrong text
-- ============================================================

-- Element 542001 EdiDesadvRecipientGLN: de="EDI-ID des DESADV-Empfängers" → en="EDI ID of the DESADV Recipient"
UPDATE AD_Element_Trl
SET Name='EDI ID of the DESADV Recipient', PrintName='EDI ID of the DESADV Recipient', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 09:02:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=542001 AND AD_Language='en_US'
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542001,'en_US')
;

-- Element 578054 EdiInvoicRecipientGLN: de="EDI-ID des INVOIC-Empfängers" → en="EDI ID of the INVOIC Recipient"
UPDATE AD_Element_Trl
SET Name='EDI ID of the INVOIC Recipient', PrintName='EDI ID of the INVOIC Recipient', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 09:02:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=578054 AND AD_Language='en_US'
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(578054,'en_US')
;

-- Element 542978 EdiDESADVDefaultItemCapacity: de='"CU pro TU" bei unbestimmter Verpackungskapazität'
--   → en='"CU per TU" for Undefined Packing Capacity'
UPDATE AD_Element_Trl
SET Name='"CU per TU" for Undefined Packing Capacity', PrintName='"CU per TU" for Undefined Packing Capacity', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 09:02:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=542978 AND AD_Language='en_US'
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542978,'en_US')
;

-- Element 584486 EdiINVOICSendingMode: fix missing space "EDI-INVOICSending Mode" → "EDI-INVOIC Sending Mode"
UPDATE AD_Element_Trl
SET Name='EDI-INVOIC Sending Mode', PrintName='EDI-INVOIC Sending Mode', IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-06-03 09:02:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID=584486 AND AD_Language='en_US'
;
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584486,'en_US')
;

-- ============================================================
-- Propagate en_US translations from AD_Element_Trl to AD_Field_Trl for all fields on this tab
-- (covers original fields + the 3 new fields added by fixes above)
-- ============================================================
UPDATE AD_Field_Trl ft
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-06-03 09:03:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
FROM AD_Field f
JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID AND et.AD_Language = 'en_US'
WHERE ft.AD_Field_ID = f.AD_Field_ID
  AND f.AD_Tab_ID = 549287
  AND ft.AD_Language = 'en_US'
  AND et.IsTranslated = 'Y'
;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
