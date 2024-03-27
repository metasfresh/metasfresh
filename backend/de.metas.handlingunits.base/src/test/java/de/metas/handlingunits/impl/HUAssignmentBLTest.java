package de.metas.handlingunits.impl;

import com.google.common.collect.ImmutableList;
import de.metas.ad_reference.ADReferenceService;
import de.metas.distribution.ddorder.DDOrderService;
import de.metas.distribution.ddorder.interceptor.DD_Order;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelService;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleRepository;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.event.IEventBusFactory;
import de.metas.event.impl.PlainEventBusFactory;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.IHUAssignmentDAO;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesRepository;
import de.metas.handlingunits.attribute.impl.HUUniqueAttributesService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.reservation.HUReservationRepository;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.filter.GenerateReceiptScheduleForModelAggregateFilter;
import de.metas.inoutcandidate.picking_bom.PickingBOMService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.resource.ResourceService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_Test;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class HUAssignmentBLTest
{
	@Nullable private String trxName;
	private IContextAware contextProvider;

	private IHUAssignmentBL huAssignmentBL;
	private IHUAssignmentDAO huAssignmentDAO;

	private I_Test record;
	private I_M_HU hu;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		POJOWrapper.setDefaultStrictValues(false);

		final ReceiptScheduleProducerFactory receiptScheduleProducerFactory = new ReceiptScheduleProducerFactory(new GenerateReceiptScheduleForModelAggregateFilter(ImmutableList.of()));
		Services.registerService(IReceiptScheduleProducerFactory.class, receiptScheduleProducerFactory);
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		SpringContextHolder.registerJUnitBean(IEventBusFactory.class, PlainEventBusFactory.newInstance());

		SpringContextHolder.registerJUnitBean(IEventBusFactory.class, PlainEventBusFactory.newInstance());
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));

		//
		// Make sure Main handling units interceptor is registered
		final DDOrderLowLevelDAO ddOrderLowLevelDAO = new DDOrderLowLevelDAO();
		final HUReservationService huReservationService = new HUReservationService(new HUReservationRepository());
		final DDOrderMoveScheduleService ddOrderMoveScheduleService = new DDOrderMoveScheduleService(
				ddOrderLowLevelDAO,
				new DDOrderMoveScheduleRepository(),
				ADReferenceService.newMocked(),
				huReservationService);
		final DDOrderLowLevelService ddOrderLowLevelService = new DDOrderLowLevelService(ddOrderLowLevelDAO, ResourceService.newInstanceForJUnitTesting());
		final DDOrderService ddOrderService = new DDOrderService(ddOrderLowLevelDAO, ddOrderLowLevelService, ddOrderMoveScheduleService);
		final HUUniqueAttributesService huUniqueAttributesService = new HUUniqueAttributesService(new HUUniqueAttributesRepository());
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new de.metas.handlingunits.model.validator.Main(
				ddOrderMoveScheduleService,
				ddOrderService,
				new PickingBOMService(),
				huUniqueAttributesService));
		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new DD_Order(ddOrderMoveScheduleService, ddOrderService));

		//
		// BL under test
		huAssignmentBL = Services.get(IHUAssignmentBL.class);
		huAssignmentDAO = Services.get(IHUAssignmentDAO.class);

		//
		// Setup ctx and trxName
		final Properties ctx = Env.getCtx();
		trxName = ITrx.TRXNAME_None;
		contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);

		//
		// Create a dummy record
		record = newInstance(I_Test.class, contextProvider);
		saveRecord(record);

		//
		// Virtual PI
		final I_M_HU_PI virtualPI = newInstance(I_M_HU_PI.class);
		virtualPI.setM_HU_PI_ID(HuPackingInstructionsId.VIRTUAL.getRepoId());
		saveRecord(virtualPI);
		//
		final I_M_HU_PI_Version virtualPIVersion = newInstance(I_M_HU_PI_Version.class);
		virtualPIVersion.setM_HU_PI_ID(virtualPI.getM_HU_PI_ID());
		virtualPIVersion.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		virtualPIVersion.setIsCurrent(true);
		saveRecord(virtualPIVersion);

		//
		// Create a dummy HU
		hu = createHU();
	}

	private I_M_HU createHU()
	{
		final I_M_HU hu = newInstance(I_M_HU.class, contextProvider);
		hu.setM_HU_PI_Version_ID(HuPackingInstructionsVersionId.VIRTUAL.getRepoId());
		saveRecord(hu);
		return hu;
	}

	/**
	 * Assert {@link #hu} is the only assigned HU to {@link #record}
	 */
	private void assertHUAssigned()
	{
		final List<I_M_HU> husAssignedActual = huAssignmentDAO.retrieveTopLevelHUsForModel(record);
		assertThat(husAssignedActual).containsExactly(hu);
	}

	/**
	 * Assert there are no HUs assigned to {@link #record}.
	 */
	private void assertNoHUsAssigned()
	{
		final List<I_M_HU> husAssignedActual = huAssignmentDAO.retrieveTopLevelHUsForModel(record);
		assertThat(husAssignedActual).isEmpty();
	}

	/**
	 * Simple test to validate that assignment is made correctly and listeners are triggered
	 */
	@Test
	public void testAssign()
	{
		assertNoHUsAssigned();

		final MockedHUAssignmentListener listener = new MockedHUAssignmentListener();
		huAssignmentBL.registerHUAssignmentListener(listener);

		listener.expectHUAssign(hu, record);

		huAssignmentBL.assignHU(record, hu, trxName);

		listener.assertExpectationsMatched();

		assertHUAssigned();
	}

	/**
	 * Simple test to validate that un-assignment is made correctly and listeners are triggered
	 */
	@Test
	public void testUnassign()
	{
		assertNoHUsAssigned();

		//
		// Make sure HU is assigned to our record
		final List<I_M_HU> husAsList = Collections.singletonList(hu);
		huAssignmentBL.assignHUs(record, husAsList, trxName);
		assertHUAssigned();

		final MockedHUAssignmentListener listener = new MockedHUAssignmentListener();
		huAssignmentBL.registerHUAssignmentListener(listener);

		listener.expectHUUnassign(hu, record);
		final Set<HuId> huIds = husAsList.stream()
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(Collectors.toSet());
		huAssignmentBL.unassignHUs(record, huIds, trxName);

		listener.assertExpectationsMatched();

		assertNoHUsAssigned();
	}

}
