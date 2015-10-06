package org.adempiere.webui.component;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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
import org.zkoss.xml.HTMLs;

/**
 * Extension of {@link org.zkoss.zul.Applet} which supports setter/getter for Archive.
 * 
 * NOTE: this will be deprecated when we move to ZK >= 5
 * 
 * @author tsa
 * 
 */
public class Applet extends org.zkoss.zul.Applet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5381400875832854352L;

	private String archive = null;

	public Applet()
	{
		super();
	}

	public String getArchive()
	{
		return archive;
	}

	public void setArchive(String archive)
	{
		if (Objects.equals(this.archive, archive))
		{
			return;
		}

		this.archive = archive;
		invalidate();
	}

	@Override
	public String getOuterAttrs()
	{
		final StringBuffer sb = new StringBuffer(80).append(super.getOuterAttrs());
		HTMLs.appendAttribute(sb, "archive", archive);
		return sb.toString();
	}

}
