package de.metas.ui.web.picking.packageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProvider.LookupScope;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSource;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import lombok.NonNull;

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

/**
 * Class to retrieve the rows shown in {@link PackageableView}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Component
public class PackageableViewRepository
{
	private final Supplier<LookupDataSource> orderLookup;
	private final Supplier<LookupDataSource> productLookup;
	private final Supplier<LookupDataSource> bpartnerLookup;

	public PackageableViewRepository()
	{
		// creating those LookupDataSources requires DB access. So, to allow this component to be initialized early during startup
		// and also to allow it to be unit-tested (when the lookups are not part of the test), I use those suppliers.

		orderLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setCtxTableName(null)
				.setCtxColumnName(I_M_Packageable_V.COLUMNNAME_C_Order_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField)));

		productLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setCtxTableName(null)
				.setCtxColumnName(I_M_Packageable_V.COLUMNNAME_M_Product_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField)));

		bpartnerLookup = Suppliers.memoize(() -> LookupDataSourceFactory.instance.getLookupDataSource(SqlLookupDescriptor.builder()
				.setCtxTableName(null)
				.setCtxColumnName(I_M_Packageable_V.COLUMNNAME_C_BPartner_ID)
				.setDisplayType(DisplayType.Search)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.buildProvider()
				.provideForScope(LookupScope.DocumentField)));
	}

	private List<PackageableRow> retrieveRowsByShipmentScheduleIds(final ViewId viewId, final Set<Integer> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Packageable_V.class)
				.addInArrayFilter(I_M_Packageable_V.COLUMN_M_ShipmentSchedule_ID, shipmentScheduleIds)
				.create()
				.stream(I_M_Packageable_V.class)
				.map(packageable -> createPickingRow(viewId, packageable))
				.collect(ImmutableList.toImmutableList());
	}

	private PackageableRow createPickingRow(final ViewId viewId, final I_M_Packageable_V packageable)
	{
		final BigDecimal qtyPicked = packageable.getQtyPicked().add(packageable.getQtyPickedPlanned());
		
		return PackageableRow.builder()
				.shipmentScheduleId(packageable.getM_ShipmentSchedule_ID())
				.viewId(viewId)
				//
				.order(orderLookup.get().findById(packageable.getC_Order_ID()))
				.product(productLookup.get().findById(packageable.getM_Product_ID()))
				.bpartner(bpartnerLookup.get().findById(packageable.getC_BPartner_ID()))
				.preparationDate(packageable.getPreparationDate())
				.qtyOrdered(packageable.getQtyOrdered())
				.qtyPicked(qtyPicked)
				//
				.build();
	}

	public PackageableRowsData createRowsData(
			@NonNull final ViewId viewId,
			@NonNull final Set<Integer> shipmentScheduleIds)
	{
		if (shipmentScheduleIds.isEmpty())
		{
			return PackageableRowsData.EMPTY;
		}

		final Set<Integer> shipmentScheduleIdsCopy = ImmutableSet.copyOf(shipmentScheduleIds);
		return PackageableRowsData.ofSupplier(() -> retrieveRowsByShipmentScheduleIds(viewId, shipmentScheduleIdsCopy));
	}
}
