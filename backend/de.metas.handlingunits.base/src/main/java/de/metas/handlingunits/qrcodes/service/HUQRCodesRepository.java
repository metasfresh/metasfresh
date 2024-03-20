package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU_QRCode;
import de.metas.handlingunits.model.I_M_HU_QRCode_Assignment;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAssignment;
import de.metas.handlingunits.qrcodes.model.HUQRCodeRepoId;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUniqueId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Stream;

@Repository
public class HUQRCodesRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public Optional<HUQRCodeAssignment> getHUAssignmentByQRCode(@NonNull final HUQRCode huQRCode)
	{
		return getByUniqueId(huQRCode.getId())
				.flatMap(this::toHUQRCodeAssignment);
	}

	private IQueryBuilder<I_M_HU_QRCode> queryByQRCode(final @NonNull HUQRCodeUniqueId uniqueId)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_QRCode.COLUMNNAME_UniqueId, uniqueId.getAsString());
	}

	private Optional<HUQRCodeAssignment> toHUQRCodeAssignment(final I_M_HU_QRCode record)
	{
		return toHUQRCodeAssignment(record, getAssignedHuIds(ImmutableSet.of(HUQRCodeRepoId.ofRepoId(record.getM_HU_QRCode_ID()))));
	}

	@NonNull
	private Optional<HUQRCodeAssignment> toHUQRCodeAssignment(
			@NonNull final I_M_HU_QRCode record,
			@NonNull final ImmutableSetMultimap<HUQRCodeRepoId, HuId> qrCodeId2HuIds)
	{
		final ImmutableSet<HuId> huIds = qrCodeId2HuIds.get(HUQRCodeRepoId.ofRepoId(record.getM_HU_QRCode_ID()));
		if (Check.isEmpty(huIds))
		{
			return Optional.empty();
		}

		final HUQRCodeUniqueId id = HUQRCodeUniqueId.ofJson(record.getUniqueId());
		return Optional.of(HUQRCodeAssignment.of(id, huIds));
	}

	@NonNull
	public I_M_HU_QRCode createNew(@NonNull final HUQRCode qrCode, @Nullable final HuId huId)
	{
		return createNew(qrCode, Optional.ofNullable(huId).map(ImmutableSet::of).orElse(null));
	}

	@NonNull
	public I_M_HU_QRCode createNew(@NonNull final HUQRCode qrCode, @Nullable final ImmutableSet<HuId> huIds)
	{
		final GlobalQRCode globalQRCode = qrCode.toGlobalQRCode();

		final I_M_HU_QRCode record = InterfaceWrapperHelper.newInstance(I_M_HU_QRCode.class);
		record.setUniqueId(qrCode.getId().getAsString());
		record.setDisplayableQRCode(qrCode.toDisplayableQRCode());
		record.setRenderedQRCode(globalQRCode.getAsString());
		// NOTE: this field is never used in our application. It's there mainly for reporting (if needed):
		record.setattributes(globalQRCode.getPayloadAsJson());

		InterfaceWrapperHelper.save(record);

		if (huIds != null)
		{
			createAssignments(record, huIds);
		}

		return record;
	}

	public void assign(@NonNull final HUQRCode qrCode, @NonNull final HuId huId)
	{
		assign(qrCode, ImmutableSet.of(huId));
	}

	public void assign(@NonNull final HUQRCode qrCode, @NonNull final ImmutableSet<HuId> huIds)
	{
		final I_M_HU_QRCode existingRecord = queryByQRCode(qrCode.getId())
				.create()
				.firstOnly(I_M_HU_QRCode.class);
		if (existingRecord != null)
		{
			// NOTE: we assume the attributes and the other fields are correct.
			// we cannot update them anyways.

			createAssignments(existingRecord, huIds);
		}
		else
		{
			createNew(qrCode, huIds);
		}
	}

	public void removeAssignment(@NonNull final HUQRCode qrCode, @NonNull final ImmutableSet<HuId> huIdsToRemove)
	{
		final I_M_HU_QRCode existingRecord = getByUniqueId(qrCode.getId()).orElse(null);
		if (existingRecord == null)
		{
			//nothing to be done
			return;
		}

		removeAssignment(existingRecord, huIdsToRemove);
	}

	public boolean isQRCodeAssignedToHU(@NonNull final HUQRCode qrCode, @NonNull final HuId huId)
	{
		return getHUAssignmentByQRCode(qrCode)
				.map(huQrCodeAssignment -> huQrCodeAssignment.isAssignedToHuId(huId))
				.orElse(false);
	}

	public Optional<HUQRCode> getFirstQRCodeByHuId(@NonNull final HuId huId)
	{
		return queryByHuId(huId)
				.create()
				.firstOptional(I_M_HU_QRCode.class)
				.map(HUQRCodesRepository::toHUQRCode);
	}

	public Optional<HUQRCode> getSingleQRCodeByHuIdOrEmpty(@NonNull final HuId huId)
	{
		final List<I_M_HU_QRCode> records = queryByHuId(huId)
				.setLimit(QueryLimit.TWO)
				.create()
				.list();

		return records.size() == 1
				? Optional.of(toHUQRCode(records.get(0)))
				: Optional.empty();
	}

	@NonNull
	public Stream<HUQRCodeAssignment> streamAssignmentsForDisplayableQrCode(@NonNull final String displayableQrCode)
	{
		final ImmutableList<I_M_HU_QRCode> qrCodes = queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_DisplayableQRCode, displayableQrCode)
				.create()
				.listImmutable(I_M_HU_QRCode.class);

		return toHUQRCodeAssignment(qrCodes);
	}

	private IQueryBuilder<I_M_HU_QRCode> queryByHuId(final @NonNull HuId sourceHuId)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_QRCode_Assignment.COLUMNNAME_M_HU_ID, sourceHuId)
				.andCollect(I_M_HU_QRCode_Assignment.COLUMN_M_HU_QRCode_ID)
				.orderBy(I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID);
	}

	public ImmutableSetMultimap<HuId, HUQRCode> getQRCodeByHuIds(@NonNull final Collection<HuId> huIds)
	{
		if (huIds.isEmpty())
		{
			return ImmutableSetMultimap.of();
		}

		final List<I_M_HU_QRCode_Assignment> assignments = getAssignmentsByHuIds(ImmutableSet.copyOf(huIds));
		final ImmutableSet<HUQRCodeRepoId> qrCodeIds = assignments.stream()
				.map(I_M_HU_QRCode_Assignment::getM_HU_QRCode_ID)
				.map(HUQRCodeRepoId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableMap<HUQRCodeRepoId, I_M_HU_QRCode> id2QrCode = getByIds(qrCodeIds);

		return assignments.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(record -> HuId.ofRepoId(record.getM_HU_ID()),
																	 record -> toHUQRCode(id2QrCode.get(HUQRCodeRepoId.ofRepoId(record.getM_HU_QRCode_ID())))));
	}

	@NonNull
	public List<HUQRCodeAssignment> getHUAssignmentsByQRCode(@NonNull final Collection<HUQRCode> huQRCodes)
	{
		if (huQRCodes.isEmpty())
		{
			return ImmutableList.of();
		}

		final Set<String> qrCodeIds = huQRCodes.stream()
				.map(HUQRCode::getId)
				.map(HUQRCodeUniqueId::getAsString)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableList<I_M_HU_QRCode> huQrCodes = queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_UniqueId, qrCodeIds)
				.create()
				.listImmutable(I_M_HU_QRCode.class);

		final List<HUQRCodeAssignment> huQrCodeAssignments = toHUQRCodeAssignment(huQrCodes).collect(ImmutableList.toImmutableList());

		if (qrCodeIds.size() != huQrCodeAssignments.size())
		{
			throw new AdempiereException("HuQRCodeAssignment not found for some of the QRCodes!")
					.appendParametersToMessage()
					.setParameter("huQrCodeAssignments", huQrCodeAssignments)
					.setParameter("qrCodeIds", qrCodeIds);
		}
		return huQrCodeAssignments;
	}

	@NonNull
	private Stream<HUQRCodeAssignment> toHUQRCodeAssignment(@NonNull final ImmutableList<I_M_HU_QRCode> qrCodes)
	{
		final ImmutableSet<HUQRCodeRepoId> qrCodeIds = qrCodes.stream()
				.map(I_M_HU_QRCode::getM_HU_QRCode_ID)
				.map(HUQRCodeRepoId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSetMultimap<HUQRCodeRepoId, HuId> qrCodeId2AssignedHUIds = getAssignedHuIds(qrCodeIds);
		return qrCodes.stream()
				.map(qrCode -> toHUQRCodeAssignment(qrCode, qrCodeId2AssignedHUIds))
				.filter(Optional::isPresent)
				.map(Optional::get);
	}

	@NonNull
	private ImmutableSetMultimap<HUQRCodeRepoId, HuId> getAssignedHuIds(@NonNull final ImmutableSet<HUQRCodeRepoId> huQrCodeIds)
	{
		return streamAssignmentForQrIds(huQrCodeIds)
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(assignment -> HUQRCodeRepoId.ofRepoId(assignment.getM_HU_QRCode_ID()),
																	 assignment -> HuId.ofRepoId(assignment.getM_HU_ID())));
	}

	private void createAssignments(@NonNull final I_M_HU_QRCode qrCode, @NonNull final ImmutableSet<HuId> huIds)
	{
		final ImmutableSet<HuId> newHuIds = toHUQRCodeAssignment(qrCode)
				.map(existingAssignment -> existingAssignment.returnNotAssignedHUs(huIds))
				.orElse(huIds);

		newHuIds.forEach(huId -> {
			final I_M_HU_QRCode_Assignment assignment = InterfaceWrapperHelper.newInstance(I_M_HU_QRCode_Assignment.class);
			assignment.setAD_Org_ID(qrCode.getAD_Org_ID());
			assignment.setM_HU_QRCode_ID(qrCode.getM_HU_QRCode_ID());
			assignment.setM_HU_ID(huId.getRepoId());

			InterfaceWrapperHelper.save(assignment);
		});
	}

	@NonNull
	private ImmutableList<I_M_HU_QRCode_Assignment> getAssignmentsByHuIds(@NonNull final ImmutableSet<HuId> huIds)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode_Assignment.COLUMN_M_HU_ID, huIds)
				.create()
				.listImmutable(I_M_HU_QRCode_Assignment.class);
	}

	@NonNull
	private ImmutableMap<HUQRCodeRepoId, I_M_HU_QRCode> getByIds(@NonNull final ImmutableSet<HUQRCodeRepoId> huQrCodeId)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode.COLUMNNAME_M_HU_QRCode_ID, huQrCodeId)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(huQrCode -> HUQRCodeRepoId.ofRepoId(huQrCode.getM_HU_QRCode_ID()),
													 Function.identity()));
	}

	@NonNull
	private Optional<I_M_HU_QRCode> getByUniqueId(final @NonNull HUQRCodeUniqueId uniqueId)
	{
		return queryByQRCode(uniqueId)
				.create()
				.firstOnlyOptional(I_M_HU_QRCode.class);
	}

	private void removeAssignment(@NonNull final I_M_HU_QRCode qrCode, @NonNull final ImmutableSet<HuId> huIdsToRemove)
	{
		streamAssignmentForQrAndHuIds(ImmutableSet.of(HUQRCodeRepoId.ofRepoId(qrCode.getM_HU_QRCode_ID())), huIdsToRemove)
				.forEach(InterfaceWrapperHelper::delete);
	}

	@NonNull
	private Stream<I_M_HU_QRCode_Assignment> streamAssignmentForQrIds(@NonNull final ImmutableSet<HUQRCodeRepoId> huQrCodeIds)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode_Assignment.COLUMN_M_HU_QRCode_ID, huQrCodeIds)
				.create()
				.stream();
	}

	@NonNull
	private Stream<I_M_HU_QRCode_Assignment> streamAssignmentForQrAndHuIds(
			@NonNull final ImmutableSet<HUQRCodeRepoId> huQrCodeIds,
			@NonNull final ImmutableSet<HuId> huIds)
	{
		return queryBL.createQueryBuilder(I_M_HU_QRCode_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_HU_QRCode_Assignment.COLUMNNAME_M_HU_QRCode_ID, huQrCodeIds)
				.addInArrayFilter(I_M_HU_QRCode_Assignment.COLUMNNAME_M_HU_ID, huIds)
				.create()
				.stream();
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
