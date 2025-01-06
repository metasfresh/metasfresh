/*
 * #%L
 * de.metas.postfinance
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

package de.metas.postfinance.document.export;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@Service
public class PostFinanceYbInvoiceHandlerFactory
{
	@NonNull private static final Logger logger = LogManager.getLogger(PostFinanceYbInvoiceHandlerFactory.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final PostFinanceYbInvoiceService postFinanceYbInvoiceService;
	@NonNull private final ImmutableList<IPostFinanceYbInvoiceHandler> postFinanceYbInvoiceHandlers;

	public PostFinanceYbInvoiceHandlerFactory(
			@NonNull final PostFinanceYbInvoiceService postFinanceYbInvoiceService,
			@NonNull final List<IPostFinanceYbInvoiceHandler> postFinanceYbInvoiceHandlers)
	{
		this.postFinanceYbInvoiceService = postFinanceYbInvoiceService;
		this.postFinanceYbInvoiceHandlers = ImmutableList.copyOf(postFinanceYbInvoiceHandlers);
	}

	public List<PostFinanceYbInvoiceResponse> prepareYbInvoices(@NonNull final List<PostFinanceYbInvoiceRequest> requests)
	{
		return requests.stream()
				.map(this::prepareYbInvoices)
				.filter(Objects::nonNull)
				.toList();
	}

	@Nullable
	public PostFinanceYbInvoiceResponse prepareYbInvoices(@NonNull final PostFinanceYbInvoiceRequest request)
	{
		trxManager.assertThreadInheritedTrxExists();

		try
		{
			final IPostFinanceYbInvoiceHandler eligibleHandler = getEligibleHandler(request).orElseThrow(PostFinanceExportException::new);
			return eligibleHandler.prepareExportData(request);
		}
		catch (final Exception ex)
		{
			logger.warn("Exception on post finance export", ex);
			postFinanceYbInvoiceService.handleDataExceptions(request.getDocOutboundLogId(), PostFinanceExportException.wrapIfNeeded(ex));
			return null;
		}
	}

	@NotNull
	public ExplainedOptional<IPostFinanceYbInvoiceHandler> getEligibleHandler(@NotNull final PostFinanceYbInvoiceRequest request)
	{
		final List<IPostFinanceYbInvoiceHandler> eligibleHandlers = postFinanceYbInvoiceHandlers.stream()
				.filter(handler -> handler.applies(request))
				.toList();

		if (eligibleHandlers.isEmpty())
		{
			return ExplainedOptional.emptyBecause("No eligible handles found for " + request);
		}
		else if (eligibleHandlers.size() > 1)
		{
			return ExplainedOptional.emptyBecause("More than one eligible handles found for " + request + ": " + eligibleHandlers);
		}
		else
		{
			return ExplainedOptional.of(eligibleHandlers.get(0));
		}
	}
}
