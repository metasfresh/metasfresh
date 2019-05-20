package de.metas.security.permissions;

import de.metas.util.Check;

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
