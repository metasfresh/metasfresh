package de.metas.handlingunits.rest_api.move_hu;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.qrcode.LocatorQRCode;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MoveHUCommand
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final HUTransformService huTransformService;
	private final HUQRCodesService huQRCodesService;

	private final List<MoveHURequestItem> requestItems;
	private final GlobalQRCode targetQRCode;

	@Builder
	private MoveHUCommand(
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final List<MoveHURequestItem> requestItems,
			@NonNull final GlobalQRCode targetQRCode)
	{
		this.huTransformService = HUTransformService.builder()
				.huQRCodesService(huQRCodesService)
				.build();

		this.huQRCodesService = huQRCodesService;
		this.requestItems = requestItems;
		this.targetQRCode = targetQRCode;

	}

	public ImmutableSet<HuId> execute()
	{
		if (requestItems.isEmpty())
		{
			//nothing to move
			return ImmutableSet.of();
		}

		return trxManager.callInThreadInheritedTrx(this::executeInTrx);
	}

	private ImmutableSet<HuId> executeInTrx()
	{
		if (LocatorQRCode.isTypeMatching(targetQRCode))
		{
			final LocatorQRCode locatorQRCode = LocatorQRCode.ofGlobalQRCode(targetQRCode);

			return moveHUIdsToLocator(extractHUIdsToMove(), locatorQRCode.getLocatorId());
		}
		else if (HUQRCode.isTypeMatching(targetQRCode))
		{
			return getMoveToTargetHUFunction(HUQRCode.fromGlobalQRCode(targetQRCode))
					.apply(extractHUIdsToMove());
		}
		else
		{
			throw new AdempiereException("Move target not handled: " + targetQRCode);
		}
	}

	@NonNull
	private Function<List<HuId>, ImmutableSet<HuId>> getMoveToTargetHUFunction(@NonNull final HUQRCode huqrCode)
	{
		final I_M_HU targetHU = handlingUnitsBL.getById(huQRCodesService.getHuIdByQRCode(huqrCode));

		if (!handlingUnitsBL.isLoadingUnit(targetHU))
		{
			throw new AdempiereException("Invalid target HU! Expecting LU type, but got=" + handlingUnitsBL.getHU_UnitType(targetHU))
					.markAsUserValidationError();
		}

		final LocatorId locatorIdOfTargetHU = warehouseDAO.getLocatorIdByRepoId(targetHU.getM_Locator_ID());

		return (huIdsToMove) -> {
			final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIdsToMove);
			moveHUsToLocator(husToMove, locatorIdOfTargetHU);

			huTransformService.tusToExistingLU(husToMove, targetHU);

			return ImmutableSet.copyOf(huIdsToMove);
		};
	}

	@NonNull
	private List<HuId> extractHUIdsToMove()
	{
		return requestItems.stream()
				.flatMap(requestItem -> {
					if (requestItem.getNumberOfTUs() == null || requestItem.getNumberOfTUs().isOne())
					{
						final HuId splitHuId = huTransformService.extractToTopLevelByQRCode(requestItem.getHuIdAndQRCode().getHuId(),
																							requestItem.getHuIdAndQRCode().getHuQRCode());
						return Stream.of(splitHuId);
					}
					return huTransformService.extractFromAggregatedByQrCode(requestItem.getHuIdAndQRCode().getHuId(),
																			requestItem.getHuIdAndQRCode().getHuQRCode(),
																			requestItem.getNumberOfTUs())
							.stream();
				})
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private ImmutableSet<HuId> moveHUIdsToLocator(@NonNull final List<HuId> huIds, @NonNull final LocatorId locatorId)
	{
		final List<I_M_HU> husToMove = handlingUnitsBL.getByIds(huIds);
		moveHUsToLocator(husToMove, locatorId);
		return ImmutableSet.copyOf(huIds);
	}

	private void moveHUsToLocator(final @NonNull List<I_M_HU> husToMove, @NonNull final LocatorId locatorId)
	{
		final Map<Integer, List<HuId>> diffLocatorToHuIds = husToMove.stream()
				.filter(hu -> hu.getM_Locator_ID() != locatorId.getRepoId())
				.collect(Collectors.groupingBy(I_M_HU::getM_Locator_ID,
											   Collectors.mapping(hu -> HuId.ofRepoId(hu.getM_HU_ID()),
																  Collectors.toList())));

		if (diffLocatorToHuIds.isEmpty())
		{
			return;
		}

		diffLocatorToHuIds.values()
				.forEach(huIdsSharingTheSameLocator -> huMovementBL.moveHUs(HUMovementGenerateRequest.builder()
																					.toLocatorId(locatorId)
																					.huIdsToMove(huIdsSharingTheSameLocator)
																					.movementDate(SystemTime.asInstant())
																					.build()));
	}
}
