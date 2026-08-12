DROP FUNCTION IF EXISTS export_datev_csv_masterdata(
    text,
    text,
    integer,
    text,
    date,
    date
)
;

DROP FUNCTION IF EXISTS export_datev_csv_masterdata(
    text,
    integer,
    text,
    date,
    date
)
;

CREATE FUNCTION export_datev_csv_masterdata(
    p_partnertype text DEFAULT 'debitors',
    p_AD_Org_ID   integer DEFAULT 1000000,
    p_IsSOTrx     text DEFAULT 'Y',
    p_startdate   date DEFAULT '1970-01-01',
    p_enddate     date DEFAULT '2999-12-31'
)
    RETURNS table
            (
                Konto       text, -- Account number (Debitor/Creditor)
                Matchcode   text, -- Search keyword (typically partner code)
                Name1       text, -- Company / Person Name
                Strasse     text, -- Street
                Plz         text, -- Postal Code
                Ort         text, -- City
                Land        text, -- Country code (ISO alpha-2)
                Telefon     text, -- Phone number
                Email_Datev       text, -- Email address
                Ustid       text, -- VAT ID
                Zahlungsbed text  -- Payment terms
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT DISTINCT
            -- "Konto" (Account Number) for Debitors/Creditors
            CASE
                WHEN p_partnertype = 'creditors' THEN
                    -- Explicitly cast to text
                    creditorid::text
                WHEN p_partnertype = 'debitors'  THEN
                    -- Explicitly cast to text
                    debtorid::text
            END                         AS "Konto",

            -- "Matchcode" (Search keyword) - explicitly cast to text
            bp.value::text              AS "Matchcode",

            -- "Name1" (Partner Name)
            bp.name::text               AS "Name1",

            -- "Strasse" (Street)
            l.address1::text            AS "Strasse",

            -- "PLZ" (Postal Code)
            l.postal::text              AS "Plz",

            -- "Ort" (City)
            l.city::text                AS "Ort",

            -- "Land" (Country code, ISO alpha-2)
            c.countrycode::text         AS "Land",

            -- "Telefon" (Phone number)
            COALESCE(u.phone, '')::text AS "Telefon", -- Default to empty if no phone

            -- "Email" (Email address)
            u.email::text               AS "Email_Datev",

            -- "UStId" (VAT ID)
            bp.vataxid::text            AS "Ustid",

            -- "Zahlungsbed" (Payment Terms)
            bp_pay.value::text          AS "Zahlungsbed"

        FROM c_bpartner bp
                 LEFT JOIN c_bpartner_location bpl
                           ON bpl.c_bpartner_location_id = COALESCE(
                                   (SELECT ci.c_bpartner_location_id
                                    FROM c_invoice ci
                                             JOIN c_bpartner_location bpl2 ON ci.c_bpartner_location_id = bpl2.c_bpartner_location_id
                                    WHERE ci.c_bpartner_id = bp.c_bpartner_id
                                      AND bpl2.isactive = 'Y'
                                    ORDER BY ci.dateinvoiced DESC
                                    LIMIT 1),
                                   (SELECT bpl2.c_bpartner_location_id
                                    FROM c_bpartner_location bpl2
                                    WHERE bpl2.c_bpartner_id = bp.c_bpartner_id
                                      AND bpl2.isactive = 'Y'
                                    ORDER BY isbilltodefault DESC, isbillto DESC
                                    LIMIT 1)
                                                           )
                 LEFT JOIN c_location l ON l.c_location_id = bpl.c_location_id
                 LEFT JOIN c_country c ON c.c_country_id = l.c_country_id
                 LEFT JOIN ad_user u ON u.c_bpartner_id = bp.c_bpartner_id
            AND u.isbilltocontact_default = 'Y'
            AND u.isactive = 'Y'
                 LEFT JOIN ad_ref_list bp_pay ON bp_pay.ad_reference_id = 195 AND bp_pay.value = bp.paymentrule
        WHERE TRUE
          AND bp.ad_org_id = p_AD_Org_ID
          AND EXISTS (SELECT 1
                      FROM c_invoice ci
                      WHERE ci.issotrx = p_IsSOTrx
                        AND ci.c_bpartner_id = bp.c_bpartner_id
                        AND ci.dateacct BETWEEN p_startdate AND p_enddate);
END;
$$
;

ALTER FUNCTION export_datev_csv_masterdata(
    text, integer, text, date, date
    ) OWNER TO metasfresh
;


/*
SELECT *
FROM export_datev_csv_masterdata('debitors', 1000000, 'Y', '2023-01-01', '2025-12-31'
     )
;
*/
