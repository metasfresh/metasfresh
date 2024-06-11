package de.metas.invoicecandidate.spi.impl.aggregator.standard;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutLineId;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceLineAggregationRequest;
import de.metas.invoicecandidate.api.IInvoiceLineAttribute;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.material.MovementType;
import de.metas.money.CurrencyId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_M_InOutLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.fail;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;

public final class InvoiceCandidateWithInOutLine
{
	// services
	private final MatchInvoiceService matchInvoiceService;

	private final I_C_Invoice_Candidate ic;
	private final I_C_InvoiceCandidate_InOutLine iciol;
	
	@Getter
	private final Set<IInvoiceLineAttribute> attributesFromInoutLines;
	
	/**
	 *  Specifies if, when the aggregation is done and if 
	 *  is not <code>null</code> the full remaining <code>QtyToInvoice</code> of the invoice candidate shall
	 *  be allocated to the <code>icIol</code>'s invoice line, or not. If <code>false</code>, then the maximum qty to be allocated is the delivered qty.
	 *  <p>
	 *  Note that in each aggregation, we assume that there is exactly one request with 
	 *  = <code>true</code>, in order to make sure that the invoice candidate's
	 *  qtyToInvoice is actually invoiced.
	 */
	@Getter
	private final boolean allocateRemainingQty;

	@Getter
	private final ProductId productId;

	@Getter
	private final UomId icUomId;

	@Getter
	private final CurrencyId currencyId;

	@Getter
	private final InvoiceCandidateId invoicecandidateId;

	public InvoiceCandidateWithInOutLine(
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final IInvoiceLineAggregationRequest request)
	{
		this.matchInvoiceService = matchInvoiceService;

		this.ic = request.getC_Invoice_Candidate();
		this.iciol = request.getC_InvoiceCandidate_InOutLine();
		this.allocateRemainingQty = request.isAllocateRemainingQty();
		this.attributesFromInoutLines = ImmutableSet.copyOf(request.getAttributesFromInoutLines());

		this.invoicecandidateId = InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID());
		this.productId = ProductId.ofRepoId(ic.getM_Product_ID());
		this.icUomId = UomId.ofRepoId(ic.getC_UOM_ID());
		this.currencyId = CurrencyId.ofRepoId(ic.getC_Currency_ID());
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** @return invoice candidate; never return <code>null</code> */
	public I_C_Invoice_Candidate getC_Invoice_Candidate()
	{
		return ic;
	}

	/** @return shipment/receipt line; could be <code>null</code> */
	@Nullable
	public I_M_InOutLine getM_InOutLine()
	{
		if (iciol == null)
		{
			return null;
		}
		return iciol.getM_InOutLine();
	}

	public StockQtyAndUOMQty getQtysAlreadyInvoiced()
	{
		final StockQtyAndUOMQty zero = StockQtyAndUOMQtys.createZero(productId, icUomId);
		if (iciol == null)
		{
			return zero;
		}
		else
		{
			final InOutLineId inoutLineId = InOutLineId.ofRepoId(iciol.getM_InOutLine_ID());
			return matchInvoiceService.getMaterialQtyMatched(inoutLineId, zero);
		}
	}

	public StockQtyAndUOMQty getQtysAlreadyShipped()
	{
		final I_M_InOutLine inOutLine = getM_InOutLine();
		if (inOutLine == null)
		{
			return StockQtyAndUOMQtys.createZero(productId, icUomId);
		}

		final InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.ofNullableCodeOrNominal(ic.getInvoicableQtyBasedOn());
		final BigDecimal uomQty;

		if (!isNull(iciol, I_C_InvoiceCandidate_InOutLine.COLUMNNAME_QtyDeliveredInUOM_Override))
		{
			uomQty = iciol.getQtyDeliveredInUOM_Override();
		}
		else
		{
			switch (invoicableQtyBasedOn)
			{
				case CatchWeight:
					uomQty = coalesce(iciol.getQtyDeliveredInUOM_Catch(), iciol.getQtyDeliveredInUOM_Nominal());
					break;
				case NominalWeight:
					uomQty = iciol.getQtyDeliveredInUOM_Nominal();
					break;
				default:
					fail("Unexpected invoicableQtyBasedOn={}", invoicableQtyBasedOn);
					uomQty = null;
					break;
			}
		}

		final BigDecimal stockQty = inOutLine.getMovementQty();
		final StockQtyAndUOMQty deliveredQty = StockQtyAndUOMQtys
				.create(
						stockQty, productId,
						uomQty, UomId.ofRepoId(iciol.getC_UOM_ID()));

		if (MovementType.isMaterialReturn(inOutLine.getM_InOut().getMovementType()))
		{
			return deliveredQty.negate();
		}
		return deliveredQty;
	}

	public boolean isShipped()
	{
		return getM_InOutLine() != null;
	}

	public I_C_InvoiceCandidate_InOutLine getC_InvoiceCandidate_InOutLine()
	{
		return iciol;
	}

}
