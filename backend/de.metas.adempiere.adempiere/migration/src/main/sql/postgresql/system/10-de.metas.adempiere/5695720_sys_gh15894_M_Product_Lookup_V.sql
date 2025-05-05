DROP VIEW IF EXISTS M_Product_Lookup_V
;

CREATE OR REPLACE VIEW M_Product_Lookup_V AS
SELECT p.M_Product_ID,
       p.Value,
       p.Name,
       p.UPC,
       p.IsSummary,
       p.IsActive,
       p.Discontinued,
       p.DiscontinuedFrom,
       p.IsBOM,
       p.M_SectionCode_ID,
       NULL::numeric AS C_BPartner_ID,
       w.M_Warehouse_ID AS M_Warehouse_ID,
       NULL::varchar AS BPartnerProductNo,
       NULL::varchar AS BPartnerProductName,
       p.AD_Client_ID,
       p.AD_Org_ID,
       p.Created,
       p.CreatedBy,
       p.Updated,
       p.UpdatedBy
FROM M_Product p
         LEFT JOIN M_Product_Warehouse w ON p.m_product_id = w.m_product_id

UNION ALL

SELECT bpp.M_Product_ID,
       p.Value,
       p.Name,
       p.UPC,
       p.IsSummary,
       p.IsActive,
       p.Discontinued,
       p.DiscontinuedFrom,
       p.IsBOM,
       p.M_SectionCode_ID,
       bpp.C_BPartner_ID,
       w.M_Warehouse_ID AS M_Warehouse_ID,
       bpp.ProductNo   AS BPartnerProductNo,
       bpp.ProductName AS BPartnerProductName,
       p.AD_Client_ID,
       p.AD_Org_ID,
       p.Created,
       p.CreatedBy,
       p.Updated,
       p.UpdatedBy
FROM C_BPartner_Product bpp
         INNER JOIN M_Product p ON (p.M_Product_ID = bpp.M_Product_ID)
         LEFT JOIN M_Product_Warehouse w ON p.m_product_id = w.m_product_id
WHERE bpp.IsActive = 'Y'
;
