package de.metas.material.planning.exception;

import java.util.Date;

import org.eevolution.model.I_PP_Product_BOM;

/**
 * Thrown when BOM is not valid on given date
 * 
 * @author Teo Sarca
 */
public class BOMExpiredException extends MrpException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -3084324343550833077L;

	public BOMExpiredException(final I_PP_Product_BOM bom, final Date date)
	{
		super(buildMessage(bom, date));
	}

	private static final String buildMessage(final I_PP_Product_BOM bom, final Date date)
	{
		return "@NotValid@ @PP_Product_BOM_ID@:" + bom.getValue() + " - @Date@:" + date;
	}
}
