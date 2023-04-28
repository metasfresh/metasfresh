DROP VIEW IF EXISTS C_BPartner_CreditLimit_Department_Lines_V
;

CREATE OR REPLACE VIEW C_BPartner_CreditLimit_Department_Lines_V
AS
SELECT lim.c_creditlimit_type_id,
       dep.m_department_id,
       lim.amount,
       ac.c_currency_id,
       lim.datefrom,
       lim.createdby,
       lim.processed,
       lim.approvedby_id,


       sectionGroupPartner.c_bpartner_id AS Section_Group_Partner_ID,
       sectionPartner.c_bpartner_id,
       sectionPartner.c_bpartner_id      AS C_BPartner_CreditLimit_Department_Lines_V_ID,

       lim.Created                       AS Created,
       lim.Updated                       AS Updated,
       lim.UpdatedBy                     AS UpdatedBy,
       lim.IsActive                      AS IsActive,
       lim.ad_client_id                  AS AD_Client_ID,
       lim.ad_org_id                     AS AD_Org_ID


FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
         JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id
         JOIN C_BPartner_CreditLimit lim ON sectionPartner.c_bpartner_id = lim.c_bpartner_id


         JOIN ad_clientinfo ci ON sectionGroupPartner.ad_client_id = ci.ad_client_id
         JOIN c_acctschema ac ON ci.c_acctschema1_id = ac.c_acctschema_id

WHERE ((lim.processed = 'Y' AND lim.isactive = 'Y') OR (lim.processed = 'N' AND lim.isactive = 'N'))
  AND lim.datefrom <= NOW()::date
  AND NOT EXISTS(SELECT 1
                 FROM c_bpartner_creditlimit lim2
                 WHERE lim.c_bpartner_id = lim2.c_bpartner_id
                   AND lim.c_bpartner_creditlimit_id != lim2.c_bpartner_creditlimit_id
                   AND lim2.datefrom >= lim.datefrom
                   AND lim2.datefrom <= NOW()::date
                   AND ((lim2.processed = 'Y' AND lim2.isactive = 'Y') OR (lim2.processed = 'N' AND lim2.isactive = 'N')))

;
