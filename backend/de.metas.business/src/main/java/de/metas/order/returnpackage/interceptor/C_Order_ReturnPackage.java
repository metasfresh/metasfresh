/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.order.returnpackage.interceptor;

import de.metas.order.OrderId;
import de.metas.order.returnpackage.core.service.OrderReturnPackageService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * On creation of a sales order, auto-creates the two return-package (Rücknahme Gebinde) rows (EUR + H1).
 * <p>
 * Gated by SysConfig {@code C_Order.ReturnPackage.AutoCreate} (default {@code false}): the auto-creation is
 * off for vanilla installs and only switched on for the deployment that exposes the feature's UI.
 */
@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order_ReturnPackage
{
	private static final String SYSCONFIG_AutoCreate = "C_Order.ReturnPackage.AutoCreate";

	@NonNull private final OrderReturnPackageService orderReturnPackageService;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createReturnPackages(@NonNull final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return;
		}

		final boolean autoCreate = sysConfigBL.getBooleanValue(SYSCONFIG_AutoCreate, false, order.getAD_Client_ID(), order.getAD_Org_ID());
		if (!autoCreate)
		{
			return;
		}

		orderReturnPackageService.createDefaultsForOrder(OrderId.ofRepoId(order.getC_Order_ID()));
	}
}
