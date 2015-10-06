package org.adempiere.webui.component;

/*
 * #%L
 * ADempiere ERP - ZkWebUI Lib
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


import org.zkforge.fckez.FCKeditor;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;

public class HtmlEditor extends Div
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6901558125456421844L;
	
	private FCKeditor editor;
	private boolean readonly = false;
	private Div readonlyContainer;
	private Html readonlyHtml;

	public HtmlEditor()
	{
		super();
		initUI();
		setReadonly(false);
	}
	
	private void initUI()
	{
		//
		// Read-write component:
		editor = new FCKeditor();
		editor.setAutoHeight(true);
		editor.setWidth("100%");
		editor.setHeight("100%");
		editor.setToolbarSet("adempiere");
		editor.setCustomConfigurationsPath("js/fckconfig.js");
		
		//
		// Read-only component:
		this.readonlyContainer = new Div();
		readonlyContainer.setStyle("overflow: auto; border: 1px solid");
		this.readonlyHtml = new Html();
		readonlyContainer.appendChild(readonlyHtml);
		//readonlyHtml.setContent(text);
	}
	
	public void setValue(String text)
	{
		editor.setValue(text);
		readonlyHtml.setContent(text);
	}
	
	public String getValue()
	{
		return editor.getValue();
	}

	public boolean isReadonly()
	{
		return readonly;
	}

	public void setReadonly(boolean readonly)
	{
		if (this.readonly == readonly && !this.getChildren().isEmpty())
		{
			return;
		}
		this.readonly = readonly;
		
		if (readonly)
		{
			String text = editor.getValue();
			readonlyHtml.setContent(text);
			
			editor.detach();
			readonlyContainer.setParent(this);
		}
		else
		{
			readonlyContainer.detach();
			editor.setParent(this);
		}
	}
	
	@Override
	public void setWidth(String width)
	{
		super.setWidth(width);
		
		// we need to set editor's width same as container's width because just using "100%" for editor won't work
		editor.setWidth(width);
	}

	@Override
	public void setHeight(String height)
	{
		super.setHeight(height);
		
		// we need to set editor's height same as container's height because just using "100%" for editor won't work
		editor.setHeight(height);
	}
}
