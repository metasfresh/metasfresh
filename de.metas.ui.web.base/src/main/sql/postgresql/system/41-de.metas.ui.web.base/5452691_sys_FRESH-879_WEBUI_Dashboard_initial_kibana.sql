-- Auftragszeilen Woche
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/3985c70590ddc66d2b4ab7a52a19f85d', name = 'Auftragszeilen Woche'
where true
and webui_dashboarditem_id = 1000009
;

-- Sales Woche
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/38e432db8b85f394f4fb98d5452b87f2', name = 'Sales Woche'
where true
and webui_dashboarditem_id = 1000010
;

-- Sales Vorwoche
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/d22e3173edbc67373717f71a8d6008ba', name = 'Sales Vorwoche'
where true
and webui_dashboarditem_id = 1000011
;

-- Auftragszeilen Voroche
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/0f2a91e4b09522a91b19a2024c2f2b3d', name = 'Auftragszeilen Vorwoche'
where true
and webui_dashboarditem_id = 1000012
;

-- 1: Top 5 Produkte Woche Sales
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/3f41ab943c28d59154de390b5e91ce7f', name = 'Top 5 Produkte Woche Sales'
where true
and webui_dashboarditem_id = 1000002
;

-- 2: Top 5 Städte Woche Sales
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/ce0d152f1dc9fe31bd662ecc5735b6f9', name = 'Top5 Städte Woche'
where true
and webui_dashboarditem_id = 1000001
;

-- 3: Umsatz diese Woche
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/e047899b4513efd8f45fecea1a3dbbb2', name = 'Umsatz Woche'
where true
and webui_dashboarditem_id = 1000003
;

-- 4: Top 5 Produkte Vorwoche Sales
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/67a4c609bbb72a6ca3e91fb8f2933d4c', name = 'Top 5 Produkte Vorwoche Sales'
where true
and webui_dashboarditem_id = 1000000
;

-- 5: Top 5 Städte Vorwoche Sales
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/b510b26de9c632a7e76f6932ded7e5e1', name = 'Top5 Städte Vorwoche'
where true
and webui_dashboarditem_id = 1000006
;

-- 6: Vorwoche
update webui_dashboarditem
set url = 'http://w101.metasfresh.com:5601/goto/a0afee8fc3e9135ed2ddfeaac929b5e8', name = 'Umsatz Vorwoche'
where true
and webui_dashboarditem_id = 1000007
; 
