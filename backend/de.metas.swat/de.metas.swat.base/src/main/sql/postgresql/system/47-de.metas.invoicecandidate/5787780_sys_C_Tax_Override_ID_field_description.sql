-- Element: C_Tax_Override_ID
-- AD_Element_ID=542590
-- Used only in C_Invoice_Candidate.C_Tax_Override_ID

-- de_DE
UPDATE AD_Element_Trl
SET Description='Übersteuert den automatisch ermittelten Steuersatz für die Rechnungsstellung.',
    Help='Ermöglicht die manuelle Übersteuerung des Steuersatzes bei der Rechnungserstellung. Hinweis: Wenn die Rechnungszeile mit einer Lieferung (Lieferschein) verknüpft ist, wird die Steuer anhand des Lieferlandes und des Lieferdatums neu ermittelt. In diesem Fall wird diese Übersteuerung ignoriert und der gesetzlich vorgeschriebene Steuersatz für das tatsächliche Lieferland angewendet. Dies ist erforderlich, um die Umsatzsteuervorschriften bei innergemeinschaftlichen Lieferungen (z.B. EU-grenzüberschreitende Lieferungen mit 0% USt) einzuhalten.',
    IsTranslated='Y',
    Updated=now(),
    UpdatedBy=100
WHERE AD_Element_ID=542590 AND AD_Language='de_DE';

/* DDL */ SELECT update_ad_element_on_ad_element_trl_update(542590, 'de_DE');
/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542590, 'de_DE');

-- de_CH
UPDATE AD_Element_Trl
SET Description='Übersteuert den automatisch ermittelten Steuersatz für die Rechnungsstellung.',
    Help='Ermöglicht die manuelle Übersteuerung des Steuersatzes bei der Rechnungserstellung. Hinweis: Wenn die Rechnungszeile mit einer Lieferung (Lieferschein) verknüpft ist, wird die Steuer anhand des Lieferlandes und des Lieferdatums neu ermittelt. In diesem Fall wird diese Übersteuerung ignoriert und der gesetzlich vorgeschriebene Steuersatz für das tatsächliche Lieferland angewendet. Dies ist erforderlich, um die Umsatzsteuervorschriften bei innergemeinschaftlichen Lieferungen (z.B. EU-grenzüberschreitende Lieferungen mit 0% USt) einzuhalten.',
    IsTranslated='Y',
    Updated=now(),
    UpdatedBy=100
WHERE AD_Element_ID=542590 AND AD_Language='de_CH';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542590, 'de_CH');

-- en_US
UPDATE AD_Element_Trl
SET Name='Tax override',
    Description='Overrides the automatically determined tax rate for invoicing.',
    Help='Allows manual override of the tax rate used when generating the invoice. Note: If the invoice line is linked to a shipment (InOut), the system will recalculate the tax based on the shipment''s destination country and date. In that case, this override is ignored and the legally required tax rate for the actual shipment destination applies. This is necessary to comply with VAT regulations for intra-community supplies (e.g. EU cross-border deliveries taxed at 0%).',
    IsTranslated='Y',
    Updated=now(),
    UpdatedBy=100
WHERE AD_Element_ID=542590 AND AD_Language='en_US';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542590, 'en_US');

-- en_GB
UPDATE AD_Element_Trl
SET Name='Tax override',
    Description='Overrides the automatically determined tax rate for invoicing.',
    Help='Allows manual override of the tax rate used when generating the invoice. Note: If the invoice line is linked to a shipment (InOut), the system will recalculate the tax based on the shipment''s destination country and date. In that case, this override is ignored and the legally required tax rate for the actual shipment destination applies. This is necessary to comply with VAT regulations for intra-community supplies (e.g. EU cross-border deliveries taxed at 0%).',
    IsTranslated='Y',
    Updated=now(),
    UpdatedBy=100
WHERE AD_Element_ID=542590 AND AD_Language='en_GB';

/* DDL */ SELECT update_TRL_Tables_On_AD_Element_TRL_Update(542590, 'en_GB');
