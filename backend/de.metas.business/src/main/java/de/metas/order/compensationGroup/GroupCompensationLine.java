package de.metas.order.compensationGroup;

import de.metas.common.util.CoalesceUtil;
import de.metas.currency.CurrencyPrecision;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.math.BigDecimal;

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
	/**
	 * Repository ID
	 */
	@Getter
	@Setter
	private RepoIdAware repoId;

	@Getter
	private final int seqNo;

	@Getter
	private final ProductId productId;
	@Getter
	private final UomId uomId;

	@Getter
	private final GroupCompensationType type;
	@Getter
	private final GroupCompensationAmtType amtType;
	@Getter
	private final Percent percentage;

	/**
	 * Base amount for percentage calculation
	 */
	@Getter
	private BigDecimal baseAmt;

	/**
	 * Might be {@code null} for {@link GroupCompensationAmtType#Percent}
	 */
	@Getter
	private BigDecimal qtyEntered;

	@Getter
	private BigDecimal price;
	@Getter
	private BigDecimal lineNetAmt;

	@Nullable @Getter private final GroupTemplateLineId groupTemplateLineId;
	@Nullable @Getter private final ManualCompensationLinePosition manualCompensationLinePosition;

	@Builder
	public GroupCompensationLine(
			final RepoIdAware repoId,
			final int seqNo,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			@NonNull final GroupCompensationType type,
			@NonNull final GroupCompensationAmtType amtType,
			final Percent percentage,
			final BigDecimal baseAmt,
			final BigDecimal qtyEntered,
			final BigDecimal price,
			final BigDecimal lineNetAmt,
			@Nullable final GroupTemplateLineId groupTemplateLineId,
			@Nullable final ManualCompensationLinePosition manualCompensationLinePosition)
	{
		this.repoId = repoId;
		this.groupTemplateLineId = groupTemplateLineId;

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
			this.qtyEntered = qtyEntered;
			this.price = price;
			this.lineNetAmt = lineNetAmt;
		}
		else if (amtType == GroupCompensationAmtType.PriceAndQty)
		{
			Check.assumeNotNull(qtyEntered, "Parameter qty is not null");
			Check.assumeNotNull(price, "Parameter price is not null");

			this.percentage = null;
			this.qtyEntered = qtyEntered;
			this.price = price;
			this.lineNetAmt = lineNetAmt;
		}
		else
		{
			throw new AdempiereException("Unknown " + GroupCompensationAmtType.class + ": " + amtType);
		}

		if (OrderGroupCompensationUtils.isGeneratedLine(getGroupTemplateLineId()))
		{
			this.manualCompensationLinePosition = null;
		}
		else
		{
			this.manualCompensationLinePosition = CoalesceUtil.coalesceNotNull(manualCompensationLinePosition, ManualCompensationLinePosition.DEFAULT);
		}
	}

	public boolean isPercentage()
	{
		return amtType == GroupCompensationAmtType.Percent;
	}

	void setBaseAmt(@NonNull final BigDecimal baseAmt)
	{
		this.baseAmt = baseAmt;
	}

	void setPriceAndQty(
			@NonNull final BigDecimal price,
			@NonNull final Quantity qtyEntered,
			@NonNull final CurrencyPrecision amountPrecision)
	{
		Check.assumeEquals(qtyEntered.getUomId(), this.uomId, "Param qtyEntered needs to have UomId={}; qtyEntered={}", this.uomId, qtyEntered);

		this.price = price;
		this.qtyEntered = qtyEntered.toBigDecimal();

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final Quantity qtyInProductUOM = uomConversionBL.convertToProductUOM(qtyEntered, getProductId());

		this.lineNetAmt = price.multiply(qtyInProductUOM.toBigDecimal());
		this.lineNetAmt = amountPrecision.roundIfNeeded(this.lineNetAmt);
	}

	public boolean isGeneratedLine()
	{
		return OrderGroupCompensationUtils.isGeneratedLine(getGroupTemplateLineId());
	}

	public boolean isManualLine()
	{
		return !isGeneratedLine();
	}

}
