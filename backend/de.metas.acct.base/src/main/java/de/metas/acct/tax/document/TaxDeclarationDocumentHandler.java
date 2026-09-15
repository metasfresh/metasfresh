package de.metas.acct.tax.document;

import de.metas.acct.api.AcctSchemaId;
import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.IDocument;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.util.TimeUtil;

import java.time.LocalDate;

@RequiredArgsConstructor
public class TaxDeclarationDocumentHandler implements DocumentHandler
{
	private static final AdMessageKey MSG_NoLinesYet      = AdMessageKey.of("TaxDeclaration_NoLinesYet");
	private static final AdMessageKey MSG_PeriodOverlap   = AdMessageKey.of("TaxDeclaration_PeriodOverlap");
	private static final AdMessageKey MSG_HasCorrections  = AdMessageKey.of("TaxDeclaration_HasCorrections");

	@NonNull private final TaxDeclarationRepository repo;

	@Override
	public String getSummary(final DocumentTableFields docFields)
	{
		return extract(docFields).getDocumentNo();
	}

	@Override
	public String getDocumentInfo(final DocumentTableFields docFields)
	{
		return getSummary(docFields);
	}

	@Override
	public LocalDate getDocumentDate(@NonNull final DocumentTableFields docFields)
	{
		return TimeUtil.asLocalDate(extract(docFields).getDateAcct());
	}

	@Override
	public int getDoc_User_ID(final DocumentTableFields docFields)
	{
		return extract(docFields).getCreatedBy();
	}

	@Override
	public String completeIt(@NonNull final DocumentTableFields docFields)
	{
		final I_C_TaxDeclaration td = extract(docFields);
		final TaxDeclarationId id = TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_ID());

		// Period-uniqueness applies to Originals only; a Correction legitimately shares its Original's period.
		// Checked before the no-lines guard so the user gets the meaningful PeriodOverlap message rather than
		// NoLinesYet (a second Original on an already-declared period always builds empty).
		if (!td.isCorrection()
				&& repo.existsCompletedOverlappingPeriod(id, AcctSchemaId.ofRepoId(td.getC_AcctSchema_ID()), td.getC_Period_ID()))
		{
			throw new AdempiereException(MSG_PeriodOverlap);
		}
		if (!repo.hasAnyLines(id))
		{
			throw new AdempiereException(MSG_NoLinesYet);
		}

		td.setProcessed(true);
		td.setDocAction(IDocument.ACTION_ReActivate);

		// Completing a Correction clears its Original's "correction needed" flag.
		if (td.isCorrection())
		{
			final TaxDeclarationId originalId = TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_Original_ID());
			final I_C_TaxDeclaration original = repo.getById(originalId);
			if (original.isCorrectionNeeded())
			{
				original.setIsCorrectionNeeded(false);
				InterfaceWrapperHelper.save(original);
			}
		}

		return IDocument.STATUS_Completed;
	}

	@Override
	public void reactivateIt(@NonNull final DocumentTableFields docFields)
	{
		final I_C_TaxDeclaration td = extract(docFields);
		final TaxDeclarationId taxDeclarationId = TaxDeclarationId.ofRepoId(td.getC_TaxDeclaration_ID());

		if (repo.existsCorrectionFor(taxDeclarationId))
		{
			throw new AdempiereException(MSG_HasCorrections);
		}

		td.setProcessed(false);
		td.setDocAction(IDocument.ACTION_Complete);
		// DocStatus is forced to IP (In Progress) by DocumentEngine.reActivateIt() after this method returns —
		// don't try to override it here.
		// Lines + Acct snapshot intentionally kept intact — per REQUIREMENTS.md §2 and the Iter 5 design.
	}

	private static I_C_TaxDeclaration extract(@NonNull final DocumentTableFields docFields)
	{
		return InterfaceWrapperHelper.create(docFields, I_C_TaxDeclaration.class);
	}
}
