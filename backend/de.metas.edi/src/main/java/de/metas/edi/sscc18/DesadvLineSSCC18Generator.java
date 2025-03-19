package de.metas.edi.sscc18;

/*
 * #%L
 * de.metas.edi
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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.impl.pack.CreateEDIDesadvPackItemRequest;
import de.metas.edi.api.impl.pack.CreateEDIDesadvPackRequest;
import de.metas.edi.api.impl.pack.EDIDesadvPack;
import de.metas.edi.api.impl.pack.EDIDesadvPackId;
import de.metas.edi.api.impl.pack.EDIDesadvPackService;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator;
import de.metas.handlingunits.allocation.impl.TotalQtyCUBreakdownCalculator.LUQtys;
import de.metas.handlingunits.attributes.sscc18.SSCC18;
import de.metas.handlingunits.attributes.sscc18.impl.SSCC18CodeBL;
import de.metas.handlingunits.generichumodel.PackagingCodeId;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.inout.IInOutBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Producer which is used to generate {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack}s labels and print them.
 *
 * @author tsa
 */
public class DesadvLineSSCC18Generator
{
	private static final Logger logger = LogManager.getLogger(DesadvLineSSCC18Generator.class);

	// services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final SSCC18CodeBL sscc18CodeBL;
	private final IDesadvBL desadvBL;
	private final EDIDesadvPackService ediDesadvPackService;

	//
	// Parameters
	/**
	 * For an {@link I_EDI_DesadvLine}, shall we also print the existing labels?
	 */
	private final boolean printExistingLabels;

	/**
	 * Needed because we need to set the packing-GTIN according to this bpartner's packing-material
	 */
	private final BPartnerId bpartnerId;

	//
	// status
	/**
	 * {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} IDs to print
	 */
	private final Set<EDIDesadvPackId> desadvLineSSCC_IDs_ToPrint = new LinkedHashSet<>();
	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@Builder
	private DesadvLineSSCC18Generator(
			@NonNull final SSCC18CodeBL sscc18CodeService,
			@NonNull final IDesadvBL desadvBL,
			final boolean printExistingLabels,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final EDIDesadvPackService ediDesadvPackService)
	{
		this.sscc18CodeBL = sscc18CodeService;
		this.desadvBL = desadvBL;
		this.printExistingLabels = printExistingLabels;
		this.bpartnerId = bpartnerId;
		this.ediDesadvPackService = ediDesadvPackService;
	}

	/**
	 * Generates {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} records until {@link IPrintableDesadvLineSSCC18Labels#getRequiredSSCC18sCount()} is fulfilled.
	 * <p>
	 * It will enqueue the SSCC18 labels to be printed.
	 * To actually print the labels, call {@link #printAll()}.
	 */
	public void generateAndEnqueuePrinting(@NonNull final IPrintableDesadvLineSSCC18Labels desadvLineLabels)
	{
		final List<EDIDesadvPack> desadvLineSSCCsExisting = desadvLineLabels.getExistingSSCC18s();
		final int countExisting = desadvLineSSCCsExisting.size();
		final int countRequired = desadvLineLabels.getRequiredSSCC18sCount().intValueExact();
		final TotalQtyCUBreakdownCalculator totalQtyCUsRemaining = desadvLineLabels.breakdownTotalQtyCUsToLUs();

		for (int i = 0; i < countRequired; i++)
		{
			// Use existing SSCC if any
			if (i < countExisting)
			{
				final EDIDesadvPack desadvPack = desadvLineSSCCsExisting.get(i);

				// Subtract the "LU" of this SSCC from total QtyCUs remaining
				totalQtyCUsRemaining.subtractLU()
						.setQtyTUsPerLU(desadvPack.getQtyTU())
						.setQtyCUsPerTU(desadvPack.getQtyCUsPerTU())
						.setQtyCUsPerLU_IfGreaterThanZero(desadvPack.getQtyCUsPerLU())
						.build();

				if (printExistingLabels)
				{
					enqueueToPrint(desadvPack);
				}
			}
			// Generate a new SSCC record
			else
			{
				final I_EDI_DesadvLine desadvLine = desadvLineLabels.getEDI_DesadvLine();
				final I_M_HU_PI_Item_Product tuPIItemProduct = desadvLineLabels.getTuPIItemProduct();

				// Subtract one LU from total QtyCUs remaining.
				final LUQtys luQtys = totalQtyCUsRemaining.subtractOneLU();

				final EDIDesadvPack desadvPack = generateDesadvLineSSCC(desadvLine, luQtys, tuPIItemProduct);
				enqueueToPrint(desadvPack);
			}
		}
	}

	/**
	 * Generates {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} records until {@link IPrintableDesadvLineSSCC18Labels#getRequiredSSCC18sCount()} is fullfilled.
	 * <p>
	 * It will enqueue the SSCC18 labels to be printed.
	 * To actually print the labels, call {@link #printAll()}.
	 *
	 * @param desadvLineLabelsCollection collection of {@link IPrintableDesadvLineSSCC18Labels}
	 */
	public void generateAndEnqueuePrinting(final Collection<IPrintableDesadvLineSSCC18Labels> desadvLineLabelsCollection)
	{
		// Do nothing if there is nothing to print
		if (Check.isEmpty(desadvLineLabelsCollection))
		{
			logger.info("Parameter 'desadvLineLabelsCollection' is empty; returning");
			return;
		}

		//
		// Generate all SSCC18 labels in one transaction
		trxManager.runInThreadInheritedTrx(() -> {
			for (final IPrintableDesadvLineSSCC18Labels desadvLineLabels : desadvLineLabelsCollection)
			{
				generateAndEnqueuePrinting(desadvLineLabels);
			}
		});
	}

	/**
	 * Print all enqueued SSCC18 labels.
	 */
	public void printAll()
	{
		if (Check.isEmpty(desadvLineSSCC_IDs_ToPrint))
		{
			// nothing to print
			return;
		}

		desadvBL.printSSCC18_Labels(Env.getCtx(), desadvLineSSCC_IDs_ToPrint);
		desadvLineSSCC_IDs_ToPrint.clear();
	}

	/**
	 * Creates a new {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack} record with a new {@link de.metas.esb.edi.model.I_EDI_Desadv_Pack_Item} record.
	 * <p>
	 * The SSCC18 code will be generated.
	 */
	private EDIDesadvPack generateDesadvLineSSCC(
			@NonNull final I_EDI_DesadvLine desadvLine,
			@NonNull final LUQtys luQtys,
			@NonNull final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		//
		// Generate the actual SSCC18 number and update the SSCC record
		final SSCC18 sscc18 = sscc18CodeBL.generate(OrgId.ofRepoId(desadvLine.getAD_Org_ID()));
		final String ipaSSCC18 = sscc18.asString(); // humanReadable=false

		// Create SSCC record
		final CreateEDIDesadvPackItemRequest createEDIDesadvPackItemRequest = buildCreateEDIDesadvPackItemRequest(desadvLine, luQtys, tuPIItemProduct);

		// PackagingCodes and PackagingGTINs
		final int packagingCodeLU_ID = tuPIItemProduct.getM_HU_PackagingCode_LU_Fallback_ID();

		final CreateEDIDesadvPackRequest createEDIDesadvPackRequest = CreateEDIDesadvPackRequest.builder()
				.orgId(OrgId.ofRepoId(desadvLine.getAD_Org_ID()))
				.seqNo(0) // let the repo fix the next free SeqNo on the fly 
				.ediDesadvId(EDIDesadvId.ofRepoId(desadvLine.getEDI_Desadv_ID()))
				.sscc18(ipaSSCC18)
				.isManualIpaSSCC(true)
				.huPackagingCodeID(PackagingCodeId.ofRepoIdOrNull(packagingCodeLU_ID))
				.gtinPackingMaterial(tuPIItemProduct.getGTIN_LU_PackingMaterial_Fallback())
				.createEDIDesadvPackItemRequest(createEDIDesadvPackItemRequest)
				.build();

		return ediDesadvPackService.createDesadvPack(createEDIDesadvPackRequest);
	}

	@NonNull
	private CreateEDIDesadvPackItemRequest buildCreateEDIDesadvPackItemRequest(
			@NonNull final I_EDI_DesadvLine desadvLine,
			@NonNull final LUQtys luQtys,
			@NonNull final I_M_HU_PI_Item_Product tuPIItemProduct)
	{
		final UomId stockUOMId = UomId.ofRepoId(desadvLine.getC_UOM_ID());
		final Quantity qtyCUsPerTU = Quantitys.of(luQtys.getQtyCUsPerTU(), stockUOMId);
		final Quantity qtyCUsPerLU = Quantitys.of(luQtys.getQtyCUsPerLU(), stockUOMId);

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofCode(desadvLine.getInvoicableQtyBasedOn());
		final List<I_M_InOutLine> lines = desadvBL.retrieveAllInOutLines(desadvLine);
		final UomId invoiceUomId = UomId.ofRepoIdOrNull(desadvLine.getC_UOM_Invoice_ID());

		final BigDecimal qtyCUPerTUinInvoiceUOM;
		final BigDecimal qtyCUPerLUinInvoiceUOM;
		if (invoicableQtyBasedOn.isCatchWeight() && !lines.isEmpty())
		{
			final BigDecimal uomToStockRatio = lines.stream()
					.map(line -> inOutBL.extractInOutLineQty(line, invoicableQtyBasedOn))
					.reduce(StockQtyAndUOMQty::add)
					.map(StockQtyAndUOMQty::getUOMToStockRatio)
					.orElseThrow(() -> new AdempiereException("Invoicable Quantity Based on is CatchWeight, but ratio is missing!"));

			qtyCUPerTUinInvoiceUOM = qtyCUsPerTU.toBigDecimal().multiply(uomToStockRatio);
			qtyCUPerLUinInvoiceUOM = qtyCUsPerLU.toBigDecimal().multiply(uomToStockRatio);
		}
		else if (invoiceUomId != null)
		{
			if (uomDAO.isUOMForTUs(qtyCUsPerTU.getUomId()) && desadvLine.getQtyItemCapacity().signum() > 0)
			{
				qtyCUPerTUinInvoiceUOM = qtyCUsPerTU.toBigDecimal().multiply(desadvLine.getQtyItemCapacity());
			}
			else
			{
				final UOMConversionContext conversionCtx = UOMConversionContext.of(ProductId.ofRepoId(desadvLine.getM_Product_ID()));
				qtyCUPerTUinInvoiceUOM = uomConversionBL.convertQuantityTo(
								qtyCUsPerTU,
								conversionCtx,
								invoiceUomId)
						.toBigDecimal();
			}
			if (Services.get(IUOMDAO.class).isUOMForTUs(qtyCUsPerLU.getUomId()) && desadvLine.getQtyItemCapacity().signum() > 0)
			{
				qtyCUPerLUinInvoiceUOM = qtyCUsPerLU.toBigDecimal().multiply(desadvLine.getQtyItemCapacity());
			}
			else
			{
				final UOMConversionContext conversionCtx = UOMConversionContext.of(ProductId.ofRepoId(desadvLine.getM_Product_ID()));
				qtyCUPerLUinInvoiceUOM = uomConversionBL.convertQuantityTo(
								qtyCUsPerLU,
								conversionCtx,
								invoiceUomId)
						.toBigDecimal();
			}
		}
		else
		{
			qtyCUPerTUinInvoiceUOM = null;
			qtyCUPerLUinInvoiceUOM = null;
		}

		final CreateEDIDesadvPackItemRequest.CreateEDIDesadvPackItemRequestBuilder createEDIDesadvPackItemRequestBuilder = CreateEDIDesadvPackItemRequest.builder()
				.ediDesadvLineId(EDIDesadvLineId.ofRepoId(desadvLine.getEDI_DesadvLine_ID()))
				.line(desadvLine.getLine())
				.qtyCUsPerTU(qtyCUsPerTU.toBigDecimal())
				.qtyTu(luQtys.getQtyTUsPerLU().intValueExact())
				.qtyCUsPerLU(qtyCUsPerLU.toBigDecimal())
				.movementQtyInStockUOM(qtyCUsPerLU.toBigDecimal())
				.qtyCUPerTUinInvoiceUOM(qtyCUPerTUinInvoiceUOM)
				.qtyCUsPerLUinInvoiceUOM(qtyCUPerLUinInvoiceUOM);

		ediDesadvPackService.setPackRecordPackagingCodeAndGTIN(createEDIDesadvPackItemRequestBuilder, tuPIItemProduct, bpartnerId, desadvLine);

		return createEDIDesadvPackItemRequestBuilder
				.build();
	}

	private void enqueueToPrint(@NonNull final EDIDesadvPack desadvLineSSCC)
	{
		desadvLineSSCC_IDs_ToPrint.add(desadvLineSSCC.getEdiDesadvPackId());
	}

	public ImmutableSet<EDIDesadvPackId> getLineSSCCIdsToPrint()
	{
		return ImmutableSet.copyOf(desadvLineSSCC_IDs_ToPrint);
	}

}
