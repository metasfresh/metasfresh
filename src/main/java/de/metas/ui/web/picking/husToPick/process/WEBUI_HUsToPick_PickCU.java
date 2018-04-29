package de.metas.ui.web.picking.husToPick.process;

import static de.metas.ui.web.handlingunits.WEBUI_HU_Constants.MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.OptionalInt;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_UOM;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.ImmutableList;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.IProductBL;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.packageable.PackageableRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_HUsToPick_PickCU extends HUsToPickViewBasedProcess implements IProcessPrecondition, IProcessParametersCallout, IProcessDefaultParametersProvider
{

	@Autowired
	private PickingCandidateService pickingCandidateService;
	@Autowired
	private PickingConfigRepository pickingConfigRepo;

	// services
	private final transient IProductBL productBL = Services.get(IProductBL.class);

	private static final String MSG_InvalidProduct = "de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_PickCU.InvalidProduct";

	private static final String PARAM_M_Product_ID = "M_Product_ID";
	/**
	 * Scanned product, used to validate it against shipment schedule's product.
	 * This parameter is not mandatory because we want to allow the SysAdm to just hide the field in case we don't need this validation.
	 */
	@Param(parameterName = PARAM_M_Product_ID, mandatory = false)
	private int scannedProductId;

	private static final String PARAM_QtyCU = "QtyCU";
	/**
	 * Qty CU to be picked
	 */
	@Param(parameterName = PARAM_QtyCU, mandatory = true)
	private BigDecimal qtyCU;

	private transient I_M_Product _productToPack; // lazy

	private boolean isAutoProcess = false;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final HUEditorRow huRow = getSingleSelectedRow();
		if (!isEligible(huRow))
		{
			final ITranslatableString reason = Services.get(IMsgBL.class).getTranslatableMsgText(MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU);
			return ProcessPreconditionsResolution.reject(reason);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_M_Product_ID.equals(parameter.getColumnName()))
		{
			// For now, according to https://github.com/metasfresh/metasfresh-webui-api/issues/876,
			// we are setting the "scanned product" field to the right value.
			final PackageableRow packageableRow = getSingleSelectedPackageableRow();
			return packageableRow.getProductId();
		}
		else if (PARAM_QtyCU.equals(parameter.getColumnName()))
		{
			final PackageableRow packageableRow = getSingleSelectedPackageableRow();
			final BigDecimal qtyToDeliver = packageableRow.getQtyOrderedWithoutPicked();

			final HUEditorRow huRow = getSingleSelectedRow();
			final BigDecimal huQty = huRow.getQtyCU();

			return qtyToDeliver.min(huQty);
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_M_Product_ID.equals(parameterName))
		{
			// Make sure user scanned the right product
			getProduct();
		}
	}

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		// #3778
		// Process the picking automatically in case it is configured this way.
		isAutoProcess = pickingConfigRepo.getPickingConfig().isAutoProcess();
		pickCUs();

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		if (!isAutoProcess)
		{

			invalidateAndGoBackToPickingSlotsView();
		}
		else
		{
			invalidatePickingSlotsView();
			invalidatePackablesView();
		}
	}

	private BigDecimal getQtyCU()
	{
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCU);
		}
		return qtyCU;
	}

	private I_M_Product getProduct()
	{
		final I_M_Product productToPack = getProductToPack();

		//
		// Assert scanned product is matching the shipment schedule product.
		// NOTE: scannedProductId might be not set, in case we deactivate the process parameter.
		if (scannedProductId > 0 && scannedProductId != productToPack.getM_Product_ID())
		{
			final I_M_Product scannedProduct = loadOutOfTrx(scannedProductId, I_M_Product.class);
			final String scannedProductStr = scannedProduct != null ? scannedProduct.getName() : "?";
			final String expectedProductStr = productToPack.getName();
			throw new AdempiereException(MSG_InvalidProduct, new Object[] { scannedProductStr, expectedProductStr });
		}

		return productToPack;
	}

	private I_M_Product getProductToPack()
	{
		if (_productToPack == null)
		{
			final PackageableRow packageableRow = getSingleSelectedPackageableRow();
			final int productId = packageableRow.getProductId();
			_productToPack = loadOutOfTrx(productId, I_M_Product.class);
		}
		return _productToPack;
	}

	private void pickCUs()
	{
		final I_M_HU splitCU = Services.get(ITrxManager.class).call(this :: performPickCU);
	
		if (isAutoProcess)
		{
			autoProcessPicking(splitCU);
		}

	}

	private I_M_HU performPickCU()
	{
		final I_M_Product product = getProduct();
		final I_C_UOM uom = productBL.getStockingUOM(product);
		final Date date = SystemTime.asDate();

		final int huIdToSplit = retrieveHUIdToSplit();
		final List<I_M_HU> splitHUs = HUSplitBuilderCoreEngine.builder()
				.huToSplit(load(huIdToSplit, I_M_HU.class))
				.requestProvider(huContext -> AllocationUtils.createAllocationRequestBuilder()
						.setHUContext(huContext)
						.setProduct(product)
						.setQuantity(getQtyCU(), uom)
						.setDate(date)
						.setFromReferencedModel(null) // N/A
						.setForceQtyAllocation(false)
						.create())
				.destination(HUProducerDestination.ofVirtualPI())
				.build()
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		final I_M_HU splitCU = ListUtils.singleElement(splitHUs);
		addHUIdToCurrentPickingSlot(splitCU.getM_HU_ID());

		return splitCU;
	}

	private void autoProcessPicking(final I_M_HU splitCU)
	{
		final PickingSlotRow rowToProcess = getPickingSlotRow();
		pickingCandidateService.processForHUIds(ImmutableList.of(splitCU.getM_HU_ID()), rowToProcess.getPickingSlotId(), OptionalInt.empty());

	}

	private int retrieveHUIdToSplit()
	{
		return retrieveEligibleHUEditorRows()
				.map(HUEditorRow::getM_HU_ID)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Only one HU shall be selected")));
	}

}
