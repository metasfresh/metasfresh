-- 2020-02-19T09:49:32.653Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para (AD_Client_ID,AD_Element_ID,AD_Org_ID,AD_Process_ID,AD_Process_Para_ID,AD_Reference_ID,ColumnName,Created,CreatedBy,DefaultValue,EntityType,FieldLength,IsActive,IsAutocomplete,IsCentrallyMaintained,IsEncrypted,IsMandatory,IsRange,Name,SeqNo,Updated,UpdatedBy) VALUES (0,577550,0,584650,541745,20,'ExcludeEmptyLines',TO_TIMESTAMP('2020-02-19 11:49:32','YYYY-MM-DD HH24:MI:SS'),100,'Y','D',0,'Y','N','Y','N','N','N','Leere Zeilen ausschließen',30,TO_TIMESTAMP('2020-02-19 11:49:32','YYYY-MM-DD HH24:MI:SS'),100)
;

-- 2020-02-19T09:49:32.655Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
INSERT INTO AD_Process_Para_Trl (AD_Language,AD_Process_Para_ID, Description,Help,Name, IsTranslated,AD_Client_ID,AD_Org_ID,Created,Createdby,Updated,UpdatedBy) SELECT l.AD_Language, t.AD_Process_Para_ID, t.Description,t.Help,t.Name, 'N',t.AD_Client_ID,t.AD_Org_ID,t.Created,t.Createdby,t.Updated,t.UpdatedBy FROM AD_Language l, AD_Process_Para t WHERE l.IsActive='Y'AND (l.IsSystemLanguage='Y' AND l.IsBaseLanguage='N') AND t.AD_Process_Para_ID=541745 AND NOT EXISTS (SELECT 1 FROM AD_Process_Para_Trl tt WHERE tt.AD_Language=l.AD_Language AND tt.AD_Process_Para_ID=t.AD_Process_Para_ID)
;

-- 2020-02-19T09:50:02.630Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM ProfitAndLossReport(''@DateFrom/1993-01-01@''::Timestamp, ''@DateTo/2992-01-01@''::Timestamp, @ExcludeEmptyLines@);',Updated=TO_TIMESTAMP('2020-02-19 11:50:02','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584650
;

-- 2020-02-19T09:51:48.252Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='SELECT * FROM ProfitAndLossReport(''@DateFrom/1993-01-01@''::Timestamp, ''@DateTo/2992-01-01@''::Timestamp, ''@ExcludeEmptyLines@'');',Updated=TO_TIMESTAMP('2020-02-19 11:51:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584650
;

-- 2020-02-19T09:53:25.618Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Process SET SQLStatement='select * from SummaryAndBalanceReport(''@DateFrom@''::date, ''@DateTo@''::date, @C_AcctSchema_ID@, @AD_Org_ID@, @Account_ID/null@, ''@ExcludeEmptyLines@'');',Updated=TO_TIMESTAMP('2020-02-19 11:53:25','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=584655
;




DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, IN p_compareWithPrevious1Year boolean, IN p_compareWithPrevious3Years boolean);
DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp);
DROP FUNCTION IF EXISTS ProfitAndLossReport(IN p_startDate timestamp, IN p_endDate timestamp, p_ExcludeEmptyLines text);

CREATE OR REPLACE FUNCTION ProfitAndLossReport(IN p_startDate timestamp,
                                               IN p_endDate timestamp,
                                               p_ExcludeEmptyLines text = 'Y')
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
                   ev.value                                                                                                                       AccountValue,
                   ev.name                                                                                                                        AccountName,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '3 Year'::interval, p_endDate - '3 Year'::interval) balance_three_years_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '2 Year'::interval, p_endDate - '2 Year'::interval) balance_two_years_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate - '1 Year'::interval, p_endDate - '1 Year'::interval) balance_one_year_ago,
                   ProfitAndLossBalanceForAccountInPeriod(ev.c_elementvalue_id, p_startDate, p_endDate)                                           current_balance
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
    STABLE;
