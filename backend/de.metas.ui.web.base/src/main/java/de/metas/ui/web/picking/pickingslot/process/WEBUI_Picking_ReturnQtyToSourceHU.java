package de.metas.ui.web.picking.pickingslot.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.PickingCandidateService;
import de.metas.handlingunits.picking.requests.RemoveQtyFromHURequest;
import de.metas.handlingunits.sourcehu.HuId2SourceHUsService;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.handlingunits.storage.IProductStorage;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.picking.pickingslot.PickingSlotRow;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

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
		implements IProcessPrecondition
{
	private final PickingCandidateService pickingCandidateService = SpringContextHolder.instance.getBean(PickingCandidateService.class);
	private final HuId2SourceHUsService sourceHUsRepository = SpringContextHolder.instance.getBean(HuId2SourceHUsService.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	private static final String PARAM_QTY_CU = "QtyCU";
	@Param(parameterName = PARAM_QTY_CU, mandatory = true)
	private BigDecimal qtyCU;

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

		final String rowType = pickingSlotRow.getType().getName();
		final boolean cuRow = Objects.equals(rowType, HUEditorRowType.VHU.getName()) || Objects.equals(rowType, HUEditorRowType.HUStorage.getName());
		if (cuRow)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_SELECT_PICKED_CU));
		}

		if (!checkSourceHuPreconditionIncludingEmptyHUs())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_WEBUI_PICKING_MISSING_SOURCE_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		if (qtyCU == null || qtyCU.signum() <= 0)
		{
			throw new FillMandatoryException("QtyCU");
		}

		final PickingSlotRow pickingSlotRow = getSingleSelectedRow();
		final HuId huId = pickingSlotRow.getHuId();

		pickingCandidateService.removeQtyFromHU(RemoveQtyFromHURequest.builder()
				.qtyCU(qtyCU)
				.huId(huId)
				.productId(getProductId(huId))
				.build());

		invalidateView();
		invalidateParentView();

		return MSG_OK;
	}

	private boolean checkSourceHuPreconditionIncludingEmptyHUs()
	{
		final HuId huId = getSingleSelectedRow().getHuId();
		final Collection<HuId> sourceHUs = sourceHUsRepository.retrieveMatchingSourceHUIds(huId);
		return !sourceHUs.isEmpty();
	}

	private ProductId getProductId(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsBL.getById(huId);
		final IHUStorageFactory storageFactory = handlingUnitsBL.getStorageFactory();
		final IHUStorage storage = storageFactory.getStorage(hu);

		final ImmutableSet<ProductId> productIds = storage.getProductStorages().stream()
				.map(IProductStorage::getProductId)
				.collect(ImmutableSet.toImmutableSet());

		if (productIds.isEmpty())
		{
			throw new AdempiereException("Selected HU is empty");
		}
		else if (productIds.size() == 1)
		{
			return productIds.iterator().next();
		}
		else
		{
			throw new AdempiereException("Selected HU has more than one product");
		}
	}
}
