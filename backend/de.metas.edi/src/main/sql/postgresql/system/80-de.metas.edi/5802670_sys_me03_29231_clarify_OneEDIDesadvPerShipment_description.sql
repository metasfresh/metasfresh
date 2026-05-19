-- Run mode: SWING_CLIENT

-- me03#29231: Lead the field description with the EdiDESADVSendingMode='E' precondition,
-- so users don't set the flag to 'Y' for a BPartner in another sending mode and wonder why
-- nothing changes. (The DisplayLogic already hides the field unless mode='E', but the flag
-- can still be set via SQL/API and the column tooltip needs to be self-explanatory.)
-- Also adds an English translation, which the original migration left as the DE placeholder.

-- AD_Field: C_BPartner -> EDI-Konfiguration -> Ein DESADV pro Lieferung (DE base)
-- 2026-05-19T14:00:00.000Z
UPDATE AD_Field
SET Description='Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''. — Wenn aktiviert: für diesen EDI-Geschäftspartner wird ein DESADV pro Lieferung erzeugt, auch wenn die Lieferung mehrere Aufträge zusammenfasst. Die Kd-Bestellnummer wird weiterhin auf jeder DESADV-Position ausgegeben. Bei anderen Sende-Modi (z.B. ''Replikationsschnittstelle'') hat dieses Flag keine Wirkung.',
    Updated=TO_TIMESTAMP('2026-05-19 14:00:00.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Field_ID=779955
;

-- AD_Field_Trl: en_US — replace the DE placeholder with a real English translation
-- 2026-05-19T14:00:01.000Z
UPDATE AD_Field_Trl
SET Description='Only effective when EdiDESADVSendingMode=''External System''. — When enabled: this EDI BPartner gets one DESADV per shipment, even when the shipment aggregates multiple orders. The customer''s order reference (POReference / Kd-Bestellnummer) is still emitted on every DESADV line. With other sending modes (e.g. ''Replication Interface'') this flag has no effect.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-19 14:00:01.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Field_ID=779955 AND AD_Language='en_US'
;

-- AD_Field_Trl: de_CH and de_DE — sync to the updated DE base text
-- 2026-05-19T14:00:02.000Z
UPDATE AD_Field_Trl
SET Description='Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''. — Wenn aktiviert: für diesen EDI-Geschäftspartner wird ein DESADV pro Lieferung erzeugt, auch wenn die Lieferung mehrere Aufträge zusammenfasst. Die Kd-Bestellnummer wird weiterhin auf jeder DESADV-Position ausgegeben. Bei anderen Sende-Modi (z.B. ''Replikationsschnittstelle'') hat dieses Flag keine Wirkung.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-19 14:00:02.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Field_ID=779955 AND AD_Language IN ('de_CH','de_DE')
;

-- AD_Element: also set a Description/Help on the element so the column-level tooltip
-- (used outside the EDI-Konfiguration tab — e.g. column-info popovers, exports) carries
-- the same disclaimer. Element Help mirrors the field Description so the precondition
-- always leads.
-- 2026-05-19T14:00:03.000Z
UPDATE AD_Element
SET Description='Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''.',
    Help='Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''. Wenn aktiviert: ein DESADV pro Lieferung, auch bei aggregierten Aufträgen. Kd-Bestellnummer bleibt auf DESADV-Positionsebene.',
    Updated=TO_TIMESTAMP('2026-05-19 14:00:03.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Element_ID=584880
;

-- 2026-05-19T14:00:04.000Z
UPDATE AD_Element_Trl
SET Description='Only effective when EdiDESADVSendingMode=''External System''.',
    Help='Only effective when EdiDESADVSendingMode=''External System''. When enabled: one DESADV per shipment, even with multi-order aggregation. The customer''s POReference is still emitted on each DESADV line.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-19 14:00:04.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Element_ID=584880 AND AD_Language='en_US'
;

-- 2026-05-19T14:00:05.000Z
UPDATE AD_Element_Trl
SET Description='Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''.',
    Help='Nur wirksam, wenn EdiDESADVSendingMode=''Externes System''. Wenn aktiviert: ein DESADV pro Lieferung, auch bei aggregierten Aufträgen. Kd-Bestellnummer bleibt auf DESADV-Positionsebene.',
    IsTranslated='Y',
    Updated=TO_TIMESTAMP('2026-05-19 14:00:05.000000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',
    UpdatedBy=100
WHERE AD_Element_ID=584880 AND AD_Language IN ('de_CH','de_DE')
;
