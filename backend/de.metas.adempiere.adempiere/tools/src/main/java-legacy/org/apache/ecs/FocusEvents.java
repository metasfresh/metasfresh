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
    javascript page event attributes.

    @version $Id: FocusEvents.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface FocusEvents
{
    /**
        make sure implementing classes have a setOnFocus method.

        The onfocus event occurs when an element receives focus either by the
        pointing device or by tabbing navigation. This attribute may be used
        with the following elements: LABEL, INPUT, SELECT, TEXTAREA, and
        BUTTON.
    */
    public abstract void setOnFocus(String script);

    /**
        make sure implementing classes have a setOnBlur method.

        The onblur event occurs when an element loses focus either by the
        pointing device or by tabbing navigation. It may be used with the same
        elements as onfocus.
    */
    public abstract void setOnBlur(String script);
}
