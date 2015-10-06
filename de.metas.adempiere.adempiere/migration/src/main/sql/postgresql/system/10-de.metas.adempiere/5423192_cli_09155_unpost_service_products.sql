select fact_acct_unpost('M_MatchInv', mi.M_MatchInv_ID)
-- , (select i.DocumentNo||'_'||i.IsSOTrx from C_Invoice i, C_InvoiceLIne il where il.C_InvoiceLine_ID=mi.C_InvoiceLine_ID and i.C_Invoice_ID=il.C_Invoice_ID) as Inv
-- , p.Value, p.Name
-- , mi.*
from M_MatchInv mi
inner join M_Product p on (p.M_Product_ID=mi.M_Product_ID)
where (p.ProductType<>'I' or p.IsStocked='N')
;

select fact_acct_unpost('M_InOut', iol.M_InOut_ID)
from M_InOutLine iol
inner join M_Product p on (p.M_Product_ID=iol.M_Product_ID)
where (p.ProductType<>'I' or p.IsStocked='N')
;

