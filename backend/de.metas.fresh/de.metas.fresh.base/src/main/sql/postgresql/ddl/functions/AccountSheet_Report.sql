/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

DROP FUNCTION IF EXISTS de_metas_acct.AccountSheet_Report(
    p_Account_ID      numeric,
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date
)
;


CREATE OR REPLACE FUNCTION de_metas_acct.AccountSheet_Report(
    p_Account_ID      numeric,
    p_C_AcctSchema_ID numeric,
    p_DateAcctFrom    DATE,
    p_DateAcctTo      date
)
    RETURNS
        TABLE
        (
            konto         text,
            gegenkonto    text,
            soll          numeric,
            haben         numeric,
            soll_währung  numeric,
            haben_währung numeric,
            währung       text,
            Belegdatum    text,
            Buchungsdatum text,
            "Nr"          text
        )
    LANGUAGE sql
    STABLE


AS
$BODY$
SELECT t.AccountValueAndName::text                                                            AS konto,
       t.counterpart_AccountValueAndName::text                                                AS gegenkonto,
       t.amtacctdr                                                                            AS soll,
       t.amtacctcr                                                                            AS haben,
       t.amtsourcedr                                                                          AS soll_währung,
       t.amtsourcecr                                                                          AS haben_währung,
       (SELECT cy.iso_code FROM c_currency cy WHERE cy.c_currency_id = t.c_currency_id)::text AS währung,
       TO_CHAR(t.datetrx, 'DD.MM.YYYY')::text                                                 AS Belegdatum,
       TO_CHAR(t.dateacct, 'DD.MM.YYYY')::text                                                AS Buchungsdatum,
       t.documentno::text                                                                     AS "Nr"
FROM de_metas_acct.RV_AccountSheet t
WHERE TRUE
  AND t.PostingType = 'A'
  AND t.C_AcctSchema_ID = p_C_AcctSchema_ID
  AND t.account_id = p_Account_ID
  AND (p_DateAcctFrom IS NULL OR t.dateacct >= p_DateAcctFrom)
  AND (p_DateAcctTo IS NULL OR t.dateacct <= p_DateAcctTo)
ORDER BY t.dateacct, t.ad_table_id, t.record_id, t.fact_acct_id
    ;
$BODY$
;

