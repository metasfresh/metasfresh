package de.metas.inoutcandidate.modelvalidator;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;

import de.metas.inoutcandidate.agg.key.impl.ReceiptScheduleKeyValueHandler;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleASIAwareFactory;
import de.metas.inoutcandidate.api.impl.ReceiptScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.impl.OrderLineReceiptScheduleListener;
import de.metas.util.Services;
import lombok.NonNull;

public class ReceiptScheduleValidator extends AbstractModuleInterceptor
{
	public static final ReceiptScheduleValidator instance = new ReceiptScheduleValidator();

	private ReceiptScheduleValidator()
	{
	}

	@Override
	protected void onBeforeInit()
	{
		//
		// 07344: Register RS AggregationKey Dependencies
		registerRSAggregationKeyDependencies();
	}

	@Override
	protected void registerInterceptors(@NonNull IModelValidationEngine engine)
	{
		engine.addModelValidator(new C_Order_ReceiptSchedule());
		engine.addModelValidator(new M_ReceiptSchedule());
		engine.addModelValidator(new M_ReceiptSchedule_Alloc());
		engine.addModelValidator(new C_OrderLine_ReceiptSchedule());
	}

	@Override
	protected void onAfterInit()
	{
		registerFactories();

		// task 08452
		Services.get(IReceiptScheduleBL.class).addReceiptScheduleListener(OrderLineReceiptScheduleListener.INSTANCE);
	}

	/**
	 * Public for testing purposes only!
	 */
	public static void registerRSAggregationKeyDependencies()
	{
		final IAggregationKeyRegistry keyRegistry = Services.get(IAggregationKeyRegistry.class);

		final String registrationKey = ReceiptScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY;

		//
		// Register Handlers
		keyRegistry.registerAggregationKeyValueHandler(registrationKey, new ReceiptScheduleKeyValueHandler());

		//
		// Register ReceiptScheduleHeaderAggregationKeyBuilder
		keyRegistry.registerDependsOnColumnnames(registrationKey,
				I_M_ReceiptSchedule.COLUMNNAME_C_DocType_ID,
				I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID,
				I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_Override_ID,
				I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_Location_ID,
				I_M_ReceiptSchedule.COLUMNNAME_C_BP_Location_Override_ID,
				I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_ID,
				I_M_ReceiptSchedule.COLUMNNAME_M_Warehouse_Override_ID,
				I_M_ReceiptSchedule.COLUMNNAME_AD_User_ID,
				I_M_ReceiptSchedule.COLUMNNAME_AD_User_Override_ID,
				I_M_ReceiptSchedule.COLUMNNAME_AD_Org_ID,
				I_M_ReceiptSchedule.COLUMNNAME_DateOrdered,
				I_M_ReceiptSchedule.COLUMNNAME_C_Order_ID,
				I_M_ReceiptSchedule.COLUMNNAME_POReference);
	}

	public void registerFactories()
	{
		Services.get(IAttributeSetInstanceAwareFactoryService.class)
				.registerFactoryForTableName(I_M_ReceiptSchedule.Table_Name, new ReceiptScheduleASIAwareFactory());
	}
}
