package de.metas.handlingunits.allocation.transfer.impl;

import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;

import com.google.common.base.Preconditions;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * This class is used by {@link HUSplitBuilder} but can also be called from others. It does the "generic" splitting work while HUSplitBuilder has a lot of setters that can help callers in setting up a particular {@link IHUProducerAllocationDestination} which is then passed to this class.
 * If you have your own {@link IAllocationSource}, {@link IAllocationDestination} etc, you can call this class directly.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUSplitBuilderCoreEngine
{
	//
	// Services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHUPIItemProductDAO piipDAO = Services.get(IHUPIItemProductDAO.class);

	private final IHUContext huContext;
	private final I_M_HU huToSplit;
	private final IAllocationRequest request;
	private final IAllocationDestination destination;

	private I_M_HU_PI_Item tuPIItem;

	private IHUDocumentLine documentLine;
	private boolean allowPartialUnloads;

	private HUSplitBuilderCoreEngine(final IHUContext huContext,
			final I_M_HU huToSplit,
			final IAllocationRequest request,
			final IAllocationDestination destination)
	{
		this.huContext = huContext;
		this.huToSplit = huToSplit;
		this.request = request;
		this.destination = destination;
	}

	/**
	 * Creates and returns a new instance. Note that all four paramters are mandatory.
	 * 
	 * @param huContext
	 * @param huToSplit
	 * @param request
	 * @param destination
	 * @return
	 */
	public static HUSplitBuilderCoreEngine of(final IHUContext huContext,
			final I_M_HU huToSplit,
			final IAllocationRequest request,
			final IAllocationDestination destination)
	{
		return new HUSplitBuilderCoreEngine(
				Preconditions.checkNotNull(huContext, "Param 'huContext' may not be null"),
				Preconditions.checkNotNull(huToSplit, "Param 'huToSplit' may not be null"),
				Preconditions.checkNotNull(request, "Param 'request' may not be null"),
				Preconditions.checkNotNull(destination, "Param 'destination' may not be null"));
	}

	/**
	 * If this instance's included {@link #destination} member is a {@link IHUProducerAllocationDestination}, then this method changes if by setting values from the included {@link #huToSplit}.
	 * Otherwise this method does nothing.
	 * 
	 * @return
	 * 
	 * @task 06902: Make sure the split keys inherit the status and locator.
	 */
	public HUSplitBuilderCoreEngine withPropagateHUValues()
	{
		final IHUProducerAllocationDestination huAllocationDestination = destinationCastOrNull();
		if (huAllocationDestination != null)
		{
			huAllocationDestination.setHUStatus(huToSplit.getHUStatus());
			huAllocationDestination.setC_BPartner(huToSplit.getC_BPartner());
			huAllocationDestination.setC_BPartner_Location_ID(huToSplit.getC_BPartner_Location_ID());
			huAllocationDestination.setM_Locator(huToSplit.getM_Locator());
		}

		return this;
	}

	private IHUProducerAllocationDestination destinationCastOrNull()
	{
		if (destination instanceof IHUProducerAllocationDestination)
		{
			return (IHUProducerAllocationDestination)destination;
		}
		return null;
	}

	/**
	 * This method only has an impact if this instance's {@link #destination} is {@link IHUProducerAllocationDestination}.
	 * 
	 * @param documentLine
	 * @return
	 */
	public HUSplitBuilderCoreEngine withDocumentLine(final IHUDocumentLine documentLine)
	{
		this.documentLine = documentLine;
		return this;
	}

	public HUSplitBuilderCoreEngine withTuPIItem(final I_M_HU_PI_Item tuPIItem)
	{
		this.tuPIItem = tuPIItem;
		return this;
	}

	/**
	 * Set a value to be passed to the {@link HULoader#setAllowPartialUnloads(boolean)} when {@link #performSplit()} is done. The default is {@code false} because of backwards compatibility.
	 * 
	 * @param allowPartialUnloads
	 * @return
	 */
	public HUSplitBuilderCoreEngine withAllowPartialUnloads(final boolean allowPartialUnloads)
	{
		this.allowPartialUnloads = allowPartialUnloads;
		return this;
	}

	/**
	 * Note: if this instance's {@link #destination} is {@link IHUProducerAllocationDestination}, you can retrieve the created HUs via {@link IHUProducerAllocationDestination#getCreatedHUs()}.
	 */
	public void performSplit()
	{
		//
		// Source: our handling unit
		final IAllocationSource source = HUListAllocationSourceDestination.of(huToSplit);

		//
		// Perform allocation
		HULoader.of(source, destination)
				.setAllowPartialLoads(true)
				.setAllowPartialUnloads(allowPartialUnloads)
				.load(request);
		// NOTE: we are not checking if everything was fully allocated because we can leave the remaining Qty into initial "huToSplit"

		final IHUProducerAllocationDestination huProducerAllocationDestination = destinationCastOrNull();
		if (huProducerAllocationDestination != null)
		{
			//
			// Get created HUs in contextProvider's transaction
			final List<I_M_HU> createdHUs = huProducerAllocationDestination.getCreatedHUs();

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
		}
		//
		// Destroy empty HUs from huToSplit
		handlingUnitsBL.destroyIfEmptyStorage(huContext, huToSplit);

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
		if (tuPIItem == null)
		{
			return null;
		}
		final I_M_HU_PI_Item_Product piip = piipDAO.retrievePIMaterialItemProduct(tuPIItem, hu.getC_BPartner(), request.getProduct(), SystemTime.asDate());
		return piip;
	}
}
