package de.metas.handlingunits.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.drp.api.IDistributionNetworkDAO;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.slf4j.Logger;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUDisplayNameBuilder;
import de.metas.handlingunits.IHUIterator;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_Status;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.movement.api.IEmptiesMovementBuilder;
import de.metas.handlingunits.movement.api.impl.EmptiesMovementBuilder;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.impl.DefaultHUStorageFactory;
import de.metas.logging.LogManager;

public class HandlingUnitsBL implements IHandlingUnitsBL
{
	private final Logger logger = LogManager.getLogger(getClass());

	private final IHUStorageFactory storageFactory = new DefaultHUStorageFactory();

	@Override
	public IHUStorageFactory getStorageFactory()
	{
		return storageFactory;
	}

	@Override
	public IMutableHUContext createMutableHUContext(final IContextAware contextProvider)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		return huContextFactory.createMutableHUContext(contextProvider);
	}

	@Override
	public IMutableHUContext createMutableHUContextForProcessing(final IContextAware contextProvider)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		return huContextFactory.createMutableHUContextForProcessing(contextProvider);
	}

	@Override
	public IMutableHUContext createMutableHUContext(final Properties ctx)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		return huContextFactory.createMutableHUContext(ctx);
	}

	@Override
	public IMutableHUContext createMutableHUContext(final Properties ctx, final String trxName)
	{
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		return huContextFactory.createMutableHUContext(ctx, trxName);
	}

	@Override
	public boolean isConcretePI(final int piId)
	{
		if (piId <= 0)
		{
			return false;
		}
		final IHandlingUnitsDAO dao = Services.get(IHandlingUnitsDAO.class);
		if (piId == dao.getNo_HU_PI_ID())
		{
			return false;
		}
		if (piId == dao.getVirtual_HU_PI_ID())
		{
			return false;
		}

		return true;
	}

	@Override
	public I_C_UOM getHandlingUOM(final I_M_Product product)
	{
		// FIXME: not sure that is correct
		return product.getC_UOM();
	}

	@Override
	public I_C_UOM getC_UOM(final I_M_Transaction mtrx)
	{
		return getHandlingUOM(mtrx.getM_Product());
	}

	@Override
	public boolean isListContainsCurrentVersion(final List<I_M_HU_PI_Version> versions)
	{
		for (final I_M_HU_PI_Version version : versions)
		{
			if (version.isCurrent())
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean destroyIfEmptyStorage(final I_M_HU huToDestroy)
	{
		//
		// Create HU Context
		final IContextAware context = InterfaceWrapperHelper.getContextAware(huToDestroy);
		final IMutableHUContext huContextInitial = Services.get(IHUContextFactory.class).createMutableHUContext(context);
		// huContextInitial.setDate(SystemTime.asDate()); // use the default date set when HUContext was created

		//
		// Try to destroy given HU (or some of it's children)
		final IMutable<Boolean> destroyed = new Mutable<>(false);
		Services.get(IHUTrxBL.class).createHUContextProcessorExecutor(huContextInitial)
				.run(new IHUContextProcessor()
				{
					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						if (destroyIfEmptyStorage(huContext, huToDestroy))
						{
							destroyed.setValue(true);
						}
						return null;
					}
				});

		return destroyed.getValue();
	}

	@Override
	public boolean destroyIfEmptyStorage(final IHUContext huContext, final I_M_HU huToDestroy)
	{
		Check.assumeNotNull(huContext, "huContext not null");
		Check.assumeNotNull(huToDestroy, "hu not null");
		Services.get(ITrxManager.class).assertTrxNotNull(huContext);

		// Service(s)
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

		//
		// Check given HU and also iterate downstream and destroy all HUs which are empty
		final HUIterator iterator = new HUIterator();
		iterator.setHUContext(huContext);
		iterator.setEnableStorageIteration(false); // we don't care about storages, only HU hierarchy is important for us
		iterator.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result afterHU(final I_M_HU currentHU)
			{
				final IHUIterator huIterator = getHUIterator();
				final IHUStorageFactory storageFactory = huIterator.getStorageFactoryToUse();
				final IHUStorage storage = storageFactory.getStorage(currentHU);
				if (storage.isEmpty())
				{
					//
					// In case we current HU is the one that we got as parameter (i.e. huToDestroy)
					// then we need to check if parent got empty after our removal and in that case, destroy it.
					// else do nothing because destroying is performed recursivelly here.
					final boolean destroyOldParentIfEmptyStorage;
					if (huIterator.getDepth() == IHUIterator.DEPTH_STARTING_HU)
					{
						destroyOldParentIfEmptyStorage = true;
					}
					else
					{
						destroyOldParentIfEmptyStorage = false;
					}

					//
					// Take out currentHU from it's parent
					huTrxBL.setParentHU(huContext,
							null, // New Parent = null
							currentHU, // HU which we are changing
							destroyOldParentIfEmptyStorage);
					//
					// Mark current HU as destroyed
					markDestroyed(huContext, currentHU);
				}

				return Result.CONTINUE;
			}

		});

		iterator.iterateInTrx(Collections.singleton(huToDestroy));

		return isDestroyed(huToDestroy);
	}

	@Override
	public void markDestroyed(final IHUContext huContext, final I_M_HU hu)
	{
		setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Destroyed);
		hu.setIsActive(false);
		Services.get(IHandlingUnitsDAO.class).saveHU(hu);
	}

	@Override
	public void markDestroyed(final IHUContext huContext, final Collection<I_M_HU> hus)
	{
		// services
		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		huTrxBL.createHUContextProcessorExecutor(huContext)
				.run(new IHUContextProcessor()
				{

					@Override
					public IMutableAllocationResult process(final IHUContext huContext)
					{
						for (final I_M_HU hu : hus)
						{
							if (isDestroyedRefreshFirst(hu))
							{
								continue;
							}

							markDestroyed(huContext, hu);
						}

						return NULL_RESULT;
					}
				});
	}

	@Override
	public boolean isDestroyed(final I_M_HU hu)
	{
		if (!hu.isActive())
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isDestroyedRefreshFirst(final I_M_HU hu) throws AdempiereException
	{
		Check.assume(!InterfaceWrapperHelper.hasChanges(hu), "hu was saved: {}", hu);
		InterfaceWrapperHelper.refresh(hu);

		return isDestroyed(hu);
	}

	@Override
	public String getDisplayName(final I_M_HU hu)
	{
		// NOTE: please keep this implementation as light as possible

		return buildDisplayName(hu)
				.build();
	}

	@Override
	public IHUDisplayNameBuilder buildDisplayName(final I_M_HU hu)
	{
		return new HUDisplayNameBuilder(hu);
	}

	@Override
	public boolean isVirtual(final I_M_HU hu)
	{
		if (hu == null)
		{
			return false;
		}

		final I_M_HU_PI_Version piVersion = hu.getM_HU_PI_Version();
		if (piVersion == null)
		{
			return false;
		}

		final int piId = piVersion.getM_HU_PI_ID();
		if (piId == Services.get(IHandlingUnitsDAO.class).getVirtual_HU_PI_ID())
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isVirtual(final I_M_HU_PI huPI)
	{
		if (huPI == null)
		{
			return false;
		}

		final int piId = huPI.getM_HU_PI_ID();
		if (piId == Services.get(IHandlingUnitsDAO.class).getVirtual_HU_PI_ID())
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isNoPI(final I_M_HU_PI_Item piItem)
	{
		if (piItem == null)
		{
			return true;
		}

		final int piItemId = piItem.getM_HU_PI_Item_ID();
		if (piItemId == Services.get(IHandlingUnitsDAO.class).getNo_HU_PI_Item_ID())
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isLoadingUnit(final I_M_HU hu)
	{
		if (hu == null)
		{
			return false;
		}

		final I_M_HU_PI_Version piVersion = hu.getM_HU_PI_Version();
		if (piVersion == null)
		{
			return false;
		}

		return X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit.equals(piVersion.getHU_UnitType());
	}

	@Override
	public void assertLoadingUnit(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		if (isLoadingUnit(hu))
		{
			return;
		}

		final String huUnitType = getHU_UnitType(hu);
		final String huUnitTypeStr = Check.isEmpty(huUnitType, true) ? "not set" : huUnitType;
		throw new HUException("HU " + getDisplayName(hu) + " shall be a Loading Unit(LU) but it is '" + huUnitTypeStr + "'");
	}

	@Override
	public boolean isTransportUnit(final I_M_HU hu)
	{
		final boolean strict = true; // consider TU only what is TU only
		return isTransportUnit(hu, strict);
	}

	@Override
	public boolean isTransportUnitOrVirtual(final I_M_HU hu)
	{
		final boolean strict = false; // consider VirtualHU as TU too
		return isTransportUnit(hu, strict);
	}

	private final boolean isTransportUnit(final I_M_HU hu, final boolean strict)
	{
		if (hu == null)
		{
			return false;
		}

		final I_M_HU_PI_Version piVersion = hu.getM_HU_PI_Version();
		if (piVersion == null)
		{
			return false;
		}

		//
		// Case: given HU is a pure TU
		final String huUnitType = piVersion.getHU_UnitType();
		if (X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit.equals(huUnitType))
		{
			return true;
		}

		// If we are asked to stricly validate TUs and we reached this point, well our HU is not a TU
		if (strict)
		{
			return false;
		}

		// We can consider Virtual HUs as a substitute for TUs
		if (isVirtual(hu))
		{
			return true;
		}

		return false;
	}

	@Override
	public String getHU_UnitType(final I_M_HU_PI pi)
	{
		Check.assumeNotNull(pi, "pi not null");
		final I_M_HU_PI_Version piVersion = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(pi);
		if (piVersion == null)
		{
			return null;
		}

		final String huUnitType = piVersion.getHU_UnitType();
		return huUnitType;
	}

	@Override
	public String getHU_UnitType(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final I_M_HU_PI_Version piVersion = hu.getM_HU_PI_Version();
		final String huUnitType = piVersion.getHU_UnitType();
		return huUnitType;
	}

	@Override
	public boolean isVirtual(final I_M_HU_Item huItem)
	{
		if (huItem == null)
		{
			return false;
		}

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		if (huItem.getM_HU_PI_Item_ID() == handlingUnitsDAO.getVirtual_HU_PI_Item_ID())
		{
			return true;
		}

		return false;
	}

	@Override
	public boolean isPureVirtual(final I_M_HU_Item huItem)
	{
		if (!isVirtual(huItem))
		{
			return false;
		}

		final I_M_HU parentHU = huItem.getM_HU();
		if (parentHU == null || parentHU.getM_HU_ID() <= 0)
		{
			return false;
		}

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU_Item parentItem = handlingUnitsDAO.retrieveParentItem(parentHU);
		if (parentItem == null || parentItem.getM_HU_Item_ID() <= 0)
		{
			return false;
		}

		final String parentItemType = getItemType(parentItem);
		if (!X_M_HU_PI_Item.ITEMTYPE_Material.equals(parentItemType))
		{
			return false;
		}

		return true;

	}

	@Override
	public boolean isPureVirtual(final I_M_HU hu)
	{
		final boolean isVirtualHU = isVirtual(hu);
		if (!isVirtualHU)
		{
			return false;
		}

		final List<I_M_HU_Item> huItems = Services.get(IHandlingUnitsDAO.class).retrieveItems(hu);
		if (huItems.size() == 0)
		{
			return false; // we don't care
		}

		//
		// HU must be fully pure virtual (for all of it's items)
		for (final I_M_HU_Item virtualItemCandidate : huItems)
		{
			if (!isPureVirtual(virtualItemCandidate))
			{
				return false;
			}
		}

		return true;
	}

	@Override
	public String getItemType(final I_M_HU_Item huItem)
	{
		Check.assumeNotNull(huItem, "huItem not null");
		final String itemType = huItem.getItemType();
		if (itemType != null)
		{
			return itemType;
		}

		// FIXME: remove it after we migrate all HUs
		return huItem.getM_HU_PI_Item().getItemType();
	}

	@Override
	public boolean isTopLevel(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");
		final boolean topLevel = hu.getM_HU_Item_Parent_ID() <= 0;
		return topLevel;
	}

	@Override
	public I_M_HU getTopLevelParent(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Navigate maximum maxDepth levels and try to get the top level parent
		final int maxDepth = 100;
		I_M_HU parent = hu;
		for (int i = 0; i < maxDepth; i++)
		{
			final I_M_HU parentNew = handlingUnitsDAO.retrieveParent(parent);
			if (parentNew == null)
			{
				return parent;
			}
			parent = parentNew;
		}

		throw new IllegalStateException("We navigated more then " + maxDepth + " levels deep to get the top level of " + hu + ". It seems like a data error");
	}

	@Override
	public List<I_M_HU> getTopLevelHUs(final List<I_M_HU> hus)
	{
		Check.assumeNotNull(hus, "hus not null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final Set<Integer> seenM_HU_IDs = new HashSet<>();
		final List<I_M_HU> husTopLevel = new ArrayList<>();

		for (final I_M_HU hu : hus)
		{
			I_M_HU parent = hu;

			while (parent != null && seenM_HU_IDs.add(parent.getM_HU_ID()))
			{
				final I_M_HU parentNew = handlingUnitsDAO.retrieveParent(parent);
				if (parentNew == null)
				{
					husTopLevel.add(parent);
				}

				parent = parentNew;
			}
		}

		return husTopLevel;
	}

	@Override
	public LUTUCUPair getTopLevelParentAsLUTUCUPair(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		//
		// Get the LU, TU and VHU
		final I_M_HU luHU;
		final I_M_HU tuHU;
		final I_M_HU vhu;
		if (isVirtual(hu))
		{
			vhu = hu;
			tuHU = getTransportUnitHU(vhu);
			luHU = getLoadingUnitHU(tuHU);
		}
		else if (isTransportUnit(hu))
		{
			vhu = null;
			tuHU = hu;
			luHU = getLoadingUnitHU(tuHU);
		}
		else
		{
			vhu = null;
			tuHU = null;
			luHU = hu;
			assertLoadingUnit(luHU); // just to be sure
		}

		return new LUTUCUPair(luHU, tuHU, vhu);
	}

	@Override
	public I_M_HU getLoadingUnitHU(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		I_M_HU lastLU = null;
		I_M_HU currentHU = hu;
		int iterationsCount = 0;
		while (currentHU != null)
		{
			//
			// Avoid cycles
			if (iterationsCount >= 100)
			{
				throw new IllegalStateException("We navigated " + iterationsCount + " levels deep to get the top level LU of " + hu + ". It seems like a data error");
			}
			iterationsCount++;

			//
			// If current HU is a Loading Unit, remember it
			if (isLoadingUnit(currentHU))
			{
				lastLU = currentHU;
			}

			//
			// Navigate one level up
			currentHU = handlingUnitsDAO.retrieveParent(currentHU);
		}

		// Make sure our LU does not have a parent
		// NOTE: shall not happen, but if it does have a parent it seems to be a configuration issue/data inconsistency
		if (lastLU != null && lastLU.getM_HU_Item_Parent_ID() > 0)
		{
			final AdempiereException ex = new AdempiereException("While searching for LU of " + hu + " we found an LU which is included in another handling unit."
					+ "\nThis shall not happen."
					+ "\nWe consider our LU to be NULL.");
			logger.warn(ex.getLocalizedMessage(), ex);

			lastLU = null;
		}

		return lastLU;
	}

	@Override
	public I_M_HU getTransportUnitHU(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		I_M_HU lastTU = null;
		I_M_HU currentHU = hu;
		int iterationsCount = 0;
		while (currentHU != null)
		{
			//
			// Avoid cycles
			if (iterationsCount >= 100)
			{
				throw new IllegalStateException("We navigated " + iterationsCount + " levels deep to get the TU of " + hu + ". It seems like a data error.");
			}
			iterationsCount++;

			//
			// If current HU is a Transport Unit or VirtualHU, remember it
			if (isTransportUnitOrVirtual(currentHU))
			{
				lastTU = currentHU;
			}

			//
			// Navigate one level up
			currentHU = handlingUnitsDAO.retrieveParent(currentHU);
		}

		return lastTU;
	}

	@Override
	public boolean isPhysicalHU(final String huStatus)
	{
		if (huStatus == null)
		{
			return false;
		}
		if (X_M_HU.HUSTATUS_Destroyed.equals(huStatus))
		{
			return false;
		}

		if (X_M_HU.HUSTATUS_Planning.equals(huStatus))
		{
			return false;
		}

		if (X_M_HU.HUSTATUS_Shipped.equals(huStatus))
		{
			return false;
		}

		// we consider the rest of the statuses to be physical
		// (Active and picked)
		return true;
	}

	@Override
	public boolean isAggregateHU(final I_M_HU hu)
	{
		if (hu == null)
		{
			return false;
		}

		final I_M_HU_Item parentItem = hu.getM_HU_Item_Parent();
		if (parentItem == null)
		{
			return false;
		}

		return X_M_HU_Item.ITEMTYPE_HUAggregate.equals(parentItem.getItemType());
	}
	
	@Override
	public I_M_Warehouse getEmptiesWarehouse(final Properties ctx, final I_M_Warehouse warehouse, final String trxName)
	{
		Check.assumeNotNull(warehouse, "warehouse not null");

		// services
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final IDistributionNetworkDAO distributionNetworkDAO = Services.get(IDistributionNetworkDAO.class);

		// In case the requirements will change and the empties ditribution network
		// will be product based, here we will need to get the product gebinde
		// and send it as parameter in the method above
		final I_DD_NetworkDistribution emptiesNetworkDistribution = handlingUnitsDAO.retrieveEmptiesDistributionNetwork(ctx,
				null, // Product
				trxName);
		if (emptiesNetworkDistribution == null)
		{
			throw new AdempiereException("@NotFound@ @DD_NetworkDistribution_ID@ (@IsHUDestroyed@=@Y@)");
		}

		final List<I_DD_NetworkDistributionLine> lines = distributionNetworkDAO.retrieveNetworkLinesBySourceWarehouse(emptiesNetworkDistribution, warehouse.getM_Warehouse_ID());

		if (lines.isEmpty())
		{
			throw new AdempiereException("@NotFound@ @M_Warehouse_ID@ (@IsHUDestroyed@=@Y@): " + warehouse.getName()
					+ "\n @DD_NetworkDistribution_ID@: " + emptiesNetworkDistribution);
		}

		return lines.get(0).getM_Warehouse();

	}

	@Override
	public void setHUStatus(final IHUContext huContext, final I_M_HU hu, final String huStatus)
	{
		final boolean forceFetchPackingMaterial = false; // rely on HU Status configuration for detection when fetching packing material
		setHUStatus(huContext, hu, huStatus, forceFetchPackingMaterial);
	}

	@Override
	public void setHUStatus(final IHUContext huContext,
			final I_M_HU hu,
			final String huStatus,
			final boolean forceFetchPackingMaterial)
	{
		// keep this so we can compare it with the new one and make sure the moving to/from
		// gebindelager is done only when needed
		final String initialHUStatus = hu.getHUStatus();

		if (Objects.equals(huStatus, initialHUStatus))
		{
			// do nothing
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
		final I_M_HU_Status statusEntry = Services.get(IHandlingUnitsDAO.class).retrieveHUStatus(ctx, huStatus);

		// do not break the tests
		// TODO: test with the new M_HU_Status table
		if (statusEntry == null)
		{
			hu.setHUStatus(huStatus);
			return;
		}

		final boolean isExchangeGebindelagerWhenEmpty = statusEntry.isExchangeGebindelagerWhenEmpty();

		//
		// 08157: If forced packing material fetching is enabled, then make sure to pull packing material from Gebinde warehouse (i.e when bringing a blank LU)
		if (forceFetchPackingMaterial)
		{
			if (isPhysicalHU(initialHUStatus))
			{
				// collect the "destroyed" HUs in case they were already physical (active)
				huContext
						.getHUPackingMaterialsCollector()
						.addHURecursively(hu, null);
			}
			else
			{
				// remove the HUs from the destroying collector (decrement qty) just in case of new HU
				huContext
						.getHUPackingMaterialsCollector()
						.removeHURecursively(hu);
			}
		}
		else if (!isExchangeGebindelagerWhenEmpty)
		{
			// do nothing
		}
		else
		{
			// TODO: use statusEntry to determine if we need to add/remove packing materials

			// remove the HUs from the destroying collector (decrement qty) just in case of new HU (no initial status)
			if (initialHUStatus == null)
			{
				huContext
						.getHUPackingMaterialsCollector()
						.removeHURecursively(hu);
			}
			// only collect the destroyed HUs in case they were already physical (active)
			else if (isPhysicalHU(initialHUStatus))
			{
				huContext
						.getHUPackingMaterialsCollector()
						.addHURecursively(hu, null);
			}
			else
			{
				// do nothing

				// TODO: evaluate the logic from here and the logic of the method at all
				// This could be the case when the HUStatus is changed from Planning to Active.
				// Teoretically we shall "fetch" the packing materials from gebinde lager in this case,
				// but by coincidence we don't want to do this because mainly this case happens when we are receving new HUs
				// from Wareneingang POS (and generate the material receipt)
				// And there we don't want to do this because those packing materials are fetched from Vendor and not from our lager.
			}
		}

		hu.setHUStatus(huStatus);

		// Do not save the HU because, at this point, we don't know what's to be done with it in future
	}

	@Override
	public IEmptiesMovementBuilder createEmptiesMovementBuilder()
	{
		return new EmptiesMovementBuilder();
	}
}
