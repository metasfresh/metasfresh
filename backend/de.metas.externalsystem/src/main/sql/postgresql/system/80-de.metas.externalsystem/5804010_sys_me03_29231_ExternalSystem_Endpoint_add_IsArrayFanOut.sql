-- me03#29231 — Add IsArrayFanOut boolean column to ExternalSystem_Endpoint
-- Plan: ai-work/29231/PLAN_ARRAY_MODE.md §3.1 (Domain model + DTO plumbing for IsArrayFanOut)
--
-- Rationale (EIP "fan-out on array"): when the upstream scripted-adapter conversion returns
-- a JSON array, the array-fan-out flag instructs the downstream Camel route to dispatch one
-- HTTP/SFTP request per array element. If the payload is a single object or the flag is
-- unset, the endpoint runs once as today. This enables consolidated multi-source-order
-- shipments (one M_InOut carrying N DESADVs) to emit N independent downstream messages
-- without changing the upstream contract.
--
-- Default 'N' so existing endpoints are unaffected.

-- =============================================================================
-- 1. AD_Element
-- =============================================================================

INSERT INTO AD_Element (AD_Element_ID, AD_Client_ID, AD_Org_ID, IsActive,
                        Created, CreatedBy, Updated, UpdatedBy,
                        ColumnName, Name, PrintName, Description, Help, EntityType)
VALUES (5848980 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        'IsArrayFanOut', 'Array-Fan-Out', 'Array-Fan-Out',
        'Bei aktivierter Option und JSON-Array als Antwort der vorgeschalteten Konvertierung wird je Array-Element eine separate Anfrage an diesen Endpunkt gesendet.',
        'Wird ein einzelnes Objekt geliefert oder ist die Option deaktiviert, läuft der Endpunkt wie bisher mit genau einer Anfrage. Wird die Option aktiviert und ein Array geliefert, ergeht je Element ein eigener HTTP/SFTP-Aufruf; jeder Aufruf wird einzeln protokolliert und sein Status (Erfolg/Fehler) am Quellsatz vermerkt.',
        'de.metas.externalsystem')
ON CONFLICT (AD_Element_ID) DO NOTHING
;

-- Skeleton AD_Element_Trl rows for all installed languages
INSERT INTO AD_Element_Trl (AD_Language, AD_Element_ID, Name, PrintName, Description, Help, IsTranslated,
                             AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, 5848980 /*From ID Server*/, t.Name, t.PrintName, t.Description, t.Help, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Element t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Element_ID = 5848980
  AND NOT EXISTS (SELECT 1 FROM AD_Element_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Element_ID = t.AD_Element_ID)
;

-- English (en_US) translation
UPDATE AD_Element_Trl
SET Name = 'Array Fan-Out',
    PrintName = 'Array Fan-Out',
    Description = 'If true and the upstream conversion returns a JSON array, one separate request is sent to this endpoint per array element.',
    Help = 'If the upstream payload is a single object or this flag is unset, the endpoint runs once as today. If the flag is set and the payload is a JSON array, one HTTP/SFTP call is dispatched per element; each call is logged and its outcome (success/error) is recorded against the source record.',
    IsTranslated = 'Y',
    Updated = TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'),
    UpdatedBy = 100
WHERE AD_Element_ID = 5848980 AND AD_Language = 'en_US'
;

-- =============================================================================
-- 2. AD_Column on ExternalSystem_Endpoint (AD_Table_ID=542551)
--    YesNo reference (AD_Reference_ID=20), char(1), mandatory, default 'N'.
-- =============================================================================

INSERT INTO AD_Column (AD_Column_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                       AD_Element_ID, AD_Table_ID, ColumnName, Name, Description,
                       Version, EntityType, AD_Reference_ID,
                       IsMandatory, DefaultValue, IsUpdateable, IsAlwaysUpdateable, IsIdentifier, IsKey, IsParent,
                       FieldLength, IsTranslated, IsSelectionColumn,
                       PersonalDataCategory,
                       CloningStrategy, FacetFilterSeqNo, IsAdvancedText, IsAllowLogging,
                       IsAutoApplyValidationRule, IsAutocomplete, IsCalculated, IsDimension,
                       IsDLMPartitionBoundary, IsEncrypted, IsExcludeFromZoomTargets, IsFacetFilter,
                       IsForceIncludeInGeneratedModel, IsGenericZoomKeyColumn, IsGenericZoomOrigin,
                       IsLazyLoading, IsRestAPICustomColumn, IsShowFilterIncrementButtons,
                       IsShowFilterInline, IsStaleable, IsSyncDatabase, IsUseDocSequence, MaxFacetsToFetch,
                       SelectionColumnSeqNo, SeqNo)
VALUES (5925820 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        5848980, 542551, 'IsArrayFanOut', 'Array-Fan-Out',
        'Bei aktivierter Option und JSON-Array als Antwort der vorgeschalteten Konvertierung wird je Array-Element eine separate Anfrage an diesen Endpunkt gesendet.',
        0, 'de.metas.externalsystem', 20,
        'Y', 'N', 'Y', 'Y', 'N', 'N', 'N',
        1, 'N', 'N',
        'NP',
        'DC', 0, 'N', 'Y',
        'N', 'N', 'N', 'N',
        'N', 'N', 'Y', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N',
        'N', 'N', 'N', 'N', 0,
        0, 0)
ON CONFLICT (AD_Column_ID) DO NOTHING
;

-- Skeleton AD_Column_Trl rows for all installed languages
INSERT INTO AD_Column_Trl (AD_Language, AD_Column_ID, Name, IsTranslated,
                            AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Column_ID, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Column t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' OR l.IsBaseLanguage = 'Y')
  AND t.AD_Column_ID = 5925820
  AND NOT EXISTS (SELECT 1 FROM AD_Column_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Column_ID = t.AD_Column_ID)
;

-- Propagate AD_Element translations into AD_Column_Trl (centrally maintained)
SELECT update_Column_Translation_From_AD_Element(5848980);

-- =============================================================================
-- 3. Physical column on ExternalSystem_Endpoint
--    3-statement pattern (add nullable → backfill 'N' → SET NOT NULL → SET DEFAULT)
--    NOTE: ADD COLUMN IF NOT EXISTS is not used (compat with PG 9.5).
-- =============================================================================

ALTER TABLE ExternalSystem_Endpoint ADD COLUMN IsArrayFanOut CHAR(1);
UPDATE ExternalSystem_Endpoint SET IsArrayFanOut = 'N' WHERE IsArrayFanOut IS NULL;
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN IsArrayFanOut SET NOT NULL;
ALTER TABLE ExternalSystem_Endpoint ALTER COLUMN IsArrayFanOut SET DEFAULT 'N';

ALTER TABLE ExternalSystem_Endpoint
    ADD CONSTRAINT ck_endpoint_isarrayfanout CHECK (IsArrayFanOut IN ('Y', 'N'));

-- =============================================================================
-- 4. AD_Field on the Endpoint tab (AD_Tab_ID=548506).
--    Placed after OutboundHttpMethod in the "Outbound HTTP" section.
-- =============================================================================

INSERT INTO AD_Field (AD_Field_ID, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy,
                      AD_Column_ID, AD_Tab_ID, Name, Description, Help, EntityType,
                      IsDisplayed, IsDisplayedGrid, IsReadOnly, IsSameLine, IsFieldOnly,
                      IsHeading, IsEncrypted, SortNo, SeqNo)
VALUES (7802600 /*From ID Server*/, 0, 0, 'Y',
        TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        TO_TIMESTAMP('2026-05-22 10:00', 'YYYY-MM-DD HH24:MI'), 100,
        5925820, 548506, 'Array-Fan-Out',
        'Bei aktivierter Option und JSON-Array als Antwort der vorgeschalteten Konvertierung wird je Array-Element eine separate Anfrage an diesen Endpunkt gesendet.',
        'Wird ein einzelnes Objekt geliefert oder ist die Option deaktiviert, läuft der Endpunkt wie bisher mit genau einer Anfrage. Wird die Option aktiviert und ein Array geliefert, ergeht je Element ein eigener HTTP/SFTP-Aufruf; jeder Aufruf wird einzeln protokolliert und sein Status (Erfolg/Fehler) am Quellsatz vermerkt.',
        'de.metas.externalsystem',
        'Y', 'N', 'N', 'N', 'N',
        'N', 'N', 0, 0)
ON CONFLICT (AD_Field_ID) DO NOTHING
;

-- Skeleton AD_Field_Trl rows for all installed languages
INSERT INTO AD_Field_Trl (AD_Language, AD_Field_ID, Description, Help, Name, IsTranslated,
                           AD_Client_ID, AD_Org_ID, Created, CreatedBy, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Field_ID, t.Description, t.Help, t.Name, 'N',
       t.AD_Client_ID, t.AD_Org_ID, t.Created, t.CreatedBy, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Field t
WHERE l.IsActive = 'Y' AND (l.IsSystemLanguage = 'Y' AND l.IsBaseLanguage = 'N')
  AND t.AD_Field_ID = 7802600
  AND NOT EXISTS (SELECT 1 FROM AD_Field_Trl tt
                  WHERE tt.AD_Language = l.AD_Language AND tt.AD_Field_ID = t.AD_Field_ID)
;

-- =============================================================================
-- 5. Propagate AD_Element_Trl into AD_Field_Trl + AD_Column_Trl per installed language.
--    Idiomatic pattern from T6 cr commit db3d27f8be1 — see metasfresh-application-dictionary
--    skill section "Translation propagation".
-- =============================================================================

DO $$
DECLARE
    lang_rec RECORD;
BEGIN
    FOR lang_rec IN
        SELECT AD_Language
        FROM AD_Language
        WHERE IsActive = 'Y'
          AND (IsSystemLanguage = 'Y' OR IsBaseLanguage = 'Y')
    LOOP
        PERFORM update_TRL_Tables_On_AD_Element_TRL_Update(5848980, lang_rec.AD_Language);
    END LOOP;
END $$
;
