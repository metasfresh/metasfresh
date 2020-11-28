INSERT INTO C_Bpartner_CreditLimit
select ad_client_ID, ad_org_ID, so_creditlimit,createdby,nextval('C_Bpartner_CreditLimit_seq'),created,createdby,created,IsActive,'1_Man',updated, updatedby,c_Bpartner_id, null
from C_Bpartner where C_Bpartner.so_creditlimit > 0;