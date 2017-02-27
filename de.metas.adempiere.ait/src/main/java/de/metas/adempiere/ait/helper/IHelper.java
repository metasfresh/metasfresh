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

public interface IHelper
{
	public static final String DEFAULT_BPGroupValue = "TestBPGroup111";
	public static final String DEFAULT_BPName = "TestBP(*)";
	public static final String DEFAULT_PricingSystemValue = "TestPS(*)";
	public static final String DEFAULT_ProductValue = "TestProduct(*)";
	public static final String DEFAULT_DiscountSchemaValue = "TestDS111";

	/**
	 * Default Product Category.
	 * 
	 * ts: 1000001 is the value for "Rabattfaehige Produkte" Product need to have this category,otherwise they will not
	 * be processed by the  commission system
	 */
	public static final String DEFAULT_ProductCategoryValue = "1000001";

	/**
	 * Default Bank Account No.
	 * 
	 * To make sure tests are running ok, we will generate on BankAccount on each run. The main reason are bank
	 * statements where if we have previous bank statements for a bank account, it can influence the tests.
	 */
	public static final String DEFAULT_BankAccount = "Tst(*)"; // AccountNo has max length=20
	public static final String DEFAULT_Bank = "TestBank000";
	public static final String DEFAULT_CashbookBank = "TestCashbook000";
	public static final String DEFAULT_RoutingNo = "TestRN";

	public void init();
	
	public TestConfig getConfig();

	public String createAndSetTrxName(String trxNamePrefix);

	public void setTrxName(String trxName);

	public void commitTrx(boolean clearAfterCommit);

	public void rollbackTrx();

	public void finish() throws Exception;

	public Properties getCtx();

	public String getTrxName();

	public Trx getTrx();

	public TestClientUI getClientUI();

	public Timestamp getNow();

	public Timestamp getToday();

	/**
	 * Adds the given text to the given model's chat info. Useful to identify AIT related POs from the client.
	 * @param model
	 * @param info
	 */
	public void addChatInfoToPO(Object model, String info);
	
	/**
	 * Parses the given string and replaces certain placeholders:
	 * <ul>
	 * <li>(*) => timestamp (millis)</li>
	 * <ul>(n) => an integer number, automatically incremented, starting with 1000 at the beginning of a test run
	 * </ul>
	 * @param name
	 * @return
	 */
	public String parseItemName(String name);

	/**
	 * Create Document Process for given document.
	 * 
	 * Please note that after processing, the document is refreshed so the PO will contain the current state.
	 * 
	 * @param doc
	 *            can be any wrapped PO or GridTab that is a document
	 * @param docAction
	 *            doc action that will be triggered
	 * @param expectedDocStatus
	 *            if not null, the resulting DocStatus shall we equal with this one
	 * @return document process. Please execute {@link ProcessHelper#run()} to actually run the process
	 */
	public ProcessHelper mkDocProcess(Object model, String docAction, String expectedDocStatus);

	/**
	 * Process given document.
	 * 
	 * @see #mkDocProcess(Object, String, String)
	 */
	public void process(Object doc, String docAction, String expectedDocStatus);

	public void processComplete(DocAction doc);
	
	/**
	 * Get/Create a Business Partner
	 * 
	 * @param bpValue
	 * @return
	 * @deprecated use {@link #mkBPartnerHelper()}.getC_BPartner(TestConfig) instead
	 */
	@Deprecated
	public I_C_BPartner getC_BPartner(String bpValue);
	
	public I_M_Product getM_Product();

	public int getC_BP_BankAccount_ID(boolean isCashBank);

	public I_C_BP_BankAccount getC_BP_BankAccount(boolean isCashBank);

	/**
	 * Get/Create a banks account
	 * 
	 * @param bankAccount
	 * @return
	 */
	public int getC_BP_BankAccount_ID(String bankAccountNo, String bankName, String routingNo, boolean isCashBank);

	public I_C_BP_BankAccount getC_BP_BankAccount(String bankAccountNo, String bankName, String routingNo, boolean isCashBank);

	/**
	 * Get/Create a product
	 * 
	 * @param productKey
	 * @return
	 */
	public I_M_Product getM_Product(String productKey);

	public I_M_Product getM_Product(String productKey, String productCategoryValue);

	public I_M_Product_Category getM_Product_Category();

	public I_M_Product_Category getM_Product_Category(String value);

	public I_M_Product getM_Product_FreightCost(String productValue);

	public void setAutomaticPeriodControl();

	public I_M_PricingSystem getM_PricingSystem(String value);

	public int getC_Currency_ID(String ISOcode);

	public org.compiere.model.I_M_PriceList getM_PriceList(String pricingSystemValue, String currencyCode, String countryCode);

	public org.compiere.model.I_M_PriceList getM_PriceList(String pricingSystemValue, String currencyCode, String countryCode, boolean IsSO);

	@Deprecated
	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode,
			String productValue,
			BigDecimal price);

	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode,
			String productValue,
			BigDecimal price, boolean isSO);

	/**
	 * 
	 * @param pricingSystemValue
	 * @param currencyCode
	 * @param countryCode
	 * @param productValue
	 * @param price used for PriceStd, PriceList and PriceLimit
	 * @param C_TaxCategory_ID if <= 0, then the config's C_TaxCategory_Normal_ID will be used. If you don't have an ID, see {@link IHelper#getC_TaxCategory(String)}.
	 * @param IsSO
	 * @return
	 */
	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode, String productValue, BigDecimal price, int C_TaxCategory_ID, boolean IsSO);

	/**
	 * 
	 * @param pl
	 * @param productValue
	 * @param price
	 * @param C_TaxCategory_ID if <= 0, then the config's C_TaxCategory_Normal_ID will be used. If you don't have an ID, see {@link IHelper#getC_TaxCategory(String)}.
	 * @return
	 */
	I_M_ProductPrice setProductPrice(I_M_PriceList pl, String productValue, BigDecimal price, int C_TaxCategory_ID);
	
	
	public I_M_ProductPrice setProductPrice(org.compiere.model.I_M_PriceList pl, String productValue, BigDecimal price);

	public MWarehouse getM_Warehouse();

	public OrderHelper mkOrderHelper();

	public InvoiceHelper mkInvoiceHelper();

	public de.metas.adempiere.model.I_C_Invoice createInvoice(I_C_Order order);

	public I_M_InOut createInOut(I_C_Order order);

	public void createT_Selection(int AD_PInstance_ID, int... ids);

	public void runProcess_InvoiceGenerate(int... orderIds);

	public void runProcess_InvoiceGenerate(String trxName, int... orderIds);

	public void runProcess_InvoiceGenerateFromCands(I_C_Invoice_Candidate... cands);
	
	public void runProcess_InOutGenerate(int... orderIds);

	public List<I_C_Invoice> retrieveInvoicesForOrders(int[] orderIds, String trxName);

	public List<I_M_InOut> retrieveInOutsForOrders(int[] orderIds, String trxName);

	public List<I_M_ShipmentSchedule> retrieveShipmentSchedulesForOrders(int... orderIds);

	public void runProcess_UpdateShipmentScheds();
	
	public void addInventory(String productValue, int qty);

	public List<I_C_InvoiceLine> retrieveLines(I_C_Invoice invoice);

	/**
	 * 
	 * @param order
	 * @return
	 * @deprecated use IOrderPA.retriveLines() instead
	 */
	@Deprecated
	public List<I_C_OrderLine> retrieveLines(I_C_Order order);

	public void assertInvoiced(I_C_Order order);

	public PaymentHelper mkPaymentHelper();

	public GridWindowHelper mkGridWindowHelper();

	public ProcessHelper mkProcessHelper();

	public <T> T createPO(Class<T> cl);

	public void createOrder_checkOrderLine(I_C_OrderLine orderLine, BigDecimal priceActualBD, I_M_ProductPrice pp);

	I_M_DiscountSchema getM_DiscountSchema(String name);

	String getCurrencyCode();

	String getCountryCode();

	public <T> T wrap(Object model, Class<T> clazz);

	public void save(Object model);

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm();

	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm(String paymentTermValue);

	public I_C_Charge getC_Charge();

	/**
	 *  Retrieves/Creates the C_Charge with the given name
	 * @param chargeName
	 * @param taxCategoryName may be <code>null</code>. Only relevant when a charge is created. The tax category (created or retrieved) to use when creating a new C_Charge
	 * @return
	 */
	public I_C_Charge getC_Charge(String chargeName, String taxCategoryName);
	
	public I_M_PriceList_Version getM_PriceList_Version(String pricingSystemValue, String currencyCode, String countryCode);

	public AcctFactAssert doAccountingAssertions();
	
	// public BOMBuilder mkBOMBuilder(); // not yet ported from 

	public I_C_TaxCategory getC_TaxCategory(String Name);

	public I_C_Tax getC_Tax(String Name, int C_TaxCategory_ID, Timestamp date, BigDecimal rate);

	String getGeneratedBy();

	BPartnerHelper mkBPartnerHelper();

}