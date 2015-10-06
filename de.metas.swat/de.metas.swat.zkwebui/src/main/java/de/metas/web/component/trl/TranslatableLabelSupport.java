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


import java.util.List;

import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Message;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;

public class TranslatableLabelSupport
{
	public static final String ATTR_TranslatableLabelSupport = TranslatableLabelSupport.class.getName();

	public static final String SYSCONFIG_Enabled = "de.metas.web.component.trl.TranslatableLabelSupport.Enabled";

	public static void updateTrl(final Object o)
	{
		if (o instanceof ITranslatableLabel)
		{
			final ITranslatableLabel t = (ITranslatableLabel)o;
			t.setLabel(t.getLabelOrig());
		}
	}

	public static String encode(final String text)
	{
		if (text == null || text.isEmpty())
		{
			return text;
		}

		return text.replace("@", "@@");
	}

	public static boolean isEnabled()
	{
		return Services.get(ISysConfigBL.class).getBooleanValue(TranslatableLabelSupport.SYSCONFIG_Enabled, false);
	}

	public static TranslatableLabelSupport forComponent(final Object field)
	{
		if (field instanceof Component)
		{
			final Component comp = (Component)field;
			final TranslatableLabelSupport tls = (TranslatableLabelSupport)comp.getAttribute(TranslatableLabelSupport.ATTR_TranslatableLabelSupport);
			return tls;
		}

		return null;
	}

	/**
	 * 
	 * @return list of {@link I_AD_Message}s which have Value=MsgText and Created=Updated
	 */
	public static List<I_AD_Message> fetchNewMessages()
	{
		final String wc = I_AD_Message.COLUMNNAME_Value + "=" + I_AD_Message.COLUMNNAME_MsgText
				+ " AND " + I_AD_Message.COLUMNNAME_Created + "=" + I_AD_Message.COLUMNNAME_Updated;
		return new Query(Env.getCtx(), I_AD_Message.Table_Name, wc, null)
				.setOnlyActiveRecords(true)
				.setOrderBy(I_AD_Message.COLUMNNAME_AD_Message_ID)
				.list(I_AD_Message.class);
	}

	private final ITranslatableLabel field;
	private boolean isTranslatable = true;
	private String labelOrig;

	public TranslatableLabelSupport(final ITranslatableLabel field)
	{
		this(field, true);
	}

	public TranslatableLabelSupport(final ITranslatableLabel field, final boolean bindEditor)
	{
		this.field = field;
		labelOrig = field.getLabel();

		if (bindEditor)
		{
			TrlLabelEditor.bindEditor(this);
		}

		if (field instanceof Component)
		{
			((Component)field).setAttribute(TranslatableLabelSupport.ATTR_TranslatableLabelSupport, this);
		}
	}

	public static String translate(final String text)
	{
		// Try to translate the label
		String textTrl = text;
		if (text != null && text.indexOf("@") >= 0)
		{
			textTrl = Msg.parseTranslation(Env.getCtx(), text);
		}
		else if (text != null && text.indexOf(" ") < 0)
		{
			textTrl = Msg.translate(Env.getCtx(), text);
		}

		//
		// Remove "&" from translated text
		textTrl = Util.cleanAmp(textTrl);

		return textTrl;
	}

	public void setLabelAndTranslate(final String label)
	{
		final String labelTrl;
		if (isTranslatable)
		{
			labelTrl = translate(label);
		}
		else
		{
			labelTrl = label;
		}
		field.setLabelTrl(labelTrl);
		labelOrig = label;
	}

	/**
	 * Get original (not translated) text
	 * 
	 * @return original text
	 */
	public String getLabelOrig()
	{
		return labelOrig;
	}

	public void updateLabel()
	{
		setLabelAndTranslate(labelOrig);
	}

	public ITranslatableLabel getTranslatableLabel()
	{
		return field;
	}

	public boolean isTranslatable()
	{
		return isTranslatable;
	}

	public void setTranslatable(final boolean isTranslatable)
	{
		final boolean isTranslateableOld = this.isTranslatable;
		this.isTranslatable = isTranslatable;

		if (isTranslateableOld != isTranslatable)
		{
			updateLabel();
		}

	}

	public Page getPage()
	{
		return field.getPage();
	}

	public String getLabel()
	{
		return field.getLabel();
	}
}
