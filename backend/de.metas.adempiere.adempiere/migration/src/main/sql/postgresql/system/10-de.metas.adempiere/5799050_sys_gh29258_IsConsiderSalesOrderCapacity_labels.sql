-- me03#29258: AD_Element 583235 / IsConsiderSalesOrderCapacity had a label
-- ("CU-TU Menge aus Auftrag verwenden") and a description ("Wenn nicht
-- aktiviert wird die Mengenzuordnung bei TU Catch Weight aus den Stammdaten
-- des Produkts genommen") that do not describe what the flag does in code.
--
-- Actual behavior (PickingJobPickCommand.computeQtyToPickCUs): when ON, CUs
-- taken from a scanned TU are capped to the remaining to-pick qty (partial
-- TU possible). When OFF, always picks the full TU capacity. The flag
-- operates on the CU side only; it does NOT cap the TU count itself.

-- AD_Element (base / de_DE)
UPDATE AD_Element
   SET Name         = 'TU-Inhalt auf Restm. kappen',
       PrintName    = 'TU-Inhalt auf Restm. kappen',
       Description  = 'Wenn aktiviert, wird beim TU-Scan die CU-Menge auf die noch offene Auftragsmenge gekappt (eine TU kann damit teilweise entnommen werden). Wenn deaktiviert, wird immer die volle TU-Kapazität entnommen, auch wenn das die Auftragsmenge übersteigt.',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583235;

-- AD_Element_Trl: de_DE, de_CH
UPDATE AD_Element_Trl
   SET Name         = 'TU-Inhalt auf Restm. kappen',
       PrintName    = 'TU-Inhalt auf Restm. kappen',
       Description  = 'Wenn aktiviert, wird beim TU-Scan die CU-Menge auf die noch offene Auftragsmenge gekappt (eine TU kann damit teilweise entnommen werden). Wenn deaktiviert, wird immer die volle TU-Kapazität entnommen, auch wenn das die Auftragsmenge übersteigt.',
       IsTranslated = 'N',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583235
   AND AD_Language IN ('de_DE', 'de_CH');

-- AD_Element_Trl: en_US
UPDATE AD_Element_Trl
   SET Name         = 'Cap TU content to remaining qty',
       PrintName    = 'Cap TU content to remaining qty',
       Description  = 'When enabled, the CU quantity taken from a scanned TU is capped to the remaining order qty (a TU may be picked partially). When disabled, the full TU capacity is always picked, even if that exceeds the order qty.',
       IsTranslated = 'Y',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583235
   AND AD_Language = 'en_US';

-- AD_Element_Trl: fr_CH — keep placeholder consistent with the German base
UPDATE AD_Element_Trl
   SET Name         = 'TU-Inhalt auf Restm. kappen',
       PrintName    = 'TU-Inhalt auf Restm. kappen',
       Description  = 'Wenn aktiviert, wird beim TU-Scan die CU-Menge auf die noch offene Auftragsmenge gekappt (eine TU kann damit teilweise entnommen werden). Wenn deaktiviert, wird immer die volle TU-Kapazität entnommen, auch wenn das die Auftragsmenge übersteigt.',
       IsTranslated = 'N',
       Updated      = now(),
       UpdatedBy    = 100
 WHERE AD_Element_ID = 583235
   AND AD_Language = 'fr_CH';

-- AD_Column: MobileUI_UserProfile_Picking.IsConsiderSalesOrderCapacity (id 588937)
-- AD_Column: MobileUI_UserProfile_Picking_Job.IsConsiderSalesOrderCapacity (id 589658)
UPDATE AD_Column
   SET Name        = 'TU-Inhalt auf Restm. kappen',
       Description = 'Wenn aktiviert, wird beim TU-Scan die CU-Menge auf die noch offene Auftragsmenge gekappt (eine TU kann damit teilweise entnommen werden). Wenn deaktiviert, wird immer die volle TU-Kapazität entnommen, auch wenn das die Auftragsmenge übersteigt.',
       Updated     = now(),
       UpdatedBy   = 100
 WHERE AD_Column_ID IN (588937, 589658);
