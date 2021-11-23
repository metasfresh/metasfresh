package de.metas.invoicecandidate.modelvalidator;

import com.google.common.base.MoreObjects;
import de.metas.document.engine.IDocumentBL;
import de.metas.invoicecandidate.api.InvoiceSyncCreationService;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler.CandidatesAutoCreateMode;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Client;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Interceptor used to intercept invoice candidates relevant documents and manage their life cycle.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
final class ILHandlerModelInterceptor extends AbstractModelInterceptor
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	
	public static Builder builder()
	{
		return new Builder();
	}

	private final String tableName;
	private final boolean isDocument;
	private final CandidatesAutoCreateMode candidatesAutoCreateMode;
	private final DocTimingType createInvoiceCandidatesTiming;
	private final InvoiceSyncCreationService invoiceSyncCreationService = SpringContextHolder.instance.getBean(InvoiceSyncCreationService.class);

	private ILHandlerModelInterceptor(@NonNull final Builder builder)
	{
		this.tableName = builder.getTableName();
		this.candidatesAutoCreateMode = builder.getCandidatesAutoCreateMode();
		this.createInvoiceCandidatesTiming = builder.getCreateInvoiceCandidatesTiming();

		this.isDocument = Services.get(IDocumentBL.class).isDocumentTable(tableName);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tableName", tableName)
				.add("isCreateInvoiceCandidates", candidatesAutoCreateMode)
				.add("createInvoiceCandidatesTiming", createInvoiceCandidatesTiming)
				.toString();
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final boolean interceptDocValidate = isDocument && (candidatesAutoCreateMode.isDoSomething());
		final boolean interceptModelChange = (!isDocument && candidatesAutoCreateMode.isDoSomething());

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
		switch (candidatesAutoCreateMode)
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

		trxManager.runAfterCommit(() -> trxManager.runInNewTrx(() -> invoiceSyncCreationService.generateIcsAndInvoices(modelReference)));
	}

	/**
	 * {@link ILHandlerModelInterceptor} instance builder
	 */
	public static final class Builder
	{
		private String tableName;
		private CandidatesAutoCreateMode candidatesAutoCreateMode = CandidatesAutoCreateMode.DONT;
		private DocTimingType createInvoiceCandidatesTiming = DocTimingType.AFTER_COMPLETE;

		private Builder()
		{
			super();
		}

		public ILHandlerModelInterceptor build()
		{
			return new ILHandlerModelInterceptor(this);
		}

		public Builder setTableName(@NonNull final String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		private String getTableName()
		{
			Check.assumeNotEmpty(tableName, "tableName not empty");
			return tableName;
		}

		public Builder setCandidatesAutoCreateMode(@NonNull final CandidatesAutoCreateMode candidatesAutoCreateMode)
		{
			this.candidatesAutoCreateMode = candidatesAutoCreateMode;
			return this;
		}

		private CandidatesAutoCreateMode getCandidatesAutoCreateMode()
		{
			return candidatesAutoCreateMode;
		}

		private DocTimingType getCreateInvoiceCandidatesTiming()
		{
			return createInvoiceCandidatesTiming;
		}

		public Builder setCreateInvoiceCandidatesTiming(@NonNull final DocTimingType createInvoiceCandidatesTiming)
		{
			this.createInvoiceCandidatesTiming = createInvoiceCandidatesTiming;
			return this;
		}
	}
}
