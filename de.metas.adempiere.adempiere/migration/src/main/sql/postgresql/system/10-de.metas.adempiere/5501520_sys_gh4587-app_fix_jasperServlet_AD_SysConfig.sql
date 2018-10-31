
UPDATE AD_SysConfig 
SET   Name='de.metas.report.jasper.client.JRServerClass' 
WHERE Name='de.metas.adempiere.report.jasper.JRServerClass';

UPDATE AD_SysConfig 
  SET Value='de.metas.report.jasper.client.RemoteServletInvoker' 
WHERE Value='de.metas.adempiere.report.jasper.server.RemoteServletServer';
