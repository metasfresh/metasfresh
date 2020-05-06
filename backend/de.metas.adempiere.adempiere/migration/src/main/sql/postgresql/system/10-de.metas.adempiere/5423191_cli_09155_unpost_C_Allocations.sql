-- Unpost C_AllocationHdrs which have a line with C_Invoice_ID=not null, C_Order_ID=not null, C_Payment_ID=null
-- because there was a bug which was fixed in this task.
-- Before the bug, those lines were posted and SUSPENSE_BALANCING account was used balance the result.

select fact_acct_unpost('C_AllocationHdr', al.C_AllocationHdr_ID)
from C_AllocationLine al
where al.C_Invoice_ID is not null and al.C_Order_ID is not null and al.C_Payment_ID is null
;
