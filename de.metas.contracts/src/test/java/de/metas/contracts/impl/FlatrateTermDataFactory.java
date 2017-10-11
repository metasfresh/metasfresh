package de.metas.contracts.impl;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Acct;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * *
 * 
 * @author metas-dev <dev@metasfresh.com>
 */

public class FlatrateTermDataFactory
{
	private static final String city = "Berlin";
	private static final String valuePricingSystem = "Abo";

	@Builder(builderMethodName = "userNew")
	public static I_AD_User createADUser(@NonNull final I_C_BPartner bpartner, final String lastName, final String firstName, final boolean isBillToContact_Default, final boolean isShipToContact_Default)
	{
		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class, bpartner);
		user.setC_BPartner(bpartner);
		user.setLastname(lastName);
		user.setFirstname(firstName);
		user.setIsBillToContact_Default(isBillToContact_Default);
		user.setIsShipToContact_Default(isShipToContact_Default);

		InterfaceWrapperHelper.save(user);
		return user;
	}
	
	@Builder(builderMethodName = "bpLocationNew")
	public static I_C_BPartner_Location createBPartnerLocation(@NonNull final I_C_BPartner bpartner,	final boolean isBillTo_Default,	final boolean isShipTo_Default, final I_C_Country country)
	{
		final I_C_Location location = createLocation(bpartner, country);

		final I_C_BPartner_Location bpartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpartnerLocation.setC_BPartner(bpartner);
		bpartnerLocation.setC_Location(location);
		bpartnerLocation.setIsBillTo(isBillTo_Default);
		bpartnerLocation.setIsBillToDefault(isBillTo_Default);

		bpartnerLocation.setIsShipTo(isShipTo_Default);
		bpartnerLocation.setIsShipToDefault(isShipTo_Default);

		InterfaceWrapperHelper.save(bpartnerLocation);
		return bpartnerLocation;
	}

	private static I_C_Location createLocation(@NonNull final I_C_BPartner bpartner, @NonNull final I_C_Country country)
	{
		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class, bpartner);
		location.setCity(city);
		location.setC_Country(country);
		InterfaceWrapperHelper.save(location);
		return location;
	}
	

	@Builder(builderMethodName = "flatrateConditionsNew")
	public static  I_C_Flatrate_Conditions createFlatrateConditions(final String name, final String invoiceRule, final String typeConditions, final I_C_Calendar calendar)
	{
		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class);
		conditions.setM_PricingSystem(createPricingSystem());
		conditions.setInvoiceRule(invoiceRule);
		conditions.setType_Conditions(typeConditions);
		conditions.setName(name);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Transition transition = flatrateTransitionNew()
				.conditions(conditions)
				.calendar(calendar)
				.deliveryInterval(1)
				.deliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE)
				.termDuration(1)
				.termDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE)
				.isAutoCompleteNewTerm(true)
				.isAutoRenew(true)
				.build();

		conditions.setC_Flatrate_Transition(transition);
		conditions.setProcessed(true);
		conditions.setDocStatus(X_C_Flatrate_Conditions.DOCSTATUS_Completed);
		conditions.setDocAction(X_C_Flatrate_Conditions.DOCACTION_Re_Activate);
		InterfaceWrapperHelper.save(conditions);

		return conditions;
	}
	
	@Builder(builderMethodName = "flatrateTransitionNew")
	private static I_C_Flatrate_Transition createFlatrateTransition(@NonNull final I_C_Flatrate_Conditions conditions, @NonNull final I_C_Calendar calendar, final int termDuration, final String termDurationUnit,
			final int deliveryInterval, final String deliveryIntervalUnit, final boolean isAutoRenew, final boolean isAutoCompleteNewTerm)
	{
		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class);
		transition.setC_Calendar_Contract(calendar);
		transition.setTermDuration(termDuration);
		transition.setTermDurationUnit(termDurationUnit);
		transition.setDeliveryInterval(deliveryInterval);
		transition.setDeliveryIntervalUnit(deliveryIntervalUnit);
		transition.setIsAutoRenew(isAutoRenew);
		transition.setIsAutoCompleteNewTerm(isAutoCompleteNewTerm);
		transition.setC_Flatrate_Conditions_Next(conditions);
		transition.setTermOfNotice(0);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_TagE);
		transition.setProcessed(true);
		transition.setDocStatus(X_C_Flatrate_Transition.DOCSTATUS_Completed);
		transition.setDocAction(X_C_Flatrate_Transition.DOCACTION_Re_Activate);
		InterfaceWrapperHelper.save(transition);
		return transition;
	}
	
	@Builder(builderMethodName = "bpartnerNew")
	public static I_C_BPartner createBpartner(final String bpValue, final boolean isCustomer)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		bpartner.setValue(bpValue);
		bpartner.setIsCustomer(isCustomer);
		InterfaceWrapperHelper.save(bpartner);
		return bpartner;
	}

	@Builder(builderMethodName = "productNew")
	public static I_M_Product createProduct(final String value, final String name)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		product.setValue(value);
		product.setName(name);

		InterfaceWrapperHelper.save(product);
		return product;
	}


	private static I_M_PricingSystem createPricingSystem()
	{
		final I_M_PricingSystem pricingSytem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		pricingSytem.setValue(valuePricingSystem);
		pricingSytem.setName(valuePricingSystem);
		InterfaceWrapperHelper.save(pricingSytem);
		return pricingSytem;
	}
	
	@Builder(builderMethodName = "productAcctNew")
	public static I_M_Product_Acct createProductAcct(final I_M_Product product, final I_C_AcctSchema acctSchema)
	{
		final I_M_Product_Acct productAcct = InterfaceWrapperHelper.newInstance(I_M_Product_Acct.class);
		productAcct.setM_Product(product);
		productAcct.setC_AcctSchema(acctSchema);
		productAcct.setC_Activity(createActivity());
		InterfaceWrapperHelper.save(productAcct);
		return productAcct;
	}
	
	private static I_C_Activity createActivity()
	{
		final I_C_Activity activity = InterfaceWrapperHelper.newInstance(I_C_Activity.class);
		activity.setValue("Activity");
		activity.setName("Activity");
		InterfaceWrapperHelper.save(activity);
		return activity;
	}
}
