alter table c_order drop CONSTRAINT if exists handoveruser_corder;

alter table c_order add constraint handoveruser_corder
    foreign key (handover_partner_id, handover_user_id)
    references ad_user(c_bpartner_id, ad_user_id)
    DEFERRABLE INITIALLY DEFERRED;
