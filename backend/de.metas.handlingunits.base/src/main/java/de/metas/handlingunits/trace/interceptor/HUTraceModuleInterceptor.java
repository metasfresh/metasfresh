package de.metas.handlingunits.trace.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;

import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.trace.HUTraceEventsService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class HUTraceModuleInterceptor extends AbstractModuleInterceptor
{
	public static final String SYSCONFIG_ENABLED = "de.metas.handlingunits.trace.interceptor.enabled";

	public static final HUTraceModuleInterceptor INSTANCE = new HUTraceModuleInterceptor();

	/**
	 * Only set for testing
	 */
	private HUTraceEventsService huTraceEventsService;

	private HUTraceModuleInterceptor()
	{
	}

	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		if (!isEnabled())
		{
			return;
		}

		engine.addModelValidator(new PP_Cost_Collector(), client);
		engine.addModelValidator(new M_InOut(), client);
		engine.addModelValidator(new M_Movement(), client);
		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked(), client);
	}

	/**
	 * Registers a new {@link TraceHUTrxListener}.
	 */
	protected void onAfterInit()
	{
		if (!isEnabled())
		{
			return;
		}

		final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
		huTrxBL.addListener(TraceHUTrxListener.INSTANCE);

	}

	/**
	 * Allow the {@link HUTraceEventsService} to be set from outside. Goal: allow testing without the need to fire up the spring-boot test runner.
	 * 
	 * @param huTraceEventsService
	 */
	@VisibleForTesting
	public void setHUTraceEventsService(@NonNull final HUTraceEventsService huTraceEventsService)
	{
		this.huTraceEventsService = huTraceEventsService;
	}

	public HUTraceEventsService getHUTraceEventsService()
	{
		if (huTraceEventsService != null)
		{
			return huTraceEventsService;
		}
		return Adempiere.getBean(HUTraceEventsService.class);
	}

	/**
	 * Uses {@link ISysConfigBL} to check if tracing shall be enabled. Note that the default value is false,
	 * because we don't want this to fire in <i>"unit"</i> tests by default.
	 * If you want to test against this feature, you can explicitly enable it for your test.
	 * 
	 * @return
	 */
	public boolean isEnabled()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final boolean enabled = sysConfigBL.getBooleanValue(SYSCONFIG_ENABLED, false, Env.getAD_Client_ID(Env.getCtx()), Env.getAD_Org_ID(Env.getCtx()));
		return enabled;
	}
}
