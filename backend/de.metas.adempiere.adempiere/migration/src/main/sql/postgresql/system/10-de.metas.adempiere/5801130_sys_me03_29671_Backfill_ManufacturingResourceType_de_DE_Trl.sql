-- me03#29671 follow-up to 5801120: AD_Ref_List values PL, PT, WC, WS for
-- AD_Reference 53223 (ManufacturingResourceType) had no de_DE translation row,
-- so the de_DE description UPDATEs in 5801120 were silent no-ops. ES already
-- had a de_DE row, so its description landed.
--
-- Fix: call add_missing_translations() to backfill any missing _Trl rows across
-- the database, then re-apply the de_DE description UPDATEs for the four codes.

SELECT add_missing_translations();

-- PL (AD_Ref_List 53244) -- Produktionslinie
UPDATE AD_Ref_List_Trl
SET Description='Produktionslinie: Produktionslinie innerhalb eines Arbeitsbereichs.',
    IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53244 AND AD_Language='de_DE';

-- PT (AD_Ref_List 53245) -- Produktionsstätte
UPDATE AD_Ref_List_Trl
SET Description='Produktionsstätte: Standort der Fertigung. PP_Order.S_Resource_ID verweist auf eine Produktionsstätte.',
    IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53245 AND AD_Language='de_DE';

-- WC (AD_Ref_List 53246) -- Arbeitsbereich
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsbereich: Aggregat oder Abteilung innerhalb einer Produktionsstätte. Fasst mehrere Arbeitsstationen zu einer logischen Einheit zusammen.',
    IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53246 AND AD_Language='de_DE';

-- WS (AD_Ref_List 53247) -- Arbeitsstation
UPDATE AD_Ref_List_Trl
SET Description='Arbeitsstation: Physisches scanbares Gerät innerhalb eines Arbeitsplatzes (Drucker, Waage, Maschine). Bediener identifizieren sich durch Scannen des Arbeitsstation-QR-Codes.',
    IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='de_DE';

-- Also re-apply Names that the previous Phase-1 migration set on AD_Ref_List
-- (EN base) so the newly-created de_DE rows carry the right base Name too.
-- (For consistency with 5801060_sys_me03_29671_ManufacturingResourceType_Labels.sql.)
UPDATE AD_Ref_List_Trl SET Name='Produktionslinie',  IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53244 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Name='Produktionsstätte', IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53245 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Name='Arbeitsbereich',    IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53246 AND AD_Language='de_DE';
UPDATE AD_Ref_List_Trl SET Name='Arbeitsstation',    IsTranslated='Y', Updated='2026-05-06 17:30', UpdatedBy=100
WHERE AD_Ref_List_ID=53247 AND AD_Language='de_DE';
