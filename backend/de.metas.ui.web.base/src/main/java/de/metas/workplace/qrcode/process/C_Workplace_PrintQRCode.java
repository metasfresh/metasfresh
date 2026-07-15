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

import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.qrcode.WorkplaceQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Workplace;

public class C_Workplace_PrintQRCode extends JavaProcess implements IProcessDefaultParametersProvider
{
	private final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	private static final String PARAM_RenderedQRCode = "RenderedQRCode";

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (parameter.getColumnName().equals(PARAM_RenderedQRCode))
		{
			return getQRCode().toGlobalQRCodeJsonString();
		}

		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	protected String doIt()
	{
		final WorkplaceQRCode qrCode = getQRCode();

		final QRCodePDFResource pdf = globalQRCodeService.createPDF(qrCode.toPrintableQRCode());
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private WorkplaceQRCode getQRCode()
	{
		final WorkplaceId workplaceId = getWorkplaceId();
		final Workplace workplace = workplaceService.getById(workplaceId);
		return WorkplaceQRCode.ofWorkplace(workplace);
	}

	private WorkplaceId getWorkplaceId()
	{
		return getRecordIdAssumingTableName(I_C_Workplace.Table_Name, WorkplaceId::ofRepoId);
	}
}
