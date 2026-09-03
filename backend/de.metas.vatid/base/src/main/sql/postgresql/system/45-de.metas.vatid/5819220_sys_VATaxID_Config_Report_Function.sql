-- Source DDL: backend/de.metas.vatid/base/src/main/sql/postgresql/ddl/functions/VATaxID_Config_Report.sql
--
-- Recreates the VAT-ID verification report function so that section 7 ("invalid VAT-ID with an open
-- order or an unprocessed invoice candidate") covers the address grain as well as the partner-header
-- grain. C_BPartner_Location.VATaxID / VATaxIDStatus are independent values, so an address whose own
-- VAT-ID is Invalid while that address sits on an open order or an unprocessed invoice candidate was
-- listed nowhere in the report -- on the one section whose entire purpose is compliance auditing. Every
-- other section of the report already covers both grains.
--
-- The new address branch matches orders on C_Order.C_BPartner_Location_ID -- the address that drives
-- tax determination -- and invoice candidates on C_Invoice_Candidate.Bill_Location_ID, that table's
-- only address FK. The pre-existing partner branch is unchanged: C_Order.C_BPartner_ID for orders and
-- C_Invoice_Candidate.Bill_BPartner_ID for invoice candidates, the latter because C_Invoice_Candidate
-- carries no other partner FK -- it is billing-oriented by design.
--
-- Supersedes the function definition shipped by 5819200_sys_VATaxID_Config_Report.sql. That script's
-- AD_Process / AD_Table_Process wiring is untouched and still current; only the function body changes,
-- and the RETURNS TABLE signature is identical, so the AD_Process SQLStatement keeps working unchanged.
--
-- IDs allocated from idserver.metas.de:
--   AD_MigrationScript 5819220 (this file's prefix)
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

        -- 7. Invalid VAT-ID -- partner header grain: the partner's own VAT-ID is invalid while the partner
        --    still has an open order or an unprocessed invoice candidate. A listing only; neither document
        --    is touched here (that is the check process's own, separate corrective step, guarded on
        --    DocStatus). Orders are matched on the order's own C_BPartner_ID -- the partner tax
        --    determination reads -- while invoice candidates carry only Bill_BPartner_ID, which is the
        --    billing-oriented grain that table is designed around.
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

        -- 7. The same at the address grain: an address whose OWN VAT-ID is invalid while that address is
        --    the one on an open order or on an unprocessed invoice candidate. An address-level VAT-ID is
        --    an independent value, so this case is invisible in the partner-header branch above. Orders
        --    are matched on the order's own C_BPartner_Location_ID (the address tax determination reads,
        --    not Bill_Location_ID); invoice candidates carry Bill_Location_ID as their only address FK.
        SELECT 70::NUMERIC, '7 - Invalid with open orders or ICs'::VARCHAR, 'Location'::VARCHAR,
               bp.C_BPartner_ID, bp.Value, bp.Name, bpl.C_BPartner_Location_ID, loc.City,
               bpl.VATaxID, bpl.VATaxIDStatus, bpl.VATaxIDCheckedAt, NULL::NUMERIC,
               ('open orders: ' || y.OpenOrders::TEXT
                   || ', unprocessed invoice candidates: ' || y.OpenICs::TEXT)::VARCHAR
        FROM C_BPartner_Location bpl
                 JOIN C_BPartner bp ON bp.C_BPartner_ID = bpl.C_BPartner_ID
                 LEFT JOIN C_Location loc ON loc.C_Location_ID = bpl.C_Location_ID
                 JOIN LATERAL (
            SELECT (SELECT COUNT(*)
                    FROM C_Order ord
                    WHERE ord.C_BPartner_Location_ID = bpl.C_BPartner_Location_ID
                      AND ord.DocStatus NOT IN ('CO', 'CL'))          AS OpenOrders,
                   (SELECT COUNT(*)
                    FROM C_Invoice_Candidate ic
                    WHERE ic.Bill_Location_ID = bpl.C_BPartner_Location_ID
                      AND ic.Processed = 'N')                        AS OpenICs
            ) y ON TRUE
        WHERE bp.AD_Org_ID = v_ad_org_id
          AND bpl.IsActive = 'Y'
          AND bpl.VATaxIDStatus = 'Invalid'
          AND (y.OpenOrders > 0 OR y.OpenICs > 0)

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
