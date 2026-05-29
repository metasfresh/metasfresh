-- Tab: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser
-- Table: AD_User_Purchaser_BPartner_V
-- 2025-12-02T15:10:24.120Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584321,0,548596,542561,540983,'Y',TO_TIMESTAMP('2025-12-02 15:10:23.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'de.metas.invoicecandidate','N','N','A','AD_User_Purchaser_BPartner_V','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Purchaser','N',70,0,TO_TIMESTAMP('2025-12-02 15:10:23.085000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:10:24.124Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548596 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-12-02T15:10:24.131Z
/* DDL */  select update_tab_translation_from_ad_element(584321)
;

-- 2025-12-02T15:10:24.140Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548596)
;

-- Tab: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser
-- Table: AD_User_Purchaser_BPartner_V
-- 2025-12-02T15:11:17.351Z
UPDATE AD_Tab SET EntityType='D',Updated=TO_TIMESTAMP('2025-12-02 15:11:17.351000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548596
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Mandant
-- Column: AD_User_Purchaser_BPartner_V.AD_Client_ID
-- 2025-12-02T15:13:00.510Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591641,758531,0,548596,TO_TIMESTAMP('2025-12-02 15:13:00.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-12-02 15:13:00.365000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:00.513Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:00.515Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-12-02T15:13:00.579Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758531
;

-- 2025-12-02T15:13:00.581Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758531)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Sektion
-- Column: AD_User_Purchaser_BPartner_V.AD_Org_ID
-- 2025-12-02T15:13:00.679Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591642,758532,0,548596,TO_TIMESTAMP('2025-12-02 15:13:00.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-12-02 15:13:00.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:00.683Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:00.684Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-12-02T15:13:00.746Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758532
;

-- 2025-12-02T15:13:00.747Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758532)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Ansprechpartner
-- Column: AD_User_Purchaser_BPartner_V.AD_User_ID
-- 2025-12-02T15:13:00.849Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591643,758533,0,548596,TO_TIMESTAMP('2025-12-02 15:13:00.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'User within the system - Internal or Business Partner Contact',10,'D','The User identifies a unique user in the system. This could be an internal user or a business partner contact','Y','N','N','N','N','N','N','N','Ansprechpartner',TO_TIMESTAMP('2025-12-02 15:13:00.751000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:00.852Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:00.854Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(138)
;

-- 2025-12-02T15:13:00.857Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758533
;

-- 2025-12-02T15:13:00.858Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758533)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Purchaser
-- Column: AD_User_Purchaser_BPartner_V.AD_User_Purchaser_ID
-- 2025-12-02T15:13:00.959Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591644,758534,0,548596,TO_TIMESTAMP('2025-12-02 15:13:00.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Purchaser',TO_TIMESTAMP('2025-12-02 15:13:00.862000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:00.961Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758534 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:00.962Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584321)
;

-- 2025-12-02T15:13:00.965Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758534
;

-- 2025-12-02T15:13:00.966Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758534)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Aktiv
-- Column: AD_User_Purchaser_BPartner_V.IsActive
-- 2025-12-02T15:13:01.059Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591649,758535,0,548596,TO_TIMESTAMP('2025-12-02 15:13:00.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-12-02 15:13:00.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:01.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758535 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:01.065Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-12-02T15:13:01.127Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758535
;

-- 2025-12-02T15:13:01.128Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758535)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Geschäftspartnergruppe
-- Column: AD_User_Purchaser_BPartner_V.C_BP_Group_ID
-- 2025-12-02T15:13:01.239Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591650,758536,0,548596,TO_TIMESTAMP('2025-12-02 15:13:01.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartnergruppe',10,'D','Eine Geschäftspartner-Gruppe bietet Ihnen die Möglichkeit, Standard-Werte für einzelne Geschäftspartner zu verwenden.','Y','N','N','N','N','N','N','N','Geschäftspartnergruppe',TO_TIMESTAMP('2025-12-02 15:13:01.132000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:01.242Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758536 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:01.245Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1383)
;

-- 2025-12-02T15:13:01.249Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758536
;

-- 2025-12-02T15:13:01.250Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758536)
;

-- Field: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser(548596,D) -> Geschäftspartner
-- Column: AD_User_Purchaser_BPartner_V.C_BPartner_ID
-- 2025-12-02T15:13:01.349Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591651,758537,0,548596,TO_TIMESTAMP('2025-12-02 15:13:01.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-12-02 15:13:01.253000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-12-02T15:13:01.353Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=758537 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-12-02T15:13:01.354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-12-02T15:13:01.359Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=758537
;

-- 2025-12-02T15:13:01.360Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(758537)
;

-- Tab: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser
-- Table: AD_User_Purchaser_BPartner_V
-- 2025-12-02T15:13:06.428Z
UPDATE AD_Tab SET TabLevel=1,Updated=TO_TIMESTAMP('2025-12-02 15:13:06.428000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548596
;

-- Tab: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser
-- Table: AD_User_Purchaser_BPartner_V
-- 2025-12-02T15:13:10.756Z
UPDATE AD_Tab SET Parent_Column_ID=544920,Updated=TO_TIMESTAMP('2025-12-02 15:13:10.756000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548596
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.M_AttributeSetInstance_ID
-- Column: C_Invoice_Candidate.M_AttributeSetInstance_ID
-- 2025-12-02T15:14:53.584Z
UPDATE AD_UI_Element SET IsAdvancedField='Y',Updated=TO_TIMESTAMP('2025-12-02 15:14:53.584000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=624785
;

-- UI Element: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Rechnungskandidaten(543052,de.metas.invoicecandidate) -> main -> 10 -> default.Einkäufer
-- 2025-12-02T15:15:12.947Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Labels_Selector_Field_ID,Labels_Tab_ID,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,0,543052,544361,639731,'L',TO_TIMESTAMP('2025-12-02 15:15:12.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','Y','Y','N','N','N',758533,548596,0,'Einkäufer',90,0,0,TO_TIMESTAMP('2025-12-02 15:15:12.820000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;


-- Tab: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser
-- Table: AD_User_Purchaser_BPartner_V
-- 2025-12-03T07:42:15.841Z
UPDATE AD_Tab SET AllowQuickInput='N',Updated=TO_TIMESTAMP('2025-12-03 07:42:15.841000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548596
;

-- Tab: Rechnungsdisposition Einkauf(540983,de.metas.invoicecandidate) -> Purchaser
-- Table: AD_User_Purchaser_BPartner_V
-- 2025-12-03T07:49:33.783Z
UPDATE AD_Tab SET AD_Column_ID=591651,Updated=TO_TIMESTAMP('2025-12-03 07:49:33.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548596
;
