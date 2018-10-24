package de.metas.handlingunits.impl;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IPair;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_BPartner;

import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUIterator;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Important class to build new HUs. Use {@link IHandlingUnitsDAO#createHUBuilder(IHUContext)} to get an isntance.
 * <p>
 * More or less employed and driven by the {@link IAllocationDestination}s and also {@link IAllocationStrategy}s, whenever the need to create a new {@link I_M_HU}.
 *
 * This builder also creates {@link I_M_HU_Item} for the new {@link I_M_HU} it creates (see {@link HUNodeIncludedItemBuilder}), but out of itself it doesn't create any child HUs.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */final class HUBuilder extends AbstractHUIterator implements IHUBuilder
{
	// Services
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private I_C_BPartner _bpartner = null;
	private int _bpartnerLocationId = -1;
	private I_M_HU_Item _parentItem = null;
	private I_M_HU_PI_Item_Product _piip = null;
	private LocatorId _locatorId = null;

	private boolean _huPlanningReceiptOwnerPM = false; // DB default false

	/**
	 * HU Status to be used when creating a new HU.
	 *
	 * Default: {@link X_M_HU#HUSTATUS_Planning}.
	 */
	private String _huStatus = X_M_HU.HUSTATUS_Planning;

	private I_M_HU_LUTU_Configuration _lutuConfiguration = null;

	public HUBuilder(@NonNull final IHUContext huContext)
	{
		setHUContext(huContext);

		registerNodeIterator(I_M_HU.class, new HUNodeIncludedItemBuilder());
		registerNodeIterator(I_M_HU_Item.class, new HUItemNodeIncludedHUBuilderNOOP());
	}

	@Override
	public final IHUBuilder setC_BPartner(final I_C_BPartner bpartner)
	{
		_bpartner = bpartner;
		return this;
	}

	protected final I_C_BPartner getC_BPartner()
	{
		return _bpartner;
	}

	@Override
	public IHUBuilder setC_BPartner_Location_ID(final int bpartnerLocationId)
	{
		_bpartnerLocationId = bpartnerLocationId;
		return this;
	}

	public int getC_BPartner_Location_ID()
	{
		return _bpartnerLocationId;
	}

	@Override
	public final IHUBuilder setM_HU_Item_Parent(final I_M_HU_Item parentItem)
	{
		_parentItem = parentItem;
		return this;
	}

	protected final I_M_HU_Item getM_HU_Item_Parent()
	{
		return _parentItem;
	}

	protected final I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		return _piip;
	}

	@Override
	public IHUBuilder setM_HU_PI_Item_Product(final I_M_HU_PI_Item_Product piip)
	{
		_piip = piip;
		return this;
	}

	@Override
	public final IHUBuilder setLocatorId(final LocatorId locatorId)
	{
		_locatorId = locatorId;
		return this;
	}

	protected final LocatorId getLocatorId()
	{
		return _locatorId;
	}

	@Override
	public IHUBuilder setHUStatus(final String huStatus)
	{
		Check.assumeNotEmpty(huStatus, "huStatus not empty");
		_huStatus = huStatus;
		return this;
	}

	protected final String getHUStatus()
	{
		return _huStatus;
	}

	@Override
	public final IHUBuilder setM_HU_LUTU_Configuration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		if (lutuConfiguration == null)
		{
			_lutuConfiguration = null;
			return this;
		}

		if (lutuConfiguration.getM_HU_LUTU_Configuration_ID() <= 0)
		{
			throw new HUException("lutuConfiguration shall be saved: " + lutuConfiguration);
		}

		if (InterfaceWrapperHelper.hasChanges(lutuConfiguration))
		{
			throw new HUException("lutuConfiguration shall have no changes: " + lutuConfiguration);
		}

		_lutuConfiguration = lutuConfiguration;
		return this;
	}

	protected final I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration()
	{
		return _lutuConfiguration;
	}

	@Override
	public IHUBuilder setHUPlanningReceiptOwnerPM(final boolean huPlanningReceiptOwnerPM)
	{
		_huPlanningReceiptOwnerPM = huPlanningReceiptOwnerPM;
		return this;
	}

	@Override
	public boolean isHUPlanningReceiptOwnerPM()
	{
		return _huPlanningReceiptOwnerPM;
	}

	private final Map<AttributeId, Object> getInitialAttributeValueDefaults()
	{
		return getHUContext().getProperty(HUAttributeConstants.CTXATTR_DefaultAttributesValue);
	}

	@Override
	public IHUIterator iterate(final Collection<I_M_HU> hus)
	{
		// this method is not supported and will never be supported because it's not the scope of this class
		throw new UnsupportedOperationException();
	}

	@Override
	public IHUIterator iterate(final I_M_HU hu)
	{
		// this method is not supported and will never be supported because it's not the scope of this class
		throw new UnsupportedOperationException();
	}

	@Override
	public I_M_HU create(final I_M_HU_PI pi)
	{
		final I_M_HU_PI_Version piVersion = handlingUnitsDAO.retrievePICurrentVersion(pi);
		return create(piVersion);
	}

	@Override
	public I_M_HU create(final I_M_HU_PI_Version piVersion)
	{
		return createInstanceRecursively(piVersion, getM_HU_Item_Parent());
	}

	/**
	 * Note that currently this method does not really do work recursive since we registered {@link HUBuilder.HUItemNodeIncludedHUBuilderNOOP} do get the downstream nodes for HU items.
	 *
	 * @param huPIVersion
	 * @param parentItem
	 * @return
	 */
	private I_M_HU createInstanceRecursively(@NonNull final I_M_HU_PI_Version huPIVersion, @Nullable final I_M_HU_Item parentItem)
	{
		//
		// Check if parent item was specified, make sure it was saved
		if (parentItem != null && parentItem.getM_HU_Item_ID() <= 0)
		{
			throw new AdempiereException(parentItem + " not saved");
		}

		final I_M_HU_PI_Version huPIVersionToUse;
		if (parentItem != null && X_M_HU_Item.ITEMTYPE_HUAggregate.equals(parentItem.getItemType()))
		{
			// gh #460: if the parent item is "aggregate", then we create a virtual HU to hold all the packing an material aggregations
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			huPIVersionToUse = handlingUnitsDAO.retrieveVirtualPIItem(getHUContext().getCtx()).getM_HU_PI_Version();
		}
		else
		{
			huPIVersionToUse = huPIVersion;
		}

		// Create a single HU instance and save it.
		final I_M_HU hu = createHUInstance(huPIVersionToUse);
		BUILDER_INVOCATION_HU_PI_VERSION.setValue(hu, huPIVersion);

		final IHUContext huContext = getHUContext();

		// Assign HU to Parent
		huTrxBL.setParentHU(huContext, parentItem, hu);

		//
		// Generate HU Attributes
		final IAttributeStorageFactory attributesStorageFactory = huContext.getHUAttributeStorageFactory();
		final IAttributeStorage attributeStorage = attributesStorageFactory.getAttributeStorage(hu);
		attributeStorage.generateInitialAttributes(getInitialAttributeValueDefaults());

		setStatus(HUIteratorStatus.Running);

		//
		// Call HU Builder to create items and other included things (if any).
		// this is where we actually create the HU items
		final AbstractNodeIterator<I_M_HU> huBuilder = getNodeIterator(I_M_HU.class);
		huBuilder.iterate(hu);

		// Collect the HU (only if physical and unless the collector is disabled) in order to be taken from the empties warehouse into the current warehouse.
		if (Services.get(IHUStatusBL.class).isPhysicalHU(hu))
		{
			huContext.getHUPackingMaterialsCollector().requirePackingMaterialForHURecursively(hu);
		}
		//
		// If after running everything the status is still running, switch it to finished
		if (getStatus() == HUIteratorStatus.Running)
		{
			setStatus(HUIteratorStatus.Finished);
		}

		return hu;
	}

	/**
	 * Creates new {@link I_M_HU} and saves it.
	 *
	 * This method is creating ONLY the {@link I_M_HU} object and not it's children.
	 *
	 * It will use {@link #getM_HU_Item_Parent()} as it's parent.
	 *
	 * @param huPIVersion
	 * @return
	 * @see #createHUInstance(I_M_HU_PI_Version, I_M_HU_Item)
	 */
	private final I_M_HU createHUInstance(final I_M_HU_PI_Version huPIVersion)
	{
		final I_M_HU_Item parentItem = getM_HU_Item_Parent();
		return createHUInstance(huPIVersion, parentItem);
	}

	/**
	 * Creates new {@link I_M_HU} and saves it.
	 *
	 * This method is creating ONLY the {@link I_M_HU} object and not it's children.
	 *
	 * @param huPIVersion set as the new HU's {@link I_M_HU_PI_Version}
	 * @param parentItem parent HU Item to link on
	 * @return created {@link I_M_HU}.
	 */
	private final I_M_HU createHUInstance(final I_M_HU_PI_Version huPIVersion, final I_M_HU_Item parentItem)
	{
		final IHUContext huContext = getHUContext();

		final I_M_HU hu = InterfaceWrapperHelper.newInstance(I_M_HU.class, huContext);
		hu.setM_HU_PI_Version(huPIVersion);

		//
		// Get Parent HU (if any)
		final I_M_HU parentHU;
		if (parentItem != null)
		{
			parentHU = parentItem.getM_HU();
		}
		else
		{
			parentHU = null;
		}

		//
		// Copy HUStatus from parent
		final String huStatus;
		if (parentHU != null)
		{
			huStatus = parentHU.getHUStatus();

		}
		else
		{
			// Set configured HUStatus if no parent item/parent item HU found.
			huStatus = getHUStatus();
		}

		huStatusBL.setHUStatus(huContext, hu, huStatus);

		//
		// Copy C_BPartner_ID from parent
		final int parentBPartnerId = parentHU == null ? -1 : parentHU.getC_BPartner_ID();
		if (parentBPartnerId > 0)
		{
			hu.setC_BPartner_ID(parentBPartnerId);
			hu.setC_BPartner_Location_ID(parentHU.getC_BPartner_Location_ID());
		}
		else
		{
			final I_C_BPartner bpartner = getC_BPartner();
			hu.setC_BPartner(bpartner);

			final int bpartnerLocationId = getC_BPartner_Location_ID();
			if (bpartner != null && bpartnerLocationId > 0)
			{
				hu.setC_BPartner_Location_ID(bpartnerLocationId);
			}
		}

		//
		// Copy M_Locator from parent or get it from HUBuilder configuration
		if (parentHU != null)
		{
			hu.setM_Locator_ID(parentHU.getM_Locator_ID());
		}
		else
		{
			final LocatorId locatorId = getLocatorId();
			hu.setM_Locator_ID(LocatorId.toRepoId(locatorId));
		}

		//
		// Set LU/TU configuration reference
		final I_M_HU_LUTU_Configuration lutuConfig = getM_HU_LUTU_Configuration();
		hu.setM_HU_LUTU_Configuration(lutuConfig);

		//
		// 07970: Set M_HU.M_HU_PI_Item_Product_ID
		final I_M_HU_PI_Item_Product piip = getM_HU_PI_Item_ProductOrNull(lutuConfig, parentHU, hu);
		hu.setM_HU_PI_Item_Product(piip);

		//
		// fresh 08162: Set M_HU.HUPlanningReceiptOwnerPM
		final boolean huPlanningReceiptOwnerPM = isHUPlanningReceiptOwnerPM();
		hu.setHUPlanningReceiptOwnerPM(huPlanningReceiptOwnerPM);

		//
		// Notify Storage and Attributes DAO that a new HU was created
		// NOTE: depends on their implementation, but they have a chance to do some optimizations
		huContext.getHUStorageFactory().getHUStorageDAO().initHUStorages(hu);
		huContext.getHUAttributeStorageFactory().getHUAttributesDAO().initHUAttributes(hu);

		//
		// Save HU
		handlingUnitsDAO.saveHU(hu);

		//
		// Return
		return hu;
	}

	/**
	 * @param parentHU
	 * @param hu
	 * @return Handling Unit PI Item Product or null, depending on the HU type and what's configured
	 */
	private I_M_HU_PI_Item_Product getM_HU_PI_Item_ProductOrNull(final I_M_HU_LUTU_Configuration lutuConfig, final I_M_HU parentHU, final I_M_HU hu)
	{
		I_M_HU_PI_Item_Product piip = getM_HU_PI_Item_Product();
		if (piip != null)
		{
			return piip;
		}

		if (lutuConfig != null)
		{
			piip = lutuConfig.getM_HU_PI_Item_Product();
		}

		if (piip != null)
		{
			return piip;
		}

		if (parentHU != null)
		{
			piip = parentHU.getM_HU_PI_Item_Product();
		}
		return piip;
	}

	/**
	 * Builder used to create {@link I_M_HU_Item}s for a given {@link I_M_HU}. Also see the comments within the code to figure out what it does.
	 *
	 * @author tsa
	 *
	 */
	protected class HUNodeIncludedItemBuilder extends HUNodeIterator
	{
		/**
		 * Creates and returns {@link I_M_HU_Item}s according to the given HU's {@link I_M_HU_PI_Item}s
		 */
		@Override
		public List<I_M_HU_Item> retrieveDownstreamNodes(final I_M_HU hu)
		{
			final List<I_M_HU_Item> result = new ArrayList<>();
			final IHUStorageDAO huStorageDAO = getHUContext().getHUStorageFactory().getHUStorageDAO();

			if (handlingUnitsBL.isAggregateHU(hu))
			{
				// #460 the given 'hu' is an "aggregate/compressed/bag" one.

				// take a look at the M_HU_PI_Version with which the "create()" method for this given aggregate 'hu' was called.
				final I_M_HU_PI_Version invocationPIVersion = BUILDER_INVOCATION_HU_PI_VERSION.getValue(hu);
				if (invocationPIVersion != null && invocationPIVersion.getM_HU_PI_Version_ID() != hu.getM_HU_PI_Version_ID())
				{
					// If 'invocationPIVersion' differs from the the M_HU_PI_Version of the aggregate 'hu' we got here
					// then we create and add items for the packaging piItems of 'invocationPIVersion'.
					// The goal is that even if e.g. we don't create a full-fledged trade-unit-HU for an IFCO that's below a palet,
					// we still add the IFCOs' packing instruction to the aggregate 'hu', so the number of included IFCOs can be represented there.
					final List<I_M_HU_PI_Item> piItems = handlingUnitsDAO
							.retrievePIItems(invocationPIVersion, getC_BPartner())
							.stream()
							.filter(pi -> X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(pi.getItemType()))
							.collect(Collectors.toList());

					// note that here we only create the item. Its Qty is set in a producerDestination's loadFinished() implementation
					for (final I_M_HU_PI_Item piItem : piItems)
					{
						final IPair<I_M_HU_Item, Boolean> item = handlingUnitsDAO.createHUItemIfNotExists(hu, piItem);
						if (item.getRight())
						{
							result.add(item.getLeft()); // a new item was created, so add it.
						}
					}
				}
			}

			// For any given 'hu' we only want either a material item or an aggregated item, but not both.
			boolean huHasMaterialItem = false;

			// Create "regular" material items and packing-material items that are declared by the PI
			final I_M_HU_PI_Version piVersion = handlingUnitsBL.getPIVersion(hu);
			final List<I_M_HU_PI_Item> piItems = handlingUnitsDAO.retrievePIItems(piVersion, getC_BPartner());

			for (final I_M_HU_PI_Item piItem : piItems)
			{
				final String itemType = piItem.getItemType();
				if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
				{
					// gh #460: don't create any HU item.
					// otherwise we would now create one item for each piItem. For a top-level LU like palette, this might mean to create dozens of different TU-items, even if only one of them is actually used.
					// *If* we actually really need to add child-HU later, then the respective HU-item will be created on the fly (currently that's happening in LULoaderInstance).
					continue;
				}

				final I_M_HU_Item item = handlingUnitsDAO.createHUItem(hu, piItem);
				result.add(item);

				// Notify Storage DAO that a new item was just created
				huStorageDAO.initHUItemStorages(item);

				if (X_M_HU_Item.ITEMTYPE_Material.equals(handlingUnitsBL.getItemType(item)))
				{
					huHasMaterialItem = true;
				}
			}

			// The given HU is not configured to hold material itself because e.g. it is a palet.
			// Therefore we add one "aggregate" item which can then later onwards represent the child-HUs and its content.
			final boolean huIsNotYetAnAggregate = !handlingUnitsBL.isAggregateHU(hu);
			final boolean huIsVirtual = handlingUnitsBL.isVirtual(hu);
			final boolean huIsLU = handlingUnitsBL.isLoadingUnit(hu);
			if (!huHasMaterialItem && huIsNotYetAnAggregate)
			{
				if (huIsLU)
				{
					final I_M_HU_Item aggregateItem = handlingUnitsDAO.createAggregateHUItem(hu);

					result.add(aggregateItem);
					huStorageDAO.initHUItemStorages(aggregateItem); // Notify Storage DAO that a new item was just created
				}
				else if (!huIsVirtual)
				{
					final I_M_HU_Item childItem = handlingUnitsDAO.createChildHUItem(hu);

					result.add(childItem);
					huStorageDAO.initHUItemStorages(childItem); // Notify Storage DAO that a new item was just created
				}
			}
			return result;
		}
	}

	/**
	 * Builder that might be used <b>(but isn't!)</b> to create included {@link I_M_HU}s for given {@link I_M_HU_Item}.
	 *
	 * Actually is doing nothing because we are not creating included HUs recursively.
	 */
	protected class HUItemNodeIncludedHUBuilderNOOP extends HUItemNodeIterator
	{
		// we do want to create a downstream-HU for hu-items with type ITEMTYPE_HUAggregate
		@Override
		public AbstractNodeIterator<?> getDownstreamNodeIterator(final I_M_HU_Item node)
		{
			return null;
		}

		@Override
		public List<Object> retrieveDownstreamNodes(final I_M_HU_Item huItem)
		{
			throw new IllegalStateException("Shall not be called because we don't have a downstream node iterator");
		}
	}
}
