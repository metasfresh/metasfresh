DROP FUNCTION IF EXISTS getTUItemsOnCurrentLUVersions(numeric)
;

CREATE OR REPLACE FUNCTION getTUItemsOnCurrentLUVersions(p_orderline_id NUMERIC)
    RETURNS TABLE
            (
                lu_version_id   NUMERIC,
                lu_name         character varying,
                tu_item_id      NUMERIC,
                tu_version_name character varying,
                qty             NUMERIC
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN QUERY
        SELECT luVersion.m_hu_pi_version_id,
               luVersion.name,
               tuItemOnTUVersion.m_hu_pi_item_id,
               tuVersion.name,
               tuItemOnLUVersion.qty
        FROM M_HU_PI_Item tuItemOnLUVersion
                 JOIN M_HU_PI_Version luVersion ON tuItemOnLUVersion.m_hu_pi_version_id = luVersion.m_hu_pi_version_id
                 JOIN m_hu_pi tuPI ON tuItemOnLUVersion.included_hu_pi_id = tuPI.m_hu_pi_id
                 JOIN m_hu_pi_version tuVersion ON tuPI.m_hu_pi_id = tuVersion.m_hu_pi_id
                 JOIN M_HU_PI_Item tuItemOnTUVersion ON tuItemOnTUVersion.m_hu_pi_version_id = tuVersion.m_hu_pi_version_id
                 JOIN M_HU_PI_Item_Product hupip ON tuItemOnTUVersion.m_hu_pi_item_id = hupip.m_hu_pi_item_id
                 JOIN C_Order o ON o.c_order_id = (SELECT c_order_id FROM C_OrderLine WHERE c_orderline_id = p_orderline_id)
        WHERE luVersion.hu_unittype = 'LU'
          AND luVersion.iscurrent = 'Y'
          AND tuItemOnLUVersion.itemtype = 'HU'
          AND (tuItemOnLUVersion.c_bpartner_id IS NULL OR tuItemOnLUVersion.c_bpartner_id = o.c_bpartner_id)
          AND tuVersion.hu_unittype = 'TU'
          AND tuVersion.iscurrent = 'Y'
          AND tuItemOnTUVersion.itemtype = 'MI'
          AND (tuItemOnTUVersion.c_bpartner_id IS NULL OR tuItemOnTUVersion.c_bpartner_id = o.c_bpartner_id)
          AND hupip.m_hu_pi_item_product_id = (SELECT m_hu_pi_item_product_id
                                               FROM C_OrderLine
                                               WHERE c_orderline_id = p_orderline_id);
END;
$$
;