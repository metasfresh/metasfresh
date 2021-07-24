drop function if exists C_BPartner_Product_Stats_refresh();

create or replace function C_BPartner_Product_Stats_refresh()
returns void
as
$BODY$
begin
    drop table if exists TMP_BPartnerProductStats_InOut;
    create temporary table TMP_BPartnerProductStats_InOut as select * from C_BPartner_Product_Stats_InOut_Online_V;
    create index on TMP_BPartnerProductStats_InOut(AD_Client_ID, C_BPartner_ID, M_Product_ID);

    drop table if exists TMP_BPartnerProductStats_Invoice;
    create temporary table TMP_BPartnerProductStats_Invoice as select * from C_BPartner_Product_Stats_Invoice_Online_V;
    create index on TMP_BPartnerProductStats_Invoice(AD_Client_ID, C_BPartner_ID, M_Product_ID);

    drop table if exists TMP_BPartnerProductStats_KEY;
    create temporary table TMP_BPartnerProductStats_KEY as
        select distinct AD_Client_ID, C_BPartner_ID, M_Product_ID from TMP_BPartnerProductStats_InOut
        union
        select distinct AD_Client_ID, C_BPartner_ID, M_Product_ID from TMP_BPartnerProductStats_Invoice;


    delete from C_BPartner_Product_Stats;

    INSERT INTO C_BPartner_Product_Stats
    (
                C_BPartner_Product_Stats_ID,
                C_BPartner_ID,
                M_Product_ID,
                --
                LastReceiptDate,
                LastShipDate,
                --
                LastSales_Invoice_ID,
                LastSalesInvoiceDate,
                LastSalesPrice,
                LastSalesPrice_Currency_ID,
                --
                AD_Client_ID,
                AD_Org_ID,
                IsActive,
                Created,
                CreatedBy,
                Updated,
                UpdatedBy
    )
    SELECT
                nextval('c_bpartner_product_stats_seq'), 
                k.C_BPartner_ID,
                k.M_Product_ID,
                --
                inouts.LastReceiptDate,
                inouts.LastShipDate,
                --
                invoices.C_Invoice_ID as LastSales_Invoice_ID,
                invoices.DateInvoiced as LastSalesInvoiceDate,
                invoices.PriceActual as LastSalesPrice,
                invoices.C_Currency_ID as LastSalesPrice_Currency_ID,
                --
                k.AD_Client_ID,
                0 as AD_Org_ID, 
                'Y' as IsActive, 
                now() as Created, 
                99 as CreatedBy, 
                now() as Updated, 
                99 as UpdatedBy
    FROM TMP_BPartnerProductStats_KEY k
    LEFT OUTER JOIN TMP_BPartnerProductStats_InOut inouts on
                            inouts.AD_Client_ID=k.AD_Client_ID
                            and inouts.C_BPartner_ID=k.C_BPartner_ID
                            and inouts.M_Product_ID=k.M_Product_ID
    LEFT OUTER JOIN TMP_BPartnerProductStats_Invoice invoices on
                            invoices.AD_Client_ID=k.AD_Client_ID
                            and invoices.C_BPartner_ID=k.C_BPartner_ID
                            and invoices.M_Product_ID=k.M_Product_ID
                            and invoices.IsSOTrx='Y'
    ;
end;
$BODY$
LANGUAGE plpgsql VOLATILE 
;


-- select C_BPartner_Product_Stats_refresh();

