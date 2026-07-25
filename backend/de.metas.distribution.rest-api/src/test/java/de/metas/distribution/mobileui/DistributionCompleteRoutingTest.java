package de.metas.distribution.mobileui;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.mobileui.external_services.product.ProductInfo;
import de.metas.distribution.mobileui.external_services.warehouse.LocatorInfo;
import de.metas.distribution.mobileui.external_services.warehouse.WarehouseInfo;
import de.metas.distribution.mobileui.job.model.DistributionJob;
import de.metas.distribution.mobileui.job.model.DistributionJobId;
import de.metas.distribution.mobileui.job.model.DistributionJobLine;
import de.metas.distribution.mobileui.job.model.DistributionJobLineId;
import de.metas.distribution.mobileui.job.service.DistributionRestService;
import de.metas.distribution.mobileui.rest_api.DistributionRestController;
import de.metas.distribution.mobileui.workflows_api.activity_handlers.CompleteDistributionWFActivityHandler;
import de.metas.i18n.TranslatableStrings;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.rest_api.activity_features.user_confirmation.UserConfirmationRequest;
import de.metas.workflow.rest_api.model.WFActivity;
import de.metas.workflow.rest_api.model.WFActivityId;
import de.metas.workflow.rest_api.model.WFProcess;
import de.metas.workflow.rest_api.model.WFProcessId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.web.bind.annotation.PostMapping;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * Covers the <b>wiring</b> of the mobile distribution completion — not its arithmetic, which is
 * {@code DistributionJobLinePlannedQtyTest}'s job.
 *
 * <p>Two properties are pinned here:</p>
 * <ol>
 *     <li><b>The give-up-the-remainder route is reachable by a client</b>: a REST mapping exists, it goes through
 *     {@link DistributionMobileApplication} and lands on {@link DistributionRestService#completeGivingUpRemainder}.
 *     The service method shipped once as an orphan nothing called, which left a mover facing a stock shortfall with
 *     no completion path at all — an unreachable route is not a feature.</li>
 *     <li><b>The strict gate stays the default</b>: the mover's Complete confirmation must not reach the
 *     unconditional close, and must never give the remainder up on its own. Silently closing a shared order short
 *     drops the demand of every other contributor.</li>
 * </ol>
 *
 * <p>The mover's end-to-end experience is decided by the mobile Playwright spec; this test only pins the wiring a
 * green Playwright run cannot see.</p>
 */
@ExtendWith(AdempiereTestWatcher.class)
class DistributionCompleteRoutingTest
{
	private static final UserId MOVER = UserId.ofRepoId(1234);

	private I_C_UOM uom;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uom = BusinessTestHelper.createUOM("Stk", 0, 0);
	}

	@Test
	void giveUpRemainderRouteIsReachableByAClient()
	{
		assertThat(postMappingPaths(DistributionRestController.class))
				.as("DistributionRestController must expose a POST mapping through which a client can give the remainder up")
				.anyMatch(path -> path.contains("completeGivingUpRemainder"));

		assertThat(publicMethod(DistributionMobileApplication.class, "completeGivingUpRemainder", WFProcessId.class, UserId.class))
				.as("DistributionMobileApplication must carry the give-up route from the controller to the service")
				.isNotNull();

		assertThat(publicMethod(DistributionRestService.class, "completeGivingUpRemainder", DistributionJobId.class, UserId.class))
				.as("DistributionRestService must expose the give-up route as an explicit, separately named action")
				.isNotNull();
	}

	@Test
	void moverConfirmingComplete_neverClosesTheJobShortOnItsOwn()
	{
		final DistributionJob job = jobWithOneLineShortBy9();

		// Default answer: every service call returns the job, so mapDocument() gets a non-null document back
		// whichever completion method the handler picks.
		final DistributionRestService restService = mock(DistributionRestService.class, invocation -> job);

		new CompleteDistributionWFActivityHandler(restService).userConfirmed(UserConfirmationRequest.builder()
				.wfProcess(wfProcessOf(job))
				.wfActivity(completeActivity())
				.build());

		verify(restService).completeAssertingPlannedQtyFullyMoved(job);
		verify(restService, never()).complete(any(DistributionJob.class));
		verify(restService, never()).completeGivingUpRemainder(any(DistributionJobId.class), any(UserId.class));
	}

	private static ImmutableList<String> postMappingPaths(final Class<?> controllerClass)
	{
		return Arrays.stream(controllerClass.getDeclaredMethods())
				.map(method -> method.getAnnotation(PostMapping.class))
				.filter(java.util.Objects::nonNull)
				.flatMap(postMapping -> Arrays.stream(postMapping.value()))
				.collect(ImmutableList.toImmutableList());
	}

	private static Method publicMethod(final Class<?> clazz, final String name, final Class<?>... parameterTypes)
	{
		try
		{
			return clazz.getMethod(name, parameterTypes);
		}
		catch (final NoSuchMethodException e)
		{
			return null;
		}
	}

	private static WFProcess wfProcessOf(final DistributionJob job)
	{
		return WFProcess.builder()
				.id(job.getId().toWFProcessId())
				.responsibleId(job.getResponsibleId())
				.document(job)
				.activities(ImmutableList.of(completeActivity()))
				.build();
	}

	private static WFActivity completeActivity()
	{
		return WFActivity.builder()
				.id(WFActivityId.ofString("Complete"))
				.caption(TranslatableStrings.anyLanguage("Complete"))
				.wfActivityType(CompleteDistributionWFActivityHandler.HANDLED_ACTIVITY_TYPE)
				.build();
	}

	private DistributionJob jobWithOneLineShortBy9()
	{
		final ZonedDateTime when = ZonedDateTime.of(2026, 1, 1, 0, 0, 0, 0, ZoneId.of("UTC"));
		return DistributionJob.builder()
				.id(DistributionJobId.ofDDOrderId(DDOrderId.ofRepoId(5555)))
				.documentNo("DD-1")
				.seqNo(SeqNo.ofInt(10))
				.customerId(BPartnerId.ofRepoId(2222))
				.dateRequired(when)
				.pickDate(when)
				.pickFromWarehouse(WarehouseInfo.builder().warehouseId(WarehouseId.ofRepoId(100)).caption("PickFromWH").build())
				.dropToWarehouse(WarehouseInfo.builder().warehouseId(WarehouseId.ofRepoId(200)).caption("DropToWH").build())
				.priority("5")
				.responsibleId(MOVER)
				.isClosed(false)
				.allowPickingAnyHU(false)
				.lines(ImmutableList.of(lineToMove15()))
				.build();
	}

	private DistributionJobLine lineToMove15()
	{
		return DistributionJobLine.builder()
				.id(DistributionJobLineId.ofDDOrderLineId(DDOrderLineId.ofRepoId(1)))
				.product(ProductInfo.builder()
						.productId(ProductId.ofRepoId(1000001))
						.caption(TranslatableStrings.anyLanguage("P1"))
						.build())
				.qtyToMove(Quantity.of("15", uom))
				.pickFromLocator(locator(201))
				.dropToLocator(locator(301))
				.steps(ImmutableList.of())
				.build();
	}

	private static LocatorInfo locator(final int locId)
	{
		final LocatorId locatorId = LocatorId.ofRepoId(100, locId);
		return LocatorInfo.builder()
				.locatorId(locatorId)
				.qrCode(LocatorQRCode.builder().locatorId(locatorId).caption("L" + locId).build())
				.caption("L" + locId)
				.build();
	}
}
