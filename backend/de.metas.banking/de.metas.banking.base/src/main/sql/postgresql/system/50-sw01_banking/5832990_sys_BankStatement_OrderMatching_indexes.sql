-- Indexes for the bank statement order matching (import lookup, completion check and the "Auftrag" dropdown of window Bankauszug):
--   C_Payment.C_Order_ID          "is the order carried by a completed or closed payment?"
--   C_BankStatementLine.C_Order_ID "is the order already on another unprocessed bank statement line?"
--   C_Order.DocumentNo of orders waiting for payment  "which waiting order has this document number?"
-- Partial, so they only hold the few rows the lookups read. Without them each lookup scans the whole table.

CREATE INDEX IF NOT EXISTS c_payment_c_order_id
    ON C_Payment (C_Order_ID) WHERE C_Order_ID IS NOT NULL
;

CREATE INDEX IF NOT EXISTS c_bankstatementline_c_order_id
    ON C_BankStatementLine (C_Order_ID) WHERE C_Order_ID IS NOT NULL
;

CREATE INDEX IF NOT EXISTS c_order_documentno_waitingpayment
    ON C_Order (DocumentNo) WHERE DocStatus = 'WP'
;
