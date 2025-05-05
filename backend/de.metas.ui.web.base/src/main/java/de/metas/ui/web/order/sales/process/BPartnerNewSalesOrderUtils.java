package de.metas.ui.web.order.sales.process;

import de.metas.bpartner.BPartnerId;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.edi.model.I_C_Order;
import de.metas.lang.SOTrx;
import de.metas.process.ProcessExecutionResult.WebuiNewRecord;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
class BPartnerNewSalesOrderUtils
{
	private static final WebuiNewRecord.TargetTab DEFAULT_TARGET_TAB = WebuiNewRecord.TargetTab.NEW_TAB;

	public WebuiNewRecord openNewSalesOrderWindowAndSetBPartner(@NonNull final BPartnerId bpartnerId)
	{
		final AdWindowId adWindowId = getSalesOrderWindowId();

		return WebuiNewRecord.builder()
				.windowId(String.valueOf(adWindowId.getRepoId()))
				.fieldValue(I_C_Order.COLUMNNAME_C_BPartner_ID, String.valueOf(bpartnerId.getRepoId()))
				.targetTab(DEFAULT_TARGET_TAB)
				.build();
	}

	public WebuiNewRecord openNewSalesOrderWindowAndNewBPartnerModal()
	{
		final AdWindowId adWindowId = getSalesOrderWindowId();

		return WebuiNewRecord.builder()
				.windowId(String.valueOf(adWindowId.getRepoId()))
				.fieldValue(I_C_Order.COLUMNNAME_C_BPartner_ID, WebuiNewRecord.FIELD_VALUE_NEW)
				.targetTab(DEFAULT_TARGET_TAB)
				.build();
	}

	private static AdWindowId getSalesOrderWindowId()
	{
		return RecordWindowFinder.newInstance(I_C_Order.Table_Name, SOTrx.SALES)
				.findAdWindowId()
				.orElseThrow(() -> new AdempiereException("No Sales Order window defined"));
	}
}
