package de.metas.ui.web.pporder.process;

import de.metas.ui.web.pporder.PPOrderLineRow;
import de.metas.ui.web.pporder.PPOrderLinesView;
import de.metas.ui.web.process.ViewBasedProcessTemplate;

/*
 * #%L
 * metasfresh-webui-api
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

public abstract class WEBUI_PP_Order_Template
		extends ViewBasedProcessTemplate
// implements IProcessPrecondition
{
	@Override
	protected final PPOrderLinesView getView()
	{
		return super.getView(PPOrderLinesView.class);
	}

	@Override
	protected final PPOrderLineRow getSingleSelectedRow()
	{
		return PPOrderLineRow.cast(super.getSingleSelectedRow());
	}

	@Override
	protected String doIt() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}
