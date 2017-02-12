package de.metas.document.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.process.DocAction;

import com.google.common.base.MoreObjects;

import de.metas.document.ICounterDocBL;

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
 * Interceptor used to intercept {@link DocAction}s that might need a counter document
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class CounterDocHandlerInterceptor extends AbstractModelInterceptor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String tableName;
	private final boolean async;

	private CounterDocHandlerInterceptor(final Builder builder)
	{
		super();

		this.tableName = builder.getTableName();
		this.async = builder.isAsync();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("tableName", tableName)
				.add("async", async)
				.toString();
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addDocValidate(tableName, this);
	}

	@Override
	public void onDocValidate(final Object model, final DocTimingType timing) throws Exception
	{
		if (timing != DocTimingType.AFTER_COMPLETE)
		{
			return; // nothing to do
		}

		final ICounterDocBL counterDocumentBL = Services.get(ICounterDocBL.class);
		if (!counterDocumentBL.isCreateCounterDocument(model))
		{
			return; // nothing to do
		}

		counterDocumentBL.createCounterDocument(model, async);

	}

	/** {@link CounterDocHandlerInterceptor} instance builder */
	public static final class Builder
	{
		private String tableName;
		private boolean async = false;

		private Builder()
		{
			super();
		}

		public CounterDocHandlerInterceptor build()
		{
			return new CounterDocHandlerInterceptor(this);
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

		public Builder setAsync(boolean async)
		{
			this.async = async;
			return this;
		}

		private boolean isAsync()
		{
			return async;
		}
	}
}
