-- ExternalSystem_Endpoint is shared by inbound (import polling/download) and outbound (export
-- delivery). Make each field's description state its direction so users know whether a field applies
-- to import, to export, or to both. Dedicated elements (used only by this table, verified) are
-- mutated directly; the three shared elements (ContentType, AuthToken, LoginUsername) get a
-- field-level description on the endpoint tab (548506) instead, so no shared element is changed.

------------------------------------------------------------------------------------------------
-- Import-only (inbound SFTP polling): SftpPollingIntervalMs 584678, SftpProcessedDirectory 584679,
-- SftpErrorDirectory 584680 -- append the direction marker to the existing text.
------------------------------------------------------------------------------------------------
UPDATE AD_Element SET Description = COALESCE(Description,'') || ' (nur Import)', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584678,584679,584680);
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (nur Import)',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584678,584679,584680) AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (import only)',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584678,584679,584680) AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (import uniquement)',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584678,584679,584680) AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (solo import)',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584678,584679,584680) AND AD_Language='it_CH';

------------------------------------------------------------------------------------------------
-- Export-only, existing text (outbound): SftpFilenamePattern 584677, OutboundHttpMethod 584106
-- -- append the direction marker.
------------------------------------------------------------------------------------------------
UPDATE AD_Element SET Description = COALESCE(Description,'') || ' (nur Export)', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584677,584106);
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (nur Export)',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584677,584106) AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (export only)',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584677,584106) AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (export uniquement)',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584677,584106) AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description = COALESCE(Description,'') || ' (solo export)',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID IN (584677,584106) AND AD_Language='it_CH';

------------------------------------------------------------------------------------------------
-- Export-only, empty until now (outbound HTTP auth) -- dedicated elements, set a full description.
--   AuthType 584192, ClientId 584194, ClientSecret 584193, SasSignature 584220
------------------------------------------------------------------------------------------------
-- AuthType
UPDATE AD_Element SET Description='Authentifizierungsmethode für ausgehende HTTP-Aufrufe (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584192;
UPDATE AD_Element_Trl SET Description='Authentifizierungsmethode für ausgehende HTTP-Aufrufe (nur Export).',           IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584192 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Authentication method for outbound HTTP calls (export only).',                  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584192 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Description='Méthode d''authentification pour les appels HTTP sortants (export uniquement).',IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584192 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Metodo di autenticazione per le chiamate HTTP in uscita (solo export).',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584192 AND AD_Language='it_CH';
-- ClientId
UPDATE AD_Element SET Description='Client-ID für die OAuth-Authentifizierung ausgehender HTTP-Aufrufe (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584194;
UPDATE AD_Element_Trl SET Description='Client-ID für die OAuth-Authentifizierung ausgehender HTTP-Aufrufe (nur Export).',        IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584194 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Client ID for OAuth authentication of outbound HTTP calls (export only).',                IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584194 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Description='ID client pour l''authentification OAuth des appels HTTP sortants (export uniquement).',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584194 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='ID client per l''autenticazione OAuth delle chiamate HTTP in uscita (solo export).',      IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584194 AND AD_Language='it_CH';
-- ClientSecret
UPDATE AD_Element SET Description='Client-Secret für die OAuth-Authentifizierung ausgehender HTTP-Aufrufe (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584193;
UPDATE AD_Element_Trl SET Description='Client-Secret für die OAuth-Authentifizierung ausgehender HTTP-Aufrufe (nur Export).',    IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584193 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='Client secret for OAuth authentication of outbound HTTP calls (export only).',            IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584193 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Description='Secret client pour l''authentification OAuth des appels HTTP sortants (export uniquement).',IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584193 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Client secret per l''autenticazione OAuth delle chiamate HTTP in uscita (solo export).',  IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584193 AND AD_Language='it_CH';
-- SasSignature
UPDATE AD_Element SET Description='SAS-Signatur (Shared Access Signature) für ausgehende HTTP-Aufrufe (nur Export).', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584220;
UPDATE AD_Element_Trl SET Description='SAS-Signatur (Shared Access Signature) für ausgehende HTTP-Aufrufe (nur Export).', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584220 AND AD_Language IN ('de_DE','de_CH');
UPDATE AD_Element_Trl SET Description='SAS (Shared Access Signature) for outbound HTTP calls (export only).',            IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584220 AND AD_Language='en_US';
UPDATE AD_Element_Trl SET Description='Signature SAS (Shared Access Signature) pour les appels HTTP sortants (export uniquement).', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584220 AND AD_Language='fr_CH';
UPDATE AD_Element_Trl SET Description='Firma SAS (Shared Access Signature) per le chiamate HTTP in uscita (solo export).', IsTranslated='Y', Updated=now(), UpdatedBy=100 WHERE AD_Element_ID=584220 AND AD_Language='it_CH';

-- NOTE: the three shared-element fields (ContentType, AuthToken, LoginUsername) are handled in the
-- next script (element fork): a plain AD_Field.Description override does NOT survive
-- after_migration_sync_translations, which re-propagates the field description from the column's
-- (shared, empty) element -- so they need a dedicated forked element instead.

-- Propagate the element description edits down to the derived AD_Column(_Trl)/AD_Field(_Trl) rows
-- (NULL language = all languages), per the convention used by the sibling migrations. The
-- after_migration sync also does this, but call it explicitly so the result never depends on it.
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584678, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584679, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584680, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584677, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584106, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584192, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584193, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584194, NULL);
SELECT update_TRL_Tables_On_AD_Element_TRL_Update(584220, NULL);
