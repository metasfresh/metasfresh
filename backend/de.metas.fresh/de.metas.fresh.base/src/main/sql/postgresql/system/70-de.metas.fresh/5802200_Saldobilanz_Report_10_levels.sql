-- Source DDL: backend/de.metas.fresh/de.metas.fresh.base/src/main/sql/postgresql/ddl/functions/Saldobilanz_Report.sql
-- Extends saldobilanz_Report to 10 levels: adds parentname5-9/parentvalue5-9 columns
-- and L5-L9 window-function sum pairs so depth-6+ accounts are included in subtotals.

--
-- this overwrites a view in de.metas.fresh !
--
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

DROP TABLE IF EXISTS report.saldobilanz_Report
;



CREATE TABLE report.saldobilanz_Report
(
    orgValue          character varying(60),
    orgName           character varying(60),
    parentname1       character varying(60),
    parentvalue1      character varying(60),
    parentname2       character varying(60),
    parentvalue2      character varying(60),
    parentname3       character varying(60),
    parentvalue3      character varying(60),
    parentname4       character varying(60),
    parentvalue4      character varying(60),
    parentname5       character varying(60),
    parentvalue5      character varying(60),
    parentname6       character varying(60),
    parentvalue6      character varying(60),
    parentname7       character varying(60),
    parentvalue7      character varying(60),
    parentname8       character varying(60),
    parentvalue8      character varying(60),
    parentname9       character varying(60),
    parentvalue9      character varying(60),
    name              character varying(60),
    namevalue         character varying(60),
    AccountType       char(1),

    sameyearsum       numeric,
    lastyearsum       numeric,
    euroSaldo         numeric,
    L9_sameyearsum    numeric,
    L9_lastyearsum    numeric,
    L8_sameyearsum    numeric,
    L8_lastyearsum    numeric,
    L7_sameyearsum    numeric,
    L7_lastyearsum    numeric,
    L6_sameyearsum    numeric,
    L6_lastyearsum    numeric,
    L5_sameyearsum    numeric,
    L5_lastyearsum    numeric,
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
    WITH (
        OIDS= FALSE
    )
;

CREATE FUNCTION report.saldobilanz_Report(IN p_Date                          Date,
                                          IN p_defaultAcc                    character varying,
                                          IN p_showCurrencyExchange          character varying,
                                          IN p_ad_org_id                     numeric(10, 0),
                                          IN p_IncludePostingTypeStatistical char(1) = 'N',
                                          p_ExcludePostingTypeYearEnd        char(1) = 'N') RETURNS SETOF report.saldobilanz_Report
AS
$BODY$
SELECT o.value                                                                                                  AS orgValue,
       o.name                                                                                                   AS orgName,
       parentname1,
       parentvalue1,
       parentname2,
       parentvalue2,
       parentname3,
       parentvalue3,
       parentname4,
       parentvalue4,
       parentname5,
       parentvalue5,
       parentname6,
       parentvalue6,
       parentname7,
       parentvalue7,
       parentname8,
       parentvalue8,
       parentname9,
       parentvalue9,
       a.name,
       a.value,
       AccountType,

       SameYearSum,
       LastYearSum,
       (CASE
            WHEN IsConvertToEUR
                THEN currencyConvert(a.SameYearSum
                , a.C_Currency_ID -- p_curfrom_id
                , (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') -- p_curto_id
                , p_Date -- p_convdate
                , (SELECT C_ConversionType_ID FROM C_ConversionType WHERE Value = 'P' AND isActive = 'Y') -- p_conversiontype_id
                , a.AD_Client_ID
                , p_ad_org_id --ad_org_id
                     )
                ELSE NULL
        END)                                                                                                    AS L4_euroSaldo,
       --
       SUM(CASE WHEN ParentValue9 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue9) AS L9_SameYearSum,
       SUM(CASE WHEN ParentValue9 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue9) AS L9_LastYearSum,
       SUM(CASE WHEN ParentValue8 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue8) AS L8_SameYearSum,
       SUM(CASE WHEN ParentValue8 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue8) AS L8_LastYearSum,
       SUM(CASE WHEN ParentValue7 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue7) AS L7_SameYearSum,
       SUM(CASE WHEN ParentValue7 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue7) AS L7_LastYearSum,
       SUM(CASE WHEN ParentValue6 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue6) AS L6_SameYearSum,
       SUM(CASE WHEN ParentValue6 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue6) AS L6_LastYearSum,
       SUM(CASE WHEN ParentValue5 IS NOT NULL THEN SameYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue5) AS L5_SameYearSum,
       SUM(CASE WHEN ParentValue5 IS NOT NULL THEN LastYearSum ELSE NULL END) OVER ( PARTITION BY ParentValue5) AS L5_LastYearSum,
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
FROM (SELECT lvl.Lvl1_name                                                                                                                                                                                                        AS ParentName1
           , lvl.Lvl1_value                                                                                                                                                                                                       AS ParentValue1
           , lvl.Lvl2_name                                                                                                                                                                                                        AS ParentName2
           , lvl.Lvl2_value                                                                                                                                                                                                       AS ParentValue2
           , lvl.Lvl3_name                                                                                                                                                                                                        AS ParentName3
           , lvl.Lvl3_value                                                                                                                                                                                                       AS ParentValue3
           , lvl.Lvl4_name                                                                                                                                                                                                        AS ParentName4
           , lvl.Lvl4_value                                                                                                                                                                                                       AS ParentValue4
           , lvl.Lvl5_name                                                                                                                                                                                                        AS ParentName5
           , lvl.Lvl5_value                                                                                                                                                                                                       AS ParentValue5
           , lvl.Lvl6_name                                                                                                                                                                                                        AS ParentName6
           , lvl.Lvl6_value                                                                                                                                                                                                       AS ParentValue6
           , lvl.Lvl7_name                                                                                                                                                                                                        AS ParentName7
           , lvl.Lvl7_value                                                                                                                                                                                                       AS ParentValue7
           , lvl.Lvl8_name                                                                                                                                                                                                        AS ParentName8
           , lvl.Lvl8_value                                                                                                                                                                                                       AS ParentValue8
           , lvl.Lvl9_name                                                                                                                                                                                                        AS ParentName9
           , lvl.Lvl9_value                                                                                                                                                                                                       AS ParentValue9
           , lvl.Name                                                                                                                                                                                                             AS Name
           , lvl.Value                                                                                                                                                                                                            AS Value
           , ev.AccountType

           , (de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, acs.C_AcctSchema_ID, p_Date::date, p_ad_org_id, p_IncludePostingTypeStatistical, p_ExcludePostingTypeYearEnd)).Balance * ev.Multiplicator                     AS SameYearSum
           , (de_metas_acct.acctBalanceToDate(ev.C_ElementValue_ID, acs.C_AcctSchema_ID, period_LastYearEnd.EndDate::date, p_ad_org_id, p_IncludePostingTypeStatistical, p_ExcludePostingTypeYearEnd)).Balance * ev.Multiplicator AS LastYearSum

           -- Hardcoding replaced

           --show euro saldo only on 1021 and 2001 accounts
           --lvl.Value IN('1021','2001') AND $3='Y'
           -- AS IsConvertToEUR


           , CASE
                 WHEN p_showCurrencyExchange = 'N' -- we don't need to check if the elementValue has a foreign currency
                     THEN FALSE
                     ELSE -- check if the element value is set to show the Internation currency and if this currency is EURO. Convert to EURO in this case
                     (
                         EXISTS
                             (SELECT 1 FROM C_ElementValue elv WHERE lvl.C_ElementValue_ID = elv.C_ElementValue_ID AND elv.ShowIntCurrency = 'Y' AND elv.Foreign_Currency_ID = (SELECT C_Currency_ID FROM C_Currency WHERE ISO_Code = 'EUR' AND isActive = 'Y') AND elv.isActive = 'Y')
                         )
             END
                                                                                                                                                                                                                                  AS IsConvertToEUR

           --
           , acs.C_Currency_ID -- Accounting currency
           , acs.AD_Client_ID

           , ci.C_Calendar_ID
           , lvl.C_ElementValue_ID
           , c.iso_code
      FROM C_Period p
               CROSS JOIN C_AcctSchema acs
          -- Get last period of previous year
               LEFT OUTER JOIN C_Period period_LastYearEnd ON (period_LastYearEnd.C_Period_ID = report.Get_Predecessor_Period_Recursive(p.C_Period_ID, p.PeriodNo::int)) AND period_LastYearEnd.isActive = 'Y'
          --
               CROSS JOIN C_Element_Levels lvl
               INNER JOIN (SELECT ev.C_ElementValue_ID
                                -- NOTE: by customer requirement, we are not considering the account sign but always DR - CR
                                , 1 AS Multiplicator
                                -- , acctBalance(C_ElementValue_ID, 1, 0) AS Multiplicator
                                , ev.ad_client_id
                                , ev.AccountType
                                , ev.AD_Org_ID
                           FROM C_ElementValue ev
                                    JOIN C_Element e ON e.C_Element_id = ev.C_Element_ID AND e.IsActive = 'Y'

                           WHERE ev.isActive = 'Y'
                             AND e.IsNaturalAccount = 'Y') ev ON (lvl.C_ElementValue_ID = ev.C_ElementValue_ID)
          -- make sure we show the standard accounts from metasfresh (org 0)
          AND (CASE WHEN lvl.lvl1_value != 'ZZ' THEN (ev.ad_org_id = p_ad_org_id) ELSE (ev.ad_org_id = 0) END)
               LEFT OUTER JOIN AD_ClientInfo ci ON (ci.AD_Client_ID = ev.AD_Client_ID) AND ci.isActive = 'Y'
               LEFT OUTER JOIN C_Currency c ON acs.C_Currency_ID = c.C_Currency_ID AND c.isActive = 'Y'
      --
      WHERE TRUE
        -- make sure we get accounting schema for org 0 in any case
        AND (acs.ad_org_id = p_ad_org_id OR acs.ad_org_id = 0)
        AND acs.isActive = 'Y'
        -- Period: determine it by DateAcct
        AND p.C_Period_ID = report.Get_Period(ci.C_Calendar_ID, p_Date)
        -- Shall we Show default accounts?
        AND (CASE WHEN p_defaultAcc = 'Y' THEN TRUE ELSE lvl1_value != 'ZZ' END)
        AND p.isActive = 'Y') a,
     ad_org o
WHERE o.ad_org_id = p_ad_org_id
ORDER BY parentValue1, parentValue2, parentValue3, parentValue4, parentValue5, parentValue6, parentValue7, parentValue8, parentValue9, value
$BODY$
    LANGUAGE sql STABLE
;
