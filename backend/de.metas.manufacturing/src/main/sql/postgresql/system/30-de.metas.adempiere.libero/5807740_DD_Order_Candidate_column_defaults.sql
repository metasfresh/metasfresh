-- DD_Order_Candidate: add DB column defaults so SQL-authored inserts (e.g. scheduled
-- DD_Order_Candidate generator functions) can omit the audit/flag boilerplate.
-- App-path (PO framework) inserts always set these explicitly, so the defaults only take
-- effect for bare SQL INSERTs. QtyProcessed / Processed / IsSimulated already have defaults.

ALTER TABLE dd_order_candidate ALTER COLUMN isactive  SET DEFAULT 'Y'
;
ALTER TABLE dd_order_candidate ALTER COLUMN created   SET DEFAULT now()
;
ALTER TABLE dd_order_candidate ALTER COLUMN createdby SET DEFAULT 100
;
ALTER TABLE dd_order_candidate ALTER COLUMN updated   SET DEFAULT now()
;
ALTER TABLE dd_order_candidate ALTER COLUMN updatedby SET DEFAULT 100
;
