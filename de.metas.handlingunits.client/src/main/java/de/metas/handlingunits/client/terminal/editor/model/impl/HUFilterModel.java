package de.metas.handlingunits.client.terminal.editor.model.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import de.metas.handlingunits.client.terminal.editor.model.filter.IHUKeyFilter;
import de.metas.handlingunits.model.I_C_POS_HUEditor_Filter;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.Check;
import de.metas.util.Services;

/* package */class HUFilterModel
{
	//
	// Services
	private final transient IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

	private final I_C_POS_HUEditor_Filter filter;
	private final IHUKeyFilter huKeyFilter;
	private Object value = null;

	public HUFilterModel(final I_C_POS_HUEditor_Filter filter)
	{
		Check.assumeNotNull(filter, "filter not null");
		this.filter = filter;

		final int javaClassId = filter.getAD_JavaClass_ID();
		final I_AD_JavaClass jc = loadOutOfTrx(javaClassId, I_AD_JavaClass.class);
		huKeyFilter = javaClassBL.newInstance(jc);
	}

	public IHUKeyFilter getHUKeyFilter()
	{
		return huKeyFilter;
	}

	public void setValue(final Object value)
	{
		this.value = value;
	}

	public Object getValue()
	{
		return value;
	}

	public int getDisplayType()
	{
		return filter.getAD_Reference_ID();
	}
}
