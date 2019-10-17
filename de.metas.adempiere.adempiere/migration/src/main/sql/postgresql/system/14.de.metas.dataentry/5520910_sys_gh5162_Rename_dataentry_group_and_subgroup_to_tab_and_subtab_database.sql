alter table dataentry_group rename to dataentry_tab;
alter table dataentry_subgroup rename to dataentry_subtab;


alter table dataentry_tab rename column dataentry_group_id to dataentry_tab_id;
alter table dataentry_subtab rename column dataentry_subgroup_id to dataentry_subtab_id;
alter table dataentry_subtab rename column dataentry_group_id to dataentry_tab_id;



alter table dataentry_section rename column dataentry_subgroup_id to dataentry_subtab_id;



alter table dataentry_record rename column dataentry_subgroup_id to dataentry_subtab_id;
