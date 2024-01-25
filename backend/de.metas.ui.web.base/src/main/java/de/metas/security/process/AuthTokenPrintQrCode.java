/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.security.process;

import de.metas.global_qrcodes.service.GlobalQRCodeService;
import de.metas.global_qrcodes.service.QRCodePDFResource;
import de.metas.process.JavaProcess;
import de.metas.security.UserAuthToken;
import de.metas.security.UserAuthTokenId;
import de.metas.security.UserAuthTokenRepository;
import de.metas.security.qr.UserAuthQRCode;
import org.compiere.SpringContextHolder;

public class AuthTokenPrintQrCode extends JavaProcess
{
	private final UserAuthTokenRepository userAuthTokenRepository = SpringContextHolder.instance.getBean(UserAuthTokenRepository.class);
	private final GlobalQRCodeService globalQRCodeService = SpringContextHolder.instance.getBean(GlobalQRCodeService.class);

	@Override
	protected String doIt() throws Exception
	{
		final UserAuthToken authToken = userAuthTokenRepository.getById(UserAuthTokenId.ofRepoId(getRecord_ID()));
		final QRCodePDFResource pdf = globalQRCodeService.createPDF(UserAuthQRCode.ofAuthToken(authToken).toPrintableQRCode());

		getResult().setReportData(pdf, pdf.getFilename(), pdf.getContentType());

		return MSG_OK;
	}
}
