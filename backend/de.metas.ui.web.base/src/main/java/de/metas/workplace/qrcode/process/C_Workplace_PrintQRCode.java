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

import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.JavaProcess;
import de.metas.util.Check;
import de.metas.workplace.Workplace;
import de.metas.workplace.WorkplaceId;
import de.metas.workplace.WorkplaceService;
import de.metas.workplace.qrcode.WorkplaceQRCode;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Workplace;

public class C_Workplace_PrintQRCode extends JavaProcess
{
	private final WorkplaceService workplaceService = SpringContextHolder.instance.getBean(WorkplaceService.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	@Override
	protected String doIt()
	{
		final WorkplaceId workplaceId = getWorkplaceId();
		final Workplace workplace = workplaceService.getById(workplaceId);
		final PrintableQRCode qrCode = WorkplaceQRCode.ofWorkplace(workplace).toPrintableQRCode();

		final QRCodePDFResource pdf = globalQRCodeService.createPDF(qrCode);
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	private WorkplaceId getWorkplaceId()
	{
		Check.assumeEquals(getTableName(), I_C_Workplace.Table_Name, "table");
		return WorkplaceId.ofRepoId(getRecord_ID());
	}
}
