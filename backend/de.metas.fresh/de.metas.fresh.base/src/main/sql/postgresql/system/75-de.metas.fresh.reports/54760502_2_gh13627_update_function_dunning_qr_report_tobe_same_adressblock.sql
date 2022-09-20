DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.Docs_Sales_Dunning_QR_Code (IN p_C_DunningDoc_ID numeric);
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
select ('SPC' || E'\n' || --QRType
        '0200' || E'\n' || --Version
        '1' || E'\n' || --Coding
        COALESCE(replace(qr_iban, ' ', ''), replace(iban, ' ', ''), '') || E'\n' || -- Account
        'K' || E'\n' || -- CR - AdressTyp = Combined address
        (case when orgbp.IsCompany = 'Y' then orgbp.Companyname else orgbp.name end) || E'\n' || --CR – Name
        coalesce(orgl.address1, '') || E'\n' || --CR –Street and building number of P.O. Box
        coalesce(orgl.postal, '') || ' ' || coalesce(orgl.city, '') || E'\n' || -- CR Postal code and town
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
        to_char((i.grandtotal - coalesce(y.zuordnung, 0)), 'FM99990.00') || E'\n' ||
        cur.iso_code || E'\n' ||
        'K' || E'\n' || -- UD– AdressTyp = Combined address
        (case
             when bp.IsCompany = 'Y' then bp.companyname
             else (coalesce(u.FirstName, '') || ' ' || coalesce(u.LastName, '')) end)
            || E'\n' || --UD – Name
        coalesce(l.address1, '') || E'\n' || --UD –Street and building number of P.O. Box
        coalesce(l.postal, '') || ' ' || coalesce(l.city, '') || E'\n' || -- UD Postal code and town
        E'\n' || --Do not fill in
        E'\n' || --Do not fill in
        c.countrycode || E'\n' || -- UD Country
        (case
             when nullif(trim(orgbpb.qr_iban), '') is not null and rn.referenceNo is not null then 'QRR'
             when nullif(trim(orgbpb.iban), '') is not null and
                  nullif(trim(orgbpb.sepa_creditoridentifier), '') is not null
                 then 'SCOR'
             else 'NON'
            end) || E'\n' || --ReferenceType
        (case
             when nullif(trim(orgbpb.qr_iban), '') is not null and rn.referenceNo is not null then rn.ReferenceNo
             when nullif(trim(orgbpb.iban), '') is not null and
                  nullif(trim(orgbpb.sepa_creditoridentifier), '') is not null
                 then 'RF' || orgbpb.sepa_creditoridentifier
             else ''
            end) || E'\n' || --Reference
        coalesce(i.description, '') || E'\n' ||--Unstructured message
        'EPD' || E'\n' || --Trailer

        '' || E'\n' --Billing information
           )                                                                        as QR_Code,
       COALESCE(iban, '')                                                           as CR_IBAN,
       COALESCE(qr_iban, '')                                                        as CR_QR_IBAN,
       (case when orgbp.IsCompany = 'Y' then orgbp.Companyname else orgbp.name end) As CR_Name,
       ((case when nullif(trim(orgl.address1), '') is not null then (orgl.address1 || E'\n') else '' end) ||
        coalesce(orgl.postal, '') || ' ' || coalesce(orgl.city, '') || E'\n' ||
        orgc.countrycode)                                                           as CR_Addres,

       (case
            when nullif(trim(qr_iban), '') is not null and rn.referenceNo is not null then
                                                    substring(rn.referenceNo, 1, 2) || ' ' ||
                                                    substring(rn.referenceNo, 3, 5) || ' ' ||
                                                    substring(rn.referenceNo, 8, 5) || ' ' ||
                                                    substring(rn.referenceNo, 13, 5) || ' ' ||
                                                    substring(rn.referenceNo, 18, 5) || ' ' ||
                                                    substring(rn.referenceNo, 23, 7)
            when nullif(trim(iban), '') is not null and nullif(trim(orgbpb.sepa_creditoridentifier), '') is not null
                then 'RF' || orgbpb.sepa_creditoridentifier
            else ''
           end)
                                                                                    AS referenceno,

       i.bpartneraddress                                                            as DR_Address,
       i.grandtotal - coalesce(y.zuordnung, 0)                                      as Amount,
       cur.iso_code                                                                 as currency,
       i.description                                                                as additional_informations,
       orgbpb.sepa_creditoridentifier                                               as SCOR,
       cl.referenceno                                                               as codeline,
       i.DocumentNo,
       y.zuordnung

from C_Invoice i
         JOIN C_BPartner bp
              ON i.C_BPartner_ID = bp.C_BPartner_ID
         LEFT JOIN AD_User u on i.AD_User_ID = u.AD_user_ID
         JOIN c_bpartner_location bpl on i.c_bpartner_location_id = bpl.c_bpartner_location_id
         JOIN c_location l on bpl.c_location_id = l.c_location_id
         JOIN C_Country c on l.c_country_id = c.c_country_id
         JOIN c_currency cur on i.c_currency_id = cur.c_currency_id
    --- org infos
         JOIN AD_Org o ON i.AD_Org_ID = o.AD_Org_ID
         JOIN AD_OrgInfo oi ON oi.AD_Org_ID = o.AD_Org_ID
         join c_bpartner_location org_bpl on oi.orgbp_location_id = org_bpl.c_bpartner_location_id
         join c_location orgl on org_bpl.c_location_id = orgl.c_location_id
         join C_Country orgc on orgl.c_country_id = orgc.c_country_id
         LEFT JOIN C_BPartner orgbp ON o.AD_Org_ID = orgbp.AD_OrgBP_ID
         LEFT JOIN C_BP_Bankaccount orgbpb
                   ON orgbpb.C_BPartner_ID = orgbp.C_BPartner_ID AND orgbpb.IsEsrAccount = 'Y'
    --- refno
         LEFT JOIN (
    SELECT rn.referenceNo, rnd.Record_ID
    FROM C_ReferenceNo_Doc rnd
             LEFT JOIN C_ReferenceNo rn ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID
    WHERE AD_Table_ID = get_table_id('C_Invoice')
      AND rn.C_ReferenceNo_Type_ID =
          (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type WHERE name = 'InvoiceReference' AND isActive = 'Y')
      AND rnd.isActive = 'Y'
) rn ON i.C_Invoice_ID = rn.Record_ID

    -- support old codeline
         LEFT JOIN (
    SELECT rn.referenceNo, rnd.Record_ID
    FROM C_ReferenceNo_Doc rnd
             LEFT JOIN C_ReferenceNo rn ON rnd.C_ReferenceNo_ID = rn.C_ReferenceNo_ID AND rn.isActive = 'Y'
    WHERE AD_Table_ID = (SELECT AD_Table_ID FROM AD_Table WHERE TableName = 'C_Invoice' AND isActive = 'Y')
      AND rn.C_ReferenceNo_Type_ID =
          (SELECT C_ReferenceNo_Type_ID FROM C_ReferenceNo_Type WHERE name = 'ESRReferenceNumber' AND isActive = 'Y')
      AND rnd.isActive = 'Y'
) cl ON i.C_Invoice_ID = cl.Record_ID

-- check allocated amount
         LEFT JOIN LATERAL (select sum(al.amount) AS zuordnung, al.c_invoice_id
                            from c_allocationline al
                            where al.C_Invoice_ID = i.C_Invoice_ID
                            GROUP BY al.c_invoice_id) y on y.c_invoice_id = i.c_invoice_id


         LEFT JOIN C_Dunning_Candidate cand
                   ON i.C_Invoice_ID = cand.Record_ID AND cand.AD_Table_ID = Get_Table_ID('C_Invoice')
         LEFT JOIN C_DunningDoc_Line_Source dls ON cand.C_Dunning_Candidate_ID = dls.C_Dunning_Candidate_ID
         LEFT JOIN C_DunningDoc_line dl ON dls.C_DunningDoc_Line_ID = dl.C_DunningDoc_Line_ID
         LEFT JOIN C_DunningDoc dd ON dl.C_DunningDoc_ID = dd.C_DunningDoc_ID
WHERE dd.C_DunningDoc_ID = p_C_DunningDoc_ID

$$
    LANGUAGE sql STABLE
;

