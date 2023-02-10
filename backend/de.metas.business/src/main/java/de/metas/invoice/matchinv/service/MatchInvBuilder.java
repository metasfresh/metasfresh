package de.metas.invoice.matchinv.service;

import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.material.MovementType;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Helper class used to create a quantity matching between {@link I_C_InvoiceLine} and {@link I_M_InOutLine} (i.e. {@link I_M_MatchInv}).
 *
 * @author tsa
 */
@ToString
public class MatchInvBuilder
{
	//
	// services
	private final MatchInvoiceService matchInvoiceService;
	private final IInvoiceBL invoiceBL;

	//
	// Parameters
	private I_C_InvoiceLine _invoiceLine;
	private I_M_InOutLine _inoutLine;
	private Timestamp _dateTrx;
	private StockQtyAndUOMQty _qtyToMatchExact;
	private boolean _considerQtysAlreadyMatched = true;
	private boolean _allowQtysOfOppositeSigns = false;
	private boolean _skipIfMatchingsAlreadyExist = false;

	//
	// Status
	private boolean _built = false;
	private Boolean _materialReturns = null; // lazy
	private Boolean _creditMemoInvoice = null; // lazy

	MatchInvBuilder(
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final IInvoiceBL invoiceBL)
	{
		this.matchInvoiceService = matchInvoiceService;
		this.invoiceBL = invoiceBL;
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
		final I_C_Invoice invoice = getInvoice();
		final I_M_InOutLine inoutLine = getInOutLine();
		final I_M_InOut inout = getInOut();

		//
		// Make sure IsSOTrx matches
		if (invoice.isSOTrx() != inout.isSOTrx())
		{
			throw new AdempiereException("@Invalid @IsSOTrx@"
					+ "\n @C_Invoice_ID@: " + invoice + ", @IsSOTrx@=" + invoice.isSOTrx()
					+ "\n @M_InOut_ID@: " + inout + ", @IsSOTrx@=" + inout.isSOTrx());
		}

		//
		// Make sure M_Product_ID matches
		final ProductId invoiceLineProductId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final ProductId inoutLineProductId = ProductId.ofRepoId(inoutLine.getM_Product_ID());
		if (!ProductId.equals(invoiceLineProductId, inoutLineProductId))
		{
			final IProductBL productBL = Services.get(IProductBL.class);
			final String invoiceProductName = productBL.getProductValueAndName(invoiceLineProductId);
			final String inoutProductName = productBL.getProductValueAndName(inoutLineProductId);
			throw new AdempiereException("@Invalid@ @M_Product_ID@"
					+ "\n @C_InvoiceLine_ID@: " + invoiceLine + ", @M_Product_ID@=" + invoiceProductName
					+ "\n @M_InOutLine_ID@: " + inoutLine + ", @M_Product_ID@=" + inoutProductName);
		}

		// Create the new M_MatchInv record
		final I_M_MatchInv matchInv = InterfaceWrapperHelper.newInstance(I_M_MatchInv.class);
		matchInv.setAD_Org_ID(invoiceLine.getAD_Org_ID());
		matchInv.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
		matchInv.setC_InvoiceLine(invoiceLine);
		matchInv.setM_InOut_ID(inoutLine.getM_InOut_ID());
		matchInv.setM_InOutLine(inoutLine);
		matchInv.setIsSOTrx(inout.isSOTrx());
		matchInv.setDocumentNo(inout.getDocumentNo());

		// Quantity
		matchInv.setQty(qtyToMatch.getStockQty().toBigDecimal());
		matchInv.setQtyInUOM(qtyToMatch.getUOMQtyNotNull().toBigDecimal());
		matchInv.setC_UOM_ID(qtyToMatch.getUOMQtyNotNull().getUomId().getRepoId());

		// Product & ASI
		matchInv.setM_Product_ID(inoutLineProductId.getRepoId());
		matchInv.setM_AttributeSetInstance_ID(inoutLine.getM_AttributeSetInstance_ID());

		//
		// Set DateTrx if specified.
		// If not, it will be automatically set on save.
		final Date dateTrx = getDateTrx();
		if (dateTrx != null)
		{
			matchInv.setDateTrx(TimeUtil.asTimestamp(dateTrx));
		}

		// NOTE: DateAcct will be automatically set on save.

		matchInv.setProcessed(true);
		InterfaceWrapperHelper.save(matchInv);

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

	private InvoiceLineId getInvoiceLineId()
	{
		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		return InvoiceLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());
	}

	private I_C_Invoice getInvoice()
	{
		return getInvoiceLine().getC_Invoice();
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
		final I_M_InOutLine inoutLine = getInOutLine();
		return inoutLine.getM_InOut();
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
			// NOTE: we are not throwing exception because this will push caller code to handle this exception, or to do the checkings before,
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
		if (qtyInvoicedNotMatchedSignum > 0)
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
		final I_C_InvoiceLine invoiceLine = getInvoiceLine();

		final ProductId productId = ProductId.ofRepoId(invoiceLine.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(invoiceLine.getC_UOM_ID());

		StockQtyAndUOMQty qtyInvoiced = StockQtyAndUOMQtys.create(
				invoiceLine.getQtyInvoiced(), productId,
				invoiceLine.getQtyEntered(), uomId);

		// Negate the qtyInvoiced if this is an CreditMemo
		if (isCreditMemoInvoice())
		{
			qtyInvoiced = qtyInvoiced.negate();
		}

		final StockQtyAndUOMQty qtyMatched;
		if (isConsiderQtysAlreadyMatched())
		{
			qtyMatched = matchInvoiceService.getQtyMatched(invoiceLine);
		}
		else
		{
			qtyMatched = StockQtyAndUOMQtys.createZero(productId, uomId);
		}

		return StockQtyAndUOMQtys.subtract(qtyInvoiced, qtyMatched);
	}

	private StockQtyAndUOMQty getQtyMovedNotMatchedInStockUOM()
	{
		final I_M_InOutLine inoutLine = getInOutLine();

		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(inoutLine.getC_UOM_ID());

		StockQtyAndUOMQty qtyReceived = StockQtyAndUOMQtys.create(
				inoutLine.getMovementQty(), productId,
				inoutLine.getQtyEntered(), uomId);

		// Negate the qtyReceived if this is a material return,
		// because we want to have the qtyReceived as an absolute value.
		if (isMaterialReturns())
		{
			qtyReceived = qtyReceived.negate();
		}

		final StockQtyAndUOMQty qtyMatched;
		if (isConsiderQtysAlreadyMatched())
		{
			qtyMatched = matchInvoiceService.getQtyMatched(
					inoutLine,
					qtyReceived.toZero()/* initialQtys */);
		}
		else
		{
			qtyMatched = StockQtyAndUOMQtys.createZero(productId, uomId);
		}

		return StockQtyAndUOMQtys.subtract(qtyReceived, qtyMatched);
	}

	/**
	 * Sets DateTrx to be used (optional).
	 */
	public MatchInvBuilder dateTrx(final Timestamp dateTrx)
	{
		assertNotBuilt();
		this._dateTrx = dateTrx;
		return this;
	}

	@Nullable
	private Timestamp getDateTrx() {return _dateTrx;}

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
		return _skipIfMatchingsAlreadyExist && matchInvoiceService.hasMatchInvs(getInvoiceLineId(), getInOutLineId());
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
}
