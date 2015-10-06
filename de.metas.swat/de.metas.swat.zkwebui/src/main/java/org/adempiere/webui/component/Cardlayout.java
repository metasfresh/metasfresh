/**
 * 
 */
package org.adempiere.webui.component;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;

/**
 * @author tsa
 * 
 */
public class Cardlayout extends HtmlBasedComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5296379015370117643L;

	public Cardlayout()
	{
		super();
	}

	public void show(final String id)
	{
		for (final Object c : getChildren())
		{
			final Component component = (Component)c;
			if (id.equals(component.getId()))
			{
				component.setVisible(true);
			}
			else
			{
				component.setVisible(false);
			}
		}
	}

	/**
	 * Re-size this layout component.
	 */
	public void resize()
	{
		smartUpdate("z.resize", "");
	}

	@Override
	public void beforeChildAdded(final Component child, final Component refChild)
	{
		super.beforeChildAdded(child, refChild);
	}

	@Override
	public boolean insertBefore(final Component child, final Component refChild)
	{
		if (!super.insertBefore(child, refChild))
		{
			return false;
		}
		smartUpdate("z.chchg", true);
		return true;
	}

	@Override
	public void onChildRemoved(final Component child)
	{
		super.onChildRemoved(child);
	}

	// public String getZclass() {
	// return _zclass == null ? "z-border-layout" : _zclass;
	// }

	// Cloneable//
	@Override
	public Object clone()
	{
		final Cardlayout clone = (Cardlayout)super.clone();
		clone.afterUnmarshal();
		return clone;
	}

	private void afterUnmarshal()
	{
		// for (Iterator it = getChildren().iterator(); it.hasNext();) {
		// }
	}

	// -- Serializable --//
	private synchronized void readObject(final java.io.ObjectInputStream s)
			throws java.io.IOException, ClassNotFoundException
	{
		s.defaultReadObject();
		afterUnmarshal();
	}

}
