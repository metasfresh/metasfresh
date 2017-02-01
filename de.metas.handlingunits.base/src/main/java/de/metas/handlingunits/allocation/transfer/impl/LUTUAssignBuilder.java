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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.Quantity;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUContextProcessorExecutor;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.attribute.IHUTransactionAttributeBuilder;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequest;
import de.metas.handlingunits.attribute.strategy.IHUAttributeTransferRequestBuilder;
import de.metas.handlingunits.attribute.strategy.impl.HUAttributeTransferRequestBuilder;
import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.exceptions.NoCompatibleHUItemParentFoundException;
import de.metas.handlingunits.impl.HUTransaction;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

/**
 * Creates an LU (according to given parameters) and assigns given TUs to it (LU zuteilen).
 *
 * @author tsa
 *
 */
public class LUTUAssignBuilder
{
	//
	// Services & factories
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IHUJoinBL huJoinBL = Services.get(IHUJoinBL.class);
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	//
	// Parameters
	private IContextAware _context;
	private List<I_M_HU> _tusToAssign;
	private I_M_HU_PI _luPI;
	private IHUDocumentLine _documentLine = null;
	private I_C_BPartner _bpartner;
	private int _bpLocationId = -1;
	private I_M_Locator _locator;
	private String _huStatus;
	private boolean _isHUPlanningReceiptOwnerPM = false;

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public final I_M_HU build()
	{
		final List<I_M_HU> tusToAssign = getTUsToAssign();

		//
		// Create the LU, assign TUs to it
		// Also transfer attributes from document line and create HU_Trx_Line for LU-TU assignments
		final IMutable<I_M_HU> resultLUHU = new Mutable<>(null);
		final IContextAware contextInitial = getContext();
		final IHUContextProcessorExecutor executor = huTrxBL.createHUContextProcessorExecutor(contextInitial);
		executor.run(new IHUContextProcessor()
		{
			@Override
			public IMutableAllocationResult process(final IHUContext huContext)
			{
				final IHUTransactionAttributeBuilder trxAttributesBuilder = executor.getTrxAttributesBuilder();
				final I_M_HU luHU = createLUHU(huContext, trxAttributesBuilder);
				assignTUsToLU(huContext, luHU, tusToAssign);

				resultLUHU.setValue(luHU);

				return NULL_RESULT; // we don't care
			}
		});

		//
		// Get create LU
		final I_M_HU luHU = resultLUHU.getValue();
		Check.assumeNotNull(luHU, "luHU not null");

		//
		// Make created HUs to be out-of-transaction to be used elsewhere
		InterfaceWrapperHelper.setTrxName(luHU, ITrx.TRXNAME_ThreadInherited);
		for (final I_M_HU joinedHU : tusToAssign)
		{
			InterfaceWrapperHelper.setTrxName(joinedHU, ITrx.TRXNAME_ThreadInherited);
		}

		return luHU;
	}

	/**
	 * Create LU-HU for selected PI. It is also
	 * <ul>
	 * <li>creates {@link I_M_HU_Trx_Line}s
	 * <li>transfer attributes from document line to LU
	 * </ul>
	 *
	 * @param huContext
	 * @param trxAttributesBuilder
	 * @return created LU
	 */
	private final I_M_HU createLUHU(final IHUContext huContext, final IHUTransactionAttributeBuilder trxAttributesBuilder)
	{
		final I_M_HU_PI luPI = getLU_PI();

		//
		// Create the LU now
		final IHUBuilder huBuilder = handlingUnitsDAO.createHUBuilder(huContext);
		huBuilder.setC_BPartner(getC_BPartner());
		huBuilder.setC_BPartner_Location_ID(getC_BPartner_Location_ID());
		huBuilder.setDate(SystemTime.asDate());
		huBuilder.setM_Locator(getM_Locator());
		huBuilder.setHUStatus(X_M_HU.HUSTATUS_Planning);

		//
		// Set the owner of the packing material to be either "us" or the people who ordered it.
		// For receipts, we will take the PM from the Gebinde Lager once the material receipt is created if this property is true.
		final boolean huPlanningReceiptOwnerPM = isHUPlanningReceiptOwnerPM();
		huBuilder.setHUPlanningReceiptOwnerPM(huPlanningReceiptOwnerPM);

		final I_M_HU luHU = huBuilder.create(luPI);
		Check.assumeNotNull(luHU, "luHU not null");

		// Change LU's to our status
		// NOTE 1: we expect packing materials to be collected (i.e. fetched from Gebinde lager)
		// NOTE 2: in case we are dealing with HUStatus Planning (e.g. in Wareneingang POS), no packing materials will be collected because the status is not actually changing.
		final boolean forceFetchPackingMaterial = true;
		handlingUnitsBL.setHUStatus(huContext, luHU, getHUStatus(), forceFetchPackingMaterial);
		InterfaceWrapperHelper.save(luHU, huContext.getTrxName());

		//
		// Create TrxLines for our new LU
		createLUHUTransactions(huContext, luHU);

		//
		// Transfer attributes
		transferAttributesFromDocumentLineToLU(huContext, trxAttributesBuilder, luHU);

		//
		// Return the created LU
		return luHU;
	}

	/**
	 * Create M_HU_Trx_Hdr and M_HU_Trx_Lines for our {@link #resultLUHU}'s instantiation
	 *
	 * @param huContext
	 */
	private final void createLUHUTransactions(final IHUContext huContext, final I_M_HU luHU)
	{
		//
		// We can only create HU Transactions if we're binding them to a document line
		final IHUDocumentLine documentLine = getDocumentLine();
		if (documentLine == null)
		{
			return;
		}

		Check.assumeNotNull(luHU, "LU shall be created at this point");

		//
		// Get the LU's item to use in trx
		final List<I_M_HU_Item> luItems = handlingUnitsDAO.retrieveItems(luHU);
		final I_M_HU_Item luItem = luItems.iterator().next(); // TODO just use the first item on the LU

		//
		// Get a TU's product storage so that we can mimic it's product in our HU transactions. We don't, in fact, really need a product.
		// TODO: maybe use a default No-Product and rely on it.
		final IHUStorageFactory huStorageFactory = huContext.getHUStorageFactory();
		//
		final I_M_HU huToUseForMockProduct = getTUsToAssign().iterator().next();
		final IHUStorage mockHUStorage = huStorageFactory.getStorage(huToUseForMockProduct);
		final List<IHUProductStorage> productStorages = mockHUStorage.getProductStorages();
		if (productStorages.isEmpty())
		{
			return; // shall not happen
		}
		final IHUProductStorage mockProductStorage = productStorages.iterator().next();

		//
		// Extract the data needed to create the HU Transactions
		final I_M_Product mockProduct = mockProductStorage.getM_Product();
		final BigDecimal mockQty = BigDecimal.ZERO;
		final I_C_UOM mockUOM = mockProductStorage.getC_UOM();
		final Quantity mockQuantity = new Quantity(mockQty, mockUOM);

		final Date date = SystemTime.asTimestamp();
		final Object referencedModel = documentLine.getTrxReferencedModel();

		//
		// Create HU Transaction From: Old HUStatus, Old Locator, minus storage Qty
		final IHUTransaction huTransactionFrom = new HUTransaction(referencedModel,
				null, // huItem from
				null, // vhuItem from
				mockProduct,
				mockQuantity.negate(),
				date,
				null, // locator from
				null); // huStatus from
		huTransactionFrom.setSkipProcessing(); // i.e. don't change HU's storage

		//
		// Create HU Transaction To: New HUStatus, New Locator, plus storage Qty
		final IHUTransaction huTransactionTo = new HUTransaction(referencedModel,
				luItem, // huItem
				null, // vhuItem
				mockProduct,
				mockQuantity,
				date,
				getM_Locator(),
				getHUStatus());
		huTransactionTo.setSkipProcessing(); // i.e. don't change HU's storage
		huTransactionTo.pair(huTransactionFrom);

		//
		// Create allocation result with no quantity (the quantity will be set from the IHUTransactions). We need the allocation result to create the transaction headers.
		final IMutableAllocationResult allocationResult = AllocationUtils.createMutableAllocationResult(BigDecimal.ZERO);
		allocationResult.addTransaction(huTransactionFrom);
		allocationResult.addTransaction(huTransactionTo);
		// Create the actual M_HU_Trx_Lines
		huTrxBL.createTrx(huContext, allocationResult);
	}

	private void assignTUsToLU(final IHUContext huContext, final I_M_HU luHU, final List<I_M_HU> tuHUs)
	{
		for (final I_M_HU tuHU : tuHUs)
		{
			try
			{
				//
				// Move TU inside the LU
				huJoinBL.assignTradingUnitToLoadingUnit(huContext, luHU, tuHU);
			}
			catch (final NoCompatibleHUItemParentFoundException ncipe)
			{
				throw new HUException("@" + NoCompatibleHUItemParentFoundException.ERR_TU_NOT_ALLOWED_ON_LU + "@ " + tuHU.getValue(), ncipe);
			}
		}
	}

	private final void transferAttributesFromDocumentLineToLU(final IHUContext huContext, final IHUTransactionAttributeBuilder trxAttributesBuilder, final I_M_HU luHU)
	{
		final IHUDocumentLine documentLine = getDocumentLine();
		if (documentLine == null)
		{
			return; // can't transfer ASI if line is null
		}

		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();

		//
		// Transfer ASI attributes from document line to our new LU
		final Object trxReferencedModel = documentLine.getTrxReferencedModel();
		final IAttributeStorage asiAttributeStorageFrom = attributeStorageFactory.getAttributeStorageIfHandled(trxReferencedModel);
		if (asiAttributeStorageFrom == null)
		{
			return; // can't transfer from nothing
		}
		final IAttributeStorage luhuAttributeStorageTo = attributeStorageFactory.getAttributeStorage(luHU);

		final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
		final IHUStorage huStorageFrom = storageFactory.getStorage(luHU);

		final IHUAttributeTransferRequestBuilder requestBuilder = new HUAttributeTransferRequestBuilder(huContext)
				.setProduct(documentLine.getM_Product())
				.setQty(documentLine.getQty())
				.setUOM(documentLine.getC_UOM())
				.setAttributeStorageFrom(asiAttributeStorageFrom)
				.setAttributeStorageTo(luhuAttributeStorageTo)
				.setHUStorageFrom(huStorageFrom);

		final IHUAttributeTransferRequest request = requestBuilder.create();
		trxAttributesBuilder.transferAttributes(request);
	}

	private final void assertConfigurable()
	{
		// nothing
	}

	public LUTUAssignBuilder setContext(final IContextAware context)
	{
		assertConfigurable();
		_context = context;
		return this;
	}

	private IContextAware getContext()
	{
		return _context;
	}

	public LUTUAssignBuilder setLU_PI(final I_M_HU_PI luPI)
	{
		assertConfigurable();
		_luPI = luPI;
		return this;
	}

	private I_M_HU_PI getLU_PI()
	{
		Check.assumeNotNull(_luPI, "_luPI not null");
		return _luPI;
	}

	public LUTUAssignBuilder setTUsToAssign(final Collection<I_M_HU> tusToAssign)
	{
		assertConfigurable();
		_tusToAssign = new ArrayList<>(tusToAssign);
		return this;
	}

	private List<I_M_HU> getTUsToAssign()
	{
		Check.assumeNotEmpty(_tusToAssign, "_tusToAssign not empty");
		return _tusToAssign;
	}

	public final LUTUAssignBuilder setHUPlanningReceiptOwnerPM(final boolean isHUPlanningReceiptOwnerPM)
	{
		assertConfigurable();
		_isHUPlanningReceiptOwnerPM = isHUPlanningReceiptOwnerPM;
		return this;
	}

	private final boolean isHUPlanningReceiptOwnerPM()
	{
		return _isHUPlanningReceiptOwnerPM;
	}

	public LUTUAssignBuilder setDocumentLine(final IHUDocumentLine documentLine)
	{
		assertConfigurable();
		_documentLine = documentLine;
		return this;
	}

	private IHUDocumentLine getDocumentLine()
	{
		return _documentLine; // null is ok
	}

	public LUTUAssignBuilder setC_BPartner(final I_C_BPartner bpartner)
	{
		assertConfigurable();
		_bpartner = bpartner;
		return this;
	}

	private final I_C_BPartner getC_BPartner()
	{
		Check.assumeNotNull(_bpartner, "_bpartner not null");
		return _bpartner;
	}

	public LUTUAssignBuilder setC_BPartner_Location_ID(final int bpartnerLocationId)
	{
		assertConfigurable();
		_bpLocationId = bpartnerLocationId;
		return this;
	}

	private final int getC_BPartner_Location_ID()
	{
		return _bpLocationId;
	}

	public LUTUAssignBuilder setM_Locator(final I_M_Locator locator)
	{
		assertConfigurable();
		_locator = locator;
		return this;
	}

	public I_M_Locator getM_Locator()
	{
		Check.assumeNotNull(_locator, "_locator not null");
		return _locator;
	}

	public LUTUAssignBuilder setHUStatus(final String huStatus)
	{
		assertConfigurable();
		_huStatus = huStatus;
		return this;
	}

	private String getHUStatus()
	{
		Check.assumeNotEmpty(_huStatus, "_huStatus not empty");
		return _huStatus;
	}
}
