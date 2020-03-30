drop index if exists ad_user_bpartner_uq;
create unique index ad_user_bpartner_uq on ad_user(c_bpartner_id, ad_user_id);
