-- Translation cleanup for the endpoint direction-description work:
--  (1) OutboundHttpMethod (584106): its fr_CH/it_CH descriptions were verbatim German copies with a
--      bolted-on direction marker (same pre-existing debt as the 4 SFTP fields fixed in 5814570) --
--      give them proper French/Italian.
--  (2) Forked label elements 585113 (AuthToken) / 585114 (LoginUsername): their it_CH (and one
--      fr_CH) *labels* were left in English / ungrammatical while their descriptions are localized --
--      align the label language with the description.

-- (1) OutboundHttpMethod description (export)
UPDATE AD_Element_Trl SET Description='Méthode HTTP utilisée pour l''envoi des données (p. ex. POST, PUT) (export uniquement)', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584106 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Metodo HTTP utilizzato per l''invio dei dati (es. POST, PUT) (solo export)',            IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584106 AND AD_Language='it_CH';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584106, 'fr_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584106, 'it_CH');

-- (2) Forked-element labels
-- AuthToken (585113): it_CH label -> Italian (matches its Italian description)
UPDATE AD_Element_Trl SET Name='Token di autenticazione', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585113 AND AD_Language='it_CH';
-- LoginUsername (585114): it_CH label -> Italian; fr_CH label -> grammatical French
UPDATE AD_Element_Trl SET Name='Nome utente',                  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585114 AND AD_Language='it_CH';
UPDATE AD_Element_Trl SET Name='Nom d''utilisateur de connexion', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=585114 AND AD_Language='fr_CH';
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585113, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'it_CH');
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(585114, 'fr_CH');
