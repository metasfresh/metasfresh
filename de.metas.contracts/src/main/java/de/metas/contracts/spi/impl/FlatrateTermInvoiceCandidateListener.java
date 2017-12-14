package de.metas.contracts.spi.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateListenerAdapter;

public class FlatrateTermInvoiceCandidateListener extends InvoiceCandidateListenerAdapter
{
	public static final FlatrateTermInvoiceCandidateListener instance = new FlatrateTermInvoiceCandidateListener();

	private FlatrateTermInvoiceCandidateListener()
	{
		super();
	}

	@Override
	public void onBeforeClosed(I_C_Invoice_Candidate candidate)
	{
		final int tableID = candidate.getAD_Table_ID();

		if (tableID != InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class))
		{
			return;
		}

		final List<I_M_ShipmentSchedule> shipmentSchedules = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule.class, candidate)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID, candidate.getAD_Table_ID())
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_Record_ID, candidate.getRecord_ID())
				.orderBy().addColumn(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).endOrderBy()
				.create()
				.list(I_M_ShipmentSchedule.class);

		shipmentSchedules.forEach(shipmentSchedule -> Services.get(IShipmentScheduleBL.class).closeShipmentSchedule(shipmentSchedule));
	}

}
