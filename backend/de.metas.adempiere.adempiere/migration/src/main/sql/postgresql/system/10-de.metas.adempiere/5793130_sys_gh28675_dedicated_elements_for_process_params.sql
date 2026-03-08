-- gh#28675: Create dedicated AD_Elements for M_Product_Category_ID and M_Product_ID
-- process parameters on M_Forecast_GenerateLines (AD_Process 585593).
--
-- IsCentrallyMaintained=N does NOT prevent the element propagation from
-- overwriting Description/Help. The only reliable way is to use a dedicated
-- AD_Element with the correct descriptions.

-- ==========================================================================
-- 1. Dedicated element for M_Product_Category_ID filter param
-- ==========================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, AD_Element_ID, ColumnName, Created, CreatedBy,
                        EntityType, IsActive, Name, PrintName,
                        Description, Help,
                        Updated, UpdatedBy)
VALUES (0, 0, 584641, 'Forecast_M_Product_Category_ID',
        TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y',
        'Produkt Kategorie',
        'Produkt Kategorie',
        'Wenn gesetzt, werden nur Produkte dieser Kategorie berücksichtigt. Wenn leer, werden alle Produkte aus den Produkt-Plandaten verwendet.',
        'Optionaler Filter: Beschränkt die Prognosegenerierung auf Produkte einer bestimmten Kategorie. Ohne Auswahl werden alle Produkte verarbeitet, die in den Produkt-Plandaten eine Prognosekonfiguration haben.',
        TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 0, 0, 584641,
       'Produkt Kategorie', 'Produkt Kategorie',
       'Wenn gesetzt, werden nur Produkte dieser Kategorie berücksichtigt. Wenn leer, werden alle Produkte aus den Produkt-Plandaten verwendet.',
       'Optionaler Filter: Beschränkt die Prognosegenerierung auf Produkte einer bestimmten Kategorie. Ohne Auswahl werden alle Produkte verarbeitet, die in den Produkt-Plandaten eine Prognosekonfiguration haben.',
       'N', TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100
FROM AD_Language l
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Element_ID=584641);

UPDATE AD_Element_Trl SET
  Name='Product Category',
  PrintName='Product Category',
  Description='If set, only products of this category are considered. If empty, all products from Product Planning are used.',
  Help='Optional filter: Restricts forecast generation to products of a specific category. Without selection, all products with forecast configuration in Product Planning are processed.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584641 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584641, 'en_US');

-- ==========================================================================
-- 2. Dedicated element for M_Product_ID filter param
-- ==========================================================================
INSERT INTO AD_Element (AD_Client_ID, AD_Org_ID, AD_Element_ID, ColumnName, Created, CreatedBy,
                        EntityType, IsActive, Name, PrintName,
                        Description, Help,
                        Updated, UpdatedBy)
VALUES (0, 0, 584642, 'Forecast_M_Product_ID',
        TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100,
        'D', 'Y',
        'Produkt',
        'Produkt',
        'Wenn gesetzt, wird nur für dieses eine Produkt eine Prognosezeile erstellt.',
        'Optionaler Filter: Beschränkt die Prognosegenerierung auf ein einzelnes Produkt. Nützlich zum Testen oder für Nachberechnungen einzelner Artikel.',
        TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100);

INSERT INTO AD_Element_Trl (AD_Language, AD_Client_ID, AD_Org_ID, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, Created, CreatedBy, Updated, UpdatedBy)
SELECT l.AD_Language, 0, 0, 584642,
       'Produkt', 'Produkt',
       'Wenn gesetzt, wird nur für dieses eine Produkt eine Prognosezeile erstellt.',
       'Optionaler Filter: Beschränkt die Prognosegenerierung auf ein einzelnes Produkt. Nützlich zum Testen oder für Nachberechnungen einzelner Artikel.',
       'N', TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100,
       TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), 100
FROM AD_Language l
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N'
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl t WHERE t.AD_Language=l.AD_Language AND t.AD_Element_ID=584642);

UPDATE AD_Element_Trl SET
  Name='Product',
  PrintName='Product',
  Description='If set, a forecast line is created only for this single product.',
  Help='Optional filter: Restricts forecast generation to a single product. Useful for testing or recalculating individual items.',
  IsTranslated='Y',
  Updated=TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Element_ID=584642 AND AD_Language='en_US';

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584642, 'en_US');

-- ==========================================================================
-- 3. Point process params to the dedicated elements
--    Set IsCentrallyMaintained=Y so descriptions come from the element
-- ==========================================================================
UPDATE AD_Process_Para SET
  AD_Element_ID=584641,
  IsCentrallyMaintained='Y',
  Updated=TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_Para_ID=543143;

UPDATE AD_Process_Para SET
  AD_Element_ID=584642,
  IsCentrallyMaintained='Y',
  Updated=TO_TIMESTAMP('2026-03-08 23:30','YYYY-MM-DD HH24:MI'), UpdatedBy=100
WHERE AD_Process_Para_ID=543144;
