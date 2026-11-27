package de.metas.handlingunits.picking.job.service.commands.pick;

import de.metas.handlingunits.HUContextHolder;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TU;
import de.metas.handlingunits.allocation.transfer.LUTUResult.TUPart;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryMode;
import de.metas.handlingunits.picking.config.mobileui.CUQtyEntryPolicy;
import de.metas.handlingunits.picking.job.model.CULabelPackId;
import de.metas.handlingunits.picking.job.model.PickingJob;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.handlingunits.picking.job.service.PickingCULabelService;
import de.metas.handlingunits.picking.job.service.ResolvedCULabel;
import de.metas.handlingunits.picking.job.service.external.hu.PickingJobHUService;
import de.metas.logging.LogManager;
import de.metas.scannable_code.ScannedCode;
import de.metas.scannable_code.format.ScannableCodeFormatId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * The steps of one pick that depend on the CU quantity entry policy of the line: resolving the scanned CU label,
 * the open-TU and duplicate-label checks, the pick attributes read from the label, stamping the pack ID on the
 * created CUs and the QR-code owner of the picked HU. {@link PickingJobPickCommand} builds one per pick and asks it
 * for these steps; the other rules of the policy (whole TU, EACH unit, CU quantity, over-pick, single-CU booking, refusal message) stay in the command.
 */
final class CULabelBooking
{
	private static final Logger logger = LogManager.getLogger(CULabelBooking.class);

	@NonNull private final CUQtyEntryMode mode;
	@NonNull private final CUQtyEntryPolicy policy;
	@NonNull private final PickingCULabelService cuLabelService;
	@NonNull private final PickingJobHUService huService;
	@NonNull private final Supplier<PickingJobLine> lineSupplier;
	@Nullable private final ScannedCode cuLabel;
	@NonNull private final Optional<ScannableCodeFormatId> cuLabelFormatId;

	@Nullable private ResolvedCULabel _resolvedCULabel; // lazy, label-required modes only

	@Builder
	private CULabelBooking(
			@NonNull final CUQtyEntryMode mode,
			@NonNull final PickingCULabelService cuLabelService,
			@NonNull final PickingJobHUService huService,
			@NonNull final Supplier<PickingJobLine> lineSupplier,
			@Nullable final ScannedCode cuLabel,
			@NonNull final Optional<ScannableCodeFormatId> cuLabelFormatId)
	{
		this.mode = mode;
		this.policy = mode.getPolicy();
		this.cuLabelService = cuLabelService;
		this.huService = huService;
		this.lineSupplier = lineSupplier;
		this.cuLabel = cuLabel;
		this.cuLabelFormatId = cuLabelFormatId;
	}

	/** Refuses a booking without an open TU, or of a label already picked in the job (when the pack ID is used). No-op when no label is required; the open-TU supplier is then not called. */
	void assertBookingAllowed(@NonNull final PickingJob pickingJob, @NonNull final BooleanSupplier isOpenTUPresent)
	{
		if (!policy.isLabelRequired())
		{
			return;
		}

		if (!isOpenTUPresent.getAsBoolean())
		{
			throw new AdempiereException(PickingJobPickCommand.CU_NO_OPEN_TU_ERROR_MSG);
		}

		final ResolvedCULabel label = getResolvedCULabel();
		if (policy.isPackIdRequired() && huService.isAlreadyPickedInJob(pickingJob, label.toCUPackIdentity()))
		{
			throw new AdempiereException(PickingCULabelService.MSG_CU_LABEL_ALREADY_PICKED, label.getPackId());
		}
	}

	/** The lot and best-before date of the label (when one is required), else the manually entered ones. */
	@NonNull
	PickAttributes getPickAttributes(@NonNull final PickAttributes manualPickAttributes)
	{
		return policy.isLabelRequired()
				? toPickAttributes(getResolvedCULabel()).fallbackTo(manualPickAttributes)
				: manualPickAttributes;
	}

	/** Stamps the label's pack ID on the CUs created by the pick. No-op when the pack ID is not used. */
	void stampPackId(@NonNull final LUTUResult packedHUs)
	{
		if (!policy.isPackIdRequired())
		{
			return;
		}

		final CULabelPackId packId = getResolvedCULabel().toCUPackIdentity().getPackId();
		for (final TU tu : packedHUs.getAllTUs())
		{
			for (final TUPart cu : tu.getCUsNotEmpty())
			{
				final IAttributeStorage cuAttributes = HUContextHolder.getCurrent().getHUAttributeStorageFactory().getAttributeStorage(cu.toHU());
				cuAttributes.setSaveOnChange(true);
				cuAttributes.setValue(AttributeConstants.PackId, packId.getValue());
			}
		}
	}

	@NonNull
	private ResolvedCULabel getResolvedCULabel()
	{
		if (_resolvedCULabel == null)
		{
			if (cuLabel == null)
			{
				// a contract violation, not an operator error: the mobile app always sends the scanned label in these modes
				// (reachable with a cached app version after the profile was switched to such a mode): the picker gets the localized message, the technical reason goes to the log
				logger.warn("Pick refused: a CU label is required for a pick in the CU quantity entry mode {}, but the pick came without one", mode);
				throw new AdempiereException(PickingCULabelService.MSG_CU_LABEL_REQUIRED);
			}
			_resolvedCULabel = cuLabelService.resolve(lineSupplier.get(), cuLabel, policy, cuLabelFormatId);
		}
		return _resolvedCULabel;
	}

	@NonNull
	private static PickAttributes toPickAttributes(@NonNull final ResolvedCULabel label)
	{
		return PickAttributes.builder()
				.isSetLotNo(label.getLotNo() != null).lotNo(label.getLotNo())
				.isSetBestBeforeDate(label.getBestBeforeDate() != null).bestBeforeDate(label.getBestBeforeDate())
				.build();
	}
}
