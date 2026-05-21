package de.metas.acct.tax.document;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

public class TaxDeclarationDocumentHandler implements DocumentHandler
{
	private static final AdMessageKey MSG_NoLinesYet    = AdMessageKey.of("TaxDeclaration_NoLinesYet");
	private static final AdMessageKey MSG_PeriodOverlap = AdMessageKey.of("TaxDeclaration_PeriodOverlap");

	@NonNull private final TaxDeclarationRepository repo =
			SpringContextHolder.instance.getBean(TaxDeclarationRepository.class);

	@Override public String getSummary(final DocumentTableFields f)      { return extract(f).getDescription(); }
	@Override public String getDocumentInfo(final DocumentTableFields f) { return getSummary(f); }
	@Override public LocalDate getDocumentDate(@NonNull final DocumentTableFields f) {
		return TimeUtil.asLocalDate(extract(f).getCreated());
	}
	@Override public int getDoc_User_ID(final DocumentTableFields f)    { return extract(f).getCreatedBy(); }

	@Override
	public String completeIt(@NonNull final DocumentTableFields f)
	{
		final I_C_TaxDeclaration td = extract(f);
		final TaxDeclarationId id = TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_ID());

		if (!repo.hasAnyLines(id))
		{
			throw new AdempiereException(MSG_NoLinesYet);
		}
		// NOTE: period overlap check uses acctSchemaId; periodId param is 0 until C_Period_ID column is added to C_TaxDeclaration
		if (repo.existsCompletedOverlappingPeriod(id, td.getC_AcctSchema_ID(), /*periodId*/0))
		{
			throw new AdempiereException(MSG_PeriodOverlap);
		}

		td.setProcessed(true);
		td.setDocAction(IDocument.ACTION_ReActivate);
		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(@NonNull final DocumentTableFields f)
	{
		final I_C_TaxDeclaration td = extract(f);
		td.setProcessed(false);
		td.setDocAction(IDocument.ACTION_Complete);
		// Lines + Acct snapshot intentionally kept intact — per REQUIREMENTS §2 and the Iter 5 design.
	}

	private static I_C_TaxDeclaration extract(@NonNull final DocumentTableFields f)
	{
		return InterfaceWrapperHelper.create(f, I_C_TaxDeclaration.class);
	}
}
