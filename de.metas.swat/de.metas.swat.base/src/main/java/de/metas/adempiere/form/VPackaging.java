package de.metas.adempiere.form;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.logging.Level;

import org.adempiere.util.Check;
import org.compiere.apps.AEnv;
import org.compiere.apps.form.FormFrame;
import org.compiere.apps.form.FormPanel;
import org.compiere.minigrid.MiniTable;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

/**
 * 
 * Swing-Related part of the PAckaging form.
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Transportverpackung_%282009_0022_G61%29'>(2009_0022_G61)</a>"
 */
public class VPackaging extends Packing implements FormPanel
{

	private static final CLogger logger = CLogger.getCLogger(VPackaging.class);

	private FormFrame frame;

	@Override
	public void init(int WindowNo, FormFrame frame)
	{
		logger.info("");
		this.frame = frame;
		Env.setContext(Env.getCtx(), WindowNo, "IsSOTrx", "Y");

		final int packingUserId = Env.getAD_User_ID(Env.getCtx());
		final PackingMd model = new PackingMd(WindowNo, packingUserId);
		model.setMiniTable(new MiniTable());
		setModel(model);

		final PackingV view = new PackingV(model, frame);
		setView(Env.getCtx(),view);

		new VGenericHandler(view, model, this);

		try
		{
			dynInit();
		}
		catch (Exception ex)
		{
			logger.log(Level.SEVERE, "init", ex);
		}
	}

	@Override
	public void dispose()
	{
		if (frame != null)
		{
			frame.dispose();
			frame = null;
		}

	}

	@Override
	public void validate()
	{

		saveSelection();
		createPackingDetails(Env.getCtx(), new int[]{getModel().getMiniTable().getSelectedRow()});
	}

	/**
	 * Show a GUI to allow fine tuning of the precomputed configuration
	 */
	@Override
	protected void executePacking(final IPackingDetailsModel detailsModel)
	{
		Check.assumeInstanceOf(detailsModel, PackingDetailsMd.class, "detailsModel");
		final PackingDetailsMd packingDetailsModel = (PackingDetailsMd)detailsModel;
		

		final PackingDetailsV view = new PackingDetailsV(frame, packingDetailsModel);

		final PackingDetailsCtrl ctrl = new PackingDetailsCtrl(packingDetailsModel);
		ctrl.setView(Env.getCtx(), view);
		ctrl.validateModel();
		ctrl.recompute(Env.getCtx());

		AEnv.positionCenterWindow(frame, view);
		view.setVisible(true);
	}

	@Override
	public void initModel(final MvcMdGenForm model)
	{
		// nothing to do
	}
}
