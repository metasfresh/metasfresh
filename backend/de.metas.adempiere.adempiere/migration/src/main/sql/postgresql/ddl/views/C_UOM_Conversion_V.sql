
CREATE OR REPLACE VIEW C_UOM_Conversion_V AS
SELECT c.AD_Client_ID, c.AD_Org_ID, c.M_Product_ID, c.C_UOM_ID AS C_UOM_From_ID,    c.C_UOM_To_ID as C_UOM_To_ID, c.MultiplyRate as MultiplyRate, c.DivideRate as DivideRate
FROM C_UOM_Conversion c
WHERE c.IsActive='Y'
UNION
SELECT c.AD_Client_ID, c.AD_Org_ID, c.M_Product_ID, c.C_UOM_To_ID AS C_UOM_From_ID, c.C_UOM_ID as C_UOM_To_ID,    c.DivideRate as MultiPlyRate,   c.MultiPlyRate as DivideRate
FROM C_UOM_Conversion c
WHERE c.IsActive='Y'
UNION
SELECT p.AD_Client_ID, p.AD_Org_ID, p.M_Product_ID, p.C_UOM_ID, p.C_UOM_ID, 1, 1
FROM M_Product p
WHERE p.IsActive='Y';
COMMENT ON VIEW C_UOM_Conversion_V IS 'A union that selects all C_UOM_Conversions two times: one time (UOM-from, UOM-to) and a second time (UOM-from, UOM-to). Also muliply and divide rate are switched in the second select
note: field name of C_UOM_Conversion.C_UOM_ID => Maßeinheit Quelle
';
