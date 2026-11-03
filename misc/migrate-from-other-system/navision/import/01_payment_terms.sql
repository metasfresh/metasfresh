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

DROP TABLE IF EXISTS migration_data.c_paymentterm;
CREATE TABLE migration_data.c_paymentterm AS
SELECT 1000000                                                                   AS ad_client_id,
       0                                                                         AS ad_org_id,
       'Y'::bpchar                                                               AS isactive,
       NOW()                                                                     AS created,
       99                                                                        AS createdby,
       NOW()                                                                     AS updated,
       99                                                                        AS updatedby,
       LEFT(T."Description", 70)                                                 AS name,
       COALESCE(NULLIF(TRIM(T."Description 2"), ''), LEFT(T."Description", 255)) AS description,
       NULL                                                                      AS documentnote,
       'N'::bpchar                                                               AS afterdelivery,
       'N'::bpchar                                                               AS isduefixed,
       CASE
           WHEN TRIM(T."Due Date Calculation") = '' THEN 0
           ELSE CAST(regexp_replace(TRIM(T."Due Date Calculation"), '[^0-9]', '', 'g') AS NUMERIC)
           END                                                                   AS netdays,
       CASE
           WHEN TRIM(T."Due Date Calculation") = '' THEN 0
           ELSE CAST(regexp_replace(TRIM(T."Due Date Calculation"), '[^0-9]', '', 'g') AS NUMERIC)
           END                                                                   AS gracedays,
       0                                                                         AS fixmonthcutoff,
       0                                                                         AS fixmonthday,
       0                                                                         AS fixmonthoffset,
       CASE
           WHEN TRIM(T."Discount Date Calculation") = '' THEN 0
           ELSE CAST(regexp_replace(TRIM(T."Discount Date Calculation"), '[^0-9]', '', 'g') AS NUMERIC)
           END                                                                   AS discountdays,
       T."Discount _"                                                            AS discount,
       0                                                                         AS discountdays2,
       0                                                                         AS discount2,
       'Y'::bpchar                                                               AS isnextbusinessday,
       'N'::bpchar                                                               AS isdefault,
       '001 - ' || LEFT(T."Code", 70)                                            AS value,
       NULL                                                                      AS netday,
       'Y'::bpchar                                                               AS isvalid,
       NULL                                                                      AS processing,
       LEFT(T."Description", 60)                                                 AS name_invoice,
       '001 - ' || LEFT(T."Code", 255)                                           AS migration_key,
       NULL                                                                      AS externalid
FROM migration_data."Navision$Payment Terms" T
WHERE NOT EXISTS (SELECT 1 FROM c_paymentterm WHERE isactive = 'Y' AND name = LEFT(T."Description", 70))
;

INSERT INTO c_paymentterm (c_paymentterm_id, ad_client_id, ad_org_id, isactive, created, createdby, updated, updatedby,
                           name, description, documentnote, afterdelivery, isduefixed, netdays, gracedays,
                           fixmonthcutoff, fixmonthday, fixmonthoffset, discountdays, discount, discountdays2,
                           discount2, isnextbusinessday, isdefault, value, netday, isvalid, processing, name_invoice,
                           migration_key, externalid)

SELECT nextval('c_paymentterm_seq'),
       ad_client_id,
       ad_org_id,
       isactive,
       created,
       createdby,
       updated,
       updatedby,
       name,
       description,
       documentnote,
       afterdelivery,
       isduefixed,
       netdays,
       gracedays,
       fixmonthcutoff,
       fixmonthday,
       fixmonthoffset,
       discountdays,
       discount,
       discountdays2,
       discount2,
       isnextbusinessday,
       isdefault,
       value,
       netday,
       isvalid,
       processing,
       name_invoice,
       migration_key,
       externalid
FROM migration_data.c_paymentterm;

INSERT INTO c_paymentterm_trl (AD_Language, c_paymentterm_ID, Description, Name, IsTranslated, AD_Client_ID, AD_Org_ID,
                               Created, Createdby, Updated, UpdatedBy)
SELECT l.AD_Language,
       t.c_paymentterm_id,
       t.Description,
       t.Name,
       'N',
       t.AD_Client_ID,
       t.AD_Org_ID,
       t.Created,
       t.Createdby,
       t.Updated,
       t.UpdatedBy
FROM AD_Language l,
     C_PaymentTerm t
WHERE l.IsActive = 'Y'
  AND l.IsSystemLanguage = 'Y'
  AND l.IsBaseLanguage = 'N'
  AND t.c_paymentterm_id IN (SELECT c_paymentterm_id FROM c_paymentterm WHERE l.isactive = 'Y')
  AND NOT EXISTS (SELECT *
                  FROM c_paymentterm_trl tt
                  WHERE tt.AD_Language = l.AD_Language
                    AND tt.c_paymentterm_id = t.c_paymentterm_id)
;
