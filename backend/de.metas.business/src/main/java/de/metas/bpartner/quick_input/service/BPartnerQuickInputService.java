/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.bpartner.quick_input.service;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.strategy.ComputeNameAndGreetingRequest;
import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.greeting.GreetingId;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BPartnerQuickInputService
{
	private static final Logger logger = LogManager.getLogger(BPartnerQuickInputService.class);
	private final BPartnerQuickInputRepository bpartnerQuickInputRepository;
	private final BPartnerNameAndGreetingStrategies bpartnerNameAndGreetingStrategies;
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	public BPartnerQuickInputService(
			@NonNull final BPartnerQuickInputRepository bpartnerQuickInputRepository, final BPartnerNameAndGreetingStrategies bpartnerNameAndGreetingStrategies)
	{
		this.bpartnerQuickInputRepository = bpartnerQuickInputRepository;
		this.bpartnerNameAndGreetingStrategies = bpartnerNameAndGreetingStrategies;
	}

	public void updateNameAndGreeting(@NonNull final BPartnerQuickInputId bpartnerQuickInputId)
	{
		final I_C_BPartner_QuickInput bpartner = bpartnerQuickInputRepository.getById(bpartnerQuickInputId);
		final boolean doSave = true;
		updateNameAndGreeting(bpartner, doSave);
	}

	public void updateNameAndGreetingNoSave(@NonNull final I_C_BPartner_QuickInput bpartner)
	{
		final boolean doSave = false;
		updateNameAndGreeting(bpartner, doSave);
	}

	private void updateNameAndGreeting(
			@NonNull final I_C_BPartner_QuickInput bpartner,
			final boolean doSave)
	{
		computeBPartnerNameAndGreeting(bpartner)
				.ifPresent(nameAndGreeting -> {
					bpartner.setName(nameAndGreeting.getName());
					bpartner.setC_Greeting_ID(GreetingId.toRepoId(nameAndGreeting.getGreetingId()));
					if (doSave)
					{
						bpartnerQuickInputRepository.save(bpartner);
					}
				})
				.ifAbsent(reason -> logger.debug("Skip updating {} because: {}", bpartner, reason.getDefaultValue()));
	}

	public ExplainedOptional<NameAndGreeting> computeBPartnerNameAndGreeting(final I_C_BPartner_QuickInput bpartner)
	{
		if (bpartner.isCompany())
		{
			return ExplainedOptional.of(NameAndGreeting.builder()
					.name(bpartner.getCompanyname())
					.greetingId(GreetingId.ofRepoIdOrNull(bpartner.getC_Greeting_ID())) // preserve current greeting
					.build());
		}
		else
		{
			final BPGroupId bpGroupId = BPGroupId.ofRepoIdOrNull(bpartner.getC_BP_Group_ID());
			if (bpGroupId == null)
			{
				return ExplainedOptional.emptyBecause("C_BP_Group_ID was not set");
			}

			final List<I_C_BPartner_Contact_QuickInput> contacts = bpartnerQuickInputRepository.retrieveContactsByQuickInputId(BPartnerQuickInputId.ofRepoId(bpartner.getC_BPartner_QuickInput_ID()));
			if (contacts.isEmpty())
			{
				return ExplainedOptional.emptyBecause("no contacts");
			}

			return bpartnerNameAndGreetingStrategies.compute(
					bpGroupDAO.getBPartnerNameAndGreetingStrategyId(bpGroupId),
					toComputeNameAndGreetingRequest(contacts));
		}
	}

	private static ComputeNameAndGreetingRequest toComputeNameAndGreetingRequest(
			final List<I_C_BPartner_Contact_QuickInput> contacts)
	{
		final ComputeNameAndGreetingRequest.ComputeNameAndGreetingRequestBuilder requestBuilder = ComputeNameAndGreetingRequest.builder();
		for (int i = 0; i < contacts.size(); i++)
		{
			final I_C_BPartner_Contact_QuickInput contact = contacts.get(0);
			requestBuilder.contact(
					ComputeNameAndGreetingRequest.Contact.builder()
							.greetingId(GreetingId.ofRepoIdOrNull(contact.getC_Greeting_ID()))
							.firstName(contact.getFirstname())
							.lastName(contact.getLastname())
							.seqNo(i + 1)
							.isDefaultContact(i == 0)
							.isMembershipContact(contact.isMembershipContact())
							.build());
		}

		return requestBuilder.build();
	}

	/**
	 * Creates BPartner, Location and contacts from given template.
	 * <p>
	 * Task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	public BPartnerId createBPartnerFromTemplate(@NonNull final I_C_BPartner_QuickInput template)
	{
		Check.assume(!template.isProcessed(), "{} not already processed", template);
		Check.assume(template.getC_Location_ID() > 0, "{} > 0", template); // just to make sure&explicit

		trxManager.assertThreadInheritedTrxExists();

		//
		// BPartner
		final BPartnerId bpartnerId;
		{
			final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
			bpartner.setAD_Org_ID(template.getAD_Org_ID());
			// bpartner.setValue(Value); // will be generated
			bpartner.setName(template.getName());
			bpartner.setName2(template.getName2());
			bpartner.setIsCompany(template.isCompany());
			bpartner.setCompanyName(template.getCompanyname());
			bpartner.setC_BP_Group_ID(template.getC_BP_Group_ID());
			bpartner.setAD_Language(template.getAD_Language());
			// Customer
			bpartner.setIsCustomer(template.isCustomer());
			bpartner.setC_PaymentTerm_ID(template.getC_PaymentTerm_ID());
			bpartner.setM_PricingSystem_ID(template.getM_PricingSystem_ID());
			// Vendor
			bpartner.setIsVendor(template.isVendor());
			bpartner.setPO_PaymentTerm_ID(template.getPO_PaymentTerm_ID());
			bpartner.setPO_PricingSystem_ID(template.getPO_PricingSystem_ID());

			bpartnerDAO.save(bpartner);
			bpartnerId = BPartnerId.ofRepoId(template.getC_BPartner_ID());

			template.setC_BPartner_ID(bpartnerId.getRepoId());
		}

		//
		// BPartner location
		final BPartnerLocationId bpartnerLocationId;
		{
			final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
			bpLocation.setC_BPartner_ID(bpartnerId.getRepoId());
			bpLocation.setC_Location_ID(template.getC_Location_ID());
			bpLocation.setIsBillTo(true);
			bpLocation.setIsBillToDefault(true);
			bpLocation.setIsShipTo(true);
			bpLocation.setIsShipToDefault(true);
			bpartnerDAO.save(bpLocation);
			bpartnerLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());

			template.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		}

		final boolean isContactInfoProvided = !Check.isEmpty(template.getFirstname()) || !Check.isEmpty(template.getLastname());

		if (isContactInfoProvided)
		{
			//
			// BPartner contact
			final I_AD_User bpContact = InterfaceWrapperHelper.newInstance(I_AD_User.class);
			bpContact.setC_BPartner_ID(bpartnerId.getRepoId());
			bpContact.setC_Greeting(template.getC_Greeting());
			bpContact.setFirstname(template.getFirstname());
			bpContact.setLastname(template.getLastname());
			bpContact.setPhone(template.getPhone());
			bpContact.setEMail(template.getEMail());
			bpContact.setIsNewsletter(template.isNewsletter());
			bpContact.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
			if (template.isCustomer())
			{
				bpContact.setIsSalesContact(true);
				bpContact.setIsSalesContact_Default(true);
			}
			if (template.isVendor())
			{
				bpContact.setIsPurchaseContact(true);
				bpContact.setIsPurchaseContact_Default(true);
			}
			bpartnerDAO.save(bpContact);

			template.setAD_User(bpContact);
		}

		template.setProcessed(true);
		bpartnerQuickInputRepository.save(template);

		return BPartnerId.ofRepoId(template.getC_BPartner_ID());
	}

	// private BPartnerComposite toBPartnerComposite(final I_C_BPartner_QuickInput template)
	// {
	// 	final BPartner bpartner = BPartner.builder()
	// 			.value(null) // to be generated
	// 			.name(template.getName())
	// 			.name2(template.getName2())
	// 			.company(template.isCompany())
	// 			.companyName(template.getCompanyname())
	// 			.groupId(BPGroupId.ofRepoId(template.getC_BP_Group_ID()))
	// 			.language(Language.asLanguage(template.getAD_Language()))
	// 			// Customer:
	// 			.customer(template.isCustomer())
	// 			.customerPricingSystemId(PricingSystemId.ofRepoIdOrNull(template.getM_PricingSystem_ID()))
	// 			.customerPaymentTermId(PaymentTermId.ofRepoIdOrNull(template.getC_PaymentTerm_ID()))
	// 			// Vendor:
	// 			.vendor(true)
	// 			.vendorPricingSystemId(PricingSystemId.ofRepoIdOrNull(template.getPO_PricingSystem_ID()))
	// 			.vendorPaymentTermId(PaymentTermId.ofRepoIdOrNull(template.getPO_PaymentTerm_ID()))
	// 			//
	// 			.build();
	//
	// 	final BPartnerLocation bpLocation = BPartnerLocation.builder()
	// 			.locationType(BPartnerLocationType.builder()
	// 					.billTo(true)
	// 					.billToDefault(true)
	// 					.shipTo(true)
	// 					.shipToDefault(true)
	// 					.build())
	// 			.existingLocationId(LocationId.ofRepoId(template.getC_Location_ID()))
	// 			.build();
	//
	// 	return BPartnerComposite.builder()
	// 			.bpartner(bpartner)
	// 			.location(bpLocation)
	// 			// TODO: contact
	// 			.build();
	// }

	private String extractName(final I_C_BPartner_QuickInput template)
	{
		if (template.isCompany())
		{
			return template.getCompanyname();
		}
		else
		{
			return userBL.buildContactName(template.getFirstname(), template.getLastname());
		}
	}
}
