--M_InOutLine.ProductNo
UPDATE AD_Column
SET
columnSql='(SELECT bpp.ProductNo from C_BPartner_Product bpp where bpp.IsActive=''Y'' AND bpp.C_BPartner_ID = ( SELECT C_BPartner_ID FROM M_InOut io WHERE io.M_InOut_ID = M_InOutLine.M_InOut_ID) AND bpp.M_Product_ID = M_InOutLine.M_Product_ID)',
updatedby=99,
updated=now()
WHERE AD_Column_ID=552570;