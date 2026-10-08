
CREATE INDEX IF NOT EXISTS m_transaction_M_AttributeSetInstance_ID
 ON public.m_transaction
 USING btree
 (M_AttributeSetInstance_ID);

CREATE INDEX IF NOT EXISTS C_OrderLine_M_AttributeSetInstance_ID
 ON public.C_OrderLine
 USING btree
 (M_AttributeSetInstance_ID); 

CREATE INDEX IF NOT EXISTS C_InvoiceLine_M_AttributeSetInstance_ID
 ON public.C_InvoiceLine
 USING btree
 (M_AttributeSetInstance_ID); 

CREATE INDEX IF NOT EXISTS M_InOutLine_M_AttributeSetInstance_ID
 ON public.M_InOutLine
 USING btree
 (M_AttributeSetInstance_ID); 
 