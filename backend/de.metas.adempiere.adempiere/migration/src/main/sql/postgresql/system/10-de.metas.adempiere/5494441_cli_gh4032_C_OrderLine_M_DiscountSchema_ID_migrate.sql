update C_OrderLine ol set M_DiscountSchema_ID=(select b.M_DiscountSchema_ID from M_DiscountSchemaBreak b where b.M_DiscountSchemaBreak_ID=ol.M_DiscountSchemaBreak_ID)
where ol.M_DiscountSchemaBreak_ID is not null and ol.M_DiscountSchema_ID is null;

