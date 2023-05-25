
DROP FUNCTION IF EXISTS de_metas_acct.sourceAcctBalanceToDate(p_Account_ID numeric,
                                                              p_C_AcctSchema_ID numeric,
                                                              p_DateAcct date,
                                                              p_AD_Org_ID numeric(10, 0),
                                                              p_C_Currency_Id numeric
);

DROP FUNCTION IF EXISTS de_metas_acct.sourceAcctBalanceToDate(p_Account_ID numeric,
                                                              p_C_AcctSchema_ID numeric,
                                                              p_DateAcct date,
                                                              p_AD_Org_ID numeric(10, 0),
                                                              p_C_Currency_Id numeric,
                                                              p_IncludePostingTypeStatistical char(1)
);

DROP FUNCTION IF EXISTS de_metas_acct.sourceAcctBalanceToDate(p_Account_ID numeric,
                                                              p_C_AcctSchema_ID numeric,
                                                              p_DateAcct date,
                                                              p_AD_Org_ID numeric(10, 0),
                                                              p_C_Currency_Id numeric,
                                                              p_IncludePostingTypeStatistical char(1),
                                                              p_ExcludePostingTypeYearEnd char(1)
);

/*
 * #%L
 * de.metas.acct.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

/*

-- This type shall be already in the database. Do not create it again

CREATE TYPE de_metas_acct.BalanceAmt AS
(
	Balance numeric
    , Debit numeric
    , Credit numeric
);

 */


CREATE OR REPLACE FUNCTION de_metas_acct.sourceAcctBalanceToDate(p_Account_ID numeric,
                                                                 p_C_AcctSchema_ID numeric,
                                                                 p_DateAcct date,
                                                                 p_AD_Org_ID numeric(10, 0),
                                                                 p_C_Currency_Id numeric,
                                                                 p_IncludePostingTypeStatistical char(1) = 'N',
                                                                 p_ExcludePostingTypeYearEnd char(1) = 'N'
)
    RETURNS de_metas_acct.BalanceAmt
AS
$BODY$
WITH filteredAndOrdered AS (
    SELECT --
           fa.PostingType,
           ev.AccountType,
           fa.AmtSourceCr,
           fa.AmtSourceDr,
           fa.DateAcct
    FROM Fact_Acct fa
             INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID) AND ev.isActive = 'Y'
    WHERE TRUE
      AND fa.Account_ID = $1        -- p_Account_ID
      AND fa.C_AcctSchema_ID = $2   -- p_C_AcctSchema_ID
      AND fa.DateAcct <= $3         -- p_DateAcct
      AND fa.ad_org_id = $4         -- p_AD_Org_ID
      AND fa.c_currency_id = $5     -- p_C_Currency_Id
      AND fa.isActive = 'Y'
)
     -- NOTE: we use COALESCE(SUM(..)) just to make sure we are not returning null
SELECT ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
FROM (
         -- Include posting type Actual
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtSourceDr - fo.AmtSourceCr, fo.AmtSourceDr, fo.AmtSourceCr)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                                                                                                     ELSE ROW (fo.AmtSourceDr - fo.AmtSourceCr, fo.AmtSourceDr, fo.AmtSourceCr)::de_metas_acct.BalanceAmt
                     END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND fo.PostingType = 'A'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Include posting type Year End
         UNION ALL
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtSourceDr - fo.AmtSourceCr, fo.AmtSourceDr, fo.AmtSourceCr)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                                                                                                     ELSE ROW (fo.AmtSourceDr - fo.AmtSourceCr, fo.AmtSourceDr, fo.AmtSourceCr)::de_metas_acct.BalanceAmt
                     END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND $7 = 'N' -- p_ExcludePostingTypeYearEnd
               AND fo.PostingType = 'Y'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Include posting type Statistical
         UNION ALL
         (
             SELECT (CASE
                 -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                         WHEN fo.AccountType IN ('E', 'R') AND fo.DateAcct >= date_trunc('year', $3) THEN ROW (fo.AmtSourceDr - fo.AmtSourceCr, fo.AmtSourceDr, fo.AmtSourceCr)::de_metas_acct.BalanceAmt
                         WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                 -- For any other account => we consider from the beginning to Date amount
                                                                                                     ELSE ROW (fo.AmtSourceDr - fo.AmtSourceCr, fo.AmtSourceDr, fo.AmtSourceCr)::de_metas_acct.BalanceAmt
                     END) AS Balance
             FROM filteredAndOrdered fo
             WHERE TRUE
               AND $6 = 'Y' -- p_IncludePostingTypeStatistical
               AND fo.PostingType = 'S'
             ORDER BY fo.DateAcct DESC
             LIMIT 1
         )
         -- Default value:
         UNION ALL
         (
             SELECT ROW (0, 0, 0)::de_metas_acct.BalanceAmt
         )
     ) t
$BODY$
    LANGUAGE sql STABLE;

