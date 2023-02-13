
DROP FUNCTION IF EXISTS getOverdueInvoicesWithOpenOrders(numeric,
                                                         numeric,
                                                         numeric,
                                                         date,
                                                         date,
                                                         date,
                                                         date)
;

CREATE OR REPLACE FUNCTION getOverdueInvoicesWithOpenOrders(
    p_m_department_id         numeric,
    p_m_sectionCode_id        numeric,
    p_c_bpartner_id           numeric,
    p_plannedLoadingDateFrom  date,
    p_plannedLoadingDateTo    date,
    p_plannedDeliveryDateFrom date,
    p_plannedDeliveryDateTo   date
)
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
    STABLE
    LANGUAGE sql
AS
$$


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

WHERE TRUE
  AND (dep.m_department_id = p_m_department_id OR p_m_department_id IS NULL)   -- Department
  AND (sc.m_sectioncode_id = p_m_sectioncode_id OR p_m_sectioncode_id IS NULL) -- Section Code
  AND (bp.c_bpartner_id = p_c_bpartner_id OR p_c_bpartner_id IS NULL)          -- Business partner
  AND (bp.c_bpartner_id = p_c_bpartner_id OR p_c_bpartner_id IS NULL)
  AND (dp.plannedloadingdate BETWEEN p_plannedLoadingDateFrom AND p_plannedLoadingDateTo -- Planned loading date (range)
    OR (p_plannedLoadingDateFrom IS NULL AND p_plannedLoadingDateTo IS NULL))
  AND (dp.planneddeliverydate BETWEEN p_plannedDeliveryDateFrom AND p_plannedDeliveryDateTo -- Planned discharge date (range)
    OR (p_plannedDeliveryDateFrom IS NULL AND p_plannedDeliveryDateTo IS NULL))

  AND o.IsSOTrx = 'Y'
  AND i.docstatus IN ('CO', 'CL')
  AND i.ispaid = 'N'
  AND ((i.dateinvoiced + netdays)
    < NOW()::DATE)
  AND ol.qtyenteredinpriceuom
    > (SELECT COALESCE(SUM(sp.actualloadqty)
                  , 0)
       FROM m_shippingpackage sp
                JOIN M_ShipperTransportation st
                     ON sp.m_shippertransportation_id = st.m_shippertransportation_id
       WHERE sp.c_orderline_id = ol.c_orderline_id
         AND st.docstatus IN ('CO', 'CL'))
    ;


$$
;

ALTER FUNCTION getOverdueInvoicesWithOpenOrders() OWNER TO metasfresh
;
