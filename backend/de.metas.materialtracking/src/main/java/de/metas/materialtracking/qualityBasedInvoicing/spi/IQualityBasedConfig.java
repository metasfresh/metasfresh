package de.metas.materialtracking.qualityBasedInvoicing.spi;

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

<<<<<<< HEAD

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.currency.Currency;
import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;
<<<<<<< HEAD
=======
import lombok.NonNull;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/**
 * Interface containing all the information needed to do quality based invoicing
 *
 * @author ts
 *
 */
public interface IQualityBasedConfig
{
	I_M_Product getScrapProduct();

	I_C_UOM getScrapUOM();

	BigDecimal getScrapProcessingFeeForPercentage(BigDecimal percentage);

	IInvoicingItem getWithholdingProduct();

	/**
	 *
	 * @return percentage (between 0 and 100) that will be withhold from total invoiceable amount
	 */
	BigDecimal getWithholdingPercent();

	/**
	 * Returns the quality adjustment for the given month.
	 *
	 * @param month 0-based number of the month, i.e. 0 is january. Note that we have it 0-based, because that is also how it's done in <code>java.util.Calendar</code>.
<<<<<<< HEAD
	 * @return
	 */
	BigDecimal getQualityAdjustmentForMonthOrNull(int month);

	/**
	 * Convenience method, calls {@link #getQualityAdjustmentForDateOrNull(Date)}.
	 *
	 * @param outboundDate
	 * @return
	 */
=======
	 */
	BigDecimal getQualityAdjustmentForMonthOrNull(int month);

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	BigDecimal getQualityAdjustmentForDateOrNull(Date outboundDate);

	BigDecimal getScrapPercentageTreshold();

	boolean isFeeForProducedMaterial(I_M_Product product);

	/**
	 * Note: this method can return true, and the fee might still be 0. This would mean that there is an invoice line, but with a net amount of 0.
<<<<<<< HEAD
	 * 
	 * @return
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	boolean isFeeForScrap();

	/**
	 *
	 * @param m_Product the unwanted (by-)product for which there is a fee when it is produced.
	 * @param percentage the percentage of the produced product.
<<<<<<< HEAD
	 * @return
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	BigDecimal getFeeForProducedMaterial(I_M_Product m_Product, BigDecimal percentage);

	String getFeeNameForProducedProduct(I_M_Product product);

<<<<<<< HEAD
	List<IInvoicingItem> getAdditionalFeeProducts();
=======
	@NonNull
	List<IInvoicingItem> getProducedTotalWithoutByProductsAdditionalFeeProducts();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	Currency getCurrency();

	/**
	 * Gets a list of {@link QualityInspectionLineType} which needs to be in PP Order Report.
<<<<<<< HEAD
	 *
	 * Only the those types will be in the report and exactly in the given order.
	 *
	 * @return
=======
	 * <p>
	 * Only the those types will be in the report and exactly in the given order.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	List<QualityInspectionLineType> getPPOrderReportLineTypes();

	/**
	 * Gets a list of {@link QualityInvoiceLineGroupType} which needs to be in invoice candidates.
<<<<<<< HEAD
	 *
	 * Only the those types will be processed and exactly in the given order.
	 *
	 * @return
=======
	 * <p>
	 * Only the those types will be processed and exactly in the given order.
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	 */
	List<QualityInvoiceLineGroupType> getQualityInvoiceLineGroupTypes();

	/**
	 * @return ID of the document type that shall be used for invoicing down-payments, i.e. invoices that are not the last one, where only a part of the overall money is paid to the vendor.
	 */
	int getC_DocTypeInvoice_DownPayment_ID();

	/**
	 * 
	 * @return ID of the document type that shall be used for the final invoice.
	 */
	int getC_DocTypeInvoice_FinalSettlement_ID();

	int getOverallNumberOfInvoicings();

	/**
	 * Product for auslagerung.
<<<<<<< HEAD
	 * 
	 * @return
	 * @task 08720
	 */
	I_M_Product getRegularPPOrderProduct();
=======
	 */
	I_M_Product getRegularPPOrderProduct();

	@NonNull
	List<IInvoicingItem> getRawAdditionalFeeProducts();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
