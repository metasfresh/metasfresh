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
-- Names below are human-approved abbreviations (some elements shortened to
-- fit the Name column comfortably); every one of the 9 renamed/kept elements
-- also gets a German Description carrying the meaning the short Name can no
-- longer hold, based on read-only behavioural analysis of the actual code
-- paths (see ai-work/31391/label-semantics-review.md). English Descriptions
-- are added as translation overrides; en_US Name/Description stay ENGLISH.
--
-- FormatPattern (53687) is a shared element (5 AD_Column usages across
-- other tables/windows too) -- translating it also fixes the label on
-- those other windows, which is the correct root-cause fix for a shared
-- element. Its Description was also still English and is corrected here
-- too, since it is shown as the field's tooltip. This element is NOT
-- further touched below: it keeps "Formatmuster" and its number/date
-- Description, which remain correct for its 4 other usages (AD_Column,
-- PA_ReportColumn, AD_PrintFormatItem, DATEV_ExportFormatColumn).
--
-- On PickingProfile_PickingJobConfig specifically, FormatPattern (AD_Column
-- 587947, AD_Field 725184) is NOT a number/date format -- it is an
-- address-component display-order token string, consumed via
-- AddressDisplaySequence.ofNullable(field.getPattern()) in
-- DisplayValueProvider.java:277-280,331-338. Per the "meaning inconsistent
-- across usages -> new AD_Element + AD_Field.AD_Name_ID" rule, a dedicated
-- AD_Element (585377, "Adressformat (Anzeige)") is created below and linked
-- onto AD_Field 725184 via AD_Name_ID, leaving the shared element 53687
-- untouched for its other 4 usages.

-- === 583513 PickingJobAggregationType : "Aggregation Type" -> "Aggregationstyp" (+ Description) ===
UPDATE AD_Element SET Name='Aggregationstyp', Description='Legt fest, wie Positionen zu einer Kommissionieraufgabe zusammengefasst werden: pro Auftrag (mit einer gemeinsamen LU für den ganzen Auftrag), pro Produkt (mit Kommissionierziel auf Zeilenebene) oder pro Lieferadresse.', Updated=TO_TIMESTAMP('2026-08-23 10:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513
;
UPDATE AD_Element_Trl SET Name='Aggregationstyp', Description='Legt fest, wie Positionen zu einer Kommissionieraufgabe zusammengefasst werden: pro Auftrag (mit einer gemeinsamen LU für den ganzen Auftrag), pro Produkt (mit Kommissionierziel auf Zeilenebene) oder pro Lieferadresse.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:01','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Aggregationstyp', Description='Legt fest, wie Positionen zu einer Kommissionieraufgabe zusammengefasst werden: pro Auftrag (mit einer gemeinsamen LU für den ganzen Auftrag), pro Produkt (mit Kommissionierziel auf Zeilenebene) oder pro Lieferadresse.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:02','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Aggregation Type', Description='Defines how lines are grouped into one picking job: by order (with one shared LU for the whole order), by product (with the pick target at line level), or by delivery location.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:03','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583513 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583513)
;

-- === 584219 IsAllowQuickPackAll : "Schnellverpackung für alle erlauben" -> "Schnelldruck erlauben" (+ Description) ===
-- Enables the on-screen "Schnelldruck" button (PickProductsActivity.jsx / translations_de.js:178);
-- PickingJobPickAllCommand picks every line of the job to its remaining qty, then completes it --
-- no printing is actually performed despite the button caption.
UPDATE AD_Element SET Name='Schnelldruck erlauben', Description='Blendet auf dem Kommissionierauftrag die Schaltfläche „Schnelldruck“ ein: sie kommissioniert alle noch offenen Positionen vollständig und schließt den Auftrag ab. Es wird dabei nichts gedruckt.', Updated=TO_TIMESTAMP('2026-08-23 10:00:04','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219
;
UPDATE AD_Element_Trl SET Name='Schnelldruck erlauben', Description='Blendet auf dem Kommissionierauftrag die Schaltfläche „Schnelldruck“ ein: sie kommissioniert alle noch offenen Positionen vollständig und schließt den Auftrag ab. Es wird dabei nichts gedruckt.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:05','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Schnelldruck erlauben', Description='Blendet auf dem Kommissionierauftrag die Schaltfläche „Schnelldruck“ ein: sie kommissioniert alle noch offenen Positionen vollständig und schließt den Auftrag ab. Es wird dabei nichts gedruckt.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:06','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Allow Quick Pack All', Description='Shows the "Schnelldruck" (quick-pack) button on the picking job: it fully picks all remaining open lines and completes the job. Despite its caption, nothing is printed.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:07','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=584219 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584219)
;

-- === 577441 IsAnonymousHuPickedOnTheFly : "Anonyme HU im Lauf kommissioniert" -> "Kommiss. ohne HU-Scan erl." (+ Description) ===
-- Lets the picker enter a packed qty without scanning a source HU; PickingJobPickCommand
-- (createPickFromHUOnTheFly) creates a new anonymous HU on the fly to receive it. Such picks are
-- excluded from transport-order assignment unless explicitly allowed
-- (InOutToTransportationOrderService.java:130).
UPDATE AD_Element SET Name='Kommiss. ohne HU-Scan erl.', Description='Erlaubt das Kommissionieren einer Menge, ohne vorher eine Quell-HU zu scannen; das System legt dafür automatisch eine neue, anonyme HU an. Solche Kommissionierungen werden von der automatischen Zuordnung zu Transportaufträgen ausgeschlossen, sofern dies nicht ausdrücklich erlaubt ist.', Updated=TO_TIMESTAMP('2026-08-23 10:00:08','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441
;
UPDATE AD_Element_Trl SET Name='Kommiss. ohne HU-Scan erl.', Description='Erlaubt das Kommissionieren einer Menge, ohne vorher eine Quell-HU zu scannen; das System legt dafür automatisch eine neue, anonyme HU an. Solche Kommissionierungen werden von der automatischen Zuordnung zu Transportaufträgen ausgeschlossen, sofern dies nicht ausdrücklich erlaubt ist.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:09','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommiss. ohne HU-Scan erl.', Description='Erlaubt das Kommissionieren einer Menge, ohne vorher eine Quell-HU zu scannen; das System legt dafür automatisch eine neue, anonyme HU an. Solche Kommissionierungen werden von der automatischen Zuordnung zu Transportaufträgen ausgeschlossen, sofern dies nicht ausdrücklich erlaubt ist.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:10','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Anonymous HU Picked On the Fly', Description='Allows picking a quantity without first scanning a source HU; the system automatically creates a new anonymous HU for it. Such picks are excluded from automatic transport-order assignment unless explicitly allowed.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:11','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=577441 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(577441)
;

-- === 583887 IsConsideredOnlyScheduledJobs : "Nur eingeplante Kommissionieraufgaben berücksichtigen" -> "Nur zugewiesene Aufgaben" (+ Description) ===
-- Only usage is MassPrintingService.java:115 (mass-printing feature): restricts candidate picking
-- jobs to those assigned to the operator's current workplace via Traffic Management (AD_Element
-- 584131 -- kept identical in DE/EN, used verbatim here). Distinct from the neighbouring
-- IsActiveWorkplaceRequired ("Aktiver Arbeitsplatz erforderlich").
UPDATE AD_Element SET Name='Nur zugewiesene Aufgaben', Description='Zeigt beim Massendruck nur Kommissionieraufgaben an, die über das Traffic Management dem aktuellen Arbeitsplatz des Bedieners zugewiesen wurden. Zu unterscheiden von „Aktiver Arbeitsplatz erforderlich“.', Updated=TO_TIMESTAMP('2026-08-23 10:00:12','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887
;
UPDATE AD_Element_Trl SET Name='Nur zugewiesene Aufgaben', Description='Zeigt beim Massendruck nur Kommissionieraufgaben an, die über das Traffic Management dem aktuellen Arbeitsplatz des Bedieners zugewiesen wurden. Zu unterscheiden von „Aktiver Arbeitsplatz erforderlich“.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:13','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Nur zugewiesene Aufgaben', Description='Zeigt beim Massendruck nur Kommissionieraufgaben an, die über das Traffic Management dem aktuellen Arbeitsplatz des Bedieners zugewiesen wurden. Zu unterscheiden von „Aktiver Arbeitsplatz erforderlich“.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:14','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Consider only scheduled jobs', Description='When mass printing, only considers picking jobs that have been assigned to the operator''s current workplace via Traffic Management. Distinct from "Active workplace required".', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:15','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583887 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583887)
;

-- === 583565 IsFilterByBarcode : "Filter by Barcode" -> "Nach Barcode filtern" (+ Description) ===
UPDATE AD_Element SET Name='Nach Barcode filtern', Description='Blendet in der Liste der Kommissionieraufgaben eine Schaltfläche ein, mit der die Liste durch Scannen eines Barcodes gefiltert werden kann.', Updated=TO_TIMESTAMP('2026-08-23 10:00:16','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565
;
UPDATE AD_Element_Trl SET Name='Nach Barcode filtern', Description='Blendet in der Liste der Kommissionieraufgaben eine Schaltfläche ein, mit der die Liste durch Scannen eines Barcodes gefiltert werden kann.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:17','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Nach Barcode filtern', Description='Blendet in der Liste der Kommissionieraufgaben eine Schaltfläche ein, mit der die Liste durch Scannen eines Barcodes gefiltert werden kann.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:18','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Filter by Barcode', Description='Shows a button in the picking-jobs list that lets the list be filtered by scanning a barcode.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:19','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583565 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583565)
;

-- === 53687 FormatPattern : "Format Pattern" -> "Formatmuster" (Name + Description; shared element, see header) ===
-- NOT touched further for the PickingProfile_PickingJobConfig usage -- see the new AD_Element
-- 585377 further below, linked via AD_Field.AD_Name_ID instead.
UPDATE AD_Element SET Name='Formatmuster', Description='Das Muster, das zur Formatierung einer Zahl oder eines Datums verwendet wird.', Updated=TO_TIMESTAMP('2026-08-20 09:30:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687
;
UPDATE AD_Element_Trl SET Name='Formatmuster', Description='Das Muster, das zur Formatierung einer Zahl oder eines Datums verwendet wird.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-20 09:30:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Formatmuster', Description='Das Muster, das zur Formatierung einer Zahl oder eines Datums verwendet wird.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-20 09:30:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Format Pattern', Description='The pattern used to format a number or date.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-20 09:30:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=53687 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(53687)
;

-- === 583942 AllowPickToStructure_LU_TU : "Kommissionierung zu LU/TU-Struktur" -> "Kommiss. zu LU/TU erlauben" (+ Description) ===
UPDATE AD_Element SET Name='Kommiss. zu LU/TU erlauben', Description='Erlaubt das Kommissionieren in eine geschachtelte LU/TU-Struktur (Palette mit Kartons/TUs).', Updated=TO_TIMESTAMP('2026-08-23 10:00:20','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu LU/TU erlauben', Description='Erlaubt das Kommissionieren in eine geschachtelte LU/TU-Struktur (Palette mit Kartons/TUs).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:21','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu LU/TU erlauben', Description='Erlaubt das Kommissionieren in eine geschachtelte LU/TU-Struktur (Palette mit Kartons/TUs).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:22','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to LU/TU structure', Description='Allows picking into a nested LU/TU structure (pallet with cartons/TUs).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:23','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583942 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583942)
;

-- === 583943 AllowPickToStructure_LU_CU : "Kommissionierung zu LU/CU-Struktur" -> "Kommiss. zu LU/CU erlauben" (+ Description) ===
UPDATE AD_Element SET Name='Kommiss. zu LU/CU erlauben', Description='Erlaubt das Kommissionieren direkt in eine LU, die CUs ohne zwischengeschaltete TU enthält (Palette ohne Kartons).', Updated=TO_TIMESTAMP('2026-08-23 10:00:24','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu LU/CU erlauben', Description='Erlaubt das Kommissionieren direkt in eine LU, die CUs ohne zwischengeschaltete TU enthält (Palette ohne Kartons).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:25','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu LU/CU erlauben', Description='Erlaubt das Kommissionieren direkt in eine LU, die CUs ohne zwischengeschaltete TU enthält (Palette ohne Kartons).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:26','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to LU/CU structure', Description='Allows picking directly into an LU that holds CUs without an intermediate TU (pallet without cartons).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:27','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583943 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583943)
;

-- === 583944 AllowPickToStructure_TU : "Kommissionierung zur obersten TU-Struktur" -> "Kommiss. zu oberster TU erl." (+ Description) ===
UPDATE AD_Element SET Name='Kommiss. zu oberster TU erl.', Description='Erlaubt das Kommissionieren in eine eigenständige TU ohne übergeordnete LU (TU als oberste Verpackungsebene).', Updated=TO_TIMESTAMP('2026-08-23 10:00:28','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu oberster TU erl.', Description='Erlaubt das Kommissionieren in eine eigenständige TU ohne übergeordnete LU (TU als oberste Verpackungsebene).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:29','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu oberster TU erl.', Description='Erlaubt das Kommissionieren in eine eigenständige TU ohne übergeordnete LU (TU als oberste Verpackungsebene).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:30','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to top level TU structure', Description='Allows picking into a standalone TU with no parent LU (TU as the top packaging level).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:31','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583944 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583944)
;

-- === 583945 AllowPickToStructure_CU : "Kommissionierung zur obersten CU-Struktur" -> "Kommiss. zu oberster CU erl." (+ Description) ===
UPDATE AD_Element SET Name='Kommiss. zu oberster CU erl.', Description='Erlaubt das Kommissionieren in eine lose CU ohne LU- oder TU-Verpackung (CU als oberste Ebene).', Updated=TO_TIMESTAMP('2026-08-23 10:00:32','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu oberster CU erl.', Description='Erlaubt das Kommissionieren in eine lose CU ohne LU- oder TU-Verpackung (CU als oberste Ebene).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:33','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Kommiss. zu oberster CU erl.', Description='Erlaubt das Kommissionieren in eine lose CU ohne LU- oder TU-Verpackung (CU als oberste Ebene).', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:34','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945 AND AD_Language='de_CH'
;
UPDATE AD_Element_Trl SET Name='Pick to top level CU structure', Description='Allows picking into a loose CU with no LU or TU packaging (CU as the top level).', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:35','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=583945 AND AD_Language='en_US'
;
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(583945)
;

-- === Part B: dedicated AD_Element for PickingProfile_PickingJobConfig.FormatPattern ===
-- On this one table (AD_Column 587947, AD_Field 725184) the shared element 53687's meaning does
-- NOT apply -- the column holds an address-component display-order token string
-- (AddressDisplaySequence.ofNullable(field.getPattern()), DisplayValueProvider.java:277-280,
-- 331-338), not a number/date format pattern. Per the "meaning inconsistent across usages -> new
-- AD_Element + AD_Field.AD_Name_ID" rule: create a new element and link it onto this ONE field,
-- leaving 53687 untouched for its other 4 usages.
INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, EntityType, Name, PrintName, Description)
VALUES (585377 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-08-23 10:00:36','YYYY-MM-DD HH24:MI:SS'), 100,
        TO_TIMESTAMP('2026-08-23 10:00:36','YYYY-MM-DD HH24:MI:SS'), 100,
        'PickingJobConfig_AddressDisplaySequence', 'D',
        'Adressformat (Anzeige)', 'Adressformat (Anzeige)',
        'Legt bei einem Adressfeld (z. B. Übergabeadresse) fest, welche Adressbestandteile in welcher Reihenfolge angezeigt werden. Kein Zahlen- oder Datumsformat.')
;

-- Skeleton _Trl rows for all active system languages
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, e.AD_Element_ID, e.Name, e.PrintName, e.Description, 'N',
       e.AD_Client_ID, e.AD_Org_ID, e.Created, e.CreatedBy,
       TO_TIMESTAMP('2026-08-23 10:00:37','YYYY-MM-DD HH24:MI:SS'), 100, 'Y'
FROM AD_Language l, AD_Element e
WHERE l.IsActive='Y' AND l.IsSystemLanguage='Y' AND e.AD_Element_ID=585377
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Element_ID=e.AD_Element_ID)
;

-- de_DE / de_CH: German, IsTranslated='N' (base-language convention)
UPDATE AD_Element_Trl SET Name='Adressformat (Anzeige)', Description='Legt bei einem Adressfeld (z. B. Übergabeadresse) fest, welche Adressbestandteile in welcher Reihenfolge angezeigt werden. Kein Zahlen- oder Datumsformat.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:38','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585377 AND AD_Language='de_DE'
;
UPDATE AD_Element_Trl SET Name='Adressformat (Anzeige)', Description='Legt bei einem Adressfeld (z. B. Übergabeadresse) fest, welche Adressbestandteile in welcher Reihenfolge angezeigt werden. Kein Zahlen- oder Datumsformat.', IsTranslated='N', Updated=TO_TIMESTAMP('2026-08-23 10:00:39','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585377 AND AD_Language='de_CH'
;

-- en_US: English override, IsTranslated='Y'
UPDATE AD_Element_Trl SET Name='Address format (display)', Description='For an address-type field (e.g. handover address), defines which address components are shown and in what order. Not a number or date format.', IsTranslated='Y', Updated=TO_TIMESTAMP('2026-08-23 10:00:40','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Element_ID=585377 AND AD_Language='en_US'
;

SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585377)
;

-- Link AD_Field 725184 (FormatPattern field on PickingProfile_PickingJobConfig / AD_Column 587947)
-- to the new element via AD_Name_ID -- the shared element 53687 is NOT touched for this usage.
UPDATE AD_Field SET AD_Name_ID=585377, Updated=TO_TIMESTAMP('2026-08-23 10:00:41','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Field_ID=725184
;

-- Propagate the new element's translations onto AD_Field_Trl / AD_Field via the AD_Name_ID path
SELECT update_FieldTranslation_From_AD_Name_Element(585377)
;

-- Rebuild the element link so dictionary tooling reflects the new AD_Name_ID override
DELETE FROM AD_Element_Link WHERE AD_Field_ID=725184
;
SELECT AD_Element_Link_Create_Missing_Field(725184)
;
