/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.deliveryplanning;

import com.google.common.collect.ImmutableList;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.SelectionSize;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_M_Delivery_Planning;
import de.metas.util.Services;

import javax.annotation.Nullable;

import java.util.List;
import java.util.stream.Stream;

/**
 * The grid selection a delivery-planning action is launched from, in the shape
 * {@link de.metas.process.IProcessPrecondition#checkPreconditionsApplicable(IProcessPreconditionsContext)} reads it.
 * <p>
 * Exists so a scenario can press a button the way a planner does - through the precondition that decides whether the
 * button is available at all - instead of calling the service the process delegates to. The two answer differently:
 * a rule that lives only in the precondition is invisible to a service-level step, so a scenario driving the service
 * can be green while the product refuses the action outright.
 * <p>
 * Deliberately implements only what the delivery-planning preconditions actually read - the selection size and the
 * selection's query filter. Every other method of the interface throws, so a precondition that starts reading
 * something else (a window, a tab, a single record) fails loudly here rather than silently receiving a stand-in.
 */
@RequiredArgsConstructor
public class DeliveryPlanningSelectionPreconditionsContext implements IProcessPreconditionsContext
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final ImmutableList<Integer> selectedDeliveryPlanningIds;

	@Nullable
	@Override
	public AdWindowId getAdWindowId()
	{
		throw notImplemented("getAdWindowId");
	}

	@Nullable
	@Override
	public AdTabId getAdTabId()
	{
		throw notImplemented("getAdTabId");
	}

	@Override
	public String getTableName()
	{
		return I_M_Delivery_Planning.Table_Name;
	}

	@Nullable
	@Override
	public <T> T getSelectedModel(final Class<T> modelClass)
	{
		throw notImplemented("getSelectedModel");
	}

	@Override
	public <T> List<T> getSelectedModels(final Class<T> modelClass)
	{
		throw notImplemented("getSelectedModels");
	}

	@NonNull
	@Override
	public <T> Stream<T> streamSelectedModels(@NonNull final Class<T> modelClass)
	{
		throw notImplemented("streamSelectedModels");
	}

	@Override
	public int getSingleSelectedRecordId()
	{
		throw notImplemented("getSingleSelectedRecordId");
	}

	@Override
	public SelectionSize getSelectionSize()
	{
		return SelectionSize.ofSize(selectedDeliveryPlanningIds.size());
	}

	@Override
	public <T> IQueryFilter<T> getQueryFilter(@NonNull final Class<T> recordClass)
	{
		return queryBL.createCompositeQueryFilter(recordClass)
				.addInArrayFilter(I_M_Delivery_Planning.COLUMNNAME_M_Delivery_Planning_ID, selectedDeliveryPlanningIds);
	}

	private static UnsupportedOperationException notImplemented(@NonNull final String methodName)
	{
		return new UnsupportedOperationException(
				DeliveryPlanningSelectionPreconditionsContext.class.getSimpleName() + " does not implement " + methodName
						+ " - a delivery-planning precondition started reading it; implement it here rather than stubbing it out");
	}
}
