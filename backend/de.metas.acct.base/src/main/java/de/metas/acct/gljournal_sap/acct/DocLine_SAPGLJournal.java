package de.metas.acct.gljournal_sap.acct;

import lombok.NonNull;
import org.compiere.acct.DocLine;
import org.compiere.model.PO;

class DocLine_SAPGLJournal extends DocLine<Doc_SAPGLJournal>
{
	public DocLine_SAPGLJournal(final @NonNull PO linePO, @NonNull final Doc_SAPGLJournal doc)
	{
		super(linePO, doc);
	}
}
