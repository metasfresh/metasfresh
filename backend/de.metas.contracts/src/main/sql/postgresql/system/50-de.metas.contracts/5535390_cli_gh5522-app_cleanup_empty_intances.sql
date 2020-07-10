
-- they shouldn'T have been created to start with
delete from C_Commission_Instance i where not exists (select 1 from c_commission_share s where s.C_Commission_Instance_ID=i.C_Commission_Instance_ID);

