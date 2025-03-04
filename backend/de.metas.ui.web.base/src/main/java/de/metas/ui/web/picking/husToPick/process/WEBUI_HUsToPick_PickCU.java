package de.metas.ui.web.picking.husToPick.process;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilderCoreEngine;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.ShipmentScheduleId;
import de.metas.picking.api.PickingConfigRepository;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.picking.packageable.PackageableRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.model.I_C_UOM;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static de.metas.ui.web.handlingunits.WEBUI_HU_Constants.MSG_WEBUI_SELECT_ACTIVE_UNSELECTED_HU;

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
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final transient IProductBL productBL = Services.get(IProductBL.class);
	private final transient IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	private static final AdMessageKey MSG_InvalidProduct = AdMessageKey.of("de.metas.ui.web.picking.husToPick.process.WEBUI_HUsToPick_PickCU.InvalidProduct");

	private static final String PARAM_M_Product_ID = "M_Product_ID";
	/**
	 * Scanned product, used to validate it against shipment schedule's product.
	 * This parameter is not mandatory because we want to allow the SysAdm to just hide the field in case we don't need this validation.
	 */
	@Param(parameterName = PARAM_M_Product_ID, mandatory = false)
	private int scannedProductId;

	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	/**
	 * Qty CU to be picked
	 */
	@Param(parameterName = PARAM_QtyCUsPerTU, mandatory = true)
	private BigDecimal qtyCUsPerTU;

	private transient ProductId _productIdToPack; // lazy

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
		else if (PARAM_QtyCUsPerTU.equals(parameter.getColumnName()))
		{
			final PackageableRow packageableRow = getSingleSelectedPackageableRow();
			final BigDecimal qtyToDeliver = packageableRow.getQtyOrderedWithoutPicked().toBigDecimal();

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
			getProductId();
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

	private BigDecimal getQtyCUsPerTU()
	{
		if (qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}
		return qtyCUsPerTU;
	}

	private ProductId getProductId()
	{
		final ProductId productIdToPack = getProductIdToPack();

		//
		// Assert scanned product is matching the shipment schedule product.
		// NOTE: scannedProductId might be not set, in case we deactivate the process parameter.
		if (scannedProductId > 0 && scannedProductId != productIdToPack.getRepoId())
		{
			final IProductBL productBL = Services.get(IProductBL.class);
			final String scannedProductStr = productBL.getProductName(ProductId.ofRepoId(scannedProductId));
			final String expectedProductStr = productBL.getProductName(productIdToPack);
			throw new AdempiereException(MSG_InvalidProduct, new Object[] { scannedProductStr, expectedProductStr });
		}

		return productIdToPack;
	}

	private ProductId getProductIdToPack()
	{
		if (_productIdToPack == null)
		{
			final PackageableRow packageableRow = getSingleSelectedPackageableRow();
			_productIdToPack = packageableRow.getProductId();
		}
		return _productIdToPack;
	}

	private void pickCUs()
	{
		final HuId splitCUId = Services.get(ITrxManager.class).callInNewTrx(this::performPickCU);

		if (isAutoProcess)
		{
			autoProcessPicking(splitCUId);
		}

	}

	private HuId performPickCU()
	{
		final HuId huIdToSplit = retrieveHUIdToSplit();

		final ProductId productId = getProductId();

		// validating the attributes here because it doesn't make sense to split the HU if it can't be used
		huAttributesBL.validateMandatoryPickingAttributes(huIdToSplit, productId);

		final List<I_M_HU> splitHUs = HUSplitBuilderCoreEngine.builder()
				.huToSplit(handlingUnitsDAO.getById(huIdToSplit))
				.requestProvider(this::createSplitAllocationRequest)
				.destination(HUProducerDestination.ofVirtualPI())
				.build()
				.withPropagateHUValues()
				.withAllowPartialUnloads(true) // we allow partial loads and unloads so if a user enters a very large number, then that will just account to "all of it" and there will be no error
				.performSplit();

		final I_M_HU splitCU = CollectionUtils.singleElement(splitHUs);
		final HuId splitCUId = HuId.ofRepoId(splitCU.getM_HU_ID());
		addHUIdToCurrentPickingSlot(splitCUId);

		return splitCUId;
	}

	private IAllocationRequest createSplitAllocationRequest(final IHUContext huContext)
	{
		final ProductId productId = getProductId();
		final I_C_UOM uom = productBL.getStockUOM(productId);

		return AllocationUtils.builder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(getQtyCUsPerTU(), uom)
				.setDate(SystemTime.asZonedDateTime())
				.setFromReferencedModel(null) // N/A
				.setForceQtyAllocation(false)
				.create();
	}

	private void autoProcessPicking(final HuId splitCUId)
	{
		final ShipmentScheduleId shipmentScheduleId = null;
		pickingCandidateService.processForHUIds(ImmutableSet.of(splitCUId), shipmentScheduleId);

	}

	private HuId retrieveHUIdToSplit()
	{
		return retrieveEligibleHUEditorRows()
				.map(HUEditorRow::getHuId)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Only one HU shall be selected")));
	}

}
