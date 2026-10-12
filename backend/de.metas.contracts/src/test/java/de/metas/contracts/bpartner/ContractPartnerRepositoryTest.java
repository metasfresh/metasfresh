package de.metas.contracts.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.flatrate.TypeConditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocStatus;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Customer_Retention;
import org.compiere.model.X_C_Customer_Retention;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

public class ContractPartnerRepositoryTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	/**
	 * The customer retention process recomputes partners with a subscription contract, and clears the value of partners that have one but no subscription contract (any more);
	 * partners with only other contracts and no value are left out.
	 */
	@Test
	public void iteratePartnersForCustomerRetention()
	{
		final BPartnerId subscriptionPartner = createPartner();
		createTerm(subscriptionPartner, TypeConditions.SUBSCRIPTION, DocStatus.Completed);

		final BPartnerId refundPartnerWithValue = createPartner();
		createTerm(refundPartnerWithValue, TypeConditions.REFUND, DocStatus.Completed);
		createCustomerRetention(refundPartnerWithValue, X_C_Customer_Retention.CUSTOMERRETENTION_Stammkunde);

		final BPartnerId refundPartnerWithoutValue = createPartner();
		createTerm(refundPartnerWithoutValue, TypeConditions.REFUND, DocStatus.Completed);
		createCustomerRetention(refundPartnerWithoutValue, null);

		final BPartnerId draftSubscriptionPartner = createPartner();
		createTerm(draftSubscriptionPartner, TypeConditions.SUBSCRIPTION, DocStatus.Drafted);

		final Iterator<BPartnerId> partners = new ContractPartnerRepository().iteratePartnersForCustomerRetention();
		final List<BPartnerId> partnerIds;
		try
		{
			partnerIds = IteratorUtils.asList(partners);
		}
		finally
		{
			IteratorUtils.close(partners);
		}

		assertThat(partnerIds).containsExactlyInAnyOrder(subscriptionPartner, refundPartnerWithValue);
	}

	private static BPartnerId createPartner()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		save(partner);
		return BPartnerId.ofRepoId(partner.getC_BPartner_ID());
	}

	private static void createTerm(@NonNull final BPartnerId bpartnerId, @NonNull final TypeConditions typeConditions, @NonNull final DocStatus docStatus)
	{
		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setBill_BPartner_ID(bpartnerId.getRepoId());
		term.setType_Conditions(typeConditions.getCode());
		term.setDocStatus(docStatus.getCode());
		save(term);
	}

	private static void createCustomerRetention(@NonNull final BPartnerId bpartnerId, @Nullable final String customerRetention)
	{
		final I_C_Customer_Retention record = newInstance(I_C_Customer_Retention.class);
		record.setC_BPartner_ID(bpartnerId.getRepoId());
		record.setCustomerRetention(customerRetention);
		save(record);
	}
}
