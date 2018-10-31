package org.adempiere.ui;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Frame;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.apps.AEnv;
import org.compiere.apps.form.FormFrame;
import org.compiere.model.GridField;
import org.compiere.model.I_AD_Form;
import org.compiere.util.Env;

import de.metas.util.Check;

/**
 * {@link IContextMenuAction} abstract implementation to be used when you want to easily open a custom form ( {@link I_AD_Form} ).
 * 
 * @author tsa
 *
 */
public abstract class OpenFormContextMenuAction extends AbstractContextMenuAction
{
	private final int _adFormId;
	private I_AD_Form _adForm;
	private boolean _adFormLoaded = false;

	public OpenFormContextMenuAction(final int adFormId)
	{
		super();
		this._adFormId = adFormId;
	}

	@Override
	public final String getName()
	{
		final I_AD_Form form = getAD_Form();
		if (form == null)
		{
			return "-";
		}
		return form.getName();
	}
	
	@Override
	public final String getTitle()
	{
		return getName();
	}

	@Override
	public final String getIcon()
	{
		return null;
	}

	@Override
	public final boolean isAvailable()
	{
		final I_AD_Form form = getAD_Form();
		return form != null;
	}

	@Override
	public final boolean isRunnable()
	{
		final I_AD_Form form = getAD_Form();
		if (form == null)
		{
			return false;
		}

		if (!isFormRunnable())
		{
			return false;
		}

		return true;
	}

	/**
	 * Check if the form is available and shall be opened in current context.
	 * 
	 * To be implemented in extending class.
	 * 
	 * @return true if form is available and it shall be opened.
	 */
	protected boolean isFormRunnable()
	{
		// nothing at this level
		return true;
	}

	@Override
	public final void run()
	{
		final FormFrame formFrame = AEnv.createForm(getAD_Form());
		if (formFrame == null)
		{
			return;
		}

		customizeBeforeOpen(formFrame);

		AEnv.showCenterWindow(getCallingFrame(), formFrame);
	}

	protected void customizeBeforeOpen(final FormFrame formFrame)
	{
		// nothing on this level
	}

	/** @return the {@link Frame} in which this action was executed or <code>null</code> if not available. */
	protected final Frame getCallingFrame()
	{
		final GridField gridField = getContext().getEditor().getField();
		if (gridField == null)
		{
			return null;
		}
		final int windowNo = gridField.getWindowNo();
		final Frame frame = Env.getWindow(windowNo);
		return frame;
	}

	protected final int getAD_Form_ID()
	{
		return _adFormId;
	}

	private final synchronized I_AD_Form getAD_Form()
	{
		if (!_adFormLoaded)
		{
			_adForm = retrieveAD_Form();
			_adFormLoaded = true;
		}
		return _adForm;
	}

	private final I_AD_Form retrieveAD_Form()
	{
		final IContextMenuActionContext context = getContext();
		Check.assumeNotNull(context, "context not null");
		final Properties ctx = context.getCtx();

		// Check if user has access to Payment Allocation form
		final int adFormId = getAD_Form_ID();
		final Boolean formAccess = Env.getUserRolePermissions().checkFormAccess(adFormId);
		if (formAccess == null || !formAccess)
		{
			return null;
		}

		// Retrieve the form
		final I_AD_Form form = InterfaceWrapperHelper.create(ctx, adFormId, I_AD_Form.class, ITrx.TRXNAME_None);

		// Translate it to user's language
		final I_AD_Form formTrl = InterfaceWrapperHelper.translate(form, I_AD_Form.class);

		return formTrl;
	}
}
