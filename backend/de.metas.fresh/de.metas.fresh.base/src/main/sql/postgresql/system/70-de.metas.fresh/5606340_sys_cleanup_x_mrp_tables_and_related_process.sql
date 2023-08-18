/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2023 metas GmbH
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


delete from ad_table where tablename='X_MRP_ProductInfo_Detail_MV';

delete from ad_user_sortpref_line_product where ad_user_sortpref_line_id IN (select ad_user_sortpref_line_id from ad_user_sortpref_line where ad_user_sortpref_hdr_id IN (select ad_user_sortpref_hdr_id from ad_user_sortpref_hdr where ad_infowindow_id=(select ad_infowindow_id from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V'))));
delete from ad_user_sortpref_line where ad_user_sortpref_hdr_id IN (select ad_user_sortpref_hdr_id from ad_user_sortpref_hdr where ad_infowindow_id=(select ad_infowindow_id from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V')));
delete from ad_user_sortpref_hdr where ad_infowindow_id IN (select ad_infowindow_id from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V'));
delete from ad_infowindow where ad_table_id=get_table_id('X_MRP_ProductInfo_V');
delete from ad_table where tablename='X_MRP_ProductInfo_V';

delete from ad_table_process where ad_process_id in (select ad_process_id from ad_process where value='Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref');
delete from ad_process where value='Fresh_QtyOnHand_UpdateSeqNo_And_Export_SortPref'; 
