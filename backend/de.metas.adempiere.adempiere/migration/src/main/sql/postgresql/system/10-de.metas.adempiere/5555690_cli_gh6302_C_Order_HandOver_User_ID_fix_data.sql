-- DROP TABLE IF EXISTS backup.c_order_wrong_handover_user_gh6302;
create table backup.c_order_wrong_handover_user_gh6302 as
SELECT handover_bpartner.value || '_' || handover_bpartner.name     AS bpartner,
       handover_contact.ad_user_id                              AS contact_id,
       handover_contact.name                                    AS contact_name,
       handover_contact.email                                   AS contact_email,
       --
       handover_contact_bp.value || '_' || handover_contact_bp.name AS contact_bpartner,
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
       handover_contact.c_bpartner_id                              contact_bpartner_id
FROM C_Order o
         INNER JOIN c_bpartner handover_bpartner ON handover_bpartner.c_bpartner_id = o.handover_partner_id
         INNER JOIN ad_user handover_contact ON handover_contact.ad_user_id = o.handover_user_id
         LEFT OUTER JOIN c_bpartner handover_contact_bp ON handover_contact_bp.c_bpartner_id = handover_contact.c_bpartner_id
WHERE o.handover_partner_id != coalesce(handover_contact.c_bpartner_id, 0)
;

-- select * from backup.c_order_wrong_handover_user_gh6302;

UPDATE c_order
SET handover_user_id=NULL, updatedby=99, updated='2020-03-30'
WHERE c_order_id IN (SELECT c_order_id FROM backup.c_order_wrong_handover_user_gh6302);
