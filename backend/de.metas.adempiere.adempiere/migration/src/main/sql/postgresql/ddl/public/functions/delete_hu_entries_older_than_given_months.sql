/*
 * #%L
 * de.metas.adempiere.adempiere.migration-sql
 * %%
 * Copyright (C) 2022 metas GmbH
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


CREATE OR REPLACE FUNCTION delete_hu_entries_older_than_given_months(
    p_past_months NUMERIC,
    p_m_hu_placeholder_id NUMERIC
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN

    CREATE TEMP TABLE temp_m_hu AS
    SELECT m_hu_id FROM m_hu WHERE hustatus != 'A' AND updated <= now() - (p_past_months || ' month')::INTERVAL;
    CREATE TEMP TABLE temp_m_hu_item AS
    SELECT m_hu_item_id FROM m_hu_item WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    CREATE TEMP TABLE temp_m_inventoryline AS
    SELECT m_inventoryline_id FROM m_inventoryline WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    CREATE TEMP TABLE temp_m_hu_trx_line AS
    SELECT m_hu_trx_line_id FROM m_hu_trx_line WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    --We delete from or update these tables first, because of the fk constrains of other tables
    DELETE FROM m_hu_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_attribute_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_storage_snapshot WHERE m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);
    DELETE FROM m_hu_storage_snapshot WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_picking_candidate WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_pickingslot_trx WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_trx_attribute WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_assignment WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_trace WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_source_hu WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    UPDATE m_receiptschedule_alloc SET m_lu_hu_id = p_m_hu_placeholder_id, m_tu_hu_id = p_m_hu_placeholder_id WHERE m_lu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_tu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);
    UPDATE m_shipmentschedule_qtypicked SET m_lu_hu_id = p_m_hu_placeholder_id, m_tu_hu_id = p_m_hu_placeholder_id WHERE m_lu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR m_tu_hu_id IN (SELECT m_hu_id FROM temp_m_hu) OR vhu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_package_hu SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE m_inventoryline_hu SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE pp_order_productattribute SET m_hu_id = p_m_hu_placeholder_id  WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    UPDATE pp_order_qty SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);


    --Tables below here have fk constrains
    DELETE FROM m_hu_attribute WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_storage WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item_storage WHERE m_hu_item_id IN (SELECT m_hu_item_id FROM temp_m_hu_item);

    --The parents id must be set to null, or else we can't delete the entries
    UPDATE m_hu_trx_line SET parent_hu_trx_line_id = NULL WHERE parent_hu_trx_line_id IN (SELECT m_hu_trx_line_id FROM temp_m_hu_trx_line);
    DELETE FROM m_hu_trx_line WHERE m_hu_trx_line_id IN (SELECT m_hu_trx_line_id FROM temp_m_hu_trx_line);


    UPDATE m_inventoryline SET m_hu_id = p_m_hu_placeholder_id WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu_item WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);
    DELETE FROM m_hu WHERE m_hu_id IN (SELECT m_hu_id FROM temp_m_hu);

    DROP TABLE temp_m_hu;
    DROP TABLE temp_m_hu_item;
    DROP TABLE temp_m_inventoryline;
    DROP TABLE temp_m_hu_trx_line;

END
$$;

COMMENT ON FUNCTION delete_hu_entries_older_than_given_months(p_past_months NUMERIC, p_m_hu_placeholder_id NUMERIC) IS
    'Deletes all handling unit entries that have an inactive status and are older than the specified months. The placeholder is used in other tables linking to the removed handling unit.';