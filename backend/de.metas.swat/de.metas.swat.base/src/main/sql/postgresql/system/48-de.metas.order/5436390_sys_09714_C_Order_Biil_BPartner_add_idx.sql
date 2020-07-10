
DROP INDEX IF EXISTS c_order_Bill_BPartner_ID;

CREATE INDEX c_order_Bill_BPartner_ID
  ON c_order
  USING btree
  (Bill_BPartner_ID);
COMMENT ON INDEX c_order_Bill_BPartner_ID
  IS 'Task 09714: adding this index to speed up MBpartner.getNotInvoicedAmt() which is used by MInOut.prepareIt() to find out whether a BPartner''s credit status allows the shipment to be made';
