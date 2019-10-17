update M_Cost c set C_Currency_ID=(select acs.C_Currency_ID from C_AcctSchema acs where acs.C_AcctSchema_ID=c.C_AcctSchema_ID)
where C_Currency_ID is null;

update M_Cost c set C_UOM_ID=(select p.C_UOM_ID from M_Product p where p.M_Product_ID=c.M_Product_ID)
where C_UOM_ID is null;

