package de.metas.handlingunits.materialtracking.spi.impl;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.Adempiere;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.util.Check;

/*package */class HUHandlingUnitsInfo implements IHandlingUnitsInfo
{
	public static HUHandlingUnitsInfo cast(final IHandlingUnitsInfo handlingUnitsInfo)
	{
		return (HUHandlingUnitsInfo)handlingUnitsInfo;
	}

	private final I_M_HU_PI _tuPI;
	private int _qtyTU;
	private final String _tuPIName;

	private final boolean _isQtyWritable;

	/* package */HUHandlingUnitsInfo(final I_M_HU_PI tuPI, final int qtyTU)
	{
		this(tuPI, qtyTU, false);
	}

	/* package */HUHandlingUnitsInfo(final I_M_HU_PI tuPI, final int qtyTU, final boolean isReadWrite)
	{
		// task 09688: currently, when in unit testing mode, we allow tuPI to be null
		Check.assume(tuPI != null || Adempiere.isUnitTestMode(), "tuPI not null");

		_tuPI = tuPI;
		_tuPIName = tuPI != null ? tuPI.getName() : null;

		_qtyTU = qtyTU;

		_isQtyWritable = isReadWrite;
	}

	@Override
	public int getQtyTU()
	{
		return _qtyTU;
	}

	@Override
	public String getTUName()
	{
		return _tuPIName;
	}

	public final I_M_HU_PI getTU_HU_PI()
	{
		return _tuPI;
	}

	@Override
	public IHandlingUnitsInfo add(final IHandlingUnitsInfo infoToAdd)
	{
		if (infoToAdd == null)
		{
			return this;
		}

		//
		// TU PI
		final I_M_HU_PI tuPI = getTU_HU_PI();
		// TODO make sure tuPIs are compatible

		//
		// Qty TU
		final int qtyTU = getQtyTU();
		final int qtyTU_ToAdd = infoToAdd.getQtyTU();
		final int qtyTU_New = qtyTU + qtyTU_ToAdd;

		final boolean isReadWrite = false;
		final HUHandlingUnitsInfo infoNew = new HUHandlingUnitsInfo(tuPI, qtyTU_New, isReadWrite);
		return infoNew;
	}

	protected void setQtyTUInner(final int qtyTU)
	{
		Check.errorIf(!_isQtyWritable, "This instance {} is read-only", this);
		_qtyTU = qtyTU;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

}
