
-- UOM-conversion between Millimeter and Laufmeter (LM)
-- 2024-04-22T10:45:53.485Z
INSERT INTO C_UOM_Conversion (AD_Client_ID,AD_Org_ID,Created,CreatedBy,C_UOM_Conversion_ID,C_UOM_ID,C_UOM_To_ID,DivideRate,IsActive,IsCatchUOMForProduct,MultiplyRate,Updated,UpdatedBy) 
    SELECT 0,0,TO_TIMESTAMP('2024-04-22 10:45:53.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100,540039,540010,1000001,0.001,'Y','N',1000,TO_TIMESTAMP('2024-04-22 10:45:53.478000','YYYY-MM-DD HH24:MI:SS.US')::timestamp without time zone AT TIME ZONE 'UTC',100
WHERE not exists (select 1 from C_UOM_Conversion where (c_uom_id=540010 and c_uom_to_id=1000001) or (c_uom_id=1000001 and c_uom_to_id=540010))
;
