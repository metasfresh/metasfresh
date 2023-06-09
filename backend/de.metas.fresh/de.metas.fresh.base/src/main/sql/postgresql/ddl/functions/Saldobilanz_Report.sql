DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN            Date,
                                                   IN defaultAcc character varying)
;

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN                      Date,
                                                   IN defaultAcc           character varying,
                                                   IN showCurrencyExchange character varying)
;

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN                                 Date,
                                                   IN defaultAcc                      character varying,
                                                   IN showCurrencyExchange            character varying,
                                                   IN p_IncludePostingTypeStatistical char(1))
;

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN                                 Date,
                                                   IN defaultAcc                      character varying,
                                                   IN showCurrencyExchange            character varying,
                                                   IN p_IncludePostingTypeStatistical char(1),
                                                   IN ad_org_id                       numeric(10, 0))
;

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN                                 Date,
                                                   IN defaultAcc                      character varying,
                                                   IN showCurrencyExchange            character varying,
                                                   IN ad_org_id                       numeric(10, 0),
                                                   IN p_IncludePostingTypeStatistical char(1))
;

DROP FUNCTION IF EXISTS report.saldobilanz_Report (IN                                 Date,
                                                   IN defaultAcc                      character varying,
                                                   IN showCurrencyExchange            character varying,
                                                   IN ad_org_id                       numeric(10, 0),
                                                   IN p_IncludePostingTypeStatistical char(1),
                                                   IN p_ExcludePostingTypeYearEnd     char(1))
;

DROP FUNCTION IF EXISTS report.saldobilanz_report(p_date                          date,
                                                  p_defaultacc                    character varying,
                                                  p_showcurrencyexchange          character varying,
                                                  p_ad_org_id                     numeric,
                                                  p_includepostingtypestatistical character DEFAULT 'N'::bpchar,
                                                  p_excludepostingtypeyearend     character DEFAULT 'N'::bpchar,
                                                  p_IsShowProductDetails          character DEFAULT 'N'::bpchar,
                                                  p_IsShowActivityDetails         character DEFAULT 'N'::bpchar
)
;


DROP TABLE IF EXISTS report.saldobilanz_Report
;


CREATE OR REPLACE FUNCTION report.saldobilanz_report(p_date                          date,
                                                     p_defaultacc                    character varying,
                                                     p_showcurrencyexchange          character varying,
                                                     p_ad_org_id                     numeric,
                                                     p_includepostingtypestatistical character DEFAULT 'N'::bpchar,
                                                     p_excludepostingtypeyearend     character DEFAULT 'N'::bpchar,
                                                     p_IsShowProductDetails          character DEFAULT 'N'::bpchar,
                                                     p_IsShowActivityDetails         character DEFAULT 'N'::bpchar
)
    RETURNS TABLE
            (
                parentname1       text,
                parentvalue1      text,
                parentname2       TEXT,
                parentvalue2      TEXT,
                parentname3       TEXT,
                parentvalue3      TEXT,
                name              TEXT,
                value             TEXT,
                AccountType       TEXT,
                L4_SameYearSum    numeric,
                L4_LastYearSum    numeric,
                L4_euroSaldo      numeric,

                L3_SameYearSum    numeric,
                L3_LastYearSum    numeric,
                L2_SameYearSum    numeric,
                L2_LastYearSum    numeric,
                L1_SameYearSum    numeric,
                L1_LastYearSum    numeric,
                C_Calendar_ID     numeric,
                C_ElementValue_ID numeric,
                AD_Org_ID         numeric,
                iso_code          text
            )
AS
$BODY$
DECLARE
    v_rowcount       numeric;
    v_AcctSchemaInfo record;
    v_periodInfo     record;
BEGIN


    -- Get Accounting Schema Info
    SELECT acs.c_acctschema_id,
           acs.c_currency_id,
           acs.ad_client_id,
           ci.c_calendar_id,
           cr.iso_code
    INTO v_AcctSchemaInfo
    FROM AD_Client c
             INNER JOIN AD_ClientInfo ci ON ci.AD_Client_ID = c.ad_client_id
             INNER JOIN C_AcctSchema acs ON acs.C_AcctSchema_ID = ci.C_AcctSchema1_ID
             INNER JOIN C_Currency cr ON acs.C_Currency_ID = cr.C_Currency_ID AND cr.isActive = 'Y'
    WHERE c.AD_Client_ID = 1000000;

    SELECT period_LastYearEnd.EndDate::date   AS period_LastYearEnd_EndDate,
           period_LastYearEnd.StartDate::date AS period_LastYearEnd_StartDate,
           p.EndDate::date                    AS EndDate,
           p.StartDate::date                  AS StartDate
    INTO v_periodInfo
    FROM C_Period p
             -- Get last period of previous year
             LEFT OUTER JOIN C_Period period_LastYearEnd ON (period_LastYearEnd.C_Period_ID = report.Get_Predecessor_Period_Recursive(p.C_Period_ID, p.PeriodNo::int)) AND period_LastYearEnd.isActive = 'Y'
    WHERE TRUE
      -- Period: determine it by DateAcct
      AND p.C_Period_ID = report.Get_Period(v_AcctSchemaInfo.C_Calendar_ID, p_date);

    DROP TABLE IF EXISTS tmp_accounts;
    CREATE TEMPORARY TABLE tmp_accounts AS
    SELECT lvl.Lvl1_name
         , lvl.Lvl1_value
         , lvl.Lvl2_name
         , lvl.Lvl2_value
         , lvl.Lvl3_name
         , lvl.Lvl3_value
         , lvl.Lvl4_name
         , lvl.Lvl4_value
         , lvl.Name
         , lvl.Value
         , ev.AccountType
         , ev.C_ElementValue_ID
         , ev.Multiplicator
         , (CASE
                WHEN p_showcurrencyexchange = 'N' -- we don't need to check if the elementValue has a foreign currency
                    THEN FALSE
                    ELSE -- check if the element value is set to show the Internation currency and if this currency is EURO. Convert to EURO in this case
                    (
                        EXISTS
                            (SELECT 1 FROM C_ElementValue elv WHERE lvl.C_ElementValue_ID = elv.C_ElementValue_ID AND elv.ShowIntCurrency = 'Y' AND elv.Foreign_Currency_ID = (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') AND elv.isActive = 'Y')
                        )
            END) AS IsConvertToEUR

    FROM C_Element_Levels lvl
             INNER JOIN (
        SELECT ev.C_ElementValue_ID
             -- NOTE: by customer requirement, we are not considering the account sign but always DR - CR
             , 1 AS Multiplicator
             -- , acctBalance(C_ElementValue_ID, 1, 0) AS Multiplicator
             , ev.ad_client_id
             , ev.AccountType
             , ev.AD_Org_ID
        FROM C_ElementValue ev
                 JOIN C_Element e ON e.C_Element_id = ev.C_Element_ID AND e.IsActive = 'Y'

        WHERE ev.isActive = 'Y'
          AND e.IsNaturalAccount = 'Y'
    ) ev ON (lvl.C_ElementValue_ID = ev.C_ElementValue_ID
        -- make sure we show the standard accounts from metasfresh (org 0)
        AND (CASE WHEN lvl.lvl1_value != 'ZZ' THEN (ev.ad_org_id = p_ad_org_id) ELSE (ev.ad_org_id = 0) END))
         --
    WHERE TRUE
      -- Shall we Show default accounts?
      AND (CASE WHEN p_defaultacc = 'Y' THEN TRUE ELSE lvl1_value != 'ZZ' END);

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Selected % accounts', v_rowcount;

    --
    -- Compute year to date balance and since all times balance
    DROP TABLE IF EXISTS tmp_balances;
    CREATE TEMPORARY TABLE tmp_balances AS
    SELECT a.C_ElementValue_ID

         , (de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, v_AcctSchemaInfo.C_AcctSchema_ID, p_date::date, p_ad_org_id, p_includepostingtypestatistical, p_excludepostingtypeyearend)).Balance * a.Multiplicator               AS SameYearSum
         , (de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, v_AcctSchemaInfo.C_AcctSchema_ID, v_periodInfo.EndDate::date, p_ad_org_id, p_includepostingtypestatistical, p_excludepostingtypeyearend)).Balance * a.Multiplicator AS LastYearSum

    FROM tmp_accounts a;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Computed beginning balances for % accounts', v_rowcount;
    CREATE UNIQUE INDEX ON tmp_balances (C_ElementValue_ID);


    DROP TABLE IF EXISTS tmp_report_balance;

    CREATE TEMPORARY TABLE tmp_report_balance
    AS
    SELECT p_date::Date                                                                                             AS RunDate,
           parentname1,
           parentvalue1,
           parentname2,
           parentvalue2,
           parentname3,
           parentvalue3,
           parentname4,
           parentvalue4,
           name,
           value,
           AccountType,

           SameYearSum                                                                                              AS L4_SameYearSum,
           LastYearSum                                                                                              AS L4_LastYearSum,
           (CASE
                WHEN IsConvertToEUR
                    THEN currencyConvert(a.SameYearSum
                    , a.C_Currency_ID -- p_curfrom_id
                    , (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') -- p_curto_id
                    , p_date -- p_convdate
                    , (SELECT C_ConversionType_ID FROM C_ConversionType WHERE Value = 'P' AND isActive = 'Y') -- p_conversiontype_id
                    , a.AD_Client_ID
                    , p_ad_org_id --ad_org_id
                    )
                    ELSE NULL
            END)                                                                                                    AS L4_euroSaldo,
           --
           SUM(CASE WHEN ParentValue4 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue4) AS L4_SameYearSum,
           SUM(CASE WHEN ParentValue4 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue4) AS L4_LastYearSum,
           SUM(CASE WHEN ParentValue3 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue3) AS L3_SameYearSum,
           SUM(CASE WHEN ParentValue3 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue3) AS L3_LastYearSum,
           SUM(CASE WHEN ParentValue2 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue2) AS L2_SameYearSum,
           SUM(CASE WHEN ParentValue2 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue2) AS L2_LastYearSum,
           SUM(CASE WHEN ParentValue1 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue1) AS L1_SameYearSum,
           SUM(CASE WHEN ParentValue1 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue1) AS L1_LastYearSum,

           -- More info:
           C_Calendar_ID
            ,
           C_ElementValue_ID,

           p_ad_org_id                                                                                              AS ad_org_id,
           a.iso_code,
           NULL::numeric                                                                                            AS rolling_balance,
           NULL::text                                                                                               AS productname,
           NULL::text                                                                                               AS activityname,
           NULL::text                                                                                               AS level
    FROM (
             SELECT ta.Lvl1_name  AS ParentName1
                  , ta.Lvl1_value AS ParentValue1
                  , ta.Lvl2_name  AS ParentName2
                  , ta.Lvl2_value AS ParentValue2
                  , ta.Lvl3_name  AS ParentName3
                  , ta.Lvl3_value AS ParentValue3
                  , ta.Lvl4_value AS ParentValue4
                  , ta.Lvl4_name  AS ParentName4
                  , ta.Name       AS Name
                  , ta.Value      AS Value
                  , ta.AccountType

                  , tb.SameYearSum
                  , tb.LastYearSum


                  , ta.IsConvertToEUR

                  --
                  , v_AcctSchemaInfo.C_Currency_ID -- Accounting currency
                  , v_AcctSchemaInfo.AD_Client_ID

                  , v_AcctSchemaInfo.C_Calendar_ID
                  , ta.C_ElementValue_ID
                  , v_AcctSchemaInfo.iso_code
             FROM tmp_accounts ta
                      INNER JOIN tmp_balances tb ON ta.C_ElementValue_ID = tb.C_ElementValue_ID
         ) a;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Created tmp_reports with % rows', v_rowcount;

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            SELECT parentname1,
                   parentvalue1,
                   parentname2,
                   parentvalue2,
                   parentname3,
                   parentvalue3,
                   name,
                   value,
                   AccountType,

                   L4_SameYearSum,
                   L4_LastYearSum,
                   L4_euroSaldo,
                   --
                   L3_SameYearSum,
                   L3_LastYearSum,
                   L2_SameYearSum,
                   L2_LastYearSum,
                   L1_SameYearSum,
                   L1_LastYearSum,

                   -- More info:
                   C_Calendar_ID,
                   C_ElementValue_ID,

                   ad_org_id,
                   iso_code
            FROM tmp_report_balance
            ORDER BY parentValue1, parentValue2, parentValue3, parentValue4, value;
    END RESULT_TABLE;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;
