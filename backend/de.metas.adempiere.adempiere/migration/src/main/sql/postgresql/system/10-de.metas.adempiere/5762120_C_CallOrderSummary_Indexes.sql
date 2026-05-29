
CREATE INDEX IF NOT EXISTS c_callordersummary_c_orderline_id
    ON c_callordersummary (c_orderline_id)
;

CREATE INDEX IF NOT EXISTS c_callorderdetail_c_callordersummary_id
    ON c_callorderdetail (c_callordersummary_id)
;
