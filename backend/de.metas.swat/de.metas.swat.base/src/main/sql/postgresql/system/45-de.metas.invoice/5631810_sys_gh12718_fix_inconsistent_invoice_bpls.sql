
create table backup.c_invoice_20220324 as select * from c_invoice;

create table backup.c_invoice_fix_bpl_20220324 as
SELECT i.c_invoice_id,
       i.documentno as i_documentno,
       i.created as i_created,
       i.updated as i_updated,
       i.c_bpartner_id as i_c_bpartner_id,
       bpl_wrong.c_bpartner_location_id as bpl_wrong_c_bpartner_location_id,
       bpl_wrong.c_bpartner_id as bpl_wrong_c_bpartner_id,
       bpl_right.c_bpartner_location_id as bpl_right_c_bpartner_location_id
FROM c_invoice i
         JOIN c_bpartner_location bpl_wrong ON bpl_wrong.c_bpartner_location_id = i.c_bpartner_location_id
         LEFT JOIN LATERAL (SELECT * FROM c_bpartner_location bpl_inner WHERE bpl_inner.c_bpartner_id = i.c_bpartner_id ORDER BY isbilltodefault DESC, isbillto DESC LIMIT 1) bpl_right ON TRUE
WHERE i.c_bpartner_id != bpl_wrong.c_bpartner_id
;

update c_invoice i set updated='2022-03-24 14:19', updatedby=99, c_bpartner_location_id=data.bpl_right_c_bpartner_location_id
from backup.c_invoice_fix_bpl_20220324 data
where data.c_invoice_id=i.c_invoice_id and data.bpl_right_c_bpartner_location_id!=i.c_bpartner_location_id;
