package de.metas.email.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.user.api.IUserDAO;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_MailText;

import de.metas.adempiere.service.IVariableParserBL;
import de.metas.email.IMailTextBuilder;
import de.metas.i18n.IModelTranslation;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.impl.NullModelTranslation;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

class MailTextBuilder implements IMailTextBuilder
{
	public static final MailTextBuilder of(final I_R_MailText mailTextDef)
	{
		return new MailTextBuilder(mailTextDef);
	}

	// services
	private final transient IVariableParserBL variableParserBL = Services.get(IVariableParserBL.class);

	private final I_R_MailText _mailTextDef;
	private final Properties _ctx;
	private final boolean html;
	private final int _mailTextId;

	//
	// Parameters
	private I_AD_User _user = null;
	private I_C_BPartner _bpartner = null;
	private Object _record = null;
	private String _adLanguage = null;
	private final Map<String, String> _customVariables = new HashMap<>();

	//
	// Cache
	private TextsVO _texts = null;
	private final Map<String, String> text2parsedText = new HashMap<>();


	private MailTextBuilder(final I_R_MailText mailTextDef)
	{
		super();
		Check.assumeNotNull(mailTextDef, "mailTextDef not null");
		_mailTextDef = mailTextDef;
		this._ctx = InterfaceWrapperHelper.getCtx(_mailTextDef);
		this.html = mailTextDef.isHtml();
		this._mailTextId = mailTextDef.getR_MailText_ID();
	}

	private I_R_MailText getMailTextDef()
	{
		return _mailTextDef;
	}

	private Properties getCtx()
	{
		return _ctx;
	}

	@Override
	public boolean isHtml()
	{
		return html;
	}
	
	@Override
	public int getR_MailText_ID()
	{
		return _mailTextId;
	}

	private IModelTranslation getMailTextDefTranslation(final String AD_Language)
	{
		final I_R_MailText mailTextDef = getMailTextDef();
		final IModelTranslationMap translationsMap = InterfaceWrapperHelper.getModelTranslationMap(mailTextDef);
		return translationsMap.getTranslation(AD_Language);
	}

	@Override
	public String getMailHeader()
	{
		final String mailHeader = getTextsVO().getMailHeader();
		return parse(mailHeader);
	}

	@Override
	public String getFullMailText()
	{
		final TextsVO texts = getTextsVO();
		final String mailText = texts.getMailText();
		//
		final StringBuilder sb = new StringBuilder();
		sb.append(mailText);

		final String mailText2 = texts.getMailText2();
		if (mailText2 != null && mailText2.length() > 0)
		{
			sb.append("\n").append(mailText2);
		}

		final String mailText3 = texts.getMailText3();
		if (mailText3 != null && mailText3.length() > 0)
		{
			sb.append("\n").append(mailText3);
		}
		//
		return parse(sb.toString());
	}	// getMailText

	@Override
	public String getMailText()
	{
		final String mailText = getTextsVO().getMailText();
		return parse(mailText);
	}	// getMailText

	/**
	 * Get parsed/translated Mail Text 2
	 *
	 * @return parsed/translated text
	 */
	public String getMailText2()
	{
		final String mailText2 = getTextsVO().getMailText2();
		return parse(mailText2);
	}	// getMailText2

	/**
	 * Get parsed/translated Mail Text 2
	 *
	 * @return parsed/translated text
	 */
	public String getMailText3()
	{
		final String mailText3 = getTextsVO().getMailText3();
		return parse(mailText3);
	}

	/**
	 * Parse Text
	 *
	 * @param text text
	 * @return parsed text
	 */
	private String parse(final String text)
	{
		if (text == null)
		{
			return "";
		}
		if (text.indexOf('@') < 0)
		{
			return text;
		}

		if (text2parsedText.containsKey(text))
		{
			return text2parsedText.get(text);
		}
		
		String textParsed = text;
		textParsed = parse(textParsed, getRecord()); // first parse the record values
		textParsed = parse(textParsed, getAD_User());
		textParsed = parse(textParsed, getC_BPartner());
		text2parsedText.put(text, textParsed); // cache it
		//
		return textParsed;
	}	// parse

	/**
	 * Parse text
	 *
	 * @param text text
	 * @param model
	 * @return parsed text
	 */
	private String parse(final String text, final Object model)
	{
		if (model == null)
		{
			return text;
		}
		if (text.indexOf('@') < 0)
		{
			return text;
		}

		String inStr = text;
		String token;
		final StringBuilder outStr = new StringBuilder();

		int i = inStr.indexOf('@');
		while (i >= 0)
		{
			outStr.append(inStr.substring(0, i));			// up to @
			inStr = inStr.substring(i + 1, inStr.length());	// from first @

			final int j = inStr.indexOf('@');						// next @
			if (j < 0)      										// no second tag
			{
				inStr = "@" + inStr;
				break;
			}

			token = inStr.substring(0, j);
			outStr.append(parseVariable(token, model));		// replace context

			inStr = inStr.substring(j + 1, inStr.length());	// from second @
			i = inStr.indexOf('@');
		}

		outStr.append(inStr);           					// add remainder
		return outStr.toString();
	}	// parse

	/**
	 * Parse Variable
	 *
	 * @param variable variable
	 * @param model
	 * @return translated variable or if not found the original tag
	 */
	private String parseVariable(final String variable, final Object model)
	{
		if (_customVariables.containsKey(variable))
		{
			final String value = _customVariables.get(variable);
			return value == null ? "" : value;
		}

		final Object value = variableParserBL.resolveVariable(variable, model, "@" + variable + "@");
		if (value == null)
		{
			return "";
		}
		return value.toString();
	}

	@Override
	public IMailTextBuilder setAD_User(final int AD_User_ID)
	{
		setAD_User(Services.get(IUserDAO.class).retrieveUser(getCtx(), AD_User_ID));
		return this;
	}

	@Override
	public IMailTextBuilder setAD_User(final I_AD_User user)
	{
		_user = user;
		text2parsedText.clear(); // reset cache
		return this;
	}

	@Override
	public I_AD_User getAD_User()
	{
		return _user;
	}

	@Override
	public IMailTextBuilder setC_BPartner(final int C_BPartner_ID)
	{
		setC_BPartner(C_BPartner_ID <= 0 ? null : InterfaceWrapperHelper.create(getCtx(), C_BPartner_ID, I_C_BPartner.class, ITrx.TRXNAME_None));
		return this;
	}

	@Override
	public IMailTextBuilder setC_BPartner(final I_C_BPartner bpartner)
	{
		_bpartner = bpartner;
		
		_texts = null; // reset texts
		text2parsedText.clear(); // reset cache
		
		return this;
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		return _bpartner;
	}

	@Override
	public String getAD_Language()
	{
		if(_adLanguage != null)
		{
			return _adLanguage;
		}
		
		final I_C_BPartner bpartner = getC_BPartner();
		return bpartner == null ? null : bpartner.getAD_Language();
	}
	
	@Override
	public IMailTextBuilder setAD_Language(final String adLanguage)
	{
		this._adLanguage = adLanguage;
		return this;
	}

	@Override
	public IMailTextBuilder setRecord(final Object record)
	{
		final boolean analyse = false;
		setRecord(record, analyse);
		return this;
	}

	@Override
	public IMailTextBuilder setRecord(final Object record, final boolean analyse)
	{
		_record = record;
		text2parsedText.clear(); // reset cache
		
		if (analyse)
		{
			final Object bpartnerIdObj = InterfaceWrapperHelper.getValueOrNull(record, "C_BPartner_ID");
			if (bpartnerIdObj instanceof Integer)
			{
				final int C_BPartner_ID = ((Integer)bpartnerIdObj).intValue();
				setC_BPartner(C_BPartner_ID);
			}

			final Object adUserIdObj = InterfaceWrapperHelper.getValueOrNull(record, "AD_User_ID");
			if (adUserIdObj instanceof Integer)
			{
				final int AD_User_ID = ((Integer)adUserIdObj).intValue();
				setAD_User(AD_User_ID);
			}
		}

		return this;
	}

	@Override
	public Object getRecord()
	{
		return _record;
	}

	@Override
	public IMailTextBuilder setCustomVariable(final String name, final String value)
	{
		_customVariables.put(name, value);
		text2parsedText.clear(); // reset cache
		return this;
	}

	private TextsVO getTextsVO()
	{
		if (_texts == null)
		{
			_texts = createTextsVO(getAD_Language());
		}
		return _texts;
	}	// translate

	private TextsVO createTextsVO(final String adLanguage)
	{
		if (adLanguage != null)
		{
			final IModelTranslation trl = getMailTextDefTranslation(adLanguage);
			if (!NullModelTranslation.isNull(trl))
			{
				final String mailHeader = trl.getTranslation(I_R_MailText.COLUMNNAME_MailHeader);
				final String mailText = trl.getTranslation(I_R_MailText.COLUMNNAME_MailText);
				final String mailText2 = trl.getTranslation(I_R_MailText.COLUMNNAME_MailText2);
				final String mailText3 = trl.getTranslation(I_R_MailText.COLUMNNAME_MailText3);
				return TextsVO.of(mailHeader, mailText, mailText2, mailText3);
			}
		}

		// No Translation
		final I_R_MailText mailTextDef = getMailTextDef();
		final String mailHeader = mailTextDef.getMailHeader();
		final String mailText = mailTextDef.getMailText();
		final String mailText2 = mailTextDef.getMailText2();
		final String mailText3 = mailTextDef.getMailText3();
		return TextsVO.of(mailHeader, mailText, mailText2, mailText3);
	}

	private static final class TextsVO
	{
		public static final TextsVO of(final String mailHeader, final String mailText, final String mailText2, final String mailText3)
		{
			return new TextsVO(mailHeader, mailText, mailText2, mailText3);
		}

		private final String mailHeader;
		private final String mailText;
		private final String mailText2;
		private final String mailText3;

		private TextsVO(final String mailHeader, final String mailText, final String mailText2, final String mailText3)
		{
			super();
			this.mailHeader = mailHeader;
			this.mailText = mailText;
			this.mailText2 = mailText2;
			this.mailText3 = mailText3;
		}

		public String getMailHeader()
		{
			return mailHeader;
		}

		public String getMailText()
		{
			return mailText;
		}

		public String getMailText2()
		{
			return mailText2;
		}

		public String getMailText3()
		{
			return mailText3;
		}
	}
}
