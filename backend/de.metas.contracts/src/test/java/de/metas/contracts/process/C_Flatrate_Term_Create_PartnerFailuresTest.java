package de.metas.contracts.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.contracts.FlatrateTermRequest.CreateFlatrateTermRequest;
import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * How "Erzeuge Vertrag" ends when a partner's contract could not be created: every partner is still processed
 * (own transaction), successful contracts stay, and the action ends with ONE error naming the failed partners.
 */
class C_Flatrate_Term_Create_PartnerFailuresTest
{
	private IFlatrateBL flatrateBL;
	private I_C_Flatrate_Conditions conditions;
	private final List<String> processedPartners = new ArrayList<>();

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		flatrateBL = Mockito.mock(IFlatrateBL.class);
		Services.registerService(IFlatrateBL.class, flatrateBL);

		conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setName("c");
		saveRecord(conditions);
	}

	private I_C_BPartner partner(final String value)
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setValue(value);
		partner.setName("name_" + value);
		saveRecord(partner);
		return partner;
	}

	/** Makes the BL fail for the given partner values and create a term for all others. */
	private void failFor(final Set<String> failingValues, final String reason)
	{
		Mockito.when(flatrateBL.createTerm(Mockito.any(CreateFlatrateTermRequest.class))).thenAnswer(invocation -> {
			final CreateFlatrateTermRequest request = invocation.getArgument(0);
			final String value = request.getBPartner().getValue();
			processedPartners.add(value);
			if (failingValues.contains(value))
			{
				throw new AdempiereException(reason + " " + value).markAsUserValidationError();
			}
			return newInstance(I_C_Flatrate_Term.class);
		});
	}

	private C_Flatrate_Term_Create process(final List<I_C_BPartner> partners)
	{
		final C_Flatrate_Term_Create process = new C_Flatrate_Term_Create()
		{
			@Override
			protected Iterable<I_C_BPartner> getBPartners()
			{
				return partners;
			}
		};
		process.setConditions(conditions);
		process.setStartDate(new java.sql.Timestamp(System.currentTimeMillis()));
		process.addProduct(null);
		return process;
	}

	@Test
	void successfulMultiPartnerRun_endsOk()
	{
		failFor(ImmutableSet.of(), "n/a");

		final String result = runDoIt(process(ImmutableList.of(partner("P1"), partner("P2"), partner("P3"))));

		assertThat(result).isEqualTo("OK");
		assertThat(processedPartners).containsExactly("P1", "P2", "P3");
	}

	@Test
	void oneFailingPartner_othersStillProcessed_oneErrorNamesTheFailedOne()
	{
		failFor(ImmutableSet.of("P2"), "overlap");
		final C_Flatrate_Term_Create process = process(ImmutableList.of(partner("P1"), partner("P2"), partner("P3")));

		assertThatThrownBy(() -> process.doIt())
				.isInstanceOf(AdempiereException.class)
				.satisfies(ex -> {
					final String message = ex.getMessage();
					assertThat(message).contains("P2_name_P2: overlap P2");
					assertThat(message).doesNotContain("P1_name_P1").doesNotContain("P3_name_P3");
				});

		assertThat(processedPartners).as("the partner after the failing one is still processed").containsExactly("P1", "P2", "P3");
	}

	@Test
	void manyFailures_listIsCapped()
	{
		final List<FlatrateTermCreator.PartnerFailure> failures = new ArrayList<>();
		for (int i = 1; i <= 25; i++)
		{
			failures.add(FlatrateTermCreator.PartnerFailure.of("P" + i, "reason"));
		}

		final String message = C_Flatrate_Term_Create.buildFailuresMessage(failures).translate("en_US");

		assertThat(message).contains("P1: reason").contains("P20: reason").doesNotContain("P21: reason");
		assertThat(message).contains("5");
	}

	private String runDoIt(final C_Flatrate_Term_Create process)
	{
		try
		{
			return process.doIt();
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
