DROP INDEX IF EXISTS PP_Order_DateOrdered_Day_and_Seq
;

CREATE INDEX PP_Order_DateOrdered_Day_and_Seq ON PP_Order (TRUNC(DateOrdered, 'DD'), seqno)
;

COMMENT ON INDEX PP_Order_DateOrdered_Day_and_Seq
    IS 'Required, otherwise metasfresh will needs to do a full sequential scan other all PP_Orders'
;