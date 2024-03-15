-- DROP VIEW IF EXISTS C_BPartner_CreditLimit_Departments_V;


CREATE OR REPLACE VIEW C_BPartner_CreditLimit_Departments_V
AS
SELECT sectionGroupPartner.c_bpartner_id                                                                            AS Section_Group_Partner_ID,
       sectionPartner.c_bpartner_id                                                                                 AS C_BPartner_ID,
       sectionCode.m_sectioncode_id,
       dep.m_department_id,
       ac.C_Currency_ID                                                                                        AS C_Currency_ID,

	   getSOCreditUsedForSectionGroupPartnerDepartment(sectionGroupPartner.c_bpartner_id, dep.m_department_id)                   AS SO_CreditUsed,
       getOpenOrderAmtForSectionGroupDepartment(sectionGroupPartner.c_bpartner_id, dep.m_department_id)                          AS M_Department_Order_OpenAmt,
       getDeliveryCreditUsedForSectionGroupDepartment(sectionGroupPartner.c_bpartner_id, dep.m_department_id)                    AS Delivery_CreditUsed,
       COALESCE(getTotalOrderValueForSectionGroupDepartment(sectionGroupPartner.c_bpartner_id, dep.m_department_id), 0)          AS TotalOrderValue,
       getOpenBalanceForSectionGroupDepartment(sectionGroupPartner.c_bpartner_id, dep.m_department_id)                           AS OpenItems,
       COALESCE(getCreditLimitForSectionGroupDepartment(sectionGroupPartner.c_bpartner_id, dep.m_department_id, NOW()::date), 0) AS CreditLimit,

       sectionPartner.c_bpartner_id as C_BPartner_CreditLimit_Departments_V_ID,
       now() as Created,
       now() as Updated,
       1000000::numeric as CreatedBy,
       1000000::numeric as UpdatedBy,
       'Y'::char as IsActive,
       sectionGroupPartner.ad_client_id as AD_Client_ID,
       sectionGroupPartner.ad_org_id as AD_Org_ID


FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id

         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id

         JOIN ad_clientinfo ci ON sectionGroupPartner.ad_client_id = ci.ad_client_id
         JOIN c_acctschema ac ON ci.c_acctschema1_id = ac.c_acctschema_id

;