package de.metas.acct.gljournal_sap;

import de.metas.acct.model.X_SAP_GLJournalLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;

@AllArgsConstructor
public enum PostingSign implements ReferenceListAwareEnum
{
	DEBIT(X_SAP_GLJournalLine.POSTINGSIGN_DR),
	CREDIT(X_SAP_GLJournalLine.POSTINGSIGN_CR),
	;

	public static final int AD_REFERENCE_ID = X_SAP_GLJournalLine.POSTINGSIGN_AD_Reference_ID;

	private static final ReferenceListAwareEnums.ValuesIndex<PostingSign> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	public static PostingSign ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public boolean isDebit() {return DEBIT.equals(this);}

	public boolean isCredit() {return CREDIT.equals(this);}

	public @NonNull PostingSign reverse()
	{
		return isDebit() ? CREDIT : DEBIT;
	}

	public static PostingSign ofAmtDrAndCr(final BigDecimal dr, final BigDecimal cr)
	{
		final boolean isDr = dr != null && dr.signum() != 0;
		final boolean isCr = cr != null && cr.signum() != 0;
		if (isDr)
		{
			if (isCr)
			{
				throw new AdempiereException("Cannot determine posting sign when both DR and CR amounts are non zero");
			}
			return DEBIT;
		}
		else if (isCr)
		{
			return CREDIT;
		}
		// zero line
		else
		{
			return DEBIT;
		}

	}
}
