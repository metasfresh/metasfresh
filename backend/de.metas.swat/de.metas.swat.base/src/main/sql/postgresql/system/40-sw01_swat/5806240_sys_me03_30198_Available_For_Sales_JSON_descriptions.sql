-- Run mode: SWING_CLIENT

-- me03#30198: Add Description, Help and parameter descriptions to Available_For_Sales_JSON.
-- Limit (542992) and Offset (542993) params already exist; this migration adds text and
-- sets Limit as mandatory (with default 2000) so it appears pre-filled in the process dialog.

-- ============================================================
-- Available_For_Sales_JSON (AD_Process_ID=585498)
-- ============================================================

-- Process description
-- 2026-06-04
UPDATE AD_Process
SET Description='Gibt verfügbare Produkte zum Verkauf als JSON aus',
    Help=E'Gibt verfügbare Produkte zum Verkauf als JSON aus.\n\nParameter:\n- ExternalSystem: Filter nach externem System\n- WarehouseCode: Filter nach Lager (Suchschlüssel)\n- ProductValue: Filter nach Produkt (Suchschlüssel oder externe ID)\n- ProductExternalReference: Filter nach externer Produktreferenz\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585498
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Returns products available for sales as JSON',
    Help=E'Returns products available for sales as JSON.\n\nParameters:\n- ExternalSystem: Filter by external system\n- WarehouseCode: Filter by warehouse (search key)\n- ProductValue: Filter by product (search key or external ID)\n- ProductExternalReference: Filter by product external reference\n- Limit: Maximum number of records to return. Default and maximum: 2000\n- Offset: Number of records to skip for pagination. Default: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585498 AND AD_Language='en_US'
;

UPDATE AD_Process_Trl
SET IsTranslated='Y',
    Description='Gibt verfügbare Produkte zum Verkauf als JSON aus',
    Help=E'Gibt verfügbare Produkte zum Verkauf als JSON aus.\n\nParameter:\n- ExternalSystem: Filter nach externem System\n- WarehouseCode: Filter nach Lager (Suchschlüssel)\n- ProductValue: Filter nach Produkt (Suchschlüssel oder externe ID)\n- ProductExternalReference: Filter nach externer Produktreferenz\n- Limit: Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000\n- Offset: Anzahl der zu überspringenden Datensätze (Paginierung). Standard: 0',
    Updated=now(), UpdatedBy=100
WHERE AD_Process_ID=585498 AND AD_Language IN ('de_DE', 'de_CH')
;

-- Make Limit mandatory (pre-filled with default 2000, matching the #30195 treatment)
-- 2026-06-04
UPDATE AD_Process_Para
SET IsMandatory='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542992
;

-- ============================================================
-- Parameter descriptions
-- Main record (base language DE) = German; en_US Trl = English
-- ============================================================

-- ExternalSystem (542985)
UPDATE AD_Process_Para SET Description='Filter nach externem System', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542985;

UPDATE AD_Process_Para_Trl SET Description='Filter by external system', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542985 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Filter nach externem System', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542985 AND AD_Language IN ('de_DE', 'de_CH');

-- WarehouseCode (543002)
UPDATE AD_Process_Para SET Description='Suchschlüssel des Lagers', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543002;

UPDATE AD_Process_Para_Trl SET Description='Search key of the warehouse', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543002 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Suchschlüssel des Lagers', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543002 AND AD_Language IN ('de_DE', 'de_CH');

-- Limit (542992)
UPDATE AD_Process_Para SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542992;

UPDATE AD_Process_Para_Trl SET Description='Maximum number of records to return. Default and maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542992 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Maximale Anzahl zurückgegebener Datensätze. Standard und Maximum: 2000', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542992 AND AD_Language IN ('de_DE', 'de_CH');

-- Offset (542993)
UPDATE AD_Process_Para SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542993;

UPDATE AD_Process_Para_Trl SET Description='Number of records to skip for pagination. Default: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542993 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Anzahl der zu überspringenden Datensätze für Paginierung. Standard: 0', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=542993 AND AD_Language IN ('de_DE', 'de_CH');

-- ProductValue (543029)
UPDATE AD_Process_Para SET Description='Produktschlüssel', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543029;

UPDATE AD_Process_Para_Trl SET Description='Product identifier', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543029 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Produktschlüssel', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543029 AND AD_Language IN ('de_DE', 'de_CH');

-- ProductExternalReference (543030)
UPDATE AD_Process_Para SET Description='Produkt Externe Referenz', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543030;

UPDATE AD_Process_Para_Trl SET Description='Product external reference', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543030 AND AD_Language='en_US';

UPDATE AD_Process_Para_Trl SET Description='Produkt Externe Referenz', IsTranslated='Y', Updated=now(), UpdatedBy=100
WHERE AD_Process_Para_ID=543030 AND AD_Language IN ('de_DE', 'de_CH');
