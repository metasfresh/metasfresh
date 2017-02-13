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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.acct.api.IAcctSchemaDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.I_M_FreightCost;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.MFreightCost;
import org.adempiere.test.TestClientUI;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.db.CConnection;
import org.compiere.model.GridTab;
import org.compiere.model.I_AD_ClientInfo;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Charge;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_C_Tax;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.MBank;
import org.compiere.model.MChat;
import org.compiere.model.MChatEntry;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MProduct;
import org.compiere.model.MStorage;
import org.compiere.model.MTable;
import org.compiere.model.MWarehouse;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_C_BankAccount;
import org.compiere.model.X_C_Tax;
import org.compiere.model.X_M_DiscountSchema;
import org.compiere.process.DocAction;
import org.compiere.process.InvoiceGenerate;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.compiere.util.TimeUtil;
import org.compiere.util.Trx;
import org.junit.Assert;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.ait.validator.BPOpenBalanceValidator;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.model.I_M_Product_Category;
import de.metas.adempiere.service.ICountryDAO;
import de.metas.currency.ICurrencyBL;
import de.metas.currency.ICurrencyDAO;
import de.metas.freighcost.api.IFreightCostBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.process.UpdateShipmentScheds;
import de.metas.interfaces.I_C_BP_BankAccount;
import de.metas.interfaces.I_C_BPartner;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.C_Invoice_Candidate_GenerateInvoice;
import de.metas.logging.LogManager;
import de.metas.order.IOrderPA;

public class Helper implements IHelper
{
	private static final long serialVersionUID = 1L;

	private boolean initialized = false;
	private Properties ctx;
	private String trxName = null;
	private TestClientUI clientUI;
	private final AcctFactAssert acctFactAsserts = new AcctFactAssert(this);

	private final String defaultPaymentTermValue = "Test_None_" + serialVersionUID;
	private final String defaultChargeValue = "Test_None_" + serialVersionUID;

	private final static String RESOURCE_TestConfig = "/test/integration/TestConfig.properties";

	// Current timestamps.
	// NOTE: this values are also used to build particular items names when their name contain "(*)"
	// NOTE: to better issolate generated items between tests, we shall not declare this values static
	private final static Timestamp NOW = SystemTime.asTimestamp();
	private final static Timestamp TODAY = TimeUtil.trunc(NOW, TimeUtil.TRUNC_DAY);
	private final static String NOW_String = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(NOW);

	protected static final Logger logger = LogManager.getLogger(Helper.class);

	@Override
	public void init()
	{
		if (this.initialized)
			return;

		//
		// Set SERVER_EMBEDDED=true
		// Required because some of classes are configured to run only on server.
		// e.g. de.metas.adempiere.service.impl.GlobalLockSystem
		System.setProperty(CConnection.SERVER_EMBEDDED_PROPERTY, "true");
		logger.info(CConnection.SERVER_EMBEDDED_PROPERTY + "=" + System.getProperty(CConnection.SERVER_EMBEDDED_PROPERTY));

		setupAdempiere();

		// init the set of allowed trxName prefices
		DB.getConstraints()
				.setOnlyAllowedTrxNamePrefixes(true)
				.addAllowedTrxNamePrefix(getTrxName() == null ? "POSave" : getTrxName());

		// Init facts
		acctFactAsserts.init();

		if (Services.isAvailable(IClientUI.class) && Services.get(IClientUI.class) instanceof TestClientUI)
		{
			this.clientUI = (TestClientUI)Services.get(IClientUI.class);
		}
		else
		{
			this.clientUI = new TestClientUI();
			Services.registerService(IClientUI.class, clientUI);
		}

		BPOpenBalanceValidator.get().register();

		this.initialized = true;
	}

	@Override
	public TestConfig getConfig()
	{
		if (_config == null)
			initConfig();
		return _config;
	}

	private static TestConfig _config = null;

	private static int staticCount = 1000;

	private static final void initConfig()
	{
		if (_config != null)
		{
			logger.warn("Already configured [SKIP]");
			return;
		}

		final InputStream in = Helper.class.getResourceAsStream(RESOURCE_TestConfig);
		if (in == null)
		{
			throw new AdempiereException("Cannot find config resource: " + RESOURCE_TestConfig);
		}

		final Properties props = new Properties();
		try
		{
			props.load(in);
		}
		catch (IOException e)
		{
			throw new AdempiereException(e);
		}

		_config = new TestConfig(props);

		logger.info("Config loaded from " + RESOURCE_TestConfig + ": " + _config);
	}

	private void setupAdempiere()
	{
		final TestConfig config = getConfig();

		Services.setAutodetectServices(true);
		// Services.registerService(IFreightCostBL.class, new FreightCostSubscriptionBL()); // TODO: hardcoded
		org.compiere.Adempiere.startupEnvironment(true);
		LogManager.setLevel(config.getLogLevel());
		// Decrease log level for CacheAsp:
		Logger cacheLogger = LogManager.getLogger(de.metas.adempiere.util.cache.CacheInterceptor.class);
		LogManager.setLoggerLevel(cacheLogger, Level.INFO);

		// when we start Adempiere.main(), right after adempiere startup, the class org.compiere.apps.AMenu is fired up and takes the first WindowNo, which is 0
		// we need to emulate that be havior, becaus otherwise our first "real" window will get WindowNo and that WindowNo this will lead to problems in the lookup code
		Env.createWindowNo(null);

		this.ctx = Env.getCtx();
		Env.setContext(ctx, "#AD_Client_ID", config.getAD_Client_ID());
		Env.setContext(ctx, "#AD_Org_ID", config.getAD_Org_ID());
		Env.setContext(ctx, "#AD_User_ID", config.getAD_User_Login_ID());

		Env.setContext(ctx, "#AD_Role_ID", config.getAD_Role_ID());

		// Make sure the test won't produce migration scripts because it's pointless and annoying
		Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#createAndSetTrxName(java.lang.String)
	 */
	@Override
	public String createAndSetTrxName(final String trxNamePrefix)
	{
		final String trxName = Services.get(ITrxManager.class).createTrxName(trxNamePrefix, false);
		setTrxName(trxName);
		return trxName;
	}

	/**
	 * Sets the trxName to be used during tests.
	 * 
	 * Note that the old trxName will be removed from the set of allowed trxName prefices and the new trxName will be added.
	 */
	@Override
	public void setTrxName(final String trxName)
	{
		rollbackTrx();

		DB.getConstraints().removeAllowedTrxNamePrefix(this.trxName == null ? "POSave" : this.trxName);
		this.trxName = trxName;

		DB.getConstraints().addAllowedTrxNamePrefix(trxName == null ? "POSave" : trxName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#commitTrx(boolean)
	 */
	@Override
	public void commitTrx(boolean clearAfterCommit)
	{
		Trx trx = getTrx();
		if (trx != null)
		{
			try
			{
				trx.commit(true);
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
		}

		if (clearAfterCommit)
		{
			trxName = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#rollbackTrx()
	 */
	@Override
	public void rollbackTrx()
	{
		Trx trx = getTrx();
		if (trx != null)
		{
			try
			{
				trx.rollback(true);
			}
			catch (SQLException e)
			{
				throw new RuntimeException(e);
			}
			trx.close();
			trx = null;
			trxName = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#finish()
	 */
	@Override
	public void finish() throws Exception
	{
		rollbackTrx();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getCtx()
	 */
	@Override
	public Properties getCtx()
	{
		return ctx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getTrxName()
	 */
	@Override
	public String getTrxName()
	{
		return trxName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getTrx()
	 */
	@Override
	public Trx getTrx()
	{
		return trxName == null ? null : Trx.get(trxName, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getClientUI()
	 */
	@Override
	public TestClientUI getClientUI()
	{
		return this.clientUI;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getNow()
	 */
	@Override
	public Timestamp getNow()
	{
		return NOW;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getToday()
	 */
	@Override
	public Timestamp getToday()
	{
		return TODAY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#parseItemName(java.lang.String)
	 */
	@Override
	public String parseItemName(String name)
	{
		return parseName(name);
	}

	public static String parseName(String name)
	{
		if (name == null)
			return name;
		return name.replace("(*)", NOW_String).replace("(n)", Integer.toString(staticCount++));
	}

	@Override
	public void process(Object model, String docAction, String expectedDocStatus)
	{
		mkDocProcess(model, docAction, expectedDocStatus)
				.run();
	}

	@Override
	public ProcessHelper mkDocProcess(final Object model, final String docAction, final String expectedDocStatus)
	{
		final ProcessHelper process;
		final PO po = InterfaceWrapperHelper.getStrictPO(model);
		if (po != null)
		{
			Assert.assertTrue("Object " + po + " is not an instanceof " + DocAction.class, po instanceof DocAction);

			final MTable poTable = MTable.get(po.getCtx(), po.get_TableName());
			final MColumn docActionColumn = poTable.getColumn("DocAction");
			Assert.assertNotNull("Object " + po + " does not have DocAction column", docActionColumn);
			final int processId = docActionColumn.getAD_Process_ID();
			Assert.assertTrue("Object " + po + " does not have a process on DocAction column", processId > 0);

			final DocAction doc = (DocAction)po;

			process = mkProcessHelper()
					.setPO(po)
					.setProcessId(processId)
					.addListener(new IProcessHelperListener()
					{

						@Override
						public void beforeRun(ProcessHelper process)
						{
							po.load(po.get_TrxName());

							// if we are not setting this value some documents could end in DocStatus=IP
							// e.g. check MOrder.completeIt method
							po.set_ValueOfColumn("DocAction", docAction);
							po.saveEx();
						}

						@Override
						public void afterRun(ProcessHelper process)
						{
							po.saveEx();
							po.load(po.get_TrxName());

							final String docStatus = doc.getDocStatus();
							if (expectedDocStatus != null)
							{
								Assert.assertEquals("Processed document " + model + " does not have the expected status", expectedDocStatus, docStatus);
							}
						}
					});
		}
		else if (GridTabWrapper.getGridTab(model) != null)
		{
			final GridTab gridTab = GridTabWrapper.getGridTab(model);
			Assert.assertNotNull("GridTab " + gridTab + " does not have DocAction column defined", gridTab.getField("DocAction"));
			Assert.assertNotNull("GridTab " + gridTab + " does not have DocStatus column defined", gridTab.getField("DocStatus"));

			final GridWindowHelper window = mkGridWindowHelper().openForGridTab(gridTab);
			gridTab.setValue("DocAction", docAction);

			process = window.mkProcessHelper("DocAction")
					.addListener(new IProcessHelperListener()
					{

						@Override
						public void beforeRun(ProcessHelper process)
						{
						}

						@Override
						public void afterRun(ProcessHelper process)
						{
							// at this point, the GridTab should be refreshed

							final String docStatus = gridTab.get_ValueAsString("DocStatus");

							// At this point, document should be posted
							doAccountingAssertions().assertPostedFlag(model, true);

							if (expectedDocStatus != null)
							{
								Assert.assertEquals("Processed document " + model + " does not have the expected status", expectedDocStatus, docStatus);
							}
						}
					});
		}
		else
		{
			Assert.fail("Object " + model + " is not supported");
			throw new RuntimeException(); // never reached
		}

		return process;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#processComplete(org.compiere.process.DocAction)
	 */
	@Override
	public void processComplete(DocAction doc)
	{
		process(doc, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
	}

	/**
	 * @deprecated use {@link #mkBPartnerHelper()}.getC_BPartnerByName(bpName) instead
	 */
	@Override
	@Deprecated
	public I_C_BPartner getC_BPartner(String bpValue)
	{

		return mkBPartnerHelper().getC_BPartner(bpValue);
	}

	@Override
	public BPartnerHelper mkBPartnerHelper()
	{
		return new BPartnerHelper(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_Product()
	 */
	@Override
	public I_M_Product getM_Product()
	{
		return getM_Product(DEFAULT_ProductValue);
	}

	@Override
	public int getC_BP_BankAccount_ID(boolean isCashBank)
	{
		return getC_BP_BankAccount(isCashBank).getC_BP_BankAccount_ID();
	}

	@Override
	public I_C_BP_BankAccount getC_BP_BankAccount(boolean isCashBank)
	{
		return getC_BP_BankAccount(DEFAULT_BankAccount,
				isCashBank ? DEFAULT_CashbookBank : DEFAULT_Bank,
				DEFAULT_RoutingNo,
				isCashBank);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getC_BankAccount_ID(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public int getC_BP_BankAccount_ID(String bankAccountNo, String bankName, String routingNo, boolean isCashBank)
	{
		return getC_BP_BankAccount(bankAccountNo, bankName, routingNo, isCashBank).getC_BP_BankAccount_ID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getC_BankAccount(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	@Override
	// @Cached
	public I_C_BP_BankAccount getC_BP_BankAccount(String bankAccountNo, String bankName, String routingNo, boolean isCashBank)
	{

		final Properties ctx = getCtx();

		MBank bank = new Query(ctx, MBank.Table_Name, MBank.COLUMNNAME_Name + "=? AND " + MBank.COLUMNNAME_RoutingNo + " = ? ", null)
				.setParameters(bankName, routingNo)
				.setClient_ID()
				.firstOnly(MBank.class);

		if (bank == null)
		{
			bank = InterfaceWrapperHelper.create(ctx, MBank.class, null);
			bank.setName(bankName);
			bank.setRoutingNo(routingNo);
			bank.setIsCashBank(isCashBank);
			bank.setIsOwnBank(true);
			bank.setDescription("Created on " + new Date() + " by " + this.getInfo());
			InterfaceWrapperHelper.save(bank);
			logger.info("Created bank: " + bank.getName() + "-" + bank);
		}

		final String bankAccountNoFinal = parseItemName(bankAccountNo);

		I_C_BP_BankAccount bankAcct = new Query(ctx, I_C_BP_BankAccount.Table_Name, I_C_BP_BankAccount.COLUMNNAME_AccountNo + " = ? AND "
				+ I_C_BP_BankAccount.COLUMNNAME_C_Bank_ID + " = ? ", null)
				.setParameters(bankAccountNoFinal, bank.getC_Bank_ID())
				.setClient_ID()
				.firstOnly(I_C_BP_BankAccount.class);

		if (bankAcct == null)
		{
			// get Bank Acc

			bankAcct = InterfaceWrapperHelper.create(ctx, I_C_BP_BankAccount.class, null);
			bankAcct.setAccountNo(bankAccountNoFinal);
			bankAcct.setC_Bank_ID(bank.getC_Bank_ID());
			bankAcct.setC_Currency_ID(getC_Currency_ID(getCurrencyCode()));
			bankAcct.setBankAccountType(X_C_BankAccount.BANKACCOUNTTYPE_Savings);
			bankAcct.setDescription("Created on " + new Date() + " by " + this.getInfo());
			InterfaceWrapperHelper.save(bankAcct);
			logger.info("Created bank account: " + bankAcct.getAccountNo() + "-" + bankAcct);
		}

		return bankAcct;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_Product(java.lang.String)
	 */
	@Override
	public I_M_Product getM_Product(String productKey)
	{
		return getM_Product(productKey, DEFAULT_ProductCategoryValue);
	}

	@Override
	// @Cached
	public I_M_Product getM_Product(final String productKey, final String productCategoryValue)
	{
		Assert.assertNotNull("productKey shall not be null", productKey);
		Assert.assertNotNull("productCategoryValue shall not be null", productCategoryValue);

		final Properties ctx = getCtx();

		final String productValueFinal = parseItemName(productKey);

		I_M_Product product = new Query(ctx, MProduct.Table_Name, MProduct.COLUMNNAME_Value + "=?", null)
				.setParameters(productValueFinal)
				.setClient_ID()
				.firstOnly(I_M_Product.class);

		final I_M_Product_Category productCategory = getM_Product_Category(productCategoryValue);

		if (product == null)
		{
			product = InterfaceWrapperHelper.create(ctx, I_M_Product.class, null);
			product.setValue(productValueFinal);
			product.setName(productValueFinal);
			product.setDescription(getGeneratedBy());
			product.setM_Product_Category_ID(productCategory.getM_Product_Category_ID());
			product.setC_UOM_ID(100); // Ea
			InterfaceWrapperHelper.save(product);
			logger.info("Created product: " + product.getValue() + "-" + product);
		}

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_Product_Category()
	 */
	@Override
	public I_M_Product_Category getM_Product_Category()
	{
		return getM_Product_Category(DEFAULT_ProductCategoryValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_Product_Category(java.lang.String)
	 */
	@Override
	// @Cached
	public I_M_Product_Category getM_Product_Category(String value)
	{
		Assert.assertNotNull("value shall not be null", value);

		final Properties ctx = getCtx();

		I_M_Product_Category pc = new Query(ctx, I_M_Product_Category.Table_Name, I_M_Product_Category.COLUMNNAME_Value + "=?", null)
				.setParameters(value)
				.setClient_ID()
				.firstOnly(I_M_Product_Category.class);
		if (pc == null)
		{
			pc = InterfaceWrapperHelper.create(ctx, I_M_Product_Category.class, null);
			pc.setValue(value);
			pc.setName(value);
			pc.setDescription(getGeneratedBy());
			InterfaceWrapperHelper.save(pc);
			logger.info("Created product category: " + pc.getValue() + "-" + pc);
		}
		return pc;
	}

	private I_C_TaxCategory getDefaultC_TaxCategory()
	{
		final Properties ctx = getCtx();

		I_C_TaxCategory tc = new Query(ctx, I_C_TaxCategory.Table_Name, I_C_TaxCategory.COLUMNNAME_IsDefault + "=?", null)
				.setParameters(true)
				.setClient_ID()
				.setOrderBy(I_C_TaxCategory.COLUMNNAME_IsDefault + " DESC")
				.first(I_C_TaxCategory.class);
		if (tc == null)
			throw new AdempiereException("No tax category found");
		return tc;
	}

	@Override
	public I_C_TaxCategory getC_TaxCategory(String Name)
	{
		Assert.assertNotNull("Name shall not be null", Name);

		final Properties ctx = getCtx();

		final String finalName = parseItemName(Name);

		I_C_TaxCategory tc = new Query(ctx, I_C_TaxCategory.Table_Name, I_C_TaxCategory.COLUMNNAME_Name + "=?", null)
				.setParameters(finalName)
				.setClient_ID()
				.firstOnly(I_C_TaxCategory.class); // we have a DB unique idx on the C_TaxCategory.Name
		if (tc == null)
		{
			tc = InterfaceWrapperHelper.create(ctx, I_C_TaxCategory.class, null);
			tc.setName(finalName);
			tc.setDescription("Created by " + this.getInfo() + " on " + new Date());
			InterfaceWrapperHelper.save(tc);
		}
		return tc;
	}

	@Override
	public I_C_Tax getC_Tax(String Name, int C_TaxCategory_ID, Timestamp date, BigDecimal rate)
	{
		Assert.assertNotNull("Name shall not be null", Name);

		final Properties ctx = getCtx();

		final String finalName = parseItemName(Name);

		final String whereClause = I_C_Tax.COLUMNNAME_C_TaxCategory_ID + " = ? AND " + I_C_Tax.COLUMNNAME_ValidFrom + " <= ? AND "
				+ I_C_Tax.COLUMNNAME_Name + " = ?";
		I_C_Tax tc = new Query(ctx, I_C_Tax.Table_Name, whereClause, null)
				.setParameters(new Object[] { C_TaxCategory_ID, date, finalName })
				.setClient_ID()
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_Tax.COLUMNNAME_ValidFrom + " DESC ")
				.first(I_C_Tax.class);
		if (tc == null)
		{
			tc = InterfaceWrapperHelper.create(ctx, I_C_Tax.class, null);
			tc.setName(finalName);
			tc.setDescription("Created by " + this.getInfo() + " on " + new Date());
			tc.setValidFrom(TODAY);
			tc.setIsDocumentLevel(true);
			tc.setSOPOType(X_C_Tax.SOPOTYPE_Both);
			tc.setRate(rate);
			tc.setC_TaxCategory_ID(C_TaxCategory_ID);
			tc.setC_Country_ID(getCountryByCode(getCountryCode()).getC_Country_ID());
			tc.setTo_Country_ID(getCountryByCode(getCountryCode()).getC_Country_ID());
			InterfaceWrapperHelper.save(tc);
		}
		return tc;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_Product_FreightCost(java.lang.String)
	 */
	@Override
	public I_M_Product getM_Product_FreightCost(String productValue)
	{
		Assert.assertNotNull("productValue shall not be null", productValue);

		final String value = productValue + "_FreightCost";
		final String productCategoryValue = "TestFreightCost";
		final I_M_Product product = getM_Product(value, productCategoryValue);

		if (Services.get(IFreightCostBL.class).isFreightCostProduct(getCtx(), product.getM_Product_ID(), null))
		{
			return product;
		}

		final MFreightCost fc = new MFreightCost(getCtx(), 0, null);
		fc.setAD_Org_ID(product.getAD_Org_ID());
		fc.setM_Product_ID(product.getM_Product_ID());
		fc.setValue(product.getValue());
		fc.setName(product.getName());
		fc.setDescription(getGeneratedBy());
		InterfaceWrapperHelper.save(fc);
		// Manually reset the cache for M_FreightCost because:
		// * in case of new POs the cache is not automatically reset
		// * see MFreightCost.retrieveFor method which is cached
		CacheMgt.get().reset(I_M_FreightCost.Table_Name);
		logger.info("Created freight cost: " + fc.getValue() + "-" + fc);

		boolean checkIsFreightCost = Services.get(IFreightCostBL.class).isFreightCostProduct(getCtx(), product.getM_Product_ID(), null);
		Assert.assertTrue("Product shall be freight cost", checkIsFreightCost);

		return product;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#setAutomaticPeriodControl()
	 */
	@Override
	public void setAutomaticPeriodControl()
	{
		final Properties ctx = getCtx();

		for (final I_C_AcctSchema as : Services.get(IAcctSchemaDAO.class).retrieveClientAcctSchemas(ctx, Env.getAD_Client_ID(ctx)))
		{
			as.setAutoPeriodControl(true);
			as.setPeriod_OpenHistory(9999);
			as.setPeriod_OpenFuture(9999);
			InterfaceWrapperHelper.save(as);
			logger.info("Configured " + as);
		}
		CacheMgt.get().reset(I_C_AcctSchema.Table_Name);
		CacheMgt.get().reset(I_AD_ClientInfo.Table_Name); // client schemas are cached under this name

	}

	public I_C_BP_BankAccount getC_BP_BankAccount()
	{
		final Properties ctx = getCtx();

		final I_C_BP_BankAccount ba = new Query(ctx, I_C_BP_BankAccount.Table_Name, null, null)
				.setClient_ID()
				.setOrderBy(de.metas.banking.model.I_C_BP_BankAccount.COLUMNNAME_IsDefault + " DESC")
				.first(I_C_BP_BankAccount.class);
		Assert.assertNotNull("No default bank account found", ba);
		return ba;
	}

	public I_C_BP_BankAccount getC_BP_BankAccount(int AD_Org_ID, int C_Currency_ID)
	{
		final Properties ctx = getCtx();

		final String whereClause = I_C_BP_BankAccount.COLUMNNAME_AD_Org_ID + "=?"
				+ " AND " + I_C_BP_BankAccount.COLUMNNAME_C_Currency_ID + "=?";
		I_C_BP_BankAccount ba = new Query(ctx, I_C_BP_BankAccount.Table_Name, whereClause, null)
				.setParameters(AD_Org_ID, C_Currency_ID)
				.setClient_ID()
				.setOrderBy(de.metas.banking.model.I_C_BP_BankAccount.COLUMNNAME_IsDefault + " DESC")
				.first(I_C_BP_BankAccount.class);
		if (ba != null)
		{
			return ba;
		}

		//
		// TODO: Create new bank account
		ba = getC_BP_BankAccount();

		return ba;
	}

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm()
	{
		if (defaultPaymentTerm == null)
			defaultPaymentTerm = getC_PaymentTerm(defaultPaymentTermValue);
		return defaultPaymentTerm;
	}

	private I_C_PaymentTerm defaultPaymentTerm = null;

	@Override
	public org.compiere.model.I_C_PaymentTerm getC_PaymentTerm(String paymentTermValue)
	{
		I_C_PaymentTerm pt = new Query(ctx, I_C_PaymentTerm.Table_Name,
				I_C_PaymentTerm.COLUMNNAME_Value + "=?",
				null)
				.setParameters(paymentTermValue)
				.setClient_ID()
				.first(I_C_PaymentTerm.class);
		if (pt != null)
			return pt;

		pt = InterfaceWrapperHelper.create(ctx, I_C_PaymentTerm.class, null);
		pt.setValue(paymentTermValue);
		pt.setName(paymentTermValue);
		pt.setDescription("Created by " + this.getInfo() + " on " + new Date());
		pt.setAD_Org_ID(0);
		InterfaceWrapperHelper.save(pt);

		return pt;
	}

	@Override
	public org.compiere.model.I_C_Charge getC_Charge()
	{
		if (defaultCharge == null)
			defaultCharge = getC_Charge(defaultChargeValue, null);
		return defaultCharge;
	}

	private I_C_Charge defaultCharge = null;

	@Override
	public org.compiere.model.I_C_Charge getC_Charge(final String chargeName, final String taxCategoryName)
	{
		I_C_Charge ch = new Query(ctx, I_C_Charge.Table_Name,
				I_C_Charge.COLUMNNAME_Name + "=?",
				null)
				.setParameters(chargeName)
				.setClient_ID()
				.firstOnly(I_C_Charge.class); // we have a DB unique idx on C_Charge.Name
		if (ch != null)
			return ch;

		ch = InterfaceWrapperHelper.create(ctx, I_C_Charge.class, null);
		ch.setName(chargeName);
		ch.setDescription("Created by " + this.getInfo() + " on " + new Date());

		final I_C_TaxCategory taxCategory =
				taxCategoryName == null
						? getDefaultC_TaxCategory()
						: getC_TaxCategory(taxCategoryName);

		ch.setC_TaxCategory_ID(taxCategory.getC_TaxCategory_ID());

		ch.setChargeAmt(Env.ONE);
		InterfaceWrapperHelper.save(ch);

		return ch;
	}

	@Override
	public I_M_DiscountSchema getM_DiscountSchema(String name)
	{
		Assert.assertNotNull("value shall not be null", name);

		final Properties ctx = getCtx();

		final String valueFinal = parseItemName(name);

		final String whereClause = I_M_DiscountSchema.COLUMNNAME_Name + "=?";
		I_M_DiscountSchema ds = new Query(ctx, I_M_DiscountSchema.Table_Name, whereClause, null)
				.setParameters(valueFinal)
				.setClient_ID()
				.firstOnly(I_M_DiscountSchema.class);

		if (ds == null)
		{
			ds = InterfaceWrapperHelper.create(ctx, I_M_DiscountSchema.class, null);
			ds.setDiscountType(X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent);
			ds.setFlatDiscount(BigDecimal.ZERO);
			ds.setIsBPartnerFlatDiscount(true);
			ds.setName(valueFinal);
			InterfaceWrapperHelper.save(ds);
			logger.info("Created discount schema " + ds);
		}
		return ds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_PricingSystem(java.lang.String)
	 */
	@Override
	public I_M_PricingSystem getM_PricingSystem(String value)
	{
		Assert.assertNotNull("value shall not be null", value);

		final Properties ctx = getCtx();

		final String valueFinal = parseItemName(value);

		final String whereClause = I_M_PricingSystem.COLUMNNAME_Value + "=?";
		I_M_PricingSystem ps = new Query(ctx, I_M_PricingSystem.Table_Name, whereClause, null)
				.setParameters(valueFinal)
				.setClient_ID()
				.firstOnly(I_M_PricingSystem.class);
		if (ps == null)
		{
			ps = InterfaceWrapperHelper.create(ctx, I_M_PricingSystem.class, null);
			ps.setValue(valueFinal);
			ps.setName(valueFinal);
			ps.setDescription(getGeneratedBy());
			ps.setIsActive(true);
			InterfaceWrapperHelper.save(ps);
			logger.info("Created price system " + ps.getName() + "-" + ps);
		}
		return ps;
	}

	private I_C_Country getCountryByCode(String countryCode)
	{
		Assert.assertNotNull("countryCode should not be null", countryCode);

		final Properties ctx = getCtx();

		for (I_C_Country c : Services.get(ICountryDAO.class).getCountries(ctx))
		{
			if (c.getCountryCode().equals(countryCode))
				return c;
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getC_Currency_ID(java.lang.String)
	 */
	@Override
	public int getC_Currency_ID(String ISOcode)
	{
		Assert.assertNotNull("ISOcode should not be null", ISOcode);

		final Properties ctx = getCtx();

		return Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(ctx, ISOcode).getC_Currency_ID();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_PriceList(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList(String pricingSystemValue, String currencyCode, String countryCode)
	{
		final boolean isSO = true;
		return getM_PriceList(pricingSystemValue, currencyCode, countryCode, isSO);
	}

	@Override
	public org.compiere.model.I_M_PriceList getM_PriceList(String pricingSystemValue, String currencyCode, String countryCode, boolean isSOPriceList)
	{
		Assert.assertNotNull("pricingSystemValue should not be null", pricingSystemValue);
		Assert.assertNotNull("currencyCode should not be null", currencyCode);
		Assert.assertNotNull("countryCode should not be null", countryCode);

		// not yet ported from 
		// final boolean isPOPriceList = !isSOPriceList;

		final Properties ctx = getCtx();

		final I_M_PricingSystem ps = getM_PricingSystem(pricingSystemValue);

		final int currencyId = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(ctx, currencyCode).getC_Currency_ID();
		Assert.assertTrue("No currency found for " + currencyCode, currencyId > 0);

		final int countryId = getCountryByCode(countryCode).getC_Country_ID();
		Assert.assertTrue("No country found for " + countryCode, countryId > 0);

		final String priceListValue = ps.getValue() + "-" + currencyCode + "-" + (isSOPriceList ? "SO" : "PO");

		final String whereClause = org.compiere.model.I_M_PriceList.COLUMNNAME_M_PricingSystem_ID + "=?"
				// + " AND " + org.compiere.model.I_M_PriceList.COLUMNNAME_Name + "=?"
				// + " AND " + org.compiere.model.I_M_PriceList.COLUMNNAME_IsSOPriceList + "=?"
				// + " AND " + org.compiere.model.I_M_PriceList.COLUMNNAME_C_Currency_ID + "=?"
				+ " AND " + org.compiere.model.I_M_PriceList.COLUMNNAME_C_Country_ID + "=?";

		org.compiere.model.I_M_PriceList pl = new Query(ctx, org.compiere.model.I_M_PriceList.Table_Name, whereClause, null)
				.setParameters(ps.getM_PricingSystem_ID()/* , priceListValue, IsSO, currencyId */, countryId)
				.firstOnly(org.compiere.model.I_M_PriceList.class);
		if (pl == null)
		{
			pl = InterfaceWrapperHelper.create(ctx, I_M_PriceList.class, null);
			pl.setM_PricingSystem_ID(ps.getM_PricingSystem_ID());
			pl.setName(priceListValue);
			pl.setDescription(getGeneratedBy());
			pl.setC_Country_ID(countryId);
			pl.setC_Currency_ID(currencyId);
			pl.setIsSOPriceList(isSOPriceList);
			// pl.setIsPOPriceList(isPOPriceList); not yet ported from 
			pl.setPricePrecision(2);
			pl.setIsTaxIncluded(true);
			pl.setEnforcePriceLimit(false);
			pl.setIsDefault(true);
			InterfaceWrapperHelper.save(pl);
			logger.info("Created price list " + pl.getName() + "-" + pl
					+ " (" + ps.getName() + "-" + ps + ", " + countryCode + ", " + currencyCode + ")");
		}
		else
		{
			Assert.assertEquals("PriceList does not have expected settings: PriceList=" + pl.getName() + ", ColumnName=IsSOPriceList",
					isSOPriceList, pl.isSOPriceList());
			// not yet ported from 
			// Assert.assertEquals("PriceList does not have expected settings: PriceList=" + pl.getName() + ", ColumnName=IsPOPriceList",
			// isPOPriceList, pl.isPOPriceList());
			Assert.assertEquals("PriceList does not have expected settings: PriceList=" + pl.getName() + ", ColumnName=C_Currency_ID",
					currencyId, pl.getC_Currency_ID());
			Assert.assertEquals("PriceList does not have expected settings: PriceList=" + pl.getName() + ", ColumnName=C_Country_ID",
					countryId, pl.getC_Country_ID());
		}
		return pl;
	}

	private I_M_PriceList_Version getM_PriceList_Version(org.compiere.model.I_M_PriceList pl)
	{
		final Properties ctx = getCtx();

		String whereClause = I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID + "=?";
		I_M_PriceList_Version plv = new Query(ctx, I_M_PriceList_Version.Table_Name, whereClause, null)
				.setParameters(pl.getM_PriceList_ID())
				.setOrderBy(I_M_PriceList_Version.COLUMNNAME_ValidFrom + " DESC")
				.first(I_M_PriceList_Version.class);

		if (plv == null)
		{
			plv = InterfaceWrapperHelper.create(ctx, I_M_PriceList_Version.class, null);
			plv.setM_PriceList_ID(pl.getM_PriceList_ID());
			plv.setM_DiscountSchema_ID(1000000); // TODO: hardcoded

			// M_Pricelist_Version_Base_ID is not mandatory anymore
			// plv.setM_Pricelist_Version_Base_ID(_config.getM_PriceListVersion_Base_ID());

			plv.setValidFrom(TimeUtil.getDay(1970, 1, 1));
			InterfaceWrapperHelper.save(plv);
			logger.info("Created price list version " + plv + " for " + pl.getName() + "-" + pl);
		}

		return plv;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#setProductPrice(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	@Deprecated
	public I_M_ProductPrice setProductPrice(String pricingSystemValue, String currencyCode, String countryCode,
			String productValue,
			BigDecimal price)
	{
		final boolean isSO = true;
		return setProductPrice(pricingSystemValue, currencyCode, countryCode, productValue, price, isSO);
	}

	@Override
	public I_M_ProductPrice setProductPrice(
			final String pricingSystemValue, 
			final String currencyCode, 
			final String countryCode,
			final String productValue,
			final BigDecimal price, 
			final boolean IsSO)
	{
		final int C_TaxCategory_ID = -1;
		return setProductPrice(pricingSystemValue, currencyCode, countryCode, productValue, price, C_TaxCategory_ID, IsSO);
	}

	@Override
	public I_M_ProductPrice setProductPrice(
			final String pricingSystemValue,
			final String currencyCode,
			final String countryCode,
			final String productValue,
			final BigDecimal price,
			final int C_TaxCategory_ID,
			final boolean IsSO)
	{
		final I_M_PriceList pl = getM_PriceList(pricingSystemValue, currencyCode, countryCode, IsSO);

		return setProductPrice(pl, productValue, price, C_TaxCategory_ID);
	}

	@Override
	public I_M_PriceList_Version getM_PriceList_Version(String pricingSystemValue, String currencyCode, String countryCode)
	{
		return getM_PriceList_Version(getM_PriceList(pricingSystemValue, currencyCode, countryCode));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#setProductPrice(org.compiere.model.I_M_PriceList, java.lang.String, java.math.BigDecimal)
	 */
	@Override
	public I_M_ProductPrice setProductPrice(org.compiere.model.I_M_PriceList pl, String productValue, BigDecimal price)
	{
		return setProductPrice(pl, productValue, price, -1); // C_TaxCategory_ID == -1
	}

	@Override
	public I_M_ProductPrice setProductPrice(
			final org.compiere.model.I_M_PriceList pl,
			final String productValue,
			final BigDecimal price,
			final int C_TaxCategory_ID)
	{
		final I_M_PriceList_Version plv = getM_PriceList_Version(pl);
		final I_M_Product product = getM_Product(productValue);

		String whereClause = I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID + "=?"
				+ " AND " + I_M_ProductPrice.COLUMNNAME_M_Product_ID + "=?";

		final boolean emptyTaxCateg = C_TaxCategory_ID <= 0;

		if (!emptyTaxCateg)
			whereClause += " AND " + I_M_ProductPrice.COLUMNNAME_C_TaxCategory_ID + "=?";

		final List<Object> params = new ArrayList<Object>();
		params.add(plv.getM_PriceList_Version_ID());
		params.add(product.getM_Product_ID());
		if (!emptyTaxCateg)
		{
			params.add(C_TaxCategory_ID);
		}

		I_M_ProductPrice pp = new Query(ctx, I_M_ProductPrice.Table_Name, whereClause, null)
				.setParameters(params)
				.firstOnly(I_M_ProductPrice.class);
		if (pp == null)
		{
			pp = InterfaceWrapperHelper.create(ctx, I_M_ProductPrice.class, ITrx.TRXNAME_None);
			pp.setM_PriceList_Version_ID(plv.getM_PriceList_Version_ID());
			pp.setM_Product_ID(product.getM_Product_ID());
			if (emptyTaxCateg)
			{
				pp.setC_TaxCategory_ID(getConfig().getC_TaxCategory_Normal_ID());
			}
			else
			{
				pp.setC_TaxCategory_ID(C_TaxCategory_ID);
			}
		}

		pp.setIsActive(true);
		pp.setPriceLimit(price);
		pp.setPriceList(price);
		pp.setPriceStd(price);
		pp.setC_UOM_ID(product.getC_UOM_ID());
		pp.setSeqNo(10);
		InterfaceWrapperHelper.save(pp);

		logger.info("Set price: " + price + " for Product=" + product.getValue() + ", PriceList=" + pl.getName() + "-" + pl);
		return pp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#getM_Warehouse()
	 */
	@Override
	public MWarehouse getM_Warehouse()
	{
		final Properties ctx = getCtx();

		final int AD_Org_ID = Env.getAD_Org_ID(ctx);
		MWarehouse[] whs = MWarehouse.getForOrg(ctx, AD_Org_ID);
		if (whs == null || whs.length == 0)
			throw new AdempiereException("No warehouse found for AD_Org_ID=" + AD_Org_ID);
		return whs[0];
	}

	private String currencyCode;

	/**
	 * Returns the currency code used when creating the order and making sure that there is also pricing data. If no value has been set using {@link #setCurrencyCode(String)}, then the code of the
	 * AD_Client configured in the <code>helper</code>'s <code>ctx</code> is used.
	 * 
	 * @return
	 */
	@Override
	public String getCurrencyCode()
	{
		if (currencyCode == null)
		{
			currencyCode = Services.get(ICurrencyBL.class).getBaseCurrency(getCtx()).getISO_Code();
		}
		return currencyCode;
	}

	private String countryCode;

	/**
	 * Returns the country code used when creating the order and making sure that there is also pricing data. If no value has been set using {@link #setCountryCode(String)}, then the code of the
	 * AD_Client configured in the <code>helper</code>'s <code>ctx</code> is used.
	 * 
	 * @return
	 */
	@Override
	public String getCountryCode()
	{
		if (countryCode == null)
		{
			final MClient client = MClient.get(getCtx(), Env.getAD_Client_ID(getCtx()));
			countryCode = client.getLocale().getCountry();
		}
		return countryCode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#mkOrderHelper()
	 */
	@Override
	public OrderHelper mkOrderHelper()
	{
		return new OrderHelper(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#mkInvoiceHelper()
	 */
	@Override
	public InvoiceHelper mkInvoiceHelper()
	{
		return new InvoiceHelper(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#createInvoice(org.compiere.model.I_C_Order)
	 */
	@Override
	public de.metas.adempiere.model.I_C_Invoice createInvoice(I_C_Order order)
	{
		runProcess_InvoiceGenerate(order.getC_Order_ID());
		List<de.metas.adempiere.model.I_C_Invoice> invoices = retrieveInvoicesForOrders(new int[] { order.getC_Order_ID() }, getTrxName());
		Assert.assertNotNull(invoices);
		Assert.assertEquals("Only one invoice should be generated", 1, invoices.size());
		return invoices.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#createInOut(org.compiere.model.I_C_Order)
	 */
	@Override
	public I_M_InOut createInOut(I_C_Order order)
	{
		runProcess_InOutGenerate(order.getC_Order_ID());
		List<I_M_InOut> inouts = retrieveInOutsForOrders(new int[] { order.getC_Order_ID() }, getTrxName());
		Assert.assertNotNull(inouts);
		Assert.assertEquals("Only one InOut should be generated", 1, inouts.size());
		return inouts.get(0);
	}

	@Override
	public void createOrder_checkOrderLine(
			I_C_OrderLine orderLine,
			BigDecimal priceActualBD,
			I_M_ProductPrice pp)
	{
		Assert.assertEquals("order line price actual not match - " + orderLine, priceActualBD, orderLine.getPriceActual());
		Assert.assertEquals("order line price list not match - " + orderLine, pp.getPriceList(), orderLine.getPriceList());
		Assert.assertEquals("order line price limit not match - " + orderLine, pp.getPriceLimit(), orderLine.getPriceLimit());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#createT_Selection(int, int)
	 */
	@Override
	public void createT_Selection(int AD_PInstance_ID, int... ids)
	{
		if (ids == null || ids.length == 0)
			return;

		StringBuffer insert = new StringBuffer();
		insert.append("INSERT INTO T_SELECTION(AD_PINSTANCE_ID, T_SELECTION_ID) ");
		for (int i = 0; i < ids.length; i++)
		{
			if (i > 0)
				insert.append(" UNION ");
			insert.append("SELECT ").append(AD_PInstance_ID).append(",").append(ids[i]).append(" FROM DUAL");
		}
		DB.executeUpdateEx(insert.toString(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#runProcess_InvoiceGenerate(int)
	 */
	@Override
	public void runProcess_InvoiceGenerate(int... orderIds)
	{
		runProcess_InvoiceGenerate(getTrxName(), orderIds);
	}

	@Override
	public void runProcess_InvoiceGenerate(String trxName, int... orderIds)
	{
		mkProcessHelper()
				.setTrxName(trxName)
				.setProcessClass(InvoiceGenerate.class)
				.setSelection(orderIds)
				.setParameter("Selection", true)
				.setParameter("DocAction", DocAction.ACTION_Complete)
				.run();
	}

	@Override
	public void runProcess_InvoiceGenerateFromCands(I_C_Invoice_Candidate... cands)
	{
		final int[] invoiceCandIds = new int[cands.length];
		for (int i = 0; i < cands.length; i++)
		{
			invoiceCandIds[i] = cands[i].getC_Invoice_Candidate_ID();
		}

		mkProcessHelper()
				.setProcessClass(C_Invoice_Candidate_GenerateInvoice.class)
				.setSelection(invoiceCandIds)
				.setParameter("Selection", true)
				.setParameter("DocAction", DocAction.ACTION_Complete)
				.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#runProcess_InOutGenerate(int)
	 */
	@Override
	public void runProcess_InOutGenerate(int... orderIds)
	{
		ProcessHelper processHelper = mkProcessHelper()
				.setProcessClass(org.adempiere.inout.process.InOutGenerate.class)
				.setParameter("Selection", true)
				.setParameter("M_Warehouse_ID", getM_Warehouse().getM_Warehouse_ID());

		for (int orderId : orderIds)
		{
			int[] ids = new Query(ctx, I_C_OrderLine.Table_Name, "C_Order_ID=?", trxName)
					.setParameters(orderId)
					.getIDs();
			processHelper.addSelection(ids);
		}

		processHelper.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#retrieveInvoicesForOrders(int[], java.lang.String)
	 */
	@Override
	public List<de.metas.adempiere.model.I_C_Invoice> retrieveInvoicesForOrders(int[] orderIds, String trxName)
	{
		final Properties ctx = getCtx();

		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlParams = new StringBuffer();
		for (int i = 0; i < orderIds.length; i++)
		{
			if (i > 0)
				sqlParams.append(",");
			sqlParams.append("?");
			params.add(orderIds[i]);
		}
		final String whereClause = "EXISTS (SELECT 1 FROM C_InvoiceLine il"
				+ " INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID=il.C_OrderLine_ID)"
				+ " WHERE il.C_Invoice_ID=C_Invoice.C_Invoice_ID"
				+ " AND ol.C_Order_ID IN (" + sqlParams + ")"
				+ ")";
		List<de.metas.adempiere.model.I_C_Invoice> list = new Query(ctx, de.metas.adempiere.model.I_C_Invoice.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setOrderBy(de.metas.adempiere.model.I_C_Invoice.COLUMNNAME_C_Invoice_ID)
				.list(de.metas.adempiere.model.I_C_Invoice.class);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#retrieveInOutsForOrders(int[], java.lang.String)
	 */
	@Override
	public List<I_M_InOut> retrieveInOutsForOrders(int[] orderIds, String trxName)
	{
		final Properties ctx = getCtx();

		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlParams = new StringBuffer();
		for (int i = 0; i < orderIds.length; i++)
		{
			if (i > 0)
				sqlParams.append(",");
			sqlParams.append("?");
			params.add(orderIds[i]);
		}
		final String whereClause = "EXISTS (SELECT 1 FROM M_InOutLine iol"
				+ " INNER JOIN C_OrderLine ol ON (ol.C_OrderLine_ID=iol.C_OrderLine_ID)"
				+ " WHERE iol.M_InOut_ID=M_InOut.M_InOut_ID"
				+ " AND ol.C_Order_ID IN (" + sqlParams + ")"
				+ ")";
		List<I_M_InOut> list = new Query(ctx, I_M_InOut.Table_Name, whereClause, trxName)
				.setParameters(params)
				.setOrderBy(I_M_InOut.COLUMNNAME_M_InOut_ID)
				.list(I_M_InOut.class);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#retrieveShipmentSchedulesForOrders(int)
	 */
	@Override
	public List<I_M_ShipmentSchedule> retrieveShipmentSchedulesForOrders(int... orderIds)
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlParams = new StringBuffer();
		for (int i = 0; i < orderIds.length; i++)
		{
			if (i > 0)
				sqlParams.append(",");
			sqlParams.append("?");
			params.add(orderIds[i]);
		}
		final String whereClause = I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID + " IN (" + sqlParams + ")";
		List<I_M_ShipmentSchedule> list = new Query(ctx, I_M_ShipmentSchedule.Table_Name, whereClause, trxName)
				.setParameters(params)
				.list(I_M_ShipmentSchedule.class);
		return list;
	}

	@Override
	public void runProcess_UpdateShipmentScheds()
	{
		mkProcessHelper()
				.setProcessClass(UpdateShipmentScheds.class)
				.run();
	}

	@SuppressWarnings("unused")
	private void dumpInvoicesForOrders(int... orderIds)
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		List<de.metas.adempiere.model.I_C_Invoice> list = retrieveInvoicesForOrders(orderIds, trxName);
		for (de.metas.adempiere.model.I_C_Invoice invoice : list)
		{
			System.out.println("Invoice: " + invoice);
			List<I_C_InvoiceLine> lines = retrieveLines(invoice);
			for (I_C_InvoiceLine iline : lines)
			{
				System.out.println("\t" + iline);
			}
		}

	}

	@SuppressWarnings("unused")
	private void dumpInOutsForOrders(int... orderIds)
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		List<I_M_InOut> list = retrieveInOutsForOrders(orderIds, trxName);
		for (I_M_InOut io : list)
		{
			System.out.println("InOut: " + io);
			List<I_M_InOutLine> lines = new Query(ctx, I_M_InOutLine.Table_Name, "M_InOut_ID=?", trxName)
					.setParameters(io.getM_InOut_ID())
					.setOrderBy("Line")
					.list(I_M_InOutLine.class);
			for (I_M_InOutLine iol : lines)
			{
				System.out.println("\t" + iol);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#addInventory(java.lang.String, int)
	 */
	@Override
	public void addInventory(final String productValue, final int qty)
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final MWarehouse wh = getM_Warehouse();
		final I_M_Locator loc = Services.get(IWarehouseBL.class).getDefaultLocator(wh);
		final I_M_Product product = getM_Product(productValue);

		boolean ok = MStorage.add(ctx,
				wh.getM_Warehouse_ID(),
				loc.getM_Locator_ID(),
				product.getM_Product_ID(),
				0, // M_AttributeSetInstance_ID,
				0, // reservationAttributeSetInstance_ID,
				new BigDecimal(qty), // diffQtyOnHand,
				Env.ZERO, // diffQtyReserved,
				Env.ZERO, // diffQtyOrdered,
				trxName);
		if (!ok)
			throw new AdempiereException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#retrieveLines(org.compiere.model.I_C_Invoice)
	 */
	@Override
	public List<de.metas.adempiere.model.I_C_InvoiceLine> retrieveLines(I_C_Invoice invoice)
	{
		Properties ctx = InterfaceWrapperHelper.getCtx(invoice);
		String trxName = InterfaceWrapperHelper.getTrxName(invoice);
		List<de.metas.adempiere.model.I_C_InvoiceLine> lines = new Query(ctx, I_C_InvoiceLine.Table_Name, I_C_InvoiceLine.COLUMNNAME_C_Invoice_ID + "=?", trxName)
				.setParameters(invoice.getC_Invoice_ID())
				.setOrderBy(I_C_InvoiceLine.COLUMNNAME_Line)
				.list(de.metas.adempiere.model.I_C_InvoiceLine.class);
		return lines;
	}

	@Override
	public List<I_C_OrderLine> retrieveLines(I_C_Order order)
	{
		return Services.get(IOrderPA.class).retrieveOrderLines(order, I_C_OrderLine.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#assertInvoiced(org.compiere.model.I_C_Order)
	 */
	@Override
	public void assertInvoiced(I_C_Order order)
	{
		Properties ctx = InterfaceWrapperHelper.getCtx(order);
		String trxName = InterfaceWrapperHelper.getTrxName(order);
		List<I_C_OrderLine> lines = new Query(ctx, I_C_OrderLine.Table_Name, I_C_OrderLine.COLUMNNAME_C_Order_ID + "=?", trxName)
				.setParameters(order.getC_Order_ID())
				.list(I_C_OrderLine.class);

		for (I_C_OrderLine line : lines)
		{
			assertThat("Line is not invoiced - " + line, line.getQtyInvoiced(), comparesEqualTo(line.getQtyOrdered()));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#mkPaymentHelper()
	 */
	@Override
	public PaymentHelper mkPaymentHelper()
	{
		return new PaymentHelper(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#mkGridWindowHelper()
	 */
	@Override
	public GridWindowHelper mkGridWindowHelper()
	{
		return new GridWindowHelper(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#mkProcessHelper()
	 */
	@Override
	public ProcessHelper mkProcessHelper()
	{
		return new ProcessHelper(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see test.integration.common.helper.IHelper#createPO(java.lang.Class)
	 */
	@Override
	public <T> T createPO(Class<T> cl)
	{
		T model = InterfaceWrapperHelper.create(ctx, cl, trxName);
		PO po = InterfaceWrapperHelper.getPO(model);
		if (po.get_ColumnIndex("Description") >= 0)
		{
			po.set_ValueOfColumn("Description", "Created on " + new Date() + " by " + this.getInfo());
		}
		return model;
	}

	@Override
	public <T> T wrap(Object model, Class<T> clazz)
	{
		if (model == null)
		{
			return null;
		}

		return InterfaceWrapperHelper.create(model, clazz);
	}

	@Override
	public void save(Object model)
	{
		Assert.assertNotNull("model is null", model);
		InterfaceWrapperHelper.save(model);
	}

	@Override
	public AcctFactAssert doAccountingAssertions()
	{
		return acctFactAsserts;
	}

	/**
	 * Helper String representation (including some settings)
	 */
	public String getInfo()
	{
		return getClass().getCanonicalName();
	}

	@Override
	public String getGeneratedBy()
	{
		return "Generated by " + this.getInfo() + " on " + (new Date());
	}

	@Override
	public void addChatInfoToPO(final Object model, final String info)
	{
		final PO po = InterfaceWrapperHelper.getPO(model);

		final MChat chat = new MChat(getCtx(), po.get_Table_ID(), po.get_ID(), null, getTrxName());
		chat.saveEx();
		final MChatEntry entry = new MChatEntry(chat, info);
		entry.saveEx();
	}

	// not yet ported from 
	// @Override
	// public BOMBuilder mkBOMBuilder()
	// {
	// return new BOMBuilder(this);
	// }
}
