package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.expectations;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.test.ErrorMessage;

import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;

/**
 * Created by ts on 28.12.2015.
 */
public class MTInvoiceCandidateExpectation<ParentExpectationType> extends InvoiceCandidateExpectation<ParentExpectationType>
{
	private QualityInvoiceLineGroupType groupType;
	private boolean groupTypeSet = false;

	public MTInvoiceCandidateExpectation(ParentExpectationType parent)
	{
		super(parent);
	}

	public MTInvoiceCandidateExpectation<ParentExpectationType> qualityInvoiceLineGroupType(String groupType)
	{
		this.groupType = QualityInvoiceLineGroupType.ofAD_Ref_List_Value(groupType);
		this.groupTypeSet = true;
		return this;
	}

	@OverridingMethodsMustInvokeSuper
	@Override
	public InvoiceCandidateExpectation<ParentExpectationType> assertExpected(final ErrorMessage message, final I_C_Invoice_Candidate actual)
	{
		final de.metas.materialtracking.model.I_C_Invoice_Candidate actualExt =
				InterfaceWrapperHelper.create(actual, de.metas.materialtracking.model.I_C_Invoice_Candidate.class);

		final ErrorMessage messageToUse = derive(message)
				.addContextInfo(actual);

		if (groupTypeSet)
		{

			final QualityInvoiceLineGroupType actualGroupType = QualityInvoiceLineGroupType.ofAD_Ref_List_Value(actualExt.getQualityInvoiceLineGroupType());
			assertEquals(messageToUse.expect("QualityInvoiceLineGroupType"), groupType, actualGroupType);
		}

		// call the base class only now, because a failing group type might be a stringer indicator of a problem..
		super.assertExpected(message, actual);

		return this;
	}
}
