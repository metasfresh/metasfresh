package de.metas.ui.web.pporder.process;

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
import de.metas.logging.LogManager;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.material.planning.pporder.PPOrderId;
import de.metas.organization.ClientAndOrgId;
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
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private final Logger logger = LogManager.getLogger(WEBUI_PP_Order_M_Source_HU_IssueCUQty.class);

	private final IPPOrderBOMBL ppOrderBomBL = Services.get(IPPOrderBOMBL.class);

	private static final String PARAM_QtyCU = "QtyCU";

	private static final String PARAM_IsShowAllParams = "IsShowAllParams";

	/**
	 * Qty CU to be issued
	 */
	@Param(parameterName = PARAM_QtyCU)
	private BigDecimal qtyCU;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (isSingleSelectedRow())
		{
			final PPOrderLineRow singleSelectedRow = getSingleSelectedRow();
			return WEBUI_PP_Order_ProcessHelper.checkIssueSourceDefaultPreconditionsApplicable(singleSelectedRow);
		}
		else
		{
			final boolean allMatch = streamPPOrderLineRows()
					.allMatch(row -> WEBUI_PP_Order_ProcessHelper.checkIssueSourceDefaultPreconditionsApplicable(row).isAccepted());
			if (allMatch)
			{
				return ProcessPreconditionsResolution.accept();
			}

			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		streamPPOrderLineRows().forEach(this::issue);
		getView().invalidateAll();
		return MSG_OK;
	}

	private boolean isSingleSelectedRow()
	{
		return getSelectedRowIds().isSingleDocumentId();
	}

	private void issue(final PPOrderLineRow row)
	{
		final List<I_M_Source_HU> sourceHus = WEBUI_PP_Order_ProcessHelper.retrieveActiveSourceHus(row);
		if (sourceHus.isEmpty())
		{
			new AdempiereException("@NoSelection@" + row).throwIfDeveloperModeOrLogWarningElse(logger);
			return;
		}

		final Map<Integer, I_M_Source_HU> huId2SourceHu = new HashMap<>();

		final ImmutableList<I_M_HU> husThatAreFlaggedAsSource = sourceHus.stream()
				.peek(sourceHu -> huId2SourceHu.put(sourceHu.getM_HU_ID(), sourceHu))
				.sorted(Comparator.comparing(I_M_Source_HU::getM_HU_ID))
				.map(I_M_Source_HU::getM_HU)
				.collect(ImmutableList.toImmutableList());

		final Quantity qty = isSingleSelectedRow()
				? Quantity.of(qtyCU, row.getUom())
				: computeQtyToIssue(row);

		final HUsToNewCUsRequest request = HUsToNewCUsRequest
				.builder()
				.sourceHUs(husThatAreFlaggedAsSource)
				.productId(row.getProductId())
				.qtyCU(qty)
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

		final PPOrderId ppOrderId = ppOrderView.getPpOrderId();
		Services.get(IHUPPOrderBL.class)
				.createIssueProducer(ppOrderId)
				.considerIssueMethodForQtyToIssueCalculation(false) // issue exactly the CUs we split
				.createIssues(extractedCUs);
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_QtyCU.equals(parameter.getColumnName()) && isSingleSelectedRow())
		{
			return computeQtyToIssue(getSingleSelectedRow());
		}
		else if (PARAM_IsShowAllParams.equals(parameter.getColumnName()))
		{
			return isSingleSelectedRow();
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	private Quantity computeQtyToIssue(final PPOrderLineRow row)
	{
		final I_PP_Order_BOMLine bomLine = Services.get(IPPOrderBOMDAO.class).getOrderBOMLineById(row.getOrderBOMLineId());
		final List<I_M_Source_HU> activeSourceHus = WEBUI_PP_Order_ProcessHelper.retrieveActiveSourceHus(row);

		final I_M_HU hu = activeSourceHus
				.stream()
				.sorted(Comparator.comparing(I_M_Source_HU::getM_HU_ID))
				.map(I_M_Source_HU::getM_HU)
				.findFirst()
				.orElseThrow(() -> new AdempiereException("@NoSelection@"));
		final IMutableHUContext huContext = Services.get(IHandlingUnitsBL.class).createMutableHUContext(getCtx(), ClientAndOrgId.ofClientAndOrg(hu.getAD_Client_ID(), hu.getAD_Org_ID()));

		final List<IHUProductStorage> productStorages = huContext.getHUStorageFactory().getStorage(hu).getProductStorages();

		final BOMComponentIssueMethod issueMethod = row.getIssueMethod();

		if (BOMComponentIssueMethod.IssueOnlyForReceived.equals(issueMethod))
		{
			final Quantity qtyLeftToIssue = row.getQtyPlan().subtract(row.getQty());

			if (qtyLeftToIssue.signum() <= 0)
			{
				return qtyLeftToIssue.toZero();
			}

			if (row.isProcessed())
			{
				final Quantity quantityToIssueForWhatWasReceived = ppOrderBomBL.computeQtyToIssueBasedOnFinishedGoodReceipt(bomLine, row.getUom());
				return qtyLeftToIssue.min(quantityToIssueForWhatWasReceived);
			}
			else
			{
				return qtyLeftToIssue;
			}

		}
		else
		{
			return productStorages.get(0).getQty();
		}
	}

}
