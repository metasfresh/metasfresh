
UPDATE C_BPartner_Stats s
SET SOCreditStatus=g.SOCreditStatus, Updated=now(), UpdatedBy=99
FROM C_BPartner bp
	JOIN C_BP_Group g ON g.C_BP_Group_ID=bp.C_BP_Group_ID
WHERE true
	AND s.C_BPartner_ID=bp.C_BPartner_ID
	AND s.SOCreditStatus IS NULL
;
