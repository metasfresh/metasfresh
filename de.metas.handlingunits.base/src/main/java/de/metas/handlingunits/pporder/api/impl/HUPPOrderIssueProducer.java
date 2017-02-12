package de.metas.handlingunits.pporder.api.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.eevolution.api.IPPOrderBOMDAO;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import de.metas.handlingunits.IDocumentCollector;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.GenericListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.attribute.IPPOrderProductAttributeBL;
import de.metas.handlingunits.impl.DocumentCollector;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_PP_Cost_Collector;
import de.metas.handlingunits.pporder.api.IHUPPOrderIssueProducer;
import de.metas.logging.LogManager;

/**
 * Issues given HUs to configured Order BOM Lines.
 *
 * @author tsa
 *
 */
/* package */class HUPPOrderIssueProducer implements IHUPPOrderIssueProducer
{
	private static final transient Logger logger = LogManager.getLogger(HUPPOrderIssueProducer.class);

	// Services
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

	private final Properties ctx;
	private String trxName = ITrx.TRXNAME_None;
	private Date movementDate;
	private List<I_PP_Order_BOMLine> targetOrderBOMLines;

	public HUPPOrderIssueProducer(final Properties ctx)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		this.ctx = ctx;
	}

	private final Properties getCtx()
	{
		return ctx;
	}

	@Override
	public HUPPOrderIssueProducer setTrxName(final String trxName)
	{
		this.trxName = trxName;
		return this;
	}

	private final String getTrxName()
	{
		return trxName;
	}

	@Override
	public List<I_PP_Cost_Collector> createIssues(final Collection<I_M_HU> hus)
	{
		logger.debug("this-ID={} going to create issues for hus={}; this={}", System.identityHashCode(this), hus, this);

		// Make sure all HUs have ThreadInherited transaction (in order to use caching)
		hus.stream().forEach(hu -> InterfaceWrapperHelper.setTrxName(hu, ITrx.TRXNAME_ThreadInherited));

		//
		// Allocation Source: our HUs
		final HUListAllocationSourceDestination husSource = new HUListAllocationSourceDestination(hus);
		// Ask to create snapshots of HUs because in case we want to revert the Cost Collector, to be able to recover the HUs (08731).
		husSource.setCreateHUSnapshots(true);
		husSource.setDestroyEmptyHUs(true); // get rid of those HUs which got empty

		//
		// Allocation Destination: our BOM Lines
		final IAllocationDestination orderBOMLinesDestination = createBOMLinesAllocationDestination();

		//
		// Create and setup context
		final IDocumentCollector documentsCollector = new DocumentCollector();
		final IContextAware context = PlainContextAware.newWithTrxName(getCtx(), getTrxName());
		final IMutableHUContext huContext = huContextFactory.createMutableHUContextForProcessing(context);
		huContext.setDocumentCollector(documentsCollector);
		huContext.setDate(getMovementDate());

		//
		// Create and configure Loader
		final HULoader loader = new HULoader(husSource, orderBOMLinesDestination);
		loader.setAllowPartialLoads(true);

		//
		// Unload everything from source (our HUs) and move it to manufacturing order BOM lines
		// NOTE: this will also produce the coresponding cost collectors (see de.metas.handlingunits.pporder.api.impl.PPOrderBOMLineHUTrxListener)
		loader.unloadAllFromSource(huContext);

		//
		// Get created cost collectors and set the Snapshot_UUID for later recall, in case of an reversal.
		// task 08177: Also make sure the attributes are saved in the PP_Order_ProductAttributes
		final List<I_PP_Cost_Collector> costCollectors = documentsCollector.getDocuments(I_PP_Cost_Collector.class);

		final String snapshotId = husSource.getSnapshotId();
		for (final I_PP_Cost_Collector costCollector : costCollectors)
		{
			costCollector.setSnapshot_UUID(snapshotId);
			InterfaceWrapperHelper.save(costCollector);

			Services.get(IPPOrderProductAttributeBL.class).addPPOrderProductAttributes(costCollector);
		}

		return costCollectors;
	}

	@Override
	public List<I_PP_Cost_Collector> createIssues(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final Set<I_M_HU> hus = Collections.singleton(hu);
		return createIssues(hus);
	}

	private IAllocationDestination createBOMLinesAllocationDestination()
	{
		final GenericListAllocationSourceDestination destinations = new GenericListAllocationSourceDestination();
		final List<I_PP_Order_BOMLine> targetOrderBOMLines = getTargetOrderBOMLines();
		for (final I_PP_Order_BOMLine orderBOMLine : targetOrderBOMLines)
		{
			final PPOrderBOMLineProductStorage productStorage = new PPOrderBOMLineProductStorage(orderBOMLine);
			final GenericAllocationSourceDestination destination = new GenericAllocationSourceDestination(productStorage, orderBOMLine);
			destinations.addAllocationDestination(destination);
		}

		return destinations;
	}

	@Override
	public IHUPPOrderIssueProducer setMovementDate(final Date movementDate)
	{
		this.movementDate = movementDate;
		return this;
	}

	private Date getMovementDate()
	{
		Check.assumeNotNull(movementDate, "movementDate not null");
		return movementDate;
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLines(final List<I_PP_Order_BOMLine> targetOrderBOMLines)
	{
		this.targetOrderBOMLines = targetOrderBOMLines;
		return this;
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLine(final I_PP_Order_BOMLine targetOrderBOMLine)
	{
		Check.assumeNotNull(targetOrderBOMLine, "targetOrderBOMLine not null");
		return setTargetOrderBOMLines(Collections.singletonList(targetOrderBOMLine));
	}

	@Override
	public IHUPPOrderIssueProducer setTargetOrderBOMLines(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");
		final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
		final List<I_PP_Order_BOMLine> ppOrderBOMLines = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrder, I_PP_Order_BOMLine.class);
		return setTargetOrderBOMLines(ppOrderBOMLines);
	}

	private List<I_PP_Order_BOMLine> getTargetOrderBOMLines()
	{
		Check.assumeNotEmpty(targetOrderBOMLines, "targetOrderBOMLines not empty");
		return targetOrderBOMLines;
	}

	@Override
	public String toString()
	{
		return "HUPPOrderIssueProducer [movementDate=" + movementDate + ", targetOrderBOMLines=" + targetOrderBOMLines + ", trxName=" + trxName + ", huContextFactory=" + huContextFactory + ", ctx=" + ctx + "]";
	}


}
