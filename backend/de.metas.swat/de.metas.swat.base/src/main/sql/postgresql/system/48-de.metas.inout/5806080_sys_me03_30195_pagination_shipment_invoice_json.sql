-- Run mode: SWING_CLIENT

-- me03#30195: Add Limit and Offset UI parameters to Historical_Shipments_JSON and Historical_Invoices_JSON.
-- The JSONPath already uses &limit=@Limit/2000@&offset=@Offset/0@ (set by migration 5779820);
-- these AD_Process_Para records expose Limit and Offset as configurable fields in the process dialog.

-- ============================================================
-- Historical_Shipments_JSON (AD_Process_ID=585488)
-- ============================================================

-- Process description
-- 2026-06-02
UPDATE AD_Process
SET Description='Exportiert historische Lieferscheine als JSON für externe Systeme',
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exports historical shipments as JSON for external systems',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exportiert historische Lieferscheine als JSON für externe Systeme',
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ParameterName: Limit (SeqNo=100)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax)
VALUES (0,543188,0,585488,543239 /*From ID Server*/,11,'Limit',now(),100,'2000','D',0,'Y','N','Y','N','Y','N','Limit',100,now(),100,'2000')
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543239
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ParameterName: Offset (SeqNo=110)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,576802,0,585488,543240 /*From ID Server*/,11,'Offset',now(),100,'0','de.metas.dao.selection',0,'Y','N','Y','N','N','N','Offset',110,now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543240
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ============================================================
-- Historical_Invoices_JSON (AD_Process_ID=585485)
-- ============================================================

-- Process description
-- 2026-06-02
UPDATE AD_Process
SET Description='Exportiert historische Rechnungen als JSON für externe Systeme',
    Help=E'Exportiert historische Rechnungen als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur die Rechnung mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Rechnungen dieser Bestellung\n- BPartnerValue: Nur Rechnungen dieses Geschäftspartners (Suchschlüssel)\n- DateInvoicedGE: Nur Rechnungen ab diesem Rechnungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exports historical invoices as JSON for external systems',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exportiert historische Rechnungen als JSON für externe Systeme',
    Help=E'Exportiert historische Rechnungen als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur die Rechnung mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Rechnungen dieser Bestellung\n- BPartnerValue: Nur Rechnungen dieses Geschäftspartners (Suchschlüssel)\n- DateInvoicedGE: Nur Rechnungen ab diesem Rechnungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485 AND AD_Language IN ('de_DE', 'de_CH')
;

-- ParameterName: Limit (SeqNo=100)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy,ValueMax)
VALUES (0,543188,0,585485,543242 /*From ID Server*/,11,'Limit',now(),100,'2000','D',0,'Y','N','Y','N','Y','N','Limit',100,now(),100,'2000')
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543242
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ParameterName: Offset (SeqNo=110)
-- 2026-06-02
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy)
VALUES (0,576802,0,585485,543243 /*From ID Server*/,11,'Offset',now(),100,'0','de.metas.dao.selection',0,'Y','N','Y','N','N','N','Offset',110,now(),100)
;

INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy,IsActive)
SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy,'Y'
FROM AD_Language l, AD_Process_Para t
WHERE l.IsActive='Y' AND (l.IsSystemLanguage='Y')
  AND t.AD_Process_Para_ID=543243
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- ============================================================
-- Parameter descriptions
-- Main record (base language DE) = German; en_US Trl = English
-- ============================================================

-- 2026-06-03
-- Limit
UPDATE AD_Process_Para SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242);

UPDATE AD_Process_Para_Trl SET Description='Maximum number of records to return. Default and maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543239, 543242) AND AD_Language IN ('de_DE', 'de_CH');

-- Offset
UPDATE AD_Process_Para SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243);

UPDATE AD_Process_Para_Trl SET Description='Number of records to skip for pagination. Default: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543240, 543243) AND AD_Language IN ('de_DE', 'de_CH');

-- Existing parameters: German on main record, English on en_US Trl
UPDATE AD_Process_Para SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967);

UPDATE AD_Process_Para_Trl SET Description='External ID of the record. Default: -1 (return all)', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (542970, 542967) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Nur Datensätze dieser Bestellung zurückgeben', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067);

UPDATE AD_Process_Para_Trl SET Description='Return only records for this order', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Nur Datensätze dieser Bestellung zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543062, 543067) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068);

UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543065, 543068) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066;

UPDATE AD_Process_Para_Trl SET Description='Return only shipments with movement date on or after this date', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543066 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069;

UPDATE AD_Process_Para_Trl SET Description='Return only invoices with invoice date on or after this date', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543069 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Externe Referenz des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072);

UPDATE AD_Process_Para_Trl SET Description='External reference of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Externe Referenz des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543070, 543072) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073);

UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543071, 543073) AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Process_Para SET Description='Basis-Dokumenttyp-Filter', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075);

UPDATE AD_Process_Para_Trl SET Description='Base document type filter', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075) AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Basis-Dokumenttyp-Filter', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID IN (543074, 543075) AND AD_Language IN ('de_DE', 'de_CH');
