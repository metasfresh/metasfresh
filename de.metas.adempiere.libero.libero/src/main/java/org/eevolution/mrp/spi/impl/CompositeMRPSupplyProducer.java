package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.spi.IMRPSupplyProducer;

import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.pporder.LiberoException;

public class CompositeMRPSupplyProducer implements IMRPSupplyProducer
{
	private final List<IMRPSupplyProducer> producers = new ArrayList<IMRPSupplyProducer>();
	private final List<IMRPSupplyProducer> producersRO = Collections.unmodifiableList(producers);

	public void addMRPSupplyProducer(final IMRPSupplyProducer supplyProducer)
	{
		if (producers.contains(supplyProducer))
		{
			return;
		}

		producers.add(supplyProducer);
	}

	public List<IMRPSupplyProducer> getAllSupplyProducers()
	{
		return producersRO;
	}

	public List<IMRPSupplyProducer> getSupplyProducers(final String tableName)
	{
		Check.assumeNotEmpty(tableName, LiberoException.class, "tableName not null");

		List<IMRPSupplyProducer> result = null;
		for (final IMRPSupplyProducer producer : producers)
		{
			final Set<String> sourceTableNames = producer.getSourceTableNames();

			if (!sourceTableNames.contains(tableName))
			{
				continue;
			}

			if (result == null)
			{
				result = new ArrayList<IMRPSupplyProducer>();
			}
			result.add(producer);
		}

		if (result == null)
		{
			return Collections.emptyList();
		}
		else
		{
			return result;
		}
	}

	@Override
	public Set<String> getSourceTableNames()
	{
		final Set<String> sourceTableNames = new LinkedHashSet<String>(); // keep the order
		for (final IMRPSupplyProducer producer : producers)
		{
			sourceTableNames.addAll(producer.getSourceTableNames());
		}
		return sourceTableNames;
	}

	@Override
	public boolean applies(final IMaterialPlanningContext mrpContext, final IMutable<String> notAppliesReason)
	{
		final StringBuilder notAppliesReasonBuf = new StringBuilder();
		for (final IMRPSupplyProducer producer : producers)
		{
			final IMutable<String> notAppliesReasonProducer = new Mutable<String>();
			if (producer.applies(mrpContext, notAppliesReasonProducer))
			{
				return true;
			}

			appendNotAppliesReason(notAppliesReasonBuf, notAppliesReasonProducer);
		}

		notAppliesReason.setValue(notAppliesReasonBuf.toString());
		return false;
	}

	private final void appendNotAppliesReason(final StringBuilder notAppliesReasonBuf, final IMutable<String> notAppliesReason)
	{
		if (Check.isEmpty(notAppliesReason.getValue(), true))
		{
			return;
		}

		if (notAppliesReasonBuf.length() > 0)
		{
			notAppliesReasonBuf.append("; ");
		}

		notAppliesReasonBuf.append(notAppliesReason.getValue());
	}

	@Override
	public void onRecordChange(final Object model, final ModelChangeType changeType)
	{
		for (final IMRPSupplyProducer producer : producers)
		{
			producer.onRecordChange(model, changeType);
		}
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		for (final IMRPSupplyProducer producer : producers)
		{
			producer.onDocumentChange(model, timing);
		}
	}

	@Override
	public boolean isRecreatedMRPRecordsSupported(final String tableName)
	{
		for (final IMRPSupplyProducer producer : producers)
		{
			if (producer.isRecreatedMRPRecordsSupported(tableName))
			{
				return true;
			}
		}

		return false;
	}

	@Override
	public void recreateMRPRecords(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);

		for (final IMRPSupplyProducer producer : producers)
		{
			// skip producer if it does not support our tableName
			if (!producer.isRecreatedMRPRecordsSupported(tableName))
			{
				continue;
			}

			producer.recreateMRPRecords(model);
		}
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		for (final IMRPSupplyProducer producer : producers)
		{
			producer.createSupply(request);
		}
	}

	public IMRPSupplyProducer getSupplyProducers(final IMaterialPlanningContext mrpContext)
	{
		final StringBuilder notAppliesReasonBuf = new StringBuilder();

		for (final IMRPSupplyProducer producer : producers)
		{
			final IMutable<String> notAppliesReasonProducer = new Mutable<String>();
			if (producer.applies(mrpContext, notAppliesReasonProducer))
			{
				return producer;
			}

			appendNotAppliesReason(notAppliesReasonBuf, notAppliesReasonProducer);
		}

		throw new IllegalStateException("No MRP supply producer found for " + mrpContext + " because: " + notAppliesReasonBuf.toString());
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor)
	{
		for (final IMRPSupplyProducer producer : producers)
		{
			producer.cleanup(mrpContext, executor);
		}
	}

	@Override
	public void onQtyOnHandReservation(final IMaterialPlanningContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation)
	{
		for (final IMRPSupplyProducer producer : producers)
		{
			producer.onQtyOnHandReservation(mrpContext, mrpExecutor, mrpDemandToSupplyAllocation);
		}
	}

	@Override
	public Class<?> getDocumentClass()
	{
		throw new UnsupportedOperationException();
	}

}
