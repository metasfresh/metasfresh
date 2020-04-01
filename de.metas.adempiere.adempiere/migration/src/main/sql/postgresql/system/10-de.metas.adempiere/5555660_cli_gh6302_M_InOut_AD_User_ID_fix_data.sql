UPDATE m_inout
SET ad_user_id=NULL, updatedby=99, updated='2020-03-30'
where ad_user_id=0;

-- drop table if exists backup.m_inout_wrong_user_gh6302;
CREATE TABLE backup.m_inout_wrong_user_gh6302 AS
SELECT bp.value || '_' || bp.name     AS bpartner,
       c.name                         AS contact_name,
       c.email                        AS contact_email,
       --
       c_bp.value || '_' || c_bp.name AS contact_bpartner,
       --
       io.m_inout_id,
       io.c_bpartner_id,
       io.ad_user_id,
       c_bp.c_bpartner_id                contact_bpartner_id
FROM m_inout io
         INNER JOIN c_bpartner bp ON bp.c_bpartner_id = io.c_bpartner_id
         INNER JOIN ad_user c ON c.ad_user_id = io.ad_user_id
         LEFT OUTER JOIN c_bpartner c_bp ON c_bp.c_bpartner_id = c.c_bpartner_id
WHERE io.c_bpartner_id != coalesce(c.c_bpartner_id, -1)
;

-- select * from  backup.m_inout_wrong_user_gh6302;

UPDATE m_inout
SET ad_user_id=NULL, updatedby=99, updated='2020-03-30'
WHERE m_inout_id IN (SELECT m_inout_id FROM backup.m_inout_wrong_user_gh6302);

