package de.metas.banking.bankstatement.match.spi;

import java.util.Date;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;

import de.metas.banking.model.I_C_BankStatementLine_Ref;

/*
 * #%L
 * de.metas.banking.swingui
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
 * {@link IPaymentBatch} implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class PaymentBatch implements IPaymentBatch
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String id;
	private final String name;
	private final Date date;
	private final ITableRecordReference record;
	private final IPaymentBatchProvider paymentBatchProvider;

	private PaymentBatch(final Builder builder)
	{
		super();

		id = builder.getId();
		name = builder.getName();
		date = builder.getDate();
		record = builder.getRecord();
		paymentBatchProvider = builder.getPaymentBatchProvider();
	}

	@Override
	public final String toString()
	{
		// NOTE: this is used in list/combo/table renderers
		return name;
	}

	@Override
	public final int hashCode()
	{
		return id.hashCode();
	}

	@Override
	public final boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final PaymentBatch other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(id, other.id)
				.isEqual();
	}

	@Override
	public final String getId()
	{
		return id;
	}

	@Override
	public final Date getDate()
	{
		return date;
	}

	@Override
	public final ITableRecordReference getRecord()
	{
		return record;
	}

	@Override
	public final void linkBankStatementLine(final I_C_BankStatementLine_Ref bankStatementLineRef)
	{
		if (paymentBatchProvider == null)
		{
			return;
		}
		if (record == null)
		{
			return;
		}
		
		paymentBatchProvider.linkBankStatementLine(this, bankStatementLineRef);
	}

	public static final class Builder
	{
		private String id;
		private String name;
		private Date date;
		private ITableRecordReference record;
		private IPaymentBatchProvider paymentBatchProvider;

		private Builder()
		{
			super();
		}

		public PaymentBatch build()
		{
			return new PaymentBatch(this);
		}

		public Builder setId(final String id)
		{
			this.id = id;
			return this;
		}

		private String getId()
		{
			if (id != null)
			{
				return id;
			}

			if (record != null)
			{
				return record.getTableName() + "#" + record.getRecord_ID();
			}

			throw new IllegalStateException("id is null");
		}

		public Builder setName(final String name)
		{
			this.name = name;
			return this;
		}

		private String getName()
		{
			if (name != null)
			{
				return name;
			}

			if (record != null)
			{
				return Services.get(IMsgBL.class).translate(Env.getCtx(), record.getTableName()) + " #" + record.getRecord_ID();
			}

			return "";
		}

		public Builder setDate(final Date date)
		{
			this.date = date;
			return this;
		}

		private Date getDate()
		{
			if (date == null)
			{
				return null;
			}
			return (Date)date.clone();
		}

		public Builder setRecord(final Object record)
		{
			this.record = TableRecordReference.of(record);
			return this;
		}

		private ITableRecordReference getRecord()
		{
			return record;
		}
		
		public Builder setPaymentBatchProvider(IPaymentBatchProvider paymentBatchProvider)
		{
			this.paymentBatchProvider = paymentBatchProvider;
			return this;
		}
		
		private IPaymentBatchProvider getPaymentBatchProvider()
		{
			return paymentBatchProvider;
		}
	}
}
