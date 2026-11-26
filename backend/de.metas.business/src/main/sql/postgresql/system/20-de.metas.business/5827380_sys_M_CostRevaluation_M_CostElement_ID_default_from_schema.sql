-- Kosten Neubewertung (cost revaluation): the header's cost element is preset to the active material cost element whose costing method
-- is the costing method of the header's accounting schema (else of the client's primary schema); no preset unless exactly one element matches.
-- Same rule as CostRevaluationService#findPresetCostElement (used by the interceptor and the callout). Was: 1000000 (Standard Costing).

-- Column: M_CostRevaluation.M_CostElement_ID
UPDATE AD_Column SET DefaultValue='@SQL=SELECT MIN(ce.M_CostElement_ID) AS DefaultValue FROM M_CostElement ce JOIN C_AcctSchema sch ON ce.CostingMethod=sch.CostingMethod AND ce.AD_Client_ID=sch.AD_Client_ID WHERE sch.C_AcctSchema_ID=COALESCE(NULLIF(@C_AcctSchema_ID/0@,0),(SELECT ci.C_AcctSchema1_ID FROM AD_ClientInfo ci WHERE ci.AD_Client_ID=@#AD_Client_ID@)) AND ce.CostElementType=''M'' AND ce.IsActive=''Y'' HAVING COUNT(*)=1', Updated=TO_TIMESTAMP('2026-10-01 15:00:00','YYYY-MM-DD HH24:MI:SS'), UpdatedBy=100 WHERE AD_Column_ID=583869
;
