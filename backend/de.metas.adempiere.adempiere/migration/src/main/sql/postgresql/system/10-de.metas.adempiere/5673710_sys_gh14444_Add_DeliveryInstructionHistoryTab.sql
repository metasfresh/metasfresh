-- 2023-01-26T08:39:59.859Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,581962,0,TO_TIMESTAMP('2023-01-26 10:39:59','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Instruction History','Delivery Instruction History',TO_TIMESTAMP('2023-01-26 10:39:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:39:59.864Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=581962 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:41:07.041Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542287,541632,'N',TO_TIMESTAMP('2023-01-26 10:41:06','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','N','N','Y','N','N','N',0,'Delivery Instructions History','NP','L','M_ShipperTransportation_Delivery_Instructions_V','DTI',TO_TIMESTAMP('2023-01-26 10:41:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:41:07.042Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542287 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-01-26T08:41:07.143Z
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNewYear,StartNo,Updated,UpdatedBy) VALUES (0,0,556196,TO_TIMESTAMP('2023-01-26 10:41:07','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_ShipperTransportation_Delivery_Instructions_V',1,'Y','N','Y','Y','M_ShipperTransportation_Delivery_Instructions_V','N',1000000,TO_TIMESTAMP('2023-01-26 10:41:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:41:07.150Z
CREATE SEQUENCE M_SHIPPERTRANSPORTATION_DELIVERY_INSTRUCTIONS_V_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:41:27.673Z
UPDATE AD_Table SET AD_Window_ID=541657,Updated=TO_TIMESTAMP('2023-01-26 10:41:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542287
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:41:46.616Z
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,IncludedTabNewRecordInputMode,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,581962,0,546754,542287,541657,'Y',TO_TIMESTAMP('2023-01-26 10:41:46','YYYY-MM-DD HH24:MI:SS'),100,'D','N','N','A','M_ShipperTransportation_Delivery_Instructions_V','Y','N','Y','Y','N','N','N','Y','Y','N','N','N','Y','Y','N','N','N',0,'Delivery Instruction History','N',30,0,TO_TIMESTAMP('2023-01-26 10:41:46','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:41:46.618Z
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=546754 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

-- 2023-01-26T08:41:46.642Z
/* DDL */  select update_tab_translation_from_ad_element(581962) 
;

-- 2023-01-26T08:41:46.653Z
/* DDL */ select AD_Element_Link_Create_Missing_Tab(546754)
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocumentNo
-- 2023-01-26T08:41:58.341Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585627,290,0,10,542287,'DocumentNo',TO_TIMESTAMP('2023-01-26 10:41:58','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','D',30,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','N','N','N','Y','Nr.',TO_TIMESTAMP('2023-01-26 10:41:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:41:58.342Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585627 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:41:58.344Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_ShipperTransportation_ID
-- 2023-01-26T08:41:58.908Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585628,540089,0,30,542287,'M_ShipperTransportation_ID',TO_TIMESTAMP('2023-01-26 10:41:58','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Transport Auftrag',TO_TIMESTAMP('2023-01-26 10:41:58','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:41:58.910Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585628 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:41:58.912Z
/* DDL */  select update_Column_Translation_From_AD_Element(540089) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Delivery_Planning_ID
-- 2023-01-26T08:41:59.606Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585629,581677,0,30,542287,'M_Delivery_Planning_ID',TO_TIMESTAMP('2023-01-26 10:41:59','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Lieferplanung',TO_TIMESTAMP('2023-01-26 10:41:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:41:59.607Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585629 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:41:59.610Z
/* DDL */  select update_Column_Translation_From_AD_Element(581677) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_SectionCode_ID
-- 2023-01-26T08:42:00.141Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585630,581238,0,30,542287,'M_SectionCode_ID',TO_TIMESTAMP('2023-01-26 10:41:59','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Sektionskennung',TO_TIMESTAMP('2023-01-26 10:41:59','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:00.142Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585630 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:00.144Z
/* DDL */  select update_Column_Translation_From_AD_Element(581238) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocStatus
-- 2023-01-26T08:42:00.539Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585631,289,0,10,542287,'DocStatus',TO_TIMESTAMP('2023-01-26 10:42:00','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','D',2,'The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','Y','N','N','N','N','N','N','N','N','Y','Belegstatus',TO_TIMESTAMP('2023-01-26 10:42:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:00.540Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585631 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:00.667Z
/* DDL */  select update_Column_Translation_From_AD_Element(289) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.DateDoc
-- 2023-01-26T08:42:01.068Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585632,265,0,16,542287,'DateDoc',TO_TIMESTAMP('2023-01-26 10:42:00','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Belegs','D',29,'The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','Y','N','N','N','N','N','N','N','N','Y','Belegdatum',TO_TIMESTAMP('2023-01-26 10:42:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:01.070Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585632 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:01.198Z
/* DDL */  select update_Column_Translation_From_AD_Element(265) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.LoadingDate
-- 2023-01-26T08:42:01.683Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585633,581900,0,16,542287,'LoadingDate',TO_TIMESTAMP('2023-01-26 10:42:01','YYYY-MM-DD HH24:MI:SS'),100,'D',29,'Y','Y','N','N','N','N','N','N','N','N','Y','Loading Date',TO_TIMESTAMP('2023-01-26 10:42:01','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:01.684Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585633 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:01.836Z
/* DDL */  select update_Column_Translation_From_AD_Element(581900) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.DeliveryDate
-- 2023-01-26T08:42:02.303Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585634,541376,0,16,542287,'DeliveryDate',TO_TIMESTAMP('2023-01-26 10:42:02','YYYY-MM-DD HH24:MI:SS'),100,'D',29,'Y','Y','N','N','N','N','N','N','N','N','Y','Lieferdatum',TO_TIMESTAMP('2023-01-26 10:42:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:02.305Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585634 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:02.455Z
/* DDL */  select update_Column_Translation_From_AD_Element(541376) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_Incoterms_ID
-- 2023-01-26T08:42:02.991Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585635,579927,0,30,542287,'C_Incoterms_ID',TO_TIMESTAMP('2023-01-26 10:42:02','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Incoterms',TO_TIMESTAMP('2023-01-26 10:42:02','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:02.992Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585635 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:02.994Z
/* DDL */  select update_Column_Translation_From_AD_Element(579927) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.IncotermLocation
-- 2023-01-26T08:42:03.386Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585636,501608,0,14,542287,'IncotermLocation',TO_TIMESTAMP('2023-01-26 10:42:03','YYYY-MM-DD HH24:MI:SS'),100,'Anzugebender Ort für Handelsklausel','D',500,'Y','Y','N','N','N','N','N','N','N','N','Y','IncotermLocation',TO_TIMESTAMP('2023-01-26 10:42:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:03.387Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585636 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:03.532Z
/* DDL */  select update_Column_Translation_From_AD_Element(501608) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_MeansOfTransportation_ID
-- 2023-01-26T08:42:04.061Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585637,581776,0,30,542287,'M_MeansOfTransportation_ID',TO_TIMESTAMP('2023-01-26 10:42:03','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','Y','N','N','N','N','N','N','N','N','Y','Means of Transportation',TO_TIMESTAMP('2023-01-26 10:42:03','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:04.062Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585637 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:04.064Z
/* DDL */  select update_Column_Translation_From_AD_Element(581776) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Product_ID
-- 2023-01-26T08:42:04.465Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585638,454,0,30,542287,'M_Product_ID',TO_TIMESTAMP('2023-01-26 10:42:04','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','D',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','Y','N','N','N','N','N','N','N','N','Y','Produkt',TO_TIMESTAMP('2023-01-26 10:42:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:04.466Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585638 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:04.606Z
/* DDL */  select update_Column_Translation_From_AD_Element(454) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Locator_ID
-- 2023-01-26T08:42:05.168Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585639,448,0,30,542287,'M_Locator_ID',TO_TIMESTAMP('2023-01-26 10:42:04','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','D',10,'"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','Y','N','N','N','N','N','N','N','N','Y','Lagerort',TO_TIMESTAMP('2023-01-26 10:42:04','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:05.169Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585639 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:05.172Z
/* DDL */  select update_Column_Translation_From_AD_Element(448) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-26T08:42:05.570Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585640,581690,0,29,542287,'ActualLoadQty',TO_TIMESTAMP('2023-01-26 10:42:05','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','Y','Tatsächlich verladene Menge',TO_TIMESTAMP('2023-01-26 10:42:05','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:05.571Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585640 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:05.716Z
/* DDL */  select update_Column_Translation_From_AD_Element(581690) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-26T08:42:06.101Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585641,581796,0,22,542287,'ActualDischargeQuantity',TO_TIMESTAMP('2023-01-26 10:42:06','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','Y','N','N','N','N','N','N','N','N','Y','Tatsächlich abgeladene Menge',TO_TIMESTAMP('2023-01-26 10:42:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:06.102Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585641 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:06.252Z
/* DDL */  select update_Column_Translation_From_AD_Element(581796) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.Created
-- 2023-01-26T08:42:06.667Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585642,245,0,16,542287,'Created',TO_TIMESTAMP('2023-01-26 10:42:06','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde','D',35,'Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt',TO_TIMESTAMP('2023-01-26 10:42:06','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:06.668Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585642 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:06.807Z
/* DDL */  select update_Column_Translation_From_AD_Element(245) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.CreatedBy
-- 2023-01-26T08:42:07.330Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585643,246,0,18,110,542287,'CreatedBy',TO_TIMESTAMP('2023-01-26 10:42:07','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag erstellt hat','D',10,'Das Feld Erstellt durch zeigt an, welcher Nutzer diesen Eintrag erstellt hat.','Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch',TO_TIMESTAMP('2023-01-26 10:42:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:07.332Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585643 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:07.470Z
/* DDL */  select update_Column_Translation_From_AD_Element(246) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.Updated
-- 2023-01-26T08:42:08.049Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585644,607,0,16,542287,'Updated',TO_TIMESTAMP('2023-01-26 10:42:07','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',35,'Aktualisiert zeigt an, wann dieser Eintrag aktualisiert wurde.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert',TO_TIMESTAMP('2023-01-26 10:42:07','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:08.050Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585644 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:08.193Z
/* DDL */  select update_Column_Translation_From_AD_Element(607) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.UpdatedBy
-- 2023-01-26T08:42:08.699Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585645,608,0,18,110,542287,'UpdatedBy',TO_TIMESTAMP('2023-01-26 10:42:08','YYYY-MM-DD HH24:MI:SS'),100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,'Aktualisiert durch zeigt an, welcher Nutzer diesen Eintrag aktualisiert hat.','Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch',TO_TIMESTAMP('2023-01-26 10:42:08','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:08.700Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585645 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:08.848Z
/* DDL */  select update_Column_Translation_From_AD_Element(608) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.IsActive
-- 2023-01-26T08:42:09.360Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585646,348,0,20,542287,'IsActive',TO_TIMESTAMP('2023-01-26 10:42:09','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','Y','N','N','N','N','N','N','N','N','Y','Aktiv',TO_TIMESTAMP('2023-01-26 10:42:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:09.361Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585646 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:09.503Z
/* DDL */  select update_Column_Translation_From_AD_Element(348) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Org_ID
-- 2023-01-26T08:42:09.998Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585647,113,0,30,542287,'AD_Org_ID',TO_TIMESTAMP('2023-01-26 10:42:09','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','Y','N','N','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-01-26 10:42:09','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:09.999Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585647 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:10.139Z
/* DDL */  select update_Column_Translation_From_AD_Element(113) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Client_ID
-- 2023-01-26T08:42:10.641Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585648,102,0,30,542287,'AD_Client_ID',TO_TIMESTAMP('2023-01-26 10:42:10','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','Y','N','N','N','N','N','N','N','N','N','Mandant',TO_TIMESTAMP('2023-01-26 10:42:10','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T08:42:10.642Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585648 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T08:42:10.783Z
/* DDL */  select update_Column_Translation_From_AD_Element(102) 
;

-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:42:21.209Z
UPDATE AD_Table SET IsView='Y',Updated=TO_TIMESTAMP('2023-01-26 10:42:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542287
;

-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:42:42.016Z
UPDATE AD_Table SET IsHighVolume='Y',Updated=TO_TIMESTAMP('2023-01-26 10:42:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Table_ID=542287
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Nr.
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocumentNo
-- 2023-01-26T08:42:57.003Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585627,710659,0,546754,TO_TIMESTAMP('2023-01-26 10:42:56','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document',30,'D','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-01-26 10:42:56','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.004Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710659 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.005Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(290) 
;

-- 2023-01-26T08:42:57.012Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710659
;

-- 2023-01-26T08:42:57.013Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710659)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Transport Auftrag
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_ShipperTransportation_ID
-- 2023-01-26T08:42:57.114Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585628,710660,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Transport Auftrag',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.115Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710660 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.116Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(540089) 
;

-- 2023-01-26T08:42:57.119Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710660
;

-- 2023-01-26T08:42:57.120Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710660)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Lieferplanung
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Delivery_Planning_ID
-- 2023-01-26T08:42:57.216Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585629,710661,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Lieferplanung',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.218Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710661 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.219Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581677) 
;

-- 2023-01-26T08:42:57.221Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710661
;

-- 2023-01-26T08:42:57.222Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710661)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Sektionskennung
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_SectionCode_ID
-- 2023-01-26T08:42:57.332Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585630,710662,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Sektionskennung',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.333Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710662 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.334Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581238) 
;

-- 2023-01-26T08:42:57.338Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710662
;

-- 2023-01-26T08:42:57.339Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710662)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Belegstatus
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocStatus
-- 2023-01-26T08:42:57.437Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585631,710663,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document',2,'D','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','N','N','N','N','N','N','Belegstatus',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.438Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710663 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.440Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(289) 
;

-- 2023-01-26T08:42:57.446Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710663
;

-- 2023-01-26T08:42:57.447Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710663)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Belegdatum
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DateDoc
-- 2023-01-26T08:42:57.545Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585632,710664,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Belegs',29,'D','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','N','N','N','N','N','N','Belegdatum',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.546Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710664 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.547Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(265) 
;

-- 2023-01-26T08:42:57.554Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710664
;

-- 2023-01-26T08:42:57.554Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710664)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Loading Date
-- Column: M_ShipperTransportation_Delivery_Instructions_V.LoadingDate
-- 2023-01-26T08:42:57.651Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585633,710665,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,29,'D','Y','N','N','N','N','N','N','N','Loading Date',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.652Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710665 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.653Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581900) 
;

-- 2023-01-26T08:42:57.656Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710665
;

-- 2023-01-26T08:42:57.657Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710665)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Lieferdatum
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DeliveryDate
-- 2023-01-26T08:42:57.754Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585634,710666,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,29,'D','Y','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.755Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710666 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.756Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(541376) 
;

-- 2023-01-26T08:42:57.759Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710666
;

-- 2023-01-26T08:42:57.760Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710666)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Incoterms
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_Incoterms_ID
-- 2023-01-26T08:42:57.882Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585635,710667,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Incoterms',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.883Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710667 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.884Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(579927) 
;

-- 2023-01-26T08:42:57.888Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710667
;

-- 2023-01-26T08:42:57.888Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710667)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> IncotermLocation
-- Column: M_ShipperTransportation_Delivery_Instructions_V.IncotermLocation
-- 2023-01-26T08:42:57.990Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585636,710668,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,'Anzugebender Ort für Handelsklausel',500,'D','Y','N','N','N','N','N','N','N','IncotermLocation',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:57.991Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710668 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:57.992Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(501608) 
;

-- 2023-01-26T08:42:57.995Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710668
;

-- 2023-01-26T08:42:57.995Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710668)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Means of Transportation
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_MeansOfTransportation_ID
-- 2023-01-26T08:42:58.100Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585637,710669,0,546754,TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Means of Transportation',TO_TIMESTAMP('2023-01-26 10:42:57','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.101Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710669 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.102Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581776) 
;

-- 2023-01-26T08:42:58.105Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710669
;

-- 2023-01-26T08:42:58.106Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710669)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Produkt
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Product_ID
-- 2023-01-26T08:42:58.203Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585638,710670,0,546754,TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','Produkt',TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.204Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710670 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.205Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(454) 
;

-- 2023-01-26T08:42:58.235Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710670
;

-- 2023-01-26T08:42:58.236Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710670)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Lagerort
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Locator_ID
-- 2023-01-26T08:42:58.352Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585639,710671,0,546754,TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager',10,'D','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','N','N','N','N','N','N','Lagerort',TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.353Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710671 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.354Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(448) 
;

-- 2023-01-26T08:42:58.361Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710671
;

-- 2023-01-26T08:42:58.362Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710671)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Tatsächlich verladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-26T08:42:58.447Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585640,710672,0,546754,TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Tatsächlich verladene Menge',TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.448Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710672 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.449Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581690) 
;

-- 2023-01-26T08:42:58.452Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710672
;

-- 2023-01-26T08:42:58.452Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710672)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Tatsächlich abgeladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-26T08:42:58.554Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585641,710673,0,546754,TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100,14,'D','Y','N','N','N','N','N','N','N','Tatsächlich abgeladene Menge',TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.556Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710673 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.557Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581796) 
;

-- 2023-01-26T08:42:58.559Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710673
;

-- 2023-01-26T08:42:58.559Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710673)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Aktiv
-- Column: M_ShipperTransportation_Delivery_Instructions_V.IsActive
-- 2023-01-26T08:42:58.665Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585646,710674,0,546754,TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','N','N','N','N','N','N','Aktiv',TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.666Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710674 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.668Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(348) 
;

-- 2023-01-26T08:42:58.875Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710674
;

-- 2023-01-26T08:42:58.877Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710674)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Sektion
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Org_ID
-- 2023-01-26T08:42:58.988Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585647,710675,0,546754,TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','Sektion',TO_TIMESTAMP('2023-01-26 10:42:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:58.989Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710675 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:58.990Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(113) 
;

-- 2023-01-26T08:42:59.145Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710675
;

-- 2023-01-26T08:42:59.146Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710675)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Mandant
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Client_ID
-- 2023-01-26T08:42:59.275Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585648,710676,0,546754,TO_TIMESTAMP('2023-01-26 10:42:59','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2023-01-26 10:42:59','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T08:42:59.276Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710676 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T08:42:59.277Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(102) 
;

-- 2023-01-26T08:42:59.437Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710676
;

-- 2023-01-26T08:42:59.439Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710676)
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:44:49.270Z
UPDATE AD_Tab SET TabLevel=1, WhereClause='m_delivery_planning_id=@M_Delivery_Planning_ID@ AND ',Updated=TO_TIMESTAMP('2023-01-26 10:44:49','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:46:02.028Z
UPDATE AD_Tab SET IsInsertRecord='N', IsSingleRow='Y',Updated=TO_TIMESTAMP('2023-01-26 10:46:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:46:08.508Z
UPDATE AD_Tab SET WhereClause='m_delivery_planning_id=@M_Delivery_Planning_ID@',Updated=TO_TIMESTAMP('2023-01-26 10:46:08','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:46:16.956Z
UPDATE AD_Tab SET IsRefreshViewOnChangeEvents='Y',Updated=TO_TIMESTAMP('2023-01-26 10:46:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T08:49:23.140Z
UPDATE AD_Tab SET Parent_Column_ID=585609,Updated=TO_TIMESTAMP('2023-01-26 10:49:23','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D)
-- UI Section: History
-- 2023-01-26T09:32:45.390Z
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,546754,545387,TO_TIMESTAMP('2023-01-26 11:32:45','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-26 11:32:45','YYYY-MM-DD HH24:MI:SS'),100,'History')
;

-- 2023-01-26T09:32:45.393Z
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=545387 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Nr.
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocumentNo
-- 2023-01-26T09:33:07.612Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710659
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Transport Auftrag
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_ShipperTransportation_ID
-- 2023-01-26T09:33:07.616Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710660
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Lieferplanung
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Delivery_Planning_ID
-- 2023-01-26T09:33:07.621Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710661
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Sektionskennung
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_SectionCode_ID
-- 2023-01-26T09:33:07.625Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710662
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Belegstatus
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocStatus
-- 2023-01-26T09:33:07.628Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710663
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Belegdatum
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DateDoc
-- 2023-01-26T09:33:07.631Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710664
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Loading Date
-- Column: M_ShipperTransportation_Delivery_Instructions_V.LoadingDate
-- 2023-01-26T09:33:07.634Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710665
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Lieferdatum
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DeliveryDate
-- 2023-01-26T09:33:07.639Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710666
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Incoterms
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_Incoterms_ID
-- 2023-01-26T09:33:07.642Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710667
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> IncotermLocation
-- Column: M_ShipperTransportation_Delivery_Instructions_V.IncotermLocation
-- 2023-01-26T09:33:07.646Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710668
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Means of Transportation
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_MeansOfTransportation_ID
-- 2023-01-26T09:33:07.649Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710669
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Produkt
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Product_ID
-- 2023-01-26T09:33:07.652Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710670
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Lagerort
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Locator_ID
-- 2023-01-26T09:33:07.655Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710671
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Tatsächlich verladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-26T09:33:07.658Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710672
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Tatsächlich abgeladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-26T09:33:07.661Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710673
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Aktiv
-- Column: M_ShipperTransportation_Delivery_Instructions_V.IsActive
-- 2023-01-26T09:33:07.665Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710674
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Sektion
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Org_ID
-- 2023-01-26T09:33:07.668Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710675
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Mandant
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Client_ID
-- 2023-01-26T09:33:07.671Z
UPDATE AD_Field SET IsDisplayedGrid='N', SeqNoGrid=0,Updated=TO_TIMESTAMP('2023-01-26 11:33:07','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710676
;

-- UI Section: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History
-- UI Column: 10
-- 2023-01-26T09:33:23.801Z
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,546571,545387,TO_TIMESTAMP('2023-01-26 11:33:23','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2023-01-26 11:33:23','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Column: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10
-- UI Element Group: main
-- 2023-01-26T09:33:58.305Z
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy) VALUES (0,0,546571,550257,TO_TIMESTAMP('2023-01-26 11:33:58','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,TO_TIMESTAMP('2023-01-26 11:33:58','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Nr.
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocumentNo
-- 2023-01-26T09:34:34.911Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710659,0,546754,550257,614861,'F',TO_TIMESTAMP('2023-01-26 11:34:34','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','N','Y','N','N','Nr.',10,0,0,TO_TIMESTAMP('2023-01-26 11:34:34','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Belegdatum
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DateDoc
-- 2023-01-26T09:34:55.035Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710664,0,546754,550257,614862,'F',TO_TIMESTAMP('2023-01-26 11:34:54','YYYY-MM-DD HH24:MI:SS'),100,'Datum des Belegs','The Document Date indicates the date the document was generated.  It may or may not be the same as the accounting date.','Y','N','Y','N','N','Belegdatum',20,0,0,TO_TIMESTAMP('2023-01-26 11:34:54','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Belegstatus
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DocStatus
-- 2023-01-26T09:35:08.246Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710663,0,546754,550257,614863,'F',TO_TIMESTAMP('2023-01-26 11:35:08','YYYY-MM-DD HH24:MI:SS'),100,'The current status of the document','The Document Status indicates the status of a document at this time.  If you want to change the document status, use the Document Action field','Y','N','Y','N','N','Belegstatus',30,0,0,TO_TIMESTAMP('2023-01-26 11:35:08','YYYY-MM-DD HH24:MI:SS'),100)
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_BPartner_Location_Loading_ID
-- 2023-01-26T09:37:29.799Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585649,581899,0,18,159,542287,'C_BPartner_Location_Loading_ID',TO_TIMESTAMP('2023-01-26 11:37:29','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Loading Address',0,0,TO_TIMESTAMP('2023-01-26 11:37:29','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T09:37:29.801Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585649 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T09:37:29.803Z
/* DDL */  select update_Column_Translation_From_AD_Element(581899) 
;

-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_BPartner_Location_Delivery_ID
-- 2023-01-26T09:37:48.637Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,DDL_NoForeignKey,EntityType,FacetFilterSeqNo,FieldLength,IsActive,IsAdvancedText,IsAllowLogging,IsAlwaysUpdateable,IsAutoApplyValidationRule,IsAutocomplete,IsCalculated,IsDimension,IsDLMPartitionBoundary,IsEncrypted,IsExcludeFromZoomTargets,IsFacetFilter,IsForceIncludeInGeneratedModel,IsGenericZoomKeyColumn,IsGenericZoomOrigin,IsIdentifier,IsKey,IsLazyLoading,IsMandatory,IsParent,IsRestAPICustomColumn,IsSelectionColumn,IsShowFilterIncrementButtons,IsShowFilterInline,IsStaleable,IsSyncDatabase,IsTranslated,IsUpdateable,IsUseDocSequence,MaxFacetsToFetch,Name,SelectionColumnSeqNo,SeqNo,Updated,UpdatedBy,Version) VALUES (0,585650,581901,0,18,159,542287,'C_BPartner_Location_Delivery_ID',TO_TIMESTAMP('2023-01-26 11:37:48','YYYY-MM-DD HH24:MI:SS'),100,'N','D',0,10,'Y','N','Y','N','N','N','N','N','N','N','Y','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N','N',0,'Delivery Address',0,0,TO_TIMESTAMP('2023-01-26 11:37:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-01-26T09:37:48.638Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585650 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-01-26T09:37:48.641Z
/* DDL */  select update_Column_Translation_From_AD_Element(581901) 
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Loading Address
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_BPartner_Location_Loading_ID
-- 2023-01-26T09:38:10.363Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585649,710677,0,546754,TO_TIMESTAMP('2023-01-26 11:38:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Loading Address',TO_TIMESTAMP('2023-01-26 11:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T09:38:10.364Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710677 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T09:38:10.365Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581899) 
;

-- 2023-01-26T09:38:10.368Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710677
;

-- 2023-01-26T09:38:10.369Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710677)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Delivery Address
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_BPartner_Location_Delivery_ID
-- 2023-01-26T09:38:10.462Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy) VALUES (0,585650,710678,0,546754,TO_TIMESTAMP('2023-01-26 11:38:10','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','N','N','Delivery Address',TO_TIMESTAMP('2023-01-26 11:38:10','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T09:38:10.463Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710678 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T09:38:10.464Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(581901) 
;

-- 2023-01-26T09:38:10.467Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710678
;

-- 2023-01-26T09:38:10.467Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710678)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Loading Address
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_BPartner_Location_Loading_ID
-- 2023-01-26T09:38:28.311Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710677,0,546754,550257,614864,'F',TO_TIMESTAMP('2023-01-26 11:38:28','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Loading Address',40,0,0,TO_TIMESTAMP('2023-01-26 11:38:28','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Loading Date
-- Column: M_ShipperTransportation_Delivery_Instructions_V.LoadingDate
-- 2023-01-26T09:38:51.536Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710665,0,546754,550257,614865,'F',TO_TIMESTAMP('2023-01-26 11:38:51','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Loading Date',50,0,0,TO_TIMESTAMP('2023-01-26 11:38:51','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Delivery Address
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_BPartner_Location_Delivery_ID
-- 2023-01-26T09:39:31.343Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710678,0,546754,550257,614866,'F',TO_TIMESTAMP('2023-01-26 11:39:31','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Delivery Address',60,0,0,TO_TIMESTAMP('2023-01-26 11:39:31','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Lieferdatum
-- Column: M_ShipperTransportation_Delivery_Instructions_V.DeliveryDate
-- 2023-01-26T09:39:38.421Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710666,0,546754,550257,614867,'F',TO_TIMESTAMP('2023-01-26 11:39:38','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Lieferdatum',70,0,0,TO_TIMESTAMP('2023-01-26 11:39:38','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Produkt
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Product_ID
-- 2023-01-26T09:39:52.618Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710670,0,546754,550257,614868,'F',TO_TIMESTAMP('2023-01-26 11:39:52','YYYY-MM-DD HH24:MI:SS'),100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','Y','N','N','Produkt',80,0,0,TO_TIMESTAMP('2023-01-26 11:39:52','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Tatsächlich verladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualLoadQty
-- 2023-01-26T09:40:15.495Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710672,0,546754,550257,614869,'F',TO_TIMESTAMP('2023-01-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tatsächlich verladene Menge',90,0,0,TO_TIMESTAMP('2023-01-26 11:40:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Tatsächlich abgeladene Menge
-- Column: M_ShipperTransportation_Delivery_Instructions_V.ActualDischargeQuantity
-- 2023-01-26T09:40:25.128Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710673,0,546754,550257,614870,'F',TO_TIMESTAMP('2023-01-26 11:40:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Tatsächlich abgeladene Menge',100,0,0,TO_TIMESTAMP('2023-01-26 11:40:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Lagerort
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_Locator_ID
-- 2023-01-26T09:41:01.643Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710671,0,546754,550257,614871,'F',TO_TIMESTAMP('2023-01-26 11:41:01','YYYY-MM-DD HH24:MI:SS'),100,'Lagerort im Lager','"Lagerort" bezeichnet, wo im Lager ein Produkt aufzufinden ist.','Y','N','Y','N','N','Lagerort',110,0,0,TO_TIMESTAMP('2023-01-26 11:41:01','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Means of Transportation
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_MeansOfTransportation_ID
-- 2023-01-26T09:41:07.363Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710669,0,546754,550257,614872,'F',TO_TIMESTAMP('2023-01-26 11:41:07','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Means of Transportation',120,0,0,TO_TIMESTAMP('2023-01-26 11:41:07','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Incoterms
-- Column: M_ShipperTransportation_Delivery_Instructions_V.C_Incoterms_ID
-- 2023-01-26T09:41:24.704Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710667,0,546754,550257,614873,'F',TO_TIMESTAMP('2023-01-26 11:41:24','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Incoterms',130,0,0,TO_TIMESTAMP('2023-01-26 11:41:24','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Sektionskennung
-- Column: M_ShipperTransportation_Delivery_Instructions_V.M_SectionCode_ID
-- 2023-01-26T09:41:55.815Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710662,0,546754,550257,614874,'F',TO_TIMESTAMP('2023-01-26 11:41:55','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','N','N','Sektionskennung',140,0,0,TO_TIMESTAMP('2023-01-26 11:41:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Aktiv
-- Column: M_ShipperTransportation_Delivery_Instructions_V.IsActive
-- 2023-01-26T09:42:05.166Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710674,0,546754,550257,614875,'F',TO_TIMESTAMP('2023-01-26 11:42:05','YYYY-MM-DD HH24:MI:SS'),100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren. Ein deaktivierter Eintrag ist nicht mehr für eine Auswahl verfügbar, aber verfügbar für die Verwendung in Berichten. Es gibt zwei Gründe, Datensätze zu deaktivieren und nicht zu löschen: (1) Das System braucht den Datensatz für Revisionszwecke. (2) Der Datensatz wird von anderen Datensätzen referenziert. Z.B. können Sie keinen Geschäftspartner löschen, wenn es Rechnungen für diesen Geschäftspartner gibt. Sie deaktivieren den Geschäftspartner und verhindern, dass dieser Eintrag in zukünftigen Vorgängen verwendet wird.','Y','N','Y','N','N','Aktiv',150,0,0,TO_TIMESTAMP('2023-01-26 11:42:05','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Sektion
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Org_ID
-- 2023-01-26T09:42:15.738Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710675,0,546754,550257,614876,'F',TO_TIMESTAMP('2023-01-26 11:42:15','YYYY-MM-DD HH24:MI:SS'),100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','Y','N','N','Sektion',160,0,0,TO_TIMESTAMP('2023-01-26 11:42:15','YYYY-MM-DD HH24:MI:SS'),100)
;

-- UI Element: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> History -> 10 -> main.Mandant
-- Column: M_ShipperTransportation_Delivery_Instructions_V.AD_Client_ID
-- 2023-01-26T09:42:21.586Z
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,710676,0,546754,550257,614877,'F',TO_TIMESTAMP('2023-01-26 11:42:21','YYYY-MM-DD HH24:MI:SS'),100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','Y','N','N','Mandant',170,0,0,TO_TIMESTAMP('2023-01-26 11:42:21','YYYY-MM-DD HH24:MI:SS'),100)
;


----------------



-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T10:37:48.535Z
UPDATE AD_Tab SET AD_Column_ID=585629, Parent_Column_ID=NULL,Updated=TO_TIMESTAMP('2023-01-26 12:37:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:08:30.290Z
UPDATE AD_Tab SET WhereClause='m_delivery_planning_id=@M_Delivery_Planning_ID@ AND @Created@>created',Updated=TO_TIMESTAMP('2023-01-26 13:08:30','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Erstellt
-- Column: M_ShipperTransportation_Delivery_Instructions_V.Created
-- 2023-01-26T11:10:21.865Z
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,ColumnDisplayLength,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IncludedTabHeight,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,SpanX,SpanY,Updated,UpdatedBy) VALUES (0,585642,710679,0,546754,0,TO_TIMESTAMP('2023-01-26 13:10:21','YYYY-MM-DD HH24:MI:SS'),100,'Datum, an dem dieser Eintrag erstellt wurde',0,'U','Das Feld Erstellt zeigt an, zu welchem Datum dieser Eintrag erstellt wurde.',0,'Y','Y','Y','N','N','N','N','N','Erstellt',0,10,0,1,1,TO_TIMESTAMP('2023-01-26 13:10:21','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-01-26T11:10:21.866Z
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=710679 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- 2023-01-26T11:10:21.890Z
/* DDL */  select update_FieldTranslation_From_AD_Name_Element(245) 
;

-- 2023-01-26T11:10:21.951Z
DELETE FROM AD_Element_Link WHERE AD_Field_ID=710679
;

-- 2023-01-26T11:10:21.952Z
/* DDL */ select AD_Element_Link_Create_Missing_Field(710679)
;

-- Field: Delivery Instruction(541657,D) -> Delivery Instruction History(546754,D) -> Erstellt
-- Column: M_ShipperTransportation_Delivery_Instructions_V.Created
-- 2023-01-26T11:10:25.164Z
UPDATE AD_Field SET EntityType='D',Updated=TO_TIMESTAMP('2023-01-26 13:10:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Field_ID=710679
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:10:45.771Z
UPDATE AD_Tab SET WhereClause='m_delivery_planning_id=@M_Delivery_Planning_ID@ AND Created<@Created@',Updated=TO_TIMESTAMP('2023-01-26 13:10:45','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:12:38.235Z
UPDATE AD_Tab SET WhereClause='m_delivery_planning_id=@M_Delivery_Planning_ID@',Updated=TO_TIMESTAMP('2023-01-26 13:12:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:14:27.234Z
UPDATE AD_Tab SET WhereClause='m_delivery_planning_id=@M_Delivery_Planning_ID@ AND Created<@Created@',Updated=TO_TIMESTAMP('2023-01-26 13:14:27','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:14:39.603Z
UPDATE AD_Tab SET Parent_Column_ID=585609,Updated=TO_TIMESTAMP('2023-01-26 13:14:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:15:56.874Z
UPDATE AD_Tab SET WhereClause='M_ShipperTransportation_Delivery_Instructions_V.m_delivery_planning_id=@M_Delivery_Planning_ID@ AND M_ShipperTransportation_Delivery_Instructions_V.Created<@Created@',Updated=TO_TIMESTAMP('2023-01-26 13:15:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

-- Tab: Delivery Instruction(541657,D) -> Delivery Instruction History
-- Table: M_ShipperTransportation_Delivery_Instructions_V
-- 2023-01-26T11:23:06.595Z
UPDATE AD_Tab SET WhereClause='M_ShipperTransportation_Delivery_Instructions_V.m_delivery_planning_id=@M_Delivery_Planning_ID@ AND M_ShipperTransportation_Delivery_Instructions_V.Created<''@Created@''',Updated=TO_TIMESTAMP('2023-01-26 13:23:06','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Tab_ID=546754
;

