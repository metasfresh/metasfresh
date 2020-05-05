update AD_OrgInfo set M_Warehouse_ID=null where AD_Org_ID=1000001; -- metas fresh Holding AG
delete from M_Warehouse where M_Warehouse_ID=1000102;
delete from AD_User where C_BPartner_ID=2156264;
delete from C_BPartner_Location where C_BPartner_ID=2156264;
delete from C_BPartner where C_BPartner_ID=2156264;
--
delete from M_Cost where AD_Org_ID=1000001;
--
delete from AD_Org where AD_Org_ID=1000001;
