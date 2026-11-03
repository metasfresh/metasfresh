-- VAT-ID online check: the Excel verification report for one VATaxID_Config record (one organisation).
--
-- Pure Application Dictionary artefact: an AD_Process of Type='Excel' whose SQLStatement calls a plain
-- SQL function, scoped to the current record via @VATaxID_Config_ID@ -- the same shape already used by
-- PP_Product_BOM_Recursive_Report (table PP_Product_BOM, function taking @PP_Product_BOM_ID@) and by this
-- module's own C_BPartner_VATaxID_Check process. AD_Table_Process is what makes the process appear as a
-- record action on the VATaxID_Config window; no AD_Menu entry is needed for a record-scoped report (the
-- template carries none either).
--
-- Eight sections in one flat result set (a single Excel sheet cannot hold multiple grids), distinguished by
-- the leading "section" column and ordered by seqno so they render as consecutive blocks: (1) count of
-- VAT-IDs per status, split by partner header vs. address; (2) partners/addresses with no VAT-ID at all;
-- (3) VAT-IDs whose last check is older than the organisation's configured re-check interval; (4) VAT-IDs
-- present but never checked at all; (5) VAT-IDs whose prefix falls outside VIES's coverage; (6) check
-- attempts left at "request sent" with no answer ever recorded; (7) invalid VAT-IDs whose partner still has
-- an open (not completed/closed) order or an unprocessed invoice candidate -- a listing only, nothing here
-- writes to either; (8) the same non-blank VAT-ID value held by more than one distinct business partner
-- (header or address), which is always a data-entry problem, never legitimate.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819200 (this file's prefix)
--   AD_Process          585652
--   AD_Table_Process     541664 (exposes the process on VATaxID_Config, table 542638)

-- 1. The report function
DROP FUNCTION IF EXISTS VATaxID_Config_Report(NUMERIC);

CREATE OR REPLACE FUNCTION VATaxID_Config_Report(p_VATaxID_Config_ID NUMERIC)
    RETURNS TABLE
            (
                SeqNo                 NUMERIC,
                Section                VARCHAR,
                RecordType             VARCHAR,
                C_BPartner_ID          NUMERIC,
                BPartnerValue          VARCHAR,
                BPartnerName           VARCHAR,
                C_BPartner_Location_ID NUMERIC,
                LocationCity           VARCHAR,
                VATaxID                VARCHAR,
                VATaxIDStatus          VARCHAR,
                VATaxIDCheckedAt       TIMESTAMP,
                RecordCount            NUMERIC,
                Detail                 VARCHAR
            )
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_ad_org_id          NUMERIC;
    v_recheck_after_days NUMERIC;
    v_cutoff             TIMESTAMP;
BEGIN
    SELECT c.AD_Org_ID, c.RecheckAfterDays
    INTO v_ad_org_id, v_recheck_after_days
    FROM VATaxID_Config c
    WHERE c.VATaxID_Config_ID = p_VATaxID_Config_ID;

    IF v_ad_org_id IS NULL THEN
        RAISE EXCEPTION 'VATaxID_Config % not found', p_VATaxID_Config_ID;
    END IF;

    v_cutoff := NOW() - (v_recheck_after_days || ' days')::INTERVAL;

    RETURN QUERY

        -- 1. Count of VAT-IDs per status, partner headers and addresses counted separately
        SELECT 10::NUMERIC, '1 - Count per status'::VARCHAR, s.RecordType,
               NULL::NUMERIC, NULL::VARCHAR, NULL::VARCHAR, NULL::NUMERIC, NULL::VARCHAR, NULL::VARCHAR,
               s.VATaxIDStatus, NULL::TIMESTAMP, s.Cnt::NUMERIC, NULL::VARCHAR
        FROM (
                 SELECT 'Partner'::VARCHAR AS RecordType, bp.VATaxIDStatus, COUNT(*) AS Cnt
                 FROM C_BPartner bp
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bp.IsActive = 'Y'
                 GROUP BY bp.VATaxIDStatus
                 UNION ALL
                 SELECT 'Location'::VARCHAR, bpl.VATaxIDStatus, COUNT(*)
                 FROM C_BPartner_Location bpl
                          JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bpl.IsActive = 'Y'
                 GROUP BY bpl.VATaxIDStatus
             ) s

        UNION ALL

        -- 2. Partners and addresses with no VAT-ID at all
        SELECT 20::NUMERIC, '2 - No VAT-ID at all'::VARCHAR, n.RecordType,
               n.C_BPartner_ID, n.BPartnerValue, n.BPartnerName, n.C_BPartner_Location_ID, n.LocationCity,
               NULL::VARCHAR, NULL::VARCHAR, NULL::TIMESTAMP, NULL::NUMERIC, NULL::VARCHAR
        FROM (
                 SELECT 'Partner'::VARCHAR AS RecordType, bp.C_BPartner_ID, bp.Value AS BPartnerValue,
                        bp.Name AS BPartnerName, NULL::NUMERIC AS C_BPartner_Location_ID,
                        NULL::VARCHAR AS LocationCity
                 FROM C_BPartner bp
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bp.IsActive = 'Y'
                   AND COALESCE(TRIM(bp.VATaxID), '') = ''
                 UNION ALL
                 SELECT 'Location'::VARCHAR, bp.C_BPartner_ID, bp.Value, bp.Name,
                        bpl.C_BPartner_Location_ID, loc.City
                 FROM C_BPartner_Location bpl
                          JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                          LEFT JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bpl.IsActive = 'Y'
                   AND COALESCE(TRIM(bpl.VATaxID), '') = ''
             ) n

        UNION ALL

        -- 3. Checked, but the result is older than the organisation's configured re-check interval
        SELECT 30::NUMERIC, '3 - Older than re-check interval'::VARCHAR, o.RecordType,
               o.C_BPartner_ID, o.BPartnerValue, o.BPartnerName, o.C_BPartner_Location_ID, o.LocationCity,
               o.VATaxID, o.VATaxIDStatus, o.VATaxIDCheckedAt, NULL::NUMERIC, NULL::VARCHAR
        FROM (
                 SELECT 'Partner'::VARCHAR AS RecordType, bp.C_BPartner_ID, bp.Value AS BPartnerValue,
                        bp.Name AS BPartnerName, NULL::NUMERIC AS C_BPartner_Location_ID,
                        NULL::VARCHAR AS LocationCity, bp.VATaxID, bp.VATaxIDStatus, bp.VATaxIDCheckedAt
                 FROM C_BPartner bp
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bp.IsActive = 'Y'
                   AND COALESCE(TRIM(bp.VATaxID), '') <> ''
                   AND bp.VATaxIDCheckedAt IS NOT NULL
                   AND bp.VATaxIDCheckedAt < v_cutoff
                 UNION ALL
                 SELECT 'Location'::VARCHAR, bp.C_BPartner_ID, bp.Value, bp.Name,
                        bpl.C_BPartner_Location_ID, loc.City, bpl.VATaxID, bpl.VATaxIDStatus, bpl.VATaxIDCheckedAt
                 FROM C_BPartner_Location bpl
                          JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                          LEFT JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bpl.IsActive = 'Y'
                   AND COALESCE(TRIM(bpl.VATaxID), '') <> ''
                   AND bpl.VATaxIDCheckedAt IS NOT NULL
                   AND bpl.VATaxIDCheckedAt < v_cutoff
             ) o

        UNION ALL

        -- 4. A VAT-ID is present but has never been checked at all
        SELECT 40::NUMERIC, '4 - Never checked'::VARCHAR, c.RecordType,
               c.C_BPartner_ID, c.BPartnerValue, c.BPartnerName, c.C_BPartner_Location_ID, c.LocationCity,
               c.VATaxID, c.VATaxIDStatus, c.VATaxIDCheckedAt, NULL::NUMERIC, NULL::VARCHAR
        FROM (
                 SELECT 'Partner'::VARCHAR AS RecordType, bp.C_BPartner_ID, bp.Value AS BPartnerValue,
                        bp.Name AS BPartnerName, NULL::NUMERIC AS C_BPartner_Location_ID,
                        NULL::VARCHAR AS LocationCity, bp.VATaxID, bp.VATaxIDStatus, bp.VATaxIDCheckedAt
                 FROM C_BPartner bp
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bp.IsActive = 'Y'
                   AND COALESCE(TRIM(bp.VATaxID), '') <> ''
                   AND bp.VATaxIDStatus = 'NotChecked'
                 UNION ALL
                 SELECT 'Location'::VARCHAR, bp.C_BPartner_ID, bp.Value, bp.Name,
                        bpl.C_BPartner_Location_ID, loc.City, bpl.VATaxID, bpl.VATaxIDStatus, bpl.VATaxIDCheckedAt
                 FROM C_BPartner_Location bpl
                          JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                          LEFT JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bpl.IsActive = 'Y'
                   AND COALESCE(TRIM(bpl.VATaxID), '') <> ''
                   AND bpl.VATaxIDStatus = 'NotChecked'
             ) c

        UNION ALL

        -- 5. VAT-ID prefix outside VIES's coverage (offline format check governs instead)
        SELECT 50::NUMERIC, '5 - Outside VIES coverage'::VARCHAR, u.RecordType,
               u.C_BPartner_ID, u.BPartnerValue, u.BPartnerName, u.C_BPartner_Location_ID, u.LocationCity,
               u.VATaxID, u.VATaxIDStatus, u.VATaxIDCheckedAt, NULL::NUMERIC, NULL::VARCHAR
        FROM (
                 SELECT 'Partner'::VARCHAR AS RecordType, bp.C_BPartner_ID, bp.Value AS BPartnerValue,
                        bp.Name AS BPartnerName, NULL::NUMERIC AS C_BPartner_Location_ID,
                        NULL::VARCHAR AS LocationCity, bp.VATaxID, bp.VATaxIDStatus, bp.VATaxIDCheckedAt
                 FROM C_BPartner bp
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bp.IsActive = 'Y'
                   AND bp.VATaxIDStatus = 'NotSupported'
                 UNION ALL
                 SELECT 'Location'::VARCHAR, bp.C_BPartner_ID, bp.Value, bp.Name,
                        bpl.C_BPartner_Location_ID, loc.City, bpl.VATaxID, bpl.VATaxIDStatus, bpl.VATaxIDCheckedAt
                 FROM C_BPartner_Location bpl
                          JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                          LEFT JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bpl.IsActive = 'Y'
                   AND bpl.VATaxIDStatus = 'NotSupported'
             ) u

        UNION ALL

        -- 6. Check attempts left at "request sent" with no answer ever recorded -- these rows are written
        --    in their own committed transaction, so they survive even when the rest of the check that
        --    started them is rolled back
        SELECT 60::NUMERIC, '6 - Stuck at request sent'::VARCHAR,
               CASE WHEN cl.C_BPartner_Location_ID IS NULL THEN 'Partner' ELSE 'Location' END::VARCHAR,
               cl.C_BPartner_ID, bp.Value, bp.Name, cl.C_BPartner_Location_ID, loc.City,
               cl.VATaxID, cl.VATaxIDStatus, cl.RequestDate, NULL::NUMERIC, NULL::VARCHAR
        FROM VATaxID_CheckLog cl
                 JOIN C_BPartner bp ON bp.C_BPartner_ID = cl.C_BPartner_ID
                 LEFT JOIN C_BPartner_Location bpl ON bpl.C_BPartner_Location_ID = cl.C_BPartner_Location_ID
                 LEFT JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
        WHERE cl.AD_Org_ID = v_ad_org_id
          AND cl.VATaxIDStatus = 'RequestSent'
          AND cl.ResponseDate IS NULL

        UNION ALL

        -- 7. Invalid VAT-ID whose partner still has an open order or an unprocessed invoice candidate --
        --    a listing only; neither is touched here (that is the check process's own, separate corrective
        --    step, guarded on DocStatus)
        SELECT 70::NUMERIC, '7 - Invalid with open orders or ICs'::VARCHAR, 'Partner'::VARCHAR,
               bp.C_BPartner_ID, bp.Value, bp.Name, NULL::NUMERIC, NULL::VARCHAR,
               bp.VATaxID, bp.VATaxIDStatus, bp.VATaxIDCheckedAt, NULL::NUMERIC,
               ('open orders: ' || x.OpenOrders::TEXT
                   || ', unprocessed invoice candidates: ' || x.OpenICs::TEXT)::VARCHAR
        FROM C_BPartner bp
                 JOIN LATERAL (
            SELECT (SELECT COUNT(*)
                    FROM C_Order ord
                    WHERE ord.C_BPartner_ID = bp.C_BPartner_ID
                      AND ord.DocStatus NOT IN ('CO', 'CL'))          AS OpenOrders,
                   (SELECT COUNT(*)
                    FROM C_Invoice_Candidate ic
                    WHERE ic.Bill_BPartner_ID = bp.C_BPartner_ID
                      AND ic.Processed = 'N')                        AS OpenICs
            ) x ON TRUE
        WHERE bp.AD_Org_ID = v_ad_org_id
          AND bp.IsActive = 'Y'
          AND bp.VATaxIDStatus = 'Invalid'
          AND (x.OpenOrders > 0 OR x.OpenICs > 0)

        UNION ALL

        -- 8. The same non-blank VAT-ID value held by more than one distinct business partner (header or
        --    address) -- always a data-entry problem, never legitimate. COUNT(DISTINCT ...) cannot be a
        --    window function in Postgres, so the duplicate count is a plain GROUP BY joined back onto the
        --    holder rows, not a window function over them.
        SELECT 80::NUMERIC, '8 - Duplicate VAT-ID across partners'::VARCHAR, h.RecordType,
               h.C_BPartner_ID, h.BPartnerValue, h.BPartnerName, h.C_BPartner_Location_ID, NULL::VARCHAR,
               h.VATaxID, NULL::VARCHAR, NULL::TIMESTAMP, dc.PartnerCount::NUMERIC, NULL::VARCHAR
        FROM (
                 SELECT 'Partner'::VARCHAR AS RecordType, bp.C_BPartner_ID, bp.Value AS BPartnerValue,
                        bp.Name AS BPartnerName, NULL::NUMERIC AS C_BPartner_Location_ID, bp.VATaxID
                 FROM C_BPartner bp
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bp.IsActive = 'Y'
                   AND COALESCE(TRIM(bp.VATaxID), '') <> ''
                 UNION ALL
                 SELECT 'Location'::VARCHAR, bp.C_BPartner_ID, bp.Value, bp.Name,
                        bpl.C_BPartner_Location_ID, bpl.VATaxID
                 FROM C_BPartner_Location bpl
                          JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                 WHERE bp.AD_Org_ID = v_ad_org_id
                   AND bpl.IsActive = 'Y'
                   AND COALESCE(TRIM(bpl.VATaxID), '') <> ''
             ) h
                 JOIN (
                 SELECT h2.VATaxID, COUNT(DISTINCT h2.C_BPartner_ID) AS PartnerCount
                 FROM (
                          SELECT bp.C_BPartner_ID, bp.VATaxID
                          FROM C_BPartner bp
                          WHERE bp.AD_Org_ID = v_ad_org_id
                            AND bp.IsActive = 'Y'
                            AND COALESCE(TRIM(bp.VATaxID), '') <> ''
                          UNION ALL
                          SELECT bp.C_BPartner_ID, bpl.VATaxID
                          FROM C_BPartner_Location bpl
                                   JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                          WHERE bp.AD_Org_ID = v_ad_org_id
                            AND bpl.IsActive = 'Y'
                            AND COALESCE(TRIM(bpl.VATaxID), '') <> ''
                      ) h2
                 GROUP BY h2.VATaxID
                 HAVING COUNT(DISTINCT h2.C_BPartner_ID) > 1
             ) dc ON dc.VATaxID = h.VATaxID

        ORDER BY 1, 4, 7;
END;
$$
;

-- 2. AD_Process
INSERT INTO AD_Process (
    AccessLevel, AD_Client_ID, AD_Org_ID, AD_Process_ID, AllowProcessReRun, Classname, CopyFromProcess,
    Created, CreatedBy, Description, EntityType, Help, IsActive, IsApplySecuritySettings,
    IsBetaFunctionality, IsDirectPrint, IsFormatExcelFile, IsNotifyUserAfterExecution, IsOneInstanceOnly,
    IsReport, IsTranslateExcelHeaders, IsUpdateExportDate, IsUseBPartnerLanguage, LockWaitTimeout, Name,
    PostgrestResponseFormat, RefreshAllAfterExecution, ShowHelp, SpreadsheetFormat, SQLStatement, Type,
    Updated, UpdatedBy, Value)
VALUES (
    '3', 0, 0, 585652 /*From ID Server*/, 'Y', 'de.metas.impexp.spreadsheet.process.ExportToSpreadsheetProcess',
    'N', TO_TIMESTAMP('2026-08-14 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'Zeigt den Prüfstatus aller USt-IdNr. der Organisation dieser Konfiguration: Anzahl je Status, fehlende USt-IdNr., veraltete Ergebnisse, nie geprüfte, außerhalb der VIES-Abdeckung, offen gebliebene Anfragen, ungültige mit offenen Belegen und doppelt verwendete USt-IdNr.',
    'D', NULL, 'Y', 'Y', 'N', 'N', 'Y', 'N', 'N',
    'Y', 'Y', 'N', 'Y', 0, 'USt-IdNr.-Prüfbericht',
    'json', 'N', 'Y', 'xls', 'SELECT * FROM VATaxID_Config_Report(@VATaxID_Config_ID@)', 'Excel',
    TO_TIMESTAMP('2026-08-14 09:00:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'VATaxID_Config_Report')
;

-- 3. AD_Process_Trl skeleton for every active system language
INSERT INTO AD_Process_Trl (AD_Language, AD_Process_ID, Description, Help, Name, IsTranslated, AD_Client_ID,
                            AD_Org_ID, Created, Createdby, Updated, UpdatedBy, IsActive)
SELECT l.AD_Language, t.AD_Process_ID, t.Description, t.Help, t.Name, 'N', t.AD_Client_ID, t.AD_Org_ID,
       t.Created, t.Createdby, t.Updated, t.UpdatedBy, 'Y'
FROM AD_Language l, AD_Process t
WHERE l.IsActive = 'Y' AND l.IsSystemLanguage = 'Y' AND t.AD_Process_ID = 585652
  AND NOT EXISTS (SELECT 1 FROM AD_Process_Trl tt WHERE tt.AD_Language = l.AD_Language AND tt.AD_Process_ID = t.AD_Process_ID)
;

-- 4. English override
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Name         = 'VAT-ID Check Report',
    Description  = 'Shows the check status of every VAT-ID of this configuration''s organisation: count per status, missing VAT-IDs, stale results, never-checked ones, ones outside VIES coverage, requests left without an answer, invalid ones with open documents, and VAT-IDs used on more than one partner.',
    Updated      = TO_TIMESTAMP('2026-08-14 09:00:10', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'en_US' AND AD_Process_ID = 585652
;

-- de_DE / de_CH already carry the base (German) text from the skeleton insert -- just flip IsTranslated,
-- mirroring every other _Trl treatment in this module.
UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-14 09:00:11', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_DE' AND AD_Process_ID = 585652
;

UPDATE AD_Process_Trl
SET IsTranslated = 'Y',
    Updated      = TO_TIMESTAMP('2026-08-14 09:00:12', 'YYYY-MM-DD HH24:MI:SS'),
    UpdatedBy    = 100
WHERE AD_Language = 'de_CH' AND AD_Process_ID = 585652
;

-- 5. AD_Table_Process -- exposes the process on the VATaxID_Config window/grid (table 542638), single
--    record only: this is a per-organisation report, not something that makes sense on a selection.
INSERT INTO AD_Table_Process (AD_Client_ID, AD_Org_ID, AD_Process_ID, AD_Table_ID, AD_Table_Process_ID,
                               Created, CreatedBy, EntityType, IsActive, Updated, UpdatedBy,
                               WEBUI_DocumentAction, WEBUI_IncludedTabTopAction, WEBUI_ViewAction,
                               WEBUI_ViewQuickAction, WEBUI_ViewQuickAction_Default)
VALUES (
    0, 0, 585652, 542638, 541664 /*From ID Server*/,
    TO_TIMESTAMP('2026-08-14 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100, 'D', 'Y',
    TO_TIMESTAMP('2026-08-14 09:01:00', 'YYYY-MM-DD HH24:MI:SS'), 100,
    'N', 'N', 'Y', 'N', 'N')
;
