package de.metas.contracts.spi.impl;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateListener;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;

public class FlatrateTermInvoiceCandidateListener implements IInvoiceCandidateListener
{
	public static final FlatrateTermInvoiceCandidateListener instance = new FlatrateTermInvoiceCandidateListener();

	private FlatrateTermInvoiceCandidateListener()
	{
		super();
	}

	@Override
	public void onBeforeClosed(@NonNull final I_C_Invoice_Candidate candidate)
	{
		final int tableID = candidate.getAD_Table_ID();

		if (tableID != InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class))
		{
			return;
		}

		final I_C_Flatrate_Term term = load(candidate.getRecord_ID(), I_C_Flatrate_Term.class);

		final IQuery<I_C_SubscriptionProgress> subscriptionQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_SubscriptionProgress.class, term)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID, term.getC_Flatrate_Term_ID())
				.create();

		final List<I_M_ShipmentSchedule> shipmentSchedules = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_ShipmentSchedule.class, candidate)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ShipmentSchedule.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_C_SubscriptionProgress.class))
				.addInSubQueryFilter(I_M_ShipmentSchedule.COLUMNNAME_Record_ID, I_C_SubscriptionProgress.COLUMNNAME_C_SubscriptionProgress_ID, subscriptionQuery)
				.orderBy().addColumn(I_M_ShipmentSchedule.COLUMNNAME_M_ShipmentSchedule_ID).endOrderBy()
				.create()
				.list(I_M_ShipmentSchedule.class);

		shipmentSchedules.forEach(shipmentSchedule -> Services.get(IShipmentScheduleBL.class).closeShipmentSchedule(shipmentSchedule));
	}

}
