package de.metas.acct.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;

import de.metas.acct.api.impl.AcctSegmentType;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import org.adempiere.exceptions.AdempiereException;

/**
 * Immutable {@link AccountDimension} implementation
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public final class AccountDimension
{
	public static final AccountDimension NULL = builder().build();

	public static final Builder builder()
	{
		return new Builder();
	}

	private final String alias;
	private final AcctSchemaId acctSchemaId;
	private final ImmutableMap<AcctSegmentType, Object> segmentValues;

	private AccountDimension(final Builder builder)
	{
		alias = builder.alias;
		acctSchemaId = builder.acctSchemaId;
		segmentValues = ImmutableMap.copyOf(builder.segmentValues);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("alias", alias)
				.add("acctSchemaId", acctSchemaId)
				.addValue(segmentValues)
				.toString();
	}

	public String getAlias()
	{
		return alias;
	}

	public final Object getSegmentValue(final AcctSegmentType segmentType)
	{
		final Object value = segmentValues.get(segmentType);

		return value;
	}

	public final boolean isSegmentValueSet(final AcctSegmentType segmentType)
	{
		return segmentValues.containsKey(segmentType);
	}

	public Builder asBuilder()
	{
		return new Builder(this);
	}

	public final AccountDimension applyOverrides(final AccountDimension overrides)
	{
		return asBuilder()
				.setAlias(null) // reset the alias
				.applyOverrides(overrides)
				.build();
	}

	public int getAD_Client_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Client),0);
	}

	public int getAD_Org_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Organization),0);
	}

	public AcctSchemaId getAcctSchemaId()
	{
		return acctSchemaId;
	}

	public int getC_ElementValue_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Account),0);
	}

	public int getC_SubAcct_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.SubAccount),0);
	}

	public int getM_Product_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Product),0);
	}

	public int getC_BPartner_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.BPartner),0);
	}

	public int getAD_OrgTrx_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.OrgTrx),0);
	}

	public int getC_LocFrom_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.LocationFrom),0);
	}

	public int getC_LocTo_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.LocationTo),0);
	}

	public int getC_SalesRegion_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.SalesRegion),0);
	}

	public int getC_Project_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Project),0);
	}

	public int getC_Campaign_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Campaign),0);
	}

	public int getC_Activity_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.Activity),0);
	}

	public int getUser1_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.UserList1),0);
	}

	public int getUser2_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.UserList2),0);
	}

	public int getUserElement1_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.UserElement1),0);
	}

	public int getUserElement2_ID()
	{
		return NumberUtils.asInt(getSegmentValue(AcctSegmentType.UserElement2),0);
	}

	public String getUserElementString1()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString1));
	}

	public String getUserElementString2()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString2));
	}

	public String getUserElementString3()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString3));
	}

	public String getUserElementString4()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString4));
	}

	public String getUserElementString5()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString5));
	}

	public String getUserElementString6()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString6));
	}

	public String getUserElementString7()
	{
		return String.valueOf(getSegmentValue(AcctSegmentType.UserElementString7));
	}

	public static final class Builder
	{
		private String alias = null;
		private AcctSchemaId acctSchemaId;
		private final Map<AcctSegmentType, Object> segmentValues = new HashMap<>();

		private Builder()
		{
			super();
		}

		/**
		 * Constructor used to initialize the builder with the values of given dimension
		 */
		private Builder(final AccountDimension dim)
		{
			alias = dim.alias;
			acctSchemaId = dim.acctSchemaId;
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

		public final Builder setSegmentValue(final AcctSegmentType segmentType, final Object value)
		{
			final Object valueConverted;

			if(value instanceof Integer)
			{
				final int intValue = NumberUtils.asInt(value, 0);
				segmentValues.put(segmentType, intValue);
			}
			else
			{
				final String stringValue = String.valueOf(value);
				segmentValues.put(segmentType, Strings.nullToEmpty(stringValue));
			}

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
		public final Builder applyOverrides(final AccountDimension overrides)
		{
			if (overrides.getAcctSchemaId() != null)
			{
				setAcctSchemaId(overrides.getAcctSchemaId());
			}

			for (final AcctSegmentType segmentType : AcctSegmentType.values())
			{
				if (!overrides.isSegmentValueSet(segmentType))
				{
					continue;
				}
				final Object segmentValue = overrides.getSegmentValue(segmentType);
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

		public Builder setAcctSchemaId(final AcctSchemaId acctSchemaId)
		{
			this.acctSchemaId = acctSchemaId;
			return this;
		}

		public Builder clearC_AcctSchema_ID()
		{
			setAcctSchemaId(null);
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

		public Builder setUserElementString1(final String userElementString1)
		{
			setSegmentValue(AcctSegmentType.UserElementString1, userElementString1);
			return this;
		}

		public Builder setUserElementString2(final String userElementString2)
		{
			setSegmentValue(AcctSegmentType.UserElementString2, userElementString2);
			return this;
		}

		public Builder setUserElementString3(final String userElementString3)
		{
			setSegmentValue(AcctSegmentType.UserElementString3, userElementString3);
			return this;
		}

		public Builder setUserElementString4(final String userElementString4)
		{
			setSegmentValue(AcctSegmentType.UserElementString4, userElementString4);
			return this;
		}

		public Builder setUserElementString5(final String userElementString5)
		{
			setSegmentValue(AcctSegmentType.UserElementString5, userElementString5);
			return this;
		}

		public Builder setUserElementString6(final String userElementString6)
		{
			setSegmentValue(AcctSegmentType.UserElementString6, userElementString6);
			return this;
		}

		public Builder setUserElementString7(final String userElementString7)
		{
			setSegmentValue(AcctSegmentType.UserElementString7, userElementString7);
			return this;
		}
	}
}
