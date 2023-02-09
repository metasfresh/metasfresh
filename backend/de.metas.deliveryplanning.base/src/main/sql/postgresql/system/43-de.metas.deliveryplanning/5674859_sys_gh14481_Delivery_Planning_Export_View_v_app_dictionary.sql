-- 2023-02-06T13:41:56.102Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582028,0,'Delivery_Planning_Export_View_v',TO_TIMESTAMP('2023-02-06 15:41:55','YYYY-MM-DD HH24:MI:SS'),100,'D','Y','Delivery Planning Export View','Delivery Planning Export View',TO_TIMESTAMP('2023-02-06 15:41:55','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T13:41:56.109Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582028 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Table: Delivery_Planning_Export_View_v
-- 2023-02-06T13:43:31.973Z
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,CopyColumnsFromTable,Created,CreatedBy,Description,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542295,'N',TO_TIMESTAMP('2023-02-06 15:43:31','YYYY-MM-DD HH24:MI:SS'),100,'View used to export delivery planning relevant columns','D','N','Y','N','N','N','N','N','N','N','Y',0,'Delivery Planning Export View','NP','L','Delivery_Planning_Export_View_v','DTI',TO_TIMESTAMP('2023-02-06 15:43:31','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:43:31.975Z
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542295 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- 2023-02-06T13:47:44.993Z
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,582029,0,'Delivery_Planning_Export_View_v_ID',TO_TIMESTAMP('2023-02-06 15:47:44','YYYY-MM-DD HH24:MI:SS'),100,'U','Y','Delivery Planning Export View','Delivery Planning Export View',TO_TIMESTAMP('2023-02-06 15:47:44','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2023-02-06T13:47:44.995Z
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=582029 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

-- Column: Delivery_Planning_Export_View_v.Delivery_Planning_Export_View_v_ID
-- 2023-02-06T13:47:45.416Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585873,582029,0,13,542295,'Delivery_Planning_Export_View_v_ID',TO_TIMESTAMP('2023-02-06 15:47:44','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','Y','N','N','N','N','N','Delivery Planning Export View',TO_TIMESTAMP('2023-02-06 15:47:44','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:45.418Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585873 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:45.447Z
/* DDL */  select update_Column_Translation_From_AD_Element(582029) 
;

-- Column: Delivery_Planning_Export_View_v.M_Delivery_Planning_ID
-- 2023-02-06T13:47:45.999Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585874,581677,0,30,542295,'M_Delivery_Planning_ID',TO_TIMESTAMP('2023-02-06 15:47:45','YYYY-MM-DD HH24:MI:SS'),100,'U',10,'Y','Y','N','N','N','N','N','N','N','N','N','Lieferplanung',TO_TIMESTAMP('2023-02-06 15:47:45','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:46.001Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585874 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:46.004Z
/* DDL */  select update_Column_Translation_From_AD_Element(581677) 
;

-- Column: Delivery_Planning_Export_View_v.DocumentNo
-- 2023-02-06T13:47:46.417Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585875,290,0,10,542295,'DocumentNo',TO_TIMESTAMP('2023-02-06 15:47:46','YYYY-MM-DD HH24:MI:SS'),100,'Document sequence number of the document','U',30,'The document number is usually automatically generated by the system and determined by the document type of the document. If the document is not saved, the preliminary number is displayed in "<>".

If the document type of your document has no automatic document sequence defined, the field is empty if you create a new document. This is for documents which usually have an external number (like vendor invoice).  If you leave the field empty, the system will generate a document number for you. The document sequence used for this fallback number is defined in the "Maintain Sequence" window with the name "DocumentNo_<TableName>", where TableName is the actual name of the table (e.g. C_Order).','Y','Y','N','N','N','N','N','N','N','N','N','Nr.',TO_TIMESTAMP('2023-02-06 15:47:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:46.420Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585875 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:46.536Z
/* DDL */  select update_Column_Translation_From_AD_Element(290) 
;

-- Column: Delivery_Planning_Export_View_v.ShipToLocation_Name
-- 2023-02-06T13:47:46.974Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585876,581681,0,14,542295,'ShipToLocation_Name',TO_TIMESTAMP('2023-02-06 15:47:46','YYYY-MM-DD HH24:MI:SS'),100,'U',2147483647,'Y','Y','N','N','N','N','N','N','N','N','N','Lieferadresse',TO_TIMESTAMP('2023-02-06 15:47:46','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:46.975Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585876 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:47.110Z
/* DDL */  select update_Column_Translation_From_AD_Element(581681) 
;

-- Column: Delivery_Planning_Export_View_v.ProductName
-- 2023-02-06T13:47:47.654Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585877,2659,0,14,542295,'ProductName',TO_TIMESTAMP('2023-02-06 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,'Name des Produktes','U',600,'Y','Y','N','N','N','N','N','N','N','N','N','Produktname',TO_TIMESTAMP('2023-02-06 15:47:47','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:47.655Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585877 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:47.764Z
/* DDL */  select update_Column_Translation_From_AD_Element(2659) 
;

-- Column: Delivery_Planning_Export_View_v.ProductCode
-- 2023-02-06T13:47:48.143Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585878,576649,0,10,542295,'ProductCode',TO_TIMESTAMP('2023-02-06 15:47:48','YYYY-MM-DD HH24:MI:SS'),100,'U',40,'Y','Y','N','N','N','N','N','N','N','N','N','Produktcode',TO_TIMESTAMP('2023-02-06 15:47:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:48.145Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585878 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:48.256Z
/* DDL */  select update_Column_Translation_From_AD_Element(576649) 
;

-- Column: Delivery_Planning_Export_View_v.WarehouseName
-- 2023-02-06T13:47:48.648Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585879,2280,0,10,542295,'WarehouseName',TO_TIMESTAMP('2023-02-06 15:47:48','YYYY-MM-DD HH24:MI:SS'),100,'Lagerbezeichnung','U',60,'Y','Y','N','N','N','N','N','N','N','N','N','Lager',TO_TIMESTAMP('2023-02-06 15:47:48','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:48.650Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585879 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:48.765Z
/* DDL */  select update_Column_Translation_From_AD_Element(2280) 
;

-- Column: Delivery_Planning_Export_View_v.OriginCountry
-- 2023-02-06T13:47:49.141Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585880,581694,0,10,542295,'OriginCountry',TO_TIMESTAMP('2023-02-06 15:47:49','YYYY-MM-DD HH24:MI:SS'),100,'U',60,'Y','Y','N','N','N','N','N','N','N','N','N','Ursprungsland',TO_TIMESTAMP('2023-02-06 15:47:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:49.142Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585880 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:49.254Z
/* DDL */  select update_Column_Translation_From_AD_Element(581694) 
;

-- Column: Delivery_Planning_Export_View_v.DeliveryDate
-- 2023-02-06T13:47:49.621Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585881,541376,0,16,542295,'DeliveryDate',TO_TIMESTAMP('2023-02-06 15:47:49','YYYY-MM-DD HH24:MI:SS'),100,'U',29,'Y','Y','N','N','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2023-02-06 15:47:49','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:49.623Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585881 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:49.743Z
/* DDL */  select update_Column_Translation_From_AD_Element(541376) 
;

-- Column: Delivery_Planning_Export_View_v.Batch
-- 2023-02-06T13:47:50.099Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585882,581692,0,10,542295,'Batch',TO_TIMESTAMP('2023-02-06 15:47:50','YYYY-MM-DD HH24:MI:SS'),100,'U',250,'Y','Y','N','N','N','N','N','N','N','N','N','Stapel Nr.',TO_TIMESTAMP('2023-02-06 15:47:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:50.101Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585882 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:50.223Z
/* DDL */  select update_Column_Translation_From_AD_Element(581692) 
;

-- Column: Delivery_Planning_Export_View_v.ReleaseNo
-- 2023-02-06T13:47:50.595Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585883,2122,0,10,542295,'ReleaseNo',TO_TIMESTAMP('2023-02-06 15:47:50','YYYY-MM-DD HH24:MI:SS'),100,'Internal Release Number','U',250,'Y','Y','N','N','N','N','N','N','N','N','N','Ausgabenummer',TO_TIMESTAMP('2023-02-06 15:47:50','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:50.596Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585883 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:50.707Z
/* DDL */  select update_Column_Translation_From_AD_Element(2122) 
;

-- Column: Delivery_Planning_Export_View_v.PlannedLoadingDate
-- 2023-02-06T13:47:51.084Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585884,581688,0,16,542295,'PlannedLoadingDate',TO_TIMESTAMP('2023-02-06 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,'U',29,'Y','Y','N','N','N','N','N','N','N','N','N','Voraussichtliches Verladedatum',TO_TIMESTAMP('2023-02-06 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:51.085Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585884 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:51.212Z
/* DDL */  select update_Column_Translation_From_AD_Element(581688) 
;

-- Column: Delivery_Planning_Export_View_v.ActualLoadingDate
-- 2023-02-06T13:47:51.584Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585885,581689,0,16,542295,'ActualLoadingDate',TO_TIMESTAMP('2023-02-06 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,'U',29,'Y','Y','N','N','N','N','N','N','N','N','N','Verladedatum',TO_TIMESTAMP('2023-02-06 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:51.585Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585885 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:51.710Z
/* DDL */  select update_Column_Translation_From_AD_Element(581689) 
;

-- Column: Delivery_Planning_Export_View_v.PlannedLoadedQuantity
-- 2023-02-06T13:47:52.082Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585886,581794,0,22,542295,'PlannedLoadedQuantity',TO_TIMESTAMP('2023-02-06 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,'U',14,'Y','Y','N','N','N','N','N','N','N','N','N','Geplante Verlademenge',TO_TIMESTAMP('2023-02-06 15:47:51','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:52.084Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585886 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:52.213Z
/* DDL */  select update_Column_Translation_From_AD_Element(581794) 
;

-- Column: Delivery_Planning_Export_View_v.ActualLoadQty
-- 2023-02-06T13:47:52.592Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585887,581690,0,29,542295,'ActualLoadQty',TO_TIMESTAMP('2023-02-06 15:47:52','YYYY-MM-DD HH24:MI:SS'),100,'U',14,'Y','Y','N','N','N','N','N','N','N','N','N','Tatsächlich verladene Menge',TO_TIMESTAMP('2023-02-06 15:47:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:52.594Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585887 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:52.708Z
/* DDL */  select update_Column_Translation_From_AD_Element(581690) 
;

-- Column: Delivery_Planning_Export_View_v.PlannedDeliveryDate
-- 2023-02-06T13:47:53.109Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585888,581686,0,16,542295,'PlannedDeliveryDate',TO_TIMESTAMP('2023-02-06 15:47:52','YYYY-MM-DD HH24:MI:SS'),100,'U',29,'Y','Y','N','N','N','N','N','N','N','N','N','Voraussichtliches Lieferdatum',TO_TIMESTAMP('2023-02-06 15:47:52','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:53.110Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585888 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:53.224Z
/* DDL */  select update_Column_Translation_From_AD_Element(581686) 
;

-- Column: Delivery_Planning_Export_View_v.ActualDeliveryDate
-- 2023-02-06T13:47:53.596Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585889,581687,0,16,542295,'ActualDeliveryDate',TO_TIMESTAMP('2023-02-06 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,'U',29,'Y','Y','N','N','N','N','N','N','N','N','N','Lieferdatum',TO_TIMESTAMP('2023-02-06 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:53.598Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585889 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:53.709Z
/* DDL */  select update_Column_Translation_From_AD_Element(581687) 
;

-- Column: Delivery_Planning_Export_View_v.PlannedDischargeQuantity
-- 2023-02-06T13:47:54.077Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585890,581795,0,22,542295,'PlannedDischargeQuantity',TO_TIMESTAMP('2023-02-06 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,'U',14,'Y','Y','N','N','N','N','N','N','N','N','N','Geplante Entlademenge',TO_TIMESTAMP('2023-02-06 15:47:53','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:54.078Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585890 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:54.198Z
/* DDL */  select update_Column_Translation_From_AD_Element(581795) 
;

-- Column: Delivery_Planning_Export_View_v.ActualDischargeQuantity
-- 2023-02-06T13:47:54.568Z
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,Updated,UpdatedBy,Version) VALUES (0,585891,581796,0,22,542295,'ActualDischargeQuantity',TO_TIMESTAMP('2023-02-06 15:47:54','YYYY-MM-DD HH24:MI:SS'),100,'U',14,'Y','Y','N','N','N','N','N','N','N','N','N','Tatsächlich abgeladene Menge',TO_TIMESTAMP('2023-02-06 15:47:54','YYYY-MM-DD HH24:MI:SS'),100,0)
;

-- 2023-02-06T13:47:54.570Z
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=585891 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- 2023-02-06T13:47:54.679Z
/* DDL */  select update_Column_Translation_From_AD_Element(581796) 
;
