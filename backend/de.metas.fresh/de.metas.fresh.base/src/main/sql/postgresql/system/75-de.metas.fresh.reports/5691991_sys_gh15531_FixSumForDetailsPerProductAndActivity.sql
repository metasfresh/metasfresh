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
                currency          character(3),
                OverallCount      bigint,
                activityname      text,
                productname       text,
                level             text
            )
AS
$BODY$
    # VARIABLE_CONFLICT USE_COLUMN
DECLARE
    v_rowcount       numeric;
    v_AcctSchemaInfo record;
    v_periodInfo     record;
    v_activityInfo   record;
    v_productInfo    record;
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

    -- get period info
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

    -- get accounts with level hierarchy
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

         , (de_metas_acct.acctBalanceToDate(a.C_ElementValue_ID, v_AcctSchemaInfo.C_AcctSchema_ID, p_date::date, p_ad_org_id, p_includepostingtypestatistical, p_excludepostingtypeyearend)).Balance * a.Multiplicator                                  AS SameYearSum
         , (de_metas_acct.acctBalanceToDate(a.C_ElementValue_ID, v_AcctSchemaInfo.C_AcctSchema_ID, v_periodInfo.period_LastYearEnd_EndDate::date, p_ad_org_id, p_includepostingtypestatistical, p_excludepostingtypeyearend)).Balance * a.Multiplicator AS LastYearSum

    FROM tmp_accounts a;
    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Computed balances for % accounts', v_rowcount;
    CREATE UNIQUE INDEX ON tmp_balances (C_ElementValue_ID);


    -- compute balance for levels as well
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
           a.iso_code
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
                  , COUNT(0) OVER () AS OverallCount
                  , NULL::text       AS activityName
                  , NULL::text       AS productName
                  , '1'              AS level
             FROM tmp_balances tb
         ) a;

    GET DIAGNOSTICS v_rowcount = ROW_COUNT;
    RAISE NOTICE 'Created tmp_reports with % rows', v_rowcount;


    -- assemble the result in one temporray table
    DROP TABLE IF EXISTS tmp_final_balance_report;
    CREATE TEMPORARY TABLE tmp_final_balance_report AS
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
           b.iso_code,
           COUNT(0) OVER () AS OverallCount,
           NULL::text       AS activityName,
           NULL::text       AS productName,
           '1'::text        AS level
    FROM tmp_report_balance b;


    -- compute balances per activity and product - level 2

    IF p_IsShowProductDetails = 'Y' OR p_IsShowActivityDetails = 'Y' THEN

        -- compute balance per  account and activity/product  until given date
        DROP TABLE IF EXISTS tmp_Fact_Acct_Per_Activity_Product;
        CREATE TEMPORARY TABLE tmp_Fact_Acct_Per_Activity_Product AS
        SELECT fa.AD_Client_ID
             , fa.Account_ID
             , fa.C_AcctSchema_ID
             , (CASE WHEN p_IsShowActivityDetails = 'Y' THEN fa.c_activity_id END) AS c_activity_id
             , (CASE WHEN p_IsShowProductDetails = 'Y' THEN fa.m_product_id END)   AS m_product_id
             , a.name                                                              AS activityName
             , p.name                                                              AS productName
             , COALESCE(SUM(fa.AmtAcctDr - fa.AmtAcctCr), 0)                       AS Balance
        FROM Fact_Acct fa
                 INNER JOIN c_elementvalue ev ON fa.account_id = ev.c_elementvalue_id
                 LEFT OUTER JOIN C_Activity a ON ((CASE WHEN p_IsShowActivityDetails = 'Y' THEN fa.c_activity_id END) = a.c_activity_id)
                 LEFT OUTER JOIN M_Product p ON ((CASE WHEN p_IsShowProductDetails = 'Y' THEN fa.m_product_id END) = p.m_product_id)
        WHERE fa.dateacct <= p_date
          AND fa.ad_org_id = p_ad_org_id
          AND (fa.PostingType = 'A' OR (p_ExcludePostingTypeYearEnd = 'N' AND fa.PostingType = 'Y') OR (p_IncludePostingTypeStatistical = 'Y' AND fa.PostingType = 'S'))
          AND (ev.AccountType NOT IN ('E', 'R') OR fa.DateAcct >= p_date)
        GROUP BY fa.AD_Client_ID
               , fa.C_AcctSchema_ID
               , fa.Account_ID
               , (CASE WHEN p_IsShowActivityDetails = 'Y' THEN fa.c_activity_id END)
               , (CASE WHEN p_IsShowProductDetails = 'Y' THEN fa.m_product_id END)
               , a.name
               , p.name;

        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Sums  per activity and product for % dates', v_rowcount;
        CREATE UNIQUE INDEX ON tmp_Fact_Acct_Per_Activity_Product (Account_ID, M_Product_ID, C_Activity_ID);

        -- compute sums per account and activity/product until given date
        DROP TABLE IF EXISTS tmp_activity_product_balances_todate;
        CREATE TEMPORARY TABLE tmp_activity_product_balances_todate AS

        SELECT a.C_ElementValue_ID
             , a.parentname1
             , a.parentvalue1
             , a.parentname2
             , a.parentvalue2
             , a.parentname3
             , a.parentvalue3
             , a.parentname4
             , a.parentvalue4
             , a.name
             , a.value
             , a.AccountType
             , m_product_id
             , c_activity_id
             , activityName
             , productName
             , Balance
        FROM tmp_Fact_Acct_Per_Activity_Product AS t
                 INNER JOIN tmp_accounts a ON a.C_ElementValue_ID = t.Account_ID;

        GET DIAGNOSTICS v_rowcount = ROW_COUNT;
        RAISE NOTICE 'Show balances per activity and product for % tmp_activity_product_balances_todate', v_rowcount;
        CREATE UNIQUE INDEX ON tmp_activity_product_balances_todate (C_ElementValue_ID, M_Product_ID, C_Activity_ID);


        -- insert the result into final report table
        INSERT INTO tmp_final_balance_report(parentname1,
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
                                             L4_euroSaldo,
            --
                                             L4_SameYearSum,
                                             L4_LastYearSum,
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
                                             iso_code,
                                             OverallCount,
                                             activityName,
                                             productName,
                                             level)

        SELECT tb.parentname1,
               tb.parentvalue1,
               tb.parentname2,
               tb.parentvalue2,
               tb.parentname3,
               tb.parentvalue3,
               tb.parentname4,
               tb.parentvalue4,
               tb.name,
               tb.value,
               tb.AccountType,

               tb.balance       AS SameYearSum,
               NULL::numeric    AS LastYearSum,
               NULL::numeric    AS L4_euroSaldo,
               --
               NULL::numeric    AS L4_SameYearSum,
               NULL::numeric    AS L4_LastYearSum,
               NULL::numeric    AS L3_SameYearSum,
               NULL::numeric    AS L3_LastYearSum,
               NULL::numeric    AS L2_SameYearSum,
               NULL::numeric    AS L2_LastYearSum,
               NULL::numeric    AS L1_SameYearSum,
               NULL::numeric    AS L1_LastYearSum,

               -- More info:
               v_AcctSchemaInfo.C_Calendar_ID,
               tb.C_ElementValue_ID,

               p_ad_org_id      AS ad_org_id,
               v_AcctSchemaInfo.iso_code,
               COUNT(0) OVER () AS OverallCount,
               tb.activityName,
               tb.productName,
               '2'::text        AS level
        FROM tmp_activity_product_balances_todate tb;

    END IF;

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
                   b.iso_code,
                   b.OverallCount,
                   b.activityName,
                   b.productName,
                   b.level
            FROM tmp_final_balance_report b
            ORDER BY parentValue1, parentValue2, parentValue3, parentValue4, value, level;

    END RESULT_TABLE;
END;
$BODY$
    LANGUAGE plpgsql VOLATILE
;
