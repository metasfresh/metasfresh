package de.metas.ui.web.picking;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class PickingViewRepository
{
	private final LookupDataSource warehouseLookup;
	private final LookupDataSource productLookup;

	public PickingViewRepository()
	{
		warehouseLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));

		productLookup = LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setColumnName(I_M_Packageable_V.COLUMNNAME_M_Product_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField));
	}

	public List<PickingRow> retrieveRowsByIds(final Collection<DocumentId> rowIds)
	{
		final Set<Integer> shipmentScheduleIds = rowIds.stream().map(DocumentId::toInt).collect(ImmutableSet.toImmutableSet());
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream(I_M_Packageable_V.class)
				.map(packageable -> createPickingRow(packageable))
				.collect(ImmutableList.toImmutableList());

	}

	private PickingRow createPickingRow(final I_M_Packageable_V packageable)
	{
		final DocumentId rowId = DocumentId.of(packageable.getM_ShipmentSchedule_ID());
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(PickingConstants.WINDOWID_PickingView, rowId);
		return PickingRow.builder()
				.documentPath(documentPath)
				.id(rowId)
				.type(DefaultRowType.Row)
				.processed(false)
				//
				.warehouse(warehouseLookup.findById(packageable.getM_Warehouse_ID()))
				.product(productLookup.findById(packageable.getM_Product_ID()))
				.deliveryDate(packageable.getDeliveryDate())
				.preparationDate(packageable.getPreparationDate())
				.qtyToDeliver(packageable.getQtyToDeliver())
				//
				.build();
	}
}
