DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Dunning_QR_Code (IN p_C_DunningDoc_ID numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.Docs_Sales_Dunning_QR_Code(IN p_C_DunningDoc_ID numeric)
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
                documentno              varchar,
                zuordnung               numeric
            )
AS
$$
SELECT ('SPC' || E'\n' || --QRType
        '0200' || E'\n' || --Version
        '1' || E'\n' || --Coding
        COALESCE(REPLACE(qr_iban, ' ', ''), REPLACE(iban, ' ', ''), '') || E'\n' || -- Account
        'K' || E'\n' || -- CR - AdressTyp = Combined address
        (CASE WHEN orgbp.IsCompany = 'Y' THEN orgbp.Companyname ELSE orgbp.name END) || E'\n' || --CR – Name
        COALESCE(orgl.address1, '') || E'\n' || --CR –Street and building number of P.O. Box
        COALESCE(orgl.postal, '') || ' ' || COALESCE(orgl.city, '') || E'\n' || -- CR Postal code and town
        E'\n' || --Do not fill in
        E'\n' || --Do not fill in
        orgc.countrycode || E'\n' || -- CR Country
        E'\n' || -- E'\n' || --UCR – AdressTyp
        E'\n' || -- E'\n' || --UCR – Name
        E'\n' || -- E'\n' || --UCR - Street or address line 1
        E'\n' || -- E'\n' || --UCR – Building number or address line 2
        E'\n' || -- E'\n' || --UCR – Postal code
        E'\n' || -- E'\n' || --UCR – City
        E'\n' || -- E'\n' || --UCR – Country
        TO_CHAR((i.grandtotal - COALESCE(y.zuordnung, 0)), 'FM99990.00') || E'\n' ||
        cur.iso_code || E'\n' ||
        'K' || E'\n' || -- UD– AdressTyp = Combined address
        (CASE
             WHEN bp.IsCompany = 'Y' THEN bp.companyname
                                     ELSE (COALESCE(u.FirstName, '') || ' ' || COALESCE(u.LastName, ''))
         END)
            || E'\n' || --UD – Name
        COALESCE(l.address1, '') || E'\n' || --UD –Street and building number of P.O. Box
        COALESCE(l.postal, '') || ' ' || COALESCE(l.city, '') || E'\n' || -- UD Postal code and town
        E'\n' || --Do not fill in
        E'\n' || --Do not fill in
        c.countrycode || E'\n' || -- UD Country
        (CASE
             WHEN NULLIF(TRIM(orgbpb.qr_iban), '') IS NOT NULL AND rn.referenceNo IS NOT NULL THEN 'QRR'
             WHEN NULLIF(TRIM(orgbpb.iban), '') IS NOT NULL AND
                  NULLIF(TRIM(orgbpb.sepa_creditoridentifier), '') IS NOT NULL
                                                                                              THEN 'SCOR'
                                                                                              ELSE 'NON'
         END) || E'\n' || --ReferenceType
        (CASE
             WHEN NULLIF(TRIM(orgbpb.qr_iban), '') IS NOT NULL AND rn.referenceNo IS NOT NULL THEN rn.ReferenceNo
             WHEN NULLIF(TRIM(orgbpb.iban), '') IS NOT NULL AND
                  NULLIF(TRIM(orgbpb.sepa_creditoridentifier), '') IS NOT NULL
                                                                                              THEN 'RF' || orgbpb.sepa_creditoridentifier
                                                                                              ELSE ''
         END) || E'\n' || --Reference
        (CASE
             WHEN i.DocumentNo IS NOT NULL THEN ('Rechnungs-Nr. ' || i.DocumentNo)
                                           ELSE ''
         END) || E'\n' ||--Unstructured message
        'EPD' || E'\n' || --Trailer

        '' || E'\n' --Billing information
           )                                                                        AS QR_Code,
       COALESCE(iban, '')                                                           AS CR_IBAN,
       COALESCE(qr_iban, '')                                                        AS CR_QR_IBAN,
       (CASE WHEN orgbp.IsCompany = 'Y' THEN orgbp.Companyname ELSE orgbp.name END) AS CR_Name,
       ((CASE WHEN NULLIF(TRIM(orgl.address1), '') IS NOT NULL THEN (orgl.address1 || E'\n') ELSE '' END) ||
        COALESCE(orgl.postal, '') || ' ' || COALESCE(orgl.city, '') || E'\n' ||
        orgc.countrycode)                                                           AS CR_Addres,

       (CASE
            WHEN NULLIF(TRIM(qr_iban), '') IS NOT NULL AND rn.referenceNo IS NOT NULL THEN
                SUBSTRING(rn.referenceNo, 1, 2) || ' ' ||
                SUBSTRING(rn.referenceNo, 3, 5) || ' ' ||
                SUBSTRING(rn.referenceNo, 8, 5) || ' ' ||
                SUBSTRING(rn.referenceNo, 13, 5) || ' ' ||
                SUBSTRING(rn.referenceNo, 18, 5) || ' ' ||
                SUBSTRING(rn.referenceNo, 23, 7)
            WHEN NULLIF(TRIM(iban), '') IS NOT NULL AND NULLIF(TRIM(orgbpb.sepa_creditoridentifier), '') IS NOT NULL
                                                                                      THEN 'RF' || orgbpb.sepa_creditoridentifier
                                                                                      ELSE ''
        END)
                                                                                    AS referenceno,

       ((CASE
             WHEN bp.IsCompany = 'Y' THEN bp.companyname
                                     ELSE (COALESCE(u.FirstName, '') || ' ' || COALESCE(u.LastName, ''))
         END) || E'\n' ||
        COALESCE(l.address1, '') || E'\n' ||
        COALESCE(l.postal, '') || ' ' || COALESCE(l.city, ''))                      AS DR_Address,
       i.grandtotal - COALESCE(y.zuordnung, 0)                                      AS Amount,
       cur.iso_code                                                                 AS currency,
       (CASE
            WHEN i.DocumentNo IS NOT NULL THEN ('Rechnungs-Nr. ' || i.DocumentNo)
                                          ELSE ''
        END)                                                                        AS additional_informations,
       orgbpb.sepa_creditoridentifier                                               AS SCOR,
       cl.referenceno                                                               AS codeline,
       i.DocumentNo,
       y.zuordnung

FROM C_Invoice i
         JOIN C_BPartner bp
              ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT JOIN AD_User u ON i.AD_User_ID = u.AD_user_ID
         JOIN c_bpartner_location bpl ON i.c_bpartner_location_id = bpl.c_bpartner_location_id
         JOIN c_location l ON bpl.c_location_id = l.c_location_id
         JOIN C_Country c ON l.c_country_id = c.c_country_id
         JOIN c_currency cur ON i.c_currency_id = cur.c_currency_id
    --- org infos
         JOIN AD_Org o ON i.AD_Org_ID = o.AD_Org_ID
         JOIN AD_OrgInfo oi ON oi.AD_Org_ID = o.AD_Org_ID
         JOIN c_bpartner_location org_bpl ON oi.orgbp_location_id = org_bpl.c_bpartner_location_id
         JOIN c_location orgl ON org_bpl.c_location_id = orgl.c_location_id
         JOIN C_Country orgc ON orgl.c_country_id = orgc.c_country_id
         LEFT JOIN C_BPartner orgbp ON o.AD_Org_ID = orgbp.AD_OrgBP_ID
         LEFT JOIN C_BP_Bankaccount orgbpb
                   ON orgbpb.C_BPartner_ID = orgbp.C_BPartner_ID AND orgbpb.IsEsrAccount = 'Y'
    --- refno
         LEFT JOIN (SELECT rn.referenceNo, rnd.Record_ID
                    FROM C_ReferenceNo_Doc rnd
                             LEFT JOIN C_ReferenceNo rn ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID
                    WHERE AD_Table_ID = get_table_id('C_Invoice')
                      AND rn.C_ReferenceNo_Type_ID =
                          (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type WHERE name = 'InvoiceReference' AND isActive = 'Y')
                      AND rnd.isActive = 'Y') rn ON i.C_Invoice_ID = rn.Record_ID

    -- support old codeline
         LEFT JOIN (SELECT rn.referenceNo, rnd.Record_ID
                    FROM C_ReferenceNo_Doc rnd
                             LEFT JOIN C_ReferenceNo rn ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
                    WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice' AND isActive = 'Y')
                      AND rn.C_ReferenceNo_Type_ID =
                          (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type WHERE name = 'ESRReferenceNumber' AND isActive = 'Y')
                      AND rnd.isActive = 'Y') cl ON i.C_Invoice_ID = cl.Record_ID

    -- check allocated amount
         LEFT JOIN LATERAL (SELECT SUM(al.amount) AS zuordnung, al.c_invoice_id
                            FROM c_allocationline al
                            WHERE al.C_Invoice_ID = i.C_Invoice_ID
                            GROUP BY al.c_invoice_id) y ON y.c_invoice_id = i.c_invoice_id


         LEFT JOIN C_Dunning_Candidate cand
                   ON i.C_Invoice_ID = cand.Record_ID AND cand.AD_Table_ID = Get_Table_ID('C_Invoice')
         LEFT JOIN C_DunningDoc_Line_Source dls ON cand.C_Dunning_Candidate_ID = dls.C_Dunning_Candidate_ID
         LEFT JOIN C_DunningDoc_line dl ON dls.C_DunningDoc_Line_ID = dl.C_DunningDoc_Line_ID
         LEFT JOIN C_DunningDoc dd ON dl.C_DunningDoc_ID = dd.C_DunningDoc_ID
WHERE dd.C_DunningDoc_ID = p_C_DunningDoc_ID

$$
    LANGUAGE sql STABLE
;


