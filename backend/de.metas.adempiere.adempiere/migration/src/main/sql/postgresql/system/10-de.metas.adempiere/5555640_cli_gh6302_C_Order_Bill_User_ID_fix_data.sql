-- DROP TABLE IF EXISTS backup.c_order_wrong_bill_user_gh6302;
create table backup.c_order_wrong_bill_user_gh6302 as
SELECT bill_bpartner.value || '_' || bill_bpartner.name     AS bpartner,
       bill_contact.ad_user_id                              AS contact_id,
       bill_contact.name                                    AS contact_name,
       bill_contact.email                                   AS contact_email,
       --
       bill_contact_bp.value || '_' || bill_contact_bp.name AS contact_bpartner,
       --
       o.c_order_id,
       o.documentno,
       o.issotrx,
       o.docstatus,
       o.dateordered,
       o.created,
       o.updated,
       o.c_bpartner_id,
       o.ad_user_id,
       bill_contact.c_bpartner_id                              contact_bpartner_id
FROM C_Order o
         INNER JOIN c_bpartner bill_bpartner ON bill_bpartner.c_bpartner_id = o.bill_bpartner_id
         INNER JOIN ad_user bill_contact ON bill_contact.ad_user_id = o.bill_user_id
         LEFT OUTER JOIN c_bpartner bill_contact_bp ON bill_contact_bp.c_bpartner_id = bill_contact.c_bpartner_id
WHERE o.bill_bpartner_id != coalesce(bill_contact.c_bpartner_id, 0)
;

-- select * from backup.c_order_wrong_bill_user_gh6302;

UPDATE c_order
SET bill_user_id=NULL, updatedby=99, updated='2020-03-30'
WHERE c_order_id IN (SELECT c_order_id FROM backup.c_order_wrong_bill_user_gh6302);
