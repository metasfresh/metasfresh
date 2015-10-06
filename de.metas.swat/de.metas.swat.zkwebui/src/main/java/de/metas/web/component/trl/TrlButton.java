package de.metas.web.component.trl;

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


import org.zkoss.zul.Button;

public class TrlButton extends Button implements ITranslatableLabel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6467035627399575923L;

	private final TranslatableLabelSupport trl;

	public TrlButton()
	{
		super();
		trl = new TranslatableLabelSupport(this, false);
	}

	@Override
	public void setLabelTrl(final String labelTrl)
	{
		super.setLabel(labelTrl);
	}

	@Override
	public void setLabel(final String label)
	{
		trl.setLabelAndTranslate(label);
	}

	@Override
	public String getLabelOrig()
	{
		return trl.getLabelOrig();
	}

	//
	// Translatable tooltiptext support
	// TODO: this is more a workaround and it should be implemented directly in TranslatableLabelSupport
	private String tooltiptextOrig;

	@Override
	public void setTooltiptext(final String tooltiptext)
	{
		final String tooltiptextTrl;
		if (trl.isTranslatable())
		{
			tooltiptextTrl = TranslatableLabelSupport.translate(tooltiptext);
		}
		else
		{
			tooltiptextTrl = tooltiptext;
		}
		setTooltiptextTrl(tooltiptextTrl);
		tooltiptextOrig = tooltiptext;
	}

	public void setTooltiptextTrl(final String tooltiptext)
	{
		super.setTooltiptext(tooltiptext);
	}

	public String getTooltiptextOrig()
	{
		return tooltiptextOrig;
	}
	// end tooltiptext trl support
}
