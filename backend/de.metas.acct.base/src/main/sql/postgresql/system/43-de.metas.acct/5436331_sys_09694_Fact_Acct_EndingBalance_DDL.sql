-- 11.01.2016 15:23
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
CREATE TABLE Fact_Acct_EndingBalance (Account_ID NUMERIC(10) NOT NULL, AD_Client_ID NUMERIC(10) NOT NULL, AD_Org_ID NUMERIC(10) NOT NULL, AmtAcctCr_DTD NUMERIC NOT NULL, AmtAcctDr_DTD NUMERIC NOT NULL, C_AcctSchema_ID NUMERIC(10) NOT NULL, Created TIMESTAMP WITH TIME ZONE NOT NULL, CreatedBy NUMERIC(10) NOT NULL, DateAcct TIMESTAMP WITHOUT TIME ZONE NOT NULL, Fact_Acct_ID NUMERIC(10) NOT NULL, IsActive CHAR(1) CHECK (IsActive IN ('Y','N')) NOT NULL, PostingType CHAR(1) NOT NULL, Updated TIMESTAMP WITH TIME ZONE NOT NULL, UpdatedBy NUMERIC(10) NOT NULL, CONSTRAINT Fact_Acct_EndingBalance_Key PRIMARY KEY (Fact_Acct_ID))
;
drop index if exists Fact_Acct_EndingBalance_Dim;
create index Fact_Acct_EndingBalance_Dim on Fact_Acct_EndingBalance(Account_ID, C_AcctSchema_ID, PostingType, DateAcct);


create index Fact_Acct_EB_Dim on Fact_Acct (C_AcctSchema_ID, Account_ID, PostingType, DateAcct);
COMMENT ON INDEX fact_acct_eb_dim IS 'Index required to speed up Fact_Acct_EndingBalance_UpdateForTag';

