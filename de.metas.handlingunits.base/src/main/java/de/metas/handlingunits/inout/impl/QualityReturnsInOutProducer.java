package de.metas.handlingunits.inout.impl;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.inout.IReturnsInOutProducer;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.product.IProductBL;

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

public class QualityReturnsInOutProducer extends AbstractReturnsInOutProducer
{
	
	private final QualityReturnsInOutLinesBuilder inoutLinesBuilder = QualityReturnsInOutLinesBuilder.newBuilder(inoutRef);
	
	// services
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

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
			//
			// Take out the HU from it's parent if needed
			extractHUFromParentIfNeeded(hu);

			// At this point we assume our HU is top level
			if (!handlingUnitsBL.isTopLevel(hu))
			{
				throw new HUException("@M_HU_ID@ @TopLevel@=@N@: " + hu);
			}
			final IHUStorageFactory huStorageFactory = handlingUnitsBL.getStorageFactory();

			//
			// Iterate the product storages of this HU and create/update the movement lines
			final IHUStorage huStorage = huStorageFactory.getStorage(hu);
			final List<IHUProductStorage> productStorages = huStorage.getProductStorages();
			for (final IHUProductStorage productStorage : productStorages)
			{
				inoutLinesBuilder.addHUProductStorage(productStorage);
			}
		}

	}


	@Override
	public boolean isEmpty()
	{
		return inoutLinesBuilder.isEmpty();
	}

	@Override
	protected int getReturnsDocTypeId(final IContextAware contextProvider, final boolean isSOTrx, final I_M_InOut inout, final String docBaseType)
	{
		// in the case of returns the docSubType is null
		final String docSubType = null;

		final I_C_DocType returnsDocType = Services.get(IDocTypeDAO.class)
				.getDocTypeOrNull(
						contextProvider.getCtx() // ctx
						, docBaseType // doc basetype
						, docSubType // doc subtype
						, inout.getAD_Client_ID() // client
						, inout.getAD_Org_ID() // org
						, ITrx.TRXNAME_None // trx
		);

		return returnsDocType == null ? -1 : returnsDocType.getC_DocType_ID();
	}

	public List<I_M_HU> getHusToReturn()
	{
		return husToReturn;
	}

	@Override
	public IReturnsInOutProducer addPackingMaterial(I_M_HU_PackingMaterial packingMaterial, int qty)
	{
		// TODO Not appliable at the moment
		return null;
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

		IContextAware ctxAware = InterfaceWrapperHelper.getContextAware(hu);

		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final I_M_HU_Item parentHUItem = null; // no parent
		huTrxBL.setParentHU(huContext, parentHUItem, hu);
	}

}
