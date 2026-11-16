package de.metas.handlingunits.picking.candidate.commands;

import de.metas.ad_reference.ADReferenceService;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.picking.PackToSpec;
import de.metas.handlingunits.picking.PickFrom;
import de.metas.handlingunits.picking.PickingCandidateRepository;
import de.metas.handlingunits.picking.requests.PickRequest;
import de.metas.inout.ShipmentScheduleId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Verifies the Product-Life-Cycle-Status pick guard in {@link PickHUCommand#performInTrx()}:
 * a product whose {@code M_Product.ProductLifeCycleStatus} blocks picking must abort the pick.
 * <p>
 * The guard is the first thing {@code performInTrx()} does after reading the product (before any
 * qty / HU-attribute / candidate logic), so a minimal {@link PickRequest} against a blocked product
 * reaches it deterministically. The permissive ("OK"/null status) path is covered without driving
 * the full command by {@code PickingJobProductServiceTest#assertPickAllowed_okStatus_doesNotThrow}.
 */
@ExtendWith(AdempiereTestWatcher.class)
public class PickHUCommand_LifeCycleStatus_Test
{
	private ProcessPickingCandidatesCommandTestHelper helper;
	private PickingCandidateRepository pickingCandidateRepository;
	private I_C_UOM uomEach;

	@BeforeEach
	public void beforeEach()
	{
		this.helper = new ProcessPickingCandidatesCommandTestHelper();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
		this.pickingCandidateRepository = helper.pickingCandidateRepository;
		this.uomEach = helper.uomEach;
	}

	private ProductId createProduct(final String lifeCycleStatus)
	{
		final ProductId productId = helper.createProduct("P1", uomEach);
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		product.setProductLifeCycleStatus(lifeCycleStatus);
		InterfaceWrapperHelper.saveRecord(product);
		return productId;
	}

	private void pick(final ProductId productId)
	{
		final ShipmentScheduleId shipmentScheduleId = helper.createShipmentSchedule(productId);
		final HuId pickFromVHUId = helper.createVHU(productId, Quantity.of("100", uomEach));

		PickHUCommand.builder()
				.pickingCandidateRepository(pickingCandidateRepository)
				.request(PickRequest.builder()
						.shipmentScheduleId(shipmentScheduleId)
						.pickFrom(PickFrom.ofHuId(pickFromVHUId))
						.qtyToPick(Quantity.of("10", uomEach))
						.packToSpec(PackToSpec.VIRTUAL)
						.build())
				.build()
				.perform();
	}

	@Test
	public void blockedStatus_pickThrows()
	{
		// "G" (Gesperrt / BLOCKED) blocks every ProductLifeCycleAction, including PICK.
		final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked);

		assertThatThrownBy(() -> pick(productId)).isInstanceOf(AdempiereException.class);
	}

	@Test
	public void doNotDeliverStatus_pickThrows()
	{
		// "N" (Lieferstopp / DO_NOT_DELIVER) blocks SHIP and PICK: the sale stays legitimate, but the goods
		// must not leave the warehouse. Pinned on THIS entry point too — the desktop pick-HU path and the
		// mobile picking job each call assertAllowed themselves, so a shared-matrix test alone would not
		// catch one of them being decoupled from IProductBL later.
		final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_DeliveryStop);

		assertThatThrownBy(() -> pick(productId)).isInstanceOf(AdempiereException.class);
	}
}
