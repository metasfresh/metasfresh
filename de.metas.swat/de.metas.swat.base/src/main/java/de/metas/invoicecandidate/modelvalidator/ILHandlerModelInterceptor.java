package de.metas.invoicecandidate.modelvalidator;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;

import com.google.common.base.MoreObjects;

import de.metas.document.engine.IDocActionBL;
import de.metas.invoicecandidate.async.spi.impl.CreateMissingInvoiceCandidatesWorkpackageProcessor;

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
 *
 */
final class ILHandlerModelInterceptor extends AbstractModelInterceptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String tableName;
	private final boolean isDocument;
	private final boolean isCreateInvoiceCandidates;
	private final DocTimingType createInvoiceCandidatesTiming;

	private ILHandlerModelInterceptor(final Builder builder)
	{
		super();

		this.tableName = builder.getTableName();
		this.isCreateInvoiceCandidates = builder.isCreateInvoiceCandidates();
		this.createInvoiceCandidatesTiming = builder.getCreateInvoiceCandidatesTiming();

		if (Services.get(IDocActionBL.class).isDocumentTable(tableName))
		{
			isDocument = true;
		}
		else
		{
			isDocument = false;
		}
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tableName", tableName)
				.add("isCreateInvoiceCandidates", isCreateInvoiceCandidates)
				.add("createInvoiceCandidatesTiming", createInvoiceCandidatesTiming)
				.toString();
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final boolean interceptDocValidate = isDocument && (isCreateInvoiceCandidates);
		final boolean interceptModelChange = (!isDocument && isCreateInvoiceCandidates);

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
	public void onDocValidate(final Object model, final DocTimingType timing) throws Exception
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
	public void onModelChange(final Object model, final ModelChangeType changeType) throws Exception
	{
		//
		// Create missing invoice candidates for given pseudo-document
		if (!isDocument && changeType.isNewOrChange() && changeType.isAfter())
		{
			createMissingInvoiceCandidates(model);
		}
	}

	/** Creates missing invoice candidates for given model, if this is enabled. */
	private final void createMissingInvoiceCandidates(final Object model)
	{
		// Skip creating the invoice candidates if this is disabled.
		if (!isCreateInvoiceCandidates)
		{
			return;
		}
		
		CreateMissingInvoiceCandidatesWorkpackageProcessor.schedule(model);
	}

	/** {@link ILHandlerModelInterceptor} instance builder */
	public static final class Builder
	{
		private String tableName;
		private boolean createInvoiceCandidates = false;
		private DocTimingType createInvoiceCandidatesTiming = DocTimingType.AFTER_COMPLETE;

		private Builder()
		{
			super();
		}

		public ILHandlerModelInterceptor build()
		{
			return new ILHandlerModelInterceptor(this);
		}

		public Builder setTableName(String tableName)
		{
			this.tableName = tableName;
			return this;
		}

		private final String getTableName()
		{
			Check.assumeNotEmpty(tableName, "tableName not empty");
			return tableName;
		}

		public Builder setCreateInvoiceCandidates(boolean createInvoiceCandidates)
		{
			this.createInvoiceCandidates = createInvoiceCandidates;
			return this;
		}

		private boolean isCreateInvoiceCandidates()
		{
			return createInvoiceCandidates;
		}

		private DocTimingType getCreateInvoiceCandidatesTiming()
		{
			return createInvoiceCandidatesTiming;
		}

		public Builder setCreateInvoiceCandidatesTiming(DocTimingType createInvoiceCandidatesTiming)
		{
			Check.assumeNotNull(createInvoiceCandidatesTiming, "createInvoiceCandidatesTiming not null");
			this.createInvoiceCandidatesTiming = createInvoiceCandidatesTiming;
			return this;
		}
	}
}
