/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

DROP FUNCTION IF EXISTS "de.metas.handlingunits".m_hu_get_all_descendant_hus(p_M_HU_ID numeric);
CREATE OR REPLACE FUNCTION "de.metas.handlingunits".m_hu_get_all_descendant_hus(p_M_HU_ID numeric)
    RETURNS setof M_HU
AS $$
WITH RECURSIVE descendants(M_HU_ID) AS (
    -- direct children of the input HU
    SELECT child.M_HU_ID
    FROM   M_HU_Item parent_item
               JOIN   M_HU      child ON child.M_HU_Item_Parent_ID = parent_item.M_HU_Item_ID
    WHERE  parent_item.M_HU_ID = p_M_HU_ID

    UNION ALL

    -- children of children
    SELECT child.M_HU_ID
    FROM   descendants d
               JOIN   M_HU_Item parent_item ON parent_item.M_HU_ID = d.M_HU_ID
               JOIN   M_HU      child       ON child.M_HU_Item_Parent_ID = parent_item.M_HU_Item_ID
)
SELECT hu.*
FROM   M_HU hu
           JOIN   descendants d ON d.M_HU_ID = hu.M_HU_ID;
$$ LANGUAGE sql STABLE;

COMMENT ON FUNCTION "de.metas.handlingunits".m_hu_get_all_descendant_hus(numeric) IS
    'Returns all HUs that are direct or indirect children of the given M_HU_ID, walking
    M_HU.M_HU_Item_Parent_ID -> M_HU_Item -> parent M_HU recursively. Input HU itself is
    NOT included. Covers LU->TU, TU->VHU (both classic via item-type HU and disaggregated
    via item-type MI), and aggregate HU children. Returned rows include all HUStatus / IsActive
    values — caller filters.';