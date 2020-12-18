package de.metas.interfaces;

import de.metas.document.model.IDocumentLocation;

/**
 * {@link org.compiere.model.I_C_OrderLine} extension with Swat columns.
 */
public interface I_C_OrderLine extends org.compiere.model.I_C_OrderLine, IDocumentLocation
{
	// @formatter:off
	String COLUMNNAME_IsDiverse = "IsDiverse";
	boolean isDiverse();
	void setIsDiverse(boolean isDiverse);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_SinglePriceTag = "SinglePriceTag";
	I_C_OrderLine getSinglePriceTag();
	int getSinglePriceTag_ID();
	void setSinglePriceTag_ID(int SinglePriceTag_ID);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_GroupSumTag = "GroupSumTag";
	String getGroupSumTag();
	void setGroupSumTag(String GroupSumTag);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_IsIncludeInTotal = "IsIncludeInTotal";
	String isIncludeInTotal();
	void setIsIncludeInTotal(boolean IsIncludeInTotal);
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_Handover_BPartner_ID = "Handover_BPartner_ID";
	void setHandover_BPartner_ID(int handoverBPartnerId);
	int getHandover_BPartner_ID();
	// @formatter:on

	// @formatter:off
	String COLUMNNAME_HandOver_Location_ID = " HandOver_Location_ID";
	void setHandOver_Location_ID(int handoverLocationId);
	int getHandOver_Location_ID();
	// @formatter:on

	void setM_HU_PI_Item_Product_ID(int M_HU_PI_Item_Product_ID);
	int getM_HU_PI_Item_Product_ID();
}
