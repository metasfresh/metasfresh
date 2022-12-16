
UPDATE AD_Column SET DefaultValue='@SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_User WHERE C_BPartner_ID=@C_BPartner_ID@', 
updated='2021-11-11 13:44', updatedby=99
WHERE AD_Column_ID=574480;
