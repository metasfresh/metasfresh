## HowTo use ReportServiceMain in local env

1. Build jasperreports with Jasper Studio 6.5.1
2. Copy application.properties from misc/dev-support
3. Adjust path to compiled *.jasper files in DB or WebUI Org-Info

```sql
UPDATE ad_orginfo
SET reportprefix = 'file:///C:/work-metas/metasfresh/backend/de.metas.fresh/de.metas.fresh.base/src/main/jasperreports/'
WHERE TRUE;
```