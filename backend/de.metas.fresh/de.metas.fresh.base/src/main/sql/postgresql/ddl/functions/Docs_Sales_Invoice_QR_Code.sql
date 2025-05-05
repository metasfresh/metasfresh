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
        COALESCE(REPLACE(qr_iban, ' ', ''), REPLACE(iban, ' ', ''), '') || E'\n' || -- Account
        'K' || E'\n' || -- CR - AdressTyp = Combined address
        orgbp.name || E'\n' || --CR – Name
        orgl.address1 || E'\n' || --CR –Street and building number of P.O. Box
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
        TO_CHAR((CASE
                     WHEN cl.referenceNo IS NOT NULL THEN TO_NUMBER(SUBSTRING(cl.referenceNo, 3, 8), '999999999') + (TO_NUMBER(SUBSTRING(cl.referenceNo, 11, 2), '99') / 100)
                                                     ELSE i.GrandTotal
                 END), 'FM99999999.00') || E'\n' ||
        cur.iso_code || E'\n' ||
        'K' || E'\n' || -- UD– AdressTyp = Combined address
        SUBSTRING(bpartneraddress FROM 1 FOR POSITION(E'\n' IN bpartneraddress)) || --UD – Name
        COALESCE(l.address1, '') || E'\n' || --UD –Street and building number of P.O. Box
        COALESCE(l.postal, '') || ' ' || COALESCE(l.city, '') || E'\n' || -- UD Postal code and town
        E'\n' || --Do not fill in
        E'\n' || --Do not fill in
        c.countrycode || E'\n' || -- UD Country
        (case
             when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then 'QRR'
             when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null
                 then 'SCOR'
             else 'NON'
            end) || E'\n' || --ReferenceType
        (case
             when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then rn.ReferenceNo
             when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null
                 then 'RF' || orgbpb.sepa_creditoridentifier
             else ''
            end) || E'\n' || --Reference
        left(replace(replace(coalesce(i.description, ''),E'\n', ''), E'\r', ''), 140) || E'\n' ||--Unstructured message
        'EPD' || E'\n' || --Trailer

        '' || E'\n' --Billing information
           )                          AS QR_Code,

       COALESCE(iban, '')             AS CR_IBAN,
       COALESCE(qr_iban, '')          AS CR_QR_IBAN,
       orgbp.name                     AS CR_Name,
       (orgl.address1 || E'\n' ||
        COALESCE(orgl.postal, '') || ' ' || COALESCE(orgl.city, '') || E'\n' ||
        orgc.countrycode)             AS CR_Addres,

       (case
            when replace(qr_iban, ' ', '') is not null and rn.referenceNo is not null then
                                                    substring(rn.referenceNo, 1, 2) || ' ' ||
                                                    substring(rn.referenceNo, 3, 5) || ' ' ||
                                                    substring(rn.referenceNo, 8, 5) || ' ' ||
                                                    substring(rn.referenceNo, 13, 5) || ' ' ||
                                                    substring(rn.referenceNo, 18, 5) || ' ' ||
                                                    substring(rn.referenceNo, 23, 7)
            when replace(iban, ' ', '') is not null and replace(orgbpb.sepa_creditoridentifier, ' ', '') is not null
                then 'RF' || orgbpb.sepa_creditoridentifier
            else ''
           end)
                                               AS referenceno,
       i.bpartneraddress                       as DR_Address,
       (case
            when cl.referenceNo is not null then to_number(substring(cl.referenceNo, 3, 8), '999999999') +
                                                 (to_number(substring(cl.referenceNo, 11, 2), '99') / 100)
            else i.GrandTotal
           end)                                as Amount,
       cur.iso_code                            as currency,
       left(replace(replace(coalesce(i.description, ''),E'\n', ''), E'\r', ''), 140) as additional_informations,
       orgbpb.sepa_creditoridentifier          as SCOR,
       cl.referenceno                          as codeline,
       i.DocumentNo

FROM C_Invoice i
         JOIN c_bpartner_location bpl ON i.c_bpartner_location_id = bpl.c_bpartner_location_id
         JOIN c_location l ON bpl.c_location_id = l.c_location_id
         JOIN C_Country c ON l.c_country_id = c.c_country_id
         JOIN c_currency cur ON i.c_currency_id = cur.c_currency_id
    --- org infos
         JOIN AD_Org o ON i.AD_Org_ID = o.AD_Org_ID AND o.isActive = 'Y'
         JOIN AD_OrgInfo oi ON oi.AD_Org_ID = o.AD_Org_ID AND o.isActive = 'Y'
         JOIN c_bpartner_location org_bpl ON oi.orgbp_location_id = org_bpl.c_bpartner_location_id
         JOIN c_location orgl ON org_bpl.c_location_id = orgl.c_location_id
         JOIN C_Country orgc ON orgl.c_country_id = orgc.c_country_id
         LEFT JOIN C_BPartner orgbp ON o.AD_Org_ID = orgbp.AD_OrgBP_ID
         LEFT JOIN C_BP_Bankaccount orgbpb
                   ON orgbpb.C_BPartner_ID = orgbp.C_BPartner_ID AND orgbpb.IsEsrAccount = 'Y' AND orgbpb.isActive = 'Y'
    --- refno
         LEFT JOIN (SELECT rn.referenceNo, rnd.Record_ID
                    FROM C_ReferenceNo_Doc rnd
                             LEFT JOIN C_ReferenceNo rn ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
                    WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice' AND isActive = 'Y')
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
WHERE i.C_Invoice_ID = p_C_invoice_id
  AND i.isActive = 'Y'
$$
    LANGUAGE sql STABLE
;