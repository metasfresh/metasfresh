-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-14T09:53:52.588188Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Parent_Column_ID,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,563235,572726,0,547277,541144,541730,'Y',TO_TIMESTAMP('2023-11-14 11:53:52.31','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_AttachmentEntry_ReferencedRecord_v','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Dokumente',1349,'N',40,1,TO_TIMESTAMP('2023-11-14 11:53:52.31','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=203/*C_Project*/')
;

-- 2023-11-14T09:53:52.593186600Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547277 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-11-14T09:53:52.626687Z
/* DDL */  select update_tab_translation_from_ad_element(572726) 
;

-- 2023-11-14T09:53:52.639687300Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547277)
;

-- 2023-11-14T09:53:52.643248500Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 547277
;

-- 2023-11-14T09:53:52.644687200Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 547277, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 543282
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Erstellt
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Created
-- 2023-11-14T09:53:52.771807900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563230,721804,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:52.664','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',10,10,0,1,1,TO_TIMESTAMP('2023-11-14 11:53:52.664','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:52.773307600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721804 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:52.774807900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-11-14T09:53:52.797317900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721804
;

-- 2023-11-14T09:53:52.798306600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721804)
;

-- 2023-11-14T09:53:52.800806500Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721804
;

-- 2023-11-14T09:53:52.801310100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721804, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627867
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Erstellt durch
-- Column: AD_AttachmentEntry_ReferencedRecord_v.CreatedBy
-- 2023-11-14T09:53:52.891540400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563231,721805,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:52.802','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','Y','Y','N','N','N','N','N','Erstellt durch',20,20,0,1,1,TO_TIMESTAMP('2023-11-14 11:53:52.802','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:52.892540300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721805 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:52.894041700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-11-14T09:53:52.912050400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721805
;

-- 2023-11-14T09:53:52.913043500Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721805)
;

-- 2023-11-14T09:53:52.915041100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721805
;

-- 2023-11-14T09:53:52.915540Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721805, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627868
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-14T09:53:53.011056400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563225,721806,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:52.916','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',30,1,1,TO_TIMESTAMP('2023-11-14 11:53:52.916','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.013056400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721806 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.014554400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-11-14T09:53:53.088054500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721806
;

-- 2023-11-14T09:53:53.089056Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721806)
;

-- 2023-11-14T09:53:53.091555900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721806
;

-- 2023-11-14T09:53:53.092056Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721806, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627869
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-14T09:53:53.180935100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563226,721807,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.093','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','Y','N','N','N','N','N','N','Organisation',40,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.093','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.182434900Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721807 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.183434200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-11-14T09:53:53.242050300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721807
;

-- 2023-11-14T09:53:53.242936200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721807)
;

-- 2023-11-14T09:53:53.245435200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721807
;

-- 2023-11-14T09:53:53.245935100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721807, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627870
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> DB-Tabelle
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID
-- 2023-11-14T09:53:53.325375900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563227,721808,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.246','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',22,'D','The Database Table provides the information of the table definition',0,'Y','Y','N','N','N','N','N','N','DB-Tabelle',50,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.246','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.326371800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721808 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.327371300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-11-14T09:53:53.336447300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721808
;

-- 2023-11-14T09:53:53.337435100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721808)
;

-- 2023-11-14T09:53:53.339936600Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721808
;

-- 2023-11-14T09:53:53.340436400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721808, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627871
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Binärwert
-- Column: AD_AttachmentEntry_ReferencedRecord_v.BinaryData
-- 2023-11-14T09:53:53.451934400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563228,721809,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.341','YYYY-MM-DD HH24:MI:SS.US'),100,'Binärer Wert',0,'D','Das Feld "Binärwert" speichert binäre Werte.',0,'Y','N','N','N','N','N','N','N','Binärwert',60,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.341','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.452936700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721809 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.454436900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(174) 
;

-- 2023-11-14T09:53:53.460436800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721809
;

-- 2023-11-14T09:53:53.460936800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721809)
;

-- 2023-11-14T09:53:53.463934800Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721809
;

-- 2023-11-14T09:53:53.464435200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721809, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627872
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Content type
-- Column: AD_AttachmentEntry_ReferencedRecord_v.ContentType
-- 2023-11-14T09:53:53.545990300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563229,721810,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.465','YYYY-MM-DD HH24:MI:SS.US'),100,60,'D',0,'Y','Y','N','N','N','N','N','N','Content type',70,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.465','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.547489300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721810 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.548489300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543391) 
;

-- 2023-11-14T09:53:53.551488100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721810
;

-- 2023-11-14T09:53:53.552490100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721810)
;

-- 2023-11-14T09:53:53.554501300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721810
;

-- 2023-11-14T09:53:53.555526Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721810, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627873
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Beschreibung
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Description
-- 2023-11-14T09:53:53.639539800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563232,721811,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.556','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D',0,'Y','Y','N','N','N','N','N','N','Beschreibung',80,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.556','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.640538200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721811 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.642039400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-11-14T09:53:53.665040900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721811
;

-- 2023-11-14T09:53:53.666944200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721811)
;

-- 2023-11-14T09:53:53.669244Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721811
;

-- 2023-11-14T09:53:53.669743500Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721811, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627874
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Dateiname
-- Column: AD_AttachmentEntry_ReferencedRecord_v.FileName
-- 2023-11-14T09:53:53.757691900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563233,721812,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.67','YYYY-MM-DD HH24:MI:SS.US'),100,'Name of the local file or URL',2000,'D','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)',0,'Y','Y','N','N','N','N','N','N','Dateiname',90,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.67','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.758692400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721812 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.760191900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2023-11-14T09:53:53.763191300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721812
;

-- 2023-11-14T09:53:53.764192100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721812)
;

-- 2023-11-14T09:53:53.766192700Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721812
;

-- 2023-11-14T09:53:53.766694300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721812, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627875
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Aktiv
-- Column: AD_AttachmentEntry_ReferencedRecord_v.IsActive
-- 2023-11-14T09:53:53.858158800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563234,721813,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.767','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','Y','N','N','N','N','N','N','Aktiv',100,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.767','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.859158300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721813 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.860658700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-11-14T09:53:53.905424400Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721813
;

-- 2023-11-14T09:53:53.906158100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721813)
;

-- 2023-11-14T09:53:53.908158800Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721813
;

-- 2023-11-14T09:53:53.908659300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721813, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627876
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Datensatz-ID
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Record_ID
-- 2023-11-14T09:53:53.989897800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563235,721814,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:53.909','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',22,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','N','N','N','N','N','N','Datensatz-ID',110,1,1,TO_TIMESTAMP('2023-11-14 11:53:53.909','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:53.990897100Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721814 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:53.992396800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2023-11-14T09:53:53.998396300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721814
;

-- 2023-11-14T09:53:53.998896100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721814)
;

-- 2023-11-14T09:53:54.000894700Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721814
;

-- 2023-11-14T09:53:54.001895400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721814, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627877
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Art
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Type
-- 2023-11-14T09:53:54.082050200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563236,721815,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:54.002','YYYY-MM-DD HH24:MI:SS.US'),100,'',1,'D','',0,'Y','Y','N','N','N','N','N','N','Art',120,1,1,TO_TIMESTAMP('2023-11-14 11:53:54.002','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:54.083051600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721815 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:54.084550200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2023-11-14T09:53:54.089050500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721815
;

-- 2023-11-14T09:53:54.089552900Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721815)
;

-- 2023-11-14T09:53:54.092050200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721815
;

-- 2023-11-14T09:53:54.092550500Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721815, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627878
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> AD_AttachmentEntry_ReferencedRecord_v
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_AttachmentEntry_ReferencedRecord_v_ID
-- 2023-11-14T09:53:54.184551400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563223,721816,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:54.093','YYYY-MM-DD HH24:MI:SS.US'),100,22,'D',0,'Y','N','N','N','N','N','N','N','AD_AttachmentEntry_ReferencedRecord_v',130,1,1,TO_TIMESTAMP('2023-11-14 11:53:54.093','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:54.185549400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721816 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:54.187050900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544432) 
;

-- 2023-11-14T09:53:54.190053Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721816
;

-- 2023-11-14T09:53:54.191050400Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721816)
;

-- 2023-11-14T09:53:54.193549300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721816
;

-- 2023-11-14T09:53:54.194595800Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721816, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627879
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> URL
-- Column: AD_AttachmentEntry_ReferencedRecord_v.URL
-- 2023-11-14T09:53:54.274699800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563239,721817,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:54.195','YYYY-MM-DD HH24:MI:SS.US'),100,'Z.B. http://www.metasfresh.com',2000,'D','',0,'Y','Y','N','N','N','N','N','N','URL',140,1,1,TO_TIMESTAMP('2023-11-14 11:53:54.195','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:54.275700700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721817 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:54.277201Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(983) 
;

-- 2023-11-14T09:53:54.280700100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721817
;

-- 2023-11-14T09:53:54.281701600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721817)
;

-- 2023-11-14T09:53:54.283701300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721817
;

-- 2023-11-14T09:53:54.284722800Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721817, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627880
;

-- Field: Approval Requests(541730,D) -> Dokumente(547277,D) -> Editierbarer Datensatz
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_AttachmentEntry_ID
-- 2023-11-14T09:53:54.376569600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563224,721818,575858,0,547277,0,TO_TIMESTAMP('2023-11-14 11:53:54.286','YYYY-MM-DD HH24:MI:SS.US'),100,'Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.',10,'D',0,'Y','N','N','N','N','N','N','N','Editierbarer Datensatz',150,1,1,TO_TIMESTAMP('2023-11-14 11:53:54.286','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-14T09:53:54.378048500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721818 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-14T09:53:54.379564400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575858) 
;

-- 2023-11-14T09:53:54.384058500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721818
;

-- 2023-11-14T09:53:54.385549100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721818)
;

-- 2023-11-14T09:53:54.388050100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721818
;

-- 2023-11-14T09:53:54.389050900Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721818, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 627881
;

-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-14T09:54:08.322568800Z
UPDATE AD_Tab SET Parent_Column_ID=NULL, SeqNo=20,Updated=TO_TIMESTAMP('2023-11-14 11:54:08.322','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547277
;

-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-14T09:55:16.386797700Z
UPDATE AD_Tab SET WhereClause='AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=259/*C_Order*/',Updated=TO_TIMESTAMP('2023-11-14 11:55:16.386','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547277
;

-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-14T09:55:29.533220700Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2023-11-14 11:55:29.532','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547277
;

-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-11-14T16:06:02.330694400Z
UPDATE AD_Column SET FieldLength=20,AD_Reference_ID = 28, Updated=TO_TIMESTAMP('2023-11-14 18:06:02.33','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587361
;

-- Column: AD_WF_Approval_Request.Record_ID
-- 2023-11-14T16:08:17.039882100Z
UPDATE AD_Column SET IsExcludeFromZoomTargets='N',Updated=TO_TIMESTAMP('2023-11-14 18:08:17.039','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Column_ID=587361
;





















-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- EntityType: D
-- -- 2023-11-14T16:14:03.224021500Z
-- UPDATE AD_Table_Process SET WEBUI_IncludedTabTopAction='Y',Updated=TO_TIMESTAMP('2023-11-14 18:14:03.224','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;

-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- EntityType: D
-- -- 2023-11-14T16:23:21.606544500Z
-- UPDATE AD_Table_Process SET WEBUI_ViewAction='N', WEBUI_ViewQuickAction='N',Updated=TO_TIMESTAMP('2023-11-14 18:23:21.606','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;

-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- EntityType: D
-- -- 2023-11-14T16:24:08.747718500Z
-- UPDATE AD_Table_Process SET WEBUI_ViewAction='Y', WEBUI_ViewQuickAction='Y',Updated=TO_TIMESTAMP('2023-11-14 18:24:08.747','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;
















-- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- UI Section: main
-- 2023-11-14T16:25:17.590923900Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547277,545870,TO_TIMESTAMP('2023-11-14 18:25:16.602','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-11-14 18:25:16.602','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-11-14T16:25:17.592424500Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545870 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Approval Requests(541730,D) -> Dokumente(547277,D) -> main
-- UI Column: 10
-- 2023-11-14T16:25:17.738753500Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547164,545870,TO_TIMESTAMP('2023-11-14 18:25:17.629','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-11-14 18:25:17.629','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10
-- UI Element Group: default
-- 2023-11-14T16:25:17.878354200Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547164,551302,TO_TIMESTAMP('2023-11-14 18:25:17.779','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-11-14 18:25:17.779','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Erstellt
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Created
-- 2023-11-14T16:25:18.011081700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721804,0,547277,551302,621269,'F',TO_TIMESTAMP('2023-11-14 18:25:17.919','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','Y','N','Erstellt',0,10,0,TO_TIMESTAMP('2023-11-14 18:25:17.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_AttachmentEntry_ReferencedRecord_v.CreatedBy
-- 2023-11-14T16:25:18.107826800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721805,0,547277,551302,621270,'F',TO_TIMESTAMP('2023-11-14 18:25:18.014','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','Y','N','Erstellt durch',0,20,0,TO_TIMESTAMP('2023-11-14 18:25:18.014','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-14T16:26:39.934368Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721806,0,547277,551302,621271,'F',TO_TIMESTAMP('2023-11-14 18:26:39.813','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',10,0,0,TO_TIMESTAMP('2023-11-14 18:26:39.813','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID
-- 2023-11-14T16:26:58.621323200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721808,0,547277,551302,621272,'F',TO_TIMESTAMP('2023-11-14 18:26:58.498','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','Y','N','N','DB-Tabelle',20,0,0,TO_TIMESTAMP('2023-11-14 18:26:58.498','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Datensatz-ID
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Record_ID
-- 2023-11-14T16:27:07.478603500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721814,0,547277,551302,621273,'F',TO_TIMESTAMP('2023-11-14 18:27:07.353','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','Y','N','N','Datensatz-ID',30,0,0,TO_TIMESTAMP('2023-11-14 18:27:07.353','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Datensatz-ID
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Record_ID
-- 2023-11-14T16:38:28.999437600Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=551302, IsDisplayed='Y', SeqNo=40,Updated=TO_TIMESTAMP('2023-11-14 18:38:28.999','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621273
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-14T16:38:43.398447100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721807,0,547277,551302,621274,'F',TO_TIMESTAMP('2023-11-14 18:38:43.245','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Organisation',50,0,0,TO_TIMESTAMP('2023-11-14 18:38:43.245','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Content type
-- Column: AD_AttachmentEntry_ReferencedRecord_v.ContentType
-- 2023-11-14T16:38:51.874045400Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721810,0,547277,551302,621275,'F',TO_TIMESTAMP('2023-11-14 18:38:51.757','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Content type',60,0,0,TO_TIMESTAMP('2023-11-14 18:38:51.757','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Beschreibung
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Description
-- 2023-11-14T16:38:59.906850200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721811,0,547277,551302,621276,'F',TO_TIMESTAMP('2023-11-14 18:38:59.786','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','Y','N','N','Beschreibung',70,0,0,TO_TIMESTAMP('2023-11-14 18:38:59.786','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Dateiname
-- Column: AD_AttachmentEntry_ReferencedRecord_v.FileName
-- 2023-11-14T16:39:12.078398300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721812,0,547277,551302,621277,'F',TO_TIMESTAMP('2023-11-14 18:39:11.931','YYYY-MM-DD HH24:MI:SS.US'),100,'Name of the local file or URL','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','Y','N','N','Dateiname',80,0,0,TO_TIMESTAMP('2023-11-14 18:39:11.931','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Aktiv
-- Column: AD_AttachmentEntry_ReferencedRecord_v.IsActive
-- 2023-11-14T16:39:20.547420800Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721813,0,547277,551302,621278,'F',TO_TIMESTAMP('2023-11-14 18:39:20.419','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',90,0,0,TO_TIMESTAMP('2023-11-14 18:39:20.419','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Art
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Type
-- 2023-11-14T16:39:28.347837700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721815,0,547277,551302,621279,'F',TO_TIMESTAMP('2023-11-14 18:39:28.174','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','Y','N','N','Art',100,0,0,TO_TIMESTAMP('2023-11-14 18:39:28.174','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.URL
-- Column: AD_AttachmentEntry_ReferencedRecord_v.URL
-- 2023-11-14T16:39:34.211322Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721817,0,547277,551302,621280,'F',TO_TIMESTAMP('2023-11-14 18:39:34.061','YYYY-MM-DD HH24:MI:SS.US'),100,'Z.B. http://www.metasfresh.com','','Y','N','Y','N','N','URL',110,0,0,TO_TIMESTAMP('2023-11-14 18:39:34.061','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Dateiname
-- Column: AD_AttachmentEntry_ReferencedRecord_v.FileName
-- 2023-11-14T16:40:37.076079200Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-11-14 18:40:37.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621277
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Beschreibung
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Description
-- 2023-11-14T16:40:37.082079500Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-11-14 18:40:37.081','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621276
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Datensatz-ID
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Record_ID
-- 2023-11-14T16:40:37.087619100Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-11-14 18:40:37.087','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621273
;





















-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- EntityType: D
-- -- 2023-11-15T09:19:13.063650500Z
-- UPDATE AD_Table_Process SET WEBUI_DocumentAction='N', WEBUI_ViewAction='N', WEBUI_ViewQuickAction='N',Updated=TO_TIMESTAMP('2023-11-15 11:19:13.063','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;

-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- -- Window: Approval Requests(541730,D)
-- -- EntityType: D
-- -- 2023-11-15T09:21:40.154975300Z
-- UPDATE AD_Table_Process SET AD_Tab_ID=547277, AD_Window_ID=541730,Updated=TO_TIMESTAMP('2023-11-15 11:21:40.154','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;

-- -- Value: AD_AttachmentEntry_ReferencedRecord_v_Download
-- -- Classname: de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download
-- -- 2023-11-15T09:31:21.273735900Z
-- UPDATE AD_Process SET AllowProcessReRun='Y',Updated=TO_TIMESTAMP('2023-11-15 11:31:21.272','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Process_ID=541033
-- ;

-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- -- Window: Approval Requests(541730,D)
-- -- EntityType: D
-- -- 2023-11-15T09:43:32.213939200Z
-- UPDATE AD_Table_Process SET WEBUI_ViewAction='Y',Updated=TO_TIMESTAMP('2023-11-15 11:43:32.213','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;

-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- -- Window: Approval Requests(541730,D)
-- -- EntityType: D
-- -- 2023-11-15T09:44:21.099802900Z
-- UPDATE AD_Table_Process SET WEBUI_ViewAction='N', WEBUI_ViewQuickAction='Y', WEBUI_ViewQuickAction_Default='Y',Updated=TO_TIMESTAMP('2023-11-15 11:44:21.099','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;

-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- Tab: Approval Requests(541730,D) -> Dokumente(547277,D)
-- -- Window: Approval Requests(541730,D)
-- -- EntityType: D
-- -- 2023-11-15T09:45:58.076434200Z
-- UPDATE AD_Table_Process SET WEBUI_DocumentAction='Y', WEBUI_ViewAction='Y',Updated=TO_TIMESTAMP('2023-11-15 11:45:58.076','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
;
























-- Tab: Meine Zulassungen(541736,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-15T15:01:50.618152600Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryIfNoFilters,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,563235,572726,0,547282,541144,541736,'Y',TO_TIMESTAMP('2023-11-15 17:01:50.478','YYYY-MM-DD HH24:MI:SS.US'),100,'D','N','N','A','AD_AttachmentEntry_ReferencedRecord_v','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','Y','N','N','N',0,'Dokumente','N',20,1,TO_TIMESTAMP('2023-11-15 17:01:50.478','YYYY-MM-DD HH24:MI:SS.US'),100,'AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=259/*C_Order*/')
;

-- 2023-11-15T15:01:50.620151200Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=547282 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-11-15T15:01:50.623151Z
/* DDL */  select update_tab_translation_from_ad_element(572726) 
;

-- 2023-11-15T15:01:50.629651900Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(547282)
;

-- 2023-11-15T15:01:50.639152Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 547282
;

-- 2023-11-15T15:01:50.640151200Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 547282, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 547277
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Erstellt
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Created
-- 2023-11-15T15:01:50.746808500Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563230,721857,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:50.651','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'D','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','N','Y','Y','N','N','N','N','N','Erstellt',10,10,0,1,1,TO_TIMESTAMP('2023-11-15 17:01:50.651','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:50.749807200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721857 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:50.753308Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-11-15T15:01:51.157076500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721857
;

-- 2023-11-15T15:01:51.157917700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721857)
;

-- 2023-11-15T15:01:51.160417200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721857
;

-- 2023-11-15T15:01:51.161416700Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721857, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721804
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Erstellt durch
-- Column: AD_AttachmentEntry_ReferencedRecord_v.CreatedBy
-- 2023-11-15T15:01:51.261761100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563231,721858,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:51.162','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat',0,'D','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.',0,'Y','N','Y','Y','N','N','N','N','N','Erstellt durch',20,20,0,1,1,TO_TIMESTAMP('2023-11-15 17:01:51.162','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:51.263262200Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721858 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:51.264261200Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(246) 
;

-- 2023-11-15T15:01:51.401960200Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721858
;

-- 2023-11-15T15:01:51.403383800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721858)
;

-- 2023-11-15T15:01:51.406384600Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721858
;

-- 2023-11-15T15:01:51.406883800Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721858, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721805
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-15T15:01:51.517975400Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563225,721859,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:51.407','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.',22,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','N','Y','N','Mandant',30,1,1,TO_TIMESTAMP('2023-11-15 17:01:51.407','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:51.518976300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721859 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:51.520976700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-11-15T15:01:52.626804800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721859
;

-- 2023-11-15T15:01:52.627304300Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721859)
;

-- 2023-11-15T15:01:52.629803500Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721859
;

-- 2023-11-15T15:01:52.630303200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721859, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721806
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-15T15:01:52.732303300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563226,721860,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:52.631','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten',22,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','Y','N','N','N','N','N','N','Organisation',40,1,1,TO_TIMESTAMP('2023-11-15 17:01:52.631','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:52.733304500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721860 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:52.734803400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-11-15T15:01:53.743626600Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721860
;

-- 2023-11-15T15:01:53.744627600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721860)
;

-- 2023-11-15T15:01:53.747124200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721860
;

-- 2023-11-15T15:01:53.747625800Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721860, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721807
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> DB-Tabelle
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID
-- 2023-11-15T15:01:53.845139200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563227,721861,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:53.748','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information',22,'D','The Database Table provides the information of the table definition',0,'Y','N','Y','N','N','N','N','N','N','DB-Tabelle',50,1,1,TO_TIMESTAMP('2023-11-15 17:01:53.748','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:53.846640600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721861 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:53.847641300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126) 
;

-- 2023-11-15T15:01:53.954625700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721861
;

-- 2023-11-15T15:01:53.955125600Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721861)
;

-- 2023-11-15T15:01:53.957625200Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721861
;

-- 2023-11-15T15:01:53.958626200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721861, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721808
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Binärwert
-- Column: AD_AttachmentEntry_ReferencedRecord_v.BinaryData
-- 2023-11-15T15:01:54.062816Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563228,721862,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:53.959','YYYY-MM-DD HH24:MI:SS.US'),100,'Binärer Wert',0,'D','Das Feld "Binärwert" speichert binäre Werte.',0,'Y','N','N','N','N','N','N','N','N','Binärwert',60,1,1,TO_TIMESTAMP('2023-11-15 17:01:53.959','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:54.063817500Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721862 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:54.065316900Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(174) 
;

-- 2023-11-15T15:01:54.073816100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721862
;

-- 2023-11-15T15:01:54.074819100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721862)
;

-- 2023-11-15T15:01:54.078815900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721862
;

-- 2023-11-15T15:01:54.079316200Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721862, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721809
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Content type
-- Column: AD_AttachmentEntry_ReferencedRecord_v.ContentType
-- 2023-11-15T15:01:54.187696600Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563229,721863,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:54.08','YYYY-MM-DD HH24:MI:SS.US'),100,60,'D',0,'Y','N','Y','N','N','N','N','N','N','Content type',70,1,1,TO_TIMESTAMP('2023-11-15 17:01:54.08','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:54.188696800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721863 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:54.190196800Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(543391) 
;

-- 2023-11-15T15:01:54.193697800Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721863
;

-- 2023-11-15T15:01:54.194697100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721863)
;

-- 2023-11-15T15:01:54.196696100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721863
;

-- 2023-11-15T15:01:54.197196400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721863, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721810
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Beschreibung
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Description
-- 2023-11-15T15:01:54.287925900Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563232,721864,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:54.198','YYYY-MM-DD HH24:MI:SS.US'),100,2000,'D',0,'Y','N','Y','N','N','N','N','N','N','Beschreibung',80,1,1,TO_TIMESTAMP('2023-11-15 17:01:54.198','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:54.289428700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721864 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:54.290424400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275) 
;

-- 2023-11-15T15:01:54.437108300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721864
;

-- 2023-11-15T15:01:54.438104200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721864)
;

-- 2023-11-15T15:01:54.440604900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721864
;

-- 2023-11-15T15:01:54.441603400Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721864, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721811
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Dateiname
-- Column: AD_AttachmentEntry_ReferencedRecord_v.FileName
-- 2023-11-15T15:01:54.550430800Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563233,721865,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:54.442','YYYY-MM-DD HH24:MI:SS.US'),100,'Name of the local file or URL',2000,'D','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)',0,'Y','N','Y','N','N','N','N','N','N','Dateiname',90,1,1,TO_TIMESTAMP('2023-11-15 17:01:54.442','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:54.551930600Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721865 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:54.552930400Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2295) 
;

-- 2023-11-15T15:01:54.563473700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721865
;

-- 2023-11-15T15:01:54.564431Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721865)
;

-- 2023-11-15T15:01:54.566431300Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721865
;

-- 2023-11-15T15:01:54.566931100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721865, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721812
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Aktiv
-- Column: AD_AttachmentEntry_ReferencedRecord_v.IsActive
-- 2023-11-15T15:01:54.664157Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563234,721866,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:54.568','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','Y','N','N','N','N','N','N','Aktiv',100,1,1,TO_TIMESTAMP('2023-11-15 17:01:54.568','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:54.665156400Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721866 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:54.666656100Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-11-15T15:01:54.944103900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721866
;

-- 2023-11-15T15:01:54.945103800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721866)
;

-- 2023-11-15T15:01:54.948101900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721866
;

-- 2023-11-15T15:01:54.949101100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721866, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721813
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Datensatz-ID
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Record_ID
-- 2023-11-15T15:01:55.047102200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563235,721867,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:54.95','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID',22,'D','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','N','Y','N','N','N','N','N','N','Datensatz-ID',110,1,1,TO_TIMESTAMP('2023-11-15 17:01:54.95','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:55.048603300Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721867 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:55.049602500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538) 
;

-- 2023-11-15T15:01:55.106103700Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721867
;

-- 2023-11-15T15:01:55.107101100Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721867)
;

-- 2023-11-15T15:01:55.110604Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721867
;

-- 2023-11-15T15:01:55.111604300Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721867, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721814
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Art
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Type
-- 2023-11-15T15:01:55.212601300Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563236,721868,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:55.113','YYYY-MM-DD HH24:MI:SS.US'),100,'',1,'D','',0,'Y','N','Y','N','N','N','N','N','N','Art',120,1,1,TO_TIMESTAMP('2023-11-15 17:01:55.113','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:55.213602800Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721868 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:55.214601600Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(600) 
;

-- 2023-11-15T15:01:55.227104300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721868
;

-- 2023-11-15T15:01:55.228295700Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721868)
;

-- 2023-11-15T15:01:55.230602600Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721868
;

-- 2023-11-15T15:01:55.231101900Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721868, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721815
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> AD_AttachmentEntry_ReferencedRecord_v
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_AttachmentEntry_ReferencedRecord_v_ID
-- 2023-11-15T15:01:55.332104200Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563223,721869,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:55.232','YYYY-MM-DD HH24:MI:SS.US'),100,22,'D',0,'Y','N','N','N','N','N','N','N','N','AD_AttachmentEntry_ReferencedRecord_v',130,1,1,TO_TIMESTAMP('2023-11-15 17:01:55.232','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:55.333104Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721869 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:55.334607300Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544432) 
;

-- 2023-11-15T15:01:55.339104300Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721869
;

-- 2023-11-15T15:01:55.340104800Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721869)
;

-- 2023-11-15T15:01:55.342143900Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721869
;

-- 2023-11-15T15:01:55.343104100Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721869, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721816
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> URL
-- Column: AD_AttachmentEntry_ReferencedRecord_v.URL
-- 2023-11-15T15:01:55.451507Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563239,721870,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:55.344','YYYY-MM-DD HH24:MI:SS.US'),100,'Z.B. http://www.metasfresh.com',2000,'D','',0,'Y','N','Y','N','N','N','N','N','N','URL',140,1,1,TO_TIMESTAMP('2023-11-15 17:01:55.344','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:55.452508700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721870 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:55.454008700Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(983) 
;

-- 2023-11-15T15:01:55.473743500Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721870
;

-- 2023-11-15T15:01:55.474508Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721870)
;

-- 2023-11-15T15:01:55.477008100Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721870
;

-- 2023-11-15T15:01:55.478008500Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721870, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721817
;

-- Field: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> Editierbarer Datensatz
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_AttachmentEntry_ID
-- 2023-11-15T15:01:55.575507100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Name_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsAlwaysUpdateable,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,563224,721871,575858,0,547282,0,TO_TIMESTAMP('2023-11-15 17:01:55.479','YYYY-MM-DD HH24:MI:SS.US'),100,'Über dieses Feld kann man zu einer editierbaren Version des Datensatzes springen.',10,'D',0,'Y','N','N','N','N','N','N','N','N','Editierbarer Datensatz',150,1,1,TO_TIMESTAMP('2023-11-15 17:01:55.479','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- 2023-11-15T15:01:55.576507700Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=721871 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-11-15T15:01:55.577507500Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(575858) 
;

-- 2023-11-15T15:01:55.581509100Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=721871
;

-- 2023-11-15T15:01:55.582008200Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(721871)
;

-- 2023-11-15T15:01:55.585008Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 721871
;

-- 2023-11-15T15:01:55.586018800Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 721871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 721818
;

-- Tab: Meine Zulassungen(541736,D) -> Dokumente(547282,D)
-- UI Section: main
-- 2023-11-15T15:01:55.698009100Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,547282,545871,TO_TIMESTAMP('2023-11-15 17:01:55.593','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-11-15 17:01:55.593','YYYY-MM-DD HH24:MI:SS.US'),100,'main')
;

-- 2023-11-15T15:01:55.699006900Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545871 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-11-15T15:01:55.703507900Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545871
;

-- 2023-11-15T15:01:55.706508300Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545871, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545870
;

-- UI Section: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main
-- UI Column: 10
-- 2023-11-15T15:01:55.803507300Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,547165,545871,TO_TIMESTAMP('2023-11-15 17:01:55.711','YYYY-MM-DD HH24:MI:SS.US'),100,'Y',10,TO_TIMESTAMP('2023-11-15 17:01:55.711','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Column: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10
-- UI Element Group: default
-- 2023-11-15T15:01:55.910009Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,547165,551304,TO_TIMESTAMP('2023-11-15 17:01:55.808','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-11-15 17:01:55.808','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Erstellt
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Created
-- 2023-11-15T15:01:56.036008100Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721857,0,547282,551304,621302,'F',TO_TIMESTAMP('2023-11-15 17:01:55.919','YYYY-MM-DD HH24:MI:SS.US'),100,'Datum, an dem dieser Eintrag erstellt wurde','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','Y','N','N','Erstellt',10,10,0,TO_TIMESTAMP('2023-11-15 17:01:55.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_AttachmentEntry_ReferencedRecord_v.CreatedBy
-- 2023-11-15T15:01:56.157008700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721858,0,547282,551304,621303,'F',TO_TIMESTAMP('2023-11-15 17:01:56.044','YYYY-MM-DD HH24:MI:SS.US'),100,'Nutzer, der diesen Eintrag erstellt hat','Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','Y','N','N','Erstellt durch',20,20,0,TO_TIMESTAMP('2023-11-15 17:01:56.044','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-15T15:01:56.277006700Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721859,0,547282,551304,621304,'F',TO_TIMESTAMP('2023-11-15 17:01:56.163','YYYY-MM-DD HH24:MI:SS.US'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N','Mandant',10,0,0,TO_TIMESTAMP('2023-11-15 17:01:56.163','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID
-- 2023-11-15T15:01:56.382210300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721861,0,547282,551304,621305,'F',TO_TIMESTAMP('2023-11-15 17:01:56.281','YYYY-MM-DD HH24:MI:SS.US'),100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N','DB-Tabelle',20,0,0,TO_TIMESTAMP('2023-11-15 17:01:56.281','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Datensatz-ID
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Record_ID
-- 2023-11-15T15:01:56.488479600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721867,0,547282,551304,621306,'F',TO_TIMESTAMP('2023-11-15 17:01:56.385','YYYY-MM-DD HH24:MI:SS.US'),100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','Y','N','N','Datensatz-ID',40,50,0,TO_TIMESTAMP('2023-11-15 17:01:56.385','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-15T15:01:56.597117200Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721860,0,547282,551304,621307,'F',TO_TIMESTAMP('2023-11-15 17:01:56.492','YYYY-MM-DD HH24:MI:SS.US'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N','Organisation',50,0,0,TO_TIMESTAMP('2023-11-15 17:01:56.492','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Content type
-- Column: AD_AttachmentEntry_ReferencedRecord_v.ContentType
-- 2023-11-15T15:01:56.703152900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721863,0,547282,551304,621308,'F',TO_TIMESTAMP('2023-11-15 17:01:56.6','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','N','N','N','Content type',60,0,0,TO_TIMESTAMP('2023-11-15 17:01:56.6','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Beschreibung
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Description
-- 2023-11-15T15:01:56.812376300Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721864,0,547282,551304,621309,'F',TO_TIMESTAMP('2023-11-15 17:01:56.706','YYYY-MM-DD HH24:MI:SS.US'),100,'Y','N','N','Y','Y','N','N','Beschreibung',70,40,0,TO_TIMESTAMP('2023-11-15 17:01:56.706','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Dateiname
-- Column: AD_AttachmentEntry_ReferencedRecord_v.FileName
-- 2023-11-15T15:01:56.915504900Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721865,0,547282,551304,621310,'F',TO_TIMESTAMP('2023-11-15 17:01:56.815','YYYY-MM-DD HH24:MI:SS.US'),100,'Name of the local file or URL','Name of a file in the local directory space - or URL (file://.., http://.., ftp://..)','Y','N','N','Y','Y','N','N','Dateiname',80,30,0,TO_TIMESTAMP('2023-11-15 17:01:56.815','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Aktiv
-- Column: AD_AttachmentEntry_ReferencedRecord_v.IsActive
-- 2023-11-15T15:01:57.027521Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721866,0,547282,551304,621311,'F',TO_TIMESTAMP('2023-11-15 17:01:56.919','YYYY-MM-DD HH24:MI:SS.US'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N','Aktiv',90,0,0,TO_TIMESTAMP('2023-11-15 17:01:56.919','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Art
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Type
-- 2023-11-15T15:01:57.182639600Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721868,0,547282,551304,621312,'F',TO_TIMESTAMP('2023-11-15 17:01:57.031','YYYY-MM-DD HH24:MI:SS.US'),100,'','','Y','N','N','Y','N','N','N','Art',100,0,0,TO_TIMESTAMP('2023-11-15 17:01:57.031','YYYY-MM-DD HH24:MI:SS.US'),100)
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.URL
-- Column: AD_AttachmentEntry_ReferencedRecord_v.URL
-- 2023-11-15T15:01:57.301132500Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,721870,0,547282,551304,621313,'F',TO_TIMESTAMP('2023-11-15 17:01:57.19','YYYY-MM-DD HH24:MI:SS.US'),100,'Z.B. http://www.metasfresh.com','','Y','N','N','Y','N','N','N','URL',110,0,0,TO_TIMESTAMP('2023-11-15 17:01:57.19','YYYY-MM-DD HH24:MI:SS.US'),100)
;






-- -- Process: AD_AttachmentEntry_ReferencedRecord_v_Download(de.metas.attachments.process.AD_AttachmentEntry_ReferencedRecord_v_Download)
-- -- Table: AD_AttachmentEntry_ReferencedRecord_v
-- -- EntityType: D
-- -- 2023-11-15T16:31:41.775559200Z
-- UPDATE AD_Table_Process SET AD_Tab_ID=NULL, AD_Window_ID=NULL, WEBUI_IncludedTabTopAction='N',Updated=TO_TIMESTAMP('2023-11-15 18:31:41.775','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Table_Process_ID=540664
-- ;




-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Erstellt
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Created
-- 2023-11-15T16:56:45.378078700Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-11-15 18:56:45.378','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621302
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_AttachmentEntry_ReferencedRecord_v.CreatedBy
-- 2023-11-15T16:56:47.798774300Z
UPDATE AD_UI_Element SET IsDisplayed='Y',Updated=TO_TIMESTAMP('2023-11-15 18:56:47.798','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621303
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID
-- 2023-11-15T16:57:01.972925500Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-11-15 18:57:01.972','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621305
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-15T16:57:10.870905Z
UPDATE AD_UI_Element SET SeqNo=509999,Updated=TO_TIMESTAMP('2023-11-15 18:57:10.87','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621307
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-15T16:57:17.510068700Z
UPDATE AD_UI_Element SET SeqNo=10000,Updated=TO_TIMESTAMP('2023-11-15 18:57:17.51','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621304
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Aktiv
-- Column: AD_AttachmentEntry_ReferencedRecord_v.IsActive
-- 2023-11-15T16:57:33.569541700Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-11-15 18:57:33.569','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621311
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Art
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Type
-- 2023-11-15T16:57:54.219139400Z
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2023-11-15 18:57:54.219','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621312
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.URL
-- Column: AD_AttachmentEntry_ReferencedRecord_v.URL
-- 2023-11-15T16:57:58.862252400Z
UPDATE AD_UI_Element SET SeqNo=100,Updated=TO_TIMESTAMP('2023-11-15 18:57:58.862','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621313
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-15T16:58:03.676323800Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-11-15 18:58:03.676','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621307
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-15T16:58:07.396806300Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-11-15 18:58:07.396','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621304
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-15T16:58:10.834874300Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2023-11-15 18:58:10.834','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621307
;

-- UI Element: Meine Zulassungen(541736,D) -> Dokumente(547282,D) -> main -> 10 -> default.Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-15T16:59:04.877454300Z
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2023-11-15 18:59:04.877','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621304
;







-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Erstellt
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Created
-- 2023-11-15T18:21:44.121978900Z
UPDATE AD_UI_Element SET SeqNo=10,Updated=TO_TIMESTAMP('2023-11-15 20:21:44.121','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621269
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Erstellt durch
-- Column: AD_AttachmentEntry_ReferencedRecord_v.CreatedBy
-- 2023-11-15T18:21:54.991724800Z
UPDATE AD_UI_Element SET SeqNo=20,Updated=TO_TIMESTAMP('2023-11-15 20:21:54.991','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621270
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.DB-Tabelle
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID
-- 2023-11-15T18:21:59.891123400Z
UPDATE AD_UI_Element SET SeqNo=30,Updated=TO_TIMESTAMP('2023-11-15 20:21:59.891','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621272
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Mandant
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Client_ID
-- 2023-11-15T18:22:07.534538200Z
UPDATE AD_UI_Element SET SeqNo=120,Updated=TO_TIMESTAMP('2023-11-15 20:22:07.534','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621271
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Organisation
-- Column: AD_AttachmentEntry_ReferencedRecord_v.AD_Org_ID
-- 2023-11-15T18:22:27.914159200Z
UPDATE AD_UI_Element SET SeqNo=110,Updated=TO_TIMESTAMP('2023-11-15 20:22:27.914','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621274
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Aktiv
-- Column: AD_AttachmentEntry_ReferencedRecord_v.IsActive
-- 2023-11-15T18:22:35.161638200Z
UPDATE AD_UI_Element SET SeqNo=50,Updated=TO_TIMESTAMP('2023-11-15 20:22:35.161','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621278
;

-- UI Element: Approval Requests(541730,D) -> Dokumente(547277,D) -> main -> 10 -> default.Art
-- Column: AD_AttachmentEntry_ReferencedRecord_v.Type
-- 2023-11-15T18:22:39.407978800Z
UPDATE AD_UI_Element SET SeqNo=90,Updated=TO_TIMESTAMP('2023-11-15 20:22:39.407','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_UI_Element_ID=621279
;






-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-16T17:51:19.222089300Z
UPDATE AD_Tab SET Parent_Column_ID=587361,Updated=TO_TIMESTAMP('2023-11-16 19:51:19.222','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547277
;

-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-16T18:07:00.035472800Z
UPDATE AD_Tab SET AllowQuickInput='N',Updated=TO_TIMESTAMP('2023-11-16 20:07:00.035','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547277
;

-- Tab: Approval Requests(541730,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-16T18:07:29.633763400Z
UPDATE AD_Tab SET WhereClause='AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=259 /*C_Order*/',Updated=TO_TIMESTAMP('2023-11-16 20:07:29.633','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547277
;

-- Tab: Meine Zulassungen(541736,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-16T18:08:45.518121700Z
UPDATE AD_Tab SET WhereClause='AD_AttachmentEntry_ReferencedRecord_v.AD_Table_ID=259 /*C_Order*/',Updated=TO_TIMESTAMP('2023-11-16 20:08:45.518','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547282
;

-- Tab: Meine Zulassungen(541736,D) -> Dokumente
-- Table: AD_AttachmentEntry_ReferencedRecord_v
-- 2023-11-16T18:09:51.540854200Z
UPDATE AD_Tab SET Parent_Column_ID=587361,Updated=TO_TIMESTAMP('2023-11-16 20:09:51.54','YYYY-MM-DD HH24:MI:SS.US'),UpdatedBy=100 WHERE AD_Tab_ID=547282
;

