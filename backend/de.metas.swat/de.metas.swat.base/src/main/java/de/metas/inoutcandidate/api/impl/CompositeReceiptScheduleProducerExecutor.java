package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.AbstractReceiptScheduleProducer;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Executes a group of {@link IReceiptScheduleProducer}s.
 * <p>
 * Takes care to not execute the {@link #createOrUpdateReceiptSchedules(Object, List)} recursivelly in same thread.
 *
 * @author tsa
 */
/* package */final class CompositeReceiptScheduleProducerExecutor extends AbstractReceiptScheduleProducer
{
	/** A set of models that are currently updated */
	private static final transient InheritableThreadLocal<Set<ArrayKey>> currentUpdatingModelKeys = new InheritableThreadLocal<Set<ArrayKey>>()
	{
		@Override
		protected java.util.Set<ArrayKey> initialValue()
		{
			return new HashSet<>();
		};
	};

	private final List<IReceiptScheduleProducer> producers = new ArrayList<IReceiptScheduleProducer>();

	private final GenerateReceiptScheduleForModelAggregateFilter modelAggregateFilter;
	
	public CompositeReceiptScheduleProducerExecutor(@NonNull final GenerateReceiptScheduleForModelAggregateFilter modelAggregateFilter)
	{
		this.modelAggregateFilter = modelAggregateFilter;
	}

	
	
	public void addReceiptScheduleProducer(final IReceiptScheduleProducer producer)
	{
		Check.assumeNotNull(producer, "producer not null");
		producers.add(producer);
	}

	private ArrayKey createModelKey(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final int id = InterfaceWrapperHelper.getId(model);
		return Util.mkKey(tableName, id);
	}

	@Override
	@Nullable
	public List<I_M_ReceiptSchedule> createOrUpdateReceiptSchedules(final Object model, final List<I_M_ReceiptSchedule> previousSchedules)
	{
		if (!modelAggregateFilter.isEligible(model))
		{
			return null;
		}

		final ArrayKey modelKey = createModelKey(model);

		//
		// Add model to current models that are updated.
		// If an update is just running for our model, then we shall skip it because we have to avoid recursions
		if (!currentUpdatingModelKeys.get().add(modelKey))
		{
			return null;
		}

		final List<I_M_ReceiptSchedule> currentPreviousSchedules = new ArrayList<I_M_ReceiptSchedule>(previousSchedules);
		final List<I_M_ReceiptSchedule> currentPreviousSchedulesRO = Collections.unmodifiableList(currentPreviousSchedules);
		final List<I_M_ReceiptSchedule> schedules = new ArrayList<I_M_ReceiptSchedule>();

		try
		{
			// Iterate all producers and ask them to create/update
			for (final IReceiptScheduleProducer producer : producers)
			{
				final List<I_M_ReceiptSchedule> currentSchedules = producer.createOrUpdateReceiptSchedules(model, currentPreviousSchedulesRO);

				// Collect created/updated receipt schedules
				if (currentSchedules != null)
				{
					currentPreviousSchedules.addAll(currentSchedules);
					schedules.addAll(currentSchedules);
				}
			}
		}
		finally
		{
			// Remove current model from the list of models that are currently updated
			currentUpdatingModelKeys.get().remove(modelKey);
		}

		return schedules;
	}

	@Override
	public void updateReceiptSchedules(final Object model)
	{
		for (final IReceiptScheduleProducer producer : producers)
		{
			producer.updateReceiptSchedules(model);
		}
	}

	@Override
	public void inactivateReceiptSchedules(final Object model)
	{
		for (final IReceiptScheduleProducer producer : producers)
		{
			producer.inactivateReceiptSchedules(model);
		}
	}
}
