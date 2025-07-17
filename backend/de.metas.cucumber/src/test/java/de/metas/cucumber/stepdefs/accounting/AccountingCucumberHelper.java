package de.metas.cucumber.stepdefs.accounting;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimaps;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.accounting.FactAcctBalanceValidator.FactAcctBalanceValidatorBuilder;
import de.metas.cucumber.stepdefs.accounting.FactAcctValidator.FactAcctValidatorBuilder;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.acct.PostingStatus;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@UtilityClass
public class AccountingCucumberHelper
{
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

	private static Optional<PostingInfo> retrievePostingInfo(@NonNull final TableRecordReference recordRef)
	{
		return streamPostingInfo(TableRecordReferenceSet.of(recordRef))
				.filter(postingInfo -> postingInfo.getRecordRef().equals(recordRef))
				.findFirst();
	}

	private static Stream<PostingInfo> streamPostingInfo(@NonNull final TableRecordReferenceSet recordRefs)
	{
		if (recordRefs.isEmpty())
		{
			return Stream.empty();
		}

		final ImmutableListMultimap<String, TableRecordReference> recordRefsByTableName = Multimaps.index(recordRefs.toSet(), TableRecordReference::getTableName);

		final StringBuilder finalSql = new StringBuilder();
		final ArrayList<Object> finalSqlParams = new ArrayList<>();

		for (final String tableName : recordRefsByTableName.keySet())
		{
			final Set<Integer> recordIds = recordRefsByTableName.get(tableName).stream().map(TableRecordReference::getRecord_ID).collect(ImmutableSet.toImmutableSet());
			if (recordIds.isEmpty())
			{
				continue;
			}

			final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);

			final ArrayList<Object> sqlParams = new ArrayList<>();
			sqlParams.add(tableName);
			final String sql = "SELECT "
					+ " ? as TableName"
					+ ", t." + keyColumnName + " as Record_ID"
					+ ", t.AD_Client_ID"
					+ ", t.Posted"
					+ ", err.StackTrace"
					+ " FROM " + tableName + " t"
					+ " LEFT OUTER JOIN AD_Issue err ON err.AD_Issue_ID=t.PostingError_Issue_ID"
					+ " WHERE " + DB.buildSqlList("t." + keyColumnName, recordIds, sqlParams);

			if (finalSql.length() > 0)
			{
				finalSql.append("\n UNION ALL \n");
			}
			finalSql.append(sql);
			finalSqlParams.addAll(sqlParams);
		}

		final ImmutableList<PostingInfo> result = DB.retrieveRows(finalSql, finalSqlParams, AccountingCucumberHelper::retrievePostingInfo);
		return result.stream();
	}

	private static PostingInfo retrievePostingInfo(final ResultSet rs) throws SQLException
	{
		return PostingInfo.builder()
				.recordRef(TableRecordReference.of(rs.getString("TableName"), rs.getInt("Record_ID")))
				.clientId(ClientId.ofRepoId(rs.getInt("AD_Client_ID")))
				.status(StringUtils.trimBlankToOptional(rs.getString("Posted")).map(PostingStatus::ofCode).orElse(PostingStatus.NotPosted))
				.stackTrace(rs.getString("StackTrace"))
				.build();
	}

	@Value
	@Builder
	private static class PostingInfo
	{
		@NonNull TableRecordReference recordRef;
		@NonNull ClientId clientId;
		@NonNull PostingStatus status;
		@Nullable String stackTrace;
	}

	public static FactAcctValidatorBuilder newFactAcctValidator()
	{
		return FactAcctValidator.builder();
	}

	public static FactAcctBalanceValidatorBuilder newFactAcctBalanceValidator()
	{
		return FactAcctBalanceValidator.builder();
	}
}
