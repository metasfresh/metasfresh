package de.metas.invoicecandidate.expectations;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.test.AbstractExpectation;

/**
 * Created by ts on 29.12.2015.
 */
public class InvoiceCandidateExpectations extends AbstractExpectation<Object>
{

	@SuppressWarnings("rawtypes")
	private List<InvoiceCandidateExpectation> invoiceCandidateExpectationList = new ArrayList<>();

	/**
	 * Creates an InvoiceCandidateExpectation instance whose {@link AbstractExpectation#endExpectation()} method whill return this instance.
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public <T extends InvoiceCandidateExpectation> T newInvoiceCandidateExpectation(final Class<T> clazz)
	{
		try
		{
			final Constructor<T> constructor = clazz.getConstructor(Object.class);
			final T newInvoiceCandidatedateExpectation = constructor.newInstance(this);

			invoiceCandidateExpectationList.add(newInvoiceCandidatedateExpectation);

			return newInvoiceCandidatedateExpectation;
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

}
