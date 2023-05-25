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

DROP FUNCTION IF EXISTS  report.fresh_account_info_report_sub(
    IN account_from_id       numeric,
    IN account_to_id         numeric,
    IN C_Period_Start_ID     numeric,
    IN C_Period_End_ID       numeric,
    IN StartDate             date,
    IN EndDate               date,
    IN c_activity_id         numeric,
    IN displayvoiddocuments  character varying,
    IN showcurrencyexchange  character varying,
    IN showonlyemptyactivity character varying,
    IN ad_org_id             numeric)
;

DROP FUNCTION IF EXISTS  report.fresh_account_info_report(
    IN p_account_from_id        numeric,
    IN p_account_to_id          numeric,
    IN p_C_Period_Start_ID      numeric,
    IN p_C_Period_End_ID        numeric,
    IN p_StartDate              date,
    IN p_EndDate                date,
    IN p_c_activity_id          numeric,
    IN p_displayvoiddocuments   character varying,
    IN p_showcurrencyexchange   character varying,
    IN p_showonlyemptyactivity  character varying,
    IN p_ad_org_id              numeric)
;

/* should already be present
CREATE TABLE tmp_fresh_account_info_report_sub
(
    dateacct               date,
    fact_acct_id           numeric(10),
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
    ad_org_id              numeric(10),
    vat_code               text,
    tax_rate_name          text,
    account_id             numeric(10),
    c_acctschema_id        numeric(10),
    start_date_acct        date,
    source_currency_id     numeric(10),
    sourcebalance1         numeric,
    rollingbalance1        numeric,
    currency1              text,
    sourcebalance2         numeric,
    rollingbalance2        numeric,
    currency2              text,
    sourcebalance3         numeric,
    rollingbalance3        numeric,
    currency3              text,
    sourcebalance4         numeric,
    rollingbalance4        numeric,
    currency4              text,
    sourcebalance5         numeric,
    rollingbalance5        numeric,
    currency5              text
)
;
*/

DROP FUNCTION IF EXISTS  report.fresh_account_info_report(
    IN account_from_id       numeric,
    IN account_to_id         numeric,
    IN C_Period_Start_ID     numeric,
    IN C_Period_End_ID       numeric,
    IN StartDate             date,
    IN EndDate               date,
    IN c_activity_id         numeric,
    IN displayvoiddocuments  character varying,
    IN showcurrencyexchange  character varying,
    IN showonlyemptyactivity character varying,
    IN ad_org_id             numeric)
;

CREATE OR REPLACE FUNCTION report.fresh_account_info_report(
    IN p_account_from_id        numeric,
    IN p_account_to_id          numeric,
    IN p_C_Period_Start_ID      numeric,
    IN p_C_Period_End_ID        numeric,
    IN p_StartDate              date,
    IN p_EndDate                date,
    IN p_c_activity_id          numeric,
    IN p_displayvoiddocuments   character varying,
    IN p_showcurrencyexchange   character varying,
    IN p_showonlyemptyactivity  character varying,
    IN p_ad_org_id              numeric)
    RETURNS TABLE
            (
                dateacct             date,
                fact_acct_id         numeric,
                bp_name              text,
                description          text,
                account2_id          text,
                a_value              text,
                amtsourcedr          numeric,
                amtsourcecr          numeric,
                amtacctdr            numeric,
                amtacctcr            numeric,
                saldo                numeric,
                param_acct_value     text,
                param_acct_name      text,
                param_end_date       date,
                param_start_date     date,
                param_activity_value text,
                param_activity_name  text,
                overallcount         bigint,
                unionorder           integer,
                docstatus            text,
                eurosaldo            numeric,
                containseur          boolean,
                ad_org_id            numeric,
                vat_code             text,
                tax_rate_name        text,
                sourcebalance1       numeric,
                rollingbalance1      numeric,
                currency1            text,
                sourcebalance2       numeric,
                rollingbalance2      numeric,
                currency2            text,
                sourcebalance3       numeric,
                rollingbalance3      numeric,
                currency3            text,
                sourcebalance4       numeric,
                rollingbalance4      numeric,
                currency4            text,
                sourcebalance5       numeric,
                rollingbalance5      numeric,
                currency5            text
            )
AS

$BODY$
BEGIN
    <<INIT_TABLE>>
    BEGIN
        DROP TABLE IF EXISTS report.tmp_fresh_account_info_report_sub;
        CREATE TABLE report.tmp_fresh_account_info_report_sub
        AS SELECT fa.DateAcct::Date,
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

                  CAST ( a.value AS text )                                                                                                                                     AS A_Value,
                  fa.amtsourcedr,
                  fa.amtsourcecr,
                  fa.AmtAcctDr,
                  fa.AmtAcctCr,
                  SUM(fa.AmtAcctDr) OVER (PARTITION BY fa.param_acct_value)                                                                                      AS AmtAcctDrEnd,
                  SUM(fa.AmtAcctCr) OVER (PARTITION BY fa.param_acct_value)                                                                                      AS AmtAcctCrEnd,
                  fa.CarryBalance + SUM(Balance) OVER (
                      PARTITION BY fa.Account_ID
                      ORDER BY fa.Account_ID, fa.DateAcct, fa.Fact_Acct_ID
                      )                                                                                                                                       AS saldo,
                  fa.CarryBalance AS carrysaldo,
                  CAST ( fa.param_acct_value AS text ),
                  CAST ( fa.param_acct_name AS text ),

                  COALESCE($6::date, (SELECT enddate::date FROM C_Period WHERE C_Period_ID = $4 AND isActive = 'Y'))                                          AS Param_End_Date,
                  COALESCE($5::date, (SELECT startdate::date FROM C_Period WHERE C_Period_ID = $3 AND isActive = 'Y'))                                        AS Param_Start_Date,

                  pa.value::text                                                                                                                                    AS Param_Activity_Value,
                  pa.Name::text                                                                                                                                     AS Param_Activity_Name,
                  fa.docStatus::text,
                  fa.ConversionMultiplyRate,
                  CASE WHEN $9 = 'Y' AND fa.ConversionMultiplyRate IS NOT NULL THEN fa.ConversionMultiplyRate * (fa.CarryBalance + SUM(Balance) OVER ()) ELSE NULL END AS EuroSaldo,
                  fa.containsEUR,
                  fa.ad_org_id,
                  fa.vat_code::text,
                  fa.tax_rate_name::text,
                  fa.account_id,
                  fa.c_acctschema_id,
                  fa.start_date_acct,
                  fa.c_currency_id    AS source_currency_id,
                  NULL::numeric       AS sourcebalance1,
                  NULL::numeric       AS rollingbalance1,
                  NULL::text          AS currency1,
                  NULL::numeric       AS sourcebalance2,
                  NULL::numeric       AS rollingbalance2,
                  NULL::text          AS currency2,
                  NULL::numeric       AS sourcebalance3,
                  NULL::numeric       AS rollingbalance3,
                  NULL::text          AS currency3,
                  NULL::numeric       AS sourcebalance4,
                  NULL::numeric       AS rollingbalance4,
                  NULL::text          AS currency4,
                  NULL::numeric       AS sourcebalance5,
                  NULL::numeric       AS rollingbalance5,
                  NULL::text          AS currency5
           FROM (SELECT fa.Account_ID,
                        fa.C_Activity_ID,
                        fa.description,
                        fa.DateAcct,
                        fa.Fact_Acct_ID,
                        AD_Table_ID,
                        Line_ID,
                        fa.amtsourcedr,
                        fa.amtsourcecr,
                        fa.AmtAcctDr,
                        fa.AmtAcctCr,
                        fa.C_BPartner_ID,
                        ev.value                           AS Param_Acct_Value,
                        ev.name                            AS Param_Acct_Name,
                        fa.Record_ID,
                        COALESCE(fa.AmtAcctDr - fa.AmtAcctCr, 0) AS Balance,
                        (de_metas_acct.acctbalancetodate
                            (ev.C_ElementValue_ID,
                             acs.C_AcctSchema_ID,
                             COALESCE($5::date, (SELECT p.startdate::date FROM C_Period p WHERE C_Period_ID = $3)),
                             $11)).Balance
                            AS CarryBalance,
                        fa.DocStatus,
                        --currencyrate returns the multiplyrate of the conversion rate for: currency from , currency to, date, conversion type, client id and org id
                        currencyrate(
                                c.c_currency_id,
                                (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y'),
                                COALESCE($6::date, (SELECT p.enddate::date FROM C_Period p WHERE C_Period_ID = $4 AND isActive = 'Y')),
                                (SELECT C_ConversionType_ID FROM C_ConversionType WHERE value = 'P' AND isActive = 'Y'),
                                ci.AD_Client_ID, ci.ad_org_id)
                            AS ConversionMultiplyRate,
                        CASE
                            WHEN $9 = 'N' -- we don't need to check if the elementValue has a foreign currency

                                THEN FALSE
                                ELSE -- check if the element value is set to show the Internation currency and if this currency is EURO. Convert to EURO in this case
                                (
                                    EXISTS
                                        (SELECT 1 FROM C_ElementValue elv WHERE ev.C_ElementValue_ID = elv.C_ElementValue_ID AND elv.ShowIntCurrency = 'Y' AND elv.Foreign_Currency_ID = (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') AND elv.isActive = 'Y')
                                    )
                        END
                            AS containsEUR,
                        fa.ad_org_id,
                        fa.vatcode                                                                       AS vat_code,
                        (SELECT t.name FROM c_tax t WHERE fa.c_tax_id = t.c_tax_id AND t.isactive = 'Y') AS tax_rate_name,
                        fa.c_currency_id,
                        acs.C_AcctSchema_ID,
                        COALESCE($5::date, (SELECT p.startdate::date FROM C_Period p WHERE C_Period_ID = $3)) AS start_date_acct

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
                     AND fa.DateAcct >= COALESCE($5::date, (SELECT p.startdate::date FROM C_Period p WHERE C_Period_ID = $3 AND isActive = 'Y'))
                     AND fa.DateAcct <= COALESCE($6::date, (SELECT p.enddate::date FROM C_Period p WHERE C_Period_ID = $4 AND isActive = 'Y'))
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
                    fa.Fact_Acct_ID;
    END INIT_TABLE;

    <<UPDATE_TABLE>>
        DECLARE
        currencies numeric[];
        length int;
        first boolean;
        row report.tmp_fresh_Account_Info_Report_Sub%rowtype;
        v_currencyid1 numeric;
        v_currencyid2 numeric;
        v_currencyid3 numeric;
        v_currencyid4 numeric;
        v_currencyid5 numeric;
        v_currency1 text;
        v_currency2 text;
        v_currency3 text;
        v_currency4 text;
        v_currency5 text;
        v_sourcebalance1 numeric;
        v_sourcebalance2 numeric;
        v_sourcebalance3 numeric;
        v_sourcebalance4 numeric;
        v_sourcebalance5 numeric;
        v_rollingbalance1 numeric;
        v_rollingbalance2 numeric;
        v_rollingbalance3 numeric;
        v_rollingbalance4 numeric;
        v_rollingbalance5 numeric;
    BEGIN
        currencies := ARRAY (SELECT DISTINCT source_currency_id FROM report.tmp_fresh_Account_Info_Report_Sub);
        length := array_length(currencies, 1);

        IF length > 0 AND length <= 5 AND $9 = 'Y'THEN --p_showcurrencyexchange (used for option show foreign currency)

            v_currencyid1 :=  currencies[1];
            SELECT iso_code FROM c_currency WHERE c_currency_id = v_currencyid1 INTO v_currency1;
            IF length > 1 THEN
                v_currencyid2 :=  currencies[2];
                SELECT iso_code FROM c_currency WHERE c_currency_id = v_currencyid2 INTO v_currency2;
            END IF;
            IF length > 2 THEN
                v_currencyid3 :=  currencies[3];
                SELECT iso_code FROM c_currency WHERE c_currency_id = v_currencyid3 INTO v_currency3;
            END IF;
            IF length > 3 THEN
                v_currencyid4 :=  currencies[4];
                SELECT iso_code FROM c_currency WHERE c_currency_id = v_currencyid4 INTO v_currency4;
            END IF;
            IF length > 4 THEN
                v_currencyid5 :=  currencies[5];
                SELECT iso_code FROM c_currency WHERE c_currency_id = v_currencyid5 INTO v_currency5;
            END IF;

            first := true;
            FOR row IN SELECT * FROM report.tmp_fresh_Account_Info_Report_Sub ORDER BY DateAcct, Fact_Acct_ID
                LOOP
                    IF first THEN
                        v_sourcebalance1 := (de_metas_acct.sourceAcctBalanceToDate(row.Account_ID, row.C_AcctSchema_ID, row.start_date_acct, row.ad_org_id, v_currencyid1)).Balance;
                        v_sourcebalance2 := (de_metas_acct.sourceAcctBalanceToDate(row.Account_ID, row.C_AcctSchema_ID, row.start_date_acct, row.ad_org_id, v_currencyid2)).Balance;
                        v_sourcebalance3 := (de_metas_acct.sourceAcctBalanceToDate(row.Account_ID, row.C_AcctSchema_ID, row.start_date_acct, row.ad_org_id, v_currencyid3)).Balance;
                        v_sourcebalance4 := (de_metas_acct.sourceAcctBalanceToDate(row.Account_ID, row.C_AcctSchema_ID, row.start_date_acct, row.ad_org_id, v_currencyid4)).Balance;
                        v_sourcebalance5 := (de_metas_acct.sourceAcctBalanceToDate(row.Account_ID, row.C_AcctSchema_ID, row.start_date_acct, row.ad_org_id, v_currencyid5)).Balance;
                        first := false;
                    END IF;
                    IF row.source_currency_id = v_currencyid1 THEN
                        v_rollingbalance1 := v_sourcebalance1 + (row.amtsourcedr - row.amtsourcecr);
                    END IF;
                    IF row.source_currency_id = v_currencyid2 THEN
                        v_rollingbalance2 := v_sourcebalance2 + (row.amtsourcedr - row.amtsourcecr);
                    END IF;
                    IF row.source_currency_id = v_currencyid3 THEN
                        v_rollingbalance3 := v_sourcebalance3 + (row.amtsourcedr - row.amtsourcecr);
                    END IF;
                    IF row.source_currency_id = v_currencyid4 THEN
                        v_rollingbalance4 := v_sourcebalance4 + (row.amtsourcedr - row.amtsourcecr);
                    END IF;
                    IF row.source_currency_id = v_currencyid5 THEN
                        v_rollingbalance5 := v_sourcebalance5 + (row.amtsourcedr - row.amtsourcecr);
                    END IF;

                    UPDATE report.tmp_fresh_Account_Info_Report_Sub r
                    SET rollingbalance1 = v_rollingbalance1, rollingbalance2 = v_rollingbalance2, rollingbalance3 = v_rollingbalance3, rollingbalance4 = v_rollingbalance4, rollingbalance5 = v_rollingbalance5,
                        sourcebalance1 = v_sourcebalance1, sourcebalance2 = v_sourcebalance2, sourcebalance3 = v_sourcebalance3, sourcebalance4 = v_sourcebalance4, sourcebalance5 = v_sourcebalance5,
                        currency1 = v_currency1, currency2 = v_currency2, currency3 = v_currency3,currency4 = v_currency4, currency5 = v_currency5
                    WHERE r.fact_acct_id = row.fact_acct_id;

                    --UPDATE sourcebalance for next row
                    v_sourcebalance1 :=  v_rollingbalance1;
                    v_sourcebalance2 :=  v_rollingbalance2;
                    v_sourcebalance3 :=  v_rollingbalance3;
                    v_sourcebalance4 :=  v_rollingbalance4;
                    v_sourcebalance5 :=  v_rollingbalance5;

                END LOOP;
        END IF;
    END UPDATE_TABLE;

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY SELECT
                         r.DateAcct,
                         r.Fact_Acct_ID,
                         r.BP_Name,
                         r.Description,
                         r.Account2_ID,
                         r.a_Value,
                         r.amtsourcedr,
                         r.amtsourcecr,
                         r.AmtAcctDr,
                         r.AmtAcctCr,
                         r.Saldo,
                         r.Param_Acct_Value,
                         r.Param_Acct_Name,
                         r.Param_End_Date,
                         r.Param_Start_Date,
                         r.Param_Activity_Value,
                         r.Param_Activity_Name,
                         COUNT(0) OVER () AS overallcount,
                         2                AS UnionOrder,
                         r.DocStatus,
                         NULL::numeric,
                         NULL::boolean,
                         r.ad_org_id,
                         r.vat_code,
                         r.tax_rate_name,
                         r.sourcebalance1,
                         r.rollingbalance1,
                         r.currency1,
                         r.sourcebalance2,
                         r.rollingbalance2,
                         r.currency2,
                         r.sourcebalance3,
                         r.rollingbalance3,
                         r.currency3,
                         r.sourcebalance4,
                         r.rollingbalance4,
                         r.currency4,
                         r.sourcebalance5,
                         r.rollingbalance5,
                         r.currency5
                     FROM report.tmp_fresh_Account_Info_Report_Sub r
                     WHERE r.Fact_Acct_ID IS NOT NULL
                     UNION ALL
                     SELECT DISTINCT NULL::date,
                                     NULL::numeric,
                                     NULL,
                                     'Anfangssaldo',
                                     NULL::text,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::numeric,
                                     r.CarrySaldo,
                                     r.Param_Acct_Value,
                                     r.Param_Acct_Name,
                                     r.Param_End_Date,
                                     r.Param_Start_Date,
                                     r.Param_Activity_Value,
                                     r.Param_Activity_Name,
                                     COUNT(0) OVER () AS overallcount,
                                     1,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::boolean,
                                     r.ad_org_id,
                                     NULL::text,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text
                     FROM report.tmp_fresh_Account_Info_Report_Sub r
                     UNION ALL
                     SELECT DISTINCT NULL::date,
                                     NULL::numeric,
                                     NULL,
                                     'Summe',
                                     NULL::text,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     r.AmtAcctDrEnd,
                                     r.AmtAcctCrEnd,
                                     r.CarrySaldo,
                                     r.Param_Acct_Value,
                                     r.Param_Acct_Name,
                                     r.Param_End_Date,
                                     r.Param_Start_Date,
                                     r.Param_Activity_Value,
                                     r.Param_Activity_Name,
                                     COUNT(0) OVER () AS overallcount,
                                     3,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::boolean,
                                     r.ad_org_id,
                                     NULL::text,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text,
                                     NULL::numeric,
                                     NULL::numeric,
                                     NULL::text
                     FROM report.tmp_fresh_Account_Info_Report_Sub r
                     UNION ALL
                     (SELECT DISTINCT NULL::date,
                                      NULL::numeric,
                                      NULL,
                                      'Summe in EUR',
                                      NULL::text,
                                      NULL::text,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::numeric,
                                      r.Param_Acct_Value,
                                      r.Param_Acct_Name,
                                      r.Param_End_Date,
                                      r.Param_Start_Date,
                                      r.Param_Activity_Value,
                                      r.Param_Activity_Name,
                                      COUNT(0) OVER () AS overallcount,
                                      4,
                                      NULL::text,
                                      r.EuroSaldo,
                                      r.containsEUR,
                                      r.ad_org_id,
                                      NULL::text,
                                      NULL::text,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::text,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::text,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::text,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::text,
                                      NULL::numeric,
                                      NULL::numeric,
                                      NULL::text
                      FROM report.tmp_fresh_Account_Info_Report_Sub r
                      WHERE r.containsEUR = 'Y')
                     ORDER BY Param_Acct_Value, UnionOrder, DateAcct,
                              Fact_Acct_ID;
    END RESULT_TABLE;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE ;


