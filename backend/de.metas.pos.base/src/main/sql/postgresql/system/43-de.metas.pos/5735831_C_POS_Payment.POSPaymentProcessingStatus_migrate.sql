update c_pos_payment set POSPaymentProcessingStatus='SUCCESSFUL' where c_payment_id is not null and POSPaymentProcessingStatus!='SUCCESSFUL';

