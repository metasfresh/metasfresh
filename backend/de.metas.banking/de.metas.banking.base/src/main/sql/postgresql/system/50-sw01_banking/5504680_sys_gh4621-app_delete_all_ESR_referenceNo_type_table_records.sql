
delete
from C_ReferenceNo_Type_Table 
where C_ReferenceNo_Type_ID IN (
	select C_ReferenceNo_Type_ID from C_ReferenceNo_Type where classname in (
	'de.metas.payment.esr.document.refid.spi.impl.InvoiceReferenceNoGenerator',
	'de.metas.payment.esr.document.refid.spi.impl.RenderedCodeLineGenerator'
	)
);
