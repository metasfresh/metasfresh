/**
 * 
 */
package de.metas.commission.model;

/**
 * @author ts
 *
 */
public interface I_HR_Movement extends org.eevolution.model.I_HR_Movement
{
    public static final String COLUMNNAME_C_Tax_ID = "C_Tax_ID";

	public void setC_Tax_ID (int C_Tax_ID);
	public int getC_Tax_ID();
	
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	public void setC_Currency_ID (int C_Currency_ID);
	public int getC_Currency_ID();
}
