package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableSetMultimap;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public class HUQRCodesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Optional<HUQRCodeAssignment> getHUAssignmentByQRCode(@NonNull final HUQRCode huQRCode)
	{
		return queryByQRCode(huQRCode.getId())
				.create()
				.firstOnlyOptional(I_M_HU_QRCode.class)
				.flatMap(HUQRCodesRepository::toHUQRCodeAssignment);
	}

	private IQueryBuilder<I_M_HU_QRCode> queryByQRCode(final @NonNull HUQRCodeUniqueId uniqueId)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_UniqueId, uniqueId.getAsString());
	}

	private static Optional<HUQRCodeAssignment> toHUQRCodeAssignment(final I_M_HU_QRCode record)
	{
		final HuId huId = HuId.ofRepoIdOrNull(record.getM_HU_ID());
		if (huId == null)
		{
			return Optional.empty();
		}

		final HUQRCodeUniqueId id = HUQRCodeUniqueId.ofJson(record.getUniqueId());
		return Optional.of(HUQRCodeAssignment.of(id, huId));
	}

	@NonNull
	public I_M_HU_QRCode createNew(@NonNull final HUQRCode qrCode, @Nullable final HuId huId)
	{
		final GlobalQRCode globalQRCode = qrCode.toGlobalQRCode();

		final I_M_HU_QRCode record = InterfaceWrapperHelper.newInstance(I_M_HU_QRCode.class);
		record.setUniqueId(qrCode.getId().getAsString());
		record.setDisplayableQRCode(qrCode.toDisplayableQRCode());
		record.setRenderedQRCode(globalQRCode.getAsString());
		// NOTE: this field is never used in our application. It's there mainly for reporting (if needed):
		record.setattributes(globalQRCode.getPayloadAsJson());

		// Assignment:
		record.setM_HU_ID(HuId.toRepoId(huId));

		InterfaceWrapperHelper.save(record);

		return record;
	}

	public void assign(@NonNull final HUQRCode qrCode, @NonNull final HuId huId)
	{
		final I_M_HU_QRCode existingRecord = queryByQRCode(qrCode.getId())
				.create()
				.firstOnly(I_M_HU_QRCode.class);
		if (existingRecord != null)
		{
			// NOTE: we assume the attributes and the other fields are correct.
			// we cannot update them anyways.

			existingRecord.setM_HU_ID(huId.getRepoId());
			InterfaceWrapperHelper.save(existingRecord);
		}
		else
		{
			createNew(qrCode, huId);
		}
	}

	public boolean isQRCodeAssignedToHU(@NonNull final HUQRCode qrCode, @NonNull final HuId huId)
	{
		return queryByQRCode(qrCode.getId())
				.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_ID, huId)
				.create()
				.anyMatch();
	}

	public Optional<HUQRCode> getFirstQRCodeByHuId(@NonNull final HuId huId)
	{
		return queryByHuId(huId)
				.create()
				.firstOptional(I_M_HU_QRCode.class)
				.map(HUQRCodesRepository::toHUQRCode);
	}

	private IQueryBuilder<I_M_HU_QRCode> queryByHuId(final @NonNull HuId sourceHuId)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_ID, sourceHuId)
				.orderBy(I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID);
	}

	public ImmutableSetMultimap<HuId, HUQRCode> getQRCodeByHuIds(@NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						record -> HuId.ofRepoId(record.getM_HU_ID()),
						HUQRCodesRepository::toHUQRCode));
	}

	private static HUQRCode toHUQRCode(final I_M_HU_QRCode record)
	{
		return HUQRCode.fromGlobalQRCodeJsonString(record.getRenderedQRCode());
	}

	public Stream<HUQRCode> streamQRCodesLike(@NonNull final String like)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(I_M_HU_QRCode.COLUMNNAME_RenderedQRCode, like, false)
				.orderBy(I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID)
				.create()
				.stream()
				.map(HUQRCodesRepository::toHUQRCode);
	}
}
