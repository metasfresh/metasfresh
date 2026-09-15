-- #############################################################################
-- Migration C: rename the scripted-import config's "Importeur" field to the clearer
-- "Import-Benutzer" / "Import User", and give it a field description.
--
-- AD_User_Import_ID holds the metasfresh user whose WebUI API access token authorises
-- the order candidates created by the import — a technical/service identity, not a
-- business "importer". The old label "Importeur"/"Importer" was misleading and the
-- it_CH translation was wrong (it read the German "Benutzerimport").
--
-- AD_Element 584119 is used ONLY by ExternalSystem_Config_ScriptedImportConversion.
-- AD_User_Import_ID (verified single-use), so the shared element is mutated directly.
-- The field appears on both config tabs (548472 field 755005, 548473 field 755015);
-- neither has an AD_Name_ID override, so the propagation updates both.
-- #############################################################################

-- 1. Base element: label + description (German base).
UPDATE AD_Element
SET Name = 'Import-Benutzer', PrintName = 'Import-Benutzer',
    Description = 'Der metasfresh-Benutzer, dessen WebUI-API-Zugriffstoken die von diesem Import erzeugten Auftragskandidaten autorisiert. Der Benutzer benötigt ein gültiges WebUI-Authentifizierungstoken.',
    Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584119;

-- 2. Per-language element translations (name + description), all IsTranslated='Y'.
UPDATE AD_Element_Trl
SET Name = 'Import-Benutzer', PrintName = 'Import-Benutzer',
    Description = 'Der metasfresh-Benutzer, dessen WebUI-API-Zugriffstoken die von diesem Import erzeugten Auftragskandidaten autorisiert. Der Benutzer benötigt ein gültiges WebUI-Authentifizierungstoken.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584119 AND AD_Language IN ('de_DE', 'de_CH');

UPDATE AD_Element_Trl
SET Name = 'Import User', PrintName = 'Import User',
    Description = 'The metasfresh user whose WebUI API access token authorises the order candidates created by this import. This user needs a valid WebUI auth token.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584119 AND AD_Language = 'en_US';

UPDATE AD_Element_Trl
SET Name = 'Utilisateur d''import', PrintName = 'Utilisateur d''import',
    Description = 'Utilisateur metasfresh dont le jeton API WebUI autorise les candidats commande créés par cet import. Un jeton WebUI valide est requis.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584119 AND AD_Language = 'fr_CH';

UPDATE AD_Element_Trl
SET Name = 'Utente di importazione', PrintName = 'Utente di importazione',
    Description = 'Utente metasfresh il cui token API WebUI autorizza i candidati ordine creati da questa importazione. È richiesto un token WebUI valido.',
    IsTranslated = 'Y', Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Element_ID = 584119 AND AD_Language = 'it_CH';

-- 3. Propagate element translations to AD_Column_Trl + AD_Field_Trl (per language).
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584119, 'de_DE');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584119, 'de_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584119, 'en_US');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584119, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584119, 'it_CH');

-- 4. Update the language-independent base Name/Description caches on the column + both fields.
UPDATE AD_Column
SET Name = 'Import-Benutzer',
    Description = 'Der metasfresh-Benutzer, dessen WebUI-API-Zugriffstoken die von diesem Import erzeugten Auftragskandidaten autorisiert. Der Benutzer benötigt ein gültiges WebUI-Authentifizierungstoken.',
    Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Column_ID = 591366;

UPDATE AD_Field
SET Name = 'Import-Benutzer',
    Description = 'Der metasfresh-Benutzer, dessen WebUI-API-Zugriffstoken die von diesem Import erzeugten Auftragskandidaten autorisiert. Der Benutzer benötigt ein gültiges WebUI-Authentifizierungstoken.',
    Updated = TO_TIMESTAMP('2026-07-17 10:10', 'YYYY-MM-DD HH24:MI'), UpdatedBy = 100
WHERE AD_Field_ID IN (755005, 755015);
