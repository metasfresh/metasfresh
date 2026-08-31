-- The re-booking trail as a read-only HISTORY tab on the Delivery Instruction window (541657): its own
-- table, view and tab. The view selects the retired allocations (dpa.IsActive='N') that both shipped
-- views filter out, so the inactive allocation row itself is the supersession link -- nothing has to be
-- inferred by comparing Created timestamps, which an aggregated header (N plannings, no single
-- M_Delivery_Planning_ID) could not answer anyway.
--
-- The view re-exposes IsActive as a constant 'Y': dpa.IsActive='N' is the SELECTION criterion, not a
-- property of the returned row, and passing it through would render every history entry as a
-- deactivated record. IsUpdateable='N' + AD_Tab.IsReadOnly='Y' mean the constant is never written back.
--
-- Caption is NOT "Lieferanweisungen fuer die Lieferplanung": AD_Tab 546737 already carries that one.

-- AD_Element for the tab caption. /*From ID Server*/ AD_Element_ID=585387
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585387 /*From ID Server*/,0,'M_ShipperTransportation_Delivery_Planning_History_V',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Lieferplanungen, die dieser Lieferanweisung zugeordnet waren und inzwischen umgebucht wurden','D','Y','Historie der Lieferplanungen','Historie der Lieferplanungen',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585387 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Historie der Lieferplanungen', Description='Lieferplanungen, die dieser Lieferanweisung zugeordnet waren und inzwischen umgebucht wurden', PrintName='Historie der Lieferplanungen', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585387 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Historie der Lieferplanungen', Description='Lieferplanungen, die dieser Lieferanweisung zugeordnet waren und inzwischen umgebucht wurden', PrintName='Historie der Lieferplanungen', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585387 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Delivery Planning History', Description='Delivery plannings that were allocated to this delivery instruction and have since been re-booked away', PrintName='Delivery Planning History', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585387 AND AD_Language='en_US'
;
-- fr_CH is pointed at the en_US text and left IsTranslated='N': no French wording exists for this
-- element, and English text in an fr_CH row is not correct text for that language.
UPDATE AD_Element_Trl SET Name='Delivery Planning History', Description='Delivery plannings that were allocated to this delivery instruction and have since been re-booked away', PrintName='Delivery Planning History', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585387 AND AD_Language='fr_CH'
;

-- AD_Element for DateRemoved -- the moment the allocation was deactivated, i.e. when the planning left
-- this instruction. Shared by the real column on M_Delivery_Planning_Alloc (added below) and by the
-- view column that surfaces it. /*From ID Server*/ AD_Element_ID=585388
INSERT INTO AD_Element (AD_Client_ID,AD_Element_ID,AD_Org_ID,ColumnName,Created,CreatedBy,Description,EntityType,IsActive,Name,PrintName,Updated,UpdatedBy) VALUES (0,585388 /*From ID Server*/,0,'DateRemoved',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Zeitpunkt, zu dem die Lieferplanung von der Lieferanweisung entfernt wurde','D','Y','Entfernt am','Entfernt am',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Element_Trl (AD_Language,AD_Element_ID, CommitWarning,Description,Help,Name,PO_Description,PO_Help,PO_Name,PO_PrintName,PrintName,WEBUI_NameBrowse,WEBUI_NameNew,WEBUI_NameNewBreadcrumb, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.PO_Description,t.PO_Help,t.PO_Name,t.PO_PrintName,t.PrintName,t.WEBUI_NameBrowse,t.WEBUI_NameNew,t.WEBUI_NameNewBreadcrumb, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Element t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Element_ID=585388 AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=t.AD_Element_ID)
;
UPDATE AD_Element_Trl SET Name='Entfernt am', Description='Zeitpunkt, zu dem die Lieferplanung von der Lieferanweisung entfernt wurde', PrintName='Entfernt am', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585388 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Entfernt am', Description='Zeitpunkt, zu dem die Lieferplanung von der Lieferanweisung entfernt wurde', PrintName='Entfernt am', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585388 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Removed On', Description='When the delivery planning was removed from the delivery instruction', PrintName='Removed On', IsTranslated='Y',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585388 AND AD_Language='en_US'
;
UPDATE AD_Element_Trl SET Name='Removed On', Description='When the delivery planning was removed from the delivery instruction', PrintName='Removed On', IsTranslated='N',
       Updated=TO_TIMESTAMP('2026-08-28 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
 WHERE AD_Element_ID=585388 AND AD_Language='fr_CH'
;


-- M_Delivery_Planning_Alloc.DateRemoved -- the real column the history view surfaces as its date.
--
-- timestamp WITH time zone (AD_Reference 16), matching Created/Updated rather than the ETD/ETA style:
-- this is an event instant, not a planned calendar date.
--
-- PersonalDataCategory='NP' here and on every AD_Column below: these columns hold ids, event/plan
-- timestamps, quantities and a release number -- no personal data.
/* DDL */ SELECT public.db_alter_table('M_Delivery_Planning_Alloc','ALTER TABLE public.M_Delivery_Planning_Alloc ADD COLUMN IF NOT EXISTS DateRemoved timestamp with time zone')
;

INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593433 /*From ID Server*/,585388,0,16,542641,'DateRemoved',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',35,'Y','Y','N','N','N','N','N','N','N','N','Y','Entfernt am','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593433 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585388);

-- Backfill: for rows retired before this column existed, dpa.Updated is the only record of the
-- deactivation instant that was ever kept -- an approximation, and only for those rows. Only inactive
-- rows get a value: an ACTIVE allocation has not been removed and must read NULL.
SELECT backup_table('m_delivery_planning_alloc', '_dateremoved_backfill');

UPDATE M_Delivery_Planning_Alloc
   SET DateRemoved = Updated
 WHERE IsActive = 'N' AND DateRemoved IS NULL
;

-- Source DDL: backend/de.metas.adempiere.adempiere/migration/src/main/sql/postgresql/ddl/public/views/M_ShipperTransportation_Delivery_Planning_History_V.sql
CREATE OR REPLACE VIEW M_ShipperTransportation_Delivery_Planning_History_V
AS
SELECT dpa.m_delivery_planning_alloc_id,
       dpa.m_shippertransportation_id,
       dpa.m_delivery_planning_id,
       dp.m_product_id,
       dp.releaseno,
       dp.etd,
       dp.eta,
       dp.plannedloadedquantity,
       dp.planneddischargequantity,
       dpa.dateremoved,
       'Y'::character(1) AS isactive,
       dpa.ad_client_id,
       dpa.ad_org_id,
       dpa.created,
       dpa.createdby,
       dpa.updated,
       dpa.updatedby
FROM M_Delivery_Planning_Alloc dpa
         JOIN M_Delivery_Planning dp ON dp.m_delivery_planning_id = dpa.m_delivery_planning_id
WHERE dpa.isactive = 'N'
;

-- AD_Table. Flags mirror the sibling view table 542287 (AccessLevel 3, IsView, IsDeleteable='N',
-- IsHighVolume='Y' -- both are view-backed child tabs on this window). /*From ID Server*/
INSERT INTO AD_Table (AccessLevel,ACTriggerLength,AD_Client_ID,AD_Org_ID,AD_Table_ID,AD_Window_ID,CopyColumnsFromTable,Created,CreatedBy,EntityType,ImportTable,IsActive,IsAutocomplete,IsChangeLog,IsDeleteable,IsDLM,IsEnableRemoteCacheInvalidation,IsHighVolume,IsSecurityEnabled,IsView,LoadSeq,Name,PersonalDataCategory,ReplicationType,TableName,TooltipType,Updated,UpdatedBy,WEBUI_View_PageLength) VALUES ('3',0,0,0,542642 /*From ID Server*/,541657,'N',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D','N','Y','N','N','N','N','Y','Y','N','Y',0,'Delivery Planning History','NP','L','M_ShipperTransportation_Delivery_Planning_History_V','DTI',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Table_Trl (AD_Language,AD_Table_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Table_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Table t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Table_ID=542642 AND NOT EXISTS (SELECT 1 FROM AD_Table_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Table_ID=t.AD_Table_ID)
;

-- Table-ID sequence, as every AD_Table gets one. /*From ID Server*/
INSERT INTO AD_Sequence (AD_Client_ID,AD_Org_ID,AD_Sequence_ID,Created,CreatedBy,CurrentNext,CurrentNextSys,Description,IncrementNo,IsActive,IsAudited,IsAutoSequence,IsTableID,Name,StartNo,Updated,UpdatedBy) VALUES (0,0,556654 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,1000000,50000,'Table M_ShipperTransportation_Delivery_Planning_History_V',1,'Y','N','Y','Y','M_ShipperTransportation_Delivery_Planning_History_V',1000000,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
CREATE SEQUENCE IF NOT EXISTS M_SHIPPERTRANSPORTATION_DELIVERY_PLANNING_HISTORY_V_SEQ INCREMENT 1 MINVALUE 1 MAXVALUE 2147483647 START 1000000
;

-- AD_Columns. Every column is IsUpdateable='N' -- the tab is a read-only audit surface.
-- M_Delivery_Planning_Alloc_ID is the single IsKey column (unique per row by construction).
-- All AD_Element_IDs are pre-existing elements except DateRemoved (585388, created above).
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593416 /*From ID Server*/,585382,0,13,542642,'M_Delivery_Planning_Alloc_ID',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','Y','N','N','N','N','N','Lieferplanung-Zuordnung','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593416 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585382);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593417 /*From ID Server*/,540089,0,30,542642,'M_ShipperTransportation_ID',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Transport Auftrag','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593417 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(540089);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593418 /*From ID Server*/,581677,0,30,542642,'M_Delivery_Planning_ID',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Lieferplanung','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593418 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(581677);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593419 /*From ID Server*/,454,0,30,542642,'M_Product_ID',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Produkt','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593419 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(454);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593420 /*From ID Server*/,2122,0,10,542642,'ReleaseNo',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',250,'Y','N','N','N','N','N','N','N','N','N','N','Ausgabenummer','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593420 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(2122);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593421 /*From ID Server*/,584066,0,16,542642,'ETD',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',29,'Y','N','N','N','N','N','N','N','N','N','N','ETD','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593421 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(584066);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593422 /*From ID Server*/,584067,0,16,542642,'ETA',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',29,'Y','N','N','N','N','N','N','N','N','N','N','ETA','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593422 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(584067);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593423 /*From ID Server*/,581794,0,29,542642,'PlannedLoadedQuantity',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','N','N','N','N','N','N','N','N','N','N','Geplante Verlademenge','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593423 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(581794);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593424 /*From ID Server*/,581795,0,29,542642,'PlannedDischargeQuantity',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',14,'Y','N','N','N','N','N','N','N','N','N','N','Geplante Liefermenge','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593424 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(581795);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593425 /*From ID Server*/,585388,0,16,542642,'DateRemoved',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',35,'Y','N','N','N','N','N','N','N','N','N','N','Entfernt am','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593425 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(585388);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593426 /*From ID Server*/,348,0,20,542642,'IsActive',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',1,'Y','N','N','N','N','N','N','N','N','N','N','Aktiv','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593426 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(348);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593427 /*From ID Server*/,113,0,30,542642,'AD_Org_ID',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Sektion','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593427 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(113);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593428 /*From ID Server*/,102,0,30,542642,'AD_Client_ID',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Mandant','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593428 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(102);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593429 /*From ID Server*/,245,0,16,542642,'Created',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',35,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593429 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(245);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593430 /*From ID Server*/,246,0,18,110,542642,'CreatedBy',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Erstellt durch','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593430 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(246);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593431 /*From ID Server*/,607,0,16,542642,'Updated',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',35,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593431 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(607);
INSERT INTO AD_Column (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Reference_ID,AD_Reference_Value_ID,AD_Table_ID,ColumnName,Created,CreatedBy,EntityType,FieldLength,IsActive,IsAllowLogging,IsAlwaysUpdateable,IsEncrypted,IsIdentifier,IsKey,IsMandatory,IsParent,IsSelectionColumn,IsTranslated,IsUpdateable,Name,PersonalDataCategory,Updated,UpdatedBy,Version) VALUES (0,593432 /*From ID Server*/,608,0,18,110,542642,'UpdatedBy',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'D',10,'Y','N','N','N','N','N','N','N','N','N','N','Aktualisiert durch','NP',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,0)
;
INSERT INTO AD_Column_Trl (AD_Language,AD_Column_ID, Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Column t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Column_ID=593432 AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Column_ID=t.AD_Column_ID)
;
SELECT update_Column_Translation_From_AD_Element(608);

-- The tab. SeqNo 40 puts it after 546736 "Versandpaket" (20). IsReadOnly='Y' + IsInsertRecord='N': an
-- audit trail is never edited from the UI. AD_Column_ID names THIS view's M_ShipperTransportation_ID
-- column as the child link; Parent_Column_ID stays NULL so the framework falls back to the parent tab's
-- own key column, as the sibling tabs 546736/546737 also do. /*From ID Server*/
INSERT INTO AD_Tab (AD_Client_ID,AD_Column_ID,AD_Element_ID,AD_Org_ID,AD_Tab_ID,AD_Table_ID,AD_Window_ID,AllowQuickInput,Created,CreatedBy,Description,EntityType,HasTree,ImportFields,InternalName,IsActive,IsAdvancedTab,IsAutodetectDefaultDateFilter,IsCheckParentsChanged,IsGenericZoomTarget,IsGridModeOnly,IsInfoTab,IsInsertRecord,IsQueryOnLoad,IsReadOnly,IsRefreshAllOnActivate,IsRefreshViewOnChangeEvents,IsSearchActive,IsSearchCollapsed,IsSingleRow,IsSortTab,IsTranslationTab,MaxQueryRecords,Name,Processing,SeqNo,TabLevel,Updated,UpdatedBy) VALUES (0,593417,585387,0,549416 /*From ID Server*/,542642,541657,'N',TO_TIMESTAMP('2026-08-28 10:00:20','YYYY-MM-DD HH24:MI:SS'),100,'Lieferplanungen, die dieser Lieferanweisung zugeordnet waren und inzwischen umgebucht wurden','D','N','N','M_ShipperTransportation_Delivery_Planning_History_V','Y','N','Y','Y','N','N','N','N','Y','Y','N','N','Y','Y','N','N','N',0,'Historie der Lieferplanungen','N',40,1,TO_TIMESTAMP('2026-08-28 10:00:20','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Tab_Trl (AD_Language,AD_Tab_ID, CommitWarning,Description,Help,Name,QuickInput_CloseButton_Caption,QuickInput_OpenButton_Caption, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Tab_ID, t.CommitWarning,t.Description,t.Help,t.Name,t.QuickInput_CloseButton_Caption,t.QuickInput_OpenButton_Caption, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Tab t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Tab_ID=549416 AND NOT EXISTS (SELECT 1 FROM AD_Tab_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Tab_ID=t.AD_Tab_ID)
;
SELECT update_tab_translation_from_ad_element(585387);
SELECT AD_Element_Link_Create_Missing_Tab(549416);

-- AD_Fields. The key field (M_Delivery_Planning_Alloc_ID) and the link field
-- (M_ShipperTransportation_ID) MUST exist even though they are not displayed -- the framework resolves
-- both the link column and the key through the tab's FIELDS, not through the table's columns. Every
-- field is IsReadOnly='Y'.
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593416,783026 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','Y','N','Lieferplanung-Zuordnung',0,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783026 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783026);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593417,783027 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','Y','N','Transport Auftrag',0,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783027 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783027);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593418,783028 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Lieferplanung',10,10,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783028 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783028);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593419,783029 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Produkt',20,20,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783029 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783029);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593420,783030 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Ausgabenummer',30,30,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783030 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783030);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593421,783031 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','ETD',40,40,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783031 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783031);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593422,783032 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','ETA',50,50,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783032 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783032);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593423,783033 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Geplante Verlademenge',60,60,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783033 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783033);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593424,783034 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Geplante Liefermenge',70,70,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783034 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783034);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,SortNo,Updated,UpdatedBy) VALUES (0,593425,783035 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','Y','Y','N','N','N','Y','N','Entfernt am',80,80,-10,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783035 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783035);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593426,783036 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','Y','N','Aktiv',0,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783036 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783036);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593427,783037 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','Y','N','Sektion',0,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783037 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783037);
INSERT INTO AD_Field (AD_Client_ID,AD_Column_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,Created,CreatedBy,DisplayLength,EntityType,IsActive,IsDisplayed,IsDisplayedGrid,IsEncrypted,IsFieldOnly,IsHeading,IsReadOnly,IsSameLine,Name,SeqNo,SeqNoGrid,Updated,UpdatedBy) VALUES (0,593428,783038 /*From ID Server*/,0,549416,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,10,'D','Y','N','N','N','N','N','Y','N','Mandant',0,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_Field_Trl (AD_Language,AD_Field_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_Field_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_Field t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_Field_ID=783038 AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Field_ID=t.AD_Field_ID)
;
SELECT AD_Element_Link_Create_Missing_Field(783038);

-- WebUI layout. A single section/column/element group -- the tab is a flat read-only list.
-- IsDisplayedGrid/SeqNoGrid are set on BOTH AD_UI_Element and AD_Field: the section-backed rendering
-- path reads AD_UI_Element, the plain document path reads AD_Field, so setting both makes the grid
-- correct either way.
INSERT INTO AD_UI_Section (AD_Client_ID,AD_Org_ID,AD_Tab_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy,Value) VALUES (0,0,549416,547921 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'history')
;
INSERT INTO AD_UI_Section_Trl (AD_Language,AD_UI_Section_ID, Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive) SELECT l.AD_Language, t.AD_UI_Section_ID, t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y' FROM AD_Language l, AD_UI_Section t WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y' OR l.IsBaseLanguage='Y') AND t.AD_UI_Section_ID=547921 AND NOT EXISTS (SELECT 1 FROM AD_UI_Section_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_UI_Section_ID=t.AD_UI_Section_ID)
;
INSERT INTO AD_UI_Column (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_Section_ID,Created,CreatedBy,IsActive,SeqNo,Updated,UpdatedBy) VALUES (0,0,549677 /*From ID Server*/,547921,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y',10,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_ElementGroup (AD_Client_ID,AD_Org_ID,AD_UI_Column_ID,AD_UI_ElementGroup_ID,Created,CreatedBy,IsActive,Name,SeqNo,UIStyle,Updated,UpdatedBy) VALUES (0,0,549677,555640 /*From ID Server*/,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','main',10,'primary',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783028,0,549416,555640,653675 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Lieferplanung',10,10,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783029,0,549416,555640,653676 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Produkt',20,20,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783030,0,549416,555640,653677 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Ausgabenummer',30,30,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783031,0,549416,555640,653678 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','ETD',40,40,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783032,0,549416,555640,653679 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','ETA',50,50,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783033,0,549416,555640,653680 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Geplante Verlademenge',60,60,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783034,0,549416,555640,653681 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Geplante Liefermenge',70,70,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
INSERT INTO AD_UI_Element (AD_Client_ID,AD_Field_ID,AD_Org_ID,AD_Tab_ID,AD_UI_ElementGroup_ID,AD_UI_Element_ID,AD_UI_ElementType,Created,CreatedBy,IsActive,IsAdvancedField,IsDisplayed,IsDisplayedGrid,IsDisplayed_SideList,Name,SeqNo,SeqNoGrid,SeqNo_SideList,Updated,UpdatedBy) VALUES (0,783035,0,549416,555640,653682 /*From ID Server*/,'F',TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100,'Y','N','Y','Y','N','Entfernt am',80,80,0,TO_TIMESTAMP('2026-08-28 10:00:00','YYYY-MM-DD HH24:MI:SS'),100)
;
