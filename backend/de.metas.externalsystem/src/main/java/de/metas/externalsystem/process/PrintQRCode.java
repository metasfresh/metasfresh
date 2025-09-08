/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.process;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.IExternalSystemChildConfigId;
import de.metas.externalsystem.config.qrcode.ExternalSystemConfigQRCode;
import de.metas.externalsystem.leichmehl.ExternalSystemLeichMehlConfigId;
import de.metas.externalsystem.model.I_ExternalSystem_Config_LeichMehl;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.JavaProcess;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

public class PrintQRCode extends JavaProcess
{
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	@Override
	protected String doIt()
	{
		final ExternalSystemConfigQRCode qrCode = ExternalSystemConfigQRCode.builder()
				.childConfigId(getChildConfigId())
				.build();

		final PrintableQRCode printableQRCode = qrCode.toPrintableQRCode();
		final QRCodePDFResource pdf = globalQRCodeService.createPDF(ImmutableList.of(printableQRCode));
		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}

	@NonNull
	private IExternalSystemChildConfigId getChildConfigId()
	{
		final String tableName = getTableName();
		if (I_ExternalSystem_Config_LeichMehl.Table_Name.equals(tableName))
		{
			return ExternalSystemLeichMehlConfigId.ofRepoId(getRecord_ID());
		}
		else
		{
			throw new AdempiereException("Unsupported child config table: " + tableName);
		}
	}
}
