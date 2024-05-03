package de.metas.contracts.invoicecandidate;

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;

import java.util.Collections;
import java.util.Iterator;

import static org.adempiere.model.InterfaceWrapperHelper.create;

public class FlatrateDataEntryHandler extends AbstractInvoiceCandidateHandler
{

	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	/**
	 * Return "DON'T" because ICs are created by the model interceptor {@link de.metas.contracts.interceptor.C_Flatrate_DataEntry} when the {@code C_Flatrate_DataEntry} is completed.
	 */
	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.DONT;
	}

	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(final Object model)
	{
		return CandidatesAutoCreateMode.DONT;
	}

	/**
	 * @return empty iterator
	 */
	@Override
	public Iterator<I_C_Flatrate_DataEntry> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit_IGNORED)
	{
		return Collections.emptyIterator();
	}

	/**
	 * @return empty result
	 */
	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(final InvoiceCandidateGenerateRequest request)
	{
		return InvoiceCandidateGenerateResult.of(this);
	}

	/**
	 * Implementation invalidates the C_Flatrate_DataEntry's C_Invoice_Candidate and C_Invoice_Candidate_Corr (if set).
	 */
	@Override
	public void invalidateCandidatesFor(final Object dataEntryObj)
	{
		final I_C_Flatrate_DataEntry dataEntry = create(dataEntryObj, I_C_Flatrate_DataEntry.class);
		if (dataEntry.getC_Invoice_Candidate_ID() > 0)
		{
			final InvoiceCandidateId id = InvoiceCandidateId.ofRepoId(dataEntry.getC_Invoice_Candidate_ID());
			invoiceCandDAO.invalidateCandFor(id);
		}
		if (dataEntry.getC_Invoice_Candidate_Corr_ID() > 0)
		{
			final InvoiceCandidateId id = InvoiceCandidateId.ofRepoId(dataEntry.getC_Invoice_Candidate_Corr_ID());
			invoiceCandDAO.invalidateCandFor(id);
		}
	}

	/**
	 * Returns "C_Flatrate_DataEntry".
	 */
	@Override
	public String getSourceTable()
	{
		return I_C_Flatrate_DataEntry.Table_Name;
	}

	/**
	 * Implementation returns the user that is set as "user in charge" in the flatrate term of the {@link I_C_Flatrate_DataEntry} that references the given invoice candidate.
	 * <p>
	 * Note: It is assumed that there is a flatrate data entry referencing the given ic
	 */
	@Override
	public int getAD_User_InCharge_ID(final I_C_Invoice_Candidate ic)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final I_C_Flatrate_DataEntry dataEntry = flatrateDB.retrieveDataEntryOrNull(ic);
		Check.assumeNotNull(dataEntry, "Param 'ic' needs to be referenced by a C_Flatrate_DataEntry record; ic={}", ic);

		return dataEntry.getC_Flatrate_Term().getAD_User_InCharge_ID();
	}

	/**
	 * Returns false, because the user in charge is taken from the flatrate term.
	 */
	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * Does nothing.
	 */
	@Override
	public void setOrderedData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := DateOrdered
	 * <li>M_InOut_ID: untouched
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(final I_C_Invoice_Candidate ic)
	{
		HandlerTools.setDeliveredData(ic);
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		// nothing to do
	}
}
