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
                                                  p_includepostingtypestatistical character,
                                                  p_excludepostingtypeyearend     character,
                                                  p_IsShowProductDetails          character,
                                                  p_IsShowActivityDetails         character
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
                parentname1       varchar,
                parentvalue1      varchar,
                parentname2       varchar,
                parentvalue2      varchar,
                parentname3       varchar,
                parentvalue3      varchar,
                parentname4       varchar,
                parentvalue4      varchar,
                name              varchar,
                namevalue         varchar,
                AccountType       char(1),

                sameyearsum       numeric,
                lastyearsum       numeric,
                euroSaldo         numeric,
                L4_sameyearsum    numeric,
                L4_lastyearsum    numeric,
                L3_sameyearsum    numeric,
                L3_lastyearsum    numeric,
                L2_sameyearsum    numeric,
                L2_lastyearsum    numeric,
                L1_sameyearsum    numeric,
                L1_lastyearsum    numeric,

                -- More info
                C_Calendar_ID     numeric,
                C_ElementValue_ID numeric,

                ad_org_id         numeric,
                currency          character(3)
            )
AS
$BODY$
    # VARIABLE_CONFLICT USE_COLUMN
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
    SELECT lvl.Lvl1_name  AS ParentName1
         , lvl.Lvl1_value AS ParentValue1
         , lvl.Lvl2_name  AS ParentName2
         , lvl.Lvl2_value AS ParentValue2
         , lvl.Lvl3_name  AS ParentName3
         , lvl.Lvl3_value AS ParentValue3
         , lvl.Lvl4_value AS ParentValue4
         , lvl.Lvl4_name  AS ParentName4
         , lvl.Name       AS Name
         , lvl.Value      AS Value
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
            END)          AS IsConvertToEUR

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
         , a.ParentName1
         , a.ParentValue1
         , a.ParentName2
         , a.ParentValue2
         , a.ParentName3
         , a.ParentValue3
         , a.ParentValue4
         , a.ParentName4
         , a.Name
         , a.Value
         , a.AccountType
         , a.IsConvertToEUR

         , (de_metas_acct.acctBalanceToDate(a.C_ElementValue_ID, v_AcctSchemaInfo.C_AcctSchema_ID, p_date::date, p_ad_org_id, p_includepostingtypestatistical, p_excludepostingtypeyearend)).Balance * a.Multiplicator               AS SameYearSum
         , (de_metas_acct.acctBalanceToDate(a.C_ElementValue_ID, v_AcctSchemaInfo.C_AcctSchema_ID, v_periodInfo.period_LastYearEnd_EndDate::date, p_ad_org_id, p_includepostingtypestatistical, p_excludepostingtypeyearend)).Balance * a.Multiplicator AS LastYearSum

    FROM tmp_accounts a;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Computed balances for % accounts', v_rowcount;
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

           SameYearSum,
           LastYearSum,
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
             SELECT tb.ParentName1
                  , tb.ParentValue1
                  , tb.ParentName2
                  , tb.ParentValue2
                  , tb.ParentName3
                  , tb.ParentValue3
                  , tb.ParentValue4
                  , tb.ParentName4
                  , tb.Name
                  , tb.Value
                  , tb.AccountType

                  , tb.SameYearSum
                  , tb.LastYearSum


                  , tb.IsConvertToEUR

                  --
                  , v_AcctSchemaInfo.C_Currency_ID -- Accounting currency
                  , v_AcctSchemaInfo.AD_Client_ID

                  , v_AcctSchemaInfo.C_Calendar_ID
                  , tb.C_ElementValue_ID
                  , v_AcctSchemaInfo.iso_code
             FROM tmp_balances tb
         ) a;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Created tmp_reports with % rows', v_rowcount;

    <<RESULT_TABLE>>
    BEGIN
        RETURN QUERY
            SELECT b.parentname1,
                   b.parentvalue1,
                   b.parentname2,
                   b.parentvalue2,
                   b.parentname3,
                   b.parentvalue3,
                   b.parentname4,
                   b.parentvalue4,
                   b.name,
                   b.value,
                   b.AccountType,

                   b.SameYearSum,
                   b.LastYearSum,
                   b.L4_euroSaldo,
                   --
                   b.L4_SameYearSum,
                   b.L4_LastYearSum,
                   b.L3_SameYearSum,
                   b.L3_LastYearSum,
                   b.L2_SameYearSum,
                   b.L2_LastYearSum,
                   b.L1_SameYearSum,
                   b.L1_LastYearSum,

                   -- More info:
                   b.C_Calendar_ID,
                   b.C_ElementValue_ID,

                   b.ad_org_id,
                   b.iso_code
            FROM tmp_report_balance b
            ORDER BY parentValue1, parentValue2, parentValue3, parentValue4, value;

    END RESULT_TABLE;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;