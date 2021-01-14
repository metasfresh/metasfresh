CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.isEUShippingFromInvoice(IN p_C_Invoice_ID numeric)
    RETURNS char(1) AS
$BODY$
DECLARE

    isEUShippingFromInvoice char(1);

BEGIN

    isEUShippingFromInvoice := (
        select case when (count(1) > 0) then 'Y' else 'N' end
        from C_invoice i

                 ---- invoice warehouse country
                 left join m_warehouse wi on wi.m_warehouse_id = i.m_warehouse_id
                 left join c_location wli on wli.c_location_id = wi.c_location_id

            ---- shipment warehouse country
                 join c_invoiceline il on il.C_invoice_id = i.C_invoice_id
                 left join m_inoutline iol on iol.m_inoutline_id = il.m_inoutline_id
                 join m_inout io on io.m_inout_id = iol.m_inout_id
                 left join m_warehouse wio on wio.m_warehouse_id = io.m_warehouse_id
                 left join c_location wlio on wlio.c_location_id = wio.c_location_id

            ---- org warehouse country
                 left join AD_orgInfo oi on oi.ad_org_id = i.ad_org_id
                 left join m_warehouse wo on wo.m_warehouse_id = oi.m_warehouse_id
                 left join c_location wlo on wlo.c_location_id = wi.c_location_id

        where i.issotrx = 'Y'
          and i.isactive = 'Y'
          and i.DocStatus in ('CO', 'CL')
          and isEUCountry(coalesce(wlio.C_Country_ID, wli.C_Country_ID, wlo.c_country_id)) = 'Y'
          and i.C_Invoice_ID = p_C_Invoice_ID
    );

    IF isEUShippingFromInvoice IS NOT NUll THEN
        RETURN isEUShippingFromInvoice;
    ELSE
        RETURN 'N';
    END IF;
END;
$BODY$ LANGUAGE plpgsql;