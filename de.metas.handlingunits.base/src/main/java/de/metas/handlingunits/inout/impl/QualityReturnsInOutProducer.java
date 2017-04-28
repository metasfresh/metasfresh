package de.metas.handlingunits.inout.impl;

import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;

import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.empties.EmptiesInOutLinesProducer;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Producer for vendor returns.
 * Used when some products were received from a vendor and it was decided they are not respecting the quality standards they can be sent back to the vendor.
 *
 * Introduced in #1062
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class QualityReturnsInOutProducer extends AbstractReturnsInOutProducer
{

	/**
	 * Builder for lines with products that are not packing materials
	 */
	private final QualityReturnsInOutLinesBuilder inoutLinesBuilder = QualityReturnsInOutLinesBuilder.newBuilder(inoutRef);

	/**
	 * Builder for packing material lines
	 */
	private final EmptiesInOutLinesProducer packingMaterialInoutLinesBuilder = EmptiesInOutLinesProducer.newInstance(inoutRef);

	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	/**
	 * List of handling units that have to be returned to vendor
	 */
	private final List<I_M_HU> husToReturn;

	public QualityReturnsInOutProducer(final Properties ctx, final List<I_M_HU> hus)
	{
		super();

		Check.assumeNotNull(ctx, "ctx not null");
		setCtx(ctx);

		husToReturn = Collections.unmodifiableList(hus);
	}

	@Override
	protected void createLines()
	{

		for (final I_M_HU hu : husToReturn)
		{
			// Step 1: Prepare the packing material lines based on the HU configuration
			{
				// take the details from the LU-TU configuration
				final I_M_HU_LUTU_Configuration luTuConfiguration = hu.getM_HU_LUTU_Configuration();

				// add LU if exists
				final I_M_HU_PI luHUPI = luTuConfiguration.getM_LU_HU_PI();

				if (luHUPI != null)
				{
					// get the packing material item of the LU HU_PI. Note that each HU-PI should only have one active item that is a packing material
					final List<I_M_HU_PI_Item> materialItems = handlingUnitsDAO
							.retrievePIItems(luHUPI, inoutRef.getValue().getC_BPartner()).stream()
							.filter(i -> X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(i.getItemType()))
							.collect(Collectors.toList());

					final I_M_HU_PackingMaterial luPackingMaterial = materialItems.get(0).getM_HU_PackingMaterial();

					if (luPackingMaterial != null)
					{
						// if the packing material was found, set it as a source for the packing material lines
						addPackingMaterial(luPackingMaterial, luTuConfiguration.getQtyLU().intValue());
					}
				}

				// add TU if exists
				final I_M_HU_PI tuHUPI = luTuConfiguration.getM_TU_HU_PI();

				if (tuHUPI != null)
				{
					// get the packing material item of the TU HU_PI. Note that each HU-PI should only have one active item that is a packing material

					final List<I_M_HU_PI_Item> materialItems = handlingUnitsDAO
							.retrievePIItems(tuHUPI, inoutRef.getValue().getC_BPartner()).stream()
							.filter(i -> X_M_HU_PI_Item.ITEMTYPE_PackingMaterial.equals(i.getItemType()))
							.collect(Collectors.toList());

					if (!materialItems.isEmpty())
					{
						final I_M_HU_PackingMaterial huPackingMaterial = materialItems.get(0).getM_HU_PackingMaterial();

						if (huPackingMaterial != null)
						{
							// if the packing material was found, set it as a source for the packing material lines
							addPackingMaterial(huPackingMaterial, luTuConfiguration.getQtyTU().intValue());
						}
					}
				}

				//
				// Take out the HU from it's parent if needed
				extractHUFromParentIfNeeded(hu);

				// At this point we assume our HU is top level
				if (!handlingUnitsBL.isTopLevel(hu))
				{
					throw new HUException("@M_HU_ID@ @TopLevel@=@N@: " + hu);
				}
			}

			// Step 2: Create product (non-packing material) lines
			{
				// Iterate the product storages of this HU and create/update the inout lines
				final IHUStorageFactory huStorageFactory = handlingUnitsBL.getStorageFactory();
				final IHUStorage huStorage = huStorageFactory.getStorage(hu);
				final List<IHUProductStorage> productStorages = huStorage.getProductStorages();
				for (final IHUProductStorage productStorage : productStorages)
				{
					inoutLinesBuilder.addHUProductStorage(productStorage);
				}
			}

		}

		// Step 3: Create the packing material lines that were prepared
		packingMaterialInoutLinesBuilder.create();

	}

	@Override
	public boolean isEmpty()
	{
		// only empty if there are no product lines.
		return inoutLinesBuilder.isEmpty();
	}

	@Override
	protected int getReturnsDocTypeId(final String docBaseType, final boolean isSOTrx, final int adClientId, final int adOrgId)
	{
		// in the case of returns the docSubType is null
		final String docSubType = IDocTypeDAO.DOCSUBTYPE_NONE;

		final I_C_DocType returnsDocType = Services.get(IDocTypeDAO.class)
				.getDocTypeOrNullForSOTrx(
						Env.getCtx() // ctx
						, docBaseType // doc basetype
						, docSubType // doc subtype
						, isSOTrx // isSOTrx
						, adClientId // client
						, adOrgId // org
						, ITrx.TRXNAME_None // trx
		);

		return returnsDocType == null ? -1 : returnsDocType.getC_DocType_ID();
	}

	@Override
	public IReturnsInOutProducer addPackingMaterial(final I_M_HU_PackingMaterial packingMaterial, final int qty)
	{
		packingMaterialInoutLinesBuilder.addSource(packingMaterial, qty);
		return this;
	}

	/**
	 * Take out the given HU from it's parent (if it's not already a top level HU)
	 *
	 * @param hu
	 */
	private void extractHUFromParentIfNeeded(final I_M_HU hu)
	{

		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		if (handlingUnitsBL.isTopLevel(hu))
		{
			return;
		}

		final IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final I_M_HU_Item parentHUItem = null; // no parent
		huTrxBL.setParentHU(huContext, parentHUItem, hu);
	}

}
