package org.adempiere.ad.security.permissions;

/*
 * #%L
 * ADempiere ERP - Base
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


import org.adempiere.util.Check;

/**
 * Defines a window which shall be started after user logs in.
 * 
 * @author tsa
 *
 */
public class StartupWindowConstraint extends Constraint
{
	public static final StartupWindowConstraint ofAD_Form_ID(final int adFormId)
	{
		if (adFormId <= 0)
		{
			return NULL;
		}
		return new StartupWindowConstraint(adFormId);
	}

	public static final StartupWindowConstraint NULL = new StartupWindowConstraint();

	private final int adFormId;

	private StartupWindowConstraint(int adFormId)
	{
		super();
		Check.assume(adFormId > 0, "adFormId > 0");
		this.adFormId = adFormId;
	}

	/** Null constructor */
	private StartupWindowConstraint()
	{
		super();
		this.adFormId = -1;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen
		return "StartupWindow[@AD_Form_ID@:" + adFormId + "]";
	}

	/** @return false, i.e. never inherit this constraint because it shall be defined by current role itself */
	@Override
	public boolean isInheritable()
	{
		return false;
	}

	public int getAD_Form_ID()
	{
		return adFormId;
	}

	public boolean isNull()
	{
		return this == NULL;
	}
}
