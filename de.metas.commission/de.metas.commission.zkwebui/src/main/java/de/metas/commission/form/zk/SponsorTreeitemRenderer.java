/**
 * 
 */
package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import org.zkoss.lang.Objects;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.TreeitemRenderer;
import org.zkoss.zul.Treerow;

/**
 * @author tsa
 * 
 */
public class SponsorTreeitemRenderer implements TreeitemRenderer
{
	@Override
	public void render(Treeitem item, Object data) throws Exception
	{
		final String label = Objects.toString(data);
		
		Treecell tc = new Treecell(label);
		tc.setTooltiptext(label);
		tc.setStyle("white-space:nowrap; overflow:hidden;");

		Treerow tr = null;
		item.setValue(data);
		if (item.getTreerow() == null)
		{
			tr = new Treerow();
			tr.setParent(item);
		}
		else
		{
			tr = item.getTreerow();
			tr.getChildren().clear();
		}
		tc.setParent(tr);
	}

}
