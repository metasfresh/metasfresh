-- 2023-03-23T08:30:20.380Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,582150,0,TO_TIMESTAMP('2023-03-23 09:30:19','YYYY-MM-DD HH24:MI:SS'),100,'Abzuarbeitende Inventurpositionen',
        'D','Y','Inventur Zählliste_NEW','Inventur Zählliste_NEW',TO_TIMESTAMP('2023-03-23 09:30:19','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:30:20.491Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582150 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- 2023-03-23T08:30:55.264Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582151,0,TO_TIMESTAMP('2023-03-23 09:30:54','YYYY-MM-DD HH24:MI:SS'),100,'Abzuarbeitende Inventurpositionen','D','Y',
                                                                                                                                                         'Inventur Zählliste_OLD','Inventur Zählliste_OLD',TO_TIMESTAMP('2023-03-23 09:30:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:30:55.362Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,
                            WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID,
                                                                                                                                              t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582151 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Window: Inventur Zählliste_NEW, InternalName=null
-- Window: Inventur Zählliste_NEW, InternalName=null
-- 2023-03-23T08:31:59.080Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,IsActive,IsBetaFunctionality,IsDefault,
                       IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority)
VALUES (0,582150,0,541685,TO_TIMESTAMP('2023-03-23 09:31:56','YYYY-MM-DD HH24:MI:SS'),100,'Abzuarbeitende Inventurpositionen',
        'D','Y','N','N','N','N','N','N','Y',
        'Inventur Zählliste_NEW','N',TO_TIMESTAMP('2023-03-23 09:31:56','YYYY-MM-DD HH24:MI:SS'),100,'M',0,0,100)
;

-- 2023-03-23T08:31:59.195Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language,t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,
       t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t
                                                                   WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Window_ID=541685
                                                                     AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2023-03-23T08:31:59.419Z
/* DDL */  select update_window_translation_from_ad_element(582150)
;

-- 2023-03-23T08:31:59.531Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541685
;

-- 2023-03-23T08:31:59.643Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541685)
;

-- Window: Inventur Zählliste_NEW, InternalName=null
-- Window: Inventur Zählliste_NEW, InternalName=null
-- 2023-03-23T08:33:46.665Z
UPDATE AD_Window SET AD_Client_ID=0, AD_Color_ID=NULL, AD_Image_ID=NULL, AD_Org_ID=0, EntityType='de.metas.swat', IsActive='Y', IsBetaFunctionality='N', IsDefault='N',
                     IsEnableRemoteCacheInvalidation='N', IsExcludeFromZoomTargets='N', IsOneInstanceOnly='N', IsOverrideInMenu='N', IsSOTrx='Y', Overrides_Window_ID=NULL, WindowType='M',
                     WinHeight=0, WinWidth=0, ZoomIntoPriority=100,Updated=TO_TIMESTAMP('2023-03-23 09:33:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541685
;

-- -- 2023-03-23T08:33:47.096Z
-- DELETE FROM AD_Window_Trl WHERE AD_Window_ID = 541685
-- ;
--
-- 2023-03-23T08:33:47.185Z
-- INSERT INTO AD_Window_Trl (AD_Window_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)
-- SELECT 541685, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated,
--        UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Window_Trl WHERE AD_Window_ID = 540458
-- ;

-- Tab: Inventur Zählliste_NEW -> Inventur-Position
-- Table: M_InventoryLine
-- Tab: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position
-- Table: M_InventoryLine
-- 2023-03-23T08:33:49.855Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,DisplayLogic,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy,WhereClause) VALUES (0,572521,0,546850,322,541685,'Y',TO_TIMESTAMP('2023-03-23 09:33:47','YYYY-MM-DD HH24:MI:SS'),100,'','de.metas.swat','N','N','A','M_InventoryLine','Y','N','Y','Y','N','N','N','N','Y','N','N','N','Y','Y','N','N','N',0,'Inventur-Position','N',10,0,TO_TIMESTAMP('2023-03-23 09:33:47','YYYY-MM-DD HH24:MI:SS'),100,'Processed = ''N''')
;

-- 2023-03-23T08:33:49.958Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Tab_ID=546850 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-03-23T08:33:50.060Z
/* DDL */  select update_tab_translation_from_ad_element(572521)
;

-- 2023-03-23T08:33:50.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546850)
;

-- 2023-03-23T08:33:50.394Z
DELETE FROM AD_Tab_Trl WHERE AD_Tab_ID = 546850
;

-- 2023-03-23T08:33:50.486Z
INSERT INTO AD_Tab_Trl (AD_Tab_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, CommitWarning, IsTranslated)  SELECT 546850, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description,  Help, CommitWarning, IsTranslated  FROM AD_Tab_Trl WHERE AD_Tab_ID = 541168
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Verarbeitet
-- Column: M_InventoryLine.Processed
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Verarbeitet
-- Column: M_InventoryLine.Processed
-- 2023-03-23T08:33:53.003Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,12071,712895,0,546850,0,TO_TIMESTAMP('2023-03-23 09:33:51','YYYY-MM-DD HH24:MI:SS'),100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'de.metas.swat','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.',0,'Y','N','N','N','N','N','N','N','Verarbeitet',10,0,1,1,TO_TIMESTAMP('2023-03-23 09:33:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:33:53.099Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712895 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:33:53.194Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2023-03-23T08:33:53.427Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712895
;

-- 2023-03-23T08:33:53.543Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712895)
;

-- 2023-03-23T08:33:53.825Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712895
;

-- 2023-03-23T08:33:54.010Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712895, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565608
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Aktiv
-- Column: M_InventoryLine.IsActive
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Aktiv
-- Column: M_InventoryLine.IsActive
-- 2023-03-23T08:33:55.769Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3558,712896,0,546850,0,TO_TIMESTAMP('2023-03-23 09:33:54','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'de.metas.swat','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.',0,'Y','N','N','N','N','N','N','N','Aktiv',20,0,1,1,TO_TIMESTAMP('2023-03-23 09:33:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:33:55.877Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712896 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:33:55.977Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2023-03-23T08:33:56.550Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712896
;

-- 2023-03-23T08:33:56.684Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712896)
;

-- 2023-03-23T08:33:56.878Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712896
;

-- 2023-03-23T08:33:56.967Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712896, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565609
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> UPC
-- Column: M_InventoryLine.UPC
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> UPC
-- Column: M_InventoryLine.UPC
-- 2023-03-23T08:33:58.765Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14102,712897,0,546850,0,TO_TIMESTAMP('2023-03-23 09:33:57','YYYY-MM-DD HH24:MI:SS'),100,'Produktidentifikation (Barcode) durch Universal Product Code oder European Article Number)',30,'de.metas.swat','Tragen Sie hier den Barcode für das Produkt in einer der Barcode-Codierungen (Codabar, Code 25, Code 39, Code 93, Code 128, UPC (A), UPC (E), EAN-13, EAN-8, ITF, ITF-14, ISBN, ISSN, JAN-13, JAN-8, POSTNET und FIM, MSI/Plessey, Pharmacode) ein.',0,'Y','N','N','N','N','N','N','N','UPC',30,0,1,1,TO_TIMESTAMP('2023-03-23 09:33:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:33:58.858Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712897 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:33:58.956Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(603)
;

-- 2023-03-23T08:33:59.068Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712897
;

-- 2023-03-23T08:33:59.178Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712897)
;

-- 2023-03-23T08:33:59.365Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712897
;

-- 2023-03-23T08:33:59.457Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712897, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565610
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Suchschlüssel
-- Column: M_InventoryLine.Value
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Suchschlüssel
-- Column: M_InventoryLine.Value
-- 2023-03-23T08:34:01.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,14101,712898,0,546850,0,TO_TIMESTAMP('2023-03-23 09:33:59','YYYY-MM-DD HH24:MI:SS'),100,'Suchschlüssel für den Eintrag im erforderlichen Format - muss eindeutig sein',40,'de.metas.swat','A search key allows you a fast method of finding a particular record.
If you leave the search key empty, the system automatically creates a numeric number.  The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','N','N','N','N','N','N','N','Suchschlüssel',40,0,1,999,1,TO_TIMESTAMP('2023-03-23 09:33:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:01.217Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712898 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:01.309Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(620)
;

-- 2023-03-23T08:34:01.423Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712898
;

-- 2023-03-23T08:34:01.513Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712898)
;

-- 2023-03-23T08:34:01.694Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712898
;

-- 2023-03-23T08:34:01.797Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712898, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565611
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Inventur-Position
-- Column: M_InventoryLine.M_InventoryLine_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Inventur-Position
-- Column: M_InventoryLine.M_InventoryLine_ID
-- 2023-03-23T08:34:03.436Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3555,712899,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:01','YYYY-MM-DD HH24:MI:SS'),100,'Eindeutige Position in einem Inventurdokument',14,'de.metas.swat','"Inventur-Position" bezeichnet die Poition in dem Inventurdokument (wenn zutreffend) für diese Transaktion.',0,'Y','N','N','N','N','N','N','N','Inventur-Position',50,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:03.531Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712899 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:03.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1028)
;

-- 2023-03-23T08:34:03.751Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712899
;

-- 2023-03-23T08:34:03.856Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712899)
;

-- 2023-03-23T08:34:04.035Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712899
;

-- 2023-03-23T08:34:04.123Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712899, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565612
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Internal Use Qty
-- Column: M_InventoryLine.QtyInternalUse
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Internal Use Qty
-- Column: M_InventoryLine.QtyInternalUse
-- 2023-03-23T08:34:06.009Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,13023,712900,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:04','YYYY-MM-DD HH24:MI:SS'),100,'Internal Use Quantity removed from Inventory',26,'de.metas.swat','Quantity of product inventory used internally (positive if taken out - negative if returned)',0,'Y','N','N','N','N','N','N','N','Internal Use Qty',60,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:06.107Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712900 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:06.207Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2654)
;

-- 2023-03-23T08:34:06.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712900
;

-- 2023-03-23T08:34:06.416Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712900)
;

-- 2023-03-23T08:34:06.613Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712900
;

-- 2023-03-23T08:34:06.703Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712900, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565613
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2023-03-23T08:34:08.391Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3556,712901,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:06','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',14,'de.metas.swat','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .',0,'Y','N','N','N','N','N','Y','N','Mandant',70,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:08.485Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712901 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:08.578Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2023-03-23T08:34:08.786Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712901
;

-- 2023-03-23T08:34:08.890Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712901)
;

-- 2023-03-23T08:34:09.075Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712901
;

-- 2023-03-23T08:34:09.167Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712901, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565614
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2023-03-23T08:34:10.951Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3557,712902,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',14,'de.metas.swat','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.',0,'Y','N','N','N','N','N','N','N','Sektion',80,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:09','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:11.063Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712902 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:11.156Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2023-03-23T08:34:11.377Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712902
;

-- 2023-03-23T08:34:11.474Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712902)
;

-- 2023-03-23T08:34:11.667Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712902
;

-- 2023-03-23T08:34:11.755Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712902, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565615
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2023-03-23T08:34:13.567Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3563,712903,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:11','YYYY-MM-DD HH24:MI:SS'),100,'Parameter für eine physische Inventur',14,'de.metas.swat','Bezeichnet die eindeutigen Parameter für eine physische Inventur',0,'Y','N','N','N','N','N','Y','N','Inventur',90,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:11','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:13.658Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712903 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:13.747Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1027)
;

-- 2023-03-23T08:34:13.847Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712903
;

-- 2023-03-23T08:34:13.972Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712903)
;

-- 2023-03-23T08:34:14.171Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712903
;

-- 2023-03-23T08:34:14.324Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712903, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565616
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Zeile Nr.
-- Column: M_InventoryLine.Line
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Zeile Nr.
-- Column: M_InventoryLine.Line
-- 2023-03-23T08:34:16.948Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3819,712904,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:14','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument',11,'de.metas.swat','Indicates the unique line for a document.  It will also control the display order of the lines within a document.',0,'Y','N','N','N','N','N','N','N','Zeile Nr.',100,0,2,1,1,TO_TIMESTAMP('2023-03-23 09:34:14','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:17.130Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712904 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:17.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(439)
;

-- 2023-03-23T08:34:17.323Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712904
;

-- 2023-03-23T08:34:17.423Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712904)
;

-- 2023-03-23T08:34:17.624Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712904
;

-- 2023-03-23T08:34:17.734Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712904, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565617
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2023-03-23T08:34:19.643Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3564,712905,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:17','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager',14,'de.metas.swat','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.',0,'Y','Y','Y','N','N','N','N','N','Lagerort',20,20,1,1,TO_TIMESTAMP('2023-03-23 09:34:17','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:19.756Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712905 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:19.845Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448)
;

-- 2023-03-23T08:34:19.961Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712905
;

-- 2023-03-23T08:34:20.057Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712905)
;

-- 2023-03-23T08:34:20.325Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712905
;

-- 2023-03-23T08:34:20.414Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712905, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565618
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Produkt
-- Column: M_InventoryLine.M_Product_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2023-03-23T08:34:22.158Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3565,712906,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:20','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',26,'de.metas.swat','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.',0,'Y','Y','Y','N','N','N','N','N','Produkt',30,30,1,1,TO_TIMESTAMP('2023-03-23 09:34:20','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:22.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712906 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:22.390Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2023-03-23T08:34:22.536Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712906
;

-- 2023-03-23T08:34:22.645Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712906)
;

-- 2023-03-23T08:34:22.848Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712906
;

-- 2023-03-23T08:34:22.935Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712906, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565619
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Maßeinheit
-- Column: M_InventoryLine.C_UOM_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Maßeinheit
-- Column: M_InventoryLine.C_UOM_ID
-- 2023-03-23T08:34:24.710Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556504,712907,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:23','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit',26,'de.metas.swat','Eine eindeutige (nicht monetäre) Maßeinheit',0,'Y','Y','Y','N','N','N','N','N','Maßeinheit',40,40,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:24.837Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712907 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:24.930Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2023-03-23T08:34:25.059Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712907
;

-- 2023-03-23T08:34:25.149Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712907)
;

-- 2023-03-23T08:34:25.346Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712907
;

-- 2023-03-23T08:34:25.489Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712907, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565620
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T08:34:27.119Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,8550,712908,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:25','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt',26,'de.metas.swat','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.',0,'Y','Y','Y','N','N','N','N','N','Merkmale',110,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:25','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:27.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712908 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:27.323Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2019)
;

-- 2023-03-23T08:34:27.431Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712908
;

-- 2023-03-23T08:34:27.523Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712908)
;

-- 2023-03-23T08:34:27.715Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712908
;

-- 2023-03-23T08:34:27.804Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712908, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565621
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Zählmenge
-- Column: M_InventoryLine.QtyCount
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T08:34:29.857Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3567,712909,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:27','YYYY-MM-DD HH24:MI:SS'),100,'Gezählte Menge',26,'de.metas.swat','"Zählmenge" zeigt die tatsächlich ermittelte Menge für ein Produkt im Bestand an.',0,'Y','Y','Y','N','N','N','N','N','Zählmenge',70,70,1,1,TO_TIMESTAMP('2023-03-23 09:34:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:29.959Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712909 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:30.045Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1049)
;

-- 2023-03-23T08:34:30.147Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712909
;

-- 2023-03-23T08:34:30.253Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712909)
;

-- 2023-03-23T08:34:30.436Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712909
;

-- 2023-03-23T08:34:30.525Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712909, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565622
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Buchmenge
-- Column: M_InventoryLine.QtyBook
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T08:34:32.095Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3566,712910,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:30','YYYY-MM-DD HH24:MI:SS'),100,'Buchmenge',26,'de.metas.swat','"Buchmenge" zeigt die im System gespeicherte Menge für ein Produkt im Bestand an.',0,'Y','Y','Y','N','N','N','Y','N','Buchmenge',60,60,1,1,TO_TIMESTAMP('2023-03-23 09:34:30','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:32.193Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712910 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:32.284Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1048)
;

-- 2023-03-23T08:34:32.387Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712910
;

-- 2023-03-23T08:34:32.470Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712910)
;

-- 2023-03-23T08:34:32.647Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712910
;

-- 2023-03-23T08:34:32.754Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712910, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565623
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Beschreibung
-- Column: M_InventoryLine.Description
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Beschreibung
-- Column: M_InventoryLine.Description
-- 2023-03-23T08:34:35.286Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,3568,712911,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:32','YYYY-MM-DD HH24:MI:SS'),100,60,'de.metas.swat',0,'Y','N','N','N','N','N','N','N','Beschreibung',120,0,999,1,TO_TIMESTAMP('2023-03-23 09:34:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:35.386Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712911 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:35.477Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(275)
;

-- 2023-03-23T08:34:35.640Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712911
;

-- 2023-03-23T08:34:35.736Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712911)
;

-- 2023-03-23T08:34:35.913Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712911
;

-- 2023-03-23T08:34:36.006Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712911, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565624
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Inventory Type
-- Column: M_InventoryLine.InventoryType
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2023-03-23T08:34:37.655Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9951,712912,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:36','YYYY-MM-DD HH24:MI:SS'),100,'Type of inventory difference',14,'de.metas.swat','The type of inventory difference determines which account is used. The default is the Inventory Difference account defined for the warehouse.  Alternatively, you could select any charge.  This allows you to account for Internal Use or extraordinary inventory losses.',0,'Y','N','N','N','N','N','N','N','Inventory Type',130,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:36','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:37.746Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712912 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:37.846Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(2202)
;

-- 2023-03-23T08:34:37.937Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712912
;

-- 2023-03-23T08:34:38.034Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712912)
;

-- 2023-03-23T08:34:38.224Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712912
;

-- 2023-03-23T08:34:38.330Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712912, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565625
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Kosten
-- Column: M_InventoryLine.C_Charge_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Kosten
-- Column: M_InventoryLine.C_Charge_ID
-- 2023-03-23T08:34:40.197Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,DisplayLogic,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,9950,712913,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:38','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Kosten',14,'','de.metas.swat','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)',0,'Y','N','N','N','N','N','N','N','Kosten',140,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:40.290Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712913 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:40.378Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(968)
;

-- 2023-03-23T08:34:40.472Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712913
;

-- 2023-03-23T08:34:40.567Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712913)
;

-- 2023-03-23T08:34:40.762Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712913
;

-- 2023-03-23T08:34:40.855Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712913, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565626
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Calculate Count (?)
-- Column: M_InventoryLine.IsCounted
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Calculate Count (?)
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T08:34:42.445Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560761,712914,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:40','YYYY-MM-DD HH24:MI:SS'),100,'Count number of not empty elements',0,'de.metas.swat','Calculate the total number (?) of not empty (NULL) elements (maximum is the number of lines).',0,'Y','Y','Y','N','N','N','N','N','Calculate Count (?)',80,80,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:40','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:42.537Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712914 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:42.635Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1835)
;

-- 2023-03-23T08:34:42.730Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712914
;

-- 2023-03-23T08:34:42.825Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712914)
;

-- 2023-03-23T08:34:43.015Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712914
;

-- 2023-03-23T08:34:43.101Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712914, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565627
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2023-03-23T08:34:44.706Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,560762,712915,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:43','YYYY-MM-DD HH24:MI:SS'),100,0,'de.metas.swat',0,'Y','Y','Y','N','N','N','Y','N','Zugewiesen an',10,10,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:43','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:44.818Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712915 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:44.933Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(544205)
;

-- 2023-03-23T08:34:45.036Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712915
;

-- 2023-03-23T08:34:45.133Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712915)
;

-- 2023-03-23T08:34:45.357Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712915
;

-- 2023-03-23T08:34:45.456Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712915, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565628
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2023-03-23T08:34:47.520Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,556506,712916,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:45','YYYY-MM-DD HH24:MI:SS'),100,10,'@HUAggregationType/''S''@=''S''','de.metas.swat',0,'Y','N','N','N','N','N','N','N','Packvorschrift',150,0,1,1,TO_TIMESTAMP('2023-03-23 09:34:45','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:47.616Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712916 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:47.719Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542132)
;

-- 2023-03-23T08:34:47.810Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712916
;

-- 2023-03-23T08:34:47.895Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712916)
;

-- 2023-03-23T08:34:48.077Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712916
;

-- 2023-03-23T08:34:48.168Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712916, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565629
;

-- Field: Inventur Zählliste_NEW -> Inventur-Position -> Handling Unit
-- Column: M_InventoryLine.M_HU_ID
-- Field: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Handling Unit
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T08:34:49.810Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,DisplayLogic,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,559545,712917,0,546850,0,TO_TIMESTAMP('2023-03-23 09:34:48','YYYY-MM-DD HH24:MI:SS'),100,10,'@HUAggregationType/''S''@=''S''','de.metas.swat',0,'Y','Y','Y','N','N','N','N','N','Handling Unit',50,50,1,1,TO_TIMESTAMP('2023-03-23 09:34:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:34:49.899Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712917 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T08:34:49.987Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(542140)
;

-- 2023-03-23T08:34:50.080Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712917
;

-- 2023-03-23T08:34:50.162Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712917)
;

-- 2023-03-23T08:34:50.362Z
DELETE FROM AD_Field_Trl WHERE AD_Field_ID = 712917
;

-- 2023-03-23T08:34:50.456Z
INSERT INTO AD_Field_Trl (AD_Field_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, Description, Help, IsTranslated)  SELECT 712917, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, Description, Help, IsTranslated  FROM AD_Field_Trl WHERE AD_Field_ID = 565630
;

-- Tab: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat)
-- UI Section: main
-- 2023-03-23T08:34:51.649Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546850,545469,TO_TIMESTAMP('2023-03-23 09:34:50','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-23 09:34:50','YYYY-MM-DD HH24:MI:SS'),100,'main')
;

-- 2023-03-23T08:34:51.749Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_UI_Section_ID=545469 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- 2023-03-23T08:34:51.935Z
DELETE FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 545469
;

-- 2023-03-23T08:34:52.025Z
INSERT INTO AD_UI_Section_Trl (AD_UI_Section_ID, AD_Language,  AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,  Name, IsTranslated)  SELECT 545469, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy,  Updated, UpdatedBy, Name, IsTranslated  FROM AD_UI_Section_Trl WHERE AD_UI_Section_ID = 540788
;

-- UI Section: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main
-- UI Column: 10
-- 2023-03-23T08:34:53.288Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546683,545469,TO_TIMESTAMP('2023-03-23 09:34:52','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-03-23 09:34:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10
-- UI Element Group: default
-- 2023-03-23T08:34:54.557Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,546683,550446,TO_TIMESTAMP('2023-03-23 09:34:53','YYYY-MM-DD HH24:MI:SS'),100,'Y','default',10,'primary',TO_TIMESTAMP('2023-03-23 09:34:53','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Zeile Nr.
-- Column: M_InventoryLine.Line
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zeile Nr.
-- Column: M_InventoryLine.Line
-- 2023-03-23T08:34:57.827Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712904,0,546850,550446,616044,'F',TO_TIMESTAMP('2023-03-23 09:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Einzelne Zeile in dem Dokument','Indicates the unique line for a document.  It will also control the display order of the lines within a document.','Y','N','N','N','N','N','N','Zeile Nr.',10,0,0,TO_TIMESTAMP('2023-03-23 09:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Beschreibung
-- Column: M_InventoryLine.Description
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.Description
-- 2023-03-23T08:34:59.809Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712911,0,546850,550446,616045,'F',TO_TIMESTAMP('2023-03-23 09:34:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Beschreibung',20,0,0,TO_TIMESTAMP('2023-03-23 09:34:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventory Type
-- Column: M_InventoryLine.InventoryType
-- 2023-03-23T08:35:01.968Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712912,0,546850,550446,616046,'F',TO_TIMESTAMP('2023-03-23 09:35:00','YYYY-MM-DD HH24:MI:SS'),100,'Type of inventory difference','The type of inventory difference determines which account is used. The default is the Inventory Difference account defined for the warehouse.  Alternatively, you could select any charge.  This allows you to account for Internal Use or extraordinary inventory losses.','Y','N','N','N','N','N','N','Inventory Type',30,0,0,TO_TIMESTAMP('2023-03-23 09:35:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2023-03-23T08:35:03.891Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712903,0,546850,550446,616047,'F',TO_TIMESTAMP('2023-03-23 09:35:02','YYYY-MM-DD HH24:MI:SS'),100,'Parameter für eine physische Inventur','Bezeichnet die eindeutigen Parameter für eine physische Inventur','Y','N','N','N','N','N','N','Inventur',40,0,0,TO_TIMESTAMP('2023-03-23 09:35:02','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Kosten
-- Column: M_InventoryLine.C_Charge_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Kosten
-- Column: M_InventoryLine.C_Charge_ID
-- 2023-03-23T08:35:05.818Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712913,0,546850,550446,616048,'F',TO_TIMESTAMP('2023-03-23 09:35:04','YYYY-MM-DD HH24:MI:SS'),100,'Zusätzliche Kosten','Kosten gibt die Art der zusätzlichen Kosten an (Abwicklung, Fracht, Bankgebühren, ...)','Y','N','N','N','N','N','N','Kosten',50,0,0,TO_TIMESTAMP('2023-03-23 09:35:04','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Mandant
-- Column: M_InventoryLine.AD_Client_ID
-- 2023-03-23T08:35:07.743Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712901,0,546850,550446,616049,'F',TO_TIMESTAMP('2023-03-23 09:35:06','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','Mandant',60,0,0,TO_TIMESTAMP('2023-03-23 09:35:06','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Packvorschrift
-- Column: M_InventoryLine.M_HU_PI_Item_Product_ID
-- 2023-03-23T08:35:09.651Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712916,0,546850,550446,616050,'F',TO_TIMESTAMP('2023-03-23 09:35:08','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','N','N','N','N','Packvorschrift',70,0,0,TO_TIMESTAMP('2023-03-23 09:35:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T08:35:12.317Z
INSERT INTO AD_UI_ElementField (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_UI_ElementField_ID,AD_UI_Element_ID,Created,CreatedBy,IsActive,SeqNo,Type,Updated,UpdatedBy) VALUES (0,712906,0,541704,616050,TO_TIMESTAMP('2023-03-23 09:35:10','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,'widget',TO_TIMESTAMP('2023-03-23 09:35:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2023-03-23T08:35:14.307Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712902,0,546850,550446,616051,'F',TO_TIMESTAMP('2023-03-23 09:35:12','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','Sektion',80,0,0,TO_TIMESTAMP('2023-03-23 09:35:12','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2023-03-23T08:35:17.979Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712915,0,546850,550446,616052,'F',TO_TIMESTAMP('2023-03-23 09:35:14','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Zugewiesen an',10,10,0,TO_TIMESTAMP('2023-03-23 09:35:14','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2023-03-23T08:35:19.947Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712905,0,546850,550446,616053,'F',TO_TIMESTAMP('2023-03-23 09:35:18','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','Y','Y','N','N','Lagerort',20,20,0,TO_TIMESTAMP('2023-03-23 09:35:18','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2023-03-23T08:35:22.779Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712906,0,546850,550446,616054,'F',TO_TIMESTAMP('2023-03-23 09:35:20','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Produkt',30,30,0,TO_TIMESTAMP('2023-03-23 09:35:20','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Maßeinheit
-- Column: M_InventoryLine.C_UOM_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Maßeinheit
-- Column: M_InventoryLine.C_UOM_ID
-- 2023-03-23T08:35:25.370Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MediaTypes,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712907,0,546850,550446,616055,'F',TO_TIMESTAMP('2023-03-23 09:35:23','YYYY-MM-DD HH24:MI:SS'),100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N','','Maßeinheit',40,0,0,TO_TIMESTAMP('2023-03-23 09:35:23','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T08:35:27.447Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712917,0,546850,550446,616056,'F',TO_TIMESTAMP('2023-03-23 09:35:25','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','Y','N','N','Handling Units',50,40,0,TO_TIMESTAMP('2023-03-23 09:35:25','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T08:35:31.996Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712908,0,546850,550446,616057,'F',TO_TIMESTAMP('2023-03-23 09:35:28','YYYY-MM-DD HH24:MI:SS'),100,'Merkmals Ausprägungen zum Produkt','The values of the actual Product Attribute Instances.  The product level attributes are defined on Product level.','Y','N','N','Y','Y','N','N','Merkmale',55,50,0,TO_TIMESTAMP('2023-03-23 09:35:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T08:35:33.684Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712910,0,546850,550446,616058,'F',TO_TIMESTAMP('2023-03-23 09:35:32','YYYY-MM-DD HH24:MI:SS'),100,'Buchmenge','"Buchmenge" zeigt die im System gespeicherte Menge für ein Produkt im Bestand an.','Y','N','N','Y','Y','N','N','Buchmenge',60,60,0,TO_TIMESTAMP('2023-03-23 09:35:32','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T08:35:36.077Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712909,0,546850,550446,616059,'F',TO_TIMESTAMP('2023-03-23 09:35:34','YYYY-MM-DD HH24:MI:SS'),100,'Gezählte Menge','"Zählmenge" zeigt die tatsächlich ermittelte Menge für ein Produkt im Bestand an.','Y','N','N','Y','Y','N','N','Zählmenge',70,70,0,TO_TIMESTAMP('2023-03-23 09:35:34','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Zählliste_NEW -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Zählliste_NEW(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T08:35:37.854Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712914,0,546850,550446,616060,'F',TO_TIMESTAMP('2023-03-23 09:35:36','YYYY-MM-DD HH24:MI:SS'),100,'','','Y','N','N','Y','Y','N','N','Gezählt',70,80,0,TO_TIMESTAMP('2023-03-23 09:35:36','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- Window: Inventur Zählliste_OLD, InternalName=M_InventoryLine_Counting
-- Window: Inventur Zählliste_OLD, InternalName=M_InventoryLine_Counting
-- 2023-03-23T08:36:40.009Z
UPDATE AD_Window SET AD_Element_ID=582151, Description='Abzuarbeitende Inventurpositionen', Help=NULL, Name='Inventur Zählliste_OLD',Updated=TO_TIMESTAMP('2023-03-23 09:36:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540458
;

-- Name: Inventur Zählliste_OLD
-- Action Type: W
-- Window: Inventur Zählliste_OLD(540458,de.metas.swat)
-- 2023-03-23T08:36:40.403Z
UPDATE AD_Menu SET Description='Abzuarbeitende Inventurpositionen', IsActive='Y', Name='Inventur Zählliste_OLD',Updated=TO_TIMESTAMP('2023-03-23 09:36:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541129
;

-- 2023-03-23T08:36:40.830Z
/* DDL */  select update_window_translation_from_ad_element(582151)
;

-- 2023-03-23T08:36:40.923Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540458
;

-- 2023-03-23T08:36:41.015Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(540458)
;

-- Window: Inventur Zählliste, InternalName=null
-- Window: Inventur Zählliste, InternalName=null
-- 2023-03-23T08:37:04.473Z
UPDATE AD_Window SET AD_Element_ID=573984, Description='Abzuarbeitende Inventurpositionen', Help=NULL, Name='Inventur Zählliste',Updated=TO_TIMESTAMP('2023-03-23 09:37:04','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541685
;

-- 2023-03-23T08:37:04.972Z
/* DDL */  select update_window_translation_from_ad_element(573984)
;

-- 2023-03-23T08:37:05.067Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541685
;

-- 2023-03-23T08:37:05.152Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541685)
;

-- Window: Inventur Zählliste, InternalName=null
-- Window: Inventur Zählliste, InternalName=null
-- 2023-03-23T08:37:10.035Z
-- UPDATE AD_Window SET IsOverrideInMenu='Y',Updated=TO_TIMESTAMP('2023-03-23 09:37:09','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541685
-- ;

-- Tab: Inventur Zählliste -> Inventur-Position
-- Table: M_InventoryLine
-- Tab: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position
-- Table: M_InventoryLine
-- 2023-03-23T08:37:27.802Z
UPDATE AD_Tab SET WhereClause='',Updated=TO_TIMESTAMP('2023-03-23 09:37:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546850
;



-----------------------------------------------



-- Column: M_InventoryLine.DocumentNo
-- Column SQL (old): null
-- Column: M_InventoryLine.DocumentNo
-- 2023-03-23T08:55:42.054Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586321,290,0,10,322,'DocumentNo','(SELECT DocumentNo from M_Inventory where  M_Inventory_id = M_InventoryLine.M_Inventory_id)',TO_TIMESTAMP('2023-03-23 09:55:40','YYYY-MM-DD HH24:MI:SS'),100,'N','Document sequence number of the document','D',0,40,'E','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','Y',0,'Nr.',0,1,TO_TIMESTAMP('2023-03-23 09:55:40','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T08:55:42.173Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586321 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T08:55:42.470Z
/* DDL */  select update_Column_Translation_From_AD_Element(290)
;





--------------------------------------------










-- Field: Inventur Zählliste -> Inventur-Position -> Nr.
-- Column: M_InventoryLine.DocumentNo
-- Field: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Nr.
-- Column: M_InventoryLine.DocumentNo
-- 2023-03-23T09:13:12.523Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586321,712918,0,546850,0,TO_TIMESTAMP('2023-03-23 10:13:10','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',0,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).',0,'Y','Y','Y','N','N','N','N','N','Nr.',0,90,0,1,1,TO_TIMESTAMP('2023-03-23 10:13:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T09:13:12.638Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712918 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T09:13:12.775Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290)
;

-- 2023-03-23T09:13:12.940Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712918
;

-- 2023-03-23T09:13:13.067Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712918)
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Nr.
-- Column: M_InventoryLine.DocumentNo
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Nr.
-- Column: M_InventoryLine.DocumentNo
-- 2023-03-23T09:17:28.929Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712918,0,546850,550446,616061,'F',TO_TIMESTAMP('2023-03-23 10:17:27','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',5,0,0,TO_TIMESTAMP('2023-03-23 10:17:27','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Nr.
-- Column: M_InventoryLine.DocumentNo
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Nr.
-- Column: M_InventoryLine.DocumentNo
-- 2023-03-23T09:17:47.810Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-23 10:17:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616061
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Nr.
-- Column: M_InventoryLine.DocumentNo
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Nr.
-- Column: M_InventoryLine.DocumentNo
-- 2023-03-23T09:17:54.279Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2023-03-23 10:17:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616061
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2023-03-23T09:17:55.145Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-23 10:17:55','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2023-03-23T09:17:56.182Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-23 10:17:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616053
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2023-03-23T09:17:57.145Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-23 10:17:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616054
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T09:17:58.062Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-23 10:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T09:17:58.990Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-23 10:17:58','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T09:17:59.818Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-23 10:17:59','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T09:18:00.683Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-23 10:18:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T09:18:01.503Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-23 10:18:01','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- Column: M_InventoryLine.MovementDate
-- Column SQL (old): null
-- Column: M_InventoryLine.MovementDate
-- 2023-03-23T09:22:45.497Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,FilterOperator,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586322,1037,0,15,322,'MovementDate','(SELECT MovementDate from M_Inventory where  M_Inventory_id = M_InventoryLine.M_Inventory_id)',TO_TIMESTAMP('2023-03-23 10:22:43','YYYY-MM-DD HH24:MI:SS'),100,'N','Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','D',0,7,'E','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.','Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','Y','N','N','N','N','N','N','N',0,'Bewegungsdatum',0,0,TO_TIMESTAMP('2023-03-23 10:22:43','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T09:22:45.619Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586322 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T09:22:45.867Z
/* DDL */  select update_Column_Translation_From_AD_Element(1037)
;

-- Field: Inventur Zählliste -> Inventur-Position -> Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- Field: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2023-03-23T09:25:03.716Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586322,712919,0,546850,0,TO_TIMESTAMP('2023-03-23 10:25:01','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde',0,'D','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.',0,'Y','Y','Y','N','N','N','N','N','Bewegungsdatum',0,100,0,1,1,TO_TIMESTAMP('2023-03-23 10:25:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T09:25:03.836Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712919 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T09:25:03.963Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1037)
;

-- 2023-03-23T09:25:04.090Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712919
;

-- 2023-03-23T09:25:04.198Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712919)
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2023-03-23T09:26:04.873Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712919,0,546850,550446,616062,'F',TO_TIMESTAMP('2023-03-23 10:26:03','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem eine Produkt in oder aus dem Bestand bewegt wurde','"Bewegungs-Datum" bezeichnet das Datum, zu dem das Produkt in oder aus dem Bestand bewegt wurde Dies ist das Ergebnis einer Auslieferung, eines Wareneingangs oder einer Warenbewegung.','Y','N','N','Y','N','N','N',0,'Bewegungsdatum',6,0,0,TO_TIMESTAMP('2023-03-23 10:26:03','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2023-03-23T09:26:24.250Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-23 10:26:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2023-03-23T09:26:30.476Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2023-03-23 10:26:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2023-03-23T09:26:31.340Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-23 10:26:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2023-03-23T09:26:32.287Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-23 10:26:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616053
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2023-03-23T09:26:33.137Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-23 10:26:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616054
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T09:26:33.985Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-23 10:26:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T09:26:34.854Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-23 10:26:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T09:26:35.719Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-23 10:26:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T09:26:36.647Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-23 10:26:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T09:26:37.511Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-23 10:26:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2023-03-23T09:27:15.154Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2023-03-23 10:27:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Nr.
-- Column: M_InventoryLine.DocumentNo
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Nr.
-- Column: M_InventoryLine.DocumentNo
-- 2023-03-23T09:28:23.759Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2023-03-23 10:28:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616061
;

-- 2023-03-23T09:40:47.681Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582152,0,'InventoryLine_Description',TO_TIMESTAMP('2023-03-23 10:40:46','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Beschreibung','Beschreibung',TO_TIMESTAMP('2023-03-23 10:40:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T09:40:47.787Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582152 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InventoryLine_Description
-- 2023-03-23T09:41:30.398Z
UPDATE AD_Element_Trl SET Name='Description', PrintName='Description',Updated=TO_TIMESTAMP('2023-03-23 10:41:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582152 AND AD_Language='en_US'
;

-- 2023-03-23T09:41:30.608Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582152,'en_US')
;

-- Element: InventoryLine_Description
-- 2023-03-23T09:41:34.767Z
UPDATE AD_Element_Trl SET IsTranslated='Y',Updated=TO_TIMESTAMP('2023-03-23 10:41:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582152 AND AD_Language='en_US'
;

-- 2023-03-23T09:41:34.981Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582152,'en_US')
;

-- Column: M_InventoryLine.InventoryLine_Description
-- Column SQL (old): null
-- Column: M_InventoryLine.InventoryLine_Description
-- 2023-03-23T09:42:54.374Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586325,582152,0,10,322,'InventoryLine_Description','(SELECT Description from M_Inventory where  M_Inventory_id = M_InventoryLine.M_Inventory_id)',TO_TIMESTAMP('2023-03-23 10:42:52','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Beschreibung',0,0,TO_TIMESTAMP('2023-03-23 10:42:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T09:42:54.486Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586325 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T09:42:54.728Z
/* DDL */  select update_Column_Translation_From_AD_Element(582152)
;

-- Field: Inventur Zählliste -> Inventur-Position -> Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- Field: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2023-03-23T09:44:44.884Z
UPDATE AD_Field SET AD_Column_ID=586325, Description=NULL, EntityType='D', Help=NULL, Name='Beschreibung',Updated=TO_TIMESTAMP('2023-03-23 10:44:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=712911
;

-- 2023-03-23T09:44:44.997Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582152)
;

-- 2023-03-23T09:44:45.106Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712911
;

-- 2023-03-23T09:44:45.212Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712911)
;

-- UI Element: Inventur Zählliste -> Inventur-Position.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- UI Element: Inventur Zählliste(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2023-03-23T09:45:38.203Z
UPDATE AD_UI_Element SET IsDisplayed='Y', SeqNo=7,Updated=TO_TIMESTAMP('2023-03-23 10:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- 2023-03-23T09:49:56.659Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582153,0,TO_TIMESTAMP('2023-03-23 10:49:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Inventur Position (Alle)','Inventur Position (Alle)',TO_TIMESTAMP('2023-03-23 10:49:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T09:49:56.771Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582153 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: null
-- 2023-03-23T09:50:25.779Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Inventory Position (All)', PrintName='Inventory Position (All)',Updated=TO_TIMESTAMP('2023-03-23 10:50:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582153 AND AD_Language='en_US'
;

-- 2023-03-23T09:50:25.999Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582153,'en_US')
;

-- Window: Inventur Position (Alle), InternalName=null
-- Window: Inventur Position (Alle), InternalName=null
-- 2023-03-23T09:50:54.918Z
UPDATE AD_Window SET AD_Element_ID=582153, Description=NULL, Help=NULL, Name='Inventur Position (Alle)',Updated=TO_TIMESTAMP('2023-03-23 10:50:54','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541685
;

-- 2023-03-23T09:50:55.447Z
/* DDL */  select update_window_translation_from_ad_element(582153)
;

-- 2023-03-23T09:50:55.548Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=541685
;

-- 2023-03-23T09:50:55.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(541685)
;

-- Window: Inventur Position (Alle), InternalName=null
-- Window: Inventur Position (Alle), InternalName=null
-- 2023-03-23T09:52:41.924Z
UPDATE AD_Window SET IsOverrideInMenu='N', Overrides_Window_ID=NULL,Updated=TO_TIMESTAMP('2023-03-23 10:52:41','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=541685
;

-- Window: Inventur Zählliste, InternalName=M_InventoryLine_Counting
-- Window: Inventur Zählliste, InternalName=M_InventoryLine_Counting
-- 2023-03-23T09:53:12.912Z
UPDATE AD_Window SET AD_Element_ID=573984, Description='Abzuarbeitende Inventurpositionen', Help=NULL, Name='Inventur Zählliste',Updated=TO_TIMESTAMP('2023-03-23 10:53:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Window_ID=540458
;

-- Name: Inventur Zählliste
-- Action Type: W
-- Window: Inventur Zählliste(540458,de.metas.swat)
-- 2023-03-23T09:53:13.340Z
UPDATE AD_Menu SET Description='Abzuarbeitende Inventurpositionen', IsActive='Y', Name='Inventur Zählliste',Updated=TO_TIMESTAMP('2023-03-23 10:53:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Menu_ID=541129
;

-- 2023-03-23T09:53:13.784Z
/* DDL */  select update_window_translation_from_ad_element(573984)
;

-- 2023-03-23T09:53:13.908Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=540458
;

-- 2023-03-23T09:53:14.016Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(540458)
;

-- Name: Inventur Position (Alle)
-- Action Type: W
-- Window: Inventur Position (Alle)(541685,de.metas.swat)
-- 2023-03-23T09:58:02.198Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,582153,542072,0,541685,TO_TIMESTAMP('2023-03-23 10:58:00','YYYY-MM-DD HH24:MI:SS'),100,'de.metas.swat','Inventory Position (All)','Y','N','N','N','N','Inventur Position (Alle)',TO_TIMESTAMP('2023-03-23 10:58:00','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T09:58:02.310Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Menu_ID=542072 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2023-03-23T09:58:02.422Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542072, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542072)
;

-- 2023-03-23T09:58:02.529Z
/* DDL */  select update_menu_translation_from_ad_element(582153)
;

-- Reordering children of `Warehouse Management`
-- Node name: `Warehouse`
-- 2023-03-23T09:58:09.471Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000052 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Type`
-- 2023-03-23T09:58:09.584Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541137 AND AD_Tree_ID=10
;

-- Node name: `Inventory Move`
-- 2023-03-23T09:58:09.685Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540791 AND AD_Tree_ID=10
;

-- Node name: `Materialnahme`
-- 2023-03-23T09:58:09.797Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541070 AND AD_Tree_ID=10
;

-- Node name: `Stock Control Purchase`
-- 2023-03-23T09:58:09.896Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540980 AND AD_Tree_ID=10
;

-- Node name: `Material Schedule`
-- 2023-03-23T09:58:09.996Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540805 AND AD_Tree_ID=10
;

-- Node name: `Forecast`
-- 2023-03-23T09:58:10.097Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540800 AND AD_Tree_ID=10
;

-- Node name: `Material Cockpit`
-- 2023-03-23T09:58:10.244Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540981 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2023-03-23T09:58:10.374Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000053 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2023-03-23T09:58:10.493Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000061 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2023-03-23T09:58:10.626Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000069 AND AD_Tree_ID=10
;

-- Node name: `Physical Inventory`
-- 2023-03-23T09:58:10.735Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541056 AND AD_Tree_ID=10
;

-- Node name: `Inventory Line Counting`
-- 2023-03-23T09:58:10.842Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541129 AND AD_Tree_ID=10
;

-- Node name: `Inventory Candidate`
-- 2023-03-23T09:58:10.962Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541872 AND AD_Tree_ID=10
;

-- Node name: `Inventur Position (Alle)`
-- 2023-03-23T09:58:11.063Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000012, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542072 AND AD_Tree_ID=10
;

-- 2023-03-23T10:00:49.732Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582154,0,'InventoryLine_CorrectionQuantity',TO_TIMESTAMP('2023-03-23 11:00:48','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','korrekturmenge','korrekturmenge',TO_TIMESTAMP('2023-03-23 11:00:48','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T10:00:49.853Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582154 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Element: InventoryLine_CorrectionQuantity
-- 2023-03-23T10:01:15.641Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='Correction Quantity', PrintName='Correction Quantity',Updated=TO_TIMESTAMP('2023-03-23 11:01:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582154 AND AD_Language='en_US'
;

-- 2023-03-23T10:01:15.858Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582154,'en_US')
;

-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- Column SQL (old): null
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T10:02:15.827Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586326,582154,0,29,322,'InventoryLine_CorrectionQuantity','(QtyBook - QtyCount)',TO_TIMESTAMP('2023-03-23 11:02:14','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'korrekturmenge',0,0,TO_TIMESTAMP('2023-03-23 11:02:14','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T10:02:15.931Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586326 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T10:02:16.143Z
/* DDL */  select update_Column_Translation_From_AD_Element(582154)
;

-- Field: Inventur Position (Alle) -> Inventur-Position -> korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- Field: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T10:03:26.188Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586326,712920,0,546850,0,TO_TIMESTAMP('2023-03-23 11:03:23','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','korrekturmenge',0,110,0,1,1,TO_TIMESTAMP('2023-03-23 11:03:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T10:03:26.307Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712920 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T10:03:26.420Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582154)
;

-- 2023-03-23T10:03:26.542Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712920
;

-- 2023-03-23T10:03:26.648Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712920)
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T10:07:51.927Z
UPDATE AD_UI_Element SET SeqNo=75,Updated=TO_TIMESTAMP('2023-03-23 11:07:51','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T10:08:25.069Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy,WidgetSize) VALUES (0,712920,0,546850,550446,616063,'F',TO_TIMESTAMP('2023-03-23 11:08:23','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'korrekturmenge',72,0,0,TO_TIMESTAMP('2023-03-23 11:08:23','YYYY-MM-DD HH24:MI:SS'),100,'S')
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2023-03-23T10:09:18.129Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-23 11:09:18','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2023-03-23T10:09:32.862Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2023-03-23 11:09:32','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2023-03-23T10:09:33.749Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2023-03-23 11:09:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2023-03-23T10:09:34.682Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2023-03-23 11:09:34','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616053
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2023-03-23T10:09:35.568Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2023-03-23 11:09:35','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616054
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T10:09:36.474Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-23 11:09:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T10:09:37.334Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-23 11:09:37','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T10:09:38.254Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-23 11:09:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T10:09:39.094Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-23 11:09:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T10:09:40.027Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-23 11:09:40','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T10:11:19.408Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-23 11:11:19','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T10:11:24.353Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-23 11:11:24','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T10:11:25.251Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-23 11:11:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- 2023-03-23T10:15:58.292Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582155,0,'InventoryLine_LotNo',TO_TIMESTAMP('2023-03-23 11:15:56','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Chargen Nr.','Chargen Nr.',TO_TIMESTAMP('2023-03-23 11:15:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T10:15:58.417Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582155 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InventoryLine.InventoryLine_LotNo
-- Column SQL (old): null
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2023-03-23T10:49:10.170Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586327,582155,0,10,322,'InventoryLine_LotNo','(SELECT ai_value from Report.fresh_Attributes att where att.m_attributesetinstance_id = M_InventoryLine.m_attributesetinstance_id   AND att.at_value=''Lot-Nummer'')',TO_TIMESTAMP('2023-03-23 11:49:08','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,255,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'Chargen Nr.',0,0,TO_TIMESTAMP('2023-03-23 11:49:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T10:49:10.285Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586327 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T10:49:10.507Z
/* DDL */  select update_Column_Translation_From_AD_Element(582155)
;

-- Field: Inventur Position (Alle) -> Inventur-Position -> Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- Field: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2023-03-23T10:50:35.049Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586327,712921,0,546850,0,TO_TIMESTAMP('2023-03-23 11:50:32','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','Chargen Nr.',0,120,0,1,1,TO_TIMESTAMP('2023-03-23 11:50:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T10:50:35.161Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712921 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T10:50:35.282Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582155)
;

-- 2023-03-23T10:50:35.404Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712921
;

-- 2023-03-23T10:50:35.536Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712921)
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2023-03-23T10:53:27.791Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712921,0,546850,550446,616064,'F',TO_TIMESTAMP('2023-03-23 11:53:26','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'Chargen Nr.',35,0,0,TO_TIMESTAMP('2023-03-23 11:53:26','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2023-03-23T10:54:05.473Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-23 11:54:05','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616064
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2023-03-23T10:54:11.012Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2023-03-23 11:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616064
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T10:54:11.944Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-23 11:54:11','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T10:54:12.820Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-23 11:54:12','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T10:54:13.833Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-23 11:54:13','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T10:54:14.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-23 11:54:14','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T10:54:15.632Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-23 11:54:15','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T10:54:16.551Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-23 11:54:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- Element: InventoryLine_LotNo
-- 2023-03-23T10:57:03.946Z
UPDATE AD_Element_Trl SET IsTranslated='Y', Name='LotNo', PrintName='LotNo',Updated=TO_TIMESTAMP('2023-03-23 11:57:03','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Element_ID=582155 AND AD_Language='en_US'
;

-- 2023-03-23T10:57:04.188Z
/* DDL */  select update_TRL_Tables_On_AD_Element_TRL_Update(582155,'en_US')
;

-- 2023-03-23T10:58:43.426Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582156,0,'InventoryLine_MHD',TO_TIMESTAMP('2023-03-23 11:58:42','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','MHD','MHD',TO_TIMESTAMP('2023-03-23 11:58:42','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T10:58:43.531Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582156 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: M_InventoryLine.InventoryLine_MHD
-- Column SQL (old): null
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2023-03-23T11:02:26.345Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,586328,582156,0,15,322,'InventoryLine_MHD','(SELECT REPLACE(att.ai_value, ''MHD: '', '''')::date from Report.fresh_Attributes att where att.m_attributesetinstance_id = M_InventoryLine.m_attributesetinstance_id   AND att.at_value=''HU_BestBeforeDate'')',TO_TIMESTAMP('2023-03-23 12:02:24','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N',0,'MHD',0,0,TO_TIMESTAMP('2023-03-23 12:02:24','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-03-23T11:02:26.465Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Column_ID=586328 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-03-23T11:02:26.686Z
/* DDL */  select update_Column_Translation_From_AD_Element(582156)
;

-- Field: Inventur Position (Alle) -> Inventur-Position -> MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- Field: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2023-03-23T11:05:00.137Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,DisplayLength,EntityType,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,586328,712922,0,546850,0,TO_TIMESTAMP('2023-03-23 12:04:57','YYYY-MM-DD HH24:MI:SS'),100,0,'D',0,'Y','Y','Y','N','N','N','N','N','MHD',0,130,0,1,1,TO_TIMESTAMP('2023-03-23 12:04:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-03-23T11:05:00.263Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y') AND t.AD_Field_ID=712922 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-03-23T11:05:00.371Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(582156)
;

-- 2023-03-23T11:05:00.491Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=712922
;

-- 2023-03-23T11:05:00.605Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(712922)
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2023-03-23T11:06:33.422Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,712922,0,546850,550446,616065,'F',TO_TIMESTAMP('2023-03-23 12:06:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','N','Y','N','N','N',0,'MHD',37,0,0,TO_TIMESTAMP('2023-03-23 12:06:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2023-03-23T11:06:57.730Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-23 12:06:57','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616065
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2023-03-23T11:07:44.281Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2023-03-23 12:07:44','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616065
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2023-03-23T11:07:45.267Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2023-03-23 12:07:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2023-03-23T11:07:46.213Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2023-03-23 12:07:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2023-03-23T11:07:47.142Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2023-03-23 12:07:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2023-03-23T11:07:48.133Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2023-03-23 12:07:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2023-03-23T11:07:49.077Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2023-03-23 12:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Gezählt
-- Column: M_InventoryLine.IsCounted
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2023-03-23T11:07:49.965Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2023-03-23 12:07:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2023-03-23T11:08:43.909Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2023-03-23 12:08:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616065
;

-- UI Element: Inventur Position (Alle) -> Inventur-Position.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2023-03-23T11:09:47.908Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2023-03-23 12:09:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_UI_Element_ID=616064
;



----------------------


-- Changes for easier reading



-- Table: M_InventoryLine
-- 2026-03-19T15:41:41.596Z
-- UPDATE AD_Table SET AD_Window_ID=168,Updated=TO_TIMESTAMP('2026-03-19 15:41:41.497000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Table_ID=322
-- ;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2026-03-19T15:50:03.371Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-19 15:50:03.371000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616047
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2026-03-19T15:50:03.586Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-19 15:50:03.586000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2026-03-19T15:50:03.793Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-19 15:50:03.793000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2026-03-19T15:50:03.999Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-03-19 15:50:03.999000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2026-03-19T15:50:04.209Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-03-19 15:50:04.209000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616051
;




--- Doc Type







-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T15:57:56.085Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,CloningStrategy,ColumnName,ColumnSQL,Created,CreatedBy,DDL_NoForeignKey,Description,EntityType,FacetFilterSeqNo,FieldLength,Help,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterInactiveValues,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,592257,196,0,30,322,'XX','C_DocType_ID','(SELECT C_DocType_ID from M_Inventory where  M_Inventory_id = M_InventoryLine.M_Inventory_ID)',TO_TIMESTAMP('2026-03-19 15:57:54.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'N','Belegart oder Verarbeitungsvorgaben','D',0,10,'Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N',0,'Belegart',0,0,TO_TIMESTAMP('2026-03-19 15:57:54.610000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

-- 2026-03-19T15:57:56.118Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=592257 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2026-03-19T15:57:56.182Z
/* DDL */  select update_Column_Translation_From_AD_Element(196)
;

-- Field: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T16:05:05.822Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,592257,775477,0,546850,TO_TIMESTAMP('2026-03-19 16:05:05.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',10,'de.metas.swat','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2026-03-19 16:05:05.444000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-03-19T16:05:05.865Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=775477 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-03-19T16:05:05.902Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2026-03-19T16:05:05.962Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=775477
;

-- 2026-03-19T16:05:05.995Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(775477)
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T16:09:18.543Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,775477,0,546850,550446,648877,'F',TO_TIMESTAMP('2026-03-19 16:09:18.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','Y','N','N','Belegart',90,0,0,TO_TIMESTAMP('2026-03-19 16:09:18.071000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T16:20:52.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-19 16:20:52.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648877
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2026-03-19T16:20:52.326Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-19 16:20:52.326000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2026-03-19T16:20:52.538Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-03-19 16:20:52.538000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2026-03-19T16:20:52.765Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-19 16:20:52.765000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616053
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2026-03-19T16:20:52.972Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-19 16:20:52.972000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616054
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2026-03-19T16:20:53.178Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-19 16:20:53.178000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616064
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2026-03-19T16:20:53.391Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-19 16:20:53.391000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616065
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2026-03-19T16:20:53.919Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-19 16:20:53.919000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2026-03-19T16:20:54.131Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-19 16:20:54.131000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2026-03-19T16:20:54.343Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-03-19 16:20:54.343000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2026-03-19T16:20:54.548Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-03-19 16:20:54.548000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2026-03-19T16:20:54.744Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-03-19 16:20:54.744000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2026-03-19T16:20:54.957Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-03-19 16:20:54.957000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2026-03-19T16:20:55.155Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-03-19 16:20:55.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2026-03-19T16:20:55.363Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-03-19 16:20:55.363000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616051
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T16:21:50.523Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-03-19 16:21:50.523000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648877
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2026-03-19T16:21:50.747Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-03-19 16:21:50.747000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616051
;


-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T16:39:03.965Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-19 16:39:03.965000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648877
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2026-03-19T16:39:04.166Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-03-19 16:39:04.166000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616051
;




-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2026-03-19T16:57:35.323Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-19 16:57:35.323000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2026-03-19T16:57:35.531Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-19 16:57:35.531000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2026-03-19T16:57:35.741Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-19 16:57:35.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616053
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2026-03-19T16:57:35.952Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-03-19 16:57:35.952000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616054
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2026-03-19T16:57:36.165Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-19 16:57:36.165000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616064
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2026-03-19T16:57:36.386Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-19 16:57:36.386000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616065
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2026-03-19T16:57:36.595Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-19 16:57:36.595000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2026-03-19T16:57:36.805Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-19 16:57:36.805000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2026-03-19T16:57:36.997Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-19 16:57:36.997000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2026-03-19T16:57:37.205Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-19 16:57:37.205000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2026-03-19T16:57:37.412Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-03-19 16:57:37.412000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2026-03-19T16:57:37.614Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-03-19 16:57:37.614000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2026-03-19T16:57:37.818Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-03-19 16:57:37.818000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T16:57:38.027Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-03-19 16:57:38.027000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648877
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Sektion
-- Column: M_InventoryLine.AD_Org_ID
-- 2026-03-19T16:57:38.237Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=160,Updated=TO_TIMESTAMP('2026-03-19 16:57:38.237000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616051
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2026-03-19T16:57:38.431Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=170,Updated=TO_TIMESTAMP('2026-03-19 16:57:38.431000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616047
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventur-Position
-- Column: M_InventoryLine.M_InventoryLine_ID
-- 2026-03-19T16:57:38.642Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=180,Updated=TO_TIMESTAMP('2026-03-19 16:57:38.642000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648879
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventur
-- Column: M_InventoryLine.M_Inventory_ID
-- 2026-03-19T16:59:52.672Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-03-19 16:59:52.672000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616047
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Inventur-Position
-- Column: M_InventoryLine.M_InventoryLine_ID
-- 2026-03-19T16:59:52.880Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-03-19 16:59:52.880000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648879
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Belegart
-- Column: M_InventoryLine.C_DocType_ID
-- 2026-03-19T17:01:11.155Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-03-19 17:01:11.155000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=648877
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Bewegungsdatum
-- Column: M_InventoryLine.MovementDate
-- 2026-03-19T17:01:11.364Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-03-19 17:01:11.364000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616062
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Beschreibung
-- Column: M_InventoryLine.InventoryLine_Description
-- 2026-03-19T17:01:11.575Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-03-19 17:01:11.575000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616045
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Lagerort
-- Column: M_InventoryLine.M_Locator_ID
-- 2026-03-19T17:01:11.787Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-03-19 17:01:11.787000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616053
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Produkt
-- Column: M_InventoryLine.M_Product_ID
-- 2026-03-19T17:01:11.982Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-03-19 17:01:11.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616054
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Chargen Nr.
-- Column: M_InventoryLine.InventoryLine_LotNo
-- 2026-03-19T17:01:12.204Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-03-19 17:01:12.204000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616064
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.MHD
-- Column: M_InventoryLine.InventoryLine_MHD
-- 2026-03-19T17:01:12.405Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-03-19 17:01:12.405000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616065
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Handling Units
-- Column: M_InventoryLine.M_HU_ID
-- 2026-03-19T17:01:12.615Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-03-19 17:01:12.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616056
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Merkmale
-- Column: M_InventoryLine.M_AttributeSetInstance_ID
-- 2026-03-19T17:01:12.824Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-03-19 17:01:12.824000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616057
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Buchmenge
-- Column: M_InventoryLine.QtyBook
-- 2026-03-19T17:01:13.023Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=110,Updated=TO_TIMESTAMP('2026-03-19 17:01:13.023000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616058
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zählmenge
-- Column: M_InventoryLine.QtyCount
-- 2026-03-19T17:01:13.251Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=120,Updated=TO_TIMESTAMP('2026-03-19 17:01:13.251000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616059
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.korrekturmenge
-- Column: M_InventoryLine.InventoryLine_CorrectionQuantity
-- 2026-03-19T17:01:13.475Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=130,Updated=TO_TIMESTAMP('2026-03-19 17:01:13.475000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616063
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Gezählt
-- Column: M_InventoryLine.IsCounted
-- 2026-03-19T17:01:13.683Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=140,Updated=TO_TIMESTAMP('2026-03-19 17:01:13.683000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616060
;

-- UI Element: Inventur Position (Alle)(541685,de.metas.swat) -> Inventur-Position(546850,de.metas.swat) -> main -> 10 -> default.Zugewiesen an
-- Column: M_InventoryLine.AssignedTo
-- 2026-03-19T17:01:13.896Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=150,Updated=TO_TIMESTAMP('2026-03-19 17:01:13.896000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=616052
;




