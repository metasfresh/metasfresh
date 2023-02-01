DROP VIEW IF EXISTS C_BPartner_CreditLimit_Department_Lines_V;

CREATE OR REPLACE VIEW C_BPartner_CreditLimit_Department_Lines_V
AS
SELECT

    lim.c_creditlimit_type_id,
    dep.m_department_id,
    lim.amount,
    ac.c_currency_id,
    lim.datefrom,
    lim.createdby,
    lim.processed,
    lim.approvedby_id,


    sectionGroupPartner.c_bpartner_id as Section_Group_Partner_ID,
    sectionPartner.c_bpartner_id,
    sectionPartner.c_bpartner_id as C_BPartner_CreditLimit_Department_Lines_V_ID,

    lim.Created as Created,
    lim.Updated as Updated,
    lim.UpdatedBy as UpdatedBy,
    lim.IsActive as IsActive,
    lim.ad_client_id as AD_Client_ID,
    lim.ad_org_id as AD_Org_ID


FROM C_BPartner sectionGroupPartner
    JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id
    JOIN c_bpartner_stats stats ON sectionPartner.c_bpartner_id = stats.c_bpartner_id
    JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
    JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
    JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id
    JOIN C_BPartner_CreditLimit lim ON sectionPartner.c_bpartner_id = lim.c_bpartner_id


    JOIN ad_clientinfo ci ON sectionGroupPartner.ad_client_id = ci.ad_client_id
    JOIN c_acctschema ac ON ci.c_acctschema1_id = ac.c_acctschema_id

    WHERE  ((lim.processed = 'Y' and lim.isactive = 'Y') OR (lim.processed = 'N' and lim.isactive = 'N'))
    AND lim.datefrom <= now()::date
    AND NOT EXISTS(SELECT 1
    FROM c_bpartner_creditlimit lim2
    WHERE lim2.datefrom > lim.datefrom
    AND lim2.datefrom <= now()::date
    AND ((lim2.processed = 'Y' and lim2.isactive = 'Y') OR (lim2.processed = 'N' and lim2.isactive = 'N')))

;
