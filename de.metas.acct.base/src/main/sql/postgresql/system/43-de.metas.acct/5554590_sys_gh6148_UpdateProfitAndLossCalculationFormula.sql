-- 2020-03-16T13:18:36.124Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM ProfitAndLossReport(''@DateFrom/1993-01-01@''::Timestamp, ''@DateTo/2992-01-01@''::Timestamp, @AD_Org_ID@::numeric, ''@ExcludeEmptyLines@'');',Updated=TO_TIMESTAMP('2020-03-16 15:18:36','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584650
;

-- 2020-03-16T13:18:52.721Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET SeqNo=40,Updated=TO_TIMESTAMP('2020-03-16 15:18:52','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541745
;

-- 2020-03-16T13:19:30.472Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,IsActive,Created,CreatedBy,AD_Process_ID,AD_Reference_ID,IsRange,AD_Process_Para_ID,FieldLength,AD_Org_ID,IsCentrallyMaintained,IsEncrypted,Updated,UpdatedBy,ColumnName,IsMandatory,IsAutocomplete,SeqNo,Help,Description,Name,EntityType,AD_Element_ID) VALUES (0,'Y',TO_TIMESTAMP('2020-03-16 15:19:30','YYYY-MM-DD HH24:MI:SS'),100,584650,30,'N',541759,0,0,'Y','N',TO_TIMESTAMP('2020-03-16 15:19:30','YYYY-MM-DD HH24:MI:SS'),100,'AD_Org_ID','Y','N',30,'Eine Organisation ist ein Bereich ihres Mandanten - z.B. Laden oder Abteilung. Sie können Daten über Organisationen hinweg gemeinsam verwenden.','Organisatorische Einheit des Mandanten','Sektion','U',113)
;

-- 2020-03-16T13:19:30.475Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Help,Description,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Help,t.Description,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541759 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-03-16T13:19:33.937Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET EntityType='D',Updated=TO_TIMESTAMP('2020-03-16 15:19:33','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541759
;

-- 2020-03-16T13:19:46.967Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-16 15:19:46','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541745
;

-- 2020-03-16T13:19:47.904Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='Y',Updated=TO_TIMESTAMP('2020-03-16 15:19:47','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541704
;

-- 2020-03-16T13:20:00.119Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-03-16 15:20:00','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541704
;

-- 2020-03-16T13:20:10.552Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process_Para SET IsMandatory='N',Updated=TO_TIMESTAMP('2020-03-16 15:20:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_Para_ID=541745
;





DROP FUNCTION IF EXISTS ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric, IN p_startDate timestamp, IN p_endDate timestamp)
;

DROP FUNCTION IF EXISTS ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric, IN p_startDate timestamp, IN p_endDate timestamp, IN p_AD_Org_Id numeric)
;

/**
  fact_acct_summary's amtacctdr and amtacctcr are always increasing, and exactly 1 per day per organisation per posting type.
  Therefore calculating the balance for the winter sales period: 2019-12-20 -> 2020-02-01 is done by subtracting endDate_balance - the_balance_before_startDate as follows:
  (2020-02-01.debit - 2020-02-01.credit) - (2019-12-19.debit - 2019-12-19.credit)

 */
CREATE OR REPLACE FUNCTION ProfitAndLossBalanceForAccountInPeriod(IN p_C_ElementValue_ID numeric,
                                                                  IN p_startDate         timestamp,
                                                                  IN p_endDate           timestamp,
                                                                  IN p_AD_Org_Id         numeric)
    RETURNS NUMERIC
AS
$BODY$


WITH factAcctSummaryNoPeriod AS
         (
             SELECT --
                    fas.amtacctdr,
                    fas.amtacctcr,
                    fas.dateacct
             FROM c_elementvalue ev
                      INNER JOIN fact_acct_summary fas ON fas.account_id = ev.c_elementvalue_id
             WHERE TRUE
               AND ev.c_elementvalue_id = p_C_ElementValue_ID
               AND ev.accounttype IN ('E', 'R')
               AND fas.ad_org_id = p_AD_Org_ID
               -- only 'Actual' posting type is used
               AND fas.postingtype = 'A'
         ),
     factAcctSummaryInPeriod AS
         (
             SELECT --
                    fas.amtacctdr,
                    fas.amtacctcr,
                    fas.dateacct
             FROM factAcctSummaryNoPeriod fas
             WHERE TRUE
               AND fas.dateacct >= p_startDate
               AND fas.dateacct <= p_endDate
         ),
     balanceUpToStartDay AS
         (
             SELECT fas.amtacctdr - fas.amtacctcr balance
             FROM factAcctSummaryNoPeriod fas
             WHERE TRUE
               AND fas.dateacct = (SELECT max(fas.dateacct)
                                   FROM factAcctSummaryNoPeriod fas
                                   WHERE fas.dateacct < p_startDate)
             UNION ALL
             (
                 SELECT 0
             )
             LIMIT 1
         ),
     balanceUpToEndDate AS
         (
             SELECT fas.amtacctdr - fas.amtacctcr balance
             FROM factAcctSummaryInPeriod fas
             WHERE fas.dateacct = (SELECT max(fas2.dateacct) FROM factAcctSummaryInPeriod fas2)
         )

SELECT maxBal.balance - minBal.balance
FROM balanceUpToStartDay minBal,
     balanceUpToEndDate maxBal

    /**
      Why is this union needed?
      In case no `fact_acct_summary` for an account_id exists, `result_table` returns no rows. It doesn't return null (so that i can use coalesce), but returns NO rows.
         example: SELECT ProfitAndLossBalanceForAccountInPeriod(1, '1993-01-01'::Timestamp, '2992-01-01'::Timestamp, 1000000); -- this returns NO rows
      I have simply added this union in case the select returns nothing. If you have a better solution, i'm more than happy to hear it, as this feels like a hack.
     */
UNION ALL
(
    SELECT 0
)
LIMIT 1
    ;
$BODY$
    LANGUAGE SQL
    STABLE
;

ALTER FUNCTION ProfitAndLossBalanceForAccountInPeriod(numeric, timestamp, timestamp, numeric)
    OWNER TO metasfresh
;





DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean, IN p_compareWithPrevious3Years boolean)
;

DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp)
;

DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, p_ExcludeEmptyLines text)
;

DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_AD_Org_ID numeric, IN p_ExcludeEmptyLines text)
;

CREATE OR REPLACE FUNCTION ProfitAndLossReport(IN p_startDate         timestamp,
                                               IN p_endDate           timestamp,
                                               IN p_AD_Org_ID         numeric,
                                               IN p_ExcludeEmptyLines text = 'Y')
    RETURNS TABLE
            (
                AccountValue            text,
                AccountName             text,
                balance_three_years_ago numeric,
                balance_two_years_ago   numeric,
                balance_one_year_ago    numeric,
                balance                 numeric
            )
AS
$BODY$

WITH data AS
         (
             SELECT--
                   ev.value                                                                                                                                    AccountValue,
                   ev.name                                                                                                                                     AccountName,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '3 Year'::interval, p_endDate - '3 Year'::interval, p_AD_Org_ID) balance_three_years_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '2 Year'::interval, p_endDate - '2 Year'::interval, p_AD_Org_ID) balance_two_years_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '1 Year'::interval, p_endDate - '1 Year'::interval, p_AD_Org_ID) balance_one_year_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate, p_endDate, p_AD_Org_ID)                                           current_balance
             FROM c_elementvalue ev
             WHERE TRUE
               AND ev.accounttype IN ('E', 'R')
               AND ev.IsSummary = 'N'
         ),
     dataExcludingEmptyLines AS
         (
             SELECT --
                    AccountValue,
                    AccountName,
                    balance_three_years_ago,
                    balance_two_years_ago,
                    balance_one_year_ago,
                    current_balance
             FROM data
             WHERE (
                               p_ExcludeEmptyLines = 'N'
                           OR (
                                           current_balance != 0
                                       OR balance_one_year_ago != 0
                                       OR balance_two_years_ago != 0
                                       OR balance_three_years_ago != 0
                                   )
                       )
         )
SELECT --
       AccountValue,
       AccountName,
       balance_three_years_ago,
       balance_two_years_ago,
       balance_one_year_ago,
       current_balance
FROM dataExcludingEmptyLines d
ORDER BY d.AccountValue
    ;
$BODY$
    LANGUAGE sql
    STABLE
;

-- test: SELECT * FROM ProfitAndLossReport('1993-01-01'::Timestamp, '2992-01-01'::Timestamp, 1000000);
-- test SELECT * FROM ProfitAndLossReport('2018-01-01'::Timestamp, '2018-07-01'::Timestamp, 1000000);
