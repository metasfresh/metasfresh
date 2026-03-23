update ad_sysconfig set value='' where name='webui.frontend.url';

insert into ad_user(
	ad_user_id, name, description, value, login, password, ad_language, isactive, issystemuser, isfullbpaccess, isdefaultcontact, ad_client_id, ad_org_id, createdby, updatedby, notificationtype, isinpayroll, issubjectmattercontact, issalescontact, isaccountlocked, fresh_gift, ispurchasecontact, ismfprocurementuser, issalescontact_default, ispurchasecontact_default, isloginashostkey, isbilltocontact_default, isshiptocontact_default, isnewsletter, isdecider, ismanagement, ismultiplier, isauthorizedsignatory, ismembershipcontact)
	values (142, 'cynthia', 'user for cypress tests', 'cynthia', 'cynthia', 'sha512:fa4902a30453c242c30378c483bca2ba2272689ff0cc143b8fca94653eef26be5243767dbc937efbe6dfc8c4066159af0d50ee533573746d3b93feb08056ef78:37d25dd5-5909-4d51-83c4-8bb9fc365b79', 'en_US', 'Y', 'Y', 'Y', 'Y', 0, 0, 0, 0, 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N', 'N');


insert into ad_user_roles(
	ad_user_id, ad_role_id, ad_client_id, ad_org_id, isactive, createdby, updatedby)
	values (142, 540024, 1000000, 1000000, 'Y', 142, 142);
