-- since we can have a duplicate payment set, we need to drop the index
drop index  if exists esr_importline_payment_uq;

UPDATE ad_column SET readonlylogic = '@ESR_Payment_Action@ = F | @ESR_Payment_Action@ = C  | (@C_Payment_ID@ < 1 & @ESR_Payment_Action@ != P) | @Processed@ = Y 
| (@ESR_Payment_Action@ = A & @ESR_Invoice_Openamt@ = 0) ' WHERE ad_column_id = 548689;