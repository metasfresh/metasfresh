-- 'org.compiere.grid.ed.BPartnerAddressRefresh'
-- 'org.compiere.grid.ed.BillToAddressRefresh',
-- 'org.compiere.grid.ed.DeliveryToAddressRefresh'


delete from ad_field_contextmenu
where ad_field_contextmenu_id in (540011, 540012, 540013);
