package de.metas.frontend_testing.masterdata.payment_term;

import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.payment.paymentterm.repository.IPaymentTermRepository;
import de.metas.payment.paymentterm.repository.PaymentTermQuery;
import de.metas.util.Services;
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
		// load the repository's cache first, so we also see that the created term is picked up afterwards
		final IPaymentTermRepository paymentTermRepository = Services.get(IPaymentTermRepository.class);
		final I_C_PaymentTerm other = InterfaceWrapperHelper.newInstance(I_C_PaymentTerm.class);
		other.setValue("other");
		other.setName("other");
		other.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(other);
		assertThat(paymentTermRepository.getById(PaymentTermId.ofRepoId(other.getC_PaymentTerm_ID())).getName()).isEqualTo("other");

		final PaymentTermId id = PaymentTermGetOrCreateCommand.builder().value("1000").build().execute();

		final I_C_PaymentTerm created = InterfaceWrapperHelper.load(id, I_C_PaymentTerm.class);
		assertThat(created.getValue()).isEqualTo("1000");
		assertThat(created.isActive()).isTrue();
		assertThat(created.getName()).startsWith("Term 1000_").isNotEqualTo("Term 1000");
		assertThat(created.getAD_Org_ID()).isEqualTo(MasterdataContext.ORG_ID.getRepoId());
		assertThat(created.isComplex()).isFalse();

		// C_PaymentTerm.NetDays, GraceDays, Discount(2) and DiscountDays(2) are NOT NULL without a database default,
		// so the INSERT must carry them explicitly (the mocked test database does not enforce that).
		for (final String columnName : new String[] { "NetDays", "GraceDays", "Discount", "Discount2", "DiscountDays", "DiscountDays2" })
		{
			assertThat(InterfaceWrapperHelper.isNull(created, columnName)).as(columnName + " is set").isFalse();
		}
		assertThat(created.getNetDays()).isZero();
		assertThat(created.getGraceDays()).isZero();
		assertThat(created.getDiscountDays()).isZero();
		assertThat(created.getDiscountDays2()).isZero();
		assertThat(created.getDiscount()).isEqualByComparingTo("0");
		assertThat(created.getDiscount2()).isEqualByComparingTo("0");

		assertThat(paymentTermRepository.getById(id).getValue()).isEqualTo("1000");
		assertThat(paymentTermRepository.firstIdOnly(PaymentTermQuery.builder().orgId(MasterdataContext.ORG_ID).value("1000").build())).contains(id);

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
