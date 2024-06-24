package de.metas.handlingunits;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.common.util.time.SystemTime;
import de.metas.dimension.model.I_DIM_Dimension_Spec;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleRepository;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.InOutLineDimensionFactory;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.impl.DocumentLocationBL;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.handlingunits.allocation.IHUContextProcessor;
import de.metas.handlingunits.allocation.IHUProducerAllocationDestination;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.ILUTUProducerAllocationDestination;
import de.metas.handlingunits.allocation.impl.AbstractAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.AbstractProducerDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.GenericAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.impl.HUProducerDestination;
import de.metas.handlingunits.allocation.impl.MTransactionAllocationSourceDestination;
import de.metas.handlingunits.allocation.strategy.AllocationStrategyFactory;
import de.metas.handlingunits.allocation.strategy.AllocationStrategySupportingServicesFacade;
import de.metas.handlingunits.allocation.transfer.IHUJoinBL;
import de.metas.handlingunits.allocation.transfer.IHUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.ITUMergeBuilder;
import de.metas.handlingunits.allocation.transfer.impl.HUSplitBuilder;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.allocation.transfer.impl.TUMergeBuilder;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.impl.PlainAttributeValue;
import de.metas.handlingunits.attribute.propagation.impl.HUAttributePropagationContext;
import de.metas.handlingunits.attribute.propagation.impl.NoPropagationHUAttributePropagator;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.impl.ListAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.attribute.strategy.impl.CopyAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.CopyHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.LinearDistributionAttributeSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullAggregationStrategy;
import de.metas.handlingunits.attribute.strategy.impl.NullSplitterStrategy;
import de.metas.handlingunits.attribute.strategy.impl.RedistributeQtyHUAttributeTransferStrategy;
import de.metas.handlingunits.attribute.strategy.impl.SumAggregationStrategy;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.impl.ShipperTransportationRepository;
import de.metas.handlingunits.model.I_DD_NetworkDistribution;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.handlingunits.spi.IHUPackingMaterialCollectorSource;
import de.metas.handlingunits.storage.impl.PlainProductStorage;
import de.metas.handlingunits.test.HUListAssertsBuilder;
import de.metas.handlingunits.test.misc.builders.HUPIAttributeBuilder;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.inoutcandidate.modelvalidator.InOutCandidateValidator;
import de.metas.inoutcandidate.modelvalidator.ReceiptScheduleValidator;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.invoicecandidate.document.dimension.InvoiceCandidateDimensionFactory;
import de.metas.materialtransaction.MTransactionUtil;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.mm.attributes.spi.impl.WeightGrossAttributeValueCallout;
import org.adempiere.mm.attributes.spi.impl.WeightNetAttributeValueCallout;
import org.adempiere.mm.attributes.spi.impl.WeightTareAdjustAttributeValueCallout;
import org.adempiere.mm.attributes.spi.impl.WeightTareAttributeValueCallout;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.assertj.core.api.Assertions;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.I_M_Transaction;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_Test;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.util.DDNetworkBuilder;
import org.eevolution.util.ProductBOMBuilder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import static de.metas.business.BusinessTestHelper.createBPartner;
import static de.metas.business.BusinessTestHelper.createProduct;
import static de.metas.business.BusinessTestHelper.createUOMConversion;
import static de.metas.business.BusinessTestHelper.createUomEach;
import static de.metas.business.BusinessTestHelper.createUomKg;
import static de.metas.business.BusinessTestHelper.createUomPCE;
import static de.metas.business.BusinessTestHelper.createWarehouse;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * This class sets up basic master data like attributes and HU-items.
 * It also has methods like {@link #createHU_PI_Item_IncludedHU(I_M_HU_PI, I_M_HU_PI, BigDecimal)} that can be called from your tests, to set up complex packing instructions.
 */
public class HUTestHelper
{
	/**
	 * Creates a new instance <b>and also calls {@link AdempiereTestHelper#init()}</b>
	 */
	public static HUTestHelper newInstanceOutOfTrx()
	{
		return new HUTestHelper()
		{
			@Override
			protected String createAndStartTransaction()
			{
				return ITrx.TRXNAME_None; // no transaction by default
			}
		};
	}

	//
	// Initialization flags
	private boolean initialized = false;
	private boolean initAdempiere = true;

	public I_AD_Client adClient;
	public I_AD_Role adRole;

	private static final String NAME_CountryMadeIn_Attribute = "Made In";
	private static final String NAME_Volume_Attribute = "Volume";
	private static final String NAME_FragileSticker_Attribute = "Fragile";

	private static final String NAME_M_Material_Tracking_ID_Attribute = "M_Material_Tracking_ID";
	private static final String NAME_AgeOffset = "AgeOffset";
	private static final String NAME_Age = "Age";

	public static final String NAME_Palet_Product = "Palet";
	public static final String NAME_IFCO_Product = "IFCO";
	public static final String NAME_Bag_Product = "Bag";
	public static final String NAME_Paloxe_Product = "Paloxe";

	private static final String NAME_Default_Warehouse = "DefaultWarehouse";
	private static final String NAME_Issue_Warehouse = "IssueWarehouse";

	public IHUTrxBL trxBL;

	public I_C_UOM uomKg;
	public UomId uomKgId;

	public I_C_UOM uomEach;
	public UomId uomEachId;

	public I_C_UOM uomPCE;

	/**
	 * Value: Palet
	 */
	public I_M_Product pPalet; // palets are our packing material
	public final BigDecimal pPalet_Weight_25kg = BigDecimal.valueOf(25);
	public I_M_HU_PackingMaterial pmPalet; // palets are our packing material

	/**
	 * Value: IFCO
	 */
	public I_M_Product pIFCO; // IFCOs are another kind of packing material
	public final BigDecimal pIFCO_Weight_1kg = BigDecimal.ONE;
	public I_M_HU_PackingMaterial pmIFCO; // IFCO Packing Material

	/**
	 * Value: Blister
	 */
	public I_M_Product pBag; // Bags are another kind of packing material
	public final BigDecimal pBag_Weight_0_5Kg = BigDecimal.valueOf(0.5);
	public I_M_HU_PackingMaterial pmBag; // Bag Packing Material

	/**
	 * Value: Paloxe
	 */
	public I_M_Product pPaloxe; // Paloxes are another kind of packing material
	public final BigDecimal pPaloxe_Weight_75kg = BigDecimal.valueOf(75);
	public I_M_HU_PackingMaterial pmPaloxe; // Paloxe Packing Material

	/**
	 * Value: Tomato
	 */
	public I_M_Product pTomato;
	public ProductId pTomatoProductId;
	public static final String NAME_Tomato_Product = "Tomato";

	public I_M_Product pSalad;
	public ProductId pSaladProductId;
	public static final String NAME_Salad_Product = "Salad";

	/**
	 * No-HU PI
	 */
	public I_M_HU_PI huDefNone;
	/**
	 * No-HU PI Item
	 */
	public I_M_HU_PI_Item huDefItemNone;
	/**
	 * No-HU PI Item Product
	 */
	public I_M_HU_PI_Item_Product huDefItemProductNone;

	/**
	 * Virtual M_HU_PI
	 */
	public I_M_HU_PI huDefVirtual;
	/**
	 * Virtual M_HU_PI Item
	 */
	public I_M_HU_PI_Item huDefItemVirtual;
	/**
	 * Virtual M_HU_PI Item Product
	 */
	public I_M_HU_PI_Item_Product huDefItemProductVirtual;
	public I_M_HU_PI_Attribute huDefVirtual_Attr_CostPrice;

	public static final String COUNTRYMADEIN_DE = "DE";
	public static final String COUNTRYMADEIN_FR = "FR";
	public static final String COUNTRYMADEIN_RO = "RO";

	public static final String QUALITYNOTICE_Test1 = "QUALITYNOTICE_Test1";
	public static final String QUALITYNOTICE_Test2 = "QUALITYNOTICE_Test2";
	public static final String QUALITYNOTICE_Test3 = "QUALITYNOTICE_Test3";

	public I_M_Attribute attr_CountryMadeIn;

	public I_M_Attribute attr_Volume;
	public I_M_Attribute attr_FragileSticker;

	/**
	 * Gross Weight. Calculation in callout: Gross = Net + Tare + TareAdjust
	 */
	public I_M_Attribute attr_WeightGross;
	/**
	 * Net Weight. Calculation in callout: Net = Gross - Tare - TareAdjust
	 */
	public I_M_Attribute attr_WeightNet;
	/**
	 * Tare Weight. Calculation in callout (seed only): Tare + TareChild x ChildrenCount (i.e LUTare = LUTarePackingMaterial + TUTarePackingMaterial x TUCountInLU)
	 */
	public I_M_Attribute attr_WeightTare;
	/**
	 * Tare Weight Adjust. Used in calculating Gross / Net depending on weight fluctuations from the defined normal weight of the packing material.
	 */
	public I_M_Attribute attr_WeightTareAdjust;

	public I_M_Attribute attr_CostPrice;

	public I_M_Attribute attr_LotNumberDate;

	// #653
	public I_M_Attribute attr_LotNumber;

	public I_M_Attribute attr_BestBeforeDate;

	public I_M_Attribute attr_SerialNo;

	/**
	 * Mandatory in receipts
	 */
	public I_M_Attribute attr_QualityDiscountPercent;
	/**
	 * Mandatory in receipts
	 */
	public I_M_Attribute attr_QualityNotice;
	/**
	 * Mandatory in receipts
	 */
	public I_M_Attribute attr_SubProducerBPartner;

	/**
	 * Mandatory for integration with the de.metas.materialtracking module
	 */
	public I_M_Attribute attr_M_Material_Tracking_ID;

	public I_M_Attribute attr_PurchaseOrderLine;
	public I_M_Attribute attr_ReceiptInOutLine;

	public I_M_Attribute attr_Age;

	public I_M_Attribute attr_AgeOffset;

	//
	// Default warehouses
	public I_M_Warehouse defaultWarehouse;
	public I_M_Warehouse issueWarehouse;
	// Empties:
	private I_M_Warehouse warehouse_Empties;
	private DDNetworkBuilder emptiesDDNetworkBuilder;

	public Properties ctx;
	public String trxName;
	private ZonedDateTime today;

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
	};
	private IMutableHUContext huContext;

	/**
	 * Invokes {@link #HUTestHelper(boolean)} with init=<code>true</code>.
	 */
	public HUTestHelper()
	{
		this(true);
	}

	/**
	 * @param init if <code>true</code>, the the constructor directly calls {@link #init()}.
	 */
	public HUTestHelper(final boolean init)
	{
		if (init)
		{
			init();
		}
	}

	public HUTestHelper setInitAdempiere(final boolean initAdempiere)
	{
		Check.assume(!initialized, "helper not initialized");
		this.initAdempiere = initAdempiere;
		return this;
	}

	/**
	 * Returns this instance, initialized.
	 * Note: final, because its called by a constructor.
	 */
	public final HUTestHelper init()
	{
		if (initialized)
		{
			return this;
		}

		if (initAdempiere)
		{
			AdempiereTestHelper.get().init();
		}

		beforeRegisteringServices();

		SpringContextHolder.registerJUnitBean(new AllocationStrategyFactory(new AllocationStrategySupportingServicesFacade()));
		SpringContextHolder.registerJUnitBean(new ShipperTransportationRepository());
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		SpringContextHolder.registerJUnitBean(IBPartnerBL.class, bpartnerBL);
		SpringContextHolder.registerJUnitBean(IDocumentLocationBL.class, new DocumentLocationBL(bpartnerBL));

		final List<DimensionFactory<?>> dimensionFactories = new ArrayList<>();
		dimensionFactories.add(new OrderLineDimensionFactory());
		dimensionFactories.add(new ReceiptScheduleDimensionFactory());
		dimensionFactories.add(new InvoiceCandidateDimensionFactory());
		dimensionFactories.add(new InOutLineDimensionFactory());

		SpringContextHolder.registerJUnitBean(new DimensionService(dimensionFactories));

		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);
		
		ctx = Env.getCtx();
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxName = createAndStartTransaction();
		trxManager.setThreadInheritedTrxName(trxName);

		//
		// Setup context: #AD_Client_ID
		adClient = InterfaceWrapperHelper.create(ctx, I_AD_Client.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(adClient);
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, adClient.getAD_Client_ID());

		//
		// Setup context: #AD_Role_ID
		adRole = InterfaceWrapperHelper.create(ctx, I_AD_Role.class, ITrx.TRXNAME_None);
		adRole.setName("TestRole");
		InterfaceWrapperHelper.save(adRole);
		Env.setContext(ctx, Env.CTXNAME_AD_Role_ID, adRole.getAD_Role_ID());

		//
		// Setup context: #Date
		today = LocalDate.of(2013, Month.NOVEMBER, 1).atStartOfDay(SystemTime.zoneId());
		Env.setContext(ctx, Env.CTXNAME_Date, TimeUtil.asDate(today));
		
		//
		// Setup module interceptors
		setupModuleInterceptors_HU();
		setupModuleInterceptors_InOutSchedule();

		//
		// Common used services
		trxBL = Services.get(IHUTrxBL.class);

		setupMasterData();

		huContext = createInitialHUContext(contextProvider);

		initialized = true;

		afterInitialized();

		return this;
	}

	public void beforeRegisteringServices()
	{
	}

	protected void afterInitialized()
	{
		// nothing here
	}

	/**
	 * Setup module interceptors: "de.metas.handlingunits" module
	 */
	protected void setupModuleInterceptors_HU()
	{
		setupModuleInterceptors_HU_Minimal();

		// NOTE: atm we cannot just register the model validator because there are some tests which are saving partial data
		// and we get NPE on many model validators
		// Suggestion: we shall register the model validator only for "Integration Tests" only
		// setupModuleInterceptors_HU_Full();
	}

	/**
	 * Setup module interceptors: "de.metas.handlingunits" module - Minimal (used by almost all tests)
	 */
	protected final void setupModuleInterceptors_HU_Minimal()
	{
		//
		// Handling units model validator
		newHandlingUnitsModelInterceptor().registerFactories();

		final IModelInterceptorRegistry modelInterceptorRegistry = Services.get(IModelInterceptorRegistry.class);

		// We need to manually register M_Movement interceptor, else we won't get the packing material lines on movements
		modelInterceptorRegistry
				.addModelInterceptor(de.metas.handlingunits.model.validator.M_Movement.instance);
	}

	/**
	 * Setup module interceptors: "de.metas.handlingunits" module - FULL (interceptors, factories, etc), like in production (used by some integration tests).
	 *
	 * <b>Important:</b> if you do the full monty with interceptors, then you also need to annotate the respective test class like this:
	 *
	 * <pre>
	 * &#64;RunWith(SpringRunner.class)
	 * &#64;SpringBootTest(classes= HandlingUnitsConfiguration.class)
	 * </pre>
	 * <p>
	 * Otherwise, tests will probably fail due to spring application context.
	 */
	protected final void setupModuleInterceptors_HU_Full()
	{
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(newHandlingUnitsModelInterceptor());
	}

	private de.metas.handlingunits.model.validator.Main newHandlingUnitsModelInterceptor()
	{
		final DDOrderLowLevelDAO ddOrderLowLevelDAO = new DDOrderLowLevelDAO();
		final HUReservationService huReservationService = new HUReservationService(new HUReservationRepository());
		final DDOrderMoveScheduleService ddOrderMoveScheduleService = new DDOrderMoveScheduleService(
				ddOrderLowLevelDAO,
				new DDOrderMoveScheduleRepository(),
				huReservationService);
		final DDOrderLowLevelService ddOrderLowLevelService = new DDOrderLowLevelService(ddOrderLowLevelDAO);
		final DDOrderService ddOrderService = new DDOrderService(ddOrderLowLevelDAO, ddOrderLowLevelService, ddOrderMoveScheduleService);
		return new de.metas.handlingunits.model.validator.Main(
				ddOrderMoveScheduleService,
				ddOrderService,
				new PickingBOMService());
	}

	/**
	 * Setup module interceptors: Receipt Schedule/Shipment Schedule
	 */
	protected final void setupModuleInterceptors_InOutSchedule()
	{
		//
		// Mimic ModelValidator behaviour
		Services.get(IModelInterceptorRegistry.class)
				.addModelInterceptor(ReceiptScheduleValidator.instance);
		InOutCandidateValidator.registerSSAggregationKeyDependencies();
	}

	/**
	 * Creates and starts database transaction
	 *
	 * @return trxName
	 */
	@Nullable
	protected String createAndStartTransaction()
	{
		final String trxNamePrefix = "HUTest";
		return createAndStartTransaction(trxNamePrefix);
	}

	protected final String createAndStartTransaction(final String trxNamePrefix)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.createTrxName(trxNamePrefix);
	}

	protected IMutableHUContext createInitialHUContext(final IContextAware contextProvider)
	{
		return handlingUnitsBL().createMutableHUContext(contextProvider);
	}

	public final void commitThreadInheritedTrx(final IHUContext huContext)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final String trxName = huContext.getTrxName();
		if (trxManager.isNull(trxName))
		{
			// nothing to do
			return;
		}

		try
		{
			final ITrx trx = trxManager.getTrx(trxName);
			trx.commit(true);
		}
		catch (final SQLException e)
		{
			throw new DBException("Error while commiting trx", e);
		}
	}

	/**
	 * Sets up all the nice fields this helper offers. Called by the {@link #init()} method.
	 */
	protected void setupMasterData()
	{
		uomKg = createUomKg();
		uomKgId = UomId.ofRepoId(uomKg.getC_UOM_ID());
		uomEach = createUomEach();
		uomEachId = UomId.ofRepoId(uomEach.getC_UOM_ID());
		uomPCE = createUomPCE();

		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();

		attr_CountryMadeIn = attributesTestHelper.createM_Attribute(HUTestHelper.NAME_CountryMadeIn_Attribute, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		createAttributeListValues(attr_CountryMadeIn,
				HUTestHelper.COUNTRYMADEIN_RO,
				HUTestHelper.COUNTRYMADEIN_DE,
				HUTestHelper.COUNTRYMADEIN_FR);

		attr_Volume = attributesTestHelper.createM_Attribute(HUTestHelper.NAME_Volume_Attribute, X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
		attr_FragileSticker = attributesTestHelper.createM_Attribute(HUTestHelper.NAME_FragileSticker_Attribute, X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, false);

		attr_WeightGross = attributesTestHelper.createM_Attribute(Weightables.ATTR_WeightGross.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, WeightGrossAttributeValueCallout.class, uomKg, true);
		attr_WeightNet = attributesTestHelper.createM_Attribute(Weightables.ATTR_WeightNet.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, WeightNetAttributeValueCallout.class, uomKg, true);
		attr_WeightTare = attributesTestHelper.createM_Attribute(Weightables.ATTR_WeightTare.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, WeightTareAttributeValueCallout.class, uomKg, true);
		attr_WeightTareAdjust = attributesTestHelper.createM_Attribute(Weightables.ATTR_WeightTareAdjust.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, WeightTareAdjustAttributeValueCallout.class, uomKg, true);

		attr_CostPrice = attributesTestHelper.createM_Attribute(HUAttributeConstants.ATTR_CostPrice.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, null, null, true);

		attr_QualityDiscountPercent = attributesTestHelper.createM_Attribute(HUAttributeConstants.ATTR_QualityDiscountPercent_Value, X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
		attr_QualityNotice = attributesTestHelper.createM_Attribute(HUAttributeConstants.ATTR_QualityNotice_Value, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		{
			//
			// Create values
			createAttributeListValue(attr_QualityNotice, QUALITYNOTICE_Test1, QUALITYNOTICE_Test1);
			createAttributeListValue(attr_QualityNotice, QUALITYNOTICE_Test2, QUALITYNOTICE_Test2);
			createAttributeListValue(attr_QualityNotice, QUALITYNOTICE_Test3, QUALITYNOTICE_Test3);
		}
		attr_SubProducerBPartner = attributesTestHelper.createM_Attribute(AttributeConstants.ATTR_SubProducerBPartner_Value.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

		attr_M_Material_Tracking_ID = attributesTestHelper.createM_Attribute(NAME_M_Material_Tracking_ID_Attribute, X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
		attr_AgeOffset = attributesTestHelper.createM_Attribute(NAME_AgeOffset, X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
		attr_Age = attributesTestHelper.createM_Attribute(NAME_Age, X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		createAttributeListValue(attr_Age, "1", "1");
		createAttributeListValue(attr_Age, "2", "2");
		createAttributeListValue(attr_Age, "3", "3");
		createAttributeListValue(attr_Age, "4", "4");
		createAttributeListValue(attr_Age, "5", "5");
		createAttributeListValue(attr_Age, "6", "6");

		attr_LotNumberDate = attributesTestHelper.createM_Attribute(HUAttributeConstants.ATTR_LotNumberDate.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Date, true);
		attr_LotNumber = attributesTestHelper.createM_Attribute(AttributeConstants.ATTR_LotNumber.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

		attr_BestBeforeDate = attributesTestHelper.createM_Attribute(AttributeConstants.ATTR_BestBeforeDate.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Date, true);

		attr_SerialNo = attributesTestHelper.createM_Attribute(AttributeConstants.ATTR_SerialNo.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

		attr_PurchaseOrderLine = attributesTestHelper.createM_Attribute(HUAttributeConstants.ATTR_PurchaseOrderLine_ID.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
		attr_ReceiptInOutLine = attributesTestHelper.createM_Attribute(HUAttributeConstants.ATTR_ReceiptInOutLine_ID.getCode(), X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);

		// FIXME: this is a workaround because we are not handling the UOM conversions in our HU tests
		createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(uomEach.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomKg.getC_UOM_ID()))
				.fromToMultiplier(BigDecimal.ONE)
				.build());

		// Create 1-to-1 conversion between "Each" and PCE / Stk
		createUOMConversion(CreateUOMConversionRequest.builder()
				.fromUomId(UomId.ofRepoId(uomEach.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomPCE.getC_UOM_ID()))
				.fromToMultiplier(BigDecimal.ONE)
				.build());

		//
		// Create and configure Pallete
		pPalet = createProduct(HUTestHelper.NAME_Palet_Product, uomEach, pPalet_Weight_25kg); // Pallets are our packing material
		pmPalet = createPackingMaterial("Palet-PM", pPalet);

		//
		// Create and configure IFCO
		pIFCO = createProduct(HUTestHelper.NAME_IFCO_Product, uomEach, pIFCO_Weight_1kg); // IFCOs are another kind of packing material
		pmIFCO = createPackingMaterial("IFCO-PM", pIFCO);

		//
		// Create and configure Bag
		pBag = createProduct(HUTestHelper.NAME_Bag_Product, uomEach, pBag_Weight_0_5Kg); // Bags are another kind of packing material
		pmBag = createPackingMaterial("Bag-PM", pBag);

		//
		// Create and configure Paloxe
		pPaloxe = createProduct(HUTestHelper.NAME_Paloxe_Product, uomEach, pPaloxe_Weight_75kg); // Paloxes are another kind of packing material
		pmPaloxe = createPackingMaterial("Paloxe-PM", pPaloxe);

		pTomato = createProduct(HUTestHelper.NAME_Tomato_Product, uomEach);
		pTomatoProductId = ProductId.ofRepoId(pTomato.getM_Product_ID());

		pSalad = createProduct(HUTestHelper.NAME_Salad_Product, uomEach);
		pSaladProductId = ProductId.ofRepoId(pSalad.getM_Product_ID());

		//
		// No-PI
		huDefNone = createTemplatePI();

		//
		// Virtual PI
		huDefVirtual = createVirtualPI();

		setupNoPIAttributes();
		setupVirtualPIAttributes();
		setupEmptiesWarehouse();

		defaultWarehouse = createWarehouse(NAME_Default_Warehouse, false); // issueWarehouse
		issueWarehouse = createWarehouse(NAME_Issue_Warehouse, true);
	}

	protected void customInit()
	{
		// nothing
	}

	public IHandlingUnitsBL handlingUnitsBL() {return Services.get(IHandlingUnitsBL.class);}

	public IHUPIItemProductBL huPIItemProductBL() {return Services.get(IHUPIItemProductBL.class);}

	private I_M_HU_PI createTemplatePI()
	{
		final I_M_HU_PI huDefNone = InterfaceWrapperHelper.create(ctx, I_M_HU_PI.class, ITrx.TRXNAME_None);
		huDefNone.setName("NoPI");
		huDefNone.setM_HU_PI_ID(HuPackingInstructionsId.TEMPLATE.getRepoId());
		InterfaceWrapperHelper.save(huDefNone);

		final String huUnitType = null; // any
		createVersion(huDefNone, true, huUnitType, HuPackingInstructionsVersionId.TEMPLATE);

		huDefItemNone = createHU_PI_Item_Material(huDefNone, HuPackingInstructionsItemId.TEMPLATE_MATERIAL_ITEM);
		huDefItemProductNone = assignProductAny(huDefItemNone, HUPIItemProductId.TEMPLATE_HU);

		return huDefNone;
	}

	private I_M_HU_PI createVirtualPI()
	{
		final I_M_HU_PI huDefVirtual = InterfaceWrapperHelper.create(ctx, I_M_HU_PI.class, ITrx.TRXNAME_None);
		huDefVirtual.setName("VirtualPI");
		huDefVirtual.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
		InterfaceWrapperHelper.save(huDefVirtual);

		createVersion(huDefVirtual,
				true, // isCurrent
				X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI, HuPackingInstructionsVersionId.VIRTUAL);

		huDefItemVirtual = createHU_PI_Item_Material(huDefVirtual, HuPackingInstructionsItemId.VIRTUAL);
		huDefItemProductVirtual = assignProductAny(huDefItemVirtual, HUPIItemProductId.VIRTUAL_HU);

		return huDefVirtual;
	}

	/**
	 * Create NoPI Attributes (i.e. template attributes)
	 */
	private void setupNoPIAttributes()
	{
		int piAttrSeqNo = 10;

		//
		// Weight Gross
		{
			final I_M_HU_PI_Attribute piAttr_WeightGross = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_WeightGross)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			piAttr_WeightGross.setIsReadOnly(false);
			piAttr_WeightGross.setSeqNo(piAttrSeqNo);
			piAttr_WeightGross.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_WeightGross);
			piAttrSeqNo += 10;
		}

		//
		// Weight Net
		{
			final I_M_HU_PI_Attribute piAttr_WeightNet = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_WeightNet)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(LinearDistributionAttributeSplitterStrategy.class)
					.setAggregationStrategyClass(SumAggregationStrategy.class)
					.setTransferStrategyClass(RedistributeQtyHUAttributeTransferStrategy.class));
			piAttr_WeightNet.setIsReadOnly(true);
			piAttr_WeightNet.setSeqNo(piAttrSeqNo);
			piAttr_WeightNet.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_WeightNet);
			piAttrSeqNo += 10;
		}

		//
		// Weight Tare
		{
			final I_M_HU_PI_Attribute piAttr_WeightTare = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_WeightTare)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp)
					.setAggregationStrategyClass(SumAggregationStrategy.class));
			piAttr_WeightTare.setIsReadOnly(true);
			piAttr_WeightTare.setSeqNo(piAttrSeqNo);
			piAttr_WeightTare.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_WeightTare);
			piAttrSeqNo += 10;
		}

		//
		// Weight Tare Adjust
		{
			final I_M_HU_PI_Attribute piAttr_WeightTareAdjust = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_WeightTareAdjust)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation));
			piAttr_WeightTareAdjust.setIsReadOnly(true);
			piAttr_WeightTareAdjust.setSeqNo(piAttrSeqNo);
			piAttr_WeightTareAdjust.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_WeightTareAdjust);
			piAttrSeqNo += 10;
		}

		//
		// Attributes used in ASI
		{
			final I_M_HU_PI_Attribute piAttr_CountryMadeIn = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_CountryMadeIn)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown));
			piAttr_CountryMadeIn.setIsReadOnly(true);
			piAttr_CountryMadeIn.setSeqNo(piAttrSeqNo);
			piAttr_CountryMadeIn.setUseInASI(true);
			InterfaceWrapperHelper.save(piAttr_CountryMadeIn);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_FragileSticker = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_FragileSticker)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_BottomUp));
			piAttr_FragileSticker.setIsReadOnly(true);
			piAttr_FragileSticker.setSeqNo(piAttrSeqNo);
			piAttr_FragileSticker.setUseInASI(true);
			InterfaceWrapperHelper.save(piAttr_FragileSticker);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_QualityDiscountPercent = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_QualityDiscountPercent)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown));
			piAttr_QualityDiscountPercent.setIsReadOnly(true);
			piAttr_QualityDiscountPercent.setSeqNo(piAttrSeqNo);
			piAttr_QualityDiscountPercent.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_QualityDiscountPercent);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_QualityNotice = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_QualityNotice)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown));
			piAttr_QualityNotice.setIsReadOnly(true);
			piAttr_QualityNotice.setSeqNo(piAttrSeqNo);
			piAttr_QualityNotice.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_QualityNotice);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_LotNumberDate = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_LotNumberDate)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class)
					.setAggregationStrategyClass(NullAggregationStrategy.class)
					.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
			piAttr_LotNumberDate.setIsReadOnly(false);
			piAttr_LotNumberDate.setSeqNo(piAttrSeqNo);
			piAttr_LotNumberDate.setUseInASI(true);
			InterfaceWrapperHelper.save(piAttr_LotNumberDate);
			piAttrSeqNo += 10;
		}

		// #653
		{
			final I_M_HU_PI_Attribute piAttr_LotNumber = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_LotNumber)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class)
					.setAggregationStrategyClass(NullAggregationStrategy.class)
					.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
			piAttr_LotNumber.setIsReadOnly(false);
			piAttr_LotNumber.setSeqNo(piAttrSeqNo);
			piAttr_LotNumber.setUseInASI(true);
			InterfaceWrapperHelper.save(piAttr_LotNumber);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_BestBeforeDate = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_BestBeforeDate)
					.setM_HU_PI(huDefNone)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
					.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class)
					.setAggregationStrategyClass(NullAggregationStrategy.class)
					.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
			piAttr_BestBeforeDate.setIsReadOnly(false);
			piAttr_BestBeforeDate.setSeqNo(piAttrSeqNo);
			piAttr_BestBeforeDate.setUseInASI(true);
			InterfaceWrapperHelper.save(piAttr_BestBeforeDate);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_PurchaseOrderLine = createM_HU_PI_Attribute(
					HUPIAttributeBuilder.newInstance(attr_PurchaseOrderLine)
							.setM_HU_PI(huDefNone)
							.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
							.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class)
							.setAggregationStrategyClass(NullAggregationStrategy.class)
							.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
			piAttr_PurchaseOrderLine.setIsReadOnly(true);
			piAttr_PurchaseOrderLine.setIsDisplayed(false);
			piAttr_PurchaseOrderLine.setSeqNo(piAttrSeqNo);
			piAttr_PurchaseOrderLine.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_PurchaseOrderLine);
			piAttrSeqNo += 10;
		}

		{
			final I_M_HU_PI_Attribute piAttr_ReceiptInOutLine = createM_HU_PI_Attribute(
					HUPIAttributeBuilder.newInstance(attr_ReceiptInOutLine)
							.setM_HU_PI(huDefNone)
							.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_TopDown)
							.setSplitterStrategyClass(CopyAttributeSplitterStrategy.class)
							.setAggregationStrategyClass(NullAggregationStrategy.class)
							.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
			piAttr_ReceiptInOutLine.setIsReadOnly(true);
			piAttr_ReceiptInOutLine.setIsDisplayed(false);
			piAttr_ReceiptInOutLine.setSeqNo(piAttrSeqNo);
			piAttr_ReceiptInOutLine.setUseInASI(false);
			InterfaceWrapperHelper.save(piAttr_ReceiptInOutLine);
			piAttrSeqNo += 10;
		}

	}

	private void setupVirtualPIAttributes()
	{
		int piAttrSeqNo = 10;

		//
		// CostPrice
		{
			huDefVirtual_Attr_CostPrice = createM_HU_PI_Attribute(HUPIAttributeBuilder.newInstance(attr_CostPrice)
					.setM_HU_PI(huDefVirtual)
					.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation)
					.setSplitterStrategyClass(NullSplitterStrategy.class)
					.setAggregationStrategyClass(NullAggregationStrategy.class)
					.setTransferStrategyClass(CopyHUAttributeTransferStrategy.class));
			huDefVirtual_Attr_CostPrice.setIsReadOnly(true);
			huDefVirtual_Attr_CostPrice.setSeqNo(piAttrSeqNo);
			huDefVirtual_Attr_CostPrice.setUseInASI(false);
			InterfaceWrapperHelper.save(huDefVirtual_Attr_CostPrice);
			piAttrSeqNo += 10;
		}
	}

	private void setupEmptiesWarehouse()
	{
		final PlainContextAware context = PlainContextAware.newOutOfTrx(getCtx());

		warehouse_Empties = createWarehouse("Empties warehouse");

		final I_C_BPartner emptiesShipperBP = createBPartner("Empties Shipper BPartner");
		final I_M_Shipper emptiesShipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class, context);
		emptiesShipper.setName("Empties shipper");
		emptiesShipper.setC_BPartner_ID(emptiesShipperBP.getC_BPartner_ID());
		InterfaceWrapperHelper.save(emptiesShipper);

		emptiesDDNetworkBuilder = new DDNetworkBuilder()
				.setContext(context)
				.name("Empties distribution network")
				.shipper(emptiesShipper);

		final I_DD_NetworkDistribution emptiesDDNetwork = emptiesDDNetworkBuilder.getDD_NetworkDistribution(I_DD_NetworkDistribution.class);
		emptiesDDNetwork.setIsHUDestroyed(true);
		InterfaceWrapperHelper.save(emptiesDDNetwork);
	}

	/**
	 * Creates a link between given warehouse and empties warehouse
	 */
	public void addEmptiesNetworkLine(final org.compiere.model.I_M_Warehouse warehouse)
	{
		emptiesDDNetworkBuilder.createDDNetworkLine(warehouse, warehouse_Empties);
	}

	public Properties getCtx()
	{
		return ctx;
	}

	public IContextAware getContextProvider()
	{
		return contextProvider;
	}

	public IMutableHUContext getHUContext()
	{
		return huContext;
	}

	public IMutableHUContext createMutableHUContextForProcessingOutOfTrx()
	{
		return createMutableHUContextForProcessing(ITrx.TRXNAME_None);
	}

	public IMutableHUContext createMutableHUContextForProcessing(@Nullable final String trxName)
	{
		final IContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);
		return handlingUnitsBL().createMutableHUContextForProcessing(contextProvider);
	}

	/**
	 * Creates an {@link IMutableHUContext} based on {@link #getContextProvider()}.
	 */
	public IMutableHUContext createMutableHUContext()
	{
		return handlingUnitsBL().createMutableHUContext(getContextProvider());
	}

	public IMutableHUContext createMutableHUContextOutOfTransaction()
	{
		return handlingUnitsBL().createMutableHUContext(ctx, ITrx.TRXNAME_ThreadInherited);
	}

	public IMutableHUContext createMutableHUContextInNewTransaction()
	{
		final String trxName = createAndStartTransaction("HUTestHelper_createMutableHUContextInNewTransaction");
		return handlingUnitsBL().createMutableHUContext(ctx, trxName);
	}

	public ZonedDateTime getTodayZonedDateTime()
	{
		return today;
	}

	public Timestamp getTodayTimestamp()
	{
		return TimeUtil.asTimestamp(getTodayZonedDateTime());
	}

	public I_M_HU_PackingMaterial createPackingMaterial(final String name, final I_M_Product product)
	{
		final I_M_HU_PackingMaterial packingMaterial = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PackingMaterial.class);
		packingMaterial.setName(name);
		packingMaterial.setM_Product_ID(product != null ? product.getM_Product_ID() : -1);
		InterfaceWrapperHelper.save(packingMaterial);

		return packingMaterial;
	}

	public I_M_HU_PI createHUDefinition(
			@NonNull final String name,
			@NonNull final String huUnitType)
	{
		final I_M_HU_PI pi = InterfaceWrapperHelper.create(ctx, I_M_HU_PI.class, ITrx.TRXNAME_None);
		pi.setName(name);
		InterfaceWrapperHelper.save(pi);

		// Create the current version
		final HuPackingInstructionsVersionId huPIVersionId = null;
		createVersion(pi, true, huUnitType, huPIVersionId);

		return pi;
	}

	public I_M_HU_PI_Version createVersion(final I_M_HU_PI handlingUnit, final boolean current)
	{
		final String huUnitType = null;
		final HuPackingInstructionsVersionId huPIVersionId = null;
		return createVersion(handlingUnit, current, huUnitType, huPIVersionId);
	}

	private I_M_HU_PI_Version createVersion(
			final I_M_HU_PI pi,
			final boolean current,
			final String huUnitType,
			final HuPackingInstructionsVersionId huPIVersionId)
	{
		final I_M_HU_PI_Version version = InterfaceWrapperHelper.create(ctx, I_M_HU_PI_Version.class, ITrx.TRXNAME_None);
		version.setName(pi.getName());
		version.setM_HU_PI(pi);
		version.setIsCurrent(current);
		if (huUnitType != null)
		{
			version.setHU_UnitType(huUnitType);
		}
		if (huPIVersionId != null)
		{
			version.setM_HU_PI_Version_ID(huPIVersionId.getRepoId());
		}
		InterfaceWrapperHelper.save(version);
		return version;
	}

	public static final BigDecimal QTY_NA = null;

	public I_M_HU_PI_Item createHU_PI_Item_Material(final I_M_HU_PI pi)
	{
		final HuPackingInstructionsItemId piItemId = null;
		return createHU_PI_Item_Material(pi, piItemId);
	}

	public I_M_HU_PI_Item createHU_PI_Item_Material(final I_M_HU_PI pi, final HuPackingInstructionsItemId piItemId)
	{
		final I_M_HU_PI_Version version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(pi);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, version);
		piItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		piItem.setM_HU_PI_Version(version);
		if (piItemId != null)
		{
			piItem.setM_HU_PI_Item_ID(piItemId.getRepoId());
		}

		InterfaceWrapperHelper.save(piItem);
		return piItem;
	}

	/**
	 * Invokes {@link #createHU_PI_Item_IncludedHU(I_M_HU_PI, I_M_HU_PI, BigDecimal, I_C_BPartner)} with bPartner being {@code null}.
	 */
	public I_M_HU_PI_Item createHU_PI_Item_IncludedHU(final I_M_HU_PI huDefinition,
													  final I_M_HU_PI includedHuDefinition,
													  final BigDecimal qty)
	{
		final I_C_BPartner bpartner = null;
		return createHU_PI_Item_IncludedHU(huDefinition, includedHuDefinition, qty, bpartner);
	}

	/**
	 * Creates an {@link I_M_HU_PI_Item} with the given {@code qty} ("capacity") and {@code pPartner}<br>
	 * and links it with the given {@code huDefinition} and {@code includedHuDefinition}.
	 */
	public I_M_HU_PI_Item createHU_PI_Item_IncludedHU(
			final I_M_HU_PI huDefinition,
			final I_M_HU_PI includedHuDefinition,
			final BigDecimal qty,
			final I_C_BPartner bpartner)
	{
		final BPartnerId bpartnerId = bpartner != null ? BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()) : null;
		return createHU_PI_Item_IncludedHU(huDefinition, includedHuDefinition, qty, bpartnerId);
	}

	public I_M_HU_PI_Item createHU_PI_Item_IncludedHU(
			final I_M_HU_PI huDefinition,
			final I_M_HU_PI includedHuDefinition,
			final BigDecimal qty,
			final BPartnerId bpartnerId)
	{
		final I_M_HU_PI_Version version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huDefinition);

		final I_M_HU_PI_Item itemDefinition = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, version);
		itemDefinition.setItemType(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit);
		itemDefinition.setIncluded_HU_PI_ID(includedHuDefinition != null ? includedHuDefinition.getM_HU_PI_ID() : -17);
		itemDefinition.setM_HU_PI_Version(version);
		itemDefinition.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		if (!Objects.equals(qty, QTY_NA))
		{
			itemDefinition.setQty(qty);
		}

		InterfaceWrapperHelper.save(itemDefinition);
		return itemDefinition;
	}

	public I_M_HU_PI_Item createHU_PI_Item_PackingMaterial(
			@NonNull final I_M_HU_PI huDefinition,
			@NonNull final I_M_HU_PackingMaterial huPackingMaterial)
	{
		final I_M_HU_PI_Version version = Services.get(IHandlingUnitsDAO.class).retrievePICurrentVersion(huDefinition);

		final I_M_HU_PI_Item piItem = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class, version);
		piItem.setM_HU_PI_Version(version);

		piItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		piItem.setM_HU_PackingMaterial(huPackingMaterial);
		piItem.setQty(BigDecimal.ONE);

		InterfaceWrapperHelper.save(piItem);
		return piItem;
	}

	/**
	 * @deprecated please use {@link #assignProduct(I_M_HU_PI_Item, ProductId, BigDecimal, I_C_UOM)} instead.
	 */
	@Deprecated
	public I_M_HU_PI_Item_Product assignProduct(final I_M_HU_PI_Item itemPI, final I_M_Product product, final BigDecimal capacity, final I_C_UOM uom)
	{
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		return assignProduct(itemPI, productId, capacity, uom);
	}

	public I_M_HU_PI_Item_Product assignProduct(
			final I_M_HU_PI_Item itemPI,
			final ProductId productId,
			final BigDecimal capacity,
			final I_C_UOM uom)
	{
		final BPartnerId bpartnerId = null;
		return assignProduct(
				itemPI,
				productId,
				Quantity.of(capacity, uom),
				bpartnerId);
	}

	public I_M_HU_PI_Item_Product assignProduct(
			final I_M_HU_PI_Item itemPI,
			final ProductId productId,
			final Quantity capacity)
	{
		final BPartnerId bpartnerId = null;
		return assignProduct(
				itemPI,
				productId,
				capacity,
				bpartnerId);
	}

	public I_M_HU_PI_Item_Product assignProduct(
			@NonNull final I_M_HU_PI_Item itemPI,
			@NonNull final ProductId productId,
			@NonNull final Quantity capacity,
			@Nullable final BPartnerId bpartnerId)
	{
		Check.errorUnless(Objects.equals(itemPI.getItemType(), X_M_HU_PI_Item.ITEMTYPE_Material), "Param 'itemPI' needs to have ItemType={}, not={}; itemPI={} material item", X_M_HU_PI_Item.ITEMTYPE_Material, itemPI.getItemType(), itemPI);

		final I_M_HU_PI_Item_Product itemDefProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class, itemPI);
		itemDefProduct.setM_HU_PI_Item(itemPI);
		itemDefProduct.setM_Product_ID(productId.getRepoId());
		itemDefProduct.setQty(capacity.toBigDecimal());
		itemDefProduct.setC_UOM_ID(capacity.getUomId().getRepoId());
		itemDefProduct.setValidFrom(TimeUtil.getDay(1970, 1, 1));
		itemDefProduct.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		InterfaceWrapperHelper.save(itemDefProduct);

		return itemDefProduct;
	}

	public I_M_HU_PI_Item_Product assignProductAny(final I_M_HU_PI_Item itemPI, final HUPIItemProductId huPIItemProductId)
	{
		final I_M_HU_PI_Item_Product itemDefProduct = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class, itemPI);
		itemDefProduct.setM_HU_PI_Item(itemPI);

		itemDefProduct.setIsAllowAnyProduct(true);
		itemDefProduct.setM_Product_ID(-1);

		itemDefProduct.setIsInfiniteCapacity(true);
		itemDefProduct.setQty(null);
		itemDefProduct.setC_UOM_ID(-1);

		itemDefProduct.setValidFrom(TimeUtil.getDay(1970, 1, 1));

		if (huPIItemProductId != null)
		{
			itemDefProduct.setM_HU_PI_Item_Product_ID(huPIItemProductId.getRepoId());
		}

		InterfaceWrapperHelper.save(itemDefProduct);
		return itemDefProduct;
	}

	public I_M_Transaction createMTransaction(final String movementType, final I_M_Product product, final BigDecimal qty)
	{
		final I_M_Transaction mtrx = InterfaceWrapperHelper.newInstance(I_M_Transaction.class, contextProvider);
		mtrx.setM_Product_ID(product.getM_Product_ID());
		mtrx.setMovementQty(qty);
		mtrx.setMovementType(movementType);
		mtrx.setMovementDate(getTodayTimestamp());
		InterfaceWrapperHelper.save(mtrx);
		return mtrx;
	}

	public I_M_Transaction createMTransactionReversal(final I_M_Transaction mtrx)
	{
		final I_M_Transaction mtrxReversal = InterfaceWrapperHelper.newInstance(I_M_Transaction.class, mtrx);
		InterfaceWrapperHelper.copyValues(mtrx, mtrxReversal, false); // honorIsCalculated=false, else we get an exception
		mtrxReversal.setMovementQty(mtrx.getMovementQty().negate());
		InterfaceWrapperHelper.save(mtrxReversal);
		return mtrxReversal;
	}

	protected HUListAssertsBuilder assertHUList(final String name, final List<I_M_HU> list)
	{
		return new HUListAssertsBuilder(null, name, list);
	}

	/**
	 * Create an {@link I_M_HU_Attribute} for the given {@link HUPIAttributeBuilder}.
	 *
	 * @return {@link I_M_HU_Attribute} created by the builder
	 */
	public I_M_HU_PI_Attribute createM_HU_PI_Attribute(final HUPIAttributeBuilder attributeBuilder)
	{
		return attributeBuilder.create(ctx);
	}

	public void createAttributeListValues(final org.compiere.model.I_M_Attribute attribute, final String... values)
	{
		for (final String value : values)
		{
			final String name = "Name_" + value;
			createAttributeListValue(attribute, value, name);
		}
	}

	public void createAttributeListValue(final org.compiere.model.I_M_Attribute attribute, final String value, final String name)
	{
		final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
		attributesRepo.createAttributeValue(AttributeListValueCreateRequest.builder()
				.attributeId(AttributeId.ofRepoId(attribute.getM_Attribute_ID()))
				.value(value)
				.name(name)
				.build());
	}

	public IAttributeValue createAttributeValue(final org.compiere.model.I_M_Attribute attribute, final Object value)
	{
		final IAttributeValue av = new PlainAttributeValue(NullAttributeStorage.instance, attribute);
		av.setValue(new HUAttributePropagationContext(NullAttributeStorage.instance, new NoPropagationHUAttributePropagator(), attribute), value);
		return av;
	}

	public IAttributeStorage createAttributeSetStorage(final IAttributeValue... attributeValues)
	{
		final List<IAttributeValue> list = Arrays.asList(attributeValues);
		final IAttributeStorageFactory attributeStorageFactory = getHUContext().getHUAttributeStorageFactory();
		return new ListAttributeStorage(attributeStorageFactory, list);
	}

	public Object createDummyReferenceModel()
	{
		final I_C_BPartner bpartner = null;
		return createDummyReferenceModel(bpartner);
	}

	public Object createDummyReferenceModel(final I_C_BPartner bpartner)
	{
		final BPartnerId bpartnerId = bpartner != null ? BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()) : null;
		return createDummyReferenceModel(bpartnerId);
	}

	public Object createDummyReferenceModel(final BPartnerId bpartnerId)
	{
		final I_Test referencedModel = InterfaceWrapperHelper.newInstance(I_Test.class, contextProvider);

		if (bpartnerId != null)
		{
			referencedModel.setC_BPartner_ID(bpartnerId.getRepoId());
		}

		InterfaceWrapperHelper.save(referencedModel);
		return referencedModel;
	}

	public GenericAllocationSourceDestination createDummySourceDestination(
			final ProductId productId,
			final BigDecimal qtyCapacity,
			final boolean fullyLoaded)
	{
		return createDummySourceDestination(productId, qtyCapacity, uomEach, fullyLoaded);
	}

	public GenericAllocationSourceDestination createDummySourceDestination(
			final ProductId productId,
			final BigDecimal qtyCapacity,
			final I_C_UOM uom,
			final boolean fullyLoaded)
	{
		final Capacity capacity = Capacity.createCapacity(
				qtyCapacity,
				productId,
				uom,
				false // allowNegativeCapacity
		);
		final BigDecimal qtyInitial = fullyLoaded ? qtyCapacity : BigDecimal.ZERO;
		final PlainProductStorage storage = new PlainProductStorage(capacity, qtyInitial);

		final Object referencedModel = createDummyReferenceModel();
		return new GenericAllocationSourceDestination(storage, referencedModel);
	}

	public List<I_M_HU> createHUs(
			final IHUContext huContext,
			final I_M_HU_PI huPI,
			final ProductId productIdToLoad,
			final BigDecimal qtyToLoad,
			final I_C_UOM qtyToLoadUOM)
	{
		return createHUs(huContext, huPI, productIdToLoad, Quantity.of(qtyToLoad, qtyToLoadUOM));
	}

	public Optional<I_M_HU> createSingleHU(
			final I_M_HU_PI huPI,
			final ProductId productIdToLoad,
			final Quantity qtyToLoad)
	{
		final HUProducerDestination destination;
		HULoader.builder()
				.source(createDummySourceDestination(productIdToLoad,
						new BigDecimal("100000000"),  // qtyCapacity
						qtyToLoad.getUOM(),  // UOM
						true)) // fullyLoaded => empty
				.destination(destination = HUProducerDestination.of(huPI))
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(productIdToLoad)
						.setQuantity(qtyToLoad)
						.setDate(getTodayZonedDateTime())
						.setFromReferencedModel(null)
						.setForceQtyAllocation(true)
						.create());

		return destination.getSingleCreatedHU();
	}

	/**
	 * Create HUs using {@link HUProducerDestination}.<br>
	 * <b>Important:</b> If you expect e.g. an LU with multiple included TUs, then don't use this method; see the javadoc of {@link HUProducerDestination}.
	 */
	public List<I_M_HU> createHUs(
			final IHUContext huContext,
			final I_M_HU_PI huPI,
			final ProductId productIdToLoad,
			final Quantity qtyToLoad)
	{
		final IAllocationSource source = createDummySourceDestination(productIdToLoad,
				new BigDecimal("100000000"),  // qtyCapacity
				qtyToLoad.getUOM(),  // UOM
				true // fullyLoaded => empty
		);

		final HUProducerDestination destination = HUProducerDestination.of(huPI);

		final HULoader loader = HULoader.of(source, destination);

		final Object referenceModel = null;
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				productIdToLoad,
				qtyToLoad,
				getTodayZonedDateTime(),  // date
				referenceModel,
				false // forceQtyAllocation
		);

		loader.load(request);

		return destination.getCreatedHUs();
	}

	/**
	 * Generate HUs for given Customer Unit qty
	 */
	public List<I_M_HU> createHUs(
			final IHUContext huContext,
			final ILUTUProducerAllocationDestination allocationDestination,
			final BigDecimal cuQty)
	{
		final Capacity tuCapacity = allocationDestination.getSingleCUPerTU();
		final ProductId cuProductId = tuCapacity.getProductId();
		final I_C_UOM cuUOM = tuCapacity.getC_UOM();

		final AbstractAllocationSourceDestination allocationSource = createDummySourceDestination(cuProductId,
				Quantity.QTY_INFINITE,
				Services.get(IProductBL.class).getStockUOM(cuProductId),
				true // fullyLoaded
		);
		final Object referencedModel = allocationSource.getReferenceModel();

		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext,
				cuProductId,
				Quantity.of(cuQty, cuUOM),
				getTodayZonedDateTime(),
				referencedModel,
				false);

		//
		// Execute transfer => HUs will be generated
		final HULoader loader = HULoader.of(allocationSource, allocationDestination);
		final IAllocationResult result = loader.load(request);
		Assertions.assertThat(result.isCompleted()).as("Result shall be completed: " + result).isTrue();

		//
		// Get generated HUs and set them to HUContext's transaction
		// NOTE: we do this because in case we were processing out of transaction then an internal transaction will be created
		// and those HUs will be returned using internal trx
		final List<I_M_HU> hus = allocationDestination.getCreatedHUs();
		for (final I_M_HU hu : hus)
		{
			InterfaceWrapperHelper.setTrxName(hu, huContext.getTrxName());
		}
		return hus;
	}

	@Builder(builderMethodName = "newVHU", builderClassName = "VHUBuilder")
	private I_M_HU createVHU(
			@NonNull final ProductId productId,
			@NonNull final Quantity qty,
			@Nullable final String huStatus,
			@Nullable final LocatorId locatorId)
	{
		final IMutableHUContext huContext = createMutableHUContextForProcessingOutOfTrx();

		final IHUProducerAllocationDestination huProducer = HUProducerDestination.ofVirtualPI()
				.setHUStatus(huStatus)
				.setLocatorId(locatorId);

		final AbstractAllocationSourceDestination dummySource = createDummySourceDestination(
				productId,
				Quantity.QTY_INFINITE,
				qty.getUOM(),
				true // fullyLoaded
		);
		final Object referencedModel = dummySource.getReferenceModel();

		final IAllocationRequest request = AllocationUtils.builder()
				.setHUContext(huContext)
				.setProduct(productId)
				.setQuantity(qty)
				.setDate(getTodayZonedDateTime())
				.setFromReferencedModel(referencedModel)
				.setForceQtyAllocation(true)
				.create();

		HULoader.builder()
				.source(dummySource)
				.destination(huProducer)
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(productId)
						.setQuantity(qty)
						.setDate(getTodayZonedDateTime())
						.setFromReferencedModel(referencedModel)
						.setForceQtyAllocation(true)
						.create());

		return huProducer.getSingleCreatedHU()
				.orElseThrow(() -> new AdempiereException("VHU not created"));
	}

	/**
	 * @param loadingUnitPIItem the PI item with type = HU that link's the LU's PI with the TU's PI. This methods passes it to the {@link ILUTUProducerAllocationDestination}.
	 */
	@Builder(builderMethodName = "newLUs", builderClassName = "LUsBuilder")
	private List<I_M_HU> createLUs(
			@Nullable final IHUContext huContext,
			@NonNull final I_M_HU_PI_Item loadingUnitPIItem,
			@NonNull final I_M_HU_PI_Item_Product tuPIItemProduct,
			@NonNull final BigDecimal totalQtyCU,
			@Nullable final LocatorId locatorId,
			@Nullable final String huStatus)
	{
		final IHUContext huContextEffective = huContext != null
				? huContext
				: createMutableHUContextForProcessingOutOfTrx();

		final BPartnerId bpartnerId = null;
		final int bpartnerLocationId = -1;
		final ProductId cuProductId = ProductId.ofRepoId(tuPIItemProduct.getM_Product_ID());
		final I_C_UOM cuUOM = IHUPIItemProductBL.extractUOMOrNull(tuPIItemProduct);

		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				UomId.ofRepoId(cuUOM.getC_UOM_ID()),
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);
		lutuConfigurationFactory.save(lutuConfiguration);

		final ILUTUProducerAllocationDestination luProducerDestination = lutuConfigurationFactory.createLUTUProducerAllocationDestination(lutuConfiguration);
		luProducerDestination.setLUPI(loadingUnitPIItem.getM_HU_PI_Version().getM_HU_PI());
		luProducerDestination.setLUItemPI(loadingUnitPIItem);
		luProducerDestination.setMaxLUsInfinite(); // allow creating as much LUs as needed
		if (locatorId != null)
		{
			luProducerDestination.setLocatorId(locatorId);
		}
		if (huStatus != null)
		{
			luProducerDestination.setHUStatus(huStatus);
		}

		//
		// Create LUs
		return createHUs(huContextEffective, luProducerDestination, totalQtyCU);
	}

	public class LUsBuilder
	{
		public HuId buildSingleLUId()
		{
			final I_M_HU lu = buildSingleLU();
			return HuId.ofRepoId(lu.getM_HU_ID());
		}

		public I_M_HU buildSingleLU()
		{
			final List<I_M_HU> luHUs = this.build();
			if (luHUs.isEmpty())
			{
				throw new IllegalStateException("We expected only one LU but we got none");
			}
			else if (luHUs.size() == 1)
			{
				return luHUs.get(0);
			}
			else
			{
				throw new IllegalStateException("We exected only one LU  but we got " + luHUs.size() + ": " + luHUs);
			}
		}
	}

	public I_M_HU createLU(
			final IHUContext huContext,
			final I_M_HU_PI_Item loadingUnitPIItem,
			final I_M_HU_PI_Item_Product tuPIItemProduct,
			final BigDecimal totalQtyCU)
	{
		return newLUs()
				.huContext(huContext)
				.loadingUnitPIItem(loadingUnitPIItem)
				.tuPIItemProduct(tuPIItemProduct)
				.totalQtyCU(totalQtyCU)
				.buildSingleLU();
	}

	public List<I_M_HU> retrieveAllHandlingUnits()
	{
		final IQueryBuilder<I_M_HU> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU.class, contextProvider);

		queryBuilder.orderBy()
				.addColumn(I_M_HU.COLUMNNAME_M_HU_ID);

		return queryBuilder
				.create()
				.list(I_M_HU.class);
	}

	public List<I_M_HU> retrieveAllHandlingUnitsOfType(final I_M_HU_PI huPI)
	{
		final IHandlingUnitsBL handlingUnitsBL = handlingUnitsBL();

		final List<I_M_HU> result = new ArrayList<>();

		// Filter
		for (final I_M_HU hu : retrieveAllHandlingUnits())
		{
			if (handlingUnitsBL.getPIVersion(hu).getM_HU_PI_ID() != huPI.getM_HU_PI_ID())
			{
				continue;
			}

			result.add(hu);
		}

		return result;
	}

	/**
	 * Take the given {@code producer} and load the given {@code loadCuQty} and {@code loadCuUOM} of the given {@code cuProduct} into new HUs.
	 * <p>
	 * You can use {@link AbstractProducerDestination#getCreatedHUs()} to collect the results after the loading.
	 * <p>
	 * Note: this method performs the load using an {@link IHUContext} that was created with {@link #createMutableHUContextOutOfTransaction()}.
	 *
	 * @param producer used as the loader's {@link IAllocationDestination}
	 */
	public final void load(
			final IHUProducerAllocationDestination producer,
			final ProductId cuProductId,
			final BigDecimal loadCuQty,
			final I_C_UOM loadCuUOM)
	{
		load(TestHelperLoadRequest.builder()
				.producer(producer)
				.cuProductId(cuProductId)
				.loadCuQty(loadCuQty)
				.loadCuUOM(loadCuUOM)
				.build());
	}

	public final void load(TestHelperLoadRequest r)
	{
		final IAllocationSource source = createDummySourceDestination(
				r.getCuProductId(),
				Quantity.QTY_INFINITE,
				r.getLoadCuUOM(),
				true); // fullyLoaded

		final HULoader huLoader = HULoader.of(source, r.getProducer())
				.setAllowPartialUnloads(false)
				.setAllowPartialLoads(false);

		final IMutableHUContext huContext0 = createMutableHUContextOutOfTransaction();
		if (r.getHuPackingMaterialsCollector() != null)
		{
			huContext0.setHUPackingMaterialsCollector(r.getHuPackingMaterialsCollector());
		}

		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext0,
				r.getCuProductId(), // product
				Quantity.of(r.getLoadCuQty(), r.getLoadCuUOM()), // qty
				de.metas.common.util.time.SystemTime.asZonedDateTime());

		huLoader.load(request);
	}

	@Builder
	@Data
	public static final class TestHelperLoadRequest
	{
		@NonNull final IHUProducerAllocationDestination producer;

		@NonNull final ProductId cuProductId;

		@NonNull final BigDecimal loadCuQty;

		@NonNull final I_C_UOM loadCuUOM;

		final IHUPackingMaterialsCollector<IHUPackingMaterialCollectorSource> huPackingMaterialsCollector;
	}

	/**
	 * Creates LUs with TUs and loads the products from the given {@code mtrx} into them.<br>
	 * <b>Important:</b> only works if the given {@code huPI} has exactly one HU PI-item for the TU.
	 * <p>
	 * This method contains the code that used to be in {@link IHUTrxBL} {@code transferIncomingToHUs()}.<br>
	 * When it was there, that method was used only by test cases and also doesn't make a lot of sense for production.
	 *
	 * @param mtrx the load's source. Also provides the context.
	 * @param huPI a "simple" PI that contains one HU-item which links to one child-HU PI
	 */
	public List<I_M_HU> createHUsFromSimplePI(final I_M_Transaction mtrx, final I_M_HU_PI huPI)
	{
		Check.assume(MTransactionUtil.isInboundTransaction(mtrx),
				"mtrx shall be inbound transaction: {}", mtrx);

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(mtrx);
		final IMutableHUContext huContext = handlingUnitsBL().createMutableHUContext(contextProvider);

		final IAllocationSource source = new MTransactionAllocationSourceDestination(mtrx);

		final LUTUProducerDestination lutuProducer = new LUTUProducerDestination();
		lutuProducer.setLUPI(huPI);

		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
		final I_M_HU_PI_Version currentPIVersion = handlingUnitsDAO.retrievePICurrentVersion(huPI);
		final List<I_M_HU_PI_Item> piItemsForChildHU = handlingUnitsDAO.retrievePIItems(currentPIVersion, null).stream()
				.filter(piItem -> Objects.equals(X_M_HU_PI_Item.ITEMTYPE_HandlingUnit, piItem.getItemType()))
				.collect(Collectors.toList());
		assertThat("This method only works if the given 'huPI' has exactly one child-HU item", piItemsForChildHU.size(), is(1));

		lutuProducer.setLUItemPI(piItemsForChildHU.get(0));
		lutuProducer.setTUPI(handlingUnitsDAO.getIncludedPI(piItemsForChildHU.get(0)));

		final HULoader loader = HULoader.of(source, lutuProducer);

		final I_C_UOM uom = handlingUnitsBL().getC_UOM(mtrx);
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				mtrx.getM_Product(),
				mtrx.getMovementQty(),
				uom,
				TimeUtil.asZonedDateTime(mtrx.getMovementDate()),
				mtrx);

		loader.load(request);

		return lutuProducer.getCreatedHUs();
	}

	/**
	 * Sets up a {@link HUListAllocationSourceDestination} for the given {@code sourceHUs} and loads them to the given {@code lutuProducer}.
	 * <p>
	 * You can use {@link LUTUProducerDestination#getCreatedHUs()} to collect the results after the loading.
	 * Note that this method does less than {@link IHUSplitBuilder}. E.g. it does not:
	 * <li>propagate the source HUs' Locator, Status etc
	 * <li>destroy empty source HUs
	 *
	 * @param lutuProducer used as the loader's {@link IAllocationDestination}
	 */
	public void transferMaterialToNewHUs(final List<I_M_HU> sourceHUs,
										 final LUTUProducerDestination lutuProducer,
										 final BigDecimal qty,
										 final I_M_Product product,
										 final I_C_UOM uom)
	{
		Check.assume(Adempiere.isUnitTestMode(), "This method shall be executed only in JUnit test mode");

		final IMutableHUContext huContext = getHUContext();
		final ZonedDateTime date = TimeUtil.asZonedDateTime(Env.getContextAsDate(Env.getCtx(), "#Date")); // FIXME use context date for now

		final IAllocationSource source = HUListAllocationSourceDestination.of(sourceHUs);

		final HULoader loader = HULoader.of(source, lutuProducer);

		// allowing partial loads and unloads because that's interesting cases to test
		loader.setAllowPartialUnloads(true);
		loader.setAllowPartialLoads(true);

		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, product, qty, uom, date);

		loader.load(request); // use context date for now
	}

	/**
	 * This method creates one or many new HU(s) by "taking" the given product and qty out of the given source HU(s). The source HU's items are modified in this process. It also creates a
	 * {@link I_M_HU_Trx_Hdr} to document from which source items (of <code>sourceHU</code>) to which destination items (of the new HU) the qtys were moved. That trx-hdr has a one line for every
	 * {@link de.metas.handlingunits.model.I_M_HU_Item} of the source HU and one line for
	 *
	 * @param sourceHUs       the HUs from which the given product and qty shall be taken
	 * @param qty             the qty of <code>product</code> that shall be included in the new HU(s)
	 * @param product         the product the shall be taken out of the <code>sourceHUs</code>
	 * @param destinationHuPI the definition of the new HU that shall be created with the given product and qty
	 * @return the newly created HUs that are "split off" the source HU
	 * @deprecated uses {@link HUProducerDestination}; deprecated for the same reasons as {@link #transferIncomingToHUs(I_M_Transaction, I_M_HU_PI)}.
	 * Please consider calling {@link #transferMaterialToNewHUs(List, LUTUProducerDestination, BigDecimal, I_M_Product, I_C_UOM)}.
	 */
	@Deprecated
	public List<I_M_HU> transferMaterialToNewHUs(final List<I_M_HU> sourceHUs, final BigDecimal qty, final I_M_Product product, final I_M_HU_PI destinationHuPI)
	{
		Check.assume(Adempiere.isUnitTestMode(), "This method shall be executed only in JUnit test mode");

		final IMutableHUContext huContext = getHUContext();
		final ZonedDateTime date = TimeUtil.asZonedDateTime(Env.getContextAsDate(Env.getCtx(), "#Date")); // FIXME use context date for now

		final IAllocationSource source = HUListAllocationSourceDestination.of(sourceHUs);
		final HUProducerDestination destination = HUProducerDestination.of(destinationHuPI);
		final HULoader loader = HULoader.of(source, destination);

		final I_C_UOM uom = handlingUnitsBL().getHandlingUOM(product);
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, product, qty, uom, date);

		loader.load(request); // use context date for now

		return destination.getCreatedHUs();
	}

	/**
	 * This method "destroys" one or many HU(s) according to the given transaction document (e.g. shipment). The source HUs' items are modified in this process. Note that the qtys contained in the
	 * given source HUs need to be sufficient for the products and qtys of the transaction document. If the given source HUs contain more material than required for the transaction document, then the
	 * rest is "left back" in the source HU(s).
	 */
	public void transferHUsToOutgoing(final I_M_Transaction outgoingTrx, final List<I_M_HU> sourceHUs)
	{
		final IMutableHUContext huContext = getHUContext();
		final ZonedDateTime date = getTodayZonedDateTime();

		final IAllocationSource source = HUListAllocationSourceDestination.of(sourceHUs);
		final IAllocationDestination destination = new MTransactionAllocationSourceDestination(outgoingTrx);
		final HULoader loader = HULoader.of(source, destination);

		final I_M_Product product = outgoingTrx.getM_Product();
		final I_C_UOM uom = handlingUnitsBL().getC_UOM(outgoingTrx);
		final BigDecimal qtyAbs = outgoingTrx.getMovementQty();
		final BigDecimal qty = qtyAbs.negate();

		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, product, qty, uom, date, outgoingTrx);

		loader.load(request);
	}

	/**
	 * This method creates one or many HU(s) and distributes the products and Qtys of the given transaction document (e.g. material receipt) among those instances' material-HU-items. The method also
	 * creates a {@link de.metas.handlingunits.model.I_M_HU_Trx_Hdr} which references the given transactionDoc.
	 *
	 * @param mtrx the material transaction (inventory, receipt etc) that document the "origin" of the products to be added to the new HU
	 * @return the newly created HUs that were created from the transaction doc.
	 * @deprecated this method only uses {@link HUProducerDestination} which will only create a simple plain HU. In almost every scenario that's not what you want test-wise.
	 * Please remove the deprecation flag and update the doc if and when a good class of testcases come up which justify having the method in this helper..
	 */
	@Deprecated
	public List<I_M_HU> transferIncomingToHUs(final I_M_Transaction mtrx, final I_M_HU_PI huPI)
	{
		Check.assume(MTransactionUtil.isInboundTransaction(mtrx), "mtrx shall be inbound transaction: {}", mtrx);

		final IAllocationSource source = new MTransactionAllocationSourceDestination(mtrx);
		final HUProducerDestination destination = HUProducerDestination.of(huPI);
		final HULoader loader = HULoader.of(source, destination);

		//
		// Create allocation request
		final IMutableHUContext huContext = getHUContext();
		final I_C_UOM uom = handlingUnitsBL().getC_UOM(mtrx);
		final IAllocationRequest request = AllocationUtils.createQtyRequest(
				huContext,
				mtrx.getM_Product(),
				mtrx.getMovementQty(),
				uom,
				TimeUtil.asZonedDateTime(mtrx.getMovementDate()),
				mtrx);

		//
		// Execute transfer
		loader.load(request);

		return destination.getCreatedHUs();
	}

	public void transferMaterialToExistingHUs(final List<I_M_HU> sourceHUs, final List<I_M_HU> destinationHUs, final I_M_Product product, final BigDecimal qty, final I_C_UOM uom)
	{
		final IAllocationSource source = HUListAllocationSourceDestination.of(sourceHUs);
		final IAllocationDestination destination = HUListAllocationSourceDestination.of(destinationHUs);
		final HULoader loader = HULoader.of(source, destination);

		//
		// Create allocation request
		final IMutableHUContext huContext = getHUContext();
		final ZonedDateTime date = getTodayZonedDateTime();
		final IAllocationRequest request = AllocationUtils.createQtyRequest(huContext, product, qty, uom, date);

		//
		// Execute transfer
		loader.load(request);
	}

	public boolean isNoPI(final I_M_HU_PI pi)
	{
		return HuPackingInstructionsId.isTemplateRepoId(pi.getM_HU_PI_ID());
	}

	public boolean isVirtualPI(final I_M_HU_PI pi)
	{
		return HuPackingInstructionsId.isVirtualRepoId(pi.getM_HU_PI_ID());
	}

	public boolean isVirtualPIItem(final I_M_HU_PI_Item piItem)
	{
		return HuPackingInstructionsItemId.isVirtualRepoId(piItem.getM_HU_PI_Item_ID());
	}

	public boolean isVirtualPIItemProduct(final I_M_HU_PI_Item_Product piItemProduct)
	{
		return piItemProduct.getM_HU_PI_Item_Product_ID() == Services.get(IHUPIItemProductDAO.class).retrieveVirtualPIMaterialItemProduct(ctx).getM_HU_PI_Item_Product_ID();
	}

	/**
	 * @return HUs which were split
	 */
	public List<I_M_HU> splitHUs(
			final IHUContext huContext,
			final I_M_HU huToSplit,
			final ProductId cuProductId,
			final BigDecimal cuQty,
			final I_C_UOM cuUOM,
			final BigDecimal cuPerTU,
			final BigDecimal tuPerLU,
			final BigDecimal maxLUToAllocate,
			final I_M_HU_PI_Item tuPIItem,
			final I_M_HU_PI_Item luPIItem)
	{
		return newSplitBuilder(huContext, huToSplit, cuProductId, cuQty, cuUOM, cuPerTU, tuPerLU, maxLUToAllocate, tuPIItem, luPIItem)
				.split();
	}

	public IHUSplitBuilder newSplitBuilder(
			final IHUContext huContext,
			final I_M_HU huToSplit,
			final ProductId cuProductId,
			final BigDecimal cuQty,
			final I_C_UOM cuUOM,
			final BigDecimal cuPerTU,
			final BigDecimal tuPerLU,
			final BigDecimal maxLUToAllocate,
			final I_M_HU_PI_Item tuPIItem,
			final I_M_HU_PI_Item luPIItem)
	{
		final IHUSplitBuilder splitBuilder = new HUSplitBuilder(huContext);

		//
		// "Our" HU, the one which the user selected for split
		splitBuilder.setHUToSplit(huToSplit);

		//
		// Create Allocation Request for all Qty from CU Key

		splitBuilder.setCUProductId(cuProductId);
		splitBuilder.setCUQty(cuQty);
		splitBuilder.setCUUOM(cuUOM);

		splitBuilder.setCUPerTU(cuPerTU);
		splitBuilder.setTUPerLU(tuPerLU);
		splitBuilder.setMaxLUToAllocate(maxLUToAllocate);

		splitBuilder.setTU_M_HU_PI_Item(tuPIItem);
		splitBuilder.setLU_M_HU_PI_Item(luPIItem);

		final boolean splitOnNoPI = isNoPI(luPIItem.getM_HU_PI_Version().getM_HU_PI());
		splitBuilder.setSplitOnNoPI(splitOnNoPI); // huDefNone

		return splitBuilder;
	}

	/**
	 * Join given <code>tradingUnits</code> to the <code>loadingUnit</code>
	 */
	public void joinHUs(final IHUContext huContext, final I_M_HU loadingUnit, final Collection<I_M_HU> tradingUnits)
	{
		Check.assumeNotNull(tradingUnits, "PAram 'tradingUnits' is not null; other params: huContext={}, loadingUnit={}", huContext, loadingUnit);
		final IHUJoinBL huJoinBL = Services.get(IHUJoinBL.class);

		tradingUnits
				.forEach(tradingUnit -> huJoinBL.assignTradingUnitToLoadingUnit(huContext, loadingUnit, tradingUnit));
	}

	/**
	 * Join given <code>tradingUnits</code> to the <code>loadingUnit</code>
	 */
	public void joinHUs(final IHUContext huContext, final I_M_HU loadingUnit, final I_M_HU... tradingUnits)
	{
		trxBL.createHUContextProcessorExecutor(huContext)
				.run(huContextLocal -> {
					joinHUs(huContextLocal, loadingUnit, Arrays.asList(tradingUnits));
					return IHUContextProcessor.NULL_RESULT;
				});
	}

	/**
	 * Configure and use {@link ITUMergeBuilder} to move given <code>sourceHUs</code> customer units (products) on the <code>targetHU</code> with the qty, UOM of that product
	 */
	public void mergeTUs(
			final IHUContext huContext,
			final List<I_M_HU> sourceHUs,
			final I_M_HU targetHU,
			final ProductId cuProductId,
			final BigDecimal cuQty,
			final I_C_UOM cuUOM)
	{
		final ITUMergeBuilder mergeBuilder = new TUMergeBuilder(huContext);
		mergeBuilder.setSourceHUs(sourceHUs);
		mergeBuilder.setTargetTU(targetHU);

		mergeBuilder.setCUProductId(cuProductId);
		mergeBuilder.setCUQty(cuQty);
		mergeBuilder.setCUUOM(cuUOM);

		mergeBuilder.setCUTrxReferencedModel(targetHU);

		mergeBuilder.mergeTUs();
	}

	public ProductBOMBuilder newProductBOM()
	{
		return new ProductBOMBuilder()
				.setContext(contextProvider);
	}

	/**
	 * Commits {@link #trxName} and writes the given {@code hu} as XML to std-out. The commit might break some tests.
	 * Please only use this method temporarily to debug tests and comment it out again when the tests are fixed.
	 *
	 * @deprecated please only use temporarily for debugging.
	 */
	@Deprecated
	public void commitAndDumpHU(@NonNull final I_M_HU hu)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (!trxManager.isNull(trxName))
		{
			trxManager.commit(trxName);
		}

		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml(hu)));
	}

	/**
	 * Similar to {@link #commitAndDumpHU(I_M_HU)}.
	 */
	@Deprecated
	public void commitAndDumpHUs(@NonNull final List<I_M_HU> hus)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		if (!trxManager.isNull(trxName))
		{
			trxManager.commit(trxName);
		}

		System.out.println(HUXmlConverter.toString(HUXmlConverter.toXml("HUs", hus)));
	}

	public I_DIM_Dimension_Spec createDimensionSpec_PP_Order_ProductAttribute_To_Transfer()
	{
		return createDimensionSpec(HUConstants.DIM_PP_Order_ProductAttribute_To_Transfer);
	}

	public I_DIM_Dimension_Spec createDimensionSpec(final String internalName)
	{
		final I_DIM_Dimension_Spec dim = InterfaceWrapperHelper.newInstance(I_DIM_Dimension_Spec.class);
		dim.setInternalName(internalName);
		InterfaceWrapperHelper.save(dim);
		return dim;
	}
}
