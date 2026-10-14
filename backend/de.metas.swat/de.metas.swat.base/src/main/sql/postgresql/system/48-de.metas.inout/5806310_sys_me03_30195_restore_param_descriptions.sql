-- Run mode: SWING_CLIENT

-- me03#30195: Re-apply parameter descriptions for Historical_Shipments_JSON (585488)
-- and Historical_Invoices_JSON (585485). A migration squeezed in by another PR reset
-- some of these descriptions; this script restores them unconditionally.
-- Main record (base language DE) = German; en_US Trl = English; de_DE/de_CH Trl = German.

-- ============================================================
-- Historical_Shipments_JSON (AD_Process_ID=585488) — Process text
-- ============================================================

UPDATE AD_Process
SET Description='Exportiert historische Lieferscheine als JSON für externe Systeme',
    Help=E'Exportiert historische Lieferscheine als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur den Lieferschein mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Lieferscheine dieser Bestellung\n- BPartnerValue: Nur Lieferscheine dieses Geschäftspartners (Suchschlüssel)\n- ShipmentDateGE: Nur Lieferscheine ab diesem Bewegungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585488
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exports historical shipments as JSON for external systems',
    Help=E'Exports historical shipments as JSON for external systems.\n\nParameters:\n- UpdatedGE: Return only records updated since this timestamp. Default: 9999-01-01 (all)\n- ExternalId: Return only the shipment with this external ID. Default: -1 (all)\n- ExternalSystemCode: Return only records from this external system\n- Order_ID: Return only shipments for this order\n- BPartnerValue: Return only shipments for this business partner (search key)\n- ShipmentDateGE: Return only shipments with movement date on or after this date\n- BPartnerExternalReference: Filter by external reference of the business partner\n- BPartnerExternalSystemValue: Filter by search key of the business partner in the external system\n- DocType_Base: Base document type filter\n- Limit: Maximum number of records to return. Default and maximum: 2000\n- Offset: Number of records to skip for pagination. Default: 0',
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

-- ============================================================
-- Historical_Invoices_JSON (AD_Process_ID=585485) — Process text
-- ============================================================

UPDATE AD_Process
SET Description='Exportiert historische Rechnungen als JSON für externe Systeme',
    Help=E'Exportiert historische Rechnungen als JSON für externe Systeme.\n\nParameter:\n- UpdatedGE: Nur Datensätze zurückgeben, die seit diesem Zeitpunkt aktualisiert wurden. Standard: 9999-01-01 (alle)\n- ExternalId: Nur die Rechnung mit dieser externen ID zurückgeben. Standard: -1 (alle)\n- ExternalSystemCode: Nur Datensätze dieses externen Systems zurückgeben\n- Order_ID: Nur Rechnungen dieser Bestellung\n- BPartnerValue: Nur Rechnungen dieses Geschäftspartners (Suchschlüssel)\n- DateInvoicedGE: Nur Rechnungen ab diesem Rechnungsdatum\n- BPartnerExternalReference: Filter nach externer Referenz des Geschäftspartners\n- BPartnerExternalSystemValue: Filter nach Suchschlüssel des Geschäftspartners im externen System\n- DocType_Base: Filter nach Basis-Dokumenttyp\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585485
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Exports historical invoices as JSON for external systems',
    Help=E'Exports historical invoices as JSON for external systems.\n\nParameters:\n- UpdatedGE: Return only records updated since this timestamp. Default: 9999-01-01 (all)\n- ExternalId: Return only the invoice with this external ID. Default: -1 (all)\n- ExternalSystemCode: Return only records from this external system\n- Order_ID: Return only invoices for this order\n- BPartnerValue: Return only invoices for this business partner (search key)\n- DateInvoicedGE: Return only invoices with invoice date on or after this date\n- BPartnerExternalReference: Filter by external reference of the business partner\n- BPartnerExternalSystemValue: Filter by search key of the business partner in the external system\n- DocType_Base: Base document type filter\n- Limit: Maximum number of records to return. Default and maximum: 2000\n- Offset: Number of records to skip for pagination. Default: 0',
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

-- ============================================================
-- Parameter descriptions — Shipments (585488)
-- Main record = German; en_US Trl = English; de_DE/de_CH Trl = German
-- ============================================================

-- Limit (543239)
UPDATE AD_Process_Para SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543239;
UPDATE AD_Process_Para_Trl SET Description='Maximum number of records to return. Default and maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543239 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543239 AND AD_Language IN ('de_DE', 'de_CH');

-- Offset (543240)
UPDATE AD_Process_Para SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543240;
UPDATE AD_Process_Para_Trl SET Description='Number of records to skip for pagination. Default: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543240 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543240 AND AD_Language IN ('de_DE', 'de_CH');

-- ExternalId (542970)
UPDATE AD_Process_Para SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=542970;
UPDATE AD_Process_Para_Trl SET Description='External ID of the record. Default: -1 (return all)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=542970 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=542970 AND AD_Language IN ('de_DE', 'de_CH');

-- Order_ID (543062)
UPDATE AD_Process_Para SET Description='Nur Datensätze dieser Bestellung zurückgeben', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543062;
UPDATE AD_Process_Para_Trl SET Description='Return only records for this order', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543062 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Nur Datensätze dieser Bestellung zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543062 AND AD_Language IN ('de_DE', 'de_CH');

-- BPartnerValue (543065)
UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543065;
UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543065 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543065 AND AD_Language IN ('de_DE', 'de_CH');

-- ShipmentDateGE (543066)
UPDATE AD_Process_Para SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543066;
UPDATE AD_Process_Para_Trl SET Description='Return only shipments with movement date on or after this date', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543066 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Nur Lieferscheine ab diesem Bewegungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543066 AND AD_Language IN ('de_DE', 'de_CH');

-- BPartnerExternalReference (543070)
UPDATE AD_Process_Para SET Description='Externe Referenz des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543070;
UPDATE AD_Process_Para_Trl SET Description='External reference of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543070 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Externe Referenz des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543070 AND AD_Language IN ('de_DE', 'de_CH');

-- BPartnerExternalSystemValue (543071)
UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543071;
UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543071 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543071 AND AD_Language IN ('de_DE', 'de_CH');

-- DocType_Base (543075)
UPDATE AD_Process_Para SET Description='Basis-Dokumenttyp-Filter', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543075;
UPDATE AD_Process_Para_Trl SET Description='Base document type filter', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543075 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Basis-Dokumenttyp-Filter', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543075 AND AD_Language IN ('de_DE', 'de_CH');

-- ============================================================
-- Parameter descriptions — Invoices (585485)
-- ============================================================

-- Limit (543242)
UPDATE AD_Process_Para SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543242;
UPDATE AD_Process_Para_Trl SET Description='Maximum number of records to return. Default and maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543242 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543242 AND AD_Language IN ('de_DE', 'de_CH');

-- Offset (543243)
UPDATE AD_Process_Para SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543243;
UPDATE AD_Process_Para_Trl SET Description='Number of records to skip for pagination. Default: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543243 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543243 AND AD_Language IN ('de_DE', 'de_CH');

-- ExternalId (542967)
UPDATE AD_Process_Para SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=542967;
UPDATE AD_Process_Para_Trl SET Description='External ID of the record. Default: -1 (return all)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=542967 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Externe ID des Datensatzes. Standard: -1 (alle zurückgeben)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=542967 AND AD_Language IN ('de_DE', 'de_CH');

-- Order_ID (543067)
UPDATE AD_Process_Para SET Description='Nur Datensätze dieser Bestellung zurückgeben', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543067;
UPDATE AD_Process_Para_Trl SET Description='Return only records for this order', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543067 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Nur Datensätze dieser Bestellung zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543067 AND AD_Language IN ('de_DE', 'de_CH');

-- BPartnerValue (543068)
UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543068;
UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543068 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543068 AND AD_Language IN ('de_DE', 'de_CH');

-- DateInvoicedGE (543069)
UPDATE AD_Process_Para SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543069;
UPDATE AD_Process_Para_Trl SET Description='Return only invoices with invoice date on or after this date', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543069 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Nur Rechnungen ab diesem Rechnungsdatum zurückgeben', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543069 AND AD_Language IN ('de_DE', 'de_CH');

-- BPartnerExternalReference (543072)
UPDATE AD_Process_Para SET Description='Externe Referenz des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543072;
UPDATE AD_Process_Para_Trl SET Description='External reference of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543072 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Externe Referenz des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543072 AND AD_Language IN ('de_DE', 'de_CH');

-- BPartnerExternalSystemValue (543073)
UPDATE AD_Process_Para SET Description='Suchschlüssel des Geschäftspartners im externen System', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543073;
UPDATE AD_Process_Para_Trl SET Description='Search key of the business partner in the external system', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543073 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Geschäftspartners im externen System', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543073 AND AD_Language IN ('de_DE', 'de_CH');

-- DocType_Base (543074)
UPDATE AD_Process_Para SET Description='Basis-Dokumenttyp-Filter', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543074;
UPDATE AD_Process_Para_Trl SET Description='Base document type filter', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543074 AND AD_Language='en_US';
UPDATE AD_Process_Para_Trl SET Description='Basis-Dokumenttyp-Filter', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Process_Para_ID=543074 AND AD_Language IN ('de_DE', 'de_CH');
