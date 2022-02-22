UPDATE AD_Process
SET classname='de.metas.ordercandidate.process.C_OLCand_SetOverrideValues', updated=TO_TIMESTAMP('2022-02-022 12:12:12', 'YYYY-MM-DD HH24:MI:SS'), updatedBy=100
WHERE classname = 'de.metas.ordercandidate.process.OLCandSetOverrideValues'
;

--TODO add trls