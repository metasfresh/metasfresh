DROP FUNCTION IF EXISTS report.fresh_account_info_report_sub(numeric,
                                                             numeric,
                                                             numeric,
                                                             numeric,
                                                             date,
                                                             date,
                                                             numeric,
                                                             varchar,
                                                             varchar,
                                                             varchar,
                                                             numeric)
;

CREATE FUNCTION report.fresh_account_info_report_sub(account_start_id      numeric,
                                                     account_end_id        numeric,
                                                     c_period_start_id     numeric,
                                                     c_period_end_id       numeric,
                                                     startdate             date,
                                                     enddate               date,
                                                     c_activity_id         numeric,
                                                     displayvoiddocuments  character varying,
                                                     showcurrencyexchange  character varying,
                                                     showonlyemptyactivity character varying,
                                                     ad_org_id             numeric)
    RETURNS TABLE
            (
                dateacct               date,
                fact_acct_id           numeric,
                bp_name                text,
                description            text,
                account2_id            text,
                a_value                text,
                amtsourcedr            numeric,
                amtsourcecr            numeric,
                amtacctdr              numeric,
                amtacctcr              numeric,
                amtacctdrend           numeric,
                amtacctcrend           numeric,
                saldo                  numeric,
                carrysaldo             numeric,
                param_acct_value       text,
                param_acct_name        text,
                param_end_date         date,
                param_start_date       date,
                param_activity_value   text,
                param_activity_name    text,
                docstatus              text,
                conversionmultiplyrate numeric,
                eurosaldo              numeric,
                containseur            boolean,
                ad_org_id              numeric,
                vat_code               text,
                tax_rate_name          text
            )
    STABLE
    LANGUAGE sql
AS
$$
SELECT fa.DateAcct::Date,
       fa.Fact_Acct_ID,
       COALESCE(
               REPLACE(SUBSTRING(jl.description FOR POSITION('/' IN jl.description)), '/', ''),
               bp.name
           )                                                                                                                                       AS BP_Name,
       COALESCE(
               REPLACE(SUBSTRING(jl.description FROM POSITION('/' IN jl.description)), '/', ''),
               COALESCE(tbl.name || ' ', '') || fa.Description
           )                                                                                                                                       AS Description,
       (CASE
            WHEN
                    (SELECT COUNT(0)
                     FROM Fact_Acct fa2
                     WHERE fa.ad_table_id = fa2.ad_table_id
                       AND fa2.Record_ID = fa.Record_ID
                       AND fa.Fact_Acct_id != fa2.Fact_Acct_id
                       AND (CASE WHEN fa.amtacctdr != 0 THEN fa2.amtacctcr != 0 WHEN fa.amtacctcr != 0 THEN fa2.amtacctdr != 0 ELSE FALSE END)
                       AND fa2.isActive = 'Y')
                    = 1 THEN (SELECT ev2.value || ' ' || ev2.name
                              FROM Fact_Acct fa2
                                       INNER JOIN C_ElementValue ev2 ON fa2.Account_ID = ev2.C_ElementValue_ID AND ev2.isActive = 'Y'
                              WHERE fa.ad_table_id = fa2.ad_table_id
                                AND fa2.Record_ID = fa.Record_ID
                                AND fa.Fact_Acct_id != fa2.Fact_Acct_id
                                AND (CASE WHEN fa.amtacctdr != 0 THEN fa2.amtacctcr != 0 WHEN fa.amtacctcr != 0 THEN fa2.amtacctdr != 0 ELSE FALSE END)
                                AND fa2.isActive = 'Y')
                        ELSE ''
        END)                                                                                                                                       AS Account2_ID, -- this selects the name and value of one or no element value, that is matching with the current fact_acct (see when you press verbucht on your docu there is more than 1 line). Later shall be changed in some way so it can selece more, but currently we cannot associate more

       a.value                                                                                                                                     AS A_Value,
       fa.amtsourcedr,
       fa.amtsourcecr,
       fa.AmtAcctDr,
       fa.AmtAcctCr,
       SUM(fa.AmtAcctDr) OVER (PARTITION BY param_acct_value)                                                                                      AS AmtAcctDrEnd,
       SUM(fa.AmtAcctCr) OVER (PARTITION BY param_acct_value)                                                                                      AS AmtAcctCrEnd,
       CarryBalance + SUM(Balance) OVER (
           PARTITION BY fa.Account_ID
           ORDER BY fa.Account_ID, fa.DateAcct, fa.Fact_Acct_ID
           )                                                                                                                                       AS IterativeBalance,
       CarryBalance,
       param_acct_value,
       param_acct_name,

       COALESCE($6::date, (SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4 AND isActive = 'Y'))                                          AS Param_End_Date,
       COALESCE($5::date, (SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3 AND isActive = 'Y'))                                        AS Param_Start_Date,

       pa.value                                                                                                                                    AS Param_Activity_Value,
       pa.Name                                                                                                                                     AS Param_Activit_Name,
       fa.docStatus,
       ConversionMultiplyRate,
       CASE WHEN $9 = 'Y' AND ConversionMultiplyRate IS NOT NULL THEN ConversionMultiplyRate * (CarryBalance + SUM(Balance) OVER ()) ELSE NULL END AS EuroSaldo,
       containsEUR,
       fa.ad_org_id,
       fa.vat_code,
       fa.tax_rate_name
FROM (SELECT fa.Account_ID,
             fa.C_Activity_ID,
             fa.description,
             DateAcct,
             Fact_Acct_ID,
             AD_Table_ID,
             Line_ID,
             amtsourcedr,
             amtsourcecr,
             AmtAcctDr,
             AmtAcctCr,
             fa.C_BPartner_ID,
             ev.value                                                                         AS Param_Acct_Value,
             ev.name                                                                          AS Param_Acct_Name,
             fa.Record_ID,
             COALESCE(AmtAcctDr - AmtAcctCr, 0)                                               AS Balance,
             (de_metas_acct.acctbalanceuntildate
                 (ev.C_ElementValue_ID,
                  acs.C_AcctSchema_ID,
                  COALESCE($5::date, (SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3)),
                  $11)).Balance
                                                                                              AS CarryBalance,
             fa.DocStatus,
             --currencyrate returns the multiplyrate of the conversion rate for: currency from , currency to, date, conversion type, client id and org id
             currencyrate(
                     c.c_currency_id,
                     (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y'),
                     COALESCE($6::date, (SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4 AND isActive = 'Y')),
                     (SELECT C_ConversionType_ID FROM C_ConversionType WHERE value = 'P' AND isActive = 'Y'),
                     ci.AD_Client_ID, ci.ad_org_id)
                                                                                              AS ConversionMultiplyRate,
             CASE
                 WHEN $9 = 'N' -- we don't need to check if the elementValue has a foreign currency

                     THEN 'N'
                     ELSE -- check if the element value is set to show the Internation currency and if this currency is EURO. Convert to EURO in this case
                     (
                         EXISTS
                             (SELECT 1 FROM C_ElementValue elv WHERE ev.C_ElementValue_ID = elv.C_ElementValue_ID AND elv.ShowIntCurrency = 'Y' AND elv.Foreign_Currency_ID = (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') AND elv.isActive = 'Y')
                         )
             END
                                                                                              AS containsEUR,
             fa.ad_org_id,
             fa.vatcode                                                                       AS vat_code,
             (SELECT t.name FROM c_tax t WHERE fa.c_tax_id = t.c_tax_id AND t.isactive = 'Y') AS tax_rate_name
      FROM (SELECT ev.C_ElementValue_ID, ev.value, ev.name, ev.ad_client_id
            FROM C_ElementValue ev
                     JOIN C_ElementValue ev_from ON ev_from.C_ElementValue_ID = $1 AND ev_from.isActive = 'Y'
                     JOIN C_ElementValue ev_to ON ev_to.C_ElementValue_ID = $2 AND ev_to.isActive = 'Y'
            WHERE ev.value >= ev_from.value
              AND ev.value <= ev_to.value
              AND CHAR_LENGTH(ev.value) >= CHAR_LENGTH(ev_from.value)
              AND CHAR_LENGTH(ev.value) <= CHAR_LENGTH(ev_to.value)
              AND ev.isActive = 'Y' --getting elements between the selected values

           ) ev

               LEFT OUTER JOIN Fact_Acct fa ON fa.Account_ID = ev.C_ElementValue_ID
          AND DateAcct >= COALESCE($5::date, (SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3 AND isActive = 'Y'))
          AND DateAcct <= COALESCE($6::date, (SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4 AND isActive = 'Y'))
          AND (CASE WHEN $7 IS NOT NULL THEN COALESCE(C_Activity_ID = $7, FALSE) ELSE TRUE END) -- this used to be COALESCE( C_Activity_ID = $7, true) and it was showing the empty ones too when activity id was set
          AND fa.isActive = 'Y'

          --taking the currency of the client to convert it into euro
               LEFT OUTER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = ev.ad_client_id AND ci.isActive = 'Y'
               LEFT OUTER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID AND acs.isActive = 'Y'
               LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'

               LEFT OUTER JOIN C_Period period_LastYearEnd ON (period_LastYearEnd.C_Period_ID = report.Get_Predecessor_Period_Recursive($3, (SELECT PeriodNo::int FROM C_Period WHERE C_Period_ID = $3 AND isActive = 'Y'))) AND period_LastYearEnd.isActive = 'Y'
      WHERE fa.postingtype IN ('A', 'Y') -- task 09804 don't show/sum other than current (A) -- now we have other current Y (year end)
     ) fa
         LEFT OUTER JOIN GL_JournalLine jl ON fa.Line_ID = jl.GL_JournalLine_ID AND fa.AD_Table_ID = (SELECT Get_Table_ID('GL_Journal')) AND jl.isActive = 'Y'
         LEFT OUTER JOIN AD_Table tbl ON fa.AD_Table_ID = tbl.AD_Table_ID AND tbl.isActive = 'Y'
         LEFT OUTER JOIN C_BPartner bp ON fa.C_BPartner_ID = bp.C_BPartner_ID AND bp.isActive = 'Y'
         LEFT OUTER JOIN C_Activity a ON fa.C_Activity_ID = a.C_Activity_ID AND a.isActive = 'Y'
         LEFT OUTER JOIN C_Activity pa ON COALESCE(pa.C_Activity_ID = $7, FALSE) AND pa.isActive = 'Y'

WHERE CASE
          WHEN ($8 = 'Y') THEN
              1 = 1
                          ELSE
              (
                  fa.DocStatus NOT IN ('CL', 'VO', 'RE')
                  )
      END

  AND fa.ad_org_id = $11

  AND CASE
          WHEN ($10 = 'N') THEN
              1 = 1
                           ELSE
              (
                  fa.C_Activity_ID IS NULL
                  )
      END

ORDER BY fa.Param_Acct_Value,
         fa.Account_ID,
         fa.DateAcct::Date,
         fa.Fact_Acct_ID
$$
;



ALTER FUNCTION report.fresh_account_info_report_sub(numeric, numeric, numeric, numeric, date, date, numeric, varchar, varchar, varchar, numeric) OWNER TO metasfresh
;