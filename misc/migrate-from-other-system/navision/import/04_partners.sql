/*
 * #%L
 * work-metas
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


DROP TABLE IF EXISTS migration_data.i_bpartner_vendor;
CREATE TABLE migration_data.i_bpartner_vendor AS
SELECT 1000000                                                           AS ad_client_id,
       1000000                                                           AS ad_org_id,
       'Y'                                                               AS isactive,
       now()                                                             AS created,
       99                                                                AS createdby,
       now()                                                             AS updated,
       99                                                                AS updatedby,
       'N'                                                               AS i_isimported,
       NULL                                                              AS i_errormsg,
       NULL::numeric                                                     AS c_bpartner_id,
       v."No_"                                                           AS bpvalue,
       LEFT(v."Name", 60)                                                AS name,
       LEFT(v."Name 2", 60)                                              AS name2,
       LEFT(v."Search Name", 255)                                        AS description,
       NULL                                                              AS duns,
       LEFT(v."VAT Registration No_", 20)                                AS taxid,
       NULL                                                              AS naics,
       NULL                                                              AS groupvalue,
       NULL::numeric                                                     AS c_bp_group_id,
       NULL::numeric                                                     AS c_bpartner_location_id,
       LEFT(v."Address", 60)                                             AS address1,
       LEFT(v."Address 2", 60)                                           AS address2,
       LEFT(v."Post Code", 10)                                           AS postal,
       NULL                                                              AS postal_add,
       LEFT(v."City", 60)                                                AS city,
       NULL::numeric                                                     AS c_region_id,
       NULL                                                              AS regionname,
       NULL::numeric                                                     AS c_country_id,
       COALESCE(NULLIF(TRIM(LEFT(v."Country_Region Code", 2)), ''),
                'DE')                                                    AS countrycode, -- Fallback as country is mandatory if we have an address
       LEFT(con."Job Title", 40)                                         AS title,
       NULL                                                              AS contactname,
       LEFT(con."Person Information", 255)                               AS contactdescription,
       NULL                                                              AS comments,
       LEFT(v."Phone No_", 40)                                           AS phone,
       LEFT(con."Mobile Phone No_", 40)                                  AS phone2,
       LEFT(v."Fax No_", 40)                                             AS fax,
       LEFT(v."E-Mail", 60)                                              AS email,
       NULL                                                              AS password,
       con."Date of Birth"                                               AS birthday,
       NULL::numeric                                                     AS c_greeting_id,
       NULLIF(TRIM(UPPER(SUBSTRING(LEFT(con."Salutation Code", 60), 1, 1))
           || LOWER(SUBSTRING(LEFT(con."Salutation Code", 60), 2))), '') AS bpcontactgreeting,
       NULL                                                              AS processing,
       'N'                                                               AS processed,
       NULL::numeric                                                     AS ad_user_id,
       NULL::numeric                                                     AS r_interestarea_id,
       NULL                                                              AS interestareaname,
       'N'                                                               AS iscustomer,
       'N'                                                               AS isemployee,
       'Y'                                                               AS isvendor,
       LEFT(v."Name", 255)                                               AS companyname, -- legal name is always stored here in navision
       LEFT(con."First Name", 255)                                       AS firstname,
       CASE
           WHEN NULLIF(TRIM(LEFT(con."Surname", 255)), '') IS NULL
               AND NULLIF(TRIM(LEFT(con."First Name", 255)), '') IS NULL
               THEN con."Name"
           ELSE LEFT(con."Surname", 255)
           END                                                           AS lastname,
       'Y'                                                               AS isbillto,
       'Y'                                                               AS isshipto,
       'Y'                                                               AS isbilltodefault,
       'Y'                                                               AS isshiptodefault,
       'Y'                                                               AS isdefaultcontact,
       'Y'                                                               AS isbilltocontact_default,
       'Y'                                                               AS isshiptocontact_default,
       CASE
           WHEN v."Language Code" IS NOT NULL AND TRIM(v."Language Code") <> '' THEN
               LOWER(LEFT(v."Language Code", 2)) || '_' ||
               UPPER(
                       COALESCE(
                               NULLIF(TRIM(v."Country_Region Code"), ''),
                               (SELECT l.countrycode
                                FROM ad_language l
                                WHERE l.languageiso = LOWER(LEFT(v."Language Code", 2))
                                LIMIT 1)-- Fallback can be wrong e.g. 'de_DE' 'de_CH'
                       )
               )
           WHEN v."Country_Region Code" IS NOT NULL AND TRIM(v."Country_Region Code") <> '' THEN
               (SELECT l.languageiso
                FROM ad_language l
                WHERE l.countrycode = UPPER(LEFT(v."Country_Region Code", 2))
                LIMIT 1) || '_' || UPPER(LEFT(v."Country_Region Code", 2))-- Fallback can be wrong e.g. 'fr_CH' 'de_CH'
           END                                                           AS ad_language,
       NULL                                                              AS address4,
       NULL                                                              AS address3,
       LEFT(v."Name 3", 60)                                              AS name3,
       'N'                                                               AS issepasigned,
       NULL                                                              AS pharmaproductpermlaw52,
       'N'                                                               AS ispharmaciepermission,
       NULL                                                              AS paymentrule,
       bank_accounts.iban                                                AS iban,
       bank_accounts.swiftcode                                           AS swiftcode,
       NULL::numeric                                                     AS c_bp_bankaccount_id,
       NULL::timestamp
           with time zone                                                AS firstsale,
       NULL                                                              AS invoiceschedule,
       CASE pm."Code"
           WHEN 'SEPA-B2B' THEN 'D'
           WHEN 'SEPA K' THEN 'T'
           END                                                           AS paymentrulepo,
       NULL::numeric                                                     AS po_paymentterm_id,
       LEFT(pt."Description", 255)                                       AS paymentterm,
       NULL                                                              AS shortdescription,
       NULL::numeric                                                     AS c_invoiceschedule_id,
       NULL::numeric                                                     AS c_dataimport_id,
       NULL                                                              AS salesresponsible,
       NULL                                                              AS purchasegroup,
       NULL                                                              AS associationmembership,
       NULL                                                              AS shipmentpermissionpharma_old,
       NULL                                                              AS permissionpharmatype,
       NULL::numeric                                                     AS shelflifemindays,
       NULL                                                              AS weekendopeningtimes,
       'N'                                                               AS isdecider,
       'N'                                                               AS ismanagement,
       'N'                                                               AS ismultiplier,
       0                                                                 AS debtorid,
       LEFT(v."County", 60)                                              AS region,
       NULL                                                              AS c_bpartner_memo,
       NULL                                                              AS salesgroup,
       NULL::numeric                                                     AS creditlimit,
       NULL::numeric                                                     AS creditlimit2,
       NULL                                                              AS ad_user_memo1,
       NULL                                                              AS ad_user_memo2,
       NULL                                                              AS ad_user_memo3,
       NULL::numeric                                                     AS c_bp_printformat_id,
       NULL::numeric                                                     AS c_aggregation_id,
       NULL                                                              AS aggregationname,
       NULL::numeric                                                     AS c_job_id,
       NULL                                                              AS jobname,
       CASE WHEN v."Blocked" = 0 THEN 'Y' ELSE 'N' END                   AS isactivestatus,
       NULL::numeric                                                     AS m_shipper_id,
       NULL                                                              AS shippername,
       NULL                                                              AS deliveryviarule,
       NULL                                                              AS vendorcategory,
       NULL                                                              AS qualification,
       NULL::numeric                                                     AS msv3_vendor_config_id,
       NULL                                                              AS msv3_baseurl,
       NULL                                                              AS msv3_vendor_config_name,
       NULL                                                              AS vendorresponsible,
       NULL                                                              AS minimumordervalue,
       NULL                                                              AS retourfax,
       NULL                                                              AS pharma_phone,
       NULL                                                              AS contacts,
       v."No_"::numeric                                                  AS creditorid,
       NULL                                                              AS customernoatvendor,
       NULL                                                              AS printformat_name,
       NULL::numeric                                                     AS ad_printformat_id,
       0                                                                 AS PrintFormat_DocumentCopies_Override,
       'N'                                                               AS IsSetPrintFormat_C_BPartner_Location,
       NULL                                                              AS PrintFormat_DocBaseType,
       NULL                                                              AS pharma_fax,
       NULL                                                              AS statusinfo,
       NULL                                                              AS ad_user_memo4,
       NULL                                                              AS gln,
       ('001 - ' || con."No_")                                           AS ad_user_externalid,
       ('001 - ' || v."No_")                                             AS c_bpartner_externalid,
       ('001 - ' || v."No_")                                             AS c_bpartner_location_externalid,
       LEFT(v."Home Page", 120)                                          AS url,
       NULL::numeric                                                     AS m_pricingsystem_id,
       NULL::numeric                                                     AS po_pricingsystem_id,
       NULL                                                              AS pricingsystem_value,
       NULL                                                              AS po_pricingsystem_value,
       NULL                                                              AS memo_invoicing,
       NULL                                                              AS pobox,
       NULL                                                              AS countryname,
       NULL                                                              AS memo_delivery,
       NULL                                                              AS paymenttermvalue,
       NULL::numeric                                                     AS c_paymentterm_id,
       NULL                                                              AS url3,
       NULL                                                              AS globalid,
       NULL::numeric                                                     AS ad_issue_id,
       NULL::numeric                                                     AS c_dataimport_run_id,
       NULL                                                              AS i_linecontent,
       NULL::numeric                                                     AS i_lineno,
       NULL                                                              AS IsUserInvoiceEmailEnabled,
       NULL                                                              AS PaymentRuleInfo,
       pm."Description"                                                  AS PaymentRulePOInfo,
       NULL::numeric                                                     AS Location_M_Shipper_ID,
       NULL                                                              AS LocationShipperName,
       'Y'                                                               AS isUpdateLocationName,
       cus."Name"

FROM migration_data."Navision$Vendor" v
         LEFT JOIN migration_data."Navision$Payment Method" pm
                   ON v."Payment Method Code" = pm."Code"
         LEFT JOIN migration_data."Navision$Contact Business Relation" rel
                   ON v."No_" = rel."No_" AND rel."Business Relation Code" = 'KRED'
         LEFT JOIN migration_data."Navision$Contact" con
                   ON rel."Contact No_" = con."No_"
         LEFT JOIN migration_data."Navision$Payment Terms" pt
                   ON v."Payment Terms Code" = pt."Code"
         LEFT JOIN migration_data."Navision$Customer" cus
                   ON cus."Name" = v."Name"
         LEFT JOIN LATERAL (SELECT LEFT(ba."IBAN", 255)       AS iban,
                                   LEFT(ba."SWIFT Code", 255) AS swiftcode
                            FROM migration_data."Navision$Vendor Bank Account" ba
                            WHERE v."No_" = ba."Vendor No_"
                              AND NULLIF(TRIM(ba."IBAN"), '') IS NOT NULL
    ) AS bank_accounts ON TRUE
WHERE cus."Name" IS NULL
;

INSERT INTO i_bpartner (i_bpartner_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        i_isimported, i_errormsg, c_bpartner_id, bpvalue, name, name2, description, duns, taxid, naics,
                        groupvalue, c_bp_group_id, c_bpartner_location_id, address1, address2, postal, postal_add, city,
                        c_region_id, regionname, c_country_id, countrycode, title, contactname, contactdescription,
                        comments, phone, phone2, fax, email, password, birthday, c_greeting_id, bpcontactgreeting,
                        processing, processed, ad_user_id, r_interestarea_id, interestareaname, iscustomer, isemployee,
                        isvendor, companyname, firstname, lastname, isbillto, isshipto, isbilltodefault,
                        isshiptodefault, isdefaultcontact, isbilltocontact_default, isshiptocontact_default,
                        ad_language, address4, address3, name3, issepasigned, pharmaproductpermlaw52,
                        ispharmaciepermission, paymentrule, iban, swiftcode, c_bp_bankaccount_id, firstsale,
                        invoiceschedule, paymentrulepo, po_paymentterm_id, paymentterm, shortdescription,
                        c_invoiceschedule_id, c_dataimport_id, salesresponsible, purchasegroup, associationmembership,
                        shipmentpermissionpharma_old, permissionpharmatype, shelflifemindays, weekendopeningtimes,
                        isdecider, ismanagement, ismultiplier, debtorid, region, c_bpartner_memo, salesgroup,
                        creditlimit, creditlimit2, ad_user_memo1, ad_user_memo2, ad_user_memo3, c_bp_printformat_id,
                        c_aggregation_id, aggregationname, c_job_id, jobname, isactivestatus, m_shipper_id, shippername,
                        deliveryviarule, vendorcategory, qualification, msv3_vendor_config_id,
                        msv3_baseurl, msv3_vendor_config_name, vendorresponsible, minimumordervalue, retourfax,
                        pharma_phone, contacts, creditorid, customernoatvendor, printformat_name, ad_printformat_id,
                        PrintFormat_DocumentCopies_Override, IsSetPrintFormat_C_BPartner_Location,
                        PrintFormat_DocBaseType, pharma_fax, statusinfo, ad_user_memo4, gln, ad_user_externalid,
                        c_bpartner_externalid, c_bpartner_location_externalid, url, m_pricingsystem_id,
                        po_pricingsystem_id, pricingsystem_value, po_pricingsystem_value, memo_invoicing, pobox,
                        countryname, memo_delivery, paymenttermvalue, c_paymentterm_id, url3, globalid, ad_issue_id,
                        c_dataimport_run_id, i_linecontent, i_lineno, IsUserInvoiceEmailEnabled, PaymentRuleInfo,
                        PaymentRulePOInfo, Location_M_Shipper_ID, LocationShipperName, isUpdateLocationName)
SELECT nextval('i_bpartner_seq'),
       t.ad_client_id,
       t.ad_org_id,
       t.isactive,
       t.created,
       t.createdby,
       t.updated,
       t.updatedby,
       t.i_isimported,
       t.i_errormsg,
       t.c_bpartner_id,
       t.bpvalue,
       t.name,
       t.name2,
       t.description,
       t.duns,
       t.taxid,
       t.naics,
       t.groupvalue,
       t.c_bp_group_id,
       t.c_bpartner_location_id,
       t.address1,
       t.address2,
       t.postal,
       t.postal_add,
       t.city,
       t.c_region_id,
       t.regionname,
       t.c_country_id,
       t.countrycode,
       t.title,
       t.contactname,
       t.contactdescription,
       t.comments,
       t.phone,
       t.phone2,
       t.fax,
       t.email,
       t.password,
       t.birthday,
       t.c_greeting_id,
       t.bpcontactgreeting,
       t.processing,
       t.processed,
       t.ad_user_id,
       t.r_interestarea_id,
       t.interestareaname,
       t.iscustomer,
       t.isemployee,
       t.isvendor,
       t.companyname,
       t.firstname,
       t.lastname,
       t.isbillto,
       t.isshipto,
       t.isbilltodefault,
       t.isshiptodefault,
       t.isdefaultcontact,
       t.isbilltocontact_default,
       t.isshiptocontact_default,
       t.ad_language,
       t.address4,
       t.address3,
       t.name3,
       t.issepasigned,
       t.pharmaproductpermlaw52,
       t.ispharmaciepermission,
       t.paymentrule,
       t.iban,
       t.swiftcode,
       t.c_bp_bankaccount_id,
       t.firstsale,
       t.invoiceschedule,
       t.paymentrulepo,
       t.po_paymentterm_id,
       t.paymentterm,
       t.shortdescription,
       t.c_invoiceschedule_id,
       t.c_dataimport_id,
       t.salesresponsible,
       t.purchasegroup,
       t.associationmembership,
       t.shipmentpermissionpharma_old,
       t.permissionpharmatype,
       t.shelflifemindays,
       t.weekendopeningtimes,
       t.isdecider,
       t.ismanagement,
       t.ismultiplier,
       t.debtorid,
       t.region,
       t.c_bpartner_memo,
       t.salesgroup,
       t.creditlimit,
       t.creditlimit2,
       t.ad_user_memo1,
       t.ad_user_memo2,
       t.ad_user_memo3,
       t.c_bp_printformat_id,
       t.c_aggregation_id,
       t.aggregationname,
       t.c_job_id,
       t.jobname,
       t.isactivestatus,
       t.m_shipper_id,
       t.shippername,
       t.deliveryviarule,
       t.vendorcategory,
       t.qualification,
       t.msv3_vendor_config_id,
       t.msv3_baseurl,
       t.msv3_vendor_config_name,
       t.vendorresponsible,
       t.minimumordervalue,
       t.retourfax,
       t.pharma_phone,
       t.contacts,
       t.creditorid,
       t.customernoatvendor,
       t.printformat_name,
       t.ad_printformat_id,
       t.PrintFormat_DocumentCopies_Override,
       t.IsSetPrintFormat_C_BPartner_Location,
       t.PrintFormat_DocBaseType,
       t.pharma_fax,
       t.statusinfo,
       t.ad_user_memo4,
       t.gln,
       t.ad_user_externalid,
       t.c_bpartner_externalid,
       t.c_bpartner_location_externalid,
       t.url,
       t.m_pricingsystem_id,
       t.po_pricingsystem_id,
       t.pricingsystem_value,
       t.po_pricingsystem_value,
       t.memo_invoicing,
       t.pobox,
       t.countryname,
       t.memo_delivery,
       t.paymenttermvalue,
       t.c_paymentterm_id,
       t.url3,
       t.globalid,
       t.ad_issue_id,
       t.c_dataimport_run_id,
       t.i_linecontent,
       t.i_lineno,
       t.IsUserInvoiceEmailEnabled,
       t.PaymentRuleInfo,
       t.PaymentRulePOInfo,
       t.Location_M_Shipper_ID,
       t.LocationShipperName,
       t.isUpdateLocationName
FROM migration_data.i_bpartner_vendor t
;

DROP TABLE IF EXISTS migration_data.i_bpartner_customer;
CREATE TABLE migration_data.i_bpartner_customer AS
SELECT 1000000                                                                  AS ad_client_id,
       1000000                                                                  AS ad_org_id,
       'Y'                                                                      AS isactive,
       now()                                                                    AS created,
       99                                                                       AS createdby,
       now()                                                                    AS updated,
       99                                                                       AS updatedby,
       'N'                                                                      AS i_isimported,
       NULL                                                                     AS i_errormsg,
       NULL::numeric                                                            AS c_bpartner_id,
       c."No_"                                                                  AS bpvalue,
       LEFT(c."Name", 60)                                                       AS name,
       LEFT(c."Name 2", 60)                                                     AS name2,
       LEFT(c."Search Name", 255)                                               AS description,
       NULL                                                                     AS duns,
       LEFT(c."VAT Registration No_", 20)                                       AS taxid,
       NULL                                                                     AS naics,
       NULL                                                                     AS groupvalue,
       NULL::numeric                                                            AS c_bp_group_id,
       NULL::numeric                                                            AS c_bpartner_location_id,
       location_contact_print_info.address1                                     AS address1,
       location_contact_print_info.address2                                     AS address2,
       location_contact_print_info.postal                                       AS postal,
       NULL                                                                     AS postal_add,
       location_contact_print_info.city                                         AS city,
       NULL::numeric                                                            AS c_region_id,
       NULL                                                                     AS regionname,
       NULL::numeric                                                            AS c_country_id,
       location_contact_print_info.countrycode                                  AS countrycode,
       location_contact_print_info.title                                        AS title,
       location_contact_print_info.contactname                                  AS contactname,
       location_contact_print_info.contactdescription                           AS contactdescription,
       NULL                                                                     AS comments,
       location_contact_print_info.phone                                        AS phone,
       location_contact_print_info.phone2                                       AS phone2,
       location_contact_print_info.fax                                          AS fax,
       location_contact_print_info.email                                        AS email,
       NULL                                                                     AS password,
       con."Date of Birth"                                                      AS birthday,
       NULL::numeric                                                            AS c_greeting_id,
       location_contact_print_info.bpcontactgreeting                            AS bpcontactgreeting,
       NULL                                                                     AS processing,
       'N'                                                                      AS processed,
       NULL::numeric                                                            AS ad_user_id,
       NULL::numeric                                                            AS r_interestarea_id,
       NULL                                                                     AS interestareaname,
       'Y'                                                                      AS iscustomer,
       'N'                                                                      AS isemployee,
       CASE WHEN ven."No_" IS NULL THEN 'N' ELSE 'Y' END                        AS isvendor,
       CASE WHEN c."No_"::int < 15000 THEN LEFT(c."Name", 255) END              AS companyname, -- legal name is always stored here in navision / starting from 15000 customers are private persons
       location_contact_print_info.firstname                                    AS firstname,
       location_contact_print_info.lastname                                     AS lastname,
       location_contact_print_info.isbillto                                     AS isbillto,
       location_contact_print_info.isshipto                                     AS isshipto,
       location_contact_print_info.isbilltodefault                              AS isbilltodefault,
       location_contact_print_info.isshiptodefault                              AS isshiptodefault,
       location_contact_print_info.isdefaultcontact                             AS isdefaultcontact,
       location_contact_print_info.isbilltocontact_default                      AS isbilltocontact_default,
       location_contact_print_info.isshiptocontact_default                      AS isshiptocontact_default,
       CASE
           WHEN c."Language Code" IS NOT NULL AND TRIM(c."Language Code") <> '' THEN
               LOWER(LEFT(c."Language Code", 2)) || '_' ||
               UPPER(
                       COALESCE(
                               NULLIF(TRIM(c."Country_Region Code"), ''),
                               (SELECT l.countrycode
                                FROM ad_language l
                                WHERE l.languageiso = LOWER(LEFT(c."Language Code", 2))
                                LIMIT 1)-- Fallback can be wrong e.g. 'de_DE' 'de_CH'
                       )
               )
           WHEN c."Country_Region Code" IS NOT NULL AND TRIM(c."Country_Region Code") <> '' THEN
               (SELECT l.languageiso
                FROM ad_language l
                WHERE l.countrycode = UPPER(LEFT(c."Country_Region Code", 2))
                LIMIT 1) || '_' || UPPER(LEFT(c."Country_Region Code", 2))-- Fallback can be wrong e.g. 'fr_CH' 'de_CH'
           END                                                                  AS ad_language,
       NULL                                                                     AS address4,
       NULL                                                                     AS address3,
       LEFT(c."Name 3", 60)                                                     AS name3,
       'N'                                                                      AS issepasigned,
       NULL                                                                     AS pharmaproductpermlaw52,
       'N'                                                                      AS ispharmaciepermission,
       CASE pm."Code"
           WHEN 'SEPA-B2B' THEN 'D'
           WHEN 'ÜBERWEISUN' THEN 'T'
           END                                                                  AS paymentrule,
       location_contact_print_info.iban                                         AS iban,
       location_contact_print_info.swiftcode                                    AS swiftcode,
       NULL::numeric                                                            AS c_bp_bankaccount_id,
       NULL::timestamp with time zone                                           AS firstsale,
       NULL                                                                     AS invoiceschedule,
       CASE pm_vendor."Code"
           WHEN 'SEPA-B2B' THEN 'D'
           WHEN 'SEPA K' THEN 'T'
           END                                                                  AS paymentrulepo,
       NULL::numeric                                                            AS po_paymentterm_id,
       LEFT(pt_vendor."Description", 255)                                       AS paymentterm,
       NULL                                                                     AS shortdescription,
       NULL::numeric                                                            AS c_invoiceschedule_id,
       NULL::numeric                                                            AS c_dataimport_id,
       NULL                                                                     AS salesresponsible,
       NULL                                                                     AS purchasegroup,
       NULL                                                                     AS associationmembership,
       NULL                                                                     AS shipmentpermissionpharma_old,
       NULL                                                                     AS permissionpharmatype,
       NULL::numeric                                                            AS shelflifemindays,
       NULL                                                                     AS weekendopeningtimes,
       'N'                                                                      AS isdecider,
       'N'                                                                      AS ismanagement,
       'N'                                                                      AS ismultiplier,
       c."No_"::numeric                                                         AS debtorid,
       LEFT(c."County", 60)                                                     AS region,
       NULL                                                                     AS c_bpartner_memo,
       NULL                                                                     AS salesgroup,
       c."Credit Limit (LCY)"                                                   AS creditlimit,
       NULL::numeric                                                            AS creditlimit2,
       NULL                                                                     AS ad_user_memo1,
       NULL                                                                     AS ad_user_memo2,
       NULL                                                                     AS ad_user_memo3,
       NULL::numeric                                                            AS c_bp_printformat_id,
       NULL::numeric                                                            AS c_aggregation_id,
       NULL                                                                     AS aggregationname,
       NULL::numeric                                                            AS c_job_id,
       NULL                                                                     AS jobname,
       CASE WHEN c."Blocked" = 0 THEN 'Y' ELSE 'N' END                          AS isactivestatus,
       NULL::numeric                                                            AS m_shipper_id,
       ship_method."Description"                                                AS shippername,
       CASE WHEN ship_method."Code" IS NOT NULL THEN 'S' END                    AS deliveryviarule,
       NULL                                                                     AS vendorcategory,
       NULL                                                                     AS qualification,
       NULL::numeric                                                            AS msv3_vendor_config_id,
       NULL                                                                     AS msv3_baseurl,
       NULL                                                                     AS msv3_vendor_config_name,
       NULL                                                                     AS vendorresponsible,
       NULL                                                                     AS minimumordervalue,
       NULL                                                                     AS retourfax,
       NULL                                                                     AS pharma_phone,
       NULL                                                                     AS contacts,
       ven."No_"::numeric                                                       AS creditorid,
       NULL                                                                     AS customernoatvendor,
       NULL                                                                     AS printformat_name,
       NULL::numeric                                                            AS ad_printformat_id,
       location_contact_print_info.PrintFormat_DocumentCopies_Override          AS PrintFormat_DocumentCopies_Override,
       location_contact_print_info.IsSetPrintFormat_C_BPartner_Location         AS IsSetPrintFormat_C_BPartner_Location,
       location_contact_print_info.PrintFormat_DocBaseType                      AS PrintFormat_DocBaseType,
       NULL                                                                     AS pharma_fax,
       NULL                                                                     AS statusinfo,
       NULL                                                                     AS ad_user_memo4,
       NULL                                                                     AS gln,
       ('001 - ' || location_contact_print_info.ad_user_externalid)             AS ad_user_externalid,
       ('001 - ' || c."No_")                                                    AS c_bpartner_externalid,
       ('001 - ' || location_contact_print_info.c_bpartner_location_externalid) AS c_bpartner_location_externalid,
       LEFT(c."Home Page", 120)                                                 AS url,
       COALESCE(ps_customer.m_pricingsystem_id, ps_cpg.m_pricingsystem_id)      AS m_pricingsystem_id,
       NULL::numeric                                                            AS po_pricingsystem_id,
       NULL                                                                     AS pricingsystem_value,
       NULL                                                                     AS po_pricingsystem_value,
       NULL                                                                     AS memo_invoicing,
       NULL                                                                     AS pobox,
       NULL                                                                     AS countryname,
       NULL                                                                     AS memo_delivery,
       LEFT(pt."Description", 255)                                              AS paymenttermvalue,
       NULL::numeric                                                            AS c_paymentterm_id,
       NULL                                                                     AS url3,
       NULL                                                                     AS globalid,
       NULL::numeric                                                            AS ad_issue_id,
       NULL::numeric                                                            AS c_dataimport_run_id,
       NULL                                                                     AS i_linecontent,
       NULL::numeric                                                            AS i_lineno,
       location_contact_print_info.IsUserInvoiceEmailEnabled                    AS IsUserInvoiceEmailEnabled,
       pm."Description"                                                         AS PaymentRuleInfo,
       pm_vendor."Description"                                                  AS PaymentRulePOInfo,
       NULL::numeric                                                            AS Location_M_Shipper_ID,
       location_contact_print_info.LocationShipperName                          AS LocationShipperName,
       'Y'                                                                      AS isUpdateLocationName,
       NULL::numeric                                                            AS M_Shipper_RoutingCode_ID,
       location_contact_print_info.ShipperRouteCodeName                         AS ShipperRouteCodeName,
       location_contact_print_info.bpartnername                                 AS bpartnername,
       location_contact_print_info.bpartnername2                                AS bpartnername2
FROM migration_data."Navision$Customer" c
         LEFT JOIN migration_data."Navision$Payment Method" pm
                   ON c."Payment Method Code" = pm."Code"
         LEFT JOIN migration_data."Navision$Contact Business Relation" rel
                   ON c."No_" = rel."No_" AND rel."Business Relation Code" = 'DEB'
         LEFT JOIN migration_data."Navision$Shipment Method" ship_method
                   ON c."Shipment Method Code" = ship_method."Code"
         LEFT JOIN migration_data."Navision$Route" route
                   ON c."Routing Code" = route."Code"
         LEFT JOIN migration_data."Navision$Contact" con
                   ON rel."Contact No_" = con."No_"
         LEFT JOIN migration_data."Navision$Payment Terms" pt
                   ON c."Payment Terms Code" = pt."Code"
         LEFT JOIN migration_data."Navision$Customer Price Group" cpg
                   ON c."Customer Price Group" = cpg."Code"
         LEFT JOIN migration_data."Navision$Vendor" ven
                   ON ven."Name" = c."Name"
         LEFT JOIN migration_data."Navision$Payment Method" pm_vendor
                   ON ven."Payment Method Code" = pm_vendor."Code"
         LEFT JOIN migration_data."Navision$Payment Terms" pt_vendor
                   ON ven."Payment Terms Code" = pt_vendor."Code"
         LEFT JOIN m_pricingsystem ps_customer
                   ON ps_customer.migration_key = ('001 - ' || c."No_")
         LEFT JOIN m_pricingsystem ps_cpg
                   ON ps_customer.migration_key = ('001 - ' || c."Customer Price Group")
         LEFT JOIN LATERAL (
    -- taking info from customer (contact of business relation info is identical)
    -- invoice copies
    SELECT LEFT(c."Address", 60)                                                  AS address1,
           LEFT(c."Address 2", 60)                                                AS address2,
           NULL                                                                   AS bpartnername,
           NULL                                                                   AS bpartnername2,
           LEFT(c."Post Code", 10)                                                AS postal,
           LEFT(c."City", 60)                                                     AS city,
           COALESCE(NULLIF(TRIM(LEFT(c."Country_Region Code", 2)), ''),
                    'DE')                                                         AS countrycode, -- Fallback as country is mandatory if we have an address
           LEFT(c."Phone No_", 40)                                                AS phone,
           LEFT(con."Mobile Phone No_", 40)                                       AS phone2,
           LEFT(c."Fax No_", 40)                                                  AS fax,
           LEFT(c."E-Mail", 60)                                                   AS email,
           'Y'                                                                    AS isbillto,
           'Y'                                                                    AS isshipto,
           'Y'                                                                    AS isbilltodefault,
           CASE
               WHEN EXISTS(SELECT 1
                           FROM migration_data."Navision$Ship-to Address" inner_ship_to
                           WHERE inner_ship_to."Customer No_" = c."No_") THEN 'N'
               ELSE 'Y' END                                                       AS isshiptodefault,
           'Y'                                                                    AS isdefaultcontact,
           'Y'                                                                    AS isbilltocontact_default,
           'Y'                                                                    AS isshiptocontact_default,
           c."No_"                                                                AS c_bpartner_location_externalid,
           NULLIF(TRIM(UPPER(SUBSTRING(LEFT(con."Salutation Code", 60), 1, 1)) ||
                       LOWER(SUBSTRING(LEFT(con."Salutation Code", 60), 2))), '') AS bpcontactgreeting,
           NULL                                                                   AS contactname,
           LEFT(con."Person Information", 255)                                    AS contactdescription,
           LEFT(con."Job Title", 40)                                              AS title,
           LEFT(con."First Name", 255)                                            AS firstname,
           CASE
               WHEN NULLIF(TRIM(LEFT(con."Surname", 255)), '') IS NULL AND
                    NULLIF(TRIM(LEFT(con."First Name", 255)), '') IS NULL THEN con."Name"
               ELSE LEFT(con."Surname", 255) END                                  AS lastname,
           con."No_"                                                              AS ad_user_externalid,
           c."Invoice Copies"::int                                                AS PrintFormat_DocumentCopies_Override,
           'N'                                                                    AS IsSetPrintFormat_C_BPartner_Location,
           'ARI'                                                                  AS PrintFormat_DocBaseType,
           CASE
               WHEN EXISTS (SELECT 1
                            FROM migration_data."Navision$CMM Recipient" cmm
                            WHERE "Document Type" = 'RECHNUNG'
                              AND con."E-Mail" = cmm."Custom Address")
                   THEN 'Y'
               ELSE 'N' END                                                       AS IsUserInvoiceEmailEnabled,
           ship_method."Description"                                              AS LocationShipperName,
           route."Beschreibung"                                                   AS ShipperRouteCodeName,
           inner_bank_accounts.iban                                               AS iban,
           inner_bank_accounts.swiftcode                                          AS swiftcode
    FROM (SELECT LEFT(ba."IBAN", 255) AS iban, LEFT(ba."SWIFT Code", 255) AS swiftcode
          FROM migration_data."Navision$Vendor Bank Account" ba
          WHERE ven."No_" = ba."Vendor No_"
            AND NULLIF(TRIM(ba."IBAN"), '') IS NOT NULL
          UNION ALL
          SELECT LEFT(ba."IBAN", 255) AS iban, LEFT(ba."SWIFT Code", 255) AS swiftcode
          FROM migration_data."Navision$Customer Bank Account" ba
          WHERE c."No_" = ba."Customer No_"
            AND NULLIF(TRIM(ba."IBAN"), '') IS NOT NULL
          UNION ALL
          SELECT NULL AS iban, NULL AS swiftcode --make sure that without bank_account it is added once
         ) AS inner_bank_accounts
    UNION ALL
    -- taking info from customer (contact of business relation info is identical)
-- shipment copies
    SELECT LEFT(c."Address", 60)                                                  AS address1,
           LEFT(c."Address 2", 60)                                                AS address2,
           NULL                                                                   AS bpartnername,
           NULL                                                                   AS bpartnername2,
           LEFT(c."Post Code", 10)                                                AS postal,
           LEFT(c."City", 60)                                                     AS city,
           COALESCE(NULLIF(TRIM(LEFT(c."Country_Region Code", 2)), ''),
                    'DE')                                                         AS countrycode, -- Fallback as country is mandatory if we have an address
           LEFT(c."Phone No_", 40)                                                AS phone,
           LEFT(con."Mobile Phone No_", 40)                                       AS phone2,
           LEFT(c."Fax No_", 40)                                                  AS fax,
           LEFT(c."E-Mail", 60)                                                   AS email,
           'Y'                                                                    AS isbillto,
           'Y'                                                                    AS isshipto,
           'Y'                                                                    AS isbilltodefault,
           CASE
               WHEN EXISTS(SELECT 1
                           FROM migration_data."Navision$Ship-to Address" inner_ship_to
                           WHERE inner_ship_to."Customer No_" = c."No_") THEN 'N'
               ELSE 'Y' END                                                       AS isshiptodefault,
           'Y'                                                                    AS isdefaultcontact,
           'Y'                                                                    AS isbilltocontact_default,
           'Y'                                                                    AS isshiptocontact_default,
           c."No_"                                                                AS c_bpartner_location_externalid,
           NULLIF(TRIM(UPPER(SUBSTRING(LEFT(con."Salutation Code", 60), 1, 1)) ||
                       LOWER(SUBSTRING(LEFT(con."Salutation Code", 60), 2))), '') AS bpcontactgreeting,
           NULL                                                                   AS contactname,
           LEFT(con."Person Information", 255)                                    AS contactdescription,
           LEFT(con."Job Title", 40)                                              AS title,
           LEFT(con."First Name", 255)                                            AS firstname,
           CASE
               WHEN NULLIF(TRIM(LEFT(con."Surname", 255)), '') IS NULL AND
                    NULLIF(TRIM(LEFT(con."First Name", 255)), '') IS NULL THEN con."Name"
               ELSE LEFT(con."Surname", 255) END                                  AS lastname,
           con."No_"                                                              AS ad_user_externalid,
           coalesce(ship_method."Delivery Note Copies", 0)                        AS PrintFormat_DocumentCopies_Override,
           'N'                                                                    AS IsSetPrintFormat_C_BPartner_Location,
           'MMS'                                                                  AS PrintFormat_DocBaseType,
           CASE
               WHEN EXISTS (SELECT 1
                            FROM migration_data."Navision$CMM Recipient" cmm
                            WHERE "Document Type" = 'RECHNUNG'
                              AND con."E-Mail" = cmm."Custom Address")
                   THEN 'Y'
               ELSE 'N' END                                                       AS IsUserInvoiceEmailEnabled,
           ship_method."Description"                                              AS LocationShipperName,
           route."Beschreibung"                                                   AS ShipperRouteCodeName,
           NULL                                                                   AS iban,
           NULL                                                                   AS swiftcode
    UNION ALL
    SELECT LEFT(inner_ship_to."Address", 60)                          AS address1,
           LEFT(inner_ship_to."Address 2", 60)                        AS address2,
           LEFT(inner_ship_to."Name", 255)                            AS bpartnername,
           LEFT(inner_ship_to."Name 2", 255)                          AS bpartnername2,
           LEFT(inner_ship_to."Post Code", 10)                        AS postal,
           LEFT(inner_ship_to."City", 60)                             AS city,
           COALESCE(NULLIF(TRIM(LEFT(inner_ship_to."Country_Region Code", 2)), ''),
                    NULLIF(TRIM(LEFT(c."Country_Region Code", 2)), ''),
                    'DE')                                             AS countrycode, -- Fallback as country is mandatory if we have an address
           NULL                                                       AS phone,
           NULL                                                       AS phone2,
           NULL                                                       AS fax,
           NULL                                                       AS email,
           'N'                                                        AS isbillto,
           'Y'                                                        AS isshipto,
           'N'                                                        AS isbilltodefault,
           CASE WHEN inner_ship_to."Code" = '1' THEN 'Y' ELSE 'N' END AS isshiptodefault,
           'N'                                                        AS isdefaultcontact,
           'N'                                                        AS isbilltocontact_default,
           'N'                                                        AS isshiptocontact_default,
           c."No_" || '_ship_to_' || inner_ship_to."Code"             AS c_bpartner_location_externalid,
           NULL                                                       AS bpcontactgreeting,
           NULL                                                       AS contactname,
           NULL                                                       AS contactdescription,
           NULL                                                       AS title,
           NULL                                                       AS firstname,
           NULL                                                       AS lastname,
           NULL                                                       AS ad_user_externalid,
           coalesce(inner_ship_method."Delivery Note Copies", 0)      AS PrintFormat_DocumentCopies_Override,
           'Y'                                                        AS IsSetPrintFormat_C_BPartner_Location,
           'MMS'                                                      AS PrintFormat_DocBaseType,
           NULL                                                       AS IsUserInvoiceEmailEnabled,
           coalesce(inner_ship_method."Description" ,
                    ship_method."Description")                        AS LocationShipperName,
           route."Beschreibung"                                       AS ShipperRouteCodeName,
           NULL                                                       AS iban,
           NULL                                                       AS swiftcode
    FROM migration_data."Navision$Ship-to Address" inner_ship_to
             LEFT JOIN migration_data."Navision$Shipment Method" inner_ship_method
                       ON inner_ship_to."Shipment Method Code" = inner_ship_method."Code"
    WHERE inner_ship_to."Customer No_" = c."No_" ) AS location_contact_print_info
                   ON TRUE
WHERE TRUE
;

INSERT INTO i_bpartner (i_bpartner_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                        i_isimported, i_errormsg, c_bpartner_id, bpvalue, name, name2, description, duns, taxid, naics,
                        groupvalue, c_bp_group_id, c_bpartner_location_id, address1, address2, postal, postal_add, city,
                        c_region_id, regionname, c_country_id, countrycode, title, contactname, contactdescription,
                        comments, phone, phone2, fax, email, password, birthday, c_greeting_id, bpcontactgreeting,
                        processing, processed, ad_user_id, r_interestarea_id, interestareaname, iscustomer, isemployee,
                        isvendor, companyname, firstname, lastname, isbillto, isshipto, isbilltodefault,
                        isshiptodefault, isdefaultcontact, isbilltocontact_default, isshiptocontact_default,
                        ad_language, address4, address3, name3, issepasigned, pharmaproductpermlaw52,
                        ispharmaciepermission, paymentrule, iban, swiftcode, c_bp_bankaccount_id, firstsale,
                        invoiceschedule, paymentrulepo, po_paymentterm_id, paymentterm, shortdescription,
                        c_invoiceschedule_id, c_dataimport_id, salesresponsible, purchasegroup, associationmembership,
                        shipmentpermissionpharma_old, permissionpharmatype, shelflifemindays, weekendopeningtimes,
                        isdecider, ismanagement, ismultiplier, debtorid, region, c_bpartner_memo, salesgroup,
                        creditlimit, creditlimit2, ad_user_memo1, ad_user_memo2, ad_user_memo3, c_bp_printformat_id,
                        c_aggregation_id, aggregationname, c_job_id, jobname, isactivestatus, m_shipper_id, shippername,
                        deliveryviarule, vendorcategory, qualification, msv3_vendor_config_id,
                        msv3_baseurl, msv3_vendor_config_name, vendorresponsible, minimumordervalue, retourfax,
                        pharma_phone, contacts, creditorid, customernoatvendor, printformat_name, ad_printformat_id,
                        PrintFormat_DocumentCopies_Override, IsSetPrintFormat_C_BPartner_Location,
                        PrintFormat_DocBaseType, pharma_fax, statusinfo, ad_user_memo4, gln, ad_user_externalid,
                        c_bpartner_externalid, c_bpartner_location_externalid, url, m_pricingsystem_id,
                        po_pricingsystem_id, pricingsystem_value, po_pricingsystem_value, memo_invoicing, pobox,
                        countryname, memo_delivery, paymenttermvalue, c_paymentterm_id, url3, globalid, ad_issue_id,
                        c_dataimport_run_id, i_linecontent, i_lineno, IsUserInvoiceEmailEnabled, PaymentRuleInfo,
                        PaymentRulePOInfo, Location_M_Shipper_ID, LocationShipperName, isUpdateLocationName,
                        M_Shipper_RoutingCode_ID, ShipperRouteCodeName, bpartnername, bpartnername2)
SELECT nextval('i_bpartner_seq'),
       t.ad_client_id,
       t.ad_org_id,
       t.isactive,
       t.created,
       t.createdby,
       t.updated,
       t.updatedby,
       t.i_isimported,
       t.i_errormsg,
       t.c_bpartner_id,
       t.bpvalue,
       t.name,
       t.name2,
       t.description,
       t.duns,
       t.taxid,
       t.naics,
       t.groupvalue,
       t.c_bp_group_id,
       t.c_bpartner_location_id,
       t.address1,
       t.address2,
       t.postal,
       t.postal_add,
       t.city,
       t.c_region_id,
       t.regionname,
       t.c_country_id,
       t.countrycode,
       t.title,
       t.contactname,
       t.contactdescription,
       t.comments,
       t.phone,
       t.phone2,
       t.fax,
       t.email,
       t.password,
       t.birthday,
       t.c_greeting_id,
       t.bpcontactgreeting,
       t.processing,
       t.processed,
       t.ad_user_id,
       t.r_interestarea_id,
       t.interestareaname,
       t.iscustomer,
       t.isemployee,
       t.isvendor,
       t.companyname,
       t.firstname,
       t.lastname,
       t.isbillto,
       t.isshipto,
       t.isbilltodefault,
       t.isshiptodefault,
       t.isdefaultcontact,
       t.isbilltocontact_default,
       t.isshiptocontact_default,
       t.ad_language,
       t.address4,
       t.address3,
       t.name3,
       t.issepasigned,
       t.pharmaproductpermlaw52,
       t.ispharmaciepermission,
       t.paymentrule,
       t.iban,
       t.swiftcode,
       t.c_bp_bankaccount_id,
       t.firstsale,
       t.invoiceschedule,
       t.paymentrulepo,
       t.po_paymentterm_id,
       t.paymentterm,
       t.shortdescription,
       t.c_invoiceschedule_id,
       t.c_dataimport_id,
       t.salesresponsible,
       t.purchasegroup,
       t.associationmembership,
       t.shipmentpermissionpharma_old,
       t.permissionpharmatype,
       t.shelflifemindays,
       t.weekendopeningtimes,
       t.isdecider,
       t.ismanagement,
       t.ismultiplier,
       t.debtorid,
       t.region,
       t.c_bpartner_memo,
       t.salesgroup,
       t.creditlimit,
       t.creditlimit2,
       t.ad_user_memo1,
       t.ad_user_memo2,
       t.ad_user_memo3,
       t.c_bp_printformat_id,
       t.c_aggregation_id,
       t.aggregationname,
       t.c_job_id,
       t.jobname,
       t.isactivestatus,
       t.m_shipper_id,
       t.shippername,
       t.deliveryviarule,
       t.vendorcategory,
       t.qualification,
       t.msv3_vendor_config_id,
       t.msv3_baseurl,
       t.msv3_vendor_config_name,
       t.vendorresponsible,
       t.minimumordervalue,
       t.retourfax,
       t.pharma_phone,
       t.contacts,
       t.creditorid,
       t.customernoatvendor,
       t.printformat_name,
       t.ad_printformat_id,
       t.PrintFormat_DocumentCopies_Override,
       t.IsSetPrintFormat_C_BPartner_Location,
       t.PrintFormat_DocBaseType,
       t.pharma_fax,
       t.statusinfo,
       t.ad_user_memo4,
       t.gln,
       t.ad_user_externalid,
       t.c_bpartner_externalid,
       t.c_bpartner_location_externalid,
       t.url,
       t.m_pricingsystem_id,
       t.po_pricingsystem_id,
       t.pricingsystem_value,
       t.po_pricingsystem_value,
       t.memo_invoicing,
       t.pobox,
       t.countryname,
       t.memo_delivery,
       t.paymenttermvalue,
       t.c_paymentterm_id,
       t.url3,
       t.globalid,
       t.ad_issue_id,
       t.c_dataimport_run_id,
       t.i_linecontent,
       t.i_lineno,
       t.IsUserInvoiceEmailEnabled,
       t.PaymentRuleInfo,
       t.PaymentRulePOInfo,
       t.Location_M_Shipper_ID,
       t.LocationShipperName,
       t.isUpdateLocationName,
       t.M_Shipper_RoutingCode_ID,
       t.ShipperRouteCodeName,
       t.bpartnername,
       t.bpartnername2
FROM migration_data.i_bpartner_customer t
;
