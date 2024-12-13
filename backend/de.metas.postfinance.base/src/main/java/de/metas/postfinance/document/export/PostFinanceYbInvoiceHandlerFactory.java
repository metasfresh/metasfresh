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

import de.metas.logging.LogManager;
import de.metas.postfinance.docoutboundlog.PostFinanceLogCreateRequest;
import de.metas.postfinance.docoutboundlog.PostFinanceLogRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
@AllArgsConstructor
public class PostFinanceYbInvoiceHandlerFactory
{
	@NonNull private static final Logger logger = LogManager.getLogger(PostFinanceYbInvoiceHandlerFactory.class);
	@NonNull private final List<IPostFinanceYbInvoiceHandler> postFinanceYbInvoiceHandlers;
	@NonNull private final PostFinanceYbInvoiceService postFinanceYbInvoiceService;
	@NonNull private final PostFinanceLogRepository postFinanceLogRepository;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);

	@Nullable
	public PostFinanceYbInvoiceResponse prepareYbInvoices(@NonNull final PostFinanceYbInvoiceRequest request)
	{
		trxManager.assertThreadInheritedTrxExists();
		final List<IPostFinanceYbInvoiceHandler> eligibleHandlers = postFinanceYbInvoiceHandlers.stream()
				.filter(handler -> handler.applies(request))
				.toList();

		Check.assume(eligibleHandlers.size() == 1, "Exactly one matching handler should be found");
		try
		{
			return eligibleHandlers.get(0).prepareExportData(request);
		}
		catch (final PostFinanceExportException e)
		{
			logger.warn("Exception on post finance export", e);
			postFinanceYbInvoiceService.handleDataExceptions(request.getDocOutboundLogId(), e);
			return null;
		}
	}

	public boolean hasEligibleHandler(@NonNull final PostFinanceYbInvoiceRequest request)
	{
		trxManager.assertThreadInheritedTrxNotExists();
		final List<IPostFinanceYbInvoiceHandler> eligibleHandlers = postFinanceYbInvoiceHandlers.stream()
				.filter(handler -> handler.applies(request))
				.toList();

		try
		{
			if (eligibleHandlers.isEmpty())
			{
				trxManager.runInNewTrx(() -> {
					// ignore docTypes without matching handler
					postFinanceYbInvoiceService.setPostFinanceStatusForSkipped(request.getDocOutboundLogReference());

					postFinanceLogRepository.create(PostFinanceLogCreateRequest.builder()
							.docOutboundLogId(request.getDocOutboundLogId())
							.message("Skipped because of no matching PostFinanceHandler")
							.build());
				});

				return false;
			}
			else if (eligibleHandlers.size() > 1)
			{
				throw new PostFinanceExportException("More than one handler found for this docType");
			}
			else
			{
				return true;
			}
		}
		catch (final PostFinanceExportException e)
		{
			logger.warn("Exception on post finance export", e);
			trxManager.runInNewTrx(() -> postFinanceYbInvoiceService.handleDataExceptions(request.getDocOutboundLogId(), e));
			return false;
		}
	}
}
