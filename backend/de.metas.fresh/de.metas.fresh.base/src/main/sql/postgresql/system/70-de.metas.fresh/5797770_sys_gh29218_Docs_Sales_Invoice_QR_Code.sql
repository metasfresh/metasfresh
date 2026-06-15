DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code (IN p_C_invoice_id numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Invoice_QR_Code(IN p_C_invoice_id numeric)
    RETURNS TABLE
            (
                QR_Code                 text,
                CR_IBAN                 varchar,
                CR_QR_IBAN              varchar,
                CR_Name                 varchar,
                CR_Addres               varchar,
                referenceno             varchar,
                DR_Address              varchar,
                Amount                  numeric,
                currency                char,
                additional_informations text,
                SCOR                    varchar,
                codeline                varchar,
                documentno              varchar
            )
AS
$$
SELECT ('SPC' || E'\n' || --QRType
        '0200' || E'\n' || --Version
        '1' || E'\n' || --Coding

-- Account: prefer QR-IBAN (for QRR), fallback to IBAN (for SCOR/NON)
-- FIX: NULLIF converts empty string '' to NULL so the outer COALESCE
--      can correctly fall through to the IBAN branch when no QR-IBAN is configured.
--      Without NULLIF, inner COALESCE(..., '') always returns non-NULL (even ''),
--      causing the outer COALESCE to short-circuit and never reach the IBAN branch.
        COALESCE(
                NULLIF(REPLACE(COALESCE(orgbpb_invoice.qr_iban, orgbpb.qr_iban, ''), ' ', ''), ''),
                NULLIF(REPLACE(COALESCE(orgbpb_invoice.iban,    orgbpb.iban,    ''), ' ', ''), ''),
                ''
        ) || E'\n' ||

            -- CR block (Creditor = org/company)
        'K'                                                              || E'\n' || -- CR AddressType = Combined
        orgbp.name                                                       || E'\n' || -- CR Name
        COALESCE(orgl.address1, '')                                      || E'\n' || -- CR Street
        COALESCE(orgl.postal, '') || ' ' || COALESCE(orgl.city, '')     || E'\n' || -- CR Postal + City
        E'\n' ||                                                                     -- CR do not fill
        E'\n' ||                                                                     -- CR do not fill
        orgc.countrycode                                                 || E'\n' || -- CR Country

-- UCR block (Ultimate Creditor) — deprecated in v2.0, must be 7 empty lines
        E'\n' || -- UCR AddressType
        E'\n' || -- UCR Name
        E'\n' || -- UCR Street or address line 1
        E'\n' || -- UCR Building number or address line 2
        E'\n' || -- UCR Postal code
        E'\n' || -- UCR City
        E'\n' || -- UCR Country

-- Amount
-- FIX: use i.GrandTotal directly when cl.referenceNo is NULL,
--      and only parse from codeline when it exists.
--      FM9999999.00 suppresses leading spaces but keeps the integer part.
        TO_CHAR(
                CASE
                    WHEN cl.referenceNo IS NOT NULL
                        THEN TO_NUMBER(SUBSTRING(cl.referenceNo, 3, 8), '99999999')
                        + (TO_NUMBER(SUBSTRING(cl.referenceNo, 11, 2), '99') / 100)
                        ELSE i.GrandTotal
                END,
                'FM99999999990.00'  -- FIX: wider mask ensures integer part is never suppressed
        ) || E'\n' ||

        cur.iso_code || E'\n' ||

            -- UD block (Ultimate Debtor = bill recipient / customer)
        'K' || E'\n' || -- UD AddressType = Combined

-- FIX: subtract 1 from POSITION to exclude the embedded \n from bpartneraddress
--      before appending the explicit E'\n', avoiding a double newline
        SUBSTRING(bpartneraddress FROM 1 FOR POSITION(E'\n' IN bpartneraddress) - 1) || E'\n' || -- UD Name

        COALESCE(l.address1, '')                                         || E'\n' || -- UD Street
        COALESCE(l.postal, '') || ' ' || COALESCE(l.city, '')           || E'\n' || -- UD Postal + City
        E'\n' ||                                                                     -- UD do not fill
        E'\n' ||                                                                     -- UD do not fill
        c.countrycode                                                    || E'\n' || -- UD Country

-- Reference Type
-- FIX: QRR branch now correctly checks qr_iban specifically (not mixed with iban).
--      SCOR branch checks iban specifically (not qr_iban).
        CASE
            WHEN REPLACE(COALESCE(orgbpb_invoice.qr_iban, orgbpb.qr_iban, ''), ' ', '') <> ''
                AND rn.referenceNo IS NOT NULL
                THEN 'QRR'
            WHEN REPLACE(COALESCE(orgbpb_invoice.iban, orgbpb.iban, ''), ' ', '') <> ''
                AND REPLACE(COALESCE(orgbpb.sepa_creditoridentifier, ''), ' ', '') <> ''
                THEN 'SCOR'
                ELSE 'NON'
        END || E'\n' ||

            -- Reference Number
        CASE
            WHEN REPLACE(COALESCE(orgbpb_invoice.qr_iban, orgbpb.qr_iban, ''), ' ', '') <> ''
                AND rn.referenceNo IS NOT NULL
                THEN rn.referenceNo
            WHEN REPLACE(COALESCE(orgbpb_invoice.iban, orgbpb.iban, ''), ' ', '') <> ''
                AND REPLACE(COALESCE(orgbpb.sepa_creditoridentifier, ''), ' ', '') <> ''
                THEN 'RF' || orgbpb.sepa_creditoridentifier
                ELSE ''
        END || E'\n' ||

            -- Unstructured message (invoice description)
        de_metas_endcustomer_fresh_reports.format_invoice_description_for_qr_code(COALESCE(i.description, '')) || E'\n' ||

        'EPD' || E'\n' || -- Trailer (mandatory)
        ''    || E'\n'    -- Billing information (empty)
           ) AS QR_Code,

       -- CR_IBAN: FIX: consistent empty string fallback (was missing on CR_QR_IBAN)
       COALESCE(orgbpb_invoice.iban,    orgbpb.iban,    '')  AS CR_IBAN,
       COALESCE(orgbpb_invoice.qr_iban, orgbpb.qr_iban, '')  AS CR_QR_IBAN,  -- FIX: added '' fallback

       orgbp.name AS CR_Name,

       (COALESCE(orgl.address1, '') || E'\n' ||
        COALESCE(orgl.postal, '') || ' ' || COALESCE(orgl.city, '') || E'\n' ||
        orgc.countrycode)          AS CR_Addres,

       -- referenceno (human-readable formatted reference for printed slip)
       -- FIX: same QR-IBAN / IBAN split as above for consistency
       CASE
           WHEN REPLACE(COALESCE(orgbpb_invoice.qr_iban, orgbpb.qr_iban, ''), ' ', '') <> ''
               AND rn.referenceNo IS NOT NULL
               THEN SUBSTRING(rn.referenceNo, 1,  2) || ' ' ||
                    SUBSTRING(rn.referenceNo, 3,  5) || ' ' ||
                    SUBSTRING(rn.referenceNo, 8,  5) || ' ' ||
                    SUBSTRING(rn.referenceNo, 13, 5) || ' ' ||
                    SUBSTRING(rn.referenceNo, 18, 5) || ' ' ||
                    SUBSTRING(rn.referenceNo, 23, 7)
           WHEN REPLACE(COALESCE(orgbpb_invoice.iban, orgbpb.iban, ''), ' ', '') <> ''
               AND REPLACE(COALESCE(orgbpb.sepa_creditoridentifier, ''), ' ', '') <> ''
               THEN 'RF' || orgbpb.sepa_creditoridentifier
               ELSE ''
       END AS referenceno,

       i.bpartneraddress AS DR_Address,

       -- Amount column (same logic as in QR string)
       CASE
           WHEN cl.referenceNo IS NOT NULL
               THEN TO_NUMBER(SUBSTRING(cl.referenceNo, 3, 8), '99999999')
               + (TO_NUMBER(SUBSTRING(cl.referenceNo, 11, 2), '99') / 100)
               ELSE i.GrandTotal
       END AS Amount,

       cur.iso_code                                                                                            AS currency,
       de_metas_endcustomer_fresh_reports.format_invoice_description_for_qr_code(COALESCE(i.description, '')) AS additional_informations,
       orgbpb.sepa_creditoridentifier                                                                         AS SCOR,
       cl.referenceno                                                                                         AS codeline,
       i.DocumentNo

FROM C_Invoice i
         JOIN c_bpartner_location bpl ON i.c_bpartner_location_id = bpl.c_bpartner_location_id
         JOIN c_location l            ON bpl.c_location_id = l.c_location_id
         JOIN C_Country c             ON l.c_country_id = c.c_country_id
         JOIN c_currency cur          ON i.c_currency_id = cur.c_currency_id

    -- Org infos
         JOIN AD_Org o                ON i.AD_Org_ID = o.AD_Org_ID
         JOIN AD_OrgInfo oi           ON oi.AD_Org_ID = o.AD_Org_ID AND o.isActive = 'Y'
         JOIN c_bpartner_location org_bpl ON oi.orgbp_location_id = org_bpl.c_bpartner_location_id
         JOIN c_location orgl         ON org_bpl.c_location_id = orgl.c_location_id
         JOIN C_Country orgc          ON orgl.c_country_id = orgc.c_country_id
         LEFT JOIN C_BPartner orgbp   ON o.AD_Org_ID = orgbp.AD_OrgBP_ID

    -- FIX: replaced IsEsrAccount = 'Y' with LATERAL pick:
    --      prefer the account flagged isDefault = 'Y', fallback to first active one.
    --      LATERAL + LIMIT 1 prevents fan-out and avoids ambiguous multi-row joins.
         LEFT JOIN LATERAL (
    SELECT *
    FROM C_BP_Bankaccount
    WHERE C_BPartner_ID = orgbp.C_BPartner_ID
      AND isActive = 'Y'
      AND (qr_iban IS NOT NULL OR iban IS NOT NULL)
    ORDER BY isDefault DESC  -- 'Y' sorts before 'N'
    LIMIT 1
    ) orgbpb ON TRUE

    -- Invoice-level bank account override
         LEFT JOIN C_BP_Bankaccount orgbpb_invoice
                   ON orgbpb_invoice.c_bp_bankaccount_id = i.Org_BP_Account_ID

    -- QR reference number (InvoiceReference type)
         LEFT JOIN (SELECT rn.referenceNo, rnd.Record_ID
                    FROM C_ReferenceNo_Doc rnd
                             LEFT JOIN C_ReferenceNo rn
                                       ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
                    WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table
                                         WHERE TableName = 'C_Invoice' AND isActive = 'Y')
                      AND rn.C_ReferenceNo_Type_ID =
                          (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type
                           WHERE name = 'InvoiceReference' AND isActive = 'Y')
                      AND rnd.isActive = 'Y') rn ON i.C_Invoice_ID = rn.Record_ID

    -- Legacy ESR codeline (ESRReferenceNumber type) — used for amount extraction fallback
         LEFT JOIN (SELECT rn.referenceNo, rnd.Record_ID
                    FROM C_ReferenceNo_Doc rnd
                             LEFT JOIN C_ReferenceNo rn
                                       ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
                    WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table
                                         WHERE TableName = 'C_Invoice' AND isActive = 'Y')
                      AND rn.C_ReferenceNo_Type_ID =
                          (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type
                           WHERE name = 'ESRReferenceNumber' AND isActive = 'Y')
                      AND rnd.isActive = 'Y') cl ON i.C_Invoice_ID = cl.Record_ID

WHERE i.C_Invoice_ID = p_C_invoice_id
  AND i.isActive = 'Y'
$$
    LANGUAGE sql STABLE
;
