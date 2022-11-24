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

import de.metas.document.engine.IDocumentBL;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Client;

/**
 * Interceptor used to intercept invoice candidates relevant documents and manage their life cycle.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class ILHandlerModelInterceptor extends AbstractModelInterceptor
{
	IInvoiceCandidateHandler handler;
	String tableName;
	boolean isDocument;
	DocTimingType createInvoiceCandidatesTiming;
	CandidatesAutoCreateMode initialCandidatesAutoCreateMode;

	CreateCandidatesOnCommitCollector collector = new CreateCandidatesOnCommitCollector();

	public ILHandlerModelInterceptor(@NonNull final IInvoiceCandidateHandler handler)
	{
		this.handler = handler;

		this.tableName = handler.getSourceTable();
		this.initialCandidatesAutoCreateMode = handler.getGeneralCandidatesAutoCreateMode();
		this.createInvoiceCandidatesTiming = handler.getAutomaticallyCreateMissingCandidatesDocTiming();
		this.isDocument = Services.get(IDocumentBL.class).isDocumentTable(this.tableName);
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final boolean interceptDocValidate = isDocument && (initialCandidatesAutoCreateMode.isDoSomething());
		final boolean interceptModelChange = (!isDocument && initialCandidatesAutoCreateMode.isDoSomething());

		if (interceptDocValidate)
		{
			engine.addDocValidate(tableName, this);
		}
		if (interceptModelChange)
		{
			engine.addModelChange(tableName, this);
		}
	}

	@Override
	public void onDocValidate(@NonNull final Object model, @NonNull final DocTimingType timing)
	{
		Check.assume(isDocument, "isDocument flag is set"); // shall not happen

		//
		// Create missing invoice candidates for given document
		if (timing == createInvoiceCandidatesTiming)
		{
			createMissingInvoiceCandidates(model);
		}
	}

	@Override
	public void onModelChange(@NonNull final Object model, @NonNull final ModelChangeType changeType)
	{
		//
		// Create missing invoice candidates for given pseudo-document
		if (!isDocument && changeType.isNewOrChange() && changeType.isAfter())
		{
			createMissingInvoiceCandidates(model);
		}
	}

	/**
	 * Creates missing invoice candidates for given model, if this is enabled.
	 */
	private void createMissingInvoiceCandidates(@NonNull final Object model)
	{
		final CandidatesAutoCreateMode modeForCurrentModel = handler.getSpecificCandidatesAutoCreateMode(model);
		switch (modeForCurrentModel)
		{
			case DONT: // just for completeness. we actually aren't called in this case
				break;
			case CREATE_CANDIDATES:
				CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(model);
				break;
			case CREATE_CANDIDATES_AND_INVOICES:
				generateIcsAndInvoices(model);
				break;
		}
	}

	private void generateIcsAndInvoices(@NonNull final Object model)
	{
		final TableRecordReference modelReference = TableRecordReference.of(model);
		collector.collect(modelReference);
	}
}
