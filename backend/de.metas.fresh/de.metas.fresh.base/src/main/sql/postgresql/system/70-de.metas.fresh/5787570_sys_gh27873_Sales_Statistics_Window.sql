-- Run mode: SWING_CLIENT

-- Window: Verkaufsstatistik, InternalName=null
-- 2026-02-10T10:32:42.035Z
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,Help,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority) VALUES (0,584505,0,542070,TO_TIMESTAMP('2026-02-10 10:32:41.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auswertung von Verkaufsaufträgen, Lieferungen und Rechnungen.','D','Das Fenster "Verkaufsstatistik“ stellt eine Übersicht über Verkaufsaufträge, Lieferungen und Rechnungen einer Organisation bereit. Es unterstützt die Analyse von Mengen und Beträgen sowie den Vergleich zwischen Bestellung, Lieferung und Rechnungsstellung je Produkt und Geschäftspartner.','Y','N','N','N','N','N','N','Y','Verkaufsstatistik','N',TO_TIMESTAMP('2026-02-10 10:32:41.502000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'M',0,0,100)
;

-- 2026-02-10T10:32:42.094Z
INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542070 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

-- 2026-02-10T10:32:42.212Z
/* DDL */  select update_window_translation_from_ad_element(584505)
;

-- 2026-02-10T10:32:42.292Z
DELETE FROM AD_Element_Link WHERE AD_Window_ID=542070
;

-- 2026-02-10T10:32:42.349Z
/* DDL */ select AD_Element_Link_Create_Missing_Window(542070)
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T10:33:30.652Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 10:33:30.652000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591973
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-02-10T10:34:11.029Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 10:34:11.029000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591969
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T10:34:48.853Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 10:34:48.853000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591981
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T10:35:21.924Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 10:35:21.923000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591975
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-02-10T10:35:54.108Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=10,Updated=TO_TIMESTAMP('2026-02-10 10:35:54.108000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591969
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T10:36:10.560Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2026-02-10 10:36:10.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591973
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T10:36:27.289Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2026-02-10 10:36:27.289000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591975
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T10:36:49.542Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2026-02-10 10:36:49.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591981
;

-- Tab: Verkaufsstatistik(542070,D) -> Verkaufsstatistik
-- Table: C_Order_M_InOut_C_Invoice_Overview_V
-- 2026-02-10T10:38:20.398Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,584506,0,548990,542578,542070,'Y',TO_TIMESTAMP('2026-02-10 10:38:19.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','A','C_Order_M_InOut_C_Invoice_Overview_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Verkaufsstatistik','N',10,0,TO_TIMESTAMP('2026-02-10 10:38:19.686000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:20.458Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=548990 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2026-02-10T10:38:20.518Z
/* DDL */  select update_tab_translation_from_ad_element(584506)
;

-- 2026-02-10T10:38:20.579Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(548990)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Verkaufsstatistik
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_Order_M_InOut_C_Invoice_Overview_V_ID
-- 2026-02-10T10:38:49.715Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591965,772010,0,548990,TO_TIMESTAMP('2026-02-10 10:38:49.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,19,'D','Y','N','N','N','N','N','N','N','Verkaufsstatistik',TO_TIMESTAMP('2026-02-10 10:38:49.100000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:49.782Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772010 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:49.840Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584506)
;

-- 2026-02-10T10:38:49.900Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772010
;

-- 2026-02-10T10:38:49.957Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772010)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Verkaufstransaktion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.IsSOTrx
-- 2026-02-10T10:38:50.618Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591966,772011,0,548990,TO_TIMESTAMP('2026-02-10 10:38:50.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufstransaktion',1,'D','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','N','N','N','N','N','Verkaufstransaktion',TO_TIMESTAMP('2026-02-10 10:38:50.081000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:50.686Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772011 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:50.750Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1106)
;

-- 2026-02-10T10:38:50.809Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772011
;

-- 2026-02-10T10:38:50.865Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772011)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T10:38:51.507Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591967,772012,0,548990,TO_TIMESTAMP('2026-02-10 10:38:50.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben',10,'D','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','N','N','N','N','N','Belegart',TO_TIMESTAMP('2026-02-10 10:38:50.982000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:51.566Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772012 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:51.624Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(196)
;

-- 2026-02-10T10:38:51.687Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772012
;

-- 2026-02-10T10:38:51.744Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772012)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Dokument Basis Typ
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T10:38:52.376Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591968,772013,0,548990,TO_TIMESTAMP('2026-02-10 10:38:51.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'',3,'D','','Y','N','N','N','N','N','N','N','Dokument Basis Typ',TO_TIMESTAMP('2026-02-10 10:38:51.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:52.434Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772013 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:52.490Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(865)
;

-- 2026-02-10T10:38:52.551Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772013
;

-- 2026-02-10T10:38:52.609Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772013)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Nr.
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-02-10T10:38:53.262Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591969,772014,0,548990,TO_TIMESTAMP('2026-02-10 10:38:52.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document sequence number of the document',30,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2026-02-10 10:38:52.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:53.330Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772014 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:53.388Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290)
;

-- 2026-02-10T10:38:53.456Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772014
;

-- 2026-02-10T10:38:53.515Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772014)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Verarbeitet
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Processed
-- 2026-02-10T10:38:54.155Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591970,772015,0,548990,TO_TIMESTAMP('2026-02-10 10:38:53.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ',1,'D','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','N','N','N','N','N','Verarbeitet',TO_TIMESTAMP('2026-02-10 10:38:53.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:54.216Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772015 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:54.275Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(1047)
;

-- 2026-02-10T10:38:54.338Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772015
;

-- 2026-02-10T10:38:54.396Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772015)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Belegstatus
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-02-10T10:38:55.047Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591971,772016,0,548990,TO_TIMESTAMP('2026-02-10 10:38:54.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'The current status of the document',2,'D','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2026-02-10 10:38:54.520000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:55.106Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772016 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:55.163Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289)
;

-- 2026-02-10T10:38:55.225Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772016
;

-- 2026-02-10T10:38:55.284Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772016)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T10:38:55.919Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591972,772017,0,548990,TO_TIMESTAMP('2026-02-10 10:38:55.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,29,'D','Y','N','N','N','N','N','N','N','Datum',TO_TIMESTAMP('2026-02-10 10:38:55.398000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:55.978Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772017 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:56.037Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(577762)
;

-- 2026-02-10T10:38:56.099Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772017
;

-- 2026-02-10T10:38:56.158Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772017)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T10:38:56.798Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591973,772018,0,548990,TO_TIMESTAMP('2026-02-10 10:38:56.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','Geschäftspartner',TO_TIMESTAMP('2026-02-10 10:38:56.272000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:56.858Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772018 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:56.917Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(187)
;

-- 2026-02-10T10:38:56.985Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772018
;

-- 2026-02-10T10:38:57.077Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772018)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Standort
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_Location_ID
-- 2026-02-10T10:38:57.776Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591974,772019,0,548990,TO_TIMESTAMP('2026-02-10 10:38:57.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners',10,'D','Identifiziert die Adresse des Geschäftspartners','Y','N','N','N','N','N','N','N','Standort',TO_TIMESTAMP('2026-02-10 10:38:57.210000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:57.835Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772019 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:57.893Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(189)
;

-- 2026-02-10T10:38:57.955Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772019
;

-- 2026-02-10T10:38:58.012Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772019)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T10:38:58.655Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591975,772020,0,548990,TO_TIMESTAMP('2026-02-10 10:38:58.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2026-02-10 10:38:58.130000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:58.714Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772020 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:58.772Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454)
;

-- 2026-02-10T10:38:58.842Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772020
;

-- 2026-02-10T10:38:58.901Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772020)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-02-10T10:38:59.534Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591976,772021,0,548990,TO_TIMESTAMP('2026-02-10 10:38:59.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit',10,'D','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','N','N','N','N','N','Maßeinheit',TO_TIMESTAMP('2026-02-10 10:38:59.018000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:38:59.592Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772021 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:38:59.652Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(215)
;

-- 2026-02-10T10:38:59.718Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772021
;

-- 2026-02-10T10:38:59.777Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772021)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Menge
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Qty
-- 2026-02-10T10:39:00.425Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591977,772022,0,548990,TO_TIMESTAMP('2026-02-10 10:38:59.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge',14,'D','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','N','N','N','N','N','Menge',TO_TIMESTAMP('2026-02-10 10:38:59.895000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:39:00.484Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772022 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:39:00.542Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(526)
;

-- 2026-02-10T10:39:00.605Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772022
;

-- 2026-02-10T10:39:00.664Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772022)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-02-10T10:39:01.312Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591978,772023,0,548990,TO_TIMESTAMP('2026-02-10 10:39:00.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren',14,'D','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','N','N','N','N','N','N','Zeilennetto',TO_TIMESTAMP('2026-02-10 10:39:00.788000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:39:01.373Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772023 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:39:01.431Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(441)
;

-- 2026-02-10T10:39:01.502Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772023
;

-- 2026-02-10T10:39:01.560Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772023)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-02-10T10:39:02.203Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591979,772024,0,548990,TO_TIMESTAMP('2026-02-10 10:39:01.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,14,'D','Y','N','N','N','N','N','N','N','Aktueller Bestand',TO_TIMESTAMP('2026-02-10 10:39:01.674000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:39:02.262Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772024 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:39:02.320Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(584507)
;

-- 2026-02-10T10:39:02.379Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772024
;

-- 2026-02-10T10:39:02.438Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772024)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Mandant
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Client_ID
-- 2026-02-10T10:39:03.062Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591980,772025,0,548990,TO_TIMESTAMP('2026-02-10 10:39:02.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-02-10 10:39:02.552000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:39:03.120Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772025 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:39:03.182Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102)
;

-- 2026-02-10T10:39:03.289Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772025
;

-- 2026-02-10T10:39:03.346Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772025)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Sektion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T10:39:03.985Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591981,772026,0,548990,TO_TIMESTAMP('2026-02-10 10:39:03.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2026-02-10 10:39:03.462000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:39:04.045Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:39:04.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113)
;

-- 2026-02-10T10:39:04.218Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772026
;

-- 2026-02-10T10:39:04.276Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772026)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Aktiv
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.IsActive
-- 2026-02-10T10:39:04.921Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,591984,772027,0,548990,TO_TIMESTAMP('2026-02-10 10:39:04.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2026-02-10 10:39:04.404000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T10:39:04.979Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T10:39:05.036Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348)
;

-- 2026-02-10T10:39:05.166Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772027
;

-- 2026-02-10T10:39:05.224Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772027)
;

-- Tab: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D)
-- UI Section: main
-- 2026-02-10T10:43:33.864Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,548990,547512,TO_TIMESTAMP('2026-02-10 10:43:33.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-10 10:43:33.445000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

-- 2026-02-10T10:43:33.924Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547512 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- UI Section: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main
-- UI Column: 10
-- 2026-02-10T10:44:20.096Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549173,547512,TO_TIMESTAMP('2026-02-10 10:44:19.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-02-10 10:44:19.670000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Section: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main
-- UI Column: 20
-- 2026-02-10T10:44:28.660Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549174,547512,TO_TIMESTAMP('2026-02-10 10:44:28.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-02-10 10:44:28.247000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10
-- UI Element Group: Document
-- 2026-02-10T10:45:51.939Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549173,554807,TO_TIMESTAMP('2026-02-10 10:45:51.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Document',10,'primary',TO_TIMESTAMP('2026-02-10 10:45:51.509000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Nr.
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-02-10T10:46:31.386Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772014,0,548990,554807,646918,'F',TO_TIMESTAMP('2026-02-10 10:46:30.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','Y','N','N','N',0,'Nr.',10,0,0,TO_TIMESTAMP('2026-02-10 10:46:30.909000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T10:47:49.033Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772012,0,548990,554807,646919,'F',TO_TIMESTAMP('2026-02-10 10:47:48.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Belegart oder Verarbeitungsvorgaben','Die Belegart bestimmt den Nummernkreis und die Vorgaben für die Belegverarbeitung.','Y','N','N','Y','N','N','N',0,'Belegart',20,0,0,TO_TIMESTAMP('2026-02-10 10:47:48.567000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T10:48:01.862Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772018,0,548990,554807,646920,'F',TO_TIMESTAMP('2026-02-10 10:48:01.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','N','N','N',0,'Geschäftspartner',30,0,0,TO_TIMESTAMP('2026-02-10 10:48:01.400000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Standort
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_Location_ID
-- 2026-02-10T10:48:25.574Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772019,0,548990,554807,646921,'F',TO_TIMESTAMP('2026-02-10 10:48:25.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Identifiziert die (Liefer-) Adresse des Geschäftspartners','Identifiziert die Adresse des Geschäftspartners','Y','N','N','Y','N','N','N',0,'Standort',40,0,0,TO_TIMESTAMP('2026-02-10 10:48:25.109000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10
-- UI Element Group: Product
-- 2026-02-10T10:49:11.676Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549173,554808,TO_TIMESTAMP('2026-02-10 10:49:11.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Product',20,TO_TIMESTAMP('2026-02-10 10:49:11.258000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T10:49:37.652Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772020,0,548990,554808,646922,'F',TO_TIMESTAMP('2026-02-10 10:49:37.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','N','N','N',0,'Produkt',10,0,0,TO_TIMESTAMP('2026-02-10 10:49:37.169000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-02-10T10:50:14.299Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772024,0,548990,554808,646923,'F',TO_TIMESTAMP('2026-02-10 10:50:13.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Aktueller Bestand',20,0,0,TO_TIMESTAMP('2026-02-10 10:50:13.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-02-10T10:50:40.982Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772021,0,548990,554808,646924,'F',TO_TIMESTAMP('2026-02-10 10:50:40.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Maßeinheit','Eine eindeutige (nicht monetäre) Maßeinheit','Y','N','N','Y','N','N','N',0,'Maßeinheit',30,0,0,TO_TIMESTAMP('2026-02-10 10:50:40.506000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-02-10T10:51:06.966Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772023,0,548990,554808,646925,'F',TO_TIMESTAMP('2026-02-10 10:51:06.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nettowert Zeile (Menge * Einzelpreis) ohne Fracht und Gebühren','Indicates the extended line amount based on the quantity and the actual price.  Any additional charges or freight are not included.  The Amount may or may not include tax.  If the price list is inclusive tax, the line amount is the same as the line total.','Y','N','N','Y','N','N','N',0,'Zeilennetto',40,0,0,TO_TIMESTAMP('2026-02-10 10:51:06.501000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20
-- UI Element Group: Flags
-- 2026-02-10T10:51:28.243Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549174,554809,TO_TIMESTAMP('2026-02-10 10:51:27.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Flags',10,TO_TIMESTAMP('2026-02-10 10:51:27.836000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Flags.Aktiv
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.IsActive
-- 2026-02-10T10:51:58.317Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772027,0,548990,554809,646926,'F',TO_TIMESTAMP('2026-02-10 10:51:57.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2026-02-10 10:51:57.835000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Flags.Verkaufstransaktion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.IsSOTrx
-- 2026-02-10T10:52:47.457Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772011,0,548990,554809,646927,'F',TO_TIMESTAMP('2026-02-10 10:52:46.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Dies ist eine Verkaufstransaktion','The Sales Transaction checkbox indicates if this item is a Sales Transaction.','Y','N','N','Y','N','N','N',0,'Verkaufstransaktion',20,0,0,TO_TIMESTAMP('2026-02-10 10:52:46.989000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Flags.Verarbeitet
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Processed
-- 2026-02-10T10:53:01.314Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772015,0,548990,554809,646928,'F',TO_TIMESTAMP('2026-02-10 10:53:00.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Checkbox sagt aus, ob der Datensatz verarbeitet wurde. ','Verarbeitete Datensatz dürfen in der Regel nich mehr geändert werden.','Y','N','N','Y','N','N','N',0,'Verarbeitet',30,0,0,TO_TIMESTAMP('2026-02-10 10:53:00.842000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Menge
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Qty
-- 2026-02-10T10:54:05.270Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772022,0,548990,554808,646929,'F',TO_TIMESTAMP('2026-02-10 10:54:04.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Menge','Menge bezeichnet die Anzahl eines bestimmten Produktes oder Artikels für dieses Dokument.','Y','N','N','Y','N','N','N',0,'Menge',15,0,0,TO_TIMESTAMP('2026-02-10 10:54:04.785000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20
-- UI Element Group: Status
-- 2026-02-10T10:54:31.335Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549174,554810,TO_TIMESTAMP('2026-02-10 10:54:30.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Status',20,TO_TIMESTAMP('2026-02-10 10:54:30.925000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T10:55:00.150Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772017,0,548990,554810,646930,'F',TO_TIMESTAMP('2026-02-10 10:54:59.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Datum',10,0,0,TO_TIMESTAMP('2026-02-10 10:54:59.611000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Dokument Basis Typ
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T10:55:20.236Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772013,0,548990,554810,646931,'F',TO_TIMESTAMP('2026-02-10 10:55:19.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Dokument Basis Typ',20,0,0,TO_TIMESTAMP('2026-02-10 10:55:19.773000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T10:58:53.096Z
UPDATE AD_Column SET AD_Reference_Value_ID=183,Updated=TO_TIMESTAMP('2026-02-10 10:58:53.096000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591968
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Dokument Basis Typ
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T11:04:25.504Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772013,0,548990,554810,646933,'F',TO_TIMESTAMP('2026-02-10 11:04:25.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','N','N','Y','N','N','N',0,'Dokument Basis Typ',30,0,0,TO_TIMESTAMP('2026-02-10 11:04:25.008000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Column: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20
-- UI Element Group: Org
-- 2026-02-10T11:04:39.209Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,549174,554811,TO_TIMESTAMP('2026-02-10 11:04:38.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','Org',30,TO_TIMESTAMP('2026-02-10 11:04:38.736000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Org.Sektion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T11:05:10.355Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772026,0,548990,554811,646934,'F',TO_TIMESTAMP('2026-02-10 11:05:09.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','N','N','N',0,'Sektion',10,0,0,TO_TIMESTAMP('2026-02-10 11:05:09.876000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Org.Mandant
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Client_ID
-- 2026-02-10T11:05:33.690Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772025,0,548990,554811,646935,'F',TO_TIMESTAMP('2026-02-10 11:05:33.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2026-02-10 11:05:33.221000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Nr.
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-02-10T11:07:50.062Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=10,Updated=TO_TIMESTAMP('2026-02-10 11:07:50.062000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646918
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T11:07:50.417Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-10 11:07:50.417000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646919
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T11:07:50.770Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-10 11:07:50.770000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646930
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T11:07:51.167Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-10 11:07:51.167000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646920
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T11:07:51.512Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-10 11:07:51.512000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646922
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-02-10T11:07:51.860Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-10 11:07:51.860000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-02-10T11:07:52.212Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-10 11:07:52.212000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-02-10T11:07:52.560Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-10 11:07:52.560000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Org.Sektion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T11:07:52.907Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-10 11:07:52.907000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646934
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Dokument Basis Typ
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T11:08:29.629Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-10 11:08:29.629000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646933
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T11:08:29.977Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-10 11:08:29.976000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646919
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T11:08:30.324Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-10 11:08:30.324000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646930
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T11:08:30.676Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-10 11:08:30.675000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646920
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T11:08:31.030Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-10 11:08:31.030000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646922
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-02-10T11:08:31.378Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-10 11:08:31.378000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-02-10T11:08:31.724Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-10 11:08:31.722000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-02-10T11:08:32.069Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-10 11:08:32.069000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Org.Sektion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T11:08:32.420Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=100,Updated=TO_TIMESTAMP('2026-02-10 11:08:32.420000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646934
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T11:08:49.892Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 11:08:49.892000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591972
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T11:09:17.146Z
UPDATE AD_Column SET FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 11:09:17.146000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591968
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-02-10T11:10:07.649Z
UPDATE AD_Column SET AD_Reference_Value_ID=131, FilterOperator='E', IsSelectionColumn='Y',Updated=TO_TIMESTAMP('2026-02-10 11:10:07.649000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591971
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T11:12:42.066Z
UPDATE AD_Column SET IsSelectionColumn='N', SelectionColumnSeqNo=0,Updated=TO_TIMESTAMP('2026-02-10 11:12:42.065000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591968
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T11:12:58.734Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=20,Updated=TO_TIMESTAMP('2026-02-10 11:12:58.734000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591972
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T11:13:21.452Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=30,Updated=TO_TIMESTAMP('2026-02-10 11:13:21.452000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591967
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocStatus
-- 2026-02-10T11:13:43.886Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=40,Updated=TO_TIMESTAMP('2026-02-10 11:13:43.886000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591971
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T11:14:04.894Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=50,Updated=TO_TIMESTAMP('2026-02-10 11:14:04.894000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591973
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T11:14:22.547Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=60,Updated=TO_TIMESTAMP('2026-02-10 11:14:22.547000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591975
;

-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T11:14:42.542Z
UPDATE AD_Column SET IsSelectionColumn='Y', SelectionColumnSeqNo=70,Updated=TO_TIMESTAMP('2026-02-10 11:14:42.542000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_Column_ID=591981
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Dokument Basis Typ
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T11:16:41.347Z
UPDATE AD_UI_Element SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2026-02-10 11:16:41.347000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646933
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T11:16:41.694Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=20,Updated=TO_TIMESTAMP('2026-02-10 11:16:41.693000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646919
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T11:16:42.048Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=30,Updated=TO_TIMESTAMP('2026-02-10 11:16:42.048000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646930
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Geschäftspartner
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_BPartner_ID
-- 2026-02-10T11:16:42.396Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=40,Updated=TO_TIMESTAMP('2026-02-10 11:16:42.395000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646920
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Produkt
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.M_Product_ID
-- 2026-02-10T11:16:42.741Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=50,Updated=TO_TIMESTAMP('2026-02-10 11:16:42.741000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646922
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Aktueller Bestand
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Current_Qty_Sum
-- 2026-02-10T11:16:43.088Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=60,Updated=TO_TIMESTAMP('2026-02-10 11:16:43.088000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646923
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-02-10T11:16:43.437Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=70,Updated=TO_TIMESTAMP('2026-02-10 11:16:43.437000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Zeilennetto
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.LineNetAmt
-- 2026-02-10T11:16:43.780Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=80,Updated=TO_TIMESTAMP('2026-02-10 11:16:43.779000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646925
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Org.Sektion
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Org_ID
-- 2026-02-10T11:16:44.125Z
UPDATE AD_UI_Element SET IsDisplayedGrid='Y', SeqNoGrid=90,Updated=TO_TIMESTAMP('2026-02-10 11:16:44.125000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646934
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2026-02-10T11:18:27.706Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2026-02-10T11:18:27.774Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2026-02-10T11:18:27.863Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2026-02-10T11:18:27.933Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2026-02-10T11:18:28.011Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2026-02-10T11:18:28.107Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2026-02-10T11:18:28.222Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2026-02-10T11:18:28.285Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2026-02-10T11:18:28.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2026-02-10T11:18:28.415Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2026-02-10T11:18:28.473Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2026-02-10T11:18:28.530Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2026-02-10T11:18:28.587Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2026-02-10T11:18:28.644Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2026-02-10T11:18:28.701Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2026-02-10T11:18:28.760Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2026-02-10T11:18:28.818Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2026-02-10T11:18:28.876Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2026-02-10T11:18:28.932Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2026-02-10T11:18:28.989Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2026-02-10T11:18:29.046Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Reordering children of `webUI`
-- Node name: `CRM`
-- 2026-02-10T11:18:35.800Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000008 AND AD_Tree_ID=10
;

-- Node name: `Marketing`
-- 2026-02-10T11:18:35.859Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541091 AND AD_Tree_ID=10
;

-- Node name: `Product Management`
-- 2026-02-10T11:18:35.917Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000009 AND AD_Tree_ID=10
;

-- Node name: `Sales`
-- 2026-02-10T11:18:35.976Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000010 AND AD_Tree_ID=10
;

-- Node name: `Purchase`
-- 2026-02-10T11:18:36.034Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000011 AND AD_Tree_ID=10
;

-- Node name: `Pricing`
-- 2026-02-10T11:18:36.092Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540752 AND AD_Tree_ID=10
;

-- Node name: `Warehouse Management`
-- 2026-02-10T11:18:36.150Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000012 AND AD_Tree_ID=10
;

-- Node name: `Contract Management`
-- 2026-02-10T11:18:36.209Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000013 AND AD_Tree_ID=10
;

-- Node name: `Manufacturing`
-- 2026-02-10T11:18:36.266Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000014 AND AD_Tree_ID=10
;

-- Node name: `Material Receipt`
-- 2026-02-10T11:18:36.324Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000017 AND AD_Tree_ID=10
;

-- Node name: `Billing`
-- 2026-02-10T11:18:36.382Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000018 AND AD_Tree_ID=10
;

-- Node name: `Finance`
-- 2026-02-10T11:18:36.441Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000015 AND AD_Tree_ID=10
;

-- Node name: `Logistics`
-- 2026-02-10T11:18:36.498Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000016 AND AD_Tree_ID=10
;

-- Node name: `Shipment`
-- 2026-02-10T11:18:36.555Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000019 AND AD_Tree_ID=10
;

-- Node name: `Pharma`
-- 2026-02-10T11:18:36.613Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541012 AND AD_Tree_ID=10
;

-- Node name: `Project Management`
-- 2026-02-10T11:18:36.670Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541329 AND AD_Tree_ID=10
;

-- Node name: `Forum Datenaustausch`
-- 2026-02-10T11:18:36.729Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541229 AND AD_Tree_ID=10
;

-- Node name: `Seminar-Verwaltung`
-- 2026-02-10T11:18:36.786Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=17, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541340 AND AD_Tree_ID=10
;

-- Node name: `Client/ Organisation`
-- 2026-02-10T11:18:36.843Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=18, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540833 AND AD_Tree_ID=10
;

-- Node name: `Service delivery`
-- 2026-02-10T11:18:36.901Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=19, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541428 AND AD_Tree_ID=10
;

-- Node name: `System`
-- 2026-02-10T11:18:36.960Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000007, SeqNo=20, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000098 AND AD_Tree_ID=10
;

-- Name: Verkaufsstatistik
-- Action Type: W
-- Window: Verkaufsstatistik(542070,D)
-- 2026-02-10T11:19:15.693Z
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,Description,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy) VALUES ('W',0,584505,542296,0,542070,TO_TIMESTAMP('2026-02-10 11:19:15.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auswertung von Verkaufsaufträgen, Lieferungen und Rechnungen.','D','Verkaufsstatistik','Y','N','N','N','N','Verkaufsstatistik',TO_TIMESTAMP('2026-02-10 11:19:15.084000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T11:19:15.759Z
INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542296 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

-- 2026-02-10T11:19:15.821Z
INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID, IsActive,Created,CreatedBy,Updated,UpdatedBy, AD_Tree_ID, Node_ID, Parent_ID, SeqNo) SELECT t.AD_Client_ID,0, 'Y', now(), 100, now(), 100,t.AD_Tree_ID, 542296, 0, 999 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT * FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND Node_ID=542296)
;

-- 2026-02-10T11:19:15.883Z
/* DDL */  select update_menu_translation_from_ad_element(584505)
;

-- Reordering children of `Service`
-- Node name: `Time Type`
-- 2026-02-10T11:19:21.319Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=369 AND AD_Tree_ID=10
;

-- Node name: `Expense Report`
-- 2026-02-10T11:19:21.384Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=318 AND AD_Tree_ID=10
;

-- Node name: `Expenses (to be invoiced)`
-- 2026-02-10T11:19:21.442Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=327 AND AD_Tree_ID=10
;

-- Node name: `Create Sales Orders from Expense`
-- 2026-02-10T11:19:21.505Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=328 AND AD_Tree_ID=10
;

-- Node name: `Expenses (not reimbursed)`
-- 2026-02-10T11:19:21.571Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=349 AND AD_Tree_ID=10
;

-- Node name: `Create AP Expense Invoices`
-- 2026-02-10T11:19:21.638Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=329 AND AD_Tree_ID=10
;

-- Node name: `Service Level`
-- 2026-02-10T11:19:21.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=284 AND AD_Tree_ID=10
;

-- Node name: `Training`
-- 2026-02-10T11:19:21.768Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=344 AND AD_Tree_ID=10
;

-- Node name: `Verkaufsstatistik`
-- 2026-02-10T11:19:21.837Z
UPDATE AD_TreeNodeMM SET Parent_ID=271, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542296 AND AD_Tree_ID=10
;

-- Reordering children of `Sales`
-- Node name: `CreditPass configuration`
-- 2026-02-10T11:19:25.938Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=0, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541221 AND AD_Tree_ID=10
;

-- Node name: `Sales Order`
-- 2026-02-10T11:19:26.061Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=1, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000040 AND AD_Tree_ID=10
;

-- Node name: `Verkaufsstatistik`
-- 2026-02-10T11:19:26.118Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=2, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542296 AND AD_Tree_ID=10
;

-- Node name: `Alberta Prescription Request`
-- 2026-02-10T11:19:26.176Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=3, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541703 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition`
-- 2026-02-10T11:19:26.236Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=4, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000097 AND AD_Tree_ID=10
;

-- Node name: `Auftragsdisposition (EDI-Import) (Legacy-EDI-Import)`
-- 2026-02-10T11:19:26.295Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=5, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542255 AND AD_Tree_ID=10
;

-- Node name: `Order Control`
-- 2026-02-10T11:19:26.352Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=6, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540855 AND AD_Tree_ID=10
;

-- Node name: `Sales Opportunity Board (Prototype)`
-- 2026-02-10T11:19:26.408Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=7, Updated=now(), UpdatedBy=100 WHERE  Node_ID=540867 AND AD_Tree_ID=10
;

-- Node name: `Credit Limit Type`
-- 2026-02-10T11:19:26.464Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=8, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541038 AND AD_Tree_ID=10
;

-- Node name: `Actions`
-- 2026-02-10T11:19:26.522Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=9, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000046 AND AD_Tree_ID=10
;

-- Node name: `Reports`
-- 2026-02-10T11:19:26.581Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=10, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000048 AND AD_Tree_ID=10
;

-- Node name: `Settings`
-- 2026-02-10T11:19:26.637Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=11, Updated=now(), UpdatedBy=100 WHERE  Node_ID=1000050 AND AD_Tree_ID=10
;

-- Node name: `CreditPass transaction results`
-- 2026-02-10T11:19:26.695Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=12, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541226 AND AD_Tree_ID=10
;

-- Node name: `Commission`
-- 2026-02-10T11:19:26.753Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=13, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541369 AND AD_Tree_ID=10
;

-- Node name: `Incoterm`
-- 2026-02-10T11:19:26.810Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=14, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541784 AND AD_Tree_ID=10
;

-- Node name: `Available for sales`
-- 2026-02-10T11:19:26.869Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=15, Updated=now(), UpdatedBy=100 WHERE  Node_ID=541962 AND AD_Tree_ID=10
;

-- Node name: `Point of Sale (POS)`
-- 2026-02-10T11:19:26.926Z
UPDATE AD_TreeNodeMM SET Parent_ID=1000010, SeqNo=16, Updated=now(), UpdatedBy=100 WHERE  Node_ID=542171 AND AD_Tree_ID=10
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Dokument Basis Typ
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocBaseType
-- 2026-02-10T11:23:01.551Z
DELETE FROM AD_UI_Element WHERE AD_UI_Element_ID=646933
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Belegart
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_DocType_ID
-- 2026-02-10T11:24:08.470Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-02-10 11:24:08.470000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646919
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Datum
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Date
-- 2026-02-10T11:25:46.040Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-02-10 11:25:46.040000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646930
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Nr.
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.DocumentNo
-- 2026-02-10T11:26:30.615Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-02-10 11:26:30.615000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646918
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Product.Maßeinheit
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.C_UOM_ID
-- 2026-02-10T11:27:02.978Z
UPDATE AD_UI_Element SET WidgetSize='S',Updated=TO_TIMESTAMP('2026-02-10 11:27:02.978000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=646924
;


-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> Datensatz-ID
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Record_ID
-- 2026-02-10T13:53:45.115Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591985,772134,0,548990,0,TO_TIMESTAMP('2026-02-10 13:53:44.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID',0,'D',0,'The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'Datensatz-ID',0,0,10,0,1,1,TO_TIMESTAMP('2026-02-10 13:53:44.213000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T13:53:45.173Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772134 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T13:53:45.234Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(538)
;

-- 2026-02-10T13:53:45.306Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772134
;

-- 2026-02-10T13:53:45.363Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772134)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 10 -> Document.Datensatz-ID
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Record_ID
-- 2026-02-10T13:54:45.716Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772134,0,548990,554807,647015,'F',TO_TIMESTAMP('2026-02-10 13:54:44.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Direct internal record ID','The Record ID is the internal unique identifier of a record. Please note that zooming to the record may not be successful for Orders, Invoices and Shipment/Receipts as sometimes the Sales Order type is not known.','Y','N','N','Y','N','N','N',0,'Datensatz-ID',15,0,0,TO_TIMESTAMP('2026-02-10 13:54:44.917000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> DB-Tabelle
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Table_ID
-- 2026-02-10T14:10:10.760Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,FacetFilterSeqNo,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsHideGridColumnIfEmpty,IsOverrideFilterDefaultValue,IsReadOnly,IsSameLine,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,591986,772135,0,548990,0,TO_TIMESTAMP('2026-02-10 14:10:09.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information',0,'D',0,'The Database Table provides the information of the table definition',0,'Y','Y','Y','N','N','N','N','N','N','N',0,'DB-Tabelle',0,0,20,0,1,1,TO_TIMESTAMP('2026-02-10 14:10:09.940000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- 2026-02-10T14:10:10.820Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=772135 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2026-02-10T14:10:10.878Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(126)
;

-- 2026-02-10T14:10:10.942Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=772135
;

-- 2026-02-10T14:10:11Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(772135)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.DB-Tabelle
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.AD_Table_ID
-- 2026-02-10T14:10:37.798Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,772135,0,548990,554810,647016,'F',TO_TIMESTAMP('2026-02-10 14:10:37.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,'Database Table information','The Database Table provides the information of the table definition','Y','N','N','Y','N','N','N',0,'DB-Tabelle',30,0,0,TO_TIMESTAMP('2026-02-10 14:10:37.342000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- UI Element: Verkaufsstatistik(542070,D) -> Verkaufsstatistik(548990,D) -> main -> 20 -> Status.Datensatz-ID
-- Column: C_Order_M_InOut_C_Invoice_Overview_V.Record_ID
-- 2026-02-10T14:11:11.464Z
UPDATE AD_UI_Element SET AD_UI_ElementGroup_ID=554810, SeqNo=40,Updated=TO_TIMESTAMP('2026-02-10 14:11:11.464000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',UpdatedBy=100 WHERE AD_UI_Element_ID=647015
;

