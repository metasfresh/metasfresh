DROP FUNCTION IF EXISTS exportSalesInvoicesInDateRange(TIMESTAMP With Time Zone, TIMESTAMP With Time Zone, numeric);

CREATE OR REPLACE FUNCTION exportSalesInvoicesInDateRange(IN p_C_Invoice_dateinvoiced_start TIMESTAMP With Time Zone,
                                                          IN p_C_Invoice_dateinvoiced_end TIMESTAMP With Time Zone,
                                                          IN p_AD_Org_ID numeric)

    RETURNS TABLE
            (
                AD_Org_ID        character varying,
                C_Bpartner_ID    character varying,
                C_Invoices_Count bigint
            )
AS

$BODY$

SELECT DISTINCT org.name as AD_Org_ID,
                bp.name  as C_Bpartner_ID,
                COUNT(*) AS C_Invoices_Count

FROM C_Invoice inv
         LEFT OUTER JOIN ad_org org on org.ad_org_id = inv.ad_org_id
         LEFT OUTER JOIN ad_orginfo orginfo on orginfo.ad_org_id = inv.ad_org_id
         LEFT OUTER JOIN c_bpartner bp on bp.c_bpartner_id = orginfo.org_bpartner_id
WHERE inv.dateinvoiced >= p_C_Invoice_dateinvoiced_start
  AND inv.dateinvoiced <= p_C_Invoice_dateinvoiced_end
  AND CASE WHEN p_AD_Org_ID > 0 THEN inv.ad_org_id = p_AD_Org_ID ELSE 1 = 1 END
  AND inv.ad_client_id = 1000000
  AND inv.isactive = 'Y'
  AND inv.docstatus IN ('CO', 'CL')
GROUP BY org.name, bp.name
$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;
