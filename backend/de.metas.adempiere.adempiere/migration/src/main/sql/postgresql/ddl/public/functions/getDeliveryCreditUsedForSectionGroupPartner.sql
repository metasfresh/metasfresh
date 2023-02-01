

DROP FUNCTION IF EXISTS getDeliveryCreditUsedForSectionGroupPartner(p_section_group_partner_id numeric, p_m_department_ID numeric)
;


CREATE FUNCTION getDeliveryCreditUsedForSectionGroupPartner(p_section_group_partner_id numeric,  p_m_department_ID numeric) RETURNS numeric
    STABLE
    LANGUAGE sql
AS
$$

SELECT COALESCE(SUM(currencyBase(sp.actualloadqty * ol.priceactual + CASE
                                                                         WHEN (o.istaxincluded = 'N' AND t.isWholeTax = 'N') THEN
                                                                             ROUND((ol.linenetamt * ROUND(t.rate / 100, 12)), c.StdPrecision)
                                                                                                                             ELSE 0
                                                                     END, o.C_Currency_ID, ol.DateOrdered, ol.AD_Client_ID, ol.AD_Org_ID)), 0)


FROM C_BPartner sectionGroupPartner
         JOIN C_BPartner sectionPartner ON sectionGroupPartner.c_bpartner_id = sectionPartner.section_group_partner_id

         JOIN M_SectionCode sectionCode ON sectionPartner.m_sectioncode_id = sectionCode.m_sectioncode_id
         JOIN m_department_sectioncode depSectionCode ON sectionCode.m_sectioncode_id = depSectionCode.m_sectioncode_id
         JOIN M_Department dep ON depSectionCode.m_department_id = dep.m_department_id


         LEFT JOIN C_BPartner_OpenAmounts_v openView ON sectionPartner.c_bpartner_id = openView.c_bpartner_id

         LEFT JOIN M_ShipperTransportation deliveryInstruction ON sectionPartner.c_bpartner_id = deliveryInstruction.shipper_bpartner_id
         LEFT JOIN M_ShippingPackage sp ON deliveryInstruction.m_shippertransportation_id = sp.m_shippertransportation_id
         LEFT JOIN C_OrderLine ol ON sp.c_orderline_id = ol.c_orderline_id
         LEFT JOIN C_Order o ON ol.c_order_id = o.c_order_id
         LEFT JOIN C_Tax t ON ol.C_Tax_ID = t.C_Tax_ID
         LEFT JOIN C_Currency c ON c.C_Currency_ID = ol.C_Currency_ID

WHERE sectionGroupPartner.c_bpartner_id = p_section_group_partner_id
and dep.m_department_id = p_m_department_ID


GROUP BY sectionPartner.section_group_partner_id, dep.m_department_id

$$
;