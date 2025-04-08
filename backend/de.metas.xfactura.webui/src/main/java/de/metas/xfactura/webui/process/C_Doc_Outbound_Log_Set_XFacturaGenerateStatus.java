/*
 * #%L
 * de.metas.xfactura.webui
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.xfactura.webui.process;

import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ADReferenceService;
import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.document.archive.xfactura.XFacturaGenerateStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;

import java.util.List;

public class C_Doc_Outbound_Log_Set_XFacturaGenerateStatus extends JavaProcess implements IProcessPrecondition
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	@NonNull private final ADReferenceService adReferenceService = ADReferenceService.get();

	private static final String PARAM_TargetGenerateStatus = "XFactura_Generate_Status"; //TODO columname from docOutBound
	private static final AdMessageKey PRECONDITION_MSG_ONLY_ONE_STATUS = AdMessageKey.of("todo");
	private static final AdMessageKey PRECONDITION_MSG_NO_TARGET_STATUS = AdMessageKey.of("todo");

	@Param(parameterName = PARAM_TargetGenerateStatus)
	private String p_TargetGenerateStatus;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if(context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final List<XFacturaGenerateStatus> fromGenerateStatusList = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addFilter(context.getQueryFilter(I_C_Doc_Outbound_Log.class))
				.create()
				.stream()
				.map(I_C_Doc_Outbound_Log::getXFactura_Generate_Status)
				.map(XFacturaGenerateStatus::ofCode)
				.distinct()
				.toList();

		if(fromGenerateStatusList.size() > 1)
		{
			return ProcessPreconditionsResolution.reject(PRECONDITION_MSG_ONLY_ONE_STATUS);
		}

		final List<XFacturaGenerateStatus> toExportStatusList = fromGenerateStatusList.get(0).getAvailableXFacturaGenerateStatuses();
		if(toExportStatusList.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(PRECONDITION_MSG_NO_TARGET_STATUS);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final ImmutableSet<DocOutboundLogId> ids = queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.create()
				.listIds(DocOutboundLogId::ofRepoId);

		ids.forEach(id -> docOutboundDAO.setXFacturaGenerateStatus(id, XFacturaGenerateStatus.ofCode(p_TargetGenerateStatus)));

		return JavaProcess.MSG_OK;
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_TargetGenerateStatus, numericKey = false, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.list)
	private LookupValuesList getTargetExportStatusLookupValues()
	{
		final List<XFacturaGenerateStatus> fromExportStatusList = getXFacturaGenerateStatuses();
		Check.assume(fromExportStatusList.size() == 1, "All records should have the same X-Factura Generate Status");
		final XFacturaGenerateStatus fromGenerateStatus = fromExportStatusList.get(0);

		return fromGenerateStatus.getAvailableXFacturaGenerateStatuses().stream()
				.map(s -> LookupValue.StringLookupValue.of(s.getCode(), adReferenceService.retrieveListNameTranslatableString(XFacturaGenerateStatus.AD_Reference_ID, s.getCode())))
				.collect(LookupValuesList.collect());
	}

	private List<XFacturaGenerateStatus> getXFacturaGenerateStatuses()
	{
		return queryBL.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.addFilter(getProcessInfo().getQueryFilterOrElseFalse())
				.create()
				.stream()
				.map(I_C_Doc_Outbound_Log::getXFactura_Generate_Status)
				.map(XFacturaGenerateStatus::ofCode)
				.distinct()
				.toList();
	}
}
