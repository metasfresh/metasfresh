package de.metas.bpartner.composite;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.document.DocTypeId;
import de.metas.greeting.GreetingId;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.i18n.TranslatableStrings;
import de.metas.incoterms.IncotermsId;
import de.metas.marketing.base.model.CampaignId;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.payment.PaymentRule;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.sectionCode.SectionCodeId;
import de.metas.util.lang.ExternalId;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.table.RecordChangeLog;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

import static de.metas.common.util.CoalesceUtil.coalesce;
import static de.metas.util.Check.isEmpty;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Data
public class BPartner
{
	public static final String ID = "id";
	public static final String EXTERNAL_ID = "externalId";
	public static final String GLOBAL_ID = "globalId";
	public static final String ACTIVE = "active";
	public static final String NAME = "name";
	public static final String NAME_2 = "name2";
	public static final String NAME_3 = "name3";
	public static final String COMPANY_NAME = "companyName";
	public static final String PARENT_ID = "parentId";
	public static final String VALUE = "value";
	public static final String PHONE = "phone";
	public static final String LANGUAGE = "language";
	public static final String URL = "url";
	public static final String URL_2 = "url2";
	public static final String URL_3 = "url3";
	public static final String GROUP_ID = "groupId";
	public static final String VENDOR = "vendor";
	public static final String CUSTOMER = "customer";
	public static final String COMPANY = "company";
	public static final String SALES_PARTNER_CODE = "salesPartnerCode";
	public static final String C_BPARTNER_SALES_REP_ID = "bPartnerSalesRepId";
	public static final String PAYMENT_RULE = "paymentRule";
	public static final String PAYMENT_RULE_PO = "paymentRulePO";
	public static final String INTERNAL_NAME = "internalName";
	public static final String VAT_ID = "vatId";
	public static final String GREETING_ID = "greetingId";
	public static final String CUSTOMER_PAYMENTTERM_ID = "customerPaymentTermId";
	public static final String CUSTOMER_PRICING_SYSTEM_ID = "customerPricingSystemId";
	public static final String VENDOR_PAYMENTTERM_ID = "vendorPaymentTermId";
	public static final String VENDOR_PRICING_SYSTEM_ID = "vendorPricingSystemId";
	public static final String EXCLUDE_FROM_PROMOTIONS = "excludeFromPromotions";
	public static final String REFERRER = "referrer";
	public static final String CAMPAIGN_ID = "campaignId";
	public static final String CREDITOR_ID = "creditorId";
	public static final String DEBTOR_ID = "debtorId";
	public static final String SECTION_CODE_ID = "sectionCodeId";
	public static final String DESCRIPTION = "description";
	public static final String DELIVERY_RULE = "deliveryRule";
	public static final String DELIVERY_VIA_RULE = "deliveryViaRule";
	public static final String STORAGE_WAREHOUSE = "storageWarehouse";
	public static final String INCOTERMS_CUSTOMER_ID = "incotermsCustomerId";
	public static final String INCOTERMS_VENDOR_ID = "incotermsVendorId";
	public static final String SECTION_GROUP_PARTNER_ID = "sectionGroupPartnerId";
	public static final String PROSPECT = "isProspect";
	public static final String SAP_BPARTNER_CODE = "sapBPartnerCode";
	public static final String SECTION_GROUP_PARTNER = "sectionGroupPartner";
	public static final String SECTION_PARTNER = "sectionPartner";

	/**
	 * May be null if the bpartner was not yet saved.
	 */
	private BPartnerId id;

	private ExternalId externalId;
	private boolean active;
	private String value;
	private String name;
	private String name2;
	private String name3;
	private final GreetingId greetingId;

	private final DocTypeId soDocTypeTargetId;
	private final String firstName;
	private final String lastName;

	/**
	 * non-empty value implies that the bpartner is also a company
	 */
	private String companyName;

	/**
	 * This translates to `C_BPartner.BPartner_Parent_ID`. It's a this bpartner's central/parent company.
	 */
	private BPartnerId parentId;

	/**
	 * This translates to `C_BPartner.Phone2`. It's this bpartner's central phone number.
	 */
	private String phone;

	private Language language;

	private String url;

	private String url2;

	private String url3;

	private BPGroupId groupId;

	private boolean vendor;
	private boolean customer;
	private boolean company;
	private String salesPartnerCode;
	private SalesRep salesRep;
	private PaymentRule paymentRule;
	private PaymentRule paymentRulePO;
	private String internalName;

	private InvoiceRule customerInvoiceRule;
	private InvoiceRule vendorInvoiceRule;

	private String globalId;

	private String vatId;

	private OrgMappingId orgMappingId;

	private final RecordChangeLog changeLog;

	private String memo;

	/**
	 * Can be {@link org.compiere.model.X_C_BPartner#SHIPMENTALLOCATION_BESTBEFORE_POLICY_Newest_First} or {@link org.compiere.model.X_C_BPartner#SHIPMENTALLOCATION_BESTBEFORE_POLICY_Expiring_First}.
	 */
	private final String shipmentAllocationBestBeforePolicy;

	/**
	 * If true, it means that the BPartner is valid without having a code, metasfresh or gln.
	 * It's identifier is an external reference that is provided outside of this bpartner's composite.
	 */
	private boolean identifiedByExternalReference;

	private PaymentTermId customerPaymentTermId;
	private PricingSystemId customerPricingSystemId;

	private PaymentTermId vendorPaymentTermId;
	private final PricingSystemId vendorPricingSystemId;

	private final boolean excludeFromPromotions;
	private final String referrer;
	@Nullable private final CampaignId campaignId;

	private final Integer creditorId;
	private final Integer debtorId;

	@Nullable
	private SectionCodeId sectionCodeId;

	@Nullable
	private String description;

	@Nullable
	private DeliveryRule deliveryRule;

	@Nullable
	private DeliveryViaRule deliveryViaRule;

	private boolean storageWarehouse;

	@Nullable
	private IncotermsId incotermsCustomerId;

	@Nullable
	private IncotermsId incotermsVendorId;

	@Nullable
	private BPartnerId sectionGroupPartnerId;

	private boolean prospect;

	@Nullable
	private String sapBPartnerCode;

	private boolean sectionGroupPartner;

	private boolean sectionPartner;

	private boolean urproduzent;

	/**
	 * They are all nullable because we can create a completely empty instance which we then fill.
	 */
	@Builder(toBuilder = true)
	private BPartner(
			@Nullable final BPartnerId id,
			@Nullable final ExternalId externalId,
			@Nullable final String globalId,
			@Nullable final Boolean active,
			@Nullable final String value,
			@Nullable final String name,
			@Nullable final String name2,
			@Nullable final String name3,
			@Nullable final GreetingId greetingId,
			@Nullable final String companyName,
			@Nullable final BPartnerId parentId,
			@Nullable final String phone,
			@Nullable final Language language,
			@Nullable final String url,
			@Nullable final String url2,
			@Nullable final String url3,
			@Nullable final BPGroupId groupId,
			@Nullable final InvoiceRule customerInvoiceRule,
			@Nullable final InvoiceRule vendorInvoiceRule,
			@Nullable final Boolean vendor,
			@Nullable final Boolean customer,
			@Nullable final Boolean company,
			@Nullable final String salesPartnerCode,
			@Nullable final SalesRep salesRep,
			@Nullable final PaymentRule paymentRule,
			@Nullable final PaymentRule paymentRulePO,
			@Nullable final String internalName,
			@Nullable final String vatId,
			@Nullable final RecordChangeLog changeLog,
			@Nullable final String shipmentAllocationBestBeforePolicy,
			@Nullable final Boolean identifiedByExternalReference,
			@Nullable final OrgMappingId orgMappingId,
			@Nullable final String memo,
			@Nullable final PaymentTermId customerPaymentTermId,
			@Nullable final PricingSystemId customerPricingSystemId,
			@Nullable final PaymentTermId vendorPaymentTermId,
			@Nullable final PricingSystemId vendorPricingSystemId,
			final boolean excludeFromPromotions,
			@Nullable final String referrer,
			@Nullable final CampaignId campaignId,
			@Nullable final DocTypeId soDocTypeTargetId,
			@Nullable final String firstName,
			@Nullable final String lastName,
			@Nullable final Integer creditorId,
			@Nullable final Integer debtorId,
			@Nullable final SectionCodeId sectionCodeId,
			@Nullable final String description,
			@Nullable final DeliveryRule deliveryRule,
			@Nullable final DeliveryViaRule deliveryViaRule,
			@Nullable final Boolean storageWarehouse,
			@Nullable final IncotermsId incotermsCustomerId,
			@Nullable final IncotermsId incotermsVendorId,
			@Nullable final BPartnerId sectionGroupPartnerId,
			@Nullable final Boolean prospect,
			@Nullable final String sapBPartnerCode,
			@Nullable final Boolean sectionGroupPartner,
			@Nullable final Boolean sectionPartner,
			@Nullable final Boolean urproduzent)
	{
		this.id = id;
		this.externalId = externalId;
		this.globalId = globalId;
		this.active = coalesce(active, true);
		this.value = value;
		this.name = name;
		this.name2 = name2;
		this.name3 = name3;
		this.greetingId = greetingId;
		this.companyName = companyName;
		this.parentId = parentId;
		this.phone = phone;
		this.language = language;
		this.url = url;
		this.url2 = url2;
		this.url3 = url3;
		this.groupId = groupId;
		this.customerInvoiceRule = customerInvoiceRule;
		this.vendorInvoiceRule = vendorInvoiceRule;
		this.vendor = coalesce(vendor, false);
		this.customer = coalesce(customer, false);
		this.company = coalesce(company, false);
		this.salesPartnerCode = salesPartnerCode;
		this.salesRep = salesRep;
		this.paymentRule = paymentRule;
		this.paymentRulePO = paymentRulePO;
		this.internalName = internalName;
		this.vatId = vatId;

		this.changeLog = changeLog;
		this.shipmentAllocationBestBeforePolicy = shipmentAllocationBestBeforePolicy;
		this.orgMappingId = orgMappingId;
		this.identifiedByExternalReference = coalesce(identifiedByExternalReference, false);
		this.memo = memo;

		this.customerPaymentTermId = customerPaymentTermId;
		this.customerPricingSystemId = customerPricingSystemId;

		this.vendorPaymentTermId = vendorPaymentTermId;
		this.vendorPricingSystemId = vendorPricingSystemId;
		this.excludeFromPromotions = excludeFromPromotions;
		this.referrer = referrer;
		this.campaignId = campaignId;

		this.soDocTypeTargetId = soDocTypeTargetId;
		this.firstName = firstName;
		this.lastName = lastName;

		this.creditorId = creditorId;
		this.debtorId = debtorId;
		this.sectionCodeId = sectionCodeId;
		this.description = description;
		this.deliveryRule = deliveryRule;
		this.deliveryViaRule = deliveryViaRule;
		this.storageWarehouse = coalesce(storageWarehouse, false);
		this.incotermsCustomerId = incotermsCustomerId;
		this.incotermsVendorId = incotermsVendorId;
		this.sectionGroupPartnerId = sectionGroupPartnerId;
		this.prospect = coalesce(prospect, false);
		this.sapBPartnerCode = sapBPartnerCode;
		this.sectionGroupPartner = coalesce(sectionGroupPartner, false);
		this.sectionPartner = coalesce(sectionPartner, false);
		this.urproduzent = coalesce(urproduzent, false);
	}

	/**
	 * Only active bpartners are actually validated. Empty list means "valid"
	 */
	public ImmutableList<ITranslatableString> validate()
	{
		final ImmutableList.Builder<ITranslatableString> result = ImmutableList.builder();
		if (!isActive())
		{
			return result.build();
		}

		// A missing "bpartner.value" is probably(=>can't be determined here) acceptable because
		// for a new BPartner, org.compiere.model.PO.saveNew() will *most probably* create a new value on the fly (can be switched off, but usually makes no sense).
		// if (isEmpty(value, true))
		// {
		// result.add(TranslatableStrings.constant("bpartner.value"));
		// }
		if (isEmpty(name, true))
		{
			result.add(TranslatableStrings.constant("bpartner.name"));
		}
		if (groupId == null)
		{
			result.add(TranslatableStrings.constant("bpartner.groupId"));
		}
		return result.build();
	}

	@Nullable
	public <T> T mapPaymentRule(@NonNull final Function<PaymentRule,T> mappingFunction)
	{
		return mapValue(mappingFunction, paymentRule);
	}

	@Nullable
	public <T> T mapPaymentRulePO(@NonNull final Function<PaymentRule,T> mappingFunction)
	{
		return mapValue(mappingFunction, paymentRulePO);
	}

	@Nullable
	public <T> T mapDeliveryRule(@NonNull final Function<DeliveryRule,T> mappingFunction)
	{
		return mapValue(mappingFunction, deliveryRule);
	}

	@Nullable
	public <T> T mapDeliveryViaRule(@NonNull final Function<DeliveryViaRule,T> mappingFunction)
	{
		return mapValue(mappingFunction, deliveryViaRule);
	}

	@Nullable
	private <In, Out> Out mapValue(@NonNull final Function<In, Out> mappingFunction, @Nullable final In inputValue)
	{
		return Optional.ofNullable(inputValue)
				.map(mappingFunction)
				.orElse(null);
	}
}
