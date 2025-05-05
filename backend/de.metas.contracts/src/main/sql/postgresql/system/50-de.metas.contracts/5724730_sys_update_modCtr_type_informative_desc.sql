
UPDATE modCntr_type
SET description = 'Dieser Vertragsbausteintyp erzeugt informative Logs für abgeschlossene Bestellzeilen, die erstellten Vertragsbaustein-Verträge, Schlussrechnungen und Definitive Schlussrechnungen. Für sie wird kein Rechnungskandidat erstellt.',
    updated = TO_TIMESTAMP('2024-05-28 15:29:47','YYYY-MM-DD HH24:MI:SS'),
    updatedby = 99
WHERE modcntr_type_id = 540008
;

