package de.metas.letters.webui.component;

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


import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.webui.editor.FieldContextMenuHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.zkoss.lang.Objects;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.mesg.MZul;

import de.metas.adempiere.form.IClientUI;
import de.metas.letters.api.ITextTemplateBL;
import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.I_C_Letter;

public class LetterEditorVM
{
	public static final String PROPERTY_AD_BoilerPlate_ID = "LetterEditorVM#AD_BoilerPlate_ID";

	public static final String ATTR_BPName = "Name";
	public static final String ATTR_BPAddress = "Address";
	public static final String ATTR_OrgName = "Sektion";

	private final ListModelList availableTextTemplatesModel = new ListModelList();

	private String selectedTextTemplateName = null;

	private Map<String, Object> attributes;

	public LetterEditorVM()
	{
		availableTextTemplatesModel.clear();
		availableTextTemplatesModel.addAll(Services.get(ITextTemplateBL.class).getAvailableLetterTemplates());
	}

	protected Properties getCtx()
	{
		final I_C_Letter letter = getC_Letter();
		if (letter == null)
		{
			return Env.getCtx();
		}
		else
		{
			return InterfaceWrapperHelper.getCtx(letter);
		}
	}

	private I_C_Letter _letter;

	public void setC_Letter(final I_C_Letter letter)
	{
		_letter = letter;
	}

	public I_C_Letter getC_Letter()
	{
		return _letter;
	}

	public ListModel getAvailableTextTemplatesModel()
	{
		return availableTextTemplatesModel;
	}

	public void setSelectedTextTemplateName(final String name)
	{
		selectedTextTemplateName = name;
	}

	public String getSelectedTextTemplateName()
	{
		return selectedTextTemplateName;
	}

	public byte[] createPDF()
	{
		final I_C_Letter letter = getC_Letter();

		final ITextTemplateBL textTemplateBL = Services.get(ITextTemplateBL.class);

		textTemplateBL.setLetterBodyParsed(letter, getAttributes());

		final byte[] pdf = textTemplateBL.createPDF(letter);
		return pdf;
	}

	public void printPDF()
	{
		final byte[] data = createPDF();

		final Properties ctx = getCtx();

		final IArchiveStorage storage = Services.get(IArchiveStorageFactory.class).getArchiveStorage(ctx);
		final I_AD_Archive archive = storage.newArchive(ctx, ITrx.TRXNAME_None);
		archive.setAD_Org_ID(Env.getAD_Org_ID(ctx));

		// don't set AD_Table_ID/Record_ID => generic Archive,
		// and generic archives will be printed directly (see de.metas.printing.model.validator.AD_Archive.printArchive(I_AD_Archive))
		// archive.setAD_Table_ID(AD_Table_ID);
		// archive.setRecord_ID(Record_ID);

		archive.setName("Letter_" + UUID.randomUUID());
		storage.setBinaryData(archive, data);
		archive.setIsReport(false);
		InterfaceWrapperHelper.save(archive);
	}

	public void onSaveTemplate()
	{
		final I_C_Letter letter = getC_Letter();

		final String letterSubject = letter.getLetterSubject();
		if (Util.isEmpty(letterSubject, true))
		{
			throw new WrongValueException(MZul.EMPTY_NOT_ALLOWED);
		}

		final String letterBody = letter.getLetterBody();
		if (Util.isEmpty(letterBody, true))
		{
			throw new WrongValueException(MZul.EMPTY_NOT_ALLOWED);
		}

		final I_AD_BoilerPlate textTemplate;
		//
		// User selected a text template and he did NOT change it's name => override selected text template
		final I_AD_BoilerPlate selectedTextTemplate = letter.getAD_BoilerPlate();
		if (selectedTextTemplate != null && selectedTextTemplate.getName().equals(selectedTextTemplateName))
		{
			textTemplate = selectedTextTemplate;

			InterfaceWrapperHelper.refresh(textTemplate);

			final boolean templateEditable = Services.get(ITextTemplateBL.class).canUpdate(textTemplate);
			Util.assume(templateEditable, "UpdateNotAllowed");

			if (Objects.equals(selectedTextTemplate.getSubject(), letterSubject)
					&& Objects.equals(selectedTextTemplate.getTextSnippext(), letterBody))
			{
				// nothing to save
				return;
			}

			if (!Services.get(IClientUI.class).ask(0, "OverrideChanges", null))
			{
				return;
			}
		}
		//
		// User selected a text template and he did change it's name => save it as a new template
		else
		{
			if (Util.isEmpty(selectedTextTemplateName, true))
			{
				throw new WrongValueException(MZul.EMPTY_NOT_ALLOWED);
			}

			textTemplate = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_BoilerPlate.class, ITrx.TRXNAME_None);
			textTemplate.setName(selectedTextTemplateName);
			textTemplate.setIsActive(true);
		}

		textTemplate.setSubject(letterSubject);
		textTemplate.setTextSnippext(letterBody);
		InterfaceWrapperHelper.save(textTemplate);

		letter.setAD_BoilerPlate(textTemplate); // link textTemplate to letter

		final int templateIdx = availableTextTemplatesModel.indexOf(textTemplate);
		if (templateIdx < 0)
		{
			// Add textTemplate to available text templates model
			availableTextTemplatesModel.add(0, textTemplate);
		}
		else
		{
			// Trigger render update
			availableTextTemplatesModel.set(templateIdx, textTemplate);
		}
	}

	public void setAttributes(final Map<String, Object> attrs)
	{
		attributes = attrs;
	}

	public Map<String, Object> getAttributes()
	{
		final Map<String, Object> attrs;
		if (attributes == null)
		{
			attrs = Collections.emptyMap();
		}
		else
		{
			attrs = attributes;
		}

		return attrs;
	}

	private String _defaultsNamespace = null;

	/**
	 * @return the defaultsNamespace
	 */
	public String getDefaultsNamespace()
	{
		return _defaultsNamespace;
	}

	/**
	 * @param defaultsNamespace the defaultsNamespace to set
	 */
	public void setDefaultsNamespace(final String defaultsNamespace)
	{
		_defaultsNamespace = defaultsNamespace;
	}

	public boolean isDefaultsEnabled()
	{
		if (Util.isEmpty(_defaultsNamespace, true))
		{
			return false;
		}

		return true;
	}

	public void loadDefaults()
	{
		if (!isDefaultsEnabled())
		{
			return;
		}

		final I_C_Letter letter = getC_Letter();
		if (letter == null)
		{
			return;
		}

		final I_AD_BoilerPlate textTemplate = FieldContextMenuHelper.get().getDefaultValue(getDefaultsNamespace(), LetterEditorVM.PROPERTY_AD_BoilerPlate_ID, I_AD_BoilerPlate.class);
		Services.get(ITextTemplateBL.class).setAD_BoilerPlate(letter, textTemplate);
	}
}
