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

package de.metas.bpartner.service;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.name.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.BPartnerNameAndGreetingStrategyId;
import de.metas.bpartner.name.ComputeNameAndGreetingRequest;
import de.metas.bpartner.name.DoNothingBPartnerNameAndGreetingStrategy;
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.greeting.GreetingId;
import de.metas.i18n.ExplainedOptional;
import de.metas.logging.LogManager;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.util.Env;
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

	public BPartnerQuickInputService(
			@NonNull final BPartnerQuickInputRepository bpartnerQuickInputRepository, final BPartnerNameAndGreetingStrategies bpartnerNameAndGreetingStrategies)
	{
		this.bpartnerQuickInputRepository = bpartnerQuickInputRepository;
		this.bpartnerNameAndGreetingStrategies = bpartnerNameAndGreetingStrategies;
	}

	public void updateNameAndGreeting(final int bpartnerQuickInputId)
	{
		final I_C_BPartner_QuickInput bpartner = bpartnerQuickInputRepository.getById(bpartnerQuickInputId);
		computeBPartnerNameAndGreeting(bpartner)
				.ifPresent(nameAndGreeting -> {
					bpartner.setName(nameAndGreeting.getName());
					bpartner.setC_Greeting_ID(GreetingId.toRepoId(nameAndGreeting.getGreetingId()));
					bpartnerQuickInputRepository.save(bpartner);
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

			final BPartnerNameAndGreetingStrategyId strategyId = bpGroupDAO.getBPartnerNameAndGreetingStrategyId(bpGroupId);
			if (DoNothingBPartnerNameAndGreetingStrategy.ID.equals(strategyId))
			{
				return DoNothingBPartnerNameAndGreetingStrategy.EMPTY_RESULT;
			}

			final List<I_C_BPartner_Contact_QuickInput> contacts = bpartnerQuickInputRepository.retrieveContactsByQuickInputId(bpartner.getC_BPartner_QuickInput_ID());
			if (contacts.isEmpty())
			{
				return ExplainedOptional.emptyBecause("no contacts");
			}

			final ComputeNameAndGreetingRequest.ComputeNameAndGreetingRequestBuilder requestBuilder = ComputeNameAndGreetingRequest.builder();
			for (int i = 0; i < contacts.size(); i++)
			{
				final I_C_BPartner_Contact_QuickInput contact = contacts.get(0);
				requestBuilder.contact(
						ComputeNameAndGreetingRequest.Contact.builder()
								.firstName(contact.getFirstname())
								.lastName(contact.getLastname())
								.seqNo(i + 1)
								.isDefaultContact(i == 0)
								.isMembershipContact(contact.isMembershipContact())
								.build());
			}

			return bpartnerNameAndGreetingStrategies.compute(strategyId, requestBuilder.build());
		}
	}

	/**
	 * Creates BPartner, Location and contact from given template.
	 *
	 * @return created bpartner
	 * Task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	public void createFromTemplate(final I_C_BPartner_QuickInput template)
	{
		Check.assumeNotNull(template, "Parameter template is not null");
		Check.assume(!template.isProcessed(), "{} not already processed", template);
		Check.assume(template.getC_Location_ID() > 0, "{} > 0", template); // just to make sure&explicit

		Services.get(ITrxManager.class).assertThreadInheritedTrxExists();

		//
		// BPartner
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(Env.getCtx(), I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
		bpartner.setAD_Org_ID(template.getAD_Org_ID());
		// bpartner.setValue(Value);
		bpartner.setName(extractName(template));
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
		//
		bpartnerDAO.save(bpartner);

		template.setC_BPartner(bpartner);

		//
		// BPartner location
		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(template.getC_Location_ID());
		bpLocation.setIsBillTo(true);
		bpLocation.setIsBillToDefault(true);
		bpLocation.setIsShipTo(true);
		bpLocation.setIsShipToDefault(true);
		bpartnerDAO.save(bpLocation);

		template.setC_BPartner_Location(bpLocation);

		final boolean isContactInfoProvided = !Check.isEmpty(template.getFirstname()) || !Check.isEmpty(template.getLastname());

		if (isContactInfoProvided)
		{
			//
			// BPartner contact
			final I_AD_User bpContact = InterfaceWrapperHelper.newInstance(I_AD_User.class, bpartner);
			bpContact.setC_BPartner_ID(bpartner.getC_BPartner_ID());
			bpContact.setC_Greeting(template.getC_Greeting());
			bpContact.setFirstname(template.getFirstname());
			bpContact.setLastname(template.getLastname());
			bpContact.setPhone(template.getPhone());
			bpContact.setEMail(template.getEMail());
			bpContact.setIsNewsletter(template.isNewsletter());
			bpContact.setC_BPartner_Location_ID(bpLocation.getC_BPartner_Location_ID());
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
	}

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
