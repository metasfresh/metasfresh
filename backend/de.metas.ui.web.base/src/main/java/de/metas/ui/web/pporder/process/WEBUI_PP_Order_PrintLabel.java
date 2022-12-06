/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.pporder.process;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RunOutOfTrx;
import de.metas.product.ProductId;
import de.metas.report.PrintFormat;
import de.metas.report.PrintFormatId;
import de.metas.report.PrintFormatRepository;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLineType;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_PrintFormat;
import org.compiere.util.DB;
import org.springframework.core.io.ByteArrayResource;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class WEBUI_PP_Order_PrintLabel extends WEBUI_PP_Order_Template implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private static final String PARAM_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	@Param(parameterName = PARAM_AD_PrintFormat_ID)
	private int printFormatId;

	/**
	 * This is only needed as a way to default the @M_Product_ID@ for the dynamic validation of the print format. If selecting a HU/HuStorage, then M_Product_ID cannot be resolved by the evaluation context without this parameter.
	 */
	@Param(parameterName = I_M_Product.COLUMNNAME_M_Product_ID)
	private ProductId productId;

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	final private PrintFormatRepository pfRepo = SpringContextHolder.instance.getBean(PrintFormatRepository.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{

		final Set<HuId> distinctHuIds = retrieveSelectedHuIds();
		if (distinctHuIds.isEmpty() || !hasPrintFormatAssigned())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	@RunOutOfTrx
	protected String doIt()
	{
		// create selection
		final Set<HuId> distinctHuIds = retrieveSelectedHuIds();
		DB.createT_Selection(getPinstanceId(), distinctHuIds, ITrx.TRXNAME_None);

		// print
		final ReportResult label = printLabel();

		// preview
		getResult().setReportData(new ByteArrayResource(label.getReportContent()), buildFilename(), OutputType.PDF.getContentType());

		return MSG_OK;

	}

	private Set<HuId> retrieveSelectedHuIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();

		final ImmutableList<PPOrderLineRow> selectedRows = getView()
				.streamByIds(selectedRowIds)
				.collect(ImmutableList.toImmutableList());

		final Set<HuId> huIds = new HashSet<>();

		for (final PPOrderLineRow row : selectedRows)
		{
			final PPOrderLineType type = row.getType();

			if (type.isMainProduct() && row.isReceipt())
			{
				final ImmutableList<PPOrderLineRow> includedRows = row.getIncludedRows();
				includedRows.stream()
						.filter(ppOrderLineRow -> ppOrderLineRow.getType().isHUOrHUStorage())
						.map(PPOrderLineRow::getHuId)
						.filter(Objects::nonNull)
						.forEach(huIds::add);
			}
			else if (type.isHUOrHUStorage())
			{
				final HuId huId = row.getHuId();

				if (huId != null && isMainProductHu(huId))
				{
					huIds.add(huId);
				}
			}
		}

		return huIds;
	}

	private boolean isMainProductHu(final HuId huId)
	{
		return getView().streamByIds(DocumentIdsSelection.ALL)
				.filter(row -> row.getType().isMainProduct() || row.isReceipt())
				.flatMap(row -> row.getIncludedRows().stream())
				.filter(row -> row.getType().isHUOrHUStorage())
				.map(PPOrderLineRow::getHuId)
				.anyMatch(huId::equals);
	}

	private boolean hasPrintFormatAssigned()
	{
		final int cnt = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product_PrintFormat.class)
				.addInArrayFilter(I_M_Product_PrintFormat.COLUMNNAME_M_Product_ID, retrieveSelectedProductIDs())
				.create()
				.count();

		return cnt > 0;
	}

	private Set<ProductId> retrieveSelectedProductIDs()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();

		final ImmutableList<PPOrderLineRow> selectedRows = getView()
				.streamByIds(selectedRowIds)
				.collect(ImmutableList.toImmutableList());

		final Set<ProductId> productIds = new HashSet<>();

		for (final PPOrderLineRow row : selectedRows)
		{
			productIds.add(row.getProductId());
		}

		return productIds;
	}

	private ReportResult printLabel()
	{
		final PInstanceRequest pinstanceRequest = createPInstanceRequest();
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(pinstanceRequest);

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(getCtx())
				.setAD_Process_ID(getPrintFormat().getReportProcessId())
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setReportLanguage(getProcessInfo().getReportLanguage())
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();

		return reportsClient.report(jasperProcessInfo);
	}

	private PInstanceRequest createPInstanceRequest()
	{

		return PInstanceRequest.builder()
				.processId(getPrintFormat().getReportProcessId())
				.processParams(ImmutableList.of(
						ProcessInfoParameter.of("AD_PInstance_ID", getPinstanceId()),
						ProcessInfoParameter.of("AD_PrintFormat_ID", printFormatId)))
				.build();
	}

	private String buildFilename()
	{
		final String instance = String.valueOf(getPinstanceId().getRepoId());
		final String title = getProcessInfo().getTitle();

		return Joiner.on("_").skipNulls().join(instance, title) + ".pdf";
	}

	private PrintFormat getPrintFormat()
	{
		return pfRepo.getById(PrintFormatId.ofRepoId(printFormatId));
	}

	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(I_M_Product.COLUMNNAME_M_Product_ID, parameter.getColumnName()))
		{
			return retrieveSelectedProductIDs().stream().findFirst().orElse(null);
		}
		return DEFAULT_VALUE_NOTAVAILABLE;
	}

}
