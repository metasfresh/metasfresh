package de.metas.adempiere.modelvalidator;

import de.metas.adempiere.exception.OrderInvoicePricesNotMatchException;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoice.service.IInvoiceLineBL;
import de.metas.invoice.service.impl.InvoiceLineBL;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.MClient;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.slf4j.Logger;

public class InvoiceLine implements ModelValidator
{

	private static final Logger logger = LogManager.getLogger(InvoiceLine.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{

		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_C_InvoiceLine.Table_Name, this);

		// register this service for callouts and model validators
		Services.registerService(IInvoiceLineBL.class, new InvoiceLineBL());
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{

		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, int type)
	{
		assert po instanceof MInvoiceLine : po;

		if (type == TYPE_BEFORE_CHANGE || type == TYPE_BEFORE_NEW)
		{
			final IInvoiceLineBL invoiceLineBL = Services.get(IInvoiceLineBL.class);
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class);

			if (!il.isProcessed())
			{

				logger.debug("Reevaluating tax for " + il);
				invoiceLineBL.setTaxBasedOnShipment(il, po.get_TrxName());

				logger.debug("Setting TaxAmtInfo for " + il);
				invoiceLineBL.setTaxAmtInfo(po.getCtx(), il, po.get_TrxName());
			}

			// Introduced by US1184, because having the same price on Order and Invoice is enforced by German Law
			if (invoiceLineBL.isPriceLocked(il))
			{
				assertOrderInvoicePricesMatch(il);
			}
		}
		else if (type == TYPE_BEFORE_DELETE)
		{
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class);
			beforeDelete(il);
		}
		return null;
	}

	void beforeDelete(final org.compiere.model.I_C_InvoiceLine invoiceLine)
	{
		final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

		final InvoiceAndLineId invoiceAndLineId = InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());

		for (final I_C_InvoiceLine refInvoiceLine : invoiceDAO.retrieveReferringLines(invoiceAndLineId))
		{
			refInvoiceLine.setRef_InvoiceLine_ID(0);
			invoiceDAO.save(refInvoiceLine);
		}
	}

	public static void assertOrderInvoicePricesMatch(final I_C_InvoiceLine invoiceLine)
	{
		final I_C_OrderLine oline = invoiceLine.getC_OrderLine();
		if (invoiceLine.getPriceActual().compareTo(oline.getPriceActual()) != 0)
		{
			throw new OrderInvoicePricesNotMatchException(I_C_InvoiceLine.COLUMNNAME_PriceActual, oline.getPriceActual(), invoiceLine.getPriceActual());
		}
		if (invoiceLine.getPriceList().compareTo(oline.getPriceList()) != 0)
		{
			throw new OrderInvoicePricesNotMatchException(I_C_InvoiceLine.COLUMNNAME_PriceList, oline.getPriceList(), invoiceLine.getPriceList());
		}
	}
}
