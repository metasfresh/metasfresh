UPDATE Revolut_Payment_Export
SET SwiftCode = BIC
;

ALTER TABLE Revolut_Payment_Export
DROP COLUMN BIC
;