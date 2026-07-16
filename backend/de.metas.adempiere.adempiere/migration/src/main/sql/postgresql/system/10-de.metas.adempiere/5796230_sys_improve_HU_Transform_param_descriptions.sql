-- Migration: Improve descriptions for WEBUI_M_HU_Transform process parameters
-- so users understand why the M_HU_PI_Item_Product dropdown may be empty.
--
-- Root cause: The dropdown is filtered by the HU's BPartner AND Product.
-- If the HU has no BPartner, only generic (BPartner=NULL) packing instructions are shown.
-- If none exist, the dropdown is empty.

-- Update base record (base language is de_CH)
UPDATE AD_Process_Para
SET Description = 'Packvorschrift für die Ziel-TU. Die Auswahl wird nach dem Produkt und dem Geschäftspartner der HU gefiltert.',
    Help = 'Zeigt nur Packvorschriften (M_HU_PI_Item_Product), die zum Produkt der ausgewählten HU passen. '
           || 'Wenn die HU einem Geschäftspartner zugeordnet ist, werden sowohl partnerspezifische als auch allgemeine Packvorschriften angezeigt. '
           || 'Wenn die HU keinen Geschäftspartner hat, werden nur allgemeine Packvorschriften (ohne Geschäftspartner) angezeigt. '
           || 'Falls die Liste leer ist: Prüfen Sie, ob eine allgemeine Packvorschrift (C_BPartner_ID = NULL) für dieses Produkt existiert, oder weisen Sie der HU einen Geschäftspartner zu.',
    Updated = '2026-03-27 17:00',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 541153;

-- de_CH translation (base language)
UPDATE AD_Process_Para_Trl
SET Description = 'Packvorschrift für die Ziel-TU. Die Auswahl wird nach dem Produkt und dem Geschäftspartner der HU gefiltert.',
    Help = 'Zeigt nur Packvorschriften (M_HU_PI_Item_Product), die zum Produkt der ausgewählten HU passen. '
           || 'Wenn die HU einem Geschäftspartner zugeordnet ist, werden sowohl partnerspezifische als auch allgemeine Packvorschriften angezeigt. '
           || 'Wenn die HU keinen Geschäftspartner hat, werden nur allgemeine Packvorschriften (ohne Geschäftspartner) angezeigt. '
           || 'Falls die Liste leer ist: Prüfen Sie, ob eine allgemeine Packvorschrift (C_BPartner_ID = NULL) für dieses Produkt existiert, oder weisen Sie der HU einen Geschäftspartner zu.',
    Updated = '2026-03-27 17:00',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 541153 AND AD_Language = 'de_CH';

-- de_DE translation
UPDATE AD_Process_Para_Trl
SET Description = 'Packvorschrift für die Ziel-TU. Die Auswahl wird nach dem Produkt und dem Geschäftspartner der HU gefiltert.',
    Help = 'Zeigt nur Packvorschriften (M_HU_PI_Item_Product), die zum Produkt der ausgewählten HU passen. '
           || 'Wenn die HU einem Geschäftspartner zugeordnet ist, werden sowohl partnerspezifische als auch allgemeine Packvorschriften angezeigt. '
           || 'Wenn die HU keinen Geschäftspartner hat, werden nur allgemeine Packvorschriften (ohne Geschäftspartner) angezeigt. '
           || 'Falls die Liste leer ist: Prüfen Sie, ob eine allgemeine Packvorschrift (C_BPartner_ID = NULL) für dieses Produkt existiert, oder weisen Sie der HU einen Geschäftspartner zu.',
    Updated = '2026-03-27 17:00',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 541153 AND AD_Language = 'de_DE';

-- en_US translation
UPDATE AD_Process_Para_Trl
SET Description = 'Packing instruction for the target TU. The selection is filtered by the HU''s product and business partner.',
    Help = 'Shows only packing instructions (M_HU_PI_Item_Product) matching the selected HU''s product. '
           || 'If the HU is assigned to a business partner, both partner-specific and generic packing instructions are shown. '
           || 'If the HU has no business partner, only generic packing instructions (with C_BPartner_ID = NULL) are shown. '
           || 'If the list is empty: Check whether a generic packing instruction (C_BPartner_ID = NULL) exists for this product, or assign a business partner to the HU.',
    IsTranslated = 'Y',
    Updated = '2026-03-27 17:00',
    UpdatedBy = 0
WHERE AD_Process_Para_ID = 541153 AND AD_Language = 'en_US';
