--
-- Adding searchcolumns for ad_org_id, value, name
--
update ad_column
set isselectioncolumn = 'Y'
where ad_column_id in
(
select ad_column_id
from AD_Column
where true
and lower(columnname) in
(
'ad_org_id'
,'value'
,'name'
)
);

--
-- Removing from searchcolumns description, bpartneraddress
--
update ad_column
set isselectioncolumn = 'N'
where ad_column_id in
(
select ad_column_id
from AD_Column
where true
and lower(columnname) in
(
'description'
,'bpartneraddress'
)
);
