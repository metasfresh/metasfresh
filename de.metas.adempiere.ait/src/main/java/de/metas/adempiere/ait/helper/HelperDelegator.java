package de.metas.adempiere.ait.helper;

/*
 * #%L
 * de.metas.adempiere.ait
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
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.test.TestClientUI;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MWarehouse;
import org.compiere.process.DocAction;
import org.compiere.util.Trx;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.model.I_M_Product_Category;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.interfaces.I_C_BP_BankAccount;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class HelperDelegator implements IHelper
{
	private final IHelper helper;
	
	protected HelperDelegator(IHelper helper)
	{
		this.helper = helper;
	}

	@Override
	public void init()
	{
		helper.init();
	}

	@Override
	public String createAndSetTrxName(String trxNamePrefix)
	{
		return helper.createAndSetTrxName(trxNamePrefix);
	}

	@Override
	public void setTrxName(String trxName)
	{
		helper.setTrxName(trxName);
	}

	@Override
	public void commitTrx(boolean clearAfterCommit)
	{
		helper.commitTrx(clearAfterCommit);
	}

	@Override
	public void rollbackTrx()
	{
		helper.rollbackTrx();
	}

	@Override
	public void finish() throws Exception
	{
		helper.finish();
	}

	@Override
	public Properties getCtx()
	{
		return helper.getCtx();
	}

	@Override
	public String getTrxName()
	{
		return helper.getTrxName();
	}

	@Override
	public Trx getTrx()
	{
		return helper.getTrx();
	}

	@Override
	public TestClientUI getClientUI()
	{
		return helper.getClientUI();
	}

	@Override
	public Timestamp getNow()
	{
		return helper.getNow();
	}

	@Override
	public Timestamp getToday()
	{
		return helper.getToday();
	}

	@Override
	public String parseItemName(String name)
	{
		return helper.parseItemName(name);
	}

	@Override
	public void process(Object doc, String docAction, String expectedDocStatus)
	{
		helper.process(doc, docAction, expectedDocStatus);
	}

	@Override
	public void processComplete(DocAction doc)
	{
		helper.processComplete(doc);
	}

	/**
	 * Get/Create a Business Partner
	 * 
	 * @param bpValue
	 * @return
	 * @deprecated use {@link #mkBPartnerHelper()}.getC_BPartner(TestConfig) instead
	 */
	@Override
	@Deprecated
	public I_C_BPartner getC_BPartner(String bpValue)
	{
		return helper.getC_BPartner(bpValue);
	}

	@Override
	public I_M_Product getM_Product()
	{
		return helper.getM_Product();
	}

	@Override
	public int getC_BP_BankAccount_ID(boolean isCashBank)
	{
		return helper.getC_BP_BankAccount_ID(isCashBank);
	}

	@Override
	public I_C_BP_BankAccount getC_BP_BankAccount(boolean isCashBank)
	{
		return helper.getC_BP_BankAccount(isCashBank);
	}

	@Override
	public int getC_BP_BankAccount_ID(String bankAccountNo, String bankName, String routingNo, boolean isCashBank)
	{
		return helper.getC_BP_BankAccount_ID(bankAccountNo, bankName, routingNo, isCashBank);
	}

	@Override
	public I_C_BP_BankAccount getC_BP_BankAccount(String bankAccountNo, String bankName, String routingNo, boolean isCashBank)
	{
		return helper.getC_BP_BankAccount(bankAccountNo, bankName, routingNo, isCashBank);
	}

	@Override
	public I_M_Product getM_Product(String productKey)
	{
		return helper.getM_Product(productKey);
	}

	@Override
	public I_M_Product getM_Product(String productKey, String productCategoryValue)
	{
		return helper.getM_Product(productKey, productCategoryValue);
	}

	@Override
	public I_M_Product_Category getM_Product_Category()
	{
		return helper.getM_Product_Category();
	}

	@Override
	public I_M_Product_Category getM_Product_Category(String value)
	{
		return helper.getM_Product_Category(value);
	}

	@Override
	public I_M_Product getM_Product_FreightCost(String productValue)
	{
		return helper.getM_Product_FreightCost(productValue);
	}

	@Override
	public void setAutomaticPeriodControl()
	{
		helper.setAutomaticPeriodControl();
	}

	@Override
	public I_M_PricingSystem getM_PricingSystem(String value)
	{
		return helper.getM_PricingSystem(value);
	}

	@Override
	public int getC_Currency_ID(String ISOcode)
	{
		return helper.getC_Currency_ID(ISOcode);
	}

	@Override
	public I_M_PriceList getM_PriceList(String pricingSystemValue, String currencyCode, String countryCode)
	{
		return helper.getM_PriceList(pricingSystemValue, currencyCode, countryCode);
	}

	@Override
	public I_M_PriceList getM_PriceList(String pricingSystemValue, String currencyCode, String countryCode, boolean isSO)
	{
		return helper.getM_PriceList(pricingSystemValue, currencyCode, countryCode, isSO);
	}

	@Override
	@Deprecated
	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode, String productValue, BigDecimal price)
	{
		return helper.setProductPrice(pricingSystemValue, currencyCode, countryCode, productValue, price);
	}
	
	@Override
	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode, String productValue, BigDecimal price, boolean isSO)
	{
		return helper.setProductPrice(pricingSystemValue, currencyCode, countryCode, productValue, price, isSO);
	}

	@Override
	public I_M_ProductPrice setProductPrice(org.compiere.model.I_M_PriceList pl, String productValue, BigDecimal price)
	{
		return helper.setProductPrice(pl, productValue, price);
	}

	@Override
	public MWarehouse getM_Warehouse()
	{
		return helper.getM_Warehouse();
	}

	@Override
	public BPartnerHelper mkBPartnerHelper()
	{
		return helper.mkBPartnerHelper();
	}
	
	@Override
	public OrderHelper mkOrderHelper()
	{
		return helper.mkOrderHelper();
	}

	@Override
	public InvoiceHelper mkInvoiceHelper()
	{
		return helper.mkInvoiceHelper();
	}

	@Override
	public I_C_Invoice createInvoice(I_C_Order order)
	{
		return helper.createInvoice(order);
	}

	@Override
	public I_M_InOut createInOut(I_C_Order order)
	{
		return helper.createInOut(order);
	}

	@Override
	public void createT_Selection(int AD_PInstance_ID, int... ids)
	{
		helper.createT_Selection(AD_PInstance_ID, ids);
	}

	@Override
	public void runProcess_InvoiceGenerate(int... orderIds)
	{
		helper.runProcess_InvoiceGenerate(orderIds);
	}
	
	@Override
	public void runProcess_InvoiceGenerate(String trxName, int... orderIds)
	{
		helper.runProcess_InvoiceGenerate(trxName, orderIds);
	}

	@Override
	public void runProcess_InOutGenerate(int... orderIds)
	{
		helper.runProcess_InOutGenerate(orderIds);
	}

	@Override
	public void runProcess_InvoiceGenerateFromCands(final I_C_Invoice_Candidate... cands)
	{
		helper.runProcess_InvoiceGenerateFromCands(cands);
	}
	
	@Override
	public List<I_C_Invoice> retrieveInvoicesForOrders(int[] orderIds, String trxName)
	{
		return helper.retrieveInvoicesForOrders(orderIds, trxName);
	}

	@Override
	public List<I_M_InOut> retrieveInOutsForOrders(int[] orderIds, String trxName)
	{
		return helper.retrieveInOutsForOrders(orderIds, trxName);
	}

	@Override
	public List<I_M_ShipmentSchedule> retrieveShipmentSchedulesForOrders(int... orderIds)
	{
		return helper.retrieveShipmentSchedulesForOrders(orderIds);
	}

	@Override
	public void runProcess_UpdateShipmentScheds()
	{
		helper.runProcess_UpdateShipmentScheds();
	}
	
	@Override
	public void addInventory(String productValue, int qty)
	{
		helper.addInventory(productValue, qty);
	}

	@Override
	public List<I_C_InvoiceLine> retrieveLines(I_C_Invoice invoice)
	{
		return helper.retrieveLines(invoice);
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<I_C_OrderLine> retrieveLines(I_C_Order order)
	{
		return helper.retrieveLines(order);
	}

	@Override
	public void assertInvoiced(I_C_Order order)
	{
		helper.assertInvoiced(order);
	}

	@Override
	public PaymentHelper mkPaymentHelper()
	{
		return helper.mkPaymentHelper();
	}

	@Override
	public GridWindowHelper mkGridWindowHelper()
	{
		return helper.mkGridWindowHelper();
	}

	@Override
	public ProcessHelper mkProcessHelper()
	{
		return helper.mkProcessHelper();
	}

	@Override
	public <T> T createPO(Class<T> cl)
	{
		return helper.createPO(cl);
	}

	@Override
	public void createOrder_checkOrderLine(I_C_OrderLine orderLine, BigDecimal priceActualBD, I_M_ProductPrice pp)
	{
		helper.createOrder_checkOrderLine(orderLine, priceActualBD, pp);
	}

	@Override
	public I_M_DiscountSchema getM_DiscountSchema(String name)
	{
		return helper.getM_DiscountSchema(name);
	}

	@Override
	public <T> T wrap(Object model, Class<T> clazz)
	{
		return helper.wrap(model, clazz);
	}

	@Override
	public void save(Object model)
	{
		helper.save(model);
	}

	@Override
	public I_C_PaymentTerm getC_PaymentTerm()
	{
		return helper.getC_PaymentTerm();
	}

	@Override
	public I_C_PaymentTerm getC_PaymentTerm(String paymentTermValue)
	{
		return helper.getC_PaymentTerm(paymentTermValue);
	}

	@Override
	public I_C_Charge getC_Charge()
	{
		return helper.getC_Charge();
	}

	@Override
	public I_C_Charge getC_Charge(String chargeName, String taxCategoryName)
	{
		return helper.getC_Charge(chargeName, taxCategoryName);
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version(String pricingSystemValue, String currencyCode, String countryCode)
	{
		return helper.getM_PriceList_Version(pricingSystemValue, currencyCode, countryCode);
	}
	
	@Override
	public AcctFactAssert doAccountingAssertions()
	{
		return helper.doAccountingAssertions();
	}

	@Override
	public I_C_TaxCategory getC_TaxCategory(String Name)
	{
		return helper.getC_TaxCategory(Name);
	}

	@Override
	public I_C_Tax getC_Tax(String Name, int C_TaxCategory_ID, Timestamp date, BigDecimal rate)
	{
		return helper.getC_Tax(Name, C_TaxCategory_ID, date, rate);
	}

	@Override
	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode, String productValue, BigDecimal price, int C_TaxCategory_ID, boolean IsSO)
	{
		return helper.setProductPrice(pricingSystemValue, currencyCode, countryCode, productValue, price, C_TaxCategory_ID, IsSO);
	}

	@Override
	public I_M_ProductPrice setProductPrice(org.compiere.model.I_M_PriceList pl, String productValue, BigDecimal price, int C_TaxCategory_ID)
	{
		return helper.setProductPrice(pl, productValue, price, C_TaxCategory_ID);
	}
	
	@Override
	public ProcessHelper mkDocProcess(Object model, String docAction, String expectedDocStatus)
	{
		return helper.mkDocProcess(model, docAction, expectedDocStatus);
	}
	
	@Override
	public TestConfig getConfig()
	{
		return helper.getConfig();
	}

	@Override
	public String getCurrencyCode()
	{
		return helper.getCurrencyCode();
	}

	@Override
	public String getCountryCode()
	{
		return helper.getCountryCode();
	}
		
	@Override
	public String getGeneratedBy()
	{
		return helper.getGeneratedBy();
	}

	@Override
	public void addChatInfoToPO(Object model, String info)
	{
		helper.addChatInfoToPO(model, info);		
	}
}
