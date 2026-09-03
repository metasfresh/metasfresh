package de.metas.deliveryplanning;

import de.metas.shipping.ShipperId;
import de.metas.shipping.ShipperTransportationDocSubTypeGuard;
import de.metas.document.dimension.DimensionService;
import de.metas.shipping.MPackageRepository;
import de.metas.shipping.ShipperRepository;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.mockito.Mockito;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the shipper-based delivery planning auto-create gate.
 *
 * Gate logic:
 * <ul>
 *     <li>a non-null, active shipper must exist</li>
 *     <li>the shipper's {@code IsCreateDeliveryPlanning} flag must be 'Y'</li>
 * </ul>
 */
public class DeliveryPlanningAutoCreateGateTest
{
	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	// ------------------------------------------------------------------ helpers

	@NonNull
	private ShipperId createShipper(final boolean isCreateDeliveryPlanning)
	{
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class);
		shipper.setName("TestShipper");
		shipper.setIsCreateDeliveryPlanning(isCreateDeliveryPlanning);
		InterfaceWrapperHelper.save(shipper);
		return ShipperId.ofRepoId(shipper.getM_Shipper_ID());
	}

	private DeliveryPlanningService buildService()
	{
		final ShipperRepository shipperRepository = new ShipperRepository();
		final DeliveryPlanningRepository repo = Mockito.mock(DeliveryPlanningRepository.class);
		final DeliveryPlanningAllocRepository allocRepo = new DeliveryPlanningAllocRepository();
		final DeliveryInstructionRepository instructionRepo = new DeliveryInstructionRepository(Mockito.mock(DimensionService.class));
		final DeliveryInstructionService instructionService = new DeliveryInstructionService(repo, allocRepo, instructionRepo, new MPackageRepository());
		final DeliveryStatusColorPaletteService colorPaletteService = Mockito.mock(DeliveryStatusColorPaletteService.class);
		final DimensionService dimensionService = Mockito.mock(DimensionService.class);
		final MeansOfTransportationService meansOfTransportationService = Mockito.mock(MeansOfTransportationService.class);
		final ShipperTransportationDocSubTypeGuard shipperTransportationDocSubTypeGuard = new ShipperTransportationDocSubTypeGuard();
		return new DeliveryPlanningService(shipperRepository, repo, allocRepo, instructionRepo, instructionService, colorPaletteService, dimensionService, meansOfTransportationService, shipperTransportationDocSubTypeGuard);
	}

	// ------------------------------------------------------------------ tests

	/**
	 * (a) shipper flag Y → delivery planning IS created (method returns true)
	 */
	@Test
	void isAutoCreateEnabled_withShipperFlagY_returnsTrue()
	{
		final ShipperId shipperId = createShipper(true);
		final DeliveryPlanningService service = buildService();

		final boolean result = service.isAutoCreateEnabled(shipperId);

		assertThat(result).isTrue();
	}

	/**
	 * (b) shipper flag N → NOT created (method returns false)
	 */
	@Test
	void isAutoCreateEnabled_withShipperFlagN_returnsFalse()
	{
		final ShipperId shipperId = createShipper(false);
		final DeliveryPlanningService service = buildService();

		final boolean result = service.isAutoCreateEnabled(shipperId);

		assertThat(result).isFalse();
	}

	/**
	 * (c) no shipper on the schedule → NOT created (method returns false)
	 */
	@Test
	void isAutoCreateEnabled_withNullShipper_returnsFalse()
	{
		final DeliveryPlanningService service = buildService();

		final boolean result = service.isAutoCreateEnabled(null);

		assertThat(result).isFalse();
	}
}
