package de.metas.materialtracking.ch.lagerkonf.invoicing.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.logging.LogManager;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.IHandlingUnitsInfoWritableQty;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.ch.lagerkonf.ILagerKonfQualityBasedConfig;
import de.metas.materialtracking.qualityBasedInvoicing.IInvoicingItem;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingBL;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLinesCollection;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import de.metas.materialtracking.qualityBasedInvoicing.impl.QualityInspectionLinesBuilder;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.impl.QualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.impl.QualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityInvoiceLineGroupsBuilder;
import de.metas.materialtracking.spi.IHandlingUnitsInfoFactory;

/**
 * Takes an {@link IQualityInspectionOrder} and creates {@link IQualityInvoiceLineGroup}s.
 *
 * Before you start using it you need to configure this builder:
 * <ul>
 * <li>{@link #setPricingContext(IPricingContext)} - set pricing context to be used when calculating the prices
 * <li>{@link #setVendorReceipt(IVendorReceipt)} - set Vendor's received Qty/UOM
 * </ul>
 *
 * To generate the {@link IQualityInvoiceLineGroup}s you need to call: {@link #create()}.
 *
 * To get the results you need to call: {@link #getCreatedInvoiceLineGroups()}.
 *
 * See https://drive.google.com/file/d/0B-AaY-YNDnR5b045VGJsdVhRUGc/view
 *
 * @author tsa
 *
 */
public class QualityInvoiceLineGroupsBuilder implements IQualityInvoiceLineGroupsBuilder
{
	// Services
	private final transient IPricingBL pricingBL = Services.get(IPricingBL.class);
	private final transient IQualityBasedInvoicingBL qualityBasedInvoicingBL = Services.get(IQualityBasedInvoicingBL.class);
	private final transient IHandlingUnitsInfoFactory handlingUnitsInfoFactory = Services.get(IHandlingUnitsInfoFactory.class);

	// NOTE: keep services used to minimum

	// Parameters
	private final IQualityInspectionOrder _qiOrder;
	private final IMaterialTrackingDocuments _materialTrackingDocuments;
	private IQualityInspectionLinesCollection _qiLines;
	private IVendorReceipt<?> _receiptFromVendor;
	private IPricingContext _pricingContext;

	private static final transient Logger logger = LogManager.getLogger(QualityInvoiceLineGroupsBuilder.class);

	// Result
	private final List<IQualityInvoiceLineGroup> _createdInvoiceLineGroups = new ArrayList<IQualityInvoiceLineGroup>();

	public QualityInvoiceLineGroupsBuilder(final IMaterialTrackingDocuments materialTrackingdocuments)
	{
		super();
		Check.assumeNotNull(materialTrackingdocuments, "qiOrder not null");
		// _qiOrder = qiOrder;
		_materialTrackingDocuments = materialTrackingdocuments;

		_qiOrder = materialTrackingdocuments.getQualityInspectionOrderOrNull();
		Check.assumeNotNull(_qiOrder, "qiOrder of {} is not null", materialTrackingdocuments);
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append(getClass().getSimpleName()).append("[");
		sb.append("\nMaterialTrackingDocuments: ").append(_materialTrackingDocuments);
		sb.append("\nOrder: ").append(_qiOrder);
		sb.append("\nReceived from Vendor: ").append(_receiptFromVendor);
		sb.append("\nPricing context: ").append(_pricingContext);

		if (_qiLines != null)
		{
			sb.append("\n").append(_qiLines);
		}

		sb.append("\nCreated invoice line groups:");
		sb.append(_createdInvoiceLineGroups);

		sb.append("\n]");

		return sb.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.metas.materialtracking.ch.lagerkonf.invoicing.impl.IqualityInvoiceLineGroupsBuilder#getCreatedInvoiceLineGroups()
	 */
	@Override
	public List<IQualityInvoiceLineGroup> getCreatedInvoiceLineGroups()
	{
		return new ArrayList<>(_createdInvoiceLineGroups);
	}

	private void addCreatedInvoiceLineGroup(final IQualityInvoiceLineGroup invoiceLineGroup)
	{
		//
		// Validate the invoice line group (before adding)
		Check.assumeNotNull(invoiceLineGroup, "invoiceLineGroup not null");

		//
		// Validate the invoiceable line
		final IQualityInvoiceLine invoiceableLine = invoiceLineGroup.getInvoiceableLine();
		Check.assumeNotNull(invoiceableLine, "invoiceLineGroup shall have invoiceable line set: {}", invoiceLineGroup);
		Check.assumeNotNull(invoiceableLine.getM_Product(), "invoiceable line's product not null: {}", invoiceLineGroup);
		Check.assumeNotNull(invoiceableLine.getC_UOM(), "invoiceable line's uom not null: {}", invoiceLineGroup);
		Check.assumeNotNull(invoiceableLine.getQty(), "invoiceable line's quantity not null: {}", invoiceLineGroup);

		//
		// Validate the invoiceable line pricing
		final IPricingResult pricingResult = invoiceableLine.getPrice();
		Check.assumeNotNull(pricingResult, "invoiceable line's price not null: {}", invoiceLineGroup);
		Check.assumeNotNull(pricingResult.isCalculated(), "invoiceable line's price shall be calculated: {}", invoiceLineGroup);

		//
		// Add it to the list
		_createdInvoiceLineGroups.add(invoiceLineGroup);
	}

	@Override
	public IQualityInvoiceLineGroupsBuilder setVendorReceipt(final IVendorReceipt<?> receiptFromVendor)
	{
		_receiptFromVendor = receiptFromVendor;
		return this;
	}

	private IVendorReceipt<?> getVendorReceipt()
	{
		Check.assumeNotNull(_receiptFromVendor, "_receiptFromVendor not null");
		return _receiptFromVendor;
	}

	@Override
	public IQualityInvoiceLineGroupsBuilder setPricingContext(final IPricingContext pricingContext)
	{
		Check.assumeNotNull(pricingContext, "pricingContext not null");
		_pricingContext = pricingContext.copy();
		return this;
	}

	private IPricingContext getPricingContext()
	{
		Check.assumeNotNull(_pricingContext, "_pricingContext not null");
		return _pricingContext;
	}

	private final IQualityInspectionLinesCollection getQualityInspectionLinesCollection()
	{
		if (_qiLines == null)
		{
			final boolean buildWithAveragedValues = true; // when we invoice we need the average values
			final QualityInspectionLinesBuilder linesBuilder = new QualityInspectionLinesBuilder(_qiOrder, buildWithAveragedValues);
			linesBuilder.setReceiptFromVendor(getVendorReceipt());
			linesBuilder.create();
			_qiLines = linesBuilder.getCreatedQualityInspectionLinesCollection();
		}

		return _qiLines;
	}

	private ILagerKonfQualityBasedConfig getQualityBasedConfig()
	{
		return (ILagerKonfQualityBasedConfig)_qiOrder.getQualityBasedConfig();
	}

	private IQualityInspectionLine getScrapQualityInspectionLine()
	{
		return getQualityInspectionLinesCollection().getByType(QualityInspectionLineType.Scrap);
	}

	private IQualityInspectionLine getRawQualityInspectionLine()
	{
		return getQualityInspectionLinesCollection().getByType(QualityInspectionLineType.Raw);
	}

	@Override
	public IQualityInvoiceLineGroupsBuilder create()
	{
		//
		// Scrap
		// i.e. Erdbesatz
		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();
		if (config.isFeeForScrap())
		{
			createQualityInvoiceLineGroup_Scrap();
		}

		//
		// By-Product(s)
		// e.g. Futterkarotten
		for (final IQualityInspectionLine line : getQualityInspectionLinesCollection().getAllByType(QualityInspectionLineType.ProducedByProducts))
		{
			createQualityInvoiceLineGroup_ProducedMaterial(line, QualityInvoiceLineGroupType.ByProduct);
		}

		// 08848: for now we create a PreceedingRegularOrder record for all PP_Orders
		if (
		// task 09117: we also want/need the regular orders if there is just one invoicing
		// task 08092: we only deal with regular orders if it is the last of > 1 invoicings
		qualityBasedInvoicingBL.isLastInspection(_qiOrder))
		{
			if (config.getOverallNumberOfInvoicings() == 1)
			{
				// task 09668
				createQualityInvoiceLineGroups_RegularOrdersSimplified();
			}
			else
			{
				createQualityInvoiceLineGroups_RegularOrdersListDistinctPPOrders();
			}
		}

		//
		// Additional fees
		// e.g.
		// Abzug für Beitrag Basic-Linie 9831.2 kg -0.06 -589.88
		// Abzug für Beitrag Verkaufsförderung
		boolean firstItem = true; //
		for (final IInvoicingItem feeItem : getQualityBasedConfig().getAdditionalFeeProducts())
		{
			createQualityInvoiceLineGroup_AditionalFees(feeItem, firstItem); // is called with firstItem==true only one time
			firstItem = false;
		}

		//
		// Main Produced product
		// i.e. Karotten mittel
		{
			final IQualityInspectionLine line = getQualityInspectionLinesCollection().getByType(QualityInspectionLineType.ProducedMain);
			createQualityInvoiceLineGroup_ProducedMaterial(line, QualityInvoiceLineGroupType.MainProduct);
		}

		// ok, at this point we can be sure that there is already a invoicing group set!
		// add one detail line for the raw material and one for the scrap-information
		{
			final IQualityInvoiceLineGroup firstDisplayedGroup = getFirstDisplayedGroupOrNull();
			Check.assumeNotNull(firstDisplayedGroup,
					"firstDisplayedGroup is not null, because we already added the main product's group; createdInvoiceLineGroups={}",
					_createdInvoiceLineGroups);

			final IQualityInspectionLine scrapLine = getScrapQualityInspectionLine();

			//
			// Before detail: raw product (as reference)
			// e.g. Karotten mittel ungewaschen
			{
				final IQualityInspectionLine rawLine = getRawQualityInspectionLine();
				final QualityInvoiceLine detail = createQualityInvoiceLine(rawLine);
				detail.setDisplayed(true);
				firstDisplayedGroup.addDetailBefore(detail);
			}

			//
			// Before detail: scrap product
			// e.g. Erdbesatz
			{
				final QualityInvoiceLine detail = createQualityInvoiceLine(scrapLine);
				detail.setDisplayed(true);
				firstDisplayedGroup.addDetailBefore(detail);

				detail.setQty(detail.getQty().negate()); // NOTE: in report is with "-"
			}
		}

		//
		// Co-Products
		// i.e. Karotten gross
		for (final IQualityInspectionLine line : getQualityInspectionLinesCollection().getAllByType(QualityInspectionLineType.ProducedCoProducts))
		{
			createQualityInvoiceLineGroup_ProducedMaterial(line, QualityInvoiceLineGroupType.CoProduct);
		}

		//
		// Withholding
		// e.g. Akontozahlung 50 %
		createQualityInvoiceLineGroup_WithholdingAmount();

		return this;
	}

	/**
	 * Returns the first {@link IQualityInvoiceLineGroup} that is supposed to be displayed
	 *
	 * @return
	 */
	private IQualityInvoiceLineGroup getFirstDisplayedGroupOrNull()
	{
		for (final IQualityInvoiceLineGroup group : getCreatedInvoiceLineGroups())
		{
			final IQualityInvoiceLine invoiceableLine = group.getInvoiceableLine();
			if (invoiceableLine == null)
			{
				continue;
			}
			if (!invoiceableLine.isDisplayed())
			{
				continue;
			}
			return group;
		}
		return null;
	}

	/**
	 * Creates <b>one</b> "Auslagerung per..." record with the date of the qualityInspectionOrder and the full quantity that was received so far.
	 *
	 * @task http://dewiki908/mediawiki/index.php/09668_Karotten_Frisch_L%C3%B6sung_ohne_Qualit%C3%A4tslagerausgleich_%28103397626711%29
	 */
	private void createQualityInvoiceLineGroups_RegularOrdersSimplified()
	{
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();

		final IQualityInspectionOrder qualityInspectionOrder = _materialTrackingDocuments.getQualityInspectionOrderOrNull();
		// if qualityInspectionOrder was null, we shouldn't be here
		Check.assumeNotNull(qualityInspectionOrder, "the IQualityInspectionOrder of IMAterialTrackingDocuments {} are not null", _materialTrackingDocuments);

		final IQualityInspectionLinesCollection qualityInspectionLines = getQualityInspectionLinesCollection();
		final IQualityInspectionLine producedWithoutByProducts = qualityInspectionLines.getByType(QualityInspectionLineType.ProducedTotalWithoutByProducts);
		final IQualityInspectionLine overallRaw = qualityInspectionLines.getByType(QualityInspectionLineType.Raw);
		final IHandlingUnitsInfo overallRawHUInfo = overallRaw.getHandlingUnitsInfoProjected();
		final BigDecimal overallQtyTU = new BigDecimal(overallRawHUInfo.getQtyTU());
		final BigDecimal overallAvgProducedQtyPerTU = producedWithoutByProducts.getQtyProjected().divide(overallQtyTU, RoundingMode.HALF_UP);
		final I_C_UOM overallRawUOM = overallRaw.getC_UOM();

		final Timestamp dateOfProduction = TimeUtil.getDay(materialTrackingPPOrderBL.getDateOfProduction(qualityInspectionOrder.getPP_Order()));

		final QualityInvoiceLineGroup invoiceLineGroup = new QualityInvoiceLineGroup(QualityInvoiceLineGroupType.ProductionOrder);

		final QualityInvoiceLine detailBefore = new QualityInvoiceLine();

		detailBefore.setProductName("Anzahl kg pro Paloxe im Durchschnitt");
		detailBefore.setDisplayed(true);
		detailBefore.setC_UOM(overallRawUOM);
		detailBefore.setQty(overallAvgProducedQtyPerTU);
		detailBefore.setM_Product(config.getRegularPPOrderProduct());

		invoiceLineGroup.addDetailBefore(detailBefore);

		final QualityInvoiceLine invoiceableLine = new QualityInvoiceLine();
		invoiceLineGroup.setInvoiceableLine(invoiceableLine);
		final boolean displayRegularOrderData = true;
		invoiceableLine.setDisplayed(displayRegularOrderData);

		// initial HUInfo
		final IHandlingUnitsInfoWritableQty huInfo = handlingUnitsInfoFactory.createHUInfoWritableQty(overallRawHUInfo);
		huInfo.setQtyTU(overallQtyTU.intValue());
		invoiceableLine.setHandlingUnitsInfo(huInfo);

		invoiceableLine.setQty(overallRaw.getQtyProjected());

		//
		// static stuff like product, uom
		final String labelPrefix = "Auslagerung per ";
		final String labelToUse = createRegularOrderLabelToUse(labelPrefix, dateOfProduction);
		invoiceableLine.setDescription(labelToUse);

		// 08702: don't use the raw product itself, but a dedicated product.
		// That's because the raw product is the product-under-contract and therefore the IC would have IsToClear='Y' if we used the raw-product here.
		// Note that we only use the product, but the raw product's uom and qty.
		invoiceableLine.setM_Product(config.getRegularPPOrderProduct());

		invoiceableLine.setC_UOM(overallRawUOM);

		// Pricing
		final IPricingContext pricingCtx = createPricingContext(invoiceableLine);
		BigDecimal priceActual = config.getQualityAdjustmentForDateOrNull(dateOfProduction);
		if (priceActual == null)
		{
			// this means that for this config and dateOfProduction, there is no QualityAdjustment
			// we set the price to be zero, and we'll create an invoice candidate, but we won't see it on the invoice jasper
			priceActual = BigDecimal.ZERO;
		}

		final IPricingResult pricingResult = createPricingResult(pricingCtx, priceActual, invoiceableLine.getC_UOM());
		invoiceableLine.setPrice(pricingResult);

		// the detail that shall override the invoiceable line's displayed infos
		final QualityInvoiceLine invoicableDetailLineOverride = createDetailForSingleRegularOrder(overallRawUOM, huInfo, invoiceableLine.getQty(), labelToUse);
		invoicableDetailLineOverride.setDisplayed(displayRegularOrderData);

		invoiceLineGroup.setInvoiceableLineOverride(invoicableDetailLineOverride);

		//
		// add a reference to each PP_Order
		final List<IQualityInspectionOrder> allProductionOrders = _materialTrackingDocuments
				.getProductionOrdersForPLV(getPricingContext().getM_PriceList_Version());

		for (final IQualityInspectionOrder productionOrder : allProductionOrders)
		{
			final QualityInvoiceLine ppOrderDetailLine = createQualityInvoiceLineDetail_RegularOrder(productionOrder,
					overallAvgProducedQtyPerTU,
					invoiceLineGroup
							.getInvoiceableLine()
							.getProductName());

			invoiceLineGroup.addDetailBefore(ppOrderDetailLine);
		}

		addCreatedInvoiceLineGroup(invoiceLineGroup);
	}

	/**
	 * Aggregates the existing production orders per production date.
	 */
	private void createQualityInvoiceLineGroups_RegularOrdersListDistinctPPOrders()
	{
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();

		final List<IQualityInspectionOrder> allProductionOrders = _materialTrackingDocuments
				.getProductionOrdersForPLV(getPricingContext().getM_PriceList_Version());

		// this check is mostly a guard against poorly set up AITs
		Check.errorIf(allProductionOrders.isEmpty(), "MaterialTrackingDocuments {} contains no IQualityInspectionOrders for plv {}",
				_materialTrackingDocuments, getPricingContext().getM_PriceList_Version());

		final IQualityInspectionLinesCollection qualityInspectionLines = getQualityInspectionLinesCollection();
		final IQualityInspectionLine producedWithoutByProducts = qualityInspectionLines.getByType(QualityInspectionLineType.ProducedTotalWithoutByProducts);
		final IQualityInspectionLine overallRaw = qualityInspectionLines.getByType(QualityInspectionLineType.Raw);
		final IHandlingUnitsInfo overallRawHUInfo = overallRaw.getHandlingUnitsInfoProjected();
		final BigDecimal overallQtyTU = new BigDecimal(overallRawHUInfo.getQtyTU());
		final BigDecimal overallAvgProducedQtyPerTU = producedWithoutByProducts.getQtyProjected().divide(overallQtyTU, RoundingMode.HALF_UP);
		final I_C_UOM overallRawUOM = overallRaw.getC_UOM();

		boolean atLeastOnePPOrderWithQualityAdjustment = false;

		//
		// iterate allRegularOrders and create and add one QualityInvoiceLineGroup per date
		final Map<Timestamp, QualityInvoiceLineGroup> date2InvoiceLineGroup = new HashMap<Timestamp, QualityInvoiceLineGroup>();
		for (final IQualityInspectionOrder productionOrder : allProductionOrders)
		{
			final Timestamp dateOfProduction = TimeUtil.getDay(materialTrackingPPOrderBL.getDateOfProduction(productionOrder.getPP_Order()));
			if (date2InvoiceLineGroup.containsKey(dateOfProduction))
			{
				continue; // already created one for this date
			}

			final boolean displayRegularOrderData = isInvoiceRegularOrderForDate(dateOfProduction);

			final QualityInvoiceLineGroup qualityInvoiceLineGroup = new QualityInvoiceLineGroup(QualityInvoiceLineGroupType.ProductionOrder);

			final QualityInvoiceLine invoiceableLine = new QualityInvoiceLine();
			qualityInvoiceLineGroup.setInvoiceableLine(invoiceableLine);

			invoiceableLine.setDisplayed(displayRegularOrderData);

			// initial HUInfo
			final IHandlingUnitsInfoWritableQty huInfo = handlingUnitsInfoFactory.createHUInfoWritableQty(overallRawHUInfo);
			huInfo.setQtyTU(0);
			invoiceableLine.setHandlingUnitsInfo(huInfo);

			// initial Qty
			invoiceableLine.setQty(BigDecimal.ZERO); // make sure it's not null, we want to iterate and add to it later

			//
			// static stuff like product, uom
			final String labelPrefix = "Auslagerung per ";
			final String labelToUse = createRegularOrderLabelToUse(labelPrefix, dateOfProduction);
			invoiceableLine.setDescription(labelToUse);

			// 08702: don't use the raw product itself, but a dedicated product.
			// That's because the raw product is the product-under-contract and therefore the IC would have IsToClear='Y' if we used the raw-product here.
			// Note that we only use the product, but the raw product's uom and qty.
			invoiceableLine.setM_Product(config.getRegularPPOrderProduct());

			invoiceableLine.setC_UOM(overallRawUOM);

			// Pricing
			final IPricingContext pricingCtx = createPricingContext(invoiceableLine);
			BigDecimal priceActual = config.getQualityAdjustmentForDateOrNull(dateOfProduction);
			if (priceActual == null)
			{
				// this means that for this config and dateOfProduction, there is no QualityAdjustment
				// we set the price to be zero, and we'll create an invoice candidate, but we won't see it on the invoice jasper
				priceActual = BigDecimal.ZERO;
			}

			final IPricingResult pricingResult = createPricingResult(pricingCtx, priceActual, invoiceableLine.getC_UOM());
			invoiceableLine.setPrice(pricingResult);

			// the detail that shall override the invoiceable line's displayed infos
			final QualityInvoiceLine invoicableDetailLineOverride = createDetailForSingleRegularOrder(overallRawUOM, huInfo, invoiceableLine.getQty(), labelToUse);
			invoicableDetailLineOverride.setDisplayed(displayRegularOrderData);

			qualityInvoiceLineGroup.setInvoiceableLineOverride(invoicableDetailLineOverride);

			date2InvoiceLineGroup.put(dateOfProduction, qualityInvoiceLineGroup);

			addCreatedInvoiceLineGroup(qualityInvoiceLineGroup);
		}

		QualityInvoiceLineGroup lastInvoiceLineGroup = null;
		BigDecimal netAmtSum = BigDecimal.ZERO; // needed for the final totals detail line
		int qtyTUSum = 0; // needed to decide if we were able to account for all HUs that were received

		//
		// now iterate allRegularOrders again, create one non-displayed detail for each PP_Order and aggregate them on the per-date-groups
		for (final IQualityInspectionOrder productionOrder : allProductionOrders)
		{
			final Timestamp dateOfProduction = TimeUtil.getDay(materialTrackingPPOrderBL.getDateOfProduction(productionOrder.getPP_Order()));

			if (isInvoiceRegularOrderForDate(dateOfProduction))
			{
				atLeastOnePPOrderWithQualityAdjustment = true;
			}

			final QualityInvoiceLineGroup invoiceLineGroup = date2InvoiceLineGroup.get(dateOfProduction); // not null because we just created them
			Check.errorIf(invoiceLineGroup == null, "Missing invoiceLineGroup for date {}", dateOfProduction);

			final boolean firstItem = lastInvoiceLineGroup == null;
			if (firstItem && isInvoiceRegularOrderForDate(dateOfProduction))
			{
				// note that we only need such a "label" line if isInvoiceRegularOrderForDate() is true
				final IProductionMaterial currentRawProductionMaterial = productionOrder.getRawProductionMaterial();
				final I_C_UOM uom = currentRawProductionMaterial.getC_UOM();

				final QualityInvoiceLine detailBefore = new QualityInvoiceLine();
				invoiceLineGroup.addDetailBefore(detailBefore);

				detailBefore.setProductName("Anzahl kg pro Paloxe im Durchschnitt");
				detailBefore.setDisplayed(true);
				detailBefore.setC_UOM(uom);
				detailBefore.setQty(overallAvgProducedQtyPerTU);
				detailBefore.setM_Product(config.getRegularPPOrderProduct());
			}

			final QualityInvoiceLine ppOrderDetailLine = createQualityInvoiceLineDetail_RegularOrder(productionOrder,
					overallAvgProducedQtyPerTU,
					invoiceLineGroup
							.getInvoiceableLine()
							.getProductName());

			invoiceLineGroup.addDetailBefore(ppOrderDetailLine);

			//
			// update the invoiceable line's qty and HU-Info
			// the casts are safe, because we created the QualityInvoiceLines in this very class
			final QualityInvoiceLine invoicableDetailLine = (QualityInvoiceLine)invoiceLineGroup.getInvoiceableLine();
			final QualityInvoiceLine invoicableDetailLineOverride = (QualityInvoiceLine)invoiceLineGroup.getInvoiceableLineOverride();

			// note that if isInvoiceRegularOrderForDate() is not true, then we do not update the invoiceable line,
			// but just the "override"-line (for diagnostics and debugging)
			if (isInvoiceRegularOrderForDate(dateOfProduction))
			{
				invoicableDetailLine.setQty(
						invoicableDetailLine.getQty().add(ppOrderDetailLine.getQty()));
			}
			invoicableDetailLineOverride.setQty(
					invoicableDetailLineOverride.getQty().add(ppOrderDetailLine.getQty()));

			final int qtyTU;
			final IHandlingUnitsInfo regularInvoiceDetailHUInfo = ppOrderDetailLine.getHandlingUnitsInfo();
			if (regularInvoiceDetailHUInfo != null)
			{
				qtyTU = regularInvoiceDetailHUInfo.getQtyTU();

				final IHandlingUnitsInfo updatedHUInfo = invoicableDetailLine.getHandlingUnitsInfo().add(regularInvoiceDetailHUInfo);
				if (isInvoiceRegularOrderForDate(dateOfProduction))
				{
					invoicableDetailLine.setHandlingUnitsInfo(updatedHUInfo);
				}
				invoicableDetailLineOverride.setHandlingUnitsInfo(updatedHUInfo);
			}
			else
			{
				qtyTU = 0;
			}

			ppOrderDetailLine.setPrice(invoicableDetailLine.getPrice()); // we augment the current PP_Order's detail line with the price info because we want its net amount.
			qtyTUSum += qtyTU;
			netAmtSum = netAmtSum.add(getNetAmount(ppOrderDetailLine));

			lastInvoiceLineGroup = invoiceLineGroup;
		}

		//
		// if there is *not* a PP_Order for each received HU *and* if where have a quality adjustment, then we need to explicitly deal with the rest
		final int missingQtyTU = overallRawHUInfo.getQtyTU() - qtyTUSum;
		final Timestamp dateForMissingQtyTUs = TimeUtil.addDays(
				_materialTrackingDocuments.getM_Material_Tracking().getValidTo(),
				1);
		if (missingQtyTU > 0
				&& isInvoiceRegularOrderForDate(dateForMissingQtyTUs))
		{
			final QualityInvoiceLineGroup invoiceLineGroup = new QualityInvoiceLineGroup(QualityInvoiceLineGroupType.ProductionOrder);

			final String labelPrefix = "Auslagerung nach ";
			final String labelToUse = createRegularOrderLabelToUse(labelPrefix, dateForMissingQtyTUs);

			final IHandlingUnitsInfoWritableQty huInfo = handlingUnitsInfoFactory.createHUInfoWritableQty(overallRawHUInfo);
			huInfo.setQtyTU(missingQtyTU);

			// the "label" line which shall be displayed (in the jasper printout) instead of the action invoice lines product, uom etc
			{
				final QualityInvoiceLine detailLineOverride = createDetailForSingleRegularOrder(overallRaw.getC_UOM(),
						huInfo,
						new BigDecimal(missingQtyTU).multiply(overallAvgProducedQtyPerTU),
						labelToUse);
				detailLineOverride.setDisplayed(true);
				invoiceLineGroup.setInvoiceableLineOverride(detailLineOverride);
			}

			// the line which shall later become the C_InvoiceLine
			{
				final QualityInvoiceLine detailLineInvcoiceable = createDetailForSingleRegularOrder(overallRaw.getC_UOM(),
						huInfo,
						new BigDecimal(missingQtyTU).multiply(overallAvgProducedQtyPerTU),
						labelToUse);
				detailLineInvcoiceable.setDisplayed(true);

				// Pricing
				final IPricingContext pricingCtx = createPricingContext(detailLineInvcoiceable);
				final BigDecimal priceActual = config.getQualityAdjustmentForDateOrNull(dateForMissingQtyTUs);

				final IPricingResult pricingResult = createPricingResult(pricingCtx, priceActual, detailLineInvcoiceable.getC_UOM());
				detailLineInvcoiceable.setPrice(pricingResult);

				invoiceLineGroup.setInvoiceableLine(detailLineInvcoiceable);
			}
			addCreatedInvoiceLineGroup(invoiceLineGroup);
			lastInvoiceLineGroup = invoiceLineGroup;
		}

		if (lastInvoiceLineGroup != null)
		{
			// Total Qualitätslagerausgleich
			final QualityInvoiceLine detailAfter = new QualityInvoiceLine();
			final IQualityInvoiceLine invoiceableLine = lastInvoiceLineGroup.getInvoiceableLine();

			lastInvoiceLineGroup.addDetailAfter(detailAfter);

			detailAfter.setDisplayed(atLeastOnePPOrderWithQualityAdjustment);
			detailAfter.setProductName("Total Qualitätslagerausgleich"); // TRL
			detailAfter.setM_Product(invoiceableLine.getM_Product());
			detailAfter.setQty(BigDecimal.ONE);

			// Pricing
			final IPricingContext pricingCtx = createPricingContext(detailAfter);
			final IPricingResult pricingResult = createPricingResult(pricingCtx, netAmtSum, lastInvoiceLineGroup.getInvoiceableLine().getC_UOM());
			detailAfter.setPrice(pricingResult);
		}
	}

	private String createRegularOrderLabelToUse(final String labelPrefix, final Timestamp date)
	{
		final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		final String labelToUse = labelPrefix + dateFormat.format(date);
		return labelToUse;
	}

	/**
	 *
	 * @param feeItem
	 * @param firstItem indicates if this is the first fee-item. If it is, then the method will prepend a details line that is about the total produced goods (without By-Products).
	 * @return
	 */
	private IQualityInvoiceLineGroup createQualityInvoiceLineGroup_AditionalFees(
			final IInvoicingItem feeItem,
			final boolean firstItem)
	{
		//
		// Invoice Line Group
		final QualityInvoiceLineGroup invoiceLineGroup = new QualityInvoiceLineGroup(QualityInvoiceLineGroupType.Fee);

		//
		// Detail: our reference line which we will use to calculate the fee invoiceable line
		// (i.e. Ausbeute (Marktfähige Ware))
		// Create the detail only if it's first line
		final IQualityInspectionLine producedTotalWithoutByProductsLine = getQualityInspectionLinesCollection().getByType(QualityInspectionLineType.ProducedTotalWithoutByProducts);
		if (firstItem)
		{
			final QualityInvoiceLine detail = createQualityInvoiceLine(producedTotalWithoutByProductsLine);
			detail.setDisplayed(true);
			invoiceLineGroup.addDetailBefore(detail);
		}

		//
		// Invoiceable line
		{
			final QualityInvoiceLine invoiceableLine = new QualityInvoiceLine();
			invoiceLineGroup.setInvoiceableLine(invoiceableLine);

			invoiceableLine.setDisplayed(true);
			invoiceableLine.setM_Product(feeItem.getM_Product());
			invoiceableLine.setQty(producedTotalWithoutByProductsLine.getQtyProjected());
			invoiceableLine.setC_UOM(producedTotalWithoutByProductsLine.getC_UOM());

			// Pricing
			final IEditablePricingContext pricingCtx = createPricingContext(invoiceableLine);
			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
			pricingResult.setPriceList(pricingResult.getPriceList().negate());
			pricingResult.setPriceStd(pricingResult.getPriceStd().negate());
			pricingResult.setPriceLimit(pricingResult.getPriceLimit().negate());
			// NOTE: we need to set the Price UOM to same UOM as Qty to avoid conversion errors like (cannot convert from Kg to Stuck)
			pricingResult.setPrice_UOM_ID(producedTotalWithoutByProductsLine.getC_UOM().getC_UOM_ID());
			invoiceableLine.setPrice(pricingResult);
		}

		//
		addCreatedInvoiceLineGroup(invoiceLineGroup);
		return invoiceLineGroup;
	}

	private IQualityInvoiceLineGroup createQualityInvoiceLineGroup_Scrap()
	{
		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();

		final IQualityInspectionLine scrapLine = getScrapQualityInspectionLine();
		final BigDecimal qtyProjected = scrapLine.getQtyProjected();
		final BigDecimal percentage = scrapLine.getPercentage();
		final int qtyPrecision = scrapLine.getC_UOM().getStdPrecision();

		final BigDecimal qtyProjectedToCharge;
		final BigDecimal percentageToCharge;
		final BigDecimal scrapPercentageTreshold = config.getScrapPercentageTreshold();
		if (percentage.compareTo(scrapPercentageTreshold) <= 0)
		{
			percentageToCharge = BigDecimal.ZERO;
			qtyProjectedToCharge = BigDecimal.ONE; // needs to be 1, if it was 0, then the IC wouldn't be invoiced
		}
		else
		{
			percentageToCharge = percentage
					.subtract(scrapPercentageTreshold)
					.setScale(2, RoundingMode.HALF_UP);

			// compute the qty that is above the treshold and therefore shall be charged
			qtyProjectedToCharge = qtyProjected
					.multiply(percentageToCharge)
					.divide(percentage, qtyPrecision, RoundingMode.HALF_UP);
		}

		final QualityInvoiceLineGroup invoiceLineGroup = new QualityInvoiceLineGroup(QualityInvoiceLineGroupType.Scrap);

		// e.g. Entsorgungskosten (Erdbesatz > 10 %)

		// TODO: AD_Message
		final String labelToUse = "Entsorgungskosten (Erdbesatz > " + scrapPercentageTreshold.setScale(2, RoundingMode.HALF_UP) + "%)";
		// Detail with the invoiceable line's "label"
		{
			final QualityInvoiceLine detail = new QualityInvoiceLine();
			invoiceLineGroup.setInvoiceableLineOverride(detail);

			detail.setDisplayed(true);

			detail.setM_Product(scrapLine.getM_Product());
			detail.setProductName(labelToUse);
			detail.setPercentage(percentageToCharge);
			detail.setQty(qtyProjectedToCharge);
			detail.setC_UOM(scrapLine.getC_UOM());
		}
		// Invoiceable Line
		{
			final QualityInvoiceLine invoiceableLine = new QualityInvoiceLine();
			invoiceLineGroup.setInvoiceableLine(invoiceableLine);

			invoiceableLine.setDisplayed(true);

			invoiceableLine.setM_Product(scrapLine.getM_Product());
			invoiceableLine.setProductName(labelToUse);
			invoiceableLine.setPercentage(percentageToCharge);
			invoiceableLine.setQty(qtyProjectedToCharge);
			invoiceableLine.setC_UOM(scrapLine.getC_UOM());

			// Pricing
			final IPricingContext pricingCtx = createPricingContext(invoiceableLine);
			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
			pricingResult.setPriceStd(config.getScrapProcessingFeeForPercentage(percentage).negate());
			invoiceableLine.setPrice(pricingResult);
		}

		addCreatedInvoiceLineGroup(invoiceLineGroup);
		return invoiceLineGroup;
	}

	/**
	 * Applies for Main Product, Co-Product and By-Product
	 *
	 * @param producedMaterial
	 * @return
	 */
	private IQualityInvoiceLineGroup createQualityInvoiceLineGroup_ProducedMaterial(
			final IQualityInspectionLine producedMaterial,
			final QualityInvoiceLineGroupType qualityInvoiceLineGroupType)
	{
		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();

		final QualityInvoiceLineGroup invoiceLineGroup = new QualityInvoiceLineGroup(qualityInvoiceLineGroupType);

		//
		// Case: produced material with Fee (by-products)
		// e.g. this produced material (usually a by-product) needs to be sorted out, so we'll charge a fee on it.
		final I_M_Product product = producedMaterial.getM_Product();
		if (config.isFeeForProducedMaterial(product))
		{
			//
			// Total produced line (this is our reference for calculations)
			// e.g. Karotten netto gewaschen
			{
				final IQualityInspectionLine producedTotal = getQualityInspectionLinesCollection().getByType(QualityInspectionLineType.ProducedTotal);
				final QualityInvoiceLine detail = createQualityInvoiceLine(producedTotal);
				detail.setDisplayed(true);
				invoiceLineGroup.addDetailBefore(detail);
			}
			//
			// By-Products line (qty negated)
			// e.g. Ausfall (Futterkarotten)
			{
				final QualityInvoiceLine detail = createQualityInvoiceLine(producedMaterial);
				detail.setDisplayed(true);
				invoiceLineGroup.addDetailBefore(detail);

				detail.setQty(producedMaterial.getQtyProjected().negate());
			}
			//
			// Processing fee for By-Products
			// e.g. Zusaetzliche Sortierkosten
			// Detail with the invoiceable line's "label"
			{
				final QualityInvoiceLine detail = createQualityInvoiceLine(producedMaterial);

				invoiceLineGroup.setInvoiceableLineOverride(detail);
				detail.setDisplayed(true);
				detail.setProductName(config.getFeeNameForProducedProduct(product));
			}
			// actual invoiceable line
			{
				final QualityInvoiceLine invoiceableLine = createQualityInvoiceLine(producedMaterial);
				invoiceLineGroup.setInvoiceableLine(invoiceableLine);

				invoiceableLine.setDisplayed(true);
				invoiceableLine.setProductName(config.getFeeNameForProducedProduct(product));

				// Pricing
				final BigDecimal feeProductPercent = producedMaterial.getPercentage();
				final BigDecimal feeAmt = config.getFeeForProducedMaterial(product, feeProductPercent);

				final IPricingContext pricingContext = createPricingContext(invoiceableLine);
				final IPricingResult pricingResult = createPricingResult(pricingContext, feeAmt.negate(), invoiceableLine.getC_UOM());
				invoiceableLine.setPrice(pricingResult);
				invoiceableLine.setDisplayed(true);
			}
		}
		//
		// Case: produced material (without Free)
		else
		{
			final QualityInvoiceLine invoiceableLine = createQualityInvoiceLine(producedMaterial);
			invoiceLineGroup.setInvoiceableLine(invoiceableLine);

			// Pricing
			final IPricingContext pricingCtx = createPricingContext(invoiceableLine);
			final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
			invoiceableLine.setPrice(pricingResult);
			invoiceableLine.setDisplayed(true);
		}

		addCreatedInvoiceLineGroup(invoiceLineGroup);
		return invoiceLineGroup;
	}

	private IQualityInvoiceLineGroup createQualityInvoiceLineGroup_WithholdingAmount()
	{
		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();
		if (config.getOverallNumberOfInvoicings() < 2)
		{
			logger.debug("Nothing to do as ILagerKonfQualityBasedConfig {} has OverallNumberOfInvoicings = {}",
					new Object[] { config, config.getOverallNumberOfInvoicings() });
			return null; // nothing to do
		}

		final IInvoicingItem withholdingItem = config.getWithholdingProduct();
		final BigDecimal withholdingBaseAmount = getTotalInvoiceableAmount();
		final I_M_Product withholdingProduct = withholdingItem.getM_Product();
		final I_C_UOM withholdingPriceUOM = withholdingItem.getC_UOM();

		final BigDecimal withholdingAmount;
		final String labelToUse;

		if (qualityBasedInvoicingBL.isLastInspection(_qiOrder))
		{
			withholdingAmount = _qiOrder.getAlreadyInvoicedNetSum();
			labelToUse = "Akonto-Netto"; // task 08947: the amount was already correct, just use this label
		}
		else
		{
			final BigDecimal withholdingPercent = config.getWithholdingPercent();
			withholdingAmount = withholdingBaseAmount
					.multiply(withholdingPercent)
					.divide(Env.ONEHUNDRED, 12, RoundingMode.HALF_UP); // will be fixed when pricing result is created
			labelToUse = "Einbehalt " + withholdingPercent.setScale(0, RoundingMode.HALF_UP) + "%";
		}

		// Invoice Line Group
		final QualityInvoiceLineGroup invoiceLineGroup = new QualityInvoiceLineGroup(QualityInvoiceLineGroupType.Withholding);

		//
		// Detail: Withholding base
		{
			final QualityInvoiceLine detail = new QualityInvoiceLine();
			invoiceLineGroup.addDetailBefore(detail);

			detail.setDisplayed(false);
			detail.setProductName("Withholding base"); // TRL
			detail.setQty(BigDecimal.ONE);
			detail.setC_UOM(withholdingPriceUOM);
			detail.setM_Product(withholdingProduct);

			// Pricing
			final IPricingContext pricingCtx = createPricingContext(detail);
			final IPricingResult pricingResult = createPricingResult(pricingCtx, withholdingBaseAmount, withholdingPriceUOM);
			detail.setPrice(pricingResult);
		}

		//
		// Withholding
		{
			final QualityInvoiceLine overridingDetail = new QualityInvoiceLine();
			invoiceLineGroup.setInvoiceableLineOverride(overridingDetail);

			overridingDetail.setDisplayed(true);
			overridingDetail.setProductName(labelToUse);
			overridingDetail.setM_Product(withholdingProduct);
			overridingDetail.setQty(BigDecimal.ONE);
			overridingDetail.setC_UOM(withholdingPriceUOM);
		}
		{
			final QualityInvoiceLine invoiceableLine = new QualityInvoiceLine();
			invoiceLineGroup.setInvoiceableLine(invoiceableLine);

			invoiceableLine.setDisplayed(true);
			invoiceableLine.setM_Product(withholdingProduct);
			invoiceableLine.setQty(BigDecimal.ONE);
			invoiceableLine.setC_UOM(withholdingPriceUOM);

			// Pricing
			final IPricingContext pricingCtx = createPricingContext(invoiceableLine);
			final IPricingResult pricingResult = createPricingResult(pricingCtx, withholdingAmount.negate(), withholdingPriceUOM);
			invoiceableLine.setPrice(pricingResult);
		}

		addCreatedInvoiceLineGroup(invoiceLineGroup);
		return invoiceLineGroup;
	}

	/**
	 * Creates and returns a single QualityInvoiceLine that references the given <code>productionOrder</code>.
	 * <p>
	 * Note: we also return a line if there is no QualityAdjustment to be invoiced, in order to keep track of every single regular PP_Order.
	 *
	 * @param productionOrder
	 * @param overallAvgProducedQtyPerTU
	 * @param labelToUse
	 * @return
	 */
	@VisibleForTesting
	private QualityInvoiceLine createQualityInvoiceLineDetail_RegularOrder(
			final IQualityInspectionOrder productionOrder,
			final BigDecimal overallAvgProducedQtyPerTU,
			final String labelToUse)
	{
		//
		// Extract parameters from regular manufacturing norder
		final IQualityInspectionLinesCollection qualityInspectionLines = getQualityInspectionLinesCollection();

		final IProductionMaterial currentRawProductionMaterial = productionOrder.getRawProductionMaterial();
		final I_C_UOM uom = currentRawProductionMaterial.getC_UOM();

		final StringBuilder description = new StringBuilder("PP_Order[IsQualityInspection=" + productionOrder.isQualityInspection()
				+ ", PP_Order.DocumentNo=" + productionOrder.getPP_Order().getDocumentNo() + "]");

		IHandlingUnitsInfo currentRawHUInfo = currentRawProductionMaterial.getHandlingUnitsInfo();
		if (currentRawHUInfo == null)
		{
			logger.info("IQualityInspectionOrder {} has no IHandlingUnitsInfo; computing the probable TU-Qty", productionOrder);

			final IQualityInspectionLine overallRaw = qualityInspectionLines.getByType(QualityInspectionLineType.Raw);
			final IHandlingUnitsInfo overallRawHUInfo = overallRaw.getHandlingUnitsInfoProjected();
			final BigDecimal overallQtyTU = new BigDecimal(overallRawHUInfo.getQtyTU());

			final BigDecimal bdOverallqtyTU = overallQtyTU
					.multiply(currentRawProductionMaterial.getQty())
					.divide(overallRaw.getQtyProjected(), RoundingMode.HALF_UP)
					.setScale(0, RoundingMode.HALF_UP);
			final int currentQtyTUCalc = bdOverallqtyTU.signum() <= 0 ? 1 : bdOverallqtyTU.intValueExact();

			description.append("; Missing HandlingUnitsInfo!"
					+ " Calculated QtyTU=" + currentQtyTUCalc + " as ( overallQtyTU=" + overallQtyTU
					+ " * currentRawProductionMaterialQty=" + currentRawProductionMaterial.getQty()
					+ " / overallRawQtyProjected=" + overallRaw.getQtyProjected() + " )");

			currentRawHUInfo = handlingUnitsInfoFactory.createHUInfoWritableQty(overallRawHUInfo);
			((IHandlingUnitsInfoWritableQty)currentRawHUInfo).setQtyTU(currentQtyTUCalc);
		}
		final BigDecimal currentQty = overallAvgProducedQtyPerTU.multiply(new BigDecimal(currentRawHUInfo.getQtyTU()));

		final QualityInvoiceLine detailForCurrentRegularOrder = createDetailForSingleRegularOrder(uom, currentRawHUInfo, currentQty, labelToUse);
		detailForCurrentRegularOrder.setPP_Order(productionOrder.getPP_Order());
		detailForCurrentRegularOrder.setDescription(description.toString());

		detailForCurrentRegularOrder.setDisplayed(false); // it's never displayed; we just add the line in order to have

		return detailForCurrentRegularOrder;
	}

	/**
	 * Checks if our config has a QualityAdjustment for the given date.
	 *
	 * @param dateOfProduction
	 * @return
	 */
	private boolean isInvoiceRegularOrderForDate(final Timestamp dateOfProduction)
	{
		if (!qualityBasedInvoicingBL.isLastInspection(_qiOrder))
		{
			return false; // for an akonto (downpayment) we don't create "auslagerungen"
		}

		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();

		final BigDecimal priceActual = config.getQualityAdjustmentForDateOrNull(dateOfProduction);
		if (priceActual == null)
		{
			logger.debug("ILagerKonfQualityBasedConfig {} has no price for dateOfProduction {}. Assuming that there is nothing to do", new Object[] { config, dateOfProduction });
			return false;
		}
		return true;
	}

	// private QualityInvoiceLine createDetailLineForRegularOrder(
	// final Timestamp date,
	// final I_C_UOM uom,
	// final IHandlingUnitsInfo currentRawHUInfo,
	// final BigDecimal qty,
	// final String labelToUse)
	// {
	// // Detail
	//
	// final QualityInvoiceLine detail = createDetailForSingleRegularOrder(uom, currentRawHUInfo, qty, labelToUse);
	//
	// return detail;
	// }

	private QualityInvoiceLine createDetailForSingleRegularOrder(final I_C_UOM uom,
			final IHandlingUnitsInfo huInfo,
			final BigDecimal qty,
			final String labelToUse)
	{
		final ILagerKonfQualityBasedConfig config = getQualityBasedConfig();

		final QualityInvoiceLine detail = new QualityInvoiceLine();

		detail.setDisplayed(false); // we only want this detail for reference and QA
		detail.setM_Product(config.getRegularPPOrderProduct());
		detail.setC_UOM(uom);
		detail.setQty(qty);
		detail.setHandlingUnitsInfo(huInfo);
		detail.setProductName(labelToUse);
		return detail;
	}

	private IEditablePricingContext createPricingContext(final IQualityInvoiceLine line)
	{
		final IPricingContext pricingContextInitial = getPricingContext();
		final IEditablePricingContext pricingContext = pricingContextInitial.copy();

		final I_M_Product product = line.getM_Product();
		pricingContext.setM_Product_ID(product == null ? -1 : product.getM_Product_ID());
		pricingContext.setQty(line.getQty());

		final I_C_UOM uom = line.getC_UOM();
		if (uom == null)
		{
			pricingContext.setConvertPriceToContextUOM(false);
			pricingContext.setC_UOM_ID(-1);
		}
		else
		{
			pricingContext.setC_UOM_ID(uom.getC_UOM_ID());
		}
		return pricingContext;
	}

	private IPricingResult createPricingResult(
			final IPricingContext pricingCtx,
			final BigDecimal price,
			final I_C_UOM priceUOM)
	{
		Check.assumeNotNull(price, "Param 'price' not null");
		Check.assumeNotNull(priceUOM, "Param 'priceUOM' not null");

		final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);

		Check.errorIf(!pricingResult.isCalculated() || pricingResult.getC_TaxCategory_ID() <= 0,
				"pricingResult {} which was created from pricingCtx {} has calculated=false and/or least contains no C_TaxCategory_ID; the referenced model is {}",
				pricingResult, pricingCtx, pricingCtx.getReferencedObject());

		final int pricePrecision = pricingResult.getPrecision();
		final BigDecimal priceToSet = price.setScale(pricePrecision, RoundingMode.HALF_UP);

		pricingResult.setC_TaxCategory_ID(pricingResult.getC_TaxCategory_ID());
		pricingResult.setPriceStd(priceToSet);
		pricingResult.setPriceLimit(priceToSet);
		pricingResult.setPriceList(priceToSet);
		pricingResult.setPrice_UOM_ID(priceUOM.getC_UOM_ID());
		pricingResult.setDiscount(BigDecimal.ZERO);
		pricingResult.setCalculated(true);

		return pricingResult;
	}

	private BigDecimal getTotalInvoiceableAmount()
	{
		BigDecimal totalNetAmt = BigDecimal.ZERO;
		for (final IQualityInvoiceLineGroup invoiceLineGroup : _createdInvoiceLineGroups)
		{
			final IQualityInvoiceLine invoiceableLine = invoiceLineGroup.getInvoiceableLine();
			final BigDecimal netAmt = getNetAmount(invoiceableLine);
			totalNetAmt = totalNetAmt.add(netAmt);
		}

		return totalNetAmt;
	}

	private BigDecimal getNetAmount(final IQualityInvoiceLine line)
	{
		Check.assumeNotNull(line, "line not null");

		final IPricingResult pricingResult = line.getPrice();
		Check.assumeNotNull(pricingResult, "pricingResult not null");
		Check.assume(pricingResult.isCalculated(), "Price is calculated for {}", line);

		final BigDecimal price = pricingResult.getPriceStd();
		final int pricePrecision = pricingResult.getPrecision();

		final BigDecimal qty = line.getQty();

		final BigDecimal netAmt = price.multiply(qty).setScale(pricePrecision, RoundingMode.HALF_UP);
		return netAmt;
	}

	/**
	 * Creates an {@link QualityInvoiceLine} instance.
	 *
	 * Product, Qty, UOM are copied from given {@link IQualityInspectionLine}.
	 *
	 * @param qiLine
	 * @return
	 */
	private QualityInvoiceLine createQualityInvoiceLine(final IQualityInspectionLine qiLine)
	{
		final QualityInvoiceLine invoiceLine = new QualityInvoiceLine();
		invoiceLine.setM_Product(qiLine.getM_Product());
		invoiceLine.setProductName(qiLine.getName());
		invoiceLine.setPercentage(qiLine.getPercentage());
		invoiceLine.setQty(qiLine.getQtyProjected());
		invoiceLine.setC_UOM(qiLine.getC_UOM());
		invoiceLine.setHandlingUnitsInfo(qiLine.getHandlingUnitsInfoProjected());
		return invoiceLine;
	}
}
