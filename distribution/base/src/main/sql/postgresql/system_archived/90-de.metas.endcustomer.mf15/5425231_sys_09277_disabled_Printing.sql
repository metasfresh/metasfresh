update AD_SysConfig set Value='N', updated=now(), updatedby=0 where Name='de.metas.printing.Enabled' and Value<>'N';

