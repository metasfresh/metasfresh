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


import org.adempiere.util.Services;
import org.adempiere.webui.component.HtmlEditor;
import org.adempiere.webui.editor.FieldContextMenuHelper;
import org.adempiere.webui.util.ZkUtil;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;

import de.metas.letters.api.ITextTemplateBL;
import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.I_C_Letter;
import de.metas.web.component.FilePreviewWindow;
import de.metas.web.component.trl.TrlLabel;

public class LetterEditor extends Div
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2087859970631307827L;

	private final LetterEditorVM vm;

	private final transient Combobox fTextTemplate = new Combobox();
	private final transient Button btnSaveTemplate = new Button();
	private final transient Button btnPreview = new Button();
	private final transient Button btnPrint = new Button();

	private final transient Textbox fLetterSubject = new Textbox();
	private final transient HtmlEditor fLetterBody = new HtmlEditor();

	private boolean hideSave = true;

	private transient boolean loading = false;

	/**
	 * constructor with parameter for hiding save button; by default is hidden
	 * 
	 * @param hideSave
	 */
	public LetterEditor(final boolean hideSave)
	{
		this(new LetterEditorVM(), hideSave);
	}

	/**
	 * by default hide save button
	 * 
	 */
	public LetterEditor()
	{
		this(true);
	}

	public LetterEditor(final LetterEditorVM vm, final boolean hideSave)
	{
		super();
		this.vm = vm;
		this.hideSave = hideSave;
		initUI();
	}

	/**
	 * by default hide save button
	 * 
	 * @param vm
	 */
	public LetterEditor(final LetterEditorVM vm)
	{
		this(vm, true);
	}

	public String getLetterBody()
	{
		return fLetterBody.getValue();
	}

	private void initUI()
	{
		fTextTemplate.setStyle("float: left; vertical-align:middle;");
		fTextTemplate.setAutocomplete(true);
		fTextTemplate.setAutodrop(true);
		fTextTemplate.setItemRenderer(new ComboitemRenderer()
		{

			@Override
			public void render(final Comboitem item, final Object data) throws Exception
			{
				final I_AD_BoilerPlate textTemplate = (I_AD_BoilerPlate)data;
				if (!textTemplate.isActive())
				{
					return;
				}
				item.setValue(textTemplate);
				item.setLabel(textTemplate.getName());
				item.setDescription(textTemplate.getSubject());
			}
		});
		fTextTemplate.setModel(vm.getAvailableTextTemplatesModel());
		fTextTemplate.addEventListener(Events.ON_CHANGE, new EventListener()
		{
			@Override
			public void onEvent(final Event event)
			{
				if (loading)
				{
					return;
				}

				final I_AD_BoilerPlate selectedTextTemplate = ZkUtil.getSelectedValue(fTextTemplate);
				if (selectedTextTemplate == null)
				{
					return;
				}
				setFromTextTemplate(selectedTextTemplate);
			}
		});

		if (!hideSave)
		{
			btnSaveTemplate.setLabel(Msg.translate(Env.getCtx(), "Save"));
			btnSaveTemplate.setImage("/images/Save16.png");
			btnSaveTemplate.setMold("os");
			btnSaveTemplate.addEventListener(Events.ON_CLICK, new EventListener()
			{
				@Override
				public void onEvent(final Event event)
				{
					onSaveTemplate();
				}
			});
		}
		btnPrint.setLabel(Msg.translate(Env.getCtx(), "Print"));
		btnPrint.setImage("/images/Print16.png");
		btnPrint.setMold("os");
		btnPrint.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(final Event event)
			{
				onPrint();
			}
		});

		btnPreview.setLabel(Msg.translate(Env.getCtx(), "Preview"));
		btnPreview.setImage("/images/PrintPreview16.png");
		btnPreview.setMold("os");
		btnPreview.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(final Event event)
			{
				onPrintPreview();
			}
		});

		final Toolbar toolbar = new Toolbar();
		toolbar.setStyle("vertical-align: middle;");
		toolbar.appendChild(fTextTemplate);
		if (!hideSave)
		{
			toolbar.appendChild(btnSaveTemplate);
		}
		toolbar.appendChild(btnPrint);
		toolbar.appendChild(btnPreview);

		fLetterSubject.setWidth("100%");
		fLetterBody.setWidth("100%");
		fLetterBody.setHeight("400px");

		final Div panel = new Div();
		panel.setParent(this);
		panel.setWidth("630px"); // size set per requirement, the user shall have the A4 feel
		panel.setHeight("100%");
		panel.appendChild(toolbar);
		createAdditionalFields(panel);

		// Letter subject
		{
			final Div subjectLine = new Div();
			subjectLine.setParent(panel);
			subjectLine.setStyle("width: 100%;");

			final Label subjectLabel = new TrlLabel();
			subjectLabel.setParent(subjectLine);
			subjectLabel.setValue(I_C_Letter.COLUMNNAME_LetterSubject);
			subjectLabel.setStyle("display: inline-block; padding-right: 5px;");
			// subjectLabel.setWidth("80px");

			fLetterSubject.setParent(subjectLine);
			fLetterSubject.setStyle("display: inline-block;");
			fLetterSubject.setWidth("500px");
		}

		// Leyter body
		panel.appendChild(fLetterBody);

		//
		// Context Menu
		if (vm.isDefaultsEnabled())
		{
			FieldContextMenuHelper.get().enableContextMenu(
					vm.getDefaultsNamespace(),
					LetterEditorVM.PROPERTY_AD_BoilerPlate_ID,
					Msg.translate(Env.getCtx(), I_C_Letter.COLUMNNAME_AD_BoilerPlate_ID), // propertyDisplayName
					fTextTemplate); // field
		}
	}

	protected void createAdditionalFields(final Div panel)
	{
		// nothing at this level
	}

	protected void onSaveTemplate()
	{
		save();
		vm.onSaveTemplate();
		load();
	}

	private void onPrintPreview()
	{
		save();

		final byte[] pdf = vm.createPDF();

		// Filedownload.save(pdf, "application/pdf", "TextTemplatePreview.pdf");
		FilePreviewWindow.openNewWindow(pdf, "application/pdf", "TextTemplatePreview.pdf");
	}

	private void onPrint()
	{
		save();
		vm.printPDF();
	}

	public void load()
	{
		loading = true;
		try
		{
			final I_C_Letter bean = vm.getC_Letter();
			ZkUtil.setSelectedValue(fTextTemplate, bean.getAD_BoilerPlate());

			fLetterSubject.setValue(bean.getLetterSubject());
			// we aded this field to redraw the stage for our fLetterBody values
			// task: http://dewiki908/mediawiki/index.php/02864:_FCKeditor_is_clearing_the_content_if_we_open_a_modal_window_on_top_of_it_%282012061210000051%29#Development_infrastructure
			fLetterBody.setValue(null);
			fLetterBody.setValue(bean.getLetterBody());

			loadAdditionalFields();
		}
		finally
		{
			loading = false;
		}
	}

	protected void loadAdditionalFields()
	{
		// nothing at this level
	}

	public void save()
	{
		final I_C_Letter letter = vm.getC_Letter();

		final I_AD_BoilerPlate selectedTextTemplate = ZkUtil.getSelectedValue(fTextTemplate);
		vm.setSelectedTextTemplateName(fTextTemplate.getText());
		letter.setAD_BoilerPlate(selectedTextTemplate);
		letter.setLetterSubject(fLetterSubject.getValue());
		letter.setLetterBody(fLetterBody.getValue());

		saveAdditionalFields();
	}

	protected void saveAdditionalFields()
	{
		// nothing on this level
	}

	public LetterEditorVM getVM()
	{
		return vm;
	}

	private void setFromTextTemplate(final I_AD_BoilerPlate textTemplate)
	{
		fLetterSubject.setValue(textTemplate.getSubject());
		fLetterBody.setValue(textTemplate.getTextSnippext());

		final boolean templateEditable = Services.get(ITextTemplateBL.class).canUpdate(textTemplate);
		btnSaveTemplate.setDisabled(!templateEditable);
	}
}
