update AD_OrgInfo oi set Org_BPartner_ID=(select bpl.C_BPartner_ID from C_BPartner_Location bpl where bpl.C_BPartner_Location_ID=oi.OrgBP_Location_ID);
	