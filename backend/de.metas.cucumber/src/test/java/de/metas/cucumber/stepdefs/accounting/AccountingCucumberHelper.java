package de.metas.cucumber.stepdefs.accounting;

import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.accounting.FactAcctValidator.FactAcctValidatorBuilder;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.PostingStatus;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@UtilityClass
public class AccountingCucumberHelper
{
<<<<<<< HEAD
=======
	public static void repost(final TableRecordReferenceSet recordRefs)
	{
		final IPostingService postingService = Services.get(IPostingService.class);

		streamPostingInfo(recordRefs)
				.map(AccountingCucumberHelper::toDocumentPostRequest)
				.collect(DocumentPostMultiRequest.collect())
				.ifPresent(postingService::postAfterCommit);
	}

	private static DocumentPostRequest toDocumentPostRequest(final PostingInfo postingInfo)
	{
		return DocumentPostRequest.builder()
				.record(postingInfo.getRecordRef())
				.clientId(postingInfo.getClientId())
				.force(true)
				.onErrorNotifyUserId(Env.getLoggedUserId())
				.build();
	}
	
	public static void waitUtilPosted(final TableRecordReferenceSet recordRefs) throws InterruptedException
	{
		waitUtilPosted(recordRefs.toSet());
	}

>>>>>>> 35b06f5141 (Fix Moving Average Invoice costing bugs + cucumber test (#21145))
	public static void waitUtilPosted(final Set<TableRecordReference> recordRefs) throws InterruptedException
	{
		if (recordRefs.isEmpty())
		{
			return;
		}

		for (final TableRecordReference recordRef : recordRefs)
		{
			waitUtilPosted(recordRef);
		}
	}

	public static void waitUtilPosted(final TableRecordReference recordRef) throws InterruptedException
	{
		StepDefUtil.tryAndWait(60, 500, () -> {
			final PostingInfo postingInfo = retrievePostingInfo(recordRef).orElse(null);
			if (postingInfo == null)
			{
				return false; // document not found yet?
			}

			final PostingStatus postingStatus = postingInfo.getStatus();
			if (postingStatus.isPosted())
			{
				return true;
			}
			else if (postingStatus.isNotPosted())
			{
				return false;
			}
			else
			{
				throw new AdempiereException("Document " + recordRef + " has posting error: " + postingInfo.getStackTrace());
			}
		});
	}

	private static Optional<PostingInfo> retrievePostingInfo(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final int recordId = recordRef.getRecord_ID();

		final PostingInfo postingInfo = DB.retrieveFirstRowOrNull(
				"SELECT t.Posted, err.StackTrace FROM " + tableName + " t"
						+ " LEFT OUTER JOIN AD_Issue err ON err.AD_Issue_ID=t.PostingError_Issue_ID"
						+ " WHERE t." + keyColumnName + "=?",
				Collections.singletonList(recordId),
				AccountingCucumberHelper::retrievePostingInfo
		);

		return Optional.ofNullable(postingInfo);
	}

	private static PostingInfo retrievePostingInfo(final ResultSet rs) throws SQLException
	{
		return PostingInfo.builder()
				.status(StringUtils.trimBlankToOptional(rs.getString("Posted")).map(PostingStatus::ofCode).orElse(PostingStatus.NotPosted))
				.stackTrace(rs.getString("StackTrace"))
				.build();
	}

	@Value
	@Builder
	private static class PostingInfo
	{
		@NonNull PostingStatus status;
		@Nullable String stackTrace;
	}

	public static FactAcctValidatorBuilder newFactAcctValidator()
	{
		return FactAcctValidator.builder();
	}
}
