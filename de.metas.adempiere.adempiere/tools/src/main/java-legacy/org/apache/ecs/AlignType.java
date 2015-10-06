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
    alignment attributes.

    @version $Id: AlignType.java,v 1.2 2006/07/30 00:54:03 jjanke Exp $
    @author <a href="mailto:snagy@servletapi.com">Stephan Nagy</a>
    @author <a href="mailto:jon@clearink.com">Jon S. Stevens</a>
*/
public interface AlignType
{

    // convience variables
    public final static String CENTER = "center";
    public final static String TOP = "top";
    public final static String BOTTOM = "bottom";
    public final static String ABSBOTTOM = "absbottom";
    public final static String ABSMIDDLE = "absmiddle";
    public final static String TEXTTOP = "texttop";
    public final static String LEFT = "left";
    public final static String RIGHT = "right";
    public final static String MIDDLE = "middle";
    public final static String BASELINE = "baseline";

    /** make sure implementing classes have a setAlign method. */
    public abstract void setAlign(String alignment);

    /** make sure implementing classes have a setVAlign method. */
    public abstract void setVAlign(String alignment);

}
