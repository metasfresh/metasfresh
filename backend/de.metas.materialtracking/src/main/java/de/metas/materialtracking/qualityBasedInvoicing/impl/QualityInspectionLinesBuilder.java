package de.metas.materialtracking.qualityBasedInvoicing.impl;

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


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.util.Env;

import de.metas.materialtracking.qualityBasedInvoicing.IProductionMaterial;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLine;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLineBuilder;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionLinesCollection;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.qualityBasedInvoicing.ProductionMaterialType;
import de.metas.materialtracking.qualityBasedInvoicing.QualityInspectionLineType;
import de.metas.materialtracking.util.QualityBasedInspectionUtils;
import de.metas.util.Check;
import lombok.NonNull;

/**
 * Creates {@link IQualityInspectionLine}s for a given {@link IQualityInspectionOrder}.
 * <p>
 * ts: note that is seems as if the IQualityInspectionOrder does not necessarily have to be an actual quality inspection PP_Order. It might also be regular PP_Order.
 *
 * @author tsa
 *
 */
public class QualityInspectionLinesBuilder
{
	// Parameters
	private final IQualityInspectionOrder _qiOrder;
	private final boolean _buildWithAveragedValues;
	private IVendorReceipt<?> _receiptFromVendor;

	// Result
	private List<IQualityInspectionLine> _createdQualityInspectionLines = null;

	/**
	 *
	 * @param qiOrder the instance for which we build the lines
	 * @param buildWithAveragedValues decides if the averaged values of the given qiOrder shall be used or the actual values
	 */
	public QualityInspectionLinesBuilder(
			@NonNull final IQualityInspectionOrder qiOrder,
			final boolean buildWithAveragedValues)
	{
		_qiOrder = qiOrder;
		_buildWithAveragedValues = buildWithAveragedValues; // task 08848
	}

	public List<IQualityInspectionLine> getCreatedQualityInspectionLines()
	{
		Check.assumeNotNull(_createdQualityInspectionLines, "lines shall be created before");
		return new ArrayList<>(_createdQualityInspectionLines);
	}

	public IQualityInspectionLinesCollection getCreatedQualityInspectionLinesCollection()
	{
		Check.assumeNotNull(_createdQualityInspectionLines, "lines shall be created before");
		return new QualityInspectionLinesCollection(getCreatedQualityInspectionLines(), _qiOrder);
	}

	private IQualityInspectionOrder getQualityInspectionOrder()
	{
		// not null
		return _qiOrder;
	}

	private IProductionMaterial getRawProductionMaterial()
	{
		return getQualityInspectionOrder().getRawProductionMaterial();
	}

	private List<IProductionMaterial> getAllProductionMaterials()
	{
		return getQualityInspectionOrder().getProductionMaterials();
	}

	private IProductionMaterial getScrapProductionMaterial()
	{
		return getQualityInspectionOrder().getScrapProductionMaterial();
	}

	private IProductionMaterial getMainProducedProductionMaterial()
	{
		return getQualityInspectionOrder().getMainProductionMaterial();
	}

	public void setReceiptFromVendor(@NonNull final IVendorReceipt<?> receiptsFromVendor)
	{
		_receiptFromVendor = receiptsFromVendor;
	}

	private IVendorReceipt<?> getReceiptFromVendor()
	{
		Check.assumeNotNull(_receiptFromVendor, "_receiptFromVendor not null");
		return _receiptFromVendor;
	}

	private BigDecimal getQtyReceivedFromVendor(@NonNull final I_C_UOM uomTo)
	{
		final IVendorReceipt<?> receiptFromVendor = getReceiptFromVendor();
		final BigDecimal qtyReceivedFromVendor = receiptFromVendor.getQtyReceived();
		final I_C_UOM qtyReceivedFromVendorUOM = receiptFromVendor.getQtyReceivedUOM();
		if (qtyReceivedFromVendorUOM.getC_UOM_ID() != uomTo.getC_UOM_ID())
		{
			throw new AdempiereException("Cannot convert qty received from vendor from " + qtyReceivedFromVendorUOM.getUOMSymbol() + " to " + uomTo.getUOMSymbol());
		}

		return qtyReceivedFromVendor;
	}

	/**
	 * Create {@link IQualityInspectionLine}s.
	 */
	public void create()
	{
		// Init
		_createdQualityInspectionLines = new ArrayList<IQualityInspectionLine>();

		//
		final IVendorReceipt<?> receiptFromVendor = getReceiptFromVendor();
		final I_C_UOM uom = receiptFromVendor.getQtyReceivedUOM(); // reporting UOM
		final List<IProductionMaterial> productionMaterials = new ArrayList<>(getAllProductionMaterials());

		//
		// Create report lines: RAW Material
		// e.g. Karotten mittel ungewaschen
		final IQualityInspectionLine raw_Line;
		{
			final IProductionMaterial productionMaterial_Raw = getRawProductionMaterial();
			final BigDecimal qty = getQtyFromProductionMaterial(uom, productionMaterial_Raw);
			final BigDecimal qtyProjected = getQtyReceivedFromVendor(uom);

			raw_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.Raw)
					.setProductionMaterial(productionMaterial_Raw)
					.setQty(qty)
					.setQtyProjected(qtyProjected)
					.setHandlingUnitsInfoProjected(receiptFromVendor.getHandlingUnitsInfo())
					.setC_UOM(uom)
					.setPercentage(null) // N/A
					.create();
		}

		//
		// Create report lines: Scrap
		// e.g. Erdbesatz
		final IQualityInspectionLine scrap_Line;
		{
			final IProductionMaterial productionMaterial_Scrap = getScrapProductionMaterial();
			final BigDecimal qty = getQtyFromProductionMaterial(uom, productionMaterial_Scrap);
			final BigDecimal percentage = QualityBasedInspectionUtils.calculatePercentage(
					qty,
					raw_Line.getQty());
			final BigDecimal qtyProjected = QualityBasedInspectionUtils.calculateQtyAsPercentageOf(
					raw_Line.getQtyProjected(),
					percentage,
					uom);
			scrap_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.Scrap)
					.setProductionMaterial(productionMaterial_Scrap)
					.setQty(qty)
					.setQtyProjected(qtyProjected)
					.setC_UOM(uom)
					.setPercentage(percentage)
					.setNegateQty(true) // NOTE: in the report the Scrap Qty is negative
					.create();
		}

		//
		// Create report line: SUM of Qty Produced
		// e.g. Karotten netto gewaschen
		final IQualityInspectionLine producedTotal_Line;
		{
			BigDecimal producedTotal_Qty = BigDecimal.ZERO;
			for (final Iterator<IProductionMaterial> it = productionMaterials.iterator(); it.hasNext();)
			{
				final IProductionMaterial productionMaterial = it.next();
				final ProductionMaterialType type = productionMaterial.getType();
				if (ProductionMaterialType.PRODUCED != type)
				{
					continue;
				}

				final BigDecimal qty = getQtyFromProductionMaterial(uom, productionMaterial);
				producedTotal_Qty = producedTotal_Qty.add(qty);
			}

			final BigDecimal producedTotal_QtyProjected = raw_Line.getQtyProjected().subtract(scrap_Line.getQtyProjected());
			producedTotal_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.ProducedTotal)
					.setName("netto gewaschen")
					.setQty(producedTotal_Qty)
					.setQtyProjected(producedTotal_QtyProjected)
					.setC_UOM(uom)
					.setPercentage(null)
					.setC_UOM(uom)
					.create();
		}

		//
		// Create report lines: Produced By-Products
		// e.g. Ausfall (Futterkarotten)
		final IQualityInspectionLine producedByProductsTotal_Line;
		{
			BigDecimal producedByProductsTotal_Qty = BigDecimal.ZERO;
			BigDecimal producedByProductsTotal_QtyProjected = BigDecimal.ZERO;
			for (final Iterator<IProductionMaterial> it = productionMaterials.iterator(); it.hasNext();)
			{
				final IProductionMaterial productionMaterial = it.next();
				if (ProductionMaterialType.PRODUCED != productionMaterial.getType())
				{
					continue;
				}
				if (!productionMaterial.isByProduct())
				{
					continue;
				}
				it.remove();

				final BigDecimal qty = getQtyFromProductionMaterial(uom, productionMaterial);
				final BigDecimal percentage = QualityBasedInspectionUtils.calculatePercentage(
						qty,
						producedTotal_Line.getQty());
				final BigDecimal qtyProjected = QualityBasedInspectionUtils.calculateQtyAsPercentageOf(
						producedTotal_Line.getQtyProjected(),
						percentage,
						uom);
				newQualityInspectionLine()
						.setQualityInspectionLineType(QualityInspectionLineType.ProducedByProducts)
						.setProductionMaterial(productionMaterial)
						.setQty(qty)
						.setQtyProjected(qtyProjected)
						.setC_UOM(uom)
						.setPercentage(percentage)
						.setNegateQty(true) // NOTE: in the report the By-Product Qty is negative
						.create();

				producedByProductsTotal_Qty = producedByProductsTotal_Qty.add(qty);
				producedByProductsTotal_QtyProjected = producedByProductsTotal_QtyProjected.add(qtyProjected);
			}

			//
			// Create report lines: Produced By-Products Total
			// i.e. SUM of all ProductByProducts lines
			// NOTE: this is an internal line which (atm) does not appear in reports
			producedByProductsTotal_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.ProducedByProductsTotal)
					.setQty(producedByProductsTotal_Qty)
					.setQtyProjected(producedByProductsTotal_QtyProjected)
					.setC_UOM(uom)
					.setPercentage(null) // N/A
					.create();

		}

		//
		// Create report line: SUM of Main Product + CoProducts
		// e.g. Ausbeute (Marktfähige Ware)
		final IQualityInspectionLine producedTotalWithoutByProducts_Line;
		{
			final BigDecimal producedTotalWithoutByProducts_Qty = producedTotal_Line.getQty().subtract(producedByProductsTotal_Line.getQty());
			final BigDecimal producedTotalWithoutByProducts_QtyProjected = producedTotal_Line.getQtyProjected().subtract(producedByProductsTotal_Line.getQtyProjected());
			final BigDecimal producedTotalWithoutByProducts_Percentage = BigDecimal.valueOf(100);
			producedTotalWithoutByProducts_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.ProducedTotalWithoutByProducts)
					.setName("Ausbeute (Marktfähige Ware)")
					.setQty(producedTotalWithoutByProducts_Qty)
					.setQtyProjected(producedTotalWithoutByProducts_QtyProjected)
					.setPercentage(producedTotalWithoutByProducts_Percentage)
					.setC_UOM(uom)
					.create();
		}

		//
		// Create report line: Main Produced Product
		// e.g. Karotten mittel
		// e.g. Product: P000363_Karotten gewaschen
		final IQualityInspectionLine producedMain_Line;
		{
			final IProductionMaterial productionMaterial_MainProducedProduced = getMainProducedProductionMaterial();
			productionMaterials.remove(productionMaterial_MainProducedProduced);

			final BigDecimal producedMain_Qty = getQtyFromProductionMaterial(uom, productionMaterial_MainProducedProduced);
			final BigDecimal producedMain_Percentage = QualityBasedInspectionUtils.calculatePercentage(
					producedMain_Qty,
					producedTotalWithoutByProducts_Line.getQty());
			final BigDecimal producedMain_QtyProjected = QualityBasedInspectionUtils.calculateQtyAsPercentageOf(
					producedTotalWithoutByProducts_Line.getQtyProjected(),
					producedMain_Percentage,
					uom);
			producedMain_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.ProducedMain)
					.setProductionMaterial(productionMaterial_MainProducedProduced)
					.setQty(producedMain_Qty)
					.setQtyProjected(producedMain_QtyProjected)
					.setC_UOM(uom)
					.setPercentage(producedMain_Percentage)
					.create();
		}

		//
		// Create report lines: Produced co-products
		// e.g. Karotten gross
		final IQualityInspectionLine producedCoProductsTotal_Line;
		{
			BigDecimal producedCoProducts_Qty = BigDecimal.ZERO;
			BigDecimal producedCoProducts_QtyProjected = BigDecimal.ZERO;
			BigDecimal producedCoProducts_Percentage = BigDecimal.ZERO;
			for (final Iterator<IProductionMaterial> it = productionMaterials.iterator(); it.hasNext();)
			{
				final IProductionMaterial productionMaterial = it.next();
				if (ProductionMaterialType.PRODUCED != productionMaterial.getType())
				{
					continue;
				}
				it.remove();

				final BigDecimal qty = getQtyFromProductionMaterial(uom, productionMaterial);
				producedCoProducts_Qty = producedCoProducts_Qty.add(qty);

				final BigDecimal percentage = QualityBasedInspectionUtils.calculatePercentage(
						qty,
						producedTotalWithoutByProducts_Line.getQty());
				producedCoProducts_Percentage = producedCoProducts_Percentage.add(percentage);

				final BigDecimal qtyProjected = QualityBasedInspectionUtils.calculateQtyAsPercentageOf(
						producedTotalWithoutByProducts_Line.getQtyProjected(),
						percentage,
						uom);
				producedCoProducts_QtyProjected = producedCoProducts_QtyProjected.add(qtyProjected);

				newQualityInspectionLine()
						.setQualityInspectionLineType(QualityInspectionLineType.ProducedCoProducts)
						.setProductionMaterial(productionMaterial)
						.setQty(qty)
						.setQtyProjected(qtyProjected)
						.setC_UOM(uom)
						.setPercentage(percentage)
						.create();
			}

			producedCoProductsTotal_Line = newQualityInspectionLine()
					.setQualityInspectionLineType(QualityInspectionLineType.ProducedCoProductsTotal)
					.setQty(producedCoProducts_Qty)
					.setQtyProjected(producedCoProducts_QtyProjected)
					.setC_UOM(uom)
					.setPercentage(producedCoProducts_Percentage)
					.create();
		}

		//
		// Adjust main product report line
		// NOTE: this can happen because of rounding issues
		{
			final BigDecimal producedMain_Percentage_Calc = Env.ONEHUNDRED.subtract(producedCoProductsTotal_Line.getPercentage());
			producedMain_Line.setPercentage(producedMain_Percentage_Calc);

			final BigDecimal producedMain_Qty_Calc = producedTotalWithoutByProducts_Line.getQty().subtract(producedCoProductsTotal_Line.getQty());
			producedMain_Line.setQty(producedMain_Qty_Calc);

			final BigDecimal producedMain_QtyProjected_Calc = producedTotalWithoutByProducts_Line.getQtyProjected().subtract(producedCoProductsTotal_Line.getQtyProjected());
			producedMain_Line.setQtyProjected(producedMain_QtyProjected_Calc);
		}
	}

	private BigDecimal getQtyFromProductionMaterial(final I_C_UOM uom, final IProductionMaterial productionMaterial)
	{
		final BigDecimal result;

		// note: if the average value is 0 we fall back to the actual value.
		// this makes us more lenient towards the current unit tests.
		// imho it's also justifyiable because if the avg qty is null it means that the normal qty is also null
		if (_buildWithAveragedValues && productionMaterial.getQM_QtyDeliveredAvg(uom).signum() != 0)
		{
			result = productionMaterial.getQM_QtyDeliveredAvg(uom);
		}
		else
		{
			result = productionMaterial.getQty(uom);
		}
		return result;
	}

	private final IQualityInspectionLineBuilder newQualityInspectionLine()
	{
		final IQualityInspectionLineBuilder reportLineBuilder = new QualityInspectionLineBuilder()
		{
			@Override
			protected void afterLineCreated(final IQualityInspectionLine line)
			{
				_createdQualityInspectionLines.add(line);
			}
		};

		return reportLineBuilder;
	}
}
