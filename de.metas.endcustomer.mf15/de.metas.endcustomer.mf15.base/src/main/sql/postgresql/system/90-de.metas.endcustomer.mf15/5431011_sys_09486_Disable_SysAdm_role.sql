-- Disable the System Administrator role.
update AD_Role set IsActive='N' where AD_Role_ID=0;

