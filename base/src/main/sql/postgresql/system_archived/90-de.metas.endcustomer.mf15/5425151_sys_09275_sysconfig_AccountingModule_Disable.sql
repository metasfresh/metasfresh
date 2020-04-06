update AD_SysConfig set Value='N', updated=now(), updatedby=0 where Name='org.adempiere.acct.Enabled';
update C_AcctProcessor set IsActive='N' where AD_Client_ID>=1000000;
