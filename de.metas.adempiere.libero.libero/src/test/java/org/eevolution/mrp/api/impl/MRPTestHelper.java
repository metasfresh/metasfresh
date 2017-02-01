package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.util.time.TimeSource;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_C_UOM_Conversion;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.model.I_S_ResourceType;
import org.compiere.model.X_AD_Workflow;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.LiberoConstants;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_DD_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPExecutorService;
import org.eevolution.mrp.api.IMutableMRPContext;
import org.eevolution.mrp.expectations.MRPExpectation;
import org.eevolution.mrp.spi.impl.DDOrderMRPSupplyProducer;
import org.eevolution.util.DDNetworkBuilder;
import org.eevolution.util.PPProductPlanningBuilder;
import org.eevolution.util.ProductBOMBuilder;
import org.junit.Assume;
import org.slf4j.Logger;
import org.slf4j.Logger;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.document.engine.IDocActionBL;
import de.metas.document.engine.impl.PlainDocActionBL;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

public class MRPTestHelper
{
	//
	// Context
	private Timestamp _today = SystemTime.asDayTimestamp();
	public Properties ctx;
	private String trxName;
	public final IContextAware contextProvider = new IContextAware()
	{
		@Override
		public Properties getCtx()
		{
			return ctx;
		}

		@Override
		public String getTrxName()
		{
			return trxName;
		}

		@Override
		public boolean isAllowThreadInherited()
		{
			return false;
		}
	};

	//
	// Services
	public PlainMRPDAO mrpDAO;
	public IQueryBL queryBL;
	public PlainDocActionBL docActionBL;
	//
	public MRPExecutorService mrpExecutorService;
	public MockedMRPExecutor mrpExecutor;

	//
	// Master Data
	public I_AD_Client adClient;
	public I_AD_Org adOrg01;
	//
	public I_C_UOM uomKg;
	public I_C_UOM uomEach;
	public I_M_Shipper shipperDefault;
	//
	public I_S_ResourceType resourceType_Plants;
	public I_S_ResourceType resourceType_Workcenters;
	public final I_S_Resource plant_any = null;
	//
	public I_AD_Workflow workflow_Standard;
	//
	public I_C_BP_Group bpGroupDefault;
	public I_M_Product_Category productCategoryDefault;

	public MRPTestHelper()
	{
		this(true);
	}

	public MRPTestHelper(final boolean initEnvironment)
	{
		super();
		init(initEnvironment);
	}

	private void init(final boolean initEnvironment)
	{
		if (initEnvironment)
		{
			AdempiereTestHelper.get().init();
		}

		// POJOWrapper.setDefaultStrictValues(true);
		this.mrpDAO = (PlainMRPDAO)Services.get(IMRPDAO.class);
		this.queryBL = Services.get(IQueryBL.class);
		this.docActionBL = (PlainDocActionBL)Services.get(IDocActionBL.class);

		setupContext(initEnvironment);
		setupMRPExecutorService();

		registerModelValidators();

		createMasterData();

	}

	private void setupContext(final boolean initEnvironment)
	{
		ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;

		//
		// Setup context: #AD_Client_ID
		int adClientId = Env.getAD_Client_ID(ctx);
		if (adClientId <= 0)
		{
			adClient = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
			InterfaceWrapperHelper.save(adClient);
			adClientId = adClient.getAD_Client_ID();
			Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClientId);
		}
		else
		{
			adClient = InterfaceWrapperHelper.create(ctx, adClientId, I_AD_Client.class, ITrx.TRXNAME_None);
		}

		int adOrgId = Env.getAD_Org_ID(ctx);
		if (adOrgId <= 0)
		{
			adOrg01 = createOrg("Org01");
			adOrgId = adOrg01.getAD_Org_ID();
			Env.setContext(ctx, Env.CTXNAME_AD_Org_ID, adOrgId);
		}
		else
		{
			adOrg01 = InterfaceWrapperHelper.create(ctx, adOrgId, I_AD_Org.class, ITrx.TRXNAME_None);
		}

		SystemTime.setTimeSource(new TimeSource()
		{
			@Override
			public long millis()
			{
				return _today.getTime();
			}
		});
	}

	private void setupMRPExecutorService()
	{
		mrpExecutor = new MockedMRPExecutor();

		Services.registerService(IMRPExecutorService.class, new MRPExecutorService()
		{
			@Override
			protected IMRPExecutor createMRPExecutor()
			{
				return mrpExecutor;
			}
		});
		mrpExecutorService = (MRPExecutorService)Services.get(IMRPExecutorService.class);
		LogManager.setLoggerLevel(getMRPLogger(), Level.INFO);
	}

	private void createMasterData()
	{
		this.uomEach = createUOM("Ea");
		this.uomKg = createUOM("Kg");

		this.shipperDefault = createShipper("shipperDefault");

		this.resourceType_Plants = createResourceType("Plants");
		this.resourceType_Workcenters = createResourceType("Workcenters");

		this.workflow_Standard = createWorkflow("Standard_MFG");
		createWorkflowNode(workflow_Standard, "Start", true);

		this.productCategoryDefault = createProductCategory("Default");
		this.bpGroupDefault = createBPGroup("Default");

		createDocType(X_C_DocType.DOCBASETYPE_DistributionOrder);
		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingOrder);
		createDocType(X_C_DocType.DOCBASETYPE_ManufacturingCostCollector);

		createMRPMessage(MRPExecutor.MRP_ERROR_999_Unknown);
		createMRPMessage(MRPExecutor.MRP_ERROR_050_CancelSupplyNotice);
		createMRPMessage(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		createMRPMessage(MRPExecutor.MRP_ERROR_070_SupplyPastDueButNotReleased);
		// createMRPMessage(MRPExecutor.MRP_ERROR_);
		// createMRPMessage(MRPExecutor.MRP_ERROR_);
		// createMRPMessage(MRPExecutor.MRP_ERROR_);
		createMRPMessage(MRPExecutor.MRP_ERROR_120_NoProductPlanning);
		createMRPMessage(MRPExecutor.MRP_ERROR_150_DemandPastDue);
		createMRPMessage(MRPExecutor.MRP_ERROR_160_CannotCreateDocument);
		createMRPMessage(DDOrderMRPSupplyProducer.ERR_DRP_010_InTransitWarehouseNotFound);
		createMRPMessage(DDOrderMRPSupplyProducer.ERR_DRP_060_NoSourceOfSupply);
	}

	private void registerModelValidators()
	{
		// FIXME: workaround to bypass org.adempiere.document.service.impl.PlainDocActionBL.isDocumentTable(String) failure
		PlainDocActionBL.isDocumentTableResponse = false;

		final I_AD_Client client = null;
		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		modelInterceptorRegistry.addModelInterceptor(new org.adempiere.model.validator.AdempiereBaseValidator(), client);
		modelInterceptorRegistry.addModelInterceptor(new org.eevolution.model.LiberoValidator(), client);
		modelInterceptorRegistry.addModelInterceptor(new org.eevolution.model.validator.MRPInterceptor(), client);
	}

	public Timestamp getToday()
	{
		return _today;
	}

	public void setToday(int year, int month, int day)
	{
		this._today = TimeUtil.getDay(year, month, day);
	}

	public IMutableMRPContext createMutableMRPContext()
	{
		final IMutableMRPContext mrpContext = new MRPContext();
		mrpContext.setCtx(ctx);
		mrpContext.setTrxName(ITrx.TRXNAME_None);
		mrpContext.setRequireDRP(true);
		// mrpContext.setPlanner_User_ID(p_Planner_ID);
		mrpContext.setDate(_today);
		mrpContext.setAD_Client_ID(adClient.getAD_Client_ID());

		return mrpContext;
	}

	public I_AD_Org createOrg(final String name)
	{
		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, I_AD_Org.class, ITrx.TRXNAME_None);
		org.setValue(name);
		org.setName(name);
		InterfaceWrapperHelper.save(org);

		final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.newInstance(I_AD_OrgInfo.class, org);
		orgInfo.setAD_Org_ID(org.getAD_Org_ID());

		//
		// InTransit Warehouse
		final I_M_Warehouse warehouseInTransit = createWarehouse(name + "_InTransit", org);
		warehouseInTransit.setIsInTransit(true);
		InterfaceWrapperHelper.save(warehouseInTransit);

		//
		// BP Org Link
		final I_C_BPartner bpartner = createBPartner("BPOrg_" + name);
		// bpartner.setAD_OrgBP_ID(String.valueOf(org.getAD_Org_ID()));
		bpartner.setAD_OrgBP_ID(org.getAD_Org_ID());
		InterfaceWrapperHelper.save(bpartner);
		//
		final I_C_BPartner_Location bpLocation = createBPLocation(bpartner);
		orgInfo.setOrgBP_Location(bpLocation);
		InterfaceWrapperHelper.save(orgInfo);

		return org;
	}

	public I_S_ResourceType createResourceType(final String name)
	{
		final I_S_ResourceType resourceType = InterfaceWrapperHelper.newInstance(I_S_ResourceType.class, contextProvider);
		resourceType.setIsActive(true);
		resourceType.setName(name);
		resourceType.setValue(name);
		resourceType.setIsDateSlot(false);
		resourceType.setIsTimeSlot(false);
		InterfaceWrapperHelper.save(resourceType);

		return resourceType;
	}

	public I_S_Resource createResource(String name, String manufacturingResourceType, final I_S_ResourceType resourceType)
	{
		final I_S_Resource resource = InterfaceWrapperHelper.newInstance(I_S_Resource.class, contextProvider);
		resource.setIsManufacturingResource(true);
		resource.setManufacturingResourceType(manufacturingResourceType);
		resource.setIsAvailable(true);
		resource.setName(name);
		resource.setValue(name);
		resource.setPlanningHorizon(365);
		resource.setS_ResourceType_ID(resourceType.getS_ResourceType_ID());
		InterfaceWrapperHelper.save(resource);

		return resource;
	}

	public I_M_Warehouse createWarehouse(final String name, final I_AD_Org org)
	{
		final I_S_Resource plant = null;
		return createWarehouse(name, org, plant);
	}

	public I_M_Warehouse createWarehouse(final String name, final I_AD_Org org, final I_S_Resource plant)
	{
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.newInstance(I_M_Warehouse.class, contextProvider);
		warehouse.setAD_Org_ID(org.getAD_Org_ID());
		warehouse.setValue(name);
		warehouse.setName(name);
		warehouse.setIsInTransit(false);
		warehouse.setPP_Plant(plant);
		InterfaceWrapperHelper.save(warehouse);

		return warehouse;
	}

	public I_C_UOM createUOM(final String name)
	{
		return createUOM(name, 2);
	}

	public I_C_UOM createUOM(final String name, final int stdPrecision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class, contextProvider);
		uom.setName(name);
		uom.setUOMSymbol(name);
		uom.setX12DE355(name);
		uom.setStdPrecision(stdPrecision);
		uom.setCostingPrecision(0);
		InterfaceWrapperHelper.save(uom);
		return uom;
	}

	public I_C_UOM_Conversion createUOMConversion(
			final int productId,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final BigDecimal multiplyRate,
			final BigDecimal divideRate)
	{
		final I_C_UOM_Conversion conversion = InterfaceWrapperHelper.create(Env.getCtx(), I_C_UOM_Conversion.class, ITrx.TRXNAME_None);

		conversion.setM_Product_ID(productId);
		conversion.setC_UOM_ID(uomFrom.getC_UOM_ID());
		conversion.setC_UOM_To_ID(uomTo.getC_UOM_ID());
		conversion.setMultiplyRate(multiplyRate);
		conversion.setDivideRate(divideRate);

		InterfaceWrapperHelper.save(conversion, ITrx.TRXNAME_None);

		return conversion;
	}

	public I_C_UOM_Conversion createUOMConversion(
			final I_M_Product product,
			final I_C_UOM uomFrom,
			final I_C_UOM uomTo,
			final String multiplyRateStr,
			final String divideRateStr)
	{
		final int productId = product == null ? -1 : product.getM_Product_ID();
		final BigDecimal multiplyRate = new BigDecimal(multiplyRateStr);
		final BigDecimal divideRate = new BigDecimal(divideRateStr);
		return createUOMConversion(productId, uomFrom, uomTo, multiplyRate, divideRate);
	}

	public I_M_Product createProduct(final String name)
	{
		return createProduct(name, uomEach);
	}

	public I_M_Product createProduct(final String name, final I_C_UOM uom)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, contextProvider);
		product.setValue(name);
		product.setName(name);
		product.setM_Product_Category_ID(productCategoryDefault == null ? -1 : productCategoryDefault.getM_Product_Category_ID());
		product.setC_UOM_ID(uom.getC_UOM_ID());
		product.setLowLevel(0);
		InterfaceWrapperHelper.save(product);

		return product;
	}

	public I_M_Product_Category createProductCategory(final String name)
	{
		final I_M_Product_Category productCategory = InterfaceWrapperHelper.newInstance(I_M_Product_Category.class, contextProvider);
		productCategory.setValue(name);
		productCategory.setName(name);
		InterfaceWrapperHelper.save(productCategory);

		return productCategory;
	}

	/**
	 * Creates a dummy MRP firm demand.
	 *
	 * @param product
	 * @param qty
	 * @param date
	 * @param plant
	 * @param warehouse
	 * @return
	 * @see #createMRPDemand(I_M_Product, BigDecimal, Date, I_S_Resource, I_M_Warehouse, I_C_BPartner)
	 */
	public I_PP_MRP createMRPDemand(
			final I_M_Product product, final BigDecimal qty,
			final Date date,
			final I_S_Resource plant,
			final I_M_Warehouse warehouse)
	{
		final I_C_BPartner bpartner = null;
		return createMRPDemand(product, qty, date, plant, warehouse, bpartner);
	}

	/**
	 * Creates a dummy MRP firm demand.
	 *
	 * NOTE:
	 * <ul>
	 * <li>DocStatus will be Completed
	 * <li>OrderType will be SalesOrder
	 * </ul>
	 *
	 * @param product
	 * @param qty
	 * @param date
	 * @param plant
	 * @param warehouse
	 * @return demand MRP record
	 */
	public I_PP_MRP createMRPDemand(
			final I_M_Product product, final BigDecimal qty,
			final Date date,
			final I_S_Resource plant,
			final I_M_Warehouse warehouse,
			final I_C_BPartner bpartner)
	{
		final I_PP_MRP mrp = InterfaceWrapperHelper.newInstance(I_PP_MRP.class, contextProvider);
		mrp.setAD_Org_ID(warehouse.getAD_Org_ID());
		mrp.setS_Resource(plant);
		mrp.setM_Warehouse(warehouse);
		mrp.setC_BPartner(bpartner);
		//
		final Timestamp dateTS = TimeUtil.asTimestamp(date);
		mrp.setDatePromised(dateTS);
		mrp.setDateStartSchedule(dateTS);
		mrp.setDateFinishSchedule(dateTS);
		//
		mrp.setTypeMRP(X_PP_MRP.TYPEMRP_Demand);
		mrp.setDocStatus(X_PP_MRP.DOCSTATUS_Completed); // Firm Demand
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_SalesOrder); // Sales Order
		mrp.setIsAvailable(true);
		//
		mrp.setM_Product(product);
		Services.get(IMRPBL.class).setQty(mrp, qty, qty, product.getC_UOM());
		//
		// mrp.setC_BPartner(C_BPartner);

		InterfaceWrapperHelper.save(mrp);

		return mrp;
	}

	public void createMRPMessage(final String code)
	{
		final I_AD_Message message = InterfaceWrapperHelper.newInstance(I_AD_Message.class, contextProvider);
		InterfaceWrapperHelper.setValue(message, "AD_Client_ID", 0);
		message.setAD_Org_ID(0);
		message.setValue(code);
		message.setMsgText(code);
		InterfaceWrapperHelper.save(message);
	}

	public PPProductPlanningBuilder newProductPlanning()
	{
		return new PPProductPlanningBuilder()
				.setContext(contextProvider);
	}

	public I_M_Shipper createShipper(final String name)
	{
		final I_C_BPartner bpartner = createBPartner(name);
		return createShipper(bpartner);
	}

	public I_M_Shipper createShipper(final I_C_BPartner bpartner)
	{
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class, contextProvider);
		shipper.setName(bpartner.getName());
		shipper.setC_BPartner_ID(bpartner.getC_BPartner_ID());

		InterfaceWrapperHelper.save(shipper);
		return shipper;
	}

	public I_C_BP_Group createBPGroup(final String name)
	{
		final I_C_BP_Group bpGroup = InterfaceWrapperHelper.newInstance(I_C_BP_Group.class, contextProvider);
		bpGroup.setValue(name);
		bpGroup.setName(name);
		InterfaceWrapperHelper.save(bpGroup);
		return bpGroup;
	}

	public I_C_BPartner createBPartner(final String name)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class, contextProvider);
		bpartner.setValue(name);
		bpartner.setName(name);
		bpartner.setIsCustomer(true);
		bpartner.setIsVendor(true);
		bpartner.setAD_OrgBP_ID(-1);
		bpartner.setC_BP_Group_ID(bpGroupDefault == null ? -1 : bpGroupDefault.getC_BP_Group_ID());

		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	public I_C_BPartner_Location createBPLocation(final I_C_BPartner bpartner)
	{
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsBillTo(true);
		bpLocation.setIsShipToDefault(true);
		bpLocation.setIsShipTo(true);
		InterfaceWrapperHelper.save(bpLocation);
		return bpLocation;
	}

	public DDNetworkBuilder newDDNetwork()
	{
		return new DDNetworkBuilder()
				.setContext(contextProvider)
				.shipper(shipperDefault);
	}

	public I_C_DocType createDocType(final String docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		docType.setAD_Org_ID(adOrg01.getAD_Org_ID());
		docType.setDocBaseType(docBaseType);
		docType.setName(docBaseType);

		InterfaceWrapperHelper.save(docType);
		return docType;
	}

	public void dumpMRPRecords()
	{
		MRPTracer.dumpMRPRecords();
	}

	public void dumpMRPRecords(final String message)
	{
		MRPTracer.dumpMRPRecords(message);
	}

	public MRPExpectation<Object> newMRPExpectation()
	{
		final MRPExpectation<Object> e = MRPExpectation.newMRPExpectation();
		e.setContext(this.contextProvider);
		return e;
	}

	public ProductBOMBuilder newProductBOM()
	{
		return new ProductBOMBuilder()
				.setContext(contextProvider);
	}

	public I_AD_Workflow createWorkflow(final String name)
	{
		final I_AD_Workflow wf = InterfaceWrapperHelper.newInstance(I_AD_Workflow.class, contextProvider);
		wf.setValue(name);
		wf.setName(name);
		wf.setWorkflowType(X_AD_Workflow.WORKFLOWTYPE_Manufacturing);
		InterfaceWrapperHelper.save(wf);
		return wf;
	}

	public I_AD_WF_Node createWorkflowNode(final I_AD_Workflow wf, final String nodeName, final boolean startNode)
	{
		final I_AD_WF_Node node = InterfaceWrapperHelper.newInstance(I_AD_WF_Node.class, contextProvider);
		node.setAD_Workflow_ID(wf.getAD_Workflow_ID());
		node.setValue(nodeName);
		node.setName(nodeName);

		InterfaceWrapperHelper.save(node);

		if (startNode)
		{
			wf.setAD_WF_Node_ID(node.getAD_WF_Node_ID());
			InterfaceWrapperHelper.save(wf);
		}

		return node;
	}

	public MRPTestRun newMRPTestRun()
	{
		return new MRPTestRun(this);
	}

	/**
	 * Execute MRP on all planning segments
	 *
	 * NOTE: this method will also check if all demands where marked as not available.
	 */
	public void runMRP()
	{
		newMRPTestRun()
				.run();
	}

	public void runMRP(final IMRPContext mrpContext)
	{
		newMRPTestRun()
				.setMRPContext(mrpContext)
				.run();
	}

	public final Logger getMRPLogger()
	{
		return LogManager.getLogger(IMRPContext.LOGGERNAME);
	}

	public void completeAllMRPDocuments()
	{
		completeMRPDocuments(I_PP_Order.class);
		completeMRPDocuments(I_DD_Order.class);
	}

	/**
	 *
	 * @param documentClass
	 * @return completed documents
	 */
	public <T> List<T> completeMRPDocuments(final Class<T> documentClass)
	{
		final List<T> documentModels = queryBL.createQueryBuilder(documentClass, contextProvider)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Completed)
				.create()
				.list();
		final List<T> documentModelsCompleted = new ArrayList<>(documentModels.size());
		for (final T documentModel : documentModels)
		{
			final DocAction document = docActionBL.getDocAction(documentModel);
			docActionBL.processEx(document, DocAction.ACTION_Complete, DocAction.STATUS_Completed);

			documentModelsCompleted.add(documentModel);
		}

		return documentModelsCompleted;
	}

	/**
	 * Simulate receiving from an manufacturing order
	 *
	 * @param ppOrder
	 * @param qtyToReceive
	 */
	public void receiveFromPPOrder(final I_PP_Order ppOrder, final BigDecimal qtyToReceive)
	{
		//
		// Receive from MO: i.e. increase QtyDelivered
		final BigDecimal qtyDeliveredOld = ppOrder.getQtyDelivered();
		final BigDecimal qtyDelivered = qtyDeliveredOld.add(qtyToReceive);
		ppOrder.setQtyDelivered(qtyDelivered);
		InterfaceWrapperHelper.save(ppOrder);

		//
		// Add received qty to QtyOnHand
		final I_M_Warehouse warehouse = ppOrder.getM_Warehouse();
		final I_M_Product product = ppOrder.getM_Product();
		mrpDAO.addQtyOnHand(warehouse, product, qtyToReceive);
	}

	public I_DD_Order retrieveSingleForwardDDOrder(final I_PP_Order ppOrder)
	{
		return Services.get(IDDOrderDAO.class)
				.retrieveForwardDDOrderLinesQuery(ppOrder)
				.andCollect(I_DD_OrderLine.COLUMN_DD_Order_ID)
				.create()
				.firstOnly(I_DD_Order.class);
	}

	public final void assumeMRP_POQ_Enabled()
	{
		Assume.assumeFalse("Skip this test because MRP_POQ_Disabled", LiberoConstants.isMRP_POQ_Disabled());
	}
}
