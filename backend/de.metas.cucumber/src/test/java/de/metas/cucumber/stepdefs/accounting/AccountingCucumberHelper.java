package de.metas.cucumber.stepdefs.accounting;

import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.StringUtils;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.PostingStatus;
import org.compiere.util.DB;

import java.util.Collections;
import java.util.Set;

@UtilityClass
public class AccountingCucumberHelper
{
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
			final PostingStatus postingStatus = retrievePostingStatus(recordRef);
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
				throw new AdempiereException("Document " + recordRef + " has posting error: " + postingStatus);
			}
		});
	}

	public static PostingStatus retrievePostingStatus(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
		final int recordId = recordRef.getRecord_ID();

		final String postingStatus = DB.getSQLValueStringEx(
				ITrx.TRXNAME_ThreadInherited,
				"SELECT Posted FROM " + tableName + " WHERE " + keyColumnName + "=?",
				Collections.singletonList(recordId)
		);

		return StringUtils.trimBlankToOptional(postingStatus).map(PostingStatus::ofCode).orElse(PostingStatus.NotPosted);
	}
}
