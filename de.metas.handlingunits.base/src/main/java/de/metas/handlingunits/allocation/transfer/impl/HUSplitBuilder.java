package de.metas.handlingunits.allocation.transfer.impl;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.IHUSplitDefinition;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;

public class HUSplitBuilder implements IHUSplitBuilder
{
	private final IHUContext huContextInitial;

	private I_M_HU _huToSplit;
	private IHUDocumentLine documentLine;

	private I_M_Product cuProduct;
	private BigDecimal _cuQty;
	private I_C_UOM cuUOM;
	private Object cuTrxReferencedModel;

	private BigDecimal cuPerTU;
	private BigDecimal tuPerLU;
	private BigDecimal maxLUToAllocate;

	private I_M_HU_PI_Item tuPIItem;
	private I_M_HU_PI_Item luPIItem;

	private boolean splitOnNoPI;

	public HUSplitBuilder(final Properties ctx)
	{
		this(Services.get(IHUContextFactory.class).createMutableHUContextForProcessing(ctx));
	}

	public HUSplitBuilder(final IHUContext huContextInitial)
	{
		super();

		this.huContextInitial = huContextInitial;
	}

	@Override
	public HUSplitBuilder setHUToSplit(final I_M_HU huToSplit)
	{
		_huToSplit = huToSplit;
		return this;
	}

	private I_M_HU getHUToSplit()
	{
		Check.assumeNotNull(_huToSplit, "huToSplit not null");
		return _huToSplit;
	}

	@Override
	public HUSplitBuilder setDocumentLine(final IHUDocumentLine documentLine)
	{
		this.documentLine = documentLine;
		return this;
	}

	@Override
	public HUSplitBuilder setCUProduct(final I_M_Product product)
	{
		cuProduct = product;
		return this;
	}

	@Override
	public HUSplitBuilder setCUQty(final BigDecimal qty)
	{
		_cuQty = qty;
		return this;
	}

	private BigDecimal getCUQty()
	{
		// TODO: if cuQty is not set we shall calculate it as CU/TU x TU/LU

		Check.assumeNotNull(_cuQty, "cuQty not null");
		return _cuQty;
	}

	@Override
	public HUSplitBuilder setCUUOM(final I_C_UOM uom)
	{
		cuUOM = uom;
		return this;
	}

	@Override
	public HUSplitBuilder setCUTrxReferencedModel(final Object trxReferencedModel)
	{
		cuTrxReferencedModel = trxReferencedModel;
		return this;
	}

	@Override
	public HUSplitBuilder setCUPerTU(final BigDecimal cuPerTU)
	{
		this.cuPerTU = cuPerTU;
		return this;
	}

	@Override
	public HUSplitBuilder setTUPerLU(final BigDecimal tuPerLU)
	{
		this.tuPerLU = tuPerLU;
		return this;
	}

	@Override
	public HUSplitBuilder setMaxLUToAllocate(final BigDecimal maxLUToAllocate)
	{
		this.maxLUToAllocate = maxLUToAllocate;
		return this;
	}

	@Override
	public HUSplitBuilder setTU_M_HU_PI_Item(final I_M_HU_PI_Item tuPIItem)
	{
		this.tuPIItem = tuPIItem;
		return this;
	}

	@Override
	public HUSplitBuilder setLU_M_HU_PI_Item(final I_M_HU_PI_Item luPIItem)
	{
		this.luPIItem = luPIItem;
		return this;
	}

	@Override
	public HUSplitBuilder setSplitOnNoPI(final boolean splitOnNoPI)
	{
		this.splitOnNoPI = splitOnNoPI;
		return this;
	}

	@Override
	public List<I_M_HU> split()
	{
		//
		// Destination: Split HUs
		final ILUTUProducerAllocationDestination destination = createLUTUProducerDestination();

		//
		// Request is configured with full qty on the CU key and upper limits for max split LU/TUs will be handled in the destination
		final Function<IHUContext, IAllocationRequest> splitQtyRequestProvider = huContext -> createSplitAllocationRequest(huContext);

		final List<I_M_HU> splitHUs = HUSplitBuilderCoreEngine.builder()
				.huContextInitital(huContextInitial)
				.huToSplit(getHUToSplit())
				.requestProvider(splitQtyRequestProvider)
				.destination(destination)
				.build()
				.withPropagateHUValues()
				.withDocumentLine(documentLine)
				.withTuPIItem(tuPIItem)
				.performSplit();

		return splitHUs;
	}

	private ILUTUProducerAllocationDestination createLUTUProducerDestination()
	{
		final ILUTUProducerAllocationDestination destination;
		{
			final IHUSplitDefinition splitDefinition = createSplitDefinition(luPIItem, tuPIItem, cuProduct, cuUOM, cuPerTU, tuPerLU, maxLUToAllocate);

			//
			// Create and configure destination
			destination = new LUTUProducerDestination(splitDefinition);

			if (splitOnNoPI)
			{
				destination.setMaxLUs(0); // don't create any LU
				destination.setMaxTUsForRemainingQty(splitDefinition.getTuPerLU().intValueExact()); // create just "qtyTU" TUs
				destination.setCreateTUsForRemainingQty(true); // create TUs for remaining (i.e. whole qty)
			}
			else
			{
				// Don't create TUs for remaining Qty (after all required LUs were created) - see 06049
				destination.setCreateTUsForRemainingQty(false);
			}
		}
		return destination;
	}

	private IHUSplitDefinition createSplitDefinition(
			final I_M_HU_PI_Item luPIItem,
			final I_M_HU_PI_Item tuPIItem,
			final I_M_Product cuProduct,
			final I_C_UOM cuUOM,
			final BigDecimal cuPerTU,
			final BigDecimal tuPerLU,
			final BigDecimal maxLUToAllocate)
	{
		return new HUSplitDefinition(luPIItem, tuPIItem, cuProduct, cuUOM, cuPerTU, tuPerLU, maxLUToAllocate);
	}

	/**
	 * Create an allocation request for the cuQty of the builder
	 *
	 * @param huContext
	 * @param referencedModel referenced model to be used in created request
	 * @return created request
	 */
	private IAllocationRequest createSplitAllocationRequest(final IHUContext huContext)
	{
		final Timestamp date = SystemTime.asTimestamp();
		return AllocationUtils.createQtyRequest(
				huContext,
				cuProduct, // Product
				getCUQty(), // Qty
				cuUOM, // UOM
				date, // Date
				cuTrxReferencedModel // Referenced model, if any
		);
	}
}
