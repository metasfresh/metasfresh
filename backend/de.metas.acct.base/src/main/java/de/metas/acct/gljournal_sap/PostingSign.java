package de.metas.acct.gljournal_sap;

import de.metas.acct.model.X_SAP_GLJournalLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
public enum PostingSign implements ReferenceListAwareEnum
{
	DEBIT(X_SAP_GLJournalLine.POSTINGSIGN_DR),
	CREDIT(X_SAP_GLJournalLine.POSTINGSIGN_CR),
	;

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
}
