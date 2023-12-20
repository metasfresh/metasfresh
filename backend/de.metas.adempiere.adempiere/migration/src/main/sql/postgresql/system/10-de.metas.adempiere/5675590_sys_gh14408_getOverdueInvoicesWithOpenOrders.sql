DROP FUNCTION IF EXISTS getOverdueInvoicesWithOpenOrders()
;

CREATE OR REPLACE FUNCTION getOverdueInvoicesWithOpenOrders()
    RETURNS TABLE
            (
                "Department"             character varying,
                "Section code"           character varying,
                "Business partner"       character varying,
                "SO"                     character varying,
                "SO date"                character varying,
                "Release number"         character varying,
                "Planned loading date"   character varying,
                "Planned discharge date" character varying

            )
AS

$BODY$


SELECT dep.value || '_' || dep.name                        AS "Department",
       sc.value || '_' || sc.name                          AS "Section code",
       bp.value || '_' || bp.name                          AS "Business partner",
       o.documentno                                        AS "SO",
       TO_CHAR(o.dateordered::date, 'MM/DD/YYYY')          AS "SO date",
       dp.releaseno                                        AS "Release number",
       TO_CHAR(dp.plannedloadingdate::date, 'MM/DD/YYYY')  AS "Planned loading date",
       TO_CHAR(dp.planneddeliverydate::date, 'MM/DD/YYYY') AS "Planned discharge date"


FROM M_Department dep
         JOIN M_Department_SectionCode depSC ON dep.m_department_id = depSC.m_department_id
         JOIN M_SectionCode sc ON depSC.m_sectioncode_id = sc.m_sectioncode_id
         JOIN C_BPartner bp ON sc.m_sectioncode_id = bp.m_sectioncode_id
         JOIN C_Order o ON bp.c_bpartner_id = o.c_bpartner_id
         JOIN C_OrderLine ol ON o.c_order_id = ol.c_order_id
         LEFT JOIN M_Delivery_Planning dp ON ol.c_orderLine_id = dp.c_orderLine_id
         JOIN C_Invoice i ON bp.c_bpartner_id = i.c_bpartner_id
         JOIN C_PaymentTerm pt ON i.c_paymentterm_id = pt.c_paymentterm_id


WHERE o.IsSOTrx = 'Y'
  AND i.docstatus IN ('CO', 'CL')
  AND i.ispaid = 'N'
  AND ((i.dateinvoiced + netdays)
    < NOW()::date)
  AND ol.qtyenteredinpriceuom
    > (SELECT COALESCE(SUM(sp.actualloadqty)
                  , 0)
       FROM m_shippingpackage sp
                JOIN M_ShipperTransportation st
                     ON sp.m_shippertransportation_id = st.m_shippertransportation_id
       WHERE sp.c_orderline_id = ol.c_orderline_id
         AND st.docstatus IN ('CO', 'CL'))
    ;


$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000
;
