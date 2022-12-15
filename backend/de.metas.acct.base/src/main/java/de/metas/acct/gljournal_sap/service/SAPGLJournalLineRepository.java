package de.metas.acct.gljournal_sap.service;

import de.metas.acct.gljournal_sap.DebitAndCreditAmountsBD;
import de.metas.acct.gljournal_sap.PostingSign;
import de.metas.acct.gljournal_sap.SAPGLJournalId;
import de.metas.acct.model.I_SAP_GLJournalLine;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.lang.MutableBigDecimal;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class SAPGLJournalLineRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public SeqNo getNextSeqNo(@NonNull final SAPGLJournalId glJournalId)
	{
		final int lastLineInt = queryLines(glJournalId)
				.create()
				.maxInt(I_SAP_GLJournalLine.COLUMNNAME_Line);

		final SeqNo lastLineNo = SeqNo.ofInt(Math.max(lastLineInt, 0));
		return lastLineNo.next();
	}

	private IQueryBuilder<I_SAP_GLJournalLine> queryLines(final @NonNull SAPGLJournalId glJournalId)
	{
		return queryBL.createQueryBuilder(I_SAP_GLJournalLine.class)
				.addInArrayFilter(I_SAP_GLJournalLine.COLUMNNAME_SAP_GLJournal_ID, glJournalId);
	}

	public DebitAndCreditAmountsBD computeDebitAndCreditAmountsAcct(@NonNull final SAPGLJournalId glJournalId)
	{
		final MutableBigDecimal debit = new MutableBigDecimal();
		final MutableBigDecimal credit = new MutableBigDecimal();

		queryLines(glJournalId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listColumns(I_SAP_GLJournalLine.COLUMNNAME_PostingSign, I_SAP_GLJournalLine.COLUMNNAME_AmtAcct)
				.forEach(row -> {
					final PostingSign postingSign = PostingSign.ofCode(row.get(I_SAP_GLJournalLine.COLUMNNAME_PostingSign).toString());
					final BigDecimal amtAcct = NumberUtils.asBigDecimal(row.get(I_SAP_GLJournalLine.COLUMNNAME_AmtAcct), BigDecimal.ZERO);

					switch (postingSign)
					{
						case DEBIT:
							debit.add(amtAcct);
							break;
						case CREDIT:
							credit.add(amtAcct);
							break;
						default:
							throw new IllegalStateException("Invalid posting type: " + postingSign);
					}
				});

		return DebitAndCreditAmountsBD.builder()
				.debit(debit.getValue())
				.credit(credit.getValue())
				.build();
	}

	public boolean hasLines(@NonNull final SAPGLJournalId glJournalId)
	{
		return queryLines(glJournalId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

}
