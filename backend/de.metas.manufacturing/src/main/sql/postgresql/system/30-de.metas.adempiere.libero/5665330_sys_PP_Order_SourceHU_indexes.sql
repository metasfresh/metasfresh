drop index if exists pp_order_sourcehu_uq;
create unique index pp_order_sourcehu_uq on pp_order_sourcehu(PP_Order_ID, M_HU_ID);

drop index if exists pp_order_sourcehu_pporder;
create index pp_order_sourcehu_pporder on pp_order_sourcehu(PP_Order_ID);

