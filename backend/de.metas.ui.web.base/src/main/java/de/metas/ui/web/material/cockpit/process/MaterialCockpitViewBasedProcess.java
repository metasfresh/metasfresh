package de.metas.ui.web.material.cockpit.process;

<<<<<<< HEAD
import java.util.stream.Stream;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
=======
import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.ui.web.material.cockpit.MaterialCockpitRow;
import de.metas.ui.web.material.cockpit.MaterialCockpitView;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;

<<<<<<< HEAD
=======
import java.util.Set;
import java.util.stream.Stream;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public abstract class MaterialCockpitViewBasedProcess extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	protected abstract ProcessPreconditionsResolution checkPreconditionsApplicable();

	@Override
	protected MaterialCockpitView getView()
	{
		return MaterialCockpitView.cast(super.getView());
	}

	@Override
	protected Stream<MaterialCockpitRow> streamSelectedRows()
	{
		return super.streamSelectedRows().map(MaterialCockpitRow::cast);
	}
<<<<<<< HEAD
=======

	protected Set<Integer> getSelectedCockpitRecordIdsRecursively()
	{
		final MaterialCockpitView materialCockpitView = getView();

		return materialCockpitView.streamByIds(getSelectedRowIds())
				.flatMap(row -> row.getAllIncludedCockpitRecordIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}

	protected Set<ProductId> getSelectedProductIdsRecursively()
	{
		final MaterialCockpitView materialCockpitView = getView();

		return materialCockpitView.streamByIds(getSelectedRowIds())
				.map(MaterialCockpitRow::getProductId)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
