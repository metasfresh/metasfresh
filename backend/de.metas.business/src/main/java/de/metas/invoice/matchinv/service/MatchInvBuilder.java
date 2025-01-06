package de.metas.invoice.matchinv.service;

import de.metas.common.util.time.SystemTime;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.listeners.MatchInvListenersRegistry;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.material.MovementType;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * Helper class used to create a quantity matching between {@link I_C_InvoiceLine} and {@link I_M_InOutLine} (i.e. {@link I_M_MatchInv}).
 *
 * @author tsa
 */
public class MatchInvBuilder
{
	//
	// services
	private final MatchInvoiceService matchInvoiceService;
	private final IInvoiceBL invoiceBL;
	private final IInOutBL inoutBL;
	private final IProductBL productBL;
	private final @NonNull MatchInvListenersRegistry listenersRegistry;

	//
	// Parameters
	private MatchInvType type;
	private I_C_InvoiceLine _invoiceLine;
	private I_M_InOutLine _inoutLine;
	private MatchInvCostPart inoutCost;
	private Instant _dateTrx;
	private StockQtyAndUOMQty _qtyToMatchExact;
	private boolean _considerQtysAlreadyMatched = true;
	private boolean _allowQtysOfOppositeSigns = false;
	private boolean _skipIfMatchingsAlreadyExist = false;

	//
	// Status
	private boolean _built = false;
	private I_C_Invoice _invoice = null; // lazy
	private I_M_InOut _inout = null; // lazy
	private Boolean _materialReturns = null; // lazy
	private Boolean _creditMemoInvoice = null; // lazy

	MatchInvBuilder(
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final IInvoiceBL invoiceBL,
			@NonNull final IInOutBL inoutBL,
			@NonNull final IProductBL productBL,
			@NonNull final MatchInvListenersRegistry listenersRegistry)
	{
		this.matchInvoiceService = matchInvoiceService;
		this.invoiceBL = invoiceBL;
		this.inoutBL = inoutBL;
		this.productBL = productBL;
		this.listenersRegistry = listenersRegistry;
	}

	/**
	 * Creates and process the {@link I_M_MatchInv}.
	 *
	 * @return <ul>
	 * <li>true if the {@link I_M_MatchInv} was created and processed.
	 * <li>false if there was NO need to create the matching.
	 * </ul>
	 */
	public boolean build()
	{
		markBuilt();

		if (isSkipBecauseMatchingsAlreadyExist())
		{
			return false;
		}

		final StockQtyAndUOMQty qtyToMatch = calculateQtyToMatch();
		if (qtyToMatch.signum() == 0)
		{
			return false;
		}

		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		final I_M_InOutLine inoutLine = getInOutLine();

		// Create the new M_MatchInv record
		final I_M_MatchInv matchInv = InterfaceWrapperHelper.newInstance(I_M_MatchInv.class);
		matchInv.setType(getType().getCode());
		matchInv.setIsSOTrx(isSOTrx());
		//matchInv.setDocumentNo(inout.getDocumentNo());
		matchInv.setAD_Org_ID(invoiceLine.getAD_Org_ID());

		// Invoice
		matchInv.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
		matchInv.setC_InvoiceLine(invoiceLine);

		// InOut
		matchInv.setM_InOut_ID(inoutLine.getM_InOut_ID());
		matchInv.setM_InOutLine(inoutLine);
		final MatchInvCostPart inoutCost = getInoutCost();
		if (inoutCost != null)
		{
			MatchInvoiceRepository.updateRecord(matchInv, inoutCost);
		}

		// Quantity
		matchInv.setQty(qtyToMatch.getStockQty().toBigDecimal());
		matchInv.setQtyInUOM(qtyToMatch.getUOMQtyNotNull().toBigDecimal());
		matchInv.setC_UOM_ID(qtyToMatch.getUOMQtyNotNull().getUomId().getRepoId());

		// Product & ASI
		matchInv.setM_Product_ID(getProductId().getRepoId());
		matchInv.setM_AttributeSetInstance_ID(inoutLine.getM_AttributeSetInstance_ID());

		//
		// Set DateTrx
		final Instant dateTrx = getDateTrx();
		if (dateTrx != null)
		{
			matchInv.setDateTrx(Timestamp.from(dateTrx));
		}
		if (matchInv.getDateTrx() == null)
		{
			matchInv.setDateTrx(SystemTime.asDayTimestamp());
		}

		//
		// Set Acct Date
		matchInv.setDateAcct(Timestamp.from(getDateAcct()));

		matchInv.setProcessed(true);
		InterfaceWrapperHelper.save(matchInv);

		listenersRegistry.fireAfterCreated(MatchInvoiceRepository.fromRecord(matchInv));

		return true;
	}

	private void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built: {}", this);
	}

	private void markBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	public MatchInvBuilder type(@NonNull final MatchInvType type)
	{
		this.type = type;
		return this;
	}

	private MatchInvType getType()
	{
		return Check.assumeNotNull(type, "type is set");
	}

	public MatchInvBuilder invoiceLine(final I_C_InvoiceLine invoiceLine)
	{
		assertNotBuilt();
		this._invoiceLine = invoiceLine;
		return this;
	}

	private I_C_InvoiceLine getInvoiceLine()
	{
		return Check.assumeNotNull(_invoiceLine, "_invoiceLine not null");
	}

	private InvoiceAndLineId getInvoiceLineId()
	{
		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		return InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());
	}

	private I_C_Invoice getInvoice()
	{
		if (_invoice == null)
		{
			_invoice = getInvoiceLine().getC_Invoice();
		}
		return _invoice;
	}

	public MatchInvBuilder inoutLine(final I_M_InOutLine inoutLine)
	{
		assertNotBuilt();
		this._inoutLine = inoutLine;
		return this;
	}

	private I_M_InOutLine getInOutLine()
	{
		return Check.assumeNotNull(_inoutLine, "_inoutLine not null");
	}

	public InOutLineId getInOutLineId()
	{
		return InOutLineId.ofRepoId(getInOutLine().getM_InOutLine_ID());
	}

	private I_M_InOut getInOut()
	{
		if (_inout == null)
		{
			final I_M_InOutLine inoutLine = getInOutLine();
			_inout = inoutLine.getM_InOut();
		}
		return _inout;
	}

	public MatchInvBuilder inoutCost(@Nullable MatchInvCostPart inoutCost)
	{
		this.inoutCost = inoutCost;
		return this;
	}

	@Nullable
	private InOutCostId getInoutCostId()
	{
		final MatchInvCostPart inoutCost = getInoutCost();
		return inoutCost != null ? inoutCost.getInoutCostId() : null;
	}

	@Nullable
	private MatchInvCostPart getInoutCost()
	{
		final MatchInvType type = getType();
		if (MatchInvType.Cost.equals(type))
		{
			return Check.assumeNotNull(inoutCost, "inoutCost is set");
		}
		else
		{
			Check.assumeNull(inoutCost, "inoutCost shall be not set");
			return null;
		}
	}

	/**
	 * Set the exact quantity which shall be matched.
	 * When then quantity to be matched is specified, then the builder:
	 * <ul>
	 * <li>will NOT check and validate again previous matched quantities, i.e. {@link #considerQtysAlreadyMatched(boolean)} will be ignored.
	 * <li>{@link #allowQtysOfOppositeSigns()} will not be considered because makes no sense.
	 * <li>regular invoice/credit memo and regular inout/material returns will NOT be checked if they are compatible.
	 * </ul>
	 */
	public MatchInvBuilder qtyToMatchExact(final StockQtyAndUOMQty qtyToMatchExact)
	{
		assertNotBuilt();
		this._qtyToMatchExact = qtyToMatchExact;
		return this;
	}

	@Nullable
	private StockQtyAndUOMQty getQtyToMatchExact()
	{
		return _qtyToMatchExact;
	}

	/**
	 * Sets if previous matched quantities shall be considered when calculating how much it can be allocated.
	 * NOTE:
	 * <ul>
	 * <li>by default, this option is enabled
	 * <li>this option has no effect if the quantity to be matched was specified (see {@link #qtyToMatchExact(StockQtyAndUOMQty)})
	 * </ul>
	 */
	public MatchInvBuilder considerQtysAlreadyMatched(final boolean considerQtysAlreadyMatched)
	{
		assertNotBuilt();
		this._considerQtysAlreadyMatched = considerQtysAlreadyMatched;
		return this;
	}

	private boolean isConsiderQtysAlreadyMatched()
	{
		return _considerQtysAlreadyMatched;
	}

	/**
	 * Enables that "quantity invoiced but not matched" and "quantity shipped/received but not matched" shall be matched when they are of opposite signs.
	 */
	public MatchInvBuilder allowQtysOfOppositeSigns()
	{
		assertNotBuilt();
		this._allowQtysOfOppositeSigns = true;
		return this;
	}

	private boolean isAllowQtysOfOppositeSigns()
	{
		return _allowQtysOfOppositeSigns;
	}

	private StockQtyAndUOMQty calculateQtyToMatch()
	{
		//
		// Consider the QtyToMatch which was precisely specified.
		// In this case we are not doing further checking.
		final StockQtyAndUOMQty qtyToMatchExact = getQtyToMatchExact();
		if (qtyToMatchExact != null)
		{
			return qtyToMatchExact;
		}

		//
		// Make sure we are matching only:
		// * regular invoice to regular inout
		// * credit memo invoice to material returns
		final boolean creditMemoInvoice = isCreditMemoInvoice();
		final boolean materialReturns = isMaterialReturns();
		if (creditMemoInvoice != materialReturns)
		{
			// NOTE: we are not throwing exception because this will push caller code to handle this exception, or to do the checking before,
			// which makes no sense.

			// throw new MatchInvException("Cannot mix regular documents with credit memos / material returns.");
			return StockQtyAndUOMQtys.createZero(ProductId.ofRepoId(_invoiceLine.getM_Product_ID()), UomId.ofRepoId(_invoiceLine.getC_UOM_ID()));
		}

		//
		// Get the quantity invoiced not matched and quantity moved and calculate how much we can match.
		final StockQtyAndUOMQty qtyInvoicedNotMatched = getQtyInvoicedNotMatched();
		final int qtyInvoicedNotMatchedSignum = qtyInvoicedNotMatched.signum();
		//
		StockQtyAndUOMQty qtyMovedNotMatched = getQtyMovedNotMatchedInStockUOM();
		final int qtyMovedNotMatchedSignum = qtyMovedNotMatched.signum();

		//
		final StockQtyAndUOMQty qtyMatched;
		if(qtyInvoicedNotMatchedSignum == 0)
		{
			qtyMatched = StockQtyAndUOMQtys.createZero(ProductId.ofRepoId(_invoiceLine.getM_Product_ID()), UomId.ofRepoId(_invoiceLine.getC_UOM_ID()));
		}
		else if (qtyInvoicedNotMatchedSignum > 0)
		{
			if (qtyMovedNotMatchedSignum > 0 || isAllowQtysOfOppositeSigns())
			{
				qtyMatched = StockQtyAndUOMQtys.minUomQty(qtyInvoicedNotMatched, qtyMovedNotMatched);
			}
			else
			// qtyMovedNotMatchedSignum < 0
			{
				// NOTE: we are not matching quantities of opposite signs
				qtyMatched = StockQtyAndUOMQtys.createZero(ProductId.ofRepoId(_invoiceLine.getM_Product_ID()), UomId.ofRepoId(_invoiceLine.getC_UOM_ID()));
			}
		}
		else
		// qtyInvoicedNotMatchedSignum < 0
		{
			if (qtyMovedNotMatchedSignum < 0 || isAllowQtysOfOppositeSigns())
			{
				qtyMatched = StockQtyAndUOMQtys.maxUomQty(qtyInvoicedNotMatched, qtyMovedNotMatched);
			}
			else
			// qtyMovedNotMatchedSignum > 0
			{
				// NOTE: we are not matching quantities of opposite signs
				qtyMatched = StockQtyAndUOMQtys.createZero(ProductId.ofRepoId(_invoiceLine.getM_Product_ID()), UomId.ofRepoId(_invoiceLine.getC_UOM_ID()));
			}
		}

		return qtyMatched;
	}

	private StockQtyAndUOMQty getQtyInvoicedNotMatched()
	{
		if (getInoutCost() != null)
		{
			throw new AdempiereException("Computing qty invoiced is supported only for material costs");
		}

		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		final StockQtyAndUOMQty qtyInvoiced = extractQtyInvoiced(invoiceLine)
				.negateIf(isCreditMemoInvoice());

		if (isConsiderQtysAlreadyMatched())
		{
			final StockQtyAndUOMQty qtyMatched = matchInvoiceService.getMaterialQtyMatched(invoiceLine);
			return StockQtyAndUOMQtys.subtract(qtyInvoiced, qtyMatched);
		}
		else
		{
			return qtyInvoiced;
		}
	}

	private static StockQtyAndUOMQty extractQtyInvoiced(final I_C_InvoiceLine invoiceLine)
	{
		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(invoiceLine.getC_UOM_ID());

		return StockQtyAndUOMQtys.create(
				invoiceLine.getQtyInvoiced(), productId,
				invoiceLine.getQtyEntered(), uomId);
	}

	private StockQtyAndUOMQty getQtyMovedNotMatchedInStockUOM()
	{
		if (getInoutCost() != null)
		{
			throw new AdempiereException("Computing qty moved is supported only for material costs");
		}

		final I_M_InOutLine inoutLine = getInOutLine();
		final StockQtyAndUOMQty qtyReceived = inoutBL.getStockQtyAndQtyInUOM(inoutLine)
				// Negate the qtyReceived if this is a material return, because we want to have the qtyReceived as an absolute value.
				.negateIf(isMaterialReturns());

		if (isConsiderQtysAlreadyMatched())
		{
			final InOutLineId inoutLineId = InOutLineId.ofRepoId(inoutLine.getM_InOutLine_ID());
			final StockQtyAndUOMQty qtyMatched = matchInvoiceService.getMaterialQtyMatched(inoutLineId, qtyReceived.toZero());

			return StockQtyAndUOMQtys.subtract(qtyReceived, qtyMatched);
		}
		else
		{
			return qtyReceived;
		}
	}

	/**
	 * Sets DateTrx to be used (optional).
	 */
	public MatchInvBuilder dateTrx(final Timestamp dateTrx)
	{
		assertNotBuilt();
		this._dateTrx = dateTrx != null ? dateTrx.toInstant() : null;
		return this;
	}

	@Nullable
	private Instant getDateTrx() {return _dateTrx;}

	@NonNull
	private Instant getDateAcct()
	{
		final Instant invoiceDateAcct = getInvoice().getDateAcct().toInstant();
		final Instant inoutDateAcct = getInOut().getDateAcct().toInstant();
		return invoiceDateAcct.isAfter(inoutDateAcct) ? invoiceDateAcct : inoutDateAcct;
	}

	/**
	 * Enables matching creation to be skipped if there exists at least one matching between invoice line and inout line.
	 */
	public MatchInvBuilder skipIfMatchingsAlreadyExist()
	{
		assertNotBuilt();
		this._skipIfMatchingsAlreadyExist = true;
		return this;
	}

	private boolean isSkipBecauseMatchingsAlreadyExist()
	{
		return _skipIfMatchingsAlreadyExist && matchInvoiceService.hasMatchInvs(getInvoiceLineId(), getInOutLineId(), getInoutCostId());
	}

	/**
	 * @return true if underlying invoice line is part of a credit memo invoice
	 */
	private boolean isCreditMemoInvoice()
	{
		if (_creditMemoInvoice == null)
		{
			_creditMemoInvoice = invoiceBL.isCreditMemo(getInvoice());
		}
		return _creditMemoInvoice;
	}

	/**
	 * @return true if underlying inout line is part of a material returns (customer or vendor).
	 */
	private boolean isMaterialReturns()
	{
		if (_materialReturns == null)
		{
			final I_M_InOut inout = getInOut();
			_materialReturns = MovementType.isMaterialReturn(inout.getMovementType());
		}
		return _materialReturns;
	}

	private ProductId getProductId()
	{
		final I_M_InOutLine inoutLine = getInOutLine();
		final ProductId inoutLineProductId = ProductId.ofRepoId(inoutLine.getM_Product_ID());

		//
		// Make sure M_Product_ID matches
		if (getType().isMaterial())
		{
			final I_C_InvoiceLine invoiceLine = getInvoiceLine();
			final ProductId invoiceLineProductId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
			if (!ProductId.equals(invoiceLineProductId, inoutLineProductId))
			{
				final String invoiceProductName = productBL.getProductValueAndName(invoiceLineProductId);
				final String inoutProductName = productBL.getProductValueAndName(inoutLineProductId);
				throw new AdempiereException("@Invalid@ @M_Product_ID@"
						+ "\n @C_InvoiceLine_ID@: " + invoiceLine + ", @M_Product_ID@=" + invoiceProductName
						+ "\n @M_InOutLine_ID@: " + inoutLine + ", @M_Product_ID@=" + inoutProductName);
			}
		}

		return inoutLineProductId;
	}

	private boolean isSOTrx()
	{
		final I_C_Invoice invoice = getInvoice();
		final I_M_InOut inout = getInOut();

		final boolean invoiceIsSOTrx = invoice.isSOTrx();
		final boolean inoutIsSOTrx = inout.isSOTrx();

		//
		// Make sure IsSOTrx matches
		if (invoiceIsSOTrx != inoutIsSOTrx)
		{
			throw new AdempiereException("@Invalid @IsSOTrx@"
					+ "\n @C_Invoice_ID@: " + invoice + ", @IsSOTrx@=" + invoiceIsSOTrx
					+ "\n @M_InOut_ID@: " + inout + ", @IsSOTrx@=" + inoutIsSOTrx);
		}

		return inoutIsSOTrx;
	}
}
