DROP FUNCTION IF EXISTS getM_Product_Category_IDs_WithSubcategories(numeric)
;

-- The given product category and all its subcategories (M_Product_Category_Parent_ID), at every level; active or not.
-- Nothing when p_M_Product_Category_ID is NULL. UNION (not UNION ALL) ends the recursion also on a parent loop: MProductCategory.assertNoLoopInTree
-- prevents loops only for saves through the model, not for SQL updates, and a loop would otherwise make every caller hang.
-- Meant for report filters: p.M_Product_Category_ID IN (SELECT getM_Product_Category_IDs_WithSubcategories(p_M_Product_Category_ID))
CREATE FUNCTION getM_Product_Category_IDs_WithSubcategories(p_M_Product_Category_ID numeric)
    RETURNS SETOF numeric
    STABLE
    ROWS 10
    LANGUAGE sql
AS
$$
WITH RECURSIVE category AS (SELECT c.M_Product_Category_ID
                            FROM M_Product_Category c
                            WHERE c.M_Product_Category_ID = p_M_Product_Category_ID
                            UNION
                            SELECT c.M_Product_Category_ID
                            FROM M_Product_Category c
                                     JOIN category parent ON parent.M_Product_Category_ID = c.M_Product_Category_Parent_ID)
SELECT M_Product_Category_ID
FROM category
$$
;
