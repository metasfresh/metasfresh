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
            ad_client_id,
            ad_org_id, 
            isactive, 
            created, 
            createdby, 
            updated, 
            updatedby
)
SELECT
            nextval('c_bpartner_product_stats_seq'), 
            C_BPartner_ID, 
            M_Product_ID, 
            --
            LastReceiptDate, 
            LastShipDate, 
            --
            ad_client_id,
            ad_org_id, 
            isactive, 
            created, 
            createdby, 
            updated, 
            updatedby
FROM C_BPartner_Product_Stats_Online_V;

