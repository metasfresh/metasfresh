alter table c_order drop CONSTRAINT if exists dropshipuser_corder;

alter table c_order add constraint dropshipuser_corder
    foreign key (dropship_bpartner_id, dropship_user_id)
    references ad_user(c_bpartner_id, ad_user_id)
    DEFERRABLE INITIALLY DEFERRED;
