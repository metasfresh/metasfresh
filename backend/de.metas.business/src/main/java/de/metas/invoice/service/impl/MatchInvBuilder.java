package de.metas.invoice.service.impl;

import de.metas.inout.IInOutBL;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IMatchInvBuilder;
import de.metas.invoice.service.IMatchInvDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Helper class used to create a quantity matching between {@link I_C_InvoiceLine} and {@link I_M_InOutLine} (i.e. {@link I_M_MatchInv}).
 *
 * @author tsa
 */
/* package */class MatchInvBuilder implements IMatchInvBuilder
{
	// services
	// private static final transient Logger logger = CLogMgt.getLogger(MatchInvBuilder.class);
	private final transient IMatchInvDAO matchInvDAO = Services.get(IMatchInvDAO.class);
	private final transient IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);

	// Parameters
	private Object _contextProvider;
	private I_C_InvoiceLine _invoiceLine;
	private I_M_InOutLine _inoutLine;
	private Date _dateTrx;
	private StockQtyAndUOMQty _qtyToMatchExact;
	private boolean _considerQtysAlreadyMatched = true;
	private boolean _allowQtysOfOppositeSigns = false;
	private boolean _skipIfMatchingsAlreadyExist = false;

	// Status
	private boolean _built = false;

	@Override
	public boolean build()
	{
		markBuilt();

		if (isSkipBecauseMatchingsAlreadyExist())
		{
			return noMatchInvNeeded();
		}

		final StockQtyAndUOMQty qtyToMatch = calculateQtyToMatch();
		if (qtyToMatch.signum() == 0)
		{
			return noMatchInvNeeded();
		}

		final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
		final I_C_Invoice invoice = invoiceLine.getC_Invoice();
		final I_M_InOutLine inoutLine = getM_InOutLine();
		final I_M_InOut inout = inoutLine.getM_InOut();

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
		final I_M_MatchInv matchInv = InterfaceWrapperHelper.newInstance(I_M_MatchInv.class, getContextProvider());
		matchInv.setAD_Org_ID(invoiceLine.getAD_Org_ID());
		matchInv.setC_Invoice_ID(invoiceLine.getC_Invoice_ID());
		matchInv.setC_InvoiceLine(invoiceLine);
		matchInv.setM_InOut_ID(inout.getM_InOut_ID());
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

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built: {}", this);
	}

	private final void markBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * Convenient method to returned from {@link #build()} when NO {@link I_M_MatchInv} record is needed
	 *
	 * @return false
	 */
	private final boolean noMatchInvNeeded()
	{
		return false;
	}

	@Override
	public IMatchInvBuilder setContext(final Object contextProvider)
	{
		assertNotBuilt();
		this._contextProvider = contextProvider;
		return this;
	}

	private final Object getContextProvider()
	{
		Check.assumeNotNull(_contextProvider, "_contextProvider not null");
		return _contextProvider;
	}

	private final String getTrxName()
	{
		return InterfaceWrapperHelper.getTrxName(getContextProvider());
	}

	@Override
	public IMatchInvBuilder setC_InvoiceLine(final I_C_InvoiceLine invoiceLine)
	{
		assertNotBuilt();
		this._invoiceLine = invoiceLine;
		return this;
	}

	private final I_C_InvoiceLine getC_InvoiceLine()
	{
		Check.assumeNotNull(_invoiceLine, "_invoiceLine not null");
		return _invoiceLine;
	}

	@Override
	public IMatchInvBuilder setM_InOutLine(final I_M_InOutLine inoutLine)
	{
		assertNotBuilt();
		this._inoutLine = inoutLine;
		return this;
	}

	private final I_M_InOutLine getM_InOutLine()
	{
		Check.assumeNotNull(_inoutLine, "_inoutLine not null");
		return _inoutLine;
	}

	@Override
	public IMatchInvBuilder setQtyToMatchExact(final StockQtyAndUOMQty qtyToMatchExact)
	{
		assertNotBuilt();
		this._qtyToMatchExact = qtyToMatchExact;
		return this;
	}

	private final StockQtyAndUOMQty getQtyToMatchExact()
	{
		return _qtyToMatchExact;
	}

	@Override
	public IMatchInvBuilder setConsiderQtysAlreadyMatched(final boolean considerQtysAlreadyMatched)
	{
		assertNotBuilt();
		this._considerQtysAlreadyMatched = considerQtysAlreadyMatched;
		return this;
	}

	private final boolean isConsiderQtysAlreadyMatched()
	{
		return _considerQtysAlreadyMatched;
	}

	@Override
	public IMatchInvBuilder setAllowQtysOfOppositeSigns(final boolean allowQtysOfOppositeSigns)
	{
		assertNotBuilt();
		this._allowQtysOfOppositeSigns = allowQtysOfOppositeSigns;
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
		// In this case we are not doing further checkings.
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
		final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();

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
			qtyMatched = matchInvDAO.retrieveQtyMatched(invoiceLine);
		}
		else
		{
			qtyMatched = StockQtyAndUOMQtys.createZero(productId, uomId);
		}

		final StockQtyAndUOMQty qtyNotMatched = StockQtyAndUOMQtys.subtract(qtyInvoiced, qtyMatched);
		return qtyNotMatched;
	}

	private StockQtyAndUOMQty getQtyMovedNotMatchedInStockUOM()
	{
		final I_M_InOutLine inoutLine = getM_InOutLine();

		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());
		final UomId uomId = UomId.ofRepoId(inoutLine.getC_UOM_ID());

		StockQtyAndUOMQty qtyReceived = StockQtyAndUOMQtys.create(
				inoutLine.getMovementQty(), productId,
				inoutLine.getQtyEntered(), uomId);

		// Negate the qtyReceived if this is an material return,
		// because we want to have the qtyReceived as an absolute value.
		if (isMaterialReturns())
		{
			qtyReceived = qtyReceived.negate();
		}

		final StockQtyAndUOMQty qtyMatched;
		if (isConsiderQtysAlreadyMatched())
		{
			qtyMatched = matchInvDAO.retrieveQtysInvoiced(
					inoutLine,
					qtyReceived.toZero()/* initialQtys */);
		}
		else
		{
			qtyMatched = StockQtyAndUOMQtys.createZero(productId, uomId);
		}

		final StockQtyAndUOMQty qtyNotMatched = StockQtyAndUOMQtys.subtract(qtyReceived, qtyMatched);
		return qtyNotMatched;
	}

	@Override
	public IMatchInvBuilder setDateTrx(final Date dateTrx)
	{
		assertNotBuilt();
		this._dateTrx = dateTrx;
		return this;
	}

	private final Timestamp getDateTrx()
	{
		return TimeUtil.asTimestamp(_dateTrx);
	}

	@Override
	public IMatchInvBuilder setSkipIfMatchingsAlreadyExist(final boolean skipIfMatchingsAlreadyExist)
	{
		assertNotBuilt();
		this._skipIfMatchingsAlreadyExist = skipIfMatchingsAlreadyExist;
		return this;
	}

	/**
	 * @return true if {@link #setSkipIfMatchingsAlreadyExist(boolean)} was enabled and we a {@link I_M_MatchInv} already exists.
	 */
	private boolean isSkipBecauseMatchingsAlreadyExist()
	{
		if (!_skipIfMatchingsAlreadyExist)
		{
			return false;
		}

		return matchInvDAO.hasMatchInvs(getC_InvoiceLine(), getM_InOutLine(), getTrxName());
	}

	private Boolean _creditMemoInvoice = null;

	/**
	 * @return true if underlying invoice line is part of a credit memo invoice
	 */
	private boolean isCreditMemoInvoice()
	{
		if (_creditMemoInvoice == null)
		{
			final I_C_InvoiceLine invoiceLine = getC_InvoiceLine();
			final I_C_Invoice invoice = invoiceLine.getC_Invoice();
			_creditMemoInvoice = invoiceBL.isCreditMemo(invoice);
		}
		return _creditMemoInvoice;
	}

	private Boolean _materialReturns = null;

	/**
	 * @return true if underlying inout line is part of a material returns (customer or vendor).
	 */
	private boolean isMaterialReturns()
	{
		if (_materialReturns == null)
		{
			final I_M_InOutLine inoutLine = getM_InOutLine();
			final I_M_InOut inout = inoutLine.getM_InOut();
			final String movementType = inout.getMovementType();
			_materialReturns = inOutBL.isReturnMovementType(movementType);
		}
		return _materialReturns;
	}
}
