create temporary table C_Invoice_PartialMatched as
select
	t.*
	, rank() over (partition by t.C_InvoiceLine_ID order by iol.M_InOutLine_ID, mi.M_MatchInv_ID) as MI_Index
	, mi.Qty as MI_Qty
	, mi.M_InOutLIne_ID
	, iol.MovementQty
	, io.DocumentNo as IO_DocNo
	, io.DocStatus as IO_DocStatus
from (
 select
  i.DocumentNo, i.C_Invoice_ID, i.DateInvoiced, i.DocStatus, dt.Name as DocTypeName, i.IsSOTrx
  , bp.Value as BPValue
  , il.Line, il.C_InvoiceLine_ID
  , p.Name as ProductName
  , il.QtyInvoiced
  , coalesce(sum(mi.Qty),0) as QtyMatched
  , count(mi.M_MatchInv_ID) as Count_MatchInvs
 from C_InvoiceLine il
 inner join M_Product p on (p.M_Product_ID=il.M_Product_ID)
 inner join C_Invoice i on (i.C_Invoice_ID=il.C_Invoice_ID)
 inner join C_DocType dt on (dt.C_DocType_ID=i.C_DocType_ID)
 inner join C_BPartner bp on (bp.C_BPartner_ID=i.C_BPartner_ID)
 left outer join M_MatchInv mi on (il.C_InvoiceLine_ID=mi.C_InvoiceLine_ID)
 group by i.C_Invoice_ID, dt.C_DocType_ID, il.C_InvoiceLine_ID, p.M_Product_ID, bp.C_BPartner_ID
) t
inner join M_MatchInv mi on (mi.C_InvoiceLine_ID=t.C_InvoiceLine_ID)
inner join M_InOutLine iol on (iol.M_InOutLine_ID=mi.M_InOutLine_ID)
inner join M_InOut io on (io.M_InOut_ID=iol.M_InOut_ID)
where true
and t.Count_MatchInvs>0
and t.QtyMatched != 0
and abs(t.QtyInvoiced)>abs(t.QtyMatched)
and t.IsSOTrx='N'
--
order by DocumentNo, mi.M_InOutLine_ID
;
create index C_Invoice_PartialMatched_C_Invoice_ID on C_Invoice_PartialMatched(C_Invoice_ID);

--
-- Show results
-- select * from C_Invoice_PartialMatched order by DocumentNo, M_InOutLine_ID

--
-- Unpost the invoices
select fact_acct_unpost('C_Invoice', C_Invoice_ID) from C_Invoice_PartialMatched;

