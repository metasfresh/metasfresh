UPDATE modcntr_type type
SET ad_javaclass_id = (SELECT ad_javaclass_id FROM ad_javaclass WHERE ad_javaclass.classname = type_temp.classname)
FROM modcntr_type_temp type_temp
WHERE type.modcntr_type_id = type_temp.modcntr_type_id
;

DROP TABLE public.ModCntr_Type_Temp
;