/**
 * 
 */
package org.compiere.grid.ed;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import org.adempiere.images.Images;
import org.compiere.apps.AEnv;
import org.compiere.apps.LetterDialog;
import org.compiere.swing.CButton;
import org.compiere.swing.CPanel;
import org.compiere.util.Env;
import org.compiere.util.Util;

import de.metas.i18n.Msg;
import de.metas.letters.model.Letters;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;

/**
 * @author teo_sarca
 * 
 */
public class VLetterAttachment extends CPanel {
	private final Dialog parentDialog;
	private VTextLong fPreview = new VTextLong("Preview", false, true, false, 60, 60);
	private CButton bEdit = new CButton();
	private CButton bCancel = new CButton();

	private BoilerPlateContext variables = BoilerPlateContext.EMPTY;
	private String letterHtml = "";
	private File letterPdfFile = null;

	private final ActionListener editAction = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			final LetterDialog d = new LetterDialog(parentDialog, Msg.translate(Env.getAD_Language(Env.getCtx()), Letters.MSG_Letter), variables);
			d.setMessage(letterHtml);
			AEnv.showCenterScreen(d);
			if (d.isPressedOK()) {
				letterHtml = d.getMessage();
				letterPdfFile = d.getPDF();
				updateComponentsStatus();
			}

		}
	};
	private final ActionListener cancelAction = new ActionListener() {
		@Override
		public void actionPerformed(final ActionEvent e) {
			letterHtml = "";
			letterPdfFile = null;
			updateComponentsStatus();
		}
	};

	public VLetterAttachment(final Dialog parentDialog) {
		super();
		this.parentDialog = parentDialog;
		init();
	}

	private void init() {
		fPreview.setText("");
		fPreview.setPreferredSize(new Dimension (500,80)); // else the component will be displayed too small if empty (when using FlowLayout) 
		bEdit.setIcon(Images.getImageIcon2("Open16"));
		bEdit.setText("");
		bEdit.addActionListener(editAction);
		bCancel.setIcon(Images.getImageIcon2("Cancel16"));
		bCancel.setText("");
		bCancel.addActionListener(cancelAction);
		//
		setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(fPreview);
		this.add(bEdit);
		this.add(bCancel);
	}

	private void updateComponentsStatus() {
		fPreview.setText(letterHtml == null ? "" : letterHtml);
		bCancel.setEnabled(!Util.isEmpty(letterHtml, false));
	}

	public void setVariables(final BoilerPlateContext variables) {
		this.variables = variables;
	}

	public String getLetterHtml() {
		return letterHtml;
	}

	public File getPDF() {
		return letterPdfFile;
	}
}
