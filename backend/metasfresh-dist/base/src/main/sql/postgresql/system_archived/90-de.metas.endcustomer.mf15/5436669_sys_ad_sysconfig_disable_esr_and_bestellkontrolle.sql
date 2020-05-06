--Bestellkontrolle ausschalten
update ad_sysconfig 
set value = 'N'
where name in ('de.metas.fresh.ordercheckup.FailIfOrderWarehouseHasNoPlant','de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.OnSalesOrderComplete','de.metas.fresh.ordercheckup.CreateAndRouteJasperReports.EnableProcessGear');

--ESR ausschalten
update ad_sysconfig 
set value = 'N'
where name in ('de.metas.payment.esr.Enabled');
