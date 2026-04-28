-- NOTE: we need to unpost all the not already reconciled payments
-- because now we are posting payments on BankInTransit account even if they are not yet reconciled

select fact_acct_unpost('C_Payment', p.C_Payment_ID)
from C_Payment p
where p.IsReconciled='N'
;

