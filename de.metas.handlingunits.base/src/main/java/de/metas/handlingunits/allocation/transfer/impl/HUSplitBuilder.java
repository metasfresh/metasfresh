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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.IHUSplitDefinition;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public class HUSplitBuilder implements IHUSplitBuilder
{
	//
	// Services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHUPIItemProductDAO piipDAO = Services.get(IHUPIItemProductDAO.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

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
		final List<I_M_HU> splitHUs = new ArrayList<I_M_HU>();

		huTrxBL.createHUContextProcessorExecutor(huContextInitial)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext0)
					{
						// Make a copy of the processing context, we will need to modify it
						final IMutableHUContext huContext = huContext0.copyAsMutable();

						// Register our split HUTrxListener so that the other listeners' onSplit() methods will be called
						huContext.getTrxListeners().addListener(HUSplitBuilderTrxListener.instance);

						// Perform the actual split
						final List<I_M_HU> splitHUsInTrx = split0(huContext);

						//
						// Make created HUs to be out-of-transaction to be used elsewhere
						for (final I_M_HU splitHU : splitHUsInTrx)
						{
							InterfaceWrapperHelper.setTrxName(splitHU, ITrx.TRXNAME_None);
							splitHUs.add(splitHU);
						}

						return NULL_RESULT; // we don't care about the result
					}
				});

		return splitHUs;
	}

	private List<I_M_HU> split0(final IHUContext huContext)
	{
		final I_M_HU huToSplit = getHUToSplit();

		//
		//using thread-inherited to let this split key work in transaction and out of transaction
		InterfaceWrapperHelper.setTrxName(huToSplit, ITrx.TRXNAME_ThreadInherited);

		//
		// Request is configured with full qty on the CU key and upper limits for max split LU/TUs will be handled in the destination
		final IAllocationRequest splitQtyRequest = createSplitAllocationRequest(huContext);

		//
		// Source: our handling unit
		final IAllocationSource source = createSplitAllocationSource();

		//
		// Destination: Split HUs
		final ILUTUProducerAllocationDestination destination;
		{
			final IHUSplitDefinition splitDefinition = createSplitDefinition(luPIItem, tuPIItem, cuProduct, cuUOM, cuPerTU, tuPerLU, maxLUToAllocate);

			//
			// Create and configure destination
			destination = new LUTUProducerDestination(splitDefinition);

			// 06902: Make sure the split keys inherit the status and locator.
			destination.setHUStatus(huToSplit.getHUStatus());
			destination.setC_BPartner(huToSplit.getC_BPartner());
			destination.setC_BPartner_Location_ID(huToSplit.getC_BPartner_Location_ID());
			destination.setM_Locator(huToSplit.getM_Locator());
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

		//
		// Perform allocation
		final HULoader loader = new HULoader(source, destination);
		loader.setAllowPartialLoads(true);
		loader.load(splitQtyRequest);
		// NOTE: we are not checking if everything was fully allocated because we can leave the remaining Qty into initial "huToSplit"

		//
		// Get created HUs in contextProvider's transaction
		final List<I_M_HU> createdHUs = destination.getCreatedHUs();

		//
		// Transfer PI Item Product from HU to split to all HUs that we created
		setM_HU_PI_Item_Product(huToSplit, createdHUs);

		//
		// Assign createdHUs to documentLine
		// NOTE: Even if IHUTrxListener implementations are already assigning HUs to documents,
		// the top level HUs (i.e. LUs) are not assigned because only those HUs on which it were material transactions are detected
		if (documentLine != null)
		{
			documentLine.getHUAllocations().addAssignedHUs(createdHUs);
		}

		//
		// Destroy empty HUs from huToSplit
		handlingUnitsBL.destroyIfEmptyStorage(huContext, huToSplit);

		return createdHUs;
	}

	/**
	 * Set HU PIIP in-depth, recursively
	 *
	 * @param sourceHU
	 * @param husToConfigure
	 */
	private void setM_HU_PI_Item_Product(final I_M_HU sourceHU, final List<I_M_HU> husToConfigure)
	{
		final I_M_HU_PI_Item_Product piip = getM_HU_PI_Item_ProductToUse(sourceHU);
		if (piip == null)
		{
			return;
		}

		for (final I_M_HU hu : husToConfigure)
		{
			if (handlingUnitsBL.isLoadingUnit(hu))
			{
				setM_HU_PI_Item_Product(sourceHU, handlingUnitsDAO.retrieveIncludedHUs(hu));
				continue;
			}
			else if (handlingUnitsBL.isTransportUnit(hu))
			{
				setM_HU_PI_Item_Product(sourceHU, handlingUnitsDAO.retrieveIncludedHUs(hu));
			}

			if (hu.getM_HU_PI_Item_Product_ID() > 0)
			{
				continue;
			}

			hu.setM_HU_PI_Item_Product(piip);
			InterfaceWrapperHelper.save(hu);
		}
	}

	private I_M_HU_PI_Item_Product getM_HU_PI_Item_ProductToUse(final I_M_HU hu)
	{
		final I_M_HU_PI_Item_Product piip = piipDAO.retrievePIMaterialItemProduct(tuPIItem, hu.getC_BPartner(), cuProduct, SystemTime.asDate());
		return piip;
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

	private IAllocationSource createSplitAllocationSource()
	{
		final I_M_HU huToSplit = getHUToSplit();
		return new HUListAllocationSourceDestination(huToSplit);
	}
}
