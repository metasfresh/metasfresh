package de.metas.inventory.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_M_Inventory;
import org.springframework.stereotype.Component;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.IDocumentNoInfo;
import de.metas.util.Services;

@Callout(I_M_Inventory.class)
@Component("de.metas.inventory.callout.M_Inventory")
public class M_Inventory
{
	public M_Inventory()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_M_Inventory.COLUMNNAME_C_DocType_ID)
	public void updateFromDocType(final I_M_Inventory inventoryRecord, final ICalloutField field)
	{
		final IDocumentNoInfo documentNoInfo = Services.get(IDocumentNoBuilderFactory.class)
				.createPreliminaryDocumentNoBuilder()
				.setNewDocType(inventoryRecord.getC_DocType())
				.setOldDocumentNo(inventoryRecord.getDocumentNo())
				.setDocumentModel(inventoryRecord)
				.buildOrNull();
		if (documentNoInfo == null)
		{
			return;
		}

		// DocumentNo
		if (documentNoInfo.isDocNoControlled())
		{
			inventoryRecord.setDocumentNo(documentNoInfo.getDocumentNo());
		}
	}
}
