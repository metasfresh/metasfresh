-- F01010.4 Invoice Accounting Overrides — clarify the C_InvoiceLine_ID field help
--
-- Window 541659 (Invoice Accounting Overrides), field 710153 (C_InvoiceLine_ID), backed by the
-- dedicated element 585017 (C_InvoiceLine_ID_InvoiceAcct, created by 5808540). The existing help
-- already states "leave empty to apply to all lines"; this adds the precedence rule so the user
-- knows a line-specific override wins over an invoice-wide (empty-line) one for that line.
--
-- Element-driven (AD_Field.AD_Name_ID = 585017): update the element + its translations, then
-- re-propagate to AD_Field_Trl via update_FieldTranslation_From_AD_Name_Element (same mechanism as
-- 5808540). Today's timestamps are strictly later than the 2026-06-18 AD_Field_Trl rows, so the
-- f_trl.updated <> e_trl.updated propagation guard passes.

-- Base (German)
UPDATE AD_Element
SET    Description = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf die gesamte Rechnung (alle Positionen) anzuwenden; eine positionsspezifische Überschreibung hat für diese Position Vorrang vor einer rechnungsweiten.',
       Help        = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf die gesamte Rechnung (alle Positionen) anzuwenden; eine positionsspezifische Überschreibung hat für diese Position Vorrang vor einer rechnungsweiten.',
       Updated = TO_TIMESTAMP('2026-06-19 10:00:00', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585017;

-- de_DE / de_CH (German)
UPDATE AD_Element_Trl
SET    Description = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf die gesamte Rechnung (alle Positionen) anzuwenden; eine positionsspezifische Überschreibung hat für diese Position Vorrang vor einer rechnungsweiten.',
       Help        = 'Die Rechnungsposition, für die dieses Konto überschrieben wird. Leer lassen, um die Überschreibung auf die gesamte Rechnung (alle Positionen) anzuwenden; eine positionsspezifische Überschreibung hat für diese Position Vorrang vor einer rechnungsweiten.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-19 10:00:01', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585017 AND AD_Language IN ('de_DE', 'de_CH');

-- en_US (English)
UPDATE AD_Element_Trl
SET    Description = 'The invoice line for which this account is overridden. Leave empty to apply the override to the whole invoice (all lines); a line-specific override takes precedence over an invoice-wide one for that line.',
       Help        = 'The invoice line for which this account is overridden. Leave empty to apply the override to the whole invoice (all lines); a line-specific override takes precedence over an invoice-wide one for that line.',
       IsTranslated = 'Y',
       Updated = TO_TIMESTAMP('2026-06-19 10:00:02', 'YYYY-MM-DD HH24:MI:SS'), UpdatedBy = 100
WHERE  AD_Element_ID = 585017 AND AD_Language = 'en_US';

/* DDL */ SELECT update_FieldTranslation_From_AD_Name_Element(585017 /*From ID Server*/);
