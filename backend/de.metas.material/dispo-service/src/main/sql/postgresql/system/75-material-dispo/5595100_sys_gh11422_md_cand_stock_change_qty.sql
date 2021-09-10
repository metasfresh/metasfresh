-- 2021-06-25T16:16:10.032Z
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='CASE            WHEN md_candidate_type IN (''DEMAND'', ''STOCK_UP'') or (md_candidate_type = ''INVENTORY_DOWN'' and MD_Candidate_BusinessCase = ''STOCK_CHANGE'') THEN -qty            WHEN md_candidate_type IN (''SUPPLY'') or (md_candidate_type = ''INVENTORY_UP'' and MD_Candidate_BusinessCase = ''STOCK_CHANGE'') THEN qty            WHEN md_candidate_type IN (''UNEXPECTED_DECREASE'', ''INVENTORY_DOWN'', ''ATTRIBUTES_CHANGED_FROM'') THEN -qtyFulfilled            WHEN md_candidate_type IN (''UNEXPECTED_INCREASE'', ''INVENTORY_UP'', ''ATTRIBUTES_CHANGED_TO'')                THEN qtyFulfilled END',Updated=TO_TIMESTAMP('2021-06-25 19:16:10','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=572317
;

