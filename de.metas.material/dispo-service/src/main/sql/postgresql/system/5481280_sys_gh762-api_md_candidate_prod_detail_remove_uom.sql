
delete from ad_ui_element where (ad_field_id)=(558301);
delete from ad_field where (ad_field_id)=(558301);
delete from AD_Column where AD_Column_ID=556532;

alter table MD_Candidate_Prod_Detail drop column if exists c_uom_id;
