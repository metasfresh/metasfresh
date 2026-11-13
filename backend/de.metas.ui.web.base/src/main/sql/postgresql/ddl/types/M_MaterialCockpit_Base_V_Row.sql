-- Canonical definition of the row type of M_MaterialCockpit_Base_V_Fn(); created by migration 5832320_sys_gh33275_MaterialCockpit_Base_V_perf.sql

CREATE TYPE M_MaterialCockpit_Base_V_Row AS
(
    ad_client_id             numeric(10, 0),
    ad_org_id                numeric(10, 0),
    productname              varchar(600),
    productvalue             varchar(255),
    m_product_id             numeric(10, 0),
    m_product_category_id    numeric(10, 0),
    c_uom_id                 numeric(10, 0),
    attributeskey            text,
    m_warehouse_id           numeric(10, 0),
    qtyreserved              numeric,
    qtytomove                numeric,
    qtytoproduce             numeric,
    qtyforecasted            numeric,
    qtystock                 numeric,
    qtyconfirmedbysupplier   numeric,
    qtyunconfirmedbysupplier numeric,
    qtydemand_qtysupply_v_id integer,
    lastcostprice            numeric
)
;
