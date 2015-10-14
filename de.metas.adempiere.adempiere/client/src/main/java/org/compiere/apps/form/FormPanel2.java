package org.compiere.apps.form;

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


/**
 * A new Form Panel introduced to deal with forms that require a special behavior.
 * 
 * For example, they have to be opened maximized by default ( see org.eevolution.form.VMRPDetailed.showFormWindow(int, FormFrame)).
 * 
 *  If a special behavior is required in a class, make the specific forms implement this interface and write the specific implementation there.
 */
public interface FormPanel2 extends FormPanel
{
	/**
	 * Method to be implemented in classes that implement this interface, if a special window visualization is required
	 * 
	 * @param WindowNo
	 * @param frame
	 */
	void showFormWindow(int WindowNo, FormFrame frame);
}
