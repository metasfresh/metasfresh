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
import de.metas.bpartner.composite.BPartner;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerContactType;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.name.NameAndGreeting;
import de.metas.bpartner.name.strategy.BPartnerNameAndGreetingStrategies;
import de.metas.bpartner.name.strategy.ComputeNameAndGreetingRequest;
import de.metas.bpartner.quick_input.BPartnerQuickInputId;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.greeting.GreetingId;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.i18n.Language;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.user.api.IUserBL;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Contact_QuickInput;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BPartnerQuickInputService
{
	private static final Logger logger = LogManager.getLogger(BPartnerQuickInputService.class);
	private final BPartnerQuickInputRepository bpartnerQuickInputRepository;
	private final BPartnerNameAndGreetingStrategies bpartnerNameAndGreetingStrategies;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;
	private final IUserBL userBL = Services.get(IUserBL.class);
	private final IBPGroupDAO bpGroupDAO = Services.get(IBPGroupDAO.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final ModelDynAttributeAccessor<I_C_BPartner_QuickInput, Boolean>
			DYNATTR_UPDATING_NAME_AND_GREETING = new ModelDynAttributeAccessor<>("UPDATING_NAME_AND_GREETING", Boolean.class);

	public BPartnerQuickInputService(
			@NonNull final BPartnerQuickInputRepository bpartnerQuickInputRepository,
			@NonNull final BPartnerNameAndGreetingStrategies bpartnerNameAndGreetingStrategies,
			@NonNull final BPartnerCompositeRepository bpartnerCompositeRepository)
	{
		this.bpartnerQuickInputRepository = bpartnerQuickInputRepository;
		this.bpartnerNameAndGreetingStrategies = bpartnerNameAndGreetingStrategies;
		this.bpartnerCompositeRepository = bpartnerCompositeRepository;
	}

	public Optional<AdWindowId> getNewBPartnerWindowId()
	{
		return RecordWindowFinder.newInstance(I_C_BPartner_QuickInput.Table_Name)
				.ignoreExcludeFromZoomTargetsFlag()
				.findAdWindowId();
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
		if (DYNATTR_UPDATING_NAME_AND_GREETING.is(bpartner, true))
		{
			return;
		}

		computeBPartnerNameAndGreeting(bpartner)
				.ifPresent(nameAndGreeting -> {
					bpartner.setBPartnerName(nameAndGreeting.getName());
					bpartner.setC_Greeting_ID(GreetingId.toRepoId(nameAndGreeting.getGreetingId()));
					if (doSave)
					{
						try (final IAutoCloseable ignored = DYNATTR_UPDATING_NAME_AND_GREETING.temporarySetValue(bpartner, true))
						{
							bpartnerQuickInputRepository.save(bpartner);
						}
					}
				})
				.ifAbsent(reason -> logger.debug("Skip updating {} because: {}", bpartner, reason.getDefaultValue()));
	}

	public ExplainedOptional<NameAndGreeting> computeBPartnerNameAndGreeting(final I_C_BPartner_QuickInput bpartner)
	{
		if (bpartner.isCompany())
		{
			final String companyname = bpartner.getCompanyname();
			if (companyname == null || Check.isBlank(companyname))
			{
				return ExplainedOptional.emptyBecause("Companyname is not set");
			}
			else
			{
				return ExplainedOptional.of(NameAndGreeting.builder()
						.name(companyname)
						.greetingId(GreetingId.ofRepoIdOrNull(bpartner.getC_Greeting_ID())) // preserve current greeting
						.build());
			}
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

		final BooleanWithReason canCreateNewBPartner = Env.getUserRolePermissions()
				.checkCanCreateNewRecord(
						ClientId.ofRepoId(template.getAD_Client_ID()),
						OrgId.ofRepoId(template.getAD_Org_ID()),
						adTableDAO.retrieveAdTableId(I_C_BPartner.Table_Name));
		if (canCreateNewBPartner.isFalse())
		{
			throw new AdempiereException(canCreateNewBPartner.getReason());
		}

		trxManager.assertThreadInheritedTrxExists();

		final BPartnerComposite bpartnerComposite = toBPartnerComposite(template);
		bpartnerCompositeRepository.save(bpartnerComposite);

		//
		// Update the location of all contacts
		final BPartnerLocationId bpartnerLocationId = bpartnerComposite.getLocations().get(0).getId();
		bpartnerComposite
				.getContacts()
				.forEach(contact -> contact.setBPartnerLocationId(bpartnerLocationId));
		bpartnerCompositeRepository.save(bpartnerComposite);

		//
		// Update the template and mark it as processed
		final BPartnerId bpartnerId = bpartnerComposite.getBpartner().getId();
		template.setC_BPartner_ID(bpartnerId.getRepoId());
		template.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		template.setProcessed(true);
		bpartnerQuickInputRepository.save(template);

		return bpartnerId;
	}

	private BPartnerComposite toBPartnerComposite(final I_C_BPartner_QuickInput template)
	{
		final BPGroupId groupId = BPGroupId.ofRepoIdOrNull(template.getC_BP_Group_ID());
		if (groupId == null)
		{
			throw new FillMandatoryException("C_BP_Group_ID");
		}

		final LocationId existingLocationId = LocationId.ofRepoIdOrNull(template.getC_Location_ID());
		if (existingLocationId == null)
		{
			throw new FillMandatoryException(I_C_BPartner_QuickInput.COLUMNNAME_C_Location_ID);
		}

		//
		// BPartner (header)
		final BPartner bpartner = BPartner.builder()
				.value(null) // to be generated
				.name(template.getBPartnerName())
				.name2(template.getName2())
				.company(template.isCompany())
				.companyName(template.getCompanyname())
				.groupId(groupId)
				.language(Language.asLanguage(template.getAD_Language()))
				// Customer:
				.customer(template.isCustomer())
				.customerPricingSystemId(PricingSystemId.ofRepoIdOrNull(template.getM_PricingSystem_ID()))
				.customerPaymentTermId(PaymentTermId.ofRepoIdOrNull(template.getC_PaymentTerm_ID()))
				// Vendor:
				.vendor(true)
				.vendorPricingSystemId(PricingSystemId.ofRepoIdOrNull(template.getPO_PricingSystem_ID()))
				.vendorPaymentTermId(PaymentTermId.ofRepoIdOrNull(template.getPO_PaymentTerm_ID()))
				//
				.build();

		//
		// BPartner Location
		final BPartnerLocation bpLocation = BPartnerLocation.builder()
				.locationType(BPartnerLocationType.builder()
						.billTo(true)
						.billToDefault(true)
						.shipTo(true)
						.shipToDefault(true)
						.build())
				.name(".")
				.existingLocationId(existingLocationId)
				.build();

		//
		// Contacts
		final ArrayList<BPartnerContact> contacts = new ArrayList<>();
		{
			final BPartnerQuickInputId bpartnerQuickInputId = BPartnerQuickInputId.ofRepoId(template.getC_BPartner_QuickInput_ID());
			final List<I_C_BPartner_Contact_QuickInput> contactTemplates = bpartnerQuickInputRepository.retrieveContactsByQuickInputId(bpartnerQuickInputId);
			for (final I_C_BPartner_Contact_QuickInput contactTemplate : contactTemplates)
			{
				final boolean isDefaultContact = contacts.isEmpty();
				final boolean isSalesContact = template.isCustomer();
				final boolean isPurchaseContact = template.isVendor();

				contacts.add(BPartnerContact.builder()
						.contactType(BPartnerContactType.builder()
								.defaultContact(isDefaultContact)
								.billToDefault(isDefaultContact)
								.shipToDefault(isDefaultContact)
								.sales(isSalesContact)
								.salesDefault(isSalesContact && isDefaultContact)
								.purchase(isPurchaseContact)
								.purchaseDefault(isPurchaseContact && isDefaultContact)
								.build())
						.newsletter(contactTemplate.isNewsletter())
						.membershipContact(contactTemplate.isMembershipContact())
						.firstName(contactTemplate.getFirstname())
						.lastName(contactTemplate.getLastname())
						.name(userBL.buildContactName(contactTemplate.getFirstname(), contactTemplate.getLastname()))
						.greetingId(GreetingId.ofRepoIdOrNull(contactTemplate.getC_Greeting_ID()))
						.phone(StringUtils.trimBlankToNull(template.getPhone()))
						.email(StringUtils.trimBlankToNull(template.getEMail()))
						// TODO: contact seqNo
						.build());
			}
		}

		return BPartnerComposite.builder()
				.orgId(OrgId.ofRepoId(template.getAD_Org_ID()))
				.bpartner(bpartner)
				.location(bpLocation)
				.contacts(contacts)
				.build();
	}
}
