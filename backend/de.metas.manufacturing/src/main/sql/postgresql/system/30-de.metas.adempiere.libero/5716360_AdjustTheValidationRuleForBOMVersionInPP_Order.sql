update ad_val_rule
set code ='(PP_Product_BOM.ValidTo IS NULL OR PP_Product_BOM.ValidTo >= ''@DateOrdered@'') AND ( PP_Product_BOM.BomType = ''A'')'
where ad_val_rule_id=540667;
