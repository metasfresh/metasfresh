-- added so 5616940 (where seqNo becomes mandatory) will not fail
update m_product_nutrition set seqno = 0 where seqno is null
;
