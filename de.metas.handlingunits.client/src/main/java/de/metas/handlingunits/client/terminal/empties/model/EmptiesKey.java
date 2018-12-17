package de.metas.handlingunits.client.terminal.empties.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_C_BPartner;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.TerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;

/**
 * Empties Key
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/07193_R%C3%BCcknahme_Gebinde_%28104585385527%29
 */
public class EmptiesKey extends TerminalKey
{
	//
	// Services
	private final transient IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final I_M_HU_PI huPI;
	private final String id;
	private final KeyNamePair value;
	private int qty = 0;

	private final String piName;
	private String name = null;
	private String nameOldFired = null;

	public EmptiesKey(final ITerminalContext terminalContext, final I_M_HU_PI huPI, final String huUnitType)
	{
		super(terminalContext);

		Check.assumeNotNull(huPI, "huPI not null");
		this.huPI = huPI;
		piName = this.huPI.getName();

		id = getClass().getSimpleName() + "#M_HU_PI_ID=" + huPI.getM_HU_PI_ID();
		value = new KeyNamePair(huPI.getM_HU_PI_ID(), piName);

		// handlingUnitsDAO.retrievePackingMaterial(huPI, bpartner);

		updateName();
	}

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public Object getName()
	{
		if (name == null)
		{
			updateName();
		}
		return name;
	}

	public String getPIName()
	{
		return piName;
	}

	@Override
	public String getTableName()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public KeyNamePair getValue()
	{
		return value;
	}

	public int getQty()
	{
		return qty;
	}

	public void setQty(final int qty)
	{
		if (this.qty == qty)
		{
			return;
		}

		Check.assume(qty >= 0, "qty >= 0");
		this.qty = qty;

		updateName();
	}

	public void setQty(final BigDecimal qty)
	{
		final int qtyInt = qty == null ? 0 : qty.intValueExact();
		setQty(qtyInt);
	}

	public final I_M_HU_PI getM_HU_PI()
	{
		return huPI;
	}

	private void updateName()
	{
		name = buildName();

		if (listeners.hasListeners(ITerminalKey.PROPERTY_Name))
		{
			final String nameOld = nameOldFired;
			final String nameNew = name;
			listeners.firePropertyChange(ITerminalKey.PROPERTY_Name, nameOld, nameNew);
			nameOldFired = nameNew;
		}

	}

	private String buildName()
	{
		final StringBuilder name = new StringBuilder();

		name.append(StringUtils.maskHTML(piName));

		final int qty = getQty();
		final String qtyStr = qty > 0 ? String.valueOf(qty) : "0";
		name.append("<br>");
		name.append("(").append(qtyStr).append(")");

		name.insert(0, "<center>").append("</center>");

		return name.toString();
	}

	public List<I_M_HU_PackingMaterial> getM_HU_PackingMaterials(final I_C_BPartner bpartner)
	{
		final I_M_HU_PI huPI = getM_HU_PI();
		final I_M_HU_PackingMaterial packingMaterial = handlingUnitsDAO.retrievePackingMaterial(huPI, bpartner);
		if (packingMaterial == null)
		{
			return Collections.emptyList();
		}

		return Collections.singletonList(packingMaterial);
	}
}
