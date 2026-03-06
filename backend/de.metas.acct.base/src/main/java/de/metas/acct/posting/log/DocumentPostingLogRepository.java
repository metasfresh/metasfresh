package de.metas.acct.posting.log;

import com.google.common.collect.ImmutableList;
import de.metas.acct.model.I_Document_Acct_Log;
import de.metas.error.AdIssueId;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DocumentPostingLogRepository
{
	public void create(@NonNull final DocumentPostingLogRequest request)
	{
		create(ImmutableList.of(request));
	}

	public void create(@NonNull final List<DocumentPostingLogRequest> requests)
	{
		if (requests.isEmpty()) {return;}

		final List<I_Document_Acct_Log> records = requests.stream()
				.map(this::toRecord)
				.collect(Collectors.toList());

		InterfaceWrapperHelper.saveAll(records);
	}

	private I_Document_Acct_Log toRecord(@NonNull final DocumentPostingLogRequest from)
	{
		final I_Document_Acct_Log record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_Document_Acct_Log.class);
		updateRecord(record, from);
		return record;
	}

	private void updateRecord(final I_Document_Acct_Log record, final @NonNull DocumentPostingLogRequest from)
	{
		InterfaceWrapperHelper.setValue(record, I_Document_Acct_Log.COLUMNNAME_AD_Client_ID, from.getClientId().getRepoId());
		record.setType(from.getType().getCode());
		record.setAD_Table_ID(from.getDocumentRef().getAD_Table_ID());
		record.setRecord_ID(from.getDocumentRef().getRecord_ID());
		record.setAD_User_ID(UserId.toRepoId(from.getUserId()));
		record.setAD_Issue_ID(AdIssueId.toRepoId(from.getAdIssueId()));
	}
}
