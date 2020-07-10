alter table c_order drop CONSTRAINT if exists aduserbill_corder;

alter table c_order add constraint aduserbill_corder
    foreign key (bill_bpartner_id, bill_user_id)
    references ad_user(c_bpartner_id, ad_user_id)
    DEFERRABLE INITIALLY DEFERRED;
