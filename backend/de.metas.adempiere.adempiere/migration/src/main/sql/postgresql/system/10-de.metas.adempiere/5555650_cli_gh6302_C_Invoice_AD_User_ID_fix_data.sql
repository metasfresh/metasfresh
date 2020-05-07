-- drop table if exists backup.c_invoice_wrong_user_gh6302;
CREATE TABLE backup.c_invoice_wrong_user_gh6302 AS
SELECT bp.value || '_' || bp.name     AS bpartner,
       c.name                         AS contact_name,
       c.email                        AS contact_email,
       --
       c_bp.value || '_' || c_bp.name AS contact_bpartner,
       --
       i.c_invoice_id,
       i.c_bpartner_id,
       i.ad_user_id,
       c_bp.c_bpartner_id                contact_bpartner_id
FROM C_Invoice i
         INNER JOIN c_bpartner bp ON bp.c_bpartner_id = i.c_bpartner_id
         INNER JOIN ad_user c ON c.ad_user_id = i.ad_user_id
         LEFT OUTER JOIN c_bpartner c_bp ON c_bp.c_bpartner_id = c.c_bpartner_id
WHERE i.c_bpartner_id != coalesce(c.c_bpartner_id, -1)
;

-- select * from  backup.c_invoice_wrong_user_gh6302;

UPDATE c_invoice
SET ad_user_id=NULL, updatedby=99, updated='2020-03-30'
WHERE c_invoice_id IN (SELECT c_invoice_id FROM backup.c_invoice_wrong_user_gh6302);

