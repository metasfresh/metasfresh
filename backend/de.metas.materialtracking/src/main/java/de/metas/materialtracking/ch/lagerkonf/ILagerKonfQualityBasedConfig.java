package de.metas.materialtracking.ch.lagerkonf;

/*
 * #%L
 * de.metas.materialtracking
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

import de.metas.currency.Currency;
import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Interface containing all the information needed to do quality based invoicing
 *
 * @author ts
 *
 */
public interface ILagerKonfQualityBasedConfig extends IQualityBasedConfig
{
	@Override
	BigDecimal getScrapProcessingFeeForPercentage(BigDecimal percentage);

	@Override
	IInvoicingItem getWithholdingProduct();

	/**
	 *
	 * @return percentage (between 0 and 100) that will be withhold from total invoiceable amount
	 */
	@Override
	BigDecimal getWithholdingPercent();

	/**
	 * Returns the quality adjustment for the given month.
	 *
	 * @param month 0-based number of the month, i.e. 0 is january. Note that we have it 0-based, because that is also how it's done in <code>java.util.Calendar</code>.
	 */
	@Override
	BigDecimal getQualityAdjustmentForMonthOrNull(int month);

	/**
	 * Convenience method, calls {@link #getQualityAdjustmentForMonthOrNull(int)}, or if the given date is outside of this config's validity time window, returns the highest value of all months.
	 */
	@Override
	BigDecimal getQualityAdjustmentForDateOrNull(Date outboundDate);

	@Override
	BigDecimal getScrapPercentageTreshold();

	@Override
	boolean isFeeForProducedMaterial(I_M_Product product);

	/**
	 *
	 * @param m_Product the unwanted (by-)product for which there is a fee when it is produced.
	 * @param percentage the percentage of the produced product.
	 */
	@Override
	BigDecimal getFeeForProducedMaterial(I_M_Product m_Product, BigDecimal percentage);

	@Override
	String getFeeNameForProducedProduct(I_M_Product product);

	@NonNull
	@Override
	List<IInvoicingItem> getProducedTotalWithoutByProductsAdditionalFeeProducts();

	@Override
	Currency getCurrency();

	/**
	 * returns the last date at which this config is valid
	 */
	Timestamp getValidToDate();

	@NonNull
	@Override
	List<IInvoicingItem> getRawAdditionalFeeProducts();
}
