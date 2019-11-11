package de.metas.handlingunits.receiptschedule.impl;

import de.metas.handlingunits.document.IHUAllocations;
import de.metas.handlingunits.document.impl.AbstractHUDocumentLine;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.receiptschedule.IHUReceiptScheduleBL;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.product.IProductBL;
import de.metas.util.Services;

public class ReceiptScheduleHUDocumentLine extends AbstractHUDocumentLine
{
	//
	// Services
	private final IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private String displayName;
	private final ReceiptScheduleHUAllocations huAllocations;

	public ReceiptScheduleHUDocumentLine(final I_M_ReceiptSchedule schedule)
	{
		super(
				Services.get(IHUReceiptScheduleBL.class).createProductStorage(schedule) // storage
				, schedule // reference model
		);

		huAllocations = new ReceiptScheduleHUAllocations(schedule, getStorage());
	}

	@Override
	public I_M_ReceiptSchedule getTrxReferencedModel()
	{
		return (I_M_ReceiptSchedule)super.getTrxReferencedModel();
	}

	private final I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return getTrxReferencedModel();
	}

	@Override
	public String getDisplayName()
	{
		if (displayName == null)
		{
			final String productName = Services.get(IProductBL.class).getProductName(getProductId());
			displayName = new StringBuilder()
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
		return huAllocations;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		final I_M_ReceiptSchedule rs = getM_ReceiptSchedule();
		return receiptScheduleBL.getC_BPartner_Location_Effective_ID(rs);
	}

	@Override
	public String toString()
	{
		return "ReceiptScheduleHUDocumentLine [displayName=" + displayName + ", huAllocations=" + huAllocations + "]";
	}


}
