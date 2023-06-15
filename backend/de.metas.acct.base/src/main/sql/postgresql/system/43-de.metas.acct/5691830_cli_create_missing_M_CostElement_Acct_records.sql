INSERT INTO m_costelement_acct (ad_client_id, ad_org_id, c_acctschema_id, created, createdby, isactive, m_costelement_id, p_costclearing_acct, updated, updatedby)
SELECT ce.ad_client_id,
       0     AS ad_org_id,
       acs.c_acctschema_id,
       NOW() AS created,
       99    AS createdby,
       'Y'   AS isactive,
       ce.m_costelement_id,
       acsd.p_costclearing_acct,
       NOW() AS updated,
       99    AS updatedby
FROM m_costelement ce,
     c_acctschema acs
         INNER JOIN c_acctschema_default acsd ON acs.c_acctschema_id = acsd.c_acctschema_id
WHERE acs.isactive = 'Y'
  AND NOT EXISTS (SELECT 1 FROM m_costelement_acct z WHERE z.m_costelement_id = ce.m_costelement_id AND z.c_acctschema_id = acs.c_acctschema_id);
