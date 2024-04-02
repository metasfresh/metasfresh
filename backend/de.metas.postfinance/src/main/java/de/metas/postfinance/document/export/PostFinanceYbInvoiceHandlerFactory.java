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

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
public class PostFinanceYbInvoiceHandlerFactory
{
	private final List<IPostFinanceYbInvoiceHandler> postFinanceYbInvoiceHandlers;
	private static final Logger logger = Logger.getLogger(PostFinanceYbInvoiceHandlerFactory.class.getName());
	private final PostFinanceYbInvoiceService postFinanceYbInvoiceService;


	@Nullable
	public PostFinanceYbInvoiceResponse prepareYbInvoices(@NonNull final PostFinanceYbInvoiceRequest postFinanceYbInvoiceRequest)
	{
		final List<IPostFinanceYbInvoiceHandler> eligibleHandlers = postFinanceYbInvoiceHandlers.stream()
				.filter(handler -> handler.applies(postFinanceYbInvoiceRequest))
				.toList();

		try
		{
			if(eligibleHandlers.isEmpty())
			{
				// ignore docTypes without matching handler
				return null;
			}
			else if(eligibleHandlers.size() > 1)
			{
				throw new PostFinanceExportException("More than one handler found for this docType");
			}
			else
			{
				return eligibleHandlers.get(0).prepareExportData(postFinanceYbInvoiceRequest);
			}
		}
		catch(final PostFinanceExportException e)
		{
			logger.log(Level.WARNING, "Exception on post finance export " + e.getMessage(), e);
			postFinanceYbInvoiceService.handleExceptions(postFinanceYbInvoiceRequest.getDocOutboundLogReference(), e);
			return null;
		}
	}
}
