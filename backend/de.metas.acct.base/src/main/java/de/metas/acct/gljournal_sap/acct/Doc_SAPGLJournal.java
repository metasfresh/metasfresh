package de.metas.acct.gljournal_sap.acct;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.doc.AcctDocContext;
import org.compiere.acct.Doc;
import org.compiere.acct.Fact;

import java.math.BigDecimal;
import java.util.List;

public class Doc_SAPGLJournal extends Doc<DocLine_SAPGLJournal>
{
	public Doc_SAPGLJournal(final AcctDocContext ctx)
	{
		super(ctx);
	}

	@Override
	protected void loadDocumentDetails()
	{
		// TODO
	}

	@Override
	protected BigDecimal getBalance()
	{
		// TODO
		return BigDecimal.ZERO;
	}

	@Override
	protected List<Fact> createFacts(final AcctSchema as)
	{
		// TODO
		return ImmutableList.of();
	}
}
