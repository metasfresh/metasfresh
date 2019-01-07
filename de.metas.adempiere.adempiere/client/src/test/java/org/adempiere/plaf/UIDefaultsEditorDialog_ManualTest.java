package org.adempiere.plaf;

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


import org.compiere.util.ValueNamePair;
import org.junit.Ignore;

/**
 * Run {@link UIDefaultsEditorDialog}, database decoupled.
 *
 * @author tsa
 *
 */
@Ignore
public class UIDefaultsEditorDialog_ManualTest
{

	public static void main(String[] args)
	{
		final ValueNamePair plaf = ValueNamePair.of(AdempiereLookAndFeel.class.getName(), AdempiereLookAndFeel.NAME);
		final ValueNamePair theme = ValueNamePair.of(MetasFreshTheme.class.getName(), MetasFreshTheme.NAME);
		AdempierePLAF.setPLAF(plaf, theme, false);

		UIDefaultsEditorDialog.createAndShow(null);
	}

}
