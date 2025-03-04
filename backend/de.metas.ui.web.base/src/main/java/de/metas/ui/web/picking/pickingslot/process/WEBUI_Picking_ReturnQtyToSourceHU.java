package de.metas.ui.web.picking.pickingslot.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.RemoveQtyFromHURequest;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.ui.web.picking.pickingslot.PickingSlotRowType;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_MISSING_SOURCE_HU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_CU;
import static de.metas.ui.web.picking.PickingConstants.MSG_WEBUI_PICKING_SELECT_PICKED_HU;

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

public class WEBUI_Picking_ReturnQtyToSourceHU
		extends WEBUI_Picking_With_M_Source_HU_Base
		implements IProcessPrecondition, IProcessDefaultParametersProvider, IProcessParametersCallout
{
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);
	private final HuId2SourceHUsService sourceHUsRepository = SpringContextHolder.instance.getBean(HuId2SourceHUsService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final String PARAM_QtyCUsPerTU = "QtyCUsPerTU";
	@Param(parameterName = PARAM_QtyCUsPerTU, mandatory = true)
	private BigDecimal qtyCUsPerTU;

	private static final String PARAM_M_Product_ID = "M_Product_ID";
	@Param(parameterName = PARAM_M_Product_ID, mandatory = true)
	private ProductId productId;

	private ImmutableList<IHUProductStorage> _huProductStorages = null; // lazy

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		if (!pickingSlotRow.isPickedHURow())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_HU));
		}

		final PickingSlotRowType rowType = pickingSlotRow.getType();
		if ((rowType.isCU() && !pickingSlotRow.isTopLevelHU())
				|| rowType.isHUStorage())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_CU));
		}

		if (!checkSourceHuPreconditionIncludingEmptyHUs())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_M_Product_ID.equals(parameter.getColumnName()))
		{
			final List<ProductId> productIds = getProductIds();
			return !productIds.isEmpty() ? productIds.get(0) : null;
		}
		if (PARAM_QtyCUsPerTU.equals(parameter.getColumnName()))
		{
			final List<ProductId> productIds = getProductIds();
			final ProductId productId = !productIds.isEmpty() ? productIds.get(0) : null;
			if (productId != null)
			{
				return getHUStorageQty(productId).toBigDecimal();
			}
			else
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@ProcessParamLookupValuesProvider(
			parameterName = PARAM_M_Product_ID,
			numericKey = true,
			lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.list,
			lookupTableName = I_M_Product.Table_Name)
	@NonNull
	public LookupValuesList getAvailableProducts()
	{
		final List<ProductId> productIds = getProductIds();
		return LookupDataSourceFactory.sharedInstance().searchInTableLookup(I_M_Product.Table_Name).findByIdsOrdered(productIds);
	}

	@Override
	public void onParameterChanged(@NonNull final String parameterName)
	{
		if (PARAM_M_Product_ID.equals(parameterName))
		{
			if (productId != null)
			{
				qtyCUsPerTU = getHUStorageQty(productId).toBigDecimal();
			}
		}
	}

	@Override
	protected String doIt()
	{
		if (productId == null)
		{
			throw new FillMandatoryException(PARAM_M_Product_ID);
		}
		if (qtyCUsPerTU == null || qtyCUsPerTU.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_QtyCUsPerTU);
		}

		final HuId huId = getSelectedHUId();

		final Quantity qtyToRemove = Quantity.of(qtyCUsPerTU, getHUStorageQty(productId).getUOM());

		pickingCandidateService.removeQtyFromHU(RemoveQtyFromHURequest.builder()
				.qtyToRemove(qtyToRemove)
				.huId(huId)
				.productId(productId)
				.build());

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		invalidateView();
		invalidateParentView();
	}

	private HuId getSelectedHUId()
	{
		return getSingleSelectedRow().getHuId();
	}

	private boolean checkSourceHuPreconditionIncludingEmptyHUs()
	{
		final HuId huId = getSelectedHUId();
		final Collection<HuId> sourceHUs = sourceHUsRepository.retrieveMatchingSourceHUIds(huId);
		return !sourceHUs.isEmpty();
	}

	private List<ProductId> getProductIds()
	{
		return getHUProductStorages()
				.stream()
				.filter(productStorage -> !productStorage.isEmpty())
				.map(IProductStorage::getProductId)
				.distinct()
				.collect(ImmutableList.toImmutableList());
	}

	private Quantity getHUStorageQty(@NonNull final ProductId productId)
	{
		return getHUProductStorages()
				.stream()
				.filter(productStorage -> ProductId.equals(productStorage.getProductId(), productId))
				.map(IHUProductStorage::getQty)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("No Qty found for " + productId));
	}

	private ImmutableList<IHUProductStorage> getHUProductStorages()
	{
		ImmutableList<IHUProductStorage> huProductStorage = _huProductStorages;
		if (huProductStorage == null)
		{
			final HuId huId = getSelectedHUId();
			final I_M_HU hu = handlingUnitsBL.getById(huId);
			final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
			huProductStorage = _huProductStorages = ImmutableList.copyOf(storageFactory
					.getStorage(hu)
					.getProductStorages());
		}
		return huProductStorage;
	}
}
