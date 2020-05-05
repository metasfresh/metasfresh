drop index if exists c_bpartner_product_stats_UQ;
create unique index c_bpartner_product_stats_UQ on c_bpartner_product_stats(c_bpartner_id, m_product_id);
