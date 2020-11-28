The reports service opens port 30090, so after having deployed it (assuming it runs on a local docker/kubernetes host),
you might want to update the sysconfig like this

```sql
UPDATE AD_SysConfig SET Value='http://localhost:30090/adempiereJasper/ReportServlet' WHERE Name='de.metas.adempiere.report.jasper.JRServerServlet';
UPDATE AD_SysConfig SET Value='http://localhost:30090/adempiereJasper/BarcodeServlet' WHERE Name='de.metas.adempiere.report.barcode.BarcodeServlet';
```
