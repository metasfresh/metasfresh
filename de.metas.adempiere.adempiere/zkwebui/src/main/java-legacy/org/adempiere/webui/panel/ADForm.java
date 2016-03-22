/******************************************************************************
 * Product: Posterita Ajax UI 												  *
 * Copyright (C) 2007 Posterita Ltd.  All Rights Reserved.                    *
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
 * Posterita Ltd., 3, Draper Avenue, Quatre Bornes, Mauritius                 *
 * or via info@posterita.org or http://www.posterita.org/                     *
 *****************************************************************************/

package org.adempiere.webui.panel;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.webui.component.Window;
import org.adempiere.webui.exception.ApplicationException;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.ADClassNameMap;
import org.compiere.model.I_AD_Form;
import org.compiere.model.MForm;
import org.compiere.process.ProcessInfo;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

/**
 * Adempiere Web UI custom form.
 * The form is abstract, so specific types of custom form must be implemented
 *
 * @author Andrew Kimball
 */
public abstract class ADForm extends Window implements EventListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5183711788893823434L;
	/** The class' logging enabler */
    protected static final Logger logger;

    static
    {
        logger = LogManager.getLogger(ADForm.class);
    }

    /** The unique identifier of the form type */
    private int m_adFormId;
    /** The identifying number of the window in which the form is housed */
    protected int m_WindowNo;


	private String m_name;


	private ProcessInfo m_pi;
	
	private IFormController m_customForm;

    /**
     * Constructor
     *
     * @param ctx		the context into which the form is being placed
     * @param adFormId	the Adempiere form identifier
     */
    protected ADForm()
    {
         m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);

         this.setWidth("100%");
         this.setHeight("95%");
         this.setStyle("position:absolute");
         this.setContentSclass("adform-content");
    }

    public int getWindowNo()
    {
    	return m_WindowNo;
    }

    // metas: changed from protected to public
    public int getAdFormId()
    {
    	return m_adFormId;
    }

    /**
     * Initialise the form
     *
     * @param adFormId	the Adempiere form identifier
     * @param name		the name of the Adempiere form
     */

    protected void init(int adFormId, String name)
    {
        if(adFormId <= 0)
        {
	           throw new IllegalArgumentException("Form Id is invalid");
	   	}

        m_adFormId = adFormId;
        //window title
        setTitle(name);
        m_name = name;

        initForm();
    }

    abstract protected void initForm();

	/**
     * @return form name
     */
    public String getFormName() {
    	return m_name;
    }

	/**
	 * Convert the rich client class name for a form to its web UI equivalent
	 *
	 * @param originalName	The full class path to convert
	 * @return the converted class name
	 */
	private static String translateFormClassName(String originalName)
	{
		String zkName = null;
		/*
		 * replacement string to translate class paths to the form
		 * "org.adempiere.webui.apps.form.<classname>"
		 */
		final String zkPackage = "org.adempiere.webui.";
		/*
		 * replacement string to translate custom form class name from
		 * "V<name>" to "W<name>"
		 */
		final String zkPrefix = "W";
		final String swingPrefix = "V";

        String tail = null;
        //first, try replace package
		if (originalName.startsWith("org.compiere."))
		{
			tail = originalName.substring("org.compiere.".length());
		}
		else if(originalName.startsWith("org.adempiere."))
		{
			tail = originalName.substring("org.adempiere.".length());
		}
		if (tail != null)
		{
			zkName = zkPackage + tail;
			
    		try {
				Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
				if (!isZkFormClass(clazz))
				{
					zkName = null;
				}
			} catch (ClassNotFoundException e) {
				zkName = null;
			}
			
			//try replace package and add W prefix to class name
			if (zkName == null)
			{
				String packageName = zkPackage;
				int lastdot = tail.lastIndexOf(".");
				String className = null;
				if (lastdot >= 0)
				{
					if (lastdot > 0)
						packageName = packageName + tail.substring(0, lastdot+1);
					className = tail.substring(lastdot+1);
				}
				else
				{
					className = tail;
				}
				
				//try convert V* to W*
				if (className.startsWith(swingPrefix))
				{
					zkName = packageName + zkPrefix + className.substring(1);
					try {
						Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
						if (!isZkFormClass(clazz))
						{
							zkName = null;
						}
					} catch (ClassNotFoundException e) {
						zkName = null;
					}
				}
				
				//try append W prefix to original class name
				if (zkName == null)
				{
					zkName = packageName + zkPrefix + className;
					try {
						Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
						if (!isZkFormClass(clazz))
						{
							zkName = null;
						}
					} catch (ClassNotFoundException e) {
						zkName = null;
					}
				}
			}
        }
        
		/*
		 *  not found, try changing only the class name 
		 */
		if (zkName == null)
		{
			int lastdot = originalName.lastIndexOf(".");
			String packageName = originalName.substring(0, lastdot);
			String className = originalName.substring(lastdot+1);
			//try convert V* to W*
			if (className.startsWith(swingPrefix))
			{
				String zkClassName = zkPrefix + className.substring(1);
				zkName = packageName + "." + zkClassName;
				try {
					Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
					if (!isZkFormClass(clazz))
					{
						zkName = null;
					}
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}
			
			//try just append W to the original class name
			if (zkName == null)
			{				
				String zkClassName = zkPrefix + className;
				zkName = packageName + "." + zkClassName;
				try {
					Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
					if (!isZkFormClass(clazz))
					{
						zkName = null;
					}
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}
			
			if (zkName == null)
			{
				//finally try whether same name is used for zk
				zkName = originalName;
				try {
					Class<?> clazz = ADForm.class.getClassLoader().loadClass(zkName);
					if (!isZkFormClass(clazz))
					{
						zkName = null;
					}
				} catch (ClassNotFoundException e) {
					zkName = null;
				}
			}
		}

		return zkName;
	}

	private static boolean isZkFormClass(Class<?> clazz) {
		return IFormController.class.isAssignableFrom(clazz) || Component.class.isAssignableFrom(clazz);
	}

	/**
	 * Create a new form corresponding to the specified identifier
	 *
	 * @param adFormID		The unique identifier for the form type
	 * @return The created form
	 */
	public static ADForm openForm (int adFormID)
	{
// metas: tsa: begin:  US831: Open one window per session per user (2010101810000044)
// metas: code changed to introduce openForm(I_AD_Form) method
		MForm mform = new MForm(Env.getCtx(), adFormID, null);
		return openForm(mform);
	}
	public static ADForm openForm (I_AD_Form mform)
	{
		if (mform == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Form_ID@");
		}
		final int adFormID = mform.getAD_Form_ID();
		Object obj;
		ADForm form;
		String webClassName = "";
    	String richClassName = mform.getClassname();
    	final I_AD_Form mformTrl = InterfaceWrapperHelper.translate(mform, I_AD_Form.class);
    	String name = mformTrl.getName();
// metas: tsa: end:  US831: Open one window per session per user (2010101810000044)

    	if (mform.getAD_Form_ID() == 0 || richClassName == null)
    	{
			throw new ApplicationException("There is no form associated with the specified selection");
    	}
    	else
    	{

    		logger.info("AD_Form_ID=" + adFormID + " - Class=" + richClassName);

    		//static lookup
    		webClassName = ADClassNameMap.get(richClassName);
    		//fallback to dynamic translation
    		if (webClassName == null || webClassName.trim().length() == 0)
    		{
    			webClassName = translateFormClassName(richClassName);
    		}
    		
    		if (webClassName == null)
    		{
    			throw new ApplicationException("Web UI form not implemented for the swing form " +
 					   richClassName);
    		}

    		try
    		{
    			//	Create instance w/o parameters
        		obj = ADForm.class.getClassLoader().loadClass(webClassName).newInstance();
    		}
    		catch (Exception e)
    		{
    			throw new ApplicationException("The selected web user interface custom form '" +
    					   webClassName +
							"' is not accessible: " + e.getLocalizedMessage(), e);
    		}

        	try
        	{
        		if (obj instanceof ADForm)
        		{
    				form = (ADForm)obj;
    				form.init(adFormID, name);
    				return form;
        		}
        		else if (obj instanceof IFormController)
        		{
        			IFormController customForm = (IFormController)obj;
        			Object o = customForm.getForm();
        			if(o instanceof ADForm)
        			{
        				form = (ADForm)o;
        				form.setICustomForm(customForm);
        				form.init(adFormID, name);
        				return form;
        			}
        			else
	        			throw new ApplicationException("The web user interface custom form '" +
	    						webClassName +
	    						"' cannot be displayed in the web user interface.");
        		}
        		else
        		{
    				throw new ApplicationException("The web user interface custom form '" +
    						webClassName +
    						"' cannot be displayed in the web user interface.");
        		}
        	}
        	catch (Exception ex)
        	{
    			logger.error("Class=" + webClassName + ", AD_Form_ID=" + adFormID, ex);
    			throw new ApplicationException("The web user interface custom form '" +
    					webClassName +
    					"' failed to initialise:" + ex);
        	}
    	}
	}	//	openForm

    /**
     *
     */
	@Override
	public void onEvent(Event arg0) throws Exception
    {

    }

	/**
	 * @param pi
	 */
	public void setProcessInfo(ProcessInfo pi) {
		m_pi = pi;
	}

	/**
	 * @return ProcessInfo
	 */
	public ProcessInfo getProcessInfo()
	{
		return m_pi;
	}
	
	public void setICustomForm(IFormController customForm)
	{
		m_customForm = customForm;
	}
	
	public IFormController getICustomForm()
	{
		return m_customForm;
	}
}
