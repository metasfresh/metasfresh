

-- 2017-11-07T15:41:31.196
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' C_Invoice_Candidate_ID IN (  select C_Invoice_Candidate_ID from C_Invoice_Candidate ic    JOIN C_Order o on ic.C_Order_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@   AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID ) OR  C_Invoice_Candidate_ID IN (  select C_Invoice_Candidate_ID from C_Invoice_Candidate ic  JOIN C_Flatrate_Term ft on (ft.C_Flatrate_Term_ID = ic.Record_ID AND ic.AD_table_ID = 540320)  JOIN C_OrderLine ol on ft.C_OrderLine_Term_ID = ol.C_OrderLine_ID  JOIN C_Order o on ol.C_Order_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@   AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID )',Updated=TO_TIMESTAMP('2017-11-07 15:41:31','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

-- 2017-11-07T18:28:16.278
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' C_Invoice_Candidate_ID IN  (  select C_Invoice_Candidate_ID from C_Invoice_Candidate ic  JOIN C_Order o on ic.C_Order_ID = o.C_Order_ID  where o.C_Order_ID = @C_Order_ID/-1@    AND ic.C_Invoice_Candidate_ID = C_Invoice_Candidate.C_Invoice_Candidate_ID )',Updated=TO_TIMESTAMP('2017-11-07 18:28:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540760
;

