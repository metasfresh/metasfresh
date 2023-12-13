package de.metas.handlingunits.rest_api.move_hu;

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
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import java.util.List;
import java.util.function.Consumer;

public class MoveHUCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final HUTransformService huTransformService;
	private final HUQRCodesService huQRCodesService;

	private final List<HUIdAndQRCode> huIdAndQRCodes;
	private final GlobalQRCode targetQRCode;

	@Builder
	private MoveHUCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final List<HUIdAndQRCode> huIdAndQRCodes,
			@NonNull final GlobalQRCode targetQRCode)
	{
		this.huTransformService = HUTransformService.builder()
				.huQRCodesService(huQRCodesService)
				.build();

		this.huQRCodesService = huQRCodesService;
		this.huIdAndQRCodes = huIdAndQRCodes;
		this.targetQRCode = targetQRCode;

	}

	public void execute()
	{
		if (huIdAndQRCodes.isEmpty())
		{
			//nothing to move
			return;
		}

		trxManager.runInThreadInheritedTrx(this::executeInTrx);
	}

	private void executeInTrx()
	{
		if (LocatorQRCode.isTypeMatching(targetQRCode))
		{
			final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(targetQRCode);

			huMovementBL.moveHUs(HUMovementGenerateRequest.builder()
										 .toLocatorId(locatorQRCode.getLocatorId())
										 .huIdsToMove(extractHUIdsToMove())
										 .movementDate(SystemTime.asInstant())
										 .build());
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

		if (!handlingUnitsBL.isLoadingUnit(targetHU))
		{
			throw new AdempiereException("Invalid target HU! Expecting LU type, but got=" + handlingUnitsBL.getHU_UnitType(targetHU))
					.markAsUserValidationError();
		}

		return (huIdsToMove) -> {
			final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIdsToMove);
			huTransformService.tusToExistingLU(husToMove, targetHU);
		};
	}

	@NonNull
	private List<HuId> extractHUIdsToMove()
	{
		return huIdAndQRCodes.stream()
				.map(huIdAndQRCode -> huTransformService.extractToTopLevelByQRCode(huIdAndQRCode.getHuId(), huIdAndQRCode.getHuQRCode()))
				.collect(ImmutableList.toImmutableList());
	}
}
