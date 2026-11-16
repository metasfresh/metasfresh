-- Element 582372: IsReverseCharge
-- Base record (de_DE): "Reverse Charge" is the standard German term (§13b UStG), keep as-is
-- Description + Help: translate to German
UPDATE AD_Element
SET Description = 'Beim Bezug von Waren oder Dienstleistungen aus anderen EU-Ländern wird die Steuerschuldnerschaft auf den Leistungsempfänger übertragen (§13b UStG).',
    Help = 'Beim Bezug von Waren oder Dienstleistungen aus anderen EU-Ländern wird die Steuerschuldnerschaft auf den Leistungsempfänger übertragen (§13b UStG). Beide Steuerbeträge (Vorsteuer und Umsatzsteuer) werden in der Buchung erfasst und saldieren sich auf null.',
    Updated = '2026-04-17 13:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 582372;

-- en_US translation
UPDATE AD_Element_Trl
SET Name = 'Reverse Charge',
    PrintName = 'Reverse Charge',
    Description = 'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer.',
    Help = 'When you buy goods or services from suppliers in other EU countries, the Reverse Charge moves the responsibility for the recording of a VAT transaction from the seller to the buyer for that good or service. Both tax amounts (input and output VAT) are recorded in the posting and net to zero.',
    IsTranslated = 'Y',
    Updated = '2026-04-17 13:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 582372 AND AD_Language = 'en_US';

-- de_DE: same as base, mark as not translated (base language)
UPDATE AD_Element_Trl
SET Description = 'Beim Bezug von Waren oder Dienstleistungen aus anderen EU-Ländern wird die Steuerschuldnerschaft auf den Leistungsempfänger übertragen (§13b UStG).',
    Help = 'Beim Bezug von Waren oder Dienstleistungen aus anderen EU-Ländern wird die Steuerschuldnerschaft auf den Leistungsempfänger übertragen (§13b UStG). Beide Steuerbeträge (Vorsteuer und Umsatzsteuer) werden in der Buchung erfasst und saldieren sich auf null.',
    IsTranslated = 'N',
    Updated = '2026-04-17 13:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 582372 AND AD_Language IN ('de_DE', 'de_CH');


-- Element 582373: ReverseChargeTaxAmt
-- Base record (de_DE): translate to German
UPDATE AD_Element
SET Name = 'Reverse-Charge-Steuerbetrag',
    PrintName = 'RC Steuerbetrag',
    Description = 'Steuerbetrag für die Reverse-Charge-Buchung (§13b UStG). Wird nicht dem Rechnungsbetrag hinzugerechnet.',
    Help = 'Der Reverse-Charge-Steuerbetrag wird bei der Rechnungsverbuchung als zwei sich aufhebende Buchungszeilen (Vorsteuer und Umsatzsteuer) erfasst. Der Betrag dient der korrekten Umsatzsteuer-Voranmeldung.',
    Updated = '2026-04-17 13:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 582373;

-- en_US translation
UPDATE AD_Element_Trl
SET Name = 'Reverse Charge Tax Amount',
    PrintName = 'RC Tax Amount',
    Description = 'Tax amount for the reverse charge posting. Not added to the invoice payable amount.',
    Help = 'The reverse charge tax amount is posted as two offsetting lines (input VAT and output VAT) during invoice accounting. The amount is used for the correct VAT declaration.',
    IsTranslated = 'Y',
    Updated = '2026-04-17 13:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 582373 AND AD_Language = 'en_US';

-- de_DE / de_CH: same as base
UPDATE AD_Element_Trl
SET Name = 'Reverse-Charge-Steuerbetrag',
    PrintName = 'RC Steuerbetrag',
    Description = 'Steuerbetrag für die Reverse-Charge-Buchung (§13b UStG). Wird nicht dem Rechnungsbetrag hinzugerechnet.',
    Help = 'Der Reverse-Charge-Steuerbetrag wird bei der Rechnungsverbuchung als zwei sich aufhebende Buchungszeilen (Vorsteuer und Umsatzsteuer) erfasst. Der Betrag dient der korrekten Umsatzsteuer-Voranmeldung.',
    IsTranslated = 'N',
    Updated = '2026-04-17 13:30',
    UpdatedBy = 0
WHERE AD_Element_ID = 582373 AND AD_Language IN ('de_DE', 'de_CH');


-- Propagate element translations to AD_Column, AD_Field, AD_Process_Para etc.
SELECT update_ad_element_on_ad_element_trl_update(582372, 'de_DE');
SELECT update_ad_element_on_ad_element_trl_update(582372, 'de_CH');
SELECT update_ad_element_on_ad_element_trl_update(582372, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582372, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582372, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582372, 'en_US');

SELECT update_ad_element_on_ad_element_trl_update(582373, 'de_DE');
SELECT update_ad_element_on_ad_element_trl_update(582373, 'de_CH');
SELECT update_ad_element_on_ad_element_trl_update(582373, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582373, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582373, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(582373, 'en_US');
