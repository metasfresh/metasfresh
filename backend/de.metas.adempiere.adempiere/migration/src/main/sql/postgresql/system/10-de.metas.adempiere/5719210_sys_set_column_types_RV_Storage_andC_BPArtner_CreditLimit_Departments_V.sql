
DROP VIEW RV_Storage;

CREATE OR REPLACE VIEW RV_Storage AS
SELECT s.ad_client_id,
       s.ad_org_id,
       s.m_product_id,
       p.value,
       p.name,
       p.description,
       p.upc,
       p.sku,
       p.c_uom_id,
       p.m_product_category_id,
       p.classification,
       p.weight,
       p.volume,
       p.versionno,
       p.guaranteedays,
       p.guaranteedaysmin,
       s.m_locator_id,
       l.m_warehouse_id,
       l.x,
       l.y,
       l.z,
       s.qtyonhand,
       s.qtyreserved,
       s.qtyonhand - s.qtyreserved AS qtyavailable,
       s.qtyordered,
       s.datelastinventory,
       s.m_attributesetinstance_id,
       asi.m_attributeset_id,
       NULL::numeric               AS shelflifedays,
       NULL::numeric               AS goodfordays,
       NULL::numeric
                                   AS shelfliferemainingpct
FROM m_storage s
         JOIN m_locator l ON s.m_locator_id = l.m_locator_id
         JOIN m_product p ON s.m_product_id = p.m_product_id
         LEFT JOIN m_attributesetinstance asi ON s.m_attributesetinstance_id = asi.m_attributesetinstance_id
;

DROP VIEW IF EXISTS C_BPartner_CreditLimit_Departments_V;
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
