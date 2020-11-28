CREATE MATERIALIZED VIEW IF NOT EXISTS MV_Fact_Acct_Sum AS
select fa.AD_Client_ID
     , fa.AD_Org_ID
     , fa.Account_ID
     , fa.C_AcctSchema_ID
     , fa.PostingType
     , fa.DateAcct
     , fa.C_Tax_ID
     , fa.vatcode
     , p.C_Period_ID
     , p.C_Year_ID
     -- Aggregated amounts
     , COALESCE(SUM(AmtAcctDr), 0) as AmtAcctDr
     , COALESCE(SUM(AmtAcctCr), 0) as AmtAcctCr
     , COALESCE(SUM(Qty), 0)       as Qty
FROM Fact_Acct fa
         left outer join C_Period p on (p.C_Period_ID = fa.C_Period_ID)
GROUP BY fa.AD_Client_ID
       , fa.AD_Org_ID
       , p.C_Period_ID
       , fa.DateAcct
       , fa.C_AcctSchema_ID
       , fa.PostingType
       , fa.Account_ID
       , fa.C_Tax_ID
       , fa.vatcode;
	   
	   
DROP FUNCTION IF EXISTS de_metas_acct.balanceToDate(p_AD_Org_ID numeric(10, 0), p_Account_ID numeric,
    p_DateAcct date,
    p_C_Vat_Code_ID numeric);

CREATE OR REPLACE FUNCTION de_metas_acct.balanceToDate(p_AD_Org_ID numeric(10, 0),
                                                       p_Account_ID numeric,
                                                       p_DateAcct date,
                                                       p_C_Vat_Code_ID numeric)
    RETURNS TABLE
            (
                vatcode     varchar,
                C_Tax_ID     numeric,
				accountNo   varchar,
				accountName varchar,
                Balance     de_metas_acct.BalanceAmt
            )
AS
$BODY$
WITH records AS
         (
             WITH filteredRecords AS (
                 with fact_records AS
                          (
                              select fa.AD_Client_ID
                                   , fa.AD_Org_ID
                                   , fa.Account_ID
                                   , fa.C_AcctSchema_ID
                                   , fa.PostingType
                                   , fa.DateAcct
                                   , fa.C_Tax_ID
                                   , fa.vatcode
                                   , p.C_Period_ID
                                   , p.C_Year_ID
                                   -- Aggregated amounts
                                   , COALESCE(SUM(AmtAcctDr), 0) as AmtAcctDr
                                   , COALESCE(SUM(AmtAcctCr), 0) as AmtAcctCr
                                   , COALESCE(SUM(Qty), 0)       as Qty
                              FROM Fact_Acct fa
                                       left outer join C_Period p on (p.C_Period_ID = fa.C_Period_ID)
                              GROUP BY fa.AD_Client_ID, fa.AD_Org_ID
                                     , p.C_Period_ID
                                     , fa.DateAcct
                                     , fa.C_AcctSchema_ID
                                     , fa.PostingType
                                     , fa.Account_ID
                                     , fa.C_Tax_ID
                                     , fa.vatcode
                          )
                 select fa.AD_Client_ID
                      , fa.AD_Org_ID
                      , fa.Account_ID
                      , fa.C_Tax_ID
                      , fa.vatcode
                      , fa.C_AcctSchema_ID
                      , fa.PostingType
                      , fa.DateAcct
                      -- Aggregated amounts: (beginning) to Date
                      , SUM(AmtAcctDr) over facts_ToDate     as AmtAcctDr
                      , SUM(AmtAcctCr) over facts_ToDate     as AmtAcctCr
                      , SUM(Qty) over facts_ToDate           as Qty
                      -- Aggregated amounts: Year to Date
                      , SUM(AmtAcctDr) over facts_YearToDate as AmtAcctDr_YTD
                      , SUM(AmtAcctCr) over facts_YearToDate as AmtAcctCr_YTD

                 from MV_Fact_Acct_Sum fa
                     window
                         facts_ToDate as (partition by fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Tax_ID, fa.vatcode order by fa.DateAcct)
                         , facts_YearToDate as (partition by fa.AD_Client_ID, fa.AD_Org_ID, fa.C_AcctSchema_ID, fa.PostingType, fa.Account_ID, fa.C_Tax_ID
                         , fa.vatcode, fa.C_Year_ID order by fa.DateAcct)
             )
             SELECT --
                    fa.PostingType,
                    ev.AccountType,
                    fa.AmtAcctCr,
                    fa.AmtAcctCr_YTD,
                    fa.AmtAcctDr,
                    fa.AmtAcctDr_YTD,
                    fa.DateAcct,
					fa.c_tax_id,
					fa.vatcode,
					ev.value as AccountNo,
					ev.Name as AccountName
             FROM filteredRecords fa
                      INNER JOIN C_ElementValue ev ON (ev.C_ElementValue_ID = fa.Account_ID) AND ev.isActive = 'Y'
					   LEFT OUTER JOIN C_Vat_Code vat on vat.C_Vat_Code_ID = p_C_Vat_Code_ID and vat.isActive = 'Y'
                              WHERE fa.DateAcct <= p_DateAcct
                                AND fa.account_id = p_Account_ID
                                AND fa.ad_org_id = p_AD_Org_ID
                                AND (CASE WHEN vat.vatcode IS NULL THEN TRUE ELSE vat.vatcode = fa.VatCode END)
         )
select vatcode,
       C_Tax_ID,  AccountNo, AccountName,
       ROW (SUM((Balance).Balance), SUM((Balance).Debit), SUM((Balance).Credit))::de_metas_acct.BalanceAmt
from (
         (
             select *
             from (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= date_trunc('year', to_date('01.03.2019', 'dd.mm.yyyy'))
                                      THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                  ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                          END)                            AS Balance, AccountNo, AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct desc ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        AND fo.PostingType = 'A'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) as a1
             where RowNo = 1
         )

         -- Include posting type Year End
         UNION ALL
         (
             select *
             from (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= date_trunc('year', to_date('01.03.2019', 'dd.mm.yyyy'))
                                      THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                  ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                          END)                            AS Balance, AccountNo, AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct desc ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        -- AND $6 = 'N' -- p_ExcludePostingTypeYearEnd
                        AND fo.PostingType = 'Y'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) as a2
             where RowNo = 1
         )

         -- Include posting type Statistical
         UNION ALL
         (
             select *
             from (
                      SELECT (CASE
                          -- When the account is Expense/Revenue => we shall consider only the Year to Date amount
                                  WHEN fo.AccountType IN ('E', 'R') AND
                                       fo.DateAcct >= date_trunc('year', to_date('01.03.2019', 'dd.mm.yyyy'))
                                      THEN ROW (fo.AmtAcctDr_YTD - fo.AmtAcctCr_YTD, fo.AmtAcctDr_YTD, fo.AmtAcctCr_YTD)::de_metas_acct.BalanceAmt
                                  WHEN fo.AccountType IN ('E', 'R') THEN ROW (0, 0, 0)::de_metas_acct.BalanceAmt
                          -- For any other account => we consider from the beginning to Date amount
                                  ELSE ROW (fo.AmtAcctDr - fo.AmtAcctCr, fo.AmtAcctDr, fo.AmtAcctCr)::de_metas_acct.BalanceAmt
                          END)                            AS Balance, AccountNo, AccountName,
                             C_tax_id,
                             vatcode,
                             ROW_NUMBER()
                             OVER (
                                 PARTITION BY C_tax_id, vatcode
                                 ORDER BY dateacct desc ) AS RowNo
                      FROM records fo
                      WHERE TRUE
                        -- AND $5 = 'Y' -- p_IncludePostingTypeStatistical
                        AND fo.PostingType = 'S'
                      ORDER BY fo.DateAcct DESC, c_tax_id, vatcode
                  ) as a3
             where RowNo = 1
         )
     ) a
group by vatcode, C_Tax_ID, AccountNo, AccountName
$BODY$
    LANGUAGE sql STABLE;
	
	
	
	
DROP FUNCTION IF EXISTS de_metas_acct.taxaccounts_details(p_AD_Org_ID numeric(10, 0),
    p_Account_ID numeric,
    p_C_Vat_Code_ID numeric,
    p_DateFrom date,
    p_DateTo date);

CREATE OR REPLACE FUNCTION de_metas_acct.taxaccounts_details(p_AD_Org_ID numeric(10, 0),
                                                             p_Account_ID numeric,
                                                             p_C_Vat_Code_ID numeric,
                                                             p_DateFrom date,
                                                             p_DateTo date)
    RETURNS TABLE
            (
                Balance           numeric,
                BalanceYear       numeric,
                accountNo         varchar,
                accountName       varchar,
                taxName           varchar,
                C_Tax_ID          numeric,
                vatcode           varchar,
                C_ElementValue_ID numeric,
                param_startdate   date,
                param_enddate     date,
                param_konto       varchar,
                param_vatcode     varchar,
                param_org         varchar
            )
AS
$BODY$
with accounts as
         (
             select C_ElementValue_ID
             from c_elementvalue
             where IsActive = 'Y'
               AND CASE WHEN p_Account_ID IS NULL THEN 1 = 1 ELSE C_ElementValue_ID = p_Account_ID END
         ),
     balanceRecords AS
         (
             Select b.*, a.C_ElementValue_ID
             from accounts a
                      join de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       p_DateTo,
                                                       p_C_Vat_Code_ID) as b on 1 = 1
         ),
     balanceRecords_dateFrom AS
         (
             Select b.*, a.C_ElementValue_ID
             from accounts a
                      join de_metas_acct.balanceToDate(p_AD_Org_ID,
                                                       C_ElementValue_ID,
                                                       p_DateFrom,
                                                       p_C_Vat_Code_ID) as b on 1 = 1
         ),
     balance AS
         (
             Select (coalesce((b2.Balance).Balance, 0) - coalesce((b1.Balance).Balance, 0)) as Balance,
                    coalesce((b2.Balance).Balance, 0)                                       as YearBalance,
                    b2.accountno,
                    b2.accountname,
                    b2.C_Tax_ID,
                    b2.vatcode,
                    b2.C_ElementValue_ID
             from balanceRecords b2
                      left join balanceRecords_dateFrom b1 on b1.c_elementvalue_id = b2.c_elementvalue_id
                 and b1.accountno = b2.accountno
                 and b1.accountname = b2.accountname
                 and coalesce(b1.vatcode, '') = coalesce(b2.vatcode, '')
                 and coalesce(b1.c_tax_id, 0) = coalesce(b2.c_tax_id, 0)
             where (b2.Balance).Debit > 0
                or (b2.Balance).Credit > 0
         )
select Balance,
       YearBalance,
       b.accountno,
       b.accountname,
       t.Name                                as taxName,
       b.C_Tax_ID,
       b.vatcode,
       b.C_ElementValue_ID,
       p_DateFrom                            AS param_startdate,
       p_DateTo                              AS param_enddate,
       (CASE
            WHEN p_Account_ID IS NULL
                THEN NULL
            ELSE (SELECT value || ' - ' || name
                  from C_ElementValue
                  WHERE C_ElementValue_ID = p_Account_ID
                    and isActive = 'Y') END) AS param_konto,
       (CASE
            WHEN p_C_Vat_Code_ID IS NULL
                THEN NULL
            ELSE (SELECT vatcode
                  FROM C_Vat_Code
                  WHERE C_Vat_Code_ID = p_C_Vat_Code_ID
                    and isActive = 'Y') END) AS param_vatcode,
       (CASE
            WHEN p_AD_Org_ID IS NULL
                THEN NULL
            ELSE (SELECT name
                  from ad_org
                  where ad_org_id = p_AD_Org_ID
                    and isActive = 'Y') END) AS param_org

from balance as b
         left outer join C_Tax t on t.C_tax_ID = b.C_tax_ID
order by vatcode, accountno
$BODY$
    LANGUAGE sql STABLE;
	
	
DROP FUNCTION IF EXISTS report.tax_accounting_report_details(IN p_dateFrom date,
    IN p_dateTo date,
    IN p_vatcode varchar,
    IN p_account_id numeric,
    IN p_c_tax_id numeric,
    IN p_org_id numeric,
    IN p_ad_language character varying(6));

CREATE OR REPLACE FUNCTION report.tax_accounting_report_details(IN p_dateFrom date,
                                                                IN p_dateTo date,
                                                                IN p_vatcode varchar,
                                                                IN p_account_id numeric,
                                                                IN p_c_tax_id numeric,
                                                                IN p_org_id numeric,
                                                                IN p_ad_language character varying(6))
    RETURNS TABLE
            (
                vatcode          character varying(10),
                kontono          character varying(40),
                kontoname        character varying(60),
                dateacct         date,
                documentno       character varying(40),
                taxname          character varying(60),
                taxrate          numeric,
                bpName           character varying,
                taxbaseamt       numeric,
                taxamt           numeric,
                taxamtperaccount numeric,
                IsTaxLine        character(1),
				iso_code         character(3),
                param_startdate  date,
                param_enddate    date,
                param_konto      character varying,
                param_vatcode    character varying,
                param_org        character varying,
                C_Tax_ID         numeric,
                account_id       numeric,
                ad_org_id        numeric
            )
AS
$$

SELECT DISTINCT ON (x.vatcode, x.kontono, x.kontoname, x.dateacct, x.documentno, x.taxname, x.taxrate, x.bpName, x.IsTaxLine, x.iso_code) x.vatcode,
                                                                                                                              x.kontono,
                                                                                                                              x.kontoname,
                                                                                                                              x.dateacct :: date,
                                                                                                                              x.documentno,
                                                                                                                              x.taxname,
                                                                                                                              x.taxrate,
                                                                                                                              x.bpName,
                                                                                                                              COALESCE(
                                                                                                                                      COALESCE(
                                                                                                                                              coalesce(x.inv_baseamt, x.gl_baseamt),
                                                                                                                                              x.hdr_baseamt,
                                                                                                                                              0 :: numeric)) as taxbaseamt,
                                                                                                                              COALESCE(
                                                                                                                                      COALESCE(
                                                                                                                                              coalesce(x.inv_taxamt, x.gl_taxamt),
                                                                                                                                              x.hdr_taxamt,
                                                                                                                                              0 :: numeric)) as taxamt,
                                                                                                                              taxamtperaccount,
                                                                                                                              IsTaxLine,
																															  x.iso_code,
                                                                                                                              x.p_dateFrom                   as param_startdate,
                                                                                                                              x.p_dateTo                     as param_endtdate,
                                                                                                                              x.param_konto,
                                                                                                                              x.param_vatcode,
                                                                                                                              x.param_org,
                                                                                                                              x.C_Tax_ID,
                                                                                                                              x.account_id,
                                                                                                                              x.ad_org_id
FROM (
         SELECT ev.value                              AS kontono,
                ev.name                               AS kontoname,
                fa.dateacct,
                fa.documentno,

                tax.name                              AS taxname,
                tax.rate                              AS taxrate,

                bp.name                               as bpName,

                i.taxbaseamt                          AS inv_baseamt,
                gl.taxbaseamt                         AS gl_baseamt,
                hdr.taxbaseamt                        AS hdr_baseamt,

                i.taxamt                              AS inv_taxamt,
                gl.taxamt                             AS gl_taxamt,
                hdr.taxamt                            AS hdr_taxamt,

                (case
                     when fa.line_id is null and fa.C_Tax_id is not null
                         then (fa.amtacctdr - fa.amtacctcr)
                     else 0
                    end)                              AS taxamtperaccount,
				
				c.iso_code,

                (case
                     when fa.line_id is null and fa.C_Tax_id is not null
                         then 'Y'
                     else 'N'
                    end)                              AS IsTaxLine,

                fa.vatcode                            AS vatcode,
                p_dateFrom,
                p_dateTo,
                (CASE
                     WHEN p_account_id IS NULL
                         THEN NULL
                     ELSE (SELECT value || ' - ' || name
                           from C_ElementValue
                           WHERE C_ElementValue_ID = p_account_id
                             and isActive = 'Y') END) AS param_konto,
                (CASE
                     WHEN p_vatcode IS NULL
                         THEN NULL
                     ELSE p_vatcode END)              AS param_vatcode,
                (CASE
                     WHEN p_org_id IS NULL
                         THEN NULL
                     ELSE (SELECT name
                           from ad_org
                           where ad_org_id = p_org_id
                             and isActive = 'Y') END) AS param_org,
                fa.C_Tax_ID,
                fa.account_id,
                fa.ad_org_id

         FROM public.fact_acct fa
                  -- gh #489: explicitly select from public.fact_acct, bacause the function de_metas_acct.Fact_Acct_EndingBalance expects it.

                  JOIN c_elementvalue ev ON ev.c_elementvalue_id = fa.account_id and ev.isActive = 'Y'
                  JOIN c_tax tax ON fa.c_tax_id = tax.c_tax_id and tax.isActive = 'Y'
					
				  LEFT JOIN C_Currency c on fa.c_Currency_ID = c.C_Currency_ID	
                  LEFT OUTER JOIN c_bpartner bp on fa.c_bpartner_id = bp.c_bpartner_id

             --Show all accounts, not only tax accounts
                  LEFT OUTER JOIN (select distinct vc.Account_ID as C_ElementValue_ID
                                   from C_Tax_Acct ta
                                            inner join C_ValidCombination vc on (vc.C_ValidCombination_ID in
                                                                                 (ta.T_Liability_Acct,
                                                                                  ta.T_Receivables_Acct, ta.T_Due_Acct,
                                                                                  ta.T_Credit_Acct, ta.T_Expense_Acct))
                                       and vc.isActive = 'Y'
                                   where ta.isActive = 'Y'
         ) ta ON ta.C_ElementValue_ID = ev.C_ElementValue_ID

             --if invoice
                  LEFT OUTER JOIN
              (SELECT (case
                           when dt.docbasetype <> 'APC'
                               then inv_tax.taxbaseamt
                           else (-1) * inv_tax.taxbaseamt
                  end)         as taxbaseamt,
                      (case
                           when dt.docbasetype <> 'APC'
                               then inv_tax.taxamt
                           else (-1) * inv_tax.taxamt
                          end) as taxamt,
                      i.c_invoice_id,
                      inv_tax.c_tax_id
               FROM c_invoice i
                        JOIN C_InvoiceTax inv_tax on i.c_invoice_id = inv_tax.c_invoice_id and inv_tax.isActive = 'Y'
                        join C_DocType dt on dt.C_DocType_ID = i.C_DocTypeTarget_id
               WHERE i.isActive = 'Y'
              ) i ON fa.record_id = i.c_invoice_id AND fa.ad_table_id = get_Table_Id('C_Invoice') AND
                     i.c_tax_id = fa.c_tax_id

                  --if gl journal
                  LEFT OUTER JOIN (SELECT (CASE
                                               WHEN gll.dr_autotaxaccount = 'Y'
                                                   THEN gll.dr_taxbaseamt
                                               WHEN cr_autotaxaccount = 'Y'
                                                   THEN gll.cr_taxbaseamt END)   AS taxbaseamt,
                                          (CASE
                                               WHEN gll.dr_autotaxaccount = 'Y'
                                                   THEN gll.dr_taxamt
                                               WHEN cr_autotaxaccount = 'Y'
                                                   THEN gll.cr_taxamt END)       AS taxamt,
                                          gl.gl_journal_id,
                                          gll.gl_journalline_id,
                                          COALESCE(gll.dr_tax_id, gll.cr_tax_id) AS tax_id
                                   FROM gl_journal gl
                                            JOIN GL_JournalLine gll
                                                 ON gl.gl_journal_id = gll.gl_journal_id and gll.isActive = 'Y'
                                   WHERE gl.isActive = 'Y'
         ) gl ON fa.record_id = gl.gl_journal_id AND fa.ad_table_id = get_Table_Id('GL_Journal') AND
                 gl.gl_journalline_id = fa.line_id AND gl.tax_id = fa.c_tax_id

             --if allocationHdr
                  LEFT OUTER JOIN (
             SELECT hdr.C_AllocationHdr_ID,
                    hdrl.C_AllocationLine_ID,
                    0 :: numeric as taxbaseamt,
                    -- leave taxbaseamt empty for now in allocationhdr
                    0 :: numeric as taxamt -- leave taxamt empty for now in allocationhdr
             FROM C_AllocationHdr hdr
                      JOIN C_AllocationLine hdrl
                           on hdr.C_AllocationHdr_ID = hdrl.C_AllocationHdr_ID and hdrl.isActive = 'Y'
             WHERE hdr.isActive = 'Y'
         ) hdr
                                  ON hdr.C_AllocationHdr_ID = fa.record_id AND
                                     fa.ad_table_id = get_Table_Id('C_AllocationHdr') AND
                                     fa.line_id = hdr.C_AllocationLine_ID

         WHERE fa.DateAcct >= p_dateFrom
           AND fa.DateAcct <= p_dateTo
           AND fa.postingtype IN ('A', 'Y')
           AND fa.ad_org_id = p_org_id
           AND (CASE
                    WHEN p_vatcode IS NULL
                        THEN TRUE
                    ELSE fa.VatCode = p_vatcode END)
           AND (CASE
                    WHEN p_account_id IS NULL
                        THEN TRUE
                    ELSE p_account_id = fa.account_id END)
           AND (CASE
                    WHEN p_c_tax_id IS NULL
                        THEN (fa.C_Tax_id is null)
                    ELSE p_c_tax_id = fa.C_Tax_id END)
           AND fa.isActive = 'Y'
     ) x

ORDER BY x.vatcode, x.kontono, x.kontoname, x.dateacct, x.documentno, x.taxname, x.taxrate, x.bpName
$$
    LANGUAGE sql
    STABLE;