create unique index C_PaySelectionLine_Payment_UQ on C_PaySelectionLine(C_Payment_ID) where C_Payment_ID is not null;

