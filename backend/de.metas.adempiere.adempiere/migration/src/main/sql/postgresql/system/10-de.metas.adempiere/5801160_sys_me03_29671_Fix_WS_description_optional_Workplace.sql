-- me03#29671 follow-up: the WS (Workstation) AD_Ref_List description previously
-- said "innerhalb eines Arbeitsplatzes" / "within a Workplace", implying that a
-- Workstation must always live inside a Workplace. That is incorrect: the
-- S_Resource.C_Workplace_ID link is optional. A Workstation may stand alone or
-- be linked to at most one Workplace at a time.

-- AD_Ref_List 53247 (WS) — base (EN) description
UPDATE AD_Ref_List
SET Description='Workstation: Physical scannable device (printer, scale, machine) at which an operator performs manufacturing work. Optionally assigned to a Workplace via C_Workplace_ID. Operators identify themselves by scanning the Workstation''s QR code.',
    Updated='2026-05-06 18:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247;

-- AD_Ref_List_Trl en_US
UPDATE AD_Ref_List_Trl
SET Description='Workstation: Physical scannable device (printer, scale, machine) at which an operator performs manufacturing work. Optionally assigned to a Workplace via C_Workplace_ID. Operators identify themselves by scanning the Workstation''s QR code.',
    IsTranslated='Y', Updated='2026-05-06 18:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='en_US';

-- AD_Ref_List_Trl de_DE
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsstation: Physisches scanbares Gerät (Drucker, Waage, Maschine), an dem ein Bediener Produktionsarbeit verrichtet. Optional einem Arbeitsplatz zugeordnet (C_Workplace_ID). Bediener identifizieren sich durch Scannen des Arbeitsstation-QR-Codes.',
    IsTranslated='Y', Updated='2026-05-06 18:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='de_DE';

-- AD_Ref_List_Trl de_CH
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsstation: Physisches scanbares Gerät (Drucker, Waage, Maschine), an dem ein Bediener Produktionsarbeit verrichtet. Optional einem Arbeitsplatz zugeordnet (C_Workplace_ID). Bediener identifizieren sich durch Scannen des Arbeitsstation-QR-Codes.',
    IsTranslated='Y', Updated='2026-05-06 18:00', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='de_CH';
