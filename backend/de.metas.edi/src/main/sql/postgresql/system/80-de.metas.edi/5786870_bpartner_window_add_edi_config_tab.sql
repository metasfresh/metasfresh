-- 2026-02-05T07:15:21.629Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,584497,0,TO_TIMESTAMP('2026-02-05 07:15:21.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','EDI-Konfiguration','EDI-Konfiguration',TO_TIMESTAMP('2026-02-05 07:15:21.426000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:15:21.643Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=584497 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2026-02-05T07:15:47.041Z
UPDATE AD_Element_Trl SET Name='EDI Configuration', PrintName='EDI Configuration',Updated=TO_TIMESTAMP('2026-02-05 07:15:47.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=584497 AND AD_Language='en_US'
;

-- 2026-02-05T07:15:47.042Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2026-02-05T07:15:47.273Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(584497,'en_US')
;

-- Tab: Geschäftspartner(123,D) -> Geschäftspartner
-- Table: C_BPartner
-- 2026-02-05T07:20:02.185Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,Help,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,2893,187,0,548980,291,123,'Y',TO_TIMESTAMP('2026-02-05 07:20:02.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','D','N','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','N','A','EdiBPartnerConfig','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','Y','N','N',0,'Geschäftspartner','N',300,1,TO_TIMESTAMP('2026-02-05 07:20:02.056000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:20:02.190Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548980 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-05T07:20:02.190Z
/* DDL */  select update_tab_translation_from_ad_element(187)
;

-- 2026-02-05T07:20:02.202Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548980)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> Erhält EDI-INVOIC
-- Column: C_BPartner.IsEdiInvoicRecipient
-- 2026-02-05T07:21:32.774Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,548482,771891,0,548980,TO_TIMESTAMP('2026-02-05 07:21:32.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','Erhält EDI-INVOIC',TO_TIMESTAMP('2026-02-05 07:21:32.643000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:32.783Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771891 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:32.786Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542000)
;

-- 2026-02-05T07:21:32.801Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771891
;

-- 2026-02-05T07:21:32.803Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771891)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> EDI-ID des DESADV-Empfängers
-- Column: C_BPartner.EdiDesadvRecipientGLN
-- 2026-02-05T07:21:32.897Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,548483,771892,0,548980,TO_TIMESTAMP('2026-02-05 07:21:32.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-ID des DESADV-Empfängers',TO_TIMESTAMP('2026-02-05 07:21:32.807000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:32.900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771892 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:32.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542001)
;

-- 2026-02-05T07:21:32.910Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771892
;

-- 2026-02-05T07:21:32.912Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771892)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> "CU pro TU" bei unbestimmter Verpackungskapazität
-- Column: C_BPartner.EdiDESADVDefaultItemCapacity
-- 2026-02-05T07:21:33.002Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,553173,771893,0,548980,TO_TIMESTAMP('2026-02-05 07:21:32.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'"CU pro TU"-Wert, den das System in einem DESADV-Dokument ausgeben soll, wenn zum Gebinde in metasfresh keine Gebindekapazität hinterlegt ist.',14,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','"CU pro TU" bei unbestimmter Verpackungskapazität',TO_TIMESTAMP('2026-02-05 07:21:32.914000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:33.010Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771893 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:33.014Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542978)
;

-- 2026-02-05T07:21:33.024Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771893
;

-- 2026-02-05T07:21:33.025Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771893)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> EDI-ID des INVOIC-Empfängers
-- Column: C_BPartner.EdiInvoicRecipientGLN
-- 2026-02-05T07:21:33.125Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,571151,771894,0,548980,TO_TIMESTAMP('2026-02-05 07:21:33.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-ID des INVOIC-Empfängers',TO_TIMESTAMP('2026-02-05 07:21:33.025000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:33.128Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771894 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:33.131Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578054)
;

-- 2026-02-05T07:21:33.135Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771894
;

-- 2026-02-05T07:21:33.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771894)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> EDI-DESADV Sendemodus
-- Column: C_BPartner.EdiDESADVSendingMode
-- 2026-02-05T07:21:33.241Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591909,771895,0,548980,TO_TIMESTAMP('2026-02-05 07:21:33.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-DESADV Sendemodus',TO_TIMESTAMP('2026-02-05 07:21:33.149000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:33.247Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:33.249Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584485)
;

-- 2026-02-05T07:21:33.251Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771895
;

-- 2026-02-05T07:21:33.252Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771895)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> EDI-DESADV Externes System Config
-- Column: C_BPartner.EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-05T07:21:33.337Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591910,771896,0,548980,TO_TIMESTAMP('2026-02-05 07:21:33.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-DESADV Externes System Config',TO_TIMESTAMP('2026-02-05 07:21:33.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:33.338Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:33.339Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584488)
;

-- 2026-02-05T07:21:33.342Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771896
;

-- 2026-02-05T07:21:33.343Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771896)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> EDI-INVOIC Sendemodus
-- Column: C_BPartner.EdiINVOICSendingMode
-- 2026-02-05T07:21:33.445Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591911,771897,0,548980,TO_TIMESTAMP('2026-02-05 07:21:33.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-INVOIC Sendemodus',TO_TIMESTAMP('2026-02-05 07:21:33.345000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:33.454Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:33.456Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584486)
;

-- 2026-02-05T07:21:33.463Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771897
;

-- 2026-02-05T07:21:33.464Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771897)
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> EDI-INVOIC Externes System Config
-- Column: C_BPartner.EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-05T07:21:33.567Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591912,771898,0,548980,TO_TIMESTAMP('2026-02-05 07:21:33.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'de.metas.esb.edi','Y','N','N','N','N','N','N','N','EDI-INVOIC Externes System Config',TO_TIMESTAMP('2026-02-05 07:21:33.466000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:21:33.572Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:21:33.575Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584487)
;

-- 2026-02-05T07:21:33.578Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771898
;

-- 2026-02-05T07:21:33.579Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771898)
;

-- Tab: Geschäftspartner(123,D) -> Geschäftspartner(548980,D)
-- UI Section: main
-- 2026-02-05T07:22:26.759Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548980,547502,TO_TIMESTAMP('2026-02-05 07:22:26.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-05 07:22:26.624000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-05T07:22:26.763Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547502 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> main
-- UI Column: 10
-- 2026-02-05T07:22:34.280Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549161,547502,TO_TIMESTAMP('2026-02-05 07:22:34.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-05 07:22:34.164000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Geschäftspartner(123,D) -> Geschäftspartner(548980,D) -> main -> 10
-- UI Element Group: main
-- 2026-02-05T07:22:46.709Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549161,554789,TO_TIMESTAMP('2026-02-05 07:22:46.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2026-02-05 07:22:46.573000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Tab: Geschäftspartner(123,D) -> EDI-Konfiguration
-- Table: C_BPartner
-- 2026-02-05T07:34:20.950Z
UPDATE AD_Tab SET AD_Element_ID=584497, CommitWarning=NULL, Description=NULL, EntityType='D', Help=NULL, Name='EDI-Konfiguration',Updated=TO_TIMESTAMP('2026-02-05 07:34:20.950000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548980
;

-- 2026-02-05T07:34:20.952Z
UPDATE AD_Tab_Trl trl SET Description=NULL,Help=NULL,Name='EDI-Konfiguration' WHERE AD_Tab_ID=548980 AND AD_Language='de_DE'
;

-- 2026-02-05T07:34:20.953Z
/* DDL */  select update_tab_translation_from_ad_element(584497)
;

-- 2026-02-05T07:34:20.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548980)
;

-- Field: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> Erhält EDI-DESADV
-- Column: C_BPartner.IsEdiDesadvRecipient
-- 2026-02-05T07:34:48.832Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569749,771899,0,548980,TO_TIMESTAMP('2026-02-05 07:34:48.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',1,'D','Y','N','N','N','N','N','N','N','Erhält EDI-DESADV',TO_TIMESTAMP('2026-02-05 07:34:48.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-05T07:34:48.836Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=771899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-05T07:34:48.838Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577426)
;

-- 2026-02-05T07:34:48.848Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=771899
;

-- 2026-02-05T07:34:48.849Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(771899)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.Erhält EDI-DESADV
-- Column: C_BPartner.IsEdiDesadvRecipient
-- 2026-02-05T07:35:17.518Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771899,0,548980,554789,646829,'F',TO_TIMESTAMP('2026-02-05 07:35:17.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erhält EDI-DESADV',10,0,0,TO_TIMESTAMP('2026-02-05 07:35:17.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-ID des DESADV-Empfängers
-- Column: C_BPartner.EdiDesadvRecipientGLN
-- 2026-02-05T07:35:32.086Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771892,0,548980,554789,646830,'F',TO_TIMESTAMP('2026-02-05 07:35:31.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-ID des DESADV-Empfängers',20,0,0,TO_TIMESTAMP('2026-02-05 07:35:31.970000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-DESADV Sendemodus
-- Column: C_BPartner.EdiDESADVSendingMode
-- 2026-02-05T07:35:45.774Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771895,0,548980,554789,646831,'F',TO_TIMESTAMP('2026-02-05 07:35:45.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-DESADV Sendemodus',30,0,0,TO_TIMESTAMP('2026-02-05 07:35:45.651000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-DESADV Externes System Config
-- Column: C_BPartner.EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-05T07:36:04.741Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771896,0,548980,554789,646832,'F',TO_TIMESTAMP('2026-02-05 07:36:04.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-DESADV Externes System Config',40,0,0,TO_TIMESTAMP('2026-02-05 07:36:04.618000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.Erhält EDI-INVOIC
-- Column: C_BPartner.IsEdiInvoicRecipient
-- 2026-02-05T07:36:26.388Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771891,0,548980,554789,646833,'F',TO_TIMESTAMP('2026-02-05 07:36:26.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Erhält EDI-INVOIC',50,0,0,TO_TIMESTAMP('2026-02-05 07:36:26.264000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-ID des INVOIC-Empfängers
-- Column: C_BPartner.EdiInvoicRecipientGLN
-- 2026-02-05T07:36:38.147Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771894,0,548980,554789,646834,'F',TO_TIMESTAMP('2026-02-05 07:36:38.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-ID des INVOIC-Empfängers',60,0,0,TO_TIMESTAMP('2026-02-05 07:36:38.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-INVOIC Sendemodus
-- Column: C_BPartner.EdiINVOICSendingMode
-- 2026-02-05T07:36:50.673Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771897,0,548980,554789,646835,'F',TO_TIMESTAMP('2026-02-05 07:36:50.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-INVOIC Sendemodus',70,0,0,TO_TIMESTAMP('2026-02-05 07:36:50.556000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-INVOIC Externes System Config
-- Column: C_BPartner.EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-05T07:37:07.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,771898,0,548980,554789,646836,'F',TO_TIMESTAMP('2026-02-05 07:37:07.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'EDI-INVOIC Externes System Config',80,0,0,TO_TIMESTAMP('2026-02-05 07:37:07.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.Erhält EDI-DESADV
-- Column: C_BPartner.IsEdiDesadvRecipient
-- 2026-02-05T07:37:59.182Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.181000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646829
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-ID des DESADV-Empfängers
-- Column: C_BPartner.EdiDesadvRecipientGLN
-- 2026-02-05T07:37:59.190Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.190000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646830
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-DESADV Sendemodus
-- Column: C_BPartner.EdiDESADVSendingMode
-- 2026-02-05T07:37:59.196Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.196000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646831
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-DESADV Externes System Config
-- Column: C_BPartner.EdiDESADV_ExternalSystem_Config_ID
-- 2026-02-05T07:37:59.201Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.201000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646832
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.Erhält EDI-INVOIC
-- Column: C_BPartner.IsEdiInvoicRecipient
-- 2026-02-05T07:37:59.206Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.206000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646833
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-ID des INVOIC-Empfängers
-- Column: C_BPartner.EdiInvoicRecipientGLN
-- 2026-02-05T07:37:59.211Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.211000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646834
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-INVOIC Sendemodus
-- Column: C_BPartner.EdiINVOICSendingMode
-- 2026-02-05T07:37:59.215Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.215000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646835
;

-- UI Element: Geschäftspartner(123,D) -> EDI-Konfiguration(548980,D) -> main -> 10 -> main.EDI-INVOIC Externes System Config
-- Column: C_BPartner.EdiINVOIC_ExternalSystem_Config_ID
-- 2026-02-05T07:37:59.220Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-05 07:37:59.220000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646836
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Erhält EDI-DESADV
-- Column: C_BPartner.IsEdiDesadvRecipient
-- 2026-02-05T07:40:54.433Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:40:54.433000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=593783
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> Erhält EDI-INVOIC
-- Column: C_BPartner.IsEdiInvoicRecipient
-- 2026-02-05T07:40:55.555Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:40:55.555000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553177
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

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> EDI-ID des DESADV-Empfängers
-- Column: C_BPartner.EdiDesadvRecipientGLN
-- 2026-02-05T07:40:57.421Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:40:57.421000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=553178
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> "CU pro TU" bei unbestimmter Verpackungskapazität
-- Column: C_BPartner.EdiDESADVDefaultItemCapacity
-- 2026-02-05T07:41:24.307Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:41:24.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=556622
;

-- Field: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> EDI-ID des INVOIC-Empfängers
-- Column: C_BPartner.EdiInvoicRecipientGLN
-- 2026-02-05T07:41:46.428Z
UPDATE AD_Field SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:41:46.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=616964
;

-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.IsEdiDesadvRecipient
-- Column: C_BPartner.IsEdiDesadvRecipient
-- 2026-02-05T07:43:06.317Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:43:06.317000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=564728
;

-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.EDI-ID des DESADV-Empfängers
-- Column: C_BPartner.EdiDesadvRecipientGLN
-- 2026-02-05T07:43:08.270Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:43:08.270000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=583834
;

-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.IsEdiInvoicRecipient
-- Column: C_BPartner.IsEdiInvoicRecipient
-- 2026-02-05T07:43:09.305Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:43:09.305000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=1000083
;

-- UI Element: Geschäftspartner(123,D) -> Geschäftspartner(220,D) -> main -> 20 -> tax.EDI-ID des INVOIC-Empfängers
-- Column: C_BPartner.EdiInvoicRecipientGLN
-- 2026-02-05T07:43:13.024Z
UPDATE AD_UI_Element SET IsActive='N',Updated=TO_TIMESTAMP('2026-02-05 07:43:13.024000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=583835
;

-- Tab: Geschäftspartner(123,D) -> EDI-Konfiguration
-- Table: C_BPartner
-- 2026-02-05T08:03:29.041Z
UPDATE AD_Tab SET SeqNo=55,Updated=TO_TIMESTAMP('2026-02-05 08:03:29.041000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548980
;

