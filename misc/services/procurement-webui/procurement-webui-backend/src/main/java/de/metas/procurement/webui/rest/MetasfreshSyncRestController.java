/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.rest;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.config.ProcurementWebuiProperties;
import de.metas.procurement.webui.exceptions.NotLoggedInException;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Rfq;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.repository.BPartnerRepository;
import de.metas.procurement.webui.service.I18N;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.service.IRfQService;
import de.metas.procurement.webui.sync.ISenderToMetasfreshService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(MetasfreshSyncRestController.ENDPOINT)
public class MetasfreshSyncRestController
{
	public static final String ENDPOINT = Application.ENDPOINT_ROOT + "/metasfreshSync";

	@SuppressWarnings("FieldCanBeLocal")
	private final Logger logger = LoggerFactory.getLogger(MetasfreshSyncRestController.class);
	private final ISenderToMetasfreshService senderToMetasfreshService;
	private final IProductSuppliesService productSuppliesService;
	private final IRfQService rfqService;
	private final BPartnerRepository bpartnersRepository;
	private final I18N i18n;

	private final String apiKey;

	public MetasfreshSyncRestController(
			@NonNull final ProcurementWebuiProperties config,
			@NonNull final ISenderToMetasfreshService senderToMetasfreshService,
			@NonNull final IProductSuppliesService productSuppliesService,
			@NonNull final IRfQService rfqService,
			@NonNull final BPartnerRepository bpartnersRepository,
			@NonNull final I18N i18n)
	{
		this.senderToMetasfreshService = senderToMetasfreshService;
		this.productSuppliesService = productSuppliesService;
		this.rfqService = rfqService;
		this.bpartnersRepository = bpartnersRepository;
		this.i18n = i18n;

		this.apiKey = config.getMetasfreshSync().getApiKey();
		logger.info("API Key: {}", apiKey);
	}

	private void assertValidApiKey(final String apiKey)
	{
		if (!Objects.equals(this.apiKey, apiKey))
		{
			throw new NotLoggedInException(i18n.get("LoginService.error.notLoggedIn"));
		}
	}

	@GetMapping("/pullFromAppServer")
	public void pullFromAppServer(
			@RequestHeader("apiKey") final String apiKey)
	{
		assertValidApiKey(apiKey);
		senderToMetasfreshService.requestFromMetasfreshAllMasterdataAsync();
	}

	@GetMapping("/pushReports")
	public void pushReports(
			@RequestHeader("apiKey") final String apiKey,
			@RequestParam(name = "bpartnerId", required = false, defaultValue = "0") final long bpartnerId,
			@RequestParam(name = "productId", required = false, defaultValue = "0") final long productId,
			@RequestParam("dateFrom") final String dateFromStr,
			@RequestParam("dateTo") final String dateToStr)
	{
		assertValidApiKey(apiKey);

		final LocalDate dateFrom = LocalDate.parse(dateFromStr);
		final LocalDate dateTo = LocalDate.parse(dateToStr);

		pushDailyReports(bpartnerId, productId, dateFrom, dateTo);
		pushWeeklyReports(bpartnerId, productId, dateFrom, dateTo);
	}

	private void pushDailyReports(
			final long bpartnerId,
			final long productId,
			final LocalDate dateFrom,
			final LocalDate dateTo)
	{
		final List<ProductSupply> productSupplies = productSuppliesService.getProductSupplies(
				bpartnerId,
				productId,
				dateFrom,
				dateTo);

		senderToMetasfreshService.pushDailyReportsAsync(productSupplies);
	}

	private void pushWeeklyReports(
			final long bpartnerId,
			final long productId,
			final LocalDate dateFrom,
			final LocalDate dateTo)
	{
		final List<WeekSupply> records = productSuppliesService.getWeeklySupplies(
				bpartnerId,
				productId,
				dateFrom,
				dateTo);

		senderToMetasfreshService.pushWeeklyReportsAsync(records);
	}

	@GetMapping("/pushActiveRfQs")
	public void pushActiveRfQs(
			@RequestHeader("apiKey") final String apiKey,
			@RequestParam(name = "bpartnerId", required = false, defaultValue = "0") final long bpartnerId)
	{
		assertValidApiKey(apiKey);

		final BPartner bpartner = bpartnerId > 0 ? bpartnersRepository.getOne(bpartnerId) : null;
		final List<Rfq> rfqs = rfqService.getActiveRfqs(bpartner);
		senderToMetasfreshService.pushRfqsAsync(rfqs);
	}
}
