DROP INDEX IF EXISTS pp_order_issueschedule_order
;

CREATE INDEX pp_order_issueschedule_order ON pp_order_issueschedule (pp_order_id)
;

