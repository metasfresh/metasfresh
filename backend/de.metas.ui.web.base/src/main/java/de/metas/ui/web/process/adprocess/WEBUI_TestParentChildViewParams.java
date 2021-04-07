package de.metas.ui.web.process.adprocess;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;

import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;

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

/**
 * Process used to test parent and child views informations provided by webui frontend.
 * 
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/755
 */
public class WEBUI_TestParentChildViewParams extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		return ProcessPreconditionsResolution.accept()
				.deriveWithCaptionOverride(buildProcessCaption());
	}

	private String buildProcessCaption()
	{
		final List<String> flags = new ArrayList<>();
		if (getParentViewRowIdsSelection() != null)
		{
			flags.add("parentView");
		}
		if (getChildViewRowIdsSelection() != null)
		{
			flags.add("childView");
		}

		final StringBuilder caption = new StringBuilder(getClass().getSimpleName());
		if (!flags.isEmpty())
		{
			caption.append(" (").append(Joiner.on(", ").join(flags)).append(")");
		}

		return caption.toString();
	}

	@Override
	protected String doIt() throws Exception
	{
		return "Parent: " + getParentViewRowIdsSelection()
				+ "\n Child: " + getChildViewRowIdsSelection();
	}
}
