-- Kosten Neubewertung, tab "Line" (546465): explain when the line values are calculated.
--
-- Per-field overrides (AD_Field.AD_Name_ID), because the column elements are shared and must not change:
--   field 702162 CurrentCostPrice (element 1394, used by 10 columns)  -> new element 585500
--   field 702163 CurrentQty       (element 2842, used by 3 columns)   -> new element 585501
--   field 705347 DeltaAmt         (element 2840, used by 3 columns)   -> new element 585502 (German caption "Differenzbetrag" instead of "Delta Amount")
--   field 702164 NewCostPrice     (element 581163, also used by M_CostRevaluation_Detail.NewCostPrice) -> new element 585503
-- de_CH = de_DE; fr_CH carries the German text, too.
--
-- Also: the zero-stock hint (AD_Message 545864) gets its amended text, and the two processes of the window get German names:
--   585097 "Run Revaluation"                       -> "Neubewertung ausführen"
--   585085 "Erstellen Sie Neubewertungspositionen" -> "Neubewertungspositionen erstellen"
-- Neither process has an AD_Menu entry, so only AD_Process + AD_Process_Trl change.

-- ---------------------------------------------------------------------------------------------------------------------
-- 1. The four name elements (ColumnName NULL: they only back the field overrides)
-- ---------------------------------------------------------------------------------------------------------------------

INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585500 /*From ID Server*/, 0, NULL, TO_TIMESTAMP('2026-10-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
        'D',
        'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
        'Y', 'Kostenpreis aktuell', 'Kostenpreis aktuell', TO_TIMESTAMP('2026-10-01 14:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585501 /*From ID Server*/, 0, NULL, TO_TIMESTAMP('2026-10-01 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
        'D',
        'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
        'Y', 'Menge aktuell', 'Menge aktuell', TO_TIMESTAMP('2026-10-01 14:00:01', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585502 /*From ID Server*/, 0, NULL, TO_TIMESTAMP('2026-10-01 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
        'D',
        'Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
        'Y', 'Differenzbetrag', 'Differenzbetrag', TO_TIMESTAMP('2026-10-01 14:00:02', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

INSERT INTO AD_Element (AD_Client_ID, AD_Element_ID, AD_Org_ID, ColumnName, Created, CreatedBy, Description, EntityType, Help, IsActive, Name, PrintName, Updated, UpdatedBy)
VALUES (0, 585503 /*From ID Server*/, 0, NULL, TO_TIMESTAMP('2026-10-01 14:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100,
        'Preis je Kosten-Maßeinheit des Produkts.',
        'D',
        'Preis je Kosten-Maßeinheit des Produkts.',
        'Y', 'Neuer Einstandspreis', 'Neuer Einstandspreis', TO_TIMESTAMP('2026-10-01 14:00:03', 'YYYY-MM-DD HH24:MI:SS'), 100)
;

-- skeleton translations (all system languages incl. the base language), German text everywhere
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, CommitWarning, Description, Help, Name, PO_Description, PO_Help, PO_Name, PO_PrintName, PrintName, WEBUI_NameBrowse, WEBUI_NameNew, WEBUI_NameNewBreadcrumb,
                            IsTranslated, AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Element_ID, t.CommitWarning, t.Description, t.Help, t.Name, t.PO_Description, t.PO_Help, t.PO_Name, t.PO_PrintName, t.PrintName, t.WEBUI_NameBrowse, t.WEBUI_NameNew, t.WEBUI_NameNewBreadcrumb,
       'N', t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l,
     AD_Element t
WHERE l.IsActive = 'Y'
  AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID IN (585500, 585501, 585502, 585503)
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- German (de_DE, de_CH) is final
UPDATE AD_Element_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 14:00:12', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID IN (585500, 585501, 585502, 585503) AND AD_Language IN ('de_DE', 'de_CH')
;

-- English
UPDATE AD_Element_Trl
SET Name='Current Cost Price', PrintName='Current Cost Price',
    Description='Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    Help='Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 14:00:13', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585500 AND AD_Language = 'en_US'
;

UPDATE AD_Element_Trl
SET Name='Current Quantity', PrintName='Current Quantity',
    Description='Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    Help='Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 14:00:14', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585501 AND AD_Language = 'en_US'
;

UPDATE AD_Element_Trl
SET Name='Delta Amount', PrintName='Delta Amount',
    Description='Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    Help='Values as of line creation or the last "Run Revaluation"; recalculated at Complete; booked values after posting.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 14:00:15', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585502 AND AD_Language = 'en_US'
;

UPDATE AD_Element_Trl
SET Name='New Cost Price', PrintName='New Cost Price',
    Description='Price per costing unit of measure of the product.',
    Help='Price per costing unit of measure of the product.',
    IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 14:00:16', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Element_ID = 585503 AND AD_Language = 'en_US'
;

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585500);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585501);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585502);
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585503);

-- ---------------------------------------------------------------------------------------------------------------------
-- 2. Point the four line fields at their name elements (base-language text set directly: AD_Field.Help is not propagated for the base language)
-- ---------------------------------------------------------------------------------------------------------------------

UPDATE AD_Field
SET AD_Name_ID=585500, Name='Kostenpreis aktuell',
    Description='Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    Help='Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    Updated=TO_TIMESTAMP('2026-10-01 14:02:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 702162
;

UPDATE AD_Field
SET AD_Name_ID=585501, Name='Menge aktuell',
    Description='Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    Help='Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    Updated=TO_TIMESTAMP('2026-10-01 14:02:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 702163
;

UPDATE AD_Field
SET AD_Name_ID=585502, Name='Differenzbetrag',
    Description='Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    Help='Werte zum Zeitpunkt der Zeilenerstellung oder des letzten ‚Neubewertung ausführen‘; beim Fertigstellen neu berechnet; nach dem Buchen die gebuchten Werte.',
    Updated=TO_TIMESTAMP('2026-10-01 14:02:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 705347
;

UPDATE AD_Field
SET AD_Name_ID=585503, Name='Neuer Einstandspreis',
    Description='Preis je Kosten-Maßeinheit des Produkts.',
    Help='Preis je Kosten-Maßeinheit des Produkts.',
    Updated=TO_TIMESTAMP('2026-10-01 14:02:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Field_ID = 702164
;

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585500);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585501);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585502);
/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585503);

DELETE FROM AD_Element_Link WHERE AD_Field_ID IN (702162, 702163, 705347, 702164);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(702162);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(702163);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(705347);
/* DDL */ SELECT AD_Element_Link_Create_Missing_Field(702164);

-- ---------------------------------------------------------------------------------------------------------------------
-- 3. AD_Message 545864 (M_CostRevaluationLine_ZeroStockCostProvisional): amended zero-stock hint
-- ---------------------------------------------------------------------------------------------------------------------

UPDATE AD_Message
SET MsgText='Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Wird vor dem Buchen dieser Kostenneubewertung Ware eingebucht, bucht sie Bestand × (neu − aktuell); danach berechnet jeder Wareneingang den gleitenden Durchschnittspreis neu.',
    Updated=TO_TIMESTAMP('2026-10-01 14:03:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545864
;

UPDATE AD_Message_Trl
SET MsgText='Bei einem Produkt ohne Lagerbestand ist der eingegebene Einstandspreis vorläufig: Wird vor dem Buchen dieser Kostenneubewertung Ware eingebucht, bucht sie Bestand × (neu − aktuell); danach berechnet jeder Wareneingang den gleitenden Durchschnittspreis neu.',
    Updated=TO_TIMESTAMP('2026-10-01 14:03:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545864 AND AD_Language <> 'en_US'
;

UPDATE AD_Message_Trl
SET MsgText='For a product with no stock the entered cost price is provisional: if goods are received before this cost revaluation is posted, it books stock × (new − current); after that every goods receipt re-derives the moving-average price.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-10-01 14:03:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Message_ID = 545864 AND AD_Language = 'en_US'
;

-- ---------------------------------------------------------------------------------------------------------------------
-- 4. German process names (German in every language except en_US, IsTranslated only for de_*; en_US unchanged), then the base row from the base-language translation
-- ---------------------------------------------------------------------------------------------------------------------

UPDATE AD_Process_Trl SET Name='Neubewertung ausführen', IsTranslated=CASE WHEN AD_Language LIKE 'de\_%' THEN 'Y' ELSE IsTranslated END,
                          Updated=TO_TIMESTAMP('2026-10-01 14:04:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 585097 AND AD_Language <> 'en_US'
;

UPDATE AD_Process_Trl SET IsTranslated='Y', Updated=TO_TIMESTAMP('2026-10-01 14:04:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 585097 AND AD_Language = 'en_US' -- stays "Run Revaluation"
;

UPDATE AD_Process_Trl SET Name='Neubewertungspositionen erstellen', IsTranslated=CASE WHEN AD_Language LIKE 'de\_%' THEN 'Y' ELSE IsTranslated END,
                          Updated=TO_TIMESTAMP('2026-10-01 14:04:03', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
WHERE AD_Process_ID = 585085 AND AD_Language <> 'en_US'
;

UPDATE AD_Process base
SET Name=trl.Name, Updated=TO_TIMESTAMP('2026-10-01 14:04:05', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100
FROM AD_Process_Trl trl
WHERE trl.AD_Process_ID = base.AD_Process_ID
  AND trl.AD_Language = getBaseLanguage()
  AND base.AD_Process_ID IN (585085, 585097)
;
