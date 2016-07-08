package org.adempiere.acct.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.HashMap;
import java.util.Map;

import org.adempiere.acct.api.IAccountDimension;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

/**
 * Immutable {@link IAccountDimension} implementation
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class AccountDimension implements IAccountDimension
{
	public static final AccountDimension NULL = builder().build();

	public static final Builder builder()
	{
		return new Builder();
	}

	private final String alias;
	private final int C_AcctSchema_ID;
	private final ImmutableMap<AcctSegmentType, Integer> segmentValues;

	private AccountDimension(final Builder builder)
	{
		super();
		alias = builder.alias;
		C_AcctSchema_ID = builder.C_AcctSchema_ID;
		segmentValues = ImmutableMap.copyOf(builder.segmentValues);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("alias", alias)
				.add("C_AcctSchema_ID", C_AcctSchema_ID)
				.addValue(segmentValues)
				.toString();
	}

	@Override
	public String getAlias()
	{
		return alias;
	}

	@Override
	public final int getSegmentValue(final AcctSegmentType segmentType)
	{
		final Integer value = segmentValues.get(segmentType);
		return value == null ? 0 : value;
	}

	@Override
	public final boolean isSegmentValueSet(final AcctSegmentType segmentType)
	{
		return segmentValues.containsKey(segmentType);
	}

	public Builder asBuilder()
	{
		return new Builder(this);
	}

	@Override
	public final IAccountDimension applyOverrides(final IAccountDimension overrides)
	{
		return asBuilder()
				.setAlias(null) // reset the alias
				.applyOverrides(overrides)
				.build();
	}

	@Override
	public int getAD_Client_ID()
	{
		return getSegmentValue(AcctSegmentType.Client);
	}

	@Override
	public int getAD_Org_ID()
	{
		return getSegmentValue(AcctSegmentType.Organization);
	}

	@Override
	public int getC_AcctSchema_ID()
	{
		return C_AcctSchema_ID;
	}

	@Override
	public int getC_ElementValue_ID()
	{
		return getSegmentValue(AcctSegmentType.Account);
	}

	@Override
	public int getC_SubAcct_ID()
	{
		return getSegmentValue(AcctSegmentType.SubAccount);
	}

	@Override
	public int getM_Product_ID()
	{
		return getSegmentValue(AcctSegmentType.Product);
	}

	@Override
	public int getC_BPartner_ID()
	{
		return getSegmentValue(AcctSegmentType.BPartner);
	}

	@Override
	public int getAD_OrgTrx_ID()
	{
		return getSegmentValue(AcctSegmentType.OrgTrx);
	}

	@Override
	public int getC_LocFrom_ID()
	{
		return getSegmentValue(AcctSegmentType.LocationFrom);
	}

	@Override
	public int getC_LocTo_ID()
	{
		return getSegmentValue(AcctSegmentType.LocationTo);
	}

	@Override
	public int getC_SalesRegion_ID()
	{
		return getSegmentValue(AcctSegmentType.SalesRegion);
	}

	@Override
	public int getC_Project_ID()
	{
		return getSegmentValue(AcctSegmentType.Project);
	}

	@Override
	public int getC_Campaign_ID()
	{
		return getSegmentValue(AcctSegmentType.Campaign);
	}

	@Override
	public int getC_Activity_ID()
	{
		return getSegmentValue(AcctSegmentType.Activity);
	}

	@Override
	public int getUser1_ID()
	{
		return getSegmentValue(AcctSegmentType.UserList1);
	}

	@Override
	public int getUser2_ID()
	{
		return getSegmentValue(AcctSegmentType.UserList2);
	}

	@Override
	public int getUserElement1_ID()
	{
		return getSegmentValue(AcctSegmentType.UserElement1);
	}

	@Override
	public int getUserElement2_ID()
	{
		return getSegmentValue(AcctSegmentType.UserElement2);
	}

	public static final class Builder
	{
		private String alias = null;
		private int C_AcctSchema_ID = -1;
		private final Map<AcctSegmentType, Integer> segmentValues = new HashMap<>();

		private Builder()
		{
			super();
		}

		/** Constructor used to initialize the builder with the values of given dimension */
		private Builder(final AccountDimension dim)
		{
			super();
			alias = dim.alias;
			C_AcctSchema_ID = dim.C_AcctSchema_ID;
			segmentValues.putAll(dim.segmentValues);
		}

		public AccountDimension build()
		{
			return new AccountDimension(this);
		}

		public Builder setAlias(final String alias)
		{
			this.alias = alias;
			return this;
		}

		public final Builder setSegmentValue(final AcctSegmentType segmentType, final int value)
		{
			segmentValues.put(segmentType, value);
			return this;
		}

		public final Builder clearSegmentValue(final AcctSegmentType segmentType)
		{
			segmentValues.remove(segmentType);
			return this;
		}

		/**
		 * Sets, to this builder, all segment values which were set to <code>overrides</code>.
		 *
		 * @param overrides
		 */
		public final Builder applyOverrides(final IAccountDimension overrides)
		{
			if (overrides.getC_AcctSchema_ID() > 0)
			{
				setC_AcctSchema_ID(overrides.getC_AcctSchema_ID());
			}

			for (final AcctSegmentType segmentType : AcctSegmentType.values())
			{
				if (!overrides.isSegmentValueSet(segmentType))
				{
					continue;
				}
				final int segmentValue = overrides.getSegmentValue(segmentType);
				setSegmentValue(segmentType, segmentValue);
			}

			return this;
		}

		public Builder setAD_Client_ID(final int AD_Client_ID)
		{
			setSegmentValue(AcctSegmentType.Client, AD_Client_ID);
			return this;
		}

		public Builder setAD_Org_ID(final int AD_Org_ID)
		{
			setSegmentValue(AcctSegmentType.Organization, AD_Org_ID);
			return this;
		}

		public Builder setC_AcctSchema_ID(final int C_AcctSchema_ID)
		{
			this.C_AcctSchema_ID = C_AcctSchema_ID;
			return this;
		}

		public Builder clearC_AcctSchema_ID()
		{
			setC_AcctSchema_ID(-1);
			return this;
		}

		public Builder setC_ElementValue_ID(final int C_ElementValue_ID)
		{
			setSegmentValue(AcctSegmentType.Account, C_ElementValue_ID);
			return this;
		}

		public Builder setC_SubAcct_ID(final int C_SubAcct_ID)
		{
			setSegmentValue(AcctSegmentType.SubAccount, C_SubAcct_ID);
			return this;
		}

		public Builder setM_Product_ID(final int M_Product_ID)
		{
			setSegmentValue(AcctSegmentType.Product, M_Product_ID);
			return this;
		}

		public Builder setC_BPartner_ID(final int C_BPartner_ID)
		{
			setSegmentValue(AcctSegmentType.BPartner, C_BPartner_ID);
			return this;
		}

		public Builder setAD_OrgTrx_ID(final int AD_OrgTrx_ID)
		{
			setSegmentValue(AcctSegmentType.OrgTrx, AD_OrgTrx_ID);
			return this;
		}

		public Builder setC_LocFrom_ID(final int C_LocFrom_ID)
		{
			setSegmentValue(AcctSegmentType.LocationFrom, C_LocFrom_ID);
			return this;
		}

		public Builder setC_LocTo_ID(final int C_LocTo_ID)
		{
			setSegmentValue(AcctSegmentType.LocationTo, C_LocTo_ID);
			return this;
		}

		public Builder setC_SalesRegion_ID(final int C_SalesRegion_ID)
		{
			setSegmentValue(AcctSegmentType.SalesRegion, C_SalesRegion_ID);
			return this;
		}

		public Builder setC_Project_ID(final int C_Project_ID)
		{
			setSegmentValue(AcctSegmentType.Project, C_Project_ID);
			return this;
		}

		public Builder setC_Campaign_ID(final int C_Campaign_ID)
		{
			setSegmentValue(AcctSegmentType.Campaign, C_Campaign_ID);
			return this;
		}

		public Builder setC_Activity_ID(final int C_Activity_ID)
		{
			setSegmentValue(AcctSegmentType.Activity, C_Activity_ID);
			return this;
		}

		public Builder setUser1_ID(final int user1_ID)
		{
			setSegmentValue(AcctSegmentType.UserList1, user1_ID);
			return this;
		}

		public Builder setUser2_ID(final int user2_ID)
		{
			setSegmentValue(AcctSegmentType.UserList2, user2_ID);
			return this;
		}

		public Builder setUserElement1_ID(final int userElement1_ID)
		{
			setSegmentValue(AcctSegmentType.UserElement1, userElement1_ID);
			return this;
		}

		public Builder setUserElement2_ID(final int userElement2_ID)
		{
			setSegmentValue(AcctSegmentType.UserElement2, userElement2_ID);
			return this;
		}
	}
}
