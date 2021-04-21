DROP INDEX IF EXISTS m_receiptschedule_c_order_id;
CREATE INDEX m_receiptschedule_c_order_id ON m_receiptschedule (c_order_id);


/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2021 metas GmbH
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


-- M_ReceiptSchedule.FilteredItemsWithSameC_Order_ID:
-- Previous ColumnSQL: count(1) over (partition by c_order_id)
update ad_column set columnsql='(select count(1) from M_ReceiptSchedule z where z.C_Order_ID=M_ReceiptSchedule.C_Order_ID)',
                     updatedby=0, updated=TO_TIMESTAMP('2021-02-23 13:05:00','YYYY-MM-DD HH24:MI:SS')
where ad_column_id=571516;

