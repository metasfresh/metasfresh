




DROP FUNCTION IF EXISTS priceSchemaExcel(character varying);

CREATE OR REPLACE FUNCTION priceSchemaExcel(	IN p_isSold 			character varying,
						IN p_M_DiscountSchema_ID 	numeric
					)

    RETURNS TABLE
            (

		        seqno						character varying,
				ProductValue				character varying,
				m_product_id				character varying,
				M_Product_Category_Value		character varying,
				m_product_category_id			character varying,
				BPartnerValue				character varying,
				c_bpartner_id				character varying,
				c_taxcategory_id			character varying,
				std_addamt				numeric,
				std_rounding				character ,
				c_taxcategory_target_id    		character varying
	   
            )
AS

$BODY$










WITH exportedLines AS (
    SELECT --
           -- fields which appear in the UI
           l.seqno                                                                                               seqno,
           (SELECT p.value FROM m_product p WHERE p.m_product_id = l.m_product_id)                               product_value,
           (SELECT p.name FROM m_product p WHERE p.m_product_id = l.m_product_id)                                product_name,
           l.m_product_id                                                                                        m_product_id,
           (SELECT pc.value FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id) m_product_category_value,
           (SELECT pc.name FROM m_product_category pc WHERE pc.m_product_category_id = l.m_product_category_id)  m_product_category_id,
           (SELECT bp.value FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                         c_bpartner_value,
           (SELECT bp.name FROM c_bpartner bp WHERE bp.c_bpartner_id = l.c_bpartner_id)                          c_bpartner_id,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_id)                 c_taxcategory_id,
           l.std_addamt,
           l.std_rounding,
           (SELECT tc.name FROM c_taxcategory tc WHERE tc.c_taxcategory_id = l.c_taxcategory_target_id)          c_taxcategory_target_id
    FROM m_discountschemaline l
    WHERE l.m_discountschema_id = p_M_DiscountSchema_ID
      AND l.isactive = 'Y')
SELECT selectedRecords.seqno::text              seqno,
       selectedRecords.ProductValue             ProductValue,
       selectedRecords.product_name             m_product_id,
       selectedRecords.M_Product_Category_Value M_Product_Category_Value,
       selectedRecords.m_product_category_id    m_product_category_id,
       selectedRecords.BPartnerValue            BPartnerValue,
       selectedRecords.c_bpartner_id            c_bpartner_id,
       selectedRecords.c_taxcategory_id         c_taxcategory_id,
       selectedRecords.std_addamt               std_addamt,
       selectedRecords.std_rounding             std_rounding,
       selectedRecords.c_taxcategory_target_id  c_taxcategory_target_id
FROM (SELECT --
             -- fields which appear in the UI
             l.seqno                    seqno,
             l.product_value            ProductValue,
             l.product_name             product_name,
             l.m_product_category_value M_Product_Category_Value,
             l.m_product_category_id    m_product_category_id,
             l.c_bpartner_value         BPartnerValue,
             l.c_bpartner_id            c_bpartner_id,
             l.c_taxcategory_id         c_taxcategory_id,
             l.std_addamt               std_addamt,
             l.std_rounding             std_rounding,
             l.c_taxcategory_target_id  c_taxcategory_target_id
      FROM exportedLines l

      UNION ALL

      SELECT --
             -- fields which appear in the UI
             ((SELECT max(exportedLines.seqno) FROM exportedLines) + (row_number() OVER ()) * 10) seqno,
             -- this select is needed since seqno must be unique
             p.value                                                                              ProductValue,
             p.name                                                                               product_name,
             NULL                                                                                 M_Product_Category_Value,
             NULL                                                                                 m_product_category_id,
             NULL                                                                                 BPartnerValue,
             NULL                                                                                 c_bpartner_id,
             NULL                                                                                 c_taxcategory_id,
             0                                                                                    std_addamt,
             'C'                                                                                  std_rounding,
             -- this is the default value
             NULL                                                                                 c_taxcategory_target_id
      FROM M_Product p
      WHERE TRUE
        AND p.m_product_id NOT IN (SELECT COALESCE(m_product_id, 0)
                                   FROM exportedLines)
        AND (case when p_isSold = 'Y' THEN p.isSold = 'Y' ELSE 1=1 END)
        AND p.IsActive = 'Y'

        AND p.AD_Org_ID IN (0, (SELECT ad_org_id FROM m_discountschema WHERE m_discountschema_id = p_M_DiscountSchema_ID))
        AND p.AD_Client_ID = (SELECT ad_client_id FROM m_discountschema WHERE m_discountschema_id = p_M_DiscountSchema_ID)) selectedRecords
		
ORDER BY cast(seqno AS numeric)








$BODY$
    LANGUAGE sql STABLE
                 COST 100
                 ROWS 1000;

