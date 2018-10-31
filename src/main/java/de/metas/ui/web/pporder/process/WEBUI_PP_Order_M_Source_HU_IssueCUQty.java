package de.metas.ui.web.pporder.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Order_BOMLine;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.sourcehu.SourceHUsService;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.pporder.util.WEBUI_PP_Order_ProcessHelper;
import de.metas.util.Services;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class WEBUI_PP_Order_M_Source_HU_IssueCUQty
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IPPOrderBOMBL ppOrderBomBL = Services.get(IPPOrderBOMBL.class);

	private static final String PARAM_QtyCU = "QtyCU";

	/**
	 * Qty CU to be issued
	 */
	@Param(parameterName = PARAM_QtyCU, mandatory = true)
	private BigDecimal qtyCU;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedRowIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final PPOrderLineRow singleSelectedRow = getSingleSelectedRow();

		return WEBUI_PP_Order_ProcessHelper.checkIssueSourceDefaultPreconditionsApplicable(singleSelectedRow);
	}

	@Override
	protected String doIt() throws Exception
	{
		final PPOrderLineRow row = getSingleSelectedRow();

		final List<I_M_Source_HU> sourceHus = WEBUI_PP_Order_ProcessHelper.retrieveActiveSourceHus(row);
		if (sourceHus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final Map<Integer, I_M_Source_HU> huId2SourceHu = new HashMap<>();

		final ImmutableList<I_M_HU> husThatAreFlaggedAsSource = sourceHus.stream()
				.peek(sourceHu -> huId2SourceHu.put(sourceHu.getM_HU_ID(), sourceHu))
				.sorted(Comparator.comparing(I_M_Source_HU::getM_HU_ID))
				.map(I_M_Source_HU::getM_HU)
				.collect(ImmutableList.toImmutableList());

		final HUsToNewCUsRequest request = HUsToNewCUsRequest
				.builder()
				.sourceHUs(husThatAreFlaggedAsSource)
				.productId(row.getProductId())
				.qtyCU(Quantity.of(qtyCU, row.getC_UOM()))
				.build();

		final EmptyHUListener emptyHUListener = EmptyHUListener
				.doBeforeDestroyed(hu -> {
					if (huId2SourceHu.containsKey(hu.getM_HU_ID()))
					{
						SourceHUsService.get().snapshotSourceHU(huId2SourceHu.get(hu.getM_HU_ID()));
					}
				}, "Create snapshot of source-HU before it is destroyed");

		final List<I_M_HU> extractedCUs = HUTransformService.builder()
				.emptyHUListener(emptyHUListener)
				.build()
				.husToNewCUs(request);

		final PPOrderLinesView ppOrderView = getView();

		final int ppOrderId = ppOrderView.getPP_Order_ID();
		Services.get(IHUPPOrderBL.class)
				.createIssueProducer()
				.setTargetOrderBOMLinesByPPOrderId(ppOrderId)
				.createDraftIssues(extractedCUs);

		getView().invalidateAll();
		ppOrderView.invalidateAll();

		return MSG_OK;
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyCU.equals(parameter.getColumnName()))
		{
			final PPOrderLineRow row = getSingleSelectedRow();

			final I_PP_Order_BOMLine bomLine = load(row.getPP_Order_BOMLine_ID(), I_PP_Order_BOMLine.class);
			final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(getCtx());
			final List<I_M_Source_HU> activeSourceHus = WEBUI_PP_Order_ProcessHelper.retrieveActiveSourceHus(row);

			final I_M_HU hu = activeSourceHus
					.stream()
					.sorted(Comparator.comparing(I_M_Source_HU::getM_HU_ID))
					.map(I_M_Source_HU::getM_HU)
					.findFirst()
					.orElseThrow(() -> new AdempiereException("@NoSelection@"));

			final List<IHUProductStorage> productStorages = huContext.getHUStorageFactory().getStorage(hu).getProductStorages();

			final String issueMethod = row.getIssueMethod();

			if (X_PP_Order_BOMLine.ISSUEMETHOD_IssueOnlyForReceived.equals(issueMethod))
			{
				final BigDecimal qtyLeftToIssue = row.getQtyPlan().subtract(row.getQty());

				if (qtyLeftToIssue.signum() <= 0)
				{
					return BigDecimal.ZERO;
				}

				final Quantity quantityToIssueForWhatWasReceived = ppOrderBomBL.calculateQtyToIssueBasedOnFinishedGoodReceipt(bomLine, row.getC_UOM());

				return qtyLeftToIssue.min(quantityToIssueForWhatWasReceived.getAsBigDecimal());

			}
			else
			{
				final BigDecimal sourceHuStorageQty = productStorages.get(0).getQty().getAsBigDecimal();

				return sourceHuStorageQty;
			}
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

}
