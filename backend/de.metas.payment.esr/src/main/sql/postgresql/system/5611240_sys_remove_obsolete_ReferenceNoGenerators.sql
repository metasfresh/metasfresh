-- 2021-10-31 16:25
-- they are obsolete and just throw an UnsupportedOperationException since october 2018, so it's a good time to drop them alltogether
delete from c_referenceno_type 
where classname in ('de.metas.payment.esr.document.refid.spi.impl.RenderedCodeLineGenerator','de.metas.payment.esr.document.refid.spi.impl.InvoiceReferenceNoGenerator');
