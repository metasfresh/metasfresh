/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoicecandidate.modelvalidator.ilhandler;

import de.metas.invoicecandidate.api.CreateInvoiceForModelService;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxOnCommitCollectorFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * This class has the job of collecting model records and then on commit create invoice candidates and invoices for them.
 */
class CreateCandidatesOnCommitCollector extends TrxOnCommitCollectorFactory<List<TableRecordReference>, TableRecordReference>
{
	private final String COLLECTOR_TRXPROPERTYNAME = ILHandlerModelInterceptor.class.getName() + ".collectorFactory";

	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final CreateInvoiceForModelService invoiceSyncCreationService = SpringContextHolder.instance.getBean(CreateInvoiceForModelService.class);
	
	@Override
	protected String getTrxProperyName()
	{
		return COLLECTOR_TRXPROPERTYNAME;
	}

	@Override
	protected String extractTrxNameFromItem(@NonNull final TableRecordReference item)
	{
		final Object model = item.getModel(Object.class);
		return InterfaceWrapperHelper.getTrxName(model);
	}

	@Override
	protected List<TableRecordReference> newCollector(@NonNull final TableRecordReference firstItem)
	{
		return new ArrayList<>();
	}

	@Override
	protected void collectItem(@NonNull final List<TableRecordReference> collector, @NonNull final TableRecordReference item)
	{
		collector.add(item);
	}

	@Override
	protected void processCollector(@NonNull final List<TableRecordReference> collector)
	{
		trxManager.runInNewTrx(() -> invoiceSyncCreationService.generateIcsAndInvoices(collector, null));
	}
}
