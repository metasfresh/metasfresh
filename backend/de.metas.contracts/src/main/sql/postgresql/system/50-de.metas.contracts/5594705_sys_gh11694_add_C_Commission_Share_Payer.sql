create table backup.c_commission_share155 as select * from c_commission_share;

update c_commission_share set updatedby=99, updated='2021-06-23 18:00:02.855408', c_bpartner_payer_id = (select max(c_bpartner.c_bpartner_id) from c_bpartner where ad_orgbp_id = c_commission_share.ad_org_id);
