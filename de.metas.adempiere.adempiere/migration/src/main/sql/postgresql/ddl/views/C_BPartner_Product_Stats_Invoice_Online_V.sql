
drop view if exists C_BPartner_Product_Stats_Invoice_Online_V;
create or replace view C_BPartner_Product_Stats_Invoice_Online_V as
select
	i.C_BPartner_ID,
	i.M_Product_ID,
	i.IsSOTrx,
	i.DateInvoiced,
	i.C_Currency_ID,
	i.PriceActual,
    i.InvoicableQtyBasedOn,
	--
	i.C_Invoice_ID,
	i.C_InvoiceLine_ID,
	--
	i.AD_Client_ID, i.AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
from (
	select 
		i.C_BPartner_ID,
		i.IsSOTrx,
		i.DateInvoiced,
		i.C_Currency_ID,
		i.C_Invoice_ID,
		--
		il.M_Product_ID,
		il.C_InvoiceLine_ID,
		il.PriceActual,
		il.InvoicableQtyBasedOn,
		--
		row_number()
			over (
				partition by i.C_BPartner_ID, i.IsSOTrx, il.M_Product_ID
				order by i.DateInvoiced desc, i.C_Invoice_ID desc, il.PriceActual desc
			) as rownum,
		--
    	i.AD_Client_ID, 0 as AD_Org_ID, i.Created, i.CreatedBy, i.Updated, i.UpdatedBy, i.IsActive
	from C_Invoice i
	inner join C_InvoiceLine il on il.C_Invoice_ID=i.C_Invoice_ID and il.M_Product_ID is not null
	where i.DocStatus in ('CO', 'CL')
) i
where rownum=1
;
