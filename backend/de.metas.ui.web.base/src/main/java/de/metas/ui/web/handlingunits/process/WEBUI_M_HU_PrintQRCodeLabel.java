package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.Profiles;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodeGenerateForExistingHUsRequest;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.process.JavaProcess;
import de.metas.report.server.OutputType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

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
@Profile(Profiles.PROFILE_Webui)
public class WEBUI_M_HU_PrintQRCodeLabel extends JavaProcess
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final HUQRCodesService huQRCodesService = SpringContextHolder.instance.getBean(HUQRCodesService.class);

	@Override
	protected String doIt()
	{
		final ImmutableSet<HuId> huIds = getHuIdsToPrint();
		final ImmutableList<HUQRCode> qrCodes = generateQrCodes(huIds);

		final Resource pdf = huQRCodesService.createPDF(qrCodes.asList(), false);
		getResult().setReportData(pdf, pdf.getFilename(), OutputType.PDF.getContentType());

		return MSG_OK;
	}

	private ImmutableSet<HuId> getHuIdsToPrint()
	{
		return queryBL.createQueryBuilder(I_M_HU.class).setOnlySelection(getPinstanceId())
				.orderBy(I_M_HU.COLUMNNAME_M_HU_ID)
				.create()
				.listIds(HuId::ofRepoId);
	}

	private ImmutableList<HUQRCode> generateQrCodes(@NonNull final ImmutableSet<HuId> huIds)
	{
		return huQRCodesService.generateForExistingHUs(
						HUQRCodeGenerateForExistingHUsRequest.builder()
								.huIds(huIds)
								.build())
				.toList();
	}
}
