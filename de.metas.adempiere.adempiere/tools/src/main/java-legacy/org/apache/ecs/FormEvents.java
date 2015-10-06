/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.apache.ecs;

/**
    This interface is intended to be implemented by elements that require
    javascript form event attributes.

    @version $Id: FormEvents.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface FormEvents
{
    /**
        make sure implementing classes have a setOnSubmit method.
        
        The onsubmit event occurs when a form is submitted. It only applies to
        the FORM element.
    */
    public abstract void setOnSubmit(String script);

    /**
        make sure implementing classes have a setOnReset method.
        
        The onreset event occurs when a form is reset. It only applies to the
        FORM element.
    */
    public abstract void setOnReset(String script);

    /**
        make sure implementing classes have a setOnSelect method.
        
        The onselect event occurs when a user selects some text in a text
        field. This attribute may be used with the INPUT and TEXTAREA elements.
    */
    public abstract void setOnSelect(String script);

    /**
        make sure implementing classes have a setOnChange method.
        
        The onchange event occurs when a control loses the input focus and its
        value has been modified since gaining focus. This attribute applies to
        the following elements: INPUT, SELECT, and TEXTAREA.
    */
    public abstract void setOnChange(String script);
}
