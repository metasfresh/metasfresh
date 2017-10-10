package de.metas.ui.web.pporder.process;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.impl.HUIterator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.sourcehu.ISourceHuService;
import de.metas.handlingunits.sourcehu.ISourceHuService.ActiveSourceHusQuery;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import lombok.NonNull;

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

public class WEBUI_PP_Order_IssueQty
		extends WEBUI_PP_Order_Template
		implements IProcessPrecondition
{
	@Param(parameterName = "QtyCU", mandatory = true)
	private BigDecimal qtyCU;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!getSelectedDocumentIds().isSingleDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}
		final PPOrderLineRow singleSelectedRow = getSingleSelectedRow();
		if (!singleSelectedRow.isIssue())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is not an issue-row; row={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}
		if (singleSelectedRow.isProcessed())
		{
			final String internalReason = StringUtils.formatMessage("The selected row is already processed; row={}", singleSelectedRow);
			return ProcessPreconditionsResolution.rejectWithInternalReason(internalReason);
		}

		// TODO check if we have a matching source HU
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final PPOrderLineRow row = getSingleSelectedRow();

		final List<I_M_HU> sourceHus = retrieveActiveSourceHus(row);
		if (sourceHus.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		final ImmutableList<I_M_HU> extractedCUs = extractCuHUs(retrieveCuHUs());

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

	private static List<I_M_HU> retrieveActiveSourceHus(@NonNull final PPOrderLineRow row)
	{
		final I_PP_Order ppOrder = load(row.getPP_Order_ID(), I_PP_Order.class);

		return Services.get(ISourceHuService.class).retrieveActiveSourceHUs(ActiveSourceHusQuery.builder().productId(row.getM_Product_ID()).warehouseId(ppOrder.getM_Warehouse_ID()).build());
	}

	private static ImmutableList<I_M_HU> retrieveCuHUs()
	{
		final Builder<I_M_HU> cuHUs = ImmutableList.builder();
		new HUIterator().setEnableStorageIteration(false)
				.setCtx(Env.getCtx())
				.setDate(SystemTime.asTimestamp())
				.setListener(new HUIteratorListenerAdapter()
				{
					@Override
					public Result afterHU(final I_M_HU hu)
					{
						final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
						if (handlingUnitsBL.isAggregateHU(hu) || handlingUnitsBL.isVirtual(hu))
						{
							cuHUs.add(hu);
						}
						return getDefaultResult();
					}
				});
		return cuHUs.build();
	}


	private ImmutableList<I_M_HU> extractCuHUs(@NonNull final ImmutableList<I_M_HU> sourceCuHUs)
	{
		BigDecimal qtyLeftToExtract = qtyCU;
		
		final HUTransformService huTransformService = HUTransformService.get(Env.getCtx());
		
		final Builder<I_M_HU> extractedCUs = ImmutableList.builder();
		for (I_M_HU sourceCuHU : sourceCuHUs)
		{
			final BigDecimal sourceCuHuQty = huTransformService.getMaximumQtyCU(sourceCuHU);
			final BigDecimal qtyToExtractFromSourceCuHu = sourceCuHuQty.min(qtyCU);
			
			final List<I_M_HU> newlyExtractedCUs = huTransformService.cuToNewCU(sourceCuHU, qtyToExtractFromSourceCuHu);
			extractedCUs.addAll(newlyExtractedCUs);
			
			qtyLeftToExtract = qtyLeftToExtract.subtract(qtyToExtractFromSourceCuHu);
			if (qtyLeftToExtract.signum() <= 0)
			{
				break;
			}
		}
		return extractedCUs.build();
	}
}
