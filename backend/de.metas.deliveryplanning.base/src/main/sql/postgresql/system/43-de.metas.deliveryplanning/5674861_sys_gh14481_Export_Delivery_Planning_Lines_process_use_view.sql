-- Value: Export Delivery Planning Lines (Jasper)
-- Classname: de.metas.report.jasper.client.process.JasperReportStarter
-- JasperReport: @PREFIX@/de/metas/docs/deliveryplanning/deliveryplanning.xls
-- 2023-02-06T13:54:56.466Z
UPDATE AD_Process SET SQLStatement='SELECT documentNo,
       shipToLocation_name,
       productName,
       productCode,
       warehouseName,
       originCountry,
       deliveryDate,
       batch,
       releaseno,
       plannedloadingdate,
       actualloadingdate,
       plannedloadedquantity,
       actualloadqty,
       planneddeliverydate,
       actualdeliverydate,
       planneddischargequantity,
       actualdischargequantity
FROM Delivery_Planning_Export_View_v AS M_Delivery_Planning
WHERE @SELECTION_WHERECLAUSE/false@;',Updated=TO_TIMESTAMP('2023-02-06 15:54:56','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Process_ID=585207
;
