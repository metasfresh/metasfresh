package org.adempiere.ad.session;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.process.PInstanceId;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class ChangeLogRecord
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int AD_Session_ID;
	private final String trxName;
	private final int AD_Table_ID;
	private final int AD_Column_ID;
	private final int record_ID;
	private final int AD_Client_ID;
	private final int AD_Org_ID;
	private final Object oldValue;
	private final Object newValue;
	private final String eventType;
	private final int AD_User_ID;
	private final PInstanceId AD_PInstance_ID;

	private ChangeLogRecord(final Builder builder)
	{
		AD_Session_ID = builder.AD_Session_ID;
		trxName = builder.trxName;
		AD_Table_ID = builder.AD_Table_ID;
		AD_Column_ID = builder.AD_Column_ID;
		record_ID = builder.record_ID;
		AD_Client_ID = builder.AD_Client_ID;
		AD_Org_ID = builder.AD_Org_ID;
		oldValue = builder.oldValue;
		newValue = builder.newValue;
		eventType = builder.eventType;
		AD_User_ID = builder.AD_User_ID;
		AD_PInstance_ID = builder.AD_PInstance_ID;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getAD_Session_ID()
	{
		return AD_Session_ID;
	}

	public String getTrxName()
	{
		return trxName;
	}

	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public int getAD_Column_ID()
	{
		return AD_Column_ID;
	}

	public int getRecord_ID()
	{
		return record_ID;
	}

	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	public Object getOldValue()
	{
		return oldValue;
	}

	public Object getNewValue()
	{
		return newValue;
	}

	public String getEventType()
	{
		return eventType;
	}

	public int getAD_User_ID()
	{
		return AD_User_ID;
	}

	public PInstanceId getAD_PInstance_ID()
	{
		return AD_PInstance_ID;
	}

	public static final class Builder
	{
		private int AD_Session_ID;
		private String trxName;
		private int AD_Table_ID;
		private int AD_Column_ID;
		private int record_ID;
		private int AD_Client_ID;
		private int AD_Org_ID;
		private Object oldValue;
		private Object newValue;
		private String eventType;
		private int AD_User_ID;
		private PInstanceId AD_PInstance_ID;

		private Builder()
		{
			super();
		}

		public ChangeLogRecord build()
		{
			return new ChangeLogRecord(this);
		}

		public Builder setAD_Session_ID(final int AD_Session_ID)
		{
			this.AD_Session_ID = AD_Session_ID;
			return this;
		}

		public Builder setTrxName(final String trxName)
		{
			this.trxName = trxName;
			return this;
		}

		public Builder setAD_Table_ID(final int AD_Table_ID)
		{
			this.AD_Table_ID = AD_Table_ID;
			return this;
		}

		public Builder setAD_Column_ID(final int AD_Column_ID)
		{
			this.AD_Column_ID = AD_Column_ID;
			return this;
		}

		public Builder setRecord_ID(final int record_ID)
		{
			this.record_ID = record_ID;
			return this;
		}

		public Builder setAD_Client_ID(final int AD_Client_ID)
		{
			this.AD_Client_ID = AD_Client_ID;
			return this;
		}

		public Builder setAD_Org_ID(final int AD_Org_ID)
		{
			this.AD_Org_ID = AD_Org_ID;
			return this;
		}

		public Builder setOldValue(final Object oldValue)
		{
			this.oldValue = oldValue;
			return this;
		}

		public Builder setNewValue(final Object newValue)
		{
			this.newValue = newValue;
			return this;
		}

		public Builder setEventType(final String eventType)
		{
			this.eventType = eventType;
			return this;
		}

		public Builder setAD_User_ID(final int AD_User_ID)
		{
			this.AD_User_ID = AD_User_ID;
			return this;
		}

		/**
		 *
		 * @param AD_PInstance_ID
		 * @return
		 * @task https://metasfresh.atlassian.net/browse/FRESH-314
		 */
		public Builder setAD_PInstance_ID(final PInstanceId AD_PInstance_ID)
		{
			this.AD_PInstance_ID = AD_PInstance_ID;
			return this;
		}
	}
}
