alter table c_order drop CONSTRAINT if exists aduser_corder;

alter table c_order add constraint aduser_corder
    foreign key (c_bpartner_id, ad_user_id)
    references ad_user(c_bpartner_id, ad_user_id)
    DEFERRABLE INITIALLY DEFERRED;
