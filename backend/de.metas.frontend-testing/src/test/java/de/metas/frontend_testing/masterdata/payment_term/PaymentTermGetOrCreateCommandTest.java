package de.metas.frontend_testing.masterdata.payment_term;

import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.payment.paymentterm.PaymentTermId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_PaymentTerm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTermGetOrCreateCommandTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void execute_whenTermWithValueExists_returnsItAndCreatesNothing()
	{
		final I_C_PaymentTerm existing = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		existing.setValue("1000");
		existing.setName("Existing");
		existing.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(existing);

		final PaymentTermId id = PaymentTermGetOrCreateCommand.builder().value("1000").build().execute();

		assertThat(id.getRepoId()).isEqualTo(existing.getC_PaymentTerm_ID());
		assertThat(POJOLookupMap.get().getRecords(I_C_PaymentTerm.class)).hasSize(1);
	}

	@Test
	public void execute_whenNoTermWithValue_createsActiveTermWithThatValue()
	{
		final PaymentTermId id = PaymentTermGetOrCreateCommand.builder().value("1000").build().execute();

		final I_C_PaymentTerm created = InterfaceWrapperHelper.load(id, I_C_PaymentTerm.class);
		assertThat(created.getValue()).isEqualTo("1000");
		assertThat(created.isActive()).isTrue();
		assertThat(created.getName()).startsWith("Term 1000_").isNotEqualTo("Term 1000");
		assertThat(created.getAD_Org_ID()).isEqualTo(MasterdataContext.ORG_ID.getRepoId());

		// second call finds the term it created
		assertThat(PaymentTermGetOrCreateCommand.builder().value("1000").build().execute()).isEqualTo(id);
	}

	@Test
	public void execute_ignoresTermOfAnotherOrg()
	{
		final I_C_PaymentTerm otherOrgTerm = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		otherOrgTerm.setAD_Org_ID(MasterdataContext.ORG_ID.getRepoId() + 1);
		otherOrgTerm.setValue("1000");
		otherOrgTerm.setName("Other org");
		otherOrgTerm.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(otherOrgTerm);

		final PaymentTermId id = PaymentTermGetOrCreateCommand.builder().value("1000").build().execute();

		assertThat(id.getRepoId()).isNotEqualTo(otherOrgTerm.getC_PaymentTerm_ID());
	}

	@Test
	public void execute_findsTermOfOrgAny()
	{
		final I_C_PaymentTerm anyOrgTerm = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		anyOrgTerm.setAD_Org_ID(0);
		anyOrgTerm.setValue("1000");
		anyOrgTerm.setName("Any org");
		anyOrgTerm.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(anyOrgTerm);

		assertThat(PaymentTermGetOrCreateCommand.builder().value("1000").build().execute().getRepoId())
				.isEqualTo(anyOrgTerm.getC_PaymentTerm_ID());
	}
}
