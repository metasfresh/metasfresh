-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)
-- Table: C_BPartner_CreditLimit_Department_Lines_V
-- 2023-02-01T21:57:50.067Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581988,0,546756,542289,541667,'Y',TO_TIMESTAMP('2023-02-01 23:57:49','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','C_BPartner_CreditLimit_Department_Lines_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Credit Limit (Department)','N',20,1,TO_TIMESTAMP('2023-02-01 23:57:49','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:57:50.069Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546756 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-02-01T21:57:50.071Z
/* DDL */  select update_tab_translation_from_ad_element(581988) 
;

-- 2023-02-01T21:57:50.075Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546756)
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)
-- Table: C_BPartner_CreditLimit_Department_Lines_V
-- 2023-02-01T21:57:56.171Z
UPDATE AD_Tab SET AD_Column_ID=585737,Updated=TO_TIMESTAMP('2023-02-01 23:57:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546756
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Credit Limit Type
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_CreditLimit_Type_ID
-- 2023-02-01T21:58:25.568Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585722,710811,0,546756,TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Credit Limit Type',TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:25.569Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:25.571Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543870) 
;

-- 2023-02-01T21:58:25.576Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710811
;

-- 2023-02-01T21:58:25.576Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710811)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Department
-- Column: C_BPartner_CreditLimit_Department_Lines_V.M_Department_ID
-- 2023-02-01T21:58:25.661Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585723,710812,0,546756,TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Department',TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:25.662Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:25.664Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581944) 
;

-- 2023-02-01T21:58:25.667Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710812
;

-- 2023-02-01T21:58:25.668Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710812)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Betrag
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Amount
-- 2023-02-01T21:58:25.751Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585724,710813,0,546756,TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung',14,'D','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','N','N','N','N','N','Betrag',TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:25.752Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:25.753Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1367) 
;

-- 2023-02-01T21:58:25.758Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710813
;

-- 2023-02-01T21:58:25.759Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710813)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Währung
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_Currency_ID
-- 2023-02-01T21:58:25.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585725,710814,0,546756,TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag',10,'D','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','N','N','N','N','N','Währung',TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:25.875Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:25.877Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(193) 
;

-- 2023-02-01T21:58:25.895Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710814
;

-- 2023-02-01T21:58:25.896Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710814)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Datum von
-- Column: C_BPartner_CreditLimit_Department_Lines_V.DateFrom
-- 2023-02-01T21:58:25.992Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585726,710815,0,546756,TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes',29,'D','Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','N','N','N','N','N','N','Datum von',TO_TIMESTAMP('2023-02-01 23:58:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:25.993Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:25.994Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1581) 
;

-- 2023-02-01T21:58:25.998Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710815
;

-- 2023-02-01T21:58:25.999Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710815)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Verarbeitet
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Processed
-- 2023-02-01T21:58:26.084Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585728,710816,0,546756,TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:26.086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:26.088Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047) 
;

-- 2023-02-01T21:58:26.100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710816
;

-- 2023-02-01T21:58:26.101Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710816)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Geschäftspartner
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_BPartner_ID
-- 2023-02-01T21:58:26.182Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585729,710817,0,546756,TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:26.183Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:26.184Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187) 
;

-- 2023-02-01T21:58:26.193Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710817
;

-- 2023-02-01T21:58:26.193Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710817)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Credit Limit (Department)
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_BPartner_CreditLimit_Department_Lines_V_ID
-- 2023-02-01T21:58:26.273Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585730,710818,0,546756,TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Credit Limit (Department)',TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:26.274Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:26.275Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581988) 
;

-- 2023-02-01T21:58:26.278Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710818
;

-- 2023-02-01T21:58:26.279Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710818)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Aktiv
-- Column: C_BPartner_CreditLimit_Department_Lines_V.IsActive
-- 2023-02-01T21:58:26.374Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585734,710819,0,546756,TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:26.375Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:26.377Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-02-01T21:58:26.613Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710819
;

-- 2023-02-01T21:58:26.615Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710819)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Mandant
-- Column: C_BPartner_CreditLimit_Department_Lines_V.AD_Client_ID
-- 2023-02-01T21:58:26.697Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585735,710820,0,546756,TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:26.698Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:26.700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-02-01T21:58:26.965Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710820
;

-- 2023-02-01T21:58:26.966Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710820)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Sektion
-- Column: C_BPartner_CreditLimit_Department_Lines_V.AD_Org_ID
-- 2023-02-01T21:58:27.059Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585736,710821,0,546756,TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-02-01 23:58:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:27.061Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:27.062Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-02-01T21:58:27.260Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710821
;

-- 2023-02-01T21:58:27.261Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710821)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Section Group Partner
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Section_Group_Partner_ID
-- 2023-02-01T21:58:27.367Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585737,710822,0,546756,TO_TIMESTAMP('2023-02-01 23:58:27','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Section Group Partner',TO_TIMESTAMP('2023-02-01 23:58:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:58:27.368Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:58:27.370Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581322) 
;

-- 2023-02-01T21:58:27.374Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710822
;

-- 2023-02-01T21:58:27.375Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710822)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Credit Limit Type
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_CreditLimit_Type_ID
-- 2023-02-01T21:58:41.705Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710811
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Department
-- Column: C_BPartner_CreditLimit_Department_Lines_V.M_Department_ID
-- 2023-02-01T21:58:42.145Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710812
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Betrag
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Amount
-- 2023-02-01T21:58:42.559Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710813
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Währung
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_Currency_ID
-- 2023-02-01T21:58:43.246Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710814
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Datum von
-- Column: C_BPartner_CreditLimit_Department_Lines_V.DateFrom
-- 2023-02-01T21:58:43.802Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710815
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Verarbeitet
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Processed
-- 2023-02-01T21:58:44.827Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710816
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Geschäftspartner
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_BPartner_ID
-- 2023-02-01T21:58:45.305Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710817
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Credit Limit (Department)
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_BPartner_CreditLimit_Department_Lines_V_ID
-- 2023-02-01T21:58:45.724Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710818
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Aktiv
-- Column: C_BPartner_CreditLimit_Department_Lines_V.IsActive
-- 2023-02-01T21:58:46.100Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710819
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Mandant
-- Column: C_BPartner_CreditLimit_Department_Lines_V.AD_Client_ID
-- 2023-02-01T21:58:46.510Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710820
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Sektion
-- Column: C_BPartner_CreditLimit_Department_Lines_V.AD_Org_ID
-- 2023-02-01T21:58:46.908Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710821
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Section Group Partner
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Section_Group_Partner_ID
-- 2023-02-01T21:58:48.141Z
UPDATE AD_Field SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-01 23:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710822
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Erstellt durch
-- Column: C_BPartner_CreditLimit_Department_Lines_V.CreatedBy
-- 2023-02-01T21:59:08.379Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585727,710823,0,546756,0,TO_TIMESTAMP('2023-02-01 23:59:08','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'U','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',0,10,0,1,1,TO_TIMESTAMP('2023-02-01 23:59:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T21:59:08.380Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T21:59:08.382Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-02-01T21:59:08.430Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710823
;

-- 2023-02-01T21:59:08.432Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710823)
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Erstellt durch
-- Column: C_BPartner_CreditLimit_Department_Lines_V.CreatedBy
-- 2023-02-01T21:59:11.938Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2023-02-01 23:59:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710823
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D)
-- UI Section: main
-- 2023-02-01T21:59:26.050Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546756,545390,TO_TIMESTAMP('2023-02-01 23:59:25','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-01 23:59:25','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-02-01T21:59:26.051Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545390 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main
-- UI Column: 10
-- 2023-02-01T21:59:26.141Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546576,545390,TO_TIMESTAMP('2023-02-01 23:59:26','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-02-01 23:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10
-- UI Element Group: default
-- 2023-02-01T21:59:26.235Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546576,550266,TO_TIMESTAMP('2023-02-01 23:59:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-02-01 23:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Erstellt durch
-- Column: C_BPartner_CreditLimit_Department_Lines_V.CreatedBy
-- 2023-02-01T21:59:26.377Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710823,0,546756,550266,614937,'F',TO_TIMESTAMP('2023-02-01 23:59:26','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,10,0,TO_TIMESTAMP('2023-02-01 23:59:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Credit Limit Type
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_CreditLimit_Type_ID
-- 2023-02-01T22:00:47.540Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710811,0,546756,550266,614938,'F',TO_TIMESTAMP('2023-02-02 00:00:47','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Credit Limit Type',10,0,0,TO_TIMESTAMP('2023-02-02 00:00:47','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Department
-- Column: C_BPartner_CreditLimit_Department_Lines_V.M_Department_ID
-- 2023-02-01T22:00:57.570Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710812,0,546756,550266,614939,'F',TO_TIMESTAMP('2023-02-02 00:00:57','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Department',20,0,0,TO_TIMESTAMP('2023-02-02 00:00:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Betrag
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Amount
-- 2023-02-01T22:01:15.425Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710813,0,546756,550266,614940,'F',TO_TIMESTAMP('2023-02-02 00:01:15','YYYY-MM-DD HH24:MI:SS'),100,'Betrag in einer definierten Währung','"Betrag" gibt den Betrag für diese Dokumentenposition an','Y','N','N','Y','N','N','N',0,'Betrag',30,0,0,TO_TIMESTAMP('2023-02-02 00:01:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_Currency_ID
-- 2023-02-01T22:01:23.545Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710814,0,546756,550266,614941,'F',TO_TIMESTAMP('2023-02-02 00:01:23','YYYY-MM-DD HH24:MI:SS'),100,'Die Währung für diesen Eintrag','Bezeichnet die auf Dokumenten oder Berichten verwendete Währung','Y','N','N','Y','N','N','N',0,'Währung',40,0,0,TO_TIMESTAMP('2023-02-02 00:01:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Datum von
-- Column: C_BPartner_CreditLimit_Department_Lines_V.DateFrom
-- 2023-02-01T22:01:32.001Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710815,0,546756,550266,614942,'F',TO_TIMESTAMP('2023-02-02 00:01:31','YYYY-MM-DD HH24:MI:SS'),100,'Startdatum eines Abschnittes','Datum von bezeichnet das Startdatum eines Abschnittes','Y','N','N','Y','N','N','N',0,'Datum von',50,0,0,TO_TIMESTAMP('2023-02-02 00:01:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Verarbeitet
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Processed
-- 2023-02-01T22:01:46.654Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710816,0,546756,550266,614943,'F',TO_TIMESTAMP('2023-02-02 00:01:46','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',60,0,0,TO_TIMESTAMP('2023-02-02 00:01:46','YYYY-MM-DD HH24:MI:SS'),100)
;


-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Approved By
-- Column: C_BPartner_CreditLimit_Department_Lines_V.ApprovedBy_ID
-- 2023-02-01T22:03:53.768Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585738,710824,0,546756,0,TO_TIMESTAMP('2023-02-02 00:03:53','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Approved By',0,20,0,1,1,TO_TIMESTAMP('2023-02-02 00:03:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-01T22:03:53.769Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-02-01T22:03:53.770Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543844) 
;

-- 2023-02-01T22:03:53.774Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710824
;

-- 2023-02-01T22:03:53.775Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710824)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Approved By
-- Column: C_BPartner_CreditLimit_Department_Lines_V.ApprovedBy_ID
-- 2023-02-01T22:04:05.970Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710824,0,546756,550266,614944,'F',TO_TIMESTAMP('2023-02-02 00:04:05','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Approved By',70,0,0,TO_TIMESTAMP('2023-02-02 00:04:05','YYYY-MM-DD HH24:MI:SS'),100)
;







-- Table: C_BPartner_CreditLimit_Department_Lines_V
-- 2023-02-02T08:43:00.050Z
UPDATE AD_Table SET AD_Window_ID=541667,Updated=TO_TIMESTAMP('2023-02-02 10:43:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542289
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Usage
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-02T08:43:30.253Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2023-02-02 10:43:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546755
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)
-- Table: C_BPartner_CreditLimit_Department_Lines_V
-- 2023-02-02T08:43:31.491Z
UPDATE AD_Tab SET IsInsertRecord='N',Updated=TO_TIMESTAMP('2023-02-02 10:43:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546756
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Usage
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-02T08:43:31.965Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 10:43:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546755
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)
-- Table: C_BPartner_CreditLimit_Department_Lines_V
-- 2023-02-02T08:43:33.381Z
UPDATE AD_Tab SET IsReadOnly='Y',Updated=TO_TIMESTAMP('2023-02-02 10:43:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546756
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)
-- Table: C_BPartner_CreditLimit_Department_Lines_V
-- 2023-02-02T08:43:59.904Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y', Parent_Column_ID=585714,Updated=TO_TIMESTAMP('2023-02-02 10:43:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546756
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Usage
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-02T08:44:09.881Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2023-02-02 10:44:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546755
;

-- Tab: Credit Limit (Departments)(541667,D) -> Credit Usage
-- Table: C_BPartner_CreditLimit_Departments_V
-- 2023-02-02T08:44:20.216Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='N',Updated=TO_TIMESTAMP('2023-02-02 10:44:20','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546755
;







-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Credit Limit Type
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_CreditLimit_Type_ID
-- 2023-02-02T09:02:32.697Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614938
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Department
-- Column: C_BPartner_CreditLimit_Department_Lines_V.M_Department_ID
-- 2023-02-02T09:02:32.706Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614939
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Betrag
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Amount
-- 2023-02-02T09:02:32.713Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614940
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_Currency_ID
-- 2023-02-02T09:02:32.720Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614941
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Datum von
-- Column: C_BPartner_CreditLimit_Department_Lines_V.DateFrom
-- 2023-02-02T09:02:32.728Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614942
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Erstellt durch
-- Column: C_BPartner_CreditLimit_Department_Lines_V.CreatedBy
-- 2023-02-02T09:02:32.734Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614937
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Verarbeitet
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Processed
-- 2023-02-02T09:02:32.741Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614943
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Approved By
-- Column: C_BPartner_CreditLimit_Department_Lines_V.ApprovedBy_ID
-- 2023-02-02T09:02:32.748Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-02 11:02:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614944
;

-- Field: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> Freigegeben
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Processed
-- 2023-02-02T09:04:52.721Z
UPDATE AD_Field SET AD_Name_ID=351, Description='Zeigt an, ob dieser Beleg eine Freigabe braucht', Help='Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann', Name='Freigegeben',Updated=TO_TIMESTAMP('2023-02-02 11:04:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710816
;

-- 2023-02-02T09:04:52.722Z
UPDATE AD_Field_Trl trl SET Description='Zeigt an, ob dieser Beleg eine Freigabe braucht',Help='Das Selektionsfeld "Freigabe" zeigt an, dass dieser Beleg eine Freigabe braucht, bevor er verarbeitet werden kann',Name='Freigegeben' WHERE AD_Field_ID=710816 AND AD_Language='de_DE'
;

-- 2023-02-02T09:04:52.724Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(351) 
;

-- 2023-02-02T09:04:52.742Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710816
;

-- 2023-02-02T09:04:52.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710816)
;


-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Usage(546755,D) -> main -> 10 -> default.Credit Limit (Departments)
-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_CreditLimit_Departments_v_ID
-- 2023-02-02T16:28:15.355Z
UPDATE AD_UI_Element SET IsDisplayed='N',Updated=TO_TIMESTAMP('2023-02-02 18:28:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614932
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_BPartner_ID
-- 2023-02-02T16:28:49.839Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710817,0,546756,550266,615513,'F',TO_TIMESTAMP('2023-02-02 18:28:48','YYYY-MM-DD HH24:MI:SS'),100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',80,0,0,TO_TIMESTAMP('2023-02-02 18:28:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_BPartner_ID
-- 2023-02-02T16:29:08.036Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=615513
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Credit Limit Type
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_CreditLimit_Type_ID
-- 2023-02-02T16:29:08.043Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614938
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Department
-- Column: C_BPartner_CreditLimit_Department_Lines_V.M_Department_ID
-- 2023-02-02T16:29:08.050Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614939
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Betrag
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Amount
-- 2023-02-02T16:29:08.059Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614940
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Währung
-- Column: C_BPartner_CreditLimit_Department_Lines_V.C_Currency_ID
-- 2023-02-02T16:29:08.066Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614941
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Datum von
-- Column: C_BPartner_CreditLimit_Department_Lines_V.DateFrom
-- 2023-02-02T16:29:08.073Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614942
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Erstellt durch
-- Column: C_BPartner_CreditLimit_Department_Lines_V.CreatedBy
-- 2023-02-02T16:29:08.080Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614937
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Verarbeitet
-- Column: C_BPartner_CreditLimit_Department_Lines_V.Processed
-- 2023-02-02T16:29:08.088Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614943
;

-- UI Element: Credit Limit (Departments)(541667,D) -> Credit Limit (Department)(546756,D) -> main -> 10 -> default.Approved By
-- Column: C_BPartner_CreditLimit_Department_Lines_V.ApprovedBy_ID
-- 2023-02-02T16:29:08.096Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-02-02 18:29:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=614944
;






-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-02T18:35:36.169Z
UPDATE AD_Column SET IsSelectionColumn='N',Updated=TO_TIMESTAMP('2023-02-02 20:35:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585714
;

-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-02T18:35:40.792Z
UPDATE AD_Column SET IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2023-02-02 20:35:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585714
;

-- Column: C_BPartner_CreditLimit_Departments_V.Section_Group_Partner_ID
-- 2023-02-02T18:35:48.638Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2023-02-02 20:35:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585714
;

-- Column: C_BPartner_CreditLimit_Departments_V.C_BPartner_ID
-- 2023-02-02T18:35:49.012Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2023-02-02 20:35:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585696
;

-- Column: C_BPartner_CreditLimit_Departments_V.M_Department_ID
-- 2023-02-02T18:35:49.380Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2023-02-02 20:35:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=585698
;

-- Reference: C_BPartner section group
-- Table: C_BPartner
-- Key: C_BPartner.C_BPartner_ID
-- 2023-02-02T18:41:51.414Z
UPDATE AD_Ref_Table SET AD_Display=2902, IsValueDisplayed='Y',Updated=TO_TIMESTAMP('2023-02-02 20:41:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=541640
;

