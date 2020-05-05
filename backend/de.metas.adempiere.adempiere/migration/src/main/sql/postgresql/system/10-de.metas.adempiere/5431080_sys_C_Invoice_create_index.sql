
DROP INDEX IF EXISTS c_invoice_created;

CREATE INDEX c_invoice_created
   ON c_invoice (created);
COMMENT ON INDEX c_invoice_created
  IS 'Required because the normal gridtab UI executes
  SELECT ...
  FROM <table>
  WHERE ...
  ORDER BY CREATED
  Limit n
and the ordering takes extremely long in the case of a large table';
