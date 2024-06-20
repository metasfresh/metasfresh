package de.metas.handlingunits.movement;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import java.util.List;
import java.util.function.Consumer;

public class MoveHUCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final HUTransformService huTransformService;
	private final HUQRCodesService huQRCodesService;

	private final List<HUIdAndQRCode> huIdAndQRCodes;
	private final GlobalQRCode targetQRCode;

	@Builder
	private MoveHUCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final List<HUIdAndQRCode> husToMove,
			@NonNull final GlobalQRCode targetQRCode)
	{
		this.huQRCodesService = huQRCodesService;
		this.huTransformService = HUTransformService.builder()
				.huQRCodesService(huQRCodesService)
				.build();

		this.huIdAndQRCodes = husToMove;
		this.targetQRCode = targetQRCode;

	}

	public void execute()
	{
		if (huIdAndQRCodes.isEmpty())
		{
			return;
		}

		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		if (LocatorQRCode.isTypeMatching(targetQRCode))
		{
			final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(targetQRCode);

			moveHUIdsToLocator(extractHUIdsToMove(), locatorQRCode.getLocatorId());
		}
		else if (HUQRCode.isTypeMatching(targetQRCode))
		{
			getMoveToTargetHUConsumer(HUQRCode.fromGlobalQRCode(targetQRCode))
					.accept(extractHUIdsToMove());
		}
		else
		{
			throw new AdempiereException("Move target not handled: " + targetQRCode);
		}
	}

	@NonNull
	private Consumer<List<HuId>> getMoveToTargetHUConsumer(@NonNull final HUQRCode huqrCode)
	{
		final I_M_HU targetHU = handlingUnitsBL.getById(huQRCodesService.getHuIdByQRCode(huqrCode));
		if (handlingUnitsBL.isDestroyed(targetHU))
		{
			handlingUnitsBL.reactivateDestroyedHU(targetHU, PlainContextAware.newWithThreadInheritedTrx());
		}

		if (handlingUnitsBL.isLoadingUnit(targetHU))
		{
			return getMoveToLUConsumer(targetHU);
		}
		else if (handlingUnitsBL.isTransportUnit(targetHU))
		{
			return getMoveToTUConsumer(targetHU);
		}
		else if (handlingUnitsBL.isVirtual(targetHU))
		{
			return getMoveToCUConsumer(targetHU);
		}
		else
		{
			throw new AdempiereException("Unsupported target!");
		}
	}

	@NonNull
	private Consumer<List<HuId>> getMoveToCUConsumer(final I_M_HU targetHU)
	{
		if (!handlingUnitsBL.isVirtual(targetHU))
		{
			throw new AdempiereException("Invalid target HU! Expecting CU type, but got=" + handlingUnitsBL.getHU_UnitType(targetHU));
		}

		final LocatorId locatorIdOfTargetHU = warehouseDAO.getLocatorIdByRepoId(targetHU.getM_Locator_ID());

		return (huIdsToMove) -> {
			final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIdsToMove);

			if (husToMove.stream().anyMatch(tu -> !handlingUnitsBL.isVirtual(tu)))
			{
				// keep in sync with misc/services/mobile-webui/mobile-webui-frontend/src/apps/huManager/containers/HUManagerScreen.jsx, see isAllowMove
				throw new AdempiereException("Expecting only CUs to be moved");
			}

			moveHUsToLocator(husToMove, locatorIdOfTargetHU);

			huTransformService.cusToExistingCU(husToMove, targetHU);
		};
	}

	@NonNull
	private Consumer<List<HuId>> getMoveToLUConsumer(final I_M_HU targetHU)
	{
		if (!handlingUnitsBL.isLoadingUnit(targetHU))
		{
			throw new AdempiereException("Invalid target HU! Expecting LU type, but got=" + handlingUnitsBL.getHU_UnitType(targetHU));
		}

		final LocatorId locatorIdOfTargetHU = warehouseDAO.getLocatorIdByRepoId(targetHU.getM_Locator_ID());

		return (huIdsToMove) -> {
			final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIdsToMove);

			if (husToMove.stream().anyMatch(tu -> !handlingUnitsBL.isTransportUnit(tu)))
			{
				throw new AdempiereException("Expecting only TUs to be moved");
			}

			moveHUsToLocator(husToMove, locatorIdOfTargetHU);

			huTransformService.tusToExistingLU(husToMove, targetHU);
		};
	}

	@NonNull
	private Consumer<List<HuId>> getMoveToTUConsumer(final I_M_HU targetHU)
	{
		if (!handlingUnitsBL.isTransportUnit(targetHU))
		{
			throw new AdempiereException("Invalid target HU! Expecting TU type, but got=" + handlingUnitsBL.getHU_UnitType(targetHU));
		}

		final LocatorId locatorIdOfTargetHU = warehouseDAO.getLocatorIdByRepoId(targetHU.getM_Locator_ID());

		return (huIdsToMove) -> {
			final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIdsToMove);

			if (husToMove.stream().anyMatch(tu -> !handlingUnitsBL.isVirtual(tu)))
			{
				throw new AdempiereException("Expecting only CUs to be moved");
			}

			moveHUsToLocator(husToMove, locatorIdOfTargetHU);

			huTransformService.cusToExistingTU(husToMove, targetHU);
		};
	}

	@NonNull
	private List<HuId> extractHUIdsToMove()
	{
		return huIdAndQRCodes.stream()
				.map(huIdAndQRCode -> huTransformService.extractToTopLevel(huIdAndQRCode.getHuId(), huIdAndQRCode.getHuQRCode()))
				.collect(ImmutableList.toImmutableList());
	}

	private void moveHUIdsToLocator(@NonNull final List<HuId> huIds, @NonNull final LocatorId locatorId)
	{
		final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIds);
		moveHUsToLocator(husToMove, locatorId);
	}

	private void moveHUsToLocator(final @NonNull List<I_M_HU> husToMove, @NonNull final LocatorId locatorId)
	{
		final List<HuId> huIdsFromDiffLocator = husToMove.stream()
				.filter(hu -> hu.getM_Locator_ID() != locatorId.getRepoId())
				.map(I_M_HU::getM_HU_ID)
				.map(HuId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		if (huIdsFromDiffLocator.isEmpty())
		{
			return;
		}

		huMovementBL.moveHUs(HUMovementGenerateRequest.builder()
									 .toLocatorId(locatorId)
									 .huIdsToMove(huIdsFromDiffLocator)
									 .movementDate(SystemTime.asInstant())
									 .build());
	}
}
