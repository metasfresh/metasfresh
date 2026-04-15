-- me03#29295: Rename misleading "HU-Aggregation" tab to "Shipment Line Attribute Config"
-- AD_Tab_ID=541052 in window 540150 (Lieferkandidaten - Handler)

-- Update base record (de_DE is the base language)
UPDATE AD_Tab SET Name = 'Lieferzeile Merkmalkonfiguration', Updated = '2026-04-15 14:00', UpdatedBy = 99
WHERE AD_Tab_ID = 541052;

-- Update translations
UPDATE AD_Tab_Trl SET Name = 'Shipment Line Attribute Config', IsTranslated = 'Y', Updated = '2026-04-15 14:00', UpdatedBy = 99
WHERE AD_Tab_ID = 541052 AND AD_Language = 'en_US';

UPDATE AD_Tab_Trl SET Name = 'Lieferzeile Merkmalkonfiguration', Updated = '2026-04-15 14:00', UpdatedBy = 99
WHERE AD_Tab_ID = 541052 AND AD_Language IN ('de_DE', 'de_CH');
