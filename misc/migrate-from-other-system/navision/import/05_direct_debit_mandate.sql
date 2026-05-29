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

-- partners need to be imported first, from i_bpartner

DROP TABLE IF EXISTS migration_data.c_bp_bankaccount_directdebitmandate;
CREATE TABLE migration_data.c_bp_bankaccount_directdebitmandate AS
SELECT 1000000                AS ad_client_id,
       1000000                AS ad_org_id,
       bp.c_bpartner_id       AS c_bpartner_id,
       ba.c_bp_bankaccount_id AS c_bp_bankaccount_id,
       NOW()                  AS created,
       99                     AS createdby,
       CASE
           WHEN T_BAM."Mandate Last Used" = '1753-01-01 00:00:00' THEN NULL
           ELSE T_BAM."Mandate Last Used"
           END                AS datelastused,
       CASE
           WHEN T_BAM."Mandate Date" = '1753-01-01 00:00:00' THEN 'N'
           ELSE 'Y'
           END                AS isactive,
       CASE
           WHEN T_BAM."Mandate Frequency" = 1 THEN 'Y'
           ELSE 'N'
           END                AS isrecurring,
       T_BAM."Mandate Date"   AS mandatedate,
       T_BAM."Mandate ID"     AS mandatereference,
       CASE
           WHEN T_BAM."Mandate Status" = 0 THEN 'F'
           WHEN T_BAM."Mandate Status" = 1 THEN 'L'
           WHEN T_BAM."Mandate Status" = 2 THEN 'R'
           END                AS mandatestatus,
       NOW()                  AS updated,
       99                     AS updatedby
FROM migration_data."Navision$Bank Account Mandate" AS T_BAM
         JOIN migration_data."Navision$Customer Bank Account" cba
              ON T_BAM."Mandate ID" = cba."Mandate ID"
         JOIN c_bpartner bp ON bp.debtorid = NULLIF(cba."Customer No_", '')::numeric AND bp.ad_org_id = 1000000
         JOIN c_bp_bankaccount ba ON ba.iban = cba."IBAN" AND ba.c_bpartner_id = bp.c_bpartner_id
;

INSERT INTO c_bp_bankaccount_directdebitmandate (ad_client_id,
                                                 ad_org_id,
                                                 c_bpartner_id,
                                                 c_bp_bankaccount_directdebitmandate_id,
                                                 c_bp_bankaccount_id,
                                                 created,
                                                 createdby,
                                                 datelastused,
                                                 isactive,
                                                 isrecurring,
                                                 mandatedate,
                                                 mandatereference,
                                                 mandatestatus,
                                                 updated,
                                                 updatedby)
SELECT s.ad_client_id,
       s.ad_org_id,
       s.c_bpartner_id,
       nextval('c_bp_bankaccount_directdebitmandate_seq'),
       s.c_bp_bankaccount_id,
       s.created,
       s.createdby,
       s.datelastused,
       s.isactive,
       s.isrecurring,
       s.mandatedate,
       s.mandatereference,
       s.mandatestatus,
       s.updated,
       s.updatedby
FROM migration_data.c_bp_bankaccount_directdebitmandate s
;
