package de.metas.ui.web.view;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * @implNote If you want to add a new action, e.g. MY_CLOSE, then you shall
 * <ul>
 * <li>add the MY_CLOSE enum member here</li>
 * <li>add the `webui.modal.actions.my_close` to AD_Message (remark: `webui.modal.actions.` prefix plus enum name lowe-case) </li>
 * </ul>
 */
public enum ViewCloseAction
{
	BACK,
	CANCEL,
	DONE,
	CLOSE,
	;

	public static ViewCloseAction fromJsonOr(final String json, final ViewCloseAction defaultValue)
	{
		if (json == null || json.isEmpty())
		{
			return defaultValue;
		}

		return valueOf(json);
	}

	public boolean isDone()
	{
		return this.equals(DONE);
	}
}
