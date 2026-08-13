package de.metas.deliveryplanning;

import org.adempiere.service.ClientId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.shipping.ShipperId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Shipper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the shipper-based delivery planning auto-create gate (AC-13a).
 *
 * Gate logic:
 * <ul>
 *     <li>sysconfig must be enabled (master-off switch)</li>
 *     <li>a non-null shipper must exist</li>
 *     <li>the shipper's {@code IsCreateDeliveryPlanning} flag must be 'Y'</li>
 * </ul>
 */
public class DeliveryPlanningAutoCreateGateTest
{
	private static final String SYSCONFIG_CREATE_AUTO = "de.metas.deliveryplanning.DeliveryPlanningService.M_Delivery_Planning_CreateAutomatically";

	private ClientAndOrgId clientAndOrgId;
	private ISysConfigBL sysConfigBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		clientAndOrgId = ClientAndOrgId.ofClientAndOrg(1, 0);
		sysConfigBL = Services.get(ISysConfigBL.class);
		// enable the sysconfig master-on globally unless a test overrides it
		sysConfigBL.setValue(SYSCONFIG_CREATE_AUTO, true, ClientId.ofRepoId(1), OrgId.ANY);
	}

	// ------------------------------------------------------------------ helpers

	@NonNull
	private ShipperId createShipper(final boolean isCreateDeliveryPlanning)
	{
		final I_M_Shipper shipper = InterfaceWrapperHelper.newInstance(I_M_Shipper.class);
		shipper.setIsCreateDeliveryPlanning(isCreateDeliveryPlanning);
		InterfaceWrapperHelper.save(shipper);
		return ShipperId.ofRepoId(shipper.getM_Shipper_ID());
	}

	private DeliveryPlanningService buildService()
	{
		// DeliveryPlanningService has one Spring-injected dependency that is irrelevant
		// for this test path; we use Mockito to satisfy the constructor.
		final DeliveryPlanningRepository repo = org.mockito.Mockito.mock(DeliveryPlanningRepository.class);
		final DeliveryStatusColorPaletteService colorPaletteService = org.mockito.Mockito.mock(DeliveryStatusColorPaletteService.class);
		final de.metas.document.dimension.DimensionService dimensionService = org.mockito.Mockito.mock(de.metas.document.dimension.DimensionService.class);
		final MeansOfTransportationService meansOfTransportationService = org.mockito.Mockito.mock(MeansOfTransportationService.class);
		return new DeliveryPlanningService(repo, colorPaletteService, dimensionService, meansOfTransportationService);
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

		final boolean result = service.isAutoCreateEnabled(clientAndOrgId, shipperId);

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

		final boolean result = service.isAutoCreateEnabled(clientAndOrgId, shipperId);

		assertThat(result).isFalse();
	}

	/**
	 * (c) no shipper on the schedule → NOT created (method returns false)
	 */
	@Test
	void isAutoCreateEnabled_withNullShipper_returnsFalse()
	{
		final DeliveryPlanningService service = buildService();

		final boolean result = service.isAutoCreateEnabled(clientAndOrgId, null);

		assertThat(result).isFalse();
	}

	/**
	 * Sysconfig master-off: even if shipper flag is Y, sysconfig=false must prevent creation.
	 */
	@Test
	void isAutoCreateEnabled_sysconfigOff_returnsFalse()
	{
		sysConfigBL.setValue(SYSCONFIG_CREATE_AUTO, false, ClientId.ofRepoId(1), OrgId.ANY);
		final ShipperId shipperId = createShipper(true);
		final DeliveryPlanningService service = buildService();

		final boolean result = service.isAutoCreateEnabled(clientAndOrgId, shipperId);

		assertThat(result).isFalse();
	}
}
