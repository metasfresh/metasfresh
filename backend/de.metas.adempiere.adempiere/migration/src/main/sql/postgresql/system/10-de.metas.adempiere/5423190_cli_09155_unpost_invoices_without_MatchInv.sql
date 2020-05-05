-- Unpost purchase invoices which does not have a matchinv because we changed how they are booked for that case.
select fact_acct_unpost('C_Invoice', i.C_Invoice_ID)	
from C_InvoiceLine il
inner join C_Invoice i on (i.C_Invoice_ID=il.C_Invoice_ID)
inner join M_Product p on (p.M_Product_ID=il.M_Product_ID)
where i.DocStatus in ('CO', 'CL', 'RE')
and i.IsSOTrx='N'
-- and i.DateAcct between '2015-01-01' and '2015-06-30'
and not exists (select 1 from M_MatchInv mi where mi.C_InvoiceLine_ID=il.C_InvoiceLIne_ID)
and p.ProductType='I'
;

