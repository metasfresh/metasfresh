/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.order;

import de.metas.order.OrderId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelInterceptor2ModelValidatorWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;

import javax.annotation.Nullable;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Makes the next completion of an armed order fail once with a DB deadlock (SQLSTATE {@code 40P01}) in {@code AFTER_COMPLETE},
 * so that the document engine rolls the completion back and retries it, as it does when PostgreSQL picks the completing
 * transaction as a deadlock victim. Registered while at least one order is armed, see {@link #disarmAll()}.
 */
final class C_Order_SimulatedDeadlockOnCompletion implements IModelInterceptor
{
	private static final C_Order_SimulatedDeadlockOnCompletion INSTANCE = new C_Order_SimulatedDeadlockOnCompletion();

	private final Set<OrderId> armedOrderIds = new HashSet<>();
	private final Set<OrderId> hitOrderIds = new HashSet<>();
	/** the same wrapper for every registration, so that an engine never accumulates several of them */
	private final ModelValidator validator = ModelInterceptor2ModelValidatorWrapper.wrapIfNeeded(this);
	@Nullable private ModelValidationEngine registeredWithEngine = null;

	private C_Order_SimulatedDeadlockOnCompletion() {}

	static synchronized void arm(@NonNull final OrderId orderId)
	{
		INSTANCE.registerIfNeeded();
		INSTANCE.armedOrderIds.add(orderId);
		INSTANCE.hitOrderIds.remove(orderId);
	}

	/**
	 * @return whether a completion of the given order ran into the deadlock since it was armed
	 */
	static synchronized boolean isHit(@NonNull final OrderId orderId)
	{
		return INSTANCE.hitOrderIds.contains(orderId);
	}

	/**
	 * Unregisters the interceptor and forgets all armed orders; to be called after each scenario.
	 */
	static synchronized void disarmAll()
	{
		INSTANCE.armedOrderIds.clear();
		INSTANCE.hitOrderIds.clear();
		INSTANCE.unregister();
	}

	private void registerIfNeeded()
	{
		final ModelValidationEngine engine = ModelValidationEngine.get();
		if (registeredWithEngine == engine)
		{
			return;
		}

		unregister(); // the engine was re-created; don't leave the interceptor in the old one
		engine.addDocValidate(I_C_Order.Table_Name, validator);
		registeredWithEngine = engine;
	}

	private void unregister()
	{
		if (registeredWithEngine != null)
		{
			registeredWithEngine.removeDocValidate(I_C_Order.Table_Name, validator);
		}
		registeredWithEngine = null;
	}

	@Override
	public void initialize(final IModelValidationEngine engine, final I_AD_Client client) {}

	@Override
	public int getAD_Client_ID() {return -1;}

	@Override
	public void onDocValidate(final Object model, final DocTimingType timing)
	{
		if (timing != DocTimingType.AFTER_COMPLETE)
		{
			return;
		}

		final OrderId orderId = OrderId.ofRepoId(InterfaceWrapperHelper.getId(model));
		synchronized (C_Order_SimulatedDeadlockOnCompletion.class)
		{
			if (!armedOrderIds.remove(orderId))
			{
				return;
			}
			hitOrderIds.add(orderId);
		}

		throw new AdempiereException(
				"Simulated DB deadlock while completing " + orderId,
				new SQLException("ERROR: deadlock detected (simulated)", "40P01"));
	}
}
