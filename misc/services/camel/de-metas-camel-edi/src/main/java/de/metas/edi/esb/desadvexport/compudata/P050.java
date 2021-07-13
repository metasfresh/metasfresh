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

package de.metas.edi.esb.desadvexport.compudata;

import java.io.Serializable;
import java.util.List;

import de.metas.edi.esb.desadvexport.compudata.join.JP060P100;

public class P050 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5693280204180840672L;

	private String record;
	private String partner;
	private String messageNo;
	private String levelNo;
	private String packageQty;
	private String packageType;

	private List<P102> p102Lines;
	private List<JP060P100> joinP060P100Lines;

	public String getRecord()
	{
		return record;
	}

	public void setRecord(final String record)
	{
		this.record = record;
	}

	public String getPartner()
	{
		return partner;
	}

	public void setPartner(final String partner)
	{
		this.partner = partner;
	}

	public String getMessageNo()
	{
		return messageNo;
	}

	public void setMessageNo(final String messageNo)
	{
		this.messageNo = messageNo;
	}

	public String getLevelNo()
	{
		return levelNo;
	}

	public void setLevelNo(final String levelNo)
	{
		this.levelNo = levelNo;
	}

	public String getPackageQty()
	{
		return packageQty;
	}

	public void setPackageQty(final String packageQty)
	{
		this.packageQty = packageQty;
	}

	public String getPackageType()
	{
		return packageType;
	}

	public void setPackageType(final String packageType)
	{
		this.packageType = packageType;
	}

	public List<JP060P100> getJoinP060P100Lines()
	{
		return joinP060P100Lines;
	}

	public void setJoinP060P100Lines(final List<JP060P100> joinP060P100Lines)
	{
		this.joinP060P100Lines = joinP060P100Lines;
	}

	public List<P102> getP102Lines()
	{
		return p102Lines;
	}

	public void setP102Lines(final List<P102> p102Lines)
	{
		this.p102Lines = p102Lines;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (joinP060P100Lines == null ? 0 : joinP060P100Lines.hashCode());
		result = prime * result + (levelNo == null ? 0 : levelNo.hashCode());
		result = prime * result + (messageNo == null ? 0 : messageNo.hashCode());
		result = prime * result + (p102Lines == null ? 0 : p102Lines.hashCode());
		result = prime * result + (packageQty == null ? 0 : packageQty.hashCode());
		result = prime * result + (packageType == null ? 0 : packageType.hashCode());
		result = prime * result + (partner == null ? 0 : partner.hashCode());
		result = prime * result + (record == null ? 0 : record.hashCode());
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
		final P050 other = (P050)obj;
		if (joinP060P100Lines == null)
		{
			if (other.joinP060P100Lines != null)
			{
				return false;
			}
		}
		else if (!joinP060P100Lines.equals(other.joinP060P100Lines))
		{
			return false;
		}
		if (levelNo == null)
		{
			if (other.levelNo != null)
			{
				return false;
			}
		}
		else if (!levelNo.equals(other.levelNo))
		{
			return false;
		}
		if (messageNo == null)
		{
			if (other.messageNo != null)
			{
				return false;
			}
		}
		else if (!messageNo.equals(other.messageNo))
		{
			return false;
		}
		if (p102Lines == null)
		{
			if (other.p102Lines != null)
			{
				return false;
			}
		}
		else if (!p102Lines.equals(other.p102Lines))
		{
			return false;
		}
		if (packageQty == null)
		{
			if (other.packageQty != null)
			{
				return false;
			}
		}
		else if (!packageQty.equals(other.packageQty))
		{
			return false;
		}
		if (packageType == null)
		{
			if (other.packageType != null)
			{
				return false;
			}
		}
		else if (!packageType.equals(other.packageType))
		{
			return false;
		}
		if (partner == null)
		{
			if (other.partner != null)
			{
				return false;
			}
		}
		else if (!partner.equals(other.partner))
		{
			return false;
		}
		if (record == null)
		{
			if (other.record != null)
			{
				return false;
			}
		}
		else if (!record.equals(other.record))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "P050 [record=" + record + ", partner=" + partner + ", messageNo=" + messageNo + ", levelNo=" + levelNo + ", packageQty=" + packageQty + ", packageType=" + packageType + ", p102Lines="
				+ p102Lines + ", joinP060P100Lines=" + joinP060P100Lines + "]";
	}
}
