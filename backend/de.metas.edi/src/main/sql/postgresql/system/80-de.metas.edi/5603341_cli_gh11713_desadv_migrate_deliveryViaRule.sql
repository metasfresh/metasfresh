
update edi_desadv d set updated='2021-09-11 10:26', updatedby=99, DeliveryViaRule=o.DeliveryViaRule
from c_order o
where o.edi_desadv_id=d.edi_desadv_id and d.DeliveryViaRule is null;
