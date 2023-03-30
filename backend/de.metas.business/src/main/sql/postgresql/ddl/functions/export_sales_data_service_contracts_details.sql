--
-- DROP FUNCTION IF EXISTS de_metas_acct.export_sales_data_service_contracts_details(fromdate date,
--                                                                                   todate   date)
-- ;

CREATE OR REPLACE FUNCTION de_metas_acct.export_sales_data_service_contracts_details(fromdate date,
                                                                                     todate   date)
    RETURNS TABLE
            (
                State          character varying,
                DateOrdered    date,
                OrderNo        character varying,
                ContractNo     character varying,
                StartDate      date,
                EndDate        date,
                ContractStatus character varying,
                Line           numeric,
                Product        character varying,
                lineNetAmt     numeric
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY (SELECT DATA.state::character varying,
                         DATA.dateordered,
                         DATA."OrderNo",
                         DATA."ContractNo",
                         DATA.startdate::DATE,
                         DATA.enddate::DATE,
                         DATA.contractstatus,
                         DATA.line,
                         DATA."Product" :: character varying,
                         DATA.linenetamt
                  FROM ((SELECT 'STARTED'                AS state,
                                o.dateordered::DATE,
                                o.documentno             AS "OrderNo",
                                ft.documentno            AS "ContractNo",
                                ft.startdate::DATE,
                                ft.enddate::DATE,
                                ft.contractstatus,
                                ol.line,
                                p.value || '-' || p.name AS "Product",
                                ol.linenetamt
                         FROM c_flatrate_term ft
                                  INNER JOIN c_orderline ol ON ft.c_orderline_term_id = ol.c_orderline_id
                                  INNER JOIN m_product p ON ft.m_product_id = p.m_product_id
                                  INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                         WHERE p.productType = 'S'
                           AND ft.processed = 'Y'
                           AND ft.docstatus IN ('CO', 'CL')
                           AND (ft.startdate BETWEEN fromDate AND toDate)
                           AND ft.contractstatus IN ('Ru') /* running contract only */
                           AND o.docstatus IN ('CO', 'CL')
                           AND o.c_doctypetarget_id IN (541083) /* Servicevertrag  */
                           AND ol.linenetamt != 0)
                        UNION ALL
                        (SELECT 'ENDED'                  AS state,
                                o.dateordered::DATE,
                                o.documentno             AS "OrderNo",
                                ft.documentno            AS "ContractNo",
                                ft.startdate::DATE,
                                ft.enddate::DATE,
                                ft.contractstatus,
                                ol.line,
                                p.value || '-' || p.name AS "Product",
                                ol.linenetamt * (-1)     AS linenetamt
                         FROM c_flatrate_term ft
                                  INNER JOIN c_orderline ol ON ft.c_orderline_term_id = ol.c_orderline_id
                                  INNER JOIN m_product p ON ft.m_product_id = p.m_product_id
                                  INNER JOIN c_order o ON ol.c_order_id = o.c_order_id
                         WHERE p.productType = 'S'
                           AND ft.processed = 'Y'
                           AND ft.docstatus IN ('CO', 'CL')
                           AND (ft.enddate BETWEEN fromDate AND toDate)
                           AND ft.contractstatus IN ('Qu') /* only Gekündigt contracts */
                           AND o.docstatus IN ('CO', 'CL')
                           AND o.c_doctypetarget_id IN (541083) /* Servicevertrag */
                           AND ol.linenetamt != 0)) DATA
                  ORDER BY DATA.state, DATA."OrderNo", DATA.line);
END
$$
;

ALTER FUNCTION de_metas_acct.export_sales_data_service_contracts_details(fromdate date,
    todate date) OWNER TO metasfresh
;
