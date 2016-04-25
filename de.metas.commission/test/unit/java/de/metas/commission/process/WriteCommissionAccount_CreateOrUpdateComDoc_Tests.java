package de.metas.commission.process;

import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Verifications;

import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationLine;
import org.junit.Test;

import de.metas.commission.interfaces.I_C_Invoice;
import de.metas.commission.interfaces.I_C_Order;
import de.metas.commission.model.MCAdvComDoc;
import de.metas.prepayorder.service.IPrepayOrderBL;

/**
 * Tests for {@link WriteCommissionAccount#createOrUpdateComDocIfRequired(I_C_AllocationLine)}.
 * 
 * @author ts
 * 
 */
public class WriteCommissionAccount_CreateOrUpdateComDoc_Tests
{

	@Mocked
	I_C_AllocationLine allocLine;

	@Mocked
	MCAdvComDoc unusedComDoc;

	@Mocked
	I_C_Invoice invoice;

	@Mocked
	I_C_Order order;

	/**
	 * If the allocLine line references an invoice, but no order, and the invoice is not a trigger, the method should
	 */
	@Test
	public void noOrder_InvoiceNoTrigger()
	{
		allocLineHasInvoice();

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isTriggerInvoice(invoice); result = false;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(invoice);
		}};
	}

	@Test
	public void noOrder_InvoiceTrigger()
	{
		allocLineHasInvoice();

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isTriggerInvoice(invoice); result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.createOrUpdate(invoice, allocLine);
		}};
	}

	@Test
	public void orderNotPaid_InvoiceTrigger()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(false);

		allocLineHasInvoice();

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order); result = false;
				WriteCommissionAccount.isTriggerInvoice(invoice); result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(order);
				MCAdvComDoc.createOrUpdate(invoice, allocLine);
		}};	
	}

	@Test
	public void orderPaid_InvoiceTrigger()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(false);

		allocLineHasInvoice();

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order); result = true;
				WriteCommissionAccount.isTriggerInvoice(invoice); result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(order);
				MCAdvComDoc.createOrUpdate(invoice, allocLine);
		}};
	}

	/**
	 * If asked, 'invoice' will seem to be a trigger, but if a comdoc is created or updated for 'invoice' it would be a
	 * bug, because 'order' is a prepay order.
	 */
	@Test
	public void prepayOrderNotPaid_InvoiceTrigger()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(true);

		allocLineHasInvoice();

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order); result = false;
				WriteCommissionAccount.isTriggerInvoice(invoice); result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(order);
				MCAdvComDoc.deactivateIfExist(invoice);
		}};
	}

	@Test
	public void prepayOrderPaid_InvoiceTrigger()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(true);

		allocLineHasInvoice();

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order);	result = true;
				WriteCommissionAccount.isTriggerInvoice(invoice); result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.createOrUpdate(order, allocLine);
				MCAdvComDoc.deactivateIfExist(invoice);
		}};
	}

	@Test
	public void prepayOrderPaid_NoInvoice()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(true);

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order);	result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.createOrUpdate(order, allocLine);

				MCAdvComDoc.deactivateIfExist(invoice);	times = 0;
				MCAdvComDoc.createOrUpdate(invoice, allocLine);	times = 0;
		}};	
	}

	@Test
	public void orderPaid_NoInvoice()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(false);

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order); result = true;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(order); times = 1;
				MCAdvComDoc.createOrUpdate(order, allocLine); times = 0;
		}};

		invoiceMockIsIgnored();
	}

	@Test
	public void orderNotPaid_NoInvoice()
	{
		allocLineHasOrder();
		orderIsPrepayOrder(false);

		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				WriteCommissionAccount.isPaidOrder(order); result = false;
		}};

		WriteCommissionAccount.createOrUpdateComDocIfRequired(allocLine);

		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(order); times = 1;
				MCAdvComDoc.createOrUpdate(order, allocLine); times = 0;
		}};

		invoiceMockIsIgnored();
	}

	private void orderIsPrepayOrder(final boolean isPrepayOrder)
	{
		new NonStrictExpectations(Services.class)
		{
			IPrepayOrderBL prepayOrderBL;
			{
				Services.get(IPrepayOrderBL.class);	result = prepayOrderBL;
				prepayOrderBL.isPrepayOrder(order);	result = isPrepayOrder;
			}
		};
	}

	private void allocLineHasOrder()
	{
		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				allocLine.getC_Order_ID(); result = 1;
				allocLine.getC_Order();	result = order;
		}};
	}

	private void allocLineHasInvoice()
	{
		new NonStrictExpectations(WriteCommissionAccount.class)
		{{
				allocLine.getC_Invoice_ID(); result = 1;
				allocLine.getC_Invoice(); result = invoice;
		}};
	}

	/**
	 * Asserts that neither {@link MCAdvComDoc#deactivateIfExist(Object)} nor
	 * {@link MCAdvComDoc#createOrUpdate(Object, I_C_AllocationLine)} is called for our invoice mock
	 */
	private void invoiceMockIsIgnored()
	{
		new Verifications()
		{{
				MCAdvComDoc.deactivateIfExist(invoice);	times = 0;
				MCAdvComDoc.createOrUpdate(invoice, allocLine);	times = 0;
		}};
	}
}
