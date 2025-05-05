package de.metas.forex.callout;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.ITabCallout;
import org.adempiere.ad.ui.spi.TabCallout;
import org.compiere.model.I_C_ForeignExchangeContract;

@TabCallout(I_C_ForeignExchangeContract.class)
public class C_ForeignExchangeContract_TabCallout implements ITabCallout
{
	private final IDocumentNoBuilderFactory documentNoFactory;

	public C_ForeignExchangeContract_TabCallout(final IDocumentNoBuilderFactory documentNoFactory) {this.documentNoFactory = documentNoFactory;}

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_ForeignExchangeContract model = calloutRecord.getModel(I_C_ForeignExchangeContract.class);

		final String documentNo = documentNoFactory.createValueBuilderFor(model)
				.setFailOnError(true)
				.build();
		model.setDocumentNo(documentNo);
	}
}
