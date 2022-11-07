DROP INDEX IF EXISTS pp_order_seqno
;

CREATE INDEX pp_order_seqno
    ON pp_order (seqno)
;

COMMENT ON INDEX pp_order_seqno
    IS 'Required, otherwise metasfresh will needs to do a full sequential scan other all PP_Orders'
;