/*
 * #%L
 * metasfresh-material-cockpit
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

package de.metas.material.cockpit.view.ddorderdetail;

import de.metas.material.cockpit.model.I_MD_Cockpit_DDOrder_Detail;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.IQuery;

@Value
public class DDOrderDetailIdentifier
{
	@NonNull
	public static DDOrderDetailIdentifier createForWarehouseRelatedIdentifier(
			@NonNull final MainDataRecordIdentifier mainDataRecordIdentifier,
			@NonNull final de.metas.material.cockpit.view.DDOrderDetailIdentifier warehouseRelatedIdentifier)
	{
		return new DDOrderDetailIdentifier(mainDataRecordIdentifier, warehouseRelatedIdentifier);
	}

	de.metas.material.cockpit.view.DDOrderDetailIdentifier ddOrderDetailIdentifier;
	MainDataRecordIdentifier mainDataRecordIdentifier;

	private DDOrderDetailIdentifier(
			@NonNull final MainDataRecordIdentifier mainDataRecordIdentifier,
			@NonNull final de.metas.material.cockpit.view.DDOrderDetailIdentifier ddOrderDetailIdentifier)
	{
		this.mainDataRecordIdentifier = mainDataRecordIdentifier;
		this.ddOrderDetailIdentifier = ddOrderDetailIdentifier;
	}

	@NonNull
	public IQuery<I_MD_Cockpit_DDOrder_Detail> createQuery()
	{
		return mainDataRecordIdentifier.createQueryBuilder()
				.andCollectChildren(I_MD_Cockpit_DDOrder_Detail.COLUMN_MD_Cockpit_ID)
				.addEqualsFilter(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_DD_OrderLine_ID, ddOrderDetailIdentifier.getDdOrderLineId())
				.addEqualsFilter(I_MD_Cockpit_DDOrder_Detail.COLUMNNAME_M_Warehouse_ID, ddOrderDetailIdentifier.getWarehouseId())
				.create();
	}
}
