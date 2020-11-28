package de.metas.handlingunits.inout.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_Transaction;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.impl.AbstractHUDocumentLine;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.storage.impl.MTransactionProductStorage;
import de.metas.materialtransaction.IMTransactionDAO;
import de.metas.product.IProductBL;
import de.metas.util.Check;
import de.metas.util.Services;

/* package */class MInOutLineHUDocumentLine extends AbstractHUDocumentLine
{

	private final I_M_InOutLine ioLine;
	private String displayName;

	public MInOutLineHUDocumentLine(final org.compiere.model.I_M_InOutLine ioLine, final I_M_Transaction mtrx)
	{
		super(
				new MTransactionProductStorage(mtrx, loadOutOfTrx(ioLine.getC_UOM_ID(), I_C_UOM.class)), // storage
				mtrx // referencedModel
		);

		Check.assumeNotNull(ioLine, "ioLine not null");
		Check.assume(ioLine.getM_InOutLine_ID() > 0, "ioLine({}) is saved", ioLine);
		this.ioLine = InterfaceWrapperHelper.create(ioLine, I_M_InOutLine.class);
	}

	@Override
	public String getDisplayName()
	{
		if (displayName == null)
		{
			final String productName = Services.get(IProductBL.class).getProductName(getProductId());
			displayName = new StringBuilder()
					.append(ioLine.getLine()).append(": ")
					.append(productName)
					.append(" x ")
					.append(getQty())
					.append(getC_UOM().getUOMSymbol())
					.toString();

		}
		return displayName;
	}

	@Override
	public IHUAllocations getHUAllocations()
	{
		return null;
	}

	@Override
	public I_M_Transaction getTrxReferencedModel()
	{
		return (I_M_Transaction)super.getTrxReferencedModel();
	}

	private MInOutLineHUDocumentLine reversalSourceLine = null;

	@Override
	public MInOutLineHUDocumentLine getReversal()
	{
		if (reversalSourceLine == null)
		{
			final org.compiere.model.I_M_InOutLine reversalLine = ioLine.getReversalLine();
			final I_M_Transaction mtrx = getTrxReferencedModel();
			final I_M_Transaction reversalMTrx = Services.get(IMTransactionDAO.class).retrieveReversalTransaction(reversalLine, mtrx);

			reversalSourceLine = new MInOutLineHUDocumentLine(reversalLine, reversalMTrx);
			reversalSourceLine.setReversal(this);
		}
		return reversalSourceLine;
	}

	private void setReversal(final MInOutLineHUDocumentLine reversalLine)
	{
		reversalSourceLine = reversalLine;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		final I_M_InOut inout = ioLine.getM_InOut();
		return inout.getC_BPartner_Location_ID();
	}
}
