/*
 * #%L
 * de-metas-edi-esb-camel
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.esb.desadvexport.compudata.join;

import java.io.Serializable;

import de.metas.edi.esb.desadvexport.compudata.P060;
import de.metas.edi.esb.desadvexport.compudata.P100;

public class JP060P100 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1954996245483559377L;

	private P060 p060;
	private P100 p100;

	public P060 getP060()
	{
		return p060;
	}

	public void setP060(final P060 p060)
	{
		this.p060 = p060;
	}

	public P100 getP100()
	{
		return p100;
	}

	public void setP100(final P100 p100)
	{
		this.p100 = p100;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (p060 == null ? 0 : p060.hashCode());
		result = prime * result + (p100 == null ? 0 : p100.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final JP060P100 other = (JP060P100)obj;
		if (p060 == null)
		{
			if (other.p060 != null)
			{
				return false;
			}
		}
		else if (!p060.equals(other.p060))
		{
			return false;
		}
		if (p100 == null)
		{
			if (other.p100 != null)
			{
				return false;
			}
		}
		else if (!p100.equals(other.p100))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "JP060100 [p060=" + p060 + ", p100=" + p100 + "]";
	}
}
