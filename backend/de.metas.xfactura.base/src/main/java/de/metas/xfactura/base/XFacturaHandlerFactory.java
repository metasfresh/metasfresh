/*
 * #%L
 * de.metas.xfactura.base
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

package de.metas.xfactura.base;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class XFacturaHandlerFactory
{
	@NonNull private static final Logger logger = LogManager.getLogger(XFacturaHandlerFactory.class);
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final XFacturaService xFacturaService;
	@NonNull private final ImmutableList<IXFacturaHandler> xFacturaHandlers;

	public XFacturaHandlerFactory(
			@NonNull final XFacturaService xFacturaService,
			@NonNull final List<IXFacturaHandler> xFacturaHandlers)
	{
		this.xFacturaService = xFacturaService;
		this.xFacturaHandlers = ImmutableList.copyOf(xFacturaHandlers);
	}

	@Nullable
	public XFacturaResponse prepareXFactura(@NonNull final XFacturaRequest request)
	{
		trxManager.assertThreadInheritedTrxExists();

		try
		{
			final IXFacturaHandler eligibleHandler = getEligibleHandler(request).orElseThrow(AdempiereException::new);
			return eligibleHandler.prepareData(request);
		}
		catch (final Exception ex)
		{
			logger.warn("Exception on generating X-Factura", ex);
			xFacturaService.handleExceptions(request.getDocOutboundLogId(), AdempiereException.wrapIfNeeded(ex));
			return null;
		}
	}

	@NotNull
	public ExplainedOptional<IXFacturaHandler> getEligibleHandler(@NotNull final XFacturaRequest request)
	{
		final List<IXFacturaHandler> eligibleHandlers = xFacturaHandlers.stream()
				.filter(handler -> handler.applies(request))
				.collect(Collectors.toList());

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
