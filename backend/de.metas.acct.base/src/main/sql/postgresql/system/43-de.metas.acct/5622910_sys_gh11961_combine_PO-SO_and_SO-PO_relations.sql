-- 2022-01-24T15:45:38.715Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause=' exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.C_Invoice_ID = i.Ref_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''N'' ) OR EXISTS(SELECT 1 from C_Invoice_Relation ir where ir.c_invoice_to_id = @C_Invoice_ID/-1@ AND ir.c_invoice_from_id = c_invoice.c_invoice_id)',Updated=TO_TIMESTAMP('2022-01-24 17:45:38','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2022-01-24T15:47:27.396Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540332
;

-- 2022-01-24T15:48:05.171Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM  AD_Reference_Trl WHERE AD_Reference_ID=541496
;

-- 2022-01-24T15:48:05.175Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Reference WHERE AD_Reference_ID=541496
;

-- 2022-01-24T15:57:42.301Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Ref_Table SET WhereClause='  exists (  select 1 from C_Invoice i  join C_Invoice i2 on i2.Ref_Invoice_ID = i.C_Invoice_ID    where i2.C_Invoice_ID = @C_Invoice_ID/-1@  and C_Invoice.C_Invoice_ID = i.C_Invoice_ID and i.IsSoTRX = ''Y''  ) OR EXISTS(SELECT 1 from C_Invoice_Relation ir where ir.c_invoice_from_id = @C_Invoice_ID/-1@ AND ir.c_invoice_to_id = c_invoice.c_invoice_id)',Updated=TO_TIMESTAMP('2022-01-24 17:57:42','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2022-01-24T15:57:55.207Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_RelationType WHERE AD_RelationType_ID=540324
;

-- 2022-01-24T15:58:09.003Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
DELETE FROM AD_Ref_Table WHERE AD_Reference_ID=541481
;

-- 2022-01-24T15:58:48.035Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Either via c_invoice.Ref_Invoice_ID or ',Updated=TO_TIMESTAMP('2022-01-24 17:58:48','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2022-01-24T15:59:39.816Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Either via c_invoice.Ref_Invoice_ID or as a reverse lookup in C_Invoice_Relation',Updated=TO_TIMESTAMP('2022-01-24 17:59:39','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540741
;

-- 2022-01-24T16:00:10.989Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Reference SET Description='Either via c_invoice.Ref_Invoice_ID or as a direct lookup in C_Invoice_Relation',Updated=TO_TIMESTAMP('2022-01-24 18:00:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Reference_ID=540742
;

-- 2022-01-24T16:00:16.651Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Description='Either via c_invoice.Ref_Invoice_ID or as a direct lookup in C_Invoice_Relation',Updated=TO_TIMESTAMP('2022-01-24 18:00:16','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540330
;

-- 2022-01-24T16:00:43.672Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_RelationType SET Description='Either via c_invoice.Ref_Invoice_ID or as a reverse lookup in C_Invoice_Relation',Updated=TO_TIMESTAMP('2022-01-24 18:00:43','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_RelationType_ID=540331
;

