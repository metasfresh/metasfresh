package de.metas.procurement.base.order;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.base.MoreObjects;

import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;

/*
 * #%L
 * de.metas.procurement.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Aggregation segment of {@link I_PMM_PurchaseCandidate} (the aggregate), excluding the time dimension.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class PMMPurchaseCandidateSegment
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final int C_BPartner_ID;
	private final int M_Product_ID;
	private final int M_AttributeSetInstance_ID;
	private final int M_HU_PI_Item_Product_ID;
	private final int C_Flatrate_DataEntry_ID;

	private Integer _hashCode;

	private PMMPurchaseCandidateSegment(final Builder builder)
	{
		super();
		C_BPartner_ID = builder.C_BPartner_ID;
		M_Product_ID = builder.M_Product_ID;
		M_AttributeSetInstance_ID = builder.M_AttributeSetInstance_ID > 0 ? builder.M_AttributeSetInstance_ID : 0;
		M_HU_PI_Item_Product_ID = builder.M_HU_PI_Item_Product_ID;
		C_Flatrate_DataEntry_ID = builder.C_Flatrate_DataEntry_ID > 0 ? builder.C_Flatrate_DataEntry_ID : -1;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("C_BPartner_ID", C_BPartner_ID)
				.add("M_Product_ID", M_Product_ID)
				.add("ASI", M_AttributeSetInstance_ID)
				.add("M_HU_PI_Item_Product_ID", M_HU_PI_Item_Product_ID)
				.add("C_Flatrate_DataEntry_ID", C_Flatrate_DataEntry_ID)
				.toString();
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final PMMPurchaseCandidateSegment other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(C_BPartner_ID, other.C_BPartner_ID)
				.append(M_Product_ID, other.M_Product_ID)
				.append(M_AttributeSetInstance_ID, other.M_AttributeSetInstance_ID)
				.append(M_HU_PI_Item_Product_ID, other.M_HU_PI_Item_Product_ID)
				.append(C_Flatrate_DataEntry_ID, other.C_Flatrate_DataEntry_ID)
				.isEqual();
	}

	@Override
	public int hashCode()
	{
		if (_hashCode == null)
		{
			_hashCode = new HashcodeBuilder()
					.append(C_BPartner_ID)
					.append(M_Product_ID)
					.append(M_AttributeSetInstance_ID)
					.append(M_HU_PI_Item_Product_ID)
					.append(C_Flatrate_DataEntry_ID)
					.toHashcode();
		}
		return _hashCode;
	}

	public int getC_BPartner_ID()
	{
		return C_BPartner_ID;
	}

	public int getM_Product_ID()
	{
		return M_Product_ID;
	}

	public int getM_AttributeSetInstance_ID()
	{
		return M_AttributeSetInstance_ID;
	}

	public int getM_HU_PI_Item_Product_ID()
	{
		return M_HU_PI_Item_Product_ID;
	}

	public int getC_Flatrate_DataEntry_ID()
	{
		return C_Flatrate_DataEntry_ID;
	}

	public static final class Builder
	{
		private Integer C_BPartner_ID;
		private Integer M_Product_ID;
		private int M_AttributeSetInstance_ID = 0;
		private Integer M_HU_PI_Item_Product_ID;
		private int C_Flatrate_DataEntry_ID = -1;

		private Builder()
		{
			super();
		}

		public PMMPurchaseCandidateSegment build()
		{
			return new PMMPurchaseCandidateSegment(this);
		}

		public Builder setC_BPartner_ID(final int C_BPartner_ID)
		{
			this.C_BPartner_ID = C_BPartner_ID;
			return this;
		}

		public Builder setM_Product_ID(final int M_Product_ID)
		{
			this.M_Product_ID = M_Product_ID;
			return this;
		}

		public Builder setM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
		{
			this.M_AttributeSetInstance_ID = M_AttributeSetInstance_ID;
			return this;
		}

		public Builder setM_HU_PI_Item_Product_ID(final int M_HU_PI_Item_Product_ID)
		{
			this.M_HU_PI_Item_Product_ID = M_HU_PI_Item_Product_ID;
			return this;
		}

		public Builder setC_Flatrate_DataEntry_ID(final int C_Flatrate_DataEntry_ID)
		{
			this.C_Flatrate_DataEntry_ID = C_Flatrate_DataEntry_ID;
			return this;
		}
	}
}
