--
-- Remove all ad_client_id from GridViews
--
update ad_ui_element
set isdisplayedgrid = 'N', seqnogrid = 0
where ad_ui_element_id in
(
select uie.AD_UI_Element_ID 
from AD_UI_Element uie
left join ad_field f on uie.ad_field_id = f.ad_field_id
left join ad_column c on f.ad_column_id = c.ad_column_id
where true
and lower(c.columnname) like 'ad_client_id'
and uie.isdisplayedgrid = 'Y'
);

--
-- Remove all ad_client_id from SideLists
--
update ad_ui_element
set isdisplayed_sidelist = 'N', seqno_sidelist = 0
where ad_ui_element_id in
(
select uie.AD_UI_Element_ID 
from AD_UI_Element uie
left join ad_field f on uie.ad_field_id = f.ad_field_id
left join ad_column c on f.ad_column_id = c.ad_column_id
where true
and lower(c.columnname) like 'ad_client_id'
and uie.isdisplayed_sidelist = 'Y'
);