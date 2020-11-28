UPDATE C_Invoice i
SET C_BPartner_SalesRep_ID=bp.C_BPartner_SalesRep_ID,
	Updated='2020-03-23 08:07:29.309001+00',
	UpdatedBy=99
FROM C_BPartner bp
WHERE bp.C_BPartner_ID=i.C_BPartner_ID
	AND i.IsSOTrx='Y' AND i.C_BPartner_SalesRep_ID IS NULL AND bp.C_BPartner_SalesRep_ID IS NOT NULL
;
