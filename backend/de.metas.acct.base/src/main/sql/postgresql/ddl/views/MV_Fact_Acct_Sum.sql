DROP MATERIALIZED VIEW MV_Fact_Acct_Sum ;

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