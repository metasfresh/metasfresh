package de.metas.edi.esb.pojo.invoice.cctop;

/*
 * #%L
 * de.metas.edi.esb
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


import java.io.Serializable;
import java.util.Date;

public class Cctop111V implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6774551274028670314L;

	private String cOrderID;
	private String mInOutID;

	private Date dateOrdered;
	private Date movementDate;
	private String poReference;
	private String shipmentDocumentno;

	public final String getcOrderID()
	{
		return cOrderID;
	}

	public final void setcOrderID(final String cOrderID)
	{
		this.cOrderID = cOrderID;
	}

	public final String getmInOutID()
	{
		return mInOutID;
	}

	public final void setmInOutID(final String mInOutID)
	{
		this.mInOutID = mInOutID;
	}

	public Date getDateOrdered()
	{
		return dateOrdered;
	}

	public void setDateOrdered(final Date dateOrdered)
	{
		this.dateOrdered = dateOrdered;
	}

	public Date getMovementDate()
	{
		return movementDate;
	}

	public void setMovementDate(final Date movementDate)
	{
		this.movementDate = movementDate;
	}

	public String getPoReference()
	{
		return poReference;
	}

	public void setPoReference(final String poReference)
	{
		this.poReference = poReference;
	}

	public String getShipmentDocumentno()
	{
		return shipmentDocumentno;
	}

	public void setShipmentDocumentno(final String shipmentDocumentno)
	{
		this.shipmentDocumentno = shipmentDocumentno;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (cOrderID == null ? 0 : cOrderID.hashCode());
		result = prime * result + (dateOrdered == null ? 0 : dateOrdered.hashCode());
		result = prime * result + (mInOutID == null ? 0 : mInOutID.hashCode());
		result = prime * result + (movementDate == null ? 0 : movementDate.hashCode());
		result = prime * result + (poReference == null ? 0 : poReference.hashCode());
		result = prime * result + (shipmentDocumentno == null ? 0 : shipmentDocumentno.hashCode());
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
		final Cctop111V other = (Cctop111V)obj;
		if (cOrderID == null)
		{
			if (other.cOrderID != null)
			{
				return false;
			}
		}
		else if (!cOrderID.equals(other.cOrderID))
		{
			return false;
		}
		if (dateOrdered == null)
		{
			if (other.dateOrdered != null)
			{
				return false;
			}
		}
		else if (!dateOrdered.equals(other.dateOrdered))
		{
			return false;
		}
		if (mInOutID == null)
		{
			if (other.mInOutID != null)
			{
				return false;
			}
		}
		else if (!mInOutID.equals(other.mInOutID))
		{
			return false;
		}
		if (movementDate == null)
		{
			if (other.movementDate != null)
			{
				return false;
			}
		}
		else if (!movementDate.equals(other.movementDate))
		{
			return false;
		}
		if (poReference == null)
		{
			if (other.poReference != null)
			{
				return false;
			}
		}
		else if (!poReference.equals(other.poReference))
		{
			return false;
		}
		if (shipmentDocumentno == null)
		{
			if (other.shipmentDocumentno != null)
			{
				return false;
			}
		}
		else if (!shipmentDocumentno.equals(other.shipmentDocumentno))
		{
			return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return "Cctop111V [cOrderID=" + cOrderID + ", mInOutID=" + mInOutID + ", dateOrdered=" + dateOrdered + ", movementDate=" + movementDate + ", poReference="
				+ poReference
				+ ", shipmentDocumentno=" + shipmentDocumentno + "]";
	}
}
