-- drop table if exists backup.c_order_wrong_user_gh6302;
create table backup.c_order_wrong_user_gh6302 as
SELECT bp.value || '_' || bp.name                 AS bpartner,
       contact.ad_user_id                         AS contact_id,
       contact.name                               AS contact_name,
       contact.email                              AS contact_email,
       --
       contact_bp.value || '_' || contact_bp.name AS contact_bpartner,
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
       contact_bp.c_bpartner_id                      contact_bpartner_id
FROM C_Order o
         INNER JOIN c_bpartner bp ON bp.c_bpartner_id = o.c_bpartner_id
         INNER JOIN ad_user contact ON contact.ad_user_id = o.ad_user_id
         LEFT OUTER JOIN c_bpartner contact_bp ON contact_bp.c_bpartner_id = contact.c_bpartner_id
WHERE o.c_bpartner_id != coalesce(contact.c_bpartner_id, 0)
;

-- select * from backup.c_order_wrong_user_gh6302;

update c_order set ad_user_id=null,updatedby=99,updated='2020-03-30'
    where c_order_id in (select c_order_id from backup.c_order_wrong_user_gh6302);
