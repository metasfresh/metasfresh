DROP TABLE IF EXISTS purchase_order_highestprice_per_day_mv$recompute
;

CREATE TABLE purchase_order_highestprice_per_day_mv$recompute
(
    m_product_id   numeric                                NOT NULL,
    date           date                                   NOT NULL,
    --
    created        timestamp WITH TIME ZONE DEFAULT NOW() NOT NULL,
    processing_tag varchar(40)
)
;

