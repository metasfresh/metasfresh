package de.metas.order.compensationGroup;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
public final class GroupCompensationLine
{
	/** Repository ID */
	@Getter
	@Setter
	private int repoId;

	@Getter
	private final int seqNo;

	@Getter
	private final int productId;
	@Getter
	private final int uomId;

	@Getter
	private final GroupCompensationType type;
	@Getter
	private final GroupCompensationAmtType amtType;
	@Getter
	private final BigDecimal percentage;

	/** Base amount for percentage calculation */
	@Getter
	private BigDecimal baseAmt;
	@Getter
	private BigDecimal qty;
	@Getter
	private BigDecimal price;
	@Getter
	private BigDecimal lineNetAmt;

	@Getter
	private int groupTemplateLineId;

	@Builder
	public GroupCompensationLine(
			final int repoId,
			final int seqNo,
			final int productId,
			final int uomId,
			@NonNull final GroupCompensationType type,
			@NonNull final GroupCompensationAmtType amtType,
			final BigDecimal percentage,
			final BigDecimal baseAmt,
			final BigDecimal qty,
			final BigDecimal price,
			final BigDecimal lineNetAmt,
			final int groupTemplateLineId)
	{
		Check.assume(productId > 0, "productId > 0");
		Check.assume(uomId > 0, "uomId > 0");

		this.repoId = repoId > 0 ? repoId : -1;
		this.groupTemplateLineId = groupTemplateLineId > 0 ? groupTemplateLineId : -1;

		this.seqNo = seqNo;

		this.productId = productId;
		this.uomId = uomId;

		this.type = type;
		this.amtType = amtType;
		this.baseAmt = baseAmt != null ? baseAmt : BigDecimal.ZERO;

		if (amtType == GroupCompensationAmtType.Percent)
		{
			Check.assumeNotNull(percentage, "Parameter percentage is not null");
			this.percentage = percentage;
			this.qty = qty;
			this.price = price;
			this.lineNetAmt = lineNetAmt;
		}
		else if (amtType == GroupCompensationAmtType.PriceAndQty)
		{
			Check.assumeNotNull(qty, "Parameter qty is not null");
			Check.assumeNotNull(price, "Parameter price is not null");
			this.percentage = null;
			this.qty = qty;
			this.price = price;
			this.lineNetAmt = lineNetAmt;
		}
		else
		{
			throw new AdempiereException("Unknown " + GroupCompensationAmtType.class + ": " + amtType);
		}
	}

	public boolean isPercentage()
	{
		return amtType == GroupCompensationAmtType.Percent;
	}

	private void assertPercentageType()
	{
		Check.assume(isPercentage(), "Compensation line shall be of type percentage: {}", this);
	}

	void setBaseAmt(@NonNull final BigDecimal baseAmt)
	{
		assertPercentageType();
		this.baseAmt = baseAmt;
	}

	void setPriceAndQty(@NonNull final BigDecimal price, @NonNull final BigDecimal qty, final int precision)
	{
		this.price = price;
		this.qty = qty;

		this.lineNetAmt = price.multiply(qty);
		if (lineNetAmt.scale() > precision)
		{
			lineNetAmt = lineNetAmt.setScale(precision, RoundingMode.HALF_UP);
		}
	}

	public boolean isGeneratedLine()
	{
		return OrderGroupCompensationUtils.isGeneratedCompensationLine(getGroupTemplateLineId());
	}

	public boolean isManualLine()
	{
		return !isGeneratedLine();
	}

}
