-- Tab: Geschäftspartner(123,D) -> Lastschrift Mandat
-- Table: C_BP_BankAccount_DirectDebitMandate
-- 2025-09-09T13:52:39.121Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,583933,0,548409,542522,123,'Y',TO_TIMESTAMP('2025-09-09 13:52:38.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_BP_BankAccount_DirectDebitMandate','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Lastschrift Mandat',NULL,'N',75,1,TO_TIMESTAMP('2025-09-09 13:52:38.964000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:39.143Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548409 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-09-09T13:52:39.151Z
/* DDL */  select update_tab_translation_from_ad_element(583933)
;

-- 2025-09-09T13:52:39.164Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548409)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Mandant
-- Column: C_BP_BankAccount_DirectDebitMandate.AD_Client_ID
-- 2025-09-09T13:52:49.968Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590819,753525,0,548409,TO_TIMESTAMP('2025-09-09 13:52:49.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-09-09 13:52:49.812000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:49.969Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753525 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:49.971Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-09-09T13:52:50.227Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753525
;

-- 2025-09-09T13:52:50.229Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753525)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Sektion
-- Column: C_BP_BankAccount_DirectDebitMandate.AD_Org_ID
-- 2025-09-09T13:52:50.319Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590820,753526,0,548409,TO_TIMESTAMP('2025-09-09 13:52:50.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-09-09 13:52:50.231000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:50.320Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753526 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:50.321Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-09-09T13:52:50.473Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753526
;

-- 2025-09-09T13:52:50.476Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753526)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Aktiv
-- Column: C_BP_BankAccount_DirectDebitMandate.IsActive
-- 2025-09-09T13:52:50.580Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590823,753527,0,548409,TO_TIMESTAMP('2025-09-09 13:52:50.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-09-09 13:52:50.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:50.581Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753527 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:50.582Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-09-09T13:52:50.681Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753527
;

-- 2025-09-09T13:52:50.683Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753527)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Lastschrift Mandat
-- Column: C_BP_BankAccount_DirectDebitMandate.C_BP_BankAccount_DirectDebitMandate_ID
-- 2025-09-09T13:52:50.774Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590826,753528,0,548409,TO_TIMESTAMP('2025-09-09 13:52:50.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Lastschrift Mandat',TO_TIMESTAMP('2025-09-09 13:52:50.684000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:50.776Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753528 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:50.778Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583933)
;

-- 2025-09-09T13:52:50.780Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753528
;

-- 2025-09-09T13:52:50.781Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753528)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Geschäftspartner
-- Column: C_BP_BankAccount_DirectDebitMandate.C_BPartner_ID
-- 2025-09-09T13:52:50.891Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590827,753529,0,548409,TO_TIMESTAMP('2025-09-09 13:52:50.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-09-09 13:52:50.783000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:50.893Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753529 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:50.895Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-09-09T13:52:50.914Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753529
;

-- 2025-09-09T13:52:50.916Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753529)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Bankverbindung
-- Column: C_BP_BankAccount_DirectDebitMandate.C_BP_BankAccount_ID
-- 2025-09-09T13:52:51.009Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590828,753530,0,548409,TO_TIMESTAMP('2025-09-09 13:52:50.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bankverbindung des Geschäftspartners',10,'D','Y','N','N','N','N','N','N','N','Bankverbindung',TO_TIMESTAMP('2025-09-09 13:52:50.918000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:51.012Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753530 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:51.013Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(837)
;

-- 2025-09-09T13:52:51.019Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753530
;

-- 2025-09-09T13:52:51.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753530)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Mandatsreferenz
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateReference
-- 2025-09-09T13:52:51.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590829,753531,0,548409,TO_TIMESTAMP('2025-09-09 13:52:51.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,35,'D','Y','N','N','N','N','N','N','N','Mandatsreferenz',TO_TIMESTAMP('2025-09-09 13:52:51.028000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:51.139Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753531 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:51.140Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583934)
;

-- 2025-09-09T13:52:51.142Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753531
;

-- 2025-09-09T13:52:51.143Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753531)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Mandatsdatum
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateDate
-- 2025-09-09T13:52:51.241Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590830,753532,0,548409,TO_TIMESTAMP('2025-09-09 13:52:51.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Mandatsdatum',TO_TIMESTAMP('2025-09-09 13:52:51.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:51.242Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753532 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:51.244Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583935)
;

-- 2025-09-09T13:52:51.245Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753532
;

-- 2025-09-09T13:52:51.246Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753532)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Mandat Status
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateStatus
-- 2025-09-09T13:52:51.358Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590831,753533,0,548409,TO_TIMESTAMP('2025-09-09 13:52:51.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Mandat Status',TO_TIMESTAMP('2025-09-09 13:52:51.248000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:51.360Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753533 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:51.362Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583936)
;

-- 2025-09-09T13:52:51.364Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753533
;

-- 2025-09-09T13:52:51.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753533)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Letzte Nutzung
-- Column: C_BP_BankAccount_DirectDebitMandate.DateLastUsed
-- 2025-09-09T13:52:51.473Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590832,753534,0,548409,TO_TIMESTAMP('2025-09-09 13:52:51.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,7,'D','Y','N','N','N','N','N','N','N','Letzte Nutzung',TO_TIMESTAMP('2025-09-09 13:52:51.366000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:51.475Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753534 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:51.477Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583937)
;

-- 2025-09-09T13:52:51.479Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753534
;

-- 2025-09-09T13:52:51.480Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753534)
;

-- Field: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> Wiederkehrend
-- Column: C_BP_BankAccount_DirectDebitMandate.IsRecurring
-- 2025-09-09T13:52:51.578Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590833,753535,0,548409,TO_TIMESTAMP('2025-09-09 13:52:51.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Wiederkehrend',TO_TIMESTAMP('2025-09-09 13:52:51.482000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-09T13:52:51.580Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753535 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-09T13:52:51.582Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583938)
;

-- 2025-09-09T13:52:51.584Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753535
;

-- 2025-09-09T13:52:51.585Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753535)
;

-- Tab: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D)
-- UI Section: main
-- 2025-09-09T13:54:30.448Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548409,546936,TO_TIMESTAMP('2025-09-09 13:54:30.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-09 13:54:30.302000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-09T13:54:30.449Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546936 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main
-- UI Column: 10
-- 2025-09-09T13:54:41.312Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548439,546936,TO_TIMESTAMP('2025-09-09 13:54:41.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-09 13:54:41.159000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10
-- UI Element Group: main
-- 2025-09-09T13:54:54.021Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,548439,553486,TO_TIMESTAMP('2025-09-09 13:54:53.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,TO_TIMESTAMP('2025-09-09 13:54:53.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Bankverbindung
-- Column: C_BP_BankAccount_DirectDebitMandate.C_BP_BankAccount_ID
-- 2025-09-09T13:55:38.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753530,0,548409,553486,636963,'F',TO_TIMESTAMP('2025-09-09 13:55:38.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bankverbindung des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Bankverbindung',10,0,0,TO_TIMESTAMP('2025-09-09 13:55:38.219000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandatsreferenz
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateReference
-- 2025-09-09T13:56:30.326Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753531,0,548409,553486,636964,'F',TO_TIMESTAMP('2025-09-09 13:56:30.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Mandatsreferenz',20,0,0,TO_TIMESTAMP('2025-09-09 13:56:30.176000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandatsdatum
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateDate
-- 2025-09-09T13:56:52.323Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753532,0,548409,553486,636965,'F',TO_TIMESTAMP('2025-09-09 13:56:52.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Mandatsdatum',30,0,0,TO_TIMESTAMP('2025-09-09 13:56:52.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Wiederkehrend
-- Column: C_BP_BankAccount_DirectDebitMandate.IsRecurring
-- 2025-09-09T13:57:29.312Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753535,0,548409,553486,636966,'F',TO_TIMESTAMP('2025-09-09 13:57:29.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Wiederkehrend',40,0,0,TO_TIMESTAMP('2025-09-09 13:57:29.153000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandat Status
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateStatus
-- 2025-09-09T13:57:51.606Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753533,0,548409,553486,636967,'F',TO_TIMESTAMP('2025-09-09 13:57:51.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Mandat Status',50,0,0,TO_TIMESTAMP('2025-09-09 13:57:51.455000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Letzte Nutzung
-- Column: C_BP_BankAccount_DirectDebitMandate.DateLastUsed
-- 2025-09-09T13:58:18.970Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753534,0,548409,553486,636968,'F',TO_TIMESTAMP('2025-09-09 13:58:18.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Letzte Nutzung',60,0,0,TO_TIMESTAMP('2025-09-09 13:58:18.819000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Aktiv
-- Column: C_BP_BankAccount_DirectDebitMandate.IsActive
-- 2025-09-09T13:58:52.831Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753527,0,548409,553486,636969,'F',TO_TIMESTAMP('2025-09-09 13:58:52.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',70,0,0,TO_TIMESTAMP('2025-09-09 13:58:52.673000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Sektion
-- Column: C_BP_BankAccount_DirectDebitMandate.AD_Org_ID
-- 2025-09-09T13:59:03.962Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753526,0,548409,553486,636970,'F',TO_TIMESTAMP('2025-09-09 13:59:03.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',80,0,0,TO_TIMESTAMP('2025-09-09 13:59:03.817000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Bankverbindung
-- Column: C_BP_BankAccount_DirectDebitMandate.C_BP_BankAccount_ID
-- 2025-09-09T13:59:53.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636963
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandatsreferenz
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateReference
-- 2025-09-09T13:59:53.754Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.753000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636964
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandatsdatum
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateDate
-- 2025-09-09T13:59:53.759Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.759000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636965
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Wiederkehrend
-- Column: C_BP_BankAccount_DirectDebitMandate.IsRecurring
-- 2025-09-09T13:59:53.765Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636966
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Mandat Status
-- Column: C_BP_BankAccount_DirectDebitMandate.MandateStatus
-- 2025-09-09T13:59:53.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636967
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Letzte Nutzung
-- Column: C_BP_BankAccount_DirectDebitMandate.DateLastUsed
-- 2025-09-09T13:59:53.774Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.774000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636968
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Aktiv
-- Column: C_BP_BankAccount_DirectDebitMandate.IsActive
-- 2025-09-09T13:59:53.777Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.777000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636969
;

-- UI Element: Geschäftspartner(123,D) -> Lastschrift Mandat(548409,D) -> main -> 10 -> main.Sektion
-- Column: C_BP_BankAccount_DirectDebitMandate.AD_Org_ID
-- 2025-09-09T13:59:53.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2025-09-09 13:59:53.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=636970
;

-- Element: C_BP_BankAccount_DirectDebitMandate_ID
-- 2025-09-09T14:55:29.629Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Direct Debit Mandate', PrintName='Direct Debit Mandate',Updated=TO_TIMESTAMP('2025-09-09 14:55:29.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583933 AND AD_Language='en_US'
;

-- 2025-09-09T14:55:29.635Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-09T14:55:29.892Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583933,'en_US')
;

-- Element: C_BP_BankAccount_DirectDebitMandate_ID
-- 2025-09-09T14:55:30.575Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-09 14:55:30.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583933 AND AD_Language='de_CH'
;

-- 2025-09-09T14:55:30.577Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583933,'de_CH')
;

-- Element: C_BP_BankAccount_DirectDebitMandate_ID
-- 2025-09-09T14:55:31.766Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2025-09-09 14:55:31.766000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583933 AND AD_Language='de_DE'
;

-- 2025-09-09T14:55:31.767Z
/* DDL */  select update_ad_element_on_ad_element_trl_update(583933,'de_DE')
;

-- 2025-09-09T14:55:31.769Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583933,'de_DE')
;

