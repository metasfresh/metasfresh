
update edi_desadv d set updated='2021-09-11 10:26', updatedby=99, DeliveryViaRule=o.DeliveryViaRule
from c_order o
where o.edi_desadv_id=d.edi_desadv_id and d.DeliveryViaRule is null;

--- if there is no linked order, or the order hos for some-reason no DeliveryViaRule (this ich actually mandatory)
--- then the edi_desadv is broken anyhow.
--- => just set it to "shipped" for "syntactical correctnes"
update  edi_desadv d set updated='2021-09-16 14:29', updatedby=99, DeliveryViaRule='S'
where d.DeliveryViaRule is null;
