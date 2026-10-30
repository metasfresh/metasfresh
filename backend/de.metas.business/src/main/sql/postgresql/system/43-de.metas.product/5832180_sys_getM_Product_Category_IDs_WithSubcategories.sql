-- Source DDL: backend/de.metas.business/src/main/sql/postgresql/ddl/functions/getM_Product_Category_IDs_WithSubcategories.sql
DROP FUNCTION IF EXISTS getM_Product_Category_IDs_WithSubcategories(numeric)
;

-- The given product category and all its subcategories (M_Product_Category_Parent_ID), at every level; active or not.
-- Nothing when p_M_Product_Category_ID is NULL. The tree has no loops (MProductCategory.assertNoLoopInTree).
-- Meant for report filters: p.M_Product_Category_ID IN (SELECT getM_Product_Category_IDs_WithSubcategories(p_M_Product_Category_ID))
CREATE FUNCTION getM_Product_Category_IDs_WithSubcategories(p_M_Product_Category_ID numeric)
    RETURNS SETOF numeric
    STABLE
    LANGUAGE sql
AS
$$
WITH RECURSIVE category AS (SELECT c.M_Product_Category_ID
                            FROM M_Product_Category c
                            WHERE c.M_Product_Category_ID = p_M_Product_Category_ID
                            UNION ALL
                            SELECT c.M_Product_Category_ID
                            FROM M_Product_Category c
                                     JOIN category parent ON parent.M_Product_Category_ID = c.M_Product_Category_Parent_ID)
SELECT M_Product_Category_ID
FROM category
$$
;
