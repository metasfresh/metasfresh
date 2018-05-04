package de.metas.aggregation.model;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Env;

/**
 * Builder class for {@link I_C_Aggregation_Attribute}.
 * 
 * @author tsa
 *
 */
public class C_Aggregation_Attribute_Builder
{
	private IContextAware context = new PlainContextAware(Env.getCtx());
	private String type;
	private String code;

	public I_C_Aggregation_Attribute build()
	{
		final I_C_Aggregation_Attribute attr = InterfaceWrapperHelper.newInstance(I_C_Aggregation_Attribute.class, context);
		attr.setType(type);
		attr.setCode(code);
		InterfaceWrapperHelper.save(attr);

		return attr;
	}

	public C_Aggregation_Attribute_Builder setType(final String type)
	{
		this.type = type;
		return this;
	}

	public C_Aggregation_Attribute_Builder setCode(final String code)
	{
		this.code = code;
		return this;
	}
}
