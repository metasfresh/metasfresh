DROP FUNCTION IF EXISTS de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric,
                                                                                         numeric,
                                                                                         numeric)
;

CREATE OR REPLACE FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(
    IN p_M_Product_ID              numeric,
    IN p_C_BPartner_ID             numeric,
    IN p_M_AttributeSetInstance_ID numeric = NULL)
    RETURNS TABLE
            (
                ProductNo             character varying,
                ProductName           character varying,
                bpp_asi_id            numeric,
                C_BPartner_Product_ID numeric
            )
AS
$BODY$
DECLARE
    bpartnerProductAttributeInstances character varying[];
    requiredAttributeInstances        character varying[];
BEGIN
    --
    -- Iterate C_BPartner_Product table
    FOR ProductNo, ProductName, bpp_asi_id, C_BPartner_Product_ID IN
        (SELECT DISTINCT ON (bpp.M_AttributeSetInstance_ID, bpp.ProductNo, bpp.ProductName) bpp.ProductNo,
                                                                                            bpp.ProductName,
                                                                                            COALESCE(bpp.m_attributesetinstance_id, 0),
                                                                                            bpp.C_BPartner_Product_ID
         FROM C_BPartner_Product bpp
         WHERE TRUE
           AND bpp.M_Product_ID = p_M_Product_ID
           AND bpp.C_BPartner_ID = p_C_BPartner_ID
         ORDER BY bpp.M_AttributeSetInstance_ID DESC NULLS LAST, bpp.ProductNo, bpp.ProductName, bpp.SeqNo)
        LOOP
            IF (bpp_asi_id <= 0) THEN
                --
                -- Case: C_BPartner_Record has no specific attributes
                -- => perfect match
                RETURN NEXT;
                RETURN;
            ELSE
                --
                -- Case: C_BPartner_Record has specific attributes
                -- => check it

                -- Fetch C_BPartner_Product attributes
                SELECT COALESCE(asi.attributeinstances, ARRAY []::character varying[])
                INTO bpartnerProductAttributeInstances
                FROM M_AttributeSetInstance_ID_AttributeInstances asi
                WHERE asi.m_attributesetinstance_id = bpp_asi_id;

                -- Fetch required attributes
                IF (requiredAttributeInstances IS NULL) THEN
                    IF (COALESCE(p_M_AttributeSetInstance_ID, 0) > 0) THEN
                        SELECT COALESCE(asi.attributeinstances, ARRAY []::character varying[])
                        INTO requiredAttributeInstances
                        FROM M_AttributeSetInstance_ID_AttributeInstances asi
                        WHERE asi.m_attributesetinstance_id = p_M_AttributeSetInstance_ID;
                    ELSE
                        requiredAttributeInstances := ARRAY []::character varying[];
                    END IF;
                END IF;

                -- Consider it as a match if all C_BPartner_Product attributes are contained in our required ASI.
                IF (requiredAttributeInstances @> bpartnerProductAttributeInstances) THEN
                    RETURN NEXT;
                    RETURN;
                END IF;
            END IF;
        END LOOP;

    --
    RETURN;
END;
$BODY$
    LANGUAGE plpgsql STABLE
;

COMMENT ON FUNCTION de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(numeric, numeric, numeric)
    IS 'This function returns a C_BPartner_Product record with a matching given product-Id, bpartner-Id and p_M_AttributeSetInstance_ID.
For the attribute-set-instance-id to match, its array as returned by the view M_AttributeSetInstance_ID_AttributeInstances 
needs to be included in the given p_M_AttributeSetInstance_ID''s M_AttributeSetInstance_ID_AttributeInstances-array.
C_BPartner_Product records that have no M_AttributeSetInstance_ID match every p_M_AttributeSetInstance_ID.
If multiple C_BPartner_Product records match, the one with the lowest SeqNo is returned.

Also see 
view M_AttributeSetInstance_ID_AttributeInstances and
issue https://github.com/metasfresh/metasfresh/issues/4795'
;


/* TEST:
SELECT bpp.m_product_id,
       bpp.c_bpartner_id,
       bpp.m_attributesetinstance_id,
       bpp.productno,
       bpp.productname,
       d.*
FROM c_bpartner_product bpp
         LEFT OUTER JOIN de_metas_endcustomer_fresh_reports.getC_BPartner_Product_Details(m_product_id, c_bpartner_id, m_attributesetinstance_id) d ON 1 = 1
WHERE TRUE
  -- AND bpp.m_product_id = 2006673
  AND bpp.productno != d.productno
  ;
 */

