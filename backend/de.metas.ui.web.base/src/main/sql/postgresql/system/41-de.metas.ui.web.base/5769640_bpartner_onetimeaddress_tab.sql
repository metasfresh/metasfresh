-- Run mode: SWING_CLIENT

-- Column: C_Order.DropShip_Location_ID
-- 2025-09-18T12:25:54.062Z
UPDATE AD_Column SET AD_Reference_ID=30,Updated=TO_TIMESTAMP('2025-09-18 12:25:54.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=55315
;

-- 2025-09-18T15:48:43.437Z
/* DDL */ SELECT public.db_alter_table('C_BPartner_Location','ALTER TABLE C_BPartner_Location DROP COLUMN IF EXISTS IsOneTime')
;

-- Column: C_BPartner_Location.IsOneTime
-- 2025-09-18T15:48:44.674Z
DELETE FROM  AD_Column_Trl WHERE AD_Column_ID=590903
;

-- 2025-09-18T15:48:44.681Z
DELETE FROM AD_Column WHERE AD_Column_ID=590903
;

-- Tab: Geschäftspartner(123,D) -> Adresse
-- Table: C_BPartner_Location
-- 2025-09-18T15:53:54.830Z
UPDATE AD_Tab SET WhereClause='IsEphemereal = ''N''',Updated=TO_TIMESTAMP('2025-09-18 15:53:54.830000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=222
;

-- Tab: Geschäftspartner(123,D) -> One time transaction
-- Table: C_BPartner_Location
-- 2025-09-18T15:55:09.347Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,922,0,548422,293,123,'Y',TO_TIMESTAMP('2025-09-18 15:55:09.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','A','C_BPartner_Location - Ephemereal','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','Y','N','N','One time transaction','N',50,1,TO_TIMESTAMP('2025-09-18 15:55:09.150000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'IsEphemereal = ''Y''')
;

-- 2025-09-18T15:55:09.349Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548422 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2025-09-18T15:55:09.351Z
/* DDL */  select update_tab_translation_from_ad_element(922)
;

-- 2025-09-18T15:55:09.365Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548422)
;

-- Tab: Geschäftspartner(123,D) -> Adresse
-- Table: C_BPartner_Location
-- 2025-09-18T16:06:10.660Z
UPDATE AD_Tab SET WhereClause='IsEphemeral = ''N''',Updated=TO_TIMESTAMP('2025-09-18 16:06:10.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=222
;

-- Tab: Geschäftspartner(123,D) -> One time transaction
-- Table: C_BPartner_Location
-- 2025-09-18T16:06:17.572Z
UPDATE AD_Tab SET WhereClause='IsEphemeral = ''Y''',Updated=TO_TIMESTAMP('2025-09-18 16:06:17.572000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548422
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Mandant
-- Column: C_BPartner_Location.AD_Client_ID
-- 2025-09-18T16:10:19.874Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2951,753785,0,548422,TO_TIMESTAMP('2025-09-18 16:10:19.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2025-09-18 16:10:19.657000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:19.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753785 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:19.880Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2025-09-18T16:10:20.409Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753785
;

-- 2025-09-18T16:10:20.411Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753785)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Sektion
-- Column: C_BPartner_Location.AD_Org_ID
-- 2025-09-18T16:10:20.529Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2952,753786,0,548422,TO_TIMESTAMP('2025-09-18 16:10:20.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2025-09-18 16:10:20.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:20.531Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753786 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:20.532Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2025-09-18T16:10:20.709Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753786
;

-- 2025-09-18T16:10:20.710Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753786)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Aktiv
-- Column: C_BPartner_Location.IsActive
-- 2025-09-18T16:10:20.846Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2953,753787,0,548422,TO_TIMESTAMP('2025-09-18 16:10:20.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2025-09-18 16:10:20.713000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:20.847Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753787 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:20.848Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2025-09-18T16:10:21.121Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753787
;

-- 2025-09-18T16:10:21.122Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753787)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Erstellt
-- Column: C_BPartner_Location.Created
-- 2025-09-18T16:10:21.270Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2954,753788,0,548422,TO_TIMESTAMP('2025-09-18 16:10:21.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',7,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2025-09-18 16:10:21.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:21.272Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753788 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:21.273Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245)
;

-- 2025-09-18T16:10:21.344Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753788
;

-- 2025-09-18T16:10:21.345Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753788)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Erstellt durch
-- Column: C_BPartner_Location.CreatedBy
-- 2025-09-18T16:10:21.478Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2955,753789,0,548422,TO_TIMESTAMP('2025-09-18 16:10:21.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',22,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2025-09-18 16:10:21.348000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:21.480Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753789 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:21.481Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246)
;

-- 2025-09-18T16:10:21.535Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753789
;

-- 2025-09-18T16:10:21.536Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753789)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Aktualisiert
-- Column: C_BPartner_Location.Updated
-- 2025-09-18T16:10:21.672Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2956,753790,0,548422,TO_TIMESTAMP('2025-09-18 16:10:21.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'D','Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2025-09-18 16:10:21.539000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:21.673Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753790 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:21.674Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(607)
;

-- 2025-09-18T16:10:21.757Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753790
;

-- 2025-09-18T16:10:21.758Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753790)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Aktualisiert durch
-- Column: C_BPartner_Location.UpdatedBy
-- 2025-09-18T16:10:21.909Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2957,753791,0,548422,TO_TIMESTAMP('2025-09-18 16:10:21.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',22,'D','Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2025-09-18 16:10:21.762000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:21.910Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753791 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:21.911Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(608)
;

-- 2025-09-18T16:10:21.969Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753791
;

-- 2025-09-18T16:10:21.970Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753791)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Geschäftspartner
-- Column: C_BPartner_Location.C_BPartner_ID
-- 2025-09-18T16:10:22.121Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2958,753792,0,548422,TO_TIMESTAMP('2025-09-18 16:10:21.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',22,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2025-09-18 16:10:21.973000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:22.122Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753792 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:22.123Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2025-09-18T16:10:22.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753792
;

-- 2025-09-18T16:10:22.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753792)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Anschrift
-- Column: C_BPartner_Location.C_Location_ID
-- 2025-09-18T16:10:22.284Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2959,753793,0,548422,TO_TIMESTAMP('2025-09-18 16:10:22.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Adresse oder Anschrift',22,'D','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','N','N','N','N','N','N','Anschrift',TO_TIMESTAMP('2025-09-18 16:10:22.156000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:22.285Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753793 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:22.286Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(202)
;

-- 2025-09-18T16:10:22.295Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753793
;

-- 2025-09-18T16:10:22.295Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753793)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Name
-- Column: C_BPartner_Location.Name
-- 2025-09-18T16:10:22.425Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2960,753794,0,548422,TO_TIMESTAMP('2025-09-18 16:10:22.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',255,'D','','Y','N','N','N','N','N','N','N','Name',TO_TIMESTAMP('2025-09-18 16:10:22.298000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:22.426Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753794 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:22.428Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(469)
;

-- 2025-09-18T16:10:22.514Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753794
;

-- 2025-09-18T16:10:22.516Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753794)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Telefon
-- Column: C_BPartner_Location.Phone
-- 2025-09-18T16:10:22.646Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2964,753795,0,548422,TO_TIMESTAMP('2025-09-18 16:10:22.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beschreibt eine Telefon Nummer',40,'D','','Y','N','N','N','N','N','N','N','Telefon',TO_TIMESTAMP('2025-09-18 16:10:22.518000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:22.648Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753795 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:22.649Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505)
;

-- 2025-09-18T16:10:22.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753795
;

-- 2025-09-18T16:10:22.657Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753795)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Telefon (alternativ)
-- Column: C_BPartner_Location.Phone2
-- 2025-09-18T16:10:22.787Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2965,753796,0,548422,TO_TIMESTAMP('2025-09-18 16:10:22.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Alternative Telefonnummer',40,'D','','Y','N','N','N','N','N','N','N','Telefon (alternativ)',TO_TIMESTAMP('2025-09-18 16:10:22.659000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:22.789Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753796 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:22.790Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(506)
;

-- 2025-09-18T16:10:22.793Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753796
;

-- 2025-09-18T16:10:22.794Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753796)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Fax
-- Column: C_BPartner_Location.Fax
-- 2025-09-18T16:10:22.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2966,753797,0,548422,TO_TIMESTAMP('2025-09-18 16:10:22.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Faxnummer',40,'D','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','N','N','N','N','N','Fax',TO_TIMESTAMP('2025-09-18 16:10:22.796000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:22.949Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753797 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:22.951Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(301)
;

-- 2025-09-18T16:10:22.954Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753797
;

-- 2025-09-18T16:10:22.955Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753797)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> ISDN
-- Column: C_BPartner_Location.ISDN
-- 2025-09-18T16:10:23.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2967,753798,0,548422,TO_TIMESTAMP('2025-09-18 16:10:22.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'ISDN- oder Modem-Anschluss',40,'D','"ISDN" gibt einen ISDN- oder Modem-Anschluss an.','Y','N','N','N','N','N','N','N','ISDN',TO_TIMESTAMP('2025-09-18 16:10:22.958000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.087Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753798 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(327)
;

-- 2025-09-18T16:10:23.091Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753798
;

-- 2025-09-18T16:10:23.092Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753798)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Vertriebsgebiet
-- Column: C_BPartner_Location.C_SalesRegion_ID
-- 2025-09-18T16:10:23.222Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,2968,753799,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Vertriebsgebiet',22,'D','"Vertriebsgebiet" gibt einen bestimmten Verkaufsbereich an.','Y','N','N','N','N','N','N','N','Vertriebsgebiet',TO_TIMESTAMP('2025-09-18 16:10:23.094000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.223Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753799 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.224Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(210)
;

-- 2025-09-18T16:10:23.232Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753799
;

-- 2025-09-18T16:10:23.233Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753799)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Vorbelegung Rechnung
-- Column: C_BPartner_Location.IsBillTo
-- 2025-09-18T16:10:23.358Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3090,753800,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rechnungs-Adresse für diesen Geschäftspartner',1,'D','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','Y','N','N','N','N','N','N','N','Vorbelegung Rechnung',TO_TIMESTAMP('2025-09-18 16:10:23.235000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.360Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753800 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.361Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(916)
;

-- 2025-09-18T16:10:23.365Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753800
;

-- 2025-09-18T16:10:23.366Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753800)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Lieferstandard
-- Column: C_BPartner_Location.IsShipTo
-- 2025-09-18T16:10:23.498Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3091,753801,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Liefer-Adresse für den Geschäftspartner',1,'D','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','N','N','N','N','N','N','Lieferstandard',TO_TIMESTAMP('2025-09-18 16:10:23.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.499Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753801 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(929)
;

-- 2025-09-18T16:10:23.503Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753801
;

-- 2025-09-18T16:10:23.504Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753801)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Zahlungs-Adresse
-- Column: C_BPartner_Location.IsPayFrom
-- 2025-09-18T16:10:23.632Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3092,753802,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Geschäftspartner zahlt von dieser Adresse und wir senden Mahnungen an diese Adresse',1,'D','Wenn "Zahlungs-Adresse" selektiert ist, zahlt der Geschäftspartner von dieser Adresse und wir senden Mahnungen an diese Adresse.','Y','N','N','N','N','N','N','N','Zahlungs-Adresse',TO_TIMESTAMP('2025-09-18 16:10:23.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.633Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753802 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.640Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(925)
;

-- 2025-09-18T16:10:23.642Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753802
;

-- 2025-09-18T16:10:23.643Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753802)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Erstattungs-Adresse
-- Column: C_BPartner_Location.IsRemitTo
-- 2025-09-18T16:10:23.772Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3093,753803,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Erstattungs-Adresse für den Lieferanten',1,'D','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.','Y','N','N','N','N','N','N','N','Erstattungs-Adresse',TO_TIMESTAMP('2025-09-18 16:10:23.645000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.774Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753803 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.775Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(927)
;

-- 2025-09-18T16:10:23.778Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753803
;

-- 2025-09-18T16:10:23.778Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753803)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Standort
-- Column: C_BPartner_Location.C_BPartner_Location_ID
-- 2025-09-18T16:10:23.910Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,3434,753804,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',22,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2025-09-18 16:10:23.780000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:23.911Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:23.912Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2025-09-18T16:10:23.921Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753804
;

-- 2025-09-18T16:10:23.922Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753804)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Adresse
-- Column: C_BPartner_Location.Address
-- 2025-09-18T16:10:24.057Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,505104,753805,0,548422,TO_TIMESTAMP('2025-09-18 16:10:23.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anschrift',360,'D','Y','N','N','N','N','N','N','N','Adresse',TO_TIMESTAMP('2025-09-18 16:10:23.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.058Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.060Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(505104)
;

-- 2025-09-18T16:10:24.062Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753805
;

-- 2025-09-18T16:10:24.063Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753805)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> GLN
-- Column: C_BPartner_Location.GLN
-- 2025-09-18T16:10:24.195Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,548323,753806,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,20,'D','Y','N','N','N','N','N','N','N','GLN',TO_TIMESTAMP('2025-09-18 16:10:24.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.196Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.197Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541976)
;

-- 2025-09-18T16:10:24.200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753806
;

-- 2025-09-18T16:10:24.200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753806)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Abladeort
-- Column: C_BPartner_Location.IsHandOverLocation
-- 2025-09-18T16:10:24.332Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,549932,753807,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Abladeort',TO_TIMESTAMP('2025-09-18 16:10:24.203000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.333Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542278)
;

-- 2025-09-18T16:10:24.336Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753807
;

-- 2025-09-18T16:10:24.338Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753807)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Replikations Standardwert
-- Column: C_BPartner_Location.IsReplicationLookupDefault
-- 2025-09-18T16:10:24.466Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,550430,753808,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','N','N','N','N','N','N','Replikations Standardwert',TO_TIMESTAMP('2025-09-18 16:10:24.340000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.467Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.469Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542394)
;

-- 2025-09-18T16:10:24.471Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753808
;

-- 2025-09-18T16:10:24.472Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753808)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Externe ID
-- Column: C_BPartner_Location.ExternalId
-- 2025-09-18T16:10:24.612Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,560989,753809,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Externe ID',TO_TIMESTAMP('2025-09-18 16:10:24.474000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.613Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.615Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543939)
;

-- 2025-09-18T16:10:24.619Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753809
;

-- 2025-09-18T16:10:24.620Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753809)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Alternative Fax
-- Column: C_BPartner_Location.Fax2
-- 2025-09-18T16:10:24.748Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564612,753810,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Faxnummer',40,'D','The Fax identifies a facsimile number for this Business Partner or  Location','Y','N','N','N','N','N','N','N','Alternative Fax',TO_TIMESTAMP('2025-09-18 16:10:24.623000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.749Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.751Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576287)
;

-- 2025-09-18T16:10:24.754Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753810
;

-- 2025-09-18T16:10:24.754Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753810)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> eMail
-- Column: C_BPartner_Location.EMail
-- 2025-09-18T16:10:24.880Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564643,753811,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse',200,'D','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','N','N','N','N','N','eMail',TO_TIMESTAMP('2025-09-18 16:10:24.757000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:24.881Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:24.882Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(881)
;

-- 2025-09-18T16:10:24.887Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753811
;

-- 2025-09-18T16:10:24.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753811)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Alternative eMail
-- Column: C_BPartner_Location.EMail2
-- 2025-09-18T16:10:25.015Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,564644,753812,0,548422,TO_TIMESTAMP('2025-09-18 16:10:24.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse',200,'D','','Y','N','N','N','N','N','N','N','Alternative eMail',TO_TIMESTAMP('2025-09-18 16:10:24.890000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.016Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.018Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576305)
;

-- 2025-09-18T16:10:25.020Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753812
;

-- 2025-09-18T16:10:25.020Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753812)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Besuchsadresse
-- Column: C_BPartner_Location.VisitorsAddress
-- 2025-09-18T16:10:25.144Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,568203,753813,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Besuchsadresse',TO_TIMESTAMP('2025-09-18 16:10:25.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.146Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.147Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(576808)
;

-- 2025-09-18T16:10:25.149Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753813
;

-- 2025-09-18T16:10:25.150Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753813)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Name Geschäftspartner
-- Column: C_BPartner_Location.BPartnerName
-- 2025-09-18T16:10:25.279Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,569638,753814,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,255,'D','Y','N','N','N','N','N','N','N','Name Geschäftspartner',TO_TIMESTAMP('2025-09-18 16:10:25.152000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.280Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.281Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543350)
;

-- 2025-09-18T16:10:25.285Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753814
;

-- 2025-09-18T16:10:25.285Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753814)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Rüstplatz-Nr.
-- Column: C_BPartner_Location.Setup_Place_No
-- 2025-09-18T16:10:25.423Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,572436,753815,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Rüstplatz beim Logistik-Partner oder im eigenen Lager',250,'D','Y','N','N','N','N','N','N','N','Rüstplatz-Nr.',TO_TIMESTAMP('2025-09-18 16:10:25.288000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.424Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.426Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578643)
;

-- 2025-09-18T16:10:25.428Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753815
;

-- 2025-09-18T16:10:25.429Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753815)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Org Mapping
-- Column: C_BPartner_Location.AD_Org_Mapping_ID
-- 2025-09-18T16:10:25.558Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,573118,753816,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Org Mapping',TO_TIMESTAMP('2025-09-18 16:10:25.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.559Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.561Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(578815)
;

-- 2025-09-18T16:10:25.563Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753816
;

-- 2025-09-18T16:10:25.564Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753816)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Gültig ab
-- Column: C_BPartner_Location.ValidFrom
-- 2025-09-18T16:10:25.692Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,576812,753817,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gültig ab inklusiv (erster Tag)',7,'D','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','N','N','N','N','N','Gültig ab',TO_TIMESTAMP('2025-09-18 16:10:25.566000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.694Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.695Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(617)
;

-- 2025-09-18T16:10:25.700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753817
;

-- 2025-09-18T16:10:25.700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753817)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Vorherige Adresse
-- Column: C_BPartner_Location.Previous_ID
-- 2025-09-18T16:10:25.826Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,576824,753818,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Adresse, die in offenen Auftragsdispos, Rechnungsdispos und Vertragesperioden durch die aktuelle ersetzt wird.',10,'D','Die Adresse, die in offenen Auftragsdispos, Rechnungsdispos und Vertragesperioden durch die aktuelle ersetzt wird.','Y','N','N','N','N','N','N','N','Vorherige Adresse',TO_TIMESTAMP('2025-09-18 16:10:25.703000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.827Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.828Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579830)
;

-- 2025-09-18T16:10:25.830Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753818
;

-- 2025-09-18T16:10:25.831Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753818)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Name editierbar
-- Column: C_BPartner_Location.IsNameReadWrite
-- 2025-09-18T16:10:25.962Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,578965,753819,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Fall nicht angehakt, wir der Name von metasfresh bestimmt',1,'D','Y','N','N','N','N','N','N','N','Name editierbar',TO_TIMESTAMP('2025-09-18 16:10:25.833000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:25.963Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753819 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:25.965Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580402)
;

-- 2025-09-18T16:10:25.967Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753819
;

-- 2025-09-18T16:10:25.967Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753819)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Einmalanschrift
-- Column: C_BPartner_Location.IsEphemeral
-- 2025-09-18T16:10:26.085Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,582649,753820,0,548422,TO_TIMESTAMP('2025-09-18 16:10:25.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.',1,'D','Y','N','N','N','N','N','N','N','Einmalanschrift',TO_TIMESTAMP('2025-09-18 16:10:25.969000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:26.086Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753820 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:26.087Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(580745)
;

-- 2025-09-18T16:10:26.089Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753820
;

-- 2025-09-18T16:10:26.090Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753820)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Umsatzsteuer ID
-- Column: C_BPartner_Location.VATaxID
-- 2025-09-18T16:10:26.216Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585129,753821,0,548422,TO_TIMESTAMP('2025-09-18 16:10:26.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,60,'D','Y','N','N','N','N','N','N','N','Umsatzsteuer ID',TO_TIMESTAMP('2025-09-18 16:10:26.092000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:26.218Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753821 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:26.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(502388)
;

-- 2025-09-18T16:10:26.222Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753821
;

-- 2025-09-18T16:10:26.223Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753821)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Lieferweg
-- Column: C_BPartner_Location.M_Shipper_ID
-- 2025-09-18T16:10:26.354Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590571,753822,0,548422,TO_TIMESTAMP('2025-09-18 16:10:26.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung',10,'D','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','N','N','N','N','N','Lieferweg',TO_TIMESTAMP('2025-09-18 16:10:26.225000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:26.355Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753822 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:26.356Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(455)
;

-- 2025-09-18T16:10:26.363Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753822
;

-- 2025-09-18T16:10:26.364Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753822)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Leitcode
-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-09-18T16:10:26.495Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590619,753823,0,548422,TO_TIMESTAMP('2025-09-18 16:10:26.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,10,'D','Y','N','N','N','N','N','N','N','Leitcode',TO_TIMESTAMP('2025-09-18 16:10:26.367000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:26.496Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753823 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:26.498Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583860)
;

-- 2025-09-18T16:10:26.500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753823
;

-- 2025-09-18T16:10:26.501Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753823)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Shipper Has Routingcode
-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-09-18T16:10:26.637Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,590620,753824,0,548422,TO_TIMESTAMP('2025-09-18 16:10:26.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,1,'D','Y','N','N','N','N','N','N','N','Shipper Has Routingcode',TO_TIMESTAMP('2025-09-18 16:10:26.503000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:10:26.638Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753824 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:10:26.639Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(583861)
;

-- 2025-09-18T16:10:26.641Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753824
;

-- 2025-09-18T16:10:26.642Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753824)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Rechnung Standard Adresse
-- Column: C_BPartner_Location.IsBillToDefault
-- 2025-09-18T16:13:17.360Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541706,753825,0,548422,0,TO_TIMESTAMP('2025-09-18 16:13:17.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Rechnung Standard Adresse',0,0,10,0,1,1,TO_TIMESTAMP('2025-09-18 16:13:17.145000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:13:17.362Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753825 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:13:17.364Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540413)
;

-- 2025-09-18T16:13:17.367Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753825
;

-- 2025-09-18T16:13:17.368Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753825)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Provisionsadresse
-- Column: C_BPartner_Location.IsCommissionTo
-- 2025-09-18T16:13:34.718Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541691,753826,0,548422,0,TO_TIMESTAMP('2025-09-18 16:13:34.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Provisionsabrechnungen werden hierhin geschickt',0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Provisionsadresse',0,0,20,0,1,1,TO_TIMESTAMP('2025-09-18 16:13:34.522000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:13:34.720Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753826 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:13:34.726Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540404)
;

-- 2025-09-18T16:13:34.732Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753826
;

-- 2025-09-18T16:13:34.735Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753826)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Provision Standard Adresse
-- Column: C_BPartner_Location.IsCommissionToDefault
-- 2025-09-18T16:13:42.191Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541703,753827,0,548422,0,TO_TIMESTAMP('2025-09-18 16:13:42.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Provision Standard Adresse',0,0,30,0,1,1,TO_TIMESTAMP('2025-09-18 16:13:42.012000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:13:42.193Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753827 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:13:42.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540410)
;

-- 2025-09-18T16:13:42.197Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753827
;

-- 2025-09-18T16:13:42.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753827)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Liefer Standard Adresse
-- Column: C_BPartner_Location.IsShipToDefault
-- 2025-09-18T16:14:22.755Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541705,753828,0,548422,0,TO_TIMESTAMP('2025-09-18 16:14:22.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Liefer Standard Adresse',0,0,40,0,1,1,TO_TIMESTAMP('2025-09-18 16:14:22.569000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:14:22.757Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753828 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:14:22.758Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540412)
;

-- 2025-09-18T16:14:22.761Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753828
;

-- 2025-09-18T16:14:22.762Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753828)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> isSubscriptionTo
-- Column: C_BPartner_Location.IsSubscriptionTo
-- 2025-09-18T16:14:36.613Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541692,753829,0,548422,0,TO_TIMESTAMP('2025-09-18 16:14:36.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'An diese Adresse werden Abos geschickt',0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'isSubscriptionTo',0,0,50,0,1,1,TO_TIMESTAMP('2025-09-18 16:14:36.415000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:14:36.614Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753829 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:14:36.615Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540405)
;

-- 2025-09-18T16:14:36.617Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753829
;

-- 2025-09-18T16:14:36.618Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753829)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Abo Standard Adresse
-- Column: C_BPartner_Location.IsSubscriptionToDefault
-- 2025-09-18T16:14:44.991Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,541704,753830,0,548422,0,TO_TIMESTAMP('2025-09-18 16:14:44.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Abo Standard Adresse',0,0,60,0,1,1,TO_TIMESTAMP('2025-09-18 16:14:44.814000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:14:44.993Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753830 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:14:44.994Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540411)
;

-- 2025-09-18T16:14:44.996Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753830
;

-- 2025-09-18T16:14:44.997Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753830)
;

-- Field: Geschäftspartner(123,D) -> One time transaction(548422,D) -> Migration_Key
-- Column: C_BPartner_Location.Migration_Key
-- 2025-09-18T16:15:00.147Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,549130,753831,0,548422,0,TO_TIMESTAMP('2025-09-18 16:14:59.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'When data is imported from a an external datasource, this element can be used to identify the data record',0,'U',0,0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Migration_Key',0,0,70,0,1,1,TO_TIMESTAMP('2025-09-18 16:14:59.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T16:15:00.148Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=753831 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2025-09-18T16:15:00.149Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542124)
;

-- 2025-09-18T16:15:00.152Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753831
;

-- 2025-09-18T16:15:00.153Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(753831)
;

-- Tab: Geschäftspartner(123,D) -> One time transaction(548422,D)
-- UI Section: main
-- 2025-09-18T16:15:46.588Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548422,546949,TO_TIMESTAMP('2025-09-18 16:15:46.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-18 16:15:46.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2025-09-18T16:15:46.590Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=546949 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main
-- UI Column: 10
-- 2025-09-18T16:15:52.555Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,548457,546949,TO_TIMESTAMP('2025-09-18 16:15:52.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2025-09-18 16:15:52.361000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10
-- UI Element Group: default
-- 2025-09-18T16:16:05.084Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,548457,553516,TO_TIMESTAMP('2025-09-18 16:16:04.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','default',10,'primary',TO_TIMESTAMP('2025-09-18 16:16:04.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Name Geschäftspartner
-- Column: C_BPartner_Location.BPartnerName
-- 2025-09-18T16:16:37.191Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753814,0,548422,553516,637135,'F',TO_TIMESTAMP('2025-09-18 16:16:36.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name Geschäftspartner',10,0,0,TO_TIMESTAMP('2025-09-18 16:16:36.944000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Name
-- Column: C_BPartner_Location.Name
-- 2025-09-18T16:16:44.289Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753794,0,548422,553516,637136,'F',TO_TIMESTAMP('2025-09-18 16:16:44.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Name',20,0,0,TO_TIMESTAMP('2025-09-18 16:16:44.032000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Name editierbar
-- Column: C_BPartner_Location.IsNameReadWrite
-- 2025-09-18T16:17:00.264Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753819,0,548422,553516,637137,'F',TO_TIMESTAMP('2025-09-18 16:17:00.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Fall nicht angehakt, wir der Name von metasfresh bestimmt','Y','N','N','Y','N','N','N',0,'Name editierbar',30,0,0,TO_TIMESTAMP('2025-09-18 16:17:00.070000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Anschrift
-- Column: C_BPartner_Location.C_Location_ID
-- 2025-09-18T16:17:26.293Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753793,0,548422,553516,637138,'F',TO_TIMESTAMP('2025-09-18 16:17:26.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Adresse oder Anschrift','Das Feld "Adresse" definiert die Adressangaben eines Standortes.','Y','N','N','Y','N','N','N',0,'Anschrift',40,0,0,TO_TIMESTAMP('2025-09-18 16:17:26.105000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Adresse
-- Column: C_BPartner_Location.Address
-- 2025-09-18T16:17:34.534Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753805,0,548422,553516,637139,'F',TO_TIMESTAMP('2025-09-18 16:17:34.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Anschrift','Y','N','N','Y','N','N','N',0,'Adresse',50,0,0,TO_TIMESTAMP('2025-09-18 16:17:34.368000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.GLN
-- Column: C_BPartner_Location.GLN
-- 2025-09-18T16:17:43.574Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753806,0,548422,553516,637140,'F',TO_TIMESTAMP('2025-09-18 16:17:43.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'GLN',60,0,0,TO_TIMESTAMP('2025-09-18 16:17:43.383000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Umsatzsteuer ID
-- Column: C_BPartner_Location.VATaxID
-- 2025-09-18T16:17:52.816Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753821,0,548422,553516,637141,'F',TO_TIMESTAMP('2025-09-18 16:17:52.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Umsatzsteuer ID',70,0,0,TO_TIMESTAMP('2025-09-18 16:17:52.626000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Aktiv
-- Column: C_BPartner_Location.IsActive
-- 2025-09-18T16:18:00.264Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753787,0,548422,553516,637142,'F',TO_TIMESTAMP('2025-09-18 16:18:00.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',80,0,0,TO_TIMESTAMP('2025-09-18 16:18:00.064000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Lieferstandard
-- Column: C_BPartner_Location.IsShipTo
-- 2025-09-18T16:18:18.697Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753801,0,548422,553516,637143,'F',TO_TIMESTAMP('2025-09-18 16:18:18.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Liefer-Adresse für den Geschäftspartner','Wenn "Liefer-Adresse" selektiert ist, wird diese Anschrift benutzt um Waren an den Kunden zu liefern oder Waren vom Lieferanten zu empfangen.','Y','N','N','Y','N','N','N',0,'Lieferstandard',90,0,0,TO_TIMESTAMP('2025-09-18 16:18:18.513000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Liefer Standard Adresse
-- Column: C_BPartner_Location.IsShipToDefault
-- 2025-09-18T16:18:38.797Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753828,0,548422,553516,637144,'F',TO_TIMESTAMP('2025-09-18 16:18:38.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Liefer Standard Adresse',100,0,0,TO_TIMESTAMP('2025-09-18 16:18:38.580000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Vorbelegung Rechnung
-- Column: C_BPartner_Location.IsBillTo
-- 2025-09-18T16:18:49.746Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753800,0,548422,553516,637145,'F',TO_TIMESTAMP('2025-09-18 16:18:49.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Rechnungs-Adresse für diesen Geschäftspartner','Wenn "Rechnungs-Adresse" slektiert ist, wird diese Anschrift verwendet um Rechnungen an einen Kunden zu senden oder von einem Lieferanten zu erhalten.','Y','N','N','Y','N','N','N',0,'Vorbelegung Rechnung',110,0,0,TO_TIMESTAMP('2025-09-18 16:18:49.526000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Rechnung Standard Adresse
-- Column: C_BPartner_Location.IsBillToDefault
-- 2025-09-18T16:18:57.817Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753825,0,548422,553516,637146,'F',TO_TIMESTAMP('2025-09-18 16:18:57.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Rechnung Standard Adresse',120,0,0,TO_TIMESTAMP('2025-09-18 16:18:57.627000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Abladeort
-- Column: C_BPartner_Location.IsHandOverLocation
-- 2025-09-18T16:19:08.646Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753807,0,548422,553516,637147,'F',TO_TIMESTAMP('2025-09-18 16:19:08.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Abladeort',130,0,0,TO_TIMESTAMP('2025-09-18 16:19:08.313000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Erstattungs-Adresse
-- Column: C_BPartner_Location.IsRemitTo
-- 2025-09-18T16:19:30.476Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753803,0,548422,553516,637148,'F',TO_TIMESTAMP('2025-09-18 16:19:30.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Erstattungs-Adresse für den Lieferanten','Wenn "Erstattungs-Adresse" selektiert ist, wird diese Adresse für Zahlungen an den Lieferanten verwendet.','Y','N','N','Y','N','N','N',0,'Erstattungs-Adresse',140,0,0,TO_TIMESTAMP('2025-09-18 16:19:30.256000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Lieferweg
-- Column: C_BPartner_Location.M_Shipper_ID
-- 2025-09-18T16:19:42.416Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753822,0,548422,553516,637149,'F',TO_TIMESTAMP('2025-09-18 16:19:42.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Methode oder Art der Warenlieferung','"Lieferweg" bezeichnet die Art der Warenlieferung.','Y','N','N','Y','N','N','N',0,'Lieferweg',150,0,0,TO_TIMESTAMP('2025-09-18 16:19:42.208000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Leitcode
-- Column: C_BPartner_Location.M_Shipper_RoutingCode_ID
-- 2025-09-18T16:19:52.255Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753823,0,548422,553516,637150,'F',TO_TIMESTAMP('2025-09-18 16:19:51.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Leitcode',160,0,0,TO_TIMESTAMP('2025-09-18 16:19:51.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Shipper Has Routingcode
-- Column: C_BPartner_Location.IsShipperHasRoutingcode
-- 2025-09-18T16:20:06.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753824,0,548422,553516,637151,'F',TO_TIMESTAMP('2025-09-18 16:20:05.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Shipper Has Routingcode',170,0,0,TO_TIMESTAMP('2025-09-18 16:20:05.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Replikations Standardwert
-- Column: C_BPartner_Location.IsReplicationLookupDefault
-- 2025-09-18T16:20:14.412Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753808,0,548422,553516,637152,'F',TO_TIMESTAMP('2025-09-18 16:20:14.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Falls bei einem Datenimport (z.B. EDI) ein Datensatz nicht eingedeutig zugeordnet werden kann, dann entscheidet dieses Feld darüber, welcher der in Frage kommenden Datensätze benutzt wird. Sollten mehre der in Frage kommenenden Datensätze als Replikations-Standard definiert sein, schlägt der nach wie vor fehl.','Y','N','N','Y','N','N','N',0,'Replikations Standardwert',180,0,0,TO_TIMESTAMP('2025-09-18 16:20:14.229000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Besuchsadresse
-- Column: C_BPartner_Location.VisitorsAddress
-- 2025-09-18T16:20:24.305Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753813,0,548422,553516,637153,'F',TO_TIMESTAMP('2025-09-18 16:20:23.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Besuchsadresse',190,0,0,TO_TIMESTAMP('2025-09-18 16:20:23.943000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Gültig ab
-- Column: C_BPartner_Location.ValidFrom
-- 2025-09-18T16:20:32.214Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753817,0,548422,553516,637154,'F',TO_TIMESTAMP('2025-09-18 16:20:32.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Gültig ab inklusiv (erster Tag)','"Gültig ab" bezeichnet den ersten Tag eines Gültigkeitzeitraumes.','Y','N','N','Y','N','N','N',0,'Gültig ab',200,0,0,TO_TIMESTAMP('2025-09-18 16:20:32.020000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Einmalanschrift
-- Column: C_BPartner_Location.IsEphemeral
-- 2025-09-18T16:20:43.300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753820,0,548422,553516,637155,'F',TO_TIMESTAMP('2025-09-18 16:20:43.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Einmalanschriften sind "flüchtige" Geschäftspartneradressen, die über die REST-API erstellt werden. Wenn eine Adresse als Einmalanschrift markiert ist, wird sie beim Import von Dokumenten in das metafresh-System verwendet. Sie steht aber nicht beim Erstellen neuer Dokumente in der metasfresh-Benutzeroberfläche zur Auswahl.','Y','N','N','Y','N','N','N',0,'Einmalanschrift',210,0,0,TO_TIMESTAMP('2025-09-18 16:20:43.095000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Rüstplatz-Nr.
-- Column: C_BPartner_Location.Setup_Place_No
-- 2025-09-18T16:20:56.956Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753815,0,548422,553516,637156,'F',TO_TIMESTAMP('2025-09-18 16:20:56.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Rüstplatz beim Logistik-Partner oder im eigenen Lager','Y','N','N','Y','N','N','N',0,'Rüstplatz-Nr.',220,0,0,TO_TIMESTAMP('2025-09-18 16:20:56.754000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Vorherige Adresse
-- Column: C_BPartner_Location.Previous_ID
-- 2025-09-18T16:21:07.515Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753818,0,548422,553516,637157,'F',TO_TIMESTAMP('2025-09-18 16:21:07.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Die Adresse, die in offenen Auftragsdispos, Rechnungsdispos und Vertragesperioden durch die aktuelle ersetzt wird.','Die Adresse, die in offenen Auftragsdispos, Rechnungsdispos und Vertragesperioden durch die aktuelle ersetzt wird.','Y','N','N','Y','N','N','N',0,'Vorherige Adresse',230,0,0,TO_TIMESTAMP('2025-09-18 16:21:07.320000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.eMail
-- Column: C_BPartner_Location.EMail
-- 2025-09-18T16:21:12.426Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753811,0,548422,553516,637158,'F',TO_TIMESTAMP('2025-09-18 16:21:12.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'EMail-Adresse','The Email Address is the Electronic Mail ID for this User and should be fully qualified (e.g. joe.smith@company.com). The Email Address is used to access the self service application functionality from the web.','Y','N','N','Y','N','N','N',0,'eMail',240,0,0,TO_TIMESTAMP('2025-09-18 16:21:12.307000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Telefon
-- Column: C_BPartner_Location.Phone
-- 2025-09-18T16:21:22.436Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753795,0,548422,553516,637159,'F',TO_TIMESTAMP('2025-09-18 16:21:22.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Beschreibt eine Telefon Nummer','Y','N','N','Y','N','N','N',0,'Telefon',250,0,0,TO_TIMESTAMP('2025-09-18 16:21:22.254000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Sektion
-- Column: C_BPartner_Location.AD_Org_ID
-- 2025-09-18T16:21:36.510Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753786,0,548422,553516,637160,'F',TO_TIMESTAMP('2025-09-18 16:21:36.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',260,0,0,TO_TIMESTAMP('2025-09-18 16:21:36.309000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Geschäftspartner(123,D) -> One time transaction(548422,D) -> main -> 10 -> default.Mandant
-- Column: C_BPartner_Location.AD_Client_ID
-- 2025-09-18T16:21:43.295Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,753785,0,548422,553516,637161,'F',TO_TIMESTAMP('2025-09-18 16:21:43.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',270,0,0,TO_TIMESTAMP('2025-09-18 16:21:43.106000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Run mode: SWING_CLIENT

-- Field: Neuer Anschrift(541941,D) -> Neuer Anschrift(548413,D) -> One time transaction
-- Column: C_BPartner_Location_QuickInput.IsOneTime
-- 2025-09-18T17:50:59.297Z
UPDATE AD_Field SET DefaultValue='Y',Updated=TO_TIMESTAMP('2025-09-18 17:50:59.296000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Field_ID=753608
;

-- UI Element: Neuer Anschrift(541941,D) -> Neuer Anschrift(548413,D) -> main -> 10 -> default.Geschäftspartner
-- Column: C_BPartner_Location_QuickInput.C_BPartner_ID
-- 2025-09-18T17:51:12.548Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=637028
;

-- 2025-09-18T17:51:12.558Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=753611
;

-- Field: Neuer Anschrift(541941,D) -> Neuer Anschrift(548413,D) -> Geschäftspartner
-- Column: C_BPartner_Location_QuickInput.C_BPartner_ID
-- 2025-09-18T17:51:12.561Z
DELETE FROM  AD_Field_Trl WHERE AD_Field_ID=753611
;

-- 2025-09-18T17:51:12.565Z
DELETE FROM AD_Field WHERE AD_Field_ID=753611
;

-- 2025-09-18T18:03:43.814Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,583982,0,TO_TIMESTAMP('2025-09-18 18:03:43.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Einmaladresse','Einmaladresse',TO_TIMESTAMP('2025-09-18 18:03:43.599000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2025-09-18T18:03:43.820Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=583982 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2025-09-18T18:03:59.806Z
UPDATE AD_Element_Trl SET Name='One-Time Address', PrintName='One-Time Address',Updated=TO_TIMESTAMP('2025-09-18 18:03:59.806000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Element_ID=583982 AND AD_Language='en_US'
;

-- 2025-09-18T18:03:59.807Z
UPDATE AD_Element base SET Name=trl.Name, PrintName=trl.PrintName, Updated=trl.Updated, UpdatedBy=trl.UpdatedBy FROM AD_Element_Trl trl  WHERE trl.AD_Element_ID=base.AD_Element_ID AND trl.AD_Language='en_US' AND trl.AD_Language=getBaseLanguage()
;

-- 2025-09-18T18:04:00.108Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(583982,'en_US')
;

-- Tab: Geschäftspartner(123,D) -> Einmaladresse
-- Table: C_BPartner_Location
-- 2025-09-18T18:04:27.884Z
UPDATE AD_Tab SET AD_Element_ID=583982, CommitWarning=NULL, Description=NULL, EntityType='D', Help=NULL, Name='Einmaladresse',Updated=TO_TIMESTAMP('2025-09-18 18:04:27.884000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Tab_ID=548422
;

-- 2025-09-18T18:04:27.886Z
UPDATE AD_Tab_Trl trl SET Name='Einmaladresse' WHERE AD_Tab_ID=548422 AND AD_Language='de_DE'
;

-- 2025-09-18T18:04:27.888Z
/* DDL */  select update_tab_translation_from_ad_element(583982)
;

-- 2025-09-18T18:04:27.891Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548422)
;

