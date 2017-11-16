package de.metas.ui.web.picking;

import java.util.Set;
import java.util.function.Supplier;

import org.springframework.context.annotation.Configuration;

import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.view.SqlViewFactory;
import de.metas.ui.web.view.descriptor.SqlViewGroupingBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowIdsConverter;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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

@Configuration
public class PickingConfiguration
{
	public PickingConfiguration(@NonNull final SqlViewFactory sqlViewFactory)
	{
		configurePackageablesGrouping(sqlViewFactory);
	}

	private void configurePackageablesGrouping(@NonNull final SqlViewFactory sqlViewFactory)
	{
		final Supplier<SqlViewGroupingBinding> supplier = () -> SqlViewGroupingBinding.builder()
				.groupBy(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID)
				.groupBy(I_M_Packageable_V.COLUMNNAME_M_Product_ID)
				.columnSql(I_M_Packageable_V.COLUMNNAME_QtyToDeliver, "SUM(QtyToDeliver)")
				.columnSql(I_M_Packageable_V.COLUMNNAME_QtyPickedPlanned, "SUM(QtyPickedPlanned)")
				.columnSql(I_M_Packageable_V.COLUMNNAME_DeliveryDate, "MIN(DeliveryDate)")
				.columnSql(I_M_Packageable_V.COLUMNNAME_PreparationDate, "IF_MIN(DeliveryDate, PreparationDate)")
				.rowIdsConverter(new PackageableSqlViewRowIdsConverter())
				.build();

		sqlViewFactory.registerGroupingSupplier(PickingConstants.WINDOWID_PackageableView, supplier);
	}

	private static final class PackageableSqlViewRowIdsConverter implements SqlViewRowIdsConverter
	{
		@Override
		public Set<Integer> convertToRecordIds(final DocumentIdsSelection rowIds)
		{
			// filter out detail rows because this converter is mainly used to build the AD_PInstance.WhereClause
			return rowIds.stream()
					.filter(DocumentId::isInt) // filter out detail rows
					.map(DocumentId::toInt)
					.collect(ImmutableSet.toImmutableSet());
		}
	}
}
