CREATE UNIQUE INDEX unq_callorder_orderline ON c_callorderdetail (C_OrderLine_ID)
;

CREATE UNIQUE INDEX unq_callorder_inoutline ON c_callorderdetail (M_InOutLine_ID)
;

CREATE UNIQUE INDEX unq_callorder_invoiceline ON c_callorderdetail (C_InvoiceLine_ID)
;

CREATE UNIQUE INDEX unq_callordersummary_contract ON c_callordersummary (C_Flatrate_Term_ID)
;