package de.metas.async.spi.impl;

/*
 * #%L
 * de.metas.async
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.X_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackagePrioStrategy;
import de.metas.util.Services;
import lombok.EqualsAndHashCode;
import org.adempiere.ad.service.IADReferenceDAO;
import org.compiere.util.Env;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: check if we can do this better (using enum??)
 *
 */
@EqualsAndHashCode
public class ConstantWorkpackagePrio implements IWorkpackagePrioStrategy
{
	private final String prio;

	private static final Map<String, ConstantWorkpackagePrio> prio2strategy = new HashMap<String, ConstantWorkpackagePrio>();

	static
	{
		prio2strategy.put(X_C_Queue_WorkPackage.PRIORITY_Urgent, new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Urgent));
		prio2strategy.put(X_C_Queue_WorkPackage.PRIORITY_High, new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_High));
		prio2strategy.put(X_C_Queue_WorkPackage.PRIORITY_Medium, new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Medium));
		prio2strategy.put(X_C_Queue_WorkPackage.PRIORITY_Low, new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Low));
		prio2strategy.put(X_C_Queue_WorkPackage.PRIORITY_Minor, new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Minor));

		prio2strategy.put("urgent", new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Urgent));
		prio2strategy.put("high", new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_High));
		prio2strategy.put("medium", new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Medium));
		prio2strategy.put("low", new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Low));
		prio2strategy.put("minor", new ConstantWorkpackagePrio(X_C_Queue_WorkPackage.PRIORITY_Minor));
	}

	private ConstantWorkpackagePrio(final String prio)
	{
		this.prio = prio;
	}

	public static ConstantWorkpackagePrio urgent()
	{
		return prio2strategy.get(X_C_Queue_WorkPackage.PRIORITY_Urgent);
	}

	public static ConstantWorkpackagePrio high()
	{
		return prio2strategy.get(X_C_Queue_WorkPackage.PRIORITY_High);
	}

	public static ConstantWorkpackagePrio medium()
	{
		return prio2strategy.get(X_C_Queue_WorkPackage.PRIORITY_Medium);
	}

	public static ConstantWorkpackagePrio low()
	{
		return prio2strategy.get(X_C_Queue_WorkPackage.PRIORITY_Low);
	}

	public static ConstantWorkpackagePrio minor()
	{
		return prio2strategy.get(X_C_Queue_WorkPackage.PRIORITY_Minor);
	}

	public static ConstantWorkpackagePrio fromString(final String string)
	{
		return prio2strategy.get(string.toLowerCase());
	}

	@Override
	public String getPrioriy(IWorkPackageQueue IGNORED)
	{
		return prio;
	}

	public String retrievePrioName()
	{
		return Services.get(IADReferenceDAO.class)
				.retrieveListNameTrl(Env.getCtx(), X_C_Queue_WorkPackage.PRIORITY_AD_Reference_ID, prio);
	}

	@Override
	public String toString()
	{
		return "ConstantWorkpackagePrio[" + prio + "]";
	}

}
