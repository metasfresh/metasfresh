-- 07.03.2017 08:36
-- I forgot to set the DICTIONARY_ID_COMMENTS System Configurator
UPDATE AD_Column SET ColumnSQL='(
select COALESCE(SUM(ol.QtyEnteredTU),0) 
from C_OrderLine ol
inner join C_Order o on (o.C_Order_ID=ol.C_Order_ID) 
where ol.C_OrderLine_id = M_ReceiptSchedule.C_OrderLine_ID
and ol.Processed=''Y'' 
and o.DocStatus IN (''CO'', ''CL'')
)',Updated=TO_TIMESTAMP('2017-03-07 08:36:21','YYYY-MM-DD HH24:MI:SS'),UpdatedBy=100 WHERE AD_Column_ID=556335
;

