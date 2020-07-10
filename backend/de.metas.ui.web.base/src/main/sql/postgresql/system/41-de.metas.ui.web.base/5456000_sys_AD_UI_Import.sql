--
-- Backup
create table backup.AD_UI_Section_BKP as select * from AD_UI_Section;
create table backup.AD_UI_Column_BKP as select * from AD_UI_Column;
create table backup.AD_UI_ElementGroup_BKP as select * from AD_UI_ElementGroup;
create table backup.AD_UI_Element_BKP as select * from AD_UI_Element;
create table backup.AD_UI_ElementField_BKP as select * from AD_UI_ElementField;


--
-- Delete all AD_UI_* data
delete from AD_UI_ElementField;
delete from AD_UI_ElementGroup;
delete from AD_UI_Element;
delete from AD_UI_Column;
delete from AD_UI_Section;

--
-- Restore (if needed)
/*
insert into AD_UI_Section select * from backup.AD_UI_Section_BKP;
insert into AD_UI_Column select * from backup.AD_UI_Column_BKP;
insert into AD_UI_ElementGroup select * from backup.AD_UI_ElementGroup_BKP;
insert into AD_UI_Element select * from backup.AD_UI_Element_BKP;
insert into AD_UI_ElementField select * from backup.AD_UI_ElementField_BKP;
*/


--
-- Import everything from backup.TMP_* tables
insert into AD_UI_Section (ad_client_id, ad_org_id, ad_tab_id, ad_ui_section_id, created, createdby, description, help, isactive, name, seqno, updated, updatedby)
select ad_client_id, ad_org_id, ad_tab_id, ad_ui_section_id, created, createdby, description, help, isactive, name, seqno, updated, updatedby
from backup.tmp_ad_ui_section s
where exists (select 1 from AD_Tab tt where tt.AD_Tab_ID=s.AD_Tab_ID)
;

insert into AD_UI_Column (ad_client_id, ad_org_id, ad_ui_column_id, ad_ui_section_id, created, createdby, isactive, updated, updatedby, seqno)
select ad_client_id, ad_org_id, ad_ui_column_id, ad_ui_section_id, created, createdby, isactive, updated, updatedby, seqno
from backup.tmp_ad_ui_column c
where exists (select 1 from AD_UI_Section s where s.AD_UI_Section_ID=c.AD_UI_Section_ID)
;

insert into AD_UI_ElementGroup (ad_client_id, ad_org_id, ad_ui_column_id, ad_ui_elementgroup_id, created, createdby, isactive, name, seqno, uistyle, updated, updatedby)
select ad_client_id, ad_org_id, ad_ui_column_id, ad_ui_elementgroup_id, created, createdby, isactive, name, seqno, uistyle, updated, updatedby
from backup.tmp_ad_ui_elementgroup eg
where exists (select 1 from AD_UI_Column c where c.AD_UI_Column_ID=eg.AD_UI_Column_ID)
;


insert into AD_UI_Element (ad_client_id, ad_field_id, ad_org_id, ad_ui_element_id, ad_ui_elementgroup_id, created, createdby, description, help, isactive, isadvancedfield, name, seqno, updated, updatedby, uistyle, isdisplayed, isdisplayedgrid, seqnogrid, isdisplayed_sidelist, seqno_sidelist, ad_tab_id)
select ad_client_id, ad_field_id, ad_org_id, ad_ui_element_id, ad_ui_elementgroup_id, created, createdby, description, help, isactive, isadvancedfield, name, seqno, updated, updatedby, uistyle, isdisplayed, isdisplayedgrid, seqnogrid, isdisplayed_sidelist, seqno_sidelist, ad_tab_id
from backup.tmp_ad_ui_element e
where exists (select 1 from AD_UI_ElementGroup eg where eg.AD_UI_ElementGroup_ID=e.AD_UI_ElementGroup_ID)
;

insert into AD_UI_ElementField (ad_client_id, ad_field_id, ad_org_id, ad_ui_element_id, ad_ui_elementfield_id, created, createdby, isactive, updated, updatedby, seqno)
select ad_client_id, ad_field_id, ad_org_id, ad_ui_element_id, ad_ui_elementfield_id, created, createdby, isactive, updated, updatedby, seqno
from backup.tmp_ad_ui_elementfield ef
where exists (select 1 from AD_UI_Element e where e.AD_UI_Element_ID=ef.AD_UI_Element_ID)
;

