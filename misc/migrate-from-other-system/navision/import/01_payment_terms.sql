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

-- Prerequisite: 00_parameters.sql
--
-- Key of a payment term: Value = key_prefix || Navision "Code" (an existing payment term with this Value is skipped).
-- Name = Navision "Description". C_PaymentTerm.Name must be unique, so a description that is used twice in Navision,
-- or that is already used by another metasfresh payment term, gets the code appended: "<Description> (<Code>)".
-- 04_partners.sql resolves the partners' payment terms via the Value, so it is not affected by this renaming.
--
-- Date formulas: only "<n> days" is converted (see migration_data.nav_dateformula_days in 00_parameters.sql).
-- A payment term with any other formula (weeks, months, current month, ...) is NOT imported and is listed by the
-- report below; create those few payment terms manually in metasfresh (with the same Value), before importing partners.

-- Report: payment terms that are NOT imported because of an unsupported date formula
SELECT T."Code",
       T."Description",
       T."Due Date Calculation",
       T."Discount Date Calculation"
FROM migration_data."Navision$Payment Terms" T
WHERE migration_data.nav_dateformula_days(T."Due Date Calculation") IS NULL
   OR migration_data.nav_dateformula_days(T."Discount Date Calculation") IS NULL
;

DROP TABLE IF EXISTS migration_data.c_paymentterm;
CREATE TABLE migration_data.c_paymentterm AS
WITH source AS (SELECT T.*,
                       (SELECT key_prefix FROM migration_data.nav_import_params) || T."Code" AS key,
                       migration_data.nav_dateformula_days(T."Due Date Calculation")      AS due_days,
                       migration_data.nav_dateformula_days(T."Discount Date Calculation") AS discount_days,
                       (COUNT(*) OVER (PARTITION BY LEFT(T."Description", 70)) > 1
                           OR EXISTS (SELECT 1
                                      FROM c_paymentterm pt
                                      WHERE pt.isactive = 'Y'
                                        AND pt.name = LEFT(T."Description", 70)))         AS is_name_taken
                FROM migration_data."Navision$Payment Terms" T)
SELECT 1000000                                                                   AS ad_client_id,
       0                                                                         AS ad_org_id,
       'Y'::bpchar                                                               AS isactive,
       NOW()                                                                     AS created,
       99                                                                        AS createdby,
       NOW()                                                                     AS updated,
       99                                                                        AS updatedby,
       CASE
           WHEN T.is_name_taken THEN LEFT(T."Description", 70) || ' (' || T."Code" || ')'
                                ELSE LEFT(T."Description", 70)
       END                                                                       AS name,
       COALESCE(NULLIF(TRIM(T."Description 2"), ''), LEFT(T."Description", 255)) AS description,
       NULL                                                                      AS documentnote,
       'N'::bpchar                                                               AS afterdelivery,
       'N'::bpchar                                                               AS isduefixed,
       T.due_days                                                                AS netdays,
       0                                                                         AS gracedays,
       0                                                                         AS fixmonthcutoff,
       0                                                                         AS fixmonthday,
       0                                                                         AS fixmonthoffset,
       T.discount_days                                                           AS discountdays, -- an empty "Discount Date Calculation" gives 0 days, also if a "Discount _" is set
       T."Discount _"                                                            AS discount,
       0                                                                         AS discountdays2,
       0                                                                         AS discount2,
       'Y'::bpchar                                                               AS isnextbusinessday,
       'N'::bpchar                                                               AS isdefault,
       LEFT(T.key, 70)                                                           AS value,
       NULL                                                                      AS netday,
       'Y'::bpchar                                                               AS isvalid,
       NULL                                                                      AS processing,
       LEFT(T."Description", 60)                                                 AS name_invoice,
       LEFT(T.key, 255)                                                          AS migration_key,
       NULL                                                                      AS externalid
FROM source T
WHERE T.due_days IS NOT NULL
  AND T.discount_days IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM c_paymentterm pt WHERE pt.value = LEFT(T.key, 70))
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
