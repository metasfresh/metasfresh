-- Translate the picking-profile / picking-job-options labels that are still
-- shown in English on the "Mobile UI Kommissionierprofil" window (tab
-- "Feld" = PickingProfile_PickingJobConfig) and the "Mobile UI
-- Kommissionieraufgabe Optionen" window. System base language is de_DE, so
-- AD_Element.Name is the German label; these 10 elements still held their
-- untranslated English original in the base column. en_US keeps the
-- original English text as an explicit translation override. de_CH mirrors
-- de_DE. fr_CH is intentionally left untouched (falls back to the new
-- German base) -- no fr_CH translation done here.
--
-- FormatPattern (53687) is a shared element (5 AD_Column usages across
-- other tables/windows too) -- translating it also fixes the label on
-- those other windows, which is the correct root-cause fix for a shared
-- element. Its Description was also still English and is corrected here
-- too, since it is shown as the field's tooltip.

-- === 583513 PickingJobAggregationType : "Aggregation Type" -> "Aggregationstyp" ===
UPDATE AD_Element SET Name='Aggregationstyp', Updated=TO_TIMESTAMP('2026-08-20 09:30:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513
;
UPDATE AD_Element_Trl SET Name='Aggregationstyp', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Aggregationstyp', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Aggregation Type', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583513)
;

-- === 584219 IsAllowQuickPackAll : "Allow Quick Pack All" -> "Schnellverpackung für alle erlauben" ===
UPDATE AD_Element SET Name='Schnellverpackung für alle erlauben', Updated=TO_TIMESTAMP('2026-08-20 09:30:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219
;
UPDATE AD_Element_Trl SET Name='Schnellverpackung für alle erlauben', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Schnellverpackung für alle erlauben', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Allow Quick Pack All', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584219)
;

-- === 577441 IsAnonymousHuPickedOnTheFly : "Anonymous HU Picked On the Fly" -> "Anonyme HU im Lauf kommissioniert" ===
UPDATE AD_Element SET Name='Anonyme HU im Lauf kommissioniert', Updated=TO_TIMESTAMP('2026-08-20 09:30:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441
;
UPDATE AD_Element_Trl SET Name='Anonyme HU im Lauf kommissioniert', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Anonyme HU im Lauf kommissioniert', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Anonymous HU Picked On the Fly', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577441)
;

-- === 583887 IsConsideredOnlyScheduledJobs : "Consider only scheduled jobs" -> "Nur eingeplante Kommissionieraufgaben berücksichtigen" ===
UPDATE AD_Element SET Name='Nur eingeplante Kommissionieraufgaben berücksichtigen', Updated=TO_TIMESTAMP('2026-08-20 09:30:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887
;
UPDATE AD_Element_Trl SET Name='Nur eingeplante Kommissionieraufgaben berücksichtigen', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Nur eingeplante Kommissionieraufgaben berücksichtigen', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Consider only scheduled jobs', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583887)
;

-- === 583565 IsFilterByBarcode : "Filter by Barcode" -> "Nach Barcode filtern" ===
UPDATE AD_Element SET Name='Nach Barcode filtern', Updated=TO_TIMESTAMP('2026-08-20 09:30:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565
;
UPDATE AD_Element_Trl SET Name='Nach Barcode filtern', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Nach Barcode filtern', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Filter by Barcode', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:19','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583565)
;

-- === 53687 FormatPattern : "Format Pattern" -> "Format-Muster" (Name + Description; shared element, see header) ===
UPDATE AD_Element SET Name='Format-Muster', Description='Das Muster, das zur Formatierung einer Zahl oder eines Datums verwendet wird.', Updated=TO_TIMESTAMP('2026-08-20 09:30:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687
;
UPDATE AD_Element_Trl SET Name='Format-Muster', Description='Das Muster, das zur Formatierung einer Zahl oder eines Datums verwendet wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Format-Muster', Description='Das Muster, das zur Formatierung einer Zahl oder eines Datums verwendet wird.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Format Pattern', Description='The pattern used to format a number or date.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53687)
;

-- === 583942 AllowPickToStructure_LU_TU : "Pick to LU/TU structure" -> "Kommissionierung zu LU/TU-Struktur" ===
UPDATE AD_Element SET Name='Kommissionierung zu LU/TU-Struktur', Updated=TO_TIMESTAMP('2026-08-20 09:30:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zu LU/TU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zu LU/TU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to LU/TU structure', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583942)
;

-- === 583943 AllowPickToStructure_LU_CU : "Pick to LU/CU structure" -> "Kommissionierung zu LU/CU-Struktur" ===
UPDATE AD_Element SET Name='Kommissionierung zu LU/CU-Struktur', Updated=TO_TIMESTAMP('2026-08-20 09:30:28','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zu LU/CU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:29','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zu LU/CU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to LU/CU structure', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583943)
;

-- === 583944 AllowPickToStructure_TU : "Pick to top level TU structure" -> "Kommissionierung zur obersten TU-Struktur" ===
UPDATE AD_Element SET Name='Kommissionierung zur obersten TU-Struktur', Updated=TO_TIMESTAMP('2026-08-20 09:30:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zur obersten TU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:33','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zur obersten TU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:34','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to top level TU structure', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:35','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583944)
;

-- === 583945 AllowPickToStructure_CU : "Pick to top level CU structure" -> "Kommissionierung zur obersten CU-Struktur" ===
UPDATE AD_Element SET Name='Kommissionierung zur obersten CU-Struktur', Updated=TO_TIMESTAMP('2026-08-20 09:30:36','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zur obersten CU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:37','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommissionierung zur obersten CU-Struktur', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:38','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to top level CU structure', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:39','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583945)
;
