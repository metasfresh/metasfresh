/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace.qrcode.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.qrcode.WorkplaceQRCode;
import org.compiere.SpringContextHolder;

import java.util.Collection;

public class C_Workplace_PrintQRCode_ViewSelection extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final Collection<Workplace> workplaces = getWorkplaces();
		final ImmutableList<PrintableQRCode> qrCodes = toPrintableQRCodes(workplaces);

		final QRCodePDFResource pdf = globalQRCodeService.createPDF(qrCodes);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private Collection<Workplace> getWorkplaces()
	{
		final ImmutableSet<WorkplaceId> workplaceIds = streamSelectedRows()
				.map(row -> row.getId().toId(WorkplaceId::ofRepoId))
				.collect(ImmutableSet.toImmutableSet());

		return workplaceService.getByIds(workplaceIds);
	}

	private static ImmutableList<PrintableQRCode> toPrintableQRCodes(final Collection<Workplace> workplaces)
	{
		return workplaces.stream()
				.map(WorkplaceQRCode::ofWorkplace)
				.map(WorkplaceQRCode::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());
	}

}
