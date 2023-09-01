package de.metas.workflow.execution.approval;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.document.DocBaseType;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.SeqNo;
import de.metas.workflow.execution.WFActivityId;
import de.metas.workflow.execution.WFProcessId;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_WF_Approval_Request;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class WFApprovalRequestRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ImmutableSet<AdTableId>> tableIdsCache = CCache.<Integer, ImmutableSet<AdTableId>>builder()
			.tableName(I_AD_WF_Approval_Request.Table_Name)
			.build();

	public WFApprovalRequest getById(@NonNull WFApprovalRequestId id)
	{
		return fromRecord(getRecordById(id));
	}

	@NotNull
	private static I_AD_WF_Approval_Request getRecordById(final @NotNull WFApprovalRequestId id)
	{
		return InterfaceWrapperHelper.loadNotNull(id, I_AD_WF_Approval_Request.class);
	}

	public Stream<WFApprovalRequest> streamByIds(@NonNull final Set<WFApprovalRequestId> ids)
	{
		if (ids.isEmpty())
		{
			return Stream.empty();
		}

		return queryRecordsByIds(ids).stream().map(WFApprovalRequestRepository::fromRecord);
	}

	private IQueryBuilder<I_AD_WF_Approval_Request> queryRecordsByIds(final @NotNull Set<WFApprovalRequestId> ids)
	{
		Check.assumeNotEmpty(ids, "ids shall not be empty");

		return queryBL.createQueryBuilder(I_AD_WF_Approval_Request.class)
				.addInArrayFilter(I_AD_WF_Approval_Request.COLUMNNAME_AD_WF_Approval_Request_ID, ids)
				.orderBy(I_AD_WF_Approval_Request.COLUMNNAME_AD_WF_Approval_Request_ID) // just to have a predictable order
				;
	}

	public List<WFApprovalRequest> getByDocumentRef(@NonNull final TableRecordReference documentRef)
	{
		return queryRecordsByDocumentRef(documentRef)
				.stream()
				.map(WFApprovalRequestRepository::fromRecord)
				.collect(ImmutableList.toImmutableList());
	}

	public int countByDocumentRef(@NonNull final TableRecordReference documentRef)
	{
		return queryRecordsByDocumentRef(documentRef).count();
	}

	private IQueryBuilder<I_AD_WF_Approval_Request> queryRecordsByDocumentRef(@NotNull final TableRecordReference documentRef)
	{
		return queryBL.createQueryBuilder(I_AD_WF_Approval_Request.class)
				.addEqualsFilter(I_AD_WF_Approval_Request.COLUMNNAME_AD_Table_ID, documentRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_WF_Approval_Request.COLUMNNAME_Record_ID, documentRef.getRecord_ID())
				.orderBy(I_AD_WF_Approval_Request.COLUMNNAME_SeqNo);
	}

	private static WFApprovalRequest fromRecord(final I_AD_WF_Approval_Request record)
	{
		return WFApprovalRequest.builder()
				.id(WFApprovalRequestId.ofRepoId(record.getAD_WF_Approval_Request_ID()))
				//
				.documentRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.docBaseType(DocBaseType.ofNullableCode(record.getDocBaseType()))
				.documentNo(StringUtils.trimBlankToNull(record.getDocumentNo()))
				//
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.status(WFApprovalRequestStatus.ofCode(record.getStatus()))
				//
				.requestDate(record.getDateRequest().toInstant())
				.responseDate(TimeUtil.asInstant(record.getDateResponse()))
				//
				.wfProcessId(WFProcessId.ofRepoIdOrNull(record.getAD_WF_Process_ID()))
				.wfActivityId(WFActivityId.ofRepoIdOrNull(record.getAD_WF_Activity_ID()))
				//
				.build();
	}

	public void saveForDocument(@NonNull final TableRecordReference documentRef, @NonNull final List<WFApprovalRequest> list)
	{
		list.forEach(request -> Check.assumeEquals(request.getDocumentRef(), documentRef, "documentRef"));

		final HashMap<WFApprovalRequestId, I_AD_WF_Approval_Request> recordsById = queryRecordsByDocumentRef(documentRef)
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(record -> WFApprovalRequestId.ofRepoId(record.getAD_WF_Approval_Request_ID())));

		for (final WFApprovalRequest approvalRequest : list)
		{
			I_AD_WF_Approval_Request record = approvalRequest.getId() != null
					? recordsById.remove(approvalRequest.getId())
					: null;
			if (record == null)
			{
				record = InterfaceWrapperHelper.newInstance(I_AD_WF_Approval_Request.class);
			}
			updateRecord(record, approvalRequest);
			InterfaceWrapperHelper.saveRecord(record);

			approvalRequest.setId(WFApprovalRequestId.ofRepoId(record.getAD_WF_Approval_Request_ID()));
		}

		InterfaceWrapperHelper.deleteAll(recordsById.values());
	}

	private static void updateRecord(@NonNull final I_AD_WF_Approval_Request record, @NonNull final WFApprovalRequest from)
	{
		record.setAD_Table_ID(from.getDocumentRef().getAD_Table_ID());
		record.setRecord_ID(from.getDocumentRef().getRecord_ID());
		record.setDocBaseType(from.getDocBaseType() != null ? from.getDocBaseType().getCode() : null);
		record.setDocumentNo(StringUtils.trimBlankToNull(from.getDocumentNo()));
		//
		record.setSeqNo(from.getSeqNo().toInt());
		record.setAD_User_ID(from.getUserId().getRepoId());
		record.setStatus(from.getStatus().getCode());
		//
		record.setDateRequest(Timestamp.from(from.getRequestDate()));
		record.setDateResponse(TimeUtil.asTimestamp(from.getResponseDate()));
		//
		record.setAD_WF_Process_ID(WFProcessId.toRepoId(from.getWfProcessId()));
		record.setAD_WF_Activity_ID(WFActivityId.toRepoId(from.getWfActivityId()));
	}

	public boolean hasRecordsForTable(@NonNull final AdTableId adTableId)
	{
		return getAdTableIds().contains(adTableId);
	}

	private ImmutableSet<AdTableId> getAdTableIds()
	{
		return tableIdsCache.getOrLoad(0, this::retrieveAdTableIds);
	}

	private ImmutableSet<AdTableId> retrieveAdTableIds()
	{
		final ImmutableList<AdTableId> adTableIds = queryBL.createQueryBuilder(I_AD_WF_Approval_Request.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_AD_WF_Approval_Request.COLUMNNAME_AD_Table_ID, AdTableId.class);
		return ImmutableSet.copyOf(adTableIds);
	}

	private static void updateRecordAndSave(final @NotNull Consumer<WFApprovalRequest> updater, final I_AD_WF_Approval_Request record)
	{
		final WFApprovalRequest request = fromRecord(record);
		updater.accept(request);
		updateRecord(record, request);
		InterfaceWrapperHelper.saveRecord(record);
	}

	public void updateByIds(@NonNull Set<WFApprovalRequestId> ids, @NonNull Consumer<WFApprovalRequest> updater)
	{
		if (ids.isEmpty())
		{
			return;
		}

		queryRecordsByIds(ids).forEach(record -> updateRecordAndSave(updater, record));
	}
}
