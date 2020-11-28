-- DROP TABLE IF EXISTS backup.c_order_wrong_dropship_user_gh6302;
create table backup.c_order_wrong_dropship_user_gh6302 as
SELECT dropship_bpartner.value || '_' || dropship_bpartner.name     AS bpartner,
       dropship_contact.ad_user_id                              AS contact_id,
       dropship_contact.name                                    AS contact_name,
       dropship_contact.email                                   AS contact_email,
       --
       dropship_contact_bp.value || '_' || dropship_contact_bp.name AS contact_bpartner,
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
       dropship_contact.c_bpartner_id                              contact_bpartner_id
FROM C_Order o
         INNER JOIN c_bpartner dropship_bpartner ON dropship_bpartner.c_bpartner_id = o.dropship_bpartner_id
         INNER JOIN ad_user dropship_contact ON dropship_contact.ad_user_id = o.dropship_user_id
         LEFT OUTER JOIN c_bpartner dropship_contact_bp ON dropship_contact_bp.c_bpartner_id = dropship_contact.c_bpartner_id
WHERE o.dropship_bpartner_id != coalesce(dropship_contact.c_bpartner_id, 0)
;

-- select * from backup.c_order_wrong_dropship_user_gh6302;

UPDATE c_order
SET dropship_user_id=NULL, updatedby=99, updated='2020-03-30'
WHERE c_order_id IN (SELECT c_order_id FROM backup.c_order_wrong_dropship_user_gh6302);
