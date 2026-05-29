-- gh#28679: Override AD_Element for Forecast Line ASI field (AD_Field_ID=747570)
-- Adds field-specific description explaining PP_Product_Planning fallback behavior

-- New override AD_Element (no ColumnName — override elements don't have one)
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType,
                        Name, PrintName,
                        Description, Help)
VALUES (584647, 0, 0, 'Y',
        TO_TIMESTAMP('2026-03-09 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        TO_TIMESTAMP('2026-03-09 12:00', 'YYYY-MM-DD HH24:MI'), 0,
        NULL, 'D',
        'Merkmale', 'Merkmale',
        'Merkmals Ausprägungen zum Produkt. Wenn leer, wird automatisch die Merkmalsausprägung aus der Produktionsplanung (PP_Product_Planning) übernommen.',
        'Ordnet dem Prognoseeintrag eine bestimmte Merkmalsausprägung zu. Wenn dieses Feld leer bleibt, verwendet das System automatisch die Merkmalsausprägung aus dem passenden Produktionsplanungseintrag (PP_Product_Planning), sofern vorhanden. Damit wird sichergestellt, dass die Materialbedarfsplanung (Material Dispo) mit den richtigen Attributen arbeitet.');

-- AD_Element_Trl skeleton rows
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID,
                            Name, PrintName, Description, Help,
                            IsTranslated, AD_Client_ID, AD_Org_ID,
                            Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID,
       t.Name, t.PrintName, t.Description, t.Help,
       'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND t.AD_Element_ID = 584647
  AND NOT EXISTS (SELECT 1
                  FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.AD_Element_ID = t.AD_Element_ID);

-- de_DE: German (base language) — same as AD_Element base, istranslated='N'
UPDATE AD_Element_Trl
SET Name        = 'Merkmale',
    PrintName   = 'Merkmale',
    Description = 'Merkmals Ausprägungen zum Produkt. Wenn leer, wird automatisch die Merkmalsausprägung aus der Produktionsplanung (PP_Product_Planning) übernommen.',
    Help        = 'Ordnet dem Prognoseeintrag eine bestimmte Merkmalsausprägung zu. Wenn dieses Feld leer bleibt, verwendet das System automatisch die Merkmalsausprägung aus dem passenden Produktionsplanungseintrag (PP_Product_Planning), sofern vorhanden. Damit wird sichergestellt, dass die Materialbedarfsplanung (Material Dispo) mit den richtigen Attributen arbeitet.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-03-09 12:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 0
WHERE AD_Element_ID = 584647
  AND AD_Language = 'de_DE';

-- de_CH: same as de_DE
UPDATE AD_Element_Trl
SET Name        = 'Merkmale',
    PrintName   = 'Merkmale',
    Description = 'Merkmals Ausprägungen zum Produkt. Wenn leer, wird automatisch die Merkmalsausprägung aus der Produktionsplanung (PP_Product_Planning) übernommen.',
    Help        = 'Ordnet dem Prognoseeintrag eine bestimmte Merkmalsausprägung zu. Wenn dieses Feld leer bleibt, verwendet das System automatisch die Merkmalsausprägung aus dem passenden Produktionsplanungseintrag (PP_Product_Planning), sofern vorhanden. Damit wird sichergestellt, dass die Materialbedarfsplanung (Material Dispo) mit den richtigen Attributen arbeitet.',
    IsTranslated = 'N',
    Updated     = TO_TIMESTAMP('2026-03-09 12:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 0
WHERE AD_Element_ID = 584647
  AND AD_Language = 'de_CH';

-- en_US: English translation
UPDATE AD_Element_Trl
SET Name        = 'Attributes',
    PrintName   = 'Attributes',
    Description = 'Attribute set instance for this forecast line. When left empty, the system automatically uses the ASI from the matching Product Planning (PP_Product_Planning) record.',
    Help        = 'Assigns a specific attribute set instance to this forecast entry. When left empty, the system automatically falls back to the ASI from the best-fitting Product Planning record (PP_Product_Planning), if one exists. This ensures that Material Disposition (MD_Candidate) operates with the correct storage attributes key.',
    IsTranslated = 'Y',
    Updated     = TO_TIMESTAMP('2026-03-09 12:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 0
WHERE AD_Element_ID = 584647
  AND AD_Language = 'en_US';

-- Link AD_Field 747570 (Forecast Line ASI field) to the new override element
UPDATE AD_Field
SET AD_Name_ID  = 584647,
    Updated     = TO_TIMESTAMP('2026-03-09 12:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy   = 0
WHERE AD_Field_ID = 747570;

-- Propagate translations from the override element to the field
SELECT update_FieldTranslation_From_AD_Name_Element(584647);
