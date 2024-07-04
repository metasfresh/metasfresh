CREATE INDEX IF NOT EXISTS C_Doc_Outbound_Log_PostFinance_Log_PostFinance_Transaction_Id ON C_Doc_Outbound_Log_PostFinance_Log (PostFinance_Transaction_Id)
;

COMMENT ON INDEX C_Doc_Outbound_Log_PostFinance_Log_PostFinance_Transaction_Id IS 'Log records are selected regular via PostFinance_Transaction_Id to find C_Doc_Outbound_Log related to PostFinance process results'
;

