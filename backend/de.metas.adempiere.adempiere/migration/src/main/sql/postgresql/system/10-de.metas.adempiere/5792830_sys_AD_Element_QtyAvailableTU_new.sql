-- New AD_Element for QtyAvailableTU (ID from central ID server: 584637)
-- Base language is de_DE → AD_Element base record holds German text
INSERT INTO ad_element
    (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     columnname, name, printname, description, help, entitytype)
VALUES
    (584637, 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'QtyAvailableTU',
     'Verfügbare TU Menge',
     'Verfügbare TU Menge',
     'Verfügbare TU Menge (Lagerbestand - Reserviert)',
     'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge',
     'D');

INSERT INTO ad_element_trl
    (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive,
     created, createdby, updated, updatedby,
     name, printname, description, help, istranslated)
VALUES
    -- de_DE: base language → istranslated='N' (uses base record values)
    (584637, 'de_DE', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Verfügbare TU Menge', 'Verfügbare TU Menge',
     'Verfügbare TU Menge (Lagerbestand - Reserviert)',
     'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge', 'N'),
    -- de_CH: same as de_DE, istranslated='N'
    (584637, 'de_CH', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Verfügbare TU Menge', 'Verfügbare TU Menge',
     'Verfügbare TU Menge (Lagerbestand - Reserviert)',
     'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge', 'N'),
    -- en_US: English translation, istranslated='Y'
    (584637, 'en_US', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Available TU Quantity', 'Qty Available TU',
     'Available TU Quantity (On Hand - Reserved)',
     'TU quantity available to promise = On Hand TU minus Reserved TU', 'Y'),
    -- en_GB: copy of en_US, istranslated='N'
    (584637, 'en_GB', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Available TU Quantity', 'Qty Available TU',
     'Available TU Quantity (On Hand - Reserved)',
     'TU quantity available to promise = On Hand TU minus Reserved TU', 'N'),
    -- fr_CH: untranslated, copy of base
    (584637, 'fr_CH', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Verfügbare TU Menge', 'Verfügbare TU Menge',
     'Verfügbare TU Menge (Lagerbestand - Reserviert)',
     'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge', 'N'),
    -- it_CH: untranslated, copy of base
    (584637, 'it_CH', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Verfügbare TU Menge', 'Verfügbare TU Menge',
     'Verfügbare TU Menge (Lagerbestand - Reserviert)',
     'Verfügbare TU Menge = Lagerbestand minus reservierte TU Menge', 'N');
