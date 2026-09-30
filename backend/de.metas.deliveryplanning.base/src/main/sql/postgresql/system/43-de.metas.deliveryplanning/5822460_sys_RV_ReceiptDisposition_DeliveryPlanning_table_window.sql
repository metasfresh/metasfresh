-- Wareneingangslogistik: the AD table, window, tab, fields and menu entry over RV_ReceiptDisposition_DeliveryPlanning.
--
-- The view (5822440) is the dispatcher's single inbound list; this script is what makes it openable.
-- Modelled on the Purchase Cockpit window (a read-only AD_Table over a view: IsView='Y', a synthetic
-- *_ID key column, IsInsertRecord='N', IsGridModeOnly='Y', IsRefreshViewOnChangeEvents='Y',
-- IsGenericZoomTarget='Y'), with one deliberate departure: the Purchase Cockpit configures ZERO
-- filter columns, which would leave a window whose whole job is "what arrives this week" unfilterable.
--
-- FILTERS. The mechanism is AD_Column.IsSelectionColumn + SelectionColumnSeqNo (not the AD_Field
-- facet-filter flags, which almost nothing in this tree uses). The arrangement mirrors the delivery
-- planning window family by family:
--   seq 0   -- filterable but not in the default panel: the four transport dates, DatePromised_Effective
--              and the business partner. That is what seq 0 means on M_Delivery_Planning too (its ETA,
--              ETD, ATA, ATD and C_BPartner_ID all sit at 0), not a broken filter.
--   seq 50  -- M_Warehouse_ID, 60 M_Product_ID: the planning's own numbers for those two.
--   seq 80  -- C_Order_ID: the receipt schedule's number. This window follows the SCHEDULE on the
--              order, not the planning: the schedule exposes C_Order_ID itself (an order picker),
--              while the planning only offers OrderDocumentNo as free text.
--   seq 90  -- POReference. The schedule numbers it 50, which collides with the planning's warehouse
--              at 50; the two source tables number independently and this window needs ONE ordering,
--              so the order pair is kept adjacent (80, 90) instead.
--   seq 180 -- ContainerNo: the schedule's own number, free here.
--   seq 200 -- AD_Org_ID: the planning's own number.
--
-- RANGE FILTERS. Every date this view exposes carries IsRangeFilter='Y'. IsRangeFilter is 'N' on every
-- date in this family today, so a date filter is a single value rather than a from-to -- useless for a
-- window read as "what arrives between Monday and Friday".
--
-- LABELS reuse the delivery planning's and the receipt schedule's existing AD_Elements throughout; the
-- only new ones are the two this window needs for itself (the record label and the window/menu caption).

-- Table: RV_ReceiptDisposition_DeliveryPlanning
INSERT INTO AD_Table (AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,AccessLevel,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy)
VALUES (0,0,542644 /*From ID Server*/,542190 /*From ID Server*/,'3',TO_TIMESTAMP('2026-09-03 08:00:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','Y','N','N','N','N','N','N','Y',0,'Receipt Logistics','NP','L','RV_ReceiptDisposition_DeliveryPlanning','DTI',TO_TIMESTAMP('2026-09-03 08:00:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542644 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Element: the record label, carried by the key column and the tab
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585423 /*From ID Server*/,0,'RV_ReceiptDisposition_DeliveryPlanning_ID',TO_TIMESTAMP('2026-09-03 08:00:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Wareneingangslogistik','Wareneingangslogistik',TO_TIMESTAMP('2026-09-03 08:00:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585423 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Receipt Logistics', PrintName='Receipt Logistics', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 08:00:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585423 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 08:00:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585423 AND AD_Language IN ('de_DE','de_CH')
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585423,'en_US')
;

-- Element: the window and menu caption
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,Created,CreatedBy,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy)
VALUES (0,585424 /*From ID Server*/,0,TO_TIMESTAMP('2026-09-03 08:00:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','Wareneingangslogistik','Wareneingangslogistik',TO_TIMESTAMP('2026-09-03 08:00:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585424 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;

UPDATE AD_Element_Trl SET Name='Receipt Logistics', PrintName='Receipt Logistics', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 08:00:09','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585424 AND AD_Language='en_US'
;

UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-09-03 08:00:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy=100 WHERE AD_Element_ID=585424 AND AD_Language IN ('de_DE','de_CH')
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585424,'en_US')
;

-- Columns
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593472 /*From ID Server*/,585423,0,13,NULL,542644,'RV_ReceiptDisposition_DeliveryPlanning_ID',TO_TIMESTAMP('2026-09-03 08:00:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',10,NULL,'Y','N','N','N','N','Y','N','N','N','N','N','N','Wareneingangslogistik','NP',0,TO_TIMESTAMP('2026-09-03 08:00:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593472 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593473 /*From ID Server*/,542202,0,30,NULL,542644,'M_ReceiptSchedule_ID',TO_TIMESTAMP('2026-09-03 08:00:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',10,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Wareneingangsdisposition','NP',0,TO_TIMESTAMP('2026-09-03 08:00:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593473 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593474 /*From ID Server*/,581677,0,30,NULL,542644,'M_Delivery_Planning_ID',TO_TIMESTAMP('2026-09-03 08:00:15','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,'D',10,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Lieferplanung','NP',0,TO_TIMESTAMP('2026-09-03 08:00:16','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593474 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593475 /*From ID Server*/,454,0,30,NULL,542644,'M_Product_ID',TO_TIMESTAMP('2026-09-03 08:00:17','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','D',10,'Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','N','N','N','Y','N','N','Produkt','NP',60,TO_TIMESTAMP('2026-09-03 08:00:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593475 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593476 /*From ID Server*/,187,0,30,541252,542644,'C_BPartner_ID',TO_TIMESTAMP('2026-09-03 08:00:19','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','D',10,'Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','N','N','N','Y','N','N','Geschäftspartner','NP',0,TO_TIMESTAMP('2026-09-03 08:00:20','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593476 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593477 /*From ID Server*/,459,0,30,NULL,542644,'M_Warehouse_ID',TO_TIMESTAMP('2026-09-03 08:00:21','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','D',10,'Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','N','N','N','Y','N','N','Lager','NP',50,TO_TIMESTAMP('2026-09-03 08:00:22','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593477 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593478 /*From ID Server*/,558,0,30,NULL,542644,'C_Order_ID',TO_TIMESTAMP('2026-09-03 08:00:23','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','D',10,'The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','N','N','N','Y','N','N','Auftrag','NP',80,TO_TIMESTAMP('2026-09-03 08:00:24','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593478 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593479 /*From ID Server*/,584067,0,15,NULL,542644,'ETA',TO_TIMESTAMP('2026-09-03 08:00:25','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum','D',7,NULL,'Y','N','N','N','N','N','N','N','Y','Y','N','N','ETA','NP',0,TO_TIMESTAMP('2026-09-03 08:00:26','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593479 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593480 /*From ID Server*/,584066,0,15,NULL,542644,'ETD',TO_TIMESTAMP('2026-09-03 08:00:27','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum','D',7,NULL,'Y','N','N','N','N','N','N','N','Y','Y','N','N','ETD','NP',0,TO_TIMESTAMP('2026-09-03 08:00:28','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593480 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593481 /*From ID Server*/,584068,0,15,NULL,542644,'ATD',TO_TIMESTAMP('2026-09-03 08:00:29','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum','D',7,NULL,'Y','N','N','N','N','N','N','N','Y','Y','N','N','ATD','NP',0,TO_TIMESTAMP('2026-09-03 08:00:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593481 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593482 /*From ID Server*/,584069,0,15,NULL,542644,'ATA',TO_TIMESTAMP('2026-09-03 08:00:31','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum','D',7,NULL,'Y','N','N','N','N','N','N','N','Y','Y','N','N','ATA','NP',0,TO_TIMESTAMP('2026-09-03 08:00:32','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593482 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593483 /*From ID Server*/,542422,0,16,NULL,542644,'DatePromised_Effective',TO_TIMESTAMP('2026-09-03 08:00:33','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag','D',7,NULL,'Y','N','N','N','N','N','N','N','Y','Y','N','N','Zugesagter Termin eff.','NP',0,TO_TIMESTAMP('2026-09-03 08:00:34','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593483 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593484 /*From ID Server*/,531,0,29,NULL,542644,'QtyOrdered',TO_TIMESTAMP('2026-09-03 08:00:35','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt','D',14,'Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','N','N','N','N','N','N','N','N','N','Bestellt/ Beauftragt','NP',0,TO_TIMESTAMP('2026-09-03 08:00:36','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593484 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593485 /*From ID Server*/,952,0,10,NULL,542644,'POReference',TO_TIMESTAMP('2026-09-03 08:00:37','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','D',40,'The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','N','N','N','N','N','N','Y','N','N','Referenz','NP',90,TO_TIMESTAMP('2026-09-03 08:00:38','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593485 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593486 /*From ID Server*/,584072,0,10,NULL,542644,'ContainerNo',TO_TIMESTAMP('2026-09-03 08:00:39','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers','D',255,NULL,'Y','N','N','N','N','N','N','N','N','Y','N','N','Container-Nr.','NP',180,TO_TIMESTAMP('2026-09-03 08:00:40','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593486 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593487 /*From ID Server*/,102,0,30,NULL,542644,'AD_Client_ID',TO_TIMESTAMP('2026-09-03 08:00:41','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','D',10,'Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','N','N','N','N','N','N','Mandant','NP',0,TO_TIMESTAMP('2026-09-03 08:00:42','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593487 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593488 /*From ID Server*/,113,0,30,NULL,542644,'AD_Org_ID',TO_TIMESTAMP('2026-09-03 08:00:43','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','D',10,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','N','N','N','Y','N','N','Sektion','NP',200,TO_TIMESTAMP('2026-09-03 08:00:44','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593488 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593489 /*From ID Server*/,348,0,20,NULL,542644,'IsActive',TO_TIMESTAMP('2026-09-03 08:00:45','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','D',1,'Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','N','N','N','N','N','N','N','N','N','N','N','Aktiv','NP',0,TO_TIMESTAMP('2026-09-03 08:00:46','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593489 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593490 /*From ID Server*/,245,0,16,NULL,542644,'Created',TO_TIMESTAMP('2026-09-03 08:00:47','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde','D',7,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Erstellt','NP',0,TO_TIMESTAMP('2026-09-03 08:00:48','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593490 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593491 /*From ID Server*/,246,0,18,NULL,542644,'CreatedBy',TO_TIMESTAMP('2026-09-03 08:00:49','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat','D',10,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Erstellt durch','NP',0,TO_TIMESTAMP('2026-09-03 08:00:50','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593491 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593492 /*From ID Server*/,607,0,16,NULL,542644,'Updated',TO_TIMESTAMP('2026-09-03 08:00:51','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde','D',7,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Aktualisiert','NP',0,TO_TIMESTAMP('2026-09-03 08:00:52','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593492 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,Description,EntityType,FieldLength,Help,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsRangeFilter,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,SelectionColumnSeqNo,Updated,UpdatedBy,Version)
VALUES (0,593493 /*From ID Server*/,608,0,18,NULL,542644,'UpdatedBy',TO_TIMESTAMP('2026-09-03 08:00:53','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat','D',10,NULL,'Y','N','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch','NP',0,TO_TIMESTAMP('2026-09-03 08:00:54','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,0)
;

INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593493 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;

-- Window
INSERT INTO AD_Window (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,IsActive,IsBetaFunctionality,IsDefault,IsEnableRemoteCacheInvalidation,IsExcludeFromZoomTargets,IsOneInstanceOnly,IsOverrideInMenu,IsSOTrx,Name,Processing,Updated,UpdatedBy,WindowType,WinHeight,WinWidth,ZoomIntoPriority)
VALUES (0,585424,0,542190 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 08:00:55','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','Y','N','N','N','N','N','N','N','Wareneingangslogistik','N',TO_TIMESTAMP('2026-09-03 08:00:56','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Q',0,0,100)
;

INSERT INTO AD_Window_Trl (AD_Language,AD_Window_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Window_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Window t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Window_ID=542190 AND NOT EXISTS (SELECT 1 FROM AD_Window_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Window_ID=t.AD_Window_ID)
;

/* DDL */ SELECT update_window_translation_from_ad_element(585424)
;

/* DDL */ SELECT AD_Element_Link_Create_Missing_Window(542190)
;

-- Tab: read-only grid over the view; no insert, refreshed when the underlying data changes
INSERT INTO AD_Tab (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy)
VALUES (0,585423,0,549491 /*From ID Server*/,542644,542190,'N',TO_TIMESTAMP('2026-09-03 08:00:57','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','N','N','RV_ReceiptDisposition_DeliveryPlanning','Y','N','Y','Y','Y','Y','N','N','Y','Y','N','Y','N','N','N','N','N',0,'Wareneingangslogistik','N',10,0,TO_TIMESTAMP('2026-09-03 08:00:58','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,NotFound_Message,NotFound_MessageDetail,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.NotFound_Message,t.NotFound_MessageDetail,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549491 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;

/* DDL */ SELECT update_tab_translation_from_ad_element(585423)
;

-- Fields
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593472,784923 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:00:59','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,10,'D',NULL,'Y','N','N','N','N','N','Y','N','Wareneingangslogistik',TO_TIMESTAMP('2026-09-03 08:01:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784923 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593473,784924 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,10,'D',NULL,'Y','N','N','N','N','N','Y','N','Wareneingangsdisposition',TO_TIMESTAMP('2026-09-03 08:01:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784924 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593474,784925 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,10,'D',NULL,'Y','N','N','N','N','N','Y','N','Lieferplanung',TO_TIMESTAMP('2026-09-03 08:01:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784925 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593475,784926 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel',10,'D','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','N','N','N','Y','N','Produkt',TO_TIMESTAMP('2026-09-03 08:01:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784926 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593476,784927 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner',10,'D','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','N','N','N','Y','N','Geschäftspartner',TO_TIMESTAMP('2026-09-03 08:01:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784927 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593477,784928 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:09','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung',10,'D','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','N','N','N','Y','N','Lager',TO_TIMESTAMP('2026-09-03 08:01:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784928 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593478,784929 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag',10,'D','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','N','N','N','Y','N','Auftrag',TO_TIMESTAMP('2026-09-03 08:01:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784929 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593479,784930 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum',7,'D',NULL,'Y','N','N','N','N','N','Y','N','ETA',TO_TIMESTAMP('2026-09-03 08:01:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784930 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593480,784931 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:15','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum',7,'D',NULL,'Y','N','N','N','N','N','Y','N','ETD',TO_TIMESTAMP('2026-09-03 08:01:16','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784931 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593481,784932 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:17','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum',7,'D',NULL,'Y','N','N','N','N','N','Y','N','ATD',TO_TIMESTAMP('2026-09-03 08:01:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784932 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593482,784933 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:19','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum',7,'D',NULL,'Y','N','N','N','N','N','Y','N','ATA',TO_TIMESTAMP('2026-09-03 08:01:20','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784933 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593483,784934 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:21','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',7,'D',NULL,'Y','N','N','N','N','N','Y','N','Zugesagter Termin eff.',TO_TIMESTAMP('2026-09-03 08:01:22','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784934 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593484,784935 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:23','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt',14,'D','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','N','N','N','Y','N','Bestellt/ Beauftragt',TO_TIMESTAMP('2026-09-03 08:01:24','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784935 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593485,784936 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:25','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden',40,'D','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','N','N','N','Y','N','Referenz',TO_TIMESTAMP('2026-09-03 08:01:26','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784936 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593486,784937 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:27','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers',255,'D',NULL,'Y','N','N','N','N','N','Y','N','Container-Nr.',TO_TIMESTAMP('2026-09-03 08:01:28','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784937 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593487,784938 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:29','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.',10,'D','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','N','N','N','Y','N','Mandant',TO_TIMESTAMP('2026-09-03 08:01:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784938 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593488,784939 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:31','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten',10,'D','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','N','N','N','Y','N','Sektion',TO_TIMESTAMP('2026-09-03 08:01:32','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784939 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593489,784940 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:33','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv',1,'D','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','N','N','N','N','N','Y','N','Aktiv',TO_TIMESTAMP('2026-09-03 08:01:34','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784940 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593490,784941 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:35','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag erstellt wurde',7,'D',NULL,'Y','N','N','N','N','N','Y','N','Erstellt',TO_TIMESTAMP('2026-09-03 08:01:36','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784941 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593491,784942 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:37','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag erstellt hat',10,'D',NULL,'Y','N','N','N','N','N','Y','N','Erstellt durch',TO_TIMESTAMP('2026-09-03 08:01:38','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784942 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593492,784943 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:39','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Datum, an dem dieser Eintrag aktualisiert wurde',7,'D',NULL,'Y','N','N','N','N','N','Y','N','Aktualisiert',TO_TIMESTAMP('2026-09-03 08:01:40','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784943 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,Description,DisplayLength,EntityType,Help,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,Updated,UpdatedBy)
VALUES (0,593493,784944 /*From ID Server*/,0,549491,TO_TIMESTAMP('2026-09-03 08:01:41','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nutzer, der diesen Eintrag aktualisiert hat',10,'D',NULL,'Y','N','N','N','N','N','Y','N','Aktualisiert durch',TO_TIMESTAMP('2026-09-03 08:01:42','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=784944 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;

-- Every field inherits its caption from an AD_Element that is already translated, so pull the
-- non-base-language texts down onto the new AD_Field_Trl rows in one pass.
UPDATE AD_Field_Trl ft
SET Name = et.Name, Description = et.Description, Help = et.Help, IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-09-03 08:01:43','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC', UpdatedBy = 100
FROM AD_Field f
         JOIN AD_Column c ON c.AD_Column_ID = f.AD_Column_ID
         JOIN AD_Element_Trl et ON et.AD_Element_ID = c.AD_Element_ID
WHERE ft.AD_Field_ID = f.AD_Field_ID
  AND et.AD_Language = ft.AD_Language
  AND f.AD_Tab_ID = 549491
  AND ft.AD_Language <> 'de_DE'
  AND et.IsTranslated = 'Y'
;

-- UI layout: one section, a left column carrying the record and its dates, a right column with the flags and the org
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value)
VALUES (0,0,549491,547990 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 08:01:44','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-09-03 08:01:45','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'main')
;

INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547990 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;

INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549755 /*From ID Server*/,547990,TO_TIMESTAMP('2026-09-03 08:01:46','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',10,TO_TIMESTAMP('2026-09-03 08:01:47','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549756 /*From ID Server*/,547990,TO_TIMESTAMP('2026-09-03 08:01:48','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y',20,TO_TIMESTAMP('2026-09-03 08:01:49','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy)
VALUES (0,0,549755,555764 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 08:01:50','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','main',10,'primary',TO_TIMESTAMP('2026-09-03 08:01:51','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549755,555765 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 08:01:52','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','dates',20,TO_TIMESTAMP('2026-09-03 08:01:53','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549756,555766 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 08:01:54','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','flags',10,TO_TIMESTAMP('2026-09-03 08:01:55','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,0,549756,555767 /*From ID Server*/,TO_TIMESTAMP('2026-09-03 08:01:56','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Y','org',20,TO_TIMESTAMP('2026-09-03 08:01:57','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Field placement. The grid order is the dispatcher's reading order: when it arrives, what the
-- order promised, from whom, what and how much, into which warehouse, then whether a planning
-- exists at all -- an empty Lieferplanung is exactly the row that still needs chasing.
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784925,0,549491,555764,654694 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:01:58','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,NULL,'Y','N','N','Y','Y','N','N',0,'Lieferplanung',10,70,0,TO_TIMESTAMP('2026-09-03 08:01:59','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784924,0,549491,555764,654695 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:00','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,NULL,NULL,'Y','N','N','Y','Y','N','N',0,'Wareneingangsdisposition',20,140,0,TO_TIMESTAMP('2026-09-03 08:02:01','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784929,0,549491,555764,654696 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:02','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Auftrag','The Order is a control document.  The  Order is complete when the quantity ordered is the same as the quantity shipped and invoiced.  When you cloase an order, unshipped (backordered) quantities are cancelled.','Y','N','N','Y','Y','N','N',0,'Auftrag',30,80,0,TO_TIMESTAMP('2026-09-03 08:02:03','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784936,0,549491,555764,654697 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:04','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Referenz-Nummer des Kunden','The business partner order reference is the order reference for this specific transaction; Often Purchase Order numbers are given to print on Invoices for easier reference.  A standard number can be defined in the Business Partner (Customer) window.','Y','N','N','Y','Y','N','N',0,'Referenz',40,90,0,TO_TIMESTAMP('2026-09-03 08:02:05','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784927,0,549491,555764,654698 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:06','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bezeichnet einen Geschäftspartner','Ein Geschäftspartner ist jemand, mit dem Sie interagieren. Dies kann Lieferanten, Kunden, Mitarbeiter oder Handelsvertreter umfassen.','Y','N','N','Y','Y','N','N',0,'Geschäftspartner',50,30,0,TO_TIMESTAMP('2026-09-03 08:02:07','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784926,0,549491,555764,654699 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:08','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Produkt, Leistung, Artikel','Bezeichnet eine Einheit, die in dieser Organisation gekauft oder verkauft wird.','Y','N','N','Y','Y','N','N',0,'Produkt',60,40,0,TO_TIMESTAMP('2026-09-03 08:02:09','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784935,0,549491,555764,654700 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:10','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Bestellt/ Beauftragt','Die "Bestellte Menge" bezeichnet die Menge einer Ware, die bestellt wurde.','Y','N','N','Y','Y','N','N',0,'Bestellt/ Beauftragt',70,50,0,TO_TIMESTAMP('2026-09-03 08:02:11','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784928,0,549491,555764,654701 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:12','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Lager oder Ort für Dienstleistung','Das Lager identifiziert ein einzelnes Lager für Artikel oder einen Standort an dem Dienstleistungen geboten werden.','Y','N','N','Y','Y','N','N',0,'Lager',80,60,0,TO_TIMESTAMP('2026-09-03 08:02:13','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784937,0,549491,555764,654702 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:14','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Nummer des Containers',NULL,'Y','N','N','Y','Y','N','N',0,'Container-Nr.',90,100,0,TO_TIMESTAMP('2026-09-03 08:02:15','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784930,0,549491,555765,654703 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:16','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Ankunftsdatum',NULL,'Y','N','N','Y','Y','N','N',0,'ETA',10,10,0,TO_TIMESTAMP('2026-09-03 08:02:17','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784934,0,549491,555765,654704 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:18','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Zugesagter Termin für diesen Auftrag',NULL,'Y','N','N','Y','Y','N','N',0,'Zugesagter Termin eff.',20,20,0,TO_TIMESTAMP('2026-09-03 08:02:19','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784931,0,549491,555765,654705 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:20','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Voraussichtliches Versanddatum',NULL,'Y','N','N','Y','Y','N','N',0,'ETD',30,110,0,TO_TIMESTAMP('2026-09-03 08:02:21','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784932,0,549491,555765,654706 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:22','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Versanddatum',NULL,'Y','N','N','Y','Y','N','N',0,'ATD',40,120,0,TO_TIMESTAMP('2026-09-03 08:02:23','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784933,0,549491,555765,654707 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:24','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Tatsächliches Ankunftsdatum',NULL,'Y','N','N','Y','Y','N','N',0,'ATA',50,130,0,TO_TIMESTAMP('2026-09-03 08:02:25','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784940,0,549491,555766,654708 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:26','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Der Eintrag ist im System aktiv','Es gibt zwei Möglichkeiten, einen Datensatz nicht mehr verfügbar zu machen: einer ist, ihn zu löschen; der andere, ihn zu deaktivieren.','Y','N','N','Y','N','N','N',0,'Aktiv',10,0,0,TO_TIMESTAMP('2026-09-03 08:02:27','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784939,0,549491,555767,654709 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:28','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Organisatorische Einheit des Mandanten','Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Y','N','N','Y','Y','N','N',0,'Sektion',10,150,0,TO_TIMESTAMP('2026-09-03 08:02:29','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,Description,Help,IsActive,IsAdvancedField,IsAllowFiltering,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,IsMultiLine,MultiLine_LinesCount,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy)
VALUES (0,784938,0,549491,555767,654710 /*From ID Server*/,'F',TO_TIMESTAMP('2026-09-03 08:02:30','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'Mandant für diese Installation.','Ein Mandant ist eine Firma oder eine juristische Person. Sie können keine Daten über Mandanten hinweg verwenden. .','Y','N','N','Y','N','N','N',0,'Mandant',20,0,0,TO_TIMESTAMP('2026-09-03 08:02:31','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

-- Menu entry, next to Lieferplanung under Logistik
INSERT INTO AD_Menu (Action,AD_Client_ID,AD_Element_ID,AD_Menu_ID,AD_Org_ID,AD_Window_ID,Created,CreatedBy,EntityType,InternalName,IsActive,IsCreateNew,IsReadOnly,IsSOTrx,IsSummary,Name,Updated,UpdatedBy)
VALUES ('W',0,585424,542359 /*From ID Server*/,0,542190,TO_TIMESTAMP('2026-09-03 08:02:32','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,'D','542190 - RV_ReceiptDisposition_DeliveryPlanning','Y','N','Y','N','N','Wareneingangslogistik',TO_TIMESTAMP('2026-09-03 08:02:33','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100)
;

INSERT INTO AD_Menu_Trl (AD_Language,AD_Menu_ID, Description,Name,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Menu_ID, t.Description,t.Name,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Menu t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Menu_ID=542359 AND NOT EXISTS (SELECT 1 FROM AD_Menu_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Menu_ID=t.AD_Menu_ID)
;

INSERT INTO AD_TreeNodeMM (AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,AD_Tree_ID,Node_ID,Parent_ID,SeqNo) SELECT t.AD_Client_ID,0,'Y',TO_TIMESTAMP('2026-09-03 08:02:34','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,TO_TIMESTAMP('2026-09-03 08:02:35','YYYY-MM-DD HH24:MI:SS')::timestamp without time zone AT TIME ZONE 'UTC',100,t.AD_Tree_ID,542359,1000016,15 FROM AD_Tree t WHERE t.AD_Client_ID=0 AND t.IsActive='Y' AND t.IsAllNodes='Y' AND t.AD_Table_ID=116 AND NOT EXISTS (SELECT 1 FROM AD_TreeNodeMM e WHERE e.AD_Tree_ID=t.AD_Tree_ID AND e.Node_ID=542359)
;

/* DDL */ SELECT update_menu_translation_from_ad_element(585424)
;
