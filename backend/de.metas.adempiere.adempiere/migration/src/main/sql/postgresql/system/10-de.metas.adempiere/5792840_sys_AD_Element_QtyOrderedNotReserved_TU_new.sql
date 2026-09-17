-- New AD_Element for QtyOrderedNotReserved_TU (ID from central ID server: 584638)
-- Base language is de_DE → AD_Element base record holds German text
INSERT INTO ad_element
    (ad_element_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
     columnname, name, printname, description, help, entitytype)
VALUES
    (584638, 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'QtyOrderedNotReserved_TU',
     'Bestellt, nicht reserviert (TU)',
     'Bestellt, nicht reserv. (TU)',
     'Bestellte TU-Menge ohne Reservierung',
     'Bestellte TU-Menge abzüglich bereits reservierter TU-Menge',
     'D');

INSERT INTO ad_element_trl
    (ad_element_id, ad_language, ad_client_id, ad_org_id, isactive,
     created, createdby, updated, updatedby,
     name, printname, description, help, istranslated)
VALUES
    -- de_DE: base language, istranslated='N'
    (584638, 'de_DE', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Bestellt, nicht reserviert (TU)', 'Bestellt, nicht reserv. (TU)',
     'Bestellte TU-Menge ohne Reservierung',
     'Bestellte TU-Menge abzüglich bereits reservierter TU-Menge', 'N'),
    -- de_CH: same as de_DE, istranslated='N'
    (584638, 'de_CH', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Bestellt, nicht reserviert (TU)', 'Bestellt, nicht reserv. (TU)',
     'Bestellte TU-Menge ohne Reservierung',
     'Bestellte TU-Menge abzüglich bereits reservierter TU-Menge', 'N'),
    -- en_US: English translation, istranslated='Y'
    (584638, 'en_US', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Ordered, not reserved (TU)', 'Ordered not reserved (TU)',
     'Ordered TU quantity without reservation',
     'Ordered TU quantity minus already reserved TU quantity', 'Y'),
    -- en_GB: copy of en_US, istranslated='N'
    (584638, 'en_GB', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Ordered, not reserved (TU)', 'Ordered not reserved (TU)',
     'Ordered TU quantity without reservation',
     'Ordered TU quantity minus already reserved TU quantity', 'N'),
    -- fr_CH: untranslated, copy of base
    (584638, 'fr_CH', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Bestellt, nicht reserviert (TU)', 'Bestellt, nicht reserv. (TU)',
     'Bestellte TU-Menge ohne Reservierung',
     'Bestellte TU-Menge abzüglich bereits reservierter TU-Menge', 'N'),
    -- it_CH: untranslated, copy of base
    (584638, 'it_CH', 0, 0, 'Y', '2026-03-07 10:00', 0, '2026-03-07 10:00', 0,
     'Bestellt, nicht reserviert (TU)', 'Bestellt, nicht reserv. (TU)',
     'Bestellte TU-Menge ohne Reservierung',
     'Bestellte TU-Menge abzüglich bereits reservierter TU-Menge', 'N');
